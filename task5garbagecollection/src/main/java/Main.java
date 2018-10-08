import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        int size = 5 * 1000 * 1000;
        List<Object> array = new ArrayList<Object>();
        while (true)  {
            for (int i = 0; i < size; i++) {
                array.add(new String(new char[0]));
            }
            for (int i = 0; i < size / 2; i++) {
                array.remove(array.size()-1);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getGarbageCollectorInfo();
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

        String print() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this._name + " -> Count: ").append(this._count)
                    .append(", Time (ms): ").append(this._time).append(" ; ");
            return stringBuilder.toString();
        }

        private String _name;
        public String getName() { return this._name; }
        private Long _count;
        private Long _time;
    }


    public static void getGarbageCollectorInfo() {
        HashMap<String, GarbageCollectorInfo> result = new HashMap<>();
        List<GarbageCollectorMXBean> mxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gc : mxBeans) {
            if (!result.containsKey(gc.getName()))
            {
                GarbageCollectorInfo newItem = new GarbageCollectorInfo(gc.getName());
                result.put(newItem.getName(),newItem);
            }
            result.get(gc.getName()).calculate(gc);
        }
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String,GarbageCollectorInfo> item : result.entrySet()) {
            str.append(item.getValue().print());
        }
        String timeStamp = new SimpleDateFormat("dd MM YYYY HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("[" + timeStamp + "] " + str);
    }
}
