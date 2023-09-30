package com.daar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.swing.JFrame;
import com.daar.reconnaissance.Reconnaissance;


public class App {
   // private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        String regEx = "S(a|g|r)+on";
       Reconnaissance.search("note.txt", regEx);
        //Reconnaissance.experimental();
       
    }
}