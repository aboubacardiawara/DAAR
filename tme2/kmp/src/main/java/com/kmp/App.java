package com.kmp;

public class App {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: mvn exec:java -Dexec.args=\"<pattern> <text_file>\"");
            System.exit(1);
        }
        Long startTime = System.currentTimeMillis();
        Finder finder = new Finder();
        finder.setSearchEngineToKmpWithOptimizedLPS();
        finder.finder(args[0], args[1]);

        Long endTime = System.currentTimeMillis();
        System.out.println("Temps d'execution : " + (endTime - startTime) + " ms");
    }

}
