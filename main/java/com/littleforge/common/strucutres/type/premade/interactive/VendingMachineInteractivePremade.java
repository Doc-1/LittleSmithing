package com.littleforge.common.strucutres.type.premade.interactive;

import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;

public class VendingMachineInteractivePremade extends InteractiveDoorPremade {
	
	public VendingMachineInteractivePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		this.setListOfDoors("b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8");
	}
	
}
