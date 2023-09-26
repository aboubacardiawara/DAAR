package com.daar.automate;

import com.daar.automatetotab.AutomatetoTab;
import com.daar.parsing.RegExTree;
import com.daar.parsing.RegexParser;

public class AutomateBuilder {

    protected int id;

    public AutomateBuilder() {
        this.id = 0;
    }

    public int currentId() {
        return this.id++;
    }

    public IAutomate buildFrom(char c) {
        return buildSimple(c);
    }

    private IAutomate buildSimple(char c) {
        IAutomate etatInitial = new Automate(currentId());
        IAutomate etatFinal = new Automate(currentId());
        etatInitial.makeAsInitialState();
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

    public IAutomate buildFromconcatenation(IAutomate r1, IAutomate r2) {
        r1.addEmptyTransitionFromAcceptingTo(r2);
        r1.getAcceptingState().unMakeAsAcceptingState();
        r2.unMakeInitialState();
        return r1;
    }

    public IAutomate buildFromClosure(IAutomate r1) {
        IAutomate initialState = new Automate(currentId());
        IAutomate finalState = new Automate(currentId());
        IAutomate r1_accpeting = r1.getAcceptingState();
        initialState.addEmptyTransitionTo(r1);
        r1_accpeting.addEmptyTransitionTo(finalState);
        r1_accpeting.addEmptyTransitionTo(r1);
        // natures des etats
        initialState.makeAsInitialState();
        initialState.addEmptyTransitionTo(finalState);
        r1.unMakeInitialState();
        r1_accpeting.unMakeAsAcceptingState();
        finalState.makeAsFinalState();
        return initialState;
    }

    public IAutomate buildFromRegex(String regex) {
        RegexParser parser = new RegexParser();
        RegExTree regexTree;
        try {
            regexTree = parser.parse(regex);
            IAutomate automateWithEpsilonTransitions = regexTree.toAutomate();
            automateWithEpsilonTransitions.exportToFile("epsilonAutomate.dot");
            AutomatetoTab regexTable = new AutomatetoTab();
            IAutomate deterministicAutomate = regexTable.minimizeAutomate(automateWithEpsilonTransitions);
            deterministicAutomate.optimize();
            deterministicAutomate.exportToFile("derministicAutomate.dot");
            return deterministicAutomate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public IAutomate buildFromPlus(IAutomate r1) {
        IAutomate initialState = new Automate(currentId());
        IAutomate finalState = new Automate(currentId());
        IAutomate r1Accepting = r1.getAcceptingState();
        initialState.addEmptyTransitionTo(r1);
        r1Accepting.addEmptyTransitionTo(finalState);
        r1Accepting.addEmptyTransitionTo(r1);
        // natures des etats
        initialState.makeAsInitialState();
        r1.unMakeInitialState();
        r1Accepting.unMakeAsAcceptingState();
        finalState.makeAsFinalState();
        return initialState;
    }

}