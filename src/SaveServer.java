package server;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import game.GameState;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveServer extends JFrame implements Runnable {
	
	private ObjectInputStream inputFromClient;

	private JTextArea wordsBox;
	
	private Connection conn;
	private PreparedStatement queryGamesStatement;
	private PreparedStatement insertGameStatement;
	
	public SaveServer() {
		// Connect to Database
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:yahtzee.db");
			queryGamesStatement = conn.prepareStatement("Select * from Games WHERE Player = ?");
			insertGameStatement = conn.prepareStatement("INSERT INTO Games (PlayerInput, Dice, Categories, Scores, "
					+ "turnCount, rollCount, yBonus, yBonusCount, yCheck)) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
		} catch (SQLException e) {
			System.err.println("Connection error: " + e);
			System.exit(1);
		}
		
		createMainPanel();
		
		wordsBox.append("Ready to Accept Connections \n");
		
		//runServer();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,400);
		Thread t = new Thread(this);
		  
		t.start();
	}
	
	public void run() {
		   
		try {
			// Create a server socket
			ServerSocket serverSocket = new ServerSocket(8000);
			System.out.println("Server started ");

			
			// Create an object ouput stream
			//outputToFile = new ObjectOutputStream(
			//		new FileOutputStream("student.dat", true));

			     
			while (true) {
				// Listen for a new connection request
				Socket socket = serverSocket.accept();

				// Create an input stream from the socket
				inputFromClient = new ObjectInputStream(socket.getInputStream());
				
				//if (inputFromClient.read() == -1) break;
				
				// Read from input
				Object object = inputFromClient.readObject();

				// Write to the file
				GameState gs = (GameState)object;
				System.out.println("Received Game from: " + gs.getPlayerInput());
				
				
				int [] dice = gs.getDice();
				boolean [] categories = gs.getCategories();
				int [] scores = gs.getScores();
				
				wordsBox.append("Got Game from: " + gs.getPlayerInput() + "\n");
				//outputToFile.writeObject(object);
				//outputToFile.flush();
				
				//
				try {
					   PreparedStatement insertGameStmt = insertGameStatement;
					   
					   insertGameStmt.setString(1, gs.getPlayerInput());
					   insertGameStmt.setInt(5, gs.getTurnCount());
					   insertGameStmt.setInt(6, gs.getRollCount());
					   insertGameStmt.setInt(7, gs.getYBonus());
					   insertGameStmt.setInt(8, gs.getYBonusCount());
					   insertGameStmt.setBoolean(9, gs.getYCheck());
					   
					   
					   String DiceText = "";
					   DiceText += "[" + dice[0];
					   for(int i=1; i<dice.length; i++) {
						   DiceText += ", " + dice[i];
					   }
					   DiceText += "]";
					   insertGameStmt.setString(2, DiceText);
					   
					   
					   String CategoriesText = "[";
					   for(int i=0; i<categories.length; i++) {
						   if (categories[i] == true) {
							   CategoriesText += "true, ";
						   } else {
							   CategoriesText += "false, "; 
						   }
					   }
					   CategoriesText += "]";
					   insertGameStmt.setString(3, CategoriesText);
					   
					   String ScoresText = "";
					   ScoresText += "[" + scores[0];
					   for(int i=1; i<scores.length; i++) {
						   ScoresText += ", " + scores[i];
					   }
					   ScoresText += "]";
					   insertGameStmt.setString(4, ScoresText);
					   
					   insertGameStmt.executeUpdate();
					
				   } catch (SQLException e) {
					   // TODO Auto-generated catch block
					   e.printStackTrace();
				   }
				
				System.out.println("A game is stored");   
			}	
		}
		catch(ClassNotFoundException ex) {
			ex.printStackTrace();	
		}
		catch(IOException ex) {
			ex.printStackTrace();	
		}
		finally {
			try {
				inputFromClient.close();  
				//outputFile close
			}
			catch (Exception ex) {
				ex.printStackTrace();   
			}	
		}
	}
	
	public void createMainPanel() {
		wordsBox = new JTextArea(35,10);

		JScrollPane listScroller = new JScrollPane(wordsBox);
		this.add(listScroller, BorderLayout.CENTER);
		listScroller.setPreferredSize(new Dimension(250, 80));
	}
	
	public static void main(String[] main) {
		SaveServer saveServer = new SaveServer();
		saveServer.setVisible(true);
	}
}
