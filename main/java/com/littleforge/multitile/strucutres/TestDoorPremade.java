package com.littleforge.multitile.strucutres;

import java.util.ArrayList;

import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.structure.connection.StructureChildConnection;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.door.LittleAdvancedDoor;
import com.creativemd.littletiles.common.structure.type.door.LittleDoor.DoorActivator;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;

import net.minecraft.nbt.NBTTagCompound;

public class TestDoorPremade extends LittleStructurePremade {
	
	private int tick = 0;
	private ArrayList<LittleAdvancedDoor> advancedDoors = new ArrayList<LittleAdvancedDoor>();
	
	public TestDoorPremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
		tick = nbt.getInteger("tick");
	}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
		nbt.setInteger("tick", tick);
	}
	
	@Override
	public void tick() {
		if (getWorld().isRemote)
			return;
		retrieveAdvancedDoors();
		for (LittleAdvancedDoor advDoor : advancedDoors) {
			if (advDoor.isAnimated()) {
				try {
					advDoor.activate(DoorActivator.COMMAND, null, null, true);
				} catch (LittleActionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void retrieveAdvancedDoors() {
		for (StructureChildConnection child : this.getChildren()) {
			try {
				if (child.getStructure() instanceof LittleAdvancedDoor) {
					LittleAdvancedDoor advDoor = (LittleAdvancedDoor) child.getStructure();
					advancedDoors.add(advDoor);
				}
			} catch (CorruptedConnectionException | NotYetConnectedException e) {
				e.printStackTrace();
			}
		}
	}
}
