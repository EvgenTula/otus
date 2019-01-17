package ru.otus.hw13multithreadsort.Sorters;

public class SorterDecorator<T extends Sorter> {

    private T sorter;
    public SorterDecorator(T sorter) {
        this.sorter = sorter;
    }

    public <E extends Comparable> void Execute(E[] arr) {
        System.out.println(this.sorter.getClass().toString());
        long timeStart = System.nanoTime();
        this.sorter.sort(arr);
        long timeEnd = System.nanoTime();
        System.out.println("time : " + ((timeEnd - timeStart) / 1_000_000_000d));
    }
}
