import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class implements the Routes that Sitemap uses to recreate the
 * paths of the website
 * @author Cujba Mihai
 */


public class Route {
    private ArrayList<String> content;
    private String root;
    private String parrent;
    private int level;

    public int getLevel() {
        return level;
    }
    /**
     *
     * @param level is the current depth of the route. It's used for
     *              the indentation
     */
    public void setLevel(int level) {
        this.level = level;
    }
    /**
     *
     * @return the parrent of the current root
     *
     */
    public String getParrent() {
        return parrent;
    }
    /**
     *
     * @param parrent set the parrent of current root
     *
     */
    public void setParrent(String parrent) {
        this.parrent = parrent;
    }
    /**
     *
     * @return the root of the route
     *
     */
    public String getRoot() {
        return root;
    }
    /**
     *
     * @param root set the root of the route
     *
     */
    public void setRoot(String root) {
        this.root = root;
        this.content=new ArrayList<String>();
    }

    /**
     *
     * @param root set the root of the route
     * @param parrent set the parrent of the route
     *
     */

    public Route(String root,String parrent) {
        this.setRoot(root);
        this.setParrent(parrent);
    }

    /**
     *
     * @return the content of the route
     *
     */


    public List<String> getContent() {
        return content;
    }

    /**
     *
     * @param content set the entire content of the route
     *
     */
    public void setContent(ArrayList<String> content) {
        this.content = content;
    }


    /**
     *
     * @param path add content to the route
     *
     */

    public void addContent(String path){
        this.content.add(path);
    }
    /**
     *
     * this method print all the content of the route
     *
     */
    public void getAllContent(){
        for(int i=0;i<content.size();i++){
            System.out.print(getContent().get(i));
        }
    }

}
