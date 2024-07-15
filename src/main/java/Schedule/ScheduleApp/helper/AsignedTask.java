package Schedule.ScheduleApp.helper;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AsignedTask {
    private static int cnt =0;


    private int taskListID;

    @JsonProperty("taskId")
    private int taskID;

    @JsonProperty("resourceId")
    private int ResourceID;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("startTime")
    private int startTime;

    @JsonProperty("endTime")
    private int endTime;


    public AsignedTask(int taskListID , int taskID, int duration) {
        this.taskListID = taskListID;
        this.taskID = taskID;
        this.duration = duration;
//        if(cnt<10){ JOptionPane.showMessageDialog(null, startTime+ " "+endTime + "|"); cnt++;}
    }

    public void setResourceID(int resourceID) {
        ResourceID = resourceID;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
        endTime = startTime+duration-1;
//        if(cnt<10){ JOptionPane.showMessageDialog(null, startTime+ " "+endTime + "|"); cnt++;}
    }

    public int getTaskID() {
        return taskID;
    }

    public int getResourceID() {
        return ResourceID;
    }

    public int getDuration() {
        return duration;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getTaskListID() {
        return taskListID;
    }

    public int getEndTime() {
        return endTime;
    }
}

