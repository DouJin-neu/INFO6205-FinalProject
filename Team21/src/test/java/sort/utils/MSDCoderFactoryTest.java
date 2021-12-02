/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package sort.utils;


import static junit.framework.TestCase.assertEquals;
import static sort.utils.MSDCoderFactory.bytesToString;
import static sort.utils.MSDCoderFactory.pinyinToSameLength;

import org.junit.Test;

/**
 * Factory class for MSDCoders.
 */
public final class MSDCoderFactoryTest {

  @Test
  public void testEnglishCoder() {
    MSDCoder<String> coder = MSDCoderFactory.englishCoder;
    assertEquals(0x0840000000000000L, coder.msdEncodeToNumber("a"));
    assertEquals(0x0880000000000000L, coder.msdEncodeToNumber("b"));
    assertEquals(0x0040000000000000L, coder.msdEncodeToNumber("A"));
    assertEquals(0x0080000000000000L, coder.msdEncodeToNumber("B"));
  }

  @Test
  public void testBitCoder() {
    MSDCoder<String> coder = MSDCoderFactory.bitCoder;
    assertEquals(bytesToString("shangD,xiaD,siD,fangA".getBytes()), coder.msdEncode("上下四方"));
    assertEquals(bytesToString("yuC".getBytes()), coder.msdEncode("宇"));
    assertEquals(bytesToString("guC,wangC,jinA,laiB".getBytes()), coder.msdEncode("古往今来"));
    assertEquals(bytesToString("zhouD".getBytes()), coder.msdEncode("宙"));
  }


  @Test
  public void testPinyinCoder() {
    MSDCoder<String> coder = MSDCoderFactory.pinyinCoder;
    assertEquals("shiD,zheC,ruB,siA", coder.msdEncode("逝者如斯"));
    assertEquals("buD,sheD,zhouD,yeD", coder.msdEncode("不舍昼夜"));
    assertEquals("luD,manD,manD", coder.msdEncode("路漫漫"));
    assertEquals("qiB,xiuA,yuanC,xiA", coder.msdEncode("其修远兮"));
  }

  @Test
  public void testPinyinToSameLength(){
    MSDCoder<String> coder = MSDCoderFactory.pinyinCoder;
    String[] words = "帝子 降兮 北渚".split(" ");
    String[] results = pinyinToSameLength(coder.msdEncode(words));

    for(String word:results){
      assertEquals(10,word.length());
    }

  }

  public static void main(String[] args) {
      String[] example = new String[]{"毕安心","边防军","毕竟","毕凌霄"};
    MSDCoder<String> coder = MSDCoderFactory.pinyinCoder;
   example = coder.msdEncode(example);
   String[] res = pinyinToSameLength(example);
    System.out.println(example);
    System.out.println(res.toString());
  }


}
