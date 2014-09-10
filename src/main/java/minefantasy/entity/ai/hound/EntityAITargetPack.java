package minefantasy.entity.ai.hound;

import minefantasy.entity.EntityHound;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class EntityAITargetPack extends EntityAINearestAttackableTarget
{
    private EntityHound theTameable;

    public EntityAITargetPack(EntityHound dog, Class target, int chance, boolean shouldSee)
    {
        super(dog, target, chance, shouldSee);
        this.theTameable = dog;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.theTameable.isTamed() ? false : (theTameable.inPack() && super.shouldExecute());
    }
    
}
