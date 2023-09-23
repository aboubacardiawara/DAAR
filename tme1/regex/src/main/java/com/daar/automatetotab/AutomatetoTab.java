package com.daar.automatetotab;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.daar.automate.Automate;
import com.daar.automate.IAutomate;


public class AutomatetoTab {


    public List<Row> automateLocalToTab(IAutomate automateLocal) {
        List<Row> table = new ArrayList<>();
        Set<IAutomate> initialStates = collectInitialState(automateLocal);
        Row first_row = new Row(); 
        first_row.setDepartureStates(initialStates);

        construct_rechable_states(initialStates, first_row);
    
        //Make containFinalState and Make containInitialState
        for(IAutomate element : initialStates) {
            if (element.isAcceptingState())
                first_row.containFinalState=true; 
            if (element.isAnInitialState())
                first_row.containInitialState=true; 
        }

        //affichage de la liste TransitionsByPosition pour le caractère b pour la premère ligne 
        for(IAutomate e : first_row.getTransitionsByPosition(98) )
            System.out.print(e.getId()); 

        System.out.print("\n"); 
        table.add(first_row);  

        //creation des nouvelles row suivant la fist_row
        return generating_other_row(table, first_row);
    }


    private List<Row> generating_other_row( List<Row> table,Row row) {
        row.getTransitionsStates().forEach((car, new_state)->{
            Row  new_row =new Row ();
            new_row.setDepartureStates(new_state);
            construct_rechable_states( new_state,new_row);

            for(IAutomate element : new_row.getDepartureStates()){
                if (element.isAcceptingState()) new_row.containFinalState=true; 
                if (element.isAnInitialState()) new_row.containInitialState=true; 
            }
            table.add(new_row);

            //affichage de la liste TransitionsByPosition pour le caractère b
            for(IAutomate e : new_row.getTransitionsByPosition(97))
                    System.out.print(e.getId());  //resultat 7,9,6   
            System.out.print("\n"); 

            // on refait la meme operation pour les ligne suivante 
            new_row.getTransitionsStates().forEach((car_, state) -> {
                //Verifie si la nouvelle transition n'a pas été traité 
                if(!getAlldepartures(table).contains(new_row.getTransitionsByPosition((int) car_)))  generating_other_row(table, new_row);
            });
            });
            
        return table ;
    }


    public IAutomate table_to_automate(List<Row> table){
        IAutomate automae_reduit= new Automate(0);
        for (Row row: table ){
            
        }
        return null;
    }

    private List<Set<IAutomate>> getAlldepartures(List<Row> table) {
        List<Set<IAutomate>>  All_departures=new ArrayList<>() ;
            for(Row e : table){
                All_departures.add(e.getDepartureStates());
            }
            return All_departures;
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

    private void construct_rechable_states(Set<IAutomate> initialStates, Row row){
        initialStates.forEach(localAutomate ->{
        localAutomate.getTransitions().forEach(
            (transitionKey, exitAutomate) ->{
                Set<IAutomate> reachable_SET = new HashSet<>(); 
                Set<IAutomate> reachableStates = reachableStates(reachable_SET,exitAutomate);    
                if (reachableStates!=null) row.updateStateAt(reachableStates, (int)transitionKey);
            }); 
        });   
    }
}

class Row {
    protected boolean containFinalState = false;
    protected boolean containInitialState = false;
    
    /** L'ensemble dees etats d'arrivée à partir des etats de depart */
    protected HashMap<Integer, Set<IAutomate>> transitionsStates;  //a list we have to defibe the size(256) or it will a null pointer
    
    /** Les etats de depart. Les etats d'arrivés sont calculé en fonction de ceux-ci */
    protected Set<IAutomate> departureStates;
    

    public Row() {
        this.transitionsStates = new HashMap<>();
    }

     public HashMap<Integer, Set<IAutomate>> getTransitionsStates() {
        return transitionsStates;
    }

    public void setDepartureStates(Set<IAutomate> initialStates) {
        this.departureStates = initialStates;
    }
    
    public void updateStateAt(Set<IAutomate> states, int i) {
        if (transitionsStates.containsKey(i)) transitionsStates.put(i, states);
        transitionsStates.put(i, states);
    }

    public boolean containsFinalState() {
        return containFinalState;
    }
    public boolean containsInitialState() {
        return containInitialState;
    }
    public Set<IAutomate> getTransitionsByPosition(int ascciPosition) {
        return transitionsStates.get(ascciPosition);
    }
    
    public Set<IAutomate> getDepartureStates() {
        return departureStates;
    }


  
    
}