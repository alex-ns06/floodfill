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

        // Pegando a altura e largura da imagem
        this.largura = imagem.getWidth();
        this.altura = imagem.getHeight();
        
        // Pegando a cor do ponto inicial
        this.corFundo = imagem.getRGB(inicioX, inicioY);
        
        // Atribuindo a nova cor a ser preenchida (a cor a respeito é um tom escuro de roxo)
        this.novaCor = new Color(123, 45, 167).getRGB(); 
    }

    // Método para execução da lógica principal usando loop while
    public void executar() {
        // Verifica se a cor inicial é a mesma da cor de preenchimento
        if (corFundo == novaCor) {
            System.out.println("A cor inicial já é a cor de preenchimento.");
            return;
        }
        
        // Definindo a altura e largura inicial para não dar "Fila Cheia" caso a imagem seja muito grande
        Fila filaPrimariaExecucao = new Fila(largura * altura);
        filaPrimariaExecucao.enfileirar(new Ponto(inicioX, inicioY));

        // VAriáveis de controle da execução
        int controleCorNova = 0;
        int contadorFrames = 0;

        // Cria pasta para armazenar os frames caso ela não exista
        File pastaFrames = new File("frames");
        if (!pastaFrames.exists()) {
            pastaFrames.mkdir();
        }

        System.out.println("Iniciando Flood Fill com Fila...");

        // loop da lógica principal
        while (!filaPrimariaExecucao.estaVazia()) {
            Ponto pixelSentinela = filaPrimariaExecucao.desenfileirar();
            int x = pixelSentinela.x;
            int y = pixelSentinela.y;

            // Verifica se não saiu dos limites
            if (x < 0 || x >= largura || y < 0 || y >= altura) {
                continue;
            }

            // Verifica se a cor atual é a cor que queremos substituir
            if (imagem.getRGB(x, y) == corFundo) {
                
                // Pinta com a nova cor
                imagem.setRGB(x, y, novaCor);
                controleCorNova++;

                // Enfileira os vizinhos
                filaPrimariaExecucao.enfileirar(new Ponto(x, y - 1));
                filaPrimariaExecucao.enfileirar(new Ponto(x, y + 1));
                filaPrimariaExecucao.enfileirar(new Ponto(x - 1, y));
                filaPrimariaExecucao.enfileirar(new Ponto(x + 1, y));

                // A cada X pixels pintados, a imagem salva automaticamente (deve ser alterado de acordo com o tamanho da imagem original)
                if (controleCorNova % 200 == 0) {
                    salvarFrame(contadorFrames);
                    contadorFrames++;
                }
            }
        }
        
        // Salva a imagem final depois do loop
        salvarFrame(contadorFrames);
        System.out.println("Processo concluído! Total de pixels: " + controleCorNova);
    }

    // Método criado para salvar o a progressão das imagens ao longo da execução do loop
    private void salvarFrame(int numeroFrame) {
        try {
            /*
                Salva o arquivo com 5 casas decimais, ideal caso a imagem tenha muitos pixels ou caso o salvamento seja feito
                em intervalos de poucos pixels. É possível alterar para menos casas decimais para evitar nomes muito grandes.
                Isso garante que os arquivos fiquem em ordem alfabética, facilitando a animação quando a progressão for exibida.
             */
            String nomeArquivo = String.format("frames/frame_%05d.png", numeroFrame);

            // Instância do objeto File que cria um arquivo com o nome acima
            File arquivoSaida = new File(nomeArquivo);

            // Escreve no disco o estado atual da imagem do BufferedImage, utilizando o formato PNG
            ImageIO.write(imagem, "png", arquivoSaida);
        } catch (Exception e) {
            System.out.println("Erro ao salvar frame: " + e.getMessage());
        }
    }
}