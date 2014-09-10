package minefantasy.entity.ai.hound;

import minefantasy.entity.EntityHound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowHound extends EntityAIFollowOwner
{
    private EntityHound thePet;

    public EntityAIFollowHound(EntityHound dog, float speed, float min, float max)
    {
    	super(dog, speed, min, max);
    	thePet = dog;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        Entity owner = this.thePet.getOwner();

        if (owner == null)
        {
            return false;
        }
        else if (!thePet.isFollowing())
        {
            return false;
        }
		return super.shouldExecute();
    }
}
