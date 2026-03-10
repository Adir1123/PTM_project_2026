package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Topic represents a subject in a publish-subscribe mechanism.
 *
 * Implements the Observer pattern: Topic is the Subject, Agents are Observers.
 * When a message is published, all subscribed Agents are notified via callback.
 */
public class Topic {

    public final String name;
    private final List<Agent> subs;
    private final List<Agent> pubs;

    Topic(String name) {
        this.name = name;
        this.subs = new ArrayList<>();
        this.pubs = new ArrayList<>();
    }

    public void subscribe(Agent a) {
        if (a != null && !subs.contains(a)) {
            subs.add(a);
        }
    }

    public void unsubscribe(Agent a) {
        subs.remove(a);
    }

    public void publish(Message m) {
        if (m == null) return;
        for (Agent a : subs) {
            a.callback(this.name, m);
        }
    }

    public void addPublisher(Agent a) {
        if (a != null && !pubs.contains(a)) {
            pubs.add(a);
        }
    }

    public void removePublisher(Agent a) {
        pubs.remove(a);
    }

    public List<Agent> getSubscribers() {
        return subs;
    }

    public List<Agent> getPublishers() {
        return pubs;
    }
}
