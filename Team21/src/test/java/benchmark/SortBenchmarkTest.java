package benchmark;

import edu.neu.coe.info6205.util.Benchmark_Timer;
import org.junit.Test;
import sort.*;
import sort.utils.MSDCoderFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortBenchmarkTest {

    @Test
    public void benchmarkTest(){
        SortBenchmarkHelper helper = new SortBenchmarkHelper();
        String[] words = helper.getWords("shuffledChineseTest.txt", SortBenchmarkTest::lineAsList);

        final MSDChineseSort<String> sorter = new MSDChineseSort<>(MSDCoderFactory.pinyinCoder);
        final TimChineseSort<String> TimSorter = new TimChineseSort<>(MSDCoderFactory.englishCoder);
        System.out.println("==========Warm Up End=================");
        SortBenchmark.runTimSortBenchmark(words, words.length, 10,TimSorter);
        SortBenchmark.runMSDSortBenchmark(words, words.length, 10,sorter);

    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

}
