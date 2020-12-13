import java.net.*;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * This class implements the Crawler that downloads the website that is given
 * to it via the URL and saves it to the file that is passed as an argument
 * to the downloader(Filename) method
 * @author Alexandru Nisulescu
 */

public class Crawler {
    URL myUrl;
    InputStream in;
    ByteArrayOutputStream out;
    byte[] response;
    int downloadDelay;

    /**
     *
     * @param delay Is the maximum time the function will spent trying to
     *              download from that specific URL
     */
    public Crawler(int delay) {
        downloadDelay=delay;
    }

    /**
     *
     * @param Url The url that needs to be downloaded
     */
    public void setMyUrl(String Url) throws ConnectionFailedException{
        try {
            this.myUrl = new URL(Url);
            HttpURLConnection con =(HttpURLConnection) myUrl.openConnection();
            con.setRequestMethod("HEAD");
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                in = new BufferedInputStream(myUrl.openStream());
                out = new ByteArrayOutputStream();
            }
            else{
                throw new ConnectionFailedException("The website " + Url +
                        "is not available...");
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public byte[] getResponse() {
        return response;
    }

    /**
     *
     * This function is used to download data from a specific URL that has been
     * already set
     *
     * @param Filename The file where you want your data to be stored
     * @exception CrawlForbiddenException that is thrown when the web page is
     * protected and can't be downloaded
     */
    public void downloader(String Filename) throws CrawlForbiddenException{
        String strURL;
        if (checkContents()) {
            try {
                getContents();
                try {
                    strURL=myUrl.getPath().toString().substring(1);
                    SiteMap x = SiteMap.getInstance();
                    x.checkRoot(strURL);
                    WriteLogToFile logger=new WriteLogToFile("Downloaded: " +
                            myUrl.toString());
                    try{
                        logger.setupLogger();
                        logger.info();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    writeToFile(Filename);
                }
                catch (FileNotFoundException e)
                {
                    e.throwExc();
                }

            }
            catch (TimeExceededException e){
                e.throwExc();
            }
        }
        else{
            String url=myUrl.toString();
            throw new CrawlForbiddenException("This website is not allowing " +
                    "the download of: "+ url + " ...");
        }

    }

    /**
     *
     * @throws TimeExceededException if the download time exceeds the user
     * given delay
     */
    public void getContents()throws TimeExceededException{
        long startTime=System.currentTimeMillis();
        long currentTime=startTime;
        try {
            byte[] buffer = new byte[2048];
            int n = 0;
            while (-1 != (n = in.read(buffer))) {
                out.write(buffer, 0, n);
                currentTime = System.currentTimeMillis();
                if (currentTime - startTime >= downloadDelay) {
                    throw new TimeExceededException("Downloading time " +
                            "exceeded...");
                }
            }
            out.close();
            in.close();
            response = out.toByteArray();
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param Filename is the file where you want to write your data to
     * @throws FileNotFoundException if the file can't be opened
     */
    public void writeToFile(String Filename) throws FileNotFoundException{
        try {
            FileOutputStream fos = new FileOutputStream(Filename);
            fos.write(response);
            fos.close();
        }
        catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new FileNotFoundException("The file you are trying to open " +
                    "doesn't exist...");
        }
    }

    /**
     *
     * @see RobotRule
     * @return True if you can download that page and False if robots.txt is
     * not allowing you to download that specific page
     */
    public boolean checkContents(){

        String host= myUrl.getHost();
        String robot= "https://" + host + "/robots.txt";
        URL urlRobot;
        try { urlRobot = new URL(robot);
        } catch (MalformedURLException e) {
            //There might be a virus or something here so if this doesn't work
            // don't thrust the website
            return false;
        }
        String strCommands;
        try
        {

            InputStream urlRobotStream = urlRobot.openStream();
            byte b[] = new byte[1000];
            int numRead = urlRobotStream.read(b);
            strCommands = new String(b, 0, numRead);
            while (numRead != -1) {
                numRead = urlRobotStream.read(b);
                if (numRead != -1)
                {
                    String newCommands = new String(b, 0, numRead);
                    strCommands += newCommands;
                }
            }
            urlRobotStream.close();
        }
        catch (IOException e)
        {
            return true; // if there is no robots.txt file, it is OK to search
        }
        if (strCommands.contains("Disallow"))
        {
            String[] split = strCommands.split("\n");
            ArrayList<RobotRule> robotRules = new ArrayList<>();
            String mostRecentUserAgent = null;
            for (int i = 0; i < split.length; i++)
            {
                String line = split[i].trim();
                if (line.toLowerCase().startsWith("user-agent"))
                {
                    int start = line.indexOf(":") + 1;
                    int end   = line.length();
                    mostRecentUserAgent = line.substring(start, end).trim();
                }
                else if (line.startsWith("Disallow")) {
                    if (mostRecentUserAgent != null) {
                        RobotRule r = new RobotRule();
                        r.userAgent = mostRecentUserAgent;
                        int start = line.indexOf(":") + 1;
                        int end   = line.length();
                        r.rule = line.substring(start, end).trim();
                        robotRules.add(r);
                    }
                }
            }
            for (RobotRule robotRule : robotRules)
            {
                String path = myUrl.getPath();
                if (robotRule.rule.length() == 0) return true; // allows everything if the file is empty
                if (robotRule.rule == "/") return false;       // the website doesn't allow crawl

                if (robotRule.rule.length() <= path.length())
                {
                    String pathCompare = path.substring(0,
                            robotRule.rule.length());
                    if (pathCompare.equals(robotRule.rule)) return false;
                }
            }
        }
        return true;
    }

}
