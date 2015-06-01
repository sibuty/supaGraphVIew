package com.seriouscompany.supagraphview.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Igor on 12.04.2015.
 */
public class Graph {

    public int[][] graph = null;
    public List<int[]> graphCoordinates = new ArrayList<>();
    private List<double[]> edgeFactors = new ArrayList<>();
    public int maxX;
    public int maxY;
    public int badPoint = -1;

    public Graph() {
    }

    public Graph(int[][] graph) {
        this.graph = graph;
    }

    public Graph(int[][] graph, int maxX, int maxY) {
        this.graph = graph;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void generateCoordinates() {
        if (!graphCoordinates.isEmpty()) {
            graphCoordinates.clear();
        }
        Random rnd = new Random();
        for (int i = 0; i < graph.length; i++) {
            int[] element = new int[2];
            element[0] = 15 + rnd.nextInt(maxX);
            element[1] = 15 + rnd.nextInt(maxY);

            if (i == 1) {
                while (containsCoordinates(element)) {
                    element[0] = 15 + rnd.nextInt(maxX);
                    element[1] = 15 + rnd.nextInt(maxY);
                }
            }

            if (i > 1) {
                while (!checkPoint(element)) {
                    element[0] = 15 + rnd.nextInt(maxX);
                    element[1] = 15 + rnd.nextInt(maxY);
                }
            }

            graphCoordinates.add(i, element);
            if (i > 0) {
                setEdgeFactors();
            }
        }
        checkAndFixPoints();
    }

    public static Graph newInstance(Graph graph) {
        Graph newInstance = new Graph();
        newInstance.maxX = graph.maxX;
        newInstance.maxY = graph.maxY;
        newInstance.graph = graph.graph;
        newInstance.graphCoordinates.clear();
        newInstance.graphCoordinates.addAll(graph.graphCoordinates);
        newInstance.edgeFactors.clear();
        newInstance.edgeFactors.addAll(graph.edgeFactors);
        return newInstance;
    }

    public static Graph cross(Graph parent1, Graph parent2, boolean first) {
        Graph result = null;
        if(first) {
            result = newInstance(parent1);
            for(int i = parent2.graphCoordinates.size()/2; i < parent2.graphCoordinates.size(); i++) {
                result.graphCoordinates.set(i, parent2.graphCoordinates.get(i));
            }
        } else {
            result = newInstance(parent2);
            for(int i = 0; i < parent1.graphCoordinates.size()/2; i++) {
                result.graphCoordinates.set(i, parent1.graphCoordinates.get(i));
            }
        }
        if(result != null) {
            result.setEdgeFactors();
            return result;
        }
        return null;
    }

    public void checkAndFixPoints() {
        int i = 0;
        Random rnd = new Random();
        while (!checkPoints()) {
            graphCoordinates.set(badPoint, null);
            setEdgeFactors();
            int[] element = new int[2];
            element[0] = 15 + rnd.nextInt(maxX);
            element[1] = 15 + rnd.nextInt(maxY);
            while (!checkPoint(element)) {
                element[0] = 15 + rnd.nextInt(maxX);
                element[1] = 15 + rnd.nextInt(maxY);
            }
            graphCoordinates.set(badPoint, element);
            setEdgeFactors();
            /*System.out.println("BAD POINT           " + badPoint);
            System.out.println("while " + i++);*/
        }
    }

    public void printFactors() {
        System.out.println(edgeFactors.size());
        for (double[] value : edgeFactors) {
            for (int i = 0; i < value.length; i++) {
                System.out.println(value[i]);
            }
            System.out.println();
        }
    }

    public boolean containsCoordinates(int[] value) {
        for (int i = 0; i < graphCoordinates.size(); i++) {
            int[] getValue = graphCoordinates.get(i);
            if (getValue != null) {
                if (getValue[0] == value[0] && getValue[1] == value[1]) {
                    return true;
                }
            }
        }
        return false;
    }

    public void mutation() {
//        System.out.println("BEFORE MUT " + getIntersectionFactors());
        Random rnd = new Random();
        int i = rnd.nextInt(graphCoordinates.size());
        int[] element = new int[2];
        graphCoordinates.set(i, null);
        setEdgeFactors();
        element[0] = 15 + rnd.nextInt(maxX);
        element[1] = 15 + rnd.nextInt(maxY);
        while (!checkPoint(element)) {
            element[0] = 15 + rnd.nextInt(maxX);
            element[1] = 15 + rnd.nextInt(maxY);
        }
        graphCoordinates.set(i, element);
        setEdgeFactors();
        checkAndFixPoints();
//        System.out.println("AFTER MUT " + getIntersectionFactors());
    }

    public boolean checkPoints() {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < edgeFactors.size(); j++) {
                int[] getValue = graphCoordinates.get(i);
                if (getValue != null) {
                    double[] factors = edgeFactors.get(j);
                    if (factors[2] != getValue[0]
                            && factors[4] != getValue[0]
                            && factors[3] != getValue[1]
                            && factors[5] != getValue[1]) {
                        if (checkCircle(factors[2], factors[3], factors[4], factors[5], getValue[0], getValue[1])) {
                            badPoint = i;
                            return false;
                        }
                    }
                }
            }
        }
        badPoint = -1;
        return true;
    }

    private void setEdgeFactors() {
        edgeFactors.clear();
        List<Integer> points = new ArrayList<>();
        for (int i = 0; i < graphCoordinates.size(); i++) {
            for (int j = 0; j < graphCoordinates.size(); j++) {
                if (graph[i][j] == 1 && i != j && !points.contains(j)
                        && graphCoordinates.get(i) != null && graphCoordinates.get(j) != null) {
                    points.add(i);
                    double[] element = new double[8];
                    element[0] = (double) graphCoordinates.get(j)[0] - graphCoordinates.get(i)[0];
                    element[1] = (double) graphCoordinates.get(j)[1] - graphCoordinates.get(i)[1];
                    element[2] = (double) graphCoordinates.get(i)[0];
                    element[3] = (double) graphCoordinates.get(i)[1];
                    element[4] = (double) graphCoordinates.get(j)[0];
                    element[5] = (double) graphCoordinates.get(j)[1];
                    element[6] = i;
                    element[7] = j;
                    edgeFactors.add(element);
                }
            }
        }
    }

    private boolean checkPoint(int[] element) {
        for (int i = 0; i < edgeFactors.size(); i++) {
            double[] factors = edgeFactors.get(i);
            if (containsCoordinates(element)) {
                return false;
            }
            if (factors[1] * (element[0] - factors[2]) == (element[1] - factors[3]) * factors[0]) {
                return false;
            }
            if (checkCircle(factors[2], factors[3], factors[4], factors[5], element[0], element[1])) {
                return false;
            }
        }
        return true;
    }

    private boolean checkCircle(double x1, double y1, double x2, double y2, double xC, double yC) {
        double _x1 = x1 - xC;
        double _x2 = x2 - xC;
        double _y1 = y1 - yC;
        double _y2 = y2 - yC;

        double dx = _x2 - _x1;
        double dy = _y2 - _y1;
        double a = dx * dx + dy * dy;
        double b = 2 * (_x1 * dx + _y1 * dy);
        double c = _x1 * _x1 + _y1 * _y1 - 100.0;

        if (-b < 0) {
            return (c < 0);
        }
        if (-b < (2 * a)) {
            return (4 * a * c - b * b < 0);
        }

        return (a + b + c < 0);
    }

    public float getDifferenceFactors() {
        double min = 0;
        double max = 0;
        for (int i = 0; i < edgeFactors.size(); i++) {
            double[] factor = edgeFactors.get(i);
            double step = Math.sqrt(Math.pow(factor[4] - factor[2], 2) + Math.pow(factor[5] - factor[3], 2));
            if (min == 0 || max == 0) {
                min = step;
                max = step;
            }
            if (step < min) {
                min = step;
            } else {
                if (step > max) {
                    max = step;
                }
            }
        }
        return (float) (max - min);
    }

    public float getAmountFactors() {
        double result = 0.0;
        for (int i = 0; i < edgeFactors.size(); i++) {
            double[] factor = edgeFactors.get(i);
            result += Math.sqrt(Math.pow(factor[4] - factor[2], 2) + Math.pow(factor[5] - factor[3], 2));
        }
        return (float) result;
    }

    public boolean checkFactor(double[] factor, double x, double y) {
        boolean flag = false;
        if (factor[2] < factor[4]) {
            if (factor[2] < x && x < factor[4]) {
                flag = true;
            }
        } else {
            if (factor[2] > x && x > factor[4]) {
                flag = true;
            }
        }
        if (flag) {
            if (factor[3] < factor[5]) {
                if (factor[2] < y && y < factor[4]) {
                    flag = true;
                }
            } else {
                if (factor[2] > y && y > factor[4]) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public int getIntersectionFactors() {
        int result = 0;
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < edgeFactors.size(); i++) {
            double[] factor1 = edgeFactors.get(i);
            for (int j = 0; j < edgeFactors.size(); j++) {
                if (i != j && !arrayList.contains(j)) {
                    double[] factor2 = edgeFactors.get(j);
                    double A1 = factor1[3] - factor1[5];
                    double B1 = factor1[4] - factor1[2];
                    double C1 = factor1[2] * factor1[5] - factor1[4] * factor1[3];

                    double A2 = factor2[3] - factor2[5];
                    double B2 = factor2[4] - factor2[2];
                    double C2 = factor2[2] * factor2[5] - factor2[4] * factor2[3];

                    double D1 = A1 * B2 - A2 * B1;
//                    double D2 = A1 * C2 - A2 * C1;
//                    double D3 = B1 * C2 - B2 * C1;

                    if (D1 != 0.0) {
                        double x = -((C1 * B2 - C2 * B1) / (A1 * B2 - A2 * B1));
                        double y = -((A1 * C2 - A2 * C1) / (A1 * B2 - A2 * B1));
                       /* System.out.println();
                        System.out.println();
                        System.out.println();
                        System.out.println(x);
                        System.out.println(y);
                        System.out.println(checkFactor(factor1, x, y) && checkFactor(factor2, x, y));
                        System.out.println(Arrays.toString(factor1));
                        System.out.println(Arrays.toString(factor2));
                        System.out.println();
                        System.out.println();
                        System.out.println();*/
                        if (checkFactor(factor1, x, y) && checkFactor(factor2, x, y)) {
                            if (!arrayList.contains(i)) {
                                arrayList.add(i);
                            }
                            result++;
                        }
                    }
//                    double v1 = (factor2[4] - factor2[2]) * (factor1[3] - factor2[3])
//                            - (factor2[5] - factor1[3]) * (factor1[2] - factor2[2]);
//                    double v2 = (factor2[4] - factor2[2]) * (factor1[5] - factor2[3]) -
//                            (factor2[5] - factor1[3]) * (factor1[4] - factor2[2]);
//                    double v3 = (factor1[4] - factor1[2]) * (factor2[3] - factor1[3]) -
//                            (factor2[5] - factor1[3]) * (factor2[2] - factor1[2]);
//                    double v4 = (factor1[4] - factor1[2]) * (factor2[5] - factor2[3]) -
//                            (factor1[5] - factor1[3]) * (factor2[4] - factor2[2]);
//                    if (v1 * v2 < 0 && v3 * v4 < 0) {
//                        if (!arrayList.contains(i)) {
//                            arrayList.add(i);
//                            arrayList.add(j);
//                            result++;
//                        }
//                    }
                }
            }
        }
        return result;
    }
}
