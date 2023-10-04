package com.kmp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

public class Experimentation {


    private static int INSTANCE_COUNT = 10;
    public static void main(String[] args) {
        
        String fileName = "xp_result.csv";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            writeDuration(fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static long countLinesInFile(File file) {
        long lineCount = 0;
        try {
            Path filePath = file.toPath();
            lineCount = Files.lines(filePath).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }


    public static void trierFichiersParNombreDeLignes(File[] files) {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                int nombreDeLignes1 = extraireNombreDeLignes(file1.getName());
                int nombreDeLignes2 = extraireNombreDeLignes(file2.getName());
                return Integer.compare(nombreDeLignes1, nombreDeLignes2);
            }
        });
    }

    public static int extraireNombreDeLignes(String nomFichier) {
        try {
            // Supprime l'extension .txt et convertit le reste en entier
            return Integer.parseInt(nomFichier.replaceAll("\\.txt$", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0; // En cas d'erreur, retournez 0 (ou une valeur par défaut)
        }
    }

    private static void writeDuration(FileWriter fileWriter) throws IOException {
        String dirName = "xp_ressources/xp_files";
        File dir  = new File(dirName);
        File[] files = dir.listFiles();
        trierFichiersParNombreDeLignes(files);
        int n = files.length;
        XpResult[] results = new XpResult[files.length];
        String pattern = "Sargon";
        Finder finder = new Finder();
        for (int i = 0; i < n; i++) {
            File file = files[i]; 
            String fileName = dirName + "/" + file.getName();
            long linesCount = countLinesInFile(file);
            
            // measure naive implementation time
            Long naiveDuration = averageDuration(pattern, finder, fileName);
            
            // measure lps implementation time
            finder.setSearchEngineToKmpWithLPS();
            Long lpsDuration = averageDuration(pattern, finder, fileName);

            // measure optimized lps implementation time
            finder.setSearchEngineToKmpWithOptimizedLPS();
            Long optimizedLpsDuration = averageDuration(pattern, finder, fileName);

            XpResult xpResult = new XpResult();
            xpResult.setLigneCount((int) linesCount);
            xpResult.setNaiveDuration(naiveDuration);
            xpResult.setLpsDuration(lpsDuration);
            xpResult.setOptimizedLpsDuration(optimizedLpsDuration);

            results[i] = xpResult;
            
        }
        

        // ecriture de l'entete du fichier
        String enTete = "#nb_line naive_duration(ms) lps_duration(ms) optimized_lps_duration(ms)\n";
        fileWriter.write(enTete);
        // ecriture du corps du fichier
        for (int i = 0; i < n; i++) {
            XpResult result = results[i];
            String line = result.getLigneCount() + " " + result.getNaiveDuration() + " " + result.getLpsDuration() + " " + result.getOptimizedLpsDuration() + "\n";
            fileWriter.write(line);   
        }

    }

    private static Long averageDuration(String pattern, Finder finder, String fileName) {
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < INSTANCE_COUNT; i++) {
            runAlgo(pattern, finder, fileName);
        }
        Long endTime = System.currentTimeMillis();
        Long totalDuration = endTime-startTime;
        return totalDuration/INSTANCE_COUNT;
    }

    private static void runAlgo(String pattern, Finder finder, String fileName) {
        finder.finder(pattern, fileName);
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
