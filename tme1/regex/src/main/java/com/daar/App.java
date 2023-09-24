package com.daar;

import java.util.Scanner;

import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;
import com.daar.automatetotab.AutomatetoTab;
import com.daar.parsing.EClosure;
import com.daar.parsing.RegExTree;
import com.daar.parsing.RegexParser;

import java.util.ArrayList;
import java.util.List;
import java.lang.Exception;

public class App {
    private static final AutomateBuilder automateBuilder = new AutomateBuilder();
    // REGEX
    private static String regEx;

    // CONSTRUCTOR
    public App() {
    }

    public static IAutomate tree_to_automat(AutomateBuilder automateBuilder, RegExTree tress) {

        if (tress.rootToString() == "|") {
            IAutomate R1 = tree_to_automat(automateBuilder, tress.getSubTrees().get(0));
            IAutomate R2 = tree_to_automat(automateBuilder, tress.getSubTrees().get(1));
            return automateBuilder.buildFromUnion(R1, R2);
        }
        if (tress.rootToString() == "*") {
            IAutomate R1 = tree_to_automat(automateBuilder, tress.getSubTrees().get(0));
            return automateBuilder.buildFromClosure(R1);
        }
        if (tress.rootToString() == ".") { // caractère qlq
            IAutomate R1 = tree_to_automat(automateBuilder, tress.getSubTrees().get(0));
            IAutomate R2 = tree_to_automat(automateBuilder, tress.getSubTrees().get(1));
            return automateBuilder.buildFromconcatenation(R1, R2);
        } else {
            return automateBuilder.buildFrom(tress.rootToString().charAt(0));
        }
    }

    public static void main(String[] args) throws Exception {
        String regEx = "c|(ab)*";
        RegexParser parser = new RegexParser();
        RegExTree tree_reg = parser.parse(regEx);
        IAutomate automat_rsult = tree_to_automat(automateBuilder, tree_reg);
        System.out.println(automat_rsult.dotify());
        AutomatetoTab regEx_table = new AutomatetoTab();
        IAutomate automate_Finale = regEx_table.minimizeAutomate(automat_rsult);
        System.out.println(automate_Finale.dotify()); // on va bien rigoler au moment du debug. :))))) OMG
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