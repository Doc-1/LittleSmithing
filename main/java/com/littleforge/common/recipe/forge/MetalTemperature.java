package com.littleforge.common.recipe.forge;

public enum MetalTemperature {
	
	NULL(0, 0, 0xffffffff, "null"),
	STRAW(200, 240, 0xfff3ae53, "straw"),
	BROWN(250, 260, 0xffad662e, "brown"),
	PURPLE(270, 290, 0xff925382, "purple"),
	BLUE(300, 410, 0xff7078a6, "blue"),
	GRAY(420, 600, 0xffdfb6b6, "gray"),
	RED(610, 700, 0xff70001a, "red"),
	BRIGHT_RED(710, 810, 0xffcb1922, "bright red"),
	ORANGE(820, 930, 0xfff55821, "orange"),
	BRIGHT_ORANGE(940, 980, 0xfff38321, "bright orange"),
	YELLOW(990, 1040, 0xfffab335, "yellow"),
	BRIGHT_YELLOW(1050, 1090, 0xfffff277, "bright yellow"),
	WHITE(1090, 1200, 0xfffffbd8, "white");
	
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
	
	public static MetalTemperature getEnumFromTemperature(int temperature) {
		if (temperature >= STRAW.getTemperatureMin() && temperature <= STRAW.getTemperatureMax()) {
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
		return NULL;
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