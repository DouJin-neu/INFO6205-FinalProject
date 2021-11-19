package neu.edu.coe.sort;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class MSDRadixExchangeSort{

  void   radixexchange (String a [], int l, int r, int bit)
  {


    int i = l;
    int j = r;

    if (r <= l || bit > max_bit) return;

    while (i!=j) {
      while (j > i && getBit(a[i], bit) == 0 ) {
        i++;
      }
      while (j > i && getBit(a[j], bit) == 1) {
        j--;
      }
      if(i>=j)break;
      swap(a, i,j);
    }

    if (getBit(a[r], bit) == 0) j++;
    radixexchange(a, l, j-1, bit+1);
    radixexchange(a, j, r, bit+1);
  }


  public void swap(String a[],int i,int j){
    String temp = a[i];
    a[i] = a[j];
    a[j] = temp;

  }

  private int max_bit;

  /* Iterative method to sort file by exchange radix sort */
  public void exchange_radixsort(String a[]){
    for (int i=0;i<a.length;i++){
      max_bit = Math.max(stringToBit(a[i]).length(),max_bit);
    }

    radixexchange(a, 0, a.length-1, 0);
  }



  public String stringToBit(String s){
    s = getPinyin(s);
    byte[] bytes = s.getBytes();
    StringBuilder binary = new StringBuilder();
    for(byte b:bytes){
      int val = b;
      for (int i = 0; i < 8; i++)
      {
        binary.append((val & 128) == 0 ? 0 : 1);
        val <<= 1;
      }
    }

    return binary.toString();
  }
  public int getBit(String s,int pos){
   String binary = stringToBit(s);

    return binary.length()>pos?Integer.valueOf(binary.charAt(pos)+""):0;
  }


  /* String to pinyin */
  public String getPinyin(String content){
    char[] chars = content.toCharArray();
    HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    // 设置大小写
    format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    // 设置声调表示方法
    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    // 设置字母u表示方法
    format.setVCharType(HanyuPinyinVCharType.WITH_V);
    String[] s;
    StringBuilder sb = new StringBuilder();
    try {
      for (int i = 0; i < chars.length; i++) {
        // 判断是否为汉字字符
        if (String.valueOf(chars[i]).matches("[\\u4E00-\\u9FA5]+")) {
          s = PinyinHelper.toHanyuPinyinStringArray(chars[i], format);
          if (s != null) {
            sb.append(s[0]);
            continue;
          }
        }
        sb.append(String.valueOf(chars[i]));
        if ((i + 1 >= chars.length) || String.valueOf(chars[i + 1]).matches("[\\u4E00-\\u9FA5]+")) {
        }
      }
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      e.printStackTrace();
    }

    return sb.toString();
  }


  public static void main(String[] args) {
    MSDRadixExchangeSort test = new MSDRadixExchangeSort();

    String[]  a = new String[]{"安","爱","张","公"};
    for(String s:a){
      System.out.println(s);
    }

    test.exchange_radixsort(a);
    for(String s:a){
      System.out.println(s);

    }


  }






}
