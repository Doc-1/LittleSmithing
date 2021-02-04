package com.littleforge.common.container;

import com.creativemd.creativecore.common.gui.container.SubContainer;
import com.littleforge.common.strucutres.type.premade.interactive.InteractiveAnvilPremade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.nbt.NBTTagCompound;

public class SubContainerAnvil extends SubContainer {
	
	private InteractiveAnvilPremade anvil;
	public final InventoryBasic slot = new InventoryBasic("result", false, 1);
	
	public SubContainerAnvil(EntityPlayer player, InteractiveAnvilPremade anvil) {
		super(player);
		
		this.anvil = anvil;
	}
	
	@Override
	public void createControls() {
		addPlayerSlotsToContainer(player, 0, 103);
	}
	
	@Override
	public void onPacketReceive(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		
	}
	
}
