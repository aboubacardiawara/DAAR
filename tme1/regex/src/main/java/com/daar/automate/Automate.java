package com.daar.automate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Automate implements IAutomate {
    protected int id;
    protected Map<Character, IAutomate> transitions;
    protected boolean isInitialState;
    protected boolean isAcceptingState;
    private ArrayList<IAutomate> emptyTransitions;
    private Set<Integer> visitedAutomateIds;

    public Automate(int id) {
        this.id = id;
        this.transitions = new HashMap<>();
        this.isInitialState = false;
        this.isAcceptingState = false;
        this.emptyTransitions = new ArrayList<IAutomate>();
        this.visitedAutomateIds = new HashSet<Integer>();
    }

    public int getId() {
        return id;
    }

    public Map<Character, IAutomate> getTransitions() {
        return transitions;
    }

    @Override
    public void addTransition(char c, IAutomate etatInitial) {
        this.transitions.put(c, etatInitial);
    }

    public boolean isAcceptingState() {
        return isAcceptingState;
    }

    public void addEmptyTransitionFromAcceptingTo(IAutomate automate) {
        IAutomate acceptingState = findAcceptingState();
        acceptingState.addEmptyTransitionTo(automate);
    }

    public IAutomate findAcceptingState() {
        if (isAcceptingState) {
            return this;
        } else {
            IAutomate result = null; // Initialisez result à null

            // Parcourez les transitions
            for (Map.Entry<Character, IAutomate> entry : transitions.entrySet()) {
                IAutomate automate = entry.getValue();
                if (automate.isAcceptingState()) {
                    result = automate;
                    break; // Sortez de la boucle si un état acceptant est trouvé
                }
            }

            // Si un état acceptant a été trouvé, retournez-le
            if (result != null) {
                return result;
            }

            // Parcourez les transitions vides
            for (IAutomate automate : emptyTransitions) {
                result = automate.findAcceptingState();
                if (result != null) {
                    break; // Sortez de la boucle si un état acceptant est trouvé
                }
            }

            // Si un état acceptant n'a pas été trouvé dans les transitions directes ni les
            // transitions vides,
            // recherchez récursivement dans les transitions.
            if (result == null) {
                for (Map.Entry<Character, IAutomate> entry : transitions.entrySet()) {
                    IAutomate automate = entry.getValue();
                    result = automate.findAcceptingState();
                    if (result != null) {
                        break; // Sortez de la boucle si un état acceptant est trouvé
                    }
                }
            }

            return result;
        }
    }

    @Override
    public IAutomate getTransition(char c) throws NoSuchTransition {
        IAutomate automate = this.transitions.get(c);
        if (automate == null)
            throw new NoSuchTransition();
        return automate;
    }

    @Override
    public boolean isAnInitialState() {
        return isInitialState;
    }

    @Override
    public void makeAsFinalState() {
        this.isAcceptingState = true;
    }

    @Override
    public void makeAsInitialState() {
        this.isInitialState = true;
    }

    @Override
    public ArrayList<IAutomate> getEmptyTransitions() {
        return this.emptyTransitions;
    }

    @Override
    public void addEmptyTransitionTo(IAutomate automate) {
        this.emptyTransitions.add(automate);
    }

    @Override
    public String dotify() {
        if (visitedAutomateIds.contains(id)) {
            return "";
        }
        visitedAutomateIds.add(id);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("digraph {\n");
        if (isInitialState)
            stringBuilder.append("\t" + id + " [color=\"red\"]\n");
        if (emptyTransitions.isEmpty() && transitions.isEmpty()) {
            stringBuilder.append("\t" + id + "\n");
        } else {
            this.emptyTransitions.forEach(
                    automate -> {
                        stringBuilder.append("\t" + id + " -> " + automate.getId());
                        stringBuilder.append(" [label=\"\"]\n");
                        stringBuilder.append(automate.dotifyAux());
                    });

            this.transitions.forEach((car, child) -> {

                stringBuilder.append("\t" + id + " -> " + child.getId());
                stringBuilder.append(" [label=\"" + car + "\"]\n");
                stringBuilder.append(child.dotifyAux());
            });
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public String dotifyAux() {
        StringBuilder stringBuilder = new StringBuilder();
        if (isAcceptingState)
            stringBuilder.append("\t" + id + " [color=\"green\"]\n");
        if (isInitialState)
            stringBuilder.append("\t" + id + " [color=\"red\"]\n");
        if (visitedAutomateIds.contains(id)) {
            return "";
        }
        visitedAutomateIds.add(id);
        if (this.getEmptyTransitions().isEmpty() && this.getTransitions().isEmpty()) {
            return stringBuilder.toString();
        }

        this.emptyTransitions.forEach(
                automate -> {
                    if (automate.isAcceptingState())
                        stringBuilder.append("\t" + automate.getId() + " [color=\"green\"]\n");
                    stringBuilder.append("\t" + this.id + " -> " + automate.getId());
                    stringBuilder.append(" [label=\"\"]\n");
                    stringBuilder.append(automate.dotifyAux());
                });
        this.transitions.forEach((car, automate) -> {
            if (automate.isAcceptingState())
                stringBuilder.append("\t" + automate.getId() + " [color=\"green\"]\n");
            stringBuilder.append("\t" + this.id + " -> " + automate.getId());
            stringBuilder.append(" [label=\"" + car + "\"]\n");
            stringBuilder.append(automate.dotifyAux());

        });
        return stringBuilder.toString();
    }

    @Override
    public void unMakeInitialState() {
        this.isInitialState = false;
    }

    @Override
    public IAutomate getAcceptingState() {
        return this.findAcceptingState();
    }

    @Override
    public void unMakeAsAcceptingState() {
        this.isAcceptingState = false;
    }

    @Override
    public String toString() {
        return "Automate [id=" + id + "]";
    }

    @Override
    public boolean match(String subtrs) {
        try {
            return matchAux(subtrs, this);
        } catch (NoSuchTransition e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean matchAux(String subtrs, IAutomate automate) throws NoSuchTransition {
        if (subtrs.isEmpty()) {
            return automate.isAcceptingState();
        }

        Character head = subtrs.charAt(0);
        if (automate.isAcceptingState()) {
            return true; // on a parcourus la chaine de caractère a un certaine niveau et on a parcourus
                         // touuuuuuuuut le graphe
        }
        if (!automate.getTransitions().containsKey(head)) {
            return false;
        }
        IAutomate nextautomate = automate.getTransition(head);
        return matchAux(subtrs.substring(1), nextautomate);

    }

    @Override
    public void exportToFile(String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            writer.write(dotify());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Optimizing the automate by merging the equivalents states.
     */
    @Override
    public void optimize() {
        Map<Set<Tuple>, IAutomate> equivalents = new HashMap<>();
        Set<Integer> visitedAutomateIds = new HashSet<>();
        this.optimizeAux(this, equivalents, new HashSet<>());
        this.reconstructAutomate(equivalents, this, visitedAutomateIds);
    }

    private void reconstructAutomate(Map<Set<Tuple>, IAutomate> mergeTable, IAutomate automate,
            Set<Integer> visitedAutomateIds) {
        if (visitedAutomateIds.contains(automate.getId())) {
            return;
        }
        visitedAutomateIds.add(automate.getId());
        automate.getTransitions().forEach((car, automateLocal) -> {
            Set<Tuple> sortiesAsTuple = automateLocal.transitionsAsTuple();
            if (customContainsChecker(mergeTable, sortiesAsTuple)) {
                // cela veut dire que l'automate à dejà subit une fusion
                // on peut donc le remplacer par l'automate equivalent
                // dans la table de fusion
                automate.addTransition(car, mergeTable.get(sortiesAsTuple));

            }
        });

        automate.getTransitions().forEach((car, automateLocal) -> {
            reconstructAutomate(mergeTable, automateLocal, visitedAutomateIds);
        });

    }

    private void optimizeAux(IAutomate automate, Map<Set<Tuple>, IAutomate> equivalents, Set<Integer> visited) {
        if (visited.contains(automate.getId())) {
            return;
        }
        visited.add(automate.getId());
        automate.getTransitions().forEach((car, automateLocal) -> {
            if (!automateLocal.isAnInitialState()) {
                Set<Tuple> transitionsAsTuple = automateLocal.transitionsAsTuple();
                if (customContainsChecker(equivalents, transitionsAsTuple)) {
                    IAutomate mergeResult = automateLocal.merge(equivalents.get(transitionsAsTuple));
                    equivalents.put(automateLocal.transitionsAsTuple(), mergeResult);
                } else {
                    equivalents.put(transitionsAsTuple, automateLocal);
                    optimizeAux(automateLocal, equivalents, visited);
                }
            }
        });
    }

    private boolean customContainsChecker(Map<Set<Tuple>, IAutomate> equivalents, Set<Tuple> transitionsAsTuple) {
        return equivalents.keySet().stream().anyMatch(tupleSet -> {
            return transitionsAsTuple.equals(tupleSet);
        });
    }

    @Override
    public Set<Tuple> transitionsAsTuple() {
        Set<Tuple> setTuples = new HashSet<>();
        this.getTransitions().forEach((car, automate) -> {
            Tuple tuple = new Tuple(car, automate.getId());
            setTuples.add(tuple);
        });
        return setTuples;
    }

    /**
     * Les deux automates doivent être equivalents.
     * Voir le papier pour la notion d'automate equivalent.
     */
    @Override
    public IAutomate merge(IAutomate automate) {
        IAutomate mergeResult = new Automate(automate.getId());
        this.getTransitions().forEach((car, transition) -> {
            mergeResult.addTransition(car, transition);
        });
        if (automate.isAcceptingState())
            mergeResult.makeAsFinalState();
        return mergeResult;
    }

}

class Tuple {
    private Character fst;
    private Integer snd;

    public Tuple(Character fst, Integer snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public Character getFst() {
        return fst;
    }

    public Integer getSnd() {
        return snd;
    }

    public Character fst() {
        return this.fst;
    }

    public Integer snd() {
        return this.snd;
    }

    public boolean compare(Object o) {
        if (!(o instanceof Tuple))
            return false;
        Tuple t = (Tuple) o;
        return this.fst == t.getFst() && this.snd == t.getSnd();
    }

    @Override
    public boolean equals(Object o) {
        return compare(o);
    }

    public String toString() {
        return "(" + this.fst + ", " + this.snd + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fst == null) ? 0 : fst.hashCode());
        result = prime * result + ((snd == null) ? 0 : snd.hashCode());
        return result;
    }

}