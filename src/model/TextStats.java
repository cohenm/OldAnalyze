package model;

public record TextStats(int charsWithSpaces, int charsWithoutSpaces, int words, int sentences) {
    @Override public String toString() {
        return "TextStats{charsWithSpaces=%d, charsWithoutSpaces=%d, words=%d, sentences=%d}"
                .formatted(charsWithSpaces, charsWithoutSpaces, words, sentences);
    }
}