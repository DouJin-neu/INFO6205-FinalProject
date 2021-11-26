package sort.utils;

public interface MSDCoder<X> {
  default String[] msdEncode(X[] xs) {
    String[] result = new String[xs.length];
    for (int i = 0; i < xs.length; i++) result[i] = msdEncode(xs[i]);
    return result;
  }

  default long[] msdEncodeToNumber(X[] xs) {
    long[] result = new long[xs.length];
    for (int i = 0; i < xs.length; i++) result[i] = msdEncodeToNumber(xs[i]);
    return result;
  }

  default String name() {
    return "MSDCoder";
  }


  String msdEncode(X x);
  long msdEncodeToNumber(X x);
}
