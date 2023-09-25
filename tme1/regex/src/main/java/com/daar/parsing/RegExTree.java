package com.daar.parsing;

import java.util.ArrayList;

import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;

public class RegExTree {
    private int root;
    private ArrayList<RegExTree> subTrees;
    private AutomateBuilder automateBuilder;

    public RegExTree(int root, ArrayList<RegExTree> subTrees) {
        this.root = root;
        this.subTrees = subTrees;
        this.automateBuilder = new AutomateBuilder();
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

    public IAutomate toAutomate() {
        return this.toAutomateAux(this.automateBuilder, this);
    }

    private IAutomate toAutomateAux(AutomateBuilder automateBuilder2, RegExTree regExTree) {
        if (regExTree.rootToString() == "|") {
            IAutomate R1 = toAutomateAux(automateBuilder, regExTree.getSubTrees().get(0));
            IAutomate R2 = toAutomateAux(automateBuilder, regExTree.getSubTrees().get(1));
            return automateBuilder.buildFromUnion(R1, R2);
        }
        if (regExTree.rootToString() == "*") {
            IAutomate R1 = toAutomateAux(automateBuilder, regExTree.getSubTrees().get(0));
            return automateBuilder.buildFromClosure(R1);
        }
        if (regExTree.rootToString() == ".") { // caractère qlq
            IAutomate R1 = toAutomateAux(automateBuilder, regExTree.getSubTrees().get(0));
            IAutomate R2 = toAutomateAux(automateBuilder, regExTree.getSubTrees().get(1));
            return automateBuilder.buildFromconcatenation(R1, R2);
        } else {
            return automateBuilder.buildFrom(regExTree.rootToString().charAt(0));
        }
    }
}
