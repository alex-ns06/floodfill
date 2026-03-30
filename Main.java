import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int larguraTela = (int) screenSize.getWidth();
            int alturaTela = (int) screenSize.getHeight();

            BufferedImage imgOriginal = ImageIO.read(new File("imagem.png"));
            BufferedImage img = new BufferedImage(larguraTela, alturaTela, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = img.createGraphics();
            g.drawImage(imgOriginal, 0, 0, larguraTela, alturaTela, null);
            g.dispose();

            Scanner sc = new Scanner(System.in);
            System.out.println("1-Fila | 2-Pilha:");
            int op = sc.nextInt();

            SwingProgresso gui = new SwingProgresso(img);

            FillFila ff = new FillFila(img, gui);
            FillPilha fp = new FillPilha(img, gui);

            while (true) {
                gui.aguardarClique();
                if (op == 1) ff.executar(gui.getClickX(), gui.getClickY());
                else fp.executar(gui.getClickX(), gui.getClickY());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}