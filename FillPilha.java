import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FillPilha {
    private BufferedImage imagem;
    private int corFundo;
    private int novaCor;
    private int largura;
    private int altura;
    private int x;
    private int y;

    public FillPilha(BufferedImage imagem, int x, int y) {
        this.imagem = imagem;
        this.x = x;
        this.y = y;

        this.largura = imagem.getWidth();
        this.altura = imagem.getHeight();

        this.corFundo = imagem.getRGB(x,y);

        this.novaCor = new Color(123, 45, 167).getRGB();
    }

    public void executar() {
        if (corFundo == novaCor) {
            System.out.println("A cor inicial já é a cor de preenchimento.");
            return;
        }

        Pilha pilhaPrimeiraExecucao = new Pilha(largura * altura);
        pilhaPrimeiraExecucao.empilhar(new Ponto(x, y));

        int controleCorNova = 0;
        int contadorFrames = 0;

        File pastaFrames = new File("frames");
        if (!pastaFrames.exists()) {
            pastaFrames.mkdir();
        }

        System.out.println("Iniciando FloodFill");

        while (!pilhaPrimeiraExecucao.estaVazia()) {
            Ponto pixelSentinela = pilhaPrimeiraExecucao.desempilhar();
            int x = pixelSentinela.x;
            int y = pixelSentinela.y;

            if (x < 0 || x>= largura || y < 0 || y >= altura) {
                continue;
            }

            if (imagem.getRGB(x,y) == corFundo) {
                imagem.setRGB(x,y, novaCor);
                controleCorNova++;

                pilhaPrimeiraExecucao.empilhar(new Ponto(x, y - 1));
                pilhaPrimeiraExecucao.empilhar(new Ponto(x, y + 1));
                pilhaPrimeiraExecucao.empilhar(new Ponto(x - 1, y));
                pilhaPrimeiraExecucao.empilhar(new Ponto(x + 1, y));

                if (controleCorNova % 200 == 0) {
                    salvarFrame(contadorFrames);
                    contadorFrames++;
                }
            }
        }

        salvarFrame(contadorFrames);
        System.out.println("Processo concluído\nTotal de frames: " + controleCorNova);
    }

    private void salvarFrame(int contadorFrames) {
        try {
            String nomeArquivo = String.format("frames/frame_%03d.png", contadorFrames);

            File arquivoSaida = new File(nomeArquivo);

            ImageIO.write(imagem, "png", arquivoSaida);
        } catch (Exception e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}