package com.seriouscompany.supagraphview.model.algorithms.methods;

import com.seriouscompany.supagraphview.model.Graph;

/**
 * Created by Igor on 08.05.2015.
 */
public interface Method {

    public double getE();

    public void setNewSolution(Graph graph);

    public Graph getSolution();

    public void setPrioritiesCriterions(Integer... prioritiesCriterions);
}
