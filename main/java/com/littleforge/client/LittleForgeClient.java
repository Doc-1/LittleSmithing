package com.littleforge.client;

import com.littleforge.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class LittleForgeClient extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {

	}
}
