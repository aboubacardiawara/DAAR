package com.daar.reconnaissance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
                    //writer.write(ligne + "\n");
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
    public static  void experimental() {
        testTailleAutomate();

        testDureeRechercheAutomate();
    }

    private static void testDureeRechercheAutomate() {
        try {
        String regEx = "S(a|g|r)+on";
        BufferedWriter writer3 = new BufferedWriter(new FileWriter("Time_Egrep.txt"));
        BufferedWriter writer4 = new BufferedWriter(new FileWriter("Time_AFD.txt"));
        String fileName="note.txt";
        Path path = Paths.get(fileName);
         
        for (int x = 0; x <7; x++) { 
            //on fait varier la taille du fichier note on duplique a chaque fois le conenued de note 
            try {
                long file_size= Files.size(path)/(1024*1024);
                System.out.println(file_size +"\n");
                //time for Egrep
                ProcessBuilder processBuilder = new ProcessBuilder("egrep",regEx,fileName);
                long startTime = System.currentTimeMillis();
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    //System.out.println(line);  //est ce que on garde
                }
                int exitCode = process.waitFor();
                long endTime = System.currentTimeMillis(); 
                reader.close();
                long executionTime = endTime - startTime;
                writer3.write(file_size+" "+executionTime+ "\n");

                //time for AFD:
                long t0 = System.currentTimeMillis();
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                IAutomate automate = new AutomateBuilder().buildFromRegex(regEx);
                String ligne;
                while ((ligne = bufferedReader.readLine()) != null) {
                    if (match(ligne, automate))
                        System.out.print(ligne + "\n");
                        continue;
                }
                bufferedReader.close();
                fileReader.close();

                long t1 = System.currentTimeMillis();
                writer4.write(file_size+ " " +(t1-t0)+ "\n");
                //duplique le contenue de note.txt attention peut bloqueer si
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
        BufferedWriter writer1 = new BufferedWriter(new FileWriter("size_result_experimental_epsilon.txt"));
        BufferedWriter writer2= new BufferedWriter(new FileWriter("size_result_experimental_deterministic.txt"));
        String regex= "ab";
        RegexParser parser = new RegexParser();
        int numPoints = 10;
        int[] yPoints_epsilon= new int[numPoints];
        int[] yPoints_deterministic= new int[numPoints];

        //TEST: pour la taille des automates
        for (int x = 0; x <10; x++) {
            try {
            RegExTree regexTree = parser.parse(regex);
            IAutomate automateWithEpsilonTransitions = regexTree.toAutomate();
            yPoints_epsilon[x] =automateWithEpsilonTransitions.size(); 
            
            AutomatetoTab regexTable = new AutomatetoTab();
            IAutomate deterministicAutomate = regexTable.determinizeAutomate(automateWithEpsilonTransitions);
            yPoints_deterministic[x] =deterministicAutomate.size();

            regex=regex+regex;
            writer1.write(yPoints_epsilon[x] + "\n");
            writer2.write(yPoints_deterministic[x] + "\n");

        } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writer1.close();
        writer2.close();
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