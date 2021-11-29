package sort;

import java.util.Arrays;
import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;

public class DualPivotChineseSort<X extends Comparable<X>> {

  public DualPivotChineseSort(final MSDCoder<X> huskyCoder) {
    this.huskyCoder =  huskyCoder;
  }

  private final MSDCoder<X> huskyCoder;


  public static void main(String[] args) {
    final int N = 50000;
    final int m = 10000;
    final boolean preSorted = args.length > 0 && Boolean.parseBoolean(args[0]);
    final DualPivotChineseSort<String> sorter = new DualPivotChineseSort<>(MSDCoderFactory.englishCoder);
    String[] a = new String[]{"安","爱","埃", "张", "公","测试"};
    sorter.sort(a);
    for (String s : a) {
      System.out.println(s);
    }
  }

  public void sort(final X[] xs) {
    //todo test, read paper
    final long[] longs = huskyCoder.msdEncodeToNumber(xs);
    final int n = xs.length;

    dualPivotQuickSort(longs, xs, 0, n-1);
  }

  public void dualPivotQuickSort(long[] longs, X[] xs,int left, int right) {

    if (left >= right) {

      return;
    }
    if (longs[left] > longs[right]) {

      swap(longs, left, right);
      MSDCoderFactory.swap(xs, left, right);
    }
    int less = left;
    int great = right;
    long pivot1 = longs[left];
    long pivot2 = longs[right];
    while (longs[++less] < pivot1) ;
    while (longs[--great] > pivot2) ;

    /*
     * Partitioning:
     *
     *   left part           center part                   right part
     * +--------------------------------------------------------------+
     * |  < pivot1  |  pivot1 <= && <= pivot2  |    ?    |  > pivot2  |
     * +--------------------------------------------------------------+
     *               ^                          ^       ^
     *               |                          |       |
     *              less                        k     great
     *
     * Invariants:
     *
     *              all in (left, less)   < pivot1
     *    pivot1 <= all in [less, k)     <= pivot2
     *              all in (great, right) > pivot2
     *
     * Pointer k is the first index of ?-part.
     */
    outer:
    for (int k = less - 1; ++k <= great; ) {

      long ak = longs[k];
      X xk = xs[k];
      if (ak < pivot1) {
        // ak 小于 p1
        swap(longs, k, less); // 交换
        MSDCoderFactory.swap(xs, k, less);

        less++;
      } else if (ak > pivot2) {
        // ak > p2
        while (longs[great] > pivot2) {
          // 找到不满足条件的位置
          if (great-- == k) {

            System.out.println("outer");
            break outer;
          }
        }
        if (longs[great] < pivot1) {
          // a[great] <= pivot1，

          longs[k] = longs[less];  // less放到 k的位置,  k 位置的元素数保存在 ak中
          longs[less] = longs[great]; // great 放到less的位置
          xs[k] = xs[less];
          xs[less] = xs[great];
          ++less;  // 更新 less
        } else {
          // pivot1 <= a[great] <= pivot2
          longs[k] = longs[great];
          xs[k] = xs[great];
        }
        /*
         * Here and below we use "a[i] = b; i--;" instead
         * of "a[i--] = b;" due to performance issue.
         */
        longs[great] = ak; // ak 放到 great位置
        xs[great] = xk; // ak 放到 great位置
        --great;
      } // 其他情况就是中间位置，不用考虑
    }

    dualPivotQuickSort(longs,xs, left, less - 1);
    dualPivotQuickSort(longs,xs,  less, great);
    dualPivotQuickSort(longs,xs,  great + 1, right);
  }

  public static void swap(long a[], int i, int j) {
    long temp = a[i];
    a[i] = a[j];
    a[j] = temp;

  }


}
