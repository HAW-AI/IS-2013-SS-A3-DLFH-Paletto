package com.haw.gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameGui {
	private JFrame f;
	private String GameName = "Paletto";
	private JButton newGameButton,doneButton,fieldButtons[];
	private int rowColumnSize = 3;
	private int fieldSize = rowColumnSize*rowColumnSize;
	private JPanel fieldPanel, scorePanel, actionPanel;

	public GameGui() {
		f = new JFrame(GameName);
		
		fieldButtons = new JButton[10];
		
		fieldPanel = new JPanel();
        scorePanel = new JPanel();
        actionPanel = new JPanel();
		
        newGameButton = new JButton("New Game");
        doneButton = new JButton("Done");
        
		for(int i=0; i < fieldSize; i++){
			fieldButtons[i] = new TokenButton("");
		}
	}

	public void start() {
		f.setLayout(new BorderLayout());
		
		f.add(fieldPanel, BorderLayout.CENTER);
		f.add(scorePanel, BorderLayout.EAST);
		f.add(actionPanel, BorderLayout.SOUTH);
		
		ActionListener moveListener = new MoveActionListener(this);
		fieldPanel.setLayout(new GridLayout(rowColumnSize,rowColumnSize));
		for(int i=0; i < fieldSize; i++){
			fieldButtons[i].addActionListener(moveListener);
			fieldPanel.add(fieldButtons[i]);
		}
		
		actionPanel.setLayout(new FlowLayout());
		actionPanel.add(newGameButton);
		actionPanel.add(doneButton);
		
		f.setSize(280, 350);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
