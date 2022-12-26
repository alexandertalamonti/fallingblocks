/*****************************************************************
* TetrisDriver is the driver that will run the rest of the program.
* Only the driver needs to be run in order for the rest of the program
* to start working, since the driver contains TetrisPanel which contains
* all the other panels in our program
* @author Aadith Charugundla, Alexander Talamonti, Julian Gavino
******************************************************************/

//imports 
import javax.swing.JFrame; 


public class TetrisDriver extends JFrame
{  
   public static void main(String[] args) //main method
   {
      JFrame frame = new JFrame("Falling Blocks"); //creates a new frame with the title of "Falling Blocks"
      frame.setSize(900, 900); //sets the size of the frame to 1200 by 1200 pixels
      frame.setLocation(90,0); //sets the location of the frame on the screen
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes the frame 
      frame.setContentPane(new TetrisPanel(frame)); //sets TetrisPanel as the current content pane
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //sets the frame so that it's maximized (full screen)
      frame.setVisible(true); //makes the frame visible
      frame.setFocusable(true); //makes the frame the object of focus
   }
}