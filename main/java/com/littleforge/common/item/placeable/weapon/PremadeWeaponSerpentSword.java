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
	private float oldPitch;
	private float oldYaw;
	
	public PremadeWeaponSerpentSword(ToolMaterial material, String unlocalizedName, String registryName, String premadeToRender, String premadeToPlace) {
		super(material, unlocalizedName, registryName, premadeToRender, premadeToPlace);
		
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		
	}
	
	@Override
	public ActionResult<ItemStack> onRightClickEntity(ItemStack heldItem, EntityPlayer player, EntityLivingBase entity) {
		EnumActionResult result = EnumActionResult.PASS;
		
		if (player.onGround) {
			int direction = 0;
			
			if (player.moveStrafing > 0)
				direction = -90;
			else if (player.moveStrafing < 0)
				direction = 90;
			
			System.out.println(direction);
			if (direction != 0) {
				float f = 2.0F;
				player.addExhaustion(0.1f);
				player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 10);
				player.motionX = -MathHelper.sin((player.rotationYaw + direction) / 180.0F * (float) Math.PI) * f;
				player.motionZ = MathHelper.cos((player.rotationYaw + direction) / 180.0F * (float) Math.PI) * f;
				oldYaw = player.rotationYaw;
				oldPitch = player.rotationPitch;
			}
			
			//player.motionX += -MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
			//player.motionZ += MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
			
			result = EnumActionResult.SUCCESS;
		}
		
		return ActionResult.newResult(result, heldItem);
	}
}
