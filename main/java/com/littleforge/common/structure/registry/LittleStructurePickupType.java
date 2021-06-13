package com.littleforge.common.structure.registry;

import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructureTypePremade;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.tile.parent.StructureTileList;

import net.minecraft.item.ItemStack;

public class LittleStructurePickupType extends LittleStructureTypePremade {
	
	private ItemStack itemToPickup;
	
	public LittleStructurePickupType(String id, String modid, Class<? extends LittleStructure> structureClass, int attribute, ItemStack itemToPickup) {
		super(id, "premade", structureClass, attribute, modid);
		this.itemToPickup = itemToPickup;
	}
	
	@Override
	public LittleStructure createStructure(StructureTileList mainBlock) {
		try {
			return clazz.getConstructor(LittleStructureType.class, IStructureTileList.class, ItemStack.class).newInstance(this, mainBlock, itemToPickup);
		} catch (Exception e) {
			throw new RuntimeException("Invalid structure type " + id, e);
		}
	}
}
