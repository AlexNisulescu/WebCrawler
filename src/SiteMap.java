import java.io.UTFDataFormatException;
import java.lang.reflect.Array;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * This class implements the Sitemap class that create the sitemap off the
 * website
 * @author Cujba Mihai
 */



public class SiteMap {
    private static SiteMap single_instance = null;
    public ArrayList<Route> Paths;

    /**
     *
     * here is the implementation of the Sitemap Singletone class
     *
     */
    private SiteMap(){
        this.Paths= new ArrayList<Route>();
    }

    public static SiteMap getInstance()
    {
        if (single_instance == null)
            single_instance = new SiteMap();

        return single_instance;
    }


    /**
     *
     * This function return the paths of the Sitemap
     *
     */

    public List<Route> getPaths() {
        return this.Paths;
    }

    /**
     *
     * This function set the paths of the Sitemap
     *
     */

    public void setPaths(ArrayList<Route> paths) {
        this.Paths = paths;
    }

    /**
     *
     * This function replicate a character n times
     * @param c is used for replication
     * @param n is the number of replications
     *
     */

    public String buildString(char c, int n) {
        char[] arr = new char[n];
        Arrays.fill(arr, c);
        return new String(arr);
    }

    /**
     *
     * This function is a recursive function that print the entire
     * Sitemap
     * @param Root is the current root of the path
     * @param level is the current depth of the path
     *
     */

    public void printSitemap(String Root,int level){
        int len=this.Paths.size();
        for(int i=0;i<len;i++){
            if (this.Paths.get(i).getParrent().equals(Root)){
                System.out.println(buildString('\t',level)+this.Paths.get(i)
                        .getRoot());
                printSitemap(this.Paths.get(i).getRoot(),level+1);
            }

        }
    }
    /**
     *
     * This function is a recursive function that create the entire
     * Sitemap
     * @param URL is the current path of the given URL, it changes
     *            by 1 depth when the function is recalled
     *
     */

    public void checkRoot(String URL){
        String[] splitted=URL.split("/");
        String name;
        for(int index=0; index<splitted.length;index++) {
            if (splitted[index].length() > 0) {
                boolean ok = false;
                boolean check2 = false;
                // verific existenta root-ului --------------------------------
                if (this.Paths != null) {
                    for (int i = 0; i < this.Paths.size(); i++) {
                        if (getPaths().get(i).getRoot()
                                .equals(splitted[index])) {
                            ok = true;
                            break;
                        }
                    }
                }
                // ------------------------------------------------------------

                if (!ok) {
                    if (index > 0) {
                        Route x = new Route(splitted[index],
                                splitted[index - 1]);
                        this.Paths.add(x);
                    } else {
                        Route x = new Route(splitted[index],
                                "/");
                        this.Paths.add(x);
                    }
                } else {
                    if (splitted.length == 2) {
                        for (int i = 0; i < this.Paths.size(); i++) {
                            if (getPaths().get(i).getRoot()
                                    .equals(splitted[0])) {
                                getPaths().get(i).addContent(splitted[1]);
                            }
                        }
                    } else {
                        StringBuilder Builder = new StringBuilder();
                        for (int z = index + 1; z < splitted.length; z++) {
                            Builder.append(splitted[z]);
                            Builder.append("/");
                        }
                        this.checkRoot(Builder.toString());
                    }
                }
            }
        }
    }

}
