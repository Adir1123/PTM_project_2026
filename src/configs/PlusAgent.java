package configs;
import graph.Agent;
import graph.Message;
import graph.TopicManagerSingleton;

/**
 * PlusAgent is a binary computational Agent.
 *
 * The agent subscribes to two input Topics (subs[0], subs[1]).
 * When both inputs receive valid Double messages, it computes their sum
 * and publishes the result to the output Topic (pubs[0]).
 *
 * This agent is designed to be configured dynamically via GenericConfig.
 */


public class PlusAgent implements Agent{

    private final String[] subs;
    private final String[] pubs;

    private double x = 0.0;
    private double y = 0.0;

    private boolean hasX = false;
    private boolean hasY = false;


    public PlusAgent(String[] subs, String[] pubs){
        
        this.subs = subs;
        this.pubs = pubs;

        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();

        if (subs != null && subs.length >= 2){

            tm.getTopic(subs[0]).subscribe(this);
            tm.getTopic(subs[1]).subscribe(this);
        }

        
        if (pubs != null && pubs.length >= 1){
            
            tm.getTopic(pubs[0]).addPublisher(this);
            
        }

    }

    @Override
    public String getName(){
        return "PlusAgent";
    }

    @Override
    public void reset(){
        x = 0.0;
        y = 0.0;
        hasX = false;
        hasY = false;
    }
    

    @Override
    public void callback(String topic, Message msg){

        
        if (msg == null || subs == null || pubs == null || subs.length < 2 || pubs.length < 1 ){
            return;
        }

        // checking if msg can be asDouble

        double val = msg.asDouble;
        if (Double.isNaN(val)){
            return;
        }


        if (topic != null && topic.equals(subs[0])){

            x = val;
            hasX = true;

        } else if (topic != null && topic.equals(subs[1])){

            y = val;
            hasY = true;

        } else {

            return;

        }

        if (hasX && hasY){

            double result = x + y;
            TopicManagerSingleton.get().getTopic(pubs[0]).publish(new Message(result));

        }
    }


    @Override
    public void close(){

        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();

        if (subs != null && subs.length >= 2){

            tm.getTopic(subs[0]).unsubscribe(this);
            tm.getTopic(subs[1]).unsubscribe(this);

        }

        if (pubs != null && pubs.length >= 1){

            tm.getTopic(pubs[0]).removePublisher(this);

        }
    }
}