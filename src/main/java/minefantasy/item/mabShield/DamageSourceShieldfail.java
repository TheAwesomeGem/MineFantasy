package minefantasy.item.mabShield;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.Explosion;

public class DamageSourceShieldfail extends DamageSource
{
	private DamageSource baseSrc;
	public DamageSourceShieldfail(DamageSource sourceBase)
    {
		super("shieldBypass");
		baseSrc = sourceBase;
    }

    /**
     * Returns true if the damage is projectile based.
     */
    public boolean isProjectile()
    {
        return baseSrc.isProjectile();
    }

    public boolean isExplosion()
    {
        return baseSrc.isExplosion();
    }

    public boolean isUnblockable()
    {
        return baseSrc.isUnblockable();
    }

    /**
     * How much satiate(food) is consumed by this DamageSource
     */
    public float getHungerDamage()
    {
        return baseSrc.getHungerDamage();
    }

    public boolean canHarmInCreative()
    {
        return baseSrc.canHarmInCreative();
    }

    public Entity getSourceOfDamage()
    {
        return this.getEntity();
    }

    public Entity getEntity()
    {
        return null;
    }

    /**
     * Returns the message to be displayed on player death.
     */
    public ChatMessageComponent getDeathMessage(EntityLivingBase hit)
    {
    	return baseSrc.getDeathMessage(hit);
    }

    /**
     * Returns true if the damage is fire based.
     */
    public boolean isFireDamage()
    {
        return baseSrc.isFireDamage();
    }

    /**
     * Return the name of damage type.
     */
    public String getDamageType()
    {
        return baseSrc.getDamageType();
    }

    /**
     * Set whether this damage source will have its damage amount scaled based on the current difficulty.
     */
    public DamageSource setDifficultyScaled()
    {
    	baseSrc.setDifficultyScaled();
        return this;
    }

    /**
     * Return whether this damage source will have its damage amount scaled based on the current difficulty.
     */
    public boolean isDifficultyScaled()
    {
        return baseSrc.isDifficultyScaled();
    }

    /**
     * Returns true if the damage is magic based.
     */
    public boolean isMagicDamage()
    {
        return baseSrc.isMagicDamage();
    }

}
