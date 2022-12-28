/*****************************************************************
* CreditsPanel is the panel that contains all the citations for
* the sources we used to create our game
* @author Aadith Charugundla, Alexander Talamonti, Julian Gavino
******************************************************************/

//imports
import java.awt.*;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;

public class CreditsPanel extends JPanel {

   private static final ImageIcon background = new ImageIcon("Tetris Background.png");
   private BufferedImage myImage;
   private Graphics buffer; 
   
   /*
   *CreditsPanel is the panel that will hold the credits and citations
   *@param myFrame the current Frame
   */
   public CreditsPanel(JFrame myFrame) { 
      //sets the layout of CreditsPanel
      setLayout(new BorderLayout());

      //creates a new buffer and draws on it
      myImage = new BufferedImage(1200, 1200, BufferedImage.TYPE_INT_RGB);
      buffer = myImage.getGraphics();
      buffer.drawImage(background.getImage(), 0, 0, 1200, 1200, null);
      
      //JLabel containing all the sources that we had
      JLabel instructionsPart1 = new JLabel("<html><div style='text-align: center;'>" + " This game was made by : Aadith Charugundla, Julian Gavino, and Alexander Talamonti. "
            + "<br/>To create sprites and gifs, the online website Piskelapp.com was used. "
            + "<br/>To code the game, the application JGrasp was used. "
            + "<br/>To create music, the application musescore 2 was used. "
            + "<br/>We got the background picture from https://goo.gl/wJqCiD"
            + "<br/>Summer Computer Science 2018"
            + "<br/>Teacher: Mr. Rose "
            + "</div></html>", SwingConstants.CENTER); 
         
      instructionsPart1.setForeground(Color.black); 
      instructionsPart1.setFont(new Font("Monospaced",Font.BOLD, 20)); 
         
      add(instructionsPart1, BorderLayout.NORTH);
      
      //adds a back button
      JButton backButton = new JButton("Back"); 
      backButton.addActionListener(new BackListener(myFrame)); 
      add(backButton, BorderLayout.SOUTH); 
   }
   
   /*
   *BackListener will bring the user back to the main menu
   */
   private class BackListener implements ActionListener
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



