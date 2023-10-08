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

        IAutomate R1 = exampleAutomateAcceptingA();
        IAutomate R2 = exampleAutomateAcceptingB();

        IAutomate automateFromUnion = this.automateBuilder.buildFromUnion(R1, R2);

        assertTrue(
                automateFromUnion.getEmptyTransitions().contains(R1),
                "Empty transitions contains R1");
        assertTrue(
                automateFromUnion.getEmptyTransitions().contains(R2),
                "Empty transitions also contains R2");

    }

    @Test
    void testSurLesId() {
        // fail("Ajouter un test sur les ids, la nature des etats ....");
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
    /**
     * Vérifie si un AFD a une liste de transition epsilon empty
     */
    @Test
    public void Test_empty_epsilon() {
        IAutomate automate = this.automateBuilder.buildFromRegex("a*|b*");
        assertTrue(automate.getEmptyTransitions().isEmpty());
    }


    private IAutomate exampleAutomateAcceptingA() {
        return this.automateBuilder.buildFrom('a');
    }

    private IAutomate exampleAutomateAcceptingB() {
        return this.automateBuilder.buildFrom('b');
    }

}