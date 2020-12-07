
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
         File directory = new File(path);
         if (directory == null || !directory.exists()) {
             System.out.println("Directory doesn't exists!");
         }
         else
         {
            Pattern patern = Pattern.compile(keyWord);
             ArrayList<String> filelist = new ArrayList<String>(); //lista de fisiere in care avem cuvantul
             File[] files=directory.listFiles();

             for (File f : files) {
                 try {
                     Scanner scanfFile = new Scanner(f);
                     while(null != scanfFile.findWithinHorizon(patern,0))
                     {

                         MatchResult mr = scanfFile.match();
                         filelist.add(f.getName());
                         break;//ca sa nu mi afiseze de mai multe ori fisierul in care se gaseste cuvantul
                     }

                 }
                 catch (Exception e) {
                    // System.out.print("Eroare\n");
                 }

             }

             System.out.println("Fisierele in care se gaseste cuvantul sunt: " + filelist);

         }
     }

}
