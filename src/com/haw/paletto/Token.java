package com.haw.paletto;

import java.awt.*;

public class Token {
	private boolean available = false;
	private Color color;
	private int xPos;
	private int yPos;
	
	public Token(Color color, int xPos, int yPos){
		this.color = color;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int xPos(){
		return this.xPos;
	}
	
	public int yPos(){
		return this.yPos;
	}
	
	public boolean isAvailable(){
		return this.available==true;
	}

	public boolean isMovable() {
		boolean result = false;
		if(this.isAvailable()){
			//TODO
			result = true;
		}
		return result;
	}
}
