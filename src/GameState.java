package game;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameState implements java.io.Serializable {
	
	private String playerInput; 
	private int[] dice;
	private int[] scores;
	private boolean[] categories; 
	private int turnCount, rollCount, yBonus, yBonusCount;
	private boolean yCheck;
	
	public GameState(String playerInput, int[] dice, boolean[] categories, int[] scores, 
			int turnCount, int rollCount, int yBonus, int yBonusCount, boolean yCheck) {
		
		this.playerInput = playerInput;
		this.dice = dice;
		this.categories = categories;
		this.scores = scores;
		this.turnCount = turnCount;
		this.rollCount = rollCount;
		this.yBonus = yBonus;
		this.yBonusCount =yBonusCount;
		this.yCheck =yCheck;
	}

	public String getPlayerInput() {
		return playerInput;
	}
	
	public int[] getDice() {
		return dice;
	}
	
	public int[] getScores() {
		return scores;
	}
	
	public boolean[] getCategories() {
		return categories;
	}
	
	public int getTurnCount() {
		return turnCount;
	}
	
	public int getRollCount() {
		return rollCount;
	}
	
	public int getYBonus() {
		return yBonus;
	}
	
	public int getYBonusCount() {
		return yBonusCount;
	}
	
	public boolean getYCheck() {
		return yCheck;
	}
}
