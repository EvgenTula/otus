package ru.otus.hw13multithreadsort.Sorters.MultithreadMerge;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class HelperMultithreadMergeSort {


    private static AtomicInteger activeThreads = new AtomicInteger(0);
    static int availableThreads;

    static <T extends Comparable> void mergeSort(T[] originalArray, int lower, int upper) {
        if (lower == upper) {
            return;
        } else {
            int middle = (lower + upper) / 2;
            if (availableThreads > activeThreads.get()) {
                MultithreadSort lowerSort = new MultithreadSort(originalArray, lower, middle);
                MultithreadSort upperSort = new MultithreadSort(originalArray, middle + 1, upper);
                lowerSort.start();
                upperSort.start();
                try {
                    lowerSort.join();
                    upperSort.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                merge(originalArray, lower, middle + 1, upper);
            } else {
                mergeSort(originalArray, lower, middle);
                mergeSort(originalArray, middle + 1, upper);
                merge(originalArray, lower, middle + 1, upper);
            }
        }
    }

    private static <T extends Comparable> void merge(T[] original,
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

    private static class MultithreadSort<T extends Comparable> extends Thread {

        private T[] originalArray;
        private int lower;
        private int upper;

        public MultithreadSort(T[] originalArray, int lower, int upper) {
            this.originalArray = originalArray;
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public void run() {
            activeThreads.incrementAndGet();
            mergeSort(originalArray, lower, upper);
            activeThreads.decrementAndGet();
        }
    }
}
