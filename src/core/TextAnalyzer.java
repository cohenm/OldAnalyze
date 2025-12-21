package core;

import model.TextStats;
import model.WordCount;
import model.WordSort;

import java.io.IOException;
import java.util.*; // Map, Set, List, Comparator, etc.
import java.util.stream.Collectors;

/// Klasa serwisowa TextAnalyzer
///
/// Encapsuluje całą logikę liczenia znaków i słów, używając dostarczonych strategii.

public class TextAnalyzer {

    private final Normalizer normalizer;
    private final Tokenizer tokenizer;
    private final SentenceTokenizer sentenceTokenizer;

    // Konstruktor - wywołujący normalizera i tokenizery
    public TextAnalyzer(Normalizer normalizer,
                        Tokenizer tokenizer,
                        SentenceTokenizer sentenceTokenizer) {
        this.normalizer = Objects.requireNonNull(normalizer, "normalizer must not be null");
        this.tokenizer = Objects.requireNonNull(tokenizer, "tokenizer must not be null");
        this.sentenceTokenizer = Objects.requireNonNull(sentenceTokenizer, "sentenceTokenizer must not be null");
    }

    // analiza tekstu
    public TextStats analyze(String text) {
        String original = Objects.requireNonNullElse(text, "");

        int charsWithSpaces = original.length();
        int charsWithoutSpaces = original.replaceAll("\\s+", "").length();

        String normalized = normalizer.normalize(original);
        List<String> words = tokenizer.words(normalized);
        List<String> sentences = sentenceTokenizer.sentences(original);

        return new TextStats(charsWithSpaces, charsWithoutSpaces, words.size(), sentences.size());
    }

    // analiza tekstu
    public TextStats analyzeFile(String path) throws IOException {
        String content = io.FileUtil.readFileToString(path);
        return analyze(content);
    }

    // ====== częstotliwości słów ======

    /** Pełna mapa częstotliwości (po normalizacji), z opcjonalnymi stop‑words i minimalną długością słowa. */
    public Map<String, Integer> wordFrequencyFromText(String text,
                                                      Set<String> stopWords,
                                                      int minWordLength) {
        String normalized = normalizer.normalize(Objects.requireNonNullElse(text, ""));
        List<String> words = tokenizer.words(normalized);

        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) {
            if ((stopWords == null || !stopWords.contains(w))
                    && w.length() >= Math.max(1, minWordLength)) {
                freq.merge(w, 1, Integer::sum);
            }
        }
        return freq;
    }

    /** Wersje plikowe (delegują do readFileToString). */
    public Map<String, Integer> wordFrequencyFromFile(String path,
                                                      Set<String> stopWords,
                                                      int minWordLength) throws IOException {
        String content = io.FileUtil.readFileToString(path);
        return wordFrequencyFromText(content, stopWords, minWordLength);
    }

    /**
     * do tego miejsca
     */


    /** Domyślnie: top N słów malejąco po liczbie wystąpień, przy remisie alfabetycznie. */
    public List<WordCount> topWordsFromText(String text,
                                            int topN,
                                            Set<String> stopWords,
                                            int minWordLength) {
        return topWordsFromText(text, topN, stopWords, minWordLength, WordSort.FREQUENCY_DESC);
    }

    public List<WordCount> topWordsFromFile(String path,
                                            int topN,
                                            Set<String> stopWords,
                                            int minWordLength) throws IOException {
        String content = io.FileUtil.readFileToString(path);
        return topWordsFromText(content, topN, stopWords, minWordLength);
    }

    // ====== NOWE: wersje ze strategią sortowania (ENUM) ======
    /** Zwraca listę słów posortowaną zgodnie z WordSort; limit topN (min. 1). */
    public List<WordCount> topWordsFromText(String text,
                                            int topN,
                                            Set<String> stopWords,
                                            int minWordLength,
                                            WordSort sortMode) {
        Map<String, Integer> freq = wordFrequencyFromText(text, stopWords, minWordLength);

        return freq.entrySet().stream()
                .map(e -> new WordCount(e.getKey(), e.getValue()))
                .sorted(sortMode.comparator())
                .limit(Math.max(1, topN))
                .collect(Collectors.toList());
    }

    /** Pełna lista posortowana wg WordSort (bez limitu). */
    public List<WordCount> allWordsFromTextSorted(String text,
                                                  Set<String> stopWords,
                                                  int minWordLength,
                                                  WordSort sortMode) {
        Map<String, Integer> freq = wordFrequencyFromText(text, stopWords, minWordLength);

        return freq.entrySet().stream()
                .map(e -> new WordCount(e.getKey(), e.getValue()))
                .sorted(sortMode.comparator())
                .collect(Collectors.toList());
    }

    /** Wersja plikowa z WordSort. */
    public List<WordCount> topWordsFromFile(String path,
                                            int topN,
                                            Set<String> stopWords,
                                            int minWordLength,
                                            WordSort sortMode) throws IOException {
        String content = io.FileUtil.readFileToString(path);
        return topWordsFromText(content, topN, stopWords, minWordLength, sortMode);
    }

    /** (Opcjonalnie) Zwraca posortowaną mapę częstotliwości jako LinkedHashMap (kolejność wg sortMode). */
    public Map<String, Integer> wordFrequencySorted(String text,
                                                    Set<String> stopWords,
                                                    int minWordLength,
                                                    WordSort sortMode) {
        Map<String, Integer> freq = wordFrequencyFromText(text, stopWords, minWordLength);

        return freq.entrySet().stream()
                .map(e -> new WordCount(e.getKey(), e.getValue()))
                .sorted(sortMode.comparator())
                .collect(Collectors.toMap(
                        WordCount::word,
                        WordCount::count,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
