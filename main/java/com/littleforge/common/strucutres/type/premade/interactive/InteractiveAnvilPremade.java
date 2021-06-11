package com.littleforge.common.strucutres.type.premade.interactive;

import javax.annotation.Nullable;

import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.LittleForge;
import com.littleforge.common.premade.interaction.actions.AddStructure;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InteractiveAnvilPremade extends InteractivePremade {
	
	public InteractiveAnvilPremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onPremadeActivated(ItemStack heldItem) {
	}
	
	public void onLeftClickStructure(World world, EntityPlayer player, LittleStructure structure) {
		System.out.println(structure);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action) throws LittleActionException {
		if (playerIn.getHeldItemMainhand().getItem().equals(LittleForge.mushroomHorn)) {
			if (!worldIn.isRemote) {
				AddStructure.setPremadeID("mushroom_horn");
				editArea = new LittleBox(37, 21, 4, 0, 0, 0);
				AddStructure.toPremade(this, playerIn);
			}
			return true;
		}
		if (!worldIn.isRemote) {
			AddStructure.setPremadeID("mushroom_horn");
			editArea = new LittleBox(4, 21, 37, 0, 0, 0);
			AddStructure.toPremade(this, playerIn);
			
		}
		System.out.println(this.facing + " " + this.direction + " " + this.west);
		
		//LittleStructureGuiHandler.openGui("anvil", new NBTTagCompound(), playerIn, this);
		return true;
	}
}
