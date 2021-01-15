package com.littleforge.common.strucutres.type.premade.interactive;

import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.common.premade.interaction.actions.AddStructure;

import net.minecraft.item.ItemStack;

public class BrickForgeInteractivePremade extends InteractivePremade {
	
	public BrickForgeInteractivePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}
	
	@Override
	public void onPremadeActivated(ItemStack heldItem) {
		editArea = new LittleBox(2, 16, 1, 0, 0, 0);
		AddStructure.toPremade(this, false);
		
		//editArea = new LittleBox(0, 0, 0, 8, 8, 8);
		//BuildProgress.forPremade(this);
	}
}
