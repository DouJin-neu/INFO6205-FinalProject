package sort;

import java.util.Arrays;
import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;

public class TimChineseSort<X extends Comparable<X>> {

  //todo change to 32
  private static final int MIN_MERGE = 2;


  public int minRunLength(int n)
  {
    assert n >= 0;

    // Becomes 1 if any 1 bits are shifted off
    int r = 0;
    while (n >= MIN_MERGE)
    {
      r |= (n & 1);
      n >>= 1;
    }
    return n + r;
  }

  // This function sorts array from left index to
  // to right index which is of size atmost RUN
  public void insertionSort(long[] arr,X[] xs, int left,
      int right)
  {
    for (int i = left + 1; i <= right; i++)
    {
      long temp = arr[i];
      X tempXs = xs[i];
      int j = i - 1;
      while (j >= left && arr[j] > temp)
      {
        arr[j + 1] = arr[j];
        xs[j + 1] = xs[j];
        j--;
      }
      arr[j + 1] = temp;
      xs[j + 1] = tempXs;
    }
  }

  // Merge function merges the sorted runs
  public void merge(long[] arr,X[] xs, int l,
      int m, int r)
  {
    // Original array is broken in two parts
    // left and right array
    int len1 = m - l + 1, len2 = r - m;
    long[] left = new long[len1];
    long[] right = new long[len2];
    X[] leftXs = Arrays.copyOf(xs,len1);
    X[] rightXs = Arrays.copyOf(xs,len2);
    Arrays.fill(leftXs,null);
    Arrays.fill(rightXs,null);

    for (int x = 0; x < len1; x++)
    {
      left[x] = arr[l + x];
      leftXs[x] = xs[l + x];
    }
    for (int x = 0; x < len2; x++)
    {
      right[x] = arr[m + 1 + x];
      rightXs[x] = xs[m + 1 + x];
    }

    int i = 0;
    int j = 0;
    int k = l;

    // After comparing, we merge those two array
    // in larger sub array
    while (i < len1 && j < len2)
    {
      if (left[i] <= right[j])
      {
        arr[k] = left[i];
        xs[k] = leftXs[i];
        i++;
      }
      else {
        arr[k] = right[j];
        xs[k] = rightXs[j];
        j++;
      }
      k++;
    }

    // Copy remaining elements
    // of left, if any
    while (i < len1)
    {
      arr[k] = left[i];
      xs[k] = leftXs[i];
      k++;
      i++;
    }

    // Copy remaining element
    // of right, if any
    while (j < len2)
    {
      arr[k] = right[j];
      xs[k] = rightXs[j];
      k++;
      j++;
    }
  }

  // Iterative Timsort function to sort the
  // array[0...n-1] (similar to merge sort)
  public void timSort(long[] arr,X[]xs, int n)
  {
    int minRun = minRunLength(MIN_MERGE);

    // Sort individual subarrays of size RUN
    for (int i = 0; i < n; i += minRun)
    {
      insertionSort(arr,xs, i,
          Math.min((i + MIN_MERGE - 1), (n - 1)));
    }

    // Start merging from size
    // RUN (or 32). It will
    // merge to form size 64,
    // then 128, 256 and so on
    // ....
    for (int size = minRun; size < n; size = 2 * size)
    {

      // Pick starting point
      // of left sub array. We
      // are going to merge
      // arr[left..left+size-1]
      // and arr[left+size, left+2*size-1]
      // After every merge, we
      // increase left by 2*size
      for (int left = 0; left < n;
          left += 2 * size)
      {

        // Find ending point of left sub array
        // mid+1 is starting point of right sub
        // array
        int mid = left + size - 1;
        int right = Math.min((left + 2 * size - 1),
            (n - 1));

        // Merge sub array arr[left.....mid] &
        // arr[mid+1....right]
        if(mid < right)
          merge(arr,xs, left, mid, right);
      }
    }
  }

  public TimChineseSort(final MSDCoder<X> msdCoder) {
    this.msdCoder =  msdCoder;
  }


  private final MSDCoder<X> msdCoder;

  /**
   * precess array xs
   * @param xs
   * @return
   */
  public long[] preProcess(final X[] xs){
    final long[] longs = msdCoder.msdEncodeToNumber(xs,'A');
    return longs;
  }

  public void sort(final X[] xs) {
    //todo switch english charactor to longs
    //todo test, read paper
    // NOTE: First pass where we code to longs and sort according to those.
//        final Coding coding = huskyCoder.huskyEncode(xs);
    final long[] longs = msdCoder.msdEncodeToNumber(xs,'A');
    final int n = xs.length;
    timSort(longs, xs, n);

  }

  // Driver code
  public static void main(String[] args)
  {
    final int N = 50000;
    final int m = 10000;
    final boolean preSorted = args.length > 0 && Boolean.parseBoolean(args[0]);
    final String inputOrder = preSorted ? "ordered" : "random";
    TimChineseSort<String> sorter = new TimChineseSort<String>(MSDCoderFactory.englishCoder);
    String[] a = new String[]{"安", "爱","埃", "张", "公","测试","毕安心","边心","边防","边","边防军","毕竟","毕凌霄","边防站", "毕安", "毕福剑"};
//    String[] input = new String[]{"安","爱","埃", "张", "公","测试", "边防站", "毕安", "毕福剑"};
    sorter.sort(a);
    for (String s : a) {
      System.out.println(s);
    }
  }
}

