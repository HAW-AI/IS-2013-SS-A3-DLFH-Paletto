package com.haw.paletto.logic;

import static java.awt.Color.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.haw.paletto.Token;

public class GameLogic {
	
	List<Color> colorList = Arrays.asList(red,blue,green,yellow,black,white,gray,orange,pink,magenta,lightGray,cyan,darkGray); 

	public GameLogic(){
		//TODO
	}
	
	public List<List<Token>> buildBoard(int size){
		List<List<Token>> result = new ArrayList<List<Token>>();
		if(size <= colorList.size()){
			List<Color> colors = getColors(size);
			colors = palettoShuffle(colors, size);
			for(int i=0; i< size;i++){
				List<Token> newList = new ArrayList<Token>(); 
				for(int j=0; j < size;j++){
					newList.add(new Token(colors.get((size*i)+j),i,j));
				}
				result.add(newList);
			}
		}
		
		return result;
	}
	
	private List<Color> getColors(int quantity){
		List<Color> result = new ArrayList<Color>();
		for(int i=0; i< quantity;i++){
			for(int j=0; j < quantity;j++){
				result.add(colorList.get(i));
			}
		}
		return result;
	}
	
	private List<Color> palettoShuffle(List<Color> colors, int size){
		boolean validOrder = false;
		
		while(!validOrder){
			validOrder = true;
			Collections.shuffle(colors);
			System.out.println(colors.size());
			for (int i = 0; (i < colors.size()); i++) {
				Color currentElem = colors.get(i);
				System.out.println(">>");
				
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

	public boolean gameWon(){
		//TODO
	}
	
	public List<Token> moveableTokens(List<List<Token>> tokens, int size){
		List<Token> result = new ArrayList<Token>();
		for(int i=0; i< size;i++){
			for(int j=0; j < size;j++){
				List<Boolean> moveableList = new ArrayList<Boolean>();
				Token currentElem = tokens.get(i).get(j);
				if((i==0 && j==0) || (i==0 && j==(size-1)) || (i==(size-1) && j==0) || (i==(size-1) && j==(size-1))){ //corners
					result.add(currentElem);
				} else if(i == 0){ //rest of first row
					moveableList.add(tokens.get(i).get(j-1).isMoveable());
					moveableList.add(tokens.get(i).get(j+1).isMoveable());
					moveableList.add(tokens.get(i+1).get(j).isMoveable());
					if(isTokenMoveable(moveableList)) result.add(currentElem);
				} else if(i == (size-1)){ //rest of last row
					moveableList.add(tokens.get(i).get(j-1).isMoveable());
					moveableList.add(tokens.get(i).get(j+1).isMoveable());
					moveableList.add(tokens.get(i-1).get(j).isMoveable());
					if(isTokenMoveable(moveableList)) result.add(currentElem);
				} else if(j == 0){ //first values of middle rows 
					moveableList.add(tokens.get(i).get(j+1).isMoveable());
					moveableList.add(tokens.get(i+1).get(j).isMoveable());
					moveableList.add(tokens.get(i-1).get(j).isMoveable());
					if(isTokenMoveable(moveableList)) result.add(currentElem);			
				} else if(j == (size-1)){ //last values of middle rows 
					moveableList.add(tokens.get(i).get(j-1).isMoveable());
					moveableList.add(tokens.get(i+1).get(j).isMoveable());
					moveableList.add(tokens.get(i-1).get(j).isMoveable());
					if(isTokenMoveable(moveableList)) result.add(currentElem);					
				} else { //rest of all rows between first and last
					moveableList.add(tokens.get(i).get(j-1).isMoveable());
					moveableList.add(tokens.get(i).get(j+1).isMoveable());
					moveableList.add(tokens.get(i+1).get(j).isMoveable());
					moveableList.add(tokens.get(i-1).get(j).isMoveable());
					if(isTokenMoveable(moveableList)) result.add(currentElem);
				}
			}
		}
		return result;
	}
	
	private boolean isTokenMoveable(List<Boolean> moveableList){
		boolean result = false;
		int count = 0;
		for(int i=0; (i < moveableList.size()) && !result ;i++){
			if(moveableList.get(i) == true){
				count++;
				if(count == 2) result = true;
			}
		}
		return result;
	}
}
