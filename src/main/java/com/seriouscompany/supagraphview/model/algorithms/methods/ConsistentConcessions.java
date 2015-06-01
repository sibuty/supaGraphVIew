package com.seriouscompany.supagraphview.model.algorithms.methods;

import com.seriouscompany.supagraphview.model.Graph;

import java.util.*;

/**
 * Created by Igor on 09.05.2015.
 */
public class ConsistentConcessions implements Method {

    private Graph graph;
    private List<Integer> prioritiesCriterions;
    private Map<Integer, Integer> criterions;
    private Integer curentCriterion = -1;

    public ConsistentConcessions(Graph graph) {
        this.graph = graph;
    }

    @Override
    public double getE() {
        for(Integer key : criterions.keySet()) {
            curentCriterion = key;
            switch (key) {
                case 0: {
                    return graph.getDifferenceFactors();
                }
                case 1: {
                    return graph.getAmountFactors();
                }
                case 2: {
                    return graph.getIntersectionFactors();
                }
                default: {
                    return -1;
                }
            }
        }
        return -1;
    }

    public double getE(Integer criterion) {
        switch (criterion) {
            case 0: {
                return graph.getDifferenceFactors();
            }
            case 1: {
                return graph.getAmountFactors();
            }
            case 2: {
                return graph.getIntersectionFactors();
            }
            default: {
                return -1;
            }
        }
    }

    @Override
    public void setNewSolution(Graph graph) {
        this.graph = graph;
    }

    @Override
    public Graph getSolution() {
        return graph;
    }

    @Override
    public void setPrioritiesCriterions(Integer... prioritiesCriterions) {
        Integer[] priorities = prioritiesCriterions;
        this.prioritiesCriterions = new ArrayList<Integer>(Arrays.asList(priorities));
        criterions = new LinkedHashMap<>();
        int i = 0;
        for (Integer value : priorities) {
            criterions.put(i, value);
            i++;
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList(criterions.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
                return -e1.getValue().compareTo(e2.getValue());
            }
        });
        criterions.clear();
        for(Map.Entry<Integer, Integer> entry : list) {
            if(entry.getValue() != 0) {
                criterions.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void removeCriterion(Integer criterion) {
        criterions.remove(criterion);
    }

    public Integer getCurentCriterion() {
        return curentCriterion;
    }

    public int getSizeCriterions() {
        return criterions.size();
    }
}
