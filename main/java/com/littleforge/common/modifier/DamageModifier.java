package com.littleforge.common.modifier;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DamageModifier {
	
	@SubscribeEvent
	public static void hasDamagedEntity(LivingDamageEvent event) {
		EntityLivingBase attacker = event.getEntityLiving().getAttackingEntity();
		System.out.println(event.getEntityLiving().getTotalArmorValue());
		event.setCanceled(true);
	}
}
