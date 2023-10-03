package com.kmp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.kmp.searchengine.impl.KMPNaive;
import com.kmp.searchengine.impl.KMPWithLPS;
import com.kmp.searchengine.impl.KMPWithOptimizedLPS;
import com.kmp.searchengine.interfaces.ISearchEngine;

public class Finder {

    private ISearchEngine searchEngine;
    private ISearchEngine defaultSearchEngine;

    public Finder() {
        this.defaultSearchEngine = new KMPNaive();
    }

    private ISearchEngine getSearchEngine() {
        if (searchEngine == null) {
            return defaultSearchEngine;
        }
        return this.searchEngine;
    }

    private void setSearchEngine(ISearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    public void finder(String pattern, String fileName) {
        ISearchEngine kmpImplementation = getSearchEngine();
        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int position = kmpImplementation.search(line, pattern);
                if (position != -1) {
                    System.out.println(position + " : " + line);
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSearchEngineToKmpWithLPS() {
        this.setSearchEngine(new KMPWithLPS());
    }

    public void setSearchEngineToKmpWithOptimizedLPS() {
        this.setSearchEngine(new KMPWithOptimizedLPS());
    }
    
}