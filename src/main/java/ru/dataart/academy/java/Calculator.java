package ru.dataart.academy.java;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Calculator {
    public static void deleteAllFilesFolder(String path) {
        for (File myFile : Objects.requireNonNull(new File(path).listFiles()))
            if (myFile.isFile())
                myFile.delete();
    }

    public int checkMaxLength(String filePath, int max) {
        String line;
        try {
            Scanner in = new Scanner(new File(filePath));
            while (in.hasNextLine()) {

                line = in.nextLine();
                String[] words = line.split(" ");
                for (String word : words) {
                    if (word.length() > max) {
                        max = word.length();
                    }
                }

            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return max;
    }

    public int checkNumberOfCharacters(String filePath, char character, int counter) {
        String line;
        String stCharacter = Character.toString(character);
        try {
            Scanner in = new Scanner(new File(filePath));
            while (in.hasNextLine()) {

                line = in.nextLine();
                String[] words = line.split(" ");
                for (String word : words) {
                    if (word.contains(stCharacter)) {
                        counter += 1;
                    }
                }

            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public static void unzipFile(String zipFilePath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream("src\\main\\java\\ru\\dataart\\academy\\Unziped\\" + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }

    }


    /**
     * @param zipFilePath -  path to zip archive with text files
     * @param character   - character to find
     * @return - how many times character is in files
     * BufferedReader reader = null;
     * try {
     * reader = new BufferedReader(new FileReader(""))
     * }
     */
    public Integer getNumberOfChar(String zipFilePath, char character) {
        int counter = 0;
        unzipFile(zipFilePath);
        File folder = new File("src\\main\\java\\ru\\dataart\\academy\\Unziped");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                counter = checkNumberOfCharacters(file.getPath(), character, counter);
            }
        }
        deleteAllFilesFolder("src\\main\\java\\ru\\dataart\\academy\\Unziped");
        return counter;
    }

    /**
     * @param zipFilePath - path to zip archive with text files
     * @return - max length
     */

    public Integer getMaxWordLength(String zipFilePath) {
        int max = 0;
        unzipFile(zipFilePath);
        File folder = new File("src\\main\\java\\ru\\dataart\\academy\\Unziped");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                max = checkMaxLength(file.getPath(), max);
            }
        }
        deleteAllFilesFolder("src\\main\\java\\ru\\dataart\\academy\\Unziped");
        return max;
    }

}
