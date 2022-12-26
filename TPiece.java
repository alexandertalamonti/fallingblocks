/***************************************************************************************************
*TPiece is a subclass of Tetromino and inherits its methods
*@author Aadith Charugundla, Alexander Talamonti, and Julian Gavino
****************************************************************************************************/
import java.awt.*;

public class TPiece extends Tetromino {   

   /************************************************************* 
  	* Contructs an TPiece with it's four block coordinates and it's Color  
   * @Param x X-coordinate of top left block
   * @Param y Y-coordinate of top left block
   * @Param c Color of the Tetromino 
  	**************************************************************/
   public TPiece(int x, int y, Color c, String img) {
      //y cannot = 0
      super(x, x+1, x+1, x+2, y, y-1, y, y, c, img) ; 
      /* 1     0                1
       0 2 3   1 3   0 1 3    0 2
               2       2        3
      */
      myLowestBlock.add(0);
      myLowestBlock.add(2);
      myLowestBlock.add(3);
   }
   
   
   /************************************************************* 
   * Makes the TPiece rotate clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   int orientation = 0; //Uses a counter to rotate between the different orientations.
   public void turnClockwise() {
      if((myXVals[2] != 0 && myXVals[2] != 9 && myYVals[2] != 19 && myYVals[3] != 19)&&collided == false) {
         if(orientation == 0) {
            myXVals[0] = myXVals[0] + 1;
            myYVals[0] = myYVals[0] - 1;
            
            myYVals[1] = myYVals[1] + 1;
            
            myYVals[2] = myYVals[2] + 1;
            
            orientation++;
            
            myLowestBlock.clear();
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         } else if (orientation == 1) {
            myXVals[0] = myXVals[0] - 1;
            myYVals[0] = myYVals[0] + 1;
            
            orientation++;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(2);
            myLowestBlock.add(3);

         } else if (orientation == 2) {
            myYVals[1] = myYVals[1] - 1;
            
            myYVals[2] = myYVals[2] - 1;
            myXVals[3] = myXVals[3] - 1;
            myYVals[3] = myYVals[3] + 1;
            
            orientation++;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(3);            
         } else {
            myXVals[3] = myXVals[3] + 1;
            myYVals[3] = myYVals[3] - 1;
            
            orientation = 0;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         }
      
      }
   }
   
   /************************************************************* 
   * Makes the TPiece rotate counter-clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   public void turnCounterClockwise() { //Counterclockwise has to rotate the Tetromino back to the position that it was just in that allowed a clockwise rotation to get it in that position
      if((myXVals[2] != 9 && myXVals[2] != 0 && myYVals[2] != 19 && myYVals[3] != 19)&&collided == false) { 
      
         if(orientation == 0) {
            myXVals[3] = myXVals[3] - 1;
            myYVals[3] = myYVals[3] + 1;
            
            orientation = 3;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(3);
         } else if (orientation == 1) {
            myXVals[0] = myXVals[0] - 1;
            myYVals[0] = myYVals[0] + 1;
            
            myYVals[1] = myYVals[1] - 1;
            
            myYVals[2] = myYVals[2] - 1;
            
            orientation--;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         } else if (orientation == 2) {
            myXVals[0] = myXVals[0] + 1;
            myYVals[0] = myYVals[0] - 1;
            
            orientation--;
            myLowestBlock.clear();
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         } else {
            myYVals[1] = myYVals[1] + 1;
            
            myYVals[2] = myYVals[2] + 1;
            myXVals[3] = myXVals[3] + 1;
            myYVals[3] = myYVals[3] - 1;
            orientation--;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         }
      
      }
   
   }
   

}