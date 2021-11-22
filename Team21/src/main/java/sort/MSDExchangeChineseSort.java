package sort;

import static sort.utils.MSDCoderFactory.charAt;

import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import java.util.Arrays;
import sort.utils.HuskySortable;
import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;

public class MSDExchangeChineseSort<X extends Comparable<X>>{
  private static final int radix = 256;
  private static final int cutoff = 0;

  private final MSDCoder<X> huskyCoder;

  private int max_bit;


  public static void main(final String[] args) {

    final int N = 50000;
    final int m = 10000;
    final boolean preSorted = args.length > 0 && Boolean.parseBoolean(args[0]);
    final String inputOrder = preSorted ? "ordered" : "random";
    MSDExchangeChineseSort<String> sorter = new MSDExchangeChineseSort<String>(MSDCoderFactory.bitCoder);
    String[] a = new String[]{"安","埃", "爱", "张", "公","测试"};
    sorter.sort(a);
    for (String s : a) {
      System.out.println(s);
    }
  }


  public MSDExchangeChineseSort(MSDCoder<X> huskyCoder) {
    super();
    this.huskyCoder = huskyCoder;
  }


  public void sort(X[] xs) {
    // NOTE: First pass where we code to longs and sort according to those.
    String[] longs = huskyCoder.huskyEncode(xs);
    for (int i = 0; i < longs.length; i++) {
      max_bit = Math.max(longs[i].length(), max_bit);
    }

    final int n = xs.length;
    sort(longs, xs , 0, n-1,0);
  }

  private void sort(String[] a,X[] xs, int l, int r, int bit) {

    int i = l;
    int j = r;

    if (r <= l || bit > max_bit) {
      return;
    }

    while (i != j) {
      while (j > i && getBit(a[i], bit) == 0) {
        i++;
      }
      while (j > i && getBit(a[j], bit) == 1) {
        j--;
      }
      if (i >= j) {
        break;
      }
      MSDCoderFactory.swap(a, i, j);
      MSDCoderFactory.swap(xs, i, j);
    }

    if (getBit(a[r], bit) == 0) {
      j++;
    }
    sort(a,xs, l, j - 1, bit + 1);
    sort(a,xs, j, r, bit + 1);

  }



  public int getBit(String s, int pos) {

    return s.length() > pos ? Integer.valueOf(s.charAt(pos) + "") : 0;
  }


}
