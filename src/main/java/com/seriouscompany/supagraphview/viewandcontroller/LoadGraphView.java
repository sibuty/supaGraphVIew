package com.seriouscompany.supagraphview.viewandcontroller;

import com.seriouscompany.supagraphview.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Igor on 03.05.2015.
 */
public class LoadGraphView extends JPanel {

    public LoadGraphView(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        init();
    }

    public LoadGraphView(LayoutManager layout) {
        super(layout);
        init();
    }

    public LoadGraphView(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        init();
    }

    public LoadGraphView() {
        init();
    }

    public void init() {
        JPanel mainJPanel = new JPanel(new FlowLayout());
        final JLabel label = new JLabel("Загрузите граф из файла");
        JButton button = new JButton("Загрузить");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.setCurrentDirectory(new File("./"));
                if (fileOpen.showDialog(null, "Открыть файл") == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    label.setText(file.getName() + " загружен");
                    try {
                        Scanner scanner = new Scanner(new FileInputStream(file));
                        ArrayList<ArrayList<Integer>> arrayList = new ArrayList<ArrayList<Integer>>();
                        while (scanner.hasNextLine()) {
                            ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
                            StringTokenizer stringTokenizer = new StringTokenizer(scanner.nextLine().replaceAll("[^0-1 ]", " "));
                            while (stringTokenizer.hasMoreTokens()) {
                                arrayList1.add(Integer.parseInt(stringTokenizer.nextToken()));
                            }
                            arrayList.add(arrayList1);
                        }
                        if (!arrayList.isEmpty()) {
                            int length = arrayList.size();
                            if (length == arrayList.get(0).size()) {
                                int[][] graphMatrix = new int[length][length];
                                for (int i = 0; i < length; i++) {
                                    for (int j = 0; j < length; j++) {
                                        graphMatrix[i][j] = arrayList.get(i).get(j);
                                    }
                                }
                                Main.graphMatrix = graphMatrix;
                                Main.printGraphMatrix();
                            }
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        mainJPanel.add(label);
        mainJPanel.add(button);
        add(mainJPanel);
    }
}
