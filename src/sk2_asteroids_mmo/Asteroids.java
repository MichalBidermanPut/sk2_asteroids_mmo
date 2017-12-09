package sk2_asteroids_mmo;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Asteroids extends JFrame {

	private static final long serialVersionUID = 1L;

	public Asteroids() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
        
        setSize(400, 300);
        setResizable(false);
        
        setTitle("Moving sprite");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
        	Asteroids ex = new Asteroids();
            ex.setVisible(true);
        });
    }
}