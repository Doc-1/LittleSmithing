package com.littleforge.common.item.placeable.weapon;

import java.util.List;

import javax.annotation.Nullable;

import com.creativemd.littletiles.LittleTiles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadeWeaponBlueprint extends PremadePlaceableItemWeapon {
	
	public PremadeWeaponBlueprint(ToolMaterial material, String unlocalizedName, String registryName, String premadeToRender, String premadeToPlace) {
		super(material, unlocalizedName, registryName, premadeToRender, premadeToPlace);
		setCreativeTab(LittleTiles.littleTab);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase player) {
		if (entity instanceof EntityMob && ((EntityMob) entity).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
			
			float f = 10.0F;
			entity.motionY += 0.5;
			entity.motionX = -MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
			entity.motionZ = MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
			entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), 20);
			
			//player.getCooldownTracker().setCooldown(stack.getItem(), 20);
			player.sendMessage(new TextComponentTranslation("attack.kill.spider"));
		}
		return super.hitEntity(stack, entity, player);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void applyCustomOpenGLHackery(ItemStack stack, TransformType cameraTransformType) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (cameraTransformType == TransformType.FIRST_PERSON_RIGHT_HAND) {
			GlStateManager.scale(2D, 2D, 2D);
			GlStateManager.translate(-0.07D, .15D, -0.07D);
			
			GlStateManager.rotate(-140.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(15.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
			
			//GlStateManager.scale(1.0D, 1.0D, 1.0D);
		}
		
		if (cameraTransformType == TransformType.GUI) {
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(6.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(5.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-1.3D, -14.0D, -0.02D);
			GlStateManager.scale(1.4D, 1.4D, 1.4D);
		}
		
	}
	
}
