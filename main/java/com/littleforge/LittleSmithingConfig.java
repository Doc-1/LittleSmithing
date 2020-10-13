package com.littleforge;

import java.util.Arrays;
import java.util.List;

import com.creativemd.creativecore.common.config.api.CreativeConfig;
import com.creativemd.creativecore.common.config.sync.ConfigSynchronization;

import net.minecraft.init.Items;

public class LittleSmithingConfig {
	
	private static final String CATEGORY_GENERAL = "general";

	//CLIENT Only read from Client. Server doesn't need to know about it
	//SERVER Only read from Server. Client doesn't need to know about it
	//UNIVERSAL Server will push it's config to Client
	
	@CreativeConfig(type = ConfigSynchronization.UNIVERSAL)
	public static Recipe recipe = new Recipe();
	
	public static class Recipe {
		
		@CreativeConfig
		public String claySmelery_2 = "Ingredients:{Items.CLAY_BALL, 5, 0}";
		
		@CreativeConfig
		public String claySmelery_3 = "Ingredients:{Items.CLAY_BALL, 10, 0}";
	}
}
