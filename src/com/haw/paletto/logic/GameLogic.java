package com.haw.paletto.logic;

import static java.awt.Color.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.haw.paletto.Token;

public class GameLogic {
	
	List<Color> colorList = Arrays.asList(red,blue,green,yellow,black,white,gray,orange,pink,magenta,lightGray,cyan,darkGray); 

	public GameLogic(){
		//TODO
	}
	
	public List<List<Token>> buildBoard(int size){
		List<List<Token>> tokens = new ArrayList<List<Token>>();
		if(size > colorList.size()){
			List<Color> colors = getColors(size);
		}
		return tokens;
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
	
	public boolean gameWon(){
		//TODO
	}
	
	public List<Token> moveableTokens(){
		//TODO
	}
}
