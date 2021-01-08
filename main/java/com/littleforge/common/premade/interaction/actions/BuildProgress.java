package com.littleforge.common.premade.interaction.actions;

import org.lwjgl.util.Color;

import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.nbt.NBTTagCompound;

public class BuildProgress {

	public static void forPremade(InteractivePremade premade) {
		for (LittleBox littleBox : premade.getTilePosList().keySet()) {
			if(LittleBox.intersectsWith(littleBox, premade.getEditArea())) {
				LittleTile littleTile = premade.getTilePosList().get(littleBox);
				
				NBTTagCompound nbt = new NBTTagCompound();
		    	littleTile.saveTileExtra(nbt);
		    	
		    	int color = ColorUtils.WHITE;
		    	if(nbt.hasKey("color")) {
		    		color = nbt.getInteger("color");
		    		Color c = ColorUtils.IntToRGBA(color);
		    		c.setAlpha(255);
		    		color = ColorUtils.RGBAToInt(c);
		    	}
		    	nbt.setInteger("color", color);
		    	littleTile.loadTileExtra(nbt);
			}
		}
	}
}
