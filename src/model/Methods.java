package model;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Objects;

import io.FileUtil;


public final class Methods {

    // Stałe regex – jedno źródło prawdy
    private static final String PUNCT_REGEX = "[\\p{Punct}„”»«]";
    private static final String WHITESPACE_REGEX = "\\s+";

    //private Methods() { /* utility class */ }

    // --- PUBLIC API ---

    /** Liczy słowa w tekście (po normalizacji: trim + usunięcie interpunkcji). */
    public static int countWords(String text) {
        final String t = normalize(text, true);
        return t.isEmpty() ? 0 : t.split(WHITESPACE_REGEX).length;
    }

    /** Liczy znaki wraz ze spacjami i znakami nowej linii. */
    public static int countCharsWithSpaces(String text) {
        return Objects.requireNonNullElse(text, "").length();
    }

    /** Liczy znaki bez białych znaków (spacje, taby, nowe linie). */
    public static int countCharsWithoutSpaces(String text) {
        final String t = Objects.requireNonNullElse(text, "");
        return t.replaceAll(WHITESPACE_REGEX, "").length();
    }

    /** Statystyki dla pliku (czytanie zgodnie z Twoim FileUtil – FileInputStream). */
    public static StatsResult statsFromFile(String path) throws IOException {
        return statsFromText(FileUtil.readFileToString(path));
    }

    /** Statystyki dla String. */
    public static StatsResult statsFromText(String text) {
        return new StatsResult(
                countCharsWithSpaces(text),
                countCharsWithoutSpaces(text),
                countWords(text)
        );
    }

    // --- PRIVATE HELPERS ---

    /**
     * Normalizacja tekstu: null → "", trim, opcjonalnie usunięcie interpunkcji (→ spacje).
     * Pozostawia polskie litery; upraszcza liczenie słów.
     */
    private static String normalize(String text, boolean stripPunctuation) {
        String t = Objects.requireNonNullElse(text, "").trim();
        if (t.isEmpty()) return "";
        return stripPunctuation ? t.replaceAll(PUNCT_REGEX, " ") : t;
    }

    // --- LEKKI MODEL WYNIKU ---

    /** Lekki, niezmienny wynik. */
    public record StatsResult(int charsWithSpaces, int charsWithoutSpaces, int words) {
        @Override public String toString() {
            return "StatsResult{charsWithSpaces=%d, charsWithoutSpaces=%d, words=%d}"
                    .formatted(charsWithSpaces, charsWithoutSpaces, words);
        }
    }
}
