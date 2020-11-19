package com.littleforge.common.premade.interaction.controls;

import com.littleforge.common.premade.interaction.PremadeInteractionControl;
import com.littleforge.common.premade.interaction.PremadeInteractionTickingControl;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PITickingProgess extends PremadeInteractionTickingControl{

	public PITickingProgess(InteractivePremade premade, String id) {
		super(premade, id);
	}

	@Override
	public void onActivated() {
	}

	@Override
	public void tickDelayed() {
		// TODO Auto-generated method stub
		
	}

}
