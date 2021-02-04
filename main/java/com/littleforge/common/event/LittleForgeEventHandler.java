package com.littleforge.common.event;

import com.creativemd.littletiles.common.block.BlockTile;
import com.creativemd.littletiles.common.event.LittleEventHandler;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.util.place.PlacementHelper;
import com.littleforge.common.api.ILittleItem;
import com.littleforge.common.item.placeable.weapon.PremadePlaceableItemWeapon;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LittleForgeEventHandler extends LittleEventHandler {
	
	@SubscribeEvent
	public static void hasDamagedEntityEvent(LivingDamageEvent event) {
		DamageSource source = event.getSource();
		Entity en = source.getTrueSource();
		EntityLivingBase entity = event.getEntityLiving();
	}
	
	@SubscribeEvent
	public static void rightClickEntityEvent(PlayerInteractEvent.RightClickItem event) {
		EntityPlayer player = event.getEntityPlayer();
		
		EntityLivingBase entity = (EntityLivingBase) player.rayTrace(8, Minecraft.getMinecraft().getRenderPartialTicks()).entityHit;
		
		Item itemHeld = event.getItemStack().getItem();
		if (!player.isSneaking()) {
			if (itemHeld instanceof PremadePlaceableItemWeapon) {
				((PremadePlaceableItemWeapon) itemHeld).onRightClickEntity(event.getItemStack(), player, entity);
			}
		}
	}
	
	@Override
	@SubscribeEvent
	public void onInteract(RightClickBlock event) {
		if (!event.getWorld().isRemote && LittleEventHandler.consumeBlockTilePrevent(event.getEntityPlayer())) {
			event.setCanceled(true);
			return;
		}
		
		ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
		
		ILittleItem iTile = (ILittleItem) PlacementHelper.getLittleInterface(stack);
		
		if (iTile != null) {
			if (event.getHand() == EnumHand.MAIN_HAND && event.getWorld().isRemote)
				onRightInteractClient(iTile, event.getEntityPlayer(), event.getHand(), event.getWorld(), stack, event.getPos(), event.getFace());
			
		}
	}
	
	public void onRightInteractClient(ILittleItem iTile, EntityPlayer player, EnumHand hand, World world, ItemStack stack, BlockPos pos, EnumFacing facing) {
		super.onRightInteractClient(iTile, player, hand, world, stack, pos, facing);
	}
	
	public static void blockBrokenEvent(LootTableLoadEvent event) {
		if (event.getName().toString().equals("minecraft:chests/simple_dungeon")) {
			event.getTable();
		}
	}
	
	@SubscribeEvent
	public static void leftClickEvent(PlayerInteractEvent.LeftClickBlock event) {
		Minecraft mc = Minecraft.getMinecraft();
		World world = event.getWorld();
		EntityPlayer player = event.getEntityPlayer();
		RayTraceResult results = player.rayTrace(6.0F, mc.getRenderPartialTicks());
		LittleStructure structure = null;
		TileEntityLittleTiles te = BlockTile.loadTe(world, results.getBlockPos());
		/*
		for (IStructureTileList struct : te.structures()) {
			try {
				System.out.println(struct.getStructure());
				NBTTagCompound nbt = new NBTTagCompound();
				
				for (LittleTile tile : struct.getStructure().mainBlock) {
					tile.saveTile(nbt);
					//System.out.println(nbt);
				}
			} catch (CorruptedConnectionException | NotYetConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		ItemStack itemHeld = player.getHeldItemMainhand();
		//if (itemHeld.getItem() instanceof PremadeItemHammer) {
		if (te != null) {
			try {
				Vec3d pos1 = player.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks());
				double d0 = player.capabilities.isCreativeMode ? 5.0F : 4.5F;
				Vec3d look = player.getLook(Minecraft.getMinecraft().getRenderPartialTicks());
				Vec3d vec32 = pos1.addVector(look.x * d0, look.y * d0, look.z * d0);
				structure = te.getFocusedTile(pos1, vec32).key.getStructure();
			} catch (CorruptedConnectionException | NotYetConnectedException e) {
			}
		}
		if (structure != null) {
			//System.out.println(structure);
			
			//((PremadeItemHammer) itemHeld.getItem()).onLeftClickStructure(world, player, structure);
		}
		//}
	}
}
