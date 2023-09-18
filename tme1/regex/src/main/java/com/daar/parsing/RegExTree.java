package com.daar.parsing;

import java.util.ArrayList;

public class RegExTree {
    public int root;
    public ArrayList<RegExTree> subTrees;

    public RegExTree(int root, ArrayList<RegExTree> subTrees) {
        this.root = root;
        this.subTrees = subTrees;
    }

    // FROM TREE TO PARENTHESIS
    public String toString() {
        if (subTrees.isEmpty())
            return rootToString();
        String result = rootToString() + "(" + subTrees.get(0).toString();
        for (int i = 1; i < subTrees.size(); i++)
            result += "," + subTrees.get(i).toString();
        return result + ")";
    }

    public String rootToString() {
        if (root == Closure.CONCAT)
            return ".";
        if (root == Closure.ETOILE)
            return "*";
        if (root == Closure.ALTERN)
            return "|";
        if (root == Closure.DOT)
            return ".";
        return Character.toString((char) root);
    }
}
