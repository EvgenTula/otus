package cache;
/*
ет. Map<K, CacheEntry<V>>

        CacheEntry содержит SoftReference<V> и Runnable. А в конструкторе получает сам объект и strong-ссылку, к примеру.

        Вроде такого:

        ```private class CacheEntry<V> {
    private final SoftReference<V> value;
    private final Runneble suicide;

    public CacheEntry(V value, Runnable suicide) {
        this.value = new SoftReference(value);
        this.suicide = suicide;
    }

    void itsTimeToDie() {suicide.run()}
}

...
map.put(key, new SoftReference(value, () -> map.remove(key)));```
*/

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class CacheEngine<K,V> {

    private final int maxElements;
    private final Map<K, Element<V>> elements = new HashMap<>();

    public CacheEngine(int maxElements) {
        this.maxElements = maxElements;
    }

    public void put(K key, V element) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }
        elements.put(key, new Element(element,() -> elements.remove(key)));
    }

    public void printSize() {
        int sum = 0;
        for (Map.Entry<K, Element<V>> item : elements.entrySet()) {
            if (item.getValue().getVal() != null) {
                sum++;
            }
        }
        System.out.println("Soft references: " + sum);
    }

    public V get(K key) {
        SoftReference<V> element = elements.get(key).getVal();
        if (element == null)
        {
            //element = new SoftReference<>(key,(V)("Element " + key));
            put(K,/*.....*/);
        }
        return element.get();
    }
}
