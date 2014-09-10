package minefantasy.api.tactic;

public interface ISoundMaker {
	/**
	 * 
	 * @return Determines if it's making sound
	 */
	public boolean isMakingNoise();
	/**
	 * 
	 * @return the volume of the sound(100 - sound of fullplate while sneaking)
	 */
	public int getSoundStrength();
}
