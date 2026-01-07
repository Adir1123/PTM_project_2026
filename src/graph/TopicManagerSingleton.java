package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/*
  TopicManagerSingleton provides a thread-safe and lazy-loaded
  access point to a single instance of TopicManager.
 */

public class TopicManagerSingleton {


    /*
      static inner class responsible for holding the single instance of TopicManager. TopicManager won't be loaded unitil it's  
      referenced for the first time which guarantees Lazy initialization
    */
    
    public static class TopicManager{

     
      private static final TopicManager instance = new TopicManager();

      private final Map<String,Topic> topics = new HashMap<>();

      private TopicManager(){}  // private constructor so only this class can create its own instance


      public Topic getTopic(String name){

        if(name == null) throw new IllegalArgumentException("Topic name can't be null");

        Topic t = topics.get(name);

        if(t == null){

          t = new Topic(name);
          topics.put(name,t);
          
        }
        
        return t;
      }


      public Collection<Topic> getTopics(){
  
        return topics.values();

      }


      public void clear(){
  
        topics.clear();

      }
    
  }

    // Calling this method triggers the loading of the inner TopicManager class
    public static TopicManager get(){

      return TopicManager.instance;

    }
    
}
