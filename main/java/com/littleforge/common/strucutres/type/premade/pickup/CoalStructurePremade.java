package com.littleforge.common.strucutres.type.premade.pickup;

import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.common.premade.interaction.actions.AddStructure;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoalStructurePremade extends PickupItemPremade {
	
	public CoalStructurePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		itemToPickup = new ItemStack(Items.COAL, 5);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action) throws LittleActionException {
		if (playerIn.getHeldItemMainhand().getItem().equals(Items.FLINT_AND_STEEL)) {
			LittleStructure parent = null;
			if (getParent() != null) {
				parent = getParent().getStructure();
				parent.removeDynamicChild(getParent().childId);
				parent.updateStructure();
			}
			this.removeStructure();
			if (parent != null) {
				AddStructure.editArea = new LittleBox(0, 12, 3, 0, 0, 0);
				AddStructure.setPremadeID("brick_forge_coal_burning");
				AddStructure.toPremade((InteractivePremade) parent, playerIn);
			}
			return true;
		} else
			return super.onBlockActivated(worldIn, tile, pos, playerIn, hand, heldItem, side, hitX, hitY, hitZ, action);
		
	}
	
}
