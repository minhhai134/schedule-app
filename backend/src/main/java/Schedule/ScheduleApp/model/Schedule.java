package Schedule.ScheduleApp;


import Schedule.ScheduleApp.helper.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Schedule {

    private HashMap<Integer, AsignedTask>  tasksList;

    private static  int cnt=0;
    private final HashMap<Integer, Resource> resources;
    private final HashMap<Integer, Task> tasks;


    public Schedule(HashMap<Integer, Resource> resources, HashMap<Integer, Task> tasks) {
        this.resources = resources;
        this.tasks = tasks;
        timeLimit = calcTimeLimit();
    }

    public Schedule(){
        this.resources = new HashMap<>();
        this.tasks = new HashMap<>();
        timeLimit = calcTimeLimit();
    }

    public Schedule(Schedule schedule){
        this.resources = schedule.getResources();
        this.tasks = schedule.getTasks();
        timeLimit = calcTimeLimit();
//        if(cnt<10) JOptionPane.showMessageDialog(null,timeLimit); cnt++;
    }


    public HashMap<Integer, Resource> getResources() {
        return resources;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }



    // add new Task
    public void addTask(int taskID, int duration, HashMap<String, Integer> requiredSkills, ArrayList<Integer> predecessors) {
        this.tasks.put(taskID, new Task(taskID, duration,requiredSkills,predecessors ));
    }

    // add new Resource
    public void addResource(int resourceId, double salary, HashMap<String, Integer> skills) {
        this.resources.put(resourceId, new Resource(resourceId, salary, skills));
    }

    public int getNumofTasks(){ return this.tasks.size();}

    public Task[] getTasksAsArray() {
        return (Task[]) this.tasks.values().toArray(new Task[this.tasks.size()]);
    }


    public void createTaskList(Individual individual) {
        // Init tasksList
        HashMap<Integer, AsignedTask>  tasksList = new HashMap<>();

        // Get individual’s chromosome
        int chromosome[] = individual.getChromosome();
        int chromosomePos = 0;
        int taskListIndex = 0;

        for (Task task : this.getTasksAsArray()) {
//            int taskIds[] = task.getCourseIds();

            tasksList.put(task.getTaskID(),new AsignedTask(taskListIndex, task.getTaskID(), task.getDuration())) ;

            // Add startTime
            tasksList.get(task.getTaskID()).setStartTime(chromosome[chromosomePos]);
            chromosomePos++;

            // Add ResourceID
            tasksList.get(task.getTaskID()).setResourceID(chromosome[chromosomePos]);
            chromosomePos++;

            taskListIndex++;

        }

        this.tasksList = tasksList;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int calcClashes() {
        int clashes = 0;

        for (Integer taskListID : this.tasksList.keySet()) {

            // Check predecessors
            ArrayList<Integer> predecessors = tasks.get(taskListID).getPredecessors();
            for(int i = 0; i < predecessors.size(); i++) {
                if(tasksList.get(predecessors.get(i)).getEndTime()>= tasksList.get(taskListID).getStartTime()) clashes++;
            }


            // Check resource available
            for (Integer taskListID2 : this.tasksList.keySet()) {
                if(tasksList.get(taskListID).getResourceID()==tasksList.get(taskListID2).getResourceID()){
                    if(tasksList.get(taskListID).getStartTime()>=tasksList.get(taskListID2).getStartTime()
                            &&  tasksList.get(taskListID).getStartTime()<=tasksList.get(taskListID2).getEndTime()
                            &&  taskListID != taskListID2)
                        clashes++;
                }

            }


        }
//        if(cnt<10){ JOptionPane.showMessageDialog(null,clashes);} cnt++;
        return clashes;

    }


    public int calc1() {
        int clashes = 0;

        for (Integer taskListID : this.tasksList.keySet()) {

            // Check predecessors
            ArrayList<Integer> predecessors = tasks.get(taskListID).getPredecessors();
            for(int i = 0; i < predecessors.size(); i++) {
                if(tasksList.get(predecessors.get(i)).getEndTime()>= tasksList.get(taskListID).getStartTime()) clashes++;
            }


        }
//        if(cnt<10){ JOptionPane.showMessageDialog(null,clashes);} cnt++;
        return clashes;

    }


    public int calc2() {
        int clashes = 0;

        for (Integer taskListID : this.tasksList.keySet()) {

            // Check resource available
            for (Integer taskListID2 : this.tasksList.keySet()) {
                if(tasksList.get(taskListID).getResourceID()==tasksList.get(taskListID2).getResourceID()){
                    if(tasksList.get(taskListID).getStartTime()>=tasksList.get(taskListID2).getStartTime()
                            &&  tasksList.get(taskListID).getStartTime()<=tasksList.get(taskListID2).getEndTime()
                            &&  taskListID != taskListID2   ){
                        clashes++;
                        System.out.println("************************");
                        System.out.println(tasksList.get(taskListID).getResourceID()+ "-"+tasksList.get(taskListID2).getResourceID() );
                        System.out.println("T1: "+taskListID+" "+tasksList.get(taskListID).getStartTime() + "-" + tasksList.get(taskListID).getEndTime()  );
                        System.out.println("T2: "+taskListID2+" "+tasksList.get(taskListID2).getStartTime() + "-" + tasksList.get(taskListID2).getEndTime()  );
                        System.out.println("************************");
                        System.out.println();
                    }

                }

            }


        }
//        if(cnt<10){ JOptionPane.showMessageDialog(null,clashes);} cnt++;
        return clashes;

    }


    public int calcDurationTime () {
        int totalTime = 0;

        for (Integer taskListID : this.tasksList.keySet()) {

            if(tasksList.get(taskListID).getEndTime()>= totalTime)
                totalTime = tasksList.get(taskListID).getEndTime() ;
        }

        return totalTime;
    }


    public double calcCost () {
        double cost = 0;

        for (Integer taskListID : this.tasksList.keySet()) {

            cost += resources.get(tasksList.get(taskListID).getResourceID()).getSalary()*tasksList.get(taskListID).getDuration();

        }

        return cost;
    }




    private  int timeLimit;

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int calcTimeLimit(){

        int T=0;
        int maxdur =0;
        if(cnt<=0){

        }

        for(Integer taskID : tasks.keySet()){

            T+=tasks.get(taskID).getDuration();
            if(tasks.get(taskID).getDuration()>maxdur) maxdur=tasks.get(taskID).getDuration();
//            if(cnt<10) JOptionPane.showMessageDialog(null,taskID +" "+ tasks.get(taskID).getDuration() +" "+ T); cnt++;
        }
//        if(cnt<10) JOptionPane.showMessageDialog(null, T); cnt++;
        return T-maxdur;
    }

    public HashMap<Integer, AsignedTask> getTasksList() {
        return tasksList;
    }

    public AsignedTask[] getTasksListAsArray() {
        return (AsignedTask[])  this.tasksList.values().toArray(new AsignedTask[this.tasksList.size()]);
    }

    public int getRandomStartTime() {
//        if(cnt<10) JOptionPane.showMessageDialog(null, timeLimit * Math.random()); cnt++;
        return (int) (timeLimit * Math.random());

    }

}

