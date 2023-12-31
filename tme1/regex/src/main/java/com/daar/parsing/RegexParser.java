package com.daar.parsing;

import java.util.ArrayList;

import java.lang.Exception;

public class RegexParser {

    // REGEX
    private String regEx;

    // MAIN

    // FROM REGEX TO SYNTAX TREE
    public RegExTree parse(String regex) throws Exception {
        this.regEx = regex;
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

    private int charToRoot(char c) {
        if (c == '.')
            return EClosure.DOT;
        if (c == '*')
            return EClosure.ETOILE;
        if (c == '+')
            return EClosure.PLUS;
        if (c == '|')
            return EClosure.ALTERN;
        if (c == '(')
            return EClosure.PARENTHESEOUVRANT;
        if (c == ')')
            return EClosure.PARENTHESEFERMANT;
        return (int) c;
    }

    private RegExTree parse(ArrayList<RegExTree> result) throws Exception {
        while (containParenthese(result))
            result = processParenthese(result);
        while (containEtoile(result))
            result = processEtoile(result);
        while (containPlus(result))
            result = processPlus(result);
        while (containConcat(result))
            result = processConcat(result);
        while (containAltern(result))
            result = processAltern(result);

        if (result.size() > 1)
            throw new Exception();

        return removeProtection(result.get(0));
    }

    private boolean containParenthese(ArrayList<RegExTree> trees) {
        for (RegExTree t : trees)
            if (t.getRoot() == EClosure.PARENTHESEFERMANT || t.getRoot() == EClosure.PARENTHESEOUVRANT)
                return true;
        return false;
    }

    private ArrayList<RegExTree> processParenthese(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<RegExTree>();
        boolean found = false;
        for (RegExTree t : trees) {
            if (!found && t.getRoot() == EClosure.PARENTHESEFERMANT) {
                boolean done = false;
                ArrayList<RegExTree> content = new ArrayList<RegExTree>();
                while (!done && !result.isEmpty())
                    if (result.get(result.size() - 1).getRoot() == EClosure.PARENTHESEOUVRANT) {
                        done = true;
                        result.remove(result.size() - 1);
                    } else
                        content.add(0, result.remove(result.size() - 1));
                if (!done)
                    throw new Exception();
                found = true;
                ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
                subTrees.add(parse(content));
                result.add(new RegExTree(EClosure.PROTECTION, subTrees));
            } else {
                result.add(t);
            }
        }
        if (!found)
            throw new Exception();
        return result;
    }

    private boolean containEtoile(ArrayList<RegExTree> trees) {
        for (RegExTree t : trees)
            if (t.getRoot() == EClosure.ETOILE && t.getSubTrees().isEmpty())
                return true;
        return false;
    }

    private boolean containPlus(ArrayList<RegExTree> trees) {
        for (RegExTree t : trees)
            if (t.getRoot() == EClosure.PLUS && t.getSubTrees().isEmpty())
                return true;
        return false;
    }

    private ArrayList<RegExTree> processEtoile(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<RegExTree>();
        boolean found = false;
        for (RegExTree t : trees) {
            if (!found && t.getRoot() == EClosure.ETOILE && t.getSubTrees().isEmpty()) {
                if (result.isEmpty())
                    throw new Exception();
                found = true;
                RegExTree last = result.remove(result.size() - 1);
                ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
                subTrees.add(last);
                result.add(new RegExTree(EClosure.ETOILE, subTrees));
            } else {
                result.add(t);
            }
        }
        return result;
    }

    private ArrayList<RegExTree> processPlus(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<RegExTree>();
        boolean found = false;
        for (RegExTree t : trees) {
            if (!found && t.getRoot() == EClosure.PLUS && t.getSubTrees().isEmpty()) {
                if (result.isEmpty())
                    throw new Exception();
                found = true;
                RegExTree last = result.remove(result.size() - 1);
                ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
                subTrees.add(last);
                result.add(new RegExTree(EClosure.PLUS, subTrees));
            } else {
                result.add(t);
            }
        }
        return result;
    }

    private boolean containConcat(ArrayList<RegExTree> trees) {
        boolean firstFound = false;
        for (RegExTree t : trees) {
            if (!firstFound && t.getRoot() != EClosure.ALTERN) {
                firstFound = true;
                continue;
            }
            if (firstFound)
                if (t.getRoot() != EClosure.ALTERN)
                    return true;
                else
                    firstFound = false;
        }
        return false;
    }

    private ArrayList<RegExTree> processConcat(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<RegExTree>();
        boolean found = false;
        boolean firstFound = false;
        for (RegExTree t : trees) {
            if (!found && !firstFound && t.getRoot() != EClosure.ALTERN) {
                firstFound = true;
                result.add(t);
                continue;
            }
            if (!found && firstFound && t.getRoot() == EClosure.ALTERN) {
                firstFound = false;
                result.add(t);
                continue;
            }
            if (!found && firstFound && t.getRoot() != EClosure.ALTERN) {
                found = true;
                RegExTree last = result.remove(result.size() - 1);
                ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
                subTrees.add(last);
                subTrees.add(t);
                result.add(new RegExTree(EClosure.CONCAT, subTrees));
            } else {
                result.add(t);
            }
        }
        return result;
    }

    private boolean containAltern(ArrayList<RegExTree> trees) {
        for (RegExTree t : trees)
            if (t.getRoot() == EClosure.ALTERN && t.getSubTrees().isEmpty())
                return true;
        return false;
    }

    private ArrayList<RegExTree> processAltern(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<RegExTree>();
        boolean found = false;
        RegExTree gauche = null;
        boolean done = false;
        for (RegExTree t : trees) {
            if (!found && t.getRoot() == EClosure.ALTERN && t.getSubTrees().isEmpty()) {
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
                result.add(new RegExTree(EClosure.ALTERN, subTrees));
            } else {
                result.add(t);
            }
        }
        return result;
    }

    private RegExTree removeProtection(RegExTree tree) throws Exception {
        if (tree.getRoot() == EClosure.PROTECTION && tree.getSubTrees().size() != 1)
            throw new Exception();
        if (tree.getSubTrees().isEmpty())
            return tree;
        if (tree.getRoot() == EClosure.PROTECTION)
            return removeProtection(tree.getSubTrees().get(0));

        ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
        for (RegExTree t : tree.getSubTrees())
            subTrees.add(removeProtection(t));
        return new RegExTree(tree.getRoot(), subTrees);
    }

    // EXAMPLE
    // --> RegEx from Aho-Ullman book Chap.10 Example 10.25
    private RegExTree exampleAhoUllman() {
        RegExTree a = new RegExTree((int) 'a', new ArrayList<RegExTree>());
        RegExTree b = new RegExTree((int) 'b', new ArrayList<RegExTree>());
        RegExTree c = new RegExTree((int) 'c', new ArrayList<RegExTree>());
        ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
        subTrees.add(c);
        RegExTree cEtoile = new RegExTree(EClosure.ETOILE, subTrees);
        subTrees = new ArrayList<RegExTree>();
        subTrees.add(b);
        subTrees.add(cEtoile);
        RegExTree dotBCEtoile = new RegExTree(EClosure.CONCAT, subTrees);
        subTrees = new ArrayList<RegExTree>();
        subTrees.add(a);
        subTrees.add(dotBCEtoile);
        return new RegExTree(EClosure.ALTERN, subTrees);
    }
}

// UTILITARY CLASS
