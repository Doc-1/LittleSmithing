package com.littleforge.common.item.placeable.weapon;

import java.util.List;

import javax.annotation.Nullable;

import com.creativemd.littletiles.LittleTiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadeItemSword45 extends PremadePlaceableItemWeapon {
	
	public PremadeItemSword45(ToolMaterial material, String unlocalizedName, String registryName, String premadeToRender, String premadeToPlace) {
		super(material, unlocalizedName, registryName, premadeToRender, premadeToPlace);
		setCreativeTab(LittleTiles.littleTab);
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return 0F;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		return super.onLeftClickEntity(stack, player, entity);
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
