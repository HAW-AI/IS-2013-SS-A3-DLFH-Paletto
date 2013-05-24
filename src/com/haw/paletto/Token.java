package com.haw.paletto;

import java.awt.*;

public class Token {
	private boolean available = true;
	private boolean moveable = false;
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

	public boolean isMoveable() {
		boolean result = false;
		if(this.isAvailable() && this.moveable==true){
			result = true;
		}
		return result;
	}
	
	public void setUnavailable(){
		this.available=false;
	}
	
	public void setMoveable(){
		this.moveable=true;
	}
	
	public void setUnmoveable(){
		this.moveable=false;
	}
}
