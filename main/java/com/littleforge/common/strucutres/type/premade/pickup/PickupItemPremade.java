package com.littleforge.common.strucutres.type.premade.pickup;

import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.littletiles.common.action.LittleAction;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.util.ingredient.LittleIngredients;
import com.creativemd.littletiles.common.util.ingredient.LittleInventory;
import com.creativemd.littletiles.common.util.ingredient.StackIngredient;
import com.littleforge.LittleForge;
import com.littleforge.common.packet.PacketUpdateNBT;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PickupItemPremade extends InteractivePremade {
	
	public ItemStack itemToPickup;
	
	public PickupItemPremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}
	
	@Override
	public ItemStack getStructureDrop() {
		if (itemToPickup != null)
			return this.itemToPickup;
		
		return new ItemStack(Item.getByNameOrId(LittleForge.MODID + ":" + this.type.id));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action) throws LittleActionException {
		if (worldIn.isRemote)
			return true;
		ItemStack stack = null;
		
		if (itemToPickup != null)
			stack = this.itemToPickup;
		else {
			stack = new ItemStack(Item.getByNameOrId(LittleForge.MODID + ":" + this.type.id));
		}
		
		LittleIngredients ingredients = new LittleIngredients();
		StackIngredient ingredient = new StackIngredient(stack);
		ingredients.add(ingredient);
		LittleInventory inventory = new LittleInventory(playerIn);
		
		if (playerIn.inventory.getCurrentItem().getItem() == LittleForge.tongs) {
			ItemStack stack2 = playerIn.getHeldItemMainhand();
			NBTTagCompound nbt = new NBTTagCompound();
			if (stack2.getTagCompound() != null)
				nbt = stack2.getTagCompound();
			
			nbt.setString("heldItem", this.type.id);
			stack2.setTagCompound(nbt);
			PacketHandler.sendPacketToServer(new PacketUpdateNBT(playerIn.getHeldItemMainhand()));
			
			if (getParent() != null) {
				LittleStructure parent = getParent().getStructure();
				parent.removeDynamicChild(getParent().childId);
				parent.updateStructure();
			}
			this.removeStructure();
			
		} else if (LittleAction.canGive(playerIn, inventory, ingredients)) {
			int currentSlot = playerIn.inventory.currentItem;
			LittleAction.give(playerIn, inventory, ingredients);
			if (getParent() != null) {
				LittleStructure parent = getParent().getStructure();
				parent.removeDynamicChild(getParent().childId);
				parent.updateStructure();
			}
			this.removeStructure();
			
		}
		return true;
	}
	
	@Override
	public void onPremadeActivated(EntityPlayer playerIn, ItemStack heldItem) {
		// TODO Auto-generated method stub
		
	}
	
}
