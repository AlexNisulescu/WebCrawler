package com.company;
import java.io.File;
import java.io.FilenameFilter;

public class wordFilter implements Search {
    private String path;
    private String keyWord;
     public wordFilter(String keyWord, String path )
     {
         this.keyWord=keyWord;
         this.path=path;

     }
     @Override
    public void search_method() {
        System.out.println("Aici voi realiza cautarea dupa cuvant keie");
    }

}
