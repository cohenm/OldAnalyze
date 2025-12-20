package core;

import model.TextStats;
import java.util.List;
import java.util.Objects;

/// Klasa serwisowa TextAnalyzer
///
/// Encapsuluje całą logikę liczenia znaków i słów, używając dostarczonych strategii.

public class TextAnalyzer {
    private final Normalizer normalizer;
    private final Tokenizer tokenizer;

    public TextAnalyzer(Normalizer normalizer, Tokenizer tokenizer) {
        this.normalizer = Objects.requireNonNull(normalizer);
        this.tokenizer = Objects.requireNonNull(tokenizer);
    }

    /** Analiza tekstu (String) */
    public TextStats analyze(String text) {
        String original = Objects.requireNonNullElse(text, "");
        int charsWithSpaces = original.length();
        int charsWithoutSpaces = original.replaceAll("\\s+", "").length();

        // Normalizacja + tokenizacja
        String normalized = normalizer.normalize(original);
        List<String> words = tokenizer.words(normalized);

        int sentences = countSentences(original);

        return new TextStats(charsWithSpaces, charsWithoutSpaces, words.size(), sentences);
    }

    /** Analiza pliku (delegacja do FileUtil) */
    public TextStats analyzeFile(String path) throws java.io.IOException {
        String content = io.FileUtil.readFileToString(path);
        return analyze(content);
    }

    /** proste liczenie zdań po . !  */
    private int countSentences(String text) {
        if (text == null) return 0;
        String trimmed = text.trim();
        if (trimmed.isEmpty()) return 0;
        String[] parts = trimmed.split("[.!?]+");
        int count = 0;
        for (String p : parts) {
            if (!p.trim().isEmpty()) count++;
        }
        return count;
    }
}
