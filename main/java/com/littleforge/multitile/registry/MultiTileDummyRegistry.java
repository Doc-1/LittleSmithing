package com.littleforge.multitile.registry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.littleforge.multitile.strucutres.MultiTileDummyStructure;

public class MultiTileDummyRegistry {

	private static HashMap<String, Integer> multiTileStructLimitDict = new HashMap<>();
	
	/**
	 * Structures should be labeled the same name Plus "_#" # will be the order it goes in
	 * The first structure should be labeled as "Structure_1" This will the starting 
	 * structure that will be built into the next Structure.
	 * @param id
	 * ID of the premade Structure minus the "_#"
	 * @param modid
	 * The ID of your (my) Mod
	 * @param classStructure
	 * The Class in which you want the last Premade Structure to use. Think of it as a Block Class
	 * @param count
	 * How many Dummy premade structures will be registered. Example, if count = 7 then register
	 * "structure_1" to "structure_7"
	 * @param ticking
	 * If ticking = true then the all these dummy structures will run a method each time a tick occurs 
	 */
	public static void registerPremadeStructureDummy(String id, String modid, Class<? extends LittleStructurePremade> classStructure, int count, boolean ticking) {
    	int i;
		for(i=1;i<=count;i++) {
    		LittleStructurePremade.registerPremadeStructureType(id+"_"+i, modid, MultiTileDummyStructure.class);
    	}    	
    }
	
	public static void setLimit() {
		
	}
	
	public static int findLimit(String id) {
		Set set = multiTileStructLimitDict.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			if(mentry.getKey().equals(id)) {
				return (int) mentry.getValue();
			}
		}
		return 0;
	}
}
