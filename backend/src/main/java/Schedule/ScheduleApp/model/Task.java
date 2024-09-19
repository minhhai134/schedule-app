package Schedule.ScheduleApp.helper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {

    private final  int taskID;

    private final int duration;

    private HashMap<String, Integer> requiredSkills;

    private ArrayList<Integer> predecessors;

    private ArrayList<Integer> compatibleResources = new ArrayList<>();

    public Task(int taskID, int duration, HashMap<String, Integer> requiredSkills, ArrayList<Integer> predecessors) {
        this.taskID = taskID;
        this.duration = duration;
        this.predecessors = predecessors;
        this.requiredSkills = requiredSkills;
    }

    public int getTaskID() {
        return taskID;
    }

    public int getDuration() {
        return duration;
    }


    public HashMap<String, Integer> getRequiredSkills() {
        return requiredSkills;
    }

    public ArrayList<Integer> getPredecessors() {
        return predecessors;
    }

    public ArrayList<Integer> getCompatibleResources() {
        return compatibleResources;
    }



    public void addRequiredSkill(String skillID, int level) {
        this.requiredSkills.put(skillID, Integer.valueOf(level));
    }

    public void addPredecessor(int predecessorID) {
        this.predecessors.add(Integer.valueOf(predecessorID));
    }

    public void addcompatibleResources(int resourceID) {
        this.compatibleResources.add(Integer.valueOf(resourceID));
    }

    public int getRandomRsrcId(){
        int rand = (int) ((compatibleResources.size())* Math.random());
        int RsrcId = Integer.parseInt(compatibleResources.get(rand).toString());

        return RsrcId;
    }

}

