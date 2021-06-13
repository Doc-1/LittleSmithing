package com.littleforge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creativemd.creativecore.common.config.api.CreativeConfig;
import com.creativemd.creativecore.common.config.sync.ConfigSynchronization;
import com.creativemd.creativecore.common.utils.stack.InfoStack;
import com.littleforge.common.recipe.LittleForgeRecipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class LittleSmithingConfig {
	
	private static final String CATEGORY_GENERAL = "general";
	
	//CLIENT Only read from Client. Server doesn't need to know about it
	//SERVER Only read from Server. Client doesn't need to know about it
	//UNIVERSAL Server will push it's config to Client
	
	@CreativeConfig(type = ConfigSynchronization.UNIVERSAL)
	public static BrickFrogeRecipe brickForgeRecipe = new BrickFrogeRecipe();
	
	public static class BrickFrogeRecipe {
		@CreativeConfig
		public List<InfoStack> brickForge_2 = new ArrayList<InfoStack>();
		@CreativeConfig
		public List<InfoStack> brickForge_3 = new ArrayList<InfoStack>();
		@CreativeConfig
		public List<InfoStack> brickForge_4 = new ArrayList<InfoStack>();
		@CreativeConfig
		public List<InfoStack> brickForge_5 = new ArrayList<InfoStack>();
		@CreativeConfig
		public List<InfoStack> brickForge_6 = new ArrayList<InfoStack>();
		@CreativeConfig
		public List<InfoStack> brickForge_7 = new ArrayList<InfoStack>();
		@CreativeConfig
		public List<InfoStack> brickForge_8 = new ArrayList<InfoStack>();
		@CreativeConfig
		public List<InfoStack> brickForge_9 = new ArrayList<InfoStack>();
		@CreativeConfig
		public List<InfoStack> brickForge_10 = new ArrayList<InfoStack>();
		
		public Map<String, List<ItemStack>> brickForgeBasic = new HashMap<String, List<ItemStack>>();
		
		public ItemStack stack = ItemStack.EMPTY;
		public NBTTagCompound nbt = new NBTTagCompound();
		
		public BrickFrogeRecipe() {
			setBrickForgeRecipie();
		}
		
		private void setBrickForgeRecipie() {
			LittleForgeRecipes.registerRecipeFromConfig(brickForgeBasic, brickForge_2, "brickForgeBasic_1", new ItemStack(Items.BRICK, 25), new ItemStack(Items.CLAY_BALL, 15));
			LittleForgeRecipes.registerRecipeFromConfig(brickForgeBasic, brickForge_3, "brickForgeBasic_2", new ItemStack(Items.BRICK, 25), new ItemStack(Items.CLAY_BALL, 15));
			LittleForgeRecipes.registerRecipeFromConfig(brickForgeBasic, brickForge_4, "brickForgeBasic_3", new ItemStack(Items.BRICK, 25), new ItemStack(Items.CLAY_BALL, 15));
			LittleForgeRecipes.registerRecipeFromConfig(brickForgeBasic, brickForge_5, "brickForgeBasic_4", new ItemStack(Items.BRICK, 25), new ItemStack(Items.CLAY_BALL, 15));
			LittleForgeRecipes.registerRecipeFromConfig(brickForgeBasic, brickForge_6, "brickForgeBasic_5", new ItemStack(Items.BRICK, 25), new ItemStack(Items.CLAY_BALL, 15));
			LittleForgeRecipes.registerRecipeFromConfig(brickForgeBasic, brickForge_7, "brickForgeBasic_6", new ItemStack(Items.BRICK, 25), new ItemStack(Items.CLAY_BALL, 15));
			LittleForgeRecipes.registerRecipeFromConfig(brickForgeBasic, brickForge_8, "brickForgeBasic_7", new ItemStack(Items.BRICK, 25), new ItemStack(Items.CLAY_BALL, 15));
			LittleForgeRecipes.registerRecipeFromConfig(brickForgeBasic, brickForge_9, "brickForgeBasic_8", new ItemStack(Items.BRICK, 25), new ItemStack(Items.CLAY_BALL, 15));
			LittleForgeRecipes.registerRecipeFromConfig(brickForgeBasic, brickForge_10, "brickForgeBasic_9", new ItemStack(Items.BRICK, 25), new ItemStack(Items.CLAY_BALL, 15));
		}
		
	}
}
