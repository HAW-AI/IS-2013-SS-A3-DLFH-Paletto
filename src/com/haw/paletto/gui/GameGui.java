package com.haw.paletto.gui;

import java.awt.*;
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

	public void start(Game game) {
		f.setLayout(new BorderLayout());
		
		f.add(fieldPanel, BorderLayout.CENTER);
		f.add(scorePanel, BorderLayout.EAST);
		f.add(actionPanel, BorderLayout.SOUTH);
		
		ActionListener moveListener = new MoveActionListener(game);
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
		actionPanel.add(newGameButton);
		
		f.setSize(280, 350);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	public void repaint(List<List<Token>> tokens){
		for(List<Token> row : tokens){
			for(Token token : row){
				fieldButtons[token.xPos()][token.yPos()].setState(token.getColor(),token.isMovable());
			}
		}
		f.repaint();
	}
}
