package sort.utils;

import java.nio.file.LinkOption;

public interface MSDCoder<X> {
  default String[] huskyEncode(X[] xs) {
    String[] result = new String[xs.length];
    for (int i = 0; i < xs.length; i++) result[i] = huskyEncode(xs[i]);
    return result;
  }

  default long[] huskyEncodeToNumber(X[] xs) {
    long[] result = new long[xs.length];
    for (int i = 0; i < xs.length; i++) result[i] = huskyEncodeToNumber(xs[i]);
    return result;
  }



  String huskyEncode(X x);
  long huskyEncodeToNumber(X x);
}
