package core;

import model.TextStats;
import io.FileUtil;

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

        return new TextStats(charsWithSpaces, charsWithoutSpaces, words.size());
    }

    /** Analiza pliku (delegacja do FileUtil) */
    public TextStats analyzeFile(String path) throws java.io.IOException {
        String content = io.FileUtil.readFileToString(path);
        return analyze(content);
    }
}
