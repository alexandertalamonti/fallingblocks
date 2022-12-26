/*****************************************************************
* TetrisPanel is the panel that acts as the main menu of the game.
* It contains buttons that lead to the other panels such as the options
* panel and the instructions panel. 
* @author Aadith Charugundla, Alexander Talamonti, Julian Gavino
******************************************************************/

//imports 
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;
import java.net.URL;
import javax.sound.sampled.*;
import java.io.*; 
import java.util.*; 

public class TetrisPanel extends JPanel {

   //create ImageIcons for the background and the titles at different themes
   private static final ImageIcon background = new ImageIcon("Tetris Background.png"); 
   private static final ImageIcon titleDefault = new ImageIcon("Title.png");
   private static final ImageIcon titleCool = new ImageIcon("Title-Cool.png");
   private static final ImageIcon titleWarm = new ImageIcon("Title-Warm.png");
   private static final ImageIcon titleTransparent = new ImageIcon("Title-Transparent.png");

   private BufferedImage myImage; //create a new BufferedImage
   private JPanel mainMenu; //create mainMenu JPanel
   public Clip clip; //create a Clip
   
   
   /*****
   * TetrisPanel is the main menu 
   * @param myFrame the current frame
   *****/
   public TetrisPanel(JFrame myFrame) { //passes the frame that it is in
      //create a new Scanner object and make it scan the file savadata.txt. If there isn't a file, it will prompt the user to make a file.
      Scanner infile = null;
      try{
         infile = new Scanner(new File("savedata.txt"));
      }
      catch(FileNotFoundException e){
         JOptionPane.showMessageDialog(null,"It looks like we couldn't access your saved preferences. Let's create a new save file.");
         String firstTheme = JOptionPane.showInputDialog("What Theme?\nDefault Theme\nWarm Theme\nCool Theme\nTransparent Theme\n");
         while(!firstTheme.equalsIgnoreCase("Default Theme") && !firstTheme.equalsIgnoreCase("Cool Theme") && !firstTheme.equalsIgnoreCase("Warm Theme") &&
            !firstTheme.equalsIgnoreCase("Transparent")&&!firstTheme.equalsIgnoreCase("Default") && !firstTheme.equalsIgnoreCase("Cool") && !firstTheme.equalsIgnoreCase("Warm") && 
            !firstTheme.equalsIgnoreCase("Transparent")) {

            firstTheme = JOptionPane.showInputDialog("That's not a valid choice!\nWhat Theme?\nDefault Theme\nWarm Theme\nCool Theme\nTransparent Theme\n");
         }
         JOptionPane.showMessageDialog(null, "We are setting your default volume to 50 (recommended). You can change this later in the options page.");
         int firstVolume = 50;
      
         File firstSave = new File("savedata.txt");
         PrintStream newSaveFile = null;
         try
         {
            newSaveFile = new PrintStream("savedata.txt");
            newSaveFile.println(firstTheme);
            newSaveFile.println(firstVolume);
            infile = new Scanner(firstSave);  
         }
         catch (FileNotFoundException f)
         {
            JOptionPane.showMessageDialog(null, "Something has gone wrong. Please restart the program and try again!");
         }
               
      }
      
      String saveTheme = infile.nextLine();  //finds which theme the user had last
      int currentVolume = Integer.parseInt(infile.nextLine()); //finds the volume that the user had last
   
      infile.close(); //close the Scanner object
   
      float musicVolume = 0 - ((100 - currentVolume) * 4 / 5); //create a new float that converts the numbers 1 to 100 to decibel values
      
      //checks for TetrisTheme.wav and creates a clip out of it
      try {
         // Open an audio input stream
         
         URL url = this.getClass().getClassLoader().getResource("TetrisTheme.wav");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            
         // Get a sound clip resource
         clip = AudioSystem.getClip();
      
      
         // Open audio clip and load samples from the audio input stream
         clip.open(audioIn);
         FloatControl gainControl = 
            (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
         gainControl.setValue(musicVolume); 
         clip.start();
      
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } 
      catch (IOException f) {
         f.printStackTrace();
      } 
      catch (LineUnavailableException e) {
         e.printStackTrace();
      }
      
      //creates a buffer and draws images on it based on what the theme is
      final int N = 1200; //width & height of buffered-image
      myImage = new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB);
      Graphics buffer = myImage.getGraphics();
      buffer.drawImage(background.getImage(), 0, 0, 1200, 1200, null);

      setLayout(new BorderLayout()); //sets the layout of the panel

      //creates a new JPanel that will hold all the buttons to go to the other panels
      mainMenu = new JPanel(); 
      mainMenu.setLayout(new FlowLayout()); 
      mainMenu.setBackground(Color.black); 
      add(mainMenu, BorderLayout.SOUTH); 

      //adding five buttons: Play Game, How to Play, Options, Quit, Credits. The appearance of the buttons changes based on theme
      // for Mac, you need to add setOpaque(true) and setBorderPainted(false) for some reason...

      //play game
      JButton playGame = new JButton("Play Game"); 
      playGame.setFont(new Font("Monospaced", Font.BOLD, 40));
      playGame.setOpaque(true);
      playGame.setBorderPainted(false);

      //instructions
      JButton howToPlay = new JButton("How To Play"); 
      howToPlay.setFont(new Font("Monospaced", Font.BOLD, 40));
      howToPlay.setOpaque(true);
      howToPlay.setBorderPainted(false);

      //options
      JButton Options = new JButton("Options"); 
      Options.setFont(new Font("Monospaced", Font.BOLD, 40));
      Options.setOpaque(true);
      Options.setBorderPainted(false);

      //Quit
      JButton Quit = new JButton("Exit Game"); 
      Quit.setFont(new Font("Monospaced", Font.BOLD, 40));
      Quit.setOpaque(true);
      Quit.setBorderPainted(false);

      //Credits
      JButton credits = new JButton("Credits"); 
      credits.setFont(new Font("Monospaced", Font.BOLD, 40));
      credits.setOpaque(true);
      credits.setBorderPainted(false);

      if(saveTheme.equals("Default Theme"))
      {
         buffer.drawImage(titleDefault.getImage(), 75, 50, 1200, 700, null);

         playGame.setBackground(Color.RED.brighter()); 
         playGame.setForeground(Color.WHITE);
         howToPlay.setBackground(Color.ORANGE);
         howToPlay.setForeground(Color.WHITE);
         Options.setBackground(Color.GREEN);
         Options.setForeground(Color.WHITE);
         Quit.setBackground(Color.BLUE.brighter());
         Quit.setForeground(Color.WHITE);
         credits.setBackground(Color.MAGENTA);
         credits.setForeground(Color.WHITE);
      }
      else if(saveTheme.equals("Cool Theme"))
      {
         buffer.drawImage(titleCool.getImage(), 75, 50, 1200, 700, null);
         
         playGame.setBackground(Color.CYAN);
         playGame.setForeground(Color.WHITE);
         howToPlay.setBackground(Color.MAGENTA.darker());
         howToPlay.setForeground(Color.WHITE);
         Options.setBackground(Color.BLUE);
         Options.setForeground(Color.WHITE);
         Quit.setBackground(Color.GREEN.darker());
         Quit.setForeground(Color.WHITE);
         credits.setBackground(Color.BLUE.darker());
         credits.setForeground(Color.WHITE);
      }
      else if(saveTheme.equals("Warm Theme"))
      {
         buffer.drawImage(titleWarm.getImage(), 75, 50, 1200, 700, null);
         
         playGame.setBackground(Color.ORANGE);
         playGame.setForeground(Color.WHITE);
         howToPlay.setBackground(Color.YELLOW.darker());
         howToPlay.setForeground(Color.WHITE);
         Options.setBackground(Color.RED);
         Options.setForeground(Color.WHITE);
         Quit.setBackground(Color.PINK.darker());
         Quit.setForeground(Color.WHITE);
         credits.setBackground(Color.ORANGE.darker());
         credits.setForeground(Color.WHITE);
      }
      else if(saveTheme.equals("Transparent Theme"))
      {
         buffer.drawImage(titleTransparent.getImage(), 75, 50, 1200, 700, null);
         
         playGame.setBackground(Color.BLACK);
         playGame.setForeground(Color.WHITE);
         howToPlay.setBackground(Color.BLACK);
         howToPlay.setForeground(Color.WHITE);
         Options.setBackground(Color.BLACK);
         Options.setForeground(Color.WHITE);
         Quit.setBackground(Color.BLACK);
         Quit.setForeground(Color.WHITE);
         credits.setBackground(Color.BLACK);
         credits.setForeground(Color.WHITE);
      }
      playGame.addActionListener(new PlayListener(myFrame, saveTheme, musicVolume)); 
      mainMenu.add(playGame); 

      howToPlay.addActionListener(new HowToPlayListener(myFrame)); 
      mainMenu.add(howToPlay);

      Options.addActionListener(new OptionsListener(myFrame)); 
      mainMenu.add(Options);
      
      Quit.addActionListener(new QuitListener()); 
      mainMenu.add(Quit);

      credits.addActionListener(new CreditsListener(myFrame)); 
      mainMenu.add(credits);
   }

   //paints the buffered-image
   public void paintComponent(Graphics g) {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }
   
   //The different Listeners of the buttons that we had
   
   /****
   * PlayListener is a listener that brings users to the playPanel when the play button is clicked
   *****/
   private class PlayListener implements ActionListener
   {
      private JFrame theFrame;
      private String myTheme;
      private float musVolume; 
      public PlayListener(JFrame frame, String theme, float vol) {
         theFrame = frame;
         myTheme = theme;
         musVolume = vol; 
      }
   
      public void actionPerformed(ActionEvent e)
      {  
         clip.stop(); 
         PlayPanel game = new PlayPanel(theFrame, myTheme, musVolume);
         KeyAdapter myKey = game.getKey();
         //The frame got the keys and that was not passed onto the panel. In order for it to work he had to add a keyListener to both the frame and the play panel.
         theFrame.addKeyListener(myKey);
         theFrame.setFocusable(true); 
         theFrame.setContentPane(game);
         theFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
         theFrame.setVisible(true);
      }
   
   }
   
   /*****
   * HowToPlayListener is a listener that brings users to the instructionsPanel when the how to play button is clicked
   ******/
   private class HowToPlayListener implements ActionListener
   {
      private JFrame theFrame;
      public HowToPlayListener(JFrame frame) {
         theFrame = frame;
      }
      public void actionPerformed(ActionEvent e)
      {  
         clip.stop(); 
         theFrame.setContentPane(new InstructionsPanel(theFrame));
         theFrame.setVisible(true);              
      }
   }
   
   

   /*****
   * OptionsListener is a listener that brings users to the optionsPanel when the how to options button is clicked
   ******/
   private class OptionsListener implements ActionListener
   {
      private JFrame theFrame;
      public OptionsListener(JFrame frame) {
         theFrame = frame;
      }
      public void actionPerformed(ActionEvent e)
      {
         clip.stop();
         theFrame.setContentPane(new OptionsPanel(theFrame));
         theFrame.setVisible(true);   
      
      }
   }
   
   /*****
   * CreditsListener is a listener that brings users to the creditsPanel when the how to credits button is clicked
   ******/
   private class CreditsListener implements ActionListener
   {
      private JFrame theFrame;
      public CreditsListener(JFrame frame) {
         theFrame = frame;
      }
      public void actionPerformed(ActionEvent e)
      {   
         clip.stop();
         theFrame.setContentPane(new CreditsPanel(theFrame));
         theFrame.setVisible(true);              
      }
   }

   /*****
   * QuitListener is a listener that closes the program when the quit button is clicked
   ******/
   private class QuitListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         System.exit(0); 
      }
   } 

}
