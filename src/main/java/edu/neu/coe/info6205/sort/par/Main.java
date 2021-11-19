package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        //ArraySize = 2000000, thread_num = 4 -> best cutoff?
        processArgs(args);
        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());

        int[] array = new int[2000000];
        int thread_num = 4;
        ParSort.thread_num = thread_num;
        ParSort.myPool = new ForkJoinPool(thread_num);
        ArrayList<Long> timeList = new ArrayList<>();
        for (int j = 1; j < 200; j++) {
            ParSort.cutoff = 10000 * j;
            // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            long time;
            long startTime = System.currentTimeMillis();
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length);
            }
            long endTime = System.currentTimeMillis();
            time = (endTime - startTime);
            timeList.add(time);


            System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10times Time:" + time + "ms");

        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/result19.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = 1;
            for (long i : timeList) {
                String content = (double) 10000*j / 2000000 + "," + (double) i / 10 + "\n";
                j++;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Different Thread Number, cut-off = 800000, arraySize = 2000000
       /* processArgs(args);
        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
        //Random random = new Random();
        int[] array = new int[2000000];
        //int[] arraySize = new int[]{1000000, 2000000, 4000000};
        //int index=1;
        System.out.println("Current Array Size is " + array.length);
        for(int power = 1; power < 8; power++) {
            int thread_num = (int) Math.pow(2, power);
            ParSort.thread_num = thread_num;
            ParSort.myPool = new ForkJoinPool(thread_num);
            //System.out.println("*****************Thread_num: " + ParSort.thread_num + "********************");

            //
            //int[] array = new int[arrayLength];
            ArrayList<Long> timeList = new ArrayList<>();

            //ParSort.cutoff = 10000 * (j+1);
            ParSort.cutoff = 800000;
            // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            long time;
            long startTime = System.currentTimeMillis();
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length);
            }
            long endTime = System.currentTimeMillis();
            time = (endTime - startTime);
            timeList.add(time);
            System.out.println("Thread_num: " + ParSort.thread_num + "\t\t10times Time:" + time + "ms");

        }*/


        //Different Thread Number and different cut-off
        /*processArgs(args);
        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
        Random random = new Random();
        int[] array = new int[2000000];
        //int[] arraySize = new int[]{1000000, 2000000, 4000000};
        int index=1;

        for(int power = 1; power < 8; power++){
            int thread_num = (int)Math.pow(2, power);
            ParSort.thread_num = thread_num;
            ParSort.myPool = new ForkJoinPool(thread_num);
            System.out.println("*****************Thread_num: " + ParSort.thread_num+"********************");

            System.out.println("Current Array Size is "+ array.length);
            //int[] array = new int[arrayLength];
            ArrayList<Long> timeList = new ArrayList<>();
            for (int j = 2000000; j > 1000; j /= 2 ) {
                //ParSort.cutoff = 10000 * (j+1);
                ParSort.cutoff = j;
                // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                long time;
                long startTime = System.currentTimeMillis();
                for (int t = 0; t < 10; t++) {
                    for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                    ParSort.sort(array, 0, array.length);
                }
                long endTime = System.currentTimeMillis();
                time = (endTime - startTime);
                timeList.add(time);
                System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10times Time:" + time + "ms");
            }
            try {
                FileOutputStream fis = new FileOutputStream("./src/result"+index+".csv");
                //FileOutputStream fis = new FileOutputStream("./src/result.csv");
                OutputStreamWriter isr = new OutputStreamWriter(fis);
                BufferedWriter bw = new BufferedWriter(isr);
                int j = 2000000;
                index++;
                for (long i : timeList) {
                    String content = (double)j / 2000000 + "," + (double) i / 10 + "\n";
                    j /= 2;
                    bw.write(content);
                    bw.flush();
                }
                //index++;
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }*/

        //Test with different array size
       /* ParSort.thread_num = 4;
        ParSort.myPool = new ForkJoinPool(4);
        int index = 9;
        for(int arraySize = 10000; arraySize < 6000000;arraySize *= 2) {

            int[] currArray = new int[arraySize];

            //System.out.println("*****************Thread_num: " + ParSort.thread_num+"********************");
            System.out.println("Current Array Size is " + arraySize);
            ArrayList<Long> timeList = new ArrayList<>();

            for (int j = 1000; j < arraySize; j *= 2) {
                ParSort.cutoff = j;
                long time;
                long startTime = System.currentTimeMillis();
                for (int t = 0; t < 10; t++) {
                    for (int i = 0; i < currArray.length; i++) currArray[i] = random.nextInt(10000000);
                    ParSort.sort(currArray, 0, currArray.length);
                }
                long endTime = System.currentTimeMillis();
                time = (endTime - startTime);
                timeList.add(time);
                System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10times Time:" + time + "ms");
            }


            try {
                FileOutputStream fis = new FileOutputStream("./src/result" + index + ".csv");
                //FileOutputStream fis = new FileOutputStream("./src/result.csv");
                OutputStreamWriter isr = new OutputStreamWriter(fis);
                BufferedWriter bw = new BufferedWriter(isr);
                int j = 1000;
                index++;
                for (long i : timeList) {
                    String content = (double) j / arraySize + "," + (double) i / 10 + "\n";
                    j *= 2;
                    bw.write(content);
                    bw.flush();
                }
                //index++;
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/



    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
