package com.littleforge.multitile.registry;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.littletiles.client.LittleTilesClient;
import com.creativemd.littletiles.common.action.LittleAction;
import com.creativemd.littletiles.common.packet.LittleActionMessagePacket;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.util.ingredient.LittleIngredients;
import com.creativemd.littletiles.common.util.ingredient.LittleInventory;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException;
import com.creativemd.littletiles.common.util.ingredient.StackIngredient;
import com.creativemd.littletiles.common.util.ingredient.StackIngredientEntry;
import com.creativemd.littletiles.common.util.tooltip.ActionMessage;
import com.littleforge.LittleForge;
import com.littleforge.multitile.strucutres.MultiTileDummyStructure;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

public abstract class MultiTileRecipeRegistry {

	private static HashMap<String, ItemStack> recipeDict = new HashMap<>();
	private static HashMap<ItemStack, Integer> inventoryDict = new HashMap<>();
	
	/**
	 * @param id
	 * ID of the premade structure
	 * @param ingredient
	 * Item that will be used to craft the next Structure in the sequence
	 * @param count
	 * How many of said item to be used 
	 */
	public static void addRecipe(String id, Item ingredient, int count) {
		recipeDict.put(id, new ItemStack(ingredient,count));
	}
	
	/**
	 * @param id
	 * ID of the premade structure
	 * @param ingredient
	 * Block that will be used to craft the next Structure in the sequence
	 * @param count
	 * How many of said item to be used 
	 */
	public static void addRecipe(String id, Block ingredient, int count) {
		recipeDict.put(id, new ItemStack(ingredient,count));
	}
	
	public static ItemStack findRecipe(String id) {
		Set set = recipeDict.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			if(mentry.getKey().equals(id)) {
				return (ItemStack) mentry.getValue();
			}
		}
		return null;
	}
	
	public static boolean takeIngredients(EntityPlayer playerIn, LittleStructureType type) {
		if (!playerIn.world.isRemote) {
			ItemStack ingredient = MultiTileRecipeRegistry.findRecipe(type.id);
			
			StackIngredient stacks = new StackIngredient();
			stacks.add(new StackIngredientEntry(ingredient, ingredient.getCount()));
			
			LittleIngredients ingredients = new LittleIngredients(stacks);
			LittleInventory inventory = new LittleInventory(playerIn);
			
			try {
	            LittleAction.checkAndTake(playerIn, inventory, ingredients);
	        } catch (NotEnoughIngredientsException e) {
	            ActionMessage message = e.getActionMessage();
	            if (message != null) 
	            	PacketHandler.sendPacketToPlayer(new LittleActionMessagePacket(e.getActionMessage()), (EntityPlayerMP) playerIn);
	            else
	                playerIn.sendStatusMessage(new TextComponentString(e.getLocalizedMessage()), true);
	            return false;
	        }
		}
		return true;
	}
	
}