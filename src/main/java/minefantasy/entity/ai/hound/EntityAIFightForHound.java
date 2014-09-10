package minefantasy.entity.ai.hound;

import minefantasy.entity.EntityHound;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIFightForHound extends EntityAITarget
{
	EntityHound hound;
    EntityLivingBase enemy;
    private int lastRevenge;

    public EntityAIFightForHound(EntityHound dog)
    {
        super(dog, false);
        this.hound = dog;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
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
                this.enemy = entitylivingbase.getLastAttacker();
                int i = entitylivingbase.getLastAttackerTime();
                return i != this.lastRevenge && this.isSuitableTarget(this.enemy, false) && this.hound.func_142018_a(this.enemy, entitylivingbase);
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
            this.lastRevenge = entitylivingbase.getLastAttackerTime();
        }

        super.startExecuting();
    }
}
