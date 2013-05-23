package com.haw.paletto;

import java.awt.*;

public class Token {
	private Color color;
	private int xPos;
	private int yPos;
	
	public Token(Color color){
		this.color = color;
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
	
	public boolean equals(Object obj){
		//TODO
	}
}
