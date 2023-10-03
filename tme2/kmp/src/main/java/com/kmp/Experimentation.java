package com.kmp;

import java.io.FileWriter;
import java.io.IOException;

public class Experimentation {
    public static void main(String[] args) {
        String fileName = "xp_result.csv";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            writeDuration(fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeDuration(FileWriter fileWriter) throws IOException {
        int linesCount = 100;
        XpResult[] results = new XpResult[linesCount];
        String pattern = "Sargon";
        String fileName = "note.txt";
        Finder finder = new Finder();
        for (int i = 0; i < linesCount; i++) {
            // measure naive implementation time
            Long startTime = System.currentTimeMillis();
            finder.finder(pattern, fileName);
            Long endTime = System.currentTimeMillis();
            Long naiveDuration = endTime-startTime;

            // measure lps implementation time
            startTime = System.currentTimeMillis();
            finder.setSearchEngineToKmpWithLPS();
            finder.finder(pattern, fileName);
            endTime = System.currentTimeMillis();
            Long lpsDuration = endTime-startTime;

            // measure optimized lps implementation time
            startTime = System.currentTimeMillis();
            finder.setSearchEngineToKmpWithOptimizedLPS();
            finder.finder(pattern, fileName);
            endTime = System.currentTimeMillis();
            Long optimizedLpsDuration = endTime-startTime;

            XpResult xpResult = new XpResult();
            xpResult.setLigneCount(i);
            xpResult.setNaiveDuration(naiveDuration);
            xpResult.setLpsDuration(lpsDuration);
            xpResult.setOptimizedLpsDuration(optimizedLpsDuration);

            results[i] = xpResult;
        }

        // ecriture de l'entete du fichier
        String enTete = "#nb_line naive_duration lps_duration optimized_lps_duration";
        fileWriter.write(enTete);
        // ecriture du corps du fichier
        for (int i = 0; i < linesCount; i++) {
            XpResult result = results[i];
            String line = result.getLigneCount() + " " + result.getNaiveDuration() + " " + result.getLpsDuration() + " " + result.getOptimizedLpsDuration() + "\n";
            fileWriter.write(line);   
        }

    }
}


class XpResult {
    private int ligneCount;
    private Long naiveDuration;
    private Long lpsDuration;
    private Long optimizedLpsDuration;

    public int getLigneCount() {
        return ligneCount;
    }
    public void setLigneCount(int ligneCount) {
        this.ligneCount = ligneCount;
    }
    public Long getNaiveDuration() {
        return naiveDuration;
    }
    public void setNaiveDuration(Long naiveDuration) {
        this.naiveDuration = naiveDuration;
    }
    public Long getLpsDuration() {
        return lpsDuration;
    }
    public void setLpsDuration(Long lpsDuration) {
        this.lpsDuration = lpsDuration;
    }
    public Long getOptimizedLpsDuration() {
        return optimizedLpsDuration;
    }
    public void setOptimizedLpsDuration(Long optimizedLpsDuration) {
        this.optimizedLpsDuration = optimizedLpsDuration;
    }
    
}