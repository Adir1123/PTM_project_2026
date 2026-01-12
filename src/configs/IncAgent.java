package configs;
import graph.Agent;
import graph.Message;
import graph.TopicManagerSingleton;


public class IncAgent implements Agent{
    
    private final String[] subs;
    private final String[] pubs;


    public IncAgent(String[] subs, String[] pubs){

        this.subs = subs;
        this.pubs = pubs;

        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();

        if (subs != null && subs.length >= 1){
            tm.getTopic(subs[0]).subscribe(this);
        }

    }

    @Override
    public String getName(){
        return "IncAgent";
    }

    @Override
    public void reset(){
        // no need for reset
    }

    @Override
    public void callback(String topic, Message msg){

        if (msg == null || pubs == null || subs == null || pubs.length < 1 || subs.length < 1 ){
            return;
        }

        if (topic == null || !topic.equals(subs[0])){
            return;
        }

        double val = msg.asDouble;
        if (Double.isNaN(val))
            return;


        double result = val + 1.0;

        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();

        tm.getTopic(pubs[0]).publish(new Message(result));

    }

    @Override
    public void close(){

        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();

        if (subs != null && subs.length >= 1){

            tm.getTopic(subs[0]).unsubscribe(this);

        }

        if (pubs != null && pubs.length >= 1){

            tm.getTopic(pubs[0]).removePublisher(this);

        }


    }

}
