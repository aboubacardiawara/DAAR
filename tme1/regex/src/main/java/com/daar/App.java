package com.daar;

import java.util.Scanner;
import java.util.logging.Logger;

import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;
import com.daar.automatetotab.AutomatetoTab;
import com.daar.parsing.EClosure;
import com.daar.parsing.RegExTree;
import com.daar.parsing.RegexParser;
import com.daar.reconnaissance.Reconnaissance;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.Exception;

public class App {
    private static final AutomateBuilder automateBuilder = new AutomateBuilder();
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        long t0 = System.currentTimeMillis();
        String regEx = "ab*";
        Reconnaissance.search("note.txt", regEx);
        long t1 = System.currentTimeMillis();
        LOGGER.info("Durée: " + (t1 - t0) + " (ms)");
        // S(a|g|r)*on
    }
}