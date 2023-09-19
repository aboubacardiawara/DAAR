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
        IAutomate finalState = new Automate(currentId());
        initialState.addEmptyTransitionTo(r1);
        initialState.addEmptyTransitionTo(r2);

        // rajoute un etat final a partir de l'etat final de R1 ou r2 (cheche l'etat
        // final)
        r1.addEmptyTransitionFromAcceptingTo(finalState);
        r2.addEmptyTransitionFromAcceptingTo(finalState);

        // changement de la nature des etats
        initialState.makeAsInitialState();
        r1.unMakeInitialState();
        r1.getAcceptingState().unMakeAsAcceptingState();
        r2.unMakeInitialState();
        r2.getAcceptingState().unMakeAsAcceptingState();
        finalState.makeAsFinalState();

        return initialState;
    }

    public IAutomate buildFromconcatenation(IAutomate r1, IAutomate r2)
    {    
          r1.addEmptyTransitionFromAcceptingTo(r2);
          r1.getAcceptingState().unMakeAsAcceptingState();
          r2.unMakeInitialState();
          return r1; 
    }

    /**
     * 
    1) s0: new state
    2) sf final state
    3) s0.addEmpty(r1)
    4) r1.final.addempty(sf)
    5) R1.final.addEmpty(r1)

    definition des natures des etats.
     */

      public IAutomate buildFromClosure(IAutomate r1)
    {      
        IAutomate initialState = new Automate(currentId());
        IAutomate finalState = new Automate(currentId());
        IAutomate r1_accpeting =  r1.getAcceptingState();
        initialState.addEmptyTransitionTo(r1);
        r1_accpeting.addEmptyTransitionTo(finalState);
        //r1_accpeting.addEmptyTransitionTo(r1); 
        // natures des etats
        initialState.makeAsInitialState();
        initialState.addEmptyTransitionTo(finalState);
        r1.unMakeInitialState();
        r1_accpeting.unMakeAsAcceptingState();
        finalState.makeAsFinalState();
        
        return initialState ;
    }

}