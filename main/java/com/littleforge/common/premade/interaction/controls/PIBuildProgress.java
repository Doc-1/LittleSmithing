package com.littleforge.common.premade.interaction.controls;

import org.lwjgl.util.Color;

import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.nbt.NBTTagCompound;

public class PIBuildProgress extends PITileColorChange{

	public PIBuildProgress(InteractivePremade premade, String id, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(premade, id, null, minX, minY, minZ, maxX, maxY, maxZ);
	}

	/**
	 * Removes Alpha color from the premade structure. 
	 */
	@Override
	public void changeTilesColor() {
		for (LittleBox littleBox : tilePosList.keySet()) {
			if(LittleBox.intersectsWith(littleBox, editArea)) {
				LittleTile littleTile = tilePosList.get(littleBox);
				
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
