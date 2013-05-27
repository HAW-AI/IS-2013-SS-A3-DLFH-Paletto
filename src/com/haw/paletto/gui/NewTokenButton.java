package com.haw.paletto.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

public class TokenButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3468209067687167196L;
	private Shape shape;

	public TokenButton() {
		super("");
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);
		setContentAreaFilled(false);
	}

	public TokenButton(String label) {
		super(label);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
	}

	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(Color.lightGray);
		} else {
			g.setColor(getBackground());
		}
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

		super.paintComponent(g);
	}

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(Color.black);
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}

	public void setState(Color color, boolean enabled) {
		this.setBackground(color);
		this.setEnabled(enabled);
	}
}
