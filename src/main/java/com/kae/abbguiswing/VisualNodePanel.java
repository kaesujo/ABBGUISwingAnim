package com.kae.abbguiswing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class VisualNodePanel extends JPanel implements Zoomable{

    private double zoomFactor = 1.0;
    private int offsetX = 0;
    private int offsetY = 0;
    private int lastMouseX;
    private int lastMouseY;
    private boolean isDragging = false;

    public List<VisualNode> getVisualNodes() {
        return visualNodes;
    }

    public void setVisualNodes(List<VisualNode> visualNodes) {
        this.visualNodes = visualNodes;
    }

    private List<VisualNode> visualNodes;

    public VisualNodePanel() {
        visualNodes = new ArrayList<>();
        addMouseWheelListener(e -> System.out.println("Mouse Wheel Moved"));

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
                    double dx = (e.getX() - lastMouseX) / zoomFactor;
                    double dy = (e.getY() - lastMouseY) / zoomFactor;

                    // Levar em conta o fator de zoom atual
                    offsetX += (int) dx;
                    offsetY += (int) dy;

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


        Timer timer = new Timer(16, new ActionListener() { // 16ms para atualização aproximadamente 60 FPS
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNodesPosition();
                repaint();
            }
        });
        timer.start();


    }

    public VisualNodePanel(List<VisualNode> visualNodes) {
        this();
        this.visualNodes = visualNodes;
    }

    public void update() {
        updateNodesPosition();
    }

    private void updateNodesPosition() {
        for (VisualNode visualNode : visualNodes) {
            visualNode.updatePosition();
        }
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Aplica a escala de zoom e o deslocamento
        g2d.scale(zoomFactor, zoomFactor);
        g2d.translate(offsetX, offsetY);

        drawNodes(g2d); // Desenha os nós considerando a escala de zoom e deslocamento
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

        for (VisualNode visualNode : visualNodes) {
            drawVisualNode(g, visualNode);
        }
    }

    @Override
    public void resetZoom() {
        zoomFactor = 1.0;
        offsetX = 0;
        offsetY = 0;
        repaint();
    }
    @Override
    public void zoomIn() {
        zoomFactor *= 1.1;
        repaint();
    }

    @Override
    public void zoomOut() {
        zoomFactor /= 1.1;
        repaint();
    }

    @Override
    public void zoomIn(int mouseX, int mouseY) {
        applyZoom(1.1, mouseX, mouseY);
        repaint();
    }

    @Override
    public void zoomOut(int mouseX, int mouseY) {
        applyZoom(1 / 1.1, mouseX, mouseY);
        repaint();
    }

    @Override
    public void applyZoom(double factor, int mouseX, int mouseY) {
        double lastZoomFactor = zoomFactor;
        zoomFactor *= factor;

        // Ajustar a posição de deslocamento para manter o ponto do mouse no mesmo local após o zoom
        offsetX = (int) (offsetX * (factor) + mouseX * (1 - factor));
        offsetY = (int) (offsetY * (factor) + mouseY * (1 - factor));

        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("VisualNodePanel");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                VisualABB abb = new VisualABB();
                List<VisualNode> visualNodes = abb.getNodes();
                VisualNodePanel panel = new VisualNodePanel(visualNodes);

                List<Integer> elementsToAdd = Arrays.asList(23, 76, 1, 5, 9, 34, 67, 2, 87);

                Thread insertionThread = new Thread(() -> {
                    for (Integer element : elementsToAdd) {
                        abb.inserir(element);
                        panel.update();
                        panel.repaint();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                insertionThread.start();



//                Thread insertionThread = new Thread(() -> {
//                    Random random = new Random();
//                    for (int i = 0; i < 8; i++) {
//                        int randomNumber = random.nextInt(100);
//                        abb.inserir(randomNumber);
//                        panel.update();
//                        panel.repaint();
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                insertionThread.start();

                frame.getContentPane().add(panel);
                frame.setSize(1000, 1000);
                frame.setVisible(true);

            }
        });
    }
}
