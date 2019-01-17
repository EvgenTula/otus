package ru.otus.hw13multithreadsort.Sorters;

public class RuntimerHelper {
    public static int getAvailableThreadsOrDefault(int threadsCount) {
        if (Runtime.getRuntime().availableProcessors() > threadsCount) {
            return Runtime.getRuntime().availableProcessors();
        } else {
            return threadsCount;
        }
    }
}
