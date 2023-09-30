package com.kmp.searchengine;

import com.kmp.searchengine.interfaces.ISearchEngine;

public class KMPNaive implements ISearchEngine {

    @Override
    public int search(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        for (int textIndex = 0; textIndex <= textLength - patternLength; textIndex++) {
            if (doesPatternMatch(text, pattern, textIndex)) {
                return textIndex;
            }
        }

        return -1;
    }

    private boolean doesPatternMatch(String text, String pattern, int startIndex) {
        int patternLength = pattern.length();

        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            char textChar = text.charAt(startIndex + patternIndex);
            char patternChar = pattern.charAt(patternIndex);

            if (textChar != patternChar) {
                return false;
            }
        }

        return true;
    }
}
