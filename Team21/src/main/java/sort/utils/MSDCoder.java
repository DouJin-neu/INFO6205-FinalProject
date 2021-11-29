package sort.utils;

import static sort.utils.MSDCoderFactory.pinyinToSameLength;

public interface MSDCoder<X> {
  default String[] msdEncode(X[] xs) {
   return msdEncode(xs, 'a');
  }
  default String[] msdEncode(X[] xs,char append) {
    String[] result = new String[xs.length];
    for (int i = 0; i < xs.length; i++) result[i] = msdEncode(xs[i]);
    return pinyinToSameLength(result,append);
  }

  default long[] msdEncodeToNumber(X[] xs) {
    return msdEncodeToNumber(xs,'a');
  }

  default long[] msdEncodeToNumber(X[] xs,char append) {
    String[] res =  msdEncode(xs,append);
    long[] result = new long[xs.length];
    for (int i = 0; i < xs.length; i++) result[i] = msdEncodeToNumber(xs[i],res,i);
    return result;
  }



  default String name() {
    return "MSDCoder";
  }


  String msdEncode(X x);
  long msdEncodeToNumber(X x);
  long msdEncodeToNumber(X x,String[] words,int index);
}
