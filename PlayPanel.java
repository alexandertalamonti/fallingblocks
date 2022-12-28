/*****************************************************************
* PlayPanel is the panel that contains the actual game. 
* @author Aadith Charugundla, Alexander Talamonti, Julian Gavino
******************************************************************/
//To do list: HOLD FEATURE, NEXT BLOCK FEATURE, RANDOMIZED GRAB BAG FEATURE, FIX COLLISIONS FROM LEFT TO RIGHT, FIX BUGS, MAKE THEMES UNLOCKABLE, CREATE A BETTER SCOREBOARD, BACK AND RESET BUTTONS
//imports
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*; 
import java.util.*; 
import javax.swing.JOptionPane;


public class PlayPanel extends JPanel {

   public static int[][] matrix = new int[20][10]; //creates a matrix that stores 0s for spaces and 1s for blocks
   public static JButton[][] board; //creates a JButton matrix that will act as the grid
   private int[] scoresArray; //creates an intArray that will hold all the previous scores
   private String[] namesArray; //creates a String array that will hold all the previous names
   private static javax.swing.Timer timer; //creates a swing timer 
   private static ArrayList<Tetromino> sequence = new ArrayList<>();  //creates an arrayList called sequence 
   private static int sequencer; 
   private static int blockNumber = 0;
   private static int prevBlock = 0;
   private static int scoreScores = 5; 
   private static Key key;
   private static int totalScore = 0;
   private static int numItems = 0;  
   private JLabel scoreLabel2; 
   private JLabel[] labelArray; //creates a JLabel array that will store labels for the leaderboard

   /***
   * PlayPanel is the panel that will hold the game and the leaderboard
   * @param myFrame the frame that it will be on
   * @param theme the appearance of the tetrominos
   * @param musVolume the volume of the song
   ***/
   public PlayPanel(JFrame myFrame, String theme, float musVolume) {
      
      //creates a Scanner object that will check TetrisScores.txt and will create one if it doesn't exist
      Scanner infile = null;
      try{
         infile = new Scanner(new File("TetrisScores.txt"));
      }
      catch(FileNotFoundException e){
         File firstSave = new File("TetrisScores.txt");
         PrintStream newSaveFile = null;
         try
         {
            newSaveFile = new PrintStream("TetrisScores.txt");
            newSaveFile.println(numItems); 
            infile = new Scanner(firstSave);  
         }
         catch (FileNotFoundException f)
         {
            JOptionPane.showMessageDialog(null, "Something has gone wrong. Please restart the program and try again!");
         }
               
      }       
      numItems = infile.nextInt(); 
      scoresArray = new int[numItems]; 
      namesArray = new String[numItems];
      
      for(int x = 0; x < numItems; x++)
      {
         scoresArray[x] = infile.nextInt(); 
         namesArray[x] = infile.next(); 
      }
      infile.close();
      
      //sets the layout of PlayPanel
      setLayout(new BorderLayout());
      
      
      JPanel scorePanel = new JPanel();
      scorePanel.setLayout(new FlowLayout());
      add(scorePanel, BorderLayout.NORTH);
      
      //creates the leaderboard panel
      JLabel highscoreLabel = new JLabel("HighScores: ");
      scorePanel.add(highscoreLabel); 
      
      labelArray = new JLabel[scoreScores];
      
      for(int x = 0; x < scoreScores; x++)
      {
         labelArray[x] = new JLabel(""); 
         scorePanel.add(labelArray[x]); 
      }
      
      
      int[] newArray = sort(scoresArray); //sorts the array by largest first
      
      if(numItems >=5) //only gets called if the number of entered scores is greater than or equal to 5
         output(newArray, namesArray);        
      
      JPanel mainPanel = new JPanel(); 
      mainPanel.setLayout(new GridLayout(1, 3)); 
      add(mainPanel, BorderLayout.CENTER); 
      
      JPanel leftPanel = new JPanel(); 
      leftPanel.setLayout(new BorderLayout()); 
      mainPanel.add(leftPanel);  
      
      JPanel boardPanel = new JPanel();
      boardPanel.setLayout(new GridLayout(20, 10));
      mainPanel.add(boardPanel);
      
      //create the board
      
      // on Mac, must setOpaque(true) and setBorderPainted(false)

      board = new JButton[20][10];
      for (int r = 0; r < 20; r++) {
         for (int c = 0; c < 10; c++) {
            board[r][c] = new JButton("");
            board[r][c].setBackground(Color.black);
            board[r][c].setOpaque(true);
            board[r][c].setBorderPainted(false);
            board[r][c].setEnabled(false);
            boardPanel.add(board[r][c]); 
            
            matrix[r][c] = 0; //0 signifies that there is nothing (black space)
         }
         
      }
      
      JPanel nextPiece = new JPanel();
      nextPiece.setLayout(new GridLayout(3, 1));  
      mainPanel.add(nextPiece); 
      
      JLabel nextLabel = new JLabel("", SwingConstants.CENTER); 
      nextLabel.setFont(new Font("Monospaced", Font.BOLD, 16)); 
      nextPiece.add(nextLabel); 
      
      JLabel scoreLabel = new JLabel("Score:", SwingConstants.CENTER); 
      scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 16)); 
      nextPiece.add(scoreLabel); 
      
      scoreLabel2 = new JLabel("----", SwingConstants.CENTER); 
      scoreLabel2.setFont(new Font("Monospaced", Font.BOLD, 16)); 
      nextPiece.add(scoreLabel2); 
      
      timer = new javax.swing.Timer(500, new Listener(theme)); //starts the timer that will make the pieces move down and update
      timer.start();
      
      key = new Key(); //add key input to the game
      addKeyListener(key);
      setFocusable(true);
   
      // we create the very first block in the constructor so the program doesn't bug the hell out
      sequencer = (int)(Math.random() * 7 + 1);
      prevBlock = sequencer;
      if(theme.equalsIgnoreCase("Default Theme")) {
         switch(sequencer) {
            case 1: sequence.add(new IPiece(0, 0, Color.CYAN, "Ipiece")); 
               break;
            case 2: sequence.add(new OPiece(0, 0, Color.MAGENTA, "Opiece")); 
               break;
            case 3: sequence.add(new LPiece(0, 0, Color.BLUE, "Lpiece")); 
               break;
            case 4: sequence.add(new JPiece(0, 2, Color.GREEN, "Jpiece")); 
               break;
            case 5: sequence.add(new SPiece(0, 0, Color.YELLOW, "Spiece")); 
               break;
            case 6: sequence.add(new ZPiece(0, 1, Color.ORANGE, "Zpiece")); 
               break;
            case 7: sequence.add(new TPiece(0, 1, Color.RED, "Tpiece")); 
               break;
            default: System.out.println("Something has gone wrong...");
         }

      } else if (theme.equalsIgnoreCase("Warm Theme")) {
         switch(sequencer) {
            case 1: Color warmCol1 = new Color(255,192,107);
               sequence.add(new IPiece(0, 0, warmCol1, "Ipiece-warm")); 
               break;
            case 2: Color warmCol2 = new Color(255,114,38);
               sequence.add(new OPiece(0, 0, warmCol2, "Opiece-warm")); 
               break;
            case 3: Color warmCol3 = new Color(229, 44, 44);
               sequence.add(new LPiece(0, 0, warmCol3, "Lpiece-warm")); 
               break;
            case 4: Color warmCol4 = new Color(255, 2, 158); 
               sequence.add(new JPiece(0, 2, warmCol4, "Jpiece-warm")); 
               break;
            case 5: 
               sequence.add(new SPiece(0, 0, Color.YELLOW, "Spiece-warm")); 
               break;
            case 6: Color warmCol6 = new Color(255, 214, 0);
               sequence.add(new ZPiece(0, 1, warmCol6, "Zpiece-warm")); 
               break;
            case 7: Color warmCol7 = new Color(204, 162, 0);
               sequence.add(new TPiece(0, 1, warmCol7, "Tpiece-warm")); 
               break;
            default: System.out.println("Something has gone wrong...");
         }
         
      } else if (theme.equalsIgnoreCase("Cool Theme")) {
         switch(sequencer) {
            case 1: Color coolCol1 = new Color(197,75,244);
               sequence.add(new IPiece(0, 0, coolCol1, "Ipiece-cool")); 
               break;
            case 2: Color coolCol2 = new Color(4,7,124);
               sequence.add(new OPiece(0, 0, coolCol2, "Opiece-cool")); 
               break;
            case 3: Color coolCol3 = new Color(0, 0, 255);
               sequence.add(new LPiece(0, 0, coolCol3, "Lpiece-cool")); 
               break;
            case 4: Color coolCol4 = new Color(85,26,139); 
               sequence.add(new JPiece(0, 2, coolCol4, "Jpiece-cool")); 
               break;
            case 5: Color coolCol5 = new Color(7,148,148);
               sequence.add(new SPiece(0, 0, coolCol5, "Spiece-cool")); 
               break;
            case 6: Color coolCol6 = new Color(24,165,66);
               sequence.add(new ZPiece(0, 1, coolCol6, "Zpiece-cool")); 
               break;
            case 7: Color coolCol7 = new Color(75,244,169);
               sequence.add(new TPiece(0, 1, coolCol7, "Tpiece-cool")); 
               break;
            default: System.out.println("Something has gone wrong...");
         }
         
      } else if (theme.equalsIgnoreCase("Transparent Theme")) {
         switch(sequencer) {
            case 1:
               sequence.add(new IPiece(0, 0, Color.WHITE, "Ipiece-transparent")); 
               break;
            case 2: 
               sequence.add(new OPiece(0, 0, Color.WHITE, "Opiece-transparent")); 
               break;
            case 3: 
               sequence.add(new LPiece(0, 0, Color.WHITE, "Lpiece-transparent")); 
               break;
            case 4: 
               sequence.add(new JPiece(0, 2, Color.WHITE, "Jpiece-transparent")); 
               break;
            case 5: 
               sequence.add(new SPiece(0, 0, Color.WHITE, "Spiece-transparent")); 
               break;
            case 6: 
               sequence.add(new ZPiece(0, 1, Color.WHITE, "Zpiece-transparent")); 
               break;
            case 7: 
               sequence.add(new TPiece(0, 1, Color.WHITE, "Tpiece-transparent")); 
               break;
            default: System.out.println("Something has gone wrong...");
         } 
      }
      else {
         System.out.println("Something has gone wrong...");
      }
      
      board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(sequence.get(blockNumber).getColor());
      board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(sequence.get(blockNumber).getColor());
      board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(sequence.get(blockNumber).getColor());
      board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(sequence.get(blockNumber).getColor());
      
   }
   
   /**
   * Listener makes the pieces slowly move down 1 spot in the grid at a time
   */
   public class Listener implements ActionListener {
      String theme;
      public Listener(String myTheme) {
         theme = myTheme;
      }
      public void actionPerformed(ActionEvent e) {
         
         if(sequence.get(blockNumber).getStopped() == true) {
            update(theme);
            clearRows();
         
         } 
         else if(sequence.get(blockNumber).getStopped() == false)
         {
            try
            {
               board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(Color.black);
               board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(Color.black);
               board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(Color.black);
               board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(Color.black);
               sequence.get(blockNumber).moveDown();
               board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(sequence.get(blockNumber).getColor());
               board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(sequence.get(blockNumber).getColor());
               board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(sequence.get(blockNumber).getColor());
               board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(sequence.get(blockNumber).getColor());
            }
            catch (ArrayIndexOutOfBoundsException  i) 
            {
               update(theme);
            }
         }
         
      
      }
         
   }

   //programming what the different keys will do in the game
   private class Key extends KeyAdapter{
      public void keyPressed(KeyEvent e) {
      
         if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if((sequence.get(blockNumber).getX(0) != 0) && (sequence.get(blockNumber).getX(1) != 0) && (sequence.get(blockNumber).getX(2) != 0) && (sequence.get(blockNumber).getX(3) != 0) && (sequence.get(blockNumber).getY(0) != 19) && (sequence.get(blockNumber).getY(1) != 19) && (sequence.get(blockNumber).getY(2) != 19) && (sequence.get(blockNumber).getY(3) != 19))
               if( matrix[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0) - 1] != 1 && matrix[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1) - 1] != 1 && matrix[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3) - 1] != 1 && matrix[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2) - 1] != 1 && matrix[sequence.get(blockNumber).getY(0) + 1][sequence.get(blockNumber).getX(0) - 1] != 1 && matrix[sequence.get(blockNumber).getY(1) + 1][sequence.get(blockNumber).getX(1) - 1] != 1 && matrix[sequence.get(blockNumber).getY(3) + 1][sequence.get(blockNumber).getX(3) - 1] != 1 && matrix[sequence.get(blockNumber).getY(2) + 1][sequence.get(blockNumber).getX(2) - 1] != 1)
               {
                  board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(Color.black);
                  board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(Color.black);
                  board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(Color.black);
                  board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(Color.black);
                  sequence.get(blockNumber).moveLeft();
                  board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(sequence.get(blockNumber).getColor());
                  board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(sequence.get(blockNumber).getColor());
                  board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(sequence.get(blockNumber).getColor());
                  board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(sequence.get(blockNumber).getColor());
               }
         }
         if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if((sequence.get(blockNumber).getX(0) != 9) && (sequence.get(blockNumber).getX(1) != 9) && (sequence.get(blockNumber).getX(2) != 9) && (sequence.get(blockNumber).getX(3) != 9) && (sequence.get(blockNumber).getY(0) != 19) && (sequence.get(blockNumber).getY(1) != 19) && (sequence.get(blockNumber).getY(2) != 19) && (sequence.get(blockNumber).getY(3) != 19))
               if( (matrix[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0) + 1] != 1 && sequence.get(blockNumber).getX(0) < 10)&& (matrix[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1) + 1] != 1 && sequence.get(blockNumber).getX(1) < 10)&& (matrix[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3) + 1] != 1 && sequence.get(blockNumber).getX(3) < 10)&& (matrix[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2) + 1] != 1 && sequence.get(blockNumber).getX(2) < 10 )&& (matrix[sequence.get(blockNumber).getY(0) + 1][sequence.get(blockNumber).getX(0) + 1] != 1 && sequence.get(blockNumber).getX(0) < 10)&& (matrix[sequence.get(blockNumber).getY(1) + 1][sequence.get(blockNumber).getX(1) + 1] != 1 && sequence.get(blockNumber).getX(1) < 10)&& (matrix[sequence.get(blockNumber).getY(3) + 1][sequence.get(blockNumber).getX(3) + 1] != 1 && sequence.get(blockNumber).getX(3) < 10 )&& (matrix[sequence.get(blockNumber).getY(2) + 1][sequence.get(blockNumber).getX(2) + 1] != 1 && sequence.get(blockNumber).getX(2) < 10))
               {  
                  board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(Color.black);
                  board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(Color.black);
                  board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(Color.black);
                  board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(Color.black);
                  sequence.get(blockNumber).moveRight();
                  board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(sequence.get(blockNumber).getColor());
                  board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(sequence.get(blockNumber).getColor());
                  board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(sequence.get(blockNumber).getColor());
                  board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(sequence.get(blockNumber).getColor());
               }
         }
         if(e.getKeyCode() == KeyEvent.VK_DOWN){
            board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(Color.black);
            board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(Color.black);
            board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(Color.black);
            board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(Color.black);
            sequence.get(blockNumber).turnCounterClockwise();
            board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(sequence.get(blockNumber).getColor());
            board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(sequence.get(blockNumber).getColor());
            board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(sequence.get(blockNumber).getColor());
            board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(sequence.get(blockNumber).getColor());
         }
         if(e.getKeyCode() == KeyEvent.VK_UP){
            board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(Color.black);
            board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(Color.black);
            board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(Color.black);
            board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(Color.black);
            sequence.get(blockNumber).turnClockwise();
            board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(sequence.get(blockNumber).getColor());
            board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(sequence.get(blockNumber).getColor());
            board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(sequence.get(blockNumber).getColor());
            board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(sequence.get(blockNumber).getColor());
         }
         if(e.getKeyCode() == KeyEvent.VK_SPACE){
            board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(Color.black);
            board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(Color.black);
            board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(Color.black);
            board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(Color.black);
            sequence.get(blockNumber).hardDown();
            board[sequence.get(blockNumber).getY(0)][sequence.get(blockNumber).getX(0)].setBackground(sequence.get(blockNumber).getColor());
            board[sequence.get(blockNumber).getY(1)][sequence.get(blockNumber).getX(1)].setBackground(sequence.get(blockNumber).getColor());
            board[sequence.get(blockNumber).getY(2)][sequence.get(blockNumber).getX(2)].setBackground(sequence.get(blockNumber).getColor());
            board[sequence.get(blockNumber).getY(3)][sequence.get(blockNumber).getX(3)].setBackground(sequence.get(blockNumber).getColor());
         }

      }
   }
   
   /****
   * update creates a new piece when a tetromino reaches the bottom or collides with another tetromino
   * @param myTheme the current theme that the user has selected
   */
   public void update(String myTheme) {
   
      //if the user loses
      if(sequence.get(blockNumber).getStopped() && (sequence.get(blockNumber).getY(0)==1 || sequence.get(blockNumber).getY(1)==1 || sequence.get(blockNumber).getY(2)==1 || sequence.get(blockNumber).getY(3)==1)) {
         timer.stop();
         
         String initials = JOptionPane.showInputDialog("Congratulations: Your score was: " + totalScore + "\nPlease add your name");
         numItems++; 
         PrintStream newSaveFile1 = null;
         try
         {
            newSaveFile1 = new PrintStream("TetrisScores.txt");
            newSaveFile1.println(numItems); 
            for(int x = 0; x < numItems - 1; x++)
            {
               newSaveFile1.println("" + scoresArray[x]);
               newSaveFile1.println(namesArray[x]);
            }
            newSaveFile1.println(totalScore);
            newSaveFile1.println(initials);    
         }
         catch(FileNotFoundException e)
         {
         }
         
         JOptionPane.showMessageDialog(null, "Well Done! If you would like to play again, please close and restart the program! :)"); 
         System.exit(0);
      }
      String theme = myTheme;
      
      //creates a new arrayList grabBag each time that helps the program cycle between the seven different block types
      ArrayList<Integer> grabBag = new ArrayList<>(7);
      for (int m = 0; m < 7; m++) {
         grabBag.add(0);
      }
      //prevents sequence from being filled up with a bunch of random Tetrominos
      if (blockNumber % 7 == 0) {
      
         for (int n = 0; n < 7; n++) {
         
            do 
            {
               sequencer = (int)(Math.random() * 7 + 1);
            } while (grabBag.contains(sequencer));
         
            grabBag.set(n, sequencer);
         
            if(theme.equalsIgnoreCase("Default Theme")) {
               switch(sequencer) {
                  case 1: sequence.add(new IPiece(0, 0, Color.CYAN, "Ipiece")); 
                     break;
                  case 2: sequence.add(new OPiece(0, 0, Color.MAGENTA, "Opiece")); 
                     break;
                  case 3: sequence.add(new LPiece(0, 0, Color.BLUE, "Lpiece")); 
                     break;
                  case 4: sequence.add(new JPiece(0, 2, Color.GREEN, "Jpiece")); 
                     break;
                  case 5: sequence.add(new SPiece(0, 0, Color.YELLOW, "Spiece")); 
                     break;
                  case 6: sequence.add(new ZPiece(0, 1, Color.ORANGE, "Zpiece")); 
                     break;
                  case 7: sequence.add(new TPiece(0, 1, Color.RED, "Tpiece")); 
                     break;
                  default: System.out.println("Something has gone wrong...");
               }

            } else if (theme.equalsIgnoreCase("Warm Theme")) {
               switch(sequencer) {
                  case 1: Color warmCol1 = new Color(255,192,107);
                     sequence.add(new IPiece(0, 0, warmCol1, "Ipiece-warm")); 
                     break;
                  case 2: Color warmCol2 = new Color(255,114,38);
                     sequence.add(new OPiece(0, 0, warmCol2, "Opiece-warm")); 
                     break;
                  case 3: Color warmCol3 = new Color(229, 44, 44);
                     sequence.add(new LPiece(0, 0, warmCol3, "Lpiece-warm")); 
                     break;
                  case 4: Color warmCol4 = new Color(255, 2, 158); 
                     sequence.add(new JPiece(0, 2, warmCol4, "Jpiece-warm")); 
                     break;
                  case 5: 
                     sequence.add(new SPiece(0, 0, Color.YELLOW, "Spiece-warm")); 
                     break;
                  case 6: Color warmCol6 = new Color(255, 214, 0);
                     sequence.add(new ZPiece(0, 1, warmCol6, "Zpiece-warm")); 
                     break;
                  case 7: Color warmCol7 = new Color(204, 162, 0);
                     sequence.add(new TPiece(0, 1, warmCol7, "Tpiece-warm")); 
                     break;
                  default: System.out.println("Something has gone wrong...");
               }
            
            } else if (theme.equalsIgnoreCase("Cool Theme")) {
               switch(sequencer) {
                  case 1: Color coolCol1 = new Color(197,75,244);
                     sequence.add(new IPiece(0, 0, coolCol1, "Ipiece-cool")); 
                     break;
                  case 2: Color coolCol2 = new Color(4,7,124);
                     sequence.add(new OPiece(0, 0, coolCol2, "Opiece-cool")); 
                     break;
                  case 3: Color coolCol3 = new Color(0, 0, 255);
                     sequence.add(new LPiece(0, 0, coolCol3, "Lpiece-cool")); 
                     break;
                  case 4: Color coolCol4 = new Color(85,26,139); 
                     sequence.add(new JPiece(0, 2, coolCol4, "Jpiece-cool")); 
                     break;
                  case 5: Color coolCol5 = new Color(7,148,148);
                     sequence.add(new SPiece(0, 0, coolCol5, "Spiece-cool")); 
                     break;
                  case 6: Color coolCol6 = new Color(24,165,66);
                     sequence.add(new ZPiece(0, 1, coolCol6, "Zpiece-cool")); 
                     break;
                  case 7: Color coolCol7 = new Color(75,244,169);
                     sequence.add(new TPiece(0, 1, coolCol7, "Tpiece-cool")); 
                     break;
                  default: System.out.println("Something has gone wrong...");
               }
            
            } else if (theme.equalsIgnoreCase("Transparent Theme")) {
               switch(sequencer) {
                  case 1:
                     sequence.add(new IPiece(0, 0, Color.WHITE, "Ipiece-transparent")); 
                     break;
                  case 2: 
                     sequence.add(new OPiece(0, 0, Color.WHITE, "Opiece-transparent")); 
                     break;
                  case 3: 
                     sequence.add(new LPiece(0, 0, Color.WHITE, "Lpiece-transparent")); 
                     break;
                  case 4: 
                     sequence.add(new JPiece(0, 2, Color.WHITE, "Jpiece-transparent")); 
                     break;
                  case 5: 
                     sequence.add(new SPiece(0, 0, Color.WHITE, "Spiece-transparent")); 
                     break;
                  case 6: 
                     sequence.add(new ZPiece(0, 1, Color.WHITE, "Zpiece-transparent")); 
                     break;
                  case 7: 
                     sequence.add(new TPiece(0, 1, Color.WHITE, "Tpiece-transparent")); 
                     break;
                  default: System.out.println("Something has gone wrong...");
               } 
            }
            else {
               System.out.println("Something has gone wrong...");
            }
            System.out.print(sequencer + " ");
         
         }
         //if the first block in the new cycle was the same as the last block in the previous cycle
         //switches the first block and the 6th block
         
         prevBlock = grabBag.get(6);
         System.out.println("Prev block = " + prevBlock);
         
      }
   
      //add multiple speeds to make game more challenging at different score levels
      if (totalScore >= 0 && totalScore < 1000) {
         timer.setDelay(500);
      } else if (totalScore >= 1000 && totalScore < 3500) {
         timer.setDelay(400);
      } else if (totalScore >= 3500 && totalScore < 10000) {
         timer.setDelay(300);
      } else if (totalScore >= 10000) {
         timer.setDelay(250);
      }
      
      //makes the next block fall down
      blockNumber++;    
   }
   
   /****
   * sorts the array so that the highest goes first and the lowest goes last
   * @param array the int array that will be sorted
   */
   public int[] sort(int[] array)
   {
   
      int[] temp = new int[numItems]; 
      String[] stringTemp = new String[numItems];
      int largest = 0; 
   
      for(int x = numItems; x > 1; x--)
      {
         for(int y = 1; y < x; y++)
         {
         
            if(array[y] < array[largest])
               largest = y;
            
         }
         temp[x - 1] = array[x - 1 ]; 
         stringTemp[x-1] = namesArray[x-1];
         array[x - 1] = array[largest];
         namesArray[x-1] = namesArray[largest]; 
         array[largest] = temp[x - 1]; 
         namesArray[largest] = stringTemp[x-1];
         largest = 0; 
      
      
      }
      return array;
   }

   /*****
   * output will change the JLabels so that they display the highscores
   * @param no the int array which will be displayed
   * @param yes the String array that will be displayed
   ******/
   public void output(int[] no, String[] yes)
   {
   
      labelArray[0].setText(yes[0] + " " + no[0]); 
      
      for(int x = 1; x < scoreScores; x++)
      {
         labelArray[x].setText(yes[x] + " " + no[x]);
      }
   }
      

   /******
   * returns the value of the matrix at the desired location
   * @param r the row
   * @param c the column
   * @return matrix[r][c]
   *******/
   public static int getMatrixVal(int r, int c) {
      return matrix[r][c];
   }
   
   /******
   * sets the value of the matrix at the desired location to the desired integer
   * @param r the row
   * @param c the column
   * @param x the integer
   ******/
   public static void setMatrix(int r, int c, int x) {
      matrix[r][c] = x;
   }
   
   /******
   * returns the key
   * @return key
   *******/
   public static Key getKey() {
      return key;
   }
   
   /*****
   * will clear a row and bring the row above it down if there are 10 blocks in that row
   ******/
   public void clearRows() {
      int numInRow = 0;
      for(int r = 0; r < matrix.length; r++) {
         for (int c = 0; c < matrix[0].length; c++) {
            if(matrix[r][c] != 0) {
               numInRow++;
            }
         }
         if(numInRow == 10)
         {
            for(int x = r; x > 5 ; x--)
               for(int y = 0; y < matrix[0].length; y++) {
                  
                  try{
                     matrix[x][y] = matrix[x - 1][y];
                     board[x][y].setBackground(board[x-1][y].getBackground());  
                     
                  }
                  catch(ArrayIndexOutOfBoundsException e)
                  {
                  //we don't really know why we need this but we do
                  }

               }  
            totalScore += 100; 
            scoreLabel2.setText("" + totalScore);    
         }
         numInRow = 0;      
      }

   }

}