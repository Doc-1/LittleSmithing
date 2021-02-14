package com.littleforge.common.recipe.forge;

import java.util.ArrayList;
import java.util.List;

import com.creativemd.creativecore.common.utils.mc.InventoryUtils;
import com.creativemd.creativecore.common.utils.stack.InfoItemStack;
import com.creativemd.creativecore.common.utils.stack.InfoStack;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException;
import com.littleforge.LittleForge;

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
		registerRecipe(new LittleAnvilRecipe(new ItemStack(Items.IRON_INGOT), 10, MetalTemperature.RED, new InfoItemStack(new ItemStack(LittleForge.tongs, 1, 0), 1)));
	}
}
