import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SwingProgresso extends JFrame {
    private JLabel label;
    private BufferedImage imagem;
    private int clickX, clickY;
    private boolean novoClique = false;

    public SwingProgresso(BufferedImage img) {
        this.imagem = img;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel(new ImageIcon(img));
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickX = e.getX();
                clickY = e.getY();
                novoClique = true;
                synchronized (SwingProgresso.this) {
                    SwingProgresso.this.notify();
                }
            }
        });
        setVisible(true);
    }

    public void atualizar() {
        label.setIcon(new ImageIcon(imagem));
        label.repaint();
    }

    public synchronized void aguardarClique() throws InterruptedException {
        novoClique = false;
        while (!novoClique) {
            this.wait();
        }
    }

    public int getClickX() { return clickX; }
    public int getClickY() { return clickY; }
}