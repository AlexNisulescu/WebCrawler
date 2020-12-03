package com.company;
import java.io.File;
import java.io.FilenameFilter;

public class typeFilter implements Search{
    private String type;
    private String path = null;
    public typeFilter(String type, String path)
    {
        this.type=type;
        this.path=path;
    }
    @Override
    public void search_method() {
        System.out.println("Aici voi realiza cautarea dupa tip");
    }
}
