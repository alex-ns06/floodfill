public class Fila {
    private Ponto[] dados;
    private int inicio, fim, tamanho;

    public Fila(int capacidade) {
        dados = new Ponto[capacidade];
        inicio = fim = tamanho = 0;
    }

    public void enfileirar(Ponto p) {
        if (tamanho < dados.length) {
            dados[fim] = p;
            fim = (fim + 1) % dados.length;
            tamanho++;
        }
    }

    public Ponto desenfileirar() {
        if (estaVazia()) return null;
        Ponto p = dados[inicio];
        inicio = (inicio + 1) % dados.length;
        tamanho--;
        return p;
    }

    public boolean estaVazia() { return tamanho == 0; }
}