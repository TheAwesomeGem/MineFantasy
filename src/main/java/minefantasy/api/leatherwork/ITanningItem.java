package minefantasy.api.leatherwork;

public interface ITanningItem {
	/**
	 * @return How effective the tool is. (Use efficiencyOnProperMaterial on Tool materials)
	 */
	float getQuality();
	
	EnumToolType getType();
}
