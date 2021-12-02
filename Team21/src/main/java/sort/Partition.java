package sort;

public class Partition<X extends Comparable<X>> {
    public final X[] xs;
    public final String[] a;
    public final int from;
    public final int to;

    public Partition(X[] xs,String[] a, int from, int to) {
        this.xs = xs;
        this.a = a;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "Partition{" +
                "xs: " + xs.length + " elements" +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
