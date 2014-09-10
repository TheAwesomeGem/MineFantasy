package minefantasy.api.anvil;

public interface ICustomRepair {

	/**
	 * Gets the effectiveness of repair hammers on anvils, contolling the amount of damage taken
	 * @return 1.0 for regular effectiveness, 0.5 means half, 2.0 means double
	 */
	float getRepairValue();

}
