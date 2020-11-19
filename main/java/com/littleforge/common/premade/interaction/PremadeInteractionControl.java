package com.littleforge.common.premade.interaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.Color;

import com.creativemd.creativecore.common.utils.math.Rotation;
import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.relative.StructureAbsolute;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.place.PlacementMode.PreviewMode;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/***
 * @author Doc
 *	Allows for changing color, material, position, ect. of tiles inside a premade structure.
 */
public abstract class PremadeInteractionControl {
	
	protected Map<LittleBox, LittleTile> tilePosList = new HashMap<LittleBox, LittleTile>();
	protected LittleBox editArea;
	protected InteractivePremade premade;

	protected boolean ticking = false;
	
	public AxisAlignedBB absolutePos;
	public String id;
	
	public PremadeInteractionControl(InteractivePremade premade, String id) {
		this.premade = premade;
		this.id = id;
		try {
			absolutePos = premade.getSurroundingBox().getAABB();
		} catch (CorruptedConnectionException | NotYetConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PremadeInteractionControl(InteractivePremade premade, String id, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		this(premade, id);
		setEditArea(minX, minY, minZ, maxX, maxY, maxZ);
	}

	protected boolean onRightClick() {
		if(premade.blockActivated) {
			onActivated();
			premade.blockActivated = false;
			return true;
		}
		return false;
	}
	
	public boolean isTicking() {
		return ticking;
	}
	
	
	public abstract void onActivated();
	
	/**
	 * @param box
	 * Set it to the area you want to edit.
	 */
	public void setEditArea(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		editArea = new LittleBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	/**
	 * 
	 * @return
	 * Returns the adjusted vec
	 */
	private Vec3d[] adjustRelativeTileVec(BlockPos pos, LittleBox box) {
		Vec3d relativeVec[] = new Vec3d[2];

		double aMinX = 0, aMinY = 0, aMinZ = 0;
		double aMaxX = 0, aMaxY = 0, aMaxZ = 0;
		
		switch (premade.direction) {
		case NORTH:
			aMinX = (box.maxZ/16D)+pos.getZ()-absolutePos.maxZ;
			aMinY = (box.minY/16D)+pos.getY()-absolutePos.minY;
			aMinZ =	(box.minX/16D)+pos.getX()-absolutePos.minX;
			
			aMaxX = (box.minZ/16D)+pos.getZ()-absolutePos.maxZ;
			aMaxY = (box.maxY/16D)+pos.getY()-absolutePos.minY;
			aMaxZ = (box.maxX/16D)+pos.getX()-absolutePos.minX;
			break;
		case EAST:
			aMinX = (box.minX/16D)+pos.getX()-absolutePos.minX;
			aMinY = (box.minY/16D)+pos.getY()-absolutePos.minY;
			aMinZ =	(box.minZ/16D)+pos.getZ()-absolutePos.minZ;
			
			aMaxX = (box.maxX/16D)+pos.getX()-absolutePos.minX;
			aMaxY = (box.maxY/16D)+pos.getY()-absolutePos.minY;
			aMaxZ = (box.maxZ/16D)+pos.getZ()-absolutePos.minZ;
			break;
		case SOUTH:
			aMinX = (box.minZ/16D)+pos.getZ()-absolutePos.minZ;
			aMinY = (box.minY/16D)+pos.getY()-absolutePos.minY;
			aMinZ =	(box.maxX/16D)+pos.getX()-absolutePos.maxX;
			
			aMaxX = (box.maxZ/16D)+pos.getZ()-absolutePos.minZ;
			aMaxY = (box.maxY/16D)+pos.getY()-absolutePos.minY;
			aMaxZ = (box.minX/16D)+pos.getX()-absolutePos.maxX;
			break;
		case WEST:
			aMinX = (box.maxX/16D)+pos.getX()-absolutePos.maxX;
			aMinY = (box.minY/16D)+pos.getY()-absolutePos.minY;
			aMinZ =	(box.maxZ/16D)+pos.getZ()-absolutePos.maxZ;
			
			aMaxX = (box.minX/16D)+pos.getX()-absolutePos.maxX;
			aMaxY = (box.maxY/16D)+pos.getY()-absolutePos.minY;
			aMaxZ = (box.minZ/16D)+pos.getZ()-absolutePos.maxZ; 
			break;
		default:
			break;
		}
		relativeVec[0] = new Vec3d(Math.abs(aMinX), Math.abs(aMinY), Math.abs(aMinZ));
		relativeVec[1] = new Vec3d(Math.abs(aMaxX), Math.abs(aMaxY), Math.abs(aMaxZ));
		return relativeVec;
	}
	
	/**
	 * Collects all tiles within the structure. It assigns each tile a box relative to the structure itself.
	 * Meaning minimum corner of the structure is considered 0,0,0.
	 */
	public void collectAllTiles() throws CorruptedConnectionException, NotYetConnectedException {
		for (IStructureTileList iStructureTileList : premade.blocksList()) {
			iStructureTileList.getTe().updateTiles((a) -> {
				IStructureTileList list = a.get(iStructureTileList);
				List<LittleTile> tileLs = new ArrayList<LittleTile>();
				BlockPos pos = iStructureTileList.getTe().getPos();
				for(LittleTile littleTile : list) {
					Vec3d relativeVec[] = adjustRelativeTileVec(pos, littleTile.getBox());
					Vec3d relativeMin = relativeVec[0];
					Vec3d relativeMax = relativeVec[1];
					LittleBox relativeBox = new LittleBox(new LittleVec((int) (relativeMin.x*16), (int) (relativeMin.y*16), (int) (relativeMin.z*16)), 
							new LittleVec((int) (relativeMax.x*16), (int) (relativeMax.y*16), (int) (relativeMax.z*16)));

					tilePosList.put(relativeBox, littleTile);
				}
			});
		}
	}
	 
	
	/*
	 * 
	 */
}
