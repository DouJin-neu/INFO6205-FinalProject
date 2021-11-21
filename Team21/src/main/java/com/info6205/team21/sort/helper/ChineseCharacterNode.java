package com.info6205.team21.sort.helper;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import com.info6205.team21.sort.utils.HuskySortable;

public class ChineseCharacterNode implements HuskySortable<ChineseCharacterNode> {
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getPinyin() {
    return pinyin;
  }

  public void setPinyin(String pinyin) {
    this.pinyin = pinyin;
  }

  private String pinyin;

  public ChineseCharacterNode(String value){
    this.value = value;
    this.pinyin = switchPinyin(value);
  }

  /* String to pinyin */
  public String switchPinyin(String content) {
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


  @Override
  public int compareTo(ChineseCharacterNode o) {
    return 0;
  }

  @Override
  public long huskyCode() {
    return 0;
  }
}
