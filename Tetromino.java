/***********************************************************************************************
*Tetromino is the superclass of all the Tetris blocks and contains basic
*methods that every Tetromino should be able to perform. Tetromino is composed
*of multiple accessor and modifier methods for each of its class variables. It also has two
*methods, turnClockwise() and turnCounterClockwise() which must be overwtitten in each subclass.
*Every class variable in Tetromino is protected so only the subclass can access them.
*@authors Aadith Charugundla, Alexander Talamonti, Julian Gavino
************************************************************************************************/
import java.awt.*;
import java.util.ArrayList;

public class Tetromino {
   
   /*******************************************************************************
   * Creates an array that stores the X-values of each block inside the Tetromino.
   *******************************************************************************/
   protected int[] myXVals = new int[4];
   /*******************************************************************************
   * Creates an array that stores the X-values of each block inside the Tetromino.
   *******************************************************************************/   
   protected int[] myYVals = new int[4];
   /****************************************************************************************************
   * Creates an ArrayList of type int that stores the number of the blocks that have nothing under them.
   ****************************************************************************************************/   
   protected ArrayList<Integer> myLowestBlock = new ArrayList<>();
   /*********************************************************************
   * Creates a Color variable that stores the color of the block.
   *********************************************************************/ 
   protected Color myColor;
   /**************************************************************************************************
   * Creates a boolean variable that stores the state of the Tetromino (Whether it has stopped or not).
   ***************************************************************************************************/ 
   protected boolean hasStopped = false;
   /******************************************************************************************************************************
   * Creates a boolean variable that stores the state of the Tetromino (Whether it has landed on top of another Tetromino or not).
   ******************************************************************************************************************************/ 
   protected boolean collided = false;
   /***************************************************************************************************************
   * Creates an int variable that stores the X value of the leftmost block of the Tetromino after it has stopped.
   ***************************************************************************************************************/ 
   protected int finalX;
   /***************************************************************************************************************
   * Creates a string variable that stores the name of the image file used to represent the block.
   ***************************************************************************************************************/ 
   protected String myImg;

   /************************************************************* 
   * Contructs a tetromino with the coordinates of four blocks and the Color
   * By convention, the first block is the top-leftmost block and the 
   * fourth block is the bottom right-most. Each block starts in the vertical position.   
   * @param xone Block 1 X-coordinate
   * @param xtwo Block 2 X-coordinate
   * @param xthree Block 3 X-coordinate
   * @param xfour Block 4 X-coordinate
   * @param yone Block 1 Y-coordinate
   * @param ytwo Block 2 Y-coordinate
   * @param ythree Block 3 Y-coordinate
   * @param yfour Block 4 Y-Coordinate
   * @param c The color of the Tetromino
   * @param img The image file used to represent the block
   **************************************************************/
   public Tetromino(int xone, int xtwo, int xthree, int xfour, int yone, int ytwo, int ythree, int yfour, Color c, String img) {
      myXVals[0] = xone;
      myXVals[1] = xtwo;
      myXVals[2] = xthree;
      myXVals[3] = xfour;
      
      myYVals[0] = yone;
      myYVals[1] = ytwo;
      myYVals[2] = ythree;
      myYVals[3] = yfour;
      myColor = c;
      myImg = img;
   }
   
   /***************************************************************************************************************
   * Makes the Tetromino move left while it isn't touching the edge of the board.
   ***************************************************************************************************************/ 
   public void moveLeft() {
      if(myXVals[0] != 0 && myYVals[0] != 19 && myYVals[1] != 19 && myYVals[2] != 19 && myYVals[3] != 19) {
         for(int x = 0; x < 4; x++) {
            myXVals[x]--;
         }
      }
   }
   
   /***************************************************************************************************************
   * Makes the Tetromino move right while it isn't touching the edge of the board.
   ***************************************************************************************************************/ 
   public void moveRight() {
      if( myXVals[3] != 9 && myYVals[0] != 19 && myYVals[1] != 19 && myYVals[2] != 19 && myYVals[3] != 19) {
         for(int x = 0; x < 4; x++) {
            myXVals[x]++;
         }
      }
   }
   
   /***************************************************************************************************************
   * Slowly moves the Tetromino down until it reaches the end or another Tetromino.
   ***************************************************************************************************************/ 
   public void moveDown() {
      if((myYVals[0] != 19 && myYVals[1] != 19 && myYVals[2] != 19 && myYVals[3] != 19)&& collided == false) {
         for(int y = 0; y < 4; y++) {
            myYVals[y]++;
            
            if(myYVals[0] != 19 && myYVals[1] != 19 && myYVals[2] != 19 && myYVals[3] != 19) {
               checkCollided(); //Calls the check collided method after every move down. 
            }
         }
      }
      
      else if (collided) { 
         hasStopped = true;
         PlayPanel.setMatrix(myYVals[0], myXVals[0], 1);
         PlayPanel.setMatrix(myYVals[1], myXVals[1], 1);
         PlayPanel.setMatrix(myYVals[2], myXVals[2], 1);
         PlayPanel.setMatrix(myYVals[3], myXVals[3], 1);
         finalX = myXVals[0]; //Once the Tetromino has stopped, we set the finalX variable to the leftmost block to use later for spawning.
      } 
      else { //This is called if the block can't move down but isn't touching another block
         hasStopped = true; 
         PlayPanel.setMatrix(myYVals[0], myXVals[0], 1);
         PlayPanel.setMatrix(myYVals[1], myXVals[1], 1);
         PlayPanel.setMatrix(myYVals[2], myXVals[2], 1);
         PlayPanel.setMatrix(myYVals[3], myXVals[3], 1);
         finalX = myXVals[0];
      }
   }
   /***************************************************************************************************************
   * Instantly moves the Tetromino down until it reaches the end or another Tetromino.
   ***************************************************************************************************************/ 
   public void hardDown() { //This method is more or less the same as moveDown but is only called once and moves the block down instantly.
      while((myYVals[0] != 19 && myYVals[1] != 19 && myYVals[2] != 19 && myYVals[3] != 19)&& collided == false) {
         for(int y = 0; y < 4; y++) {
            myYVals[y]++;
            if(myYVals[0] != 19 && myYVals[1] != 19 && myYVals[2] != 19 && myYVals[3] != 19) {
               checkCollided();
            }
         
         }
      }
      if (collided){
         hasStopped = true;
         PlayPanel.setMatrix(myYVals[0], myXVals[0], 1);
         PlayPanel.setMatrix(myYVals[1], myXVals[1], 1);
         PlayPanel.setMatrix(myYVals[2], myXVals[2], 1);
         PlayPanel.setMatrix(myYVals[3], myXVals[3], 1);
         finalX = myXVals[0];
         return; //If it has collided, it already has called the above methods, so exit this method.
      }
      
      hasStopped = true;
      PlayPanel.setMatrix(myYVals[0], myXVals[0], 1);
      PlayPanel.setMatrix(myYVals[1], myXVals[1], 1);
      PlayPanel.setMatrix(myYVals[2], myXVals[2], 1);
      PlayPanel.setMatrix(myYVals[3], myXVals[3], 1);
      finalX = myXVals[0];
   
   }


   /************************************************************* 
   * Makes the tetromino rotate clockwise and is a method that 
   * will be defined differently in each of the Tetromino subclasses.
   **************************************************************/
   public void turnClockwise() {}
   
   /************************************************************* 
   * Makes the tetromino rotate counterclockwise and is a method that 
   * will be defined differently in each of the Tetromino subclasses.
   **************************************************************/
   public void turnCounterClockwise() {}
   
   /************************************************************************
   * This method checks whether a Tetromino has fallen on another Tetromino.
   ************************************************************************/
   public void checkCollided() {
      for(int x = 0; x < myLowestBlock.size(); x++) { //For all the elements in the ArrayList myLowestBlocks
         if(PlayPanel.getMatrixVal(myYVals[myLowestBlock.get(x)]+1,myXVals[myLowestBlock.get(x)]) >0) { //If the block below the current lowest block is not empty
            collided = true; //Then the Tetromino has collided with another Tetromino
         }
      }
   }
   
   /************************************************************* 
   * A method that returns the X-coordinate of a single block in 
   * the myXVals array. 
   * @param index The Index of the Array 
   * @return Tetromino block X-coordinate
   **************************************************************/
   public int getX(int index) {
      return myXVals[index];
   }
   
   /************************************************************* 
   * A method that returns the Y-coordinate of a single block in 
   * the myYVals array. 
   * @param index The Index of the Array
   * @return Tetromino block Y-coordinatte
   **************************************************************/
   public int getY(int index) {
      return myYVals[index];
   }
   
   /************************************************************* 
   * Is a method that will return the color of the tetromino 
   * @return Tetromino color
   **************************************************************/
   public Color getColor() {
      return myColor;
   }
   
   /************************************************************* 
   * A method that returns the value of the hasStopped variable. 
   * @return Returns the state of the tetromino (if it has stopped).
   **************************************************************/
   public boolean getStopped() {
      return hasStopped;
   }
   
   /*********************************************************************
   * A method that returns the ArrayList myLowestBlock
   * @return Returns the ArrayList of the lowest blocks in each Tetromino
   **********************************************************************/
   public ArrayList<Integer> getMyLowest() {
      return myLowestBlock;
   }
   
   /************************************************************************** 
   * A method that returns the value of finalX.
   * @return Returns the top-leftmost block's X-coordinate after it has stopped.
   ***************************************************************************/
   public int getFinalX() {
      return finalX;
   }
   
   /************************************************************************** 
   * A method that returns the value of myImg.
   * @return Returns the image used to represent the Tetromino.
   ***************************************************************************/
   public String getImg() {
      return myImg;
   }
}