package com.kmp;

public class App {

    public static void main(String[] args) {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        int[] lps = computeLPSArray(pattern);
        search(text, pattern, lps);
    }

    // Fonction pour calculer le tableau LPS (Longest Prefix Suffix)
    public static int[] computeLPSArray(String pattern) {
        int n = pattern.length();
        int[] lps = new int[n];
        lps[0] = 0;
        int length = 0; // Longueur du plus long préfixe suffixe jusqu'à présent

        int i = 1;
        while (i < n) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
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

    // Fonction pour effectuer la recherche avec l'algorithme KMP
    public static void search(String text, String pattern, int[] lps) {
        int m = pattern.length();
        int n = text.length();
        int i = 0; // Index pour le texte
        int j = 0; // Index pour le motif

        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == m) {
                System.out.println("Motif trouvé à l'index " + (i - j));
                j = lps[j - 1];
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
    }
}
