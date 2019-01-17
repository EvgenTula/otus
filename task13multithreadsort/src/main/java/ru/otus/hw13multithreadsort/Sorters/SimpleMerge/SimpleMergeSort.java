package ru.otus.hw13multithreadsort.Sorters.SimpleMerge;

import ru.otus.hw13multithreadsort.Sorters.Sorter;

import java.util.Arrays;

public class SimpleMergeSort implements Sorter {
    public <T extends Comparable> void sort(T[] arr) {
        T[] tmpArray = Arrays.copyOfRange(arr,0,arr.length);
        HelperSimpleSort.mergeSort(arr,tmpArray, 0, arr.length - 1);
    }
}
