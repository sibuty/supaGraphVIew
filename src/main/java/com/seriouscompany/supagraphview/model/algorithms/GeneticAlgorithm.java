package com.seriouscompany.supagraphview.model.algorithms;

import com.seriouscompany.supagraphview.model.Graph;
import com.seriouscompany.supagraphview.model.algorithms.methods.Method;

import java.util.*;

/**
 * Created by Igor on 24.05.2015.
 */
public class GeneticAlgorithm implements Algorithm {

    private Method method;
    private int maxX;
    private int maxY;
    private int[][] graphMatrix;
    private Graph bestSolution = null;
    private Graph prevSolution = null;
    private double mutateChance = 0.15;
    private int populationSize = 100;

    private ArrayList<Graph> population = new ArrayList<>();
    private ArrayList<Graph> currentPopulation = new ArrayList<>();
    private ArrayList<Graph> children = new ArrayList<>();

    public GeneticAlgorithm(int[][] graphMatrix, Method method, int maxX, int maxY) {
        this.graphMatrix = graphMatrix;
        this.method = method;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    private double getMiddleFitness() {
        double midFitness = 0;
        for (Graph solution : population) {
            double fitness = getFitness(solution);
            if(fitness < 1) {
                midFitness += fitness;
            }
        }
        return midFitness / population.size();
    }

    private int getChance(Graph solution) {
        double midFitness = getMiddleFitness();
        double chance = getFitness(solution) / midFitness;
        Random rnd = new Random();
        int randomIndex = 0;
        if (chance < 1) {
            randomIndex = rnd.nextInt(101) + 1;
            if (randomIndex < chance * 100) {
                return 1;
            } else {
                return 0;
            }
        } else if (chance < 2 && chance >= 1) {
            randomIndex = rnd.nextInt(101) + 1;
            if (randomIndex < (chance - 1) * 100) {
                return 2;
            } else {
                return 1;
            }
        } else if(chance < 3 && chance >= 2) {
            randomIndex = rnd.nextInt(101) + 1;
            if (randomIndex < (chance - 2) * 100) {
                return 3;
            } else {
                return 2;
            }
        }
        return 3;
    }

    private void getFirstPopulation() {
        if(!population.isEmpty()) {
            population.clear();
        }
        for (int i = 0; i < populationSize; i++) {
            final Graph graph = new Graph(graphMatrix, maxX, maxY);
            graph.generateCoordinates();
            population.add(graph);
        }
    }

    private void doChoose() {
        Random rnd = new Random();
        while (currentPopulation.size() < populationSize) {
//            System.out.println("Doing choose.");
//            System.out.println("Current population size = " + currentPopulation.size());
            choose();
        }
        while (children.size() < populationSize) {
            int randomIndex = getRandomIndex(-1);
            Graph currentParent = currentPopulation.get(randomIndex);
            mainCross(currentParent, getStepParent(randomIndex));
        }
        currentPopulation.clear();
        for (int i = 0; i < children.size(); i++) {
            if (rnd.nextInt(100) < mutateChance * 100) {
                children.get(i).mutation();
//                System.out.println("Was MUTATE.");
            }
        }
        for (Graph value : population) {
            children.add(Graph.newInstance(value));
        }
        Collections.sort(children, new Comparator<Graph>() {
            @Override
            public int compare(Graph o1, Graph o2) {
                if (getFitness(o1) > getFitness(o2)) {
                    return -1;
                }
                if (getFitness(o1) < getFitness(o2)) {
                    return 1;
                }
                return 0;
            }
        });
    }

    private void setNewPopulation() {
        population.clear();
        for (int i = 0; i < populationSize; i++) {
            population.add(i, children.get(i));
        }
        children.clear();
    }

    private void setNewBestSolution() {
        bestSolution = population.get(0);
    }

    private void choose() {
        int randomIndex = getRandomIndex(-1);
        Graph currentParent = population.get(randomIndex);
        switch (getChance(currentParent)) {
            case 3: {
                currentPopulation.add(currentParent);
            }
            case 2: {
                currentPopulation.add(currentParent);
            }
            case 1: {
                currentPopulation.add(currentParent);
                break;
            }
        }
    }

    private int getRandomIndex(int randomIndex) {
        Random rnd = new Random();
        int newRandomIndex = rnd.nextInt(populationSize);
        if (randomIndex >= 0) {
            while (newRandomIndex > randomIndex || newRandomIndex < randomIndex) {
//                System.out.println("Doing random.");
                newRandomIndex = rnd.nextInt(populationSize);
            }
        }
        return newRandomIndex;
    }

    private Graph getStepParent(int randomIndex) {
        return currentPopulation.get(getRandomIndex(randomIndex));
    }

    private void mainCross(Graph parent1, Graph parent2) {
        cross(parent1, parent2, true);
        cross(parent1, parent2, false);
    }

    private void cross(Graph parent1, Graph parent2, boolean first) {
        final Graph child = Graph.cross(parent1, parent2, first);
        if (checkChild(child)) {
            children.add(child);
        }
    }

    private boolean checkChild(Graph child) {
        List<int[]> coordinates = child.graphCoordinates;
        for (int i = 0; i < coordinates.size(); i++) {
            for (int j = 0; j < coordinates.size(); j++) {
                int[] getValue = coordinates.get(i);
                int[] value = coordinates.get(j);
                if (getValue != null) {
                    if (i != j && getValue[0] == value[0] && getValue[1] == value[1]) {
                        return false;
                    }
                }
            }
        }
        child.checkAndFixPoints();
        return true;
    }

    private double getFitness(Graph solution) {
        method.setNewSolution(solution);
        final double fitness = method.getE();
        method.setNewSolution(null);
        return fitness == 0 ? 3 : 1 / fitness;
    }

    private double getNoramlFitness(Graph solution) {
        final double normalFitness = getFitness(solution);
        return normalFitness == 3 ? 0 : 1/normalFitness;
    }

    private void doStepAlgorithm() {
        doChoose();
        setNewPopulation();
        setNewBestSolution();
    }

    private void printPopulation() {
        for (Graph value : population) {
            System.out.println(1 / getFitness(value));
        }
    }

    @Override
    public Graph getBestSolution() {
        getFirstPopulation();
        doStepAlgorithm();
        Graph prevBestSolution = Graph.newInstance(bestSolution);
        int i = 0;
        while (true) {
            doStepAlgorithm();
            if (getFitness(bestSolution) > getFitness(prevBestSolution)) {
                prevBestSolution = Graph.newInstance(bestSolution);
                i = 0;
            } else {
                if (getFitness(bestSolution) == getFitness(prevBestSolution)) {
                    i++;
                } else {
                    i = 0;
                }
            }
            /*System.out.println("Prev = " + getNoramlFitness(prevBestSolution));
            System.out.println("Best = " + getNoramlFitness(bestSolution));*/
            if (i > 10) {
                return prevBestSolution;
            }
        }
    }
}
