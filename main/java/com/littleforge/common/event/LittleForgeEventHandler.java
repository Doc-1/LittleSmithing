package com.littleforge.common.event;

import com.creativemd.littletiles.common.block.BlockTile;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.littleforge.common.item.PremadeItemStoneHammer;
import com.littleforge.common.item.placeable.PremadePlaceableItem;
import com.littleforge.common.item.placeable.weapon.PremadePlaceableItemWeapon;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LittleForgeEventHandler {
	
	@SubscribeEvent
	public static void hasDamagedEntity(LivingDamageEvent event) {
		DamageSource source = event.getSource();
		Entity en = source.getTrueSource();
		EntityLivingBase entity = event.getEntityLiving();
	}
	
	@SubscribeEvent
	public static void rightClickEntity(PlayerInteractEvent.RightClickItem event) {
		EntityPlayer player = event.getEntityPlayer();
		
		EntityLivingBase entity = (EntityLivingBase) player.rayTrace(8, Minecraft.getMinecraft().getRenderPartialTicks()).entityHit;
		
		Item itemHeld = event.getItemStack().getItem();
		if (!player.isSneaking()) {
			if (itemHeld instanceof PremadePlaceableItemWeapon) {
				((PremadePlaceableItemWeapon) itemHeld).onRightClickEntity(event.getItemStack(), player, entity);
			}
			if (itemHeld instanceof PremadePlaceableItem) {
				((PremadePlaceableItem) itemHeld).onRightClickEntity(event.getItemStack(), player, entity);
			}
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
			PremadeItemStoneHammer.onLeftClickStructure(world, player, structure);
		}
	}
}
