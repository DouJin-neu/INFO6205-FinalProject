package sort;

import static benchmark.SortBenchmarkHelper.getWords;
import static sort.utils.Utilities.writeToFile;

import benchmark.SortBenchmark;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;

public class DualPivotChineseSort<X extends Comparable<X>> {

  public DualPivotChineseSort(final MSDCoder<X> msdCoder) {
    this.msdCoder =  msdCoder;
  }

  private final MSDCoder<X> msdCoder;


  public static void main(String[] args) {
    final int N = 50000;
    final int m = 10000;
    final boolean preSorted = args.length > 0 && Boolean.parseBoolean(args[0]);
    DualPivotChineseSort<String> sorter = new DualPivotChineseSort<String>(MSDCoderFactory.englishCoder);
//    String[] a = new String[]{"安", "爱","埃", "张", "公","测试","毕安心","边心","边防","边","边防军","毕竟","毕凌霄","边防站", "毕安", "毕福剑"};
//    sorter.sort(a);
//    for (String s : a) {
//      System.out.println(s);
//    }
    String[] words1 = getWords("shuffledChinese250K.txt", SortBenchmark::lineAsList);

    long startTime1 = System.currentTimeMillis();
    sorter.sort(words1);
    long endTime1 = System.currentTimeMillis();

    long time1 = (endTime1- startTime1);
    System.out.println(time1);

    long time;
    List<String> resources = new ArrayList<>();
    resources.add("shuffledChinese250K.txt");
    resources.add("shuffledChinese500K.txt");
    resources.add("shuffledChinese1M.txt");
    resources.add("shuffledChinese2M.txt");
    resources.add("shuffledChinese4M.txt");

      for(int i=0;i<resources.size();i++){
        String[] words = getWords(resources.get(i), SortBenchmark::lineAsList);
        long startTime = System.currentTimeMillis();
//        for (int t = 0; t < 10; t++) {
            sorter.sort(words);
//        }
      long endTime = System.currentTimeMillis();
      time = (endTime - startTime);
//      long mean = time/10;
      long mean = time;
      writeToFile(words.length+","+mean+"","DualPivotChineseSort.csv",true);
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

  public void sort(final X[] xs) {
    //todo test, read paper
    final long[] longs = msdCoder.msdEncodeToNumber(xs,'a');

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

        /* * Here and below we use "a[i] = b; i--;" instead
         * of "a[i--] = b;" due to performance issue.*/

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
