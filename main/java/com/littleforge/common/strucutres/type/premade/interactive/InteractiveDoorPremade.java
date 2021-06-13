package com.littleforge.common.strucutres.type.premade.interactive;

import java.util.HashMap;

import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.structure.connection.StructureChildConnection;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.door.LittleDoor.DoorActivator;
import com.creativemd.littletiles.common.structure.type.door.LittleDoorBase;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InteractiveDoorPremade extends InteractivePremade {
	
	public HashMap<String, Boolean> whatDoorIsOpen = new HashMap<String, Boolean>(); //Closed state is default state
	
	public InteractiveDoorPremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}
	
	@Override
	public void tick() {
		if (getWorld().isRemote)
			return;
		if (whatDoorIsOpen.get("b1")) {
			System.out.println(this.mainBlock.getPos());
			whatDoorIsOpen.replace("b1", false);
		}
		
		for (String doorName : whatDoorIsOpen.keySet()) {
			try {
				LittleDoorBase door = getDoorNamed(doorName);
				if (door != null && !door.isInMotion() && door.isAnimated()) {
					door.activate(DoorActivator.COMMAND, null, null, true);
					whatDoorIsOpen.replace(doorName, true);
				}
			} catch (LittleActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/*
	 * 
	 */
	
	/** Closed state is default state
	 * 
	 * @param doors
	 *            List of the door's names that are to be in the premade structure */
	public void setListOfDoors(String... doors) {
		for (String door : doors) {
			whatDoorIsOpen.put(door, false);
		}
	}
	
	public boolean isDoorOpen(String doorName) {
		for (StructureChildConnection child : this.getChildren()) {
			try {
				if (child.getStructure().name.equals(doorName)) {
					return ((LittleDoorBase) child.getStructure()).isAnimated();
				}
			} catch (CorruptedConnectionException | NotYetConnectedException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public LittleDoorBase getDoorNamed(String doorName) {
		
		for (StructureChildConnection child : this.getChildren()) {
			try {
				//System.out.println(child.getStructure().name);
				if (child.getStructure().name.equals(doorName)) {
					return (LittleDoorBase) child.getStructure();
				}
			} catch (CorruptedConnectionException | NotYetConnectedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public void onPremadeActivated(EntityPlayer playerIn, ItemStack heldItem) {
		// TODO Auto-generated method stub
		
	}
	
}
