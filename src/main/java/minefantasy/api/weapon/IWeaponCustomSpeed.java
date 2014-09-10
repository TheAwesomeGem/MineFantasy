package minefantasy.api.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IWeaponCustomSpeed {

	int getHitTime(ItemStack weapon, EntityLivingBase target);

}
