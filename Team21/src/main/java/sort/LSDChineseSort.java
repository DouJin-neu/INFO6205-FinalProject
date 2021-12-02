package sort;

import edu.neu.coe.info6205.sort.elementary.InsertionSortMSD;
import java.util.Arrays;
import sort.utils.MSDCoder;
import sort.utils.MSDCoderFactory;


public class LSDChineseSort<X extends Comparable<X>>{


    public static void main(final String[] args) {

        final int N = 50000;
        final int m = 10000;
        final boolean preSorted = args.length > 0 && Boolean.parseBoolean(args[0]);
        final String inputOrder = preSorted ? "ordered" : "random";
         LSDChineseSort<String> sorter = new LSDChineseSort<String>(MSDCoderFactory.pinyinCoder);
        String[] a = new String[]{"安", "爱","埃", "张", "公","测试","毕安心","比安心","边心","边防","边","边防军","毕竟","毕凌霄","边防站", "毕安", "毕福剑"};
//        String[] a = new String[]{"安","爱","埃", "张", "公","测试"};
        sorter.sort(a);
        for (String s : a) {
            System.out.println(s);
        }
    }

    public LSDChineseSort(final MSDCoder<X> msdCoder) {
        this.msdCoder =  msdCoder;
    }

    private int maxLength;

    private final MSDCoder<X> msdCoder;

    private final int ASCII_RANGE = 256;


    /**
     * precess array xs
     * @param xs
     * @return
     */
    public String[] preProcess(final X[] xs){
        String[] longs = msdCoder.msdEncode(xs,'a');
        for (int i = 0; i < longs.length; i++) {
            maxLength = Math.max(longs[i].length(), maxLength);
        }
        return longs;
    }

    public void sort(X[] xs) {
        // NOTE: First pass where we code to longs and sort according to those.
       String[] longs = msdCoder.msdEncode(xs,'a');
        final int n = xs.length;

        for (int i = 0; i < longs.length; i++) {
            maxLength = Math.max(longs[i].length(), maxLength);
        }
        sort(longs,xs, 0, n-1);

    }

    /**
     * sort method is implementation of LSD String sort algorithm.
     *
     * @param strArr It contains an array of String on which LSD sort needs to be performed
     * @param from   This is the starting index from which sorting operation will begin
     * @param to     This is the ending index up until which sorting operation will be continued
     */
    public void sort(String[] strArr,X[] xs, int from, int to) {
        for (int i = maxLength - 1; i >= 0; i--)
            charSort(strArr,xs, i, from, to);
    }

    /**
     * charAsciiVal method returns ASCII value of particular character in a String.
     *
     * @param str          String input for which ASCII Value need to be found
     * @param charPosition Character position of which ASCII value needs to be found. If character
     *                     doesn't exist then ASCII value of null i.e. 0 is returned
     * @return int Returns ASCII value
     */
    private int charAsciiVal(String str, int charPosition) {
        if (charPosition >= str.length()) {
            return 0;
        }
        return str.charAt(charPosition);
    }

    /**
     * charSort method is implementation of LSD sort algorithm at particular character.
     *
     * @param strArr       It contains an array of String on which LSD char sort needs to be performed
     * @param charPosition This is the character position on which sort would be performed
     * @param from         This is the starting index from which sorting operation will begin
     * @param to           This is the ending index up until which sorting operation will be continued
     */
    private void charSort(String[] strArr,X[] xs,  int charPosition, int from, int to) {
        int[] count = new int[ASCII_RANGE + 2];
        int[] count2 = new int[ASCII_RANGE + 2];
        String[] result = new String[strArr.length];
        X[] xsCopy = Arrays.copyOf(xs,strArr.length);

        for (int i = from; i <= to; i++) {
            int c = charAsciiVal(strArr[i], charPosition);
            count[c + 2]++;
            count2[c + 2]++;
        }

        // transform counts to indices
        for (int r = 1; r < ASCII_RANGE + 2; r++) {
            count[r] += count[r - 1];
            count2[r] += count[r - 1];
        }

        // distribute
        for (int i = from; i <= to; i++) {
            int c = charAsciiVal(strArr[i], charPosition);
            result[count[c + 1]++] = strArr[i];
            xsCopy[count2[c + 1]++] = xs[i];
        }

        // copy back
        if (to + 1 - from >= 0) {
            System.arraycopy(result, 0, strArr, from, to + 1 - from);
            System.arraycopy(xsCopy, 0, xs, from, to + 1 - from);
        }
    }

}
