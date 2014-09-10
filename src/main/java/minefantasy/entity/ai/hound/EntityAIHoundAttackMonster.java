package minefantasy.entity.ai.hound;

import minefantasy.entity.EntityHound;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class EntityAIHoundAttackMonster extends EntityAINearestAttackableTarget
{
    private EntityHound theTameable;

    public EntityAIHoundAttackMonster(EntityHound dog, Class enemy, int chance, boolean chase)
    {
        super(dog, enemy, chance, chase);
        this.theTameable = dog;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return theTameable.attackMob ? super.shouldExecute() : false;
    }
}
