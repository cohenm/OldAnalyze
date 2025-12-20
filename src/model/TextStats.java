package model;

public record TextStats(int charsWithSpaces, int charsWithoutSpaces, int words) {
    @Override public String toString() {
        return "TextStats{charsWithSpaces=%d, charsWithoutSpaces=%d, words=%d}"
                .formatted(charsWithSpaces, charsWithoutSpaces, words);
    }
}