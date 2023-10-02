package com.kmp.searchengine.impl;

import com.kmp.searchengine.interfaces.ISearchEngine;

/**
 * Implementation of the Knuth-Morris-Pratt (KMP) pattern searching algorithm
 * with
 * Longest Prefix Suffix (LPS) optimization.
 */
public class KMPWithLPS implements ISearchEngine {

    private int[] lps;

    /**
     * Searches for the given pattern in the input text.
     *
     * @param text    The text in which to search for the pattern.
     * @param pattern The pattern to search for in the text.
     * @return The index of the first occurrence of the pattern in the text, or -1
     *         if not found.
     */
    @Override
    public int search(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();
        if (patternLength == 0) {
            // Le motif viide est 
            // toujours present en debut de texte
            return 0;
        }
        if (lps == null) {
            lps = computeLPSArray(pattern);
        }
        
        int textIndex = 0;
        int patternIndex = 0;

        while (textIndex < textLength) {
            char textChar = text.charAt(textIndex);
            char patternChar = pattern.charAt(patternIndex);

            if (textChar == patternChar) {
                patternIndex++;
                textIndex++;
            }
            
            if (patternIndex == patternLength) {
                // Pattern found, retourn the position of the beginning.
                return textIndex - patternIndex;
            } else if (textIndex < textLength && textChar != patternChar) {
                patternIndex = updatePatternIndex(patternIndex, lps);
                if (patternIndex == 0) {
                    textIndex++;
                }
            }
        }

        // Pattern not found in the text
        return -1;
    }

    /**
     * Computes the Longest Prefix Suffix (LPS) array for the given pattern.
     *
     * @param pattern The pattern for which to compute the LPS array.
     * @return The LPS array for the pattern.
     */
    protected int[] computeLPSArray(String pattern) {
        int patternLength = pattern.length();
        int[] lps = new int[patternLength];
        int length = 0;
        int i = 1;

        while (i < patternLength) {
            char patternChar = pattern.charAt(i);
            char currentChar = pattern.charAt(length);

            if (patternChar == currentChar) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    /**
     * Updates the pattern index using the LPS array.
     *
     * @param patternIndex The current pattern index.
     * @param lps          The LPS array.
     * @return The updated pattern index.
     */
    private int updatePatternIndex(int patternIndex, int[] lps) {
        if (patternIndex != 0) {
            return lps[patternIndex - 1];
        } else {
            return 0;
        }
    }

}
