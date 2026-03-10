package configs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graph.Message;

public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setEdges(List<Node> edges) {
        this.edges = edges;
    }

    public List<Node> getEdges() {
        return this.edges;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public Message getMsg() {
        return this.msg;
    }

    public void addEdge(Node node) {
        edges.add(node);
    }

    public boolean hasCycles() {
        return hasCyclesDfs(new HashSet<>(), new HashSet<>());
    }

    private boolean hasCyclesDfs(Set<Node> visited, Set<Node> inPath) {
        if (inPath.contains(this))
            return true;

        if (visited.contains(this))
            return false; // Already checked this Node, no cycles found from here

        visited.add(this);
        inPath.add(this);

        for (Node child : edges) {
            if (child != null && child.hasCyclesDfs(visited, inPath))
                return true;
        }

        inPath.remove(this);
        return false;
    }
}
