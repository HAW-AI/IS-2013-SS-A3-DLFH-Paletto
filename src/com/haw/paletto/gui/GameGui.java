package com.haw.paletto.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.List;
import java.util.Map;

import com.haw.paletto.Game;
import com.haw.paletto.Token;

public class GameGui {
	private JFrame f;
	private String GameName = "Paletto";
	private TokenButton fieldButtons[][];
	private Button newGameButton,doneButton;
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
		
        newGameButton = new Button("New Game");
        doneButton = new Button("Move Done");
        
        aiScoreLabel = new JLabel("AI Player");
        playerScoreLabel = new JLabel("Human Player");
        
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
		aiScorePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		aiScorePanel.setPreferredSize( new Dimension(100,100) );
		playerScorePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		playerScorePanel.setPreferredSize( new Dimension(100,100) );

		scorePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		scorePanel.setPreferredSize( new Dimension( 100, 100 ) );
		scorePanel.add(aiScoreLabel);
		scorePanel.add(aiScorePanel);
		scorePanel.add(playerScoreLabel);
		scorePanel.add(playerScorePanel);
		
		f.setSize(280, 350);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void newGame(){
		game.newGame();
		repaint(game);
	}
	
	public void done(){
		Game.movePlayer(this,this.game);
		repaint(game);
	}

	public void repaint(Game game){
		for(List<Token> row : game.getBoard().tokens()){
			for(Token token : row){
				fieldButtons[token.yPos()][token.xPos()].setState(token.color(),token.isMoveable());
			}
		}
		
		aiScorePanel.removeAll();
		for (Map.Entry<Color, Integer> entry : game.getAiStones().entrySet()) {
			JLabel newLabel = new JLabel(entry.getValue().toString());
			newLabel.setBackground(entry.getKey());
			newLabel.setOpaque(true);
			aiScorePanel.add(newLabel);
		}		
		
		playerScorePanel.removeAll();
		for (Map.Entry<Color, Integer> entry : game.getPlayerStones().entrySet()) {
			JLabel newLabel = new JLabel(entry.getValue().toString());
			newLabel.setBackground(entry.getKey());
			newLabel.setOpaque(true);
			playerScorePanel.add(newLabel);
		}
		if(game.isOver()){
			String msg = "";
			if(game.hasAiWon()){ 
				msg = "Game over.";
			}else{ 
				msg = "You win!";
			}
			JOptionPane.showMessageDialog(f,msg,"End of Game",JOptionPane.PLAIN_MESSAGE);
		}
		f.validate();
		f.repaint();
	}
}
