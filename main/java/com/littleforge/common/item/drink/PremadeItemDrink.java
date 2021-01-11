package com.littleforge.common.item.drink;

import com.littleforge.common.item.placeable.PremadePlaceableItem;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadeItemDrink extends PremadePlaceableItem {
	
	public PremadeItemDrink(String unlocalizedName, String registryName, String premadeToRender, String premadeToPlace) {
		super(unlocalizedName, registryName, premadeToRender, premadeToPlace);
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (!worldIn.isRemote)
			entityLiving.curePotionEffects(stack); // FORGE - move up so stack.shrink does not turn stack into air
		if (entityLiving instanceof EntityPlayerMP) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityLiving;
			CriteriaTriggers.CONSUME_ITEM.trigger(entityplayermp, stack);
			entityplayermp.addStat(StatList.getObjectUseStats(this));
		}
		
		if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
			stack.shrink(1);
		}
		
		return stack.isEmpty() ? ItemStack.EMPTY : stack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void applyCustomOpenGLHackery(ItemStack stack, TransformType cameraTransformType) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (cameraTransformType == TransformType.FIRST_PERSON_RIGHT_HAND) {
			GlStateManager.translate(-0.5D, 0.05D, 0.1D);
			GlStateManager.scale(3D, 3D, 3D);
			GlStateManager.rotate(-140.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(15.0F, 1.0F, 0.10F, -0.50F);
			
			//GlStateManager.scale(1.0D, 1.0D, 1.0D);
		}
		
		if (cameraTransformType == TransformType.GUI) {
			GlStateManager.translate(-1D, 0.4D, 0.0D);
			GlStateManager.scale(5D, 5D, 5D);
		}
		
		if (cameraTransformType == TransformType.GROUND) {
			
			GlStateManager.translate(0.01D, -0.50D, 0.30D);
			GlStateManager.scale(4D, 4D, 4D);
		}
		
	}
	
}
