package Schedule.ScheduleApp;



import Schedule.ScheduleApp.model.AsignedTask;
import Schedule.ScheduleApp.model.Population;
import Schedule.ScheduleApp.model.Schedule;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ScheduleGA {

    @PostMapping("/createSchedule")
    public HashMap<Integer, AsignedTask> createSchedule() {

        Schedule schedule = null;
        try {
            schedule = DataLoader.initializeSchedule();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Initialize GA
        GeneticAlgorithm ga = new GeneticAlgorithm(80, 0.01, 0.85, 2, 6);

        Population population = ga.initPopulation(schedule);

        ga.evalPopulation(population, schedule);

        // Keep track of current generation
        int generation = 1;

        // Start evolution loop
        while (ga.isTerminationConditionMet(generation, 700) == false ) {
            //&& ga.isTerminationConditionMet(population) == false
            // Print fitness


            schedule.setTimeLimit(population.getFittest(0).getTotalTime());

            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population, schedule);

            // Evaluate population
            ga.evalPopulation(population, schedule);

            // Increment the current generation
            generation++;


        }

        // Print fitness
        schedule.createTaskList(population.getFittest(0));


        //Print TaskList:
        System.out.println();
        AsignedTask taskslist[] = schedule.getTasksListAsArray();
        int classIndex = 1;

        System.out.println("Time limit: "+schedule.calcTimeLimit());
        System.out.println("Total time: "+schedule.calcDurationTime());
        System.out.println("Conflict: "+schedule.calcClashes());
        System.out.println("Task conflict: "+schedule.calc1());
        System.out.println("Resource conflict: "+schedule.calc2());

        for (AsignedTask bestTask : taskslist) {
            System.out.print("TaskID: " + bestTask.getTaskID() +" ");
            if(bestTask.getTaskID()>=10) System.out.print(" ");
            else System.out.print(" "+ " ");
            for(int i=0;i<bestTask.getStartTime();i++) {System.out.print(" ");}
            for(int i=bestTask.getStartTime();i<=bestTask.getEndTime();i++){  System.out.print(bestTask.getResourceID());}
            System.out.print("\n");

        }

        return schedule.getTasksList();
    }
}



