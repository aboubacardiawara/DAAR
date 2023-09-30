package com.kmp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kmp.searchengine.KMPNaive;
import com.kmp.searchengine.interfaces.ISearchEngine;

public class KMPTest {
    protected ISearchEngine searchEngine;

    @BeforeEach
    public void setUp() {
        searchEngine = new KMPNaive();
    }

    @Test
    public void testSearch() {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        int position = searchEngine.search(text, pattern);
        assertTrue(position == 0);
    }
}
