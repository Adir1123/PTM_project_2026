package configs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import graph.Agent;
import graph.Topic;
import graph.TopicManagerSingleton;

public class Graph extends ArrayList<Node> {

    public boolean hasCycles() {
        for (Node nd : this) {
            if (nd != null && nd.hasCycles()) {
                return true;
            }
        }
        return false;
    }

    /** Builds the graph from the current TopicManager. */
    public void createFromTopics() {
        this.clear();

        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();
        Collection<Topic> topics = tm.getTopics();

        Map<String, Node> nodesByName = new HashMap<>();

        // Create Topic nodes
        for (Topic t : topics) {
            String tNodeName = "T" + t.name;
            nodesByName.putIfAbsent(tNodeName, new Node(tNodeName));
        }

        // Create edges: Topic -> subscriber Agents, publisher Agents -> Topic
        for (Topic t : topics) {
            Node topicNode = nodesByName.get("T" + t.name);

            for (Agent a : t.getSubscribers()) {
                String aNodeName = "A" + a.getName();
                nodesByName.putIfAbsent(aNodeName, new Node(aNodeName));
                topicNode.addEdge(nodesByName.get(aNodeName));
            }

            for (Agent a : t.getPublishers()) {
                String aNodeName = "A" + a.getName();
                nodesByName.putIfAbsent(aNodeName, new Node(aNodeName));
                nodesByName.get(aNodeName).addEdge(topicNode);
            }
        }

        this.addAll(nodesByName.values());
    }
}
