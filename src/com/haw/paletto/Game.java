package com.haw.paletto;
import java.util.List;

import com.haw.paletto.gui.GameGui;
import com.haw.paletto.logic.AI;
import com.haw.paletto.logic.GameLogic;
public class Game {

	private GameLogic logic;
	private Board board;
	private GameGui gui;
	private AI ai;
	
	public static void main(String[] args) {
        GameGui gui = new GameGui();
        GameLogic logic = new GameLogic();
        AI ai = new AI();
		Game paletto = new Game(logic, gui, ai);
		paletto.start();
	}
	
	public Game(GameLogic logic,GameGui gui, AI ai){
		this.logic = logic;
		this.gui = gui;
		this.ai = ai;
	}
	
	private void start() {
		// TODO
	}

	public void newGame(){
		// TODO
	}
	
	public boolean playerWon(){
		// TODO
	}
	
	public boolean takeStone(Token stones){
		// TODO
	}
	
	public List<Token> takeableStones(){
		// TODO
	}
	
	public void moveAi(){
		// TODO
	}
	
	public void movePlayer(){
		// TODO
	}
}
