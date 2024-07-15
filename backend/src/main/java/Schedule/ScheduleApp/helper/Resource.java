package Schedule.ScheduleApp.helper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Resource {

    private int resourceID;

    public HashMap<String, Integer> getSkills() {
        return skills;
    }

    public ArrayList<Integer> getSuitaleTasks() {
        return suitaleTasks;
    }

    private double salary;

    private HashMap<String, Integer> skills = new HashMap<>();

    private ArrayList<Integer> suitaleTasks = new ArrayList<>();

    public Resource(int resourceID, double salary, HashMap<String, Integer> skills) {
        this.resourceID = resourceID;
        this.salary = salary;
        this.skills = skills;
    }

    public int getResourceID() {
        return resourceID;
    }

    public double getSalary() {
        return salary;
    }

    public void addSkill(String skillID, int level) {
        skills.put(skillID, Integer.valueOf(level));
    }

    public void addsuitaleTasks(int taskID) {
        this.suitaleTasks.add(Integer.valueOf(taskID));
    }
}

