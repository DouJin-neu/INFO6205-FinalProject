package sort;

import benchmark.SortBenchmarkHelper;
import org.junit.Test;
import sort.utils.MSDCoderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DualPivotChineseSortTest {

    String[] input = new String[]{"安","爱","埃", "张", "公","测试"};
    String[] expected = "埃 爱 安 测试 公 张".split(" ");

    @Test
    public void test(){

        final DualPivotChineseSort<String> sorter = new DualPivotChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(input);
        System.out.println(Arrays.toString(input));
        assertArrayEquals(expected, input);
    }

    @Test
    public void sortFile1() throws IOException{
        int n = 41;
        SortBenchmarkHelper helper = new SortBenchmarkHelper();
        String[] words = helper.getWords("shuffledChineseTest.txt", DualPivotChineseSortTest::lineAsList);
        final DualPivotChineseSort<String> sorter = new DualPivotChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(words);
        assertEquals("边黎明", words[0]);
        assertEquals("马文春", words[27]);
    }

    @Test
    public void sortFile2() throws IOException{
        SortBenchmarkHelper helper = new SortBenchmarkHelper();
        String[] words = helper.getWords("shuffledChinese1M.txt", DualPivotChineseSortTest::lineAsList);
        final DualPivotChineseSort<String> sorter = new DualPivotChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(words);
        assertEquals("阿安", words[0]);
        assertEquals("阿冬", words[34]);
        assertEquals("阿飞儿", words[41]);
        assertEquals("阿蕾蕾", words[140]);
        assertEquals("阿威", words[260]);
        assertEquals("阿滢", words[347]);
        assertEquals("阿姿", words[400]);
        assertEquals("艾爱", words[401]);
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

}
