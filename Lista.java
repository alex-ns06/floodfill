public class Lista {
    private No inicio;

    public void adicionar(Ponto p) {
        No novo = new No(p);
        if (inicio == null) {
            inicio = novo;
        } else {
            No aux = inicio;
            while (aux.proximo != null) aux = aux.proximo;
            aux.proximo = novo;
        }
    }

    public boolean estaVazia() { return inicio == null; }
}