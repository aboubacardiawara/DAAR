package com.kmp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;

import com.kmp.searchengine.interfaces.ISearchEngine;
import com.kmp.searchengine.impl.KMPWithLPS;
import com.kmp.searchengine.impl.KMPWithOptimizedLPS;

public class App {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: mvn exec:java -Dexec.args=\"<pattern> <text_file>\"");
            System.exit(1);
        }
        String pattern = args[0];
        String fileName = args[1];
        System.out.println("Pattern : " + pattern);
        System.out.println("File : " + fileName);
        Long startTime = System.currentTimeMillis();
        ISearchEngine kmpImplementation = new KMPWithOptimizedLPS();
        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int lineCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                int position = kmpImplementation.search(line, pattern);
                if (position != -1) {
                    System.out.println(position + " : " + line);
                    lineCount++;
                }
            }

            fileReader.close();
            System.out.println(lineCount + " lignes contiennent le mot Sargon");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("Temps d'execution : " + (endTime - startTime) + " ms");
        

    }

}
