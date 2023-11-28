package com.kae.abbguiswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ZoomableBinarySearchTreeGUI extends JFrame {
    private final VisualABB abb;
    private final VisualNodePanel treePanel;

    public ZoomableBinarySearchTreeGUI() {
        setTitle("Árvore Binária de Busca");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Timer timer = new Timer(16, new ActionListener() { // 16 milissegundos é aproximadamente 60 FPS
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTreePanel();
            }
        });
        timer.start();

        abb = new VisualABB();
        treePanel = new VisualNodePanel(abb.getNodes());

        JButton inserirButton = new JButton("Inserir nó");
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog("Valor do nó a inserir:");
                if (value != null && !value.isEmpty()) {
                    int intValue = Integer.parseInt(value);
                    abb.inserir(intValue, getWidth()/2, 80);
                    for (VisualNode node:
                         abb.getNodes()) {
                        System.out.println(node.getStartPos());
                        System.out.println(node.getTargetPos());
                    }
                    System.out.println(abb.getNodes().toArray().length);
                    updateTreePanel();
                }
            }
        });

        JButton ampliarButton = new JButton("Ampliar");
        ampliarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treePanel.zoomIn();
                updateTreePanel();
            }
        });

        JButton reduzirButton = new JButton("Reduzir");
        reduzirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treePanel.zoomOut();
                updateTreePanel();
            }
        });
        JButton resetarButton = new JButton("Resetar Zoom");
        resetarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treePanel.resetZoom();
                updateTreePanel();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(inserirButton);
        buttonPanel.add(ampliarButton);
        buttonPanel.add(reduzirButton);
        buttonPanel.add(resetarButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(treePanel), BorderLayout.CENTER);

        add(mainPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateTreePanel() {
        treePanel.update();
        treePanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ZoomableBinarySearchTreeGUI();
            }
        });
    }
}
