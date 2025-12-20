import java.util.ArrayList;
import java.util.Collections;

public class Main {

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
        for (String p : parts){
            if(!p.isBlank()){
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
        String withoutWhiteSpace = text.replaceAll("\\s+","");
        return withoutWhiteSpace.length();
    }

    public static void main(String[] args) {
        // Przykładowy tekst — później możesz podawać go z args lub Scannerem
        String text = "Ala,          ma kota! A               kot ma   Alę.\nTo jest test.";
        System.out.println("Tekst: " + text);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Słowa: " + countWords(text));
        System.out.println("Znaki (ze spacjami): " + countCharsWithSpaces(text));
        System.out.println("Znaki (bez spacji): " + countCharsWithoutSpaces(text));

    }
}