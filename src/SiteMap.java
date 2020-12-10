import java.io.UTFDataFormatException;
import java.lang.reflect.Array;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class SiteMap {
    private static SiteMap single_instance = null;
    public ArrayList<Route> Paths;

    private SiteMap(String argument)    {
        try {
            File Reader = new File("file." + argument + ".txt");
            Scanner myReader = new Scanner(Reader);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
        }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private SiteMap(){
        this.Paths= new ArrayList<Route>();
    }

    public static SiteMap getInstance()
    {
        if (single_instance == null)
            single_instance = new SiteMap();

        return single_instance;
    }

    void addRoute(Route component) {
        this.Paths.add(component);
    }

    public List<Route> getPaths() {
        return this.Paths;
    }

    public void setPaths(ArrayList<Route> paths) {
        this.Paths = paths;
    }

    public String buildString(char c, int n) {
        char[] arr = new char[n];
        Arrays.fill(arr, c);
        return new String(arr);
    }

    public void printSitemap(String Root,int level){
        int len=this.Paths.size();
        for(int i=0;i<len;i++){
            //System.out.println(this.Paths.get(i).getRoot()+" "+this.Paths.get(i).getParrent()+" "+Root);
            //System.out.println(this.Paths.get(i).getParrent()+" "+Root+" "+this.Paths.get(i).getParrent().length()+" "+Root.length());
            if (this.Paths.get(i).getParrent().equals(Root)){
                System.out.println(buildString('\t',level)+this.Paths.get(i).getRoot());
                printSitemap(this.Paths.get(i).getRoot(),level+1);
            }

        }
    }

    public void checkRoot(String URL){
        String[] splitted=URL.split("/");
        String name;
        for(int index=0; index<splitted.length;index++) {
            if (splitted[index].length() > 0) {
                boolean ok = false;
                boolean check2 = false;
                // verific existenta root-ului ---------------------------------------------
                if (this.Paths != null) {
                    for (int i = 0; i < this.Paths.size(); i++) {
                        if (getPaths().get(i).getRoot().equals(splitted[index])) {
                            ok = true;
                            break;
                        }
                    }
                }
                // --------------------------------------------------------------------------

                if (!ok) {
                    if (index > 0) {
                        Route x = new Route(splitted[index], splitted[index - 1]);
                        this.Paths.add(x);
                    } else {
                        Route x = new Route(splitted[index], "/");
                        this.Paths.add(x);
                    }
                } else {
                    if (splitted.length == 2) {
                        for (int i = 0; i < this.Paths.size(); i++) {
                            if (getPaths().get(i).getRoot().equals(splitted[0])) {
                                getPaths().get(i).addContent(splitted[1]);
                            }
                        }
                    } else {
                        StringBuilder Builder = new StringBuilder();
                        for (int index2 = index + 1; index2 < splitted.length; index2++) {
                            Builder.append(splitted[index2]);
                            Builder.append("/");
                        }
                        this.checkRoot(Builder.toString());
                    }
                }
            }
        }
    }

}
