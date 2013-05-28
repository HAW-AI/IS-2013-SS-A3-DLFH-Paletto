package com.haw.paletto.logic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.haw.paletto.Game;
import com.haw.paletto.Token;
import com.haw.paletto.Board;
import java.awt.*;

public class AI {
	
	static int searchDepth = 3;
	
	public static Game doBestMove(Game game){
		return doMove(game, bestMove(game, searchDepth, true),true);
	}
	
	public static Game doMove(Game game, List<Token> move, boolean isAi) {
		return Game.takeStones(game, move, isAi);
	}
		
	public static List<Token> bestMove(Game game, int depth, boolean isAi){
		int val = Integer.MIN_VALUE;
		List<Token> result = null;
		for(List<Token> aMove : possibleMoves(game.getBoard())){
			Game newGame = doMove(Game.clone(game), aMove, isAi);
			int eval = evalNextState(newGame,isAi,depth-1, aMove.get(0).color(), aMove.size(), game);
			System.out.println("BestMove "+aMove+" = "+eval);
			if(eval > val){
				val=eval;
				result = aMove;
			}
		}
		System.out.println("---------------FinalBestMove "+result+" = "+val);
		return result;
	}
	
	public static int evalNextState(Game game, boolean ai, int searchDepth, Color color, int size, Game oldGame){
		return minmax(game, ai, searchDepth, color, size, oldGame);
	}
	
	public static int minmax(Game game, boolean ai, int searchDepth, Color color, int size, Game oldGame){
		//System.out.println("Depth:"+searchDepth);
		if(searchDepth <= 0 || Game.playerWon(game)){ //end recursion if depth = 0 or one player has won
			return evalMove(oldGame, ai, color, size, searchDepth);
		}
		boolean isMax = ai? true : false; //AI is max player
		int resultVal= isMax?-Integer.MAX_VALUE:Integer.MAX_VALUE; //AI = min start result, player = max start result
		for(List<Token> aMove : possibleMoves(game.getBoard())){
			//System.out.println("Testing move: "+aMove);
			Game newGame = doMove(game, aMove, ai);
			int nextVal=minmax(newGame, !ai, searchDepth-1, color, size, game);
			
			//max with higher value or min with lower value = set this value
			if(isMax && (nextVal>=resultVal)){
				//System.out.println("MAX");
				resultVal=nextVal;
			}
			if(!isMax && (nextVal<=resultVal)){
				//System.out.println("MIN");
				resultVal=nextVal;
			}
		}
		System.out.println("MinMaxResult:"+resultVal);
		return resultVal;
	}
	
	public static int evalMove(Game game, boolean ai, Color c, int size, int impact){
		int result = 0;
		Color gainedColor = c;
		Map<Color,Integer> playerList, aiList;

		playerList = game.getPlayerStones();
		aiList = game.getAiStones();

		if(!playerList.containsKey(gainedColor) && !aiList.containsKey(gainedColor) && size == game.getSize()){
			result += 1000;
		} if(playerList.containsKey(gainedColor) && ((size + playerList.get(gainedColor))==game.getSize())){
			result += 100;
		} else if(aiList.containsValue(gainedColor) && ((size + aiList.get(gainedColor))==game.getSize())){
			result += 100;
		} else if(playerList.containsKey(gainedColor) && !aiList.containsKey(gainedColor)){
			result -= 1;
		} else if(!playerList.containsKey(gainedColor) && aiList.containsKey(gainedColor)){
			result += 1;
		} else if(playerList.containsKey(gainedColor) && aiList.containsKey(gainedColor)){
			result = 0;
		} else if(!playerList.containsKey(gainedColor) && !aiList.containsKey(gainedColor)) {
			result += 1;
		}
		if(!ai){
			result = result *(-1);
		}
		//System.out.println("Result("+getColorNamen(gainedColor)+"): ["+result+"]");
		return result;
	}
	
	//returns all possible Moves as list of Tokens, ordered by color
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
			List<List<Token>> tokens = powerset(takeableColors.get(c));
			tokens.remove(new ArrayList<Token>()); //remove empty sets
			result.addAll(tokens);
		}
		//System.out.println("Possible Moves: "+result);
		return result;
	}
	
	//returns powerset of takeable stones of one color
	private static <T> List<List<T>> powerset(Collection<T> list) {
		  List<List<T>> ps = new ArrayList<List<T>>();
		  ps.add(new ArrayList<T>());   // add the empty set
		 
		  // for every item in the original list
		  for (T item : list) {
		    List<List<T>> newPs = new ArrayList<List<T>>();
		 
		    for (List<T> subset : ps) {
		      // copy all of the current powerset's subsets
		      newPs.add(subset);
		 
		      // plus the subsets appended with the current item
		      List<T> newSubset = new ArrayList<T>(subset);
		      newSubset.add(item);
		      if(!newSubset.isEmpty()) newPs.add(newSubset);
		    }
		 
		    // powerset is now powerset of list.subList(0, list.indexOf(item)+1)
		    ps = newPs;
		  }
		  return ps;
		}
	
	public static String getColorNamen(Color colorParam) {
        try {
            //first read all fields in array
            Field[] field = Class.forName("java.awt.Color").getDeclaredFields();
            for (Field f : field) {
                String colorName = f.getName();
                Class<?> t = f.getType();
                // System.out.println(f.getType());
                // check only for constants - "public static final Color"
                if (t == java.awt.Color.class) {
                    Color defined = (Color) f.get(null);
                    if (defined.equals(colorParam)) {
                        return colorName.toUpperCase();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error... " + e.toString());
        }
        return "NO_MATCH";
    }
}
