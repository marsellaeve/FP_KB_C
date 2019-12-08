package id.ac.theAppies;

//MyLogin.java
import javax.swing.*;

import java.awt.EventQueue;
import java.awt.event.*;

public class Home {
	private JFrame f = new JFrame("Home");
	private JButton bok = new JButton("Play");
	
	public Home() {
	
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(bok);
		
		bok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				loadBoard();
			}
		});
		f.setSize(800,600);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
	}
	
	void loadBoard() {
		f.getContentPane().removeAll();
		f.getContentPane().add(new Board());
		f.revalidate();
	}
	public static void main(String[] args) {
		new Home();
	}
}