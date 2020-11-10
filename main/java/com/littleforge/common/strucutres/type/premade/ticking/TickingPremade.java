package com.littleforge.common.strucutres.type.premade.ticking;

import com.creativemd.creativecore.common.utils.math.Rotation;
import com.creativemd.littletiles.client.gui.controls.GuiDirectionIndicator;
import com.creativemd.littletiles.client.gui.controls.GuiTileViewer;
import com.creativemd.littletiles.common.action.LittleActionException;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.directional.StructureDirectional;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.tile.math.box.LittleBoxes;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVecContext;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.place.Placement;
import com.creativemd.littletiles.common.util.place.PlacementMode;
import com.creativemd.littletiles.common.util.place.PlacementPreview;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TickingPremade extends InteractivePremade{
	
	private int tick = 0;
	protected int tickMax;
	
	public TickingPremade(LittleStructureType type, IStructureTileList mainBlock, int tickMaxium, int seriesMax) {
		super(type, mainBlock);
		tickMax = tickMaxium;
		seriesMaxium = seriesMax;
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
		tick = nbt.getInteger("tick");
	}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
		nbt.setInteger("tick", tick);
	}

	@Override
	public void tick() {
		if (getWorld().isRemote)
			return;
		if(tickMax != 0) {
			tick++;
			try {	
				if (tick >= tickMax) {
					tick = 0;
					SurroundingBox box = getSurroundingBox();
					long minX = box.getMinX();
					long minY = box.getMinY();
					long minZ = box.getMinZ();
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
					//previews = updateStructureDirection(previews, box, min);
					
					this.removeStructure();
					PlacementPreview nextPremade = new PlacementPreview(this.getWorld(), previews, PlacementMode.normal, preview.box, false, min, LittleVec.ZERO, EnumFacing.NORTH);
					Placement place = new Placement(null, nextPremade);
					place.place();
				}
			}catch (CorruptedConnectionException | NotYetConnectedException e1) {
				e1.printStackTrace();
			} catch (LittleActionException e) {
				e.printStackTrace();
			}
		}
	}
}
