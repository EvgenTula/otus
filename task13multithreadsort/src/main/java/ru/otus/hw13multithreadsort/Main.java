package ru.otus.hw13multithreadsort;

import ru.otus.hw13multithreadsort.Sorters.ExecutorServiceMerge.ExecutorServiceSort;
import ru.otus.hw13multithreadsort.Sorters.MultithreadMerge.MultithreadMergeSort;
import ru.otus.hw13multithreadsort.Sorters.SimpleMerge.SimpleMergeSort;
import ru.otus.hw13multithreadsort.Sorters.SorterDecorator;

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
        Integer[] array3 = Arrays.copyOfRange(array,0,array.length);

        //System.out.println("Merge sort");
        SorterDecorator simpleSort = new SorterDecorator(new SimpleMergeSort());
        simpleSort.Execute(array1);

        //System.out.println("MultithreadMergeSort sort");
        SorterDecorator multithreadMergeSort = new SorterDecorator(new MultithreadMergeSort());
        multithreadMergeSort.Execute(array2);
        System.out.println(Arrays.deepEquals(array1,array2));

        //System.out.println("threadSortExecutorService merge sort");
        SorterDecorator threadSortExecutorService = new SorterDecorator(new ExecutorServiceSort());
        threadSortExecutorService.Execute(array3);
        System.out.println(Arrays.deepEquals(array1,array3));
    }
}
