package org.example;

public class Epic extends Task{

    protected String[] subTasks;

    public Epic(int id, String[] subTasks){
        super(id);
        this.subTasks = subTasks;
    }

    @Override
    public boolean matches(String query){
        for (int i = 0; i < subTasks.length; i++) {
            if(subTasks[i].contains(query)){
                return true;
            }
        }
        return false;
    }

    public String[] getSubTasks(){
        return subTasks;
    }
}
