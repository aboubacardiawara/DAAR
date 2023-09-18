package com.daar.automate;

import com.daar.parsing.RegExTree;

public class AutomateBuilder {

    /**
     * Build an NDFA from a RegExTree.
     * 
     * @return
     */
    public IAutomate buildFrom(RegExTree regExTree) {
        return buildSimple(regExTree);
    }

    private IAutomate buildSimple(RegExTree regExTree) {
        IAutomate etatInitial = new Automate(0);
        etatInitial.makeAsInitialState();
        IAutomate etatFinal = new Automate(1);
        etatFinal.makeAsFinalState();
        etatInitial.addTransition('a', etatFinal);

        return etatInitial;
    }

}