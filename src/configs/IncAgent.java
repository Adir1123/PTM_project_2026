package configs;

import graph.Agent;
import graph.Message;
import graph.TopicManagerSingleton;

/**
 * IncAgent is a unary computational Agent.
 *
 * Subscribes to a single input Topic (subs[0]).
 * Upon receiving a valid Double message, increments the value by 1
 * and publishes the result to the output Topic (pubs[0]).
 */
public class IncAgent implements Agent {

    private final String[] subs;
    private final String[] pubs;

    public IncAgent(String[] subs, String[] pubs) {
        this.subs = subs;
        this.pubs = pubs;

        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();

        if (subs != null && subs.length >= 1) {
            tm.getTopic(subs[0]).subscribe(this);
        }
        if (pubs != null && pubs.length >= 1) {
            tm.getTopic(pubs[0]).addPublisher(this);
        }
    }

    @Override
    public String getName() {
        return "IncAgent";
    }

    @Override
    public void reset() {}

    @Override
    public void callback(String topic, Message msg) {
        if (msg == null || pubs == null || subs == null || pubs.length < 1 || subs.length < 1) {
            return;
        }

        if (!subs[0].equals(topic)) {
            return;
        }

        double val = msg.asDouble;
        if (Double.isNaN(val)) return;

        double result = val + 1.0;
        TopicManagerSingleton.get().getTopic(pubs[0]).publish(new Message(result));
    }

    @Override
    public void close() {
        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();

        if (subs != null && subs.length >= 1) {
            tm.getTopic(subs[0]).unsubscribe(this);
        }
        if (pubs != null && pubs.length >= 1) {
            tm.getTopic(pubs[0]).removePublisher(this);
        }
    }
}
