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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
public class Reconnaissance extends JPanel {

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
    


   
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.blue);

        int width = getWidth();
        int height = getHeight();

        // Tracer la courbe sinusoïdale
        Path2D path = new Path2D.Double();
        for (int x = 0; x < width; x++) {
            double y = Math.sin(Math.toRadians(x)) * height / 2 + height / 2;
            if (x == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        g2d.draw(path);
    }
    
}