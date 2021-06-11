package com.littleforge.common.recipe.forge;

import java.util.ArrayList;
import java.util.List;

import com.creativemd.creativecore.common.utils.mc.InventoryUtils;
import com.creativemd.creativecore.common.utils.stack.InfoStack;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LittleAnvilRecipe {
	
	private static List<LittleAnvilRecipe> recipes = new ArrayList<>();
	
	public static void registerRecipe(LittleAnvilRecipe recipe) {
		recipes.add(recipe);
	}
	
	public static List<LittleAnvilRecipe> matchingRecipes(IInventory inventory, Item hardyTool) {
		List<LittleAnvilRecipe> results = new ArrayList<>();
		for (LittleAnvilRecipe recipe : recipes)
			if (recipe.canComsume(inventory, hardyTool))
				results.add(recipe);
		return results;
	}
	
	public static LittleAnvilRecipe getRecipe(int id) {
		return recipes.get(id);
	}
	
	private int id;
	public final InfoStack[] ingredients;
	public final ItemStack result;
	public final MetalTemperature temperature;
	public final int hits;
	public final Item hardyTool;
	
	public LittleAnvilRecipe(ItemStack result, int hits, MetalTemperature temperature, Item hardyTool, InfoStack... ingredients) {
		this.hardyTool = hardyTool;
		this.ingredients = ingredients;
		this.result = result;
		this.hits = hits;
		this.temperature = temperature;
		id = recipes.size();
		//System.out.println("\n\n\n\n\n" + this.id);
	}
	
	public int getId() {
		return id;
	}
	
	public boolean canComsume(IInventory inventory, Item hardyTool) {
		InventoryBasic simulation = new InventoryBasic("comsumeTest", false, inventory.getSizeInventory());
		for (int i = 0; i < simulation.getSizeInventory(); i++) {
			simulation.setInventorySlotContents(i, inventory.getStackInSlot(i).copy());
		}
		try {
			consume(simulation, hardyTool);
			return true;
		} catch (LittleActionException e) {
		}
		return false;
	}
	
	public void consume(IInventory inventory, Item hardyTool) throws LittleActionException {
		for (InfoStack info : ingredients) {
			if (InventoryUtils.consumeInfoStack(info, inventory) > 0)
				throw new NotEnoughIngredientsException(info.getItemStack());
			if (this.hardyTool != null)
				if (!hardyTool.equals(this.hardyTool))
					throw new NotEnoughIngredientsException(info.getItemStack());
		}
	}
	
}
