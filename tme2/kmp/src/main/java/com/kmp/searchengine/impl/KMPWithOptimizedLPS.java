package com.kmp.searchengine.impl;

public class KMPWithOptimizedLPS extends KMPWithLPS {
    /**
     * Computes the Longest Prefix Suffix (LPS) array for the given pattern a more optimized way.
     *
     * @param pattern The pattern for which to compute the LPS array.
     * @return The LPS array for the pattern.
     */
    @Override
    protected int[] computeLPSArray(String pattern) {
        int[] lps = super.computeLPSArray(pattern);
        return LPSOptimizer.optimise(pattern, lps);
    }
}