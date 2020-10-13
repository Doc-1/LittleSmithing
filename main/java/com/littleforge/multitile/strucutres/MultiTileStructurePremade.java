package com.littleforge.multitile.strucutres;

import com.creativemd.creativecore.common.utils.math.Rotation;
import com.creativemd.littletiles.common.structure.attribute.LittleStructureAttribute;
import com.creativemd.littletiles.common.structure.directional.StructureDirectional;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;
import com.littleforge.common.recipe.LittleForgeRecipes;

import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public abstract class MultiTileStructurePremade extends LittleStructurePremade{
	
	protected int seriesIndex;
	protected int seriesMax;
	protected String seriesName = type.id.toString().split("_")[0];
	

	public MultiTileStructurePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}

	@StructureDirectional
	public EnumFacing direction;
	
	public LittlePreviews updateStructureDirection(LittlePreviews previews, SurroundingBox box, BlockPos min) {
		switch (direction) {
		case NORTH:
			previews.rotatePreviews(Rotation.Y_CLOCKWISE, box.getAbsoluteBox().getDoubledCenter(min));
			break;
		case SOUTH:
			previews.rotatePreviews(Rotation.Y_COUNTER_CLOCKWISE, box.getAbsoluteBox().getDoubledCenter(min));
			break;
		case WEST:
			previews.rotatePreviews(Rotation.Y_CLOCKWISE, box.getAbsoluteBox().getDoubledCenter(min));
			previews.rotatePreviews(Rotation.Y_CLOCKWISE, box.getAbsoluteBox().getDoubledCenter(min));
			break;
		default:
			break;
		}
		return previews;
	}

	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
	}

	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
	}
	
	protected String nextSeries() {
		
		int seriesAt = Integer.parseInt(type.id.toString().split("_")[1]);
		if(seriesIndex > seriesAt) {
			return seriesName + "_" + (seriesAt+1);
		}
		return "";
	}
	
	public static void registerPremadeStructureType(String id, String modid, Class<? extends LittleStructurePremade> classStructure) {
		registerPremadeStructureType(id, modid, classStructure, LittleStructureAttribute.NONE);
	}
}
