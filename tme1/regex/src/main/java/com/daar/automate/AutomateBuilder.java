package com.daar.automate;

import com.daar.parsing.RegExTree;

public class AutomateBuilder {

    protected int id;

    public AutomateBuilder() {
        this.id = 0;
    }

    public int currentId() {
        return this.id++;
    }

    /**
     * Build an NDFA from a RegExTree.
     * 
     * @return
     */
    public IAutomate buildFrom(RegExTree regExTree) {
        return null;
    }

    public IAutomate buildFrom(char c) {
        return buildSimple(c);
    }

    private IAutomate buildSimple(char c) {
        IAutomate etatInitial = new Automate(currentId());
        etatInitial.makeAsInitialState();
        IAutomate etatFinal = new Automate(currentId());
        etatFinal.makeAsFinalState();
        etatInitial.addTransition(c, etatFinal);

        return etatInitial;
    }

    public IAutomate buildFromUnion(IAutomate r1, IAutomate r2) {
        IAutomate initialState = new Automate(currentId());
        initialState.makeAsInitialState();
        r1.unMakeInitialState();
        r1.getAcceptingState().unMakeAsAcceptingState();
        IAutomate finalState = new Automate(currentId());
        initialState.addEmptyTransitionTo(r1);
        initialState.addEmptyTransitionTo(r2);

        // rajoute un etat final a partir de l'etat final de R1 ou r2 (cheche l'etat
        // final)
        r1.addEmptyTransitionFromAcceptingTo(finalState);
        r2.addEmptyTransitionFromAcceptingTo(finalState);

        return initialState;
    }

}