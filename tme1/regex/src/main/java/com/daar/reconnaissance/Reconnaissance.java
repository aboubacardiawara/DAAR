package com.daar.reconnaissance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;
import com.daar.automate.NoSuchTransition;
import com.daar.parsing.RegExTree;
import com.daar.parsing.RegexParser;
import com.daar.automatetotab.AutomatetoTab;
import java.net.*;

public class Reconnaissance{

    /**
     * Prend un texte et une automate decrivant un langage
     * et verifie si le texte appartient au langage.
     * 
     * @throws NoSuchTransition
     */
    public static boolean match(String text, IAutomate automate) {
        int i = 0;
        if (text.length() == 0)
            return automate.match(text);
        int n = text.length();
        while (i < n) {
            String subtrs = text.substring(i);
            if (automate.match(subtrs)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static void search(String fileName, String regex) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            long t0 = System.currentTimeMillis();
            IAutomate automate = new AutomateBuilder().buildFromRegex(regex);
           // BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));
            long t1 = System.currentTimeMillis();
            Logger LOGGER = Logger.getLogger(Reconnaissance.class.getName());
            //LOGGER.info("[creation and optimization] Durée: " + (t1 - t0) + " (ms)");
            t0 = System.currentTimeMillis();
            String ligne;
            int count_lignes= 0;
            while ((ligne = bufferedReader.readLine()) != null) {
                if (match(ligne, automate)){
                    System.out.println(ligne);
                    count_lignes++;
                }
                    
            }
           //writer.close();
           bufferedReader.close();
           //fileReader.close();
           t1 = System.currentTimeMillis();
           System.out.println("le nombre de lignes " +count_lignes);
           LOGGER.info("[Search] Durée: " + (t1 - t0) + " (ms)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
    /**
     * 
     */
    public static  void experimental(String fileName,int nbr_dupliquer) {
        //testTailleAutomate();
        testDureeRechercheAutomate(fileName,nbr_dupliquer);
    }

    public static  void experimental_avrg(String regex,int nbr_dupliquer) throws IOException {
        long totale_egrep=0;
        long totale_afd=0;
        int nb_files=10;
        String regEx = regex;
        long file_size=0;
        BufferedWriter writer3 = new BufferedWriter(new FileWriter("Results/Time_AFD_average.txt",true));
        BufferedWriter writer4 = new BufferedWriter(new FileWriter("Results/Time_Egrep_average.txt",true));
        for (int i= 0; i<nb_files; i++){
            String file_test="/Users/linaazerouk/Documents/M2/Daar/projet1Daar/DAAR/tme1/regex/Results/test_files/testfile"+i+".txt";     
            file_size= new File(file_test).length();
           
                for (int x=0;x<=nbr_dupliquer; x++){ 
                    try {
                        //Time for Egrep
                        ProcessBuilder processBuilder = new ProcessBuilder("egrep",regEx,file_test);
                        long startTime = System.currentTimeMillis();
                        processBuilder.redirectErrorStream(true);
                        Process process = processBuilder.start();
                        int exitCode = process.waitFor();
                        long endTime = System.currentTimeMillis(); 
                        long executionTime = endTime - startTime;
                        totale_egrep= executionTime+totale_egrep;

                        //Time for AFD:
                        long t0 = System.currentTimeMillis();
                        FileReader fileReader = new FileReader(file_test);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        IAutomate automate = new AutomateBuilder().buildFromRegex(regEx);
                        String ligne;
                        while ((ligne = bufferedReader.readLine()) != null) {
                            if (match(ligne, automate))
                            System.out.println(ligne);
                        }
                        bufferedReader.close();
                        fileReader.close();
                        long t1 = System.currentTimeMillis()-t0;
                        totale_afd= t1+totale_afd;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            
        }
        writer3.write(file_size+" "+(totale_afd/nb_files)+ "\n");
        writer4.write(file_size+ " " +(totale_egrep/nb_files)+ "\n");
        writer3.close();
        writer4.close();
    }
        
    private static void testDureeRechercheAutomate(String fileName,int nbr_dupliquer) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }
        try {
        String regEx = "S(a|g|r)+on";
        BufferedWriter writer3 = new BufferedWriter(new FileWriter("Results/Time_Egrep.txt",true));
        BufferedWriter writer4 = new BufferedWriter(new FileWriter("Results/Time_AFD.txt",true));
        for (int x=0;x<=nbr_dupliquer; x++){ 
            try {
                long file_size= file.length();
                //Time for Egrep
                ProcessBuilder processBuilder = new ProcessBuilder("egrep",regEx,fileName);
                long startTime = System.currentTimeMillis();
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                //BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                int exitCode = process.waitFor();
                System.out.print(fileName);
                long endTime = System.currentTimeMillis(); 
                //reader.close();
                long executionTime = endTime - startTime;
                writer3.write(file_size+" "+executionTime+ "\n");

                //Time for AFD:
                long t0 = System.currentTimeMillis();
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                IAutomate automate = new AutomateBuilder().buildFromRegex(regEx);
                String ligne;
                while ((ligne = bufferedReader.readLine()) != null) {
                    if (match(ligne, automate))
                        System.out.println(ligne);
                }
                bufferedReader.close();
                fileReader.close();

                long t1 = System.currentTimeMillis()-t0;
                writer4.write(file_size+ " " +t1+ "\n");
                dupliquer(fileName); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            writer3.close();
            writer4.close();
         } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void testTailleAutomate() {
        try {
        BufferedWriter writer1 = new BufferedWriter(new FileWriter("Results/size_result_experimental_epsilon.txt"));
        BufferedWriter writer2= new BufferedWriter(new FileWriter("Results/size_result_experimental_AFD_non_optimisé.txt"));
        BufferedWriter writer3= new BufferedWriter(new FileWriter("Results/size_result_experimental_AFD_optimise.txt"));

        String regex= "c|d*c";
        RegexParser parser = new RegexParser();
        int numPoints = 10;
        int[] yPoints_epsilon= new int[numPoints];
        int[] yPoints_deterministic= new int[numPoints];
        int [] yPoints_deterministic_optimized= new int[numPoints];
        //TEST: pour la taille des automates
        for (int x = 0; x <numPoints; x++) {
            try {
            RegExTree regexTree = parser.parse(regex);
            IAutomate automateWithEpsilonTransitions = regexTree.toAutomate();
            yPoints_epsilon[x] =automateWithEpsilonTransitions.size(); 
            
            AutomatetoTab regexTable = new AutomatetoTab();
            IAutomate AFD = regexTable.determinizeAutomate(automateWithEpsilonTransitions);
            yPoints_deterministic[x] =AFD.size();

            AFD.optimize();
            yPoints_deterministic_optimized[x] = AFD.size();
            regex=regex+"*"+regex;
            writer1.write(yPoints_epsilon[x] + "\n");
            writer2.write(yPoints_deterministic[x] + "\n");
            writer3.write(yPoints_deterministic_optimized[x] + "\n");

        } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writer1.close();
        writer2.close();
        writer3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dupliquer(String fileName){ 
        try {
             FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
 
             StringBuilder contenuExistant = new StringBuilder();
             String ligne;
             while ((ligne = bufferedReader.readLine()) != null) {
                 contenuExistant.append(ligne).append("\n");
             }
 
             FileWriter fileWriter = new FileWriter(fileName);
 
             fileWriter.write(contenuExistant.toString());
             fileWriter.write("\n");
             fileWriter.write(contenuExistant.toString());
             bufferedReader.close();
             fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}