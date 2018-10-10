package cache;

public class Main {
    public static void main(String[] args) {

        int size = 250;

        CacheEngine<Integer,Element<Integer, String>> cacheEngine = new CacheEngine<>(size);

        cacheEngine.printSize();
        for (int i = 0; i < size; i++) {
            cacheEngine.put(i,new Element<>(i, "Element " + i));
        }

        cacheEngine.printSize();
        //System.gc();
        //cacheEngine.printSize();
        //cacheEngine.dispose();
    }
}
