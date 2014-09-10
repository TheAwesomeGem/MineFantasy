package minefantasy.entity;

import java.util.List;

import minefantasy.MineFantasyBase;
import minefantasy.item.ItemListMF;
import minefantasy.system.CombatManager;
import minefantasy.system.EntityDamageSourceAP;
import minefantasy.system.MF_Calculate;
import minefantasy.system.StatListMF;
import minefantasy.system.TacticalManager;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 * 
 * Breeds:
 * Red: 100Hp, high fire breath, high retreat
 * Green: 120Hp, low fire breath, low retreat
 * Black: 150Hp, Med fire breath, very high retreat, heals
 * Gold: 200Hp, very high fire breath, very low retreat
 */
public class EntityDragonSmall extends EntityFlyingMob implements IMob {

	private final int baseData = 13;
    public int courseChangeCooldown = 0;
    public double waypointX;
    public double waypointY;
    protected int breed;
    private Entity prevAttackTarget;
    public double waypointZ;
    private Entity targetedEntity = null;
    public int wingAngle = 45;
    public int wingFlap;
    public int jawMove;
    public int neckAngle;
    public boolean isFlying;
    private int retreatCooldown;
    public int tailX;
    public int fireBreathTick;
    public int groundTick;
    public int wingTick;
    public int tailZ;
    public int armSwing;
    public int armSwing2;
    private float moveSpeedOrigin;
    public int fireBreathCooldown;
    public int throwCooldown;
    /** Cooldown time between target loss and new target aquirement. */
    private int aggroCooldown = 0;
    public int prevAttackCounter = 0;
    public int attackCounter = 0;
	private boolean fast;

    public EntityDragonSmall(World world) 
    {
        super(world);
        
        this.setSize(2F, 1F);
        this.experienceValue = 250;
        fireBreathCooldown = 1000;
        moveSpeedOrigin = 8F;
        this.getNavigator().setCanSwim(true);
    }
    @Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(48.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(moveSpeedOrigin);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(30.0F);
    }

    public boolean isFlying() {
        return true;
    }

	public float getSpeedModifier() 
    {
    	float mod = 1F;
    	if(fast)
    		mod = 2F;
    	
    	if(breed == 3)
    	{
    		mod *= 2F;
    	}
        if (isTerrestrial()) {
            return 2F + 1F*mod;
        }     	
        return mod;
    }

	@Override
	public int getTotalArmorValue()
	{
		switch(breed)
		{
		case 1:
			return 8;
		case 2:
			return 10;
		case 3:
			return 15;
		case 4:
			return 20;
		}
    	return 10;
	}
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(baseData, new Integer(this.jawMove));
        this.dataWatcher.addObject(baseData + 1, new Integer(this.groundTick));
        this.dataWatcher.addObject(baseData + 2, new Integer(this.neckAngle));
        this.dataWatcher.addObject(baseData + 3, new Integer(breed));
    }

    public float getMaxHealthOnBreed() 
    {
    	switch(breed)
		{
		case 1:
			return 80;
		case 2:
			return 120;
		case 3:
			return 150;
		case 4:
			return 200;
		}
    	return 80;
    }
    @Override
    public boolean attackEntityFrom(DamageSource source, int damage) {
    	if(source.isFireDamage())
    		return false;
    	
    	if(source == DamageSource.inWall)
    		return false;
        if(source.getEntity() != null && source.getEntity() instanceof EntityPlayer)
        {
            if(getAttackTarget() == null || damage > 16 || (targetedEntity != null && !(targetedEntity instanceof EntityPlayer)))
            	if(retreatCooldown <= 0)
                setTarget(source.getEntity());
        }
        
        if(source.getEntity() != null)
        {
            if(getAttackTarget() == null)
            	if(retreatCooldown <= 0)
            	{
            		setTarget(source.getEntity());
            	}
        }
        if(rand.nextInt(getDisengageChance()) < damage)
        {
        	if(targetedEntity != null)
        	{
        		prevAttackTarget = targetedEntity;
        	}
        	retreatCooldown = 20;
        	this.waypointY = posY + 16;
        	setTarget(null);
        }
        return super.attackEntityFrom(source, damage);
    }

	private int getDisengageChance() {
    	int d = 100;
    	
    	switch(breed)
    	{
    	case 1:
    		d = 100;
    		break;
    	case 2:
    		d = 300;
    		break;
    	case 3:
    		d = 100;
    		break;
    	case 4:
    		d = 800;
    		break;
    	}
    	
		if(worldObj.difficultySetting == 1)
			return d *= 2;
		if(worldObj.difficultySetting == 3)
			return d /= 2;
		return d;
	}
	
	@Override
    public void onLivingUpdate() 
	{
        super.onLivingUpdate();
        setStats();
        if(retreatCooldown > 0)
        {
        	retreatCooldown --;
        }
        else if(prevAttackTarget != null)
        {
        	if(!this.calmDown() || rand.nextInt(10) == 0)
        	{
        		setTarget(prevAttackTarget); 
        		
        		if(rand.nextInt(3) == 0)
     	        {
     	        	fireBreathCooldown = 0;
     	        }
     	        moveToTarget();}
     	        prevAttackTarget = null; 
        }
        if (neckAngle > 0) 
        {
	        neckAngle --;
        }
        if (isTerrestrial()) 
        {
            setMoveSpeed(moveSpeedOrigin * getFlySpeed());
        } else 
        {
        	setMoveSpeed(moveSpeedOrigin);
        }
        this.dataWatcher.updateObject(baseData, Integer.valueOf(this.jawMove));
	        this.dataWatcher.updateObject(baseData + 2, Integer.valueOf(this.neckAngle));
        
        if (groundTick > 0) {
            groundTick--;
        }
        wingTick++;
        if (wingTick == 20) {
            wingTick = 0;
            if(!isTerrestrial())
            worldObj.playSoundAtEntity(this, data_minefantasy.sound("mob.flap"), 1, 1);
        }
        int i = (120 / 20) * wingTick;
        wingFlap = -40 + i;
        if (this.onGround) {
            groundTick = 20;
        }
        
        if (isTerrestrial()) {
            wingFlap = -40;
            wingAngle = 0;
        } else {
            wingAngle = 45;
        }
        if (jawMove > 0) {
            jawMove -= 4;
        }
        if (fireBreathTick > 0) {
            jawMove = 20;
        }
        if (fireBreathTick > 0) {
            fireBreathTick--;
        }
        if (fireBreathCooldown > 0) {
            fireBreathCooldown--;
        }
        if (throwCooldown > 0) {
            throwCooldown--;
        }

        if (targetedEntity != null && fireBreathTick > 0) {
            breatheFire(targetedEntity);
        }
        if (targetedEntity != null) {
            if (this.getDistanceToEntity(targetedEntity) > 300 || targetedEntity.isDead) {
                targetedEntity = null;
            }
        }
        if (targetedEntity == null && (rand.nextInt(100) == 0) && retreatCooldown <= 0) {
            this.waypointX = this.posX + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointY = this.posY + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointZ = this.posZ + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }
        if (targetedEntity == null && (rand.nextInt(100) == 0) && retreatCooldown > 0) {
            this.waypointX = this.posX + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointY = this.posY;
            this.waypointZ = this.posZ + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }
        if(targetedEntity != null)
        {
            if(!(targetedEntity instanceof EntityPlayer))
            {
                if(targetedEntity!= null && !this.canEntityBeSeen(targetedEntity))
                {
                    if(rand.nextInt(10) == 0) setTarget(null);
                }
            }
            if (targetedEntity != null && (this.getDistanceToEntity(targetedEntity) > 200 || targetedEntity.isDead)) {
                targetedEntity = null;
            }
            if(!(targetedEntity instanceof EntityPlayer))
            {
                if(targetedEntity!= null && !this.canEntityBeSeen(targetedEntity))
                {
                    if(rand.nextInt(10) == 0) setTarget(null);
                }
            }
        }
        if(!worldObj.isRemote)
        this.dataWatcher.updateObject(baseData + 1, Integer.valueOf(this.groundTick));
        if(worldObj.isRemote)
        	groundTick = dataWatcher.getWatchableObjectInt(baseData + 1);
        
        if (willRetreat(targetedEntity) && rand.nextInt(getRetreatChance()) == 0) {
        	prevAttackTarget = targetedEntity;
        	retreatCooldown = 100;
        	this.waypointX = this.posX + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointY = this.posY + (double) ((this.rand.nextFloat()) * 16.0F);
            this.waypointZ = this.posZ + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        	setTarget(null);
        }
        if(!worldObj.isRemote)
        {
            fast = this.destroyBlocksInAABB(this.boundingBox) | this.destroyBlocksInAABB(this.boundingBox);
            
            if(isInWater())
            {
            	if (rand.nextFloat() < 0.8F)
                {
                    getJumpHelper().setJumping();
                }
            }
        }
	}

    private void setMoveSpeed(float speed) 
    {
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(speed);
	}
	private float getFlySpeed() 
    {
		return 20F;
	}

	private double getDistanceToHome() 
    {
    	ChunkCoordinates home = this.getHomePosition();
		return this.getDistance(home.posX, home.posY, home.posZ);
	}

	private int getRetreatChance() {
    	switch(breed)
		{
		case 1:
			return 200;
		case 2:
			return 400;
		case 3:
			return 100;
		case 4:
			return 1000;
		}
    	return 200;
	}

	private void moveToTarget() {
    	if(targetedEntity != null)
    	{
	    	this.waypointX = targetedEntity.posX;
	        this.waypointY = targetedEntity.posY;
	        this.waypointZ = targetedEntity.posZ;	
    	}
	}

	public boolean isTerrestrial() {
        return groundTick > 0;
    }
    @Override
    public void playLivingSound() {
        jawMove = 30;
        neckAngle = 10;
        
        
        String s = this.getLivingSound();

        float pitch = this.getSoundPitch();
        
        if (s != null)
        {
            this.playSound(s, this.getSoundVolume(), pitch);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    protected void updateEntityActionState()
    {
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == 0) {
            this.setDead();
        }
        if(worldObj.isRemote)
        	setDead();
        if (isTerrestrial() && motionY < 0) {
            motionY = 0;
        }
        if (targetedEntity != null && this.getDistanceToEntity(targetedEntity) < 6) {
            if (posY >= (targetedEntity.posY - 3) && !onGround) {
                motionY = -0.25F;
            }
        }
        this.prevAttackCounter = this.attackCounter;
        double var1 = this.waypointX - this.posX;
        double var3 = this.waypointY - this.posY;
        double var5 = this.waypointZ - this.posZ;
        double var7 = (double) MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);

        if (targetedEntity == null && var7 < 1.0D || var7 > (60D)) 
        {
            this.waypointX = this.posX + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * (16.0F));
            this.waypointY = this.posY + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * (16.0F));
            this.waypointZ = this.posZ + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * (16.0F));
        }

        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.rand.nextInt(5) + 2;

            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, var7)) {
                this.motionX += var1 / var7 * 0.2D;
                this.motionY += var3 / var7 * 0.2D;
                this.motionZ += var5 / var7 * 0.2D;
            } else {
                if (!isTerrestrial()) {
                    if (targetedEntity != null && posY < (targetedEntity.posY - 1)) {
                        motionY = 0.25;
                    }
                    if (targetedEntity != null && posY > (targetedEntity.posY)) {
                        motionY = -0.25;
                    }
                } else {
                    if (targetedEntity != null && posY < (targetedEntity.posY + 6)) {
                        motionY = 0.25;
                    }
                }
            }
        }

        if (this.targetedEntity != null && this.targetedEntity.isDead) {
            this.targetedEntity = null;
        }

        if (this.targetedEntity == null) 
        {
        	this.setEntityToAttack(EntityPlayer.class);
        }

        double var9 = 200.0D;

        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < var9 * var9) {
            double var11 = this.targetedEntity.posX - this.posX;
            double var13 = this.targetedEntity.boundingBox.minY + (double) (this.targetedEntity.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
            double var15 = this.targetedEntity.posZ - this.posZ;
            this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(var11, var15)) * 180.0F / (float) Math.PI;
            boolean inRangeOfAttack = this.targetedEntity.getDistanceToEntity(this) < 4 && this.canEntityBeSeen(targetedEntity);
            boolean inRangeOfFire = this.targetedEntity.getDistanceToEntity(this) > 4 && this.targetedEntity.getDistanceToEntity(this) < 24 && fireBreathCooldown <= 0;
            if (this.canEntityBeSeen(this.targetedEntity) && inRangeOfFire && !isConfused())
            {
                this.fireBreathTick = 20 + rand.nextInt(40);
                fireBreathCooldown = 900 + getBreathCooldown() + rand.nextInt(getBreathCooldown());
                worldObj.playSoundAtEntity(this, data_minefantasy.sound("mob.breatheFire"), 1, 1);
                jawMove = 20;
            }
            if (this.canEntityBeSeen(this.targetedEntity) && inRangeOfAttack) {
                if (this.attackCounter <= 0) {
                    attackEntity(targetedEntity, this.getAttackStrength(targetedEntity));
                    this.attackCounter = getAttackTime();
                    jawMove = 40;
                    worldObj.playSoundAtEntity(this, data_minefantasy.sound("mob.bite"), 1, 1);
                }
            } if (this.attackCounter > 0) {
                --this.attackCounter;
            }
            if (this.canEntityBeSeen(this.targetedEntity) && !inRangeOfAttack) {
                waypointX = targetedEntity.posX;
                waypointY = targetedEntity.posY;
                waypointZ = targetedEntity.posZ;
            }
            boolean var12_1 = this.targetedEntity.getDistanceSqToEntity(this) < 12;
        } else {
            this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float) Math.PI;

            if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }
    }

    private boolean isConfused()
    {
    	return this.getActivePotionEffect(Potion.confusion) != null;
	}
	private int getAttackTime()
    {
    	switch(breed)
		{
		case 1:
			return 15;
		case 2:
			return 12;
		case 3:
			return 8;
		case 4:
			return 10;
		}
		return 20;
	}
    
	private int getBreathCooldown() 
    {
    	switch(breed)
		{
		case 1:
			return 600;
		case 2:
			return 1200;
		case 3:
			return 800;
		case 4:
			return 400;
		}
		return 600;
	}

	protected void attackEntity(Entity entity, float f) 
	{
        float damage = this.getAttackStrength(entity);
        
        if (rand.nextInt(3) == 0) 
        {
            targetedEntity.motionY = 2;
            jump();
            neckAngle = 10;
            damage = 2.0F;
        }
        worldObj.playSoundAtEntity(this, data_minefantasy.sound("mob.bite"), 1, 1);
        float AP = damage * getAPDamage();
        entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage-AP);
        entity.attackEntityFrom(EntityDamageSourceAP.causeDamage(this), damage);
    }


    private float getAPDamage()
    {
		return 0.2F;
	}
	public void setEntityToAttack(Class enClass) 
    {
        List list = worldObj.getEntitiesWithinAABB(enClass, AxisAlignedBB.getAABBPool().getAABB(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(getAggro(), getAggro(), getAggro()));
        if (!list.isEmpty()) 
        {
            Entity target = (Entity) list.get(worldObj.rand.nextInt(list.size()));
            if (canAttackEntity(target))
            {
            	double r = getAggro();
            	if(TacticalManager.isDetected(this, target))
            	{
            		r /= 3D;
            	}
            	
            	boolean inRange = this.getDistanceToEntity(target) <= r;
            	if(retreatCooldown <= 0 && inRange)
            	{
            		setTarget(target);
            	}
            } else 
            {
                list.remove(target);
            }
        }
    }
    private double getAggro() 
    {
    	if(this.calmDown())
    	{
    		return 30;
    	}
		return 120;
	}
	public void onDeath(DamageSource source)
    {
        super.onDeath(source);

        if (source.getEntity() != null && source.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer var2 = (EntityPlayer)source.getEntity();
                var2.addStat(StatListMF.dragonslayer, 1);
        }
        if (source.getSourceOfDamage() != null && source.getSourceOfDamage() instanceof EntityPlayer)
        {
            EntityPlayer var2 = (EntityPlayer)source.getSourceOfDamage();
                var2.addStat(StatListMF.dragonslayer, 1);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) 
    {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("fire", this.fireBreathCooldown);
        nbt.setInteger("toss", this.throwCooldown);
        nbt.setInteger("breed", breed);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("fire")) {
            fireBreathCooldown = nbt.getInteger("fire");
        }
        if (nbt.hasKey("toss")) {
            throwCooldown = nbt.getInteger("toss");
        }
        breed = nbt.getInteger("breed");
        if (breed == 0) {
        	breed = rand.nextBoolean() ? 1 : 2;
		}
    }
    
	public String getTexture() 
	{
		switch (breed) {
		case 1:
			return data_minefantasy.image("/mob/dragonRed.png");
		case 2:
			return data_minefantasy.image("/mob/dragonGreen.png");
		case 3:
			return data_minefantasy.image("/mob/dragonBlack.png");
		case 4:
			return data_minefantasy.image("/mob/dragonGold.png");
		}
		return data_minefantasy.image("/mob/dragonRed.png");
	}

    @Override
    public void setDead() {
        super.setDead();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(worldObj.getTotalWorldTime() < 2)setDead();
        if(ticksExisted == 1)
        	createMount();
        if(!worldObj.isRemote)
        {
        	if(breed == 3 && ticksExisted % 20 == 0)
        	{
        		heal(1);
        	}
        	this.dataWatcher.updateObject(baseData + 3, Integer.valueOf(breed));
        }else
        {
        	breed = dataWatcher.getWatchableObjectInt(baseData + 3);
        }
    }

    @Override
    protected boolean canDespawn() {
        return true;
    }

    /**
     * True if the ghast has an unobstructed line of travel to the waypoint.
     */
    private boolean isCourseTraversable(double x, double y, double z, double distance) {
        if(targetedEntity != null && this.getDistanceToEntity(targetedEntity)< 1.2D)
            return false;
        double var9 = (this.waypointX - this.posX) / distance;
        double var11 = (this.waypointY - this.posY) / distance;
        double var13 = (this.waypointZ - this.posZ) / distance;
        AxisAlignedBB bb = this.boundingBox.copy();

        for (int var16 = 1; (double) var16 < distance; ++var16) {
            bb.offset(var9, var11, var13);

            if (this.worldObj.getCollidingBoundingBoxes(this, bb).size() > 0) {
            	if(!this.isBlockGlass(bb))
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound() {
        return data_minefantasy.sound("mob.dragon");
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound() {
        return data_minefantasy.sound("mob.dragonhurt");
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound() {
        return data_minefantasy.sound("mob.dragonHurt");
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId() {
        return 0;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean multiply, int amount) 
    {
    	this.dropSpecificItem(ItemListMF.misc.itemID, ItemListMF.fireGland, 1);
        int amountDropped = this.rand.nextInt(3 + amount);
        
        for (int timesDropped = 0; timesDropped < amountDropped; ++timesDropped) 
        {
        	this.dropItem(Item.gunpowder.itemID, 1);
        }
    }

    public EntityItem dropSpecificItem(int id, int damage, int amount) {
        return this.dropSpecificItemWithOffset(id, damage, amount, 0.0F);
    }

    public EntityItem dropSpecificItemWithOffset(int id, int damage, int amount, float offset) {
        return this.entityDropItem(new ItemStack(id, amount, damage), offset);
    }
    @Override
    public boolean getCanSpawnHere()
    {
    	int chance = this.dimension == 0 ? cfg.dragonChance : cfg.dragonChance/4;
    	if(rand.nextInt(cfg.dragonChance) != 0)
    	{
    		return false;
    	}
    	
    	if(worldObj.difficultySetting < cfg.dragonDiff)
		{
			return false;
		}
		
		if(!super.getCanSpawnHere())
		{
			return false;
		}
    	if(!canSpawnAtPos())
    	{
    		return false;
    	}
    	if(MineFantasyBase.isDebug())
		{
			System.out.println("Try Dragon Spawn: " + this.getDistanceAway());
		}
    	return true;
    }
    
    public boolean isProperBlock(int x, int y, int z)
    {
    	return true;
	}
    
    public boolean canSpawnAtPos() {
    	if(this.dimension != 0)
    	{
    		return true;
    	}
    	ChunkCoordinates spawn = worldObj.getSpawnPoint();
    	
    	if(!worldObj.canBlockSeeTheSky((int)posX, (int)posY+1, (int)posZ))
    	{
    		return false;
    	}
		return  this.getDistance(spawn.posX, spawn.posY, spawn.posZ) > getDistanceToSpawn();
	}
    
    /**
     * Returns the volume for the sounds this mob makes.
     */
    
    protected float getSoundVolume() {
        return 3.0F;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    private void breatheFire(Entity target)
    {
    	int spread = 1;
    	
    	for(int a = 0; a < spread; a ++)
    	{
	        double var11 = this.targetedEntity.posX - this.posX;
	        double var13 = this.targetedEntity.boundingBox.minY + (double) (this.targetedEntity.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
	        double var15 = this.targetedEntity.posZ - this.posZ;
	        EntityFirebreath var17 = new EntityFirebreath(this.worldObj, this, var11, var13, var15, 20);
	        double var18 = 1.0D;
	        Vec3 var20 = this.getLook(1.0F);
	        var17.posX = this.posX + var20.xCoord * var18;
	        var17.posY = this.posY + (double) (this.height / 2.0F) + 0.5D;
	        var17.posZ = this.posZ + var20.zCoord * var18;
	        this.worldObj.spawnEntityInWorld(var17);
    	}
    }

    public void setTarget(Entity target) {
        this.targetedEntity = target;
    }
    private boolean canAttackEntity(Entity target) {
        if(!this.canEntityBeSeen(target))return false;
        if (target instanceof EntityDragonSmall || target == this.riddenByEntity) {
            return false;
        }
        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)target;
            return !player.capabilities.isCreativeMode;
        }
        return canEntityBeSeen(target);
    }
    public int getNeckAngle()
    {
    	return this.dataWatcher.getWatchableObjectInt(baseData + 2);
    }
    public int getJawMove()
    {
    	return this.dataWatcher.getWatchableObjectInt(baseData);
    }
    public int wingFlap()
    {
    	return wingFlap;
    }
    public int wingAngle()
    {
        return wingAngle;
    }
    private boolean willRetreat(Entity entity)
    {
    	if(retreatCooldown > 0)return false;
    	if(entity == null)
    		return false;
    	if(!this.isCourseTraversable(entity.posX, entity.posY, entity.posZ, getDistanceToEntity(entity)))
    		return true;
    	return this.getDistanceToEntity(entity) < 8;
    }
    
    @Override
    protected void despawnEntity()
    {
    	super.despawnEntity();
    }
    private void setStats()
    {
		if (breed == 0) {
			
			this.setHomeArea((int)posX, (int)posY, (int)posZ, 32);
			breed = rand.nextInt(2)+1;
			
			int rareBreed = rand.nextInt(20);
			if(getDistanceAway() > cfg.dragonDistance*5)
			{
				rareBreed = rand.nextInt(2);
			}
			
			if(rareBreed == 0)
				breed = 3;
			if(rareBreed == 1)
				breed = 4;
			
			if(breed >= 3)
			{
				this.experienceValue = 500;
			}
			setMaxHealth(this.getMaxHealthOnBreed());
			this.setHealth(getMaxHealth());
		}
		isImmuneToFire = true;
					
	}
    
    private void setMaxHealth(float maxHealth) 
    {
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(maxHealth);
	}
	private boolean destroyBlocksInAABB(AxisAlignedBB box)
    {
    	if(!cfg.dragonGrief)return false;
    	
        int var2 = MathHelper.floor_double(box.minX);
        int var3 = MathHelper.floor_double(box.minY);
        int var4 = MathHelper.floor_double(box.minZ);
        int var5 = MathHelper.floor_double(box.maxX);
        int var6 = MathHelper.floor_double(box.maxY);
        int var7 = MathHelper.floor_double(box.maxZ);
        boolean var8 = false;
        boolean var9 = false;

        for (int x = var2; x <= var5; ++x)
        {
            for (int y = var3; y <= var6; ++y)
            {
                for (int z = var4; z <= var7; ++z)
                {
                    Material var13 = this.worldObj.getBlockMaterial(x, y, z);
                    int id = worldObj.getBlockId(x, y, z);
                    int meta = worldObj.getBlockMetadata(x, y, z);

                    if (var13 != null)
                    {
                        if (var13 == Material.glass)
                        {
                            var9 = true;
                            this.worldObj.setBlockToAir(x, y, z);
                            for(int a = 0 ; a < 1+rand.nextInt(4); a ++)
                                this.worldObj.playSoundAtEntity(this, "random.glass", 1F, 1F);
                        }
                        else if (isMaterialBreakable(var13))
                        {
                            var9 = true;
                            this.worldObj.setBlockToAir(x, y, z);
                            for(int a = 0 ; a < 1+rand.nextInt(4); a ++)
                                this.worldObj.playSoundAtEntity(this, "step.grass", 1F, 1F);
                        }
                        else if (var13 == Material.ground && worldObj.getBlockTileEntity(x, y, z) == null)
                        {
                            var9 = true;
                            this.worldObj.setBlockToAir(x, y, z);
                            Block.blocksList[id].dropBlockAsItem(worldObj, x, y, z, meta, 0);
                            for(int a = 0 ; a < 1+rand.nextInt(4); a ++)
                                this.worldObj.playSoundAtEntity(this, "step.gravel", 1F, 1F);
                            this.attackEntityFrom(DamageSource.cactus, 1);
                        }
                        else if (var13 == Material.grass && worldObj.getBlockTileEntity(x, y, z) == null)
                        {
                            var9 = true;
                            this.worldObj.setBlockToAir(x, y, z);
                            Block.blocksList[id].dropBlockAsItem(worldObj, x, y, z, meta, 0);
                            for(int a = 0 ; a < 1+rand.nextInt(4); a ++)
                                this.worldObj.playSoundAtEntity(this, "step.grass", 1F, 1F);
                            this.attackEntityFrom(DamageSource.cactus, 1);
                        }
                        else if ( var13 == Material.wood && worldObj.getBlockTileEntity(x, y, z) == null)
                        {
                            var9 = true;
                            this.worldObj.setBlockToAir(x, y, z);
                            Block.blocksList[id].dropBlockAsItem(worldObj, x, y, z, meta, 0);
                            for(int a = 0 ; a < 1+rand.nextInt(4); a ++)
                                this.worldObj.playSoundAtEntity(this, "mob.zombie.woodBreak", 1F, 1F);
                            this.attackEntityFrom(DamageSource.cactus, 1);
                        }
                        else if ( var13 == Material.rock && worldObj.getBlockTileEntity(x, y, z) == null)
                        {
                        	if(Block.blocksList[id].getBlockHardness(worldObj, x, y, z) < 50F && Block.blocksList[id].getBlockHardness(worldObj, x, y, z) > 0F)
                        	{
	                            var9 = true;
	                            this.worldObj.setBlockToAir(x, y, z);
	                            Block.blocksList[id].dropBlockAsItem(worldObj, x, y, z, meta, 0);
	                            for(int a = 0 ; a < 1+rand.nextInt(4); a ++)
	                                this.worldObj.playSoundAtEntity(this, "mob.zombie.iron", 1F, 1F);
	                            this.attackEntityFrom(DamageSource.cactus, 5);
                        	}
                        }
                        else if ( var13 == Material.iron && worldObj.getBlockTileEntity(x, y, z) == null)
                        {
                            var9 = true;
                            this.worldObj.setBlockToAir(x, y, z);
                            Block.blocksList[id].dropBlockAsItem(worldObj, x, y, z, meta, 0);
                            this.attackEntityFrom(DamageSource.cactus, 10);
                        }
                        else
                        {
                            var8 = true;
                        }
                    }
                }
            }
        }

        if (var9)
        {
            double var16 = box.minX + (box.maxX - box.minX) * (double)this.rand.nextFloat();
            double var17 = box.minY + (box.maxY - box.minY) * (double)this.rand.nextFloat();
            double var14 = box.minZ + (box.maxZ - box.minZ) * (double)this.rand.nextFloat();
            this.worldObj.spawnParticle("largeexplode", var16, var17, var14, 0.0D, 0.0D, 0.0D);
            
            
        }

        return var8;
    }
    
    


	private boolean isBlockGlass(AxisAlignedBB box)
    {
        int var2 = MathHelper.floor_double(box.minX);
        int var3 = MathHelper.floor_double(box.minY);
        int var4 = MathHelper.floor_double(box.minZ);
        int var5 = MathHelper.floor_double(box.maxX);
        int var6 = MathHelper.floor_double(box.maxY);
        int var7 = MathHelper.floor_double(box.maxZ);
        boolean var8 = false;
        boolean var9 = false;

        for (int var10 = var2; var10 <= var5; ++var10)
        {
            for (int var11 = var3; var11 <= var6; ++var11)
            {
                for (int var12 = var4; var12 <= var7; ++var12)
                {
                    Material var13 = this.worldObj.getBlockMaterial(var10, var11, var12);

                    if (var13 != null)
                    {
                        if (var13 == Material.glass || isMaterialBreakable(var13))
                        {
                            var9 = true;
                            //this.worldObj.setBlockWithNotify(var10, var11, var12, 0);
                            return true;
                        }
                        else
                        {
                            var8 = true;
                        }
                    }
                }
            }
        }

        if (var9)
        {
            double var16 = box.minX + (box.maxX - box.minX) * (double)this.rand.nextFloat();
            double var17 = box.minY + (box.maxY - box.minY) * (double)this.rand.nextFloat();
            double var14 = box.minZ + (box.maxZ - box.minZ) * (double)this.rand.nextFloat();
            this.worldObj.spawnParticle("largeexplode", var16, var17, var14, 0.0D, 0.0D, 0.0D);
            
        }

        return false;
    }

	private boolean isMaterialBreakable(Material material) {
		return material == Material.cactus ||
				material == Material.cake ||
				material == Material.circuits ||
				material == Material.cloth ||
				material == Material.leaves ||
				material == Material.pumpkin ||
				material == Material.redstoneLight ||
				material == Material.plants;
	}
	private void createMount()
    {
    }
	
	private boolean calmDown()
	{
        return !worldObj.isDaytime() || this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) || this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + (double)this.height), MathHelper.floor_double(this.posZ));
    }

	public double getDistanceToSpawn()
	{
		return cfg.dragonDistance;
	}

	public float getAttackStrength(Entity entity) {
		int dam = 6;
		switch(breed)
		{
		case 1:
			dam = 3;
		case 2:
			dam = 4;
		case 3:
			dam = 5;
		case 4:
			dam = 5;
		}
		return  dam + rand.nextInt(dam);
	}

	@Override
	public void applyEntityCollision(Entity entity)
	{
		if(entity instanceof EntitySkeleton && ridingEntity == null && !entity.isRiding())
		{
			entity.mountEntity(this);
			
			this.onUpdate();
			entity.onUpdate();
		}
		super.applyEntityCollision(entity);
	}

	@Override
	public double getMountedYOffset()
	{
		return super.getMountedYOffset();
	}
	
	/**
	 * Determines if the Dragon is active
	 */
	private double getDistanceAway()
	{
		ChunkCoordinates spawn = worldObj.getSpawnPoint();
		return this.getDistance(spawn.posX, spawn.posY, spawn.posZ);
	}
}
