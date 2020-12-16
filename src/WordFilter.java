import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

/**
 *
 * This class implements the WordFilter that searchs by keyWord
 * We display the files in the directory that contain the keyWord
 * @author Alexandra Naicu
 */

public class WordFilter implements Search {

    private String path;

    /**
     *
     * @param path the directory path from where
     *             we search and display the files
     */

    public WordFilter(String path)
    {

        this.path=path;

    }
    @Override
    /**
     *
     * This function is used to search the keyWord and desplay all files
     * that contain that pattern
     * @throws FileNotFoundException if the file can't be opened
     */

    public void search_method(String keyWord) throws FileNotFoundException{
        File directory = new File(path);
        if (directory == null || !directory.exists()) {
            System.out.println("Directory doesn't exists!");
        }
        else
        {
            Pattern patern = Pattern.compile(keyWord);
            ArrayList<String> filelist = new ArrayList<String>(); //file list
            File[] files=directory.listFiles();

            for (File f : files) {
                try {
                    Scanner scanfFile = new Scanner(f);
                    while(null != scanfFile.findWithinHorizon(patern,0))
                    {

                        MatchResult mr = scanfFile.match();
                        filelist.add(f.getName());
                        break;//to display only once
                    }

                }
                catch(IOException e){
                    ;
                }

            }

            System.out.println("Fisierele in care se gaseste cuvantul sunt: " + filelist); //print all files have that type

        }
    }

}
