package com.littleforge.common.recipe.forge;

import java.util.ArrayList;
import java.util.List;

import com.creativemd.creativecore.common.utils.mc.InventoryUtils;
import com.creativemd.creativecore.common.utils.stack.InfoOre;
import com.creativemd.creativecore.common.utils.stack.InfoStack;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class LittleAnvilRecipe {
	
	private static List<LittleAnvilRecipe> recipes = new ArrayList<>();
	
	public static void registerRecipe(LittleAnvilRecipe recipe) {
		recipes.add(recipe);
	}
	
	public static List<LittleAnvilRecipe> matchingRecipes(IInventory inventory) {
		List<LittleAnvilRecipe> results = new ArrayList<>();
		for (LittleAnvilRecipe recipe : recipes)
			if (recipe.canComsume(inventory))
				results.add(recipe);
		return results;
	}
	
	public static LittleAnvilRecipe getRecipe(int id) {
		return recipes.get(id);
	}
	
	public enum MetalTemperature {
		
		STRAW(200, 240, 0xfff3ae53),
		BROWN(250, 260, 0xffad662e),
		PURPLE(270, 290, 0xff925382),
		BLUE(300, 410, 0xff7078a6),
		GRAY(420, 600, 0xffdfb6b6),
		RED(610, 700, 0xff70001a),
		BRIGHT_RED(710, 810, 0xffcb1922),
		ORANGE(820, 930, 0xfff55821),
		BRIGHT_ORANGE(940, 980, 0xfff38321),
		YELLOW(990, 1040, 0xfffab335),
		BRIGHT_YELLOW(1050, 1090, 0xfffff277),
		WHITE(1090, 1200, 0xfffffbd8);
		
		private int temperatureMin;
		private int temperatureMax;
		private int color;
		
		MetalTemperature(final int temperatureMin, final int temperatureMax, final int color) {
			this.temperatureMin = temperatureMin;
			this.temperatureMax = temperatureMax;
			this.color = color;
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
		
		@Override
		public String toString() {
			return "Min = " + temperatureMin + ", Max = " + temperatureMax;
		}
	}
	
	private int id;
	public final InfoStack[] ingredients;
	public final ItemStack result;
	public final MetalTemperature temperature;
	public final int hits;
	
	public LittleAnvilRecipe(ItemStack result, int hits, MetalTemperature temperature, InfoStack... ingredients) {
		this.ingredients = ingredients;
		this.result = result;
		this.hits = hits;
		this.temperature = temperature;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean canComsume(IInventory inventory) {
		InventoryBasic simulation = new InventoryBasic("test", false, inventory.getSizeInventory());
		for (int i = 0; i < simulation.getSizeInventory(); i++)
			simulation.setInventorySlotContents(i, inventory.getStackInSlot(i).copy());
		try {
			consume(simulation);
			return true;
		} catch (LittleActionException e) {
		}
		return false;
	}
	
	public void consume(IInventory inventory) throws LittleActionException {
		for (InfoStack info : ingredients)
			if (InventoryUtils.consumeInfoStack(info, inventory) > 0)
				throw new NotEnoughIngredientsException(info.getItemStack());
	}
	
	static {
		registerRecipe(new LittleAnvilRecipe(new ItemStack(Items.IRON_INGOT), 10, MetalTemperature.RED, new InfoOre("goldIngot")));
	}
}
