package com.seriouscompany.supagraphview.model.algorithms;

import com.seriouscompany.supagraphview.model.algorithms.methods.ConsistentConcessions;
import com.seriouscompany.supagraphview.model.algorithms.methods.Convolution;
import com.seriouscompany.supagraphview.model.algorithms.methods.Method;
import com.seriouscompany.supagraphview.model.Graph;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class AnnealingMethod implements Algorithm {

    private double maxTemperature;
    private Method method;
    private double minTemperature;
    private Graph bestSolution;

    public AnnealingMethod(Graph solution, Method method, double minTemperature, double maxTemperature) {
        this.bestSolution = solution;
        this.method = method;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    @Override
    public Graph getBestSolution() {
        double minE = 0;
        double temperature = maxTemperature;
        int i = 1;
        if (method instanceof Convolution) {
            method.setNewSolution(bestSolution);
            minE = method.getE();
            while (temperature > minTemperature) {
//                System.out.println("                          !!!ITERATION NUMBER!!!    " + i);
//                System.out.println("MIN E = " + minE);
                Graph stepSolution = Graph.newInstance(bestSolution);
                stepSolution.mutation();
                method.setNewSolution(stepSolution);
                double dE = method.getE() - minE;
                if (dE <= 0) {
                    bestSolution = stepSolution;
                    minE = method.getE();
                } else {
                    if (getResultiByProbability(getProbability(dE, temperature))) {
                        bestSolution = stepSolution;
                        minE = method.getE();
                    }
                }
                temperature = maxTemperature / i;
                ++i;
            }
        } else {
            ConsistentConcessions method = (ConsistentConcessions) this.method;
            Map<Integer, Double> criterions = new LinkedHashMap<>();
            while (criterions.size() < method.getSizeCriterions()) {
                temperature = maxTemperature;
                method.setNewSolution(bestSolution);
                minE = method.getE();
                while (temperature > minTemperature) {
//                    System.out.println("                          !!!ITERATION NUMBER!!!    " + i);
//                    System.out.println("MIN E = " + minE);
                    Graph stepSolution = Graph.newInstance(bestSolution);
                    stepSolution.mutation();
                    method.setNewSolution(stepSolution);
                    while (!checkCriterions(criterions, method)) {
                        stepSolution.mutation();
                        method.setNewSolution(stepSolution);
                    }
                    double dE = method.getE() - minE;
                    if (dE <= 0) {
                        bestSolution = stepSolution;
                        minE = method.getE();
                    } else {
                        if (getResultiByProbability(getProbability(dE, temperature))) {
                            bestSolution = stepSolution;
                            minE = method.getE();
                        }
                    }
                    temperature = maxTemperature * 0.1 / i;
                    ++i;
                }
                criterions.put(method.getCurentCriterion(), minE + minE * 0.1);
                method.removeCriterion(method.getCurentCriterion());
            }
        }
        return bestSolution;
    }

    private boolean checkCriterions(Map<Integer, Double> criterions, ConsistentConcessions method) {
        for(Integer key : criterions.keySet()) {
            if(method.getE(key) > criterions.get(key)) {
                return false;
            }
        }
        return true;
    }

    private double getProbability(double dE, double t) {
        return Math.exp(-dE / t);
    }

    private boolean getResultiByProbability(double probability) {
        Random rnd = new Random();
        double value = (double) rnd.nextInt(1001) / 1000.0;
//        System.out.println("\n\n\nVALUE = " + value + "\n\n\n");
        if (value <= probability) {
            return true;
        }
        return false;
    }
}
