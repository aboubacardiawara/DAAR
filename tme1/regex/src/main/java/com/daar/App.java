package com.daar;

import com.daar.reconnaissance.Reconnaissance;

public class App {
    // private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        String regEx = "S(a|g|r)+on";
        //Reconnaissance.search("note.txt", regEx);
        int nbr_dupliquer=0;
        //String file_test="note.txt";
        //Reconnaissance.experimental(file_test,nbr_dupliquer);
        for (int i=1; i<50; i++){
            String file_test="/Users/linaazerouk/Documents/M2/Daar/projet1Daar/DAAR/tme1/regex/Results/test_files/testfile"+i+".txt";
            Reconnaissance.experimental(file_test, nbr_dupliquer);
        }
    }
}