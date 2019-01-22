package ru.otus.hw13multithreadsort.Sorters.SimpleMerge;

public class HelperSimpleSort {

    public static <T extends Comparable> void mergeSort(T[] originalArray,T[] tmpArr, int lower, int upper) {
        if (lower == upper) {
            return;
        } else {
            int middle = (lower + upper) / 2;

            mergeSort(originalArray,tmpArr, lower, middle);
            mergeSort(originalArray,tmpArr, middle + 1, upper);

            merge(originalArray, tmpArr, lower, middle + 1, upper);
        }
    }

    private static <T extends Comparable> void merge(T[] original,
                                                     T[] tmp,
                                                     int lower,
                                                     int middle,
                                                     int upper) {
        int index = 0;

        int lowerIndex = lower;
        int lowerBound = middle-1;

        int upperIndex = middle;
        int upperBound = upper;

        int size = upperBound - lowerIndex + 1;

        while (lowerIndex <= lowerBound && upperIndex <= upperBound) {
            if (original[lowerIndex].compareTo(original[upperIndex]) == -1) {
                tmp[index++] = original[lowerIndex++];
            } else {
                tmp[index++] = original[upperIndex++];
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
