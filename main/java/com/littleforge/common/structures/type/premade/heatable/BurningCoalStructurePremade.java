package com.littleforge.common.structures.type.premade.heatable;

import javax.vecmath.Vector3d;

import com.creativemd.creativecore.common.world.IOrientatedWorld;
import com.creativemd.littletiles.common.item.ItemLittleWrench;
import com.creativemd.littletiles.common.particle.LittleParticle;
import com.creativemd.littletiles.common.particle.LittleParticlePresets;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.structure.type.premade.LittleParticleEmitter.ParticleSettings;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BurningCoalStructurePremade extends InteractivePremade {
	
	int tick = 0;
	
	public BurningCoalStructurePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void tick() {
		if (getWorld().isRemote) {
			tick++;
			if (tick >= 3) {
				tick = 0;
				spawnParticle(getWorld());
			}
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	public void spawnParticle(World world) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemLittleWrench || Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() instanceof ItemLittleWrench)
			return;
		
		try {
			AxisAlignedBB bb = getSurroundingBox().getAABB();
			Vector3d pos = new Vector3d(0, 0.7, 0);
			Vector3d speed = new Vector3d(0, 0.0005, 0);
			
			ParticleSettings settings = LittleParticlePresets.SMOKE.settings;
			settings.lifetime = 200;
			settings.endSize = 2.0F;
			settings.randomColor = false;
			double randX = (Math.random() * (0.2 - (-0.2)) + (-0.2));
			double randZ = (Math.random() * (0.2 - (-0.2)) + (-0.2));
			System.out.println(direction);
			switch (this.direction) {
			case NORTH:
				
				break;
			case EAST:
				
				break;
			case SOUTH:
				speed.z += 0.03;
				randX = (Math.random() * (0.1 - (-0.2)) + (-0.2));
				break;
			case WEST:
				
				break;
			
			default:
				break;
			}
			
			pos.x += randX;
			pos.z += randZ;
			pos.x *= bb.maxX - bb.minX;
			pos.y *= bb.maxY - bb.minY;
			pos.z *= bb.maxZ - bb.minZ;
			pos.x += (bb.minX + bb.maxX) / 2;
			pos.y += (bb.minY + bb.maxY) / 2;
			pos.z += (bb.minZ + bb.maxZ) / 2;
			
			if (world instanceof IOrientatedWorld) {
				((IOrientatedWorld) world).getOrigin().transformPointToWorld(pos);
				((IOrientatedWorld) world).getOrigin().onlyRotateWithoutCenter(speed);
			}
			
			mc.effectRenderer.addEffect(new LittleParticle(world, pos, speed, settings));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onPremadeActivated(EntityPlayer playerIn, ItemStack heldItem) {
		// TODO Auto-generated method stub
		
	}
}
