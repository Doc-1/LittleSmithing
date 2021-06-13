package com.littleforge.common.premade.interaction.actions;

import org.lwjgl.util.Color;

import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.nbt.NBTTagCompound;

public abstract class ChangeTileColor {
	
	public static LittleBox editArea;
	
	public static void forPremade(InteractivePremade premade, Color color) {
		for (LittleBox littleBox : premade.getTilePosList().keySet()) {
			if (LittleBox.intersectsWith(littleBox, editArea)) {
				LittleTile littleTile = premade.getTilePosList().get(littleBox);
				NBTTagCompound nbt = new NBTTagCompound();
				littleTile.saveTileExtra(nbt);
				nbt.setInteger("color", ColorUtils.RGBAToInt(color));
				littleTile.loadTileExtra(nbt);
			}
		}
	}
	
}
