package com.haw.paletto.logic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
	
	public static Game doMove(Game game, List<Token> move, boolean aiMove) {
		return Game.takeStones(game, move, aiMove);
	}
			
	public static List<Token> bestMove(Game game, int depth, boolean aiMove){
		int val = Integer.MIN_VALUE;
		List<Token> result = new ArrayList<Token>();
		for(List<Token> aMove : possibleMoves(game.getBoard())){
			if(Game.moveAllowed(game.getBoard().tokens(), game.getSize(),aMove)){
				Game newGame = doMove(Game.clone(game), aMove, aiMove);
				List<List<Token>> moveList = new ArrayList<List<Token>>();
				int eval = evalNextState(newGame, game, aiMove, depth-1, aMove, moveList);
				System.out.println("BestMove "+aMove+" = "+eval);
				if(eval >= val){ //this is a max move
					val=eval;
					result = aMove;
				}
			} else {
				System.out.println(aMove+" not allowed");
			}
		}
		System.out.println("---------------Max Move "+result+" = "+val);
		return result;
	}
	
	public static int evalNextState(Game game, Game lastGame, boolean aiMove, int searchDepth,List<Token> lastMove, List<List<Token>> moveList){
		return minmax(game, lastGame, aiMove, searchDepth, lastMove, moveList);
	}
	
	public static int minmax(Game game, Game lastGame,  boolean aiMove, int searchDepth, List<Token> lastMove, List<List<Token>> moveList){
		List<List<Token>> newMoveList = new ArrayList<List<Token>>();
		newMoveList.addAll(moveList);
		newMoveList.add(lastMove);
		
		//System.out.println(">>>>>>>>>>>>>>>>><"+Game.playerWon(game));
		if(searchDepth <= 0 || Game.playerWon(game)){ //end recursion if depth = 0 or one player has won
			int result = evalMove(lastGame, aiMove, newMoveList.get(newMoveList.size()-1)); //last game is neede for last player score
			System.out.println(newMoveList+" > Result:"+result);
			return result;
		}
		aiMove = !aiMove;
		boolean isMax = aiMove? true : false; //AI is max player
		int resultVal= isMax?Integer.MIN_VALUE:Integer.MAX_VALUE; //AI = min start result, player = max start result

		for(List<Token> aMove : possibleMoves(game.getBoard())){
			if(Game.moveAllowed(game.getBoard().tokens(), game.getSize(),aMove)){
				Game newGame = doMove(Game.clone(game), aMove, aiMove);
				int nextVal=minmax(newGame, game, aiMove, searchDepth-1, aMove, newMoveList);
				//max with higher value or min with lower value = set this value
				if(isMax && (nextVal>=resultVal)){
					//System.out.println("MAX");
					resultVal=nextVal;
				}
				if(!isMax && (nextVal<=resultVal)){
					//System.out.println("MIN");
					resultVal=nextVal;
				}
			} else {
				System.out.println(aMove+" not allowed");
			}
		}
		if(isMax){
			System.out.println("Max:"+resultVal);
		} else {
			System.out.println("Min:"+resultVal);
		}
		return resultVal;
	}
	
	public static int evalMove(Game game, boolean aiMove, List<Token> lastMove){
		int result = 0;
		int aiWinPossibility = 0;
		int humanWinPosibility = 0;
		int deadlyMove = 0;
		
		Color moveColor = lastMove.get(0).color(); //all colors of the tokens in a move have the same color
		int tokenQuantity = lastMove.size();
		Map<Color,Integer> humanStones, aiStones;

		humanStones = game.getPlayerStones();
		aiStones= game.getAiStones();
		
		List<Color> commonStones = new ArrayList<Color>(humanStones.keySet());
		commonStones.retainAll(aiStones.keySet());
		
		//1.jede farbe die nicht beide haben erhöhen possibility
		aiWinPossibility += (game.getSize()-commonStones.size());
		humanWinPosibility += (game.getSize()-commonStones.size());

		//2.eine farbe von der ich mehr habe ist eine größere gewinnposiblity für mich als eine die ich noch nicht habe
		if(aiStones.containsKey(moveColor)){
			aiWinPossibility += aiStones.get(moveColor);
		}
		if(humanStones.containsKey(moveColor)){
			humanWinPosibility += humanStones.get(moveColor);
		}		

		//3. wenn beide alle farben haben ist der letzte stein unbezahlbar
		if(aiMove && humanStones.keySet().size() == game.getSize() && aiStones.keySet().size() == game.getSize() && Game.takeableStones(game.getBoard()).size() == 1){
			deadlyMove = Integer.MAX_VALUE;
		}
		if(!aiMove && humanStones.keySet().size() == game.getSize() && aiStones.keySet().size() == game.getSize() && Game.takeableStones(game.getBoard()).size() == 1){
			deadlyMove = Integer.MIN_VALUE;
		}
		
		//4. der sieg ist unbezahlbar
		if(( aiStones.containsKey(moveColor) && ( (tokenQuantity + aiStones.get(moveColor))==game.getSize()) )){
			deadlyMove = Integer.MAX_VALUE;
		}
		if(( humanStones.containsKey(moveColor) && ((tokenQuantity + humanStones.get(moveColor))==game.getSize()) )){
			//!!!!!! humanStones.get(moveColor) falsch! es wäre richtig wenn man die punkte von vorher nimmt aber dann stimmt auch was nicht
			deadlyMove = Integer.MIN_VALUE;
		}	
		
		if(aiMove && (tokenQuantity == game.getSize())){
			deadlyMove = Integer.MAX_VALUE;
		}
		if(!aiMove && (tokenQuantity == game.getSize())){
			deadlyMove = Integer.MIN_VALUE;
		}
		
		if(aiMove){
			result = aiWinPossibility - humanWinPosibility;
		//	System.out.println("ai");

		} else {
			result = humanWinPosibility - aiWinPossibility;
			//System.out.println("human");
		}
		if(deadlyMove != 0){
			result = deadlyMove;
		}
		
		//System.out.println("Result("+getColorNamen(gainedColor)+"): ["+result+"]");
		return result;
	}
	
	//returns all possible Moves as list of Tokens, ordered by color
	public static List<List<Token>> possibleMoves(Board board){
		List<Token> takeableStones = Game.takeableStones(board);
		System.out.println(">>>>>>>Takeablestones: "+takeableStones);
		List<List<Token>> result = new ArrayList<List<Token>>();
		Map<Color,List<Token>> takeableColors = new HashMap<Color,List<Token>>();
		for(Token token : takeableStones){
			if(!takeableColors.containsKey(token.color())){
				List<Token> list = new ArrayList<Token>();
				list.add(token);
				takeableColors.put(token.color(),list);
			}else{
				takeableColors.get(token.color()).add(token);
			}
		}
		for(Color color : takeableColors.keySet()){
			List<List<Token>> tokens = powerset(takeableColors.get(color));
			tokens.remove(new ArrayList<Token>()); //remove empty sets
			result.addAll(tokens);
		}
		System.out.println(">>>>>>>Possiblemoves: "+result);		
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
