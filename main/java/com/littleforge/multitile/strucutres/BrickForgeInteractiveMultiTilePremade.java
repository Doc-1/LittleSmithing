package com.littleforge.multitile.strucutres;

import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.creativecore.common.utils.type.Pair;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.tile.parent.StructureTileList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BrickForgeInteractiveMultiTilePremade extends InteractiveMultiTilePremade{

	public BrickForgeInteractiveMultiTilePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action)throws LittleActionException {
		if(worldIn.isRemote)
			return true;

		//{tiles:[{boxes:[[I;9,1,14,16,4,16],[I;0,1,14,8,4,16],[I;13,5,14,16,8,16],[I;5,5,14,12,8,16],[I;0,5,14,4,8,16]],tile:{color:-11850457,block:"littletiles:ltcoloredblock:5"}},{boxes:[[I;8,1,14,9,4,16],[I;0,0,14,16,1,16],[I;12,5,14,13,8,16],[I;4,5,14,5,8,16],[I;0,4,14,16,5,16]],tile:{color:-11448772,block:"littletiles:ltcoloredblock:5"}},{boxes:[[I;13,13,14,16,16,16],[I;0,13,14,3,16,16],[I;9,9,14,16,12,16],[I;0,9,14,8,12,16],[I;4,13,14,12,16,16]],tile:{color:-2109002457,block:"littletiles:ltcoloredblock:5"}},{boxes:[[I;0,8,14,16,9,16],[I;0,12,14,16,13,16],[I;12,13,14,13,16,16],[I;8,9,14,9,12,16],[I;3,13,14,4,16,16]],tile:{color:-2108600772,block:"littletiles:ltcoloredblock:5"}}],min:[I;0,0,14],size:[I;16,16,2],count:20}
		LittleTransformStructure transform = new LittleTransformStructure(blocksList(), getSurroundingBox().getAABB());
		transform.collision();
		return true;
	}
}
