package sort.utils;

public interface MSDCoder<X> {
  default String[] msdEncode(X[] xs) {
    String[] result = new String[xs.length];
    for (int i = 0; i < xs.length; i++) result[i] = msdEncode(xs[i]);
    return result;
  }

  String msdEncode(X x);
}
