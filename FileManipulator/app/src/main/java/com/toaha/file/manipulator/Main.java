package com.toaha.file.manipulator;

import com.toaha.file.manipulator.FileManipulator;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        FileManipulator fm = new FileManipulator("/storage/emulated/0/Example.txt" , "w");
        fm.write("Hello, World 🌍!");
        
        String ReaderString = new String(new FileManipulator("/storage/emulated/0/Example.txt" , "r").read());
        
        System.out.print(ReaderString);
    }
}