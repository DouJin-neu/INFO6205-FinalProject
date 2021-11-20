package sort;

import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Config;
import elementary.InsertionSortMSD;
import sort.helper.ChineseCharactorNode;

/**
 * This class represents the purest form of Husky Sort based on IntroSort for pass 1 and the System sort for pass 2.
 * <p>
 * CONSIDER redefining all of the "to" parameters to be consistent with our other Sort utilities.
 *
 */
public class MSDChineseSort extends SortWithHelper {


    public MSDChineseSort(Helper helper) {
        super(helper);
    }

    public MSDChineseSort(String description, int N, Config config) {
        super(description, N, config);
    }

    @Override
    public void sort(Object[] xs, int from, int to) {

        sort((ChineseCharactorNode[]) xs, from, to,0);
    }


    public void sort(ChineseCharactorNode[] xs, int lo, int hi,int d) {
        if (hi < lo + cutoff) InsertionSortMSD.sort(xs, lo, hi, d);
        else {
            int[] count = new int[radix + 2];        // Compute frequency counts.
            for (int i = lo; i < hi; i++)
                count[charAt(xs[i].getPinyin(), d) + 2]++;
            for (int r = 0; r < radix + 1; r++)      // Transform counts to indices.
                count[r + 1] += count[r];
            for (int i = lo; i < hi; i++)     // Distribute.
                aux[count[charAt(xs[i].getPinyin(), d) + 1]++] = xs[i];
            // Copy back.
            if (hi - lo >= 0) System.arraycopy(aux, 0, xs, lo, hi - lo);
            // Recursively sort for each character value.
            // TO BE IMPLEMENTED
            for(int r = 0; r < radix+1; r++){
                sort(xs, lo+count[r], lo+count[r+1]-1, d+1);
            }
        }
    }


    @Override
    public Object[] preProcess(Object[] xs) {
        ChineseCharactorNode[] processXs = new ChineseCharactorNode[xs.length];
        int i=0;
        for(Object s:xs){
            processXs[i++] = new ChineseCharactorNode(s.toString());
        }

        return processXs;
    }

    @Override
    public void postProcess(Object[] xs) {

    }

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }

    private static final int radix = 256;
    private static final int cutoff = 15;
    private static ChineseCharactorNode[] aux;       // auxiliary array for distribution
}
