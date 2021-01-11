package com.littleforge.common.item.placeable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.creativemd.creativecore.client.rendering.RenderBox;
import com.creativemd.creativecore.client.rendering.model.ICreativeRendered;
import com.creativemd.littletiles.client.gui.configure.SubGuiConfigure;
import com.creativemd.littletiles.client.gui.configure.SubGuiModeSelector;
import com.creativemd.littletiles.client.render.cache.ItemModelCache;
import com.creativemd.littletiles.common.api.ILittleTile;
import com.creativemd.littletiles.common.item.ItemLittleChisel;
import com.creativemd.littletiles.common.item.ItemMultiTiles;
import com.creativemd.littletiles.common.structure.registry.LittleStructureRegistry;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructurePremadeEntry;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructureTypePremade;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.place.PlacementMode;
import com.creativemd.littletiles.common.util.place.PlacementPosition;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadePlaceableItem extends Item implements ILittleTile, ICreativeRendered {
	
	public String premadeToPlace = "";
	public String premadeToRender = "";
	public boolean isShifting = false;
	
	public PremadePlaceableItem(String unlocalizedName, String registryName, String premadeToRender, String premadeToPlace) {
		this.premadeToPlace = premadeToPlace;
		this.premadeToRender = premadeToRender;
		setUnlocalizedName(unlocalizedName);
		setRegistryName(registryName);
		hasSubtypes = true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<RenderBox> getRenderingCubes(IBlockState state, TileEntity te, ItemStack stack) {
		LittleStructureTypePremade premade = (LittleStructureTypePremade) LittleStructureRegistry.getStructureType(premadeToRender);
		LittlePreviews previews = LittleStructurePremade.getPreviews(premade.id).copy();
		List<RenderBox> cubes = premade.getRenderingCubes(previews);
		if (cubes == null) {
			cubes = new ArrayList<>();
			
			for (LittlePreview preview : previews.allPreviews())
				cubes.add(preview.getCubeBlock(previews.getContext()));
		}
		return cubes;
	}
	
	@SideOnly(Side.CLIENT)
	public static IBakedModel model;
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		isShifting = entityIn.isSneaking();
	}
	
	@Override
	public boolean onRightClick(World world, EntityPlayer player, ItemStack stack, PlacementPosition position, RayTraceResult result) {
		if (player.isSneaking()) {
			return true;
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public LittleGridContext getPositionContext(ItemStack stack) {
		return ItemMultiTiles.currentContext;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiConfigure getConfigureGUIAdvanced(EntityPlayer player, ItemStack stack) {
		return new SubGuiModeSelector(stack, ItemMultiTiles.currentContext, ItemLittleChisel.currentMode) {
			
			@Override
			public void saveConfiguration(LittleGridContext context, PlacementMode mode) {
				ItemLittleChisel.currentMode = mode;
				ItemMultiTiles.currentContext = context;
			}
		};
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void saveCachedModel(EnumFacing facing, BlockRenderLayer layer, List<BakedQuad> cachedQuads, IBlockState state, TileEntity te, ItemStack stack, boolean threaded) {
		stack = LittleStructurePremade.getPremadeStack(premadeToPlace);
		if (stack != null)
			ItemModelCache.cacheModel(getPremade(stack).stack, facing, cachedQuads);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<BakedQuad> getCachedModel(EnumFacing facing, BlockRenderLayer layer, IBlockState state, TileEntity te, ItemStack stack, boolean threaded) {
		stack = LittleStructurePremade.getPremadeStack(premadeToPlace);
		if (stack == null)
			return null;
		LittleStructurePremadeEntry entry = getPremade(stack);
		if (entry == null)
			return null;
		return ItemModelCache.requestCache(entry.stack, facing);
	}
	
	@Override
	public boolean hasLittlePreview(ItemStack stack) {
		return isShifting;
	}
	
	public void removeUnnecessaryData(ItemStack stack) {
		if (stack.hasTagCompound()) {
			stack.getTagCompound().removeTag("tiles");
			stack.getTagCompound().removeTag("size");
			stack.getTagCompound().removeTag("min");
		}
	}
	
	private static HashMap<String, LittlePreviews> cachedPreviews = new HashMap<>();
	
	public static void clearCache() {
		cachedPreviews.clear();
	}
	
	@Override
	public LittlePreviews getLittlePreview(ItemStack stack) {
		String id = getPremadeId(LittleStructurePremade.tryGetPremadeStack(this.premadeToPlace));
		if (cachedPreviews.containsKey(id))
			return cachedPreviews.get(id).copy();
		return LittleStructurePremade.getPreviews(id).copy();
	}
	
	@Override
	public void saveLittlePreview(ItemStack stack, LittlePreviews previews) {
		cachedPreviews.put(getPremadeId(LittleStructurePremade.tryGetPremadeStack(this.premadeToPlace)), previews);
	}
	
	@Override
	public boolean sendTransformationUpdate() {
		return false;
	}
	
	@Override
	public boolean containsIngredients(ItemStack stack) {
		return true;
	}
	
	@Override
	public PlacementMode getPlacementMode(ItemStack stack) {
		if (!ItemMultiTiles.currentMode.canPlaceStructures())
			return PlacementMode.getStructureDefault();
		return ItemMultiTiles.currentMode;
	}
	
	@Override
	public boolean shouldCache() {
		return false;
	}
	
	@Override
	public boolean snapToGridByDefault() {
		return true;
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
