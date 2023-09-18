package com.daar.automate;

import java.util.HashMap;
import java.util.Map;

public class Automate implements IAutomate {
    protected int id;
    protected Map<Character, IAutomate> transitions;

    public Automate(int id) {
        this.id = id;
        this.transitions = new HashMap<>();
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
        return true;
    }

    @Override
    public IAutomate getTransition(char c) {
        return this.transitions.get(c);
    }

}