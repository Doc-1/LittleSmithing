package com.littleforge.common.strucutres.type.premade.assambly;

import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.LittleSmithingConfig;
import com.littleforge.common.packet.PacketUpdateStructureFromClient;
import com.littleforge.common.premade.interaction.actions.BuildProgress;
import com.littleforge.common.recipe.LittleForgeRecipes;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BrickForgePremade extends InteractivePremade {
	
	public BrickForgePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		assemblyRecipe = LittleSmithingConfig.brickForgeRecipe.brickForgeBasic;
		seriesMaxium = assemblyRecipe.size();
	}
	
	@Override
	public void onPremadeActivated(EntityPlayer playerIn, ItemStack heldItem) {
		
		if (seriesAt <= seriesMaxium) {
			if (LittleForgeRecipes.takeIngredients(playerIn, this.type.id, seriesAt, assemblyRecipe)) {
				this.editArea = new LittleBox(0, 0, 0, 34, (int) (9.6 * (seriesAt)), 16);
				BuildProgress.forPremade(this);
				NBTTagCompound nbt = new NBTTagCompound();
				this.writeToNBT(nbt);
				this.seriesAt++;
				nbt.setInteger("at", seriesAt);
				PacketHandler.sendPacketToServer(new PacketUpdateStructureFromClient(this.getStructureLocation(), nbt));
			}
		} else {
			
		}
		
		//editArea = new LittleBox(0, 0, 0, 34, 8, 16);
		//BuildProgress.forPremade(this);
	}
}
