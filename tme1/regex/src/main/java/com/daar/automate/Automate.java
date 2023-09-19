package com.daar.automate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Automate implements IAutomate {
    protected int id;
    protected Map<Character, IAutomate> transitions;
    protected boolean isInitialState;
    protected boolean isAcceptingState;
    private ArrayList<IAutomate> emptyTransitions;

    public Automate(int id) {
        this.id = id;
        this.transitions = new HashMap<>();
        this.isInitialState = false;
        this.isAcceptingState = false;
        this.emptyTransitions = new ArrayList<IAutomate>();
    }

    public int getId() {
        return id;
    }

    public Map<Character, IAutomate> getTransitions() {
        return transitions;
    }

    @Override
    public void addTransition(char c, IAutomate etatInitial) {
        this.transitions.put(c, etatInitial);
    }

    public boolean isAcceptingState() {
        return isAcceptingState;
    }

    @Override
    public IAutomate getTransition(char c) throws NoSuchTransition {
        IAutomate automate = this.transitions.get(c);
        if (automate == null)
            throw new NoSuchTransition();
        return automate;
    }

    @Override
    public boolean isAnInitialState() {
        return isInitialState;
    }

    @Override
    public void makeAsFinalState() {
        this.isAcceptingState = true;
    }

    @Override
    public void makeAsInitialState() {
        this.isInitialState = true;
    }

    @Override
    public ArrayList<IAutomate> getEmptyTransitions() {
        return this.emptyTransitions;
    }

    @Override
    public void addEmptyTransitionTo(IAutomate automate) {
        this.emptyTransitions.add(automate);
    }

    @Override
    public String dotify() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("digraph {\n");
        if (emptyTransitions.isEmpty() && transitions.isEmpty()) {
            stringBuilder.append("\t" + id + "\n");
        } else {
            this.emptyTransitions.forEach(
                    automate -> {

                        stringBuilder.append("\t" + id + " -> " + automate.getId());
                        stringBuilder.append(" [label=\"\"]\n");
                        stringBuilder.append(automate.dotifyAux());
                    });

            this.transitions.forEach((car, child) -> {

                stringBuilder.append("\t" + id + " -> " + child.getId());
                stringBuilder.append(" [label=\"" + car + "\"]\n");
                stringBuilder.append(child.dotifyAux());
            });
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public String dotifyAux() {

        StringBuilder stringBuilder = new StringBuilder();
        if (this.getEmptyTransitions().isEmpty() && this.getTransitions().isEmpty()) {
            return "";
        }
        this.emptyTransitions.forEach(
                automate -> {
                    stringBuilder.append("\t" + this.id + " -> " + automate.getId());
                    stringBuilder.append(" [label=\"\"]\n");
                });

        this.transitions.forEach((car, automate) -> {
            stringBuilder.append("\t" + this.id + " -> " + automate.getId());
            stringBuilder.append(" [label=\"" + car + "\"]\n");
        });
        return stringBuilder.toString();
    }

}