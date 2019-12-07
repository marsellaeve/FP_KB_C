package id.ac.theAppies;

//MyLogin.java
import javax.swing.*;

import java.awt.EventQueue;
import java.awt.event.*;

public class Home {
	private JFrame f = new JFrame("Login");
	private JButton bok = new JButton("OK");
	
	public Home() {
	
		f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		f.getContentPane().add(bok);
		
		bok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
				 EventQueue.invokeLater(() -> {
		            App ex = new App();
		            ex.setVisible(true);
		        });
			}
		});
		f.setSize(800,600);
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Home();
	}
}