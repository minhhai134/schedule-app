package Schedule.ScheduleApp;

import Schedule.ScheduleApp.helper.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataLoader {
    private final String filename = "D:\\PRJ2\\CODE\\Sample code\\Class-Scheduler-Sample3\\src\\main\\java\\GeneticAlgorithm\\ClassScheduler\\data.txt";

//    private Map<Integer, Task> taskMap = new HashMap<>();

    public static Schedule initializeSchedule() throws IOException {

        HashMap<Integer, Resource> resources = new HashMap<>();
        HashMap<Integer, Task> tasks = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader("D:\\PRJ2\\CODE\\Sample code\\DamMinhHai_ProjectII\\src\\main\\java\\GeneticAlgorithm\\ClassScheduler\\data.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue; // Skip empty lines

            line = line.replaceAll("\\s+", " ");
            if (line.contains("=")) continue;

            String[] parts = line.trim().split(" ");

            // Check if the line is for resources or tasks by checking for a decimal point
            if (parts[1].contains(".")) {  // Resource line detected by salary presence
                int resourceID = Integer.parseInt(parts[0]);
                double salary = Double.parseDouble(parts[1]);

                HashMap<String, Integer> skills = new HashMap<>();
                for (int i = 2; i < parts.length; i++) {
                    String[] skillParts = parts[i].split(":");
                    String skillID = skillParts[0];
                    int level = Integer.parseInt(skillParts[1]);
                    skills.put(skillID, Integer.valueOf(level));
                }
                Resource resource = new Resource(resourceID, salary, skills);

                resources.put(Integer.valueOf(resourceID), resource);
            } else if (parts[0].contains("=")) {
                continue;
            } else {  // Task line
                int taskID = Integer.parseInt(parts[0]);
                int duration = Integer.parseInt(parts[1]);


                // Handle the required skill
                HashMap<String, Integer> requiredSkills = new HashMap<>();
                String[] skillPart = parts[2].split(":");
                requiredSkills.put(skillPart[0], Integer.parseInt(skillPart[1]));


                // Handle predecessors, if any
                ArrayList<Integer> predecessors = new ArrayList<>();
                for (int i = 3; i < parts.length; i++) {
                    int predecessorID = Integer.parseInt(parts[i]);
                    predecessors.add(predecessorID);
                }

                Task task = new Task(taskID, duration, requiredSkills, predecessors);
                tasks.put(Integer.valueOf(taskID), task);
//                taskMap.put(taskID, task);
            }
        }
        reader.close();

        // Link tasks with their predecessors after all tasks are loaded
//        for (Task task : tasks) {
//            for (Integer predID : task.getPredecessors()) {
//                Task predecessor = taskMap.get(predID);
//                if (predecessor != null) {
//                    task.addPredecessorTask(predecessor);
//                }
//            }
//        }

        for (Integer taskID : tasks.keySet()) {  // Xet lan luot tung task

            HashMap<String, Integer> requiredSkills = tasks.get(taskID).getRequiredSkills(); // Lay ra danh sach requiredSkills

            for (Integer resID : resources.keySet()) { // Xet lan luot tung rsc

                Resource rsc = resources.get(resID);

                for (String skill : rsc.getSkills().keySet()) {  // Xet lan luot tung skill cua rsc
                    if (requiredSkills.containsKey(skill)) {
                        if (requiredSkills.get(skill).intValue() <= rsc.getSkills().get(skill).intValue()) {
                            rsc.addsuitaleTasks(taskID);
                            tasks.get(taskID).addcompatibleResources(Integer.valueOf(resID));
                            break;
                        }
                    }
                }


            }

        }



//TEST:
//        ArrayList<Integer> list = tasks.get(Integer.valueOf(41)).getCompatibleResources();
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).intValue());
//        }

//        ArrayList<Integer> list2 = resources.get(Integer.valueOf(1)).getSuitaleTasks();
//        for (int i = 0; i < list2.size(); i++) {
//            System.out.println(list2.get(i).intValue());
//        }

        Schedule schedule = new Schedule(resources, tasks);
        return schedule;
    }


    //    public taskSchedule initializeSchedule(){
//
//    }
    public static void main(String args[]){

    }

}

