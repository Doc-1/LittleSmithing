package com.littleforge.client;

import java.util.ArrayList;

import com.creativemd.creativecore.client.CreativeCoreClient;
import com.creativemd.creativecore.client.rendering.model.CreativeBlockRenderHelper;
import com.creativemd.littletiles.server.LittleTilesServer;
import com.littleforge.common.event.LittleForgeEventHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class LittleForgeClient extends LittleTilesServer {
	
	Minecraft mc = Minecraft.getMinecraft();
	private static ArrayList<Item> renderedItems = new ArrayList<Item>();
	
	public static void addItemToRenderTiles(Item... items) {
		for (Item item : items) {
			renderedItems.add(item);
		}
	}
	
	@Override
	public void loadSidePost() {
		MinecraftForge.EVENT_BUS.register(LittleForgeEventHandler.class);
		for (Item item : renderedItems) {
			CreativeCoreClient.registerItemColorHandler(item);
		}
	}
	
	@Override
	public void loadSide() {
		for (Item item : renderedItems) {
			if (item.getHasSubtypes()) {
				registerItemRenderer(item);
				CreativeBlockRenderHelper.registerCreativeRenderedItem(item);
			} else {
				CreativeCoreClient.registerItemRenderer(item);
				CreativeBlockRenderHelper.registerCreativeRenderedItem(item);
			}
		}
		
	}
	
	public static void registerItemRenderer(Item item) {
		for (int i = 0; i <= 18; i++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}

/*
 * 
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.tongs);
		ModelLoader.setCustomModelResourceLocation(LittleForge.tongs, 0, new ModelResourceLocation(LittleForge.MODID + ":tongs", "inventory"));
		ModelLoader.setCustomModelResourceLocation(LittleForge.tongs, 1, new ModelResourceLocation(LittleForge.MODID + ":tongs_background", "inventory"));
 */
