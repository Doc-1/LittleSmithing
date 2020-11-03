package com.littleforge.multitile.strucutres;

import java.util.ArrayList;
import java.util.List;

import com.creativemd.creativecore.common.utils.math.Rotation;
import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.littletiles.LittleTiles;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.attribute.LittleStructureAttribute;
import com.creativemd.littletiles.common.structure.directional.StructureDirectional;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.relative.StructureAbsolute;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructureTypePremade;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleAbsoluteBox;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.math.vec.LittleAbsoluteVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVecContext;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.tile.parent.StructureTileList;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;
import com.littleforge.common.recipe.LittleForgeRecipes;

import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class MultiTilePremade extends LittleStructurePremade{
	
	protected int seriesMaxium;
	protected String seriesName;
	protected int seriesAt;

	public MultiTilePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		seriesName = type.id.toString().split("_")[0];
		seriesAt = Integer.parseInt(this.type.id.toString().split("_")[1]);
	}

	@StructureDirectional
	public EnumFacing direction;
	
	public LittlePreviews updateStructureDirection(LittlePreviews previews, SurroundingBox box, BlockPos min) {
		BlockPos boxPos = box.getMinPos();
		AxisAlignedBB aabb = new AxisAlignedBB(boxPos);
		/*
		LittleGridContext context = LittleGridContext.get(contextSize);
		RayTraceResult res = plr.rayTrace(6.0, (float) 0.1);
		LittleAbsoluteVec pos = new LittleAbsoluteVec(res, context);
		
		long x = context.toGridAccurate(result.hitVec.x);
		long y = context.toGridAccurate(result.hitVec.y);
		long z = context.toGridAccurate(result.hitVec.z);
		this.pos = new BlockPos((int) Math.floor(context.toVanillaGrid(x)), (int) Math.floor(context.toVanillaGrid(y)), (int) Math.floor(context.toVanillaGrid(z)));
		this.contextVec = new LittleVecContext(new LittleVec((int) (x - context.toGridAccurate(pos.getX())), (int) (y - context.toGridAccurate(pos.getY())), (int) (z - context.toGridAccurate(pos.getZ()))), context);
		*/
		LittleGridContext context = box.getContext();
		StructureAbsolute absolute = new StructureAbsolute(boxPos, box.getAbsoluteBox().box, context);

		System.out.println(box.getAbsoluteBox().getDoubledCenter(boxPos) + " " + absolute.getDoubledCenterVec());
		switch (direction) {
		case NORTH:
			previews.rotatePreviews(Rotation.Y_CLOCKWISE, absolute.getDoubledCenterVec());
			break;
		case SOUTH:
			//previews.rotatePreviews(Rotation.Y_COUNTER_CLOCKWISE, box.getAbsoluteBox().getDoubledCenter(min));
			break;
		case WEST:
			//previews.rotatePreviews(Rotation.Y_CLOCKWISE, box.getAbsoluteBox().getDoubledCenter(min));
			//previews.rotatePreviews(Rotation.Y_CLOCKWISE, box.getAbsoluteBox().getDoubledCenter(min));
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
		if(seriesMaxium > seriesAt) {
			return seriesName + "_" + (seriesAt+1);
		}
		return "";
	}
	
	public static void registerPremadeStructureType(String id, String modid, Class<? extends LittleStructurePremade> classStructure) {
		registerPremadeStructureType(id, modid, classStructure, LittleStructureAttribute.NONE);
	}
}
