package com.daar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.daar.automate.Automate;
import com.daar.automate.IAutomate;

/**
 * Unit test for simple App.
 */
public class AutomateTest {

    @Test
    public void buildingAnAutomateWithOneState() {
        IAutomate initialStateAutomate = new Automate(0);

        assertFalse(
                initialStateAutomate.isAnInitialState(), "A newly created automate is not a starting state");
        assertFalse(
                initialStateAutomate.isAcceptingState(), "A newly created automate is not aN accepting state");
    }

    @Test
    public void turningANewAutomateToInitialState() {
        IAutomate initialStateAutomate = new Automate(0);
        initialStateAutomate.makeAsInitialState();
        assertTrue(initialStateAutomate.isAnInitialState());
    }

    @Test
    public void turningANewAutomateToAcceptingState() {
        IAutomate acceptingStateAutomate = new Automate(0);
        acceptingStateAutomate.makeAsFinalState();
        assertTrue(acceptingStateAutomate.isAcceptingState());
    }

}
