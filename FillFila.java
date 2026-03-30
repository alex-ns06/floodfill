import java.awt.Color;
import java.awt.image.BufferedImage;

public class FillFila {
    private BufferedImage imagem;
    private int largura, altura;
    private SwingProgresso gui;

    private int corDePreenchimento = new Color(255, 0, 0).getRGB();

    public FillFila(BufferedImage imagem, SwingProgresso gui) {
        this.imagem = imagem;
        this.largura = imagem.getWidth();
        this.altura = imagem.getHeight();
        this.gui = gui;
    }

    public void executar(int x, int y) {
        int corFundo = imagem.getRGB(x, y);
        if (corFundo == corDePreenchimento) return;

        Fila fila = new Fila(largura * altura);
        fila.enfileirar(new Ponto(x, y));

        int pixelsPintados = 0;
        while (!fila.estaVazia()) {
            Ponto p = fila.desenfileirar();
            if (p.x < 0 || p.x >= largura || p.y < 0 || p.y >= altura) continue;

            if (imagem.getRGB(p.x, p.y) == corFundo) {
                imagem.setRGB(p.x, p.y, corDePreenchimento);
                pixelsPintados++;

                fila.enfileirar(new Ponto(p.x, p.y - 1));
                fila.enfileirar(new Ponto(p.x, p.y + 1));
                fila.enfileirar(new Ponto(p.x - 1, p.y));
                fila.enfileirar(new Ponto(p.x + 1, p.y));

                if (pixelsPintados % 50 == 0) {
                    gui.atualizar();
                    try { Thread.sleep(5); } catch (Exception e) {}
                }
            }
        }
        gui.atualizar();
    }
}