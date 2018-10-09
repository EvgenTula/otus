package cache;

import java.lang.ref.SoftReference;

public class Element<V> {

    private final byte[] array = new byte[1024 * 1024];

    private final SoftReference<V> softReference;
    private final Runnable destroy;
    public Element(V val, Runnable destroy) {
        this.softReference = new SoftReference(val);
        this.destroy = destroy;
    }

    public SoftReference<V> getVal() {
        return this.softReference;
    }
}
