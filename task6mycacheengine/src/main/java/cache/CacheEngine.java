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
            elements.remove(firstKey);
        }
        SoftReference<V> softReference = new SoftReference<>(element);
        PhantomReferenceItem<K,V> phantomReferenceItem = new PhantomReferenceItem<>(
                key,
                element,
                referenceQueue,
                ()-> { elements.remove(key); });
        softReferenceList.add(softReference);
        phantomReferencesList.add(phantomReferenceItem);
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
    }

    public V get(K key) {

        SoftReference<V> softReferenceElement = elements.get(key);
        if (softReferenceElement == null)
        {
            //TODO: Подгрузка объектов из источника
        }
        return softReferenceElement.get();
    }

    public void dispose() {
        elements.clear();
        for (SoftReference<V> item : softReferenceList) {
            item.clear();
        }
        softReferenceList.clear();


        for (PhantomReferenceItem<K,V> item : phantomReferencesList) {
            item.cleanup();
            item.clear();
        }
        phantomReferencesList.clear();
    }

    private class PhantomReferenceItem<K,T> extends PhantomReference {
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

        public class ReferenceQueueThread extends Thread {

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

                if (reference instanceof CacheEngine.PhantomReferenceItem && reference != null) {
                    ((PhantomReferenceItem) reference).cleanup();
                }
            }
        }
    }
}
