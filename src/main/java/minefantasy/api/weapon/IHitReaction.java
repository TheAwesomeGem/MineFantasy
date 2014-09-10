package minefantasy.api.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public interface IHitReaction 
{
	public void onUserHit(DamageSource source, EntityLivingBase target);
}
