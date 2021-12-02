package sort;

import edu.neu.coe.info6205.graphs.BFS_and_prims.StdRandom;
import java.util.Arrays;
import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;

// Java program to implement
// dual pivot QuickSort
public class QuickDualPivot<X extends Comparable<X>> {
  public QuickDualPivot(final MSDCoder<X> msdCoder) {
    this.msdCoder =  msdCoder;
  }

  private final MSDCoder<X> msdCoder;

  public static void main(String[] args) {
    QuickDualPivot<String> sorter = new QuickDualPivot<String>(MSDCoderFactory.englishCoder);
    String[] a = new String[]{"安", "爱","埃", "张", "公","测试","毕安心","边心","边防","边","边防军","毕竟","毕凌霄","边防站", "毕安", "毕福剑"};
//    sorter.sort(a);
//    for (String s : a) {
//      System.out.println(s);
//    }
    sorter.sort(a);
    show(a);

  }

  public long[] preProcess(final X[] xs){
    final long[] longs = msdCoder.msdEncodeToNumber(xs,'a');
    return longs;
  }

  // quicksort the array a[] using dual-pivot quicksort
  public void sort(X[] xs) {
//    StdRandom.shuffle(a);
    String[] a = msdCoder.msdEncode(xs);
    sort(a, xs,0, a.length - 1);
  }

  // quicksort the subarray a[lo .. hi] using dual-pivot quicksort
  public void sort(String[] a,X[] xs, int lo, int hi) {
    if (hi <= lo) return;

    // make sure a[lo] <= a[hi]
    if (less(a[hi], a[lo])) {
      exch(a, lo, hi);
      exch(xs, lo, hi);
    }

    int lt = lo + 1, gt = hi - 1;
    int xlt = lo + 1, xgt = hi - 1;
    int i = lo + 1;
    int xi = lo + 1;
    while (i <= gt) {
      if       (less(a[i], a[lo])){
        exch(a, lt++, i++);
        exch(xs, xlt++, xi++);
      }
      else if  (less(a[hi], a[i])) {
        exch(a, i, gt--);
        exch(xs, xi, xgt--);
      }
      else {
        i++;
        xi++;
      }
    }
    exch(a, lo, --lt);
    exch(xs, lo, --xlt);
    exch(a, hi, ++gt);
    exch(xs, hi, ++xgt);

    // recursively sort three subarrays
    sort(a,xs, lo, lt-1);
    if (less(a[lt], a[gt])) sort(a,xs, lt+1, gt-1);
    sort(a,xs, gt+1, hi);

  }



  /***************************************************************************
   *  Helper sorting functions.
   ***************************************************************************/

  // is v < w ?
  private boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  // exchange a[i] and a[j]
  private void exch(Object[] a, int i, int j) {
    Object swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }



  // print array to standard output
  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      System.out.println(a[i]);
    }
  }
  

}
