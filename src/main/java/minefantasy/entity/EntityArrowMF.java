package minefantasy.entity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

import com.google.common.io.ByteArrayDataInput;

import minefantasy.MineFantasyBase;
import minefantasy.api.weapon.EntityDamageSourceRanged;
import minefantasy.item.ArrowType;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.StatListMF;
import minefantasy.system.cfg;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
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
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityArrowMF extends EntityArrow implements IProjectile, PacketUserMF
{
	private ArrowType type;
	
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private int inData = 0;
    private boolean hasHit = false;
    private boolean inGround = false;
    private float powerEnchant;

    /** 1 if the player can pick up the arrow */
    public int canBePickedUp = 0;

    /** Seems to be some sort of timer for animating an arrow. */
    public int arrowShake = 0;

    /** The owner of this arrow. */
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir = 0;

    /** The amount of knockback an arrow applies when it hits a mob. */
    private int knockbackStrength;
	private int kills = 0;

    public EntityArrowMF(World world)
    {
        super(world);
        if(rand.nextInt(10) == 0)
        {
        	setCritical(true);
        }
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }

    public EntityArrowMF(World world, double x, double y, double z, int id)
    {
        this(world);
        setType(id);
        this.setPosition(x, y, z);
        this.yOffset = 0.0F;
    }

    public EntityArrowMF(World world, EntityLivingBase shooter, float power)
    {
    	this(world, shooter, 1.0F, power, 0);
    }
    public EntityArrowMF(World world, EntityLivingBase shooter, float accur, float power, int id)
    {
    	this(world);
    	setType(id);
        this.shootingEntity = shooter;

        if(shooter instanceof EntityPlayer)
        {
        	canBePickedUp = ((EntityPlayer)shooter).capabilities.isCreativeMode ? 2 : 1;
        }
        this.setLocationAndAngles(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, power * 1.5F, accur);
    }
    
    public EntityArrowMF(World world, EntityLivingBase shooter, EntityLivingBase target, float power, float precision, int id)
    {
    	this(world);
    	
    	shootingEntity = shooter;
    	canBePickedUp = shooter instanceof EntityPlayer ? 1:0;
        setType(id);
        
        this.posY = shooter.posY + (double)shooter.getEyeHeight() - 0.10000000149011612D;
        double var6 = target.posX - shooter.posX;
        double var8 = target.posY + (double)target.getEyeHeight() - (0.699999988079071D / 0.2F * getArc()) - this.posY;
        double var10 = target.posZ - shooter.posZ;
        
        double range = shooter.getDistanceToEntity(target);
        
        var8 = calculateRange(var8, range, power);
        if(target.posY > shooter.posY)
        {
        	double dist = target.posY - shooter.posY;
        	var8 += dist/10;
        }
        
        double var12 = (double)MathHelper.sqrt_double(var6 * var6 + var10 * var10);
        
        if (var12 >= 1.0E-7D)
        {
            float var14 = (float)(Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
            float var15 = (float)(-(Math.atan2(var8, var12) * 180.0D / Math.PI));
            double var16 = var6 / var12;
            double var18 = var10 / var12;
            this.setLocationAndAngles(shooter.posX + var16, this.posY, shooter.posZ + var18, var14, var15);
            this.yOffset = 0.0F;
            float var20 = (float)var12 * getArc();
            this.setThrowableHeading(var6, (var8 + (double)var20), var10, power, precision);
        }
    }

    private double calculateRange(double yPos, double range, double power) {
    	double yOffset = (0.5D / 12)*range;
    	if(range > 28)
    	{
    		yOffset *= range/16;
    	}
    	if(range > 48)
    	{
    		yOffset += (1.0D / 50)*range;
    	}
    	
    	if(yOffset > 0)
        {
        	yPos += yOffset / 24D * range;
        }
    	
    	return yPos;
	}

	public EntityArrowMF(World world, EntityLivingBase shooter, float power, int type)
    {
        this(world);
        setType(type);
        this.shootingEntity = shooter;

        if (shooter instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }

        this.setLocationAndAngles(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
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
        this.dataWatcher.addObject(17, Integer.valueOf(0));
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    public void setVelocity(double xv, double yv, double zv)
    {
        this.motionX = xv;
        this.motionY = yv;
        this.motionZ = zv;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(xv * xv + zv * zv);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(xv, zv) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(yv, (double)f) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	if(worldObj.isRemote)
    	{
    		if(type == null)
    		{
    			type = ArrowType.getArrow(getType());
    		}
    	}
        super.onEntityUpdate();

        if(inGround && isReed())
        {
        	worldObj.playSoundAtEntity(this, "dig.grass", 1, 1);
        	setDead();
        }
        
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
                if (movingobjectposition.entityHit != null && !hasHit)
                {
                	f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    float dam = (float)((double)f2 * getDamage());

                    if (this.getIsCritical())
                    {
                    	dam += rand.nextFloat() * (dam/2F);
                    }

                    DamageSource damagesource = null;
                    DamageSource damagesource2 = null;

                    if (this.shootingEntity == null)
                    {
                        damagesource = DamageSource.causeArrowDamage(this, this);
                        damagesource2 = new EntityDamageSourceRanged(this, this, true);
                    }
                    else
                    {
                        damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
                        damagesource2 = new EntityDamageSourceRanged(this, this.shootingEntity, true);
                    }

                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
                    {
                        movingobjectposition.entityHit.setFire(5);
                    }
                    
                    dam = applyBonus(movingobjectposition.entityHit, dam);
                    
                    float penetrate = this.getArmourPenetration();
                    int APdamage = (int)(dam*penetrate);
                    dam -= APdamage;

                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, dam))
                    {
                    	movingobjectposition.entityHit.hurtResistantTime = 0;
                    	if(movingobjectposition.entityHit.attackEntityFrom(damagesource2, APdamage))
                    	{
                    	}
                        if (movingobjectposition.entityHit instanceof EntityLivingBase)
                        {
                            EntityLivingBase entityliving = (EntityLivingBase)movingobjectposition.entityHit;

                            if (!this.worldObj.isRemote)
                            {
                                entityliving.setArrowCountInEntity(entityliving.getArrowCountInEntity() + 1);
                            }

                            if (this.knockbackStrength > 0)
                            {
                                f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                if (f3 > 0.0F)
                                {
                                    movingobjectposition.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f3, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f3);
                                }
                            }

                            if (this.shootingEntity != null)
                            {
                                EnchantmentThorns.func_92096_a(this.shootingEntity, entityliving, this.rand);
                            }

                            if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                            {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
                            }
                        }

                        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                        boolean leathalHit = false;
                        if(movingobjectposition.entityHit instanceof EntityLivingBase)
                        {
                        	if(((EntityLivingBase)movingobjectposition.entityHit).getHealth() <= 0)
                        			leathalHit = true;
                        }
                        if (!(movingobjectposition.entityHit instanceof EntityEnderman) && !(isBroad() && leathalHit))
                        {
                        	tryBreak(movingobjectposition.entityHit);
                        }
                        else
                        {
                        	if(isBroad())
                        	{
                        		if(!isDead)
                        		{
                        		}
                        		kills ++;
                        		if(kills >= 3)
                        		{
                        			if(shootingEntity != null && shootingEntity instanceof EntityPlayer)
                        			{
                        				((EntityPlayer)shootingEntity).addStat(StatListMF.tripleKill, 1);
                        			}
                        		}
                        	}
                        }
                    }
                    else if(!isBroad())
                    {
                        this.motionX *= -0.10000000149011612D;
                        this.motionY *= -0.10000000149011612D;
                        this.motionZ *= -0.10000000149011612D;
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
                    this.setCritical(false);

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
            f1 = getGravity();

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
        if(ticksExisted <= 10 && !worldObj.isRemote)
        {
        	sendTrackerPacket();
        }
    }
    
    private void tryBreak(Entity hit) 
    {
    	hasHit = true;
    	if(this.getArrow() != null)
    	{
    		int c = getArrow().getBreakChance();
    		int chance = -1;
    		if(c != -1)
    		{
				if(c <= 0)c = 1;
				chance = rand.nextInt(c);
    		}
    		
    		if(MineFantasyBase.isDebug() && !worldObj.isRemote)
    		{
    			System.out.println("Arrow Break Chance: 1 in " + getArrow().getBreakChance() + ". hit has rolled a " + chance);
    		}
    		if(!cfg.durableArrows || chance == 0)
    		{
    			if(!worldObj.isRemote)
    			{
    				if(isReed())
    					playSound("dig.grass", 1.0F, 1.0F);
    				else
    					playSound("random.break", 1.0F, 1.0F);
    				this.setDead();
    			}
    		}
    		else
    		{
    			if(hit != null && this.canBePickedUp == 1)
    			{
    				hit.entityDropItem(this.getDroppedItem(), 0);
    			}
    			setDead();
    		}
    	}
    	else
    	{
    		if(!worldObj.isRemote)
			{
				this.setDead();
			}
    	}
	}

	private boolean isBroad() 
    {
    	if(type != null)
    	{
    		return type.arrowType == 2;
    	}
		return false;
	}

	private float applyBonus(Entity entityHit, float dam) 
    {
    	EntityLivingBase living = null;
    	if(entityHit instanceof EntityLivingBase)
    	{
    		living = (EntityLivingBase)entityHit;
    	}
    	
    	if(type != null)
    	{
    		if(type.material == ToolMaterialMedieval.DRAGONFORGE)
    		{
    			entityHit.setFire(10);
    		}
    		if(type.material == ToolMaterialMedieval.IGNOTUMITE)
    		{
    			if(shootingEntity != null && shootingEntity instanceof EntityLivingBase)
    			{
    				((EntityLivingBase)shootingEntity).heal(dam/4F);
    			}
    		}
    		if(type.material == ToolMaterialMedieval.ORNATE)
    		{
    			if (living != null && living.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD || entityHit.getClass().getName().endsWith("MoCEntityWerewolf")) 
    			{
    				living.setFire(10);
					if(living.getClass().getName().endsWith("MoCEntityWarewolf"))
					{
						dam *= 10;
					}
					else
					{
						dam *= 3;
					}
					living.worldObj.playSoundAtEntity(living, "random.fizz", 1, 1);
				}
    		}
    	}
    	dam += powerEnchant;
		return dam;
	}
	@Override
	public void setDamage(double dam)
	{
		float bonus = (float)(dam - this.getDamage());
		
		powerEnchant = bonus;
	}

	private float getArmourPenetration()
    {
    	if(type != null)
    	{
    		if(type.arrowType == 1)return 0.3F;
    		if(type.arrowType == 2)return 0.5F;
    	}
    	return 0.0F;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
    	super.writeEntityToNBT(nbt);
    	nbt.setBoolean("HasHit", hasHit);
    	nbt.setInteger("Kills", kills);
    	nbt.setInteger("type", getType());
        nbt.setShort("xTile", (short)this.xTile);
        nbt.setShort("yTile", (short)this.yTile);
        nbt.setShort("zTile", (short)this.zTile);
        nbt.setByte("inTile", (byte)this.inTile);
        nbt.setByte("inData", (byte)this.inData);
        nbt.setByte("shake", (byte)this.arrowShake);
        nbt.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        nbt.setByte("pickup", (byte)this.canBePickedUp);
        nbt.setFloat("Power", powerEnchant);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
    	super.readEntityFromNBT(nbt);
    	if(nbt.hasKey("Kills"))
    	kills = nbt.getInteger("Kills");
    	
    	if(nbt.hasKey("type"))
    	setType(nbt.getInteger("type"));
    	
        this.xTile = nbt.getShort("xTile");
        this.yTile = nbt.getShort("yTile");
        this.zTile = nbt.getShort("zTile");
        this.inTile = nbt.getByte("inTile") & 255;
        this.inData = nbt.getByte("inData") & 255;
        this.arrowShake = nbt.getByte("shake") & 255;
        this.inGround = nbt.getByte("inGround") == 1;
        hasHit = nbt.getBoolean("HasHit");

        if (nbt.hasKey("pickup"))
        {
            this.canBePickedUp = nbt.getByte("pickup");
        }
        else if (nbt.hasKey("player"))
        {
            this.canBePickedUp = nbt.getBoolean("player") ? 1 : 0;
        }
        
        powerEnchant = nbt.getFloat("Power");
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer player)
    {
    	ItemStack drop = this.getDroppedItem();
        if (drop != null && !this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
        {
            boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && player.capabilities.isCreativeMode;

            if (this.canBePickedUp == 1 && !player.inventory.addItemStackToInventory(drop))
            {
                flag = false;
            }

            if (flag)
            {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
    /*
     * Normal: 0.05
     * Broad: * 1.5
     * Bodkin: * 0.5
     * Mithril *0.5
     * Reed: * 0.75
     */
    private float getGravity() 
    {
    	float gravity = 0.05F;
    	if(type != null)
    	{
    		if(type.material == ToolMaterialMedieval.MITHRIL)
    		{
    			gravity *= 0.5F;
    		}
    		else if(type.material == EnumToolMaterial.WOOD)
    		{
    			gravity *= 0.75F;
    		}
    		
    		if(type.arrowType == 1)
    		{
    			gravity *= 0.5F;
    		}
    		else if(type.arrowType == 2)
    		{
    			gravity *= 1.5F;
    		}
    	}
    	
    	return gravity;
	}
    public String getTexture()
	{
    	if(type == null || type.getTextureName() == null)
    	{
    		return "arrowIron";
    	}
    	return type.getTextureName();
	}
    private float getArc() {
    	switch(this.getType())
    	{
    	case 1: // Shrapnel
    		return 0.8F;
    	case 2: // Needle
    		return 0.1F;
    	case 3: // Reed
    		return 0.18F;
    	case 4: // Broad
    		return 0.3F;
    	case 5: // Flint Bolt
    		return 0.12F;
    	case 6: // Bolt
    		return 0.12F;
    	default: // includes iron
    		return 0.2F;
    	}
	}

    public double getDamage()
    {
    	return ArrowType.getDamage(type);
    }
    /**
     * Sets the amount of knockback the arrow applies when it hits a mob.
     */
    public void setKnockbackStrength(int str)
    {
    	super.setKnockbackStrength(str);
        this.knockbackStrength = str;
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public void setCritical(boolean flag)
    {
    	super.setIsCritical(flag);
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (flag)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    public void setIsCritical(boolean flag)
    {
    	
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public boolean getIsCritical()
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        return (b0 & 1) != 0;
    }
    /**
     * 
     * @return
     * 0: Iron
     * 1: Shrapnel
     * 2: Needle
     * 3: Broad
     * 4: Reed
     * 5: Flint Bolt
     * 6: Bolt
     */
    public int getType()
    {
    	return dataWatcher.getWatchableObjectInt(17);
    }
    public void setType(int type)
    {
    	this.type = ArrowType.getArrow(type);
    	dataWatcher.updateObject(17, type);
    }

	public void setOwner(EntityLivingBase thrower) {
		shootingEntity = thrower;
	}
	public ItemStack getDroppedItem()
	{
		return new ItemStack(ItemListMF.arrowMF, 1, getType());
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
	private int getArrowModel()
	{
		if(type != null)
		{
			return type.arrowType;
		}
		return 0;
	}
	
	private boolean isReed()
	{
		return type != null && type.material == EnumToolMaterial.WOOD;
	}
	public ArrowType getArrow()
	{
		return ArrowType.getArrow(getType());
	}
}
