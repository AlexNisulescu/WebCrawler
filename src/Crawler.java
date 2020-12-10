import java.net.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class Crawler {
    URL myUrl;
    InputStream in;
    ByteArrayOutputStream out;
    byte[] response;

    public Crawler() {

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
        try {
            byte[] buffer = new byte[2048];
            int n = 0;
            while (-1 != (n = in.read(buffer))) {
                out.write(buffer, 0, n);
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

    public void checkContents(){

    }

}
