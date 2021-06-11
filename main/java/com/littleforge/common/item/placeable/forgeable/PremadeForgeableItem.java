package com.littleforge.common.item.placeable.forgeable;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import com.creativemd.creativecore.client.rendering.RenderBox;
import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.littletiles.common.structure.registry.LittleStructureRegistry;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructureTypePremade;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.littleforge.common.item.placeable.PremadePlaceableItem;
import com.littleforge.common.recipe.forge.MetalTemperature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadeForgeableItem extends PremadePlaceableItem {
	
	public PremadeForgeableItem(String unlocalizedName, String registryName, String premadeToRender, String premadeToPlace) {
		super(unlocalizedName, registryName, premadeToRender, premadeToPlace);
		
	}
	
	/*
	 * NBTTagCompound nbt;
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (stack.hasTagCompound())
			nbt = stack.getTagCompound();
		else
			nbt = new NBTTagCompound();
		
		if (nbt.hasKey("Temperature")) {
			if (nbt.getInteger("Temperature") >= 1200)
				nbt.setInteger("Temperature", 0);
			else
				nbt.setInteger("Temperature", nbt.getInteger("Temperature") + 10);
			
		} else
			nbt.setInteger("Temperature", 0);
		stack.setTagCompound(nbt);
		
	 */
	
	@Override
	public void applyCustomOpenGLHackery(ItemStack stack, TransformType cameraTransformType) {
		GlStateManager.enableLighting();
		GlStateManager.enableBlend();
		GlStateManager.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, RenderHelper.setColorBuffer(1.0F, 1.0F, 1.0F, 0.0f));
		
		if (cameraTransformType == TransformType.FIRST_PERSON_LEFT_HAND) {
			GlStateManager.scale(2D, 2D, 2D);
			GlStateManager.translate(0.16D, 0.3D, -0.1D);
			
			GlStateManager.rotate(15.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(-5.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-80.0F, 0.0F, 0.0F, 1.0F);
			
			//GlStateManager.scale(1.0D, 1.0D, 1.0D);
		}
		
		if (cameraTransformType == TransformType.GUI)
			GlStateManager.scale(1.6D, 1.6D, 1.6D);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<RenderBox> getRenderingCubes(IBlockState state, TileEntity te, ItemStack stack) {
		
		LittleStructureTypePremade premade = (LittleStructureTypePremade) LittleStructureRegistry.getStructureType("wooden_tongs");
		LittleStructureTypePremade premade2 = (LittleStructureTypePremade) LittleStructureRegistry.getStructureType("dirty_iron");
		LittlePreviews previews = new LittlePreviews(LittleStructurePremade.getPreviews(premade.id)).copy();
		LittlePreviews previews2 = new LittlePreviews(LittleStructurePremade.getPreviews(premade2.id)).copy();
		
		//Changes color based off of NBT data. Gets color from MetalTemperature Enum.
		for (LittlePreview preview : previews2) {
			preview.getBox().add(6, 12, -7);
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Temperature")) {
				
				int nbtTemperature = stack.getTagCompound().getInteger("Temperature");
				MetalTemperature temperature = MetalTemperature.getEnumFromTemperature(nbtTemperature);
				MetalTemperature temperatureNext = temperature.getNext();
				Color color1 = ColorUtils.IntToRGBA(temperature.getColor());
				Color color2 = ColorUtils.IntToRGBA(temperatureNext.getColor());
				int steps = temperatureNext.getTemperatureMin() - temperature.getTemperatureMin();
				
				if (temperature == MetalTemperature.WHITE)
					steps = temperature.getTemperatureMax() - temperature.getTemperatureMin();
				
				int stepAt = nbtTemperature - temperature.getTemperatureMin();
				double ratio = (double) stepAt / (double) steps;
				
				int red = (int) (color2.getRed() * ratio + color1.getRed() * (1 - ratio));
				int green = (int) (color2.getGreen() * ratio + color1.getGreen() * (1 - ratio));
				int blue = (int) (color2.getBlue() * ratio + color1.getBlue() * (1 - ratio));
				Color stepColor = new Color(red, green, blue);
				//System.out.println(stepColor);
				preview.setColor(ColorUtils.RGBAToInt(stepColor));
			}
			previews.addPreview(null, preview, LittleGridContext.get(8));
		}
		List<RenderBox> cubes = null;
		if (cubes == null) {
			cubes = new ArrayList<>();
			
			for (LittlePreview preview : previews.allPreviews())
				cubes.add(preview.getCubeBlock(previews.getContext()));
			
			LittlePreview.shrinkCubesToOneBlock(cubes);
		}
		
		return cubes;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void saveCachedModel(EnumFacing facing, BlockRenderLayer layer, List<BakedQuad> cachedQuads, IBlockState state, TileEntity te, ItemStack stack, boolean threaded) {
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<BakedQuad> getCachedModel(EnumFacing facing, BlockRenderLayer layer, IBlockState state, TileEntity te, ItemStack stack, boolean threaded) {
		return null;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		NBTTagCompound nbt;
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (stack.hasTagCompound())
			nbt = stack.getTagCompound();
		else
			nbt = new NBTTagCompound();
		
		if (nbt.hasKey("Temperature")) {
			if (nbt.getInteger("Temperature") >= 1200)
				nbt.setInteger("Temperature", 0);
			else
				nbt.setInteger("Temperature", nbt.getInteger("Temperature") + 20);
			
		} else
			nbt.setInteger("Temperature", 0);
		stack.setTagCompound(nbt);
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Temperature")) {
			
			MetalTemperature temperature = MetalTemperature.getEnumFromTemperature(stack.getTagCompound().getInteger("Temperature"));
			tooltip.add(Integer.toString(stack.getTagCompound().getInteger("Temperature")));
			tooltip.add(temperature.getColorName());
		}
	}
}
