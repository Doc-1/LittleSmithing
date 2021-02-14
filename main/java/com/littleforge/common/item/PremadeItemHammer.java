package com.littleforge.common.item;

import com.creativemd.creativecore.client.rendering.model.ICreativeRendered;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadeItemHammer extends PremadeItem implements ICreativeRendered {
	
	public PremadeItemHammer(String unlocalizedName, String registryNm) {
		super(unlocalizedName, registryNm);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void applyCustomOpenGLHackery(ItemStack stack, TransformType cameraTransformType) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (cameraTransformType == TransformType.FIRST_PERSON_RIGHT_HAND) {
			GlStateManager.translate(-0.07D, -0.07D, -0.07D);
			
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
