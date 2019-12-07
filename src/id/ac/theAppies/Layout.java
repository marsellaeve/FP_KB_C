package id.ac.theAppies;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Layout extends JFrame {
	private static final String ImageObserver = null;
//	private Component Kotak;
	public static Canvas canvas;
	
	public Layout (String title) {
		super(title);
		this.setSize(300,300);
		this.setLocation(100,100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		JButton button1 = new JButton("Tanya");
		JButton button2 = new JButton("Reload");
		JButton button3 = new JButton("Next");
		JButton button4 = new JButton("Music");
		
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout(8,8));
		container.setBackground(Color.DARK_GRAY);
		this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.DARK_GRAY));
		
		//untuk setting dsb 
		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new FlowLayout(5));
		firstPanel.setBackground(Color.DARK_GRAY);
		container.add(firstPanel, BorderLayout.NORTH);
		
		firstPanel.add(button1);
		firstPanel.add(button2);
		firstPanel.add(button3);
		firstPanel.add(button4);
			
		//untuk kotak karakternya
		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new FlowLayout(5));
		secondPanel.setBackground(Color.DARK_GRAY);
		container.add(secondPanel, BorderLayout.AFTER_LAST_LINE);
		
//		Kotak kotak1 = new Kotak(15, 15);
//		Kotak kotak2 = new Kotak(20, 15);
//		Kotak kotak3 = new Kotak(25, 15);
//		Kotak kotak4 = new Kotak(30, 15);
//		Kotak kotak5 = new Kotak(35, 15);
//		
		
//		secondPanel.add(kotak1);//karakter 1
//		secondPanel.add(kotak2);//karakter 2
//		secondPanel.add(kotak3);//karakter 3
//		secondPanel.add(kotak4);//karakter 4
//		secondPanel.add(kotak5);//karakter 5
//		
		//untuk container nampung koordinate nya
		JLabel label = new JLabel(" ", SwingConstants.CENTER);
		label.setOpaque(true);
		label.setBorder(new LineBorder(Color.BLACK, 2));
		
		JPanel canva = new JPanel();
		canva.setBorder(new LineBorder(Color.BLACK, 2));
		canva.setLayout(new FlowLayout(4,4,4));
		canva.setBackground(Color.DARK_GRAY);
		
//		canva.add(coordinate);//kordinat titiknya
		canva.add(label);
		
		ButtonHandler buttonHandler = new ButtonHandler();
		button1.addActionListener( buttonHandler );
		button2.addActionListener( buttonHandler );
		button3.addActionListener( buttonHandler );
		button4.addActionListener( buttonHandler );
		
		addWindowListener(new WindowAdapter() {	
			public void WindowClosing(WindowEvent e) {
				int pilih = JOptionPane.showConfirmDialog(null, "Yakin Keluar ?", "Pasti ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if( pilih == JOptionPane.YES_OPTION) {
					 e.getWindow().dispose();
					 System.out.println("tutup");
				}
				else {
					 setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
	}
	
	private class ButtonHandler implements ActionListener {	
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Tanya")) {
				canvas.HowToPlay();
			}
			
			else if(e.getActionCommand().equals("Next")) {
				canvas.NextLevel();
			}
			
			else if(e.getActionCommand().equals("Reload")) {
				canvas.Reload();
			}
			else if (e.getActionCommand().equals("Music")) {
				canvas.Music();
			}
		}
	}
}