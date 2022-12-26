/***************************************************************************************************
*OPiece is a subclass of Tetromino and inherits its methods
*@author Aadith Charugundla, Alexander Talamonti, and Julian Gavino
****************************************************************************************************/
import java.awt.*;

public class OPiece extends Tetromino {   

   /************************************************************* 
  	* Contructs an OPiece with it's four block coordinates and it's Color  
   * @param x X-coordinate of top left block
   * @param y Y-coordinate of top left block
   * @param c Color of the Tetromino 
  	**************************************************************/
   public OPiece(int x, int y, Color c, String img) 
   {
      super(x, x, x + 1, x + 1, y, y + 1, y, y + 1, c, img)  ; 
      /*
      0 2 
      1 3
      */
      myLowestBlock.add(1);
      myLowestBlock.add(3);
   }

   //The OPiece is a square so the clockwise and counterClockwise methods could be left as they were
   
   /************************************************************* 
   * Makes the OPiece rotate clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   public void turnClockwise() {}
   
   /************************************************************* 
   * Makes the OPiece rotate counter-clockwise and is a concrete method that 
   * is defined differently in each of the Tetromino subclasses.
   * Also changes which blocks are in the lowest position.
   **************************************************************/
   public void turnCounterClockwise() {}
   

}