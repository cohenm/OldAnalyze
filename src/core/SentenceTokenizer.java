package core;

import java.util.List;

public interface SentenceTokenizer {
    List<String> sentences(String text);
}