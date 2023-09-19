package com.daar;

import java.util.Scanner;

import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;
import com.daar.parsing.EClosure;
import com.daar.parsing.RegExTree;
import com.daar.parsing.RegexParser;

import java.util.ArrayList;

import java.lang.Exception;

public class App {
    private static final AutomateBuilder automateBuilder = new AutomateBuilder();
    // REGEX
    private static String regEx;

    // CONSTRUCTOR
    public App() {
    }

    // MAIN
    public static void main2(String arg[]) {
        System.out.println("Welcome to Bogota, Mr. Thomas Anderson.");
        if (arg.length != 0) {
            regEx = arg[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("  >> Please enter a regEx: ");
            regEx = scanner.next();
        }
        System.out.println("  >> Parsing regEx \"" + regEx + "\".");
        System.out.println("  >> ...");

        if (regEx.length() < 1) {
            System.err.println("  >> ERROR: empty regEx.");
        } else {
            System.out.print("  >> ASCII codes: [" + (int) regEx.charAt(0));
            for (int i = 1; i < regEx.length(); i++)
                System.out.print("," + (int) regEx.charAt(i));
            System.out.println("].");
            try {
                RegexParser parser = new RegexParser();
                RegExTree ret = parser.parse(regEx);
                System.out.println("  >> Tree result: " + ret.toString() + ".");
            } catch (Exception e) {
                System.err.println("  >> ERROR: syntax error for regEx \"" + regEx + "\".");
            }
        }

        System.out.println("  >> ...");
        System.out.println("  >> Parsing completed.");
        System.out.println("Goodbye Mr. Anderson.");
    }

    public static void main(String[] args) {
        System.out.println(exempleComplexe().dotify());
        
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