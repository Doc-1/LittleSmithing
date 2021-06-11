package com.littleforge.common.recipe.forge;

public enum MetalTemperature {
	
	BLACK(0, 199, 0x11111111, "null"),
	STRAW(200, 240, 0xfff3ae53, "straw"),
	BROWN(241, 260, 0xffad662e, "brown"),
	PURPLE(261, 290, 0xff925382, "purple"),
	BLUE(291, 410, 0xff7078a6, "blue"),
	GRAY(411, 600, 0xffdfb6b6, "gray"),
	RED(601, 700, 0xff70001a, "red"),
	BRIGHT_RED(701, 810, 0xffcb1922, "bright red"),
	ORANGE(811, 930, 0xfff55821, "orange"),
	BRIGHT_ORANGE(931, 980, 0xfff38321, "bright orange"),
	YELLOW(981, 1040, 0xfffab335, "yellow"),
	BRIGHT_YELLOW(1041, 1090, 0xfffff277, "bright yellow"),
	WHITE(1091, 1200, 0xfffffbd8, "white");
	// 261
	//-291
	//----
	//  30
	
	// 270
	//-261
	//----
	//   9
	
	private int temperatureMin;
	private int temperatureMax;
	private int color;
	private String colorName;
	
	MetalTemperature(final int temperatureMin, final int temperatureMax, final int color, final String colorName) {
		this.temperatureMin = temperatureMin;
		this.temperatureMax = temperatureMax;
		this.colorName = colorName;
		this.color = color;
	}
	
	public MetalTemperature getNext() {
		if (this == STRAW) {
			return BROWN;
		} else if (this == BROWN) {
			return PURPLE;
		} else if (this == PURPLE) {
			return BLUE;
		} else if (this == BLUE) {
			return GRAY;
		} else if (this == GRAY) {
			return RED;
		} else if (this == RED) {
			return BRIGHT_RED;
		} else if (this == BRIGHT_RED) {
			return ORANGE;
		} else if (this == ORANGE) {
			return BRIGHT_ORANGE;
		} else if (this == BRIGHT_ORANGE) {
			return YELLOW;
		} else if (this == YELLOW) {
			return BRIGHT_YELLOW;
		} else if (this == BRIGHT_YELLOW) {
			return WHITE;
		} else if (this == WHITE) {
			return WHITE;
		} else if (this == BLACK) {
			return STRAW;
		}
		return null;
	}
	
	public static MetalTemperature getEnumFromTemperature(int temperature) {
		if (temperature >= BLACK.getTemperatureMin() && temperature <= BLACK.getTemperatureMax()) {
			return BLACK;
		} else if (temperature >= STRAW.getTemperatureMin() && temperature <= STRAW.getTemperatureMax()) {
			return STRAW;
		} else if (temperature >= BROWN.getTemperatureMin() && temperature <= BROWN.getTemperatureMax()) {
			return BROWN;
		} else if (temperature >= PURPLE.getTemperatureMin() && temperature <= PURPLE.getTemperatureMax()) {
			return PURPLE;
			
		} else if (temperature >= BLUE.getTemperatureMin() && temperature <= BLUE.getTemperatureMax()) {
			return BLUE;
			
		} else if (temperature >= GRAY.getTemperatureMin() && temperature <= GRAY.getTemperatureMax()) {
			return GRAY;
			
		} else if (temperature >= RED.getTemperatureMin() && temperature <= RED.getTemperatureMax()) {
			return RED;
			
		} else if (temperature >= BRIGHT_RED.getTemperatureMin() && temperature <= BRIGHT_RED.getTemperatureMax()) {
			return BRIGHT_RED;
			
		} else if (temperature >= ORANGE.getTemperatureMin() && temperature <= ORANGE.getTemperatureMax()) {
			return ORANGE;
			
		} else if (temperature >= BRIGHT_ORANGE.getTemperatureMin() && temperature <= BRIGHT_ORANGE.getTemperatureMax()) {
			return BRIGHT_ORANGE;
			
		} else if (temperature >= YELLOW.getTemperatureMin() && temperature <= YELLOW.getTemperatureMax()) {
			return YELLOW;
			
		} else if (temperature >= BRIGHT_YELLOW.getTemperatureMin() && temperature <= BRIGHT_YELLOW.getTemperatureMax()) {
			return BRIGHT_YELLOW;
			
		} else if (temperature >= WHITE.getTemperatureMin() && temperature <= WHITE.getTemperatureMax()) {
			return WHITE;
		}
		return null;
	}
	
	public int getTemperatureMax() {
		return this.temperatureMax;
	}
	
	public int getTemperatureMin() {
		return this.temperatureMin;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public String getColorName() {
		return colorName;
	}
	
	@Override
	public String toString() {
		return "Min = " + temperatureMin + ", Max = " + temperatureMax;
	}
}