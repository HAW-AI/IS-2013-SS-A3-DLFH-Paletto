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
	private static final int rowColumnSize = 3;
	
	public static void main(String[] args) {
        GameGui gui = new GameGui(rowColumnSize);
        GameLogic logic = new GameLogic();
        Board board = new Board(logic.buildBoard(rowColumnSize));
        AI ai = new AI();
		Game paletto = new Game(logic, gui, ai, board);
		paletto.start();
	}
	
	public Game(GameLogic logic,GameGui gui, AI ai, Board board){
		this.logic = logic;
		this.gui = gui;
		this.ai = ai;
		this.board = board;
	}
	
	private void start() {
		gui.start(this);
	}

	public void newGame(){
		// TODO
	}
	
	public boolean playerWon(){
		// TODO
	}
	
	public void takeStone(int xPos, int yPos){
		List<List<Token>> tokens = board.removeStone(xPos,yPos);
		gui.repaint(tokens);
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
