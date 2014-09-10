package minefantasy.entity.ai.hound;

import minefantasy.entity.EntityHound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIDefendOwnerHound extends EntityAITarget
{
	EntityHound hound;
    EntityLivingBase enemy;
    private int lastRevengeTime;

    public EntityAIDefendOwnerHound(EntityHound dog)
    {
        super(dog, false);
        this.hound = dog;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
    	if (!(this.hound.isTamed() && hound.shouldDefendOwner(enemy)))
        {
        	return false;
        }
        else
        {
            EntityLivingBase entitylivingbase = this.hound.func_130012_q();

            if (entitylivingbase == null)
            {
                return false;
            }
            else
            {
                this.enemy = entitylivingbase.getAITarget();
                int i = entitylivingbase.func_142015_aE();
                return i != this.lastRevengeTime && this.isSuitableTarget(this.enemy, false) && this.hound.func_142018_a(this.enemy, entitylivingbase);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.enemy);
        EntityLivingBase entitylivingbase = this.hound.func_130012_q();

        if (entitylivingbase != null)
        {
            this.lastRevengeTime = entitylivingbase.func_142015_aE();
        }

        super.startExecuting();
    }
}
