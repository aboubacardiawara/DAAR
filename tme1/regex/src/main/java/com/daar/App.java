package com.daar;

import java.util.logging.Logger;

import com.daar.reconnaissance.Reconnaissance;

import java.lang.Exception;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        String regEx = "S(a|g|r)+on";
        Reconnaissance.search("note.txt", regEx);
    }
}