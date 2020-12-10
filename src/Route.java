import java.util.ArrayList;
import java.util.List;

public class Route {
    private ArrayList<String> content;
    private String root;
    private String parrent;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParrent() {
        return parrent;
    }

    public void setParrent(String parrent) {
        this.parrent = parrent;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
        this.content=new ArrayList<String>();
    }

    public Route(String root,String parrent) {
        this.setRoot(root);
        this.setParrent(parrent);
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public void addContent(String path){
        this.content.add(path);
    }

    public void getAllContent(){
        for(int i=0;i<content.size();i++){
            System.out.print(getContent().get(i));
        }
    }

}
