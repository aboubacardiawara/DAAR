package com.daar.automate;

import java.util.ArrayList;

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

}
