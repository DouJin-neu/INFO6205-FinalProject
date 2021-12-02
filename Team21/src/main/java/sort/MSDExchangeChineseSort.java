package sort;

import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;

public class MSDExchangeChineseSort<X extends Comparable<X>>{
  private static final int radix = 256;
  private static final int cutoff = 0;

  private final MSDCoder<X> msdCoder;

  private int max_bit;


  public static void main(final String[] args) {

    MSDExchangeChineseSort<String> sorter = new MSDExchangeChineseSort<String>(MSDCoderFactory.bitCoder);
    String[] a = new String[]{"安", "爱","埃", "张", "公","测试","毕安心","比安心","边心","边防","边","边防军","毕竟","毕凌霄","边防站", "毕安", "毕福剑"};
    sorter.sort(a);
    for (String s : a) {
      System.out.println(s);
    }
  }


  public MSDExchangeChineseSort(final MSDCoder<X> msdCoder) {
    this.msdCoder =  msdCoder;
  }


  /**
   * precess array xs
   * @param xs
   * @return
   */
  public String[] preProcess(final X[] xs){
    String[] longs = msdCoder.msdEncode(xs,'0');
    for (int i = 0; i < longs.length; i++) {
      max_bit = Math.max(longs[i].length(), max_bit);
    }
    return longs;
  }

  public void sort(X[] xs) {
    // NOTE: First pass where we code to longs and sort according to those.
    String[] longs = msdCoder.msdEncode(xs,'0');
    for (int i = 0; i < longs.length; i++) {
      max_bit = Math.max(longs[i].length(), max_bit);
    }

    final int n = xs.length;
    sort(longs, xs , 0, n-1,0);
  }

  public void sort(String[] a,X[] xs, int l, int r, int bit) {

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
