package benchmark;

import org.junit.Test;
import sort.LSDChineseSort;
import sort.LSDChineseSortTest;
import sort.utils.MSDCoderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SortBenchmarkHelperTest {

    @Test
    public void getFileTest() throws IOException {
        int n = 41;
        SortBenchmarkHelper helper = new SortBenchmarkHelper();
        String[] words = helper.getWords("shuffledChineseTest.txt", SortBenchmarkHelperTest::lineAsList);
        for(String word: words){
            System.out.print(word+", ");
        }
        assertEquals(n, words.length);

    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

}
