package com.daar;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.daar.automate.Automate;
import com.daar.automate.IAutomate;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void automateUnCaractere() {
        IAutomate automate1 = example1();
        assertTrue(automate1.getId() == 0);
        assertTrue(automate1.getTransition('a').getId() == 1);
        assertTrue(automate1.getTransition('a').isAcceptingState());
    }

    private IAutomate example1() {
        IAutomate etatInitial = new Automate(0);
        IAutomate etatFinal = new Automate(1);
        System.out.println(etatInitial);
        System.out.println(etatFinal);
        etatInitial.addTransition('a', etatFinal);

        return etatInitial;
    }
}
