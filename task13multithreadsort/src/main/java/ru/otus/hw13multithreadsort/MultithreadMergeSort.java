package ru.otus.hw13multithreadsort;

import java.util.Arrays;

public class MultithreadMergeSort {

    private static class MultithreadSort<T extends Comparable> extends Thread {

        private T[] originalArray;
        private T[] tmpArr;
        private int lower;
        private int upper;
        private static int THREADS;
        static {
            if (Runtime.getRuntime().availableProcessors() < 4) {
                THREADS = Runtime.getRuntime().availableProcessors();
            } else {
                THREADS = 5;
            }
            System.out.println(" THREADS = " + THREADS);
        }


        public MultithreadSort(T[] originalArray,T[] tmpArr, int lower, int upper) {
            this.originalArray = originalArray;
            this.tmpArr = tmpArr;
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public void run() {
            if (lower == upper) {
                return;
            } else {
                int middle = (lower + upper) / 2;
                if (THREADS > Thread.activeCount()) {
                    MultithreadSort lowerSort = new MultithreadSort(originalArray, tmpArr, lower, middle);
                    MultithreadSort upperSort = new MultithreadSort(originalArray, tmpArr, middle + 1, upper);
                    lowerSort.start();
                    upperSort.start();
                    try {
                        lowerSort.join();
                        upperSort.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    merge(originalArray, tmpArr, lower, middle + 1, upper);
                } else {
                    mergeSort(originalArray, tmpArr, lower, middle);
                    mergeSort(originalArray, tmpArr, middle + 1, upper);
                    merge(originalArray, tmpArr, lower, middle + 1, upper);
                }
            }
        }

        private static <T extends Comparable> void mergeSort(T[] originalArray,T[] tmpArr, int lower, int upper) {
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

    public static <T extends Comparable> void sort(T[] arr) {
        long timeStart = System.nanoTime();
        T[] tmpArray = Arrays.copyOfRange(arr,0,arr.length);
        System.out.println(Thread.activeCount());
        MultithreadSort sort = new MultithreadSort(arr,tmpArray, 0, arr.length - 1);
        sort.start();
        try {
            sort.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long timeEnd = System.nanoTime();
        System.out.println("time : " + ((double)timeEnd - timeStart / 1_000_000_000));
    }
}
