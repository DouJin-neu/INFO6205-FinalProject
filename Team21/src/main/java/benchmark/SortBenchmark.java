package benchmark;

import edu.neu.coe.info6205.util.Timer;
import java.util.Collections;
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

import static benchmark.SortBenchmarkHelper.getWords;
import static edu.neu.coe.info6205.util.Utilities.formatWhole;
import static sort.utils.Utilities.writeToFile;

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
        sortBenchmark.sortStrings("shuffledChinese250K.txt");
        sortBenchmark.sortStrings("shuffledChinese500K.txt");
        sortBenchmark.sortStrings("shuffledChinese1M.txt");
        sortBenchmark.sortStrings("shuffledChinese2M.txt");
        sortBenchmark.sortStrings("shuffledChinese4M.txt");

    }

    public void sortStrings(String resource) throws IOException {
        logger.info("Beginning String sorts");

        // NOTE: Leipzig Chinese words benchmarks (according to command-line arguments)
        String[] words = getWords(resource, SortBenchmark::lineAsList);
//        System.out.println(words.length);
        Collections.shuffle(Arrays.asList(words));
        benchmarkStringSorters(words, words.length, 10);

    }

    /**
     * Calculate the appropriate number of warmup runs.
     *
     * @param m the number of runs.
     * @return at least 2 and at most m/10.
     */
    static int getWarmupRuns(int m) {
        return Integer.max(2, Integer.min(10, m / 10));
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

        //Random random = new Random();

        //sort words with certain length
        /*String[] words = new String[nWords];
        for (int i = 0; i < nWords; i++) words[i] =sourceWords[random.nextInt(sourceWords.length)];*/

        //doing benchmarks for different types of sorting algorithms
        try{
            runMSDSortBenchmark(sourceWords, nWords, nRuns, new MSDChineseSort<>(MSDCoderFactory.pinyinCoder));
            runMSDExchangeSortBenchmark(sourceWords, nWords, nRuns, new MSDExchangeChineseSort<>(MSDCoderFactory.bitCoder));
            runLSDSortBenchmark(sourceWords, nWords, nRuns, new LSDChineseSort<>(MSDCoderFactory.pinyinCoder));
            runHuskyMergeBenchmark(sourceWords, nWords, nRuns, new HuskyMergeChineseSort<>(MSDCoderFactory.englishCoder));
            runDualPivotBenchmark(sourceWords, nWords, nRuns, new DualPivotChineseSort<>(MSDCoderFactory.englishCoder));
            runTimSortBenchmark(sourceWords, nWords, nRuns, new TimChineseSort<>(MSDCoderFactory.englishCoder));
        }catch(StackOverflowError e){
            e.printStackTrace();
        }


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
        String[] longs = sorter.preProcess(words);

        String[] xsCopy = Arrays.copyOf(words, nWords);
        String[] longsCopy = Arrays.copyOf(longs, nWords);

        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
//            sorter.sort(words);
            sorter.sort(longsCopy, longs, words,xsCopy , 0, nWords-1,0);
//            sorter.sort( longs, words , 0, nWords-1,0);
            return null;
        });
        System.out.println("Run MSDChineseSort Benchmark for "+ nRuns + " Mean time: " + mean);
        writeToFile(nWords+","+mean,"MSDChineseSort.csv",true);

    }

    public static void runTimSortBenchmark(String[] words, int nWords, int nRuns, TimChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        //preprocess array
        long[] longs = sorter.preProcess(words);

        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
//            sorter.sort(words);
            sorter.timSort(longs, words, nWords);
            return null;
        });
        System.out.println("Run TimChineseSort Benchmark for "+ nRuns + " Mean time: " + mean);
        writeToFile(nWords+","+mean+"","TimChineseSort.csv",true);

    }

    public static void runMSDExchangeSortBenchmark(String[] words, int nWords, int nRuns, MSDExchangeChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        String[] longs =sorter.preProcess(words);

        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
//            sorter.sort(words);
            sorter.sort(longs, words , 0, nWords-1,0);
            return null;
        });
        System.out.println("Run MSDExchangeSort Benchmark for "+ nRuns + " Mean time: " + mean);
        writeToFile(nWords+","+mean+"","MSDExchangeSort.csv",true);

    }

    public static void runLSDSortBenchmark(String[] words, int nWords, int nRuns, LSDChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        String[] longs = sorter.preProcess(words);

        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
//            sorter.sort(words);
            sorter.sort(longs,words, 0, nWords-1);
            return null;
        });
        System.out.println("Run LSDSort Benchmark for "+ nRuns + " Mean time: " + mean);
        writeToFile(nWords+","+mean+"","LSDSort.csv",true);
    }

    public static void runDualPivotBenchmark(String[] words, int nWords, int nRuns, DualPivotChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        long[] longs =sorter.preProcess(words);
        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
//            sorter.sort(words);
            sorter.dualPivotQuickSort(longs, words, 0, nWords-1);
            return null;
        });
        System.out.println("Run DualPivotSort Benchmark for "+ nRuns + " Mean time: " + mean);
        writeToFile(nWords+","+mean+"","DualPivotSort.csv",true);
    }

    public static void runHuskyMergeBenchmark(String[] words, int nWords, int nRuns, HuskyMergeChineseSort<String> sorter) {
        final Timer timer = new Timer();
        final int zzz = 20;
        long[] longs = sorter.preProcess(words);
        final String[] xsCopy = Arrays.copyOf(words, nWords);
        final long[] longsCopy = Arrays.copyOf(longs, nWords);

        final double mean = timer.repeat(nRuns, () -> zzz, t-> {
//            sorter.sort(words);
            sorter.mergeSort(longsCopy, xsCopy, longs, words, 0, nWords);
            return null;
        });
        System.out.println("Run HuskyMergeSort Benchmark for "+ nRuns + " Mean time: " + mean);
        writeToFile(nWords+","+mean+"","HuskyMergeSort.csv",true);
    }

    private boolean isConfigBoolean(String section, String option) {
        return config.getBoolean(section, option);
    }

    private boolean isConfigBenchmarkStringSorter(String option) {
        return isConfigBoolean("benchmarkstringsorters", option);
    }

   public static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

}
