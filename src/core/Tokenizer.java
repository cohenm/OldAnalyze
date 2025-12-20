package core;

import java.util.List;

/// Tokenizer — odpowiedzialny za rozbicie na słowa (lub zdania, jeśli rozbudujesz)

public interface Tokenizer {
    List<String> words(String normalizedText);
}

