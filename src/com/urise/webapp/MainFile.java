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
        getAllFileNamesInDirectory("D:\\_JAVA\\!Internship\\JavaOps\\basejava");
    }


    private static void getAllFileNamesInDirectory(String directoryName) {
        File dir = new File(directoryName);
        File[] list = dir.listFiles();
        if (list != null) {
            for (File eachFile : list) {
                try {
                    String path = eachFile.getCanonicalPath();
                    File directoryOrFile = new File(path);
                    if (directoryOrFile.isDirectory()) {
                        getAllFileNamesInDirectory(path);
                    } else {
                        System.out.println(eachFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}