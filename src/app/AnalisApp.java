package app;

import model.Methods;

import java.io.FileNotFoundException;


public class AnalisApp {
    public static void main(String[] args) throws FileNotFoundException {

        String path = "file.txt";

        try {
            Methods.StatsResult res = Methods.statsFromFile(path);

            System.out.println("=== STATYSTYKI ===");
            System.out.println("Słowa: " + res.words);
            System.out.println("Znaki (ze spacjami): " + res.charsWithSpaces);
            System.out.println("Znaki (bez spacji): " + res.charsWithoutSpaces);
        } catch (Exception e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
        }
    }
}
