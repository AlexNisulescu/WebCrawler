package com.company;
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
        //Show all files

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
            System.out.println(f.getName());
        }
    }
}
