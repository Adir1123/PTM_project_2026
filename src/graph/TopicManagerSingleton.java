package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * TopicManagerSingleton provides a thread-safe and lazy-loaded
 * access point to a single instance of TopicManager.
 */
public class TopicManagerSingleton {

    /**
     * Static inner class holding the single TopicManager instance.
     * Not loaded until first referenced, guaranteeing lazy initialization.
     */
    public static class TopicManager {

        private static final TopicManager instance = new TopicManager();
        private final Map<String, Topic> topics = new HashMap<>();

        private TopicManager() {}

        public Topic getTopic(String name) {
            if (name == null) throw new IllegalArgumentException("Topic name can't be null");
            return topics.computeIfAbsent(name, Topic::new);
        }

        public Collection<Topic> getTopics() {
            return topics.values();
        }

        public void clear() {
            topics.clear();
        }
    }

    public static TopicManager get() {
        return TopicManager.instance;
    }
}
