package minefantasy.entity.ai.hound;

import minefantasy.entity.EntityHound;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class EntityAIFleeHurt extends EntityAIAvoidEntity {

	private EntityHound hound;
	
	public EntityAIFleeHurt(EntityHound dog, Class flee,
			float distance, float speedF, float speedN) {
		super(dog, flee, distance, speedF, speedN);
		hound = dog;
	}
	/**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	return false;// hound.fleeTick > 0 && super.shouldExecute();
    }

}
