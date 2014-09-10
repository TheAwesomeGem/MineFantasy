package minefantasy.api.tactic;

public interface ISpecialSenses {
	/**
	 * 
	 * @return the arc of sight(default 90). meaning it cant see anything buth what's 90degrees arround them
	 */
	int getViewingArc();
	
	/**
	 * (The lower the better): Note this can go below 0 for better senses
	 * @return Determines the sound before it hears an entity
	 * Default 5
	 */
	int getHearing();
	
	/**
	 * (The lower the better): Note this can go below 0 for better senses
	 * @return Determines the sight before it sees an entity
	 * Default 20
	 */
	int getSight();
}
