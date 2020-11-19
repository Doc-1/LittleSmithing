package com.littleforge.common.premade.interaction;

import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

public abstract class PremadeInteractionTickingControl extends PremadeInteractionControl{
	
	protected int ticksPast = 0;
	protected int tickDelay;
	protected boolean tickLocked = true;
	
	public PremadeInteractionTickingControl(InteractivePremade premade, String id) {
		super(premade, id);
		ticking = true;
	}
	
	public void update() {
		premade.blockActivated = false;
		if(onRightClick()) {
			tickLocked = false;
		}
		if(!tickLocked) {
			if(ticksPast >= tickDelay) 
				ticksPast = 0;
			else 
				ticksPast++;
			if(ticksPast == tickDelay) {
				tickDelayed();
				ticksPast = 0;
				tickLocked = true;
			}
		}
	}
	
	public abstract void tickDelayed();
	
	@Override
	public void onActivated() {
		// TODO Auto-generated method stub
		
	}

}
