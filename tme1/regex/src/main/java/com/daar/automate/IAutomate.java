package com.daar.automate;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Interface for a finite automaton.
 */
public interface IAutomate {

    /**
     * Adds a transition from the current state on the given character to the given
     * state.
     *
     * @param c           The character to transition on.
     * @param etatInitial The state to transition to.
     */
    public void addTransition(char c, IAutomate etatInitial);

    /**
     * Returns the ID of the current state.
     *
     * @return The ID of the current state.
     */
    public int getId();

    /**
     * Returns the state reached by following the transition on the given character
     * from the current state.
     *
     * @param c The character to transition on.
     * @return The state reached by following the transition on the given character
     *         from the current state.
     * @throws NoSuchTransition If there is no such transition.
     */
    public IAutomate getTransition(char c) throws NoSuchTransition;

    /**
     * Returns true if the current state is an accepting state.
     *
     * @return True if the current state is an accepting state.
     */
    public boolean isAcceptingState();

    /**
     * Returns true if the current state is an initial state.
     *
     * @return True if the current state is an initial state.
     */
    public boolean isAnInitialState();

    /**
     * Marks the current state as an accepting state.
     */
    public void makeAsFinalState();

    /**
     * Marks the current state as an initial state.
     */
    public void makeAsInitialState();

    /**
     * Returns a list of states reachable from the current state by following empty
     * transitions.
     *
     * @return A list of states reachable from the current state by following empty
     *         transitions.
     */
    public ArrayList<IAutomate> getEmptyTransitions();

    /**
     * Adds an empty transition from the current state to the given state.
     *
     * @param r1 The state to transition to.
     */
    public void addEmptyTransitionTo(IAutomate r1);

    /**
     * Returns a string representation of the automaton in the DOT language.
     *
     * @return A string representation of the automaton in the DOT language.
     */
    public String dotify();

    /**
     * Returns a map of transitions from the current state to other states.
     *
     * @return A map of transitions from the current state to other states.
     */
    public Map<Character, IAutomate> getTransitions();

    /**
     * Returns a string representation of the automaton in the DOT language,
     * including all states and transitions.
     *
     * @return A string representation of the automaton in the DOT language,
     *         including all states and transitions.
     */
    public String dotifyAux();

    /**
     * Adds an empty transition from all accepting states to the given state.
     *
     * @param finalState The state to transition to.
     */
    public void addEmptyTransitionFromAcceptingTo(IAutomate finalState);

    /**
     * Removes the initial state flag from the current state.
     */
    public void unMakeInitialState();

    /**
     * Returns the accepting state reachable from the current state.
     *
     * @return The accepting state reachable from the current state.
     */
    public IAutomate getAcceptingState();

    /**
     * Removes the accepting state flag from the current state.
     */
    public void unMakeAsAcceptingState();

    /**
     * Returns the accepting state reachable from the current state, or null if
     * there is no such state.
     *
     * @return The accepting state reachable from the current state, or null if
     *         there is no such state.
     */
    public IAutomate findAcceptingState();

    /**
     * Returns true if the automaton accepts the given string.
     *
     * @param subtrs The string to test.
     * @return True if the automaton accepts the given string.
     */
    public boolean match(String subtrs);

    /**
     * Exports the automaton to a file in the DOT language.
     *
     * @param string The name of the file to export to.
     */
    public void exportToFile(String string);

    /**
     * Minimizes the automaton by merging equivalent states.
     */
    public void optimize();

    /**
     * Returns a set of tuples representing the transitions from the current state.
     *
     * @return A set of tuples representing the transitions from the current state.
     */
    public Set<Tuple> transitionsAsTuple();

    /**
     * Merges the current automaton with the given automaton.
     *
     * @param iAutomate The automaton to merge with.
     * @return The merged automaton.
     */
    public IAutomate merge(IAutomate iAutomate);

    public int  size();

}