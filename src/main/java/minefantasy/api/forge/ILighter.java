package minefantasy.api.forge;

public interface ILighter
{
	/**
	 * Gets the chance that it can light forges
	 * 1.0 = 100% chance
	 */
	public double getChance();
	
	/**
	 * Determines if it can light forges and firepits
	 */
	public boolean canLight();
}
