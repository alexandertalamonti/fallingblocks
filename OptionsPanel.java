/*****************************************************************
*Options is the panel that contains all the changable features of
*the game such as the theme and the volume. 
*@authors Aadith Charugundla, Alexander Talamonti, Julian Gavino
******************************************************************/

//imports
import java.awt.*;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.swing.event.*; 
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.*;      
import java.util.*;    
import javax.swing.*;



public class OptionsPanel extends JPanel {

   JPanel previewPanel; //creating a JPanel called previewPanel
   JLabel imgLabel; //creating a JLabel called imgLabel
   
   private static final ImageIcon background = new ImageIcon("Tetris Background.png"); //making an ImageIcon for the background
   
   private String[] themes = {"Default", "Cool", "Warm", "Transparent"};
   private JRadioButton[] radioButtons = new JRadioButton[4];

   private String currentTheme; //creating a String called currentTheme
   private BufferedImage myImage; //creating a BufferedImage called myImage
   private Graphics buffer; //creating a Graphics object called buffer
   private JTextField volumeBox; //creating a JTextField called volumeBox
   private JSlider volumeSlider; //creating a JSlider called volumeSlider
   
   /*
   *OptionsPanel is the panel that holds all the changable options
   *@param myFrame the current Frame
   */
   public OptionsPanel(JFrame myFrame) { 
      
            
      //setting the background and the layout of the options panel
      setBackground(Color.black);  
      setLayout(new BorderLayout()); 
      
      //creating a buffer and drawing an image on it
      myImage = new BufferedImage(1200, 1200, BufferedImage.TYPE_INT_RGB);
      buffer = myImage.getGraphics();
      buffer.drawImage(background.getImage(), 0, 0, 1200, 1200, null);
      
      //THEME OPTIONS: 
      //panel for theme options            
      JPanel themePanel = new JPanel();
      themePanel.setLayout(new GridLayout(1, 2)); 
      themePanel.setOpaque(false); 
      add(themePanel, BorderLayout.NORTH);
      
      //panel for the theme preview
      previewPanel = new JPanel(); 
      previewPanel.setLayout(new FlowLayout()); 
      previewPanel.setBackground(Color.WHITE); 
      previewPanel.setOpaque(false);
      add(previewPanel, BorderLayout.CENTER); 
      
      ButtonGroup themeGroup = new ButtonGroup(); 
      //Defines 4 separate buttons for each possible theme and add them to a button group
      // to ensure only one can be activated at a time
      for (int i = 0; i < themes.length; i++) {
         radioButtons[i] = new JRadioButton(String.format("%s Theme", themes[i]));
         radioButtons[i].addItemListener(new ThemeListener(themes[i]));
         themePanel.add(radioButtons[i]);
         themeGroup.add(radioButtons[i]);
      }

      //creates a Scanner object to scan savedata.txt. If no file exists, it will prompt the user to make one. 
      
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
      
      String saveTheme = infile.nextLine(); 
      int currentVolume = Integer.parseInt(infile.nextLine()); 
      infile.close(); 
      
      //The Display pngs were created with help from piskellapp.com, an online image editor that lets users draw images pixel by pixel.
      //changes the image displayed based on the theme that was chosen.
      if(saveTheme.equals("Default"))
      {
         ImageIcon defaultIcon2 = new ImageIcon("defaultDisplay.png");
         defaultIcon2.getImage().flush();
         imgLabel = new JLabel(defaultIcon2);
         previewPanel.add(imgLabel);
         radioButtons[0].setSelected(true);
      }
      if(saveTheme.equals("Cool"))
      {
         ImageIcon defaultIcon2 = new ImageIcon("coolDisplay.png");
         defaultIcon2.getImage().flush();
         imgLabel = new JLabel(defaultIcon2);
         previewPanel.add(imgLabel);
         radioButtons[1].setSelected(true);
      }
      if(saveTheme.equals("Warm"))
      {
         ImageIcon defaultIcon2 = new ImageIcon("warmDisplay.png");
         defaultIcon2.getImage().flush();
         imgLabel = new JLabel(defaultIcon2);
         previewPanel.add(imgLabel);
         radioButtons[2].setSelected(true);
      }
      if(saveTheme.equals("Transparent"))
      {
         ImageIcon defaultIcon2 = new ImageIcon("transparentDisplay.png");
         defaultIcon2.getImage().flush();
         imgLabel = new JLabel(defaultIcon2);
         previewPanel.add(imgLabel);
         radioButtons[3].setSelected(true);
      }
   

      //VOLUME OPTIONS + SAVE & BACK BUTTON
      //panel where the volume options will be
      JPanel volumePanel = new JPanel();
      volumePanel.setLayout(new GridLayout(3, 1));
      volumePanel.setOpaque(false); 
      add(volumePanel, BorderLayout.SOUTH);
      
      JLabel volumeLabel = new JLabel("Volume Control:", SwingConstants.CENTER); 
      volumeLabel.setFont(new Font("Monospaced", Font.BOLD, 42)); 
      volumePanel.add(volumeLabel); 

      JPanel volumeControl = new JPanel(); 
      volumeControl.setLayout(new FlowLayout()); 
      volumeControl.setOpaque(false); 
      volumePanel.add(volumeControl); 
      //creates a slider of values 0-100 defaulting at 50
      volumeSlider = new JSlider(0, 100, currentVolume);
      volumeSlider.addChangeListener(new SliderListener());
      volumeControl.add(volumeSlider);
      
      //textfield showing the value of the slider defaults at 50
      volumeBox = new JTextField("" + currentVolume, 3);
      volumeBox.addActionListener(new BoxListener());
      volumeControl.add(volumeBox);
      
      //panel containing the back and save buttons
      JPanel buttonControl = new JPanel(); 
      buttonControl.setLayout(new FlowLayout());
      buttonControl.setOpaque(false);  
      volumePanel.add(buttonControl); 
      
      JButton saveButton = new JButton("Save");
      saveButton.setFont(new Font("Monospaced", Font.BOLD, 50)); 
      saveButton.addActionListener(new SaveListener()); 
      buttonControl.add(saveButton);
      
      JButton exitButton = new JButton("Back");
      exitButton.setFont(new Font("Monospaced", Font.BOLD, 50)); 
      exitButton.addActionListener(new BackListener(myFrame));
      buttonControl.add(exitButton);
   }
   
   //paints the buffered-image
   public void paintComponent(Graphics g) {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }


   // Listeners
   /**
   * ThemeListener checks the thme and changes the image/display based on the one that was chosen
   */ 
   private class ThemeListener implements ItemListener  {
      private String myTheme;
   
      public ThemeListener(String themeName) {
         myTheme = themeName;
      }
      
      public void itemStateChanged(ItemEvent e) {
         if(myTheme.equals("Warm")) {
            ImageIcon warmIcon = new ImageIcon("warmDisplay.png");
            warmIcon.getImage().flush();
            imgLabel.setIcon(warmIcon);
         }
         
         else if(myTheme.equals("Cool")) {
            ImageIcon coolIcon = new ImageIcon("coolDisplay.png");
            coolIcon.getImage().flush();
            imgLabel.setIcon(coolIcon); 
         }
         
         else if(myTheme.equals("Transparent")) {
            ImageIcon transparentIcon = new ImageIcon("transparentDisplay.png");
            transparentIcon.getImage().flush();
            imgLabel.setIcon(transparentIcon);
         }
         else{
            ImageIcon defaultIcon = new ImageIcon("defaultDisplay.png");
            defaultIcon.getImage().flush();
            imgLabel.setIcon(defaultIcon);
         }
         currentTheme = myTheme;
      }
   }
   
   /**
   * SliderListener changes the value of the slider based on the number in the box
   */
   public class SliderListener implements ChangeListener {
      public void stateChanged (ChangeEvent e) {
         volumeBox.setText("" + volumeSlider.getValue());
      }
   }
   
   /**
   * BoxListener changes the value of the box based on the slider value
   */
   public class BoxListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         volumeSlider.setValue(Integer.parseInt(volumeBox.getText()));
      }
   }
   
   /**
   * SaveListener writes the settings that the user had to savadata.txt
   */
   public class SaveListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
      
         PrintStream outfile = null;
         try{
            outfile = new PrintStream(new FileOutputStream("savedata.txt"));
         }
         catch(FileNotFoundException d)
         {
            JOptionPane.showMessageDialog(null,"The file could not be created.");
         }
      
         outfile.println(currentTheme); 
         outfile.println(volumeBox.getText()); 
         outfile.close();
         JOptionPane.showMessageDialog(null, "Your settings were successfully saved!");
      }
   }

   /**
   * BackListener is a listener that brings users back to the main menu when the back button is pressed
   */
   public class BackListener implements ActionListener {
      JFrame theFrame;
      public BackListener(JFrame frame) {
         theFrame = frame;
      }
      public void actionPerformed(ActionEvent e) {
         theFrame.setContentPane(new TetrisPanel(theFrame));
         theFrame.setVisible(true);
      }
   }

}