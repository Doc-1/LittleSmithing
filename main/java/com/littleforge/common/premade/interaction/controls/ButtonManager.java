package com.littleforge.common.premade.interaction.controls;

import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.structure.type.door.LittleDoor;
import com.littleforge.common.premade.interaction.controls.ButtonManager.State;

public class ButtonManager {
	
	public enum State {
		OPEN(0), CLOSED(1), JUSTCLOSED(3);
		public int state;
		
		private State(int state) {
			this.state = state;
		}
		
		@Override
		public String toString() {
			switch (this) {
			case OPEN:
				return "Open";
			case CLOSED:
				return "Closed";
			case JUSTCLOSED:
				return "Just Closed";
			default:
				break;
			}
			return "";
		}
	}
	
	public LittleDoor door;
	public State state = State.CLOSED;
	
	public int ticksPast = 0;
	public int tickDelay = 10;
	public boolean tickLocked = true;
	
	public ButtonManager(LittleDoor door) {
		this.door = door;
	}
	
	public ButtonManager(LittleDoor door, State state) {
		this.door = door;
		this.state = state;
	}
	

	/**
	 * Runs after x number of ticks have passed since pushing button.
	 */
	public void tickDelayed() {
		if(state.equals(State.OPEN)) {
			try {
				door.disableRightClick = false;
				door.activate(null, null, true);
			} catch (LittleActionException e) {
				e.printStackTrace();
			}finally {
				state = State.JUSTCLOSED;
			}
		}
		
	}
}

