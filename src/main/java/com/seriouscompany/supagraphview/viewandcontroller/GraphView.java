package com.seriouscompany.supagraphview.viewandcontroller;

import com.seriouscompany.supagraphview.model.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 12.04.2015.
 */
public class GraphView extends JPanel {

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    private Graph graph = null;

    public GraphView() {
    }

    public GraphView(Graph graph) {
        this.graph = graph;
    }

    public int getMaxX() {
        return getWidth() - 30;
    }

    public int getMaxY() {
        return getHeight() - 30;
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        if (graph != null) {
            graph.maxX = getWidth() - 30;
            graph.maxY = getHeight() - 30;
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            List<int[]> points = graph.graphCoordinates;
            int[][] graphMatrix = graph.graph;
            if (points != null && !points.isEmpty()) {
                for (int i = 0; i < graphMatrix.length; i++) {
                    List<Integer> useless = new ArrayList<>();
                    for (int j = 0; j < graphMatrix.length; j++) {
                        if (graphMatrix[i][j] == 1 && i != j && !useless.contains(j)) {
                            useless.add(i);
                            int[] first = points.get(i);
                            int[] second = points.get(j);
                            g2D.setColor(Color.BLACK);
                            g2D.drawLine(first[0], first[1], second[0], second[1]);
                        }
                    }
                }
                for (int i = 0; i < points.size(); i++) {
                    int[] first = points.get(i);
                    g2D.setColor(Color.RED);
                    g2D.fillOval(first[0] - 5, first[1] - 5, 10, 10);
                    g2D.setColor(Color.BLUE);
                    g2D.drawString(String.valueOf(i), first[0] + 5, first[1] + 5);
                }
            }
        }
    }
}
