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
	
	public Game game;

	public GameGui(int rowColumnSize) {
		this.rowColumnSize = rowColumnSize;
		f = new JFrame(GameName);
		
		fieldButtons = new TokenButton[rowColumnSize][rowColumnSize];
		
		fieldPanel = new JPanel();
        scorePanel = new JPanel();
        actionPanel = new JPanel();
		
        newGameButton = new TokenButton("New Game");
        doneButton = new TokenButton("Move Done");
        
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
		
		f.setSize(280, 350);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void newGame(){
		setGame(Game.newGame(3));
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
		f.repaint();
	}
}
