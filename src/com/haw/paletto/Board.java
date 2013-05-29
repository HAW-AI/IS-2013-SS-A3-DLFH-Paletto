package com.haw.paletto;

import java.util.ArrayList;
import java.util.List;

public class Board {
	List<List<Token>> tokens;
	
	
	public Board(List<List<Token>> tokens){
		this.tokens = tokens;
	}
	
	public List<List<Token>> tokens(){
		return this.tokens;
	}
	
	public Token token(int xPos, int yPos){
		return tokens.get(yPos).get(xPos);
	}
	
	public void tokens(List<List<Token>> tokens){
		this.tokens = tokens;
	}

	public List<List<Token>> removeStone(int xPos, int yPos) {
		tokens.get(yPos).get(xPos).setUnavailable();
		return tokens;
	}
	
	public void takeStones(List<Token> stones){
		for(Token stone : stones){
			removeStone(stone.xPos(),stone.yPos());
		}
	}
	
	public void setMoveable(List<Token> moveableTokens){
		for(List<Token> row : tokens){
			for(Token token : row){
				token.setUnmoveable();
			}
		}
		
		for(Token moveableToken : moveableTokens){
			tokens.get(moveableToken.yPos()).get(moveableToken.xPos()).setMoveable();
		}
	}
	
	public int getSize(){
		return tokens.get(0).size();
	}
	
	public static int getSize(Board board){
		return board.tokens.get(0).size();
	}
	
	public Board clone(){
		List<List<Token>> result = new ArrayList<List<Token>>();
		for(List<Token> row : tokens){
			List<Token> resultRow = new ArrayList<Token>();
			for(Token token : row){
				resultRow.add(token.clone());
			}
			result.add(resultRow);
		}
		return new Board(result);
	}
	
	public static boolean isEmpty(Board board){
		boolean result = true;
		for(List<Token> row : board.tokens()){
			for(Token t : row){
				if(t.isAvailable())result = false;
			}
		}
		return result;
	}
	
	public boolean equals(Object that){
		boolean result = false;
		if(that == this) {
		      result = true;
	    } else if(that instanceof Board) {
	      Board board = (Board) that;
			for(List<Token> row : board.tokens()){
				for(Token token : row){
					if(!result && this.tokens().get(token.yPos()).get(token.xPos()).equals(token)){
						result = true;
					}
				}
			}
	    }
	    return result;
	}
}
