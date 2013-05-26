package com.haw.paletto.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.haw.paletto.Game;

public class MoveActionListener implements ActionListener{
	
	private Game game;
	private GameGui gui;

	public MoveActionListener(GameGui gui, Game game){
		this.game = game;
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String FieldPosition = event.getActionCommand();
		int xPos = Integer.parseInt(FieldPosition.split("-")[1]);
		int yPos = Integer.parseInt(FieldPosition.split("-")[0]);
		this.gui.setGame(Game.takeStone(game, xPos, yPos, false));
	}
}
