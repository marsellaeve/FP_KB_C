package id.ac.theAppies;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
/**
 * Created by Sir.Dominar on 20/07/2017.
 */
public class LevelPage extends JPanel
{
    private JPanel titleNamePanel, titleMenuButtonPanel, blankPanel;
    private JLabel titleNameLabel;
    public static JButton startButton;
    private JButton continueButton;
    private JButton exitButton;
    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 32);
    private Font startButtonFont = new Font("Times New Roman", Font.PLAIN, 30);
    private Border noBorder = BorderFactory.createEmptyBorder();
 
    public LevelPage()
    {
        //This creates a blank panel to center the text
        blankPanel = new JPanel(new BorderLayout());
        blankPanel.setBounds(320, 200, 600, 150);
        blankPanel.setBackground(Color.blue);
 
        //This is used to create the text panel
        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(320, 200, 600, 150);
        titleNamePanel.setBackground(Color.BLACK);
        titleNameLabel = new JLabel("ADVENTURES OF THE GREAT ARSHAD!");
        titleNameLabel.setForeground(Color.lightGray);
        titleNameLabel.setFont(titleFont);
 
        //This is used to create the button menu panel
        titleMenuButtonPanel = new JPanel();
        titleMenuButtonPanel.setBounds(420, 400, 400, 200);
        titleMenuButtonPanel.setBackground(Color.BLACK);
 
        //This creates the start button
        startButton = new JButton("NEW ADVENTURE");
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.lightGray);
        startButton.setFont(startButtonFont);
        startButton.setBorder(noBorder);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            titleMenuButtonPanel.setVisible(false);
            titleMenuButtonPanel.setEnabled(false);
            titleNamePanel.setVisible(false);
            titleNamePanel.setEnabled(false);
            blankPanel.setVisible(false);
            blankPanel.setEnabled(false);
 
            new Board();
        });
 
        continueButton = new JButton("CONTINUE ADVENTURE");
        continueButton.setBackground(Color.black);
        continueButton.setForeground(Color.lightGray);
        continueButton.setFont(startButtonFont);
        continueButton.setBorder(noBorder);
        continueButton.setFocusPainted(false);
        continueButton.addActionListener((ActionListener) e -> System.out.println("TEST CONTINUE OUTPUT"));
 
                //This creates the exit button
        exitButton = new JButton("EXIT THE ARSHAD");
        exitButton.setBackground(Color.black);
        exitButton.setForeground(Color.lightGray);
        exitButton.setFont(startButtonFont);
        exitButton.setBorder(noBorder);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener((ActionEvent e) -> System.exit(0)); //This closes the application on button press.
 
        //This adds the title name to the title panel
        add(titleNamePanel);
        titleNamePanel.add(titleNameLabel);
 
        //This adds the menu buttons to the panel
        setLayout(new BorderLayout());
        add(titleMenuButtonPanel);
        titleMenuButtonPanel.add(startButton);
        titleMenuButtonPanel.add(continueButton);
        titleMenuButtonPanel.add(exitButton);
 
        //This adds the blank panel to the top so the buttons are centered
        add(blankPanel);
    }
}