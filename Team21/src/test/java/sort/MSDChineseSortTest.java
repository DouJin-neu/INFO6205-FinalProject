package sort;


import benchmark.SortBenchmarkHelper;
import org.junit.Test;
import sort.utils.MSDCoderFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This class represents the purest form of Husky Sort based on IntroSort for pass 1 and the System sort for pass 2.
 * <p>
 * CONSIDER redefining all of the "to" parameters to be consistent with our other Sort utilities.
 *
 */
public class MSDChineseSortTest {

    String[] input = new String[]{"安","爱","埃", "张", "公","测试"};
    String[] expected = "爱 埃 安 测试 公 张".split(" ");

    @Test
    public void test(){

        final MSDChineseSort<String> sorter = new MSDChineseSort<>(MSDCoderFactory.pinyinCoder);
        sorter.sort(input);
        System.out.println(Arrays.toString(input));
        assertArrayEquals(expected, input);
    }

    @Test
    public void sortFile1() throws IOException {
        int n = 41;
        SortBenchmarkHelper helper = new SortBenchmarkHelper();
        String[] words = helper.getWords("shuffledChineseTest.txt", MSDChineseSortTest::lineAsList);
        final MSDChineseSort<String> sorter = new MSDChineseSort<>(MSDCoderFactory.pinyinCoder);
        sorter.sort(words);
        assertEquals("边黎明", words[0]);
        assertEquals("黄浩华", words[12]);
        assertEquals("黄腾亿", words[13]);
        assertEquals("黄有珍", words[14]);
    }

    @Test
    public void sortFile2() throws IOException {
        SortBenchmarkHelper helper = new SortBenchmarkHelper();
        String[] words = helper.getWords("shuffledChinese.txt", MSDChineseSortTest::lineAsList);
        final MSDChineseSort<String> sorter = new MSDChineseSort<>(MSDCoderFactory.pinyinCoder);
        sorter.sort(words);
        assertEquals("阿安", words[0]);
        assertEquals("阿姿", words[400]);
        assertEquals("艾爱", words[401]);
        assertEquals("何静姝", words[238108]);
        assertEquals("焦志成", words[331980]);
        assertEquals("焦正军", words[331974]);
        assertEquals("张艾丽", words[999982]);
        assertEquals("张爱爱", words[999997]);
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }
}