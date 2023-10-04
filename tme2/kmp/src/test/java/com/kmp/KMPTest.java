package com.kmp;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.kmp.searchengine.impl.KMPNaive;
import com.kmp.searchengine.impl.KMPWithLPS;
import com.kmp.searchengine.impl.KMPWithOptimizedLPS;
import com.kmp.searchengine.impl.LPSOptimizer;
import com.kmp.searchengine.interfaces.ISearchEngine;

public class KMPTest {

    @ParameterizedTest
    @MethodSource("kmpImplementations")
    public void testSearchPatternInText(ISearchEngine kmpImplementation) {
        // Test avec un motif présent dans le texte
        assertEquals(0, kmpImplementation.search("ABABABACD", "ABA"));

        // Test avec un motif présent dans le texte, mais à la fin
        assertEquals(6, kmpImplementation.search("ABABABABAC", "ABAC"));

        // Test avec un motif unique présent dans le texte
        assertEquals(8, kmpImplementation.search("ABABABACD", "D"));

        // Test avec un motif non présent dans le texte
        assertEquals(-1, kmpImplementation.search("ABABABACD", "XYZ"));
    }

    @ParameterizedTest
    @MethodSource("kmpImplementations")
    public void testSearchPatternInEmptyText(ISearchEngine kmpImplementation) {
        // Test avec un texte vide
        assertEquals(-1, kmpImplementation.search("", "ABA"));

        // Test avec un motif vide (le motif vide est toujours présent dans un texte
        // vide)
        assertEquals(0, kmpImplementation.search("ABABABACD", ""));
    }

    
    @Test
    public void testLPSMami() {
        String pattern = "mami";
        int[] lps = {0, 0, 1, 0};

        assertArrayEquals(lps, new KMPWithLPS().computeLPSArray(pattern));
    }


    @Test
    public void testLPSMAMAMIA() {
        String pattern = "MAMAMIA";
        int[] lps = {0, 0, 1, 2, 3, 0, 0};
        assertArrayEquals(lps, new KMPWithLPS().computeLPSArray(pattern));
    }
    

    @Test
    public void testLPSWikipedia() {
        String pattern = "ABCDABD";
        int[] lps = new KMPWithLPS().computeLPSArray(pattern);
        int[] expectedResult = {0, 0, 0, 0, 1, 2, 0};

        assertArrayEquals(expectedResult, lps);
    }

    @Test
    public void testLPSOptimizationMami() {
        String pattern = "mami";
        int[] lps = {-1, 0, 0, 1, 0};
        int[] expectedResult = {-1,0,-1,1,0};

        assertArrayEquals(expectedResult, LPSOptimizer.optimise(pattern, lps));
    }

    @Test
    public void testLPSOptimizationMAMAMIA() {
        String pattern = "MAMAMIA";
        int[] lps = {-1, 0, 0, 1, 2, 3, 0, 0};
        int[] expectedResult = {-1,0,-1,0,-1,3,0,0};
        assertArrayEquals(expectedResult, LPSOptimizer.optimise(pattern, lps));
    }

    static Stream<ISearchEngine> kmpImplementations() {
        return Stream.of(new KMPNaive(), new KMPWithLPS(), new KMPWithOptimizedLPS());
    }
}