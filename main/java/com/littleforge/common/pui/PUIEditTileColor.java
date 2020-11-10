package com.littleforge.common.pui;

import org.lwjgl.util.Color;

import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.nbt.NBTTagCompound;

public class PUIEditTileColor extends PUIControl{

	public PUIEditTileColor(InteractivePremade premade) {
		super(premade);
	}

	/***
	 * Removes Alpha color from the premade structure. 
	 */
	public void removeAlpha() {
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
	
	public void editTilesColor(int r, int g, int b, int a) {
		for (LittleTile littleTile : tileList) {
			int color = ColorUtils.RGBAToInt(r, g, b, a);
			NBTTagCompound nbt = new NBTTagCompound();
	    	littleTile.saveTileExtra(nbt);
	    	nbt.setInteger("color", color);
	    	littleTile.loadTileExtra(nbt);
		}
	}
}
