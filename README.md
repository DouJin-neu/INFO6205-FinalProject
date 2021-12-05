# INFO6205-FinalProject
This is the Team 21 final project for sorting Chinese strings

## Introduction
In this project, we put forward an approach to use MSD radix sort to sort Chinese strings in the English order of Pinyin.
To do this, first, we convert Chinese strings to Pinyin with Pinyin4j package. Then continue convert the Pinyin to long arrays.
After that, using different sorting algorithms to sort the array, and make a copy of the Chinese character while exchange
the position.

```bash
public static String switchPinyin(String content) {
        char[] chars = content.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        // Set case type
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // set tone type
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        // set ü type to u
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        String[] s;
        StringBuilder sb = new StringBuilder();
        boolean pinyin = false;
        //...
}
```

## New Modules and changes to the existed code
We created a new module named Team21, the code is in this module. 
1. Under sort folder, we implement different sort algorithms
to allow them to sort Chinese strings, and output the correct order.
2. Under sort.utils folder, the MSDCoderFactory class helps us to encode and decode the Chinese characters. 
We also add logger to trace the running of the program. The Timer and Benchmark classes are used to do the benchmark for different types of sorting algorithms.
3. Under benchmark folder, that's the place we create benchmarking for different types of sorting algorithms.
4. When implement other sorting algorithms like Dual-pivot Quicksort, Husky Mergesort, LSD radix sort, Timsort, etc. We have made some changes to let them sort the 
Chinese strings. We use the existed code to build the basic structure of the algorithm, and use switchPinyin method to convert strings to long arrays.
   
## Results
The sorted 1M Chinese names sample with different sorting algorithms are under the src/sortResults folder. Only shows first 500 sorted names.
The benchmarking results are under src/benchmarkResultsFiles folder.
