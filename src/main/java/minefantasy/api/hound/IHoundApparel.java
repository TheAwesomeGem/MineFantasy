package minefantasy.api.hound;

/**
 * This is used when items give the hound more skins(like armour)
 */
public interface IHoundApparel {
	/**
	 * @return The Closer texture (used with mail armour)
	 */
	String getTexture();

	/**
	 * @return The Upper layer. (added with plate armour)
	 */
	String getOverlay();
}
