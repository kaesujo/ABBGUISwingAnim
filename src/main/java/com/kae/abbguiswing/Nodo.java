package com.kae.abbguiswing;

public class Nodo {
    private int info;
    private Nodo esquerda=null; // inicializacao null redundante pq sou inseguro
    private Nodo direita=null; // novamente, pq sou inseguro

    public Nodo() {
        esquerda=null;
        direita=null;
    }

    public Nodo(int info) {
        this.info = info;
        esquerda=null;
        direita=null;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public Nodo getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(Nodo esquerda) {
        this.esquerda = esquerda;
    }

    public Nodo getDireita() {
        return direita;
    }

    public void setDireita(Nodo direita) {
        this.direita = direita;
    }
}
