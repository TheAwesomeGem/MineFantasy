package minefantasy.api.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ICrossbowHandler {
	
	/**
	 * Called when a shot is fired
	 * @param bow The crossbow used
	 * @param world The world the bow is fired in
	 * @param shooter The player shooting the weapon
	 * @param accuracy The accuracy of the weapon
	 * @param power The weapon damage(hand crossbows do 0.5) not the velocity
	 * @param ammo The itemID fired
	 * @return true if a projectile is fired, false if this handler doesnt use that ammo
	 */
	public boolean shoot(ItemStack bow, World world, EntityPlayer shooter, float accuracy, float power, ItemStack ammo);
}
