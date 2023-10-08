package com.daar;

import java.io.IOException;
import java.util.ArrayList;

import com.daar.reconnaissance.Reconnaissance;

public class App {
    // private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println("Lire le read me pour l'utilisation de l'application");
            System.exit(1);
        }
        // xp();
        app(args[0], args[1]);

    }

    private static void xp() throws IOException {
        String regEx = "S(a|g|r)*on";
        // Reconnaissance.search("note.txt", regEx);
        // Exp: time file size
        int nbr_dupliquer = 0;
        // String file_test="note.txt";
        // Reconnaissance.experimental(file_test,nbr_dupliquer);

        // Exp: time file temps d'execution
        for (int i = 1; i < 10; i++) {
            // String
            // file_test="/Users/linaazerouk/Documents/M2/Daar/projet1Daar/DAAR/tme1/regex/Results/test_files/testfile"+i+".txt";
            // Reconnaissance.experimental(file_test, nbr_dupliquer);
        }

        // Exp: time file et moyenne temps d'execution
        final String[] liste = { "S(a|g|r)*on", "s*c+d+e+s*", "((R+e)|(m+a))on", "Hello", "((sa)+(ph))+" };
        for (String element : liste) {
            System.out.println(element);
            Reconnaissance.experimental_avrg(element, nbr_dupliquer);

        }
    }

    public static void app(String file, String regex) {
        System.out.println("file: " + file);
        Reconnaissance.search(file, regex);
    }
}