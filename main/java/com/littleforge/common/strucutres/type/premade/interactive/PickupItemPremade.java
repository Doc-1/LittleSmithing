package com.littleforge.common.strucutres.type.premade.interactive;

import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.LittleForge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PickupItemPremade extends LittleStructurePremade {
	
	public Item itemToPickup;
	
	public PickupItemPremade(LittleStructureType type, IStructureTileList mainBlock, Item itemToPickup) {
		super(type, mainBlock);
		this.itemToPickup = itemToPickup;
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public ItemStack getStructureDrop() {
		if (itemToPickup != null)
			return new ItemStack(this.itemToPickup);
		
		return new ItemStack(Item.getByNameOrId(LittleForge.MODID + ":" + this.type.id));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action) throws LittleActionException {
		if (worldIn.isRemote)
			return true;
		ItemStack stack = null;
		
		if (itemToPickup != null)
			stack = new ItemStack(this.itemToPickup);
		else {
			stack = new ItemStack(Item.getByNameOrId(LittleForge.MODID + ":" + this.type.id));
		}
		System.out.println(playerIn.inventory.getCurrentItem());
		
		if (playerIn.inventory.getCurrentItem().getItem() == Items.AIR) {
			int currentSlot = playerIn.inventory.currentItem;
			playerIn.inventory.setInventorySlotContents(currentSlot, stack);
			this.removeStructure();
		}
		return false;
	}
	
}
