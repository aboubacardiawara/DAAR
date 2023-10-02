package com.kmp;

public class LPSOptimizer {

    public static int[] optimise(String pattern, int[] lps) {
        char[] patternAsArray = pattern.toCharArray();
        int[] carryOver = lps;
        for (int i = 0; i < pattern.length(); i++) {
            if (carryOver[i] < 0) {
                continue;
            }
            if ( patternAsArray[i] == patternAsArray[carryOver[i]] && carryOver[carryOver[i]] != -1) {
                carryOver[i] = carryOver[carryOver[i]];
            } else if(patternAsArray[i] == patternAsArray[carryOver[i]] && carryOver[carryOver[i]] == -1) {
                carryOver[i] = -1;
            }
            
        }
        return carryOver;
    }

}
