package com.haw.paletto;

import java.util.List;

public class Board {
	List<List<Token>> tokens;
	
	
	public Board(List<List<Token>> tokens){
		this.tokens = tokens;
	}
	
	public List<List<Token>> tokens(){
		return this.tokens;
	}
	
	public void tokens(List<List<Token>> tokens){
		this.tokens = tokens;
	}

	public List<List<Token>> removeStone(int xPos, int yPos) {
		tokens.get(xPos).get(yPos).setUnavailable();
		return tokens;
	}
	
	public void setMoveable(List<Token> moveableTokens){
		for(Token token : moveableTokens){
			this.tokens.get(token.yPos()).get(token.xPos()).setMoveable();
		}
	}
}
