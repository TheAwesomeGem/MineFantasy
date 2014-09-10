package minefantasy.entity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

import com.google.common.io.ByteArrayDataInput;

import minefantasy.item.ToolMaterialMedieval;
import minefantasy.item.weapon.ItemSpearMF;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityThrownSpear extends EntityArrow implements IProjectile, PacketUserMF, IThrownItem, ISyncedInventory
{
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile;
    private int inData;
    private boolean inGround;

    /** 1 if the player can pick up the arrow and 2 if it can be picked up but not retrieved*/
    public int canBePickedUp;

    /** Seems to be some sort of timer for animating an arrow. */
    public int arrowShake;

    /** The owner of this arrow. */
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage = 2.0D;

    /** The amount of knockback an arrow applies when it hits a mob. */
    private int knockbackStrength;
	private ItemStack spearItem;

    public EntityThrownSpear(World world)
    {
        super(world);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }

    public EntityThrownSpear(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
        this.yOffset = 0.0F;
    }

    public EntityThrownSpear(World world, EntityLivingBase thrower, float power)
    {
        this(world);
        this.shootingEntity = thrower;

        if (thrower instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }

        this.setLocationAndAngles(thrower.posX, thrower.posY + (double)thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, power * 1.5F, 1.0F);
    }

    protected void entityInit()
    {
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)f2;
        par3 /= (double)f2;
        par5 /= (double)f2;
        par1 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
        par3 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
        par5 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);
    }

    @SideOnly(Side.CLIENT)

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    public void setVelocity(double par1, double par3, double par5)
    {
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
    	 this.onEntityUpdate();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }

        int i = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

        if (i > 0)
        {
            Block.blocksList[i].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

            if (axisalignedbb != null && axisalignedbb.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        if (this.inGround)
        {
            int j = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
            int k = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

            if (j == this.inTile && k == this.inData)
            {
                ++this.ticksInGround;

                if (this.ticksInGround == 1200)
                {
                    this.setDead();
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else
        {
            ++this.ticksInAir;
            Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks_do_do(vec3, vec31, false, true);
            vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null)
            {
                vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int l;
            float f1;

            for (l = 0; l < list.size(); ++l)
            {
                Entity entity1 = (Entity)list.get(l);

                if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
                {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double)f1, (double)f1, (double)f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

                if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
                {
                    movingobjectposition = null;
                }
            }

            float f2;
            float f3;

            if (movingobjectposition != null)
            {
                if (movingobjectposition.entityHit != null)
                {
                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    float dam = (float)getDamage();
                    if(isJavelin())
                    {
                    	 dam = (float)Math.ceil((double)f2 * this.damage);
                    }

                    DamageSource damagesource = null;

                    if (this.shootingEntity == null)
                    {
                        damagesource = DamageSource.causeThrownDamage(this, this);
                    }
                    else
                    {
                        damagesource = DamageSource.causeThrownDamage(this, this.shootingEntity);
                    }

                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
                    {
                        movingobjectposition.entityHit.setFire(5);
                    }
                    dam = modifyDamage(movingobjectposition.entityHit, dam);

                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float)dam))
                    {
                    	applySpecials(movingobjectposition.entityHit);
                        if (movingobjectposition.entityHit instanceof EntityLivingBase)
                        {
                            EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;

                            if (!this.worldObj.isRemote)
                            {
                                entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                            }

                            float kb = knockbackStrength;
                            if(movingobjectposition.entityHit instanceof EntityLivingBase)
                            {
                            	kb = modifyKnockback((EntityLivingBase)movingobjectposition.entityHit, kb);
                            }
                            if (kb > 0)
                            {
                                f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                if (f3 > 0.0F)
                                {
                                    movingobjectposition.entityHit.addVelocity(this.motionX * (double)kb * 0.6000000238418579D / (double)f3, 0.1D, this.motionZ * (double)kb * 0.6000000238418579D / (double)f3);
                                }
                            }

                            if (this.shootingEntity != null)
                            {
                                EnchantmentThorns.func_92096_a(this.shootingEntity, entitylivingbase, this.rand);
                            }

                            if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                            {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
                            }
                        }

                        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                        if (!(movingobjectposition.entityHit instanceof EntityEnderman))
                        {
                            this.damageSpear();
                        }
                    }
                    else
                    {
                        this.motionX = 0D;
                        this.motionY = 0D;
                        this.motionZ = 0D;
                        this.rotationYaw += 180.0F;
                        this.prevRotationYaw += 180.0F;
                        this.ticksInAir = 0;
                    }
                }
                else
                {
                    this.xTile = movingobjectposition.blockX;
                    this.yTile = movingobjectposition.blockY;
                    this.zTile = movingobjectposition.blockZ;
                    this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                    this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - this.posX));
                    this.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - this.posY));
                    this.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - this.posZ));
                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
                    this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);

                    if (this.inTile != 0)
                    {
                        Block.blocksList[this.inTile].onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                    }
                }
            }

            if (this.getIsCritical())
            {
                for (l = 0; l < 4; ++l)
                {
                    this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double)l / 4.0D, this.posY + this.motionY * (double)l / 4.0D, this.posZ + this.motionZ * (double)l / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f4 = 0.99F;
            f1 = 0.05F;

            if (this.isInWater())
            {
                for (int j1 = 0; j1 < 4; ++j1)
                {
                    f3 = 0.25F;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f3, this.posY - this.motionY * (double)f3, this.posZ - this.motionZ * (double)f3, this.motionX, this.motionY, this.motionZ);
                }

                f4 = 0.8F;
            }

            this.motionX *= (double)f4;
            this.motionY *= (double)f4;
            this.motionZ *= (double)f4;
            this.motionY -= (double)f1;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
        if(ticksExisted % 20 == 0)
        {
        	syncSpear();
        }
        if(ticksExisted <= 10 && !worldObj.isRemote)
        {
        	sendTrackerPacket();
        }
    }

    
    private float modifyKnockback(EntityLivingBase entityHit, float kb)
    {
    	if(getSpear() != null)
    	{
    		int mod = 1+EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, getSpear());
    		return kb*mod;
    	}
		return kb;
	}

	private float modifyDamage(Entity entityHit, float dam)
    {
    	EntityLivingBase hit = null;
    	if(entityHit instanceof EntityLivingBase)
    	{
    		hit = (EntityLivingBase)entityHit;
    	}
    	
    	if(getSpear() == null)return dam;
    	
    	ItemStack weapon = getSpear();
    	
    	return modifyDamLiving(weapon, hit, dam);
	}
    
    private void applySpecials(Entity entityHit)
    {
    	EntityLivingBase hit = null;
    	if(entityHit instanceof EntityLivingBase)
    	{
    		hit = (EntityLivingBase)entityHit;
    	}
    	
    	if(getSpear() == null)return;
    	
    	ItemStack weapon = getSpear();
    	
    	hitLiving(weapon, hit);
	}

	private void hitLiving(ItemStack weapon, EntityLivingBase target)
	{
		if (getMaterial() == ToolMaterialMedieval.DRAGONFORGE) 
		{
			target.setFire(20);
		}
		
		if (getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
		{
			if (target instanceof EntityLiving) {
				PotionEffect poison = new PotionEffect(Potion.poison.id, 100, 1);
				((EntityLiving)target).addPotionEffect(poison);
			}
		}

		if (getMaterial() == ToolMaterialMedieval.ORNATE) 
		{
			if (target instanceof EntityLiving) 
			{
				if (((EntityLiving) target).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) 
				{
					target.setFire(20);
					int hurt = target.hurtResistantTime;
					target.hurtResistantTime = 0;
					target.attackEntityFrom(DamageSource.generic, (float)damage);
					target.hurtResistantTime = hurt;
					target.worldObj.playSoundAtEntity(target, "random.fizz", 1, 1);
				}
				if (((EntityLiving) target).getClass().getName().endsWith("MoCEntityWarewolf")) 
				{
					target.setFire(20);
					int hurt = target.hurtResistantTime;
					target.hurtResistantTime = 0;
					target.attackEntityFrom(DamageSource.generic, (float)damage*10);
					target.hurtResistantTime = hurt;
					target.worldObj.playSoundAtEntity(target, "random.fizz", 1, 1);
				}
			}
		}
		
		int fire = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, weapon);
		if(fire > 0)
		{
			target.setFire(20 * fire);
		}
	}
	
	private float modifyDamLiving(ItemStack weapon, EntityLivingBase target, float dam)
	{
		float sharp = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, weapon);
		float spider = EnchantmentHelper.getEnchantmentLevel(Enchantment.baneOfArthropods.effectId, weapon);
		float smite = EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, weapon);
		
		if(!worldObj.isRemote)
		{
			System.out.println("Start " + dam);
		}
		
		if(sharp > 0)
		{
			dam += sharp * 1.25F;
			
			if(!worldObj.isRemote)
			{
				System.out.println("Sharpness " + (sharp * 1.25F));
			}
		}
		if(spider > 0 && target.getCreatureAttribute()==EnumCreatureAttribute.ARTHROPOD)
		{
			dam += spider * 2.5F;
			
			if(!worldObj.isRemote)
			{
				System.out.println("Bane " + (spider * 2.5F));
			}
		}
		if(smite > 0 && target.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD)
		{
			dam += (smite * 2.5F);
			
			if(!worldObj.isRemote)
			{
				System.out.println("Smite " + (smite * 2.5F));
			}
		}
		
		if(!worldObj.isRemote)
		{
			System.out.println("End " + dam);
		}
		
		return dam;
	}
	
	private EnumToolMaterial getMaterial() 
	{
		if(getSpear().getItem() instanceof ItemSpearMF)
		{
			return ((ItemSpearMF)getSpear().getItem()).getMaterial();
		}
		return EnumToolMaterial.STONE;
	}

	private void damageSpear() 
    {
    	ItemStack wep = getSpear();
    	if(wep != null)
    	{
    		if(isJavelin())
    		{
    			if(rand.nextInt(10) == 0)
    			{
    				setDead();
    			}
    		}
    		else
    		{
    			if(shootingEntity instanceof EntityLivingBase)
    			{
    				wep.damageItem(1, (EntityLivingBase)shootingEntity);
    			}
    			else
    			{
    				wep.attemptDamageItem(1, rand);
    			}
    			if(wep.getItemDamage() > wep.getMaxDamage())
    			{
    				setDead();
    			}
    			
    		}
    	}
	}

	public void syncSpear() 
    {
    	if(!worldObj.isRemote)
    	{
    		try
    		{
    			Packet packet = PacketManagerMF.getPacketItemStackArray(this, 0, getSpear());
    			PacketDispatcher.sendPacketToAllPlayers(packet);
    		}catch(Exception e){}
    	}
	}
	public void sendTrackerPacket()
	{
		try {
			Packet packet = PacketManagerMF.getEntityPacketDoubleArray(this, new double[]{posX, posY, posZ, motionX, motionY, motionZ, rotationPitch, rotationYaw, prevRotationPitch, prevRotationYaw});
			FMLCommonHandler.instance().getMinecraftServerInstance()
					.getConfigurationManager()
					.sendPacketToAllPlayers(packet);
		} catch (NullPointerException e) {
			;
		}
	}

	/**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        nbt.setShort("xTile", (short)this.xTile);
        nbt.setShort("yTile", (short)this.yTile);
        nbt.setShort("zTile", (short)this.zTile);
        nbt.setByte("inTile", (byte)this.inTile);
        nbt.setByte("inData", (byte)this.inData);
        nbt.setByte("shake", (byte)this.arrowShake);
        nbt.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        nbt.setByte("pickup", (byte)this.canBePickedUp);
        nbt.setDouble("damage", this.damage);
        
        if(getSpear() != null)
        {
        	NBTTagCompound itemNBT = new NBTTagCompound();
        	
        	getSpear().writeToNBT(itemNBT);
        	nbt.setCompoundTag("Spear", itemNBT);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        this.xTile = nbt.getShort("xTile");
        this.yTile = nbt.getShort("yTile");
        this.zTile = nbt.getShort("zTile");
        this.inTile = nbt.getByte("inTile") & 255;
        this.inData = nbt.getByte("inData") & 255;
        this.arrowShake = nbt.getByte("shake") & 255;
        this.inGround = nbt.getByte("inGround") == 1;

        if (nbt.hasKey("damage"))
        {
            this.damage = nbt.getDouble("damage");
        }

        if (nbt.hasKey("pickup"))
        {
            this.canBePickedUp = nbt.getByte("pickup");
        }
        else if (nbt.hasKey("player"))
        {
            this.canBePickedUp = nbt.getBoolean("player") ? 1 : 0;
        }
        if(nbt.hasKey("Spear"))
        {
        	NBTTagCompound itemNBT = nbt.getCompoundTag("Spear");
        	
        	setSpear(ItemStack.loadItemStackFromNBT(itemNBT));
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
        {
            boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;

            if (getSpear() != null && this.canBePickedUp == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(getSpear()))
            {
                flag = false;
            }

            if (flag)
            {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    public void setDamage(double par1)
    {
        this.damage = par1;
    }

    public double getDamage()
    {
        return this.damage;
    }
    public boolean isJavelin()
    {
    	if(getSpear() != null && !getSpear().isItemStackDamageable())
    	{
    		return true;
    	}
    	return false;
    }

    /**
     * Sets the amount of knockback the arrow applies when it hits a mob.
     */
    public void setKnockbackStrength(int par1)
    {
        this.knockbackStrength = par1;
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public void setIsCritical(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -2)));
        }
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public boolean getIsCritical()
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        return (b0 & 1) != 0;
    }

	public EntityThrownSpear setSpear(ItemStack item) 
	{
		this.spearItem = item;
		syncSpear();
		return this;
	}
	public ItemStack getSpear()
	{
		return spearItem;
	}

	@Override
	public void setItem(ItemStack item, int slot)
	{
		if(worldObj.isRemote)
		{
			setSpear(item);
		}
	}

	@Override
	public ItemStack getRenderItem() 
	{
		return getSpear();
	}

	@Override
	public int getSpin() {
		return 0;
	}

	@Override
	public int getRotate() {
		return 0;
	}

	@Override
	public float getScale() {
		return 2.0F;
	}

	@Override
	public boolean isEnchanted() 
	{
		return getSpear() != null ? getSpear().isItemEnchanted() : false;
	}

	@Override
	public void recievePacket(ByteArrayDataInput data) {
		posX = data.readDouble();
		posY = data.readDouble();
		posZ = data.readDouble();
		
		motionX = data.readDouble();
		motionY = data.readDouble();
		motionZ = data.readDouble();
		
		rotationPitch = (float)data.readDouble();
		rotationYaw = (float)data.readDouble();
		prevRotationPitch = (float)data.readDouble();
		prevRotationYaw = (float)data.readDouble();
	}
}
