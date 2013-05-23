package com.haw.paletto.gui;

import javax.swing.*;

public class TokenButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3468209067687167196L;
	private boolean available = false;

	public TokenButton(){
		super("");
	}
	
	public TokenButton(String label){
		super(label);
	}
	
	public boolean setMoveable(){
		boolean result = false;
		if(this.isAvailable()){
			this.setEnabled(true);
			result = true;
		}
		return result;
	}
	
	public boolean setUnmoveable(){
		boolean result = false;
		if(this.isAvailable()){
			this.setEnabled(false);
		}
		return result;
	}
	
	public boolean remove(){
		boolean result = false;
		if(this.isAvailable()){
			this.available = true;			
		}
		return result;
	}
	
	public boolean isAvailable(){
		return this.available==true;
	}
}
