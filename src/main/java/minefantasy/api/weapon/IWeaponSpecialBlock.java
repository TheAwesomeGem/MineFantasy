package minefantasy.api.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public interface IWeaponSpecialBlock 
{
	float blockDamage(EntityLivingBase entity, float damage, DamageSource source);
}
