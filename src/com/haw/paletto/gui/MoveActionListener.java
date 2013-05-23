package com.haw.paletto.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.haw.paletto.Game;

public class MoveActionListener implements ActionListener{
	
	private Game game;

	public MoveActionListener(Game game){
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String FieldPosition = event.getActionCommand();
		int xPos = Integer.parseInt(FieldPosition.split("-")[0]);
		int yPos = Integer.parseInt(FieldPosition.split("-")[1]);
		game.takeStone(xPos, yPos);
	}
}
