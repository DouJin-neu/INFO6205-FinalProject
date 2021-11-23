package sort;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;
import sort.utils.LazyLogger;
import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;
import sort.utils.MSDCoderFactoryTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


/**
 * This class represents the purest form of Husky Sort based on IntroSort for pass 1 and the System sort for pass 2.
 * <p>
 * CONSIDER redefining all of the "to" parameters to be consistent with our other Sort utilities.
 *
 */
public class HuskyMergeChineseSortTest{

    String[] input = new String[]{"安","爱","埃", "张", "公","测试"};
    String[] expected = "爱 埃 安 测试 公 张".split(" ");

    private final static LazyLogger logger = new LazyLogger(HuskyMergeChineseSortTest.class);

    @Test
    public void test(){

        final int N = 6;
        final int m = 10;
        //final boolean preSorted = arg > 0 && Boolean.parseBoolean(args[0]);
        //final String inputOrder = preSorted ? "ordered" : "random";
        //logger.info("MergeHuskySort: sorting " + N + " " + inputOrder + " alphabetic ASCII words " + m + " times");
        logger.info("MergeHuskySort: sorting " + N + " " + " alphabetic ASCII words " + m + " times");
        final HuskyMergeChineseSort<String> sorter = new HuskyMergeChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(input);
        System.out.println(Arrays.toString(input));
        assertArrayEquals(expected, input);
    }

    @Test
    public void sortFile1() throws IOException {
        int n = 41;
        String[] words = getWords("shuffledChineseTest.txt", HuskyMergeChineseSortTest::lineAsList);
        final HuskyMergeChineseSort<String> sorter = new HuskyMergeChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(words);
        assertEquals("边黎明", words[0]);
        assertEquals("马文春", words[27]);
    }

    /**
     * Create a string representing an integer, with commas to separate thousands.
     *
     * @param x the integer.
     * @return a String representing the number with commas.
     */
    public static String formatWhole(final int x) {
        return String.format("%,d", x);
    }

    /**
     * Method to open a resource relative to this class and from the corresponding File, get an array of Strings.
     *
     * @param resource           the URL of the resource containing the Strings required.
     * @param stringListFunction a function which takes a String and splits into a List of Strings.
     * @return an array of Strings.
     */
    static String[] getWords(final String resource, final Function<String, List<String>> stringListFunction) {
        try {
            final File file = new File(getPathname(resource, DualPivotChineseSortTest.class));
            final String[] result = getWordArray(file, stringListFunction, 1);
            System.out.println("getWords: testing with " + formatWhole(result.length) + " unique words: from " + file);
            return result;
        } catch (final FileNotFoundException e) {
            System.out.println("Cannot find resource: " + resource);
            return new String[0];
        }
    }

    private static List<String> getWordList(final FileReader fr, final Function<String, List<String>> stringListFunction, final int minLength) {
        final List<String> words = new ArrayList<>();
        for (final Object line : new BufferedReader(fr).lines().toArray())
            words.addAll(stringListFunction.apply((String) line));
        return words.stream().distinct().filter(s -> s.length() >= minLength).collect(Collectors.toList());
    }

    /**
     * Method to read given file and return a String[] of its content.
     *
     * @param file               the file to read.
     * @param stringListFunction a function which takes a String and splits into a List of Strings.
     * @param minLength          the minimum acceptable length for a word.
     * @return an array of Strings.
     */
    static String[] getWordArray(final File file, final Function<String, List<String>> stringListFunction, final int minLength) {
        try (final FileReader fr = new FileReader(file)) {
            return getWordList(fr, stringListFunction, minLength).toArray(new String[0]);
        } catch (final IOException e) {
            System.out.println("Cannot open file: " + file);
            return new String[0];
        }
    }

    private static String getPathname(final String resource, @SuppressWarnings("SameParameterValue") final Class<?> clazz) throws FileNotFoundException {
        final URL url = clazz.getClassLoader().getResource(resource);
        if (url != null) return url.getPath();
        throw new FileNotFoundException(resource + " in " + clazz);
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

}
