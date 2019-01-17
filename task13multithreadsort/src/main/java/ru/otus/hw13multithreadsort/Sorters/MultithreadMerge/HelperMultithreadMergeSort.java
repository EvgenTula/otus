package ru.otus.hw13multithreadsort.Sorters.MultithreadMerge;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class HelperMultithreadMergeSort {

    static AtomicInteger activeThreads = new AtomicInteger(0);
    static int availableThreads;

    static <T extends Comparable> void merge(T[] original,
                                             int lower,
                                             int middle,
                                             int upper) {
        T[] tmp = Arrays.copyOfRange(original,lower,upper + 1);

        int lowerIndex = lower;
        int lowerBound = middle-1;

        int upperIndex = middle;
        int upperBound = upper;

        int size = upperBound - lowerIndex + 1;

        int index = 0;

        while (lowerIndex <= lowerBound && upperIndex <= upperBound) {
            if (original[lowerIndex].compareTo(original[upperIndex]) > 0) {
                tmp[index++] = original[upperIndex++];
            } else {
                tmp[index++] = original[lowerIndex++];
            }
        }

        while (lowerIndex <= lowerBound) {
            tmp[index++] = original[lowerIndex++];
        }

        while (upperIndex <= upperBound) {
            tmp[index++] = original[upperIndex++];
        }

        for(int i = 0; i < size; i++) {
            original[lower + i] = tmp[i];
        }
    }

}
