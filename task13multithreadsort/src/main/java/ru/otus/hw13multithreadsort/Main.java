package ru.otus.hw13multithreadsort;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int size = 10000000;

        Integer[] array = new Integer[size];
        Random rnd = new Random();
        for (int i = 0; i < size; i ++) {
            array[i] = rnd.nextInt(size);
        }

        Integer[] array1 = Arrays.copyOfRange(array,0,array.length);
        Integer[] array2 = Arrays.copyOfRange(array,0,array.length);

        System.out.println("Before sort");
        //System.out.println(Arrays.toString(array1));

        System.out.println("Merge sort");
        MergeSort.sort(array1);
        //System.out.println(Arrays.toString(array1));

        System.out.println("Multithread merge sort");
        MultithreadMergeSort.sort(array2);
        //System.out.println(Arrays.toString(array2));
    }

}
