package Schedule.ScheduleApp;


import Schedule.ScheduleApp.helper.*;

import javax.swing.*;
import java.util.Arrays;
public class Individual {
    private static int cnt=0;
    private int[] chromosome;
    private double fitness = -1;

    private int totalTime;

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public double getCost() {
        return cost;
    }

    public int getClashes() {
        return clashes;
    }

    public void setClashes(int clashes) {
        this.clashes = clashes;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    private double cost;

    private int clashes;

    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }






    // Constructors:
    public Individual(int[] chromosome) {
        // Create individualchromosome
        this.chromosome = chromosome;
    }

    public Individual(int chromosomeLength) {
        // Create random individual
        int[] individual;
        individual = new int[chromosomeLength];
        for (int gene = 0; gene < chromosomeLength; gene++) {
            individual[gene] = gene;
        }

        this.chromosome = individual;
    }


    public Individual(Schedule schedule) {
        int numTasks = schedule.getNumofTasks();
        // 1 gene for startTime, 1 for Resource
        int chromosomeLength = numTasks * 2;
        // Create random individual
        int newChromosome[] = new int[chromosomeLength];
        int chromosomeIndex = 0;

        // Loop through tasks

        for (Task task : schedule.getTasksAsArray()) {

            // Add random startTime
            newChromosome[chromosomeIndex] = schedule.getRandomStartTime();
//                if(cnt<10){ JOptionPane.showMessageDialog(null,schedule.getTimeLimit() + " "+newChromosome[chromosomeIndex]);} cnt++;
            chromosomeIndex++;


            // Add random resource
            newChromosome[chromosomeIndex] = task.getRandomRsrcId();
            chromosomeIndex++;

        }

//        if(cnt<10) {
//            String str = new String();
//            for(int i=0;i<newChromosome.length;i++){str+=newChromosome[i];str+=" "; if(i%2!=0) str+="|";}
//            JOptionPane.showMessageDialog(null,str); cnt++;
//        }

        this.chromosome = newChromosome;
    }

    public int[] getChromosome() {
        return this.chromosome;
    }

    public int getChromosomeLength() {
        return this.chromosome.length;
    }

    public void setGene(int offset, int gene) {
        this.chromosome[offset] = gene;
    }

    public int getGene(int offset) {
        return this.chromosome[offset];
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return this.fitness;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String output = "";
        for (int gene = 0; gene < this.chromosome.length; gene++) {
            output += this.chromosome[gene] + ",";
        }
        return output;
    }

    public boolean containsGene(int gene) {
        for (int i = 0; i < this.chromosome.length; i++) {
            if (this.chromosome[i] == gene) {
                return true;
            }
        }
        return false;
    }


    /**
     * Generates hash code based on individual’s
     * chromosome
     *
     * @return Hash value
     */
    @Override
    public int hashCode() {
        int hash = Arrays.hashCode(this.chromosome);
        return hash;
    }

    /**
     * Equates based on individual’s chromosome
     *
     * @param obj
     * @return Equality boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Individual individual = (Individual) obj;
        return Arrays.equals(this.chromosome, individual.chromosome);
    }

}
