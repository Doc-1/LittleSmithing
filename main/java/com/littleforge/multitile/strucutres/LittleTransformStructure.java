package com.littleforge.multitile.strucutres;

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
import com.creativemd.littletiles.common.util.vec.SurroundingBox;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
/***
 * 
 * @author Doc
 *	Allows for changing color, material, position, ect. of tiles inside a premade structure.
 */
public class LittleTransformStructure {
	
	private Iterable<IStructureTileList> blocksList;
	private Map<LittleBox, LittleTile> tilePosList = new HashMap<LittleBox, LittleTile>();
	private List<LittleTile> tileList;
	
	private LittleBox editArea;
	private SurroundingBox structureBox;
	
	public EnumFacing structureDirection;
	
	public AxisAlignedBB absolutePos;
	public double removeX;
	public double removeY;
	public double removeZ;
	
	public LittleTransformStructure(Iterable<IStructureTileList> blocksLs, SurroundingBox box, EnumFacing structDirection) {
		blocksList = blocksLs;
		absolutePos = box.getAABB();
		structureBox = box;
		structureDirection = structDirection;

		removeX = box.getAABB().minX;
		removeY = box.getAABB().minY;
		removeZ = box.getAABB().minZ;
		try {
			collectAllTiles();
		} catch (CorruptedConnectionException | NotYetConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * @param box
	 * Set it to the area you want to edit.
	 */
	public void setEditArea(LittleBox box) {
		System.out.println(structureDirection);
		double x = (box.maxX + box.minX) / 2;
		double y = (box.maxY + box.minY) / 2;
		double z = (box.maxZ + box.minZ) / 2;
		LittleVec doubleCenter = new LittleVec((int) (x * 2), (int) (y * 2), (int) (z * 2));
		
		switch (structureDirection) {
		case NORTH:
			box.rotateBox(Rotation.Y_CLOCKWISE, box.getCenter());
			break;
		case WEST:
			box.flipBox(Axis.Z, doubleCenter);
			break;
		case SOUTH:
			box.rotateBox(Rotation.Y_CLOCKWISE, box.getCenter());
			break;
		default:
			break;
		}
		editArea = box;
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
	
	/***
	 * Collects all tiles within the structure. It assigns each tile a box relative to the structure itself.
	 * Meaning minimum corner of the structure is considered 0,0,0.
	 */
	public void collectAllTiles() throws CorruptedConnectionException, NotYetConnectedException {
		for (IStructureTileList iStructureTileList : blocksList) {
			iStructureTileList.getTe().updateTiles((a) -> {
				IStructureTileList list = a.get(iStructureTileList);
				List<LittleTile> tileLs = new ArrayList<LittleTile>();
				BlockPos pos = iStructureTileList.getPos();
				
				for(LittleTile littleTile : list) {
					Vec3d relativeMin = new Vec3d(((littleTile.getBox().minX/16D) + pos.getX()-removeX), 
							(littleTile.getBox().minY/16D) + pos.getY()-removeY, 
							(littleTile.getBox().minZ/16D) + pos.getZ()-removeZ);
					Vec3d relativeMax = new Vec3d(((littleTile.getBox().maxX/16D) + pos.getX()-removeX), 
							(littleTile.getBox().maxY/16D) + pos.getY()-removeY, 
							(littleTile.getBox().maxZ/16D) + pos.getZ()-removeZ);
					
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
