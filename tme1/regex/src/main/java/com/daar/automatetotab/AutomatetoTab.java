package com.daar.automatetotab;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.daar.automate.IAutomate;


public class AutomatetoTab {

    public List<Row> automateLocalToTab(IAutomate automateLocal) {
        List<Row> table = new ArrayList<>();
        Set<IAutomate> initialStates = collectInitialState(automateLocal);
        Row first_row = new Row(); 
        first_row.setDepartureStates(initialStates);
        
        initialStates.forEach(localAutomate -> {
        localAutomate.getTransitions().forEach(
            (transitionKey, exitAutomate) -> {
                Set<IAutomate> reachable_SET = new HashSet<>(); 
                Set<IAutomate> reachableStates = reachableStates(reachable_SET,exitAutomate);     
                first_row.updateStateAt(reachableStates, (int)transitionKey);
            }); 
        });  
    
        //Make containFinalState and Make containInitialState
        for (IAutomate element : initialStates) {
            if (element.isAcceptingState())
                first_row.containFinalState=true; 
            if (element.isAnInitialState())
                first_row.containInitialState=true; 
        }
        //affichage de la liste TransitionsByPosition pour le caractère a
        for (IAutomate e : first_row.getTransitionsByPosition(97) )
            System.out.println(e.getId());

        //ajout des autres lignes 
        table.add(first_row);  
        return table;
    }

/**
 * Explore 
 */
private Set<IAutomate> reachableStates(Set<IAutomate> reachable_SET ,IAutomate exitAutomate) {
    
    reachable_SET.add(exitAutomate);
    if (exitAutomate.isAcceptingState()) return reachable_SET;
    exitAutomate.getEmptyTransitions().forEach(
            rechebale_automate-> {
            reachable_SET.add(rechebale_automate);
            reachableStates(reachable_SET, rechebale_automate);            
        });
    return reachable_SET;
}

    public Set<IAutomate> collectInitialState(IAutomate automateLocal){
        Set<IAutomate> res = new HashSet<>();
        res.add(automateLocal);
        res.addAll(automateLocal.getEmptyTransitions());
        return res;
    } 
}

class Row {
    protected boolean containFinalState = false;
    protected boolean containInitialState = false;
    
    /** L'ensemble dees etats d'arrivée à partir des etats de depart */
    protected Set<IAutomate>[] transitionsStates; 
    
    /** Les etats de depart. Les etats d'arrivés sont calculé en fonction de ceux-ci */
    protected Set<IAutomate> departureStates;
    
    public Row() {
        this.transitionsStates = new HashSet[256];;
    }

    public void setDepartureStates(Set<IAutomate> initialStates) {
        this.departureStates = initialStates;
    }
    
    public void updateStateAt(Set<IAutomate> states, int i) {
        if (transitionsStates[i] == null) transitionsStates[i] = new HashSet<>();
        transitionsStates[i].addAll(states);
    }

    public boolean containsFinalState() {
        return containFinalState;
    }
    public boolean containsInitialState() {
        return containInitialState;
    }
    public Set<IAutomate> getTransitionsByPosition(int ascciPosition) {
        return transitionsStates[ascciPosition];
    }
    
    public Set<IAutomate> getDepartureStates() {
        return departureStates;
    }
    
}