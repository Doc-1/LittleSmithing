package com.littleforge.common.recipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.creativemd.creativecore.common.gui.controls.gui.custom.GuiStackSelectorAll.StackCollector;
import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.littletiles.common.action.LittleAction;
import com.creativemd.littletiles.common.packet.LittleActionMessagePacket;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.util.ingredient.LittleIngredients;
import com.creativemd.littletiles.common.util.ingredient.LittleInventory;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException;
import com.creativemd.littletiles.common.util.ingredient.StackIngredient;
import com.creativemd.littletiles.common.util.ingredient.StackIngredientEntry;
import com.creativemd.littletiles.common.util.tooltip.ActionMessage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.littleforge.LittleForge;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class LittleForgeRecipes {
	
	private static HashMap<String, Recipie> recipe = new HashMap<String, Recipie>();
	private static List<String> list = new ArrayList<String>();
	
	public static ItemStack getResults() {
		return null;
	}
	
	public static ItemStack getResult() {
		//stacks.add(new StackIngredientEntry(ingredient, 1));

		return null;
	}

	public static boolean takeIngredients(EntityPlayer playerIn, String id) {
		return false;
		/*
		LittleIngredients ingredients = new LittleIngredients(stacks);
		LittleInventory inventory = new LittleInventory(playerIn);
		try {
			LittleAction.canTake(playerIn, inventory, ingredients);
			return true;
        } catch (NotEnoughIngredientsException e) {
            ActionMessage message = e.getActionMessage();
            if (message != null) 
            	PacketHandler.sendPacketToPlayer(new LittleActionMessagePacket(e.getActionMessage()), (EntityPlayerMP) playerIn);
            else
                playerIn.sendStatusMessage(new TextComponentString(e.getLocalizedMessage()), true);
            return false;
        }
        */
	}
	
	protected static class Recipie {
		protected static LittleIngredients allIngredients;
		protected static ItemStack result;
	}
	
}