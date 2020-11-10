package com.littleforge.common.strucutres.type.premade.interactive;

import com.creativemd.littletiles.client.LittleTilesClient;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.common.pui.PUIControl;
import com.littleforge.common.pui.PUIEditTileColor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BrickForgeInteractivePremade extends InteractivePremade {
	
	public BrickForgeInteractivePremade(LittleStructureType type, IStructureTileList mainBlock) {
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
		
		//System.out.println("Up:" + (facing==(EnumFacing.UP)) + " East:" + (east==(EnumFacing.EAST)) + " West:" + (west==(EnumFacing.WEST)));
		System.out.println(east + " " + west);
		PUIEditTileColor transform = new PUIEditTileColor(this);
		transform.setEditArea(0, 0, 0, 1, 8, 5);
		transform.removeAlpha();
		return true;
	}
}
