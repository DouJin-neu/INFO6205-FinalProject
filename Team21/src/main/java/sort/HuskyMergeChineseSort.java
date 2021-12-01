package sort;

import java.util.Arrays;
import sort.utils.LazyLogger;
import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;


/**
 * This class represents the purest form of Husky Sort based on IntroSort for pass 1 and the System sort for pass 2.
 * <p>
 * CONSIDER redefining all of the "to" parameters to be consistent with our other Sort utilities.
 *
 */
public class HuskyMergeChineseSort<X extends Comparable<X>>{
    private final MSDCoder<X> msdCoder;
    private static final int cutoff = 8;


    public static void main(final String[] args) {

        final int N = 50000;
        final int m = 10000;
        final boolean preSorted = args.length > 0 && Boolean.parseBoolean(args[0]);
        final String inputOrder = preSorted ? "ordered" : "random";
        logger.info("MergeHuskySort: sorting " + N + " " + inputOrder + " alphabetic ASCII words " + m + " times");
        final HuskyMergeChineseSort<String> sorter = new HuskyMergeChineseSort<>(MSDCoderFactory.englishCoder);
        String[] a = new String[]{"安", "爱","埃", "张", "公","测试","毕安心","边心","边防","边","边防军","毕竟","毕凌霄","边防站", "毕安", "毕福剑"};
        sorter.sort(a);
        for (String s : a) {
            System.out.println(s);
        }
    }


    /**
     * precess array xs
     * @param xs
     * @return
     */
    public long[] preProcess(final X[] xs){
        final long[] longs = msdCoder.msdEncodeToNumber(xs,'a');
        return longs;
    }

    /**
     * The main sort method.
     * This version of merge sort has three improvements over the basic HuskySort/MergeSort scheme:
     * <ul>
     *     <li>Insertion sort cutoff</li>
     *     <li>Insurance check for all right-hand partition larger than all left-hand partition.</li>
     *     <li>Avoidance of copying between the arrays (other than the sort method itself).</li>
     * </ul>
     *
     * @param xs the array to be sorted.
     */
    public void sort(final X[] xs) {
        //todo switch english charactor to longs
        //todo test, read paper
        // NOTE: First pass where we code to longs and sort according to those.
//        final Coding coding = huskyCoder.huskyEncode(xs);
        final long[] longs = msdCoder.msdEncodeToNumber(xs,'a');
        final int n = xs.length;
        final X[] xsCopy = Arrays.copyOf(xs, n);
        final long[] longsCopy = Arrays.copyOf(longs, n);
        mergeSort(longsCopy, xsCopy, longs, xs, 0, n);
    }


    public HuskyMergeChineseSort(final MSDCoder<X> msdCoder) {
        this.msdCoder =  msdCoder;
    }

    /**
     * Merge-sort the lsSortable/xsSortable arrays using the provided auxiliary arrays.
     *
     * @param lsSortable the longs which will actually be used for the comparisons.
     * @param xsSortable the Xs which will be moved collaterally along with their corresponding longs.
     * @param lsAux      the auxiliary long array (will be a copy of lsSortable) on entry.
     * @param xsAux      the auxiliary X array (will be a copy of xsSortable) on entry.
     * @param from       the index from which to begin sorting.
     * @param to         the index of the first element not to be sorted.
     */
    public void mergeSort(final long[] lsSortable, final X[] xsSortable, final long[] lsAux,
        final X[] xsAux, final int from, final int to) {
        @SuppressWarnings("UnnecessaryLocalVariable") final int lo = from;
        if (to <= lo + cutoff) {
            insertionSort(xsAux, lsAux, from, to);
            return;
        }
        final int mid = from + (to - from - 1) / 2;
        mergeSort(lsAux, xsAux, lsSortable, xsSortable, lo, mid);
        mergeSort(lsAux, xsAux, lsSortable, xsSortable, mid, to);
        merge(xsSortable, xsAux, lsSortable, lsAux, lo, mid, to - 1);
    }

    /**
     * Merge the sorted arrays xsOrdered and lsOrdered and place the result into xsDst and lsDst.
     *
     * @param xsOrdered the X array that is ordered in each of two partitions.
     * @param xsDst     the X array which will be fully ordered on return.
     * @param lsOrdered the long array that is ordered in each of two partitions.
     * @param lsDst     the long array which will be fully ordered on return.
     * @param lo        the first index.
     * @param mid       the mid-point index.
     * @param hi        the high index.
     */
    private void merge(final X[] xsOrdered, final X[] xsDst, final long[] lsOrdered, final long[] lsDst, final int lo, final int mid, final int hi) {
        // Insurance check: if everything in high partition is larger than everything in low partition, just return.
        if (lsOrdered[mid] > lsOrdered[mid - 1]) return;
        int i = lo;
        int j = mid;
        int k = lo;
        for (; k <= hi; k++){
            if (i >= mid) copy(xsOrdered, lsOrdered, xsDst, lsDst, j++, k);
            else if (j > hi) copy(xsOrdered, lsOrdered, xsDst, lsDst, i++, k);
            else if (lsOrdered[j] < lsOrdered[i]) {
                copy(xsOrdered, lsOrdered, xsDst, lsDst, j++, k);
            } else copy(xsOrdered, lsOrdered, xsDst, lsDst, i++, k);
        }

//        while (i<mid&&j<=hi){
//            if (lsOrdered[j] < lsOrdered[i]) {
//                copy(xsOrdered, lsOrdered, xsDst, lsDst, j++, k++);
//            } else copy(xsOrdered, lsOrdered, xsDst, lsDst, i++, k++);
//        }
//
//        while (i<mid){
//            copy(xsOrdered, lsOrdered, xsDst, lsDst, i++, k++);
//        }
//
//        while (j<=hi){
//            copy(xsOrdered, lsOrdered, xsDst, lsDst, j++, k++);
//        }


    }

    // TEST
    private void insertionSort(final X[] xs, final long[] ls, final int from, final int to) {
        for (int i = from + 1; i < to; i++)
            for (int j = i; j > from && ls[j] < ls[j - 1]; j--)
                swap(xs, ls, j, j - 1);
    }

    private void swap(final X[] xs, final long[] ls, final int i, final int j) {
        // Swap ls
        final long temp1 = ls[i];
        ls[i] = ls[j];
        ls[j] = temp1;
//        System.out.println(i+" "+j);
        // Swap xs
        final X temp2 = xs[i];
        xs[i] = xs[j];
        xs[j] = temp2;
        //System.out.println(i+" "+j);
    }

    private void copy(final X[] xsFrom, final long[] lsFrom, final X[] xsTo, final long[] lsTo, final int i, final int j) {
        xsTo[j] = xsFrom[i];
        lsTo[j] = lsFrom[i];
    }

    private static String[] getAlphaBetaArrayOrdered(final int n) {
        final String[] strings = new String[n];
        int m = 0;
        for (int i = 0; i < 26; i++)
            for (int j = 0; j < 26; j++)
                for (int k = 0; k < 26; k++)
                    for (int l = 0; l < 26; l++)
                        if (m < n)
                            strings[m++] = "" + (char) ('A' + i) + (char) ('A' + j) + (char) ('A' + k) + (char) ('A' + l);
        return strings;
    }


    private final static LazyLogger logger = new LazyLogger(HuskyMergeChineseSort.class);
}
