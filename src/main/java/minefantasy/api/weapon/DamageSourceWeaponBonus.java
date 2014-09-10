package minefantasy.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class DamageSourceWeaponBonus extends DamageSource
{
    protected Entity damageSourceEntity;

    public DamageSourceWeaponBonus(String entityType, Entity attacker, boolean AP)
    {
        super(entityType);
        if(AP)
        {
        	this.setDamageBypassesArmor();
        }
        this.damageType = "battlegearExtra";
        this.damageSourceEntity = attacker;
    }

    public Entity getEntity()
    {
        return this.damageSourceEntity;
    }

    @Override
    public ChatMessageComponent getDeathMessage(EntityLivingBase entity)
    {
        ItemStack itemstack = this.damageSourceEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.damageSourceEntity).getHeldItem() : null;
        String s = "death.attack." + this.damageType;
        String s1 = s + ".item";
        return itemstack != null && itemstack.hasDisplayName() && StatCollector.func_94522_b(s1) ? ChatMessageComponent.createFromTranslationWithSubstitutions(s1, new Object[] {entity.getTranslatedEntityName(), this.damageSourceEntity.getTranslatedEntityName(), itemstack.getDisplayName()}): ChatMessageComponent.createFromTranslationWithSubstitutions(s, new Object[] {entity.getTranslatedEntityName(), this.damageSourceEntity.getTranslatedEntityName()});
    }
}
