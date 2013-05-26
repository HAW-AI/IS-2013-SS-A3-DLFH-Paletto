package com.haw.paletto;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haw.paletto.gui.GameGui;
import com.haw.paletto.logic.AI;
import com.haw.paletto.logic.GameLogic;
public class Game {

	final int size;
	Board board;
	Map<Color,Integer> aiStones;
	Map<Color,Integer> playerStones;
	
	public Game(int size){
		this.size = size;
		board = new Board(GameLogic.buildBoard(size));
		aiStones = new HashMap<Color,Integer>();
		playerStones = new HashMap<Color,Integer>();
	}
	
	public Game(int size, Board board, Map<Color,Integer> aiStones, Map<Color,Integer> playerStones){
		this.size = size;
		this.board = board;
		this.aiStones = aiStones;
		this.playerStones = playerStones;
	}
	
	public int getSize(){return this.size;}
	
	public Board getBoard(){ return this.board;}
	
	public Map<Color,Integer> getAiStones(){ return this.aiStones;}
	
	public Map<Color,Integer> getPlayerStones(){ return this.playerStones;}
	
	public static void main(String[] args) {
		GameGui gui = new GameGui(3);
		Game game = new Game(3);
		gui.start(game);
		List<Token> moveableTokens = GameLogic.moveableTokens(game.getBoard().tokens(), game.getSize());
		game.getBoard().setMoveable(moveableTokens);
		gui.repaint(game);
	}
	
	public static Game moveAi(Game game){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		return AI.doBestMove(game);
	}
	
	public static Game movePlayer(GameGui gui, Game game){
		System.out.println("Move Player");
		Game result = moveAi(game);
		System.out.println("Moved AI");
		List<Token> moveableTokens = GameLogic.moveableTokens(game.getBoard().tokens(), game.getSize());
		game.getBoard().setMoveable(moveableTokens);
		gui.setGame(game);
		return result;
	}
	
	public static Game newGame(int size){
		return new Game(size);
	}
	
	public static Game clone(Game game){
		return new Game(game.size, game.board.clone(), new HashMap<Color,Integer>(game.aiStones), new HashMap<Color,Integer>(game.playerStones));
	}
	
	
	public static boolean playerWon(Game game){
		return GameLogic.gameWon(game);
	}
	
	//TODO to players list
	public static Game takeStone(Game game, int xPos, int yPos){
		Color currentTokenColor = game.getBoard().token(xPos, yPos).color();
		List<List<Token>> tokens = game.getBoard().removeStone(xPos,yPos);
		List<Token> moveableTokens = GameLogic.moveableTokens(tokens, game.getSize(), currentTokenColor);
		game.getBoard().setMoveable(moveableTokens);
		return game;
	}
	
	public static Game takeStones(Game game, List<Token> move, boolean aiOnTurn){
		game.getBoard().takeStones(move);
		if(aiOnTurn){
			if(game.getAiStones().get(move.get(0).color()) != null){
				game.getAiStones().put(
					move.get(0).color(), game.getAiStones().get(
							move.get(0).color()
							) + move.size() 
					);
			}else{
				game.getAiStones().put(move.get(0).color(), move.size());
			}
		}else{
			if(game.getPlayerStones().get(move.get(0).color()) != null){
				game.getPlayerStones().put(
					move.get(0).color(), game.getPlayerStones().get(
							move.get(0).color()
							) + move.size() 
					);
			}else{
				game.getPlayerStones().put(move.get(0).color(), move.size());
			}
		}
		return game;
	}
	
	public static List<Token> takeableStones(Board board){
		return GameLogic.moveableTokens(board.tokens(),board.getSize());
	}
	
	public static boolean moveAllowed(List<Token> move){
		return GameLogic.moveAllowed(move);
	}
}
