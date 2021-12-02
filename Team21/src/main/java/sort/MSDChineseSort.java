package sort;

import elementary.InsertionSortMSD;
import java.util.Arrays;
import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;


public class MSDChineseSort<X extends Comparable<X>>{


    public static void main(final String[] args) {

         MSDChineseSort<String> sorter = new MSDChineseSort<String>(MSDCoderFactory.pinyinCoder);
        String[] a = new String[]{"安", "爱","埃", "张", "公","测试","毕安心","边心","边防","边","边防军","毕竟","毕凌霄","边防站", "毕安", "毕福剑"};
        sorter.sort(a);
        for (String s : a) {
            System.out.println(s);
        }
    }

    public MSDChineseSort(final MSDCoder<X> msdCoder) {
        this.msdCoder =  msdCoder;
    }

    private InsertionSortMSD insertionSortMSD = new InsertionSortMSD();
    private final MSDCoder<X> msdCoder;

    /**
     * precess array xs
     * @param xs
     * @return
     */
    public String[] preProcess(final X[] xs){
        String[] longs = msdCoder.msdEncode(xs,'a');
        return longs;
    }

    public void sort(X[] xs) {
        // NOTE: First pass where we code to longs and sort according to those.
       String[] longs = msdCoder.msdEncode(xs,'a');
        final int n = xs.length;
         X[] xsCopy = Arrays.copyOf(xs, n);
        String[] longsCopy = Arrays.copyOf(longs, n);
        sort(longsCopy, longs, xs,xsCopy , 0, n-1,0);
    }

    public void insertionSort(long[] arr, X[] xs, int left,
        int right) {
        for (int i = left + 1; i <= right; i++) {
            long temp = arr[i];
            X tempXs = xs[i];
            int j = i - 1;
            while (j >= left && arr[j] > temp) {
                arr[j + 1] = arr[j];
                xs[j + 1] = xs[j];
                j--;
            }
            arr[j + 1] = temp;
            xs[j + 1] = tempXs;
        }
    }

    public void sort(String[] a,String[] aux,X[] xs,X[]auXs, int lo, int hi, int d) {
        if(hi<=lo)return;
        if (hi <= lo + cutoff)
        {  insertionSortMSD.sort(a,xs, lo, hi, d); return;  }
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

        // copy back
        if (lo <= hi) {
            System.arraycopy(aux, 0, a, lo, hi + 1 - lo);
            System.arraycopy(auXs, 0, xs, lo, hi + 1 - lo);
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
    private static final int cutoff = 32;

}
