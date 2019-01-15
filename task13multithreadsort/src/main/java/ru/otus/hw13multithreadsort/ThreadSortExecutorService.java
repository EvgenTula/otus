package ru.otus.hw13multithreadsort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ThreadSortExecutorService {

    private static int THREADS;
    private static ThreadPoolExecutor service;
    static {
        if (Runtime.getRuntime().availableProcessors() < 4) {
            THREADS = Runtime.getRuntime().availableProcessors();
        } else {
            THREADS = 4;
        }
        service = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS);
    }

    public static <T extends Comparable> void sort(T[] arr) {
        long timeStart = System.nanoTime();



        List<Range> rangeList = prepareRange(arr);
        for (Range item : rangeList) {
            service.execute(() -> {
                mergeSort(arr, item.from,item.to);
            });
        }

        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < rangeList.size(); i++) {
            int from = rangeList.get(0).from;
            int to = rangeList.get(i).to;
            int middle = rangeList.get(i).from;
            merge(arr,from, middle, to);
        }

        long timeEnd = System.nanoTime();
        System.out.println("time : " + ((timeEnd - timeStart) / 1_000_000_000d));
    }

    private static <T extends Comparable> void mergeSort(T[] originalArray, int lower, int upper) {
        if (lower == upper) {
            return;
        } else {
            int middle = (lower + upper) / 2;
            mergeSort(originalArray, lower, middle);
            mergeSort(originalArray, middle + 1, upper);
            merge(originalArray, lower, middle + 1, upper);
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

    private static <T> List<Range> prepareRange(T[] arr) {
        List<Range> result = new ArrayList<>();
        int arraySize = 0;
        if (arr.length < THREADS) {
            arraySize = arr.length;
        } else {
            arraySize = (arr.length ) / THREADS;
        }
        int from = 0;
        int to = 0;
        int index = 0;

        while(to != arr.length) {
            from = index * (arraySize);
            to = ((index * (arraySize)) + arraySize);
            if ((index * arraySize) + 1 > arr.length - 1 || (index + 1) >= THREADS) {
                to = arr.length;
            }
            result.add(new Range(from, to - 1));
            index++;
        }
        return result;
    }


    private static class Range {
        private int from;
        private int to;

        public Range(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}
