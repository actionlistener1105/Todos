package org.example;

public class Meeting extends Task{

    protected String topic;
    protected String project;
    protected String start;

    public Meeting(int id, String topic, String project, String start){
        super(id);
        this.topic = topic;
        this.project = project;
        this.start = start;
    }

    @Override
    public boolean matches(String query){
        if(topic.contains(query)){
            return true;
        }
        if(project.contains(query)){
            return true;
        }
        if(start.contains(query)){
            return true;
        }
        return false;
    }

    public String getTopic(){
        return topic;
    }

    public String getProject(){
        return project;
    }

    public String getStart(){
        return start;
    }
}
