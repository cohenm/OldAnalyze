import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*

 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        String path = "file.txt";
        StringBuilder sb = new StringBuilder();

        try(FileInputStream input = new FileInputStream(path)){
            int b;
            while ((b = input.read()) != -1) {
                sb.append((char) b);
            }
        } catch (IOException e){
            System.out.println("Błąd odczytu pliku: " + e.getMessage());
            return;
        }

        String text = sb.toString();
        System.out.println("=== Zawartość pliku ===");

        System.out.println("Słowa: " + methods.countWords(text));
        System.out.println("Znaki (ze spacjami): " + methods.countCharsWithSpaces(text));
        System.out.println("Znaki (bez spacji): " + methods.countCharsWithoutSpaces(text));

    }
}