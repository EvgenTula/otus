package ru.otus.hw13multithreadsort.Sorters;

public interface Sorter {
    <T extends Comparable> void sort(T[] arr);
}
