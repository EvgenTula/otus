package ru.otus.hw13multithreadsort.Sorters.ExecutorServiceMerge;

import ru.otus.hw13multithreadsort.Sorters.Sorter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceSort implements Sorter {

    private static final int DEFAULT_THREADS_COUNT = 4;
    private static ThreadPoolExecutor service;

    public <T extends Comparable> void sort(T[] arr) {

        if (Runtime.getRuntime().availableProcessors() > DEFAULT_THREADS_COUNT) {
            HelperExecutorServiceSort.availableThreads = Runtime.getRuntime().availableProcessors();
        } else {
            HelperExecutorServiceSort.availableThreads = DEFAULT_THREADS_COUNT;
        }
        service = (ThreadPoolExecutor) Executors.newFixedThreadPool(HelperExecutorServiceSort.availableThreads);

        List<Callable<Object>> list = new ArrayList<>();

        List<HelperExecutorServiceSort.Range> rangeList = HelperExecutorServiceSort.prepareRange(arr);
        for (HelperExecutorServiceSort.Range item : rangeList) {
            list.add(Executors.callable(() -> {
                HelperExecutorServiceSort.mergeSort(arr, item.from,item.to);
            }));
        }

        try {
            service.invokeAll(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            service.shutdown();
        }

        for (int i = 1; i < rangeList.size(); i++) {
            int from = rangeList.get(0).from;
            int to = rangeList.get(i).to;
            int middle = rangeList.get(i).from;
            HelperExecutorServiceSort.merge(arr,from, middle, to);
        }
    }

}
