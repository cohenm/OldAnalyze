package app;

import core.DefaultNormalizer;
import core.TextAnalyzer;
import core.WhitespaceTokenizer;
import model.TextStats;


import java.util.Scanner;

public class TextApp {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String sentence = input.nextLine();
        String path = sentence + ".txt";



        TextAnalyzer analyzer = new TextAnalyzer(new DefaultNormalizer(), new WhitespaceTokenizer());

        try {
            TextStats stats = analyzer.analyzeFile(path);
            System.out.println("=== STATYSTYKI ===");
            System.out.println("Słowa: " + stats.words());
            System.out.println("Znaki (ze spacjami): " + stats.charsWithSpaces());
            System.out.println("Znaki (bez spacji): " + stats.charsWithoutSpaces());
        } catch (Exception e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
            System.exit(2);
        }
    }
}