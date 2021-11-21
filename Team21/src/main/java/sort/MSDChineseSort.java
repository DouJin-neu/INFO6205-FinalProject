package sort;

import edu.neu.coe.info6205.sort.elementary.InsertionSortMSD;

import java.util.Arrays;
import sort.helper.ChineseCharacterNode;
import sort.helper.ChineseSortHelper;
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
        for (int i = 0; i < m; i++)
            if (preSorted)
                // This should take about 20 seconds
                sorter.sort(ChineseSortHelper.generateRandomChineseArray(3,N,m));

    }


    public MSDChineseSort(final MSDCoder<X> huskyCoder) {
        this.huskyCoder =  huskyCoder;

    }


    private final MSDCoder<X> huskyCoder;

    public void sort(final X[] xs) {
        // NOTE: First pass where we code to longs and sort according to those.
       String[] longs = huskyCoder.huskyEncode(xs);
        final int n = xs.length;
        final X[] xsCopy = Arrays.copyOf(xs, n);
        String[] longsCopy = Arrays.copyOf(longs, n);
        sort(longsCopy, xsCopy, longs, xs, 0, n-1,0);
    }

    private void sort(String[] a, X[] xs, String[] aux, X[] auXS, int lo, int hi,int d) {
        if (hi < lo + cutoff) InsertionSortMSD.sort(a, lo, hi, d);
        else {
            int[] count = new int[radix + 2];        // Compute frequency counts.
            for (int i = lo; i < hi; i++)
                count[charAt(a[i], d) + 2]++;
            for (int r = 0; r < radix + 1; r++)      // Transform counts to indices.
                count[r + 1] += count[r];
            for (int i = lo; i < hi; i++) {     // Distribute.
                aux[count[charAt(a[i], d) + 1]++] = a[i];
                auXS[count[charAt(a[i], d) + 1]++] = xs[i];
            }
            // Copy back.
            if (hi - lo >= 0) {
                System.arraycopy(aux, 0, a, lo, hi - lo);
                System.arraycopy(auXS, 0, xs, lo, hi - lo);
            }
            // Recursively sort for each character value.
            // TO BE IMPLEMENTED
            for(int r = 0; r < radix+1; r++){
                sort(a,xs,aux,auXS, lo+count[r], lo+count[r+1]-1, d+1);
            }
        }
    }


    @Override
    public Object[] preProcess(Object[] xs) {
        ChineseCharacterNode[] processXs = new ChineseCharacterNode[xs.length];
        int i=0;
        for(Object s:xs){
            processXs[i++] = new ChineseCharacterNode(s.toString());
        }

        return processXs;
    }

  

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }

    private static final int radix = 256;
    private static final int cutoff = 15;
    private  X[] aux;       // auxiliary array for distribution


}
