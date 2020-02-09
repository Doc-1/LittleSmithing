package com.littleforge.config;

import com.creativemd.igcm.api.ConfigBranch;
import com.creativemd.igcm.api.ConfigTab;
import com.creativemd.igcm.api.segments.BooleanSegment;
import com.creativemd.igcm.api.segments.FloatSegment;
import com.creativemd.igcm.api.segments.IntegerSegment;
import com.creativemd.igcm.api.segments.IntegerSliderSegment;
import com.creativemd.littletiles.LittleTiles;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

public class IGCMLoader {
	
	public static void initIGCM() {
		ConfigTab.root.registerElement("ltphoto", new ConfigBranch("LTPhoto", new ItemStack(LittleTiles.screwdriver)) {
			
			@Override
			public void saveExtra(NBTTagCompound nbt) {
				
			}
			
			@Override
			public void loadExtra(NBTTagCompound nbt) {
				
			}
			
			@Override
			public boolean requiresSynchronization() {
				return true;
			}
			
			@Override
			public void onRecieveFrom(Side side) {
				
			}

			@Override
			public void createChildren() {
			
			}
				
		});
	}
}
