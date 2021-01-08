package com.littleforge.common.premade.interaction.actions;

import com.creativemd.creativecore.common.utils.math.Rotation;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.relative.StructureRelative;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVecContext;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.place.Placement;
import com.creativemd.littletiles.common.util.place.PlacementMode;
import com.creativemd.littletiles.common.util.place.PlacementPreview;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;
import com.littleforge.common.recipe.LittleForgeRecipes;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentTranslationFormatException;

public abstract class AddStructure {
	
	
	public static LittleVec adjustEditArea(LittleBox editArea, EnumFacing facing) {
		
		switch (facing) {
		case NORTH:
			break;
		case SOUTH:
			break;
		case WEST:
			break;
		default:
			break;
		}
		
		return null;
	}
	
	public static void toPremade(InteractivePremade premade, boolean removeStructure) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		System.out.println(premade.direction);
		if(LittleForgeRecipes.takeIngredients(player, premade.type.id)) {
			try {
				long minX = premade.getSurroundingBox().getMinX();
				long minY = premade.getSurroundingBox().getMinY();
				long minZ = premade.getSurroundingBox().getMinZ();
				
				LittleGridContext context = premade.getSurroundingBox().getContext();
				BlockPos min = new BlockPos(context.toBlockOffset(minX), context.toBlockOffset(minY), context.toBlockOffset(minZ));
				LittleVecContext minVec = new LittleVecContext(new LittleVec((int) (minX - (long) min.getX() * (long) context.size), (int) (minY - (long) min.getY() * (long) context.size), (int) (minZ - (long) min.getZ() * (long) context.size)), context);
				
				LittlePreviews previews = LittleStructurePremade.getStructurePremadeEntry("soda").previews.copy(); // Change this line to support different states
				LittleVec previewMinVec = previews.getMinVec();
				LittlePreview preview = null;
				minVec.forceContext(previews);
				for (LittlePreview prev : previews) {
					prev.box.sub(previewMinVec);
					prev.box.add(minVec.getVec());
					prev.box.add(new LittleVec(premade.getEditArea().minX, premade.getEditArea().minY, premade.getEditArea().minZ));
					preview = prev;
				}
				//previews.convertToSmallest();
				//previews.rotatePreviews(Rotation.X_CLOCKWISE, new StructureRelative(previews.getSurroundingBox(), LittleGridContext.get(32)).getDoubledCenterVec());
				PlacementPreview nextPremade = new PlacementPreview(premade.getWorld(), previews, PlacementMode.all, preview.box, false, min, LittleVec.ZERO, EnumFacing.NORTH);
				
				if(removeStructure) {
					premade.removeStructure();
				}
				
				
				
				
			}catch (CorruptedConnectionException | NotYetConnectedException e1) {
				e1.printStackTrace();
			}
		}
	}


	
}
