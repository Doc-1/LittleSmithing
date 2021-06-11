package com.littleforge.common.strucutres.type.premade.interactive;

import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.common.premade.interaction.actions.AddStructure;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BrickForgeInteractivePremade extends InteractivePremade {
	
	public BrickForgeInteractivePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		seriesMaxium = 9;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action) throws LittleActionException {
		AddStructure.setPremadeID("mushroom_horn");
		editArea = new LittleBox(0, 84, 0, 0, 0, 0);
		AddStructure.toPremade(this, playerIn);
		System.out.println(this.facing + " " + this.direction + " " + this.west);
		return true;
	}
	
	@Override
	public void onPremadeActivated(ItemStack heldItem) {
		//editArea = new LittleBox(0, 0, 0, 34, 8, 16);
		//BuildProgress.forPremade(this);
	}
}
