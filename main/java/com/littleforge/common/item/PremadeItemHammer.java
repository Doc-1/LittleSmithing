package com.littleforge.common.item;

import com.creativemd.creativecore.client.rendering.model.ICreativeRendered;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadeItemHammer extends PremadeItem implements ICreativeRendered {
	
	public enum HammerType {
		BALLPEEN("ball_peen"), CROSSPEEN("cross_peen"), FORMING("forming"), CREASING("creasing"), RAISING("raising");
		
		private String name;
		
		private HammerType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
	}
	
	public PremadeItemHammer(String unlocalizedName, String registryNm, HammerType type, int durability) {
		super(unlocalizedName, registryNm);
		setMaxDamage(durability);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void applyCustomOpenGLHackery(ItemStack stack, TransformType cameraTransformType) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (cameraTransformType == TransformType.FIRST_PERSON_RIGHT_HAND) {
			GlStateManager.scale(2D, 2D, 2D);
			GlStateManager.translate(-0.04D, .15D, -0D);
			GlStateManager.rotate(-140.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(15.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
			//GlStateManager.scale(1.0D, 1.0D, 1.0D);
		}
		
		if (cameraTransformType == TransformType.GUI)
			GlStateManager.scale(1.6D, 1.6D, 1.6D);
		
		if (cameraTransformType == TransformType.GUI) {
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(6.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(5.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-1.3D, -14.0D, -0.02D);
			GlStateManager.scale(1.4D, 1.4D, 1.4D);
		}
		
	}
	
}
