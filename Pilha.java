public class Pilha {
    private Ponto[] dados;
    private int topo;

    public Pilha(int capacidade) {
        dados = new Ponto[capacidade];
        topo = -1;
    }

    public void empilhar(Ponto p) {
        if (topo == dados.length - 1) {
            throw new RuntimeException("Pilha cheia");
        }
        dados[++topo] = p;
    }

    public Ponto desempilhar() {
        if (estaVazia()) {
            throw new RuntimeException("Pilha vazia");
        }
        return dados[topo--];
    }

    public boolean estaVazia() {
        return topo == -1;
    }
}