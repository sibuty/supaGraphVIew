package com.seriouscompany.supagraphview.main;

import com.seriouscompany.supagraphview.viewandcontroller.DrawGraph;

/**
 * Created by Igor on 12.04.2015.
 */
public class Main {

    public static int[][] graphMatrix = null;
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
    }

    public static void printGraphMatrix() {
        if (graphMatrix != null) {
            for (int i = 0; i < graphMatrix.length; i++) {
                for (int j = 0; j < graphMatrix.length; j++) {
                    System.out.print(graphMatrix[i][j] + "\t");
                }
                System.out.println();
            }
        }
    }
}