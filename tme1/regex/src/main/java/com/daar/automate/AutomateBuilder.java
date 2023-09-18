package com.daar.automate;

import com.daar.parsing.RegExTree;

public class AutomateBuilder {

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
        IAutomate etatInitial = new Automate(0);
        etatInitial.makeAsInitialState();
        IAutomate etatFinal = new Automate(1);
        etatFinal.makeAsFinalState();
        etatInitial.addTransition(c, etatFinal);

        return etatInitial;
    }

}