
package com.littleforge.multitile.strucutres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import com.creativemd.creativecore.common.utils.type.Pair;
import com.creativemd.littletiles.common.action.block.LittleActionPlaceAbsolute;
import com.creativemd.littletiles.common.action.block.LittleActionPlaceAbsolute.LittleActionPlaceAbsolutePremade;
import com.creativemd.littletiles.common.packet.LittlePlacedAnimationPacket;
import com.creativemd.littletiles.common.action.block.LittleActionPlaceStack;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVecContext;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.tile.place.PlacePreview;
import com.creativemd.littletiles.common.tile.preview.LittleAbsolutePreviews;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.place.Placement;
import com.creativemd.littletiles.common.util.place.PlacementMode;
import com.creativemd.littletiles.common.util.place.PlacementPreview;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

public class MultiTileClayForge extends LittleStructurePremade {
	
	private int seriesIndex = 13;
	private String seriesName = type.id.toString().split("_")[0];
	private int tick = 0;
	private boolean hasFuel = false;
	private String test;
	
	public MultiTileClayForge(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
		tick = nbt.getInteger("tick");
	}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
		nbt.setInteger("tick", tick);
	}
	
	private String nextSeries() {
		int seriesAt = Integer.parseInt(type.id.toString().split("_")[1]);
		if (seriesIndex > seriesAt) {
			return seriesName + "_" + (seriesAt + 1);
		}
		return "";
	}
	
	@Override
	public void tick() {
		if (getWorld().isRemote)
			return;
		
		tick++;
		if (tick >= 20) {
			
			tick = 0;
			SurroundingBox box = new SurroundingBox(false, null).add(mainBlock);
			long minX = box.getMinX();
			long minY = box.getMinY();
			long minZ = box.getMinZ();
			LittleGridContext context = box.getContext();
			BlockPos min = new BlockPos(context.toBlockOffset(minX), context.toBlockOffset(minY), context.toBlockOffset(minZ));
			LittleVecContext minVec = new LittleVecContext(new LittleVec((int) (minX - (long) min.getX() * (long) context.size), (int) (minY - (long) min.getY() * (long) context.size), (int) (minZ - (long) min.getZ() * (long) context.size)), context);
			System.out.println(minVec);

			LittlePreviews previews = getStructurePremadeEntry(nextSeries()).previews.copy(); // Change this line to support different states
			LittleVec previewMinVec = previews.getMinVec();
			LittlePreview preview = null;
			minVec.forceContext(previews);
			for (LittlePreview prev : previews) {
				prev.box.sub(previewMinVec);
				prev.box.add(minVec.getVec());
				preview = prev;
			}
			
			previews.convertToSmallest();
			try {
				PlacementPreview placementPreview = new PlacementPreview(this.getWorld(), previews, PlacementMode.normal, preview.box, false, min, previewMinVec, EnumFacing.NORTH);
				this.removeStructure();
				Placement place = new Placement(Minecraft.getMinecraft().player, placementPreview);
				place.tryPlace();
			} catch (CorruptedConnectionException | NotYetConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Places new structure
			//System.out.println("10 seconds "+this.getMainTile().getBlockPos());
		}
	}
	
}
