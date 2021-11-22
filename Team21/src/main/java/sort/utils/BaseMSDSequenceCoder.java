package sort.utils;

/**
 * Base Husky sequence coder.
 */
public abstract class BaseMSDSequenceCoder<X extends CharSequence> implements MSDSequenceCoder<X> {

    /**
     * Method to determine if this Husky Coder is perfect for a sequence of the given length.
     * If the result is false for a particular length, it implies that inversions will remain after the first pass of Husky Sort.
     * If the result is true for all actual lengths, then the second pass of Husky Sort would be superfluous.
     *
     * @param length the length of a particular String.
     * @return true if length <= maxLength.
     */
    public final boolean perfectForLength(final int length) {
        return length <= maxLength;
    }

    /**
     * Constructor.
     *
     * @param name      the name of this coder.
     * @param maxLength the maximum length of a sequence which can be perfectly encoded.
     */
    public BaseMSDSequenceCoder(final String name, final int maxLength) {
        this.name = name;
        this.maxLength = maxLength;
    }

    /**
     * @return the name of this coder.
     */
    final public String name() {
        return name;
    }

    /**
     * Encode an array of Xs.
     *
     * @param xs an array of X elements.
     * @return an array of longs corresponding to the the Husky codes of the X elements.
     */
    @Override
    final public String[] msdEncode(final X[] xs) {
        boolean isPerfect = true;
         String[] result = new String[xs.length];
        for (int i = 0; i < xs.length; i++) {
            final X x = xs[i];
            if (isPerfect) isPerfect = perfectForLength(x.length());
            result[i] = msdEncode(x);
        }
        return result;
    }

    @Override
    final public long[] huskyEncodeToNumber(final X[] xs) {
        boolean isPerfect = true;
        long[] result = new long[xs.length];
        for (int i = 0; i < xs.length; i++) {
            final X x = xs[i];
            if (isPerfect) isPerfect = perfectForLength(x.length());
            result[i] = huskyEncodeToNumber(x);
        }
        return result;
    }

    @Override
    final public String toString() {
        return "BaseHuskySequenceCoder{" +
                "name='" + name + '\'' +
                '}';
    }

    private final String name;
    private final int maxLength;
}
