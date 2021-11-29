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
    assertEquals(bytesToString("shang,xia,si,fang".getBytes()), coder.msdEncode("上下四方"));
    assertEquals(bytesToString("yu".getBytes()), coder.msdEncode("宇"));
    assertEquals(bytesToString("gu,wang,jin,lai".getBytes()), coder.msdEncode("古往今来"));
    assertEquals(bytesToString("zhou".getBytes()), coder.msdEncode("宙"));
  }


  @Test
  public void testPinyinCoder() {
    MSDCoder<String> coder = MSDCoderFactory.pinyinCoder;
    assertEquals("shi,zhe,ru,si", coder.msdEncode("逝者如斯"));
    assertEquals("bu,she,zhou,ye", coder.msdEncode("不舍昼夜"));
    assertEquals("lu,man,man", coder.msdEncode("路漫漫"));
    assertEquals("qi,xiu,yuan,xi", coder.msdEncode("其修远兮"));
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
