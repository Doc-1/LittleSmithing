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
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.util.EnumHelper;
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

import com.littleforge.common.item.PremadeItemStoneHammer;
import com.littleforge.common.item.ItemStructurePremade;
import com.littleforge.common.item.PremadeItem;
import com.littleforge.common.item.PremadeItemIronSludgeHammer;
import com.littleforge.common.item.PremadeItemSword;
import com.littleforge.common.item.PremadeItemWoodenTongs;
import com.littleforge.common.recipe.LittleForgeRecipes;
import com.littleforge.heated.structures.DirtyIronStructurePremade;
import com.littleforge.multitile.strucutres.BrickForgeInteractiveMultiTilePremade;
import com.littleforge.multitile.strucutres.ClaySmelteryInteractiveMultiTilePremade;
import com.littleforge.multitile.strucutres.ClaySmelteryTickingMultiTilePremade;
import com.littleforge.multitile.strucutres.InteractiveMultiTilePremade;
import com.littleforge.multitile.strucutres.MultiTilePremade;
import com.littleforge.multitile.strucutres.TestDoorPremade;
import com.littleforge.premade.structures.StoneAnvilStructurePremade;
import com.creativemd.creativecore.common.config.holder.CreativeConfigRegistry;
import com.creativemd.creativecore.common.gui.container.SubContainer;
import com.creativemd.creativecore.common.gui.container.SubGui;
import com.creativemd.creativecore.common.gui.opener.CustomGuiHandler;
import com.creativemd.creativecore.common.gui.opener.GuiHandler;
import com.creativemd.littletiles.LittleTiles;
import com.creativemd.littletiles.client.gui.SubGuiRecipe;
import com.creativemd.littletiles.client.gui.SubGuiRecipeAdvancedSelection;
import com.creativemd.littletiles.common.container.SubContainerRecipeAdvanced;
import com.creativemd.littletiles.common.item.ItemPremadeStructure;
import com.creativemd.littletiles.common.item.ItemRecipeAdvanced;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.attribute.LittleStructureAttribute;
import com.creativemd.littletiles.common.structure.registry.LittleStructureRegistry;
import com.creativemd.littletiles.common.structure.type.LittleBed;
import com.creativemd.littletiles.common.structure.type.door.LittleAxisDoor;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructureTypePremade;
import com.creativemd.littletiles.server.LittleTilesServer;
import com.littleforge.server.LittleForgeServer;

@Mod(modid = LittleForge.MODID, name = LittleForge.NAME, version = LittleForge.VERSION, guiFactory = "com.littleforge.client.LittleSmithingSettings", dependencies = "required-after:creativecore;required-after:littletiles")
@Mod.EventBusSubscriber
public class LittleForge {
	@SidedProxy(clientSide = "com.littleforge.client.LittleForgeClient", serverSide = "com.littleforge.server.LittleForgeServer")
	public static LittleTilesServer proxy;
	
	public static LittleSmithingConfig CONFIG;

    public static final String MODID = "littleforge";
    public static final String NAME = "Little Forge";
    public static final String VERSION = "1.0";
    
	public static ItemSword sword;
	public static Item hammer;
	public static Item ironHammer;
	public static Item woodenTongs;

    public static final ToolMaterial Test = EnumHelper.addToolMaterial(MODID, 3, 250, 8.0F, 100.0F, 10);
    
    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
    	proxy.loadSidePre();
		sword = new PremadeItemSword(Test ,"Sword", "sword");
		hammer = new PremadeItemStoneHammer("StoneHammer", "stone_hammer");
		ironHammer = new PremadeItemIronSludgeHammer("IronHammer", "iron_sludge_hammer");
		woodenTongs = new PremadeItemWoodenTongs("WoodenTongs", "wooden_tongs");
    }
    
    @SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(sword, hammer, woodenTongs, ironHammer);
		proxy.loadSide();
	}
    
    @SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		
    }
	
	private static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
    @EventHandler
    public void Init(FMLInitializationEvent event) {
		CreativeConfigRegistry.ROOT.registerValue(MODID, CONFIG = new LittleSmithingConfig());
		
    	LittleStructurePremade.registerPremadeStructureType("dirty_iron", LittleForge.MODID, DirtyIronStructurePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);

    	LittleStructurePremade.registerPremadeStructureType("stone_anvil", LittleForge.MODID, StoneAnvilStructurePremade.class);
    	
    	LittleStructurePremade.registerPremadeStructureType("sword", LittleForge.MODID, ItemStructurePremade.class);
    	LittleStructurePremade.registerPremadeStructureType("stone_hammer", LittleForge.MODID, ItemStructurePremade.class);
    	LittleStructurePremade.registerPremadeStructureType("iron_sludge_hammer", LittleForge.MODID, ItemStructurePremade.class);
    	LittleStructurePremade.registerPremadeStructureType("wooden_tongs", LittleForge.MODID, ItemStructurePremade.class);
    	LittleStructurePremade.registerPremadeStructureType("wooden_tongs_dirtyiron", LittleForge.MODID, ItemStructurePremade.class);
    	
    	MultiTilePremade.registerPremadeStructureType("brickForgeBasic_1", LittleForge.MODID, BrickForgeInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("brickForgeBasic_2", LittleForge.MODID, BrickForgeInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("brickForgeBasic_3", LittleForge.MODID, BrickForgeInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("brickForgeBasic_4", LittleForge.MODID, BrickForgeInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("brickForgeBasic_5", LittleForge.MODID, BrickForgeInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("brickForgeBasic_6", LittleForge.MODID, BrickForgeInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("brickForgeBasic_7", LittleForge.MODID, BrickForgeInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("brickForgeBasic_8", LittleForge.MODID, BrickForgeInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("brickForgeBasic_9", LittleForge.MODID, BrickForgeInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("brickForgeBasic_10", LittleForge.MODID, BrickForgeInteractiveMultiTilePremade.class);
    	
    	MultiTilePremade.registerPremadeStructureType("clayForge_1", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("clayForge_2", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("clayForge_3", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("clayForge_4", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("clayForge_5", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("clayForge_6", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
    	MultiTilePremade.registerPremadeStructureType("clayForge_7", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);

    	//LittleStructurePremade.registerPremadeStructureType("clayForge_7", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	MultiTilePremade.registerPremadeStructureType("clayForge_8", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	MultiTilePremade.registerPremadeStructureType("clayForge_9", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	MultiTilePremade.registerPremadeStructureType("clayForge_10", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	MultiTilePremade.registerPremadeStructureType("clayForge_11", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	MultiTilePremade.registerPremadeStructureType("clayForge_12", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
    	MultiTilePremade.registerPremadeStructureType("clayForge_13", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class);
    	
    	proxy.loadSidePost();

    	
    	//LittleStructurePremade.registerPremadeStructureType("clayForge_6", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING );


    	//MultiTileStructureRegistry.registerPremadeStructureType("clayForge", LittleForge.MODID, LittlePhotoImporter.class,6); 
    	/*
    	LittleForgeRecipeFactory.addRecipe("clayForge_1", new ItemStack(Items.CLAY_BALL, 8));
    	LittleForgeRecipeFactory.addRecipe("clayForge_2", new ItemStack(Items.CLAY_BALL, 5));
    	LittleForgeRecipeFactory.addRecipe("clayForge_3", new ItemStack(Items.CLAY_BALL, 5));
    	LittleForgeRecipeFactory.addRecipe("clayForge_4", new ItemStack(Items.CLAY_BALL, 5));
    	LittleForgeRecipeFactory.addRecipe("clayForge_5", new ItemStack(Items.FLINT, 1));
    	LittleForgeRecipeFactory.addRecipe("clayForge_6", new ItemStack(Blocks.IRON_ORE, 1));
    	LittleForgeRecipeFactory.addRecipe("clayForge_7", new ItemStack(Items.STICK, 64));
    	*/
    }
}
