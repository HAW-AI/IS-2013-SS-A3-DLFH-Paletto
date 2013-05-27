package com.haw.paletto.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.List;

import com.haw.paletto.Game;
import com.haw.paletto.Token;

public class GameGui {
	private JFrame f;
	private String GameName = "Paletto";
	private TokenButton newGameButton,doneButton,fieldButtons[][];
	private int rowColumnSize;
	private JPanel fieldPanel, scorePanel, actionPanel;
	private JPanel aiScorePanel, playerScorePanel;
	
	private JLabel aiScoreLabel, playerScoreLabel;
	
	public Game game;

	public GameGui(int rowColumnSize) {
		this.rowColumnSize = rowColumnSize;
		f = new JFrame(GameName);
		
		fieldButtons = new TokenButton[rowColumnSize][rowColumnSize];
		
		fieldPanel = new JPanel();
        scorePanel = new JPanel();
        actionPanel = new JPanel();
        aiScorePanel = new JPanel();
        playerScorePanel = new JPanel();
		
        newGameButton = new TokenButton("New Game");
        doneButton = new TokenButton("Move Done");
        
        aiScoreLabel = new JLabel();
        playerScoreLabel = new JLabel();
        
		for(int i=0; i < rowColumnSize; i++){
			for(int j=0; j < rowColumnSize; j++){
				fieldButtons[i][j] = new TokenButton(i+"-"+j);
			}
		}
	}
	
	public void setGame(Game newgame){
		this.game = newgame;
		repaint(game);
	}

	public void start(Game newgame) {
		this.game = newgame;
		f.setLayout(new BorderLayout());
		
		f.add(fieldPanel, BorderLayout.CENTER);
		f.add(scorePanel, BorderLayout.EAST);
		f.add(actionPanel, BorderLayout.SOUTH);
		
		ActionListener moveListener = new MoveActionListener(this, game);
		fieldPanel.setLayout(new GridLayout(rowColumnSize,rowColumnSize));
		for(int i=0; i < rowColumnSize; i++){
			for(int j=0; j < rowColumnSize; j++){
				fieldButtons[i][j].addActionListener(moveListener);
				fieldButtons[i][j].setActionCommand(i+"-"+j);				
				fieldPanel.add(fieldButtons[i][j]);
			}
		}
		
		actionPanel.setLayout(new FlowLayout());
		actionPanel.add(doneButton);
		doneButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				done();
			}
		});
		newGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				newGame();
			}
		});
		actionPanel.add(newGameButton);
		/*
		String aiScoreString = "AI:", playerScoreString = "Player:";
		
		for(Color c : game.getAiStones().keySet()){
			aiScoreString.concat(c.getClass()+": "+game.getAiStones().get(c)+"\n");
			System.out.println("repaint in start");
		}
		for(Color c : game.getPlayerStones().keySet()){
			playerScoreString.concat(c.getClass()+": "+game.getPlayerStones().get(c)+"\n");
		}
		aiScoreLabel.setText(aiScoreString);
		playerScoreLabel.setText(playerScoreString);
		*/
		aiScorePanel.add(aiScoreLabel);
		playerScorePanel.add(playerScoreLabel);
		
		scorePanel.setLayout(new BorderLayout());
		scorePanel.add(aiScorePanel, BorderLayout.SOUTH);
		scorePanel.add(playerScorePanel, BorderLayout.NORTH);
		
		f.setSize(280, 350);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void newGame(){
		//TODO new Game did not work yet
		setGame(Game.newGame());
	}
	
	public void done(){
		Game.movePlayer(this,this.game);
	}

	public void repaint(Game game){
		for(List<Token> row : game.getBoard().tokens()){
			for(Token token : row){
				fieldButtons[token.yPos()][token.xPos()].setState(token.color(),token.isMoveable());
			}
		}
		
		//TODO print Colors names
		String aiScoreString = "<html><p align=left>AI:", playerScoreString = "<html><p align=left>Player:";
		
		for(Color c : game.getAiStones().keySet()){
			aiScoreString+=("<br>"+c+": "+game.getAiStones().get(c));
		}
		aiScoreString+="</p></html>";
		for(Color c : game.getPlayerStones().keySet()){
			playerScoreString+=("<br>"+c+": "+game.getPlayerStones().get(c));
		}
		playerScoreString+="</p></html>";
		aiScoreLabel.setText(aiScoreString);
		playerScoreLabel.setText(playerScoreString);
	}
}
