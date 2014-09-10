package minefantasy.api.hound;

/**
 * This interface lets items be equipped in a hound slot.
 * This is used by ItemHoundArmour, ItemHoundPack, ItemHoundDispenser and ItemHoundFeedbag
 */
public interface IHoundEquipment {
	/**
	 * @return The slot the item takes. (0 = helm, 1 = body)
	 */
	int getPiece();
	/**
	 * @return The minimal Strength level required
	 */
	int getRequiredStr();
	/**
	 * @return The minimal Endurance level required
	 */
	int getRequiredEnd();
	/**
	 * @return The minimal Stamina level required
	 */
	int getRequiredSta();
	
	/**
	 * Gets the amount the item slows the hound down
	 * @return - for slow down + for speed up (0.0F = no change)
	 */
	float getMobilityModifier();
}