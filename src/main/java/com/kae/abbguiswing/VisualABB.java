package com.kae.abbguiswing;

import java.awt.*;
import java.util.ArrayList;
public class VisualABB extends ABB {

    private static final int DESLOCAMENTO = 20;
    private VisualNode raiz;
    private final ArrayList<VisualNode> nodes;

    public ArrayList<VisualNode> getNodes() {
        return nodes;
    }

    public VisualABB() {
        this.raiz = null;
        this.nodes = new ArrayList<>();
    }

    public void inserir(int info, int x, int y) {
        raiz = inserirRec(raiz, info, x, y, 100, 60);
    }

    @Override
    public void inserir(int info) {
        raiz = inserirRec(raiz, info, 200, 60, 100, 60);
    }

    private VisualNode inserirRec(VisualNode node, int info, int x, int y, int horizontalGap, int verticalGap) {
        if (node == null) {
            Point startPos = new Point(200, 60);  // Posição inicial da raiz
            Point targetPos = new Point(x, y);
            VisualNode novo = new VisualNode(info, startPos, targetPos);
            nodes.add(novo);
            return novo;
        }

        if (info < node.getInfo()) {
            node.setEsquerda(inserirRec(node.getEsquerda(), info, x - horizontalGap, y + verticalGap + DESLOCAMENTO, horizontalGap / 2 - 10, verticalGap + DESLOCAMENTO));
        } else if (info > node.getInfo()) {
            node.setDireita(inserirRec(node.getDireita(), info, x + horizontalGap + DESLOCAMENTO, y + verticalGap + DESLOCAMENTO, horizontalGap / 2 - 10, verticalGap + DESLOCAMENTO));
        }
        return node;
    }
}
