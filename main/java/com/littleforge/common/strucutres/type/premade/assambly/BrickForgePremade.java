package com.littleforge.common.strucutres.type.premade.assambly;

import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.littletiles.common.action.LittleAction;
import com.creativemd.littletiles.common.packet.LittleActionMessagePacket;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.util.ingredient.LittleIngredients;
import com.creativemd.littletiles.common.util.ingredient.LittleInventory;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException;
import com.creativemd.littletiles.common.util.ingredient.StackIngredient;
import com.creativemd.littletiles.common.util.tooltip.ActionMessage;
import com.littleforge.LittleSmithingConfig;
import com.littleforge.common.packet.PacketUpdateStructureFromClient;
import com.littleforge.common.premade.interaction.actions.AddStructure;
import com.littleforge.common.premade.interaction.actions.BuildProgress;
import com.littleforge.common.recipe.LittleForgeRecipes;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;

public class BrickForgePremade extends InteractivePremade {
	
	public BrickForgePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		assemblyRecipe = LittleSmithingConfig.brickForgeRecipe.brickForgeBasic;
		seriesMaxium = assemblyRecipe.size();
	}
	
	@Override
	public void onPremadeActivated(EntityPlayer playerIn, ItemStack heldItem) {
		
		if (seriesAt <= seriesMaxium) {
			if (LittleForgeRecipes.takeIngredients(playerIn, this.type.id, seriesAt, assemblyRecipe)) {
				BuildProgress.editArea = new LittleBox(0, 0, 0, 34, (int) (9.6 * (seriesAt)), 16);
				BuildProgress.forPremade(this);
				NBTTagCompound nbt = new NBTTagCompound();
				this.writeToNBT(nbt);
				this.seriesAt++;
				nbt.setInteger("at", seriesAt);
				PacketHandler.sendPacketToServer(new PacketUpdateStructureFromClient(this.getStructureLocation(), nbt));
			}
		} else if (canTakeFuel(playerIn)) {
			AddStructure.editArea = new LittleBox(0, 12, 3, 0, 0, 0);
			AddStructure.setPremadeID("brick_forge_coal");
			if (AddStructure.toPremade(this, playerIn))
				takeFuel(playerIn);
		}
		
		//editArea = new LittleBox(0, 0, 0, 34, 8, 16);
		//BuildProgress.forPremade(this);
	}
	
	public boolean canTakeFuel(EntityPlayer playerIn) {
		try {
			LittleIngredients ingredients = new LittleIngredients();
			StackIngredient ingredient = new StackIngredient(new ItemStack(Items.COAL, 5));
			ingredients.add(ingredient);
			LittleInventory inventory = new LittleInventory(playerIn);
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
	}
	
	public void takeFuel(EntityPlayer playerIn) {
		try {
			LittleIngredients ingredients = new LittleIngredients();
			StackIngredient ingredient = new StackIngredient(new ItemStack(Items.COAL, 5));
			ingredients.add(ingredient);
			LittleInventory inventory = new LittleInventory(playerIn);
			LittleAction.checkAndTake(playerIn, inventory, ingredients);
		} catch (NotEnoughIngredientsException e) {
			ActionMessage message = e.getActionMessage();
			if (message != null)
				PacketHandler.sendPacketToPlayer(new LittleActionMessagePacket(e.getActionMessage()), (EntityPlayerMP) playerIn);
			else
				playerIn.sendStatusMessage(new TextComponentString(e.getLocalizedMessage()), true);
		}
	}
	
}
