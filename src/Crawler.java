import java.net.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class Crawler {
    URL myUrl;
    URL firstURL;
    InputStream in;
    ByteArrayOutputStream out;
    byte[] response;
    int downloadDelay;

    public Crawler(String url, int delay) {
        try {
            firstURL = new URL(url);
            URLConnection connection = firstURL.openConnection();
            in = new BufferedInputStream(firstURL.openStream());
            out = new ByteArrayOutputStream();
            downloadDelay=delay;
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void setMyUrl(URL Url) {
        try {
            this.myUrl = Url;
            URLConnection connection = myUrl.openConnection();
            in = new BufferedInputStream(myUrl.openStream());
            out = new ByteArrayOutputStream();
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void downloader(String Filename){
        long startTime=System.currentTimeMillis();
        long currentTime=startTime;
            try {
                byte[] buffer = new byte[2048];
                int n = 0;
                while (-1 != (n = in.read(buffer))) {
                    out.write(buffer, 0, n);
                    currentTime=System.currentTimeMillis();
                    if (currentTime-startTime>=downloadDelay){
                        //TimeExceededException t=new TimeExceededException("Downloading time exceeded...");
                        //t.throwExc();
                        break;
                    }
                }
                out.close();
                in.close();
                response = out.toByteArray();
                writeToFile(Filename);
            }
            catch (IOException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

    }

    public void writeToFile(String Filename) {
        try {
            FileOutputStream fos = new FileOutputStream(Filename);
            fos.write(response);
            fos.close();
        }
        catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean checkContents(){


        return true;
    }

}
