import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.Vector;

public class Config extends Thread {
    int nThreads;
    String root;
    int logLevel;
    int delay;

    Vector<String> urlVars;
    Crawler crawler;

    public void setnThreads(int nThreads) {
        this.nThreads = nThreads;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getnThreads() {
        return nThreads;
    }

    public String getRoot() {
        return root;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public int getDelay() {
        return delay;
    }



    private void readFileUrl(String file){
        try {
            URL path = ClassLoader.getSystemResource(file);
            File fdata = new File((path.toURI()));
            Scanner reader = new Scanner(fdata);
            int count=0;
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if(count!=0)
                    urlVars.add(data);
                else{
                    crawler=new Crawler(data);
                    //put set for delay
                }
                count++;
            }
        }
        catch (FileNotFoundException | URISyntaxException e) {
            System.out.println("An error occured at opening file");
            e.printStackTrace();
        }
    }

    private void readFileConf(String file){
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
                    datret+=datas[0]+"\\"+datas[1];
                    this.root=datret;
                }
                if(newdata[0].equals("log"))
                    this.logLevel=Integer.parseInt(newdata[1]);
                if(newdata[0].equals("delay"))
                    this.delay=Integer.parseInt(newdata[1]);
            }
        }
        catch (FileNotFoundException | URISyntaxException e) {
            System.out.println("An error occured at opening file");
            e.printStackTrace();
        }
    }



    public Config(int nThreads, String root, int logLevel, int delay) {
        this.nThreads = nThreads;
        this.root = root;
        this.logLevel = logLevel;
        this.delay = delay;
        urlVars=new Vector<>();
    }

    public Config() { urlVars=new Vector<>(); }


    public void recursiveDownload(String conf, String Urls){
        readFileConf(conf);
        readFileUrl(Urls);

        final int threads=nThreads;
        final int lenght=urlVars.size();
        final int balk=(lenght/threads);
        Config[]thread=new Config[threads];
        for(int i=0;i<nThreads;i++){
            final int current=i;
            thread[i]=new Config();
            for(int j=current*balk;j<(current+1)*balk;j++)
                thread[i].addUrl(urlVars.get(j));
            thread[i].start();
        }

    }

    public void parse(){

    }

    public void run() {
        try
        {
            for(String i:urlVars){
                URL myUrl=new URL(i);
                crawler.setMyUrl(myUrl);

                crawler.downloader(getPathFromUrl(i));
            }
        }
        catch (Exception e)
        {
            System.out.println ("Exception is caught");
        }
    }

    public String getPathFromUrl(String url){
        try {
            String[] spliter = url.split("/");
            String data = new String();
            data += root;
            for (int i = 2; i < spliter.length; i++) {
                if (i == 2) {
                    data += "\\" + spliter[i] + "\\";
                } else if (i != spliter.length - 1)
                    data += spliter[i] + "\\";
                else {
                    data += spliter[i] + ".html";
                    File file = new File(data);
                    FileWriter writer = new FileWriter(file);
                }
                if (i != spliter.length - 1) {
                    File file = new File(data);
                    file.mkdirs();
                }
            }
            return data;
        }
        catch (IOException e){
            System.out.println("IO error ");
        }
        return null;
    }

    private void addUrl(String url) {
        urlVars.add(url);
    }

}
