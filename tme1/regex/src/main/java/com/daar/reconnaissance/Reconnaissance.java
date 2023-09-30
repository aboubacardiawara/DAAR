package com.daar.reconnaissance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;
import com.daar.automate.NoSuchTransition;
import com.daar.parsing.RegExTree;
import com.daar.parsing.RegexParser;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import com.daar.automatetotab.AutomatetoTab;

import javax.sound.sampled.Line;
import javax.swing.JComponent;
import javax.swing.JPanel;
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
    


   
    public static  void experimental() {
       /*super.paintComponent(g);

      
        g.setColor(Color.BLUE);
        int width = getWidth();
        int height = getHeight();
        int echelleX = 50; // 10 pixels par unité sur l'axe des x
        int echelleY = 50; 
       

        // Dessiner les graduations sur l'axe des x
        g.setColor(Color.BLACK);
        for (int x = 0; x <= width; x += 50) {
            g.drawLine(x, height - 5, x, height + 5);
            g.drawString(Integer.toString(x / echelleX), x - 10, height + 20);
        }

        // Dessiner les graduations sur l'axe des y
        for (int y = 0; y <= height; y += 50) {
            g.drawLine(-5, y, 5, y);
            g.drawString(Integer.toString((height - y) / echelleY), -30, y + 5);
        }*/
        try {
        BufferedWriter writer1 = new BufferedWriter(new FileWriter("result_experimental_epsilon.txt"));
        BufferedWriter writer2= new BufferedWriter(new FileWriter("result_experimental_deterministic.txt"));
        String regex= "ab";
        RegexParser parser = new RegexParser();
        int numPoints = 10;

        //int[] xPoints_epsilon = new int[numPoints];
        int[] yPoints_epsilon= new int[numPoints];

       // int[] xPoints_deterministic= new int[numPoints];
        int[] yPoints_deterministic= new int[numPoints];

        for (int x = 0; x <10; x++) {
           // xPoints_epsilon[x]=x*50;  //pour la visualisation
            //xPoints_deterministic[x]=x*50; 
            try {
            RegExTree regexTree = parser.parse(regex);
            IAutomate automateWithEpsilonTransitions = regexTree.toAutomate();
            yPoints_epsilon[x] =automateWithEpsilonTransitions.size(); 
            
            AutomatetoTab regexTable = new AutomatetoTab();
            IAutomate deterministicAutomate = regexTable.minimizeAutomate(automateWithEpsilonTransitions);
            yPoints_deterministic[x] =deterministicAutomate.size();

            regex=regex+regex;
           // System.out.println("Epsilon=> x:"+xPoints_epsilon[x]+" y:"+yPoints_epsilon[x]);
            //System.out.println("Deterministic=> x:"+xPoints_deterministic[x]+" y:"+yPoints_deterministic[x]);
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
        /*g.drawPolyline(xPoints_epsilon, yPoints_epsilon, numPoints);
        g.setColor(Color.RED);
        g.drawPolyline(xPoints_deterministic, yPoints_deterministic, numPoints);
        */
    }
    
}