package sort;

import benchmark.SortBenchmarkHelper;
import org.junit.Test;
import sort.utils.LazyLogger;
import sort.utils.MSDCoderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SortedFileTest {
    final static LazyLogger logger = new LazyLogger(SortedFileTest.class);
    private SortBenchmarkHelper helper = new SortBenchmarkHelper();
    String[] words = helper.getWords("shuffledChinese1M.txt", SortedFileTest::lineAsList);

    @Test
    public void getSortedFile_MSDSort(){
        logger.info("Get sorted names with MSDSort");

        final MSDChineseSort<String> sorter = new MSDChineseSort<>(MSDCoderFactory.pinyinCoder);
        sorter.sort(words);

        try {
            FileOutputStream fis = new FileOutputStream("./src/sortResults/result_msdSort.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            for (int i = 0; i < 500; i++) {
                String content = words[i] + "\n";
                bw.write(content);
                bw.flush();
            }
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSortedFile_MSDExchangeSort(){
        logger.info("Get sorted names with MSDExchangeSort");

        final MSDExchangeChineseSort<String> sorter = new MSDExchangeChineseSort<>(MSDCoderFactory.bitCoder);
        sorter.sort(words);

        try {
            FileOutputStream fis = new FileOutputStream("./src/sortResults/result_msdExchangeSort.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            for (int i = 0; i < 500; i++) {
                String content = words[i] + "\n";
                bw.write(content);
                bw.flush();
            }
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSortedFile_LSDSort(){
        logger.info("Get sorted names with MSDExchangeSort");

        final LSDChineseSort<String> sorter = new LSDChineseSort<>(MSDCoderFactory.pinyinCoder);
        sorter.sort(words);

        try {
            FileOutputStream fis = new FileOutputStream("./src/sortResults/result_lsdSort.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            for (int i = 0; i < 500; i++) {
                String content = words[i] + "\n";
                bw.write(content);
                bw.flush();
            }
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSortedFile_DualPivot(){
        logger.info("Get sorted names with DualPivotSort");

        final DualPivotChineseSort<String> sorter = new DualPivotChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(words);

        try {
            FileOutputStream fis = new FileOutputStream("./src/sortResults/result_dualPivot.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            for (int i = 0; i < 500; i++) {
                String content = words[i] + "\n";
                bw.write(content);
                bw.flush();
            }
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSortedFile_huskyMergeSort(){
        logger.info("Get sorted names with DualPivotSort");

        final HuskyMergeChineseSort<String> sorter = new HuskyMergeChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(words);

        try {
            FileOutputStream fis = new FileOutputStream("./src/sortResults/result_huskyMerge.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            for (int i = 0; i < 500; i++) {
                String content = words[i] + "\n";
                bw.write(content);
                bw.flush();
            }
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSortedFile_TimSort(){
        logger.info("Get sorted names with DualPivotSort");

        final TimChineseSort<String> sorter = new TimChineseSort<>(MSDCoderFactory.englishCoder);
        sorter.sort(words);

        try {
            FileOutputStream fis = new FileOutputStream("./src/sortResults/result_timSort.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            for (int i = 0; i < 500; i++) {
                String content = words[i] + "\n";
                bw.write(content);
                bw.flush();
            }
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeToFileTest(){
        String fileName = "Test";
        String content = "new test";
        try {
            File file = new File("./src/benchmarkResultsFiles/" + fileName);
            if(!file.exists()){
                content="Number,Time\n"+content;
            }
            FileOutputStream fis = new FileOutputStream("./src/benchmarkResultsFiles/" + fileName, true);
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);

            bw.write(content + "\n");
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }
}
