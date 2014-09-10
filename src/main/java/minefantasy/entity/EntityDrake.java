/**
 * 
 */
package minefantasy.entity;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.api.tactic.ISpecialSenses;
import minefantasy.entity.ai.drake.*;
import minefantasy.item.ItemListMF;
import minefantasy.system.MF_Calculate;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

/**SPEED: DNDspeed * 0.06;
 *(nonAI) DNDspeed * 0.14;
 * Human speed: 5DND, = 0.7MC
 * XP = DNDxp/10
 * SPAWN: DNDlvl * 3
 * 
 * 
 * Stats:
 * XP: 20
 * Spawn = 300
 * Age: 3h20m
 * Speed 7
 * Hp 30
 * Dam 4 (2.0H)
 * @author AnonymousProductions
 *
 */
public class EntityDrake extends EntityDaymob implements ISpecialSenses, IGrowable{

	private int mouthAngle = 0;
	public int tailIdleY;
	public int armScratch;
	public int scratchTime;
	public int tailDirY;
	private int age = 100;
	private int maxAge = 100;
	private final int dataWMin = 13;
	/**
	 * Displays what state of AI the mob is in
	 * @if_0 normal
	 * @if_1 looking for home
	 * @if_2 protect egg
	 */
	public byte state;
	private int[] home = new int[]{0,0,0};
	private boolean hasHome;
	
	public EntityDrake(World world) 
	{
		super(world);
		
		stepHeight = 1;
		syncStats();
		this.experienceValue = 20;
		setHealth(this.getMaxHealth());
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0F, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityLiving.class, 1.0F, false));
        this.tasks.addTask(3, new EntityAIFindCave(this, 1.0F));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0F, false));
        this.tasks.addTask(6, new EntityAIWander(this, 0.8F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
	}
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(getMoveSpeed());
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(30.0F);
    }

	public EntityDrake(World world, double x, double y, double z, int a)
	{
		this(world);
		setPosition(x, y, z);
		setAge(a);
	}
	
	/**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk() {
        return 2;
    }

	private void setAge(int i) 
	{
		age = i;
		float scw = getScaleOnAge()*1.5F * getScaleOnBreed();
		float sch = getScaleOnAge()*2.5F * getScaleOnBreed();
		setSize(scw, sch);
		posY += 0.02D;
		this.setSpeed(getMoveSpeed());
		this.setMaxHealthStat(this.getMaxHealthStat());
	}

	/**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float getBlockPathWeight(int x, int y, int z)
    {
    	float weight;
    	if(state == 1)
    	{
    		return 0.5F - this.worldObj.getLightBrightness(x, y, z);
    	}
    	
    	return this.worldObj.getBlockId(x, y - 1, z) == Block.grass.blockID ? 10.0F : this.worldObj.getLightBrightness(x, y, z) - 0.5F;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, int dam)
    {
    	if(source.getEntity() != null && source.getEntity() instanceof EntityLiving && getLifeStage() <= 1)
    	{
    		AxisAlignedBB search = this.boundingBox.expand(32, 32, 32);
    		List<EntityDrake> friends = worldObj.getEntitiesWithinAABB(EntityDrake.class, search);
    		for(EntityDrake drake : friends)
    		{
    			drake.setAttackTarget((EntityLiving)source.getEntity());
    		}
    	}
    	return super.attackEntityFrom(source, dam);
    }
    @Override
    public boolean interact(EntityPlayer player)
    {
    	if(player.getCurrentEquippedItem() == null && this.getLifeStage() == 0)player.swingItem();
    	if(player.getCurrentEquippedItem() == null && this.getLifeStage() == 0 && rand.nextInt(5) == 0)
    	{
    		player.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
    		mouthAngle = 10;
    		this.playLivingSound();
   		}
    	return super.interact(player);
    }

	public float getMaxHealthStat()
	{
		return (int)((30 * getScaleOnAge())*this.getTerritoryBuff()) * getBreedHpMod();
	}
	
	private float getBreedHpMod() 
	{
		return 1.0F + (2.0F*getBreed());
	}
	private float getBreedAcMod() 
	{
		return 1.0F + (1.0F*getBreed());
	}
	private float getBreedDamMod() 
	{
		return 1.0F + (0.6F*getBreed());
	}
	
	public void setMaxHealthStat(float f)
	{
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(f);
	}
	
	public void setSpeed(float speed)
	{
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(speed);
	}
	
	@Override
	public int getTalkInterval()
	{
		return 240;
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(dataWMin, Integer.valueOf(mouthAngle));
		dataWatcher.addObject(dataWMin+1, Integer.valueOf(0));
		dataWatcher.addObject(dataWMin+2, Integer.valueOf(age));
		dataWatcher.addObject(dataWMin+3, Integer.valueOf(0));
	}
	

	@SideOnly(Side.CLIENT)
	public float getMouthAngle()
	{
		int angle = mouthAngle;
		if(angle < -10)angle = -10;
		if(angle > 10)angle = 10;
		return angle*2;
	}
	
	@SideOnly(Side.CLIENT)
	public float getTailYAngle()
	{
		int angle = tailIdleY;
		if(angle > 20)angle = 20;
		return angle;
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(worldObj.getTotalWorldTime() <= 2) setDead();
		if(mouthAngle > 0)mouthAngle --;
		if(worldObj.isRemote)
		{
			mouthAngle = dataWatcher.getWatchableObjectInt(dataWMin);
			age = dataWatcher.getWatchableObjectInt(dataWMin+2);
		}
		else
		{
			if(ticksExisted % 20 == 0)
			{
			}
			dataWatcher.updateObject(dataWMin, Integer.valueOf(mouthAngle));
			dataWatcher.updateObject(dataWMin+1, Integer.valueOf(isIdle() ? 1 : 0));
			dataWatcher.updateObject(dataWMin+2, Integer.valueOf(age));
			
			if(this.ticksExisted % getAgeMultiplier() == 0)
			{
				grow();
			}
				
			if(this.ticksExisted % 100 == 0)
			{
				this.heal(1);
			}
			
			if(!hasHome && state == 0 && this.getLifeStage() == 3 && rand.nextInt(8000) == 0)
			{
				AxisAlignedBB bb = this.boundingBox.expand(64, 32, 64);
				if(worldObj.getEntitiesWithinAABB(EntityDrake.class, bb).size() < 5)
				state = 1;
			}
		}

		
		
		//ANIMATE
		if(worldObj.isRemote)
		{
			if(tailDirY == 0)//-
			{
				tailIdleY -=3;
				if(tailIdleY <= -15)tailDirY = 1;
			}
			if(tailDirY == 1)//-
			{
				tailIdleY +=3;
				if(tailIdleY >= 15)tailDirY = 0;
			}
			if(tailIdleY == 0)
			{
				if(rand.nextInt(3) == 0)
				{
					tailDirY = 2;
				}
			}
			if(rand.nextInt(100) == 0)
			{
				tailDirY = rand.nextInt(1);
			}
			if(scratchTime > 0)
			{
				if(!isIdle())
				{
					scratchTime = armScratch = 0;
				}
				scratchTime --;
				armScratch --;
				if(armScratch <= 0)
					armScratch = 5;
			}
			else
			{
				if(rand.nextInt(500) == 0 && isIdle())
				{
					scratchTime = 30 + rand.nextInt(50);
					armScratch = 5;
				}
				else
				{
					armScratch = 0;
				}
			}
		}
		if(this.state == 1 && !hasHome)
		{
			if(!worldObj.canBlockSeeTheSky((int)posX, (int)posY, (int)posZ))
			{
				int chance = (int)worldObj.getBlockLightValue((int)posX, (int)posY, (int)posZ);
				if(worldObj.getBlockId((int)posX, (int)posY-1, (int)posZ) == Block.stone.blockID)
					chance += 9000;
				if(chance < 5 || rand.nextInt(1000000) < chance)
				{
					AxisAlignedBB search = boundingBox.expand(64, 32, 64);
					List drakes = worldObj.getEntitiesWithinAABB(EntityDrake.class, search);
					if(drakes.size() < 8)
					settle((int)posX, (int)posY-1, (int)posZ);
				}
			}
		}
		
		
		if(hasHome)
		{
			double distance = this.getDistance(home[0], home[1], home[2]);
			if(distance > 8)
			{
				this.setAttackTarget(null);
				getNavigator().tryMoveToXYZ(home[0], home[1], home[2], getMoveSpeed());
			}
		}
	}
	public float getMoveSpeed()
	{
		return 0.5F * this.getScaleOnAge();
	}

	private void syncStats() 
	{
		this.setSpeed(getMoveSpeed());
		this.setMaxHealthStat(this.getMaxHealthStat());
		
	}
	private int getAgeMultiplier() {
		
		return (int)MF_Calculate.getTicksForMinutes(2);
	}
	private boolean isIdle() {
		if(worldObj.isRemote)
		{
			return dataWatcher.getWatchableObjectInt(dataWMin+1) == 1;
		}
		else
		{
			return this.getAttackTarget() == null && getNavigator().noPath();
		}
	}

	@Override
	public void playLivingSound()
	{
		super.playLivingSound();
		mouthAngle = 20;
	}
	
	@Override
	public int getTotalArmorValue()
    {
        return (int)(5 * getScaleOnAge()*getBreedAcMod());
    }

	@Override
	protected boolean isAIEnabled()
    {
        return true;
    }
	
	@Override
	public boolean getCanSpawnHere()
    {
    	if(rand.nextInt(10) != 0)
    	{
    		return false;
    	}
    	
    	AxisAlignedBB bb = this.boundingBox.expand(200, 100, 200);
    	List<EntityDrake> list2 = worldObj.getEntitiesWithinAABB(EntityDrake.class, bb);
    	if(list2.size() > 5)
    	{
    		return false;
    	}	
    	//if(worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT)
    		//return false;
    	
		return super.getCanSpawnHere();
    }
	
	
	public boolean attackEntityAsMob(Entity entity)
    {
		if(!this.canEntityBeSeen(entity))return false;
		
        mouthAngle = 11;
        return super.attackEntityAsMob(entity);
    }
	@Override
	public float getAttackStrength(Entity entity)
	{
		return (int)((5 * this.getScaleOnAge()) * getTerritoryBuff() * getBreedDamMod());
	}
	
	/**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound() {
        return data_minefantasy.sound("mob.drakeidle");
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound() {
        return data_minefantasy.sound("mob.drakehurt");
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound() {
        return data_minefantasy.sound("mob.drakeidle");
    }
    
    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.playSound(data_minefantasy.sound("mob.drakestep"), 0.15F, 1.0F);
    }
    
    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return 0;
    }
    
    protected void dropRareDrop(int par1)
    {
    }
    
    @SideOnly(Side.CLIENT)
    public float getScratchForDisplay()
    {
    	int time = 5-armScratch;
    	return (100/5)*time;
    }
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("Age", age);
        nbt.setInteger("Breed", getBreed());
        nbt.setByte("DarkeState", state);
        nbt.setIntArray("Territory", home);
        nbt.setBoolean("hasHome", hasHome);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
    	super.readEntityFromNBT(nbt);
    	setAge(nbt.getInteger("Age"));
    	state = nbt.getByte("DarkeState");
    	home = nbt.getIntArray("Territory");
    	hasHome = nbt.getBoolean("hasHome");
    	if(nbt.hasKey("Breed"))
    	{
    		setBreed(nbt.getInteger("Breed"));
    	}
    }
    
    @Override
    public void setAttackTarget(EntityLivingBase entity)
    {
    	if(getLifeStage() >= 2)
    	super.setAttackTarget(entity);
    }

	@Override
	public int getViewingArc() {
		return 270;
	}

	@Override
	public int getHearing() {
		return 0;
	}

	@Override
	public int getSight() {
		return 15;
	}

	@Override
	public float getTotalScale()
	{
		return getScaleOnAge() * getScaleOnBreed() *0.8F;
	}

	private float getScaleOnAge() {
			return age > 10 ? 0.01F * age : 0.1F;
	}
	@Override
	public float getSoundPitch()
	{
		float pitchDown = 1F*getScaleOnAge();
		return 2F - pitchDown;
	}
	
	@Override
	public float getSoundVolume()
	{
		float volumeUp = 0.75F*getScaleOnAge();
		return 0.25F + volumeUp;
	}

	
	/**
	 * Gets the stage of life
	 * @return 0:Hatchling_______1:Young_______2:Juvenile_______3: Adult
	 */
	public byte getLifeStage()
	{
		if(age > 60)
			return 3;//ADULT
		if(age > 30)
			return 2;//JUVENILE
		if(age > 10)
			return 1;//YOUNG
		if(age > 0)
			return 0;//HATCHLING
		return 3;//ADAULT
	}

	private void spawnHatchlings()
	{
		if(worldObj.isRemote)
			return;
		int amount = rand.nextInt(3) + 1;
		for(int a = 0; a < amount; a++)
		{
			EntityDrake hatchling = new EntityDrake(worldObj, posX, posY, posZ, 1);
			worldObj.spawnEntityInWorld(hatchling);
		}
	}
	public boolean isChild()
	{
		return this.getLifeStage() <= 1;
	}
	public void grow()
	{
		if(hasRoomToGrow() && age < maxAge)
		{
			int age2 = age + 1;
			setAge(age2);
		}
	}
	
	public boolean hasRoomToGrow()
	{
	        for (int var1 = 0; var1 < 8; ++var1)
	        {
	            float var2 = ((float)((var1 >> 0) % 2) - 0.5F) * this.width * 1.1F;
	            float var3 = ((float)((var1 >> 1) % 2) - 0.5F) * 0.1F;
	            float var4 = ((float)((var1 >> 2) % 2) - 0.5F) * this.width * 1.1F;
	            int var5 = MathHelper.floor_double(this.posX + (double)var2);
	            int var6 = MathHelper.floor_double(this.posY + (double)this.getEyeHeight() + (double)var3);
	            int var7 = MathHelper.floor_double(this.posZ + (double)var4);

	            if (this.worldObj.isBlockNormalCube(var5, var6, var7))
	            {
	                return false;
	            }
	        }

	        return true;
	}
	
	
	private void settle(int x, int y, int z) {
		if(hasHome)
			return;
		if(state == 2)
			return;
		
		System.out.println("Drake Settle");
		
		//SET HOME
		this.home[0] = x;
		this.home[1] = y;
		this.home[2] = z;
		hasHome = true;
		
		//SETS BUFF ON DIFFICULTY
		//Easy = 50%inc
		//Normal = 2x
		//Hard = 3x
		float buff = getTerritoryBuff();
		this.state = 2;
		int heal = (int)(this.getMaxHealth() * (getTerritoryBuff() - 1F));
		heal(heal);
		this.spawnHatchlings();
	}

	/**
	 * 
	 * @return The multiplier of stats when territory owned
	 */
	private float getTerritoryBuff() 
	{
		if(state != 2)
			return 1F;
		
		int diff = worldObj.difficultySetting;
		float buff = 1.0F;
		if(diff == 2)buff = 1.5F;
		if(diff == 3)buff = 2.0F;
		
		return buff;
	}

	@Override
	public double getDistanceToSpawn() {
		return cfg.drakeDistance;
	}
	
	@Override
	protected void dropFewItems(boolean playerKill, int looting)
    {
        int counter;

        int amount = this.rand.nextInt(2) + this.rand.nextInt(1 + looting) + (int)(getScaleOnAge());

        for (counter = 0; counter < amount; ++counter)
        {
            if (this.isBurning())
            {
                this.dropItem(ItemListMF.drakeCooked.itemID, 1);
            }
            else
            {
                this.dropItem(ItemListMF.drakeRaw.itemID, 1);
            }
        }
        
        
        
        
        amount = (getScaleOnAge()>0.5F ? 1 : 0) + this.rand.nextInt(1 + looting);

        for (counter = 0; counter < amount; ++counter)
        {
        	this.dropItem(ItemListMF.misc.itemID, 1, ItemListMF.hideDrake);
        }
    }
	
	public EntityItem dropItem(int id, int num, int dam)
    {
        return this.dropItemWithOffset(id, num, dam, 0.0F);
    }
	public EntityItem dropItemWithOffset(int id, int stack, int damage, float offset)
    {
        return this.entityDropItem(new ItemStack(id, stack, damage), offset);
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
		return (super.getMountedYOffset() + 0.5D)*this.getTotalScale();
	}
	
	private void setBreed(int i)
	{
		dataWatcher.updateObject(dataWMin+3, i);
	}
	public int getBreed()
	{
		return dataWatcher.getWatchableObjectInt(dataWMin+3);
	}
	
	@Override
    public EntityLivingData onSpawnWithEgg(EntityLivingData data)
    {
		double distance = getDistanceAway();
		
		if(distance > getDistanceToSpawn()*3.0F)
		{
			setBreed(2);
		}
		else if(distance > getDistanceToSpawn()*2.0F)
		{
			setBreed(1);
		}
		else
		{
			setBreed(0);
		}
		
		setAge(100);
		if(rand.nextInt(3) == 0)
			setAge(50);
		
        return super.onSpawnWithEgg(data);
    }
	
	private double getDistanceAway()
	{
		ChunkCoordinates spawn = worldObj.getSpawnPoint();
		return this.getDistance(spawn.posX, spawn.posY, spawn.posZ);
	}
	
	private float getScaleOnBreed()
	{
		if(getBreed() == 2)
		{
			return 1.1F;
		}
		return 1.0F;
	}
	
	@SideOnly(Side.CLIENT)
	public String getTexture()
	{
		int b = getBreed();
		if(b == 1)
		{
			return "DrakeBlue";
		}
		if(b == 2)
		{
			return "DrakeGold";
		}
		return "Drake";
	}
}
