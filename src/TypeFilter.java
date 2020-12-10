import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class TypeFilter implements Search{
    private String type;
    private String path = null;
    public TypeFilter(String type, String path)
    {
        this.type=type;
        this.path=path;
    }
    @Override
    public void search_method() {
    File directory = new File(this.path);
    if(!directory.exists()) //verificam daca nu  exista calea catre acel director si afisam un mesaj corespunzator
    {
        System.out.println(String.format("Directory %s does not exist", this.path)); //verificam daca exista calea catre acel director
        return;
    }
        if (!directory.isDirectory())//verificam daca calea  nu este a unui  director
        {
            System.out.println(String.format("Provided value %s is not a directory", this.path));
            return;
        }
        //Show all files

      FileFilter allfiles = new FileFilter() {
          @Override
          public boolean accept(File pathname) {
              if(pathname.getName().endsWith(type))//verificam extensiile
              {
                  return true;
              }
              return false;
          }
      };
        File[] files = directory.listFiles(allfiles);//realizam o lista cu toate fisierele din director
        for (File f: files)
        {
            System.out.println(f.getName());//le afisam pe cele care indeplinesc conditia
        }
    }
}
