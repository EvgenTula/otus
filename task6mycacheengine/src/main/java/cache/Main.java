package cache;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int size = 250;
        System.out.println(Thread.activeCount());
        CacheEngine<Integer,Element<Integer, String>> cacheEngine = new CacheEngine<>(size);
        System.out.println(Thread.activeCount());
        cacheEngine.printSize();
        for (int i = 0; i < size; i++) {
            cacheEngine.put(i,new Element<>(i, "Element " + i));
        }
        System.out.println(Thread.activeCount());
        cacheEngine.printSize();
        System.out.println(Thread.activeCount());
        //System.gc();
        //cacheEngine.printSize();
        cacheEngine.dispose();
        System.out.println(Thread.activeCount());
        while (true) {
            Thread.sleep(1000);
            System.out.println(Thread.activeCount());
        }

    }
}
