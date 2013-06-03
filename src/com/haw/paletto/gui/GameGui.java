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
	
	private JLabel aiScoreNameLabel, playerScoreNameLabel, aiScoreLabel, playerScoreLabel;
	
	private Game game;

	public GameGui(int rowColumnSize) {
		this.rowColumnSize = rowColumnSize;
		f = new JFrame(GameName);
		
		fieldButtons = new TokenButton[rowColumnSize][rowColumnSize];
		
		fieldPanel = new JPanel();
        scorePanel = new JPanel();
        actionPanel = new JPanel();
		
        newGameButton = new Button("New Game");
        doneButton = new Button("Move Done");
        
        aiScoreNameLabel = new JLabel("AI Player");
        playerScoreNameLabel = new JLabel("Human Player");
        
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

		scorePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		scorePanel.setPreferredSize( new Dimension( 100, 500 ) );
		aiScoreLabel.setPreferredSize(new Dimension( 100, 150 ) );
		aiScoreLabel.setVerticalAlignment(SwingConstants.TOP);
		playerScoreLabel.setPreferredSize(new Dimension( 100, 150 ) );
		playerScoreLabel.setVerticalAlignment(SwingConstants.TOP);
		scorePanel.add(aiScoreNameLabel);
		scorePanel.add(aiScoreLabel);
		scorePanel.add(playerScoreNameLabel);
		scorePanel.add(playerScoreLabel);
		
		f.setSize(500, 450);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void newGame(){
		game.newGame();
		doneButton.setEnabled(true);
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
		
		String startText = "<html><ul style='list-style-type:disc; margin:0 0 0 15; padding:0;'>";
		String endText = "</ul></html>";
		
		aiScoreLabel.setText(startText+buildScoreList(game.getAiStones())+endText);
        playerScoreLabel.setText(startText+buildScoreList(game.getPlayerStones())+endText);
		f.validate();
		f.repaint();
		
		if(game.isOver()){
			String msg = "";
			if(game.hasAiWon()){ 
				msg = "Game over.";
			}else{ 
				msg = "You win!";
			}
			doneButton.setEnabled(false);
			JOptionPane.showMessageDialog(f,msg,"End of Game",JOptionPane.PLAIN_MESSAGE);
		}
		
	}
	
	private String buildScoreList(Map<Color, Integer> score){
		String result = "";
		for (Map.Entry<Color, Integer> entry : score.entrySet()) {
			result += "<li color="+colorToHtmlHex(entry.getKey())+"><font color=#000000>"+entry.getValue()+"</font></li>";
		}
		return result;
	}
	
	private String colorToHtmlHex(Color color){
		String s = Integer.toHexString(color.getRGB() & 0xffffff);
		if(s.length() < 6){ //pad left with zeros
			s = "000000".substring(0,6-s.length()) + s;
		}
		return '#'+s;
	}
}
