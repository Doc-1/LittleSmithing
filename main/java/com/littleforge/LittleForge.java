package com.littleforge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.Loader;

import java.awt.Color;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.littleforge.config.IGCMLoader;
import com.littleforge.multitile.MultiTileClayForge;
import com.littleforge.multitile.MultiTileStructure;
import com.littleforge.multitile.MultiTileStructureRegistry;
import com.creativemd.creativecore.common.gui.container.SubContainer;
import com.creativemd.creativecore.common.gui.container.SubGui;
import com.creativemd.creativecore.common.gui.opener.CustomGuiHandler;
import com.creativemd.creativecore.common.gui.opener.GuiHandler;
import com.creativemd.littletiles.LittleTiles;
import com.creativemd.littletiles.client.gui.SubGuiRecipe;
import com.creativemd.littletiles.client.gui.SubGuiRecipeAdvancedSelection;
import com.creativemd.littletiles.common.container.SubContainerRecipeAdvanced;
import com.creativemd.littletiles.common.item.ItemRecipeAdvanced;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.attribute.LittleStructureAttribute;
import com.creativemd.littletiles.common.structure.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.structure.registry.LittleStructureRegistry;
import com.creativemd.littletiles.common.structure.type.LittleBed;
import com.creativemd.littletiles.common.structure.type.door.LittleAxisDoor;
import com.littleforge.server.LittleForgeServer;

@Mod(modid = LittleForge.MODID, name = LittleForge.NAME, version = LittleForge.VERSION)
@Mod.EventBusSubscriber
public class LittleForge
{
	
	@SidedProxy(clientSide = "com.littleforge.client.LittleForgeClient", serverSide = "com.littleforge.server.LittleForgeServer")
	public static CommonProxy proxy;
	
    public static final String MODID = "littleforge";
    public static final String NAME = "Little Forge";
    public static final String VERSION = "1.0";
    
	
	
    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
    	proxy.preInit(event);
    }
    
    
    @SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
    	
	}
    
    @SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {

    }
	
	private static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
    
    @EventHandler
    public void Init(FMLInitializationEvent event) {    	
    	LittleStructurePremade.registerPremadeStructureType("clayForge_1", LittleForge.MODID, MultiTileStructure.class);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_2", LittleForge.MODID, MultiTileStructure.class);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_3", LittleForge.MODID, MultiTileStructure.class);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_4", LittleForge.MODID, MultiTileStructure.class);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_5", LittleForge.MODID, MultiTileStructure.class);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_6", LittleForge.MODID, MultiTileStructure.class);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_7", LittleForge.MODID, MultiTileStructure.class);

    	//LittleStructurePremade.registerPremadeStructureType("clayForge_7", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_8", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_9", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_10", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_11", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_12", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	LittleStructurePremade.registerPremadeStructureType("clayForge_13", LittleForge.MODID, MultiTileStructure.class);

    	
    	
    	//LittleStructurePremade.registerPremadeStructureType("clayForge_6", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING );


    	//MultiTileStructureRegistry.registerPremadeStructureType("clayForge", LittleForge.MODID, LittlePhotoImporter.class,6); 
    	MultiTileStructureRegistry.addRecipe("clayForge_1", Items.CLAY_BALL, 8);
    	MultiTileStructureRegistry.addRecipe("clayForge_2", Items.CLAY_BALL, 5);
    	MultiTileStructureRegistry.addRecipe("clayForge_3", Items.CLAY_BALL, 5);
    	MultiTileStructureRegistry.addRecipe("clayForge_4", Items.CLAY_BALL, 5);
    	MultiTileStructureRegistry.addRecipe("clayForge_5", Items.FLINT, 1);
    	MultiTileStructureRegistry.addRecipe("clayForge_6", Blocks.IRON_ORE, 1);
    	MultiTileStructureRegistry.addRecipe("clayForge_7", Items.STICK, 64);
    	
    	if (Loader.isModLoaded("igcm"))
			IGCMLoader.initIGCM();
    }
    
    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
    }
}
