package com.kmp.searchengine;

import com.kmp.searchengine.interfaces.ISearchEngine;

public class KMPNaive implements ISearchEngine {

    @Override
    public int search(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        for (int i = 0; i <= textLength - patternLength; i++) {
            int j;

            // Vérifier si le motif correspond au sous-texte actuel
            for (j = 0; j < patternLength; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break; // Les caractères ne correspondent pas, passer au suivant dans le texte
                }
            }

            if (j == patternLength) {
                return i; // Motif trouvé à l'indice i dans le texte
            }
        }

        return -1; // Motif non trouvé dans le texte
    }

}
