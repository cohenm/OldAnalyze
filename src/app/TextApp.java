package app;

import core.DefaultNormalizer;
import core.DefaultSentenceTokenizer;
import core.TextAnalyzer;
import core.WhitespaceTokenizer;
import model.TextStats;
import java.util.Scanner;

public class TextApp {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Podaj nazwę pliku:");
        String ext = input.nextLine();
        String path = ext + ".txt";


        TextAnalyzer analyzer = new TextAnalyzer(
                new DefaultNormalizer(),
                new WhitespaceTokenizer(),
                new DefaultSentenceTokenizer());


        try {
            TextStats stats = analyzer.analyzeFile(path);
            System.out.println("=== STATYSTYKI ===");
            System.out.println("Słowa: " + stats.words());
            System.out.println("Znaki (ze spacjami): " + stats.charsWithSpaces());
            System.out.println("Znaki (bez spacji): " + stats.charsWithoutSpaces());
            System.out.println("Zdania: " + stats.sentences());
        } catch (Exception e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
            System.exit(2);
        }
    }
}