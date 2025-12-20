package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Narzędzia do wczytywania plików tekstowych.
 * Wersja zgodna z Twoim podejściem: FileInputStream + StringBuilder.
 * Uwaga: dla plików UTF-8 polskie znaki mogą nie zdekodować się poprawnie,
 * bo czytamy surowe bajty i rzutujemy na char. Jeśli będziesz potrzebować
 * pełnego wsparcia UTF-8, rozważ InputStreamReader(UTF-8) + BufferedReader.
 */
public class FileUtil {

    /**
     * Czyta cały plik jako String, składając kolejne bajty do StringBuilder.
     * Nie dodaje znaków nowej linii automatycznie (czytamy "tak jak leci").
     *
     * @param path ścieżka do pliku
     * @return zawartość pliku jako String
     * @throws IOException gdy nie uda się otworzyć/odczytać pliku
     */

    public static String readFileToString(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream input = new FileInputStream(path)) {
            int b;
            while ((b = input.read()) != -1) {
                sb.append((char) b);
            }
        }
        return sb.toString();
    }
    /**
     * (Opcjonalnie) Czyta plik i dokleja znak nowej linii po każdej linii,
     * emulując zachowanie "line-based". Wciąż używa FileInputStream.
     * Uwaga: to proste podejście; jeśli potrzebujesz rzetelnego dzielenia na linie,
     * użyj BufferedReader + InputStreamReader(UTF-8).
     */

    public static String readFileToStringWithNewlines(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream input = new FileInputStream(path)) {
            int b;
            while ((b = input.read()) != -1) {
                char c = (char) b;
                sb.append(c);
                // Tu można próbować wykrywać koniec linii po '\r' lub '\n' i dopisywać '\n',
                // ale FileInputStream nie daje "liniowego" czytania, to tylko demonstracja.
            }
        }
        return sb.toString();
    }

    // --- Alternatywa (komentowana) dla pełnego wsparcia UTF-8 ---
    // public static String readFileUtf8(String path) throws IOException {
    //     StringBuilder sb = new StringBuilder();
    //     try (BufferedReader reader = new BufferedReader(
    //             new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             sb.append(line).append('\n'); // zachowujemy nowe linie
    //         }
    //     }
    //     return sb.toString();
    // }
}