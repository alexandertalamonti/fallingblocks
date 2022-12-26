/***************************************************************************************************
*ZPiece is a subclass of Tetromino and inherits its methods
*@author Aadith Charugundla, Alexander Talamonti, and Julian Gavino
****************************************************************************************************/
import java.awt.*;

public class ZPiece extends Tetromino {   

   /************************************************************* 
  	 * Contructs an ZPiece with it's four block coordinates and it's Color  
    * @param x X-coordinate of top left block
    * @param y Y-coordinate of top left block
    * @param c Color of the Tetromino 
  	 **************************************************************/
   public ZPiece(int x, int y, Color c, String img) 
   {
      super(x, x, x+1, x+1, y, y+1, y-1, y, c, img); 
      //for this we may need to figure out a way to make sure the value (0) isn't entered
      /* 2
       0 3    0 1
       1        2 3
       */
       myLowestBlock.add(1);
       myLowestBlock.add(3);
   }
   
   
   /************************************************************* 
   * Makes the ZPiece rotate clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   boolean horizontal = false;
   public void turnClockwise() {
      if((myXVals[2] != 9 && myYVals[1] != 19 && myYVals[3] != 19)&&collided == false) {
         if(horizontal == true) {
            myXVals[1] = myXVals[1] - 1;
            myXVals[3] = myXVals[3] - 1;
            myYVals[1] = myYVals[1] + 1;
            myYVals[2] = myYVals[2] - 2; 
            myYVals[3] = myYVals[3] - 1;
            horizontal = false;
            myLowestBlock.clear();
            myLowestBlock.add(1);
            myLowestBlock.add(3);
         } 
         else {
            
            myXVals[1] = myXVals[1] + 1; 
            myXVals[3] = myXVals[3] + 1;
            myYVals[1] = myYVals[1] - 1;
            myYVals[2] = myYVals[2] + 2; 
            myYVals[3] = myYVals[3] + 1;     
            horizontal = true;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         }
      }
   
   }
   
    /************************************************************* 
   * Makes the ZPiece rotate counter-clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   public void turnCounterClockwise() {
      if((myXVals[2] != 9 && myYVals[1] != 19 && myYVals[3] != 19)&&collided == false) {
         if(horizontal == true) {
            myXVals[1] = myXVals[1] - 1;
            myXVals[3] = myXVals[3] - 1;
            myYVals[1] = myYVals[1] + 1;
            myYVals[2] = myYVals[2] - 2; 
            myYVals[3] = myYVals[3] - 1;
            horizontal = false;
            myLowestBlock.clear();
            myLowestBlock.add(1);
            myLowestBlock.add(3);
         
         } 
         else {
            
            myXVals[1] = myXVals[1] + 1; 
            myXVals[3] = myXVals[3] + 1;
            myYVals[1] = myYVals[1] - 1;
            myYVals[2] = myYVals[2] + 2; 
            myYVals[3] = myYVals[3] + 1;     
            horizontal = true;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         
         }
      }
   }

}