package com.littleforge.common.item.placeable.weapon;

import java.util.List;

import javax.annotation.Nullable;

import com.creativemd.littletiles.LittleTiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadeItemSword25 extends PremadePlaceableItemWeapon {
	
	public PremadeItemSword25(ToolMaterial material, String unlocalizedName, String registryName, String premadeToRender, String premadeToPlace) {
		super(material, unlocalizedName, registryName, premadeToRender, premadeToPlace);
		setCreativeTab(LittleTiles.littleTab);
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return 0F;
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
			GlStateManager.scale(2.2D, 2.2D, 2.2D);
			GlStateManager.translate(0.05D, .05D, -0.02D);
			
			GlStateManager.rotate(99.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(-32.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(85.0F, 0.0F, 0.0F, 1.0F);
			
			//GlStateManager.scale(1.0D, 1.0D, 1.0D);
		}
		
		if (cameraTransformType == TransformType.GUI)
			GlStateManager.scale(1.6D, 1.6D, 1.6D);
		
	}
}
