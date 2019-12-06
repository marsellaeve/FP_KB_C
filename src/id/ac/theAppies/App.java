package id.ac.theAppies;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class App extends JFrame {

    public App() {
        
        initUI();
    }
    
    private void initUI() {

        add(new Board());

        setTitle("The Appies");
        setSize(800, 650);
        
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            App ex = new App();
            ex.setVisible(true);
        });
    }
}