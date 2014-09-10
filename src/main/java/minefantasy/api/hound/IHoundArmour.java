package minefantasy.api.hound;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public interface IHoundArmour 
{
	boolean shouldDamage(DamageSource source);
	float getResistance(DamageSource source);
	float getMobilityModifier();
	float getACDisplayPercent();
}
