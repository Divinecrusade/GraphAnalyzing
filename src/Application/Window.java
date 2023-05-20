package Application;
import javax.swing.JFrame;

public class Window extends JFrame {

    public Window() {
        super("Graph analyzing");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main() {
        new Window();
    }
}
