package com.daar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.daar.automate.Automate;
import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;

/**
 * Unit test for simple App.
 */
public class AppTest {

    protected AutomateBuilder automateBuilder;

    @Before
    public void before() {
        this.automateBuilder = new AutomateBuilder();
    }

    /**
     * Creating a simple automate with two states and one transition.
     */
    @Test
    public void automateUnCaractere() {
        IAutomate automate = exampleAutomateAcceptingA();
        assertTrue(automate.getId() == 0);
        assertTrue(automate.getTransition('a').getId() == 1);
        assertTrue(automate.isAnInitialState());
        assertTrue(automate.getTransition('a').isAcceptingState());
        assertFalse(automate.getTransition('a').isAnInitialState());
        assertFalse(automate.isAcceptingState());
    }

    private IAutomate exampleAutomateAcceptingA() {
        return this.automateBuilder.buildFrom(null);
    }
}
