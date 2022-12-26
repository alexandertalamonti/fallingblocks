/***************************************************************************************************
*LPiece is a subclass of Tetromino and inherits its methods
*@author Aadith Charugundla, Alexander Talamonti, and Julian Gavino
****************************************************************************************************/
import java.awt.*;

public class LPiece extends Tetromino {   

   /************************************************************* 
  	* Contructs an LPiece with it's four block coordinates and it's Color  
   * @param x X-coordinate of top left block
   * @param y Y-coordinate of top left block
   * @param c Color of the Tetromino 
  	**************************************************************/
   public LPiece(int x, int y, Color c, String img) {
   /* 0     0 2 3   0 1   
      1     1         2       2
      2 3             3   0 1 3
   */ 
      super(x, x, x, x+1, y, y + 1, y + 2, y + 2, c, img)  ; 
      myLowestBlock.add(2);
      myLowestBlock.add(3);
   }
   
   
   /************************************************************* 
   * Makes the LPiece rotate clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   int orientation = 0;
   public void turnClockwise() {
      if((myYVals[1] != 19 && myYVals[3] != 19)&&collided == false) { 
         if(orientation == 0  && myXVals[3] != 9) {
            myXVals[2] = myXVals[2] + 1;
            myYVals[2] = myYVals[2] - 2;
            myXVals[3] = myXVals[3] + 1;
            myYVals[3] = myYVals[3] - 2;
            
            orientation++;
            
            myLowestBlock.clear();
            myLowestBlock.add(1);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
            
         } 
         else if (orientation == 1) {
            
            myXVals[1] = myXVals[1] + 1;
            myYVals[1] = myYVals[1] - 1;
            
            myYVals[2] = myYVals[2] + 1;
            
            myXVals[3] = myXVals[3] - 1;
            myYVals[3] = myYVals[3] + 2;
            
            orientation++;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(3);
         
         } 
         else if (orientation == 2 && myXVals[0] != 0) {
            myXVals[0] = myXVals[0] - 1;
            myYVals[0] = myYVals[0] + 2;
            myXVals[1] = myXVals[1] - 1;
            myYVals[1] = myYVals[1] + 2;
         
         
            orientation++;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(1);
            myLowestBlock.add(3);
            
         } 
         else if (orientation == 3){
            myXVals[0] = myXVals[0] + 1;
            myYVals[0] = myYVals[0] - 2;
            myYVals[1] = myYVals[1] - 1;
            myXVals[2] = myXVals[2] - 1;
            myYVals[2] = myYVals[2] + 1;
         
            orientation = 0;
            myLowestBlock.clear();
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         
         }
      }
   
   }
   
   /************************************************************* 
   * Makes the LPiece rotate counter-clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   public void turnCounterClockwise() {
         
      if((myYVals[1] != 19 && myYVals[3] != 19)&&collided == false) {
         if(orientation == 0 && myXVals[0] != 0) {
            myXVals[0] = myXVals[0] - 1;
            myYVals[0] = myYVals[0] + 2;
            
            myYVals[1] = myYVals[1] + 1;
            
            myXVals[2] = myXVals[2] + 1;
            myYVals[2] = myYVals[2] - 1;
         
            orientation = 3;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(1);
            myLowestBlock.add(3);
         
            
         } 
         else if (orientation == 1) {
            myXVals[2] = myXVals[2] - 1;
            myYVals[2] = myYVals[2] + 2;
            myXVals[3] = myXVals[3] - 1;
            myYVals[3] = myYVals[3] + 2;
         
            orientation--;
            myLowestBlock.clear();
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         
         } 
         else if (orientation == 2 && myXVals[3] != 9) {
         
            myXVals[1] = myXVals[1] - 1;
            myYVals[1] = myYVals[1] + 1;
         
            myYVals[2] = myYVals[2] - 1;
            myXVals[3] = myXVals[3] + 1;
            myYVals[3] = myYVals[3] - 2;
         
            orientation--;
            myLowestBlock.clear();
            myLowestBlock.add(1);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         } 
         else if (orientation == 3) {
            myXVals[0] = myXVals[0] + 1;
            myYVals[0] = myYVals[0] - 2;
            myXVals[1] = myXVals[1] + 1;
            myYVals[1] = myYVals[1] - 2;
                     
            orientation--;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(3);
         
         }
      
      }
   
   
   }
}