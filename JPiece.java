/***************************************************************************************************
*JPiece is a subclass of Tetromino and inherits its methods
*@author Aadith Charugundla, Alexander Talamonti, and Julian Gavino
****************************************************************************************************/
import java.awt.*;

public class JPiece extends Tetromino {   

   /************************************************************* 
  	* Contructs an JPiece with it's four block coordinates and it's Color  
   * @param x X-coordinate of top left block
   * @param y Y-coordinate of top left block
   * @param c Color of the Tetromino 
  	**************************************************************/
   public JPiece(int x, int y, Color c, String img) {
      //y must be at least 2
      super(x, x+1, x+1, x+1, y, y-2, y-1, y, c, img); 
      /* 1           0 3  
         2   0       1    0 1 2 (the zero's x never changes)
       0 3   1 2 3   2        3
      */
      myLowestBlock.add(0);
      myLowestBlock.add(3);
   }
   
   
   /************************************************************* 
   * Makes the JPiece rotate clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   int orientation = 0;
   public void turnClockwise() {
      if((myYVals[2] != 19 && myYVals[3] != 19)&&collided == false) {
         if(orientation == 0 && myXVals[3] != 9) {
         
            myYVals[0] = myYVals[0] - 1;
            myXVals[1] = myXVals[1] - 1;
            myYVals[1] = myYVals[1] + 2;
            
            myYVals[2] = myYVals[2] + 1;
            myXVals[3] = myXVals[3] +1;;
            
            orientation++;
            myLowestBlock.clear();
            myLowestBlock.add(1);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         } 
         else if (orientation == 1) {
         
            myYVals[0] = myYVals[0] - 1;
            
            myYVals[1] = myYVals[1] - 1;
            myXVals[2] = myXVals[2] - 1;
            
            myXVals[3] = myXVals[3] - 1;
            myYVals[3] = myYVals[3] - 2;
                        
            orientation++;
            myLowestBlock.clear();
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         } 
         else if (orientation == 2 && myXVals[3] != 9) {
            myYVals[0] = myYVals[0] + 1;
            myXVals[1] = myXVals[1] + 1;
            
            myXVals[2] = myXVals[2] + 2;
            myYVals[2] = myYVals[2] - 1;
            myXVals[3] = myXVals[3] + 1;
            myYVals[3] = myYVals[3] + 2;
            
            orientation++;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(1);
            myLowestBlock.add(3);
         } 
         else if (orientation == 3) {
            myYVals[0] = myYVals[0] + 1;
            
            myYVals[1] = myYVals[1] - 1;
            myXVals[2] = myXVals[2] - 1;
            
            myXVals[3] = myXVals[3] - 1;
            orientation = 0;
            
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(3);
         }
      }
   
   }
   
   /************************************************************* 
   * Makes the JPiece rotate counter-clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   public void turnCounterClockwise() {
   
      if((myYVals[2] != 19 && myYVals[3] != 19)&&collided == false) {
      /* 1           0 3  
         2   0       1    0 1 2 (the zero's x never changes)
       0 3   1 2 3   2        3
      */
         
         if(orientation == 0 && myXVals[3] != 9) {
         
            myYVals[0] = myYVals[0] - 1;
         
            myYVals[1] = myYVals[1] + 1;
            
            myXVals[2] = myXVals[2] + 1;
            myXVals[3] = myXVals[3] + 1;
         
            orientation = 3;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(1);
            myLowestBlock.add(3);
            
         } 
         else if (orientation == 1) {
         
            myYVals[0] = myYVals[0] + 1;
            myXVals[1] = myXVals[1] + 1;
            myYVals[1] = myYVals[1] - 2;
            myYVals[2] = myYVals[2] - 1;
            
            myXVals[3] = myXVals[3] - 1;
                       
            orientation--;
            myLowestBlock.clear();
            myLowestBlock.add(0);
            myLowestBlock.add(3);
         
         } 
         else if (orientation == 2 && myXVals[3] != 9) {
            myYVals[0] = myYVals[0] + 1;
            
            myYVals[1] = myYVals[1] + 1;
            myXVals[2] = myXVals[2] + 1;
         
            myXVals[3] = myXVals[3] + 1;
            myYVals[3] = myYVals[3] + 2;
            
            orientation--;
            myLowestBlock.clear();
            myLowestBlock.add(1);
            myLowestBlock.add(2);
            myLowestBlock.add(3);
            
         } 
         else if (orientation == 3) {
            myYVals[0] = myYVals[0] - 1;
            
            myXVals[1] = myXVals[1] - 1;
            myXVals[2] = myXVals[2] - 2;
            myYVals[2] = myYVals[2] + 1;
            myXVals[3] = myXVals[3] - 1;
            myYVals[3] = myYVals[3] - 2;
            
            orientation--;
            myLowestBlock.clear();
            myLowestBlock.add(2);
            myLowestBlock.add(3);
         
         }
      }
   }
   

}