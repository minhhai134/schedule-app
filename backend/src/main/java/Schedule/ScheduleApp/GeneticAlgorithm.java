package Schedule.ScheduleApp;


import Schedule.ScheduleApp.model.Individual;
import Schedule.ScheduleApp.model.Population;
import Schedule.ScheduleApp.model.Schedule;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

public class GeneticAlgorithm {

    private static int cnt =0;
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    protected int tournamentSize;

    // implementing multi heuristics
    private double temperature = 1.0;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
                            int tournamentSize) {

        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }

    public Population initPopulation(Schedule schedule) {
        // Initialize population
        Population population = new Population(this.populationSize, schedule);
        return population;
    }

    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
        return (generationsCount > maxGenerations);
    }

    public boolean isTerminationConditionMet(Population population) {
        return population.getFittest(0).getClashes() == 0;
    }

    // Create fitness hashtable
    private Map<Individual, Double> fitnessHash = Collections.synchronizedMap(
            new LinkedHashMap<Individual, Double>() {
                @Override
                protected boolean removeEldestEntry(Entry<Individual, Double> eldest) {
                    // Store a maximum of 1500 fitness values
                    return this.size() > 1500;
                }
            });


    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public int getElitismCount() {
        return elitismCount;
    }

    public void setElitismCount(int elitismCount) {
        this.elitismCount = elitismCount;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Map<Individual, Double> getFitnessHash() {
        return fitnessHash;
    }

    public void setFitnessHash(Map<Individual, Double> fitnessHash) {
        this.fitnessHash = fitnessHash;
    }



    public double calcFitness(Individual individual, Schedule schedule) {
        Double storedFitness = this.fitnessHash.get(individual);
        if (storedFitness != null) {
            return storedFitness;
        }

        // Create new Schedule object for thread
        Schedule threadSchedule = new Schedule(schedule);

        threadSchedule.createTaskList(individual);


//**************************************************************************

        int clashes = threadSchedule.calcClashes();


        int totalTime = threadSchedule.calcDurationTime();


        double cost = threadSchedule.calcCost();

        individual.setClashes(clashes);

        individual.setTotalTime(totalTime);

        individual.setCost(cost);

//**************************************************************************


//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        double fitness = 1 /( (double) (clashes + 1)*totalTime);

        individual.setFitness(fitness);

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // Store fitness in hashtable
        this.fitnessHash.put(individual, fitness);

        return fitness;
    }

    public void evalPopulation(Population population, Schedule schedule) {

        IntStream.range(0, population.size()).parallel()
                .forEach((int i) -> {
                    GeneticAlgorithm.this.calcFitness(population.getIndividual(i), schedule);
                });

        double populationFitness = 0;

        // Loop over population evaluating individuals and summing population
        // fitness
        for (Individual individual : population.getIndividuals()) {
            double a= this.calcFitness(individual, schedule);

            populationFitness += a;
        }

        population.setPopulationFitness(populationFitness);
    }



    public Individual selectParent(Population population) {
        // Create tournament
        Population tournament = new Population(this.tournamentSize);

        // Add random individuals to the tournament
        population.shuffle();
        for (int i = 0; i < this.tournamentSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }

        // Return the best
        return tournament.getFittest(0);
    }


    public Population crossoverPopulation(Population population) {
        // Create new population
        Population newPopulation = new Population(population.size());

        // Loop over current population by fitness

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {

            Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual

            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                Individual offspring = new Individual(parent1.getChromosomeLength());


                Individual parent2 = selectParent(population);

                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    // Use half of parent1's genes and half of parent2's genes
                    if (0.5 > Math.random()) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }


                newPopulation.setIndividual(populationIndex, offspring);
            } else {

                newPopulation.setIndividual(populationIndex, parent1);
            }
        }

        return newPopulation;
    }



    public Population mutatePopulation(Population population, Schedule schedule) {
        // Initialize new population
        Population newPopulation = new Population(this.populationSize);


        double bestFitness = population.getFittest(0).getFitness();

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);


            Individual randomIndividual = new Individual(schedule);


            double adaptiveMutationRate = this.mutationRate;
            if (individual.getFitness() > population.getAvgFitness()) {
                double fitnessDelta1 = bestFitness - individual.getFitness();
                double fitnessDelta2 = bestFitness - population.getAvgFitness();
                adaptiveMutationRate = (fitnessDelta1 / fitnessDelta2) * this.mutationRate;
            }

            if (populationIndex > this.elitismCount) {
                for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                    //  if (adaptiveMutationRate > Math.random()) {
                    if ((adaptiveMutationRate * this.getTemperature()) > Math.random()) {

                        // Swap for new gene
                        individual.setGene(geneIndex, randomIndividual.getGene(geneIndex));
                    }
                }
            }

            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }

        // Return mutated population
        return newPopulation;
    }

    public double getTemperature() {
        return temperature;
    }






}


