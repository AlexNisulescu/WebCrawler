import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Semaphore;

/**
 *
 *  This class implements a parser for Crawler with URLs and create a
 *  hierarchy of directories. Also, fix the root of directories where
 *  files will be downloaded and can parse URLs on threads.
 * @author Laudan Daniel
 */

public class Config extends Thread {
    int nThreads;
    String root;
    int logLevel;
    int delay;

    private Vector<String> urlVars;
    private ArrayList<String> urlCopy;
    private Crawler crawler;
    private Semaphore mutex;

    /**
     *
     *  Constructor.
     *  Initialize a vector of strings, urlVars where is urls
     *  for thread call function.
     *
     */
    public Config() {
        urlVars=new Vector<>();
        urlCopy=new ArrayList<>();
    }

    /**
     *
     * @param nThreads number of threads which download pages.
     */
    public void setnThreads(int nThreads) {
        this.nThreads = nThreads;
    }

    /**
     *
     * @param root root where hierarchy of directories will be downloaded.
     */
    public void setRoot(String root) {
        this.root = root;
    }

    /**
     *
     * @param logLevel deep search for href url.
     */
    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    /**
     *
     * @param delay param delay for crawler.
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     *
     * @param crawler set crawler instance where threads of config will be created.
     */
    public void setCrawler(Crawler crawler){
        this.crawler=crawler;
    }

    /**
     * Getter.
     * @return number of threas.
     */
    public int getnThreads() {
        return nThreads;
    }

    /**
     * Getter.
     * @return root path.
     */
    public String getRoot() {
        return root;
    }

    /**
     * Getter.
     * @return log-level for deep find.
     */
    public int getLogLevel() {
        return logLevel;
    }

    /**
     * Getter.
     * @return delay for Crawler instance.
     */
    public int getDelay() {
        return delay;
    }

    /**
     *
     * @return instance of urlVars.
     */
    public Vector<String> getUrlVars() {
        return urlVars;
    }

    /**
     *
     * @return return instance of urlCopy.
     */
    public ArrayList<String> getUrlCopy() {
        return urlCopy;
    }


    /**
     *
     * This function read urlsFile and introduce urls in
     * urlVars (Vector<String>)
     * @param file File of urls. Url line by line.
     */
    private void readFileUrl(String file) throws IOException {
        try {
            URL path = ClassLoader.getSystemResource(file);
            File fdata = new File((path.toURI()));
            Scanner reader = new Scanner(fdata);
            int count=0;
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                urlVars.add(data);
                count++;
            }
        }
        catch (IOException | URISyntaxException e) {
            System.out.println("An error occured at opening file");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file File of config. Search for threads, root, log_level and delay.
     */
    private void readFileConf(String file) throws IOException {
        try {
            URL path = ClassLoader.getSystemResource(file);
            File conf = new File((path.toURI()));
            Scanner reader = new Scanner(conf);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String []newdata;
                newdata=data.split("=");
                if(newdata[0].equals("thread"))
                    this.nThreads=Integer.parseInt(newdata[1]);
                if(newdata[0].equals("root_dir")){
                    String []datas= newdata[1].split("/");
                    String datret=new String();
                    for(int i=0;i<datas.length;i++)
                        datret+=datas[i]+"\\";
                    //datret+=datas[0]+"\\"+datas[1];
                    this.root=datret;
                }
                if(newdata[0].equals("log"))
                    this.logLevel=Integer.parseInt(newdata[1]);
                if(newdata[0].equals("delay"))
                    this.delay=Integer.parseInt(newdata[1]);
            }
        }
        catch (IOException | URISyntaxException e) {
            System.out.println("An error occured at opening file");
            e.printStackTrace();
        }
    }


    /**
     * This function download pages recursive with initial configFile and urlsFile.
     * @param conf configFile
     * @param Urls urlsFile
     * This function will create a nThread number which will start to download pages.
     * Threads will split theier urls and parse them to parser for go deep in page
     * where will be downloading more pages.
     */
    public void recursiveDownload(String conf, String Urls) throws FileNotFoundException{
        try {
            readFileConf(conf);
            readFileUrl(Urls);

            Crawler[] crawl=new Crawler[nThreads];
            final int threads = nThreads;
            final int lenght = urlVars.size();
            final int balk = (lenght / threads);
            Config[] thread = new Config[threads];
            for (int i = 0; i < nThreads; i++) {
                final int current = i;
                thread[i] = new Config();
                crawl[i]=new Crawler(delay);
                thread[i].setCrawler(crawl[i]);
                thread[i].setRoot(root);
                for (int j = current * balk; j < (current + 1) * balk; j++) {
                    thread[i].addUrl(urlVars.get(j));
                }
                thread[i].run();

                for (int k = 0; k < logLevel; k++) {
                    for (int j = 0; j < thread[i].getUrlVars().size(); j++) {
                        ArrayList<String> strings = new ArrayList<String>();
                        strings = parse(thread[i].getPathFromUrl(urlVars.get(i)));

                        if (!strings.isEmpty())
                            for (int ct = 0; ct < strings.size(); ct++) {
                                if(!thread[i].getUrlCopy().contains(strings.get(ct))) {
                                    thread[i].getUrlCopy().add(strings.get(ct));
                                }
                            }
                        else {
                            break;
                        }
                    }

                    thread[i].getUrlVars().clear();
                    for (int j = 0; j < thread[i].getUrlCopy().size(); j++)
                        thread[i].getUrlVars().add(thread[i].getUrlCopy().get(j));
                    thread[i].getUrlCopy().clear();

                    thread[i].run();
                }
            }
        }
        catch(Exception e){
            System.out.println("Error at recursive downlaod");
            e.printStackTrace();
        }

    }

    /**
     *
     * @param path is the hierarchy of directories is set and parse this for find
     *             more url pages.
     * @return this will return a list of urls if it is valid or null.
     */
    public ArrayList<String> parse(String path) throws BadArgumentsException{
        try {
            Parser parser = new Parser();
            parser.parse(path);
            return parser.getURLs();

        }
        catch (Exception e){
            System.out.println("Bad argument");
        }
        return null;
    }

    /**
     * This is the override function of threads which will be call for each thread.
     */
    public void run() {
            for (String i : urlVars) {
                //mutex.acquire();
                String string=new String();
                try {
                    crawler.setMyUrl(i);
                    string = getPathFromUrl(i);
                    createPath(string);
                }
                catch(Exception e){
                    System.out.println("Error at run");
                    e.printStackTrace();
                }
                if(string!=null)
                    crawler.downloader(string);
                //mutex.release();
            }
    }

    /**
     *
     * @param url is page url.
     * @return this return a String in format for accessing it.
     */
    public String getPathFromUrl(String url) throws Exception{
        try {
            String[] spliter = url.split("/");
            String data = new String();
            data += root;
            String[] splitt=root.split("\\\\");
            int index=splitt.length;
            for (int i = index-1; i < spliter.length; i++) {
                if (i == index-1) {
                    data += spliter[i] + "\\";
                } else if (i != spliter.length - 1 )
                    data += spliter[i] + "\\";
                else {
                    data += spliter[i] + ".html";
                }
            }
            if(data.endsWith("\\")) {
                data = data.substring(0, data.length() - 1);
                data+=".html";
            }
            return data;
        }
        catch (Exception e){
            System.out.println("Error at getting path from url");
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param path is directoies path String for creating hierarchy of directories
     */
    public void createPath(String path) throws FileNotFoundException{
        if(path.endsWith("\\")) {
            path = path.substring(0, path.length() - 1);
        }
        String[] spliter = path.split("\\\\");
        String data=new String();
        data+=root;
        String[] splitt=root.split("\\\\");
        int index=splitt.length;
        try {
            for (int i = index; i < spliter.length; i++) {
                 if (i != spliter.length - 1)
                    data += spliter[i] + "\\";
                else {
                    data += spliter[i];
                    File file = new File(data);
                    FileWriter writer = new FileWriter(file);
                }
                if (i != spliter.length - 1) {
                    File file = new File(data);
                    file.mkdirs();
                }
            }
        }
        catch (IOException e){
            System.out.println("Error at create path");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param url add an url to Vector<String> urlVars.
     */
    private void addUrl(String url) {
        urlVars.add(url);
    }

    /**
     *
     * @param url add an url to ArrayList<String> urlCopy.
     */
    private void addUrlCopy(String url){
        urlCopy.add(url);
    }
}
