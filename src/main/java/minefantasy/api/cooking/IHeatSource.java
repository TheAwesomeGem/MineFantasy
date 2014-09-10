package minefantasy.api.cooking;

public interface IHeatSource {
	/**
	 * Determines if a roast can be placed above
	 * @return true if the posts render above it
	 */
	boolean canPlaceAbove();
	
	/**
	 * Gets the heat of the source(the higher, the faster)
	 * @return the heat in celcius
	 */
	int getHeat();
}
