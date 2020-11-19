package com.littleforge.common.strucutres.type.premade.interactive;

import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.common.premade.interaction.controls.PIControlButtons;

public class VendingMachineInteractivePremade extends InteractivePremade{

	public VendingMachineInteractivePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		
	}

	@Override
	public void createControls() {
		PIControlButtons buttons = new PIControlButtons(this, "vendingButtons", 30);
		controls.add(buttons);
	}

}
