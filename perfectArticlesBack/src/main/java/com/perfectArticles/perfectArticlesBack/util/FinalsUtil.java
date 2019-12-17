package com.perfectArticles.perfectArticlesBack.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FinalsUtil {

    private static final String[] IGNORE_WORDS_ARRAY = {"the", "of", "to", "and", "a", "in", "is", "it", "you", "that", "he",
            "was", "for", "on", "are", "with", "as", "his", "i", "they", "be", "at", "one", "have", "this", "from", "or",
            "had", "by", "hot", "but", "some", "what", "there", "we", "can", "out", "other", "were", "all", "your",
            "when", "up", "use", "word", "how", "said", "an", "each", "she", "which", "do", "their", "time", "if",
            "will", "way", "about", "many", "then", "them", "would", "write", "like", "so", "these", "her", "long",
            "make", "thing", "see", "him", "two", "has", "look", "more", "day", "could", "go", "come", "did", "my",
            "sound", "no", "most", "number", "who", "over", "know", "water", "than", "call", "first", "people", "may",
            "down", "side", "been", "now", "find"};
    public static final Set<String> IGNORE_IN_TAG = new HashSet<String>(Arrays.asList(IGNORE_WORDS_ARRAY));

    public static final String LIKES = "LIKES";
}
