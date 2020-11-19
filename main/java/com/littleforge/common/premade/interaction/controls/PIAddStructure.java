package com.littleforge.common.premade.interaction.controls;

import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVecContext;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.place.Placement;
import com.creativemd.littletiles.common.util.place.PlacementMode;
import com.creativemd.littletiles.common.util.place.PlacementPreview;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;
import com.littleforge.common.premade.interaction.PremadeInteractionControl;
import com.littleforge.common.recipe.LittleForgeRecipes;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentTranslationFormatException;

public class PIAddStructure extends PremadeInteractionControl{
	
	private boolean removeStructure = false;

	public PIAddStructure(InteractivePremade premade, String id, int minX, int minY, int minZ, boolean removeStructure) {
		super(premade, id, minX, minY, minZ, 0, 0, 0);
		this.removeStructure = removeStructure;
	}
	
	@Override
	public void onActivated() {
		addStructure();
	}
	
	private void addStructure() {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(LittleForgeRecipes.takeIngredients(player, premade.type.id)) {
			try {
				long minX = premade.getSurroundingBox().getMinX();
				long minY = premade.getSurroundingBox().getMinY();
				long minZ = premade.getSurroundingBox().getMinZ();
				
				LittleGridContext context = premade.getSurroundingBox().getContext();
				BlockPos min = new BlockPos(context.toBlockOffset(minX), context.toBlockOffset(minY), context.toBlockOffset(minZ));
				LittleVecContext minVec = new LittleVecContext(new LittleVec((int) (minX - (long) min.getX() * (long) context.size), (int) (minY - (long) min.getY() * (long) context.size), (int) (minZ - (long) min.getZ() * (long) context.size)), context);
				
				LittlePreviews previews = premade.getStructurePremadeEntry("exporter").previews.copy(); // Change this line to support different states
				LittleVec previewMinVec = previews.getMinVec();
				LittlePreview preview = null;
				minVec.forceContext(previews);
				for (LittlePreview prev : previews) {
					prev.box.sub(previewMinVec);
					prev.box.add(minVec.getVec());
					prev.box.add(new LittleVec(editArea.minX, editArea.minY, editArea.minZ));
					preview = prev;
				}
				previews.convertToSmallest();
				PlacementPreview nextPremade = new PlacementPreview(premade.getWorld(), previews, PlacementMode.all, preview.box, false, min, LittleVec.ZERO, EnumFacing.NORTH);
				
				if(removeStructure) {
					premade.removeStructure();
				}
				
				Placement place = new Placement(null, nextPremade);
				if(place.tryPlace() == null) {
					player.sendStatusMessage(new TextComponentTranslation("structure.interaction.structurecollision").appendText(min.getX() + ", " + min.getY() + ", " + (min.getZ()+1)), true);
				}
				
				
			}catch (CorruptedConnectionException | NotYetConnectedException e1) {
				e1.printStackTrace();
			}
		}
	}


	
}
