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
   
   private String[] buttonLabelList = {"Play Game", "How To Play", "Options", "Exit Game", "Credits"};
   private String[] themes = {"Default", "Warm", "Cool", "Transparent"};
   private JButton[] buttons = new JButton[5];
   
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
         clip.loop(-1);
      
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

      // Create each game button
      for (int i = 0; i < buttonLabelList.length; i++) {
         buttons[i] = new JButton(buttonLabelList[i]);
         buttons[i].setFont(new Font("Monospaced", Font.BOLD, 40));
         buttons[i].setOpaque(true);
         buttons[i].setBorderPainted(false);
      }

      if(saveTheme.equals("Default Theme"))
      {
         buffer.drawImage(titleDefault.getImage(), 75, 50, 1200, 700, null);

         buttons[0].setBackground(Color.RED.brighter()); 
         buttons[1].setBackground(Color.ORANGE);
         buttons[2].setBackground(Color.GREEN);
         buttons[3].setBackground(Color.BLUE.brighter());
         buttons[4].setBackground(Color.MAGENTA);
      }
      else if(saveTheme.equals("Cool Theme"))
      {
         buffer.drawImage(titleCool.getImage(), 75, 50, 1200, 700, null);
         
         buttons[0].setBackground(Color.CYAN);
         buttons[1].setBackground(Color.MAGENTA.darker());
         buttons[2].setBackground(Color.BLUE);
         buttons[3].setBackground(Color.GREEN.darker());
         buttons[4].setBackground(Color.BLUE.darker());
      }
      else if(saveTheme.equals("Warm Theme"))
      {
         buffer.drawImage(titleWarm.getImage(), 75, 50, 1200, 700, null);
         
         buttons[0].setBackground(Color.ORANGE);
         buttons[1].setBackground(Color.YELLOW.darker());
         buttons[2].setBackground(Color.RED);
         buttons[3].setBackground(Color.PINK.darker());
         buttons[4].setBackground(Color.ORANGE.darker());
      }
      else if(saveTheme.equals("Transparent Theme"))
      {
         buffer.drawImage(titleTransparent.getImage(), 75, 50, 1200, 700, null);
         
         buttons[0].setBackground(Color.BLACK);
         buttons[1].setBackground(Color.BLACK);
         buttons[2].setBackground(Color.BLACK);
         buttons[3].setBackground(Color.BLACK);
         buttons[4].setBackground(Color.BLACK);
      }
      
      buttons[0].setForeground(Color.WHITE);
      buttons[1].setForeground(Color.WHITE);
      buttons[2].setForeground(Color.WHITE);
      buttons[3].setForeground(Color.WHITE);
      buttons[4].setForeground(Color.WHITE);

      
      buttons[0].addActionListener(new PlayListener(myFrame, saveTheme, musicVolume)); 
      mainMenu.add(buttons[0]); 

      buttons[1].addActionListener(new HowToPlayListener(myFrame)); 
      mainMenu.add(buttons[1]);

      buttons[2].addActionListener(new OptionsListener(myFrame)); 
      mainMenu.add(buttons[2]);
      
      buttons[3].addActionListener(new QuitListener()); 
      mainMenu.add(buttons[3]);

      buttons[4].addActionListener(new CreditsListener(myFrame)); 
      mainMenu.add(buttons[4]);
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
