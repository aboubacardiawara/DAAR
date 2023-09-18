package com.daar.parsing;

import java.util.ArrayList;

public class RegExTree {
    private int root;
    private ArrayList<RegExTree> subTrees;

    public RegExTree(int root, ArrayList<RegExTree> subTrees) {
        this.root = root;
        this.subTrees = subTrees;
    }

    public int getRoot() {
        return root;
    }

    public ArrayList<RegExTree> getSubTrees() {
        return subTrees;
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
        if (root == EClosure.CONCAT)
            return ".";
        if (root == EClosure.ETOILE)
            return "*";
        if (root == EClosure.ALTERN)
            return "|";
        if (root == EClosure.DOT)
            return ".";
        return Character.toString((char) root);
    }
}
