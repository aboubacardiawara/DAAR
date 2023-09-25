package com.daar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.daar.automate.Automate;
import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;

/**
 * Unit test for simple App.
 */
public class AutomateTest {

    private AutomateBuilder automateBuilder;

    @BeforeEach
    public void beforeEachtestCase() {
        this.automateBuilder = new AutomateBuilder();
    }

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

    @Test
    public void buildingAutomateFromRegex() {
        IAutomate deterministicAutomate = new AutomateBuilder().buildFromRegex("a");
        assertTrue(deterministicAutomate.isAnInitialState());
    }

    @Test
    public void automateMatchElementOfItsLangage() {
        IAutomate automate = this.automateBuilder.buildFromRegex("a*|b*");
        assertTrue(automate.match("aaaa"));
        assertTrue(automate.match("bbbb"));
    }

    @Test
    public void automateDoesNotMatchElementOutsideItsLangage() {
        IAutomate automate = this.automateBuilder.buildFromRegex("a*|b*");
        assertFalse(automate.match("aaaab"));
        assertFalse(automate.match("bbbba"));
    }

    @Test
    public void automateMatchMoreComplexeCase() {
        IAutomate automate = this.automateBuilder.buildFromRegex("((abou)*|(lyna)*)( likes coding)*");
        assertTrue(automate.match("abou"));
        assertTrue(automate.match("lyna"));
        assertTrue(automate.match("abou likes coding"));
        assertFalse(automate.match("abou likes "));
    }

}
