package configs;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import graph.Agent;
import graph.Topic;
import graph.TopicManagerSingleton;


public class Graph extends ArrayList<Node> {
    
    public boolean hasCycles(){

        for(Node nd : this){

            if(nd != null && nd.hasCycles()){
                return true;
            }
            
        }

        return false;
    }
    

    // builds the graph from the current TopicManager
 public void createFromTopics() {
    
        this.clear();

        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();
        Collection<Topic> topics = tm.getTopics();

        // map to avoid creating duplicate nodes
        Map<String, Node> nodesByName = new HashMap<>();

        // 1) Create Topic nodes (T<topicName>)
        for (Topic t : topics) {
            String tNodeName = "T" + t.name;
            nodesByName.putIfAbsent(tNodeName, new Node(tNodeName));
        }

        // 2) Create edges Topic -> Agent (subscribers), and Agent -> Topic (publishers)
        for (Topic t : topics) {
            Node topicNode = nodesByName.get("T" + t.name);

            // Topic -> Agents that subscribe to it
            for (Agent a : t.getSubscribers()) {
                if (a == null) continue;

                String aNodeName = "A" + a.getName();
                nodesByName.putIfAbsent(aNodeName, new Node(aNodeName));

                Node agentNode = nodesByName.get(aNodeName);
                topicNode.addEdge(agentNode);
            }

            // Agents that publish to this topic: Agent -> Topic
            for (Agent a : t.getPublishers()) {
                if (a == null) continue;

                String aNodeName = "A" + a.getName();
                nodesByName.putIfAbsent(aNodeName, new Node(aNodeName));

                Node agentNode = nodesByName.get(aNodeName);
                agentNode.addEdge(topicNode);
            }
        }

        // 3) Add all nodes to the graph list (avoid duplicates)
        this.addAll(nodesByName.values());
    }

}