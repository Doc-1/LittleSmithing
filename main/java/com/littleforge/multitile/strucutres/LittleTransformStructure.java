package com.littleforge.multitile.strucutres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.Color;

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

import net.minecraft.nbt.NBTTagCompound;
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
	
	public AxisAlignedBB absolutePos;
	public double removeX;
	public double removeY;
	public double removeZ;
	
	public LittleTransformStructure(Iterable<IStructureTileList> blocksLs, AxisAlignedBB absPos) {
		blocksList = blocksLs;
		absolutePos = absPos;
	
		
		
		removeX = absolutePos.minX;
		removeY = absolutePos.minY;
		removeZ = absolutePos.minZ;
		
		try {
			collectAllTiles();
		} catch (CorruptedConnectionException | NotYetConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * 
	 * @param box
	 * 
	 */
	public void selectBox(AxisAlignedBB box) {
		double minX = box.minX;
		double minY = box.minY;
		double minZ = box.minZ;
		
		double maxX = box.maxX;
		double maxY = box.maxY;
		double maxZ = box.maxZ;
		
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
	
	public void collision() {
		LittleBox box = new LittleBox(0, 0, 0, 16, 16, 48);
		for (LittleBox littleBox : tilePosList.keySet()) {
			if(LittleBox.intersectsWith(littleBox, box)) {
				LittleTile littleTile = tilePosList.get(littleBox);
				int color = ColorUtils.RGBAToInt(25, 0, 0, 255);
				NBTTagCompound nbt = new NBTTagCompound();
		    	littleTile.saveTileExtra(nbt);
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
	/***
	 * Collects all tiles within the structure. It assigns each tile a box relative to the structure itself.
	 * Meaning minimum corner of the structure is considered 0,0,0.
	 */
	public void collectAllTiles() throws CorruptedConnectionException, NotYetConnectedException {
		for (IStructureTileList iStructureTileList : blocksList) {
			iStructureTileList.getTe().updateTiles((a) -> {
				Vec3d relativeMin;
				Vec3d relativeMax;
				IStructureTileList list = a.get(iStructureTileList);
				List<LittleTile> tileLs = new ArrayList<LittleTile>();
				BlockPos pos = iStructureTileList.getPos();
				for(LittleTile littleTile : list) {
					relativeMin = new Vec3d(((littleTile.getBox().minX/16D) + pos.getX()-removeX), 
							(littleTile.getBox().minY/16D) + pos.getY()-removeY, 
							(littleTile.getBox().minZ/16D) + pos.getZ()-removeZ);
					relativeMax = new Vec3d(((littleTile.getBox().maxX/16D) + pos.getX()-removeX), 
							(littleTile.getBox().maxY/16D) + pos.getY()-removeY, 
							(littleTile.getBox().maxZ/16D) + pos.getZ()-removeZ);
					
					LittleBox relativeBox = new LittleBox(new LittleVec((int) (relativeMin.x*16), (int) (relativeMin.y*16), (int) (relativeMin.z*16)), 
							new LittleVec((int) (relativeMax.x*16), (int) (relativeMax.y*16), (int) (relativeMax.z*16)));
					//System.out.println(relativeMin + " " + relativeMax + " " + relativeBox);

					tilePosList.put(relativeBox, littleTile);
				}
			});
		}
		
	}
	 
	
	/*
	 * 
	 */
}
