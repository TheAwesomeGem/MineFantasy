package minefantasy.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.StatCollector;

public class EntityDamageSourceBomb extends EntityDamageSourceIndirect
{
    private Entity thrower;

    public EntityDamageSourceBomb(Entity bomb, Entity user)
    {
        super("bomb", bomb, user);
        thrower = user;
    }

    /**
     * Returns the message to be displayed on player death.
     */
    @Override
    public ChatMessageComponent getDeathMessage(EntityLivingBase hurtEnemy)
    {
        String throwername = this.thrower == null ? this.damageSourceEntity.getTranslatedEntityName() : this.thrower.getTranslatedEntityName();
        ItemStack itemstack = this.thrower instanceof EntityLivingBase ? ((EntityLivingBase)this.thrower).getHeldItem() : null;
        String damageType = "death.attack.bomb";
        
        String src = "";
        if(thrower != null && thrower != getSourceOfDamage())
        {
        	if(thrower == hurtEnemy)
        	{
        		src = ".suicide";
        	}
        	else
        	{
        		src = ".thrown";
        	}
        }
        String s2 = damageType + src;
        return  ChatMessageComponent.createFromTranslationWithSubstitutions(s2, new Object[] {hurtEnemy.getTranslatedEntityName(), throwername});
    }
}
