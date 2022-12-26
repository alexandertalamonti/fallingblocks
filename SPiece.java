/***************************************************************************************************
*SPiece is a subclass of Tetromino and inherits its methods
*@author Aadith Charugundla, Alexander Talamonti, and Julian Gavino
****************************************************************************************************/
import java.awt.*;

public class SPiece extends Tetromino {   

   /************************************************************* 
  	* Contructs an SPiece with it's four block coordinates and it's Color  
   * @param x X-coordinate of top left block
   * @param y Y-coordinate of top left block
   * @param c Color of the Tetromino 
  	**************************************************************/
   public SPiece(int x, int y, Color c, String img) 
   {
      super(x, x, x+1, x+1, y, y+1, y+1, y+2, c, img)  ; 
      /*
      0
      1 2    2 3
        3  0 1
      */
      myLowestBlock.add(1);
      myLowestBlock.add(3);
   }
   
   
   /************************************************************* 
   * Makes the SPiece rotate clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   boolean horizontal = false;
   public void turnClockwise() 
   {
    if ((myXVals[0] < 8 && myYVals[3] != 19 && myYVals[0] != 19) && collided == false ) {   
         if(horizontal == true) {
            myXVals[1] = myXVals[1] - 1; 
            myXVals[3] = myXVals[3] - 1;
            myYVals[0] = myYVals[0] - 2;
            myYVals[1] = myYVals[1] - 1; 
            myYVals[3] = myYVals[3] + 1;
            horizontal = false;
            
            myLowestBlock.clear();
            myLowestBlock.add(1);
            myLowestBlock.add(3);
         } 
         else {
            
            myXVals[1] = myXVals[1] + 1; 
            myXVals[3] = myXVals[3] + 1;
            myYVals[0] = myYVals[0] + 2;
            myYVals[1] = myYVals[1] + 1; 
            myYVals[3] = myYVals[3] - 1;     
            horizontal = true;
            
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(1);
            myLowestBlock.add(3);
         }
      }
   }
   
   /************************************************************* 
   * Makes the SPiece rotate counter-clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   public void turnCounterClockwise() {
       if ((myXVals[0] < 8 && myYVals[3] != 19 && myYVals[0] != 19) && collided == false ) {   
         if(horizontal == true) {
            myXVals[1] = myXVals[1] - 1; 
            myXVals[3] = myXVals[3] - 1;
            myYVals[0] = myYVals[0] - 2;
            myYVals[1] = myYVals[1] - 1; 
            myYVals[3] = myYVals[3] + 1;
            horizontal = false;
         } 
         else {
            
            myXVals[1] = myXVals[1] + 1; 
            myXVals[3] = myXVals[3] + 1;
            myYVals[0] = myYVals[0] + 2;
            myYVals[1] = myYVals[1] + 1; 
            myYVals[3] = myYVals[3] - 1;     
            horizontal = true;
         }
      }
         //horizontal = true;

   
   }

}