import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class FillFila {
    private BufferedImage imagem;
    private int corFundo;
    private int novaCor;
    private int largura;
    private int altura;
    private int inicioX;
    private int inicioY;

    public FillFila(BufferedImage imagem, int inicioX, int inicioY) {
        this.imagem = imagem;
        this.inicioX = inicioX;
        this.inicioY = inicioY;

        this.largura = imagem.getWidth();
        this.altura = imagem.getHeight();
        
        this.corFundo = imagem.getRGB(inicioX, inicioY);
        
        this.novaCor = new Color(123, 45, 167).getRGB(); 
    }

    public void executar() {
        if (corFundo == novaCor) {
            System.out.println("A cor inicial já é a cor de preenchimento.");
            return;
        }
        
        Fila filaPrimariaExecucao = new Fila(largura * altura);
        filaPrimariaExecucao.enfileirar(new Ponto(inicioX, inicioY));

        int controleCorNova = 0;
        int contadorFrames = 0;

        File pastaFrames = new File("frames");
        if (!pastaFrames.exists()) {
            pastaFrames.mkdir();
        }

        System.out.println("Iniciando Flood Fill com Fila...");

        while (!filaPrimariaExecucao.estaVazia()) {
            Ponto pixelSentinela = filaPrimariaExecucao.desenfileirar();
            int x = pixelSentinela.x;
            int y = pixelSentinela.y;

            if (x < 0 || x >= largura || y < 0 || y >= altura) {
                continue;
            }

            if (imagem.getRGB(x, y) == corFundo) {
                
                imagem.setRGB(x, y, novaCor);
                controleCorNova++;

                filaPrimariaExecucao.enfileirar(new Ponto(x, y - 1));
                filaPrimariaExecucao.enfileirar(new Ponto(x, y + 1));
                filaPrimariaExecucao.enfileirar(new Ponto(x - 1, y));
                filaPrimariaExecucao.enfileirar(new Ponto(x + 1, y));

                if (controleCorNova % 200 == 0) {
                    salvarFrame(contadorFrames);
                    contadorFrames++;
                }
            }
        }
        
        salvarFrame(contadorFrames);
        System.out.println("Processo concluído! Total de pixels: " + controleCorNova);
    }

    private void salvarFrame(int numeroFrame) {
        try {
            String nomeArquivo = String.format("frames/frame_%03d.png", numeroFrame);

            File arquivoSaida = new File(nomeArquivo);

            ImageIO.write(imagem, "png", arquivoSaida);
        } catch (Exception e) {
            System.out.println("Erro ao salvar frame: " + e.getMessage());
        }
    }
}