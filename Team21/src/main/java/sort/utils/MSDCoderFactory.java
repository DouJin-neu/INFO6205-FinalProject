/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package sort.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.LongBuffer;
import java.nio.charset.Charset;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;
import java.util.Random;
import org.checkerframework.checker.units.qual.C;
import sort.helper.ChineseCharactorNode;

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
    public final static HuskySequenceCoder<String> pinyinCoder = new BaseHuskySequenceCoder<String>("pinyin", MAX_LENGTH_ENGLISH) {
        /**
         * Encode x as a long.
         * As much as possible, if x > y, huskyEncode(x) > huskyEncode(y).
         * If this cannot be guaranteed, then the result of imperfect(z) will be true.
         *
         * @param str the X value to encode.
         * @return a long which is, as closely as possible, monotonically increasing with the domain of X values.
         */
        public String huskyEncode(final String str) {
            //todo
            return "switch chinese to pinyin";
        }
    };
}
