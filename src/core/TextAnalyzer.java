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
    private final SentenceTokenizer sentenceTokenizer;

    public TextAnalyzer(Normalizer normalizer,
                        Tokenizer tokenizer,
                        SentenceTokenizer sentenceTokenizer) {
        this.normalizer = Objects.requireNonNull(normalizer, "normalizer must not be null");
        this.tokenizer = Objects.requireNonNull(tokenizer, "tokenizer must not be null");
        this.sentenceTokenizer = Objects.requireNonNull(sentenceTokenizer, "sentenceTokenizer must not be null");
    }

    public TextStats analyze(String text) {
        String original = Objects.requireNonNullElse(text, "");

        int charsWithSpaces = original.length();
        int charsWithoutSpaces = original.replaceAll("\\s+", "").length();

        String normalized = normalizer.normalize(original);
        List<String> words = tokenizer.words(normalized);
        List<String> sentences = sentenceTokenizer.sentences(original);

        return new TextStats(charsWithSpaces, charsWithoutSpaces, words.size(), sentences.size());
    }

    public TextStats analyzeFile(String path) throws java.io.IOException {
        String content = io.FileUtil.readFileToString(path);
        return analyze(content);
    }
}