package minefantasy.api.armour;

import net.minecraft.item.ItemStack;

public interface IArmourCustomSpeed {
	/**
	 * @return The speed moved in armour(Put in the speed you move with the full suit, it divides itself)
	 * Eg. Light armour = 1.0 (no penalty), Medium = 0.95 (5% less) heavy is 0.85 and plate = 0.75
	 */
	float getMoveSpeed(ItemStack item);
}
