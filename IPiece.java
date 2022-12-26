/***************************************************************************************************
*IPiece is a subclass of Tetromino and inherits its methods
*@author Aadith Charugundla, Alexander Talamonti, and Julian Gavino
****************************************************************************************************/
import java.awt.*;

public class IPiece extends Tetromino {   

   /************************************************************* 
  	* Contructs an IPiece with it's four block coordinates and it's Color  
   * @param x X-coordinate of top left block
   * @param y Y-coordinate of top left block
   * @param c Color of the Tetromino 
  	**************************************************************/
   public IPiece(int x, int y, Color c, String img) {
      super(x, x, x, x, y, y+1, y+2, y+3, c, img)  ; 
      /*
      0  0 1 2 3
      1
      2
      3
      */
      myLowestBlock.add(3);
   }
   
   
   /************************************************************* 
   * Makes the IPiece rotate clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   boolean horizontal = false; //Cycles this boolean after every turn.
   public void turnClockwise() {
          //All the blocks will start in the vertical position     
         //Switch the previous block 1 with current block 4
         //Switches the x and y values for easy rotation when vertical
      if ((myYVals[0] < 16 && myXVals[0] < 7) &&collided == false) {   
         if(horizontal == true) {
            myXVals[1] = myXVals[1] - 1; 
            myXVals[2] = myXVals[2] - 2; 
            myXVals[3] = myXVals[3] - 3; 
            myYVals[1] = myYVals[0] + 1;
            myYVals[2] = myYVals[0] + 2;
            myYVals[3] = myYVals[0] + 3;
            horizontal = false;
            myLowestBlock.clear();
            myLowestBlock.add(3); //Adds the value three as the block in the lowest position.
         } 
         else {
            
            myXVals[1] = myXVals[0] + 1; 
            myXVals[2] = myXVals[0] + 2; 
            myXVals[3] = myXVals[0] + 3; 
            myYVals[1] = myYVals[1] - 1;
            myYVals[2] = myYVals[2] - 2;
            myYVals[3] = myYVals[3] - 3;
            horizontal = true;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(1);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         }
      }
   
   }
   
   /************************************************************* 
   * Makes the IPiece rotate counter-clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
  
   public void turnCounterClockwise() {
      if ((myYVals[0] < 16 && myXVals[0] < 7) &&collided == false) {   
         if(horizontal == true) {
            myXVals[1] = myXVals[1] - 1; 
            myXVals[2] = myXVals[2] - 2; 
            myXVals[3] = myXVals[3] - 3; 
            myYVals[1] = myYVals[0] + 1;
            myYVals[2] = myYVals[0] + 2;
            myYVals[3] = myYVals[0] + 3;
            horizontal = false;
            myLowestBlock.clear();
            myLowestBlock.add(3);
         } 
         else {
            
            myXVals[1] = myXVals[0] + 1; 
            myXVals[2] = myXVals[0] + 2; 
            myXVals[3] = myXVals[0] + 3; 
            myYVals[1] = myYVals[1] - 1;
            myYVals[2] = myYVals[2] - 2;
            myYVals[3] = myYVals[3] - 3;
            horizontal = true;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(1);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         
         }
      }
   
   }
   

}