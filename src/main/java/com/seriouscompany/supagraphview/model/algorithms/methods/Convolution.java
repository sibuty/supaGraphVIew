package com.seriouscompany.supagraphview.model.algorithms.methods;

import com.seriouscompany.supagraphview.model.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Igor on 08.05.2015.
 */
public class Convolution implements Method {

    private Graph graph;
    private List<Integer> prioritiesCriterions;

    public Convolution(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void setPrioritiesCriterions(Integer... prioritiesCriterions) {
        Integer[] priorities = prioritiesCriterions;
        this.prioritiesCriterions = new ArrayList<Integer>(Arrays.asList(priorities));
    }

    @Override
    public double getE() {
        double result = 0;
        int i = 0;
        for(Integer prority : prioritiesCriterions) {
            if(prority != 0) {
                switch (i) {
                    case 0: {
                        result += graph.getDifferenceFactors() * prority;
                        break;
                    }
                    case 1: {
                        result += graph.getAmountFactors() * prority;
                        break;
                    }
                    case 2: {
                        result += graph.getIntersectionFactors() * prority;
                        break;
                    }
                    default: {
                        return -1;
                    }
                }
            }
            i++;
        }
        return result;
    }

    @Override
    public void setNewSolution(Graph graph) {
        this.graph = graph;
    }

    @Override
    public Graph getSolution() {
        return graph;
    }
}
