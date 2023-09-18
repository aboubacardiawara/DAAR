package com.daar.automate;

import java.util.HashMap;
import java.util.Map;

public class Automate implements IAutomate {
    protected int id;
    protected Map<Character, IAutomate> transitions;
    protected boolean isInitialState;
    protected boolean isAcceptingState;

    public Automate(int id) {
        this.id = id;
        this.transitions = new HashMap<>();
        this.isInitialState = false;
        this.isAcceptingState = false;
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
    public IAutomate getTransition(char c) {
        return this.transitions.get(c);
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

}