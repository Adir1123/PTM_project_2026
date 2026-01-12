package configs;

import graph.Agent;
import graph.ParallelAgent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/*
 * GenericConfig loads Agents from a configuration file.
 * Each Agent is described by 3 lines:
 * 1) full class name
 * 2) subs topics (comma separated)
 * 3) pubs topics (comma separated)
 */


public class GenericConfig implements Config {
    
    private String confFile;
    private final List<Agent> agents = new ArrayList<>();


    public void setConfFile(String confFile){
        this.confFile = confFile;
    }

    @Override
    public void create(){

        // clearing previous config
        close();
        agents.clear();

        List<String> lines = readLines();

        if (lines.size() % 3 != 0){
            throw new RuntimeException("Invalid config file: number of lines is not divisible by 3");
        }

        for(int i=0; i<lines.size(); i+=3){

            String className = lines.get(i);
            String[] subs = splitByComma(lines.get(i + 1));
            String[] pubs = splitByComma(lines.get(i + 2));

            Agent a = createAgent(className, subs, pubs);

            Agent wrapped = new ParallelAgent(a);

            agents.add(wrapped);

        }
    }

    
    @Override
    public void close(){

        for (Agent a: agents){

            try{
                a.close();
            }catch (Exception ignored){}
        }
    }

    @Override
    public String getName() {
        return "GenericConfig";
    }

    @Override
    public int getVersion() {
        return 1;
    }



    private List<String> readLines() {

        if (confFile == null) {
            throw new RuntimeException("confFile was not set (call setConfFile first)");
        }

        List<String> lines = new ArrayList<>();


        // Trying to read
        try (BufferedReader br = new BufferedReader(new FileReader(confFile))){

            String line;
            while ((line = br.readLine()) != null){
                line = line.trim();
                if (!line.isEmpty())
                    lines.add(line);
            }


        } catch (Exception e){
            throw new RuntimeException("Failed to read config file: " + confFile, e);
        }

        return lines;
    }


    private String[] splitByComma(String line) {

        line = line.trim();
        if (line.isEmpty()) {
            return new String[0];
        }

        String[] parts = line.split(",");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }


    private Agent createAgent(String className, String[] subs, String[] pubs) {
        try {
            Class<?> c = Class.forName(className);

            // constructor with (String[], String[])
            Constructor<?> ctor = c.getConstructor(String[].class, String[].class);

            Object obj = ctor.newInstance(subs, pubs);

            return (Agent) obj;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to create agent: " + className, e);
        }
    }

}
