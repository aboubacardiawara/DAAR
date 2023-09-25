package com.daar.automatetotab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.daar.automate.Automate;
import com.daar.automate.IAutomate;

public class AutomatetoTab {

    private int id = 0;

    public IAutomate minimizeAutomate(IAutomate automate) {
        return tableToAutomate(this.automateLocalToTab(automate));
    }

    private List<Row> automateLocalToTab(IAutomate automateLocal) {
        List<Row> table = new ArrayList<>();
        Set<IAutomate> initialStates = collectInitialState(automateLocal);
        Row first_row = new Row();
        first_row.setDepartureStates(initialStates);

        construct_rechable_states(initialStates, first_row);

        // Make containFinalState and Make containInitialState
        for (IAutomate element : initialStates) {
            if (element.isAcceptingState())
                first_row.containFinalState = true;
            if (element.isAnInitialState())
                first_row.containInitialState = true;
        }

        table.add(first_row);

        // creation des nouvelles row suivant la fist_row
        return generating_other_row(table, first_row);
    }

    private List<Row> generating_other_row(List<Row> table, Row row) {
        row.getTransitionsStates().forEach((car, new_state) -> {
            Row new_row = new Row();
            new_row.setDepartureStates(new_state);
            construct_rechable_states(new_state, new_row);

            for (IAutomate element : new_row.getDepartureStates()) {
                if (element.isAcceptingState())
                    new_row.containFinalState = true;
                if (element.isAnInitialState())
                    new_row.containInitialState = true;
            }
            table.add(new_row);

            // on refait la meme operation pour les ligne suivante
            new_row.getTransitionsStates().forEach((car_, state) -> {
                // Verifie si la nouvelle transition n'a pas été traité
                if (!getAlldepartures(table).contains(new_row.getTransitionsByPosition((int) car_)))
                    generating_other_row(table, new_row);
            });
        });

        return table;
    }

    private IAutomate tableToAutomate(List<Row> table) {
        Map<Set<Integer>, IAutomate> repository = collectAllAutomates(table);
        IAutomate automateReduite = setUpFeatures(repository, table);
        return automateReduite;
    }

    private IAutomate setUpFeatures(Map<Set<Integer>, IAutomate> repo, List<Row> table) {
        table.forEach(row -> {
            Set<IAutomate> automates0 = row.getDepartureStates();
            Set<Integer> key = automatesSetToIntegerSet(automates0);
            IAutomate newGenerationAutomate = repo.get(key);
            // config the nature of state
            if (row.containsInitialState())
                newGenerationAutomate.makeAsInitialState();
            if (row.containFinalState)
                newGenerationAutomate.makeAsFinalState();
            // config the transitions of the state
            row.getTransitionsStates().forEach((ascci, automatesSet) -> {
                char transitionKey = (char) (int) ascci;
                // char transitionKey = (char) (ascci.toString()).charAt(0);

                newGenerationAutomate.addTransition(transitionKey, repo.get(automatesSetToIntegerSet(automatesSet)));
            });
        });

        return repo.get(automatesSetToIntegerSet(table.get(0).getDepartureStates()));
    }

    private int currentId() {
        return id++;
    }

    /**
     * On veut creer tous les etats de la nouvelle automate reduite à partir du
     * tableau.
     * Ici on ne definira ni leur nature (initial, accepting), ni leurs transitions.
     */
    private Map<Set<Integer>, IAutomate> collectAllAutomates(List<Row> table) {
        Map<Set<Integer>, IAutomate> repo = new HashMap<>();
        table.forEach(row -> {
            // initial state
            Set<IAutomate> automates = row.getDepartureStates();
            Set<Integer> key = automatesSetToIntegerSet(automates);
            if (!repo.containsKey(key)) {
                repo.put(key, new Automate(currentId()));
            }
            // transitions states
            row.getTransitionsStates().forEach((ascci, automatesSet) -> {
                Set<Integer> key2 = automatesSetToIntegerSet(automatesSet);
                if (!repo.containsKey(key2))
                    repo.put(key, new Automate(currentId()));
            });
        });
        return repo;
    }

    /**
     * Construit un ensemble d'entiers (id) à partir d'un ensemble d'automates.
     */
    private Set<Integer> automatesSetToIntegerSet(Set<IAutomate> automates) {
        Set<Integer> list_integer = new HashSet<>();
        automates.forEach(automate -> {
            list_integer.add(automate.getId());
        });
        return list_integer;
    }

    private List<Set<IAutomate>> getAlldepartures(List<Row> table) {
        List<Set<IAutomate>> All_departures = new ArrayList<>();
        for (Row e : table) {
            All_departures.add(e.getDepartureStates());
        }
        return All_departures;
    }

    /**
     * Explore
     */
    private Set<IAutomate> reachableStates(Set<IAutomate> reachable_SET, IAutomate exitAutomate) {
        reachable_SET.add(exitAutomate);

        if (exitAutomate.isAcceptingState())
            return reachable_SET;

        exitAutomate.getEmptyTransitions().forEach(
                rechebale_automate -> {
                    reachable_SET.add(rechebale_automate);
                    reachableStates(reachable_SET, rechebale_automate);
                });
        return reachable_SET;
    }

    public Set<IAutomate> collectInitialState(IAutomate automate) {
        Set<IAutomate> res = new HashSet<>();
        res.add(automate);
        Set<IAutomate> visited = new HashSet<>();
        collectInitialStateAux(automate, res, visited);
        return res;
    }

    private void collectInitialStateAux(IAutomate automate, Set<IAutomate> res, Set<IAutomate> visited) {
        if (visited.contains(automate))
            return;
        visited.add(automate);
        automate.getEmptyTransitions().forEach(automate_ -> {
            res.add(automate_);
            collectInitialStateAux(automate_, res, visited);
        });
    }

    private void construct_rechable_states(Set<IAutomate> initialStates, Row row) {
        initialStates.forEach(localAutomate -> {
            localAutomate.getTransitions().forEach(
                    (transitionKey, exitAutomate) -> {
                        Set<IAutomate> reachable_SET = new HashSet<>();
                        Set<IAutomate> reachableStates = reachableStates(reachable_SET, exitAutomate);
                        if (reachableStates != null)
                            row.updateStateAt(reachableStates, (int) transitionKey);
                    });
        });
    }
}

class Row {
    protected boolean containFinalState = false;
    protected boolean containInitialState = false;

    /** L'ensemble dees etats d'arrivée à partir des etats de depart */
    protected HashMap<Integer, Set<IAutomate>> transitionsStates; // a list we have to defibe the size(256) or it will a
                                                                  // null pointer

    /**
     * Les etats de depart. Les etats d'arrivés sont calculé en fonction de ceux-ci
     */
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
        if (transitionsStates.containsKey(i))
            transitionsStates.put(i, states);
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
// CONSTRUCTION DE L'AUTOMATE REDUITE FROM THE TAB

/**
 * creer un dictionnaire ensemble_automates_ids -> objet_automate. C'est notre
 * repertoire d'automates à partir du tableau
 * ensuite creer reparcourir le tableau.
 * pour chaque row:
 * e0 :: Set<IAutomate> = ensembles_de_depart de row
 * automate = repository.get(
 * pour chaque transitions dans row.transitionsStates :: Hashmap[]:
 * objet = repository.get(transitions) // on recuperer l'objet deja construit
 * // on creer une transitions automate -> tous les objets
 * // on defini la nature de automate: final ou init (row nous le dira)
 * 
 * 
 * Exemple
 * repository {
 * {4,0,2} -> automate0,
 * {1,5,3,6,9} -> automate1,
 * {2, 6, 8, 9, 56} -> automate2,
 * {7,6,9} -> automate3
 * }
 * 
 * maintenant on reparcours le tab
 * - row 0:
 * {4,0,2} -> automate0;
 * - nature: automate0.makeInit();
 * - transitions:
 * - 1) a -> {1,5,3,6,9}; automate0.addtransiton(a, repo.get({1,5,3,6,9}))
 * ...
 * A la fin, on aura construit l'automate reduite. Enfin j'espère. C'est bon
 * convaincue ?
 * 
 * }
 * 
 */