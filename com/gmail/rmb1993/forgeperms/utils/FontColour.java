package com.gmail.rmb1993.forgeperms.utils;

public enum FontColour {
	
	BLACK("0"), 
	DARK_BLUE("1"), 
	DARK_GREEN("2"), 
	DARK_AQUA("3"), 
	DARK_RED("4"), 
	PURPLE("5"), 
	ORANGE("6"), 
	GREY("7"), 
	DARK_GREY("8"), 
	BLUE("9"), 
	GREEN("a"), 
	AQUA("b"), 
	RED("c"), 
	PINK("d"), 
	YELLOW("e"), 
	WHITE("f");
	
	private final String value;
	private final String characterValue = "\u00a7";
	
	private FontColour(String value) {
		this.value = characterValue + value;
	}
	
	public String toString() {
		return this.value;
	}

}
