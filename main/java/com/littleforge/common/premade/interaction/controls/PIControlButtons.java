package com.littleforge.common.premade.interaction.controls;

import java.util.ArrayList;
import java.util.HashMap;

import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.structure.IAnimatedStructure;
import com.creativemd.littletiles.common.structure.connection.StructureChildConnection;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.type.door.LittleAdvancedDoor;
import com.creativemd.littletiles.common.structure.type.door.LittleDoor;
import com.littleforge.common.premade.interaction.PremadeInteractionControl;
import com.littleforge.common.premade.interaction.PremadeInteractionTickingControl;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;
import com.littleforge.common.premade.interaction.controls.ButtonManager.State;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class PIControlButtons extends PremadeInteractionTickingControl{


	private HashMap<String, ButtonManager> doors = new HashMap<String, ButtonManager>();
	
	public PIControlButtons(InteractivePremade premade, String id, int tickDelay) {
		super(premade, id);
		this.tickDelay = tickDelay;
		ticking = true;
	}
	
	
	
	/**
	 * runs every tick
	 */
	@Override
	public void update() {
		updateDoorState();
		for(String doorName : doors.keySet()) {
			if(isDoorOpened(doorName)) {
				doors.get(doorName).tickLocked = false;
			}
			if(!doors.get(doorName).tickLocked) {
				if(doors.get(doorName).ticksPast >= doors.get(doorName).tickDelay) 
					doors.get(doorName).ticksPast = 0;
				else 
					doors.get(doorName).ticksPast++;
				if(doors.get(doorName).ticksPast == doors.get(doorName).tickDelay) {
					doors.get(doorName).tickDelayed();
					doors.get(doorName).ticksPast = 0;
					doors.get(doorName).tickLocked = true;
				}
			}
		}
		
	}
	
	public boolean isDoorOpened(String doorName) {
		if(doors.get(doorName).state.equals(State.OPEN)) 
			return true;
		return false;
	}
	
	/**
	 * Updates the state of the door.
	 */
	public void updateDoorState() {
		retrieveAdvancedDoors();
		for(String doorName : doors.keySet()) {
			
			if(doors.get(doorName).state.equals(State.CLOSED)) {
				getDoor(doorName).disableRightClick = false;
				if(getDoor(doorName).isInMotion()) {
					doors.get(doorName).state = State.OPEN;
					getDoor(doorName).disableRightClick = true;
					premade.updateStructure();
				}
			}
			if(doors.get(doorName).state.equals(State.JUSTCLOSED)) 
				if(!getDoor(doorName).isInMotion()) {
					doors.get(doorName).state = State.CLOSED;
					premade.updateStructure();
				}
		}
	}
	
	/**
	 * Gets the door based on the name provided
	 * @param doorName
	 * Name of the door that was assigned at creation
	 * @return
	 * the door that has the name
	 */
	public LittleDoor getDoor(String doorName) {
		if(doors.containsKey(doorName))
			return doors.get(doorName).door;
		return null;
	}
	
	/**
	 * Closes the door based on the name provided
	 * @param doorName
	 * Name of the door that was assigned at creation
	 */
	public void resetDoor(String doorName) {
		LittleDoor door = getDoor(doorName);
		try {
			door.activate(null, null, true);
		} catch (LittleActionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Stops the player from right clicking 
	 * @param doorName
	 * @param disabled
	 */
	public void disableDoor(String doorName, boolean disabled) {
	}
	
	/**
	 * Collects all advanced doors in the premade
	 */
	public void retrieveAdvancedDoors() {
		for(StructureChildConnection child :premade.getChildren()) {
			try {
				if(child.getStructure() instanceof LittleDoor) {
					LittleDoor door = (LittleDoor) child.getStructure();
					if(doors.isEmpty() || !doors.containsKey(door.name)) 
						doors.put(door.name, new ButtonManager(door));
					else {
						ButtonManager buttonManager = doors.get(door.name);
						buttonManager.door = door;
						doors.put(door.name, buttonManager);
					}
				}
			} catch (CorruptedConnectionException | NotYetConnectedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void tickDelayed() {
		
	}
	
	
	
}
