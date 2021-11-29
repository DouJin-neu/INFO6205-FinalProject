package benchmark;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.util.Timer;
import sort.DualPivotChineseSort;
import sort.HuskyMergeChineseSort;
import sort.LSDChineseSort;
import sort.MSDChineseSort;
import sort.MSDExchangeChineseSort;
import sort.TimChineseSort;
import sort.utils.Config;
import sort.utils.MSDCoderFactory;
import edu.neu.coe.info6205.util.LazyLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static benchmark.SortBenchmarkHelper.getWords;
import static edu.neu.coe.info6205.util.Utilities.formatWhole;

public class SortBenchmark {

    private final Config config;
    final static LazyLogger logger = new LazyLogger(SortBenchmark.class);

    public SortBenchmark(Config config){this.config = config;}

    public static void main(String[] args) throws IOException {
        Config config = Config.load(SortBenchmark.class);
        logger.info("SortBenchmark.main: " + config.get("sortbenchmark", "version") + " with word counts: " + Arrays.toString(args));
        if (args.length == 0) logger.warn("No word counts specified on the command line");
        SortBenchmark sortBenchmark = new SortBenchmark(config);
        //do benchmark here
        sortBenchmark.sortStrings();

    }

    private void sortStrings() throws IOException {
        logger.info("Beginning String sorts");

        // NOTE: Leipzig Chinese words benchmarks (according to command-line arguments)
        benchmarkStringSorters(getWords("shuffledChineseTest.txt", SortBenchmark::lineAsList), 41, 10);

    }

    /**
     * Method to run pure (non-instrumented) string sorter benchmarks.
     * <p>
     * NOTE: this is package-private because it is used by unit tests.
     *
     * @param sourceWords  the word source.
     * @param nWords the number of words to be sorted.
     * @param nRuns  the number of runs.
     */
    void benchmarkStringSorters(String[] sourceWords, int nWords, int nRuns) {
        logger.info("Testing pure sorts with " + formatWhole(nRuns) + " runs of sorting " + formatWhole(nWords) + " words");
        Random random = new Random();

        //sort words with certain length
        String[] words = new String[nWords];
        for (int i = 0; i < nWords; i++) words[i] =sourceWords[random.nextInt(sourceWords.length)];

        //doing benchmarks for different types of sorting algorithms
        //MSDChineseSort
        runMSDSortBenchmark(words, nWords, nRuns, new MSDChineseSort<>(MSDCoderFactory.pinyinCoder));
        runMSDExchangeSortBenchmark(words, nWords, nRuns, new MSDExchangeChineseSort<>(MSDCoderFactory.bitCoder));
        runLSDSortBenchmark(words, nWords, nRuns, new LSDChineseSort<>(MSDCoderFactory.pinyinCoder));
        runHuskyMergeBenchmark(words, nWords, nRuns, new HuskyMergeChineseSort<>(MSDCoderFactory.englishCoder));
        runDualPivotBenchmark(words, nWords, nRuns, new DualPivotChineseSort<>(MSDCoderFactory.englishCoder));
        runTimSortBenchmark(words, nWords, nRuns, new TimChineseSort<>(MSDCoderFactory.englishCoder));

    }

    /**
     * Method to run the MSD Chinese Sort benchmark
     *
     * @param words       an array of available words (to be chosen randomly).
     * @param nWords      the number of words to be sorted.
     * @param nRuns       the number of runs of the sort to be preformed.
     * @param sorter      the sorter to use--NOTE that this sorter will be closed at the end of this method.
     */
    public static void runMSDSortBenchmark(String[] words, int nWords, int nRuns, MSDChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
            sorter.sort(words);
            return null;
        });
        System.out.println("Run MSDChineseSort Benchmark for "+ nRuns + " Mean time: " + mean);
    }

    public static void runTimSortBenchmark(String[] words, int nWords, int nRuns, TimChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
            sorter.sort(words);
            return null;
        });
        System.out.println("Run TimChineseSort Benchmark for "+ nRuns + " Mean time: " + mean);
    }

    public static void runMSDExchangeSortBenchmark(String[] words, int nWords, int nRuns, MSDExchangeChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
            sorter.sort(words);
            return null;
        });
        System.out.println("Run MSDExchangeSort Benchmark for "+ nRuns + " Mean time: " + mean);
    }

    public static void runLSDSortBenchmark(String[] words, int nWords, int nRuns, LSDChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
            sorter.sort(words);
            return null;
        });
        System.out.println("Run LSDSort Benchmark for "+ nRuns + " Mean time: " + mean);
    }

    public static void runDualPivotBenchmark(String[] words, int nWords, int nRuns, DualPivotChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
            sorter.sort(words);
            return null;
        });
        System.out.println("Run DualPivotSort Benchmark for "+ nRuns + " Mean time: " + mean);
    }

    public static void runHuskyMergeBenchmark(String[] words, int nWords, int nRuns, HuskyMergeChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
            sorter.sort(words);
            return null;
        });
        System.out.println("Run HuskyMergeSort Benchmark for "+ nRuns + " Mean time: " + mean);
    }

    private boolean isConfigBoolean(String section, String option) {
        return config.getBoolean(section, option);
    }

    private boolean isConfigBenchmarkStringSorter(String option) {
        return isConfigBoolean("benchmarkstringsorters", option);
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

}
