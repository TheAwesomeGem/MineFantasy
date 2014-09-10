package minefantasy.entity.ai.hound;

import java.util.Iterator;
import java.util.List;

import minefantasy.entity.EntityHound;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class EntityAIDefendHound extends EntityAITarget
{
    boolean alertFriends;

    /** The PathNavigate of our entity. */
    EntityLivingBase attacker;
    private EntityHound dog;

    public EntityAIDefendHound(EntityHound user, boolean alert)
    {
        super(user, false);
        this.alertFriends = alert;
        this.setMutexBits(1);
        dog = user;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if(!dog.isTamed() && !dog.inPack() && dog.getHealth() < 5)
        {
        	return false;
        }
        return this.isSuitableTarget(this.taskOwner.getAITarget(), true);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.taskOwner.getAITarget() != null && this.taskOwner.getAITarget() != this.attacker;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        this.attacker = this.taskOwner.getAITarget();

        if (this.alertFriends)
        {
            List list = this.taskOwner.worldObj.getEntitiesWithinAABB(EntityHound.class, AxisAlignedBB.getAABBPool().getAABB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D).expand((double)this.getTargetDistance(), 10.0D, (double)this.getTargetDistance()));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
            	EntityHound friend = (EntityHound)iterator.next();

                if (this.taskOwner != friend && friend.getAttackTarget() == null)
                {
                	if(friend.willFightFor((EntityHound)taskOwner))
                	{
                		friend.setAttackTarget(this.taskOwner.getAITarget());
                	}
                }
            }
        }

        super.startExecuting();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        if (this.taskOwner.getAttackTarget() != null && this.taskOwner.getAttackTarget() instanceof EntityPlayer && ((EntityPlayer)this.taskOwner.getAttackTarget()).capabilities.disableDamage)
        {
            super.resetTask();
        }
    }
}
