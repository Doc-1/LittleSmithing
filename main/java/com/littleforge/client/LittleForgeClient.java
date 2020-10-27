package com.littleforge.client;

import com.creativemd.creativecore.client.CreativeCoreClient;
import com.creativemd.creativecore.client.rendering.model.CreativeBlockRenderHelper;
import com.creativemd.littletiles.LittleTiles;
import com.creativemd.littletiles.client.render.world.LittleChunkDispatcher;
import com.creativemd.littletiles.common.item.ItemLittleChisel;
import com.creativemd.littletiles.common.item.ItemLittleGrabber;
import com.creativemd.littletiles.common.item.ItemRecipe;
import com.creativemd.littletiles.common.item.ItemRecipeAdvanced;
import com.creativemd.littletiles.server.LittleTilesServer;
import com.littleforge.CommonProxy;
import com.littleforge.LittleForge;
import com.littleforge.common.item.PremadeItem;
import com.littleforge.common.item.PremadeItemSword;
import com.littleforge.common.recipe.LittleForgeRecipes;
import com.littleforge.common.util.LeftClickBlockListener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class LittleForgeClient extends LittleTilesServer {
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void loadSidePost() {
		MinecraftForge.EVENT_BUS.register(LeftClickBlockListener.class);		

		CreativeCoreClient.registerItemColorHandler(LittleForge.sword);
		CreativeCoreClient.registerItemColorHandler(LittleForge.hammer);
		CreativeCoreClient.registerItemColorHandler(LittleForge.woodenTongs);
	}
	
	@Override
	public void loadSide() {
		

		CreativeCoreClient.registerItemRenderer(LittleForge.sword);
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.sword);

		CreativeCoreClient.registerItemRenderer(LittleForge.hammer);
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.hammer);

		CreativeCoreClient.registerItemRenderer(LittleForge.woodenTongs);
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.woodenTongs);
	}
}

/*
 * 
		CreativeBlockRenderHelper.registerCreativeRenderedItem(LittleForge.tongs);
		ModelLoader.setCustomModelResourceLocation(LittleForge.tongs, 0, new ModelResourceLocation(LittleForge.MODID + ":tongs", "inventory"));
		ModelLoader.setCustomModelResourceLocation(LittleForge.tongs, 1, new ModelResourceLocation(LittleForge.MODID + ":tongs_background", "inventory"));
 */


