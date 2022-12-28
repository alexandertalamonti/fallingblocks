/*****************************************************************
* InstructionsPanel is the panel that contains the rules of the 
* game and gives examples of how the game works
* @author Aadith Charugundla, Alexander Talamonti, Julian Gavino
******************************************************************/

//imports
import java.awt.*;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;



public class InstructionsPanel extends JPanel {
   private static final ImageIcon background = new ImageIcon("Tetris Background.png");
   private BufferedImage myImage;
   private Graphics buffer; 
   
   /*
   *InstructionsPanel is the panel that will hold all the instructions of the game
   *@param myFrame the current Frame
   */
   public InstructionsPanel(JFrame myFrame) { 
      //sets the layout of instructionsPanel
      setLayout(new BorderLayout());
      
      //creates a buffer and draws an iamge on it
      myImage = new BufferedImage(1200, 1200, BufferedImage.TYPE_INT_RGB);
      buffer = myImage.getGraphics();
      buffer.drawImage(background.getImage(), 0, 0, 1200, 1200, null);
      
      //JLabel containing the instructions of the game
      JLabel instructionsPart1 = new JLabel("<html><div style='text-align: center;'>" + " The aim of the game is to get as many points as you can within the time limit. This is done "
            + "by arranging<br/> the tetraminoes as they fall. Every time the tetraminoes fill a row you will be awarded points."
            + "<br/> Clearing lines will<br/> give you 100 points per line. "
            + "<br/> To move the piece left and right, use the left and right arrow keys. To rotate<br/> the piece clockwise, press the "
            + "up arrow key. To move the piece counter-clockwise,<br/> press the down arrow key. To hard drop the piece, press the spacebar.<br/>Good luck and have fun!" + "</div></html>", SwingConstants.CENTER); 
      
      instructionsPart1.setForeground(Color.black); 
      instructionsPart1.setFont(new Font("Monospaced",Font.BOLD, 20)); 
         
      add(instructionsPart1, BorderLayout.NORTH);
      
      //adds a back button
      JButton backButton = new JButton("Back"); 
      backButton.addActionListener(new BackListener(myFrame)); 
      add(backButton, BorderLayout.SOUTH); 
      
      //JPanel that will hold our two example gifs
      JPanel gifPanel = new JPanel(); 
      gifPanel.setLayout(new GridLayout(2, 2));
      gifPanel.setOpaque(false);  
      add(gifPanel, BorderLayout.CENTER); 
      
      JLabel desc1 = new JLabel("Normal Line Clear:");
      desc1.setFont(new Font("Monospaced", Font.BOLD, 34));
      desc1.setForeground(Color.black);  
      desc1.setHorizontalAlignment(SwingConstants.CENTER); 
      gifPanel.add(desc1);  
      
      JLabel desc2 = new JLabel("Tetris:");
      desc2.setFont(new Font("Monospaced", Font.BOLD, 34));
      desc2.setForeground(Color.black); 
      desc2.setHorizontalAlignment(SwingConstants.CENTER);  
      gifPanel.add(desc2);
      
      String gifName = "instructionsExample.gif";
      ImageIcon gifIcon = new ImageIcon(gifName);
      gifIcon.getImage().flush();
      JLabel imgLabel = new JLabel(); 
      imgLabel.setHorizontalAlignment(SwingConstants.CENTER); 
      imgLabel.setIcon(gifIcon);
      gifPanel.add(imgLabel); 
      
      String gifName2 = "instructionsExample2.gif";
      ImageIcon gifIcon2 = new ImageIcon(gifName2);
      gifIcon2.getImage().flush();
      JLabel imgLabel2 = new JLabel(); 
      imgLabel2.setHorizontalAlignment(SwingConstants.CENTER); 
      imgLabel2.setIcon(gifIcon2);
      gifPanel.add(imgLabel2); 
      
   }
   
   /*
   *BackListener is a listener that will return the user to the main menu
   */
   private class  BackListener implements ActionListener
   {  
      JFrame theFrame;   
      public BackListener(JFrame frame) {

         theFrame = frame;
      }
      
      public void actionPerformed(ActionEvent e)
      {
         theFrame.setContentPane(new TetrisPanel(theFrame));
         theFrame.setVisible(true);  
      }
   } 
   
    //paints the buffered-image
   public void paintComponent(Graphics g) {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }


}