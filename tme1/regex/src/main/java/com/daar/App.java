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
    // REGEX
    private static String regEx;

    // CONSTRUCTOR
    public App() {
    }

    public static void main(String[] args) throws Exception {
        String regEx = "Hello";
        IAutomate automate = automateBuilder.buildFromRegex(regEx);
        Boolean is_match = Reconnaissance.match("Hello world", automate);
        System.out.println(is_match);
    }

    private static void exportToFile(IAutomate automate, String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            writer.write(automate.dotify());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IAutomate exempleComplexe() {
        return automateBuilder.buildFromClosure(automateBuilder.buildFromClosure(exempleClosure()));
    }

    public static IAutomate exempleConcatenation() {
        IAutomate R1 = automateBuilder.buildFrom('a');
        IAutomate R2 = automateBuilder.buildFrom('b');
        IAutomate automateFromConcatenation = automateBuilder.buildFromconcatenation(R1, R2);
        return automateFromConcatenation;
    }

    public static IAutomate exempleClosure() {
        IAutomate R1 = automateBuilder.buildFrom('a');
        IAutomate automateFromClosure = automateBuilder.buildFromClosure(R1);
        return automateFromClosure;
    }

    public static IAutomate exempleUnion() {
        IAutomate R1 = automateBuilder.buildFrom('a');
        IAutomate R2 = automateBuilder.buildFrom('b');
        IAutomate automateFromUnion = automateBuilder.buildFromUnion(R1, R2);
        return automateFromUnion;
    }
}