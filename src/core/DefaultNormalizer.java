package core;

import java.util.Objects;

/// DefaultNormalizer — domyślna implementacja (trim + usunięcie interpunkcji + lowercase)
public class DefaultNormalizer implements Normalizer {
    private static final String PUNCT_REGEX = "[\\p{Punct}„”»«]";
    @Override
    public String normalize(String text) {
        String t = Objects.requireNonNullElse(text, "").trim();
        if (t.isEmpty()) return "";
        return t.toLowerCase().replaceAll(PUNCT_REGEX, " ");
    }
}
