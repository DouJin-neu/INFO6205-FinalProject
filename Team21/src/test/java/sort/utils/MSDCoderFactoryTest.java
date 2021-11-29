/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package sort.utils;


import static junit.framework.TestCase.assertEquals;
import static sort.utils.MSDCoderFactory.bytesToString;

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
    assertEquals(bytesToString("shangxiasifang".getBytes()), coder.msdEncode("上下四方"));
    assertEquals(bytesToString("yu".getBytes()), coder.msdEncode("宇"));
    assertEquals(bytesToString("guwangjinlai".getBytes()), coder.msdEncode("古往今来"));
    assertEquals(bytesToString("zhou".getBytes()), coder.msdEncode("宙"));
  }


  @Test
  public void testPinyinCoder() {
    MSDCoder<String> coder = MSDCoderFactory.pinyinCoder;
    assertEquals("shizherusi", coder.msdEncode("逝者如斯"));
    assertEquals("bushezhouye", coder.msdEncode("不舍昼夜"));
    assertEquals("lumanman", coder.msdEncode("路漫漫"));
    assertEquals("qixiuyuanxi", coder.msdEncode("其修远兮"));
  }


}
