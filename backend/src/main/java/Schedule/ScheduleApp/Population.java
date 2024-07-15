package Schedule.ScheduleApp;


import Schedule.ScheduleApp.Individual;
import Schedule.ScheduleApp.Schedule;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
public class Population {

    private Individual population[];
    private double populationFitness = -1;

    public Population(int populationSize) {   // KHOI TAO MOT POPULATION rong, dung trong crossOver, selectParent, mutatePopulation
        // Initial population
        this.population = new Individual[populationSize];
    }
// DOAN NAY LA TU COMMENT LAI DE TEST XEM KHI XOA DI CHUONG TRINH CO CHAY ON KHONG
//    public Population(int populationSize, int chromosomeLength) {
//        // Initial population
//        this.population = new Individual[populationSize];
//
//        // Loop over population size
//        for (int individualCount = 0; individualCount < populationSize; individualCount++) {
//            // Create individual
//            Individual individual = new Individual(chromosomeLength);
//            // Add individual to population
//            this.population[individualCount] = individual;
//        }
//    }

    public Population(int populationSize, Schedule schedule) {   // KHOI TAO POPULATION CO GIA TRI NGAU NHIEN
        // Initial population                                    // CHI DUNG MOT LAN DE KHOI TAO O PHAN DAU CUA CHUONG TRINH
        this.population = new Individual[populationSize];

        // Loop over population size
        for (int individualCount = 0; individualCount < populationSize; individualCount++) {
            // Create individual
            Individual individual = new Individual(schedule);  // KHOI TAO MOT CA THE VOI GIA TRI NGAU NHIEN BANG mot object schedule
            // Add individual to population
            this.population[individualCount] = individual;
        }
    }

    /**
     * Get average fitness
     *
     * @return The average individual fitness
     */
    public double getAvgFitness() {    // => DUNG CHO DOT BIEN- MUTATION
        if (this.populationFitness == -1) {
            double totalFitness = 0;
            for (Individual individual : population) {
                totalFitness += individual.getFitness();
            }

            this.populationFitness = totalFitness;
        }

        return populationFitness / this.size();
    }

    public Individual[] getIndividuals() {
        return this.population;
    }

    // Lay ca the co do fitness cao thu "offset"
    public Individual getFittest(int offset) {
        // Order population by fitness
        Arrays.sort(this.population, (Individual o1, Individual o2) -> {  // SAP XEP QUAN THE THEO THU TU FITNESS GIAM DAN
            if (o1.getFitness() > o2.getFitness()) {
                return -1;
            } else if (o1.getFitness() < o2.getFitness()) {
                return 1;
            }
            return 0;
        });

        // Return the fittest individual
        return this.population[offset];
    }

    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }

    public double getPopulationFitness() {
        return this.populationFitness;
    }

    public int size() {
        return this.population.length;
    }

    public Individual setIndividual(int offset, Individual individual) {
        return population[offset] = individual;
    }

    public Individual getIndividual(int offset) {
        return population[offset];
    }


    public void shuffle() {   // => TRAO DOI THU TU CAC CA THE TRONG QUAN THE
        Random rnd = new Random();
        for (int i = population.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Individual a = population[index];
            population[index] = population[i];
            population[i] = a;
        }
    }

}

