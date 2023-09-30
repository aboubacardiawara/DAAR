package com.kmp;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testSearch0() {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABD";
        int position = searchEngine.search(text, pattern);
        assertEquals(0, position);
    }

    @Test
    public void testSearch1() {
        String text = "ABRACADABRA";
        String pattern = "CADABRA";
        int position = searchEngine.search(text, pattern);
        assertEquals(4, position);
    }

    @Test
    public void testSearch2() {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        int position = searchEngine.search(text, pattern);
        assertEquals(10, position);
    }

    @Test
    public void testSearch3() {
        String text = "ABABDABACDABABCABAB";
        String pattern = "Hello";
        int position = searchEngine.search(text, pattern);
        assertEquals(-1, position);
    }
}
