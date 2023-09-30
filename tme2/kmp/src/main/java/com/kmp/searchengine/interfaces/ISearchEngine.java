package com.kmp.searchengine.interfaces;

public interface ISearchEngine {
    /**
     * Recherche le motif dans le texte donné en utilisant l'algorithme KMP.
     *
     * @param text    Le texte dans lequel rechercher le motif.
     * @param pattern Le motif à rechercher dans le texte.
     * @return le premier indice où le motif a été trouvé dans le texte.
     */
    public int search(String text, String pattern);
}
