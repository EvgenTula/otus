import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

public class Main {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {

        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        HashMap<String, GarbageCollectorInfo> list_gc = new HashMap<String, GarbageCollectorInfo>();

        int size = 5 * 1000 * 1000;
        List<Object> array = new ArrayList<Object>();
        while (true)  {

            System.out.println("array sizeof = " + array.size());

            for (int i = 0; i < size; i++) {
                array.add(new String(new char[0]));
            }
            System.out.println("Created " + size + " objects.");

            for (int i = 0; i < size / 2; i++) {
                array.remove(array.size()-1);
            }
            System.out.println("array sizeof after remove = " + array.size());
            printGarbageCollectorInfo(list_gc);
        }
    }

    static class GarbageCollectorInfo {
        GarbageCollectorInfo(String name) {
            this._name = name;
            this._time = 0L;
            this._count = 0L;
        }

        void calculate(GarbageCollectorMXBean gc)  {
            if (gc.getCollectionCount() >=0)
            {
                this._count += gc.getCollectionCount();
                this._time += gc.getCollectionTime();
            }
        }

        void print() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this._name + " -> Count: ").append(this._count)
                    .append(", Time (ms): ").append(this._time);
            System.out.println(stringBuilder);
        }

        private String _name;
        public String getName() { return this._name; }
        private Long _count;
        private Long _time;
    }


    public static void printGarbageCollectorInfo(HashMap<String, GarbageCollectorInfo> list_gc) {

        List<GarbageCollectorMXBean> mxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gc : mxBeans) {
            if (!list_gc.containsKey(gc.getName()))
            {
                GarbageCollectorInfo newItem = new GarbageCollectorInfo(gc.getName());
                list_gc.put(newItem.getName(),newItem);
            }
            list_gc.get(gc.getName()).calculate(gc);
        }
        for (Map.Entry<String,GarbageCollectorInfo> item : list_gc.entrySet()) {
            item.getValue().print();
        }
    }

}
