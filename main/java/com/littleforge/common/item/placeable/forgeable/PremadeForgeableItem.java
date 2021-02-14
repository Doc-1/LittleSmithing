package com.littleforge.common.item.placeable.forgeable;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderBox;
import com.creativemd.littletiles.common.structure.registry.LittleStructureRegistry;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructureTypePremade;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.littleforge.common.item.placeable.PremadePlaceableItem;
import com.littleforge.common.recipe.forge.MetalTemperature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.util.ITooltipFlag;
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
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public void applyCustomOpenGLHackery(ItemStack stack, TransformType cameraTransformType) {
		GlStateManager.enableLighting();
		GlStateManager.enableBlend();
		GlStateManager.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, RenderHelper.setColorBuffer(1.0F, 1.0F, 1.0F, 0.0f));
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<RenderBox> getRenderingCubes(IBlockState state, TileEntity te, ItemStack stack) {
		if (stack.getMetadata() == 1) {
			premadeToRender = "metal_block";
		}
		if (stack.getMetadata() == 0) {
			premadeToRender = "wooden_tongs";
		}
		LittleStructureTypePremade premade = (LittleStructureTypePremade) LittleStructureRegistry.getStructureType(premadeToRender);
		LittlePreviews previews = new LittlePreviews(LittleStructurePremade.getPreviews(premade.id)).copy();
		//Changes color based off of NBT data. Gets color from MetalTemperature Enum.
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Temperature")) {
			MetalTemperature temperature = MetalTemperature.getEnumFromTemperature(stack.getTagCompound().getInteger("Temperature"));
			for (LittlePreview preview : previews.allPreviews()) {
				preview.setColor(temperature.getColor());
			}
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
