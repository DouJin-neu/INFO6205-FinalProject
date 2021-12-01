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

public class DualPivotChineseSortTest {
    //除了DualPivotChineseSort/MSDExchangeChineseSort，其他排序方法”爱“都是第一个
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
        String[] words = helper.getWords("shuffledChinese2M.txt", DualPivotChineseSortTest::lineAsList);
        final DualPivotChineseSort<String> sorter = new DualPivotChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(words);
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

}
