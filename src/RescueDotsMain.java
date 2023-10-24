import javax.swing.*;
import java.awt.*;

/**
 * @author Agata Wysokinska
 **/

public class RescueDotsMain extends JFrame {

    public RescueDotsMain() {

        initUI();
    }

    private void initUI() {
        RescueDotsSurface surface = new RescueDotsSurface();
        this.add(surface);
        this.setTitle("RescueDots");
        this.setSize(1280, 1024);
        surface.setBackground(Color.darkGray);
        surface.addMouseListener(surface);
        surface.addMouseMotionListener(surface);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                RescueDotsMain ex = new RescueDotsMain();
                ex.setVisible(true);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exp) {
                    exp.printStackTrace();
                }

            }
        });
    }
}