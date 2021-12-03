package sort;

import benchmark.SortBenchmarkHelper;
import org.junit.Test;
import sort.utils.LazyLogger;
import sort.utils.MSDCoderFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class SortedFileTest {
    final static LazyLogger logger = new LazyLogger(SortedFileTest.class);
    private SortBenchmarkHelper helper = new SortBenchmarkHelper();
    String[] words = helper.getWords("shuffledChinese1M.txt", SortedFileTest::lineAsList);

    @Test
    public void getSortedFile_pinyin(){
        logger.info("Get sorted names in Pinyin order");

        final MSDChineseSort<String> sorter = new MSDChineseSort<>(MSDCoderFactory.pinyinCoder);
        sorter.sort(words);

        try {
            FileOutputStream fis = new FileOutputStream("./src/result_pinyinOrder.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            for (String word : words) {
                String content = word + "\n";
                bw.write(content);
                bw.flush();
            }
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSortedFile_bitCode(){
        logger.info("Get sorted names in bit code order");

        final MSDExchangeChineseSort<String> sorter = new MSDExchangeChineseSort<>(MSDCoderFactory.bitCoder);
        sorter.sort(words);

        try {
            FileOutputStream fis = new FileOutputStream("./src/result_bitCodeOrder.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            for (String word : words) {
                String content = word + "\n";
                bw.write(content);
                bw.flush();
            }
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSortedFile_english(){
        logger.info("Get sorted names in english coder order");

        final QuickDualPivot<String> sorter = new QuickDualPivot<>(MSDCoderFactory.englishCoder);
        sorter.sort(words);

        try {
            FileOutputStream fis = new FileOutputStream("./src/result_englishCodeOrder.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            for (String word : words) {
                String content = word + "\n";
                bw.write(content);
                bw.flush();
            }
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
