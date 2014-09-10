package minefantasy.api.aesthetic;

import net.minecraft.world.World;

/**
 * This is implemented by chimney blocks
 */
public interface IChimney {

	/**
	 * This is called by machines that emit smoke
	 * @param world The world
	 * @param x The X coord
	 * @param y The Y coord
	 * @param z The Z coord
	 * @param num The amount of particles(determines thickness)
	 * @param speedX The amount it spreads outwards
	 * @param speedY The vertical height
	 * @return true if the chimney puffs the smoke
	 */
	boolean puffSmoke(World world, int x, int y, int z, float num,
			float speedX, float speedY);

}
