package id.ac.theAppies;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame
{
    private LevelPage titleScreen;
    private Board ingameFrame;
    private JButton startButton;
    
    public MainFrame()
    {
        super("The Adventure's of the Great Arshad!"); //This is super for title name on title bar
 
        //This is setting a naked mainFrame size of the game
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
 
        titleScreen = new LevelPage();
        add(titleScreen);
        
        startButton = new JButton("NEW ADVENTURE");
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.lightGray);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            ingameFrame = new Board();
            add(ingameFrame);
            ingameFrame.setVisible(true);
            titleScreen.setVisible(false);
        });
    }
}