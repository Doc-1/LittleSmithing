package com.littleforge.common.strucutres.type.premade.interactive;

import java.util.ArrayList;

import com.creativemd.creativecore.common.utils.math.Rotation;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.structure.LittleStructure;
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
import com.littleforge.common.premade.interaction.PremadeInteractionControl;
import com.littleforge.common.premade.interaction.PremadeInteractionTickingControl;
import com.littleforge.common.recipe.LittleForgeRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public abstract class InteractivePremade extends LittleStructurePremade {
	
	protected int seriesMaxium;
	protected String seriesName;
	protected int seriesAt;
	
	public boolean blockActivated;
	public ArrayList<PremadeInteractionControl> controls = new ArrayList<PremadeInteractionControl>();
	
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
	protected Object failedLoadingRelative(NBTTagCompound nbt, StructureDirectionalField field) {
		if (field.key.equals("facing"))
			return EnumFacing.UP;
		if (field.key.equals("east"))
			return EnumFacing.EAST;
		if (field.key.equals("west"))
			return EnumFacing.WEST;
		return super.failedLoadingRelative(nbt, field);
	}

	public abstract void createControls();
	
	public PremadeInteractionControl getControl(String id) {
		for (PremadeInteractionControl control : controls) {
			if(control.id.equals(id)) 
				return control;
		}
		return null;
	}
	
	protected String nextSeries() {
		//if (seriesMaxium > seriesAt) {
		//	return seriesName + "_" + (seriesAt + 1);
		//}
		return "";
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
	}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
	}
	
	@Override
	public void tick() {
		if(!controls.isEmpty()) 
			for (PremadeInteractionControl control : controls) 
				if(control.isTicking()) 
					((PremadeInteractionTickingControl) control).update();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action) throws LittleActionException {
		if (worldIn.isRemote)
			return true;
		if(controls.isEmpty()) {
			createControls();
		}
		
		if (facing != EnumFacing.UP) {
			playerIn.sendStatusMessage(new TextComponentTranslation("structure.interaction.wrongfacing"), true);
			blockActivated = false;
			return true;
		}else {
			blockActivated = true;
			return true;
		}
	}
}
