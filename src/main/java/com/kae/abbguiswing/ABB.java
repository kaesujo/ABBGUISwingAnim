package com.kae.abbguiswing;

public class ABB {
    private Nodo raiz;

    public ABB() { }

    public ABB(int[] lista) {
        raiz = null;
        for (int x : lista)
            inserir(x);
    }

    public ABB(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    private boolean busca(Nodo nodo, int x) {
        if (nodo == null) {
            return false;
        }
        if (nodo.getInfo() == x) {
            return true;
        } else if (x < nodo.getInfo()) {
            return busca(nodo.getEsquerda(), x);
        } else {
            return busca(nodo.getDireita(), x);
        }
    }

    public boolean busca(int x) {
        return busca(this.raiz, x);
    }

    private static Nodo inserirRec(Nodo nodo, int x) {
        if (nodo == null) {
            return new Nodo(x);
        }
        if (x < nodo.getInfo()) {
            nodo.setEsquerda(inserirRec(nodo.getEsquerda(), x));
        } else if (x > nodo.getInfo()) {
            nodo.setDireita(inserirRec(nodo.getDireita(), x));
        }
        return nodo;
    }

    public void inserir(int x) {
        raiz = inserirRec(raiz, x);
    }

    private static void exibirRec(Nodo nodo, int nivel) {
        if (nodo != null) {
            exibirRec(nodo.getDireita(), nivel + 1);

            for (int i = 0; i < nivel; i++)
                System.out.print("\t");

            if (nivel == 0) {
                System.out.print("\u001B[1;31m");
                System.out.print(nodo.getInfo());
                System.out.println("\u001B[0m");
            } else {
                System.out.println(nodo.getInfo());
            }

            exibirRec(nodo.getEsquerda(), nivel + 1);
        } else if (nivel == 0) {
            System.out.println("Árvore vazia");
        }
    }

    public static void exibir(Nodo raiz) {
        System.out.print("ABB: ");
        System.out.print("\u001B[1;31m");
        System.out.print("(vermelho = raiz)");
        System.out.println("\u001B[0m");
        exibirRec(raiz, 0);
    }

    public void exibir() {
        exibir(raiz);
    }

    // DEMAIS IMPLEMENTACOES A PARTIR DAQUI:

    private int contarNodos(Nodo raiz) {
        if (raiz == null) return 0;
        else
            return (
                    1
                            + contarNodos(raiz.getEsquerda())
                            + contarNodos(raiz.getDireita())
            ); // separado em linhas para melhor leitura
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    public static int contarFolhas(Nodo raiz) {
        if (raiz == null)
            return 0;
        else if (raiz.getEsquerda() == null && raiz.getDireita() == null)
            return 1;
        else
            return contarFolhas(raiz.getEsquerda()) + contarFolhas(raiz.getDireita());
    }

    public int contarFolhas() {
        return contarFolhas(raiz);
    }

    public static int soma(Nodo raiz) {
        if (raiz == null)
            return 0;
        else
            return (
                    raiz.getInfo()
                            + soma(raiz.getEsquerda())
                            + soma(raiz.getDireita())
            );
    }

    public int soma() {
        return soma(raiz);
    }

    private static Nodo menor(Nodo raiz) {
        if (raiz == null) {
            return null; // A árvore é vazia, retorna null
        } else if (raiz.getEsquerda() == null) {
            return raiz;
        } else {
            return menor(raiz.getEsquerda());
        }
    }

    public Nodo menor() {
        return menor(raiz);
    }

    private static Nodo maior(Nodo raiz) {
        if (raiz == null) {
            return null; // A árvore é vazia, retorna null
        } else if (raiz.getDireita() == null) {
            return raiz;
        } else {
            return maior(raiz.getDireita());
        }
    }

    public Nodo maior() {
        return maior(raiz);
    }

    // constroi uma arvore mais balanceada
    // considerando começar do meio de uma lista ordenada
    public static Nodo construirArvoreMelhor(int[] lista, int inicio, int fim) {
        // Caso base: lista vazia
        if (inicio > fim) {
            return null;
        }
        int meio = (inicio + fim) / 2;

        Nodo raiz = new Nodo(lista[meio]);
        raiz.setEsquerda(construirArvoreMelhor(lista, inicio, meio - 1));
        raiz.setDireita(construirArvoreMelhor(lista, meio + 1, fim));

        return raiz;
    }

    public static int altura(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }
        int alturaEsquerda, alturaDireita;
        alturaEsquerda = altura(raiz.getEsquerda());
        alturaDireita = altura(raiz.getDireita());
        return (1 + Math.max(alturaEsquerda, alturaDireita));
    }

    public int altura() {
        return altura(raiz);
    }

    /*
        Pre-ordem: visita o nodo, subarv. esq, subarv. direita
        Em ordem: visita a subarv. esq., nodo, subarv. direita
        Pós-ordem: visita a subarv. esq, subarv. direita, nodo
     */
    private static void exibirPreOrdem(Nodo raiz) {
        if (raiz==null)
            return;

        System.out.print(raiz.getInfo() + "  ");
        exibirPreOrdem(raiz.getEsquerda());
        exibirPreOrdem(raiz.getDireita());
    }

    public void exibirPreOrdem() {
        exibirPreOrdem(raiz);
    }
    private static void exibirEmOrdem(Nodo raiz) {
        if (raiz==null)
            return;

        exibirEmOrdem(raiz.getEsquerda());
        System.out.print(raiz.getInfo() + "  ");
        exibirEmOrdem(raiz.getDireita());
    }

    public void exibirEmOrdem() {
        exibirEmOrdem(raiz);
    }

    private static void exibirPosOrdem(Nodo raiz) {
        if (raiz==null)
            return;

        exibirPosOrdem(raiz.getEsquerda());
        exibirPosOrdem(raiz.getDireita());
        System.out.print(raiz.getInfo() + "  ");
    }

    public void exibirPosOrdem() {
        exibirPosOrdem(raiz);
    }

}
