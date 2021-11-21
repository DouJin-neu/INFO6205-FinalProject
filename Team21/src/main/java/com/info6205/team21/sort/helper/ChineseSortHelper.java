/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package com.info6205.team21.sort.helper;


import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.util.Config;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

/**
 * Various utilities to help with HuskySort.
 */
public class ChineseSortHelper<X extends Comparable<X>> implements Helper<X> {


    /**
     * Generate a random String of (English) alphabetic characters.
     *
     * @param number    the number of Strings to generate.
     * @param minLength the minimum number of characters in a String.
     * @param maxLength the maximum number of characters in a String.
     * @return an array (of length number) of Strings, each of length between minLength and maxLength.
     */
    public static String[] generateRandomChineseArray(int number, int minLength, int maxLength) {

        //todo
        String[] result = new String[number];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < number; i++) {
            StringBuilder tmp = new StringBuilder();
            int length = random.nextInt(minLength, maxLength + 1);
//            for (int j = 0; j < length; j++) tmp.append(alphabet[random.nextInt(0, alphabet.length)]);
            result[i] = tmp.toString();
        }
        return result;
    }


    @Override
    public boolean instrumented() {
        return false;
    }

    @Override
    public X[] random(Class<X> clazz, Function<Random, X> f) {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Config getConfig() {
        return null;
    }

    @Override
    public int getN() {
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public int compare(X[] xs, int i, int j) {
        return 0;
    }

    @Override
    public boolean less(X v, X w) {
        return false;
    }

    @Override
    public int compare(X v, X w) {
        return 0;
    }

    @Override
    public void swapInto(X[] xs, int i, int j) {

    }

    @Override
    public boolean sorted(X[] xs) {
        return false;
    }

    @Override
    public int inversions(X[] xs) {
        return 0;
    }

    @Override
    public void postProcess(X[] xs) {

    }

    @Override
    public void init(int n) {

    }
}
