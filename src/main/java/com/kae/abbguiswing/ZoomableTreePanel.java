package com.kae.abbguiswing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class ZoomableTreePanel extends JPanel {
    private final java.util.List<VisualNode> visualNodes;
    private double zoomFactor = 1.0;
    private int offsetX = 0;
    private int offsetY = 0;
    private int lastMouseX;
    private int lastMouseY;
    private boolean isDragging = false;

    public ZoomableTreePanel() {
        this.visualNodes = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
                isDragging = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    double dx = e.getX() - lastMouseX;
                    double dy = e.getY() - lastMouseY;

                    // Levar em conta o fator de zoom atual
                    offsetX += (int) (dx / zoomFactor);
                    offsetY += (int) (dy / zoomFactor);

                    lastMouseX = e.getX();
                    lastMouseY = e.getY();
                    repaint();
                }
            }
        });

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                if (notches < 0) {
                    zoomIn(e.getX(), e.getY());
                } else {
                    zoomOut(e.getX(), e.getY());
                }
            }
        });
    }

    public void updateNodePositions() {
        for (VisualNode visualNode : visualNodes) {
            visualNode.updatePosition();
        }
        repaint();
    }

    public void resetZoom() {
        zoomFactor = 1.0;
        offsetX = 0;
        offsetY = 0;
        repaint();
    }

    public void zoomIn() {
        zoomFactor *= 1.1;
        repaint();
    }

    public void zoomOut() {
        zoomFactor /= 1.1;
        repaint();
    }

    public void zoomIn(int mouseX, int mouseY) {
        applyZoom(1.1, mouseX, mouseY);
    }

    public void zoomOut(int mouseX, int mouseY) {
        applyZoom(1 / 1.1, mouseX, mouseY);
    }

    private void applyZoom(double factor, int mouseX, int mouseY) {
        double lastZoomFactor = zoomFactor;
        zoomFactor *= factor;

        // Ajustar a posição de deslocamento para manter o ponto do mouse no mesmo local após o zoom
        offsetX = (int) (offsetX * (factor) + mouseX * (1 - factor));
        offsetY = (int) (offsetY * (factor) + mouseY * (1 - factor));

        repaint();
    }

    private void drawLine(Graphics2D g, int x1, int y1, int x2, int y2, float espessura) {
        Stroke originalStroke = g.getStroke(); // Salva o Stroke original
        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(espessura));
        g.drawLine(x1, y1, x2, y2);
        g.setStroke(originalStroke); // Restaura o Stroke original
    }

    private void drawVisualNode(Graphics2D g, VisualNode visualNode) {
        int circleSize = 48;
        int x = visualNode.getCurrentPos().x;
        int y = visualNode.getCurrentPos().y;

        g.setColor(Color.GRAY);
        g.fillOval(x - circleSize / 2, y - circleSize / 2, circleSize, circleSize);

        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(String.valueOf(visualNode.getInfo()));
        int textHeight = fm.getHeight();
        g.drawString(String.valueOf(visualNode.getInfo()), x - textWidth / 2, y + textHeight / 4);
    }

    private void drawNodeConnections(Graphics2D g, VisualNode parent, VisualNode child) {
        drawLine(g, parent.getCurrentPos().x, parent.getCurrentPos().y, child.getCurrentPos().x, child.getCurrentPos().y, 5.0f);
    }

    private void drawNodes(Graphics2D g) {
        // Desenhe os nós primeiro
        for (VisualNode visualNode : visualNodes) {
            drawVisualNode(g, visualNode);
        }

        // Em seguida, desenhe as linhas
        for (VisualNode visualNode : visualNodes) {
            VisualNode leftChild = visualNode.getEsquerda();
            VisualNode rightChild = visualNode.getDireita();

            if (leftChild != null) {
                drawNodeConnections(g, visualNode, leftChild);
            }

            if (rightChild != null) {
                drawNodeConnections(g, visualNode, rightChild);
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(zoomFactor, zoomFactor);
        g2d.translate(offsetX, offsetY);

        updateNodePositions();  // Adicione esta linha para atualizar as posições dos nós
        drawNodes(g2d);
    }

    public ArrayList<VisualNode> getVisualNodes() {
        return (ArrayList<VisualNode>) visualNodes;
    }
}
