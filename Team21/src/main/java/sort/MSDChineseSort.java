package sort;

import edu.neu.coe.info6205.sort.elementary.InsertionSortMSD;
import java.util.Arrays;
import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;

/**
 * This class represents the purest form of Husky Sort based on IntroSort for pass 1 and the System sort for pass 2.
 * <p>
 * CONSIDER redefining all of the "to" parameters to be consistent with our other Sort utilities.
 *
 */
public class MSDChineseSort<X extends Comparable<X>>{


    public static void main(final String[] args) {

        final int N = 50000;
        final int m = 10000;
        final boolean preSorted = args.length > 0 && Boolean.parseBoolean(args[0]);
        final String inputOrder = preSorted ? "ordered" : "random";
         MSDChineseSort<String> sorter = new MSDChineseSort<String>(MSDCoderFactory.pinyinCoder);
        String[] a = new String[]{"安","爱","埃", "张", "公","测试"};
        sorter.sort(a);
        for (String s : a) {
            System.out.println(s);
        }
    }

    public MSDChineseSort(final MSDCoder<X> huskyCoder) {
        this.huskyCoder =  huskyCoder;
    }


    private final MSDCoder<X> huskyCoder;

    public void sort(X[] xs) {
        // NOTE: First pass where we code to longs and sort according to those.
       String[] longs = huskyCoder.huskyEncode(xs);
        final int n = xs.length;
         X[] xsCopy = Arrays.copyOf(xs, n);
        String[] longsCopy = Arrays.copyOf(longs, n);
        sort(longsCopy, longs, xs,xsCopy , 0, n-1,0);
    }

    private void sort(String[] a,String[] aux,X[] xs,X[]auXs, int lo, int hi, int d) {

        if (hi <= lo + cutoff)
        {  InsertionSortMSD.sort(a, lo, hi, d); return;  }
        int[] count = new int[radix+2];        // Compute frequency counts.
        int[] count2 = new int[radix + 2];        // Compute frequency counts.

        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i], d) + 2]++;
            count2[charAt(a[i], d) + 2]++;
        }

        for (int r = 0; r < radix+1; r++) {      // Transform counts to indices.
            count[r + 1] += count[r];
            count2[r + 1] += count2[r];
        }
        for (int i = lo; i <= hi; i++) {     // Distribute.
            aux[count[charAt(a[i], d) + 1]++] = a[i];
            auXs[count2[charAt(a[i], d) + 1]++] = xs[i];
        }
        for (int i = lo; i <= hi; i++) {     // Copy back.
            a[i] = aux[i - lo];
            xs[i] = auXs[i-lo];
        }
        // Recursively sort for each character value.
        for (int r = 0; r < radix; r++) {
            sort(a,aux,xs,auXs, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }


    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }

    private static final int radix = 256;
    private static final int cutoff = 0;

}
