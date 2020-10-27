package com.littleforge.heated.structures;

import org.apache.http.auth.NTCredentials;

import com.creativemd.creativecore.common.utils.math.Rotation;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.action.block.LittleActionActivated;
import com.creativemd.littletiles.common.recipe.PremadeRecipeFactory;
import com.creativemd.littletiles.common.structure.attribute.LittleStructureAttribute;
import com.creativemd.littletiles.common.structure.directional.StructureDirectional;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;
import com.littleforge.common.item.PremadeItemWoodenTongs;
import com.littleforge.common.recipe.LittleForgeRecipes;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DirtyIronStructurePremade extends LittleStructurePremade{
	
	private int temperature = 0;
	
	public DirtyIronStructurePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
		temperature = nbt.getInteger("temperature");
	}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
		nbt.setInteger("temperature", temperature);
	}

	@Override
	public void tick() {
		temperature = 1001;
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBTExtra(nbt);
		System.out.println("tick!");
	}
}
