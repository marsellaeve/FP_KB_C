package id.ac.theAppies;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Game {
	JFrame window;
	Container con;
	JPanel titleNamePanel, startButtonPanel,mainTextPanel;
	JLabel titleNameLabel;
	Font titleFont = new Font("Times New Roman",Font.PLAIN,90);
	Font normalFont = new Font("Times New Roman",Font.PLAIN,30);
	JButton startButton;
	JTextArea mainTextArea;
	JButton choice1,choice2,choice3,choice4;
	TitleScreenHandler tsHandler = new TitleScreenHandler();
	
	public static void main(String[] args) {
		new Game();
	}
	public Game() {
		window = new JFrame();
		window.setSize(800,650);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.DARK_GRAY);
		window.setLayout(null);
		window.setVisible(true);
		con = window.getContentPane();
		
		titleNamePanel = new JPanel();
		titleNamePanel.setBounds(100,100,600,150);
		titleNamePanel.setBackground(Color.DARK_GRAY);
		titleNameLabel = new JLabel("The Appies");
		titleNameLabel.setForeground(Color.white);
		titleNameLabel.setFont(titleFont);
		
		startButtonPanel = new JPanel();
		startButtonPanel.setBounds(300,400,200,100);
		startButtonPanel.setBackground(Color.DARK_GRAY);
	
		startButton = new JButton("START");
		startButton.setBackground(Color.DARK_GRAY);
		startButton.setForeground(Color.white);
		startButton.setFont(normalFont);
		startButton.addActionListener(tsHandler);

		startButtonPanel.add(startButton);
		titleNamePanel.add(titleNameLabel);
		con.add(titleNamePanel);
		con.add(startButtonPanel);
		con.revalidate();
	}
	public void createGameScreen() {
		titleNamePanel.setVisible(false);
		startButtonPanel.setVisible(false);
		mainTextPanel = new JPanel();
		mainTextPanel.setBounds(100,100, 600,250);
		mainTextPanel.setBackground(Color.blue);
		con.add(mainTextPanel);
		
		mainTextArea = new JTextArea("");
		mainTextArea.setBackground(Color.DARK_GRAY);
		mainTextArea.setBounds(100,100,600,250);
		mainTextArea.setForeground(Color.white);
		mainTextArea.setFont(normalFont);
		mainTextArea.setLineWrap(true);
		mainTextPanel.add(mainTextArea);
	}
	
	public class TitleScreenHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			createGameScreen();
		}
	}
}
