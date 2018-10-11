package cache;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class CacheEngine<K,V> {

    private final int maxElements;
    private final Map<K,SoftReference<V>> elements = new HashMap<>();
    private final ReferenceQueue<V> referenceQueue = new ReferenceQueue<>();

    private final Thread monitorReferenceQueue = new Thread(() -> {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (elements) {
                    Reference reference = referenceQueue.poll();
                    if (reference == null) {
                        Thread.sleep(100);
                    } else if (reference instanceof SoftReferenceItem) {
                        SoftReferenceItem softReferenceItem = (SoftReferenceItem) reference;
                        elements.remove(softReferenceItem.getKey());
                        softReferenceItem.cleanup();
                    }
                }
            }
        }catch (Exception ex) {
            System.out.println("monitorReferenceQueue stop");
        }
    });

    public CacheEngine(int maxElements) {
        this.maxElements = maxElements;
        monitorReferenceQueue.start();
    }

    public void put(K key, V element) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }
        SoftReference<V> softReference = new SoftReferenceItem<>(key, element, referenceQueue);
        elements.put(key, softReference);
    }

    public void printSize() {
        while (referenceQueue.poll() != null)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        monitorReferenceQueue.interrupt();
        elements.clear();
    }

    private class SoftReferenceItem<K,V> extends SoftReference {
        private final K key;
        public SoftReferenceItem(K key, V obj, ReferenceQueue queue) {
            super(obj, queue);
            this.key = key;
        }

        public K getKey() {
            return this.key;
        }

        public void cleanup() {
            super.clear();
        }
    }
}
