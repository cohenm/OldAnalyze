package model;

import java.util.ArrayList;
import java.io.IOException;

import io.FileUtil;

public class Methods {

    static int countWords(String text) {
        if (text == null) return 0;
        String trimmed = text.trim();
        if (trimmed.isEmpty()) return 0;

        // Ignorujemy interpunkcję (możesz wyłączyć, jeśli niepotrzebne)
        trimmed = trimmed.replaceAll("[\\p{Punct}„”»«]", " ");

        // Rozbijamy po białych znakach
        String[] parts = trimmed.split("\\s+");

        // Przenosimy do ArrayList
        ArrayList<String> words = new ArrayList<>(parts.length);
        for (String p : parts) {
            if (!p.isBlank()) {
                words.add(p);
            }
        }
        return words.size();
    }

    /**
     * Liczy znaki wraz ze spacjami.
     * Wykorzystuje String.length(), zwraca 0 dla null.
     */
    static int countCharsWithSpaces(String text) {
        if (text == null) return 0;
        return text.length();
    }

    /**
     * Liczy znaki bez białych znaków (spacje, taby, nowe linie itd.).
     * Wykorzystuje regex \s+ do usunięcia białych znaków.
     * Zwraca 0 dla null.
     */
    static int countCharsWithoutSpaces(String text) {
        if (text == null) return 0;
        String withoutWhiteSpace = text.replaceAll("\\s+", "");
        return withoutWhiteSpace.length();
    }

    // --- nowa funkcja: liczenie statystyk z pliku ---

    /**
     * Wczytuje plik i zwraca podstawowe statystyki:
     * - słowa
     * - znaki ze spacjami
     * - znaki bez spacji
     * <p>
     * Używa FileUtil.readFileToString(...) (FileInputStream) zgodnie z Twoim podejściem.
     */
    public static StatsResult statsFromFile(String path) throws IOException {
        String content = FileUtil.readFileToString(path);
        return statsFromText(content);
    }

    /**
     * Liczy statystyki bezpośrednio dla podanego tekstu.
     */

    public static StatsResult statsFromText(String text) {
        int words = countWords(text);
        int charsWithSpaces = countCharsWithSpaces(text);
        int charsWithoutSpaces = countCharsWithoutSpaces(text);
        return new StatsResult(charsWithSpaces, charsWithoutSpaces, words);
    }

    // Prosty obiekt-wynik
    public static class StatsResult {
        public final int charsWithSpaces;
        public final int charsWithoutSpaces;
        public final int words;

        public StatsResult(int charsWithSpaces, int charsWithoutSpaces, int words) {
            this.charsWithSpaces = charsWithSpaces;
            this.charsWithoutSpaces = charsWithoutSpaces;
            this.words = words;
        }

        @Override
        public String toString() {
            return "StatsResult{" +
                    "charsWithSpaces=" + charsWithSpaces +
                    ", charsWithoutSpaces=" + charsWithoutSpaces +
                    ", words=" + words +
                    '}';
        }
    }
}