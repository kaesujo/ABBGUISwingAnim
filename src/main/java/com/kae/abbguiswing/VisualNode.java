package com.kae.abbguiswing;
import java.awt.*;
import java.awt.*;

public class VisualNode extends Nodo {
    private Point currentPos;
    private Point startPos=null; // Posição inicial
    private Point targetPos=null; // Posição de destino
    private long startTime, elapsedTime;
    private static double speed=1; // Vetor de velocidade
    double animationDuration=1200;

    private VisualNode esquerda;
    private VisualNode direita;

    VisualNode(int info, Point startPos, Point targetPos) {
        super(info);
        this.startPos = startPos;
        this.targetPos = targetPos;
        this.currentPos = new Point(startPos);
        this.startTime = System.currentTimeMillis();
    }

    public VisualNode() {
        super();
    }

    public void updatePosition() {
        long currentTime = System.currentTimeMillis();
        setElapsedTime(currentTime - getStartTime());

        double progress = getElapsedTime() * getSpeed() / animationDuration;

        // Certifique-se de que o progresso esteja dentro dos limites [0, 1]
        progress = Math.min(1.0, progress);

        // Lógica de animação para cada nó (interpolação linear entre posição inicial e posição de destino)
        int deltaX = targetPos.x - startPos.x;
        int deltaY = targetPos.y - startPos.y;

        int newX = (int) (startPos.x + progress * deltaX);
        int newY = (int) (startPos.y + progress * deltaY);

        currentPos.setLocation(newX, newY);

        // Se o progresso atingiu 1.0, a animação deve parar
        if (progress >= 1.0) {
            setElapsedTime(0);
        }
    }


    private long getAnimationDuration() {
        return targetPos.x != startPos.x ? Math.abs(targetPos.x - startPos.x) : Math.abs(targetPos.y - startPos.y);
    }

    public Point getCurrentPos() {
        return currentPos;
    }

    public Point getStartPos() {
        return startPos;
    }

    public Point getTargetPos() {
        return targetPos;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public VisualNode getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(VisualNode esquerda) {
        this.esquerda = esquerda;
    }

    @Override
    public VisualNode getDireita() {
        return direita;
    }

    public void setDireita(VisualNode direita) {
        this.direita = direita;
    }


}
