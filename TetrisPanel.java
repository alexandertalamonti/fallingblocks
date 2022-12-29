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
import javax.sound.sampled.*;
import java.io.*; 
import java.util.*; 

public class TetrisPanel extends JPanel {


   private BufferedImage myImage; //create a new BufferedImage
   private JPanel mainMenu; //create mainMenu JPanel
   public Clip music; //create a Clip
   
   private static final ImageIcon background = new ImageIcon("Tetris Background.png"); 

   // Initialize map to hold ImageIcons for each title
   public Map<String, String> titles = Map.of(
      "Default", "Title.png",
      "Cool", "Title-Cool.png",
      "Warm", "Title-Warm.png",
      "Transparent", "Title-Transparent.png"
   );

   private String[] buttonLabelList = {"Play Game", "How To Play", "Options", "Exit Game", "Credits"};
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
      
         File firstSave = new File("savedata.txt");
         PrintStream newSaveFile = null;
         try
         {
            newSaveFile = new PrintStream("savedata.txt");
            newSaveFile.println("Default");
            newSaveFile.println(50);
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
      if (music == null || !music.isRunning()) {
         try {
            // Open an audio input stream
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("TetrisTheme.wav"));
            // Get a sound clip resource
            music = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream
            music.open(audioIn);
            FloatControl gainControl = 
               (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(musicVolume); 
            music.loop(-1);
         
         } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
         } 
         catch (IOException f) {
            f.printStackTrace();
         } 
         catch (LineUnavailableException e) {
            e.printStackTrace();
         }   
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

      buffer.drawImage(new ImageIcon(titles.get(saveTheme)).getImage(), 75, 50, 1200, 700, null);

      if(saveTheme.equals("Default"))
      {
         buttons[0].setBackground(Color.RED.brighter()); 
         buttons[1].setBackground(Color.ORANGE);
         buttons[2].setBackground(Color.GREEN);
         buttons[3].setBackground(Color.BLUE.brighter());
         buttons[4].setBackground(Color.MAGENTA);
      }
      else if(saveTheme.equals("Cool"))
      {         
         buttons[0].setBackground(Color.CYAN);
         buttons[1].setBackground(Color.MAGENTA.darker());
         buttons[2].setBackground(Color.BLUE);
         buttons[3].setBackground(Color.GREEN.darker());
         buttons[4].setBackground(Color.BLUE.darker());
      }
      else if(saveTheme.equals("Warm"))
      {         
         buttons[0].setBackground(Color.ORANGE);
         buttons[1].setBackground(Color.YELLOW.darker());
         buttons[2].setBackground(Color.RED);
         buttons[3].setBackground(Color.PINK.darker());
         buttons[4].setBackground(Color.ORANGE.darker());
      }
      else if(saveTheme.equals("Transparent"))
      {         
         buttons[0].setBackground(Color.BLACK);
         buttons[1].setBackground(Color.BLACK);
         buttons[2].setBackground(Color.BLACK);
         buttons[3].setBackground(Color.BLACK);
         buttons[4].setBackground(Color.BLACK);
      }
      
      buttons[0].addActionListener(new PlayListener(myFrame, saveTheme)); 
      buttons[1].addActionListener(new SwitchListener(myFrame, new InstructionsPanel(myFrame))); 
      buttons[2].addActionListener(new SwitchListener(myFrame, new OptionsPanel(myFrame))); 
      buttons[3].addActionListener(new QuitListener()); 
      buttons[4].addActionListener(new SwitchListener(myFrame, new CreditsPanel(myFrame))); 

      for (JButton button : buttons) {
         button.setForeground(Color.WHITE);
         mainMenu.add(button);
      }
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
      public PlayListener(JFrame frame, String theme) {
         theFrame = frame;
         myTheme = theme;
      }
   
      public void actionPerformed(ActionEvent e)
      {  
         PlayPanel game = new PlayPanel(theFrame, myTheme);
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
   * SwitchListener is a listener that brings users to the other Panels when a button is clicked
   ******/
   private class SwitchListener implements ActionListener
   {
      private JFrame theFrame;
      private JPanel thePanel;
      public SwitchListener(JFrame frame, JPanel panel) {
         theFrame = frame;
         thePanel = panel;
      }
      public void actionPerformed(ActionEvent e)
      {  
         theFrame.setContentPane(thePanel);
         theFrame.setVisible(true);              
      }
   }

   /*****
   * QuitListener is a listener that closes the program when the quit button is clicked
   ******/
   private class QuitListener implements ActionListener {
      public void actionPerformed(ActionEvent e) { System.exit(0); }
   } 
}