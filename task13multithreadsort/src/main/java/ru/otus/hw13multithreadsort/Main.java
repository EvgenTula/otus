package ru.otus.hw13multithreadsort;

import java.util.Arrays;
import java.util.Random;



/*
Здравствуйте, Евгений. У вас нашлось сразу две реализации сортировки. Я проверял обе. Найденные недочеты:
1. AVAILABLE_THREADS и ACTIVE_THREADS не константы (те static final), а просто статические переменные.
Поэтому писать их лучше по соглашению для переменных и методов.

2. 4 - магическое число. Его лучше как раз сделать константой DEFAULT_THREADS_COUNT
3. Не совсем понятно зачем для выяснения количества потоков использовать секцию static.
По моему лучше просто передавать количество потоков в метод или просто получать в sort

4. Даже если нет, то по моему лучше будет сделать как-то так
AvailableThreads = (Runtime.getRuntime().availableProcessors() > 4)? Runtime.getRuntime().availableProcessors(): DEFAULT_THREADS_COUNT;
 Т.е. если потоков больше чем 4 используем все, иначе 4 т.к. меньше чем в ТЗ не комильфо
5. В MultithreadMergeSort массив tmpArray создается, но не используется

6. Там же не совсем ясно зачем мы отдаем поток под сортировку в целом если потом его сразу ждем. (sort.start(); + sort.join();) .
 Это равноценно sort.run();

7. ACTIVE_THREADS изменяется в потоках. Нужна синхронизация или AtomicInteger

8. Для int size = 10000000; у меня не срабатывает последняя сортировка (пишет false)

9. Методы, которые не относятся напрямую к сортировке можно вынести в хелпер
*/
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

        //System.out.println(Arrays.toString(array1));
        System.out.println("Merge sort");
        MergeSort.sort(array1);

        System.out.println("MultithreadMerge sort");
        MultithreadMergeSort.sort(array2);
        System.out.println(Arrays.deepEquals(array1,array2));

        System.out.println("ThreadSortExecutorService merge sort");
        ThreadSortExecutorService.sort(array3);
        System.out.println(Arrays.deepEquals(array1,array3));
    }
}
