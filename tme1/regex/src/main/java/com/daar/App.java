package com.daar;

import java.util.Scanner;

import com.daar.parsing.Closure;
import com.daar.parsing.RegExTree;

import java.util.ArrayList;

import java.lang.Exception;

public class App {

    // REGEX
    private static String regEx;

    // CONSTRUCTOR
    public App() {
    }

    // MAIN
    public static void main(String arg[]) {
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
                RegExTree ret = parse();
                System.out.println("  >> Tree result: " + ret.toString() + ".");
            } catch (Exception e) {
                System.err.println("  >> ERROR: syntax error for regEx \"" + regEx + "\".");
            }
        }

        System.out.println("  >> ...");
        System.out.println("  >> Parsing completed.");
        System.out.println("Goodbye Mr. Anderson.");
    }

    // FROM REGEX TO SYNTAX TREE
    private static RegExTree parse() throws Exception {
        // BEGIN DEBUG: set conditionnal to true for debug example
        if (false)
            throw new Exception();
        RegExTree example = exampleAhoUllman();
        if (false)
            return example;
        // END DEBUG

        ArrayList<RegExTree> result = new ArrayList<RegExTree>();
        for (int i = 0; i < regEx.length(); i++)
            result.add(new RegExTree(charToRoot(regEx.charAt(i)), new ArrayList<RegExTree>()));

        return parse(result);
    }

    private static int charToRoot(char c) {
        if (c == '.')
            return Closure.DOT;
        if (c == '*')
            return Closure.ETOILE;
        if (c == '|')
            return Closure.ALTERN;
        if (c == '(')
            return Closure.PARENTHESEOUVRANT;
        if (c == ')')
            return Closure.PARENTHESEFERMANT;
        return (int) c;
    }

    private static RegExTree parse(ArrayList<RegExTree> result) throws Exception {
        while (containParenthese(result))
            result = processParenthese(result);
        while (containEtoile(result))
            result = processEtoile(result);
        while (containConcat(result))
            result = processConcat(result);
        while (containAltern(result))
            result = processAltern(result);

        if (result.size() > 1)
            throw new Exception();

        return removeProtection(result.get(0));
    }

    private static boolean containParenthese(ArrayList<RegExTree> trees) {
        for (RegExTree t : trees)
            if (t.root == Closure.PARENTHESEFERMANT || t.root == Closure.PARENTHESEOUVRANT)
                return true;
        return false;
    }

    private static ArrayList<RegExTree> processParenthese(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<RegExTree>();
        boolean found = false;
        for (RegExTree t : trees) {
            if (!found && t.root == Closure.PARENTHESEFERMANT) {
                boolean done = false;
                ArrayList<RegExTree> content = new ArrayList<RegExTree>();
                while (!done && !result.isEmpty())
                    if (result.get(result.size() - 1).root == Closure.PARENTHESEOUVRANT) {
                        done = true;
                        result.remove(result.size() - 1);
                    } else
                        content.add(0, result.remove(result.size() - 1));
                if (!done)
                    throw new Exception();
                found = true;
                ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
                subTrees.add(parse(content));
                result.add(new RegExTree(Closure.PROTECTION, subTrees));
            } else {
                result.add(t);
            }
        }
        if (!found)
            throw new Exception();
        return result;
    }

    private static boolean containEtoile(ArrayList<RegExTree> trees) {
        for (RegExTree t : trees)
            if (t.root == Closure.ETOILE && t.subTrees.isEmpty())
                return true;
        return false;
    }

    private static ArrayList<RegExTree> processEtoile(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<RegExTree>();
        boolean found = false;
        for (RegExTree t : trees) {
            if (!found && t.root == Closure.ETOILE && t.subTrees.isEmpty()) {
                if (result.isEmpty())
                    throw new Exception();
                found = true;
                RegExTree last = result.remove(result.size() - 1);
                ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
                subTrees.add(last);
                result.add(new RegExTree(Closure.ETOILE, subTrees));
            } else {
                result.add(t);
            }
        }
        return result;
    }

    private static boolean containConcat(ArrayList<RegExTree> trees) {
        boolean firstFound = false;
        for (RegExTree t : trees) {
            if (!firstFound && t.root != Closure.ALTERN) {
                firstFound = true;
                continue;
            }
            if (firstFound)
                if (t.root != Closure.ALTERN)
                    return true;
                else
                    firstFound = false;
        }
        return false;
    }

    private static ArrayList<RegExTree> processConcat(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<RegExTree>();
        boolean found = false;
        boolean firstFound = false;
        for (RegExTree t : trees) {
            if (!found && !firstFound && t.root != Closure.ALTERN) {
                firstFound = true;
                result.add(t);
                continue;
            }
            if (!found && firstFound && t.root == Closure.ALTERN) {
                firstFound = false;
                result.add(t);
                continue;
            }
            if (!found && firstFound && t.root != Closure.ALTERN) {
                found = true;
                RegExTree last = result.remove(result.size() - 1);
                ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
                subTrees.add(last);
                subTrees.add(t);
                result.add(new RegExTree(Closure.CONCAT, subTrees));
            } else {
                result.add(t);
            }
        }
        return result;
    }

    private static boolean containAltern(ArrayList<RegExTree> trees) {
        for (RegExTree t : trees)
            if (t.root == Closure.ALTERN && t.subTrees.isEmpty())
                return true;
        return false;
    }

    private static ArrayList<RegExTree> processAltern(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<RegExTree>();
        boolean found = false;
        RegExTree gauche = null;
        boolean done = false;
        for (RegExTree t : trees) {
            if (!found && t.root == Closure.ALTERN && t.subTrees.isEmpty()) {
                if (result.isEmpty())
                    throw new Exception();
                found = true;
                gauche = result.remove(result.size() - 1);
                continue;
            }
            if (found && !done) {
                if (gauche == null)
                    throw new Exception();
                done = true;
                ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
                subTrees.add(gauche);
                subTrees.add(t);
                result.add(new RegExTree(Closure.ALTERN, subTrees));
            } else {
                result.add(t);
            }
        }
        return result;
    }

    private static RegExTree removeProtection(RegExTree tree) throws Exception {
        if (tree.root == Closure.PROTECTION && tree.subTrees.size() != 1)
            throw new Exception();
        if (tree.subTrees.isEmpty())
            return tree;
        if (tree.root == Closure.PROTECTION)
            return removeProtection(tree.subTrees.get(0));

        ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
        for (RegExTree t : tree.subTrees)
            subTrees.add(removeProtection(t));
        return new RegExTree(tree.root, subTrees);
    }

    // EXAMPLE
    // --> RegEx from Aho-Ullman book Chap.10 Example 10.25
    private static RegExTree exampleAhoUllman() {
        RegExTree a = new RegExTree((int) 'a', new ArrayList<RegExTree>());
        RegExTree b = new RegExTree((int) 'b', new ArrayList<RegExTree>());
        RegExTree c = new RegExTree((int) 'c', new ArrayList<RegExTree>());
        ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
        subTrees.add(c);
        RegExTree cEtoile = new RegExTree(Closure.ETOILE, subTrees);
        subTrees = new ArrayList<RegExTree>();
        subTrees.add(b);
        subTrees.add(cEtoile);
        RegExTree dotBCEtoile = new RegExTree(Closure.CONCAT, subTrees);
        subTrees = new ArrayList<RegExTree>();
        subTrees.add(a);
        subTrees.add(dotBCEtoile);
        return new RegExTree(Closure.ALTERN, subTrees);
    }
}

// UTILITARY CLASS
