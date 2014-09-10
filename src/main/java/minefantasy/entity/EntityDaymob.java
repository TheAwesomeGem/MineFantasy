package minefantasy.entity;

import minefantasy.system.MF_Calculate;
import minefantasy.system.cfg;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

/**
 * Derrived from EntityMob. but designed for daytime use
 * @see EntityMob
 */
public abstract class EntityDaymob extends EntityCreature implements IMob
{
    public EntityDaymob(World world)
    {
        super(world);
        this.experienceValue = 5;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        this.updateArmSwingProgress();
        super.onLivingUpdate();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == 0)
        {
            this.setDead();
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
        return var1 != null && this.canEntityBeSeen(var1) ? var1 : null;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, int damage)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else if (super.attackEntityFrom(source, damage))
        {
            Entity var3 = source.getEntity();

            if (this.riddenByEntity != var3 && this.ridingEntity != var3)
            {
                if (var3 != this)
                {
                    this.entityToAttack = var3;
                }

                return true;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean attackEntityAsMob(Entity target)
    {
        float var2 = this.getAttackStrength(target);

        if (this.isPotionActive(Potion.damageBoost))
        {
            var2 += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if (this.isPotionActive(Potion.weakness))
        {
            var2 -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }

        int var3 = 0;

        if (target instanceof EntityLiving)
        {
            var2 += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLiving)target);
            var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLiving)target);
        }

        boolean var4 = target.attackEntityFrom(DamageSource.causeMobDamage(this), var2);

        if (var4)
        {
            if (var3 > 0)
            {
                target.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)var3 * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)var3 * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int var5 = EnchantmentHelper.getFireAspectModifier(this);

            if (var5 > 0)
            {
                target.setFire(var5 * 4);
            }

            if (target instanceof EntityLiving)
            {
                EnchantmentThorns.func_92096_a(this, (EntityLiving)target, this.rand);
            }
        }

        return var4;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity target, float damage)
    {
        if (this.attackTime <= 0 && damage < 2.0F && target.boundingBox.maxY > this.boundingBox.minY && target.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            this.attackEntityAsMob(target);
        }
    }


    /**
     * Checks to make sure the light is not too dark where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        int var3 = MathHelper.floor_double(this.posZ);

        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int light = this.worldObj.getBlockLightValue(var1, var2, var3);

            if (this.worldObj.isThundering())
            {
                int sky = this.worldObj.skylightSubtracted;
                this.worldObj.skylightSubtracted = 10;
                light = this.worldObj.getBlockLightValue(var1, var2, var3);
                this.worldObj.skylightSubtracted = sky;
            }

            return light >= 15-this.rand.nextInt(5);
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere()
    {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        int var3 = MathHelper.floor_double(this.posZ);
        
        return isProperDistance() && isProperBlock(var1, var2, var3) && this.worldObj.getFullBlockLightValue(var1, var2, var3) >= getMinimalLight() && super.getCanSpawnHere();
    }

    public int getMinimalLight() {
		return 10;
	}

	public boolean isProperBlock(int x, int y, int z) {
    	return this.worldObj.getBlockId(x, y - 1, z) == Block.grass.blockID;
	}

	public boolean isProperDistance() {
    	if(this.dimension != 0)
    		return true;
    	
    	ChunkCoordinates spawn = worldObj.getSpawnPoint();
		return this.getDistance(spawn.posX, spawn.posY, spawn.posZ) > getDistanceToSpawn();
	}

	public abstract double getDistanceToSpawn();

    
	/**
     * Returns the amount of damage a mob should deal.
     */
    public abstract float getAttackStrength(Entity e);
}
