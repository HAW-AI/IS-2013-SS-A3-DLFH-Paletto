package com.haw.paletto;

import java.awt.*;

import javax.swing.UIManager;

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
	
	public Token(Color color, int xPos, int yPos, boolean available, boolean moveable){
		this.color = color;
		this.xPos = xPos;
		this.yPos = yPos;
		this.available = available;
		this.moveable = moveable;
	}
	
	public Color color(){
		return this.color;
	}
	
	public int xPos(){
		return this.xPos;
	}
	
	public int yPos(){
		return this.yPos;
	}
	
	public boolean isAvailable(){
		return this.available;
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
		this.color=UIManager.getColor("Button.background");
	}
	
	public void setMoveable(){
		this.moveable=true;
	}
	
	public void setUnmoveable(){
		this.moveable=false;
	}
	
	public Token clone(){
		return new Token(this.color, this.xPos, this.yPos, this.available, this.moveable);
	}
	
	@Override
	public String toString(){
		return "y"+this.yPos+" x"+this.xPos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (available ? 1231 : 1237);
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (moveable ? 1231 : 1237);
		result = prime * result + xPos;
		result = prime * result + yPos;
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
		Token other = (Token) obj;
		if (available != other.available)
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (moveable != other.moveable)
			return false;
		if (xPos != other.xPos)
			return false;
		if (yPos != other.yPos)
			return false;
		return true;
	}
}
	
	
