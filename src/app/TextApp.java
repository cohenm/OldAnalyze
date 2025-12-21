package app;

import core.DefaultNormalizer;
import core.DefaultSentenceTokenizer;
import core.TextAnalyzer;
import core.WhitespaceTokenizer;
//import model.TextStats;
//import model.WordCount;

import java.util.*; // Scanner etc.


public class TextApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Pobierz bazową nazwę pliku i zbuduj ścieżkę
        System.out.print("Podaj bazową nazwę pliku (bez .txt): ");
        String baseName = sc.nextLine().trim();
        String path = baseName + ".txt";

        // Konfiguracja analizatora
        TextAnalyzer analyzer = new TextAnalyzer(
                new DefaultNormalizer(),
                new WhitespaceTokenizer(),
                new DefaultSentenceTokenizer()
        );

        // Uruchom interaktywne menu
        new TextMenu(analyzer, path, sc).run();
    }
}