import java.lang.instrument.Instrumentation;

public class AgentMemory {
    private static Instrumentation _instrumentation;

    public static void premain(String args, Instrumentation instrumentation) {
        AgentMemory._instrumentation = instrumentation;
    }

    public static long sizeof(Object obj) {
        if (AgentMemory._instrumentation != null) {
            return AgentMemory._instrumentation.getObjectSize(obj);
        }
        throw new IllegalStateException("AgentMemory._instrumentation is null");
    }
}
