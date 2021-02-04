package com.littleforge.common.api;

import com.creativemd.littletiles.common.api.ILittleTile;
import com.creativemd.littletiles.common.util.place.PlacementPosition;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public interface ILittleItem extends ILittleTile {
	
	@Override
	default boolean onRightClick(World world, EntityPlayer player, ItemStack stack, PlacementPosition position, RayTraceResult result) {
		return ILittleTile.super.onRightClick(world, player, stack, position, result);
	}
}
