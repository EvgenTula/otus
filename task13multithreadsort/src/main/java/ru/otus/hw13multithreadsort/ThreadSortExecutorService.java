package ru.otus.hw13multithreadsort;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadSortExecutorService {

    private static int THREADS;
    static {
        if (Runtime.getRuntime().availableProcessors() < 4) {
            THREADS = Runtime.getRuntime().availableProcessors();
        } else {
            THREADS = 4;
        }
    }


    public static <T extends Comparable> void sort(T[] arr) {
        ExecutorService service = Executors.newFixedThreadPool(THREADS);
        int arraySize = 0;
        if (arr.length < THREADS) {
            arraySize = arr.length;
        } else {
            arraySize = (arr.length ) / THREADS;
        }
        T[] tmpArray = Arrays.copyOfRange(arr,0,arr.length);
        int from = 0;
        int to = 0;
        int index = 0;
        while(to != arr.length) {
            from = index * (arraySize);
            to = ((index * (arraySize)) + arraySize);
            if ((index * arraySize) + 1 > arr.length - 1 || (index + 1) >= THREADS) {
                to = arr.length;
            }
            int finalTo = to;
            int finalFrom = from;
            service.execute(() -> {
                mergeSort(arr,tmpArray, finalFrom,finalTo-1);
            });
            index++;
        }
        service.shutdown();
        System.out.println("arr : " + Arrays.toString(arr));
        System.out.println("tmp : " + Arrays.toString(tmpArray));

        //int middle = (arr.length-1) / 2;
        //System.out.println("final");
        //merge(arr, arr, 0, middle + 1, arr.length - 1);
        //ExecutorService executor = Executors.si
        //MergeSort.sort(arr);
    }

    private static <T extends Comparable> void mergeSort(T[] originalArray,T[] tmpArr, int lower, int upper) {
        if (lower == upper) {
            return;
        } else {
            int middle = (lower + upper) / 2;
            mergeSort(originalArray, tmpArr, lower, middle);
            mergeSort(originalArray, tmpArr, middle + 1, upper);
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
            System.out.println(Thread.currentThread().toString() + " : " + Arrays.toString(tmp));
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
        System.out.println(Thread.currentThread().toString() + " : orig " + Arrays.toString(original));
    }

}
