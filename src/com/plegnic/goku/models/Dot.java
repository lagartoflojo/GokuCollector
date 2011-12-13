package com.plegnic.goku.models;

public final class Dot {
	
	private final float x, y;
	private final int color, diameter;
	
	public Dot(float x, float y, int color, int diameter) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.diameter = diameter;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getDiameter() {
		return diameter;
	}

}
