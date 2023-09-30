package com.kmp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.kmp.searchengine.impl.KMPWithLPS;
import com.kmp.searchengine.interfaces.ISearchEngine;

public class App {

    public static void main(String[] args) {
        ISearchEngine kmpImplementation = new KMPWithLPS();
        try (FileReader fileReader = new FileReader("babylone.txt")) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                kmpImplementation.search(line, "Saragon");
            }

            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
