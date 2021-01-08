package com.littleforge.packet;

import java.util.ArrayList;
import java.util.List;

import com.creativemd.creativecore.common.packet.CreativeCorePacket;
import com.creativemd.littletiles.common.util.ingredient.LittleInventory;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException.NotEnoughSpaceException;
import com.littleforge.LittleForge;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateGivenItem extends CreativeCorePacket{
	
	private ItemStack stack;

	public PacketUpdateGivenItem() {
		ItemStack mainHand = Minecraft.getMinecraft().player.inventory.getItemStack();
		this.stack = mainHand;
	}
	
	public PacketUpdateGivenItem(ItemStack stack) {
		this.stack = stack;
	}
	@Override
	public void writeBytes(ByteBuf buf) {
		writeItemStack(buf, stack);
	}

	@Override
	public void readBytes(ByteBuf buf) {
		stack = readItemStack(buf);
	}

	@Override
	public void executeClient(EntityPlayer player) {
		int currentSlot = player.inventory.currentItem;

		player.inventory.setInventorySlotContents(currentSlot, stack);
	}

	@Override
	public void executeServer(EntityPlayer player) {
		int currentSlot = player.inventory.currentItem;

		player.inventory.setInventorySlotContents(currentSlot, stack);
	}
}