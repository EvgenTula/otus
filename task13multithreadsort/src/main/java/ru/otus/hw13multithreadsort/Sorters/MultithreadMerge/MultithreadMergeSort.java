package ru.otus.hw13multithreadsort.Sorters.MultithreadMerge;

import ru.otus.hw13multithreadsort.Sorters.RuntimerHelper;
import ru.otus.hw13multithreadsort.Sorters.Sorter;

public class MultithreadMergeSort implements Sorter {

    private static final int DEFAULT_THREADS_COUNT = 4;

    public <T extends Comparable> void sort(T[] arr) {
        HelperMultithreadMergeSort.availableThreads = RuntimerHelper.getAvailableThreadsOrDefault(DEFAULT_THREADS_COUNT);
        mergeSort(arr,0, arr.length - 1);
    }


    static <T extends Comparable> void mergeSort(T[] originalArray, int lower, int upper) {
        if (lower == upper) {
            return;
        } else {
            int middle = (lower + upper) / 2;
            if (HelperMultithreadMergeSort.availableThreads > HelperMultithreadMergeSort.activeThreads.get()) {
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
                HelperMultithreadMergeSort.merge(originalArray, lower, middle + 1, upper);
            } else {
                mergeSort(originalArray, lower, middle);
                mergeSort(originalArray, middle + 1, upper);
                HelperMultithreadMergeSort.merge(originalArray, lower, middle + 1, upper);
            }
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
            HelperMultithreadMergeSort.activeThreads.incrementAndGet();
            mergeSort(originalArray, lower, upper);
            HelperMultithreadMergeSort.activeThreads.decrementAndGet();
        }
    }
}
