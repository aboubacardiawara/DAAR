package com.daar.automate;

import java.util.ArrayList;
import java.util.Map;

public interface IAutomate {

    public void addTransition(char c, IAutomate etatInitial);

    public int getId();

    public IAutomate getTransition(char c) throws NoSuchTransition;

    public boolean isAcceptingState();

    public boolean isAnInitialState();

    public void makeAsFinalState();

    public void makeAsInitialState();

    public ArrayList<IAutomate> getEmptyTransitions();

    public void addEmptyTransitionTo(IAutomate r1);

    public String dotify();

    public Map<Character, IAutomate> getTransitions();

    public Object dotifyAux();

}
