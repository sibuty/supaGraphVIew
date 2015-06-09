package com.seriouscompany.supagraphview.viewandcontroller;

import com.seriouscompany.supagraphview.main.Main;
import com.seriouscompany.supagraphview.model.Graph;
import com.seriouscompany.supagraphview.model.algorithms.Algorithm;
import com.seriouscompany.supagraphview.model.algorithms.AnnealingMethod;
import com.seriouscompany.supagraphview.model.algorithms.GeneticAlgorithm;
import com.seriouscompany.supagraphview.model.algorithms.methods.Convolution;
import com.seriouscompany.supagraphview.model.algorithms.methods.Method;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Igor on 12.04.2015.
 */
public class DrawGraph extends JFrame {
    private JPanel contentPanel;
    private final GraphView graphView;
    private JButton startVisible;


    public DrawGraph() {
        graphView = new GraphView();
    }

    public void init() {
        setTitle("Draw Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(700, 700));

        initContentPanel();

        getContentPane().add(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initContentPanel() {
        contentPanel = new JPanel(new BorderLayout());

        final JPanel centerPanel = new JPanel(new BorderLayout());
        graphView.setSize(new Dimension(500, 500));
        final JScrollPane mainScrollPane = new JScrollPane(graphView);
        centerPanel.add(mainScrollPane);

        /*final Graph graph = new Graph();
        graph.graph = Main.graphMatrix;
        graphView.setGraph(graph);

        JButton button = new JButton("New graph");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graph.generateCoordinates();
                while (graph.getDifferenceFactors() > 50) {
                    graph.generateCoordinates();
                }
                graphView.repaint();
            }
        });

        JButton button1 = new JButton("Mutation");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graph.mutation();
                graphView.repaint();
            }
        });

        final JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(button);
        buttonPanel.add(button1);*/

        final JPanel northPanel = new JPanel(new BorderLayout());

        final JPanel loadGraphView = new LoadGraphView();

        final JPanel graphInfoPanel = new JPanel(new BorderLayout());
        final JPanel criterionPanel = new JPanel(new FlowLayout());
        initCheckBoxes(criterionPanel);

        JPanel lablePanel = new JPanel();
        lablePanel.add(new JLabel("Выберите тип алгоритма и нужные критерии и их приоритеты."));

        graphInfoPanel.add(lablePanel, BorderLayout.NORTH);
        graphInfoPanel.add(criterionPanel, BorderLayout.CENTER);

        northPanel.add(loadGraphView, BorderLayout.NORTH);
        northPanel.add(graphInfoPanel, BorderLayout.CENTER);

        contentPanel.add(northPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private void drawGraph(Graph graph) {
        graphView.setGraph(graph);
        graphView.repaint();
        Runtime r = Runtime.getRuntime();
        System.out.println("Memory used = " + new BigDecimal((float) (r.totalMemory() - r.freeMemory()) / 8 / 1024 / 1024).setScale(2, RoundingMode.UP).floatValue() + "Mbs");
        startVisible.setEnabled(true);
    }

    private void initCheckBoxes(final JPanel criterionPanel) {

        final JPanel algorithmsSelect = new JPanel();
        algorithmsSelect.setLayout(new BoxLayout(algorithmsSelect, BoxLayout.Y_AXIS));

        final JCheckBox genetic = new JCheckBox("Генетичейский алгоритм");
        genetic.setHorizontalTextPosition(JCheckBox.LEFT);

        final JCheckBox fire = new JCheckBox("Метод отжига");
        fire.setHorizontalTextPosition(JCheckBox.LEFT);

        algorithmsSelect.add(genetic);
        algorithmsSelect.add(fire);

        final JPanel criterions = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 5;

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        final JCheckBox factorsEqual = new JCheckBox("Равенство ребер");
        criterions.add(factorsEqual, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.0;
        final JTextField factorsEqualField = new JTextField("", 5);
        criterions.add(factorsEqualField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.0;
        final JCheckBox factorsLenght = new JCheckBox("Минимальная длина ребер");
        criterions.add(factorsLenght, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.0;
        final JTextField factorsLenghtField = new JTextField("", 5);
        criterions.add(factorsLenghtField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.0;
        final JCheckBox factorsIntersection = new JCheckBox("Минимальное число пересечений");
        criterions.add(factorsIntersection, c);

        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.0;
        final JTextField factorsIntersectionField = new JTextField("", 5);
        criterions.add(factorsIntersectionField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        startVisible = new JButton("Ок");
        criterions.add(startVisible, c);

        startVisible.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (startVisible.isEnabled()) {
                    if (!factorsEqual.isSelected()
                            && !factorsLenght.isSelected()
                            && !factorsIntersection.isSelected()) {
                        JOptionPane.showMessageDialog(DrawGraph.this,
                                "Выберите хотя бы один критерий.",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (!factorsEqualField.getText().isEmpty()
                                || !factorsLenghtField.getText().isEmpty()
                                || !factorsIntersectionField.getText().isEmpty()) {
                            try {
                                startVisible.setEnabled(false);
                                int equalPrior = factorsEqual.isSelected() ? Integer.parseInt(factorsEqualField.getText()) : 0;
                                int lengthPrior = factorsLenght.isSelected() ? Integer.parseInt(factorsLenghtField.getText()) : 0;
                                int intersectionPrior = factorsIntersection.isSelected() ? Integer.parseInt(factorsIntersectionField.getText()) : 0;
                                if (genetic.isSelected()) {
                                    Method method = new Convolution(null);
                                    method.setPrioritiesCriterions(equalPrior, lengthPrior, intersectionPrior);
                                    Algorithm algorithm =
                                            new GeneticAlgorithm(Main.graphMatrix, method, graphView.getMaxX(), graphView.getMaxY());
                                    drawGraph(algorithm.getBestSolution());
                                } else if (fire.isSelected()) {
                                    if (Main.graphMatrix != null) {
                                        Graph graph = new Graph(Main.graphMatrix, graphView.getMaxX(), graphView.getMaxY());
                                        Convolution method = new Convolution(graph);
                                        method.setPrioritiesCriterions(equalPrior, lengthPrior, intersectionPrior);
                                    /*ConsistentConcessions method = new ConsistentConcessions(graph);
                                    method.setPrioritiesCriterions(equalPrior, lengthPrior, intersectionPrior);*/
                                        Algorithm algorithm = new AnnealingMethod(graph, method, 1, 1000);
                                        drawGraph(algorithm.getBestSolution());
                                    } else {
                                        JOptionPane.showMessageDialog(DrawGraph.this,
                                                "Загрузите граф.",
                                                "Ошибка ввода",
                                                JOptionPane.ERROR_MESSAGE);
                                        startVisible.setEnabled(true);
                                    }
                                }
                            /*factorsEqualField.setText("");
                            factorsLenghtField.setText("");
                            factorsIntersectionField.setText("");
                            factorsEqual.setSelected(false);
                            factorsLenght.setSelected(false);
                            factorsIntersection.setSelected(false);*/
                            } catch (Exception exp) {
                                exp.printStackTrace();
                                JOptionPane.showMessageDialog(DrawGraph.this,
                                        "Некорректные приоритеты выбранных критериев",
                                        "Ошибка ввода",
                                        JOptionPane.ERROR_MESSAGE);
                                startVisible.setEnabled(true);
                            }
                        } else {
                            JOptionPane.showMessageDialog(DrawGraph.this,
                                    "Введите приоритеты выбранных критериев",
                                    "Ошибка ввода",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        criterions.setVisible(false);

        genetic.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (fire.isSelected()) {
                        fire.setSelected(false);
                    }
                    criterions.setVisible(true);
                } else {
                    if (criterions.isVisible()) {
                        criterions.setVisible(false);
                    }
                }
            }
        });

        fire.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (genetic.isSelected()) {
                        genetic.setSelected(false);
                    }
                    criterions.setVisible(true);
                } else {
                    if (criterions.isVisible()) {
                        criterions.setVisible(false);
                    }
                }
            }
        });

        criterionPanel.add(algorithmsSelect);
        criterionPanel.add(criterions);
    }

    private void exp1(Algorithm algorithm, Method method) {
        double midTime = 0;
        Graph solution = null;
        for(int i = 0; i < 50; i++) {
            long time1 = System.nanoTime();
            solution = algorithm.getBestSolution();
            method.setNewSolution(solution);
            while (method.getE() > solution.getLamdaMin()) {
                solution = algorithm.getBestSolution();
                method.setNewSolution(solution);
            }
            midTime += (double) (System.nanoTime() - time1) / 1000000000;
        }
        System.out.println(midTime/50 + " seconds " + algorithm.getClass().toString());
        drawGraph(solution);
    }

    private void exp2(Algorithm algorithm) {
        double midTime = 0;
        Graph solution = null;
        for(int i = 0; i < 50; i++) {
            long time1 = System.nanoTime();
            solution = algorithm.getBestSolution();
            while (solution.getLamdaEqual() > 0.2) {
                solution = algorithm.getBestSolution();
            }
            midTime += (double) (System.nanoTime() - time1) / 1000000000;
        }
        System.out.println(midTime/50 + " seconds " + algorithm.getClass().toString());
        drawGraph(solution);
    }
}
