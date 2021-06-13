package com.littleforge.common.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import com.creativemd.creativecore.client.rendering.RenderBox;
import com.creativemd.creativecore.client.rendering.model.ICreativeRendered;
import com.creativemd.littletiles.client.render.cache.ItemModelCache;
import com.creativemd.littletiles.common.structure.registry.LittleStructureRegistry;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructurePremadeEntry;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructureTypePremade;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadeItem extends Item implements ICreativeRendered {
	
	String registryName;
	
	public PremadeItem(String unlocalizedName, String registryNm) {
		registryName = registryNm;
		setUnlocalizedName(unlocalizedName);
		setRegistryName(registryNm);
		hasSubtypes = true;
		setMaxStackSize(1);
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
	public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<RenderBox> getRenderingCubes(IBlockState state, TileEntity te, ItemStack stack) {
		LittleStructureTypePremade premade = (LittleStructureTypePremade) LittleStructureRegistry.getStructureType(registryName);
		LittlePreviews previews = LittleStructurePremade.getPreviews(premade.id).copy();
		List<RenderBox> cubes = premade.getRenderingCubes(previews);
		if (cubes == null) {
			cubes = new ArrayList<>();
			for (LittlePreview preview : previews.allPreviews())
				cubes.add(preview.getCubeBlock(previews.getContext()));
			LittlePreview.shrinkCubesToOneBlock(cubes);
		}
		
		return cubes;
	}
	
	@SideOnly(Side.CLIENT)
	public static IBakedModel model;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void saveCachedModel(EnumFacing facing, BlockRenderLayer layer, List<BakedQuad> cachedQuads, IBlockState state, TileEntity te, ItemStack stack, boolean threaded) {
		if (stack != null)
			ItemModelCache.cacheModel(stack, facing, cachedQuads);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<BakedQuad> getCachedModel(EnumFacing facing, BlockRenderLayer layer, IBlockState state, TileEntity te, ItemStack stack, boolean threaded) {
		if (stack == null)
			return null;
		return ItemModelCache.requestCache(stack, facing);
	}
	
	private static HashMap<String, LittlePreviews> cachedPreviews = new HashMap<>();
	
	public static void clearCache() {
		cachedPreviews.clear();
	}
	
	public static String getPremadeId(ItemStack stack) {
		if (stack.hasTagCompound())
			return stack.getTagCompound().getCompoundTag("structure").getString("id");
		return null;
	}
	
	public static LittleStructurePremadeEntry getPremade(ItemStack stack) {
		if (stack.hasTagCompound())
			return LittleStructurePremade.getStructurePremadeEntry(stack.getTagCompound().getCompoundTag("structure").getString("id"));
		return null;
	}
}
