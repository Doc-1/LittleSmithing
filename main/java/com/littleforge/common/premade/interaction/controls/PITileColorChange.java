package com.littleforge.common.premade.interaction.controls;

import org.lwjgl.util.Color;

import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.littleforge.common.premade.interaction.PremadeInteractionControl;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.nbt.NBTTagCompound;

public class PITileColorChange extends PremadeInteractionControl{

	protected Color color;
	
	public PITileColorChange(InteractivePremade premade, String id, Color color, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(premade, id, minX, minY, minZ, maxX, maxY, maxZ);
		this.color = color;
		try {
			collectAllTiles();
		} catch (CorruptedConnectionException | NotYetConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onActivated() {
		changeTilesColor();
	}
	
	public void changeTilesColor() {
		for (LittleBox littleBox : tilePosList.keySet()) {
			if(LittleBox.intersectsWith(littleBox, editArea)) {
				LittleTile littleTile = tilePosList.get(littleBox);
				
				NBTTagCompound nbt = new NBTTagCompound();
		    	littleTile.saveTileExtra(nbt);
		    	nbt.setInteger("color", ColorUtils.RGBAToInt(color));
		    	littleTile.loadTileExtra(nbt);
			}
		}
	}



}
