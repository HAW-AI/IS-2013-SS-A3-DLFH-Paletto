package com.haw.paletto.gui;

import java.awt.Color;

import javax.swing.*;


public class TokenButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3468209067687167196L;
	
	public TokenButton(){
		super("");
	}
	
	public TokenButton(String label){
		super(label);
		this.setFocusPainted(false);
		this.setBorderPainted(false);

	}
	
	public void setState(Color color, boolean enabled){
		this.setBackground(color);
		this.setEnabled(enabled);
	}
}
