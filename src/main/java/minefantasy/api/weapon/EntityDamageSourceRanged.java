package minefantasy.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSourceIndirect;

public class EntityDamageSourceRanged extends EntityDamageSourceIndirect
{
    private Entity shooter;

    public EntityDamageSourceRanged(Entity arrow, Entity user, boolean AP)
    {
        super("arrow", arrow, user);
        shooter = user;
        if(AP)
        this.setDamageBypassesArmor();
    }
}
