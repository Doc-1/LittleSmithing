package com.littleforge.common.strucutres.type.premade.interactive;

import java.util.ArrayList;
import java.util.List;
import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.littletiles.common.action.LittleAction;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.packet.LittleActionMessagePacket;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.util.ingredient.LittleIngredients;
import com.creativemd.littletiles.common.util.ingredient.LittleInventory;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException;
import com.creativemd.littletiles.common.util.ingredient.StackIngredient;
import com.creativemd.littletiles.common.util.tooltip.ActionMessage;
import com.littleforge.LittleForge;
import com.littleforge.packet.PacketUpdateGivenItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class PickupItemPremade extends LittleStructurePremade{

	public PickupItemPremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
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
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action)
			throws LittleActionException {
		if(worldIn.isRemote)
			return true;
		ItemStack stack = new ItemStack(Item.getByNameOrId(LittleForge.MODID + ":" + this.type.id));
		
		System.out.println(playerIn.inventory.getCurrentItem());
		
		if(playerIn.inventory.getCurrentItem().getItem() == Items.AIR) {
			PacketHandler.sendPacketToServer(new PacketUpdateGivenItem(stack));
			this.removeStructure();
		}
		return false;
	}

}
