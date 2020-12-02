import java.net.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class Crawler {
    private String content;
    URL myUrl;
    BufferedReader br;

    public Crawler(String url) {
        content=new String();
        try {
            myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void setMyUrl(URL Url) {
        this.myUrl = Url;
    }

    public void downloader(){
        String buffer;
        try {
            while ((buffer = br.readLine()) != null) {
                System.out.println(buffer);
                content += buffer;
                content += "\n";
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile(String Filename)
    {
        try {
            FileWriter myObj = new FileWriter(Filename);
            myObj.write(content);
            myObj.close();
        }
        catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void checkContents(){

    }

}
