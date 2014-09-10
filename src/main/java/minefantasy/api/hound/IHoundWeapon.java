package minefantasy.api.hound;

import net.minecraft.entity.Entity;

public interface IHoundWeapon {

	/**
	 * Gets the damage against an entity
	 * @param tar the entity being attacked
	 * 
	 * Entity is null in some instances
	 */
	float getDamage(Entity tar);

}
