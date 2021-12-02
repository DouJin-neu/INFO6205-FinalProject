package elementary;


/**
 * This is a basic implementation of insertion sort.
 * It does not extend Sort, nor does it employ any optimizations.
 */
public class InsertionSortMSD<X> {

    public void sort(String[] a,X[] xs, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--){
                swap(a, j, j - 1);
                swap(xs, j, j - 1);
            }

    }

    public void sort(long[] a, X[] xs, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && a[j]<a[j - 1]; j--){
                swap(a, j, j - 1);
                swap(xs, j, j - 1);
            }

    }

    private static boolean less(String v, String w, int d) {
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }

    private static void swap(Object[] a, int j, int i) {
        Object temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }
    private static void swap(long[] a, int j, int i) {
        long temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }
}

