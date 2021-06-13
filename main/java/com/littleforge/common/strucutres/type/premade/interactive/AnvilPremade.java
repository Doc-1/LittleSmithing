package com.littleforge.common.strucutres.type.premade.interactive;

import com.creativemd.littletiles.client.gui.handler.LittleStructureGuiHandler;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.LittleForge;
import com.littleforge.common.premade.interaction.actions.AddStructure;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AnvilPremade extends InteractivePremade {
	
	protected int recipeID = -1;
	
	public AnvilPremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		leftClickListener(true);
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
		if (nbt.hasKey("recipeID"))
			recipeID = nbt.getInteger("recipeID");
	}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
		nbt.setInteger("recipeID", recipeID);
	}
	
	@Override
	public void onLeftClickStructure(World world, EntityPlayer player, LittleStructure structure) {
		if (player.getHeldItemMainhand().getItem().equals(LittleForge.ballPeen)) {
			System.out.println(recipeID);
			player.getHeldItemMainhand().damageItem(1, player);
		}
	}
	
	public int getRecipeID() {
		return recipeID;
	}
	
	@Override
	public void onPremadeActivated(EntityPlayer playerIn, ItemStack heldItem) {
		if (playerIn.getHeldItemMainhand().getItem().equals(LittleForge.mushroomHorn)) {
			
			AddStructure.setPremadeID("mushroom_horn");
			AddStructure.editArea = new LittleBox(4, 19, 37, 0, 0, 0);
			AddStructure.toPremade(this, playerIn);
			
		}
		LittleStructureGuiHandler.openGui("anvil", new NBTTagCompound(), playerIn, this);
	}
}
