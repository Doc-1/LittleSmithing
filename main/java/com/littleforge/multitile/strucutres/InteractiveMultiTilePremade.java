
package com.littleforge.multitile.strucutres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.creativecore.common.utils.type.HashMapList;
import com.creativemd.creativecore.common.utils.type.Pair;
import com.creativemd.littletiles.common.action.LittleAction;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.action.block.LittleActionPlaceStack;
import com.creativemd.littletiles.common.packet.LittleActionMessagePacket;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.registry.LittleStructureRegistry;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVecContext;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.tile.parent.StructureTileList;
import com.creativemd.littletiles.common.tile.place.PlacePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTilesTicking;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.ingredient.LittleIngredients;
import com.creativemd.littletiles.common.util.ingredient.LittleInventory;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException;
import com.creativemd.littletiles.common.util.ingredient.StackIngredient;
import com.creativemd.littletiles.common.util.ingredient.StackIngredientEntry;
import com.creativemd.littletiles.common.util.place.Placement;
import com.creativemd.littletiles.common.util.place.PlacementMode;
import com.creativemd.littletiles.common.util.place.PlacementPreview;
import com.creativemd.littletiles.common.util.tooltip.ActionMessage;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;
import com.littleforge.LittleForge;
import com.littleforge.common.recipe.LittleForgeRecipes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import scala.reflect.internal.Trees.This;
/**
 * Premade Structure that allows right click to add or change structure
 * @author _Doc
 * 
 */
public abstract class InteractiveMultiTilePremade extends MultiTilePremade {
	
	public InteractiveMultiTilePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		seriesMaxium = 10;
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {}
	
	@Override
	public boolean onBlockActivated(World worldIn, LittleTile tile, BlockPos pos, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ, LittleActionActivated action) throws LittleActionException {
		if(worldIn.isRemote)
			return true;
		
		System.out.println(this.getAttribute());
		String next = nextSeries();
		if(!next.isEmpty()) {
			if(LittleForgeRecipes.takeIngredients(playerIn, type.id)) {
				try {
					SurroundingBox box = getSurroundingBox();
					long minX = box.getMinX();
					long minY = box.getMinY();
					long minZ = box.getMinZ();
					
					long maxX = box.getMaxX();
					long maxY = box.getMaxY();
					long maxZ = box.getMaxZ();
					LittleGridContext context = box.getContext();
					BlockPos min = new BlockPos(context.toBlockOffset(minX), context.toBlockOffset(minY), context.toBlockOffset(minZ));
					LittleVecContext minVec = new LittleVecContext(new LittleVec((int) (minX - (long) min.getX() * (long) context.size), (int) (minY - (long) min.getY() * (long) context.size), (int) (minZ - (long) min.getZ() * (long) context.size)), context);
					
					LittlePreviews previews = getStructurePremadeEntry(nextSeries()).previews.copy(); // Change this line to support different states
					LittleVec previewMinVec = previews.getMinVec();
					LittlePreview preview = null;
					minVec.forceContext(previews);
					for (LittlePreview prev : previews) {
						prev.box.sub(previewMinVec);
						prev.box.add(minVec.getVec());
						preview = prev;
					}
					
					previews.convertToSmallest();
					previews = updateStructureDirection(previews, box, min);
			
					this.removeStructure();
					PlacementPreview nextPremade = new PlacementPreview(this.getWorld(), previews, PlacementMode.normal, preview.box, false, min, LittleVec.ZERO, EnumFacing.NORTH);
					Placement place = new Placement(null, nextPremade);
					place.place();
					
				}catch (CorruptedConnectionException | NotYetConnectedException e1) {
					e1.printStackTrace();
				} catch (LittleActionException e) {
					e.printStackTrace();
				}
			}
		}
	return true;
	}
}
