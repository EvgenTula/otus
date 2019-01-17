package ru.otus.hw13multithreadsort.Sorters.ExecutorServiceMerge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelperExecutorServiceSort {

    static int availableThreads;

    static <T extends Comparable> void mergeSort(T[] originalArray, int lower, int upper) {
        if (lower == upper) {
            return;
        } else {
            int middle = (lower + upper) / 2;
            mergeSort(originalArray, lower, middle);
            mergeSort(originalArray, middle + 1, upper);
            merge(originalArray, lower, middle + 1, upper);
        }
    }

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

    static <T> List<Range> prepareRange(T[] arr) {
        List<Range> result = new ArrayList<>();
        int arraySize = 0;
        if (arr.length < availableThreads) {
            arraySize = arr.length;
        } else {
            arraySize = (arr.length ) / availableThreads;
        }
        int from = 0;
        int to = 0;
        int index = 0;

        while(to != arr.length) {
            from = index * (arraySize);
            to = ((index * (arraySize)) + arraySize);
            if ((index * arraySize) + 1 > arr.length - 1 || (index + 1) >= availableThreads) {
                to = arr.length;
            }
            result.add(new Range(from, to - 1));
            index++;
        }
        return result;
    }


    static class Range {
        public int from;
        public int to;

        public Range(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}
