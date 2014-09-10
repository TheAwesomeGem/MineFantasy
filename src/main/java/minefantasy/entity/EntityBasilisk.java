package minefantasy.entity;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.item.ArmourDesign;
import minefantasy.item.ItemListMF;
import minefantasy.system.TacticalManager;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBasilisk extends EntityMob {

	public EntityBasilisk(World world)
    {
        super(world);
        
        this.setSize(2.0F, 1.0F);
		applyRandomBreed();
		
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
    }
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(4.0F);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(30.0F);
    }
	
	@Override
    public boolean getCanSpawnHere() 
    {
		if(dimension != -1 && rand.nextInt(10) != 0)
		{
			return false;
		}
		if(!canSpawnAtPos())
    	{
    		return false;
    	}
		
		return super.getCanSpawnHere();

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
		return  this.getDistance(spawn.posX, spawn.posY, spawn.posZ) > cfg.basiliskDistance;
	}
	
	@Override
	public int getTotalArmorValue()
	{
		switch(getBreed())
		{
		case 0:return 10;
		case 1:return 15;
		case 2:return 20;
		}
		return super.getTotalArmorValue();
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float dam)
	{
		this.setMouthTicks(10);
		return super.attackEntityFrom(source, dam);
	}
	
	protected void entityInit()
    {
        super.entityInit();
        this.getDataWatcher().addObject(12, Byte.valueOf((byte)-1));
        this.getDataWatcher().addObject(13, Integer.valueOf(0));
        this.getDataWatcher().addObject(14, Integer.valueOf(0));
    }
	public void setBreed(byte breed)
	{
		if(breed == 2)
		{
			isImmuneToFire = true;
		}
		else
		{
			isImmuneToFire = false;
		}
		getDataWatcher().updateObject(12, breed);
		
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(getBreedHealth());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(getAttackStrength());
	}
	public void setMouthTicks(int tick)
	{
		getDataWatcher().updateObject(13, tick);
	}
	
	/**
	 * gets the breed
	 * @return 0 = poison, 1 = petrify, 2 = nether
	 */
	public byte getBreed()
	{
		return getDataWatcher().getWatchableObjectByte(12);
	}
	public int getMouthTicks()
	{
		return getDataWatcher().getWatchableObjectInt(13);
	}
	
	public void setGazeTime(int tick)
	{
		getDataWatcher().updateObject(14, tick);
	}
	public int getGazeTime()
	{
		return getDataWatcher().getWatchableObjectInt(14);
	}
	
	/**
	 * Sets to nether in nether,
	 * 1:5 chance stone:poison in over
	 */
	public void applyRandomBreed()
	{
		if(this.dimension == -1)
		{
			setBreed((byte)2);
			setSize(3.0F, 2.0F);
		}
		else
		{
			if(rand.nextInt(3) == 0 && getDistanceAway() > cfg.basiliskDistance*2)
			{
				setBreed((byte)1);
			}
			else
			setBreed((byte)0);
		}
		this.setHealth(getMaxHealth());
	}

	public float getBreedHealth()
	{
		if(getBreed() == 2)
		{
			return 60F;
		}
		return 30F;
	}
	
	@SideOnly(Side.CLIENT)
    public String getTexture()
    {
        return data_minefantasy.image("/mob/Basilisk" + getBreedStr() + ".png");
    }
	
	public float getAttackStrength()
	{
		switch(getBreed())
		{
		case 1:
			return 10;
		case 2:
			return 12;
		}
		return 8;
	}
	
	@Override
	public void onLivingUpdate()
	{
		 super.onLivingUpdate();
		 
		 if(!worldObj.isRemote)
		 {
			 int sndTimer = getGazeTime();
			 if(sndTimer > 0)sndTimer --;
			 setGazeTime(sndTimer);
			 
			 int mouth = getMouthTicks();
			 if(mouth > 0)mouth --;
			 setMouthTicks(mouth);
			 
			 
			 for(int a = 0; a < worldObj.playerEntities.size(); a ++)
			 {
				 EntityPlayer pl = (EntityPlayer)worldObj.playerEntities.get(a);
				 
				 if(isCrosshairOver(pl))
				 {
					 if(!pl.capabilities.isCreativeMode)
					 applyGaze(pl);
				 }
			 }
			if(getAttackTarget() != null)
			{
				EntityLivingBase tar = getAITarget();
				if(tar != null && tar != riddenByEntity && !(tar instanceof EntityBasilisk) && tar != this)
				{
					if(!(tar instanceof EntityPlayer))
					{
						if(this.canEntityBeSeen(tar) && !TacticalManager.isFlankedBy(this, tar, 270) && !TacticalManager.isFlankedBy(tar, this, 270))
						{
							applyGaze(tar);
						}
					}
				}
			}
		 }
	}
	public String getBreedStr()
	{
		if(getBreed() == 1)return "Stone";
		if(getBreed() == 2)return "Fire";
		return "";
	}
	@Override
	protected boolean isAIEnabled()
    {
        return true;
    }
	
	
	public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);

        nbt.setByte("Breed", getBreed());
        nbt.setInteger("Gaze", getGazeTime());
    }

    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);

        setBreed(nbt.getByte("Breed"));
        setGazeTime(nbt.getInteger("Gaze"));
    }

    public boolean isCrosshairOver(EntityPlayer player)
    {
    	if(!TacticalManager.isFlankedBy(player, this, 270))
        {
            Vec3 var3 = player.getLook(1.0F).normalize();
            Vec3 var4 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - player.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - (player.posY + (double)player.getEyeHeight()), this.posZ - player.posZ);
            double var5 = var4.lengthVector();
            var4 = var4.normalize();
            double var7 = var3.dotProduct(var4);
            return var7 > 1.0D - 0.025D / var5 ? player.canEntityBeSeen(this) : false;
        }
    	return false;
    }
    
    public void applyGaze(EntityLivingBase target)
    {
    	if(worldObj.isRemote)return;
    	
    	if(getGazeTime() > 0)return;
    		
    	boolean hit = false;
    	byte breed = getBreed();
    	int diff = worldObj.difficultySetting;
    	
    	if(getBreed() == 0)//Poison
    	{
    		target.addPotionEffect(new PotionEffect(Potion.poison.id, 200 * diff, 1));
    	}
    	if(getBreed() == 1)//Petrify
    	{
    		target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 300 * diff, 100));
    		target.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 300 * diff, 100));
    	}
    	if(getBreed() == 2)//Fire
    	{
    		target.setFire(5*diff);
    	}
    	
    	if(getAttackTarget() == null)
    	{
    		setAttackTarget(target);
    		this.getNavigator().tryMoveToEntityLiving(target, 0.22F);
    	}
    	
    	worldObj.playSoundAtEntity(target, "mob.zombie.unfect", 1.0F, 1.0F);
    	setGazeTime(getCooldown());
    }
	private int getCooldown() {
		if(getBreed() == 2)
		{
			return 40;
		}
		return 60;
	}
	@Override
    public boolean attackEntityAsMob(Entity en)
    {
		if(!this.canEntityBeSeen(en))return false;
		
    	this.setMouthTicks(10);
    	worldObj.playSoundAtEntity(this, data_minefantasy.sound("mob.bite"), 1, 1);
    	return super.attackEntityAsMob(en);
    }
    @Override
    public void onDeath(DamageSource source)
    {
    	super.onDeath(source);
    	this.setMouthTicks(10);
    }
    @Override
    protected String getLivingSound()
    {
        return data_minefantasy.sound("mob.hiss");
    }
    @Override
    protected String getHurtSound()
    {
        return "damage.hit";
    }
    @Override
    protected String getDeathSound()
    {
        return "damage.hit";
    }
    @Override
    public float getSoundPitch()
    {
    	return 1.0F;
    }
    @Override
    public void playLivingSound()
    {
    	super.playLivingSound();
    	this.setMouthTicks(3);
    }
    @Override
    protected void playStepSound(int x, int y, int z, int m)
    {
		 this.playSound(data_minefantasy.sound("mob.basiliskWalk"), 1F, 1.0F);
    }
    
    @Override
    public String getEntityName()
    {
    	if(getBreed() == 2)
    	{
    		return "Nether Basilisk";
    	}
    	return "Basilisk";
    }//EntitySpider
	public float getScale() {
		if(getBreed() == 2)
		{
			return 1.5F;
		}
		return 1.0F;
	}
	
	@Override
	protected int getDropItemId()
    {
		return 0;
    }

	@Override
    protected void dropFewItems(boolean playerKill, int looting)
    {
		int counter;

        int amount = this.rand.nextInt(2) + this.rand.nextInt(1 + looting);

        for (counter = 0; counter < amount; ++counter)
        {
            if (this.isBurning())
            {
                this.dropItem(ItemListMF.basiliskCooked.itemID, 1);
            }
            else
            {
                this.dropItem(ItemListMF.basiliskRaw.itemID, 1);
            }
        }
        
        
        
        
        amount = 1 + this.rand.nextInt(1 + looting);

        for (counter = 0; counter < amount; ++counter)
        {
        	this.dropItem(ItemListMF.misc.itemID, 1, getHide());
        }
        
        if(getBreed() == 2)
        {
	        if (playerKill && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + looting) > 0))
	        {
	            this.dropItem(Item.netherStalkSeeds.itemID, 1);
	        }
        }
    }
	
	private int getHide()
	{
		switch(getBreed())
		{
		case 0:return ItemListMF.hideBasiliskBlue;
		case 1:return ItemListMF.hideBasiliskBrown;
		case 2:return ItemListMF.hideBasiliskBlack;
		}
		return ItemListMF.hideBasiliskBlue;
	}
	public EntityItem dropItem(int id, int num, int dam)
    {
        return this.dropItemWithOffset(id, num, dam, 0.0F);
    }
	public EntityItem dropItemWithOffset(int id, int stack, int damage, float offset)
    {
        return this.entityDropItem(new ItemStack(id, stack, damage), offset);
    }
	
	public float getMouthAngle()
	{
		float ang = (float)getMouthTicks();
		if(ang > 10F)ang = 10F;
		
		return ang/10;
	}
	private double getDistanceAway()
	{
		ChunkCoordinates spawn = worldObj.getSpawnPoint();
		return this.getDistance(spawn.posX, spawn.posY, spawn.posZ);
	}
}
