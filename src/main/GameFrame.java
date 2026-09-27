package main;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import javax.swing.JLabel;


public class GameFrame extends JFrame{

  public GamePanel gamePanel;
  
  //Frame Constructor 
  public GameFrame(){
    
    //Setup
    this.setTitle("Zombiethon"); //Sets title
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sets close operation
    this.setResizable(false); //Sets if frame can be resized
    
    ImageIcon image = new ImageIcon("images/AK-12.png");// creates an ImageIcon
    this.setIconImage(image.getImage());
    //set background into white
    this.getContentPane().setBackground(Color.white);
    
    gamePanel = new GamePanel();
    this.add(gamePanel);
    this.pack();

    this.setVisible(true); //Sets if frame is visible
    this.setLocationRelativeTo(null); //Sets location of frame to center of screen
    
  }
  
}