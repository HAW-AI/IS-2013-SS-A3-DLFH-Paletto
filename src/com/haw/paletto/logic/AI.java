package com.haw.paletto.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.haw.paletto.Game;
import com.haw.paletto.Token;
import com.haw.paletto.Board;
import java.awt.*;

public class AI {
	
	static int searchDepth = 4;
	
	public static Game doBestMove(Game game){
		return doMove(game, bestMove(game, searchDepth, true),true);
	}
	
	public static Game doMove(Game game, List<Token> move, boolean aiOnTurn) {
		return Game.takeStones(game, move, aiOnTurn);
	}
	
	public static List<Token> bestMove(Game game, int depth, boolean aiOnTurn){
		int val = Integer.MIN_VALUE;
		List<Token> result = null;
		for(List<Token> move : possibleMoves(game.getBoard())){
			Game newGame = doMove(Game.clone(game), move, aiOnTurn);
			int eval = evalNextState(newGame,true,depth-1);
			if(eval>val){ val=eval; result = move;}
		}
		return result;
	}
	
	public static int evalNextState(Game game, boolean ai, int searchDepth){
		return minmax(game, ai, searchDepth);
	}
	
	public static int minmax(Game game, boolean ai, int searchDepth){
		if(searchDepth <= 0 || Game.playerWon(game)){
			return evalMove(game,ai);
		}
		int resultVal;
		if(ai) resultVal = Integer.MAX_VALUE;
		else resultVal = Integer.MIN_VALUE;
		
		for(List<Token> move : possibleMoves(game.getBoard())){
			if(Game.moveAllowed(move)){
				Game newGame = doMove(game, move, ai);
				int nextVal=minmax(newGame, !ai, searchDepth-1);
				if( (ai && nextVal>=resultVal) || (!ai && nextVal<=resultVal)){
					resultVal = nextVal;
				}
			}
		}
		return resultVal;
		
	}
	
	public static int evalMove(Game game, boolean ai){
		//TODO nicht schön so!
		int result = 0;
		if(ai){
			for(Color color : game.getAiStones().keySet()){
				result += game.getAiStones().get(color);
			}
		}else{
			for(Color color : game.getPlayerStones().keySet()){
				result += game.getPlayerStones().get(color);
			}
		}
		return result;
	}
	
	public static List<List<Token>> possibleMoves(Board board){
		List<Token> takeableStones = Game.takeableStones(board);
		List<List<Token>> result = new LinkedList<List<Token>>();
		Map<Color,List<Token>> takeableColors = new HashMap<Color,List<Token>>();
		for(Token t : takeableStones){
			if(!takeableColors.containsValue(t.color())){
				List<Token> l = new LinkedList<Token>();
				l.add(t);
				takeableColors.put(t.color(),l);
			}else{
				takeableColors.get(t.color()).add(t);
			}
		}
		for(Color c : takeableColors.keySet()){
			//TODO teillisten
			result.add(takeableColors.get(c));
		}
		return result;
	}
}
