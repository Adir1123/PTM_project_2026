package graph;

import java.util.ArrayList;
import java.util.List;



/**
 * Topic represents a subject in a publish-subscribe mechanism.
 * 
 * This class implements the Observer design pattern:
 * - Topic acts as the Subject.
 * - Agents act as Observers that subscribe to this Topic.
 * - When a message is published, all subscribed Agents
 *   are notified via their callback method.
 */



public class Topic {

    public final String name; // name of the topic
    private final List<Agent> subs;
    private final List<Agent> pubs;



    Topic(String name){
        this.name=name;
        this.subs = new ArrayList<>();
        this.pubs = new ArrayList<>();
    }


    // Adding subscriber Agent a
    public void subscribe(Agent a){ 
        
        if(a != null && !subs.contains(a)){

            subs.add(a);

        }

    }

    // Removing subscriber Agent a
    public void unsubscribe(Agent a){

        subs.remove(a); 

    }


    // Publishes a message to all subscribed Agents (observers)

    public void publish(Message m){

        if(m == null)
            return;

       for (Agent a : subs){
            a.callback(this.name, m);
       }
       
    }

    public void addPublisher(Agent a){

           if(a != null && !pubs.contains(a)){

            pubs.add(a);

        }

    }

    public void removePublisher(Agent a){

         pubs.remove(a);

    }

    public List<Agent> getSubscribers() {
        return subs;
    }

    public List<Agent> getPublishers() {
        return pubs;
    }  


}
