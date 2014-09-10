package minefantasy.entity;

public interface INameableEntity {
	/**
	 * 
	 * @return the entity ID (entityId)
	 */
	int getEntityID();
	/**
	 * called when the object is renamed
	 * @param name: The name to give
	 */
	void sendNewName(String name);
}
