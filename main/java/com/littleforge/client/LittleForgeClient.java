package com.littleforge.client;

import com.creativemd.creativecore.client.CreativeCoreClient;
import com.creativemd.creativecore.client.rendering.model.CreativeBlockRenderHelper;
import com.creativemd.littletiles.server.LittleTilesServer;
import com.littleforge.LittleForge;
import com.littleforge.common.event.LittleForgeEventHandler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class LittleForgeClient extends LittleTilesServer {
	
	Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void loadSidePost() {
		
		MinecraftForge.EVENT_BUS.register(LittleForgeEventHandler.class);
		
		CreativeCoreClient.registerItemColorHandler(LittleForge.sword);
		CreativeCoreClient.registerItemColorHandler(LittleForge.serpentSword);
		
		CreativeCoreClient.registerItemColorHandler(LittleForge.rolledUpBlueprint);
		CreativeCoreClient.registerItemColorHandler(LittleForge.soda);
		CreativeCoreClient.registerItemColorHandler(LittleForge.hammer);
		CreativeCoreClient.registerItemColorHandler(LittleForge.ironHammer);
		CreativeCoreClient.registerItemColorHandler(LittleForge.woodenTongs);
	}
	
	@Override
	public void loadSide() {
		
		CreativeCoreClient.registerItemRenderer(LittleForge.sword);
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.sword);
		
		CreativeCoreClient.registerItemRenderer(LittleForge.serpentSword);
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.serpentSword);
		
		CreativeCoreClient.registerItemRenderer(LittleForge.rolledUpBlueprint);
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.rolledUpBlueprint);
		
		CreativeCoreClient.registerItemRenderer(LittleForge.hammer);
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.hammer);
		
		CreativeCoreClient.registerItemRenderer(LittleForge.ironHammer);
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.ironHammer);
		
		CreativeCoreClient.registerItemRenderer(LittleForge.woodenTongs);
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.woodenTongs);
		
		CreativeCoreClient.registerItemRenderer(LittleForge.soda);
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.soda);
	}
}

/*
 * 
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.tongs);
		ModelLoader.setCustomModelResourceLocation(LittleForge.tongs, 0, new ModelResourceLocation(LittleForge.MODID + ":tongs", "inventory"));
		ModelLoader.setCustomModelResourceLocation(LittleForge.tongs, 1, new ModelResourceLocation(LittleForge.MODID + ":tongs_background", "inventory"));
 */
