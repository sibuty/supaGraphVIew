package com.seriouscompany.supagraphview.main;

import com.seriouscompany.supagraphview.viewandcontroller.DrawGraph;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Igor on 12.04.2015.
 */
public class Main {

    public static int[][] graphMatrix = null;
    public static int needCount = 10;
// = {
//            {0, 1, 0},
//            {1, 0, 0},
//            {0, 0, 0}

//    public static int[][] graphMatrix =
//            {
//           //0  1  2  3  4
//            {0, 1, 0, 0, 0}, //0
//            {1, 0, 1, 1, 1}, //1
//            {0, 1, 0, 1, 1}, //2
//            {0, 1, 1, 0, 1}, //3
//            {0, 1, 1, 1, 0}  //4

//            {1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
//            {1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
//            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
//            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
//            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
//            {1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
//            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
//            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
//    };

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DrawGraph window = new DrawGraph();
                window.init();
            }
        });
        getData(25);
    }

    public static void getData(int count) {
        int delta = 0;
        boolean flag = true;
        if(count >= needCount) {
            delta = (count - 1) / needCount;
            flag = false;
        } else {
            delta = needCount / (count - 1);
        }
        generateMatrices(count, delta, flag);
    }

    public static void generateMatrices(int count, int delta, boolean flag) {
        int k = 0;
        int c = 1;
        int z = 1;
        while (k < needCount - 1) {
            generateMatrix(count, c);
            z++;
            if (flag) {
                if (z > delta) {
                    z = 1;
                    c++;
                }
            } else {
                c += delta;
            }
            k++;
        }
        generateMatrix(count, count);
    }

    public static void generateMatrix(int count, int side) {
        final int[][] matrix = new int[count][count];
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    if (Math.abs(j - i) <= side) {
                        matrix[i][j] = 1;
                    }
                }
            }
        }
        graphMatrix = matrix;
        printGraphMatrix();
    }


    public static void printGraphMatrix() {
        if (graphMatrix != null) {
            for (int i = 0; i < graphMatrix.length; i++) {
                for (int j = 0; j < graphMatrix.length; j++) {
                    System.out.print(graphMatrix[i][j] + "\t");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}