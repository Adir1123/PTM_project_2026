package graph;

/**
 * ParallelAgent is a simple Decorator for Agent.
 * It wraps another Agent and delegates all calls to it.
 */
public class ParallelAgent implements Agent {

    private final Agent inner;

    public ParallelAgent(Agent inner) {
        if (inner == null) throw new IllegalArgumentException("inner agent is null");
        this.inner = inner;
    }

    @Override
    public String getName() {
        return inner.getName();
    }

    @Override
    public void reset() {
        inner.reset();
    }

    @Override
    public void callback(String topic, Message msg) {
        inner.callback(topic, msg);
    }

    @Override
    public void close() {
        inner.close();
    }
}
