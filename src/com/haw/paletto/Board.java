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

	@Override
	public String toString() {
		return "Board [" + tokens + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tokens == null) ? 0 : tokens.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (tokens == null) {
			if (other.tokens != null)
				return false;
		} else if (!tokens.equals(other.tokens))
			return false;
		return true;
	}
}
