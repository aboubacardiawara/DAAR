package com.daar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;
import com.daar.automate.NoSuchTransition;

public class AutomateBuilderTest {

    protected AutomateBuilder automateBuilder;

    @BeforeEach
    public void before() {
        this.automateBuilder = new AutomateBuilder();
    }

    @Test
    public void buildingAnUnionOfTwoAutomate() {

    }

    /**
     * Creating a simple automate with two states and one transition.
     */
    @Test
    public void automateUnCaractereA() {
        char car = 'a';
        IAutomate automate = exampleAutomateAcceptingA();
        assertTrue(automate.getId() == 0);

        try {
            assertTrue(automate.getTransition(car).getId() == 1);
            assertFalse(automate.isAcceptingState());
            assertTrue(automate.isAnInitialState());
            assertTrue(automate.getTransition(car).isAcceptingState());
            assertFalse(automate.getTransition(car).isAnInitialState());
        } catch (NoSuchTransition e) {
            fail("Error should not occur");
        }
    }

    /**
     * Creating a simple automate with two states and one transition.
     */
    @Test
    public void automateUnCaractereB() {
        char car = 'b';
        IAutomate automate = exampleAutomateAcceptingB();
        assertTrue(automate.getId() == 0);
        try {
            assertTrue(automate.getTransition(car).getId() == 1);
            assertTrue(automate.isAnInitialState());
            assertTrue(automate.getTransition(car).isAcceptingState());
            assertFalse(automate.getTransition(car).isAnInitialState());
            assertFalse(automate.isAcceptingState());
        } catch (NoSuchTransition e) {
            fail("Error should not occur");
        }
    }

    private IAutomate exampleAutomateAcceptingA() {
        return this.automateBuilder.buildFrom('a');
    }

    private IAutomate exampleAutomateAcceptingB() {
        return this.automateBuilder.buildFrom('b');
    }

}