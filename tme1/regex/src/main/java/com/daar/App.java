package com.daar;

import java.util.logging.Logger;

import com.daar.automate.AutomateBuilder;
import com.daar.automate.IAutomate;
import com.daar.reconnaissance.Reconnaissance;

import java.lang.Exception;

public class App {
    private static final AutomateBuilder automateBuilder = new AutomateBuilder();
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        String regEx = "Hello";
        IAutomate automate = automateBuilder.buildFromRegex(regEx);
        Boolean is_match = Reconnaissance.match("Hello world", automate);
        System.out.println(is_match);
    }

}