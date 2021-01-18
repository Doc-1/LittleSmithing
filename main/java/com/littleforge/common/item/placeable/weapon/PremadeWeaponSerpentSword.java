package com.littleforge.common.item.placeable.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PremadeWeaponSerpentSword extends PremadeItemSword25 {
	
	private EntityLivingBase entity;
	private int step = -1;
	
	public PremadeWeaponSerpentSword(ToolMaterial material, String unlocalizedName, String registryName, String premadeToRender, String premadeToPlace) {
		super(material, unlocalizedName, registryName, premadeToRender, premadeToPlace);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		
		if (worldIn.isRemote && step != -1 && entityIn != null) {
			EntityPlayer player = (EntityPlayer) entityIn;
			player.addExhaustion(0.1f);
			player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 20);
			float f = 1.0F;
			switch (step) {
			case 0:
				player.motionX = -MathHelper.sin((player.rotationYaw + 90) / 180.0F * (float) Math.PI) * f;
				player.motionZ = MathHelper.cos((player.rotationYaw + 90) / 180.0F * (float) Math.PI) * f;
				
				player.motionX += -MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
				player.motionZ += MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
				break;
			case 10:
				player.motionX = -MathHelper.sin((player.rotationYaw + 270) / 180.0F * (float) Math.PI) * 2.4F;
				player.motionZ = MathHelper.cos((player.rotationYaw + 270) / 180.0F * (float) Math.PI) * 2.4F;
				
				player.motionX += -MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
				player.motionZ += MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
				break;
			case 20:
				player.motionX = -MathHelper.sin((player.rotationYaw + 90) / 180.0F * (float) Math.PI) * f;
				player.motionZ = MathHelper.cos((player.rotationYaw + 90) / 180.0F * (float) Math.PI) * f;
				
				player.motionX += -MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
				player.motionZ += MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
				break;
			
			default:
				break;
			}
			step++;
			if (step > 20) {
				step = -1;
			}
			
		}
	}
	
	@Override
	public ActionResult<ItemStack> onRightClickEntity(ItemStack heldItem, EntityPlayer player, EntityLivingBase entity) {
		EnumActionResult result = EnumActionResult.PASS;
		if (player.onGround) {
			this.entity = player;
			step = 0;
			result = EnumActionResult.SUCCESS;
		}
		
		return ActionResult.newResult(result, heldItem);
	}
}
