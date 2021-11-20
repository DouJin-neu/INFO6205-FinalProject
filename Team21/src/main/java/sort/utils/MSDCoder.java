package sort.utils;

public interface MSDCoder<X> {
  default String[] huskyEncode(X[] xs) {
    String[] result = new String[xs.length];
    for (int i = 0; i < xs.length; i++) result[i] = huskyEncode(xs[i]);
    return result;
  }

  String huskyEncode(X x);
}
