package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


//import graphics.ImagePanel;

public class YahtzeeFrame extends JFrame {
	
	private JPanel gamePanel, playerPanel, upperPanel, lowerPanel, dicePanel;
	
	private ImagePanel imagePanel1, imagePanel2, imagePanel3, imagePanel4, imagePanel5;

	private JTextField playerInput;
	private JLabel turnLabel;
	private JLabel rollLabel;
	
	private JButton acesButton, twosButton, threesButton, foursButton, fivesButton, sixesButton;
	private JButton threeOfKindButton, fourOfKindButton, fullHouseButton, smallStraightButton, largeStraightButton, yahtzeeButton, chanceButton;
	private JCheckBox keep1, keep2, keep3, keep4, keep5;
	private JButton rollButton;
	
	private JTextField acesField, twosField, threesField, foursField, fivesField, sixesField;
	private JTextField threeKindField, fourKindField, fullHouseField, smallStraightField, largeStraightField, yahtzeeField, chanceField;
	private JTextField subtotalField, bonusField, upperSectionTotalField;
	private JTextField yahtzeeBonusField, lowerSectionTotalField, grandTotalField;
	
	int die1, die2, die3, die4, die5;
	int dice[];
	Image imgDie1, imgDie2, imgDie3, imgDie4, imgDie5;
	
	boolean aces, twos, threes, fours, fives, sixes, threeKind, fourKind, fullHouse, smallStraight, largeStraight, yahtzee, chance;

	int turnCount, rollCount;
	int yBonus;
	int yBonusCount;
	
	boolean yCheck;
	 
	// Host name or IP  
	String host = "localhost";
	
	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HEIGHT = 800;
	private static final double FACTOR = 0.5;
	
	
	public YahtzeeFrame() {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);  
		menuBar.add(createFileMenu());
		
		playGame();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
	}
	
	public void playGame() {

		aces = false;
		twos = false;
		threes = false;
		fours = false; 
		fives = false; 
		sixes = false; 
		threeKind = false; 
		fourKind = false; 
		fullHouse = false; 
		smallStraight = false; 
		largeStraight = false; 
		yahtzee = false; 
		chance = false;
		
		yCheck = false;
		yBonus = 0;
		yBonusCount = 0;
		turnCount = 1;
		turnLabel = new JLabel("Turn: 1");
		rollLabel = new JLabel("Rolls Left: 3");
		
		imgDie1 = new ImageIcon("blankDie.png").getImage();
		imgDie2 = new ImageIcon("blankDie.png").getImage();
		imgDie3 = new ImageIcon("blankDie.png").getImage();
		imgDie4 = new ImageIcon("blankDie.png").getImage();
		imgDie5 = new ImageIcon("blankDie.png").getImage();

		createGamePanel();
		
		playTurn(turnCount);
	}
	
	public void playTurn(int turn) {
		yahtzeeBonusField.setText("" + (yBonus * yBonusCount));
		
		if (turn < 14) {
			rollCount = 3;
			
			imagePanel1.setImage("blankDie.png");
	  	   	imagePanel1.scaleImage(FACTOR);
	  	   	imagePanel2.setImage("blankDie.png");
		   	imagePanel2.scaleImage(FACTOR);
		   	imagePanel3.setImage("blankDie.png");
	  	   	imagePanel3.scaleImage(FACTOR);
	  	   	imagePanel4.setImage("blankDie.png");
		   	imagePanel4.scaleImage(FACTOR);
		   	imagePanel5.setImage("blankDie.png");
	  	   	imagePanel5.scaleImage(FACTOR);
	  	   	
	  	   	keep1.setSelected(false);
	  	   	keep2.setSelected(false);
	  	   	keep3.setSelected(false);
	  	   	keep4.setSelected(false);
	  	   	keep5.setSelected(false);
			
			turnLabel.setText("Round: " + turn + " of 13");
			rollLabel.setText("Rolls Left: 3");
		} else {
			endGame();
		}
	}
	
	public void endGame() {
		imagePanel1.setImage("endDie.png");
  	   	imagePanel1.scaleImage(FACTOR);
  	   	imagePanel2.setImage("endDie.png");
	   	imagePanel2.scaleImage(FACTOR);
	   	imagePanel3.setImage("endDie.png");
  	   	imagePanel3.scaleImage(FACTOR);
  	   	imagePanel4.setImage("endDie.png");
	   	imagePanel4.scaleImage(FACTOR);
	   	imagePanel5.setImage("endDie.png");
  	   	imagePanel5.scaleImage(FACTOR);
  	  
  	   	keep1.setSelected(false);
	   	keep2.setSelected(false);
	   	keep3.setSelected(false);
	   	keep4.setSelected(false);
	   	keep5.setSelected(false);
  	  
  	   	turnLabel.setText("Turn: Game Over");
  	   	rollLabel.setText("Roll: Game Over");
  	   	
  	   	int subtotal = Integer.parseInt(acesField.getText()) + Integer.parseInt(twosField.getText()) + 
  	   			Integer.parseInt(threesField.getText()) + Integer.parseInt(foursField.getText()) + 
  	   			Integer.parseInt(fivesField.getText()) + Integer.parseInt(sixesField.getText());
  	   	subtotalField.setText("" + subtotal);
  	   	
  	   	int upperBonus = 0;
  	   	if (subtotal >= 63) {
  	   		upperBonus = 35;
  	   	}
  	   	bonusField.setText("" + upperBonus);
  	   	
  	   	int upperSectionTotal = subtotal + upperBonus;
  	   	upperSectionTotalField.setText("" + upperSectionTotal);

  	   	int lowerSectionTotal = Integer.parseInt(threeKindField.getText()) + Integer.parseInt(fourKindField.getText()) + 
  	   			Integer.parseInt(fullHouseField.getText()) + Integer.parseInt(smallStraightField.getText()) + 
  	   			Integer.parseInt(largeStraightField.getText()) + Integer.parseInt(yahtzeeField.getText()) + 
  	   			Integer.parseInt(chanceField.getText());
  	  
  	   	System.out.println("Lower subtotal: " + lowerSectionTotal);
  	   	int yahtzeeBonus = Integer.parseInt(yahtzeeBonusField.getText());
  	   	System.out.println("Yahtzee Bonus: " + yahtzeeBonus);
  	   	lowerSectionTotal += yahtzeeBonus;
  	   	System.out.println("Lower total: " + lowerSectionTotal);

  	   	lowerSectionTotalField.setText("" + lowerSectionTotal);
  	   	
  	   	int grandTotal = upperSectionTotal + lowerSectionTotal;
  	   	grandTotalField.setText("" + grandTotal);
	}
	
	// method returns 0 if not yahtzee and value on dice if yahtzee
	public int checkYahtzee(int[] dice) {
		boolean foundFive = false;
		int yDie = 0;
		int dieCounts[] = new int[] {0,0,0,0,0,0};
		
		for (int i=0; i<dice.length; i++) {
			if (dice[i] == 1) {dieCounts[0] += 1;}
			else if (dice[i] == 2) {dieCounts[1] += 1;}
			else if (dice[i] == 3) {dieCounts[2] += 1;}
			else if (dice[i] == 4) {dieCounts[3] += 1;}
			else if (dice[i] == 5) {dieCounts[4] += 1;}
			else if (dice[i] == 6) {dieCounts[5] += 1;}
		}
		for (int i=0; i<dieCounts.length; i++) {
			if (dieCounts[i] > 4) {
				foundFive = true;
				yDie = i+1;
			}
		}
		if (foundFive) {

			if (yDie == 0) {yCheck = true;}
			else if (yDie == 1) {yCheck = aces;}
			else if (yDie == 2) {yCheck = twos;}
			else if (yDie == 3) {yCheck = threes;}
			else if (yDie == 4) {yCheck = fours;}
			else if (yDie == 5) {yCheck = fives;}
			else if (yDie == 6) {yCheck = sixes;}	
		} 
		
		return yDie;
	}

	
	private JMenu createFileMenu() {
	      JMenu menu = new JMenu("Game");
	      menu.add(createLoadGameItem());
	      menu.add(createSaveGameItem());
	      menu.add(createExitItem());
	      return menu;
	   }

	private JMenuItem createExitItem() {
	      JMenuItem item = new JMenuItem("Exit");      
	      class MenuItemListener implements ActionListener
	      {
	         public void actionPerformed(ActionEvent event)
	         {
	            System.exit(0);
	         }
	      }      
	      ActionListener listener = new MenuItemListener();
	      item.addActionListener(listener);
	      return item;
	   } 
	   
	
	private JMenuItem createLoadGameItem() {
		   JMenuItem item = new JMenuItem("Load Game");       
		   class MenuItemListener implements ActionListener   
		   {
			   public void actionPerformed(ActionEvent event) 
			   { 
				 //
			   }
		   }      
		   ActionListener listener = new MenuItemListener();
		   item.addActionListener(listener);
		   return item;
	   }
	   
	   
	private JMenuItem createSaveGameItem() {
		   JMenuItem item = new JMenuItem("Save Game");       
		   class MenuItemListener implements ActionListener   
		   {
			   public void actionPerformed(ActionEvent event) 
			   { 
				   // enforce using a player name
				   String player = playerInput.getText().trim();
				   if (player.isEmpty()) {
	    			   JOptionPane.showMessageDialog(gamePanel, "Player Name Cannot Be Empty");

				   } else {
					   try {
					        // Establish connection with the server
					        Socket socket = new Socket(host, 8000);

					        // Create an output stream to the server
					        ObjectOutputStream toServer =
					          new ObjectOutputStream(socket.getOutputStream());

					        // Setup
					        JTextField[] textFields = new JTextField[] {acesField, twosField, threesField, foursField, fivesField, sixesField, 
					    			threeKindField, fourKindField, fullHouseField, smallStraightField, largeStraightField, 
					    			yahtzeeField, chanceField};
					     
					        // Get Values
					        String gsPlayerInput = playerInput.getText().trim(); 
					    	int[] gsDice = new int[] {die1,die2,die3,die4,die5};
					    	boolean[] gsCategories = new boolean[] {aces, twos, threes, fours, fives, sixes, 
					    			threeKind, fourKind, fullHouse, smallStraight, largeStraight, yahtzee, chance};
					    	int[] gsScores = new int[gsCategories.length] ;
					    	
					    	//Date date = new Date();
					    	
					    	for(int i=0; i<gsScores.length; i++){
					    		if (gsCategories[i] == true) {
					    			JTextField currField = textFields[i];
					    			gsScores[i] = Integer.parseInt(currField.getText().trim());
					    		} else {
					    			gsScores[i] = -1;
					    		}
					    	}

					        // Create Game State Object => send to server
					        GameState currGame = new GameState(gsPlayerInput, gsDice, gsCategories, gsScores, 
					    			turnCount, rollCount, yBonus, yBonusCount, yCheck);
					        toServer.writeObject(currGame);
					      }
					      catch (IOException ex) {
					        ex.printStackTrace();
					      } 
				   }
			   	}	   
		   }
				         
		   ActionListener listener = new MenuItemListener();
		   item.addActionListener(listener);
		   return item;
	   }

	
	private void createGamePanel() {
		gamePanel = new JPanel(new BorderLayout());
		
		JPanel upperSection = createUpperSection();
		JPanel lowerSection = createLowerSection();
		
		JPanel sectionsPanel = new JPanel(new GridLayout(2,1));
		sectionsPanel.add(upperSection);
		sectionsPanel.add(lowerSection);
		
		dicePanel = createDicePanel();
		
		playerPanel = new JPanel();
		JLabel playerLabel = new JLabel("Player Name: ");
		playerPanel.add(playerLabel);
		playerInput = new JTextField(20);
		playerPanel.add(playerInput);

		gamePanel.add(playerPanel, BorderLayout.NORTH);
		gamePanel.add(sectionsPanel, BorderLayout.CENTER);
		gamePanel.add(dicePanel, BorderLayout.EAST);
		
		acesButton.addActionListener(new AddAcesListener());
		twosButton.addActionListener(new AddTwosListener());
		threesButton.addActionListener(new AddThreesListener());
		foursButton.addActionListener(new AddFoursListener());
		fivesButton.addActionListener(new AddFivesListener());
		sixesButton.addActionListener(new AddSixesListener());
		threeOfKindButton.addActionListener(new AddThreeKindListener());
		fourOfKindButton.addActionListener(new AddFourKindListener());
		fullHouseButton.addActionListener(new AddFullHouseListener());
		smallStraightButton.addActionListener(new AddSmallStraightListener());
		largeStraightButton.addActionListener(new AddLargeStraightListener());
		yahtzeeButton.addActionListener(new AddYahtzeeListener());
		chanceButton.addActionListener(new AddChanceListener());		
		rollButton.addActionListener(new AddRollListener());

		add(gamePanel);
	}
	
	private JPanel createUpperSection() {
		upperPanel = new JPanel(new GridLayout(9,2));
		upperPanel.setBorder(new TitledBorder(new EtchedBorder(), "Upper Section"));

		
		//Aces
		acesButton = new JButton("Aces");
		JPanel acesButtonPanel = new JPanel();
		acesButtonPanel.add(acesButton);
		acesField = new JTextField(10);
		
		//Twos
		twosButton = new JButton("Twos");
		JPanel twosButtonPanel = new JPanel();
		twosButtonPanel.add(twosButton);
		twosField = new JTextField(10);
		
		//Threes
		threesButton = new JButton("Threes");
		JPanel threesButtonPanel = new JPanel();
		threesButtonPanel.add(threesButton);
		threesField = new JTextField(10);
		
		//Fours
		foursButton = new JButton("Fours");
		JPanel foursButtonPanel = new JPanel();
		foursButtonPanel.add(foursButton);
		foursField = new JTextField(10);
		
		//Fives
		fivesButton = new JButton("Fives");
		JPanel fivesButtonPanel = new JPanel();
		fivesButtonPanel.add(fivesButton);
		fivesField = new JTextField(10);
		
		//Sixes
		sixesButton = new JButton("Sixes");
		JPanel sixesButtonPanel = new JPanel();
		sixesButtonPanel.add(sixesButton);
		sixesField = new JTextField(10);
		
		//Score Subtotal
		JLabel subtotalLabel = new JLabel("Score Subtotal:");
		subtotalField = new JTextField(10);
		
		//Bonus
		JLabel bonusLabel = new JLabel("Bonus:");
		bonusField = new JTextField(10);
		
		
		//Upper Section Total
		JLabel upperSectionTotalLabel = new JLabel("Upper Section Total:");
		upperSectionTotalField = new JTextField(10);
		
		
		upperPanel.add(acesButtonPanel);
		upperPanel.add(acesField);
		upperPanel.add(twosButtonPanel);
		upperPanel.add(twosField);
		upperPanel.add(threesButtonPanel);
		upperPanel.add(threesField);
		upperPanel.add(foursButtonPanel);
		upperPanel.add(foursField);
		upperPanel.add(fivesButtonPanel);
		upperPanel.add(fivesField);
		upperPanel.add(sixesButtonPanel);
		upperPanel.add(sixesField);
		upperPanel.add(subtotalLabel);
		upperPanel.add(subtotalField);
		upperPanel.add(bonusLabel);
		upperPanel.add(bonusField);
		upperPanel.add(upperSectionTotalLabel);
		upperPanel.add(upperSectionTotalField);
		
		return upperPanel;
	}
	
	private JPanel createLowerSection() {
		lowerPanel = new JPanel(new GridLayout(10,1));
		lowerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Lower Section"));
		
		//Three of a Kind
		threeOfKindButton = new JButton("3 of a Kind");
		JPanel threeOfKindButtonPanel = new JPanel();
		threeOfKindButtonPanel.add(threeOfKindButton);
		threeKindField = new JTextField(10);
		
		//Four of a Kind
		fourOfKindButton = new JButton("4 of a Kind");
		JPanel fourOfKindButtonPanel = new JPanel();
		fourOfKindButtonPanel.add(fourOfKindButton);
		fourKindField = new JTextField(10);
		
		//Full House
		fullHouseButton = new JButton("Full House");
		JPanel fullHouseButtonPanel = new JPanel();
		fullHouseButtonPanel.add(fullHouseButton);
		fullHouseField = new JTextField(10);
		
		//Small Straight
		smallStraightButton = new JButton("Small Straight");
		JPanel smallStraightButtonPanel = new JPanel();
		smallStraightButtonPanel.add(smallStraightButton);
		smallStraightField = new JTextField(10);
		
		//Large Straight
		largeStraightButton = new JButton("Large Straight");
		JPanel largeStraightButtonPanel = new JPanel();
		largeStraightButtonPanel.add(largeStraightButton);
		largeStraightField = new JTextField(10);
		
		//Yahtzee
		yahtzeeButton = new JButton("Yahtzee");
		JPanel yahtzeeButtonPanel = new JPanel();
		yahtzeeButtonPanel.add(yahtzeeButton);
		yahtzeeField = new JTextField(10);
		
		//Chance
		chanceButton = new JButton("Chance");
		JPanel chanceButtonPanel = new JPanel();
		chanceButtonPanel.add(chanceButton);
		chanceField = new JTextField(10);
		
		//Yahtzee Bonus
		JLabel yahtzeeBonusLabel = new JLabel("Yahtzee Bonus:");
		yahtzeeBonusField = new JTextField(10);
		
		//Lower Section Total
		JLabel lowerSectionTotalLabel = new JLabel("Lower Section Total:");
		lowerSectionTotalField = new JTextField(10);
		
		//Grand Total
		JLabel grandTotalLabel = new JLabel("Grand Total:");
		grandTotalField = new JTextField(10);
		Font testFont = new Font(grandTotalLabel.getFont().getName(),Font.BOLD,grandTotalLabel.getFont().getSize());
		grandTotalLabel.setFont(testFont);
		grandTotalField.setFont(testFont);
	
		
		lowerPanel.add(threeOfKindButtonPanel);
		lowerPanel.add(threeKindField);
		lowerPanel.add(fourOfKindButtonPanel);
		lowerPanel.add(fourKindField);
		lowerPanel.add(fullHouseButtonPanel);
		lowerPanel.add(fullHouseField);
		lowerPanel.add(smallStraightButtonPanel);
		lowerPanel.add(smallStraightField);
		lowerPanel.add(largeStraightButtonPanel);
		lowerPanel.add(largeStraightField);
		lowerPanel.add(yahtzeeButtonPanel);
		lowerPanel.add(yahtzeeField);
		lowerPanel.add(chanceButtonPanel);
		lowerPanel.add(chanceField);
		lowerPanel.add(yahtzeeBonusLabel);
		lowerPanel.add(yahtzeeBonusField);
		lowerPanel.add(lowerSectionTotalLabel);
		lowerPanel.add(lowerSectionTotalField);
		lowerPanel.add(grandTotalLabel);
		lowerPanel.add(grandTotalField);
		
		
		return lowerPanel;
	}
	
	private JPanel createDicePanel() {
		dicePanel = new JPanel(new GridLayout(6,1));
		
		// 1st die
		imagePanel1 = new ImagePanel(imgDie1);
		imagePanel1.scaleImage(FACTOR);
		keep1 = new JCheckBox("Keep 1st Die");
		JPanel checkPanel1 = new JPanel(new BorderLayout());
		checkPanel1.add(imagePanel1, BorderLayout.CENTER);
		checkPanel1.add(keep1, BorderLayout.SOUTH);
		
		// 2nd die
		imagePanel2 = new ImagePanel(imgDie2);
		imagePanel2.scaleImage(FACTOR);
		keep2 = new JCheckBox("Keep 2nd Die");
		JPanel checkPanel2 = new JPanel(new BorderLayout());
		checkPanel2.add(imagePanel2, BorderLayout.CENTER);
		checkPanel2.add(keep2, BorderLayout.SOUTH);
		
		//3rd die
		imagePanel3 = new ImagePanel(imgDie3);
		imagePanel3.scaleImage(FACTOR);
		keep3 = new JCheckBox("Keep 3rd Die");
		JPanel checkPanel3 = new JPanel(new BorderLayout());
		checkPanel3.add(imagePanel3, BorderLayout.CENTER);
		checkPanel3.add(keep3, BorderLayout.SOUTH);
		
		// 4th die
		imagePanel4 = new ImagePanel(imgDie4);
		imagePanel4.scaleImage(FACTOR);
		keep4 = new JCheckBox("Keep 4th Die");
		JPanel checkPanel4 = new JPanel(new BorderLayout());
		checkPanel4.add(imagePanel4, BorderLayout.CENTER);
		checkPanel4.add(keep4, BorderLayout.SOUTH);
		
		// 5th die
		imagePanel5 = new ImagePanel(imgDie5);
		imagePanel5.scaleImage(FACTOR);
		keep5 = new JCheckBox("Keep 5th Die");
		JPanel checkPanel5 = new JPanel(new BorderLayout());
		checkPanel5.add(imagePanel5, BorderLayout.CENTER);
		checkPanel5.add(keep5, BorderLayout.SOUTH);
		
		// Roll Button
		rollButton = new JButton("Roll");
		JPanel rollButtonPanel = new JPanel();
		rollButtonPanel.add(rollButton);
		
		JPanel countPanel = new JPanel(new GridLayout(3,1));
		countPanel.add(turnLabel);
		countPanel.add(rollLabel);
		countPanel.add(rollButtonPanel);
		
		dicePanel.add(checkPanel1);
		dicePanel.add(checkPanel2);
		dicePanel.add(checkPanel3);
		dicePanel.add(checkPanel4);
		dicePanel.add(checkPanel5);
		dicePanel.add(countPanel);

		
		return dicePanel;
	}
	

	class AddRollListener implements ActionListener
    {
       public void actionPerformed(ActionEvent event)
       {
    	   if (rollCount > 0) {
    		   if (rollCount >= 3 &&
    				   (keep1.isSelected() || keep2.isSelected() || keep3.isSelected() || keep4.isSelected() || keep5.isSelected())) 
    		   { 
    			   JOptionPane.showMessageDialog(dicePanel, "Roll All Dice on First Roll \n (do not keep any dice)");
    			   keep1.setSelected(false);
    			   keep2.setSelected(false);
    		  	   keep3.setSelected(false);
    		  	   keep4.setSelected(false);
    		  	   keep5.setSelected(false);
    		   } else if (keep1.isSelected() && keep2.isSelected() && keep3.isSelected() && keep4.isSelected() && keep5.isSelected()) {
    			   JOptionPane.showMessageDialog(dicePanel, "Must Roll At Least One Die \n"
    			   		+ "Or Select Any Available Score Category \n \n (all keep die boxes are currently selected)");
    		   } else {
    			   if (!keep1.isSelected()) {
            		   Random r1 = new Random(); 
            		   die1 = r1.nextInt((6-1) +1) + 1;
            		   imgDie1 = new ImageIcon("die" + die1 + ".png").getImage();
            		   imagePanel1.setImage(imgDie1);
                 	   imagePanel1.scaleImage(FACTOR);   
            	   }
            	   if (!keep2.isSelected()) {
            		   Random r2 = new Random(); 
                 	   die2 = r2.nextInt((6-1) +1) + 1;
                 	   imgDie2 = new ImageIcon("die" + die2 + ".png").getImage();
                 	   imagePanel2.setImage(imgDie2);
                	   imagePanel2.scaleImage(FACTOR);   
            	   }
            	   if (!keep3.isSelected()) {
            		   Random r3 = new Random(); 
                 	   die3 = r3.nextInt((6-1) +1) + 1;
                 	   imgDie3 = new ImageIcon("die" + die3 + ".png").getImage();
                 	   imagePanel3.setImage(imgDie3);
                	   imagePanel3.scaleImage(FACTOR);   
            	   }
            	   if (!keep4.isSelected()) {
            		   Random r4 = new Random(); 
                 	   die4 = r4.nextInt((6-1) +1) + 1;
                 	   imgDie4 = new ImageIcon("die" + die4 + ".png").getImage();
                 	   imagePanel4.setImage(imgDie4);
                	   imagePanel4.scaleImage(FACTOR);  
            	   }
            	   if (!keep5.isSelected()) {
            		   Random r5 = new Random(); 
                 	   die5 = r5.nextInt((6-1) +1) + 1;
                 	   imgDie5 = new ImageIcon("die" + die5 + ".png").getImage();
                 	   imagePanel5.setImage(imgDie5);
                	   imagePanel5.scaleImage(FACTOR); 
            	   }
            	   
            	   
//            	   // For Testing Special Yahtzee Rules
//            	   // Produces a Yahtzee on every roll using a random die value
//            	   Random r6 = new Random(); 
//             	   die1 = r6.nextInt((6-1) +1) + 1;
//             	   die2 = die1;
//             	   die3 = die1;
//             	   die4 = die1;
//             	   die5 = die1;
//             	   imgDie1 = new ImageIcon("die" + die1 + ".png").getImage();
//            	   imagePanel1.setImage(imgDie1);
//            	   imagePanel1.scaleImage(FACTOR);
//            	   imgDie2 = new ImageIcon("die" + die2 + ".png").getImage();
//            	   imagePanel2.setImage(imgDie2);
//            	   imagePanel2.scaleImage(FACTOR);
//            	   imgDie3 = new ImageIcon("die" + die3 + ".png").getImage();
//            	   imagePanel3.setImage(imgDie3);
//            	   imagePanel3.scaleImage(FACTOR);
//            	   imgDie4 = new ImageIcon("die" + die4 + ".png").getImage();
//            	   imagePanel4.setImage(imgDie4);
//            	   imagePanel4.scaleImage(FACTOR);
//             	   imgDie5 = new ImageIcon("die" + die5 + ".png").getImage();
//             	   imagePanel5.setImage(imgDie5);
//            	   imagePanel5.scaleImage(FACTOR);
            	   
            	   
            	   // Verifying Die Values
            	   System.out.println("Die 1: " + die1 + "	imgDie1: " + imgDie1);
            	   System.out.println("Die 2: " + die2 + "	imgDie2: " + imgDie2);
            	   System.out.println("Die 3: " + die3 + "	imgDie3: " + imgDie3);
            	   System.out.println("Die 4: " + die4 + "	imgDie4: " + imgDie4);
            	   System.out.println("Die 5: " + die5 + "	imgDie5: " + imgDie5);
            	   
            	   dice = new int[] {die1,die2,die3,die4,die5};
            	   if(checkYahtzee(dice) > 0) {
            		   JOptionPane.showMessageDialog(gamePanel, "YAHTZEE! \n (forced joker rules apply)");
            	   }
            	   rollCount -= 1;
            	   rollLabel.setText("Rolls Left: " + rollCount);
    		   }
    		    
    	   } else {
    		   JOptionPane.showMessageDialog(dicePanel, "Rolls Used Up. \n Select Any Available Score Category.");
    	   }
    	   
       }            
    }
	
	class AddAcesListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!aces && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && checkYahtzee(dice) != 1 && 
						(yCheck == false || threeKind == false || fourKind == false || fullHouse == false || 
						smallStraight == false || largeStraight == false || yahtzee == false || chance == false)) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select a valid category)");
				} else {
					aces = true;
					
					if (checkYahtzee(dice) != 0 && yahtzee == true) {
						yBonusCount += 1;
					}
					// compute score
					int score = 0;
					for (int i=0; i<dice.length; i++) {
						if (dice[i] == 1) {
							score += 1;
						}
					}
					// display score and start next round 
					acesField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Aces Category Already Filled");
			}
		}
	}
	
	class AddTwosListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!twos && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && checkYahtzee(dice) != 2 && 
						(yCheck == false || threeKind == false || fourKind == false || fullHouse == false || smallStraight == false || 
						largeStraight == false || yahtzee == false || chance == false)) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select a valid category)");
				} else {
					twos = true;
					
					if (checkYahtzee(dice) != 0 && yahtzee == true) {
						yBonusCount += 1;
					}
					// compute score
					int score = 0;
					for (int i=0; i<dice.length; i++) {
						if (dice[i] == 2) {
							score += 2;
						}
					}
					// display score and start next round
					twosField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Twos Category Already Filled");
			}
		
		}
	}
	
	class AddThreesListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!threes && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && checkYahtzee(dice) != 3 && 
						(yCheck == false || threeKind == false || fourKind == false || fullHouse == false || smallStraight == false || 
						largeStraight == false || yahtzee == false || chance == false)) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select a valid category)");
				} else {
					threes = true;

					if (checkYahtzee(dice) != 0 && yahtzee == true) {
						yBonusCount += 1;
					}
					// compute score
					int score = 0;
					for (int i=0; i<dice.length; i++) {
						if (dice[i] == 3) {
							score += 3;
						}
					}
					// display score and start next round
					threesField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Threes Category Already Filled");
			}
		
		}
	}
	
	class AddFoursListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!fours && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && checkYahtzee(dice) != 4 && 
						(yCheck == false || threeKind == false || fourKind == false || fullHouse == false || smallStraight == false || 
						largeStraight == false || yahtzee == false || chance == false)) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select a valid category)");
				} else {
					fours = true;

					if (checkYahtzee(dice) != 0 && yahtzee == true) {
						yBonusCount += 1;
					}
					// compute score
					int score = 0;
					for (int i=0; i<dice.length; i++) {
						if (dice[i] == 4) {
							score += 4;
						}
					}
					// display score and start next round
					foursField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Fours Category Already Filled");
			}		
		}
	}
	
	class AddFivesListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!fives && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && checkYahtzee(dice) != 5 && 
						(yCheck == false || threeKind == false || fourKind == false || fullHouse == false || smallStraight == false || 
						largeStraight == false || yahtzee == false || chance == false)) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select a valid category)");
				} else {
					fives = true;

					if (checkYahtzee(dice) != 0 && yahtzee == true) {
						yBonusCount += 1;
					}
					// compute score
					int score = 0;
					for (int i=0; i<dice.length; i++) {
						if (dice[i] == 5) {
							score += 5;
						}
					}
					// display score and start next round
					fivesField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Fives Category Already Filled");
			}		
		}
	}
	
	class AddSixesListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!sixes && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && checkYahtzee(dice) != 6 && 
						(yCheck == false || threeKind == false || fourKind == false || fullHouse == false || smallStraight == false || 
						largeStraight == false || yahtzee == false || chance == false)) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select a valid category)");
				} else {
					sixes = true;

					if (checkYahtzee(dice) != 0 && yahtzee == true) {
						yBonusCount += 1;
					}
					// compute score
					int score = 0;
					for (int i=0; i<dice.length; i++) {
						if (dice[i] == 6) {
							score += 6;
						}
					}
					// display score and start next round
					sixesField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Sixes Category Already Filled");
			}		
		}
	}
	
	class AddThreeKindListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!threeKind && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && yCheck == false) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select the valid category from upper section)");
				} else {
					threeKind = true;
					
					if (checkYahtzee(dice) != 0 && yahtzee == true) {
						yBonusCount += 1;
					}
					// compute score
					boolean foundThree = false;
					int score = 0;
					int dieCounts[] = new int[] {0,0,0,0,0,0};
					for (int i=0; i<dice.length; i++) {
						if (dice[i] == 1) {dieCounts[0] += 1;}
						else if (dice[i] == 2) {dieCounts[1] += 1;}
						else if (dice[i] == 3) {dieCounts[2] += 1;}
						else if (dice[i] == 4) {dieCounts[3] += 1;}
						else if (dice[i] == 5) {dieCounts[4] += 1;}
						else if (dice[i] == 6) {dieCounts[5] += 1;}
					}
					for (int i=0; i<dieCounts.length; i++) {
						if (dieCounts[i] > 2) {foundThree = true;}
					}
					if (foundThree) {
						for (int i=0; i<dice.length; i++) {
							score += dice[i];
						}
					}
					// display score and start next round
					threeKindField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Three of a Kind Category Already Filled");
			}		
		}
	}
	
	class AddFourKindListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!fourKind && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && yCheck == false) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select the valid category from upper section)");
				} else {
					fourKind = true;

					if (checkYahtzee(dice) != 0 && yahtzee == true) {
						yBonusCount += 1;
					}
					// compute score
					boolean foundFour = false;
					int score = 0;
					int dieCounts[] = new int[] {0,0,0,0,0,0};
					for (int i=0; i<dice.length; i++) {
						if (dice[i] == 1) {dieCounts[0] += 1;}
						else if (dice[i] == 2) {dieCounts[1] += 1;}
						else if (dice[i] == 3) {dieCounts[2] += 1;}
						else if (dice[i] == 4) {dieCounts[3] += 1;}
						else if (dice[i] == 5) {dieCounts[4] += 1;}
						else if (dice[i] == 6) {dieCounts[5] += 1;}
					}
					for (int i=0; i<dieCounts.length; i++) {
						if (dieCounts[i] > 3) {foundFour = true;}
					}
					if (foundFour) {
						for (int i=0; i<dice.length; i++) {
							score += dice[i];
						}
					}
					// display score and start next round
					fourKindField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);	
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Four of a Kind Category Already Filled");
			}		
		}
	}
	
	class AddFullHouseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!fullHouse && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && yCheck == false) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select the valid category from upper section)");
				} else if (checkYahtzee(dice) != 0 && yCheck == true){
					yBonusCount += 1;
					fullHouse = true;
					int score = 25;
					// display score and start next round
					fullHouseField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				} else {
					fullHouse = true;

					// compute score
					boolean foundTwo = false;
					boolean foundThree = false;
					int score = 0;
					int dieCounts[] = new int[] {0,0,0,0,0,0};
					for (int i=0; i<dice.length; i++) {
						if (dice[i] == 1) {dieCounts[0] += 1;}
						else if (dice[i] == 2) {dieCounts[1] += 1;}
						else if (dice[i] == 3) {dieCounts[2] += 1;}
						else if (dice[i] == 4) {dieCounts[3] += 1;}
						else if (dice[i] == 5) {dieCounts[4] += 1;}
						else if (dice[i] == 6) {dieCounts[5] += 1;}
					}
					for (int i=0; i<dieCounts.length; i++) {
						if (dieCounts[i] == 2) {foundTwo = true;}
						else if (dieCounts[i] == 3) {foundThree = true;}
					}
					if (foundTwo && foundThree) {
						score = 25;
					}
					// display score and start next round
					fullHouseField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Full House Category Already Filled");
			}		
		}
	}
	
	class AddSmallStraightListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!smallStraight && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && yCheck == false) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select the valid category from upper section)");
				} else if (checkYahtzee(dice) != 0 && yCheck == true){
					yBonusCount +=1;
					smallStraight = true;
					int score = 30;
					// display score and start next round
					smallStraightField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				} else {
					smallStraight = true;
					
					// compute score
					int score = 0;
					boolean dieFound[] = new boolean[] {false,false,false,false,false,false};
					for (int i=0; i<dice.length; i++) {
						if (dice[i] == 1) {dieFound[0] = true;}
						else if (dice[i] == 2) {dieFound[1] = true;}
						else if (dice[i] == 3) {dieFound[2] = true;}
						else if (dice[i] == 4) {dieFound[3] = true;}
						else if (dice[i] == 5) {dieFound[4] = true;}
						else if (dice[i] == 6) {dieFound[5] = true;}
					}
					if (dieFound[0] && dieFound[1] && dieFound[2] && dieFound[3]) {
						score = 30;
					} else if (dieFound[1] && dieFound[2] && dieFound[3] && dieFound[4]) {
						score = 30;
					} else if (dieFound[2] && dieFound[3] && dieFound[4] && dieFound[5]) {
						score = 30;
					}
					// display score and start next round
					smallStraightField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Small Straight Category Already Filled");
			}		
		}
	}
	
	class AddLargeStraightListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!largeStraight && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && yCheck == false) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select the valid category from upper section)");
				} else if (checkYahtzee(dice) != 0 && yCheck == true){
					yBonusCount += 1;
					largeStraight = true;
					int score = 40;
					// display score and start next round
					largeStraightField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				} else {
					largeStraight = true;
					
					// compute score
					boolean foundLargeStraight = false;
					int score = 0;
					Arrays.sort(dice);
					if (dice[0] == 1 && dice[1] == 2 && dice[2] == 3 && dice[3] == 4 && dice[4] == 5) {
						foundLargeStraight = true;
					} else if (dice[0] == 2 && dice[1] == 3 && dice[2] == 4 && dice[3] == 5 && dice[4] == 6) {
						foundLargeStraight = true;
					}
					if (foundLargeStraight) {
						score = 40;
					}
					// display score and start next round
					largeStraightField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Large Straight Category Already Filled");
			}		
		}
	}
	
	class AddYahtzeeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!yahtzee && rollCount < 3) {
				yahtzee = true;
				
				// compute score
				boolean foundFive = false;
				int score = 0;
				int dieCounts[] = new int[] {0,0,0,0,0,0};
				for (int i=0; i<dice.length; i++) {
					if (dice[i] == 1) {dieCounts[0] += 1;}
					else if (dice[i] == 2) {dieCounts[1] += 1;}
					else if (dice[i] == 3) {dieCounts[2] += 1;}
					else if (dice[i] == 4) {dieCounts[3] += 1;}
					else if (dice[i] == 5) {dieCounts[4] += 1;}
					else if (dice[i] == 6) {dieCounts[5] += 1;}
				}
				for (int i=0; i<dieCounts.length; i++) {
					if (dieCounts[i] > 4) {foundFive = true;}
				}
				if (foundFive) {
					score = 50;
					yBonus = 100;
				}
				// display score and start next round
				yahtzeeField.setText("" + score);
				turnCount +=1;
				playTurn(turnCount);
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Yahtzee Category Already Filled");
			}		
		}
	}
	
	class AddChanceListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!chance && rollCount < 3) {
				if (checkYahtzee(dice) != 0 && yCheck == false) {
					JOptionPane.showMessageDialog(upperPanel, "Forced Joker Rules \n (select the valid category from upper section)");
				} else {
					chance = true;
					
					if (checkYahtzee(dice) != 0 && yahtzee == true) {
						yBonusCount += 1;
					}
					// compute score
					int score = 0;
					for (int i=0; i<dice.length; i++) {
						score += dice[i];
					}
					// display score and start next round
					chanceField.setText("" + score);
					turnCount +=1;
					playTurn(turnCount);
				}
			} else if (rollCount >= 3) {
				JOptionPane.showMessageDialog(upperPanel, "Must Roll Dice Before Selecting A Category");
			} else {
				JOptionPane.showMessageDialog(upperPanel, "Chance Category Already Filled");
			}		
		}
	}
	
	
	public static void main(String args[]) {
		YahtzeeFrame yahtzee = new YahtzeeFrame();
		yahtzee.setVisible(true);
	}
}
