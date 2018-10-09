package cache;

public class Main {
    public static void main(String[] args) {

        int size = 250;

        CacheEngine<Integer,String> cacheEngine = new CacheEngine<>(size);
        for (int i = 0; i < size; i++) {
            cacheEngine.put(new Element<>(i,"Elemtent " + i));
        }
        cacheEngine.printSize();
        //System.gc();
        cacheEngine.printSize();
    }
}
