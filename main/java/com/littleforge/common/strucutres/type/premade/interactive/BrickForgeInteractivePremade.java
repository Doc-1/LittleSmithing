package com.littleforge.common.strucutres.type.premade.interactive;

import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.common.premade.interaction.actions.AddStructure;

import net.minecraft.item.ItemStack;

public class BrickForgeInteractivePremade extends InteractivePremade {
	
	public BrickForgeInteractivePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		seriesMaxium = 9;
	}
	
	@Override
	public void onPremadeActivated(ItemStack heldItem) {
		editArea = new LittleBox(64, 0, 0, 0, 0, 0);
		AddStructure.setPremadeID("brickForgeBasic");
		AddStructure.toPremade(this, false);
		//AddStructure.setPremadeID("exporter");
		//AddStructure.toPremade(this, false);
		
		//editArea = new LittleBox(0, 0, 0, 8, 8, 8);
		//BuildProgress.forPremade(this);
	}
}
