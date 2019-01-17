package ru.otus.hw13multithreadsort.Sorters.MultithreadMerge;

import ru.otus.hw13multithreadsort.Sorters.Sorter;

public class MultithreadMergeSort implements Sorter {

    private static final int DEFAULT_THREADS_COUNT = 4;

    public <T extends Comparable> void sort(T[] arr) {
        if (Runtime.getRuntime().availableProcessors() > DEFAULT_THREADS_COUNT) {
            HelperMultithreadMergeSort.availableThreads = Runtime.getRuntime().availableProcessors();
        } else {
            HelperMultithreadMergeSort.availableThreads = DEFAULT_THREADS_COUNT;
        }
        HelperMultithreadMergeSort.mergeSort(arr,0, arr.length - 1);
    }
}
