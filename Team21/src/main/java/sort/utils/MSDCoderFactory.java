/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package sort.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Factory class for MSDCoders.
 */
public final class MSDCoderFactory{
    private static final int BITS_LONG = 64;
    private static final int BITS_BYTE = 8;
    private static final int BYTES_LONG = BITS_LONG / BITS_BYTE;
    private static final int MASK_BYTE = 0xFF;
    private static final int MASK_SHORT = 0xFFFF;

    private static final int BIT_WIDTH_ASCII = 7;
    private static final int MAX_LENGTH_ASCII = BITS_LONG / BIT_WIDTH_ASCII;
    private static final int MASK_ASCII = 0x7F;

    private static final int BIT_WIDTH_ENGLISH = 6;
    private static final int MAX_LENGTH_ENGLISH = BITS_LONG / BIT_WIDTH_ENGLISH;
    private static final int MASK_ENGLISH = 0x3F;

    private static final int BIT_WIDTH_UNICODE = 16;
    private static final int MAX_LENGTH_UNICODE = BITS_LONG / BIT_WIDTH_UNICODE;
    private static final int MASK_UNICODE = MASK_SHORT;

    private static final int BIT_WIDTH_UTF8 = 8;
    private static final int MAX_LENGTH_UTF8 = BITS_LONG / BIT_WIDTH_UTF8;
    private static final int MASK_UTF8 = MASK_BYTE;




    /**
     * A Husky Coder for English Strings.
     * <p>
     * This should work correctly for all 52 English characters (upper and lower case),
     * as well as the following 11 characters: @ [ \ ] ^ _ ` { | } ~
     * <p>
     * But, in any case, we are only optimizing for printable ascii characters here.
     * If the long encoding is off for some reason (like there's a number embedded in the name),
     * it's no big deal.
     * It just means that the final pass will have to work a bit harder to fix the extra inversion.
     */
    public final static MSDSequenceCoder<String> pinyinCoder = new BaseMSDSequenceCoder<String>("pinyin", MAX_LENGTH_ENGLISH) {

        @Override
        public String msdEncode(String s) {
            return switchPinyin(s);
        }

        public long msdEncodeToNumber(final String str){
            return 0;
        }
    };

    public final static MSDSequenceCoder<String> bitCoder = new BaseMSDSequenceCoder<String>("bit", MASK_SHORT) {

        @Override
        public String msdEncode(String s) {
            return stringToBit(s);
        }

        public long msdEncodeToNumber(final String str){
            return 0;
        }
    };

    /**
     * A Husky Coder for English Strings.
     * <p>
     * This should work correctly for all 52 English characters (upper and lower case),
     * as well as the following 11 characters: @ [ \ ] ^ _ ` { | } ~
     * <p>
     * But, in any case, we are only optimizing for printable ascii characters here.
     * If the long encoding is off for some reason (like there's a number embedded in the name),
     * it's no big deal.
     * It just means that the final pass will have to work a bit harder to fix the extra inversion.
     */
    public final static BaseMSDSequenceCoder<String> englishCoder = new BaseMSDSequenceCoder<String>("English", MAX_LENGTH_ENGLISH) {
        /**
         * Encode x as a long.
         * As much as possible, if x > y, huskyEncode(x) > huskyEncode(y).
         * If this cannot be guaranteed, then the result of imperfect(z) will be true.
         *
         * @param str the X value to encode.
         * @return a long which is, as closely as possible, monotonically increasing with the domain of X values.
         */
        public long msdEncodeToNumber(final String str) {
            String pinyin = switchPinyin(str);
            return englishToLong(pinyin);
        }

        public String msdEncode(final String str) {
            return null;
        }

    };

    private static long englishToLong(final String str) {
        return stringToLong(str, MAX_LENGTH_ENGLISH, BIT_WIDTH_ENGLISH, MASK_ENGLISH);
    }

    private static long stringToLong(final String str, final int maxLength, final int bitWidth, final int mask) {
        final int length = Math.min(str.length(), maxLength);
        final int padding = maxLength - length;
        long result = 0L;
        if (((mask ^ MASK_SHORT) & MASK_SHORT) == 0)
            for (int i = 0; i < length; i++) result = result << bitWidth | str.charAt(i);
        else
            for (int i = 0; i < length; i++) result = result << bitWidth | str.charAt(i) & mask;
        result = result << bitWidth * padding;
        return result;
    }

        public static String stringToBit(String s) {
        s = switchPinyin(s);
        byte[] bytes = s.getBytes();
        return bytesToString(bytes);
    }

    public static String bytesToString(byte[] bytes){
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }

        return binary.toString();
    }


    /* String to pinyin */
    public static String switchPinyin(String content) {
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

    public static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }

    public static void swap(Object a[], int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;

    }


}
