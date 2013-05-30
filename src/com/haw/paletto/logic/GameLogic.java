package com.haw.paletto.logic;

import static java.awt.Color.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.haw.paletto.Board;
import com.haw.paletto.Game;
import com.haw.paletto.Token;

public class GameLogic {
	
	static List<Color> colorList = Arrays.asList(red,blue,green,yellow,black,white,gray,orange,pink,magenta,lightGray,cyan,darkGray); 

	public static List<List<Token>> buildBoard(int size){
		List<List<Token>> result = new ArrayList<List<Token>>();
		if(size <= colorList.size()){
			List<Color> colors = getColors(size);
			colors = palettoShuffle(colors, size);
			for(int i=0; i< size;i++){
				List<Token> newList = new ArrayList<Token>(); 
				for(int j=0; j < size;j++){
					newList.add(new Token(colors.get((size*i)+j),j,i));
				}
				result.add(newList);
			}
		}
		
		return result;
	}
	
	private static List<Color> getColors(int quantity){
		List<Color> result = new ArrayList<Color>();
		for(int i=0; i< quantity;i++){
			for(int j=0; j < quantity;j++){
				result.add(colorList.get(i));
			}
		}
		return result;
	}
	
	private static List<Color> palettoShuffle(List<Color> colors, int size){
		boolean validOrder = false;
		
		while(!validOrder){
			validOrder = true;
			Collections.shuffle(colors);
			for (int i = 0; (i < colors.size()); i++) {
				Color currentElem = colors.get(i);
				
				if(i==0){ //first value
					if(colors.get(i+1).equals(currentElem) || colors.get(i+size).equals(currentElem) ) validOrder = false;
				} else if(i < size){ //rest of first row
					if(colors.get(i-1).equals(currentElem) || colors.get(i+1).equals(currentElem) || colors.get(i+size).equals(currentElem) ) validOrder = false;
				} else if(i >= size && i < (size*size-size-1)){ //all rows between first and last
					if(colors.get(i-1).equals(currentElem) || colors.get(i+1).equals(currentElem) || colors.get(i-size).equals(currentElem) || colors.get(i+size).equals(currentElem) ) validOrder = false;
				} else if(i==(size*size-1)){ //last value
					if(colors.get(i-1).equals(currentElem) || colors.get(i-size).equals(currentElem) ) validOrder = false;
				} else { //rest of last row
					if(colors.get(i-1).equals(currentElem) || colors.get(i+1).equals(currentElem) || colors.get(i-size).equals(currentElem) ) validOrder = false;
				}
			}
		}
		return colors;
	}

	public static boolean gameWon(Game game){
		return (Board.isEmpty(game.getBoard()) || game.getAiStones().containsValue(game.getSize()) || game.getPlayerStones().containsValue(game.getSize()));
	}
	
	public static List<Token> moveableTokens(List<List<Token>> tokens, int size){
		return moveableTokens(tokens, size, null);
	}
	
	public static List<Token> moveableTokens(List<List<Token>> tokens, int size, Color color){
		List<Token> result = new ArrayList<Token>();
		for(int i=0; i< size;i++){
			for(int j=0; j < size;j++){
				List<Boolean> moveableList = new ArrayList<Boolean>();
				Token currentElem = tokens.get(i).get(j);
				if(moveAllowed(tokens, size, Arrays.asList(currentElem)) && (color == null || color.equals(currentElem.color()))){
					if((i==0 && j==0) || (i==0 && j==(size-1)) || (i==(size-1) && j==0) || (i==(size-1) && j==(size-1))){ //corners
						if(tokens.get(i).get(j).isAvailable()) result.add(currentElem);
					} else if(i == 0){ //rest of first row
						moveableList.add(false);
						moveableList.add(tokens.get(i).get(j-1).isAvailable());
						moveableList.add(tokens.get(i).get(j+1).isAvailable());
						moveableList.add(tokens.get(i+1).get(j).isAvailable());
						if(isTokenMoveable(moveableList)) result.add(currentElem);
					} else if(i == (size-1)){ //rest of last row
						moveableList.add(false);
						moveableList.add(tokens.get(i).get(j-1).isAvailable());
						moveableList.add(tokens.get(i).get(j+1).isAvailable());
						moveableList.add(tokens.get(i-1).get(j).isAvailable());
						if(isTokenMoveable(moveableList)) result.add(currentElem);
					} else if(j == 0){ //first values of middle rows 
						moveableList.add(false);
						moveableList.add(tokens.get(i).get(j+1).isAvailable());
						moveableList.add(tokens.get(i+1).get(j).isAvailable());
						moveableList.add(tokens.get(i-1).get(j).isAvailable());
						if(isTokenMoveable(moveableList)) result.add(currentElem);	
					} else if(j == (size-1)){ //last values of middle rows 
						moveableList.add(false);
						moveableList.add(tokens.get(i).get(j-1).isAvailable());
						moveableList.add(tokens.get(i+1).get(j).isAvailable());
						moveableList.add(tokens.get(i-1).get(j).isAvailable());
						if(isTokenMoveable(moveableList)) result.add(currentElem);	
					} else { //rest of all rows between first and last
						moveableList.add(tokens.get(i).get(j-1).isAvailable());
						moveableList.add(tokens.get(i).get(j+1).isAvailable());
						moveableList.add(tokens.get(i+1).get(j).isAvailable());
						moveableList.add(tokens.get(i-1).get(j).isAvailable());
						if(isTokenMoveable(moveableList)) result.add(currentElem);
					}
				}
			}
		}
		return result;
	}
	
	private static boolean isTokenMoveable(List<Boolean> moveableList){
		boolean result = false;
		int count = 0;
		for(int i=0; (i < moveableList.size()) && !result; i++){
			if(!moveableList.get(i)){
				count++;
				if(count == 2) result = true;
			}
		}
		return result;
	}
	
	public static boolean moveAllowed(List<List<Token>> tokens, int size, List<Token> move){
		Queue<Token> notInspected = new LinkedList<Token>();
		List<Token> inspected = new LinkedList<Token>();
		int availableTokens = 0;
		for(int i=0; i < size;i++){
			for(int j=0; j < size;j++){
				Token token = tokens.get(i).get(j);
//				System.out.println("current: "+token+" move:"+move.get(0)+" equals:"+move.get(0).equals(token));
//				System.out.println(" available:"+token.isAvailable());
//				System.out.println(" notMove:"+!move.contains(token));
				if(token.isAvailable() && !move.contains(token)){
					//System.out.println("~~~"+token);
					availableTokens++;
					if(notInspected.isEmpty()) notInspected.add(token);
				}
			}
		}
		//
		while(!notInspected.isEmpty()){
			Token currentToken = notInspected.poll();
//			System.out.print("current "+currentToken);
//			System.out.print(" notInspected:"+!inspected.contains(currentToken));
//			System.out.print(" notMove:"+!inspected.contains(currentToken));
			if(!move.contains(currentToken) && !inspected.contains(currentToken)){
				//System.out.print(" added");
				inspected.add(currentToken);
				int xPos = currentToken.xPos();
				int yPos = currentToken.yPos();
				if(yPos==0 && xPos==0){ //top left corner
					if(tokens.get(yPos).get(xPos+1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos+1));
					if(tokens.get(yPos+1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos+1).get(xPos));	
				} else if(yPos==0 && xPos==(size-1)){ //top right corner
					if(tokens.get(yPos).get(xPos-1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos-1));
					if(tokens.get(yPos+1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos+1).get(xPos));	
				} else if(yPos==(size-1) && xPos==0){ //bottom left corner
					if(tokens.get(yPos).get(xPos+1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos+1));
					if(tokens.get(yPos-1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos-1).get(xPos));
				} else if(yPos==(size-1) && xPos==(size-1)){ //bottom right corner
					if(tokens.get(yPos).get(xPos-1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos-1));
					if(tokens.get(yPos-1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos-1).get(xPos));	
				} else if(yPos == 0){ //rest of first row
					if(tokens.get(yPos).get(xPos-1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos-1));
					if(tokens.get(yPos).get(xPos+1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos+1));
					if(tokens.get(yPos+1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos+1).get(xPos));	
				} else if(yPos == (size-1)){ //rest of last row
					if(tokens.get(yPos).get(xPos-1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos-1));
					if(tokens.get(yPos).get(xPos+1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos+1));
					if(tokens.get(yPos-1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos-1).get(xPos));
				} else if(xPos == 0){ //first values of middle rows 	
					if(tokens.get(yPos).get(xPos+1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos+1));
					if(tokens.get(yPos+1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos+1).get(xPos));	
					if(tokens.get(yPos-1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos-1).get(xPos));
				} else if(xPos == (size-1)){ //last values of middle rows 	
					if(tokens.get(yPos).get(xPos-1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos-1));
					if(tokens.get(yPos+1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos+1).get(xPos));	
					if(tokens.get(yPos-1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos-1).get(xPos));
				} else { //rest of all rows between first and last
					if(tokens.get(yPos).get(xPos-1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos-1));
					if(tokens.get(yPos).get(xPos+1).isAvailable()) notInspected.add(tokens.get(yPos).get(xPos+1));
					if(tokens.get(yPos+1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos+1).get(xPos));	
					if(tokens.get(yPos-1).get(xPos).isAvailable()) notInspected.add(tokens.get(yPos-1).get(xPos));
				}
			}
			//System.out.println();
		}
//		System.out.println(inspected);
//		System.out.println("move "+move+" = "+availableTokens +"=="+ inspected.size()+" "+(availableTokens == inspected.size()));
		return (availableTokens == inspected.size());
	}
}
