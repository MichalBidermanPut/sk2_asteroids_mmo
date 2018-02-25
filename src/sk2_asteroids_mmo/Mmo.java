package sk2_asteroids_mmo;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Mmo extends JFrame {

	private static final long serialVersionUID = 1L;

	public Mmo(String username) {
        
        initUI(username);
    }
    
    private void initUI(String username) {
        
        add(new Board(username));
        
        setSize(806, 635);
        setResizable(false);
        
        setTitle("ASTEROIDS");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            LogIn ex = new LogIn();
            ex.setVisible(true);
        });
    }
}