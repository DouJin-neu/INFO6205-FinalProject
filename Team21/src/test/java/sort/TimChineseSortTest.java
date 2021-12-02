package sort;
import benchmark.SortBenchmarkHelper;
import org.junit.Test;
import sort.utils.LazyLogger;
import sort.utils.MSDCoderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This class represents the purest form of Husky Sort based on IntroSort for pass 1 and the System sort for pass 2.
 * <p>
 * CONSIDER redefining all of the "to" parameters to be consistent with our other Sort utilities.
 *
 */
public class TimChineseSortTest {
    String[] input = new String[]{"安","爱","埃", "张", "公","测试"};
    String[] expected = "埃 爱 安 测试 公 张".split(" ");

    private final static LazyLogger logger = new LazyLogger(HuskyMergeChineseSortTest.class);

    @Test
    public void test(){

        final int N = 6;
        final int m = 10;
        final TimChineseSort<String> sorter = new TimChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(input);
        System.out.println(Arrays.toString(input));
        assertArrayEquals(expected, input);
    }

    @Test
    public void sortFile1() throws IOException {
        int n = 41;
        SortBenchmarkHelper helper = new SortBenchmarkHelper();
        String[] words = helper.getWords("shuffledChineseTest.txt", TimChineseSortTest::lineAsList);
        final TimChineseSort<String> sorter = new TimChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(words);
        assertEquals("边黎明", words[0]);
        assertEquals("马文春", words[27]);
        assertEquals("边黎明", words[0]);
        assertEquals("黄浩华", words[12]);
        assertEquals("黄腾亿", words[13]);
        assertEquals("黄有珍", words[14]);
    }


    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }
}
