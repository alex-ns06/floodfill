public class Main {
    public static void main(String[] args) {
        try {
            String caminhoImagem = "imagem.png";
            int x = 60;
            int y = 60;

            java.awt.image.BufferedImage imagem =
                javax.imageio.ImageIO.read(new java.io.File(caminhoImagem));

            System.out.println("Escolha o método:");
            System.out.println("1 - Fila");
            System.out.println("2 - Pilha");

            int opcao = System.in.read() - '0';

            if (opcao == 1) {
                FillFila fill = new FillFila(imagem, x, y);
                fill.executar();
                System.out.println("Executado com Fila.");
            } else if (opcao == 2) {
                FillPilha fill = new FillPilha(imagem, x, y);
                fill.executar();
                System.out.println("Executado com Pilha.");
            } else {
                System.out.println("Opção inválida.");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}