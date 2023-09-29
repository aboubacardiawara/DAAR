package com.daar;

import java.util.logging.Logger;

import javax.swing.JFrame;

import com.daar.reconnaissance.Reconnaissance;

import java.lang.Exception;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        String regEx = "S(a|g|r)+on";
        Reconnaissance.search("note.txt", regEx);
         JFrame frame = new JFrame("Courbe Sinusoïdale");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new Reconnaissance());
        frame.setVisible(true);
    }
}