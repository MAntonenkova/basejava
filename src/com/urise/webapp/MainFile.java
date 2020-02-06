package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file2 = new File("D:\\_JAVA\\!Internship\\JavaOps\\basejava");
        getAllFileNamesInDirectory(file2);
    }

    private static void getAllFileNamesInDirectory(File fileOrDir) {
        File[] list = fileOrDir.listFiles();
        if (list != null) {
            for (File eachFile : list) {
                if (eachFile.isDirectory()) {
                    System.out.println("Directory: " + eachFile.getName());
                    getAllFileNamesInDirectory(eachFile);
                } else if (eachFile.isFile()) {
                    System.out.println("    File: " + eachFile.getName());
                }
            }
        }
    }
}