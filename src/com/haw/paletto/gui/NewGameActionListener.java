package com.haw.paletto.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.haw.paletto.Game;

public class NewGameActionListener implements ActionListener{
	private Game game;

	public NewGameActionListener(Game game){
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		game.newGame();
	}
}
