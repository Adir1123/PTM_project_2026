package graph;
import java.util.function.BinaryOperator;



public class BinOpAgent implements Agent {
    
    private final String name;
    private final String inTopic1;
    private final String inTopic2;
    private final String outTopic;
    private final BinaryOperator<Double> op;
    private double x;
    private double y;

    public BinOpAgent(String name,
                      String inTopic1,
                      String inTopic2,
                      String outTopic,
                      BinaryOperator<Double> op) {

        this.name = name;
        this.inTopic1 = inTopic1;
        this.inTopic2 = inTopic2;
        this.outTopic = outTopic;
        this.op = op;

        reset();
        
        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();
        tm.getTopic(inTopic1).subscribe(this);
        tm.getTopic(inTopic2).subscribe(this);
        tm.getTopic(outTopic).addPublisher(this);

}

@Override
public String getName(){
    return this.name;
}

@Override
public void reset(){
    this.x = 0.0;
    this.y = 0.0;
}

@Override
public void callback(String topic, Message msg){

    if(msg == null){
        return;
    }


    double val = msg.asDouble;

    if(Double.isNaN(val)){
        return;
    }

    
    if(topic != null && topic.equals(inTopic1)){
        x = val;
    } else if (topic != null && topic.equals(inTopic2)){
        y = val;
    } else {
        return;
    }


    double result = op.apply(x, y);

    TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();
    tm.getTopic(outTopic).publish(new Message(result));

  
}


@Override
public void close() {
    TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();
    tm.getTopic(inTopic1).unsubscribe(this);
    tm.getTopic(inTopic2).unsubscribe(this);
    tm.getTopic(outTopic).removePublisher(this);
}

}