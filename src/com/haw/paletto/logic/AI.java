package com.haw.paletto.logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		for(List<Token> posMove : possibleMoves(game.getBoard())){
			Game newGame = doMove(Game.clone(game), posMove, aiOnTurn);
			int eval = evalNextState(newGame,aiOnTurn,depth-1, posMove, game);
			if(eval>val){ val=eval; result = posMove;}
		}
		return result;
	}
	
	public static int evalNextState(Game game, boolean ai, int searchDepth, List<Token> move, Game oldGame){
		return minmax(game, ai, searchDepth, move, oldGame);
	}
	
	public static int minmax(Game game, boolean ai, int searchDepth, List<Token> move, Game oldGame){
		if(searchDepth <= 0 || Game.playerWon(game)){
			return evalMove(oldGame,ai,move);
		}
		int resultVal;
		if(ai) resultVal = Integer.MAX_VALUE;
		else resultVal = Integer.MIN_VALUE;
		
		for(List<Token> posMove : possibleMoves(game.getBoard())){
			if(Game.moveAllowed(posMove)){
				Game newGame = doMove(game, posMove, ai);
				int nextVal=minmax(newGame, !ai, searchDepth-1, posMove, game);
				if( (!ai && nextVal>=resultVal) || (ai && nextVal<=resultVal)){
					resultVal = nextVal;
				}
			}
		}
		return resultVal;
		
	}
	
	public static int evalMove(Game game, boolean ai, List<Token> move){
		int result = 0;
		Color gainedColor = move.get(0).color();
		Map<Color,Integer> ownList, opponentList;
		if(ai){
			ownList = game.getAiStones();
			opponentList = game.getPlayerStones();
		}else{
			ownList = game.getPlayerStones();
			opponentList = game.getAiStones();
		}
		if(ownList.containsKey(gainedColor)){
			//gut, wenn eigene farbe erhöht
			//schlecht, wenn eigene farbe und gegner schon hat
			if(opponentList.containsKey(gainedColor)){
				result = 0;
			}else{
				result = move.size() + ownList.get(gainedColor);
				if(result == game.getSize()) result = Integer.MAX_VALUE;
			}
			
		}else{ //gut, wenn gegner farbe sammelt und selbst noch nicht
			if(opponentList.containsKey(gainedColor)){
				result = opponentList.get(gainedColor); //TODO
				if((opponentList.get(gainedColor)+move.size()) == game.getSize()) result = Integer.MAX_VALUE;
			}else{
				result = move.size();
			}
		}
		return result;
	}
	
	public static List<List<Token>> possibleMoves(Board board){
		List<Token> takeableStones = Game.takeableStones(board);
		List<List<Token>> result = new LinkedList<List<Token>>();
		Map<Color,List<Token>> takeableColors = new HashMap<Color,List<Token>>();
		for(Token t : takeableStones){
			if(t.isAvailable()){
				if(!takeableColors.containsKey(t.color())){
					List<Token> l = new LinkedList<Token>();
					l.add(t);
					takeableColors.put(t.color(),l);
				}else{
					takeableColors.get(t.color()).add(t);
				}
			}
		}
		for(Color c : takeableColors.keySet()){
			List<Token> colorSet = takeableColors.get(c);
			int numColorStones = colorSet.size();
			Set<List<Token>> colorMoves = new HashSet<List<Token>>();
			for(int i = 0; i < numColorStones; i++){
				List<Token> move = new LinkedList<Token>();
				for(int j=0; j<numColorStones-1; j++){
					if((i & (1 << j)) != 0) {
						move.add(colorSet.get(j));
					}
				}
				colorMoves.add(move);
			}
			result.add(colorSet);
		}
		return result;
	}
}
