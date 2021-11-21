package com.info6205.team21.sort.benchmark;

import com.info6205.team21.sort.utils.LazyLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static edu.neu.coe.info6205.util.Utilities.formatWhole;

public class SortBenchmarkHelper {

    final static LazyLogger logger = new LazyLogger(SortBenchmarkHelper.class);

    // TEST
    static String[] getWords(String resource, Function<String, Collection<String>> getStrings) throws FileNotFoundException {
        List<String> words = new ArrayList<>();
        FileReader fr = new FileReader(getFile(resource, SortBenchmarkHelper.class));
        for (Object line : new BufferedReader(fr).lines().toArray()) words.addAll(getStrings.apply((String) line));
        words = words.stream().distinct().filter(new Predicate<String>() {
            private static final int MINIMUM_LENGTH = 2;

            public boolean test(String s) {
                return s.length() >= MINIMUM_LENGTH;
            }
        }).collect(Collectors.toList());
        logger.info("Testing with words: " + formatWhole(words.size()) + " from " + resource);
        String[] result = new String[words.size()];
        result = words.toArray(result);
        return result;
    }

    static Collection<String> getWords(Pattern regex, String line) {
        final Matcher matcher = regex.matcher(line);
        if (matcher.find()) {
            final String word = matcher.group(1);
            final String[] strings = word.split("[\\s\\p{Punct}\\uFF0C]");
            return Arrays.asList(strings);
        } else
            return new ArrayList<>();
    }

    // TEST
    private static String getFile(String resource, @SuppressWarnings("SameParameterValue") Class<?> clazz) throws FileNotFoundException {
        final URL url = clazz.getClassLoader().getResource(resource);
        if (url != null) return url.getFile();
        throw new FileNotFoundException(resource + " in " + clazz);
    }

    // NOTE private constructor (singleton pattern)
    private SortBenchmarkHelper() {
    }
}
