package minefantasy.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class EntityAISwimSmart extends EntityAIBase
{
    private EntityLiving theEntity;

    public EntityAISwimSmart(EntityLiving user)
    {
        this.theEntity = user;
        this.setMutexBits(4);
        user.getNavigator().setCanSwim(true);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
    	boolean swimDown = false;
    	
    	if(theEntity.getAITarget() != null)
    	{
    		if(theEntity.getAITarget().posY < (theEntity.posY - 1))
    		{
    			swimDown = true;
    		}
    	}
    	
    	if(theEntity.getAir() < 10)
    	{
    		swimDown = false;
    	}
    	
    	if(!swimDown)
    	this.theEntity.getJumpHelper().setJumping();
    }
}
