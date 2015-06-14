package com.seriouscompany.supagraphview.viewandcontroller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Igor on 14.06.2015.
 */
public class TestingFrame extends JFrame {
    private JPanel contentPanel;

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
    }
}
