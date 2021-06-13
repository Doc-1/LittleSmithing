
package com.littleforge.multitile.strucutres;

import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.common.strucutres.type.premade.ticking.TickingPremade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ClaySmelteryTickingMultiTilePremade extends TickingPremade {
	
	public ClaySmelteryTickingMultiTilePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock, 20, 13);
	}
	
	@Override
	public void onPremadeActivated(EntityPlayer playerIn, ItemStack heldItem) {
		// TODO Auto-generated method stub
		
	}
	
}
