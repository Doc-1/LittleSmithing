package com.littleforge.common.strucutres.type.premade.interactive;

import com.creativemd.creativecore.common.utils.math.Rotation;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.structure.directional.StructureDirectional;
import com.creativemd.littletiles.common.structure.directional.StructureDirectionalField;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.relative.StructureAbsolute;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVecContext;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.place.Placement;
import com.creativemd.littletiles.common.util.place.PlacementMode;
import com.creativemd.littletiles.common.util.place.PlacementPreview;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;
import com.littleforge.common.recipe.LittleForgeRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class InteractivePremade extends LittleStructurePremade {
	
	protected int seriesMaxium;
	protected String seriesName;
	protected int seriesAt;
	
	public InteractivePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		//seriesName = type.id.toString().split("_")[0];
		//seriesAt = Integer.parseInt(this.type.id.toString().split("_")[1]);
	}
	
	@StructureDirectional
	public EnumFacing direction;
	
	@StructureDirectional
	public EnumFacing facing;

	@StructureDirectional
	public EnumFacing east;

	@StructureDirectional
	public EnumFacing west;

	@Override
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action)
			throws LittleActionException {
		if(worldIn.isRemote)
			return true;
		
		System.out.println(this.getAttribute());
		String next = nextSeries();
		if(!next.isEmpty()) {
			if(LittleForgeRecipes.takeIngredients(playerIn, type.id)) {
				try {
					SurroundingBox box = getSurroundingBox();
					long minX = box.getMinX();
					long minY = box.getMinY();
					long minZ = box.getMinZ();
					
					long maxX = box.getMaxX();
					long maxY = box.getMaxY();
					long maxZ = box.getMaxZ();
					LittleGridContext context = box.getContext();
					BlockPos min = new BlockPos(context.toBlockOffset(minX), context.toBlockOffset(minY), context.toBlockOffset(minZ));
					LittleVecContext minVec = new LittleVecContext(new LittleVec((int) (minX - (long) min.getX() * (long) context.size), (int) (minY - (long) min.getY() * (long) context.size), (int) (minZ - (long) min.getZ() * (long) context.size)), context);
					
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
			
					this.removeStructure();
					PlacementPreview nextPremade = new PlacementPreview(this.getWorld(), previews, PlacementMode.normal, preview.box, false, min, LittleVec.ZERO, EnumFacing.NORTH);
					Placement place = new Placement(null, nextPremade);
					place.place();
					
				}catch (CorruptedConnectionException | NotYetConnectedException e1) {
					e1.printStackTrace();
				} catch (LittleActionException e) {
					e.printStackTrace();
				}
			}
		}
	return true;
	}
	
	@Override
	protected Object failedLoadingRelative(NBTTagCompound nbt, StructureDirectionalField field) {
		if (field.key.equals("facing"))
			return EnumFacing.UP;
		if (field.key.equals("east"))
			return EnumFacing.EAST;
		if (field.key.equals("west"))
			return EnumFacing.WEST;
		return super.failedLoadingRelative(nbt, field);
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {}
	
	protected String nextSeries() {
		//if (seriesMaxium > seriesAt) {
		//	return seriesName + "_" + (seriesAt + 1);
		//}
		return "";
	}
}
