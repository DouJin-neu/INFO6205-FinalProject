package benchmark;

import org.junit.Test;
import sort.DualPivotChineseSort;
import sort.utils.Config;
import sort.utils.LazyLogger;
import sort.utils.MSDCoderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;

public class SortBenchmarkTest {

    final static LazyLogger logger = new LazyLogger(SortBenchmarkTest.class);

    @Test
    public void benchmarkTest() throws IOException{

        Config config = Config.load(SortBenchmarkTest.class);
        SortBenchmark sortBenchmark = new SortBenchmark(config);
        //do benchmark here
        int warmupRuns = SortBenchmark.getWarmupRuns(10);
        logger.info("Warming up ");
        for(int i = 0; i < warmupRuns; i++){
            sortBenchmark.sortStrings("shuffledChinese250K.txt");
        }
        logger.info("Warming up End");
        logger.info("Start sorting benchmark");
        sortBenchmark.sortStrings("shuffledChinese250K.txt");
        sortBenchmark.sortStrings("shuffledChinese500K.txt");
        sortBenchmark.sortStrings("shuffledChinese1M.txt");
        sortBenchmark.sortStrings("shuffledChinese2M.txt");
        sortBenchmark.sortStrings("shuffledChinese4M.txt");

    }

    @Test
    public void getWarmupRuns() {
        assertEquals(2, SortBenchmark.getWarmupRuns(0));
        assertEquals(2, SortBenchmark.getWarmupRuns(20));
        assertEquals(3, SortBenchmark.getWarmupRuns(30));
        assertEquals(10, SortBenchmark.getWarmupRuns(100));
        assertEquals(10, SortBenchmark.getWarmupRuns(1000));
    }

    @Test
    public void dualPivotBenchmarkTest(){
        String[] words = SortBenchmarkHelper.getWords("shuffledChinese250K.txt", SortBenchmark::lineAsList);
        SortBenchmark.runDualPivotBenchmark(words, words.length, 10, new DualPivotChineseSort<>(MSDCoderFactory.englishCoder));
    }

    private int getWarmupRuns(int m) {
        return Integer.max(2, Integer.min(10, m / 10));
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

}
