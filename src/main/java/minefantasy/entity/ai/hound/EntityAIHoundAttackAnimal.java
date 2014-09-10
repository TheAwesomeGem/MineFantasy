package minefantasy.entity.ai.hound;

import minefantasy.entity.EntityHound;
import minefantasy.system.cfg;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityVillager;

public class EntityAIHoundAttackAnimal extends EntityAINearestAttackableTarget
{
    private EntityHound theTameable;

    public EntityAIHoundAttackAnimal(EntityHound dog, Class enemy, int chance, boolean chase)
    {
        super(dog, enemy, chance, chase);
        this.theTameable = dog;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return theTameable.attackAnimal ? super.shouldExecute() : false;
    }
}
