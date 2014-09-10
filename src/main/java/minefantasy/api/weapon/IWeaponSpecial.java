package minefantasy.api.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IWeaponSpecial 
{

	void onHit(float damage, ItemStack weapon, EntityLivingBase target, EntityLivingBase attacker);

}
