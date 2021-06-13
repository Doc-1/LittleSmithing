package com.littleforge.premade.structures;

import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class StoneAnvilStructurePremade extends InteractivePremade {
	
	public StoneAnvilStructurePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
	}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
	}
	
	@Override
	public void onPremadeActivated(EntityPlayer playerIn, ItemStack heldItem) {
		// TODO Auto-generated method stub
		
	}
	
}
