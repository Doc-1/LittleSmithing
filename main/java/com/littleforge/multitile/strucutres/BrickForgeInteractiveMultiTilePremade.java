package com.littleforge.multitile.strucutres;

import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BrickForgeInteractiveMultiTilePremade extends MultiTilePremade {
	
	public BrickForgeInteractiveMultiTilePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action) throws LittleActionException {
		if (worldIn.isRemote)
			return true;
		
		if (facing != EnumFacing.UP) {
			playerIn.sendStatusMessage(new TextComponentTranslation("structure.interaction.wrongfacing"), true);
			return true;
		}
		
		LittleTransformStructure transform = new LittleTransformStructure(blocksList(), getSurroundingBox(), direction);
		
		transform.setEditArea(new LittleBox(0, 0, 0, 16, 8, 2));
		transform.removeAlpha();
		return true;
	}
}
