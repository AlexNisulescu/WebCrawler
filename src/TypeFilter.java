import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 *
 * This class implements the TypeFilter that searchs by file type
 * We display the files in the directory have that type
 * @author Alexandra Naicu
 */

public class TypeFilter implements Search{
    private String type;
    private String path = null;
    
     /**
     *
     * @param type the extension according to which 
     *             we search and display the files
     */
    
    /**
     *
     * @param path the directory path from where 
     *             we search and display the files
     */
    
    public TypeFilter(String type, String path)
    {
        this.type=type;
        this.path=path;
    }
    
     /**
     *
     * This function is used to search and deplay all files 
     * that have that extension type
     * @return True if files with that extension are found in the directory
     */
    
    @Override
    public void search_method() {
    File directory = new File(this.path);
    if(!directory.exists())
    {
        System.out.println(String.format("Directory %s does not exist", this.path));
        return;
    }
        if (!directory.isDirectory())
        {
            System.out.println(String.format("Provided value %s is not a directory", this.path));
            return;
        }
      
      FileFilter allfiles = new FileFilter() {
          @Override
          public boolean accept(File pathname) {
              if(pathname.getName().endsWith(type))
              {
                  return true;
              }
              return false;
          }
      };
        File[] files = directory.listFiles(allfiles);
        for (File f: files)
        {
            System.out.println(f.getName()); //print all files have that type
        }
    }
}
