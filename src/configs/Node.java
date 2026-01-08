package configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import graph.Message;


public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;


    public Node (String name){

        this.name = name;
        this.edges = new ArrayList<>();
        this.msg = null;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setEdges(List<Node> edges){
        this.edges = edges;
    }

    public List<Node> getEdges(){
        return this.edges;
    }

    public void setMsg(Message msg){
        this.msg = msg;
    }

    public Message getMsg(){
        return this.msg;
    }

    public void addEdge(Node node){
        edges.add(node);
    }

    public boolean hasCycles(){

        return hasCyclesDfs(new HashSet<>() ,new HashSet<>());

    }


    private boolean hasCyclesDfs(Set<Node> visited, Set<Node> inPath){

        if(inPath.contains(this))
            return true; // back edge = cycle

        if(visited.contains(this))
            return false; // Already checed this Node, no cycles found from here


        // If im here, I need to the Node as visited and as inPath
        visited.add(this); 
        inPath.add(this);

        // edges = List of Node's childerns
        for(Node currentThisChild : edges){

            if(currentThisChild != null && currentThisChild.hasCyclesDfs(visited, inPath))
                return true;

        }

        
        inPath.remove(this);
        return false;

        
    }
}