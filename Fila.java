public class Fila {
    private Ponto[] dados;
    private int inicio;
    private int fim;
    private int tamanho;

    public Fila(int capacidade) {
        dados = new Ponto[capacidade];
        inicio = 0;
        fim = 0;
        tamanho = 0;
    }

    public void enfileirar(Ponto p) {
        if (tamanho == dados.length) {
            throw new RuntimeException("Fila cheia");
        }
        dados[fim] = p;
        fim = (fim + 1) % dados.length;
        tamanho++;
    }

    public Ponto desenfileirar() {
        if (estaVazia()) {
            throw new RuntimeException("Fila vazia");
        }
        Ponto p = dados[inicio];
        inicio = (inicio + 1) % dados.length;
        tamanho--;
        return p;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }
}