package ru.otus.hw13multithreadsort;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int size = 8;

        Integer[] array = new Integer[size];
        Random rnd = new Random();
        for (int i = 0; i < size; i ++) {
            array[i] = rnd.nextInt(size);
        }

        Integer[] array1 = Arrays.copyOfRange(array,0,array.length);
        Integer[] array2 = Arrays.copyOfRange(array,0,array.length);
        Integer[] array3 = Arrays.copyOfRange(array,0,array.length);

        System.out.println(Arrays.toString(array1));
        System.out.println("Merge sort");
        MergeSort.sort(array1);
        //System.out.println(Arrays.toString(array1));

        System.out.println("MultithreadMerge sort");
        MultithreadMergeSort.sort(array2);
        //System.out.println(Arrays.toString(array2));
        System.out.println(Arrays.deepEquals(array1,array2));

        System.out.println("ThreadSortExecutorService merge sort");
        ThreadSortExecutorService.sort(array3);
        System.out.println(Arrays.toString(array3));

        System.out.println(Arrays.deepEquals(array1,array3));
    }
}
