package com.littleforge.multitile.strucutres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.relative.StructureAbsolute;
import com.creativemd.littletiles.common.tile.LittleTile;
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
	private Map<BlockPos, List<LittleTile>> tilePosList = new HashMap<BlockPos, List<LittleTile>>();
	private List<LittleTile> tileList;
	
	public LittleTransformStructure(Iterable<IStructureTileList> blocksLs) {
		blocksList = blocksLs;
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
	
	public void collectAllTiles() throws CorruptedConnectionException, NotYetConnectedException {
		for (IStructureTileList iStructureTileList : blocksList) {
			System.out.println(iStructureTileList.getPos());
			iStructureTileList.getTe().updateTiles((x) -> {
				IStructureTileList list = x.get(iStructureTileList);
				List<LittleTile> tileLs = new ArrayList<LittleTile>();
				for(LittleTile littleTile : list) {
					tileLs.add(littleTile);
					StructureAbsolute absolute = new StructureAbsolute(list.getPos(), littleTile.getBox(), LittleGridContext.get());

					System.out.println(littleTile.getBox() +" "+ list.getPos()); 
				}
				tilePosList.put(iStructureTileList.getPos(), tileLs);
			});
		}
		
	}
	 
	
	/*
	 * 
	 */
}
