package com.daar.reconnaissance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;
import com.daar.automate.NoSuchTransition;

public class Reconnaissance {

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
            BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));

            long t1 = System.currentTimeMillis();
            Logger LOGGER = Logger.getLogger(Reconnaissance.class.getName());
            LOGGER.info("[creation and optimization] Durée: " + (t1 - t0) + " (ms)");
            t0 = System.currentTimeMillis();
            String ligne;
            while ((ligne = bufferedReader.readLine()) != null) {
                if (match(ligne, automate))
                    writer.write(ligne + "\n");
            }
            writer.close();
            bufferedReader.close();
            fileReader.close();

            t1 = System.currentTimeMillis();
            LOGGER.info("[Search] Durée: " + (t1 - t0) + " (ms)");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}