/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.util.Config;

import java.io.IOException;

public class InsertionSortOpt<X extends Comparable<X>> extends InsertionSort<X> {

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to com.info6205.team21.sort.
     * @param config the configuration.
     */
    public InsertionSortOpt(int N, Config config) {
        super(DESCRIPTION, N, config);
    }

    public InsertionSortOpt(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSortOpt(Helper<X> helper) {
        super(helper);
    }

    /**
     * Sort the sub-array xs:from:to using insertion com.info6205.team21.sort.
     *
     * @param xs   com.info6205.team21.sort the array xs from "from" to "to".
     * @param from the index of the first element to com.info6205.team21.sort
     * @param to   the index of the first element not to com.info6205.team21.sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();
        for (int i = from + 1; i < to; i++) {
            helper.swapIntoSorted(xs, i);
        }
    }

    /**
     * This is used by unit tests.
     *
     * @param ys  the array to be sorted.
     * @param <Y> the underlying element type.
     */
    public static <Y extends Comparable<Y>> void mutatingInsertionSort(Y[] ys) throws IOException {
        new InsertionSortOpt<Y>(Config.load(InsertionSortOpt.class)).mutatingSort(ys);
    }

    public static final String DESCRIPTION = "Insertion com.info6205.team21.sort optimized";

}
