package minefantasy.api.tailor;

/**
 * Used to create needles for sewing
 * MF needles use 4x their Enum material in durability(since their used many times each recipe)
 */
public interface INeedle {

	/**
	 * Gets the speed modifier for each stitch
	 * use the same variable as tool material efficiency
	 * (EnumToolMaterial.getEfficiencyOnProperMaterial())
	 */
	float getEfficiency();
	
	/**
	 * Gets the tier for the needle better tiers can sew better materials
	 */
	int getTier();

}
