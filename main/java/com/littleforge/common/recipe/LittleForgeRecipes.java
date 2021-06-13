package com.littleforge.common.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.creativecore.common.utils.stack.InfoStack;
import com.creativemd.littletiles.common.action.LittleAction;
import com.creativemd.littletiles.common.packet.LittleActionMessagePacket;
import com.creativemd.littletiles.common.util.ingredient.LittleIngredients;
import com.creativemd.littletiles.common.util.ingredient.LittleInventory;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException;
import com.creativemd.littletiles.common.util.ingredient.StackIngredient;
import com.creativemd.littletiles.common.util.tooltip.ActionMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

public abstract class LittleForgeRecipes {
	
	public static ItemStack getResults() {
		return null;
	}
	
	public static ItemStack getResult() {
		//stacks.add(new StackIngredientEntry(ingredient, 1));
		
		return null;
	}
	
	public static boolean takeIngredients(EntityPlayer playerIn, String id, int seriesAt, Map<String, List<ItemStack>> recipes) {
		LittleIngredients ingredients = new LittleIngredients(getIngredientsFromConfig(id, seriesAt, recipes));
		LittleInventory inventory = new LittleInventory(playerIn);
		try {
			LittleAction.checkAndTake(playerIn, inventory, ingredients);
			return true;
		} catch (NotEnoughIngredientsException e) {
			ActionMessage message = e.getActionMessage();
			if (message != null)
				PacketHandler.sendPacketToPlayer(new LittleActionMessagePacket(e.getActionMessage()), (EntityPlayerMP) playerIn);
			else
				playerIn.sendStatusMessage(new TextComponentString(e.getLocalizedMessage()), true);
			return false;
		}
	}
	
	public static StackIngredient getIngredientsFromConfig(String id, int seriesAt, Map<String, List<ItemStack>> recipes) {
		int nextSeries = seriesAt;
		String nextSeriesName = id + "_" + nextSeries;
		if (recipes.containsKey(nextSeriesName)) {
			List<ItemStack> itemStackList = recipes.get(nextSeriesName);
			return new StackIngredient(itemStackList);
		}
		return null;
	}
	
	public static void registerRecipeFromConfig(Map<String, List<ItemStack>> structureIngredientList, List<InfoStack> infoStack, String id, ItemStack... stack) {
		List<ItemStack> itemStacks = new ArrayList<ItemStack>();
		for (ItemStack itemStack : stack) {
			infoStack.add(InfoStack.parseObject(itemStack));
			itemStacks.add(itemStack);
		}
		structureIngredientList.put(id, itemStacks);
	}
}