package cache;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheEngine<K,V> {

    private final int maxElements;
    private final Map<K,SoftReference<V>> elements = new HashMap<>();
    private final List<SoftReference<V>> softReferenceList = new ArrayList<>();
    private final List<PhantomReferenceItem<K,V>> phantomReferencesList = new ArrayList<>();
    private final ReferenceQueue<V> referenceQueue = new ReferenceQueue<>();

    public CacheEngine(int maxElements) {
        this.maxElements = maxElements;
    }

    public void put(K key, V element) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            //SoftReference<V> removeItem = elements.remove(firstKey);
            //softReferenceList.remove(softReferenceList.stream().filter(i -> i.key == firstKey).findFirst());
            //softReferenceList.remove(removeItem);
        }
        SoftReference<V> softReference = new SoftReference<>(element);
        PhantomReferenceItem<K,V> phantomReferenceItem = new PhantomReferenceItem<>(
                key,
                element,
                referenceQueue,
                ()-> {
                    elements.remove(key);
                    phantomReferencesList.removeIf(i -> i.key.equals(key));
                    softReferenceList.removeIf(i -> i.equals(softReference));
                    this.printSize();
                });

        //SoftReferenceItem<K, V> softReference = new SoftReferenceItem<>(key,element,referenceQueue,()->{ elements.remove(key);
        //softReferenceList.removeIf(i -> i.key == key);
        /* softReferenceList.remove(softReferenceList.stream().filter(i -> i.key == key).findFirst()); */
        //});

        softReferenceList.add(softReference);

        //phantomReferencesList.add(new PhantomReferenceItem<>(key,element,referenceQueue,()-> { elements.remove(key); this.printSize(); phantomReferencesList.removeIf(i -> i.key.equals(key)); }));
        elements.put(key, softReference);
    }

    public void printSize() {
        int cntKey = 0;
        int cntValue = 0;

        for (Map.Entry<K, SoftReference<V>> item : elements.entrySet()) {
            cntKey++;
            if (item.getValue().get() != null) {
                cntValue++;
            }
        }

        System.out.println("Map: key = " + cntKey + " ; value = " + cntValue);
        System.out.println("Soft references: " + softReferenceList.size() + " ; Phantom references: " + phantomReferencesList.size());
    }

    public V get(K key) {

        SoftReference<V> softReferenceElement = elements.get(key);
        if (softReferenceElement == null)
        {
            //TODO: Подгрузка объектов из источника
            //Element<K,V> item = new Element<K, V>;
            //new Element(element,() -> elements.remove(key)
            //element = new SoftReference<>(key,(V)("Element " + key));
            //put(K, element);

        }
        return softReferenceElement.get();
    }

    public void dispose() {
        elements.clear();
        softReferenceList.clear();
        //phantomReferencesList.clear();
    }

    private static class PhantomReferenceItem<K,T> extends PhantomReference {
        private final K key;
        private final Runnable destroy;
        public PhantomReferenceItem(K key,T obj, ReferenceQueue queue, Runnable destroy) {
            super(obj, queue);
            this.key = key;
            this.destroy = destroy;
            Thread thread = new ReferenceQueueThread(this,queue);
            thread.start();
        }

        public K getKey() {
            return this.key;
        }

        public void cleanup() {
            destroy.run();
            super.clear();
        }

        public static class ReferenceQueueThread<T> extends Thread {

            private final ReferenceQueue referenceQueue;
            private final PhantomReferenceItem phantomReference;
            public ReferenceQueueThread(PhantomReferenceItem phantomReference, ReferenceQueue referenceQueue) {
                this.referenceQueue = referenceQueue;
                this.phantomReference = phantomReference;
            }

            @Override
            public void run() {

                Reference reference = null;

                while ((reference = referenceQueue.poll()) == null) {
                    try {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException("Thread " + getName() + " has been interrupted");
                    }
                }

                if (reference instanceof CacheEngine.PhantomReferenceItem) {
                    ((PhantomReferenceItem) reference).cleanup();
                }
            }
        }
    }
}
