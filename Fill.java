import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Fill {
    // Declarando os atributos
    private BufferedImage imagem;
    private int corFundo;
    private int novaCor;
    private int largura;
    private int altura;
    private int x;
    private int y;

    // Declarando o construtor
    public Fill(BufferedImage imagem, int x, int y) {
        this.imagem = imagem;

        // Pegando a altura e largura da imagem
        this.largura = imagem.getWidth();
        this.altura = imagem.getHeight();

        // Pegando a cor do ponto inicial
        this.corFundo = imagem.getRGB(x,y);

        // Atribuindo a nova cor a ser preenchida (a cor a respeito é um tom escuro de roxo)
        this.novaCor = new Color(123, 45, 167).getRGB();
    }

    // Método para execução da lógica principal usando loop while
    public void executar() {
        // Definindo a largura e altura inicial para não dar "Pilha Cheia" caso a imagem seja muito grande
        Pilha filaPrimeiraExecucao = new Pilha(largura * altura);
        filaPrimeiraExecucao.empilhar(new Ponto(x, y));

        // Variáveis de controle da execução
        int controleCorNova = 0;
        int contadorFrames = 0;

        // Cria pasta para armazenar os frames caso ela não exista
        File pastaFrames = new File("frames");
        if (!pastaFrames.exists()) {
            pastaFrames.mkdir();
        }

        System.out.println("Iniciando FloodFill");

        // Loop da lógica principal
        while (!filaPrimeiraExecucao.estaVazia()) {
            Ponto pixelSentinela = filaPrimeiraExecucao.desempilhar();
            int x = pixelSentinela.x;
            int y = pixelSentinela.y;

            // Verifica se não saiu dos limites da matriz da imagem
            if (x < 0 || x>= largura || y < 0 || y >= altura) {
                continue;
            }

            // Verifica se a cor de fundo é a cor que queremos substituir
            if (imagem.getRGB(x,y) == corFundo) {
                // Pinta com a cor nova
                imagem.setRGB(x,y, novaCor);
                controleCorNova++;

                // Empilha os vizinhos
                filaPrimeiraExecucao.empilhar(new Ponto(x, y - 1));
                filaPrimeiraExecucao.empilhar(new Ponto(x, y + 1));
                filaPrimeiraExecucao.empilhar(new Ponto(x - 1, y));
                filaPrimeiraExecucao.empilhar(new Ponto(x + 1, y));

                // A cada X pixels pintados, a imagem salva automaticamente (deve ser alterado de acordo com o tamanho da imagem original)
                if (controleCorNova % 200 == 0) {
                    salvarFrame(contadorFrames);
                    contadorFrames++;
                }
            }
        }

        // Salva a imagem final depois do loop
        salvarFrame(contadorFrames);
        System.out.println("Processo concluído\nTotal de frames: " + controleCorNova);
    }

    // Método criado para salvar o a progressão das imagens ao longo da execução do loop
    private void salvarFrame(int contadorFrames) {
        try {
            /*
                Salva o arquivo com 5 casas decimais, ideal caso a imagem tenha muitos pixels ou caso o salvamento seja feito
                em intervalos de poucos pixels. É possível alterar para menos casas decimais para evitar nomes muito grandes.
                Isso garante que os arquivos fiquem em ordem alfabética, facilitando a animação quando a progressão for exibida.
             */
            String nomeArquivo = String.format("frames/frame_%05d.png", contadorFrames);

            // Instância do objeto File que cria um arquivo com o nome acima
            File arquivoSaida = new File(nomeArquivo);

            // Escreve no disco o estado atual da imagem do BufferedImage, utilizando o formato PNG
            ImageIO.write(imagem, "png", arquivoSaida);
        } catch (Exception e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}