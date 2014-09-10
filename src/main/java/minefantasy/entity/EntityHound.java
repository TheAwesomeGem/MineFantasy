package minefantasy.entity;

import java.util.Iterator;
import java.util.List;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.MineFantasyBase;
import minefantasy.api.cooking.IHeatSource;
import minefantasy.api.hound.*;
import minefantasy.api.tactic.ISpecialSenses;
import minefantasy.block.special.BlockDogBowl;
import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityDogBowl;
import minefantasy.entity.ai.hound.*;
import minefantasy.item.ItemArmourMFOld;
import minefantasy.item.ItemBandage;
import minefantasy.item.ItemListMF;
import minefantasy.item.ItemPetChange;
import minefantasy.item.MedievalArmourMaterial;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/*
 * Compared to Mobs...
 * Drake: Level 15
 * Minotaur: Level 20
 * Knight: Level 20
 * Basilisk: 
 * Dragon
 * 
 * EntityWolf
 */
public class EntityHound extends EntityTameable
	implements PacketUserMF, IInventory, INameableEntity, ISpecialSenses, ISyncedInventory
{
	public int invertSpots = -1;
	private float trust = 0.0F;
	private int ID;
	/**
	 * This is just used for the Gui Screen to remember if the command tab is open
	 * It resets on loading
	 */
	public boolean showCommands = false;
	
    private float field_70926_e;
    private float field_70924_f;
    /** true is the wolf is wet else false */
    private boolean isShaking; 
    private boolean field_70928_h;
    /**
     * This time increases while wolf is shaking and emitting water particles.
     */
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
	public int eatAnimation;
	public float jawMove;
	private int chewTimer;
	private float saturationLevel;
	
	private final int IdBase = 18;
	
	private int timeUntilSit;
	//OTHER OPTIONS//
	public boolean attackMob;
	public boolean attackAnimal;
	public boolean attackPlayer;
	public boolean attackDefense = true;
	public boolean fightPvp = true;
	
	public boolean leapAttack;
	public boolean pickupItems;
	public boolean boostStep;
	
	public int strength;
	public int stamina;
	public int endurance;
	
	public int xp;
	public int xpMax = 80;
	public int level = 1;
	public int AtPoints;
	/**
	 * The position the hound is told to stay at
	 */
	private double[] stayPos = new double[3];
	
	/**
	 * The position the hound can teleport to
	 */
	private double[] homePos = new double[3];
	
	/**
	 * Slot 0 = helmet, 1 = armour 
	 */
	private ItemStack[] armour;
	public InventoryHoundPack pack = new InventoryHoundPack("Pack Hound", true, 72);

	private int lastEaten;

    public EntityHound(World world)
    {
        super(world);
        ID = rand.nextInt(100000000)+1;
        setHome(world.getSpawnPoint().posX, world.getSpawnPoint().posY, world.getSpawnPoint().posZ);
        setStats();
        armour = new ItemStack[2];
        this.setSize(0.6F, 0.8F);
        getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTargetHound(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0F, false));
        this.tasks.addTask(5, new EntityAIFollowHound(this, 1.0F, 2.0F, 4.0F));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0F));
        this.tasks.addTask(7, new EntityAIWanderHound(this, 1.0F));
        this.tasks.addTask(8, new EntityAIBegHound(this, 8.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.tasks.addTask(3, new EntityAITemptHound(this, 0.75F, false));
        
        //Defend Self
        this.targetTasks.addTask(3, new EntityAIDefendHound(this, true));
        
        //Defend Owner
        this.targetTasks.addTask(1, new EntityAIDefendOwnerHound(this));
        this.targetTasks.addTask(2, new EntityAIFightForHound(this));
        
        //Attack Entities 
        this.targetTasks.addTask(2, new EntityAIHoundAttackMonster(this, IMob.class, 0, true));
        this.targetTasks.addTask(2, new EntityAIHoundAttackAnimal(this, IAnimals.class, 0, true));
        this.targetTasks.addTask(2, new EntityAIHoundAttackPlayer(this, EntityPlayer.class, 0, true));
        
        this.targetTasks.addTask(4, new EntityAITargetPack(this, EntityAnimal.class, 200, true));
    }
    @Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(30.0F);
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    @Override
    public void setAttackTarget(EntityLivingBase target)
    {
    	if(isTamed() && getOwner() != null && getOwner() == target)
    	{
    		return;
    	}
    	if(isTamed() && target != null && target instanceof EntityPlayer)
    	{
    		if(!fightPvp && isTamed())
    		{
    			return;
    		}
    	}
        super.setAttackTarget(target);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(IdBase+1, new Byte((byte)0));//ENTITY STATE
        this.dataWatcher.addObject(IdBase+2, new Byte((byte)BlockColored.getBlockFromDye(1)));//COLLAR
        this.dataWatcher.addObject(IdBase+3, new Integer(-1));//BREED
        this.dataWatcher.addObject(IdBase+4, "");//COMMAND
        this.dataWatcher.addObject(IdBase+5, (byte)0);//STAND
        this.dataWatcher.addObject(IdBase+6, new Float(20F));//Hunger
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
	@SideOnly(Side.CLIENT)
    protected void playStepSound(int x, int y, int z, int id)
    {
        this.playSound("mob.wolf.step", 0.15F, 1.0F);
        
        if(getArmourSpeedBuff() < 0)
        {
        	playSound("mob.irongolem.throw", Math.min(2.5F, -getArmourSpeedBuff()), 1.0F);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("Domestic", trust);
        nbt.setBoolean("Angry", this.isAngry());
        nbt.setByte("CollarColour", (byte)this.getCollarColour());
        nbt.setInteger("breed", getBreedInt());
        nbt.setString("Command", getCommand());
        nbt.setInteger("NoSit", timeUntilSit);
        nbt.setInteger("LastEat", lastEaten);
        nbt.setInteger("InvertSpots", invertSpots);
        nbt.setInteger("houndID", ID);
        
        nbt.setDouble("StayX", stayPos[0]);
        nbt.setDouble("StayY", stayPos[1]);
        nbt.setDouble("StayZ", stayPos[2]);
        
        nbt.setDouble("HomeX", homePos[0]);
        nbt.setDouble("HomeY", homePos[1]);
        nbt.setDouble("HomeZ", homePos[2]);
        
        nbt.setBoolean("Attack Mobs", attackMob);
        nbt.setBoolean("Attack Animals", attackAnimal);
        nbt.setBoolean("Attack Players", attackPlayer);
        nbt.setBoolean("Defend Player", attackDefense);
        nbt.setBoolean("Enable PvP", fightPvp);
        
        nbt.setBoolean("LeapAttack", leapAttack);
        nbt.setBoolean("ItemPickup", pickupItems);
        nbt.setBoolean("Boost", boostStep);
        
        nbt.setInteger("Strength", strength);
        nbt.setInteger("Stamina", stamina);
        nbt.setInteger("Endurance", endurance);
        
        nbt.setFloat("Hunger", getHunger());
        
        nbt.setInteger("XP", xp);
        nbt.setInteger("XPMax", xpMax);
        nbt.setInteger("Level", level);
        nbt.setInteger("AttPoints", AtPoints);
        
        
        
        
        NBTTagList list = new NBTTagList();
		for (int c = 0; c < this.armour.length; ++c)
        {
            if (this.armour[c] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)c);
                this.armour[c].writeToNBT(var4);
                list.appendTag(var4);
            }
        }
		nbt.setTag("Items", list);
        
		
		NBTTagList list2 = new NBTTagList();
		for (int c = 0; c < pack.getSizeInventory(); ++c)
        {
            if (pack.getStackInSlot(c) != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)c);
                pack.getStackInSlot(c).writeToNBT(var4);
                list2.appendTag(var4);
            }
        }
		nbt.setTag("Pack", list2);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        trust = nbt.getFloat("Domestic");
        this.setAngry(nbt.getBoolean("Angry"));

        if(nbt.hasKey("houndID"))
        	ID = nbt.getInteger("houndID");
        
        timeUntilSit = nbt.getInteger("NoSit");
        lastEaten = nbt.getInteger("LastEat");
        invertSpots = nbt.getInteger("InvertSpots");
        
        stayPos[0] = nbt.getDouble("StayX");
        stayPos[1] = nbt.getDouble("StayY");
        stayPos[2] = nbt.getDouble("StayZ");
        
        homePos[0] = nbt.getDouble("HomeX");
        homePos[1] = nbt.getDouble("HomeY");
        homePos[2] = nbt.getDouble("HomeZ");
        
        if (nbt.hasKey("CollarColour"))
        {
            this.setCollarColour(nbt.getByte("CollarColour"));
        }
        setBreedInt(nbt.getInteger("breed"));
		if (getBreedInt() <= -1) {
			setRandomBreed();
		}
		setCommand(nbt.getString("Command"));
		
		attackMob = nbt.getBoolean("Attack Mobs");
		attackAnimal = nbt.getBoolean("Attack Animals");
		attackPlayer = nbt.getBoolean("Attack Players");
		attackDefense = nbt.getBoolean("Defend Player");
		fightPvp = nbt.getBoolean("Enable PvP");
		
		leapAttack = nbt.getBoolean("LeapAttack");
		pickupItems = nbt.getBoolean("ItemPickup");
		boostStep = nbt.getBoolean("Boost");
		
		strength = nbt.getInteger("Strength");
		stamina = nbt.getInteger("Stamina");
		endurance = nbt.getInteger("Endurance");
		
		setHunger(nbt.getFloat("Hunger"));
		
		xp = nbt.getInteger("XP");
		xpMax = nbt.getInteger("XPMax");
		level = nbt.getInteger("Level");
		AtPoints = nbt.getInteger("AttPoints");
		
		
		NBTTagList var2 = nbt.getTagList("Items");
        this.armour = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.armour.length)
            {
                this.armour[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        
        
        
        NBTTagList var6 = nbt.getTagList("Pack");

        for (int var3 = 0; var3 < var6.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var6.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < pack.getSizeInventory())
            {
                pack.setInventorySlotContents(var5, ItemStack.loadItemStackFromNBT(var4));
            }
        }
    }

	public int getAvailableRows()
	{
		if(armour[1] != null)
		{
			if(armour[1].getItem() != null)
			{
				if(armour[1].getItem() instanceof ItemHoundPack)
				{
					return ((ItemHoundPack)armour[1].getItem()).getAvalibleRows();
				}
			}
		}
		return 0;
	}
    
    @Override
    public void setDead()
    {
    	super.setDead();
		if(!worldObj.isRemote)
		{
			for(int a = 0; a < pack.getSizeInventory(); a ++)
			{
				if(pack.getStackInSlot(a) != null)
				{
					this.entityDropItem(pack.getStackInSlot(a), 1.0F);
					pack.setInventorySlotContents(a, null);
				}
			}
			for(int a = 0; a < armour.length; a ++)
			{
				if(armour[a] != null)
				{
					this.entityDropItem(armour[a], 1.0F);
					armour[a] = null;
				}
			}
		}
		super.setDead();
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
    	if(getHunger() < getLowHunger() || getHealth() < (isChild() ? 3 : 5))
    	{
    		return "mob.wolf.whine";
    	}
    	if(isAngry())
    	{
    		return "mob.wolf.growl";
    	}
    	if(rand.nextInt(3) == 0)
    	{
    		return "mob.wolf.panting";
    	}
        return "mob.wolf.bark";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.wolf.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.wolf.death";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
    protected void dropFewItems(boolean pKill, int looting)
    {
    	for(int a = 0; a < 1 + rand.nextInt(1 + looting); a ++)
    	{
    		this.entityDropItem(ItemListMF.component(ItemListMF.hideHound), 1.0F);
    	}
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if(!worldObj.isRemote)
        {
        	//BOOST
        	if(ticksExisted % 40 == 0)
        	{
        		stepHeight = shouldBoost() ? 1.0F : 0.5F;
        	}
        	float spd = this.getSpeedModifier();
        	motionX *= spd;
        	motionZ *= spd;
        	
        	/**
        	 * Healing
        	 */
        	if(canHeal() && ticksExisted % 100 == 0)
        	{
        		if(getHealth() < getMaxHealth())
        		{
        			heal(1);
        		}
        	}
        	
        	/**
        	 * Hungry
        	 */
        	if(getHunger() > 0 && ticksExisted % getHungerDecay() == 0 && cfg.hungryHound)
        	{
        		if(isTamed() && getOwner() != null && getHunger() > 0)
        		{
        			float exhaust = getExhaustion();
        			
        			if(saturationLevel >= exhaust)
        			{
        				saturationLevel -= exhaust;
        			}
        			else
        			{
        				if(saturationLevel > 0)
        				{
        					exhaust -= saturationLevel;
        				}
        				saturationLevel = 0;
        				setHunger(getHunger()-exhaust);
        			}
        		}
        	}
        	if(ticksExisted % 20 == 0)
        	{
        		if(getHunger() <= 0 && getHealth() > getStarveLethality())
        		{
        			this.attackEntityFrom(DamageSource.starve, 1);
        		}
        		if(getHunger() < 0)
        		{
        			setHunger(0);
        		}
        		
        		if(shouldPickupItems())
        		{
        			searchForItems(false);
        		}
        	}
        	
        	if(ticksExisted % 600 == 0)
        	{
        		if(getAttackTarget() != null)
        		{
        			if(!this.canEntityBeSeen(getAttackTarget()))
        			{
        				setAttackTarget(null);
        			}
        		}
        	}
        	if(isTamed() && getHunger() < (getMaxHunger()-1F))
    		{
    			tryToEat();
    		}
        	if(lastEaten > 0)lastEaten --;
        	
        	if(!isTamed() && ticksExisted % 200 == 0)
        	{
        		inPack = searchForPack();
        	}
        	
        	if(isAngry() && getAttackTarget() == null && rand.nextInt(200) == 0)
        	{
        		setAngry(false);
        	}
        	
        	if(!isTamed() && !inPack() && !isAngry() && ticksExisted % 20 == 0)
        	{
        		if(!cfg.easyTameHound && trust > 0F)
        		{
        			trust += getPlayerBonus();
        			trust += getVillaBonus();
        		}
        	}
        }
        updateAnimations();
        
        if (!this.worldObj.isRemote && this.isShaking && !this.field_70928_h && !this.hasPath() && this.onGround)
        {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.worldObj.setEntityState(this, (byte)8);
        }
        
        
        
        if (!this.dead && isTamed())
        {
            List list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(1.0D, 0.0D, 1.0D));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
            	this.pickupItem((EntityItem)iterator.next());
            }
        }
    }

    private float getStarveLethality() 
    {
		return cfg.starveHound ? 0 : (isChild() ? 1 : 5);
	}
    
	private float getExhaustion() 
    {
    	if(this.isPotionActive(Potion.field_76443_y.id))
    	{
    		return 0.0F;
    	}
    	if(getOwner() == null)
    	{
    		return 0.0F;
    	}
    	float exhaust = 0.1F;
		return exhaust;
	}
	private void updateAnimations() 
    {
    	if (jawMove > 0) {
			jawMove--;
		}
		if (eatAnimation > 0) {
			eatAnimation--;
		}
		if (chewTimer > 0) {
			chewTimer--;
		}
		
		if (jawMove <= 0 && chewTimer > 0) {
			jawMove = 3 + rand.nextInt(5);
			playSound("random.eat", getSoundVolume(), getSoundPitch());
		}
	}
    
    @Override
    public void playLivingSound()
    {
    	super.playLivingSound();
    	jawMove = 10;
    }
    public void addToHunger(float add)
    {
    	setHunger(getHunger() + add);
    }

	private void tryToEat() 
    {
    	if(this.remainingFeedbag() > 0)
		{
			if (getHunger() < getMaxHunger())
			{
				addToHunger(1);
				chew(20);
				eatAnimation = 20;
				worldObj.playAuxSFX(2001, (int)posX, (int)posY, (int)posZ,
						Block.dirt.blockID);
				this.eatBag(1);
			}
		}
		else
		{	
			if (ticksExisted % 20 == 0) 
			{
    			lookForFood();
    		}
			if(ticksExisted % 5 == 0 && isTamed())
			{
				eatFromBowl();
			}
		}
	}
    
    
    private void eatFromBowl() 
    {
    	int search = 2;
    	double range = 1.5D;
    	for (int x = (int) posX - search; x <= (int) posX + search; x++)
    	{
			for (int y = (int) posY - search; y <= (int) posY + search; y++) 
			{
				for (int z = (int) posZ - search; z <= (int) posZ + search; z++) 
				{
					if (this.getDistance(x+0.5D, y+0.5D, z+0.5D) < range && worldObj.getBlockId(x, y, z) == BlockListMF.dogbowl.blockID) 
					{
						getNavigator().clearPathEntity();
						TileEntityDogBowl tile = (TileEntityDogBowl) worldObj.getBlockTileEntity(x, y, z);
						if (tile != null && tile.food > 0) 
						{
							lastEaten = 20;
							addToHunger(1);
							if(this.saturationLevel < 10.0F)
							{
								saturationLevel = 10.0F;
							}
							tile.food--;
							chew(20);
							eatAnimation = 20;
							worldObj.playAuxSFX(2001, x, y, z,Block.dirt.blockID);
							facePoint(x, y, z);
							
							return;
						}
					}
				}
			}
		}
	}
    
    private void facePoint(double d0, double d1, double d2)
    {
    	double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d1 * d1);
        float f2 = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f3 = (float)(-(Math.atan2(d2, d3) * 180.0D / Math.PI));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f3, 10F);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f2, getVerticalFaceSpeed());
    }
    
    private float updateRotation(float x, float y, float angle)
    {
        float f3 = MathHelper.wrapAngleTo180_float(y - x);

        if (f3 > angle)
        {
            f3 = angle;
        }

        if (f3 < -angle)
        {
            f3 = -angle;
        }

        return x + f3;
    }

	private void lookForFood() 
    {
    	
		for (int x = (int) posX - 16; x < (int) posX + 16; x++) 
		{
			for (int y = (int) posY - 16; y < (int) posY + 16; y++) 
			{
				for (int z = (int) posZ - 16; z < (int) posZ + 16; z++) 
				{
					if (worldObj.getBlockId(x, y, z) == BlockListMF.dogbowl.blockID) 
					{
						TileEntityDogBowl tile = (TileEntityDogBowl) worldObj.getBlockTileEntity(x, y, z);
						if (tile != null && tile.food > 0) 
						{
							getNavigator().tryMoveToXYZ(x+0.5D, y+0.5D, z+0.5D, 1.0F);
							return;
						}
					}
				}
			}
		}
		
		searchForItems(true);
	}

	
	private void searchForItems(boolean foodOnly)
	{
		List list = worldObj.getEntitiesWithinAABB(EntityItem.class,AxisAlignedBB.getAABBPool().getAABB(posX, posY, posZ, posX + 1.0D,posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
		
		if (!list.isEmpty())
		{
			EntityItem target = (EntityItem) list.get(worldObj.rand.nextInt(list.size()));
			
			
			boolean pickup = false;
			
			//IF IS FOOD
			if(foodOnly)
			{
				if (target != null && target.getEntityItem().getItem() instanceof ItemFood)
				{
					ItemFood food = (ItemFood) (target.getEntityItem().getItem());
					if (food != null && willEatFood(food)) 
					{
						pickup = true;
					}
				}
			}
			else
			{
				pickup = true;
			}
			
			
			if (pickup) 
			{
				if (getNavigator().noPath()) 
				{
					this.setTarget(target);
					this.getNavigator().tryMoveToXYZ(target.posX, target.posY, target.posZ, 1.0F);
					return;
				}
			}
		}
	}

	private void chew(int i) 
	{
		jawMove = 5;
		chewTimer = i;
		playSound("random.eat", getSoundVolume(), getSoundPitch());
	}

	/**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        this.field_70924_f = this.field_70926_e;

        
        if (!worldObj.isRemote) 
        {
        	/**
        	 * This will let the Hound return to the last point commanded
        	 * If on stay, it will return after 2 blocks range, teleporting if it must
        	 * If idle: the hound will try walk back after 16
        	 */
        	if(getCommand().equalsIgnoreCase("Stay") && getNavigator().noPath() && getAttackTarget() == null)
        	{
        		if(lastEaten <= 0 && this.getDistance(stayPos[0], stayPos[1], stayPos[2]) > 2)
        		{
        			if(!getNavigator().tryMoveToXYZ(stayPos[0], stayPos[1], stayPos[2], 1.0F))
        			{
        				int i = MathHelper.floor_double(stayPos[0]) - 2;
                        int j = MathHelper.floor_double(stayPos[2]) - 2;
                        int k = MathHelper.floor_double(stayPos[1]);

                        for (int l = 0; l <= 4; ++l)
                        {
                            for (int i1 = 0; i1 <= 4; ++i1)
                            {
                                if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && worldObj.doesBlockHaveSolidTopSurface(i + l, k - 1, j + i1) && !worldObj.isBlockNormalCube(i + l, k, j + i1) && !worldObj.isBlockNormalCube(i + l, k + 1, j + i1))
                                {
                                    setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), rotationYaw, rotationPitch);
                                    this.getNavigator().clearPathEntity();
                                    return;
                                }
                            }
                        }
        			}
        		}
        	}
        	if(timeUntilSit > 0)
        	{
        		timeUntilSit --;
        	}
        	if(!getNavigator().noPath() && timeUntilSit < 10)
        	{
        		timeUntilSit = 10;
        	}
			
			updateCommands();
			if(xp >= xpMax)
			{
				levelup();
			}
			
			if(ticksExisted % 20 == 0)
			{
				updateArmours();
				this.sendStatPackets();
			}
			this.sendQuickPackets();
			
			boolean flag = getNavigator().noPath() && timeUntilSit <= 0;
			this.setStill(flag);
			
			int maxItems = this.getAvailableRows()*9;
			for(int a = maxItems; a < pack.getSizeInventory(); a ++)
			{
				if(pack.getStackInSlot(a) != null)
				{
					this.entityDropItem(pack.getStackInSlot(a), 1.0F);
					pack.setInventorySlotContents(a, null);
				}
			}
			
			if(isChild())
			{
				if(getHealth() > getMaxHealth())
				{
					setHealth(getMaxHealth());
				}
				
				if(getHunger() > getMaxHunger())
				{
					setHunger(getMaxHunger());
				}
				
				if(this.getGrowingAge() == -1)
				{
					this.setGrowingAge(0);
					setHunger(getMaxHunger());
					refreshHealth();
				}
			}
			if(invertSpots < 0)
			{
				invertSpots = rand.nextBoolean() ? 1 : 0;
			}
			if(ticksExisted % 20 == 0)
			{
				float maxHealth = this.getMaxHealth();
				float properHealth = this.getMaxHealth(endurance);
				
				if(maxHealth != properHealth)
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(properHealth);
				}
			}
		} 
        
        if (this.isInterested())
        {
            this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
        }
        else
        {
            this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
        }

        if (this.isInterested())
        {
            this.numTicksToChaseTarget = 10;
        }

        if (this.isWet())
        {
            this.isShaking = true;
            this.field_70928_h = false;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else if ((this.isShaking || this.field_70928_h) && this.field_70928_h)
        {
            if (this.timeWolfIsShaking == 0.0F)
            {
                this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05F;

            if (this.prevTimeWolfIsShaking >= 2.0F)
            {
                this.isShaking = false;
                this.field_70928_h = false;
                this.prevTimeWolfIsShaking = 0.0F;
                this.timeWolfIsShaking = 0.0F;
            }

            if (this.timeWolfIsShaking > 0.4F)
            {
                float f = (float)this.boundingBox.minY;
                int i = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);

                for (int j = 0; j < i; ++j)
                {
                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.worldObj.spawnParticle("splash", this.posX + (double)f1, (double)(f + 0.8F), this.posZ + (double)f2, this.motionX, this.motionY, this.motionZ);
                }
            }
        }
    }

	@SideOnly(Side.CLIENT)
    public boolean getWolfShaking()
    {
        return this.isShaking;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Used when calculating the amount of shading to apply while the wolf is shaking.
     */
    public float getShadingWhileShaking(float shade)
    {
        return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * shade) / 2.0F * 0.25F;
    }

    @SideOnly(Side.CLIENT)
    public float getShakeAngle(float f, float f1)
    {
        float f2 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * f + f1) / 1.8F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }
        else if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        return MathHelper.sin(f2 * (float)Math.PI) * MathHelper.sin(f2 * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    @SideOnly(Side.CLIENT)
    public float getInterestedAngle(float angle)
    {
        return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * angle) * 0.15F * (float)Math.PI;
    }

    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float damage)
    {
    	if(source == DamageSource.inWall)
    	{
    		return false;
    	}
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
        	damage = applyResistance(source, damage);
        	
        	if(source.isFireDamage())
    		{
    			if(isSuper() || (cfg.houndNoFire && getHealth() <= 1))
    			{
    				return false;
    			}
    		}
        	
        	if(damage <= 0)
        	{
        		return false;
        	}
        	timeUntilSit = 100;
            Entity entity = source.getEntity();
            this.aiSit.setSitting(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
            {
                damage = (damage + 1) / 2;
            }
            if (entity != null && entity instanceof EntityPlayer && !isTamed())
            {
            	setAngry(true);
            }

            return super.attackEntityFrom(source, damage);
        }
    }
	private float applyResistance(DamageSource source, float damage) 
    {
		float resist = getEquippedArmour(source);
		
		damage -= (damage*resist);
		return (float)Math.max(damage, 0);
	}

	private boolean isSuper()
	{
		if(this.getBreed() != null)
		{
			return getBreed().tierOfBreed == 3;
		}
		return false;
	}
	@Override
    public boolean attackEntityAsMob(Entity enemy)
    {
    	timeUntilSit = 100;
    	jawMove = 10;
        float i = getBiteDamage(enemy);
        
        if(armour[0] != null)
		{
			if(armour[0].getItem() instanceof ItemHoundWeapon)
			{
				if(enemy instanceof EntityLiving)
				((ItemHoundWeapon)armour[0].getItem()).hitEntity(armour[0], (EntityLiving)enemy, this);
			}
			if (enemy instanceof EntityLiving)
            {
                i += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLiving)enemy);
            }
		}
        if(enemy.attackEntityFrom(DamageSource.causeMobDamage(this), i))
        {
	        xp += (int)i;
	        return true;
        }
        if (enemy instanceof EntityLiving)
        {
            EnchantmentThorns.func_92096_a(this, (EntityLiving)enemy, this.rand);
            int l = EnchantmentHelper.getFireAspectModifier(this);

            if (l > 0 && !enemy.isBurning())
            {
                enemy.setFire(l);
            }
        }
        
        return false;
    }
    
    @Override
	public int getTotalArmorValue()
    {
		return 0;
    }
    
    private float getEquippedArmour(DamageSource source) 
    {
    	float base = 0;
        for (ItemStack slot : armour)
        {
            if (slot != null && slot.getItem() instanceof IHoundArmour)
            {
            	base += ((IHoundArmour)slot.getItem()).getResistance(source);
            }
        }
        return (float)Math.min(0.999F, base);
	}
    
	public float getACDisplayPercent() 
	{
    	float base = 0;
        for (ItemStack slot : armour)
        {
            if (slot != null && slot.getItem() instanceof IHoundArmour)
            {
            	base += ((IHoundArmour)slot.getItem()).getACDisplayPercent();
            }
        }
        return (float)Math.min(0.99F, base);
	}
    
    @Override
    protected float applyArmorCalculations(DamageSource source, float damage)
    {
        if (!source.isUnblockable())
        {
            int i = 25 - this.getTotalArmorValue();
            float f1 = damage * (float)i;
            this.damageArmour(source, damage);
            damage = f1 / 25.0F;
        }

        return damage;
    }
    
	protected void damageArmour(DamageSource source, float damage)
    {
        damage /= 2;

        if (damage < 1)
        {
            damage = 1;
        }

        for (int var2 = 0; var2 < this.armour.length; ++var2)
        {
            if (this.armour[var2] != null && this.armour[var2].getItem().isDamageable())
            {
            	if(shouldDamage(armour[var2], source))
            	{
            		this.armour[var2].damageItem((int)damage, this);
	
	                if (this.armour[var2].stackSize <= 0)
	                {
	                    this.armour[var2] = null;
	                    updateArmours();
	                }
            	}
            }
        }
    }
    
    private boolean shouldDamage(ItemStack stack, DamageSource source)
    {
    	if(stack == null)
    	{
    		return false;
    	}
    	
    	if(stack.getItem() instanceof IHoundArmour)
    	{
    		return ((IHoundArmour)stack.getItem()).shouldDamage(source);
    	}
    	
		return true;
	}
	@Override
    public void onKillEntity(EntityLivingBase enemy)
    {
    	//this.getAIMoveSpeed()
    	int reward = 0;
    	if(enemy instanceof EntityLiving)
    	{
    		reward = (int)((EntityLiving)enemy).experienceValue;
    	}
    	if(enemy instanceof EntityPlayer)
    	{
    		reward = 100;
    	}
    	xp += reward;
    }

	/**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer player)
    {
        ItemStack itemstack = player.inventory.getCurrentItem();

        if (this.isTamed())
        {
            if (itemstack != null)
            {
            	if(player.isSneaking() && itemstack.getItem() instanceof ItemBandage)
            	{
            		return false;
            	}
            	if(MineFantasyBase.isDebug())
            	{
	            	if(applyDebugItems(player, itemstack)) return true;
            	}
                if (Item.itemsList[itemstack.itemID] instanceof ItemFood)
                {
                    ItemFood itemfood = (ItemFood)Item.itemsList[itemstack.itemID];

                    if (willEatFood(itemfood))
                    {
                    	boolean consume = false;
                    	if(canEatFood(itemfood))
                    	{
                    		consume = true;
                    		eatFood(itemfood);
                    	}
                    	else
                    	{
                    		if(this.fillBag(itemfood.getHealAmount()))
                    		{
                    			consume = true;
                    		}
                    	}
                        if (consume && !player.capabilities.isCreativeMode)
                        {
                            --itemstack.stackSize;
                        }

                        if (itemstack.stackSize <= 0)
                        {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                        }

                        if(consume)
                        return true;
                    }
                }
                else if (itemstack.itemID == Item.dyePowder.itemID)
                {
                    int i = BlockColored.getBlockFromDye(itemstack.getItemDamage());

                    if (i != this.getCollarColour())
                    {
                        this.setCollarColour(i);

                        if (!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
                        {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                        }

                        return true;
                    }
                }
                else if(itemstack.getItem() instanceof ItemPetChange)
                {
                	if(onUseTrade(player, itemstack))
                	{
                		return true;
                	}
                }
            }
            if (!this.worldObj.isRemote && !this.isBreedingItem(itemstack))
            {
            	player.openGui(MineFantasyBase.instance, 2, worldObj, entityId,
						0, 0);
				return true;
            }
        }
        else if (tryToTame(player, itemstack))
        {
        	this.setHunger(this.getMaxHunger());
            return true;
        }

        return super.interact(player);
    }
    
    /**
     * Tries to tame the hound. 
     * @param player the taming player
     * @param itemstack the item being used
     */
    private boolean tryToTame(EntityPlayer player, ItemStack itemstack)
    {
    	if(itemstack != null && itemstack.itemID == Item.bone.itemID && (!inPack() && canBeTamed(player) || player.capabilities.isCreativeMode))
    	{
    		if(player.capabilities.isCreativeMode)
    		{
    			tame(player, true);
    			return true;
    		}
    		if(!cfg.easyTameHound && trust > 25F && trust < 100F)
    		{
    			return false;
    		}
    		if(isAngry())
    		{
    			playSound("mob.wolf.bark", 1.2F, 1.0F);
    			player.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
    			return true;
    		}
    		chew(20);
    		
    		//Start
    		if(!cfg.easyTameHound && trust < 25F)
    		{
    			trust += rand.nextFloat()*5F;
    		}
    		if (!player.capabilities.isCreativeMode)
	        {
	            --itemstack.stackSize;
	        }
    		if (itemstack.stackSize <= 0)
	        {
	            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	        }
    		
    		//Tame
    		if((cfg.easyTameHound || trust >= 100F) && rand.nextInt(5) == 0)
    		{
		        tame(player, true);
		        return true;
    		}
    		else
    		{
    			tame(player, false);
    		}
    	}
        return false;
	}


    /**
     * Called when taming has succeeded
     * @param player the player taming the creature
     */
    private void tame(EntityPlayer player, boolean success)
    {
    	if (this.worldObj.isRemote)
        {
    		return;
        }
        if (success)
        {
            this.setTamed(true); //Dog Will Tame
            this.setPathToEntity((PathEntity)null); //Dog Will Stop Moving
            this.setAttackTarget((EntityLiving)null); //Dog Will Cease Combat
            this.setOwner(player.username); //Sets The Owner
            this.playTameEffect(true); //Create Hearts
            this.worldObj.setEntityState(this, (byte)7); //Set Tame
            command("Follow");
            
            refreshHealth();
        }
        else
        {
            this.playTameEffect(false); //Fail look
            this.worldObj.setEntityState(this, (byte)6); //Set Fail
        }
	}

    private void refreshHealth()
    {
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(getMaxHealth(endurance));
        this.setHealth(getMaxHealth());
    }
	/**
     * Determines if a dog can be tamed
     * @param player the player trying to tame
     * @return if it is possible(False if the hound is in combat)
     */
    private boolean canBeTamed(EntityPlayer player) 
    {
		return getAttackTarget() == null;
	}

	public int remainingFeedbag()
	{
		if(armour[0] != null)
		{
			if(armour[0].getItem() instanceof ItemHoundFeedbag)
			{
				ItemHoundFeedbag bag = (ItemHoundFeedbag)armour[0].getItem();
				return bag.getMaxDamage() - armour[0].getItemDamage();
			}
		}
		return 0;
	}
    
    public boolean fillBag(int food)
	{
		if(armour[0] != null)
		{
			if(armour[0].getItem() instanceof ItemHoundFeedbag)
			{
				int f = armour[0].getItemDamage();
				
				if(f <= 1)return false;
				
				if(!worldObj.isRemote)
				{
					armour[0].setItemDamage(f-food);
					if(armour[0].getItemDamage() < 1)armour[0].setItemDamage(1);
				}
				return true;
			}
		}
		return false;
	}
    
    public boolean eatBag(int food)
	{
		if(armour[0] != null)
		{
			if(armour[0].getItem() instanceof ItemHoundFeedbag)
			{
				int f = armour[0].getItemDamage();
				
				if(f >= armour[0].getMaxDamage())return false;
				
				if(!worldObj.isRemote)
				{
					armour[0].setItemDamage(f+food);
					if(armour[0].getItemDamage() < 1)armour[0].setItemDamage(1);
				}
				return true;
			}
		}
		return false;
	}
    
    
    private boolean canEatFood(ItemFood itemfood) 
    {
		return getHunger() < getMaxHunger();
	}

	@Override
	public boolean canAttackClass(Class entity)
	{
		if(!cfg.houndKillMan && entity == EntityVillager.class)
			return false;
		
		return true;
	}
    
    /**
     * Applies a command from the player
     * @param string the command sent("Idle", "Stay", "Follow")
     */
    public void command(String string) 
    {
    	this.detachHome();
		setCommand(string);
		if(string.equalsIgnoreCase("Stay"))
		{
			setStayPos();
		}
		if(string.equalsIgnoreCase("Idle"))
		{
			setHome();
		}
	}
    
    private void setHome()
    {
    	setHomeArea((int)posX, (int)posY, (int)posZ, 15);
    }

	@SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 8)
        {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    @SideOnly(Side.CLIENT)
    public float getTailRotation()
    {
    	float tail = (float)Math.toRadians(90);
    	float hp = (20F / getMaxHealth()) * getHealth();
    	
    	if(cfg.easyTameHound)
    	{
    		tail = (float)Math.PI / 5F;
    	}
    	else if(trust > 0F)
    	{
    		tail = (tail / 100F) * trust;
    	}
    	
        return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.55F - (float)(20 - hp) * 0.02F) * (float)Math.PI : (tail));
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack item)
    {
        return item == null ? false : (!(Item.itemsList[item.itemID] instanceof ItemFood) ? false : ((ItemFood)Item.itemsList[item.itemID]).isWolfsFavoriteMeat());
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    public boolean isAngry()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    /**
     * Sets whether this wolf is angry or not.
     */
    public void setAngry(boolean flag)
    {
    	if(isTamed() && flag)return;
    	
    	
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (flag)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 2)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -3)));
        }
    }

    /**
     * Return this wolf's collar color.
     */
    public int getCollarColour()
    {
        return this.dataWatcher.getWatchableObjectByte(20) & 15;
    }

    /**
     * Set this wolf's collar color.
     */
    public void setCollarColour(int par1)
    {
        this.dataWatcher.updateObject(20, Byte.valueOf((byte)(par1 & 15)));
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityHound spawnBabyAnimal(EntityAgeable parent)
    {
    	//TODO: Breeding
    	EntityHound hound2 = null;
    	if(parent instanceof EntityHound)
    	{
    		hound2 = (EntityHound)parent;
    	}
    	
    	
        EntityHound dog = new EntityHound(this.worldObj);
        
        String s = this.getOwnerName();

        if (s != null && s.trim().length() > 0)
        {
            dog.setOwner(s);
            dog.setTamed(true);
        }

        if(hound2 != null)
        {
        	if(getBreed() != null && hound2.getBreed() != null)
        	{
        		dog.setBreedInt(getBreedFor(getBreedInt(), hound2.getBreedInt()));
        	}
        }

        dog.command("Stay");
        return dog;
    }

    private int getBreedFor(int breed, int breed2) 
    {
    	EnumHoundBreed special = EnumHoundBreed.getBreedFor(breed, breed2);
    	
    	if(special != null)
    	{
    		double chance = getChanceFor(special.chanceToCreate, cfg.HoundBreed);
    		
    		if(MineFantasyBase.isDebug())
    		{
    			System.out.println("Chance: "+chance + " (" + (chance*100F) + ")% cfg= " + cfg.HoundBreed);
    		}
    		if(MineFantasyBase.isDebug() || rand.nextDouble() <= chance)
    		{
    			return special.ordinal();
    		}
    	}
    	
		return (rand.nextBoolean() ? breed : breed2);
	}

	private double getChanceFor(double chance, int a) 
	{
		double multiplier = 1.0F / ((float)a);
		return chance*multiplier;
	}
	public void func_70918_i(boolean flag)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(IdBase+1);

        if (flag)
        {
            this.dataWatcher.updateObject(IdBase+1, Byte.valueOf((byte)1));
        }
        else
        {
            this.dataWatcher.updateObject(IdBase+1, Byte.valueOf((byte)0));
        }
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal animal)
    {
        if (animal == this)
        {
            return false;
        }
        else if (!this.isTamed())
        {
            return false;
        }
        else if (!(animal instanceof EntityHound))
        {
            return false;
        }
        else
        {
            EntityHound entitywolf = (EntityHound)animal;
            return !entitywolf.isTamed() ? false : (entitywolf.isSitting() ? false : this.isInLove() && entitywolf.isInLove());
        }
    }
    
    private void setStats() 
    {
    	if (getBreedInt() <= -1)
    	{
			setRandomBreed();
		}
	}

    public boolean isInterested()
    {
        return this.dataWatcher.getWatchableObjectByte(IdBase+1) == 1 && !inPack();
    }

    public EntityAgeable createChild(EntityAgeable parent2)
    {
        return this.spawnBabyAnimal(parent2);
    }

	public ItemStack getStackInSlot(int i) 
	{
		return armour[i];
	}

	public boolean isAlpha() 
	{
		return false;
	}

	public String getName()
	{
		return getEntityName();
	}

	public String getTexture() 
	{
		if(getBreed() != null)
		{
			if(invertSpots == 1)
			{
				if(getSpots() != null)
				{
					return data_minefantasy.image("/mob/Hound/hound" + getSpots() + ".png");
				}
			}
		}
		return data_minefantasy.image("/mob/Hound/hound" + getFurTex() + ".png");
	}
	
	public String getFurTex() {
		if(getBreed() != null)
		{
			return getBreed().texture;
		}
		return "Brown";
	}

	public boolean isFollowing() 
	{
		return getCommand().equalsIgnoreCase("Follow");
	}
	
	public String getCommand()
	{
		String str = dataWatcher.getWatchableObjectString(IdBase+4);
		if(str == null)
		{
			str = "";
		}
		return str;
	}
	public void setCommand(String s)
	{
		dataWatcher.updateObject(IdBase+4, s);
	}
	
	@Override
	public boolean isSitting()
    {
		if(!isStill())
		{
			return false;
		}
		if(getCommand().equalsIgnoreCase("Stay"))
		{
			return true;
		}
		return false;
    }
	
	private boolean isStill() 
	{
		return dataWatcher.getWatchableObjectByte(IdBase+5) == 1;
	}
	private void setStill(boolean flag) 
	{
		byte b = (byte)(flag ? 1 : 0);
		dataWatcher.updateObject(IdBase+5, b);
	}

	private void updateCommands()
	{
		if(getCommand().length() == 0)
		{
			setCommand("Idle");
		}
		if(getCommand().equalsIgnoreCase("Follow"))
		{
			if(getOwner() == null)
			{
				/*
				if(false && this.hasUnlockedTeleport())//TODO: Later on; add some sort of teleport when offline option
				{
					teleport(homePos);
					stayPos[0] = homePos[0];
					stayPos[1] = homePos[1];
					stayPos[2] = homePos[2];
					setCommand("Stay");
				}
				else
				*/
				{
					setStayPos();
					setCommand("Stay");
				}
			}
		}
	}

	public boolean shouldWander() 
	{
		if(isFollowing() || isSitting())
		{
			return false;
		}
		return true;
	}

	@Override
	public void recievePacket(ByteArrayDataInput data)
	{
		try
		{
		int id = data.readInt();
		
		
		if(id == 0)//STATS
		{
			strength = data.readInt();
			stamina = data.readInt();
			endurance = data.readInt();
			
			xp = data.readInt();
			xpMax = data.readInt();
			level = data.readInt();
			AtPoints = data.readInt();
		}
		
		
		
		if(id == 1)//BOOLEANS
		{
			attackMob = data.readInt() == 1;
			attackAnimal = data.readInt() == 1;
			attackPlayer = data.readInt() == 1;
			attackDefense = data.readInt() == 1;
			fightPvp = data.readInt() == 1;
			
			leapAttack = data.readInt() == 1;
			pickupItems = data.readInt() == 1;
			boostStep = data.readInt() == 1;
			
			inPack = data.readInt() == 1;
			trust = data.readInt();
			invertSpots = data.readInt();
		}
		if(id == 2)//COMMAND
		{
			int i = data.readInt();
			if(i < commandList.length)
			{
				command(commandList[i]);
			}
		}
		if(id == 3)//TOGGLE
		{
			int p = data.readInt();
			activatePower(p);
		}
		if(id == 4)//Level
		{
			int i = data.readInt();
			if(i == 0)//STR
			{
				if(strength < 100)
					this.strength = tryLevelSkill(strength);
			}
			if(i == 1)//STA
			{
				if(stamina < 100)
					this.stamina = tryLevelSkill(stamina);
			}
			if(i == 2)//END
			{
				if(endurance < 100)
				{
					float max = getMaxHealthFor();
					this.endurance = tryLevelSkill(endurance);
					syncAttributes();
					heal(getMaxHealth() - max);
				}
			}
		}
		if(id == 5)
		{
			int a1 = data.readInt();
			int s1 = data.readInt();
			int d1 = data.readInt();
			
			int a2 = data.readInt();
			int s2 = data.readInt();
			int d2 = data.readInt();
			if(a1 > 0)
			{
					armour[0] = new ItemStack(a1, s1, d1);
			}
			
			if(a2 > 0)
			{
					armour[1] = new ItemStack(a2, s2, d2);
			}
		}
		}catch(Exception e)
		{
			System.err.println("MineFantasy: Issues found in hound packet data");
		}
	}
	
	/**
	 * Activates from the power bar
	 * @param p the powerbar slot
	 */
	private void activatePower(int p) 
	{
		switch(p)
		{
		case 0:
			attackMob = !attackMob;
			break;
		case 1:
			attackAnimal = !attackAnimal;
			break;
		case 2:
			attackPlayer = !attackPlayer;
			if(attackPlayer)
			{
				fightPvp = true;
			}
			break;
		case 3:
			attackDefense = !attackDefense;
			break;
		case 4:
			fightPvp = !fightPvp;
			break;
			
			
		case 5://LEAP
			leapAttack = !leapAttack;
			break;
		case 6://PICKUP
			pickupItems = !pickupItems;
			break;
		case 7://BOOST
			boostStep = !boostStep;
			stepHeight = boostStep ? 1.0F : 0.5F;
			break;
		case 8://TELEPORT
			if(hasUnlockedTeleport())
			{
				teleport(homePos);
				stayPos[0] = homePos[0];
				stayPos[1] = homePos[1];
				stayPos[2] = homePos[2];
				setCommand("Stay");
			}
			break;
		case 9://SETPOS
			if(hasUnlockedTeleport())
			{
				setHome(posX, posY, posZ);
				trySendPlayerMessage("Your Hound " + getNameString() + "Has set home: "
				+ posX + "x "+ posY + "y "+ posZ + "z ");
			}
			break;
		case 10: //Disengage
		{
			if(getAttackTarget() != null)
			{
				this.setAttackTarget(null);
				getNavigator().clearPathEntity();
			}
		}
		}
	}
	
	private void trySendPlayerMessage(String message)
	{
		if(!worldObj.isRemote)
		if(getOwner() != null && getOwner() instanceof EntityPlayer)
		{
			((EntityPlayer)getOwner()).addChatMessage(message);
		}
	}
	private void setHome(double x, double y, double z)
	{
		homePos[0] = x;
		homePos[1] = y;
		homePos[2] = z;
	}

	private void sendQuickPackets()
	{
		try {
			Packet packet = PacketManagerMF.getEntityPacketIntegerArray(this, new int[]{1, attackMob ? 1 : 0, attackAnimal ? 1 : 0, attackPlayer ? 1 : 0, attackDefense ? 1 : 0  ,fightPvp ? 1 : 0  , leapAttack ? 1 : 0 , pickupItems ? 1 : 0 , boostStep ? 1 : 0, inPack ? 1 : 0, (int)trust, invertSpots});
			FMLCommonHandler.instance().getMinecraftServerInstance()
					.getConfigurationManager()
					.sendPacketToAllPlayers(packet);
		} catch (NullPointerException e) {
			;
		}
	}
	
	private void sendStatPackets()
	{
		try {
			Packet packet = PacketManagerMF.getEntityPacketIntegerArray(this, new int[]{0, strength, stamina, endurance, xp, xpMax, level, AtPoints});
			FMLCommonHandler.instance().getMinecraftServerInstance()
					.getConfigurationManager()
					.sendPacketToAllPlayers(packet);
		} catch (NullPointerException e) {
			;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void commandFromClient(int id)
	{
		try {
			Packet packet = PacketManagerMF.getEntityPacketIntegerArray(this, new int[]{2, id});
			PacketDispatcher.sendPacketToServer(packet);
			FMLCommonHandler.instance().getMinecraftServerInstance()
					.getConfigurationManager()
					.sendPacketToAllPlayers(packet);
		} catch (NullPointerException e) {
			;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void levelFromClient(int id)
	{
		try {
			Packet packet = PacketManagerMF.getEntityPacketIntegerArray(this, new int[]{4, id});
			PacketDispatcher.sendPacketToServer(packet);
			FMLCommonHandler.instance().getMinecraftServerInstance()
					.getConfigurationManager()
					.sendPacketToAllPlayers(packet);
		} catch (NullPointerException e) {
			;
		}
	}
	
	private void setStayPos()
	{
		stayPos[0] = posX;
		stayPos[1] = posY;
		stayPos[2] = posZ;
	}
	private String[] commandList = new String[]{"Idle", "Stay", "Follow"};
	private float levelupMulti = 1.05F;
	private int maxLevel = 100;

	private boolean inPack;

	
	private int tryLevelSkill(int skill)
	{
		if(skill < 100 && AtPoints > 0)
		{
			AtPoints --;
			skill ++;
		}
		return skill;
	}

	@Override
	public int getSizeInventory() 
	{
		return armour.length;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (armour[i] != null) 
		{
			if (armour[i].stackSize <= j) 
			{
				ItemStack itemstack = armour[i];
				armour[i] = null;
				return itemstack;
			}
			
			ItemStack itemstack1 = armour[i].splitStack(j);
			
			if (armour[i].stackSize == 0) 
			{
				armour[i] = null;
			}
			return itemstack1;
		}
		else 
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) 
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		armour[i] = itemstack;
	}

	@Override
	public String getInvName()
	{
		return getEntityName();
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return this.getDistanceToEntity(var1) < 16 && !isDead;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void usePower(int id)
	{
		try {
			Packet packet = PacketManagerMF.getEntityPacketIntegerArray(this, new int[]{3, id});
			PacketDispatcher.sendPacketToServer(packet);
			FMLCommonHandler.instance().getMinecraftServerInstance()
					.getConfigurationManager()
					.sendPacketToAllPlayers(packet);
		} catch (NullPointerException e) {
			;
		}
	}
	
	
	public float getMaxHealthFor()
    {
		return isChild() ? 5 : getMaxHealth(endurance);
    }
	
	public float getMaxHealth(int lvl)
    {
		if(isChild())return 5;
		
		float hp = getHealthOnBreed();
    	
    	if(isTamed())
    	{
    		double buff = 1.0D / 100D * (double)lvl;
    		
    		hp += (float)((double)hp * buff);
    	}
    	return hp;
    }
	public float getHealthOnBreed()
    {
		float bonus = 1;
		if(getBreed() != null)
		{
			bonus = getBreed().defense;
		}
		return 10 * bonus;
	}
    
    public float getBiteDamage(Entity tar) 
    {
    	float damage = getBaseDamage(strength);
    	
    	if(armour[0] != null)
		{
			if(armour[0].getItem() instanceof IHoundWeapon)
			{
				damage += ((IHoundWeapon)armour[0].getItem()).getDamage(tar);
			}
		}
		
		if (this.isPotionActive(Potion.damageBoost))
        {
            damage += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if (this.isPotionActive(Potion.weakness))
        {
            damage -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }
        
        
		return damage;
	}
    public float getBaseDamage(int level) 
    {
    	float damage = 1;
		if(getBreed() != null)
		{
			damage += (getBreed().attack/2);
		}
		
    	float str = getDamageMod(level);
    	return damage * str;
	}

	public float getDamageMod(int level) 
    {
		return 1.0F + (1.0F / 100F * (float)level);
	}

	
	public int getHungerDecay() 
    {
		int d = getHungerDecay(stamina);
		
		if(shouldBoost())
		{
			d = (int)((double)d * 0.9F);
		}
		
		if(isChild())
		{
			d *= 4;
		}
		return d;
    }
	/**
     * Gets the time span between hunger loss
     * @return every 30 seconds(level 1). special breeds lose every 90 seconds
     * 5x longer at lvl 100
     * 
     * Total time:
     * Lvl1: 10mins (30mins special)
     * lvl50: 30mins (90mins special)
     * lvl100: 1hour (3hours special)
     */
    public int getHungerDecay(int lvl) 
    {
    	int decay = getDecayOnBreed() / 10; // 3 Seconds
    	
    	double bonus = 5.0D / 100D * (double)lvl;
    	decay += (int)((double)decay * bonus);
    	
		return decay;
	}
    public int getDecayOnBreed()
    {
    	int bonus = 1;
		if(getBreed() != null)
		{
			bonus = getBreed().stamina;
		}
		return 1200 + (300 * bonus);
	}

    /**
     * Hounds can't heal below 85% hunger
     */
	private boolean canHeal()
    {
    	return getHunger() >= (getMaxHunger()*0.85F);
    }
    
    
    
	
	private void levelup()
	{
		levelup(true);
	}
	private void levelup(boolean legit)
	{
		boolean leap = hasUnlockedLeap();
		boolean pickup = hasUnlockedPickup();
		boolean boost = hasUnlockedBoost();
		boolean teleport = hasUnlockedTeleport();
		
		
		if(legit)
		{
			xp -= xpMax;
			worldObj.playSoundAtEntity(this, "random.levelup", 1.0F, 1.0F);
		}
		experienceValue *= this.levelupMulti;
		level ++;
		xpMax *= levelupMulti;
		
		AtPoints += 2;
		if(level % 5 == 0)
		{
			AtPoints += 3;
		}
		
		if(strength >= 100 && stamina >= 100 && endurance >= 100)
		{
			AtPoints = 0;
		}
		
		
		if(leap != hasUnlockedLeap())
		{
			trySendPlayerMessage("Your Hound " + getNameString() + "Has Learned 'Leap Attack'");
		}
		if(pickup != hasUnlockedPickup())
		{
			trySendPlayerMessage("Your Hound " + getNameString() + "Has Learned 'Collect Items'");
		}
		if(boost != hasUnlockedBoost())
		{
			trySendPlayerMessage("Your Hound " + getNameString() + "Has Learned 'Step Boost'");
		}
		if(teleport != hasUnlockedTeleport())
		{
			trySendPlayerMessage("Your Hound " + getNameString() + "Has Learned 'Teleport Home'");
		}
		syncAttributes();
	}

	private void syncAttributes()
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(this.getMaxHealthFor());
	}
	public float getMaxHunger()
	{
		return isChild() ? 5 : getMaxHungerOnBreed();
	}
	
	private float getMaxHungerOnBreed() 
	{
		float bonus = 1;
		if(getBreed() != null)
		{
			bonus = getBreed().stamina;
		}
		return (5 * bonus) + 5;
	}

	@SideOnly(Side.CLIENT)
	public float getDisplayHealth()
	{
		return getHealth();
	}

	@Override
	public int getEntityID()
	{
		return entityId;
	}

	@Override
	public void sendNewName(String name) 
	{
		setCustomNameTag(name);
	}
	
	public void setHunger(float h)
	{
		dataWatcher.updateObject(IdBase+6, h);
	}
	public float getHunger()
	{
		return dataWatcher.getWatchableObjectFloat(IdBase+6);
	}

	@Override
	public int getViewingArc() 
	{
		return 360;
	}

	@Override
	public int getHearing()
	{
		return -5;
	}

	@Override
	public int getSight()
	{
		return 10;
	}
	
	public void eatFood(ItemFood food)
	{
		chew(100);
		this.saturationLevel = food.getSaturationModifier()*food.getHealAmount();
		this.setHunger(Math.min(getMaxHunger(), getHunger() + food.getHealAmount()));
	}

	@Override
	public void onInventoryChanged() {}

	public boolean canEquip(IHoundEquipment equipment) 
	{
		/*
		if(strength < equipment.getRequiredStr() || stamina < equipment.getRequiredSta() || endurance < equipment.getRequiredEnd())
		{
			return false;
		}
		*/
		return true;
	}
	
	private void syncItems() 
    {
		if(!worldObj.isRemote)
		{
			for(int a = 0; a < armour.length; a ++)
			{
				Packet packet = PacketManagerMF.getPacketItemStackArray(this, a, armour[a]);
				try
				{
					FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
				} catch(NullPointerException e)
				{
					System.out.println("MineFantasy: Client connection lost");
					return;
				}
			}
		}
	}
	private void updateArmours() 
	{
		syncItems();
	}
	public void unused()
	{
		int a1 = -1;
		int a2 = -1;
		int s1 = 1;
		int s2 = 1;
		int d1 = 0;
		int d2 = 0;
		if(armour[0] != null)
		{
			a1 = armour[0].itemID;
			s1 = armour[0].stackSize;
			d1 = armour[0].getItemDamage();
		}
		if(armour[1] != null)
		{
			a2 = armour[1].itemID;
			s2 = armour[1].stackSize;
			d2 = armour[1].getItemDamage();
		}
		
		try {
			Packet packet = PacketManagerMF.getEntityPacketIntegerArray(this, new int[]{5, a1, s1, d1, a2, s2, d2});
			FMLCommonHandler.instance().getMinecraftServerInstance()
					.getConfigurationManager()
					.sendPacketToAllPlayers(packet);
		} catch (NullPointerException e) {
			;
		}
	}
	
	
	
	
	protected int applyPotionDamageCalculations(DamageSource source, int damage)
    {
		//TODO Potion Calc
        int j;
        int k;
        int l;

        if (this.isPotionActive(Potion.resistance))
        {
            damage /= 2;
        }

        if (damage <= 0)
        {
            return 0;
        }
        else
        {
            j = EnchantmentHelper.getEnchantmentModifierDamage(this.getLastActiveItems(), source);

            if (j > 20)
            {
                j = 20;
            }

            return damage;
        }
    }
	@Override
	public ItemStack[] getLastActiveItems()
    {
        return this.armour;
    }
	@Override
	public ItemStack getHeldItem()
	{
		return armour[0];
	}

	/**
	 * Used to determine what hounds gang up
	 * @param theDog: The dog requesting assistance
	 * @return If both are untamed: they will fight together.
	 * If both are tamed by the same person: they will fight too
	 */
	public boolean willFightFor(EntityHound theDog) 
	{
		if(!isTamed())
		{
			return !theDog.isTamed();
		}
		else
		{
			if(getOwnerName() != null && theDog.getOwnerName() != null)
			{
				return getOwnerName().equals(theDog.getOwnerName());
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * A list of items that are used for testing
	 * @return if an item has been used
	 */
	private boolean applyDebugItems(EntityPlayer user, ItemStack itemstack)
	{
		if(itemstack.itemID == Item.book.itemID)
    	{
    		levelup(false);
    		return true;
    	}
    	if(itemstack.itemID == Item.slimeBall.itemID)
    	{
    		setHunger(getHunger()-1);
    		return true;
    	}
    	if(itemstack.itemID == Item.appleGold.itemID)
    	{
    		if(isChild())
    		{
	    		setGrowingAge(-2);
	    		return true;
    		}
    		else
    		{
	    		setGrowingAge(0);
	    		return true;
    		}
    	}
    	if(isTamed() && itemstack.itemID == Item.bone.itemID)
    	{
    		this.setOwner(user.username);
    		return true;
    	}
    	if(itemstack.itemID == Item.wheat.itemID)
    	{
    		if(getBreedInt() < EnumHoundBreed.getMaxBreeds())
    		setBreedInt(getBreedInt() + 1);
    		return true;
    	}
    	if(itemstack.itemID == Item.carrot.itemID)
    	{
    		if(getBreedInt() > 0)
    		setBreedInt(getBreedInt() - 1);
    		return true;
    	}
    	return false;
	}
	
	/**
	 * Tries to eat food off the ground
	 * @param target the food on the ground
	 * @return true if it was eaten
	 */
	private boolean tryEatItemFood(EntityItem target)
	{
		if (target != null && target.getEntityItem().getItem() instanceof ItemFood)
		{
			ItemFood food = (ItemFood) (target.getEntityItem().getItem());
			if (food != null && willEatFood(food)) 
			{
				addToHunger(food.getHealAmount());
				
				if(eatAnimation <= 0)
				{
					chew(food.getHealAmount() * 10);
					eatAnimation = 20;
				}
				
				if(!worldObj.isRemote)
				{
					target.getEntityItem().stackSize--;
					if (target.getEntityItem().stackSize <= 0) 
					{
						target.setDead();
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * If the hound will eat a food, normally: this is only meat, but if the hound is half hunger
	 * it will eat any food
	 */
	private boolean willEatFood(ItemFood food) 
	{
		return food.isWolfsFavoriteMeat() || getHunger() < getLowHunger();
	}
	public boolean isTempting(ItemStack item)
	{
		if(item != null)
		{
			if(item.itemID == Item.bone.itemID)
			{
				return true;
			}
			if(item.getItem() instanceof ItemFood)
			{
				if(((ItemFood)item.getItem()).isWolfsFavoriteMeat())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	private int getLowHunger() 
	{
		return isChild() ? 3 : 10;
	}

	/**
	 * Called when the hound walks over an item
	 */
	private void pickupItem(EntityItem item)
	{
		if(item == null || item.getEntityItem() == null)return;
		if(getHunger() < getMaxHunger())
		{
			if(tryEatItemFood(item))
			{
				return;
			}
		}
		ItemStack itemstack = item.getEntityItem();
		if(!worldObj.isRemote && shouldPickupItems() && item.delayBeforeCanPickup <= 0 && pack.addItemStackToInventory(itemstack))
		{
			worldObj.playSoundAtEntity(this, "random.pop",0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);//EntityPlayer
			item.getEntityItem().stackSize--;
			if (item.getEntityItem().stackSize <= 0) 
			{
				item.setDead();
			}
		}
	}
	
	/**
	 * Determines if the hound should collect items
	 */
	private boolean shouldPickupItems()
	{
		return getAvailableRows() > 0 && pickupItems && hasUnlockedPickup();
	}
	
	
	/**
	 * Determines if the hound will leap attack
	 */
	public boolean shouldLeapAttack()
	{
		return leapAttack && hasUnlockedLeap();
	}
	
	/**
	 * Determines if the hound will draw attention from the owner
	 */
	public boolean shouldBoost()
	{
		return boostStep && hasUnlockedBoost();
	}
	
	public boolean hasUnlockedLeap()
	{
		return level >= 5;
	}
	public boolean hasUnlockedBoost()
	{
		return level >= 25;
	}
	public boolean hasUnlockedPickup()
	{
		return level >= 40;
	}
	public boolean hasUnlockedTeleport()
	{
		return level >= 80;
	}
	
	
	/**
	 * Teleports the dog to coords
	 * @param coords the x,y,z array(must be 3 doubles)
	 * @return true if a teleport is successful
	 */
	private boolean teleport(double[] coords)
	{
		if(coords.length >= 3)
		{
			return teleport(coords[0], coords[1], coords[2]);
		}
		return false;
	}
	
	/**
	 * Teleports the dog to x, y, and z
	 */
	private boolean teleport(double x, double y, double z)
	{
		int i = MathHelper.floor_double(x);
        int j = MathHelper.floor_double(z);
        int k = MathHelper.floor_double(y);
		if (worldObj.doesBlockHaveSolidTopSurface(i, k - 1, j) && !worldObj.isBlockNormalCube(i, k, j) && !worldObj.isBlockNormalCube(i, k + 1, j))
        {
            setLocationAndAngles((double)((float)(i) + 0.5F), (double)k, (double)((float)(j) + 0.5F), rotationYaw, rotationPitch);
            this.getNavigator().clearPathEntity();
            return true;
        }
		
		
		 i = MathHelper.floor_double(x) - 2;
         j = MathHelper.floor_double(z) - 2;
         k = MathHelper.floor_double(y);

        for (int l = 0; l <= 4; ++l)
        {
            for (int i1 = 0; i1 <= 4; ++i1)
            {
                if ((l < 1 || i1 < 1 || l > 3 || i1 > 3)&& worldObj.doesBlockHaveSolidTopSurface(i + l, k - 1, j + i1) && !worldObj.isBlockNormalCube(i + l, k, j + i1) && !worldObj.isBlockNormalCube(i + l, k + 1, j + i1))
                {
                    setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), rotationYaw, rotationPitch);
                    this.getNavigator().clearPathEntity();
                    return true;
                }
            }
        }
        
        trySendPlayerMessage("Your Hound " + getNameString() + "Failed to find a spot to teleport");
        return false;
	}
	
	public float getSpeedModifier()
	{
		float speed = 1.0F; //TODO rewrite speed because of FUCKING ATTRIBUTES!
		
		if(!(cfg.disableWeight && getArmourSpeedBuff() < 0))
		{
			speed += getArmourSpeedBuff();
		}
		if(isChild())
		{
			speed *= 0.75F;
		}
		return speed;
	}
	/**
	 * Gets the amount armour effects the hound
	 * @return -1 for slow +1 for fast 0 for no effect
	 */
	private float getArmourSpeedBuff()
	{
		float speed = 0.0F;
		
		ItemStack[] var2 = this.armour;
        int var3 = var2.length;
        
		for (int var4 = 0; var4 < var3; ++var4)
        {
            ItemStack var5 = var2[var4];

            if (var5 != null && var5.getItem() instanceof IHoundArmour)
            {
                float var6 = ((IHoundArmour)var5.getItem()).getMobilityModifier();
                speed += var6;
            }
        }
		
		return speed;
	}
	
	
	/**
	 * Gets the name used in messages
	 * @return The custom name or nothing
	 */
	private String getNameString()
	{
		return this.hasCustomNameTag() ?  ("'" + this.getCustomNameTag() + "' ") : "";
	}

	public boolean inPack()
	{
		return inPack;
	}
	
	/**
	 * Attepts to see if there is a large enough pack to fight in
	 * @return if the pack size is more than 4
	 */
	public boolean searchForPack()
	{
		int packSize = 0;
		
		AxisAlignedBB search = this.boundingBox.expand(16D, 8D, 16D);
		
		List<EntityHound>dogs = worldObj.getEntitiesWithinAABB(EntityHound.class, search);
		
		Iterator dogList = dogs.iterator();
		while(dogList.hasNext())
		{
			EntityHound dog = (EntityHound)dogList.next();
			if(!dog.isTamed())
			{
				packSize ++;
			}
		}
		
		return packSize > 4;
	}
	
	/**
	 * Gets the bonus to trust on how many players are in the area
	 */
	public float getPlayerBonus()
	{
		AxisAlignedBB search = this.boundingBox.expand(16D, 8D, 16D);
		List<EntityHound>players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, search);
		
		int mins = 10;
		return (100F / (60F * mins)) * (float)players.size();
	}
	
	public float getVillaBonus()
	{
		AxisAlignedBB search = this.boundingBox.expand(16D, 8D, 16D);
		List<EntityHound>players = worldObj.getEntitiesWithinAABB(EntityVillager.class, search);
		
		int mins = 10;
		return (100F / (60F * mins)) * (float)players.size();
	}

	public boolean canTempt() 
	{
		return !isTamed() && !inPack() && !isAngry() && trust > 25F;
	}
	
	private EnumHoundBreed getBreed()
	{
		return EnumHoundBreed.getBreed(getBreedInt());
	}
	
	
	private int getBreedInt() 
	{
		return dataWatcher.getWatchableObjectInt(IdBase+3);
	}

	private void setBreedInt(int value)
	{
		dataWatcher.updateObject(IdBase+3, value);
	}

	/**
	 * Sets a random breed
	 */
	private void setRandomBreed() 
	{
		setBreedInt(rand.nextInt(6));
		refreshHealth();
	}
	
	@SideOnly(Side.CLIENT)
	public String getSpots()
	{
		if(getBreed() != null)
		{
			return getBreed().spots;
		}
		return null;
	}
	
	private NBTTagCompound getNBT(ItemStack itemstack) 
	{
		if(!itemstack.hasTagCompound())
		{
			itemstack.setTagCompound(new NBTTagCompound());
		}
		return itemstack.getTagCompound();
	}
	
	private boolean onUseTrade(EntityPlayer user, ItemStack item)
	{
		if(item.hasTagCompound() && item.getTagCompound().hasKey("TradedHound"))
		{
			if(!(getOwnerName() != null && user.username == getOwnerName()) && item.getTagCompound().getInteger("TradedHound") == ID)
			{
				setOwner(user.username);
                user.inventory.setInventorySlotContents(user.inventory.currentItem, (ItemStack)null);
                user.addChatMessage("This hound " + getNameString() + "is now yours!");
                return true;
			}
		}
		if(getOwner() != null && getOwner() == user) // IF CAN TRADE
		{
			getNBT(item).setInteger("TradedHound", ID);
			
			String name = (this.getNameString().length() > 0) ? this.getNameString() : "(Unnamed)";
			
			item.setItemName("Hound Trade: " + name);
			return true;
		}
		return false;
	}
	
	public boolean canSeeName(EntityPlayer observer)
    {
        Vec3 vec3 = observer.getLook(1.0F).normalize();
        Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - observer.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - (observer.posY + (double)observer.getEyeHeight()), this.posZ - observer.posZ);
        double d0 = vec31.lengthVector();
        vec31 = vec31.normalize();
        double d1 = vec3.dotProduct(vec31);
        return d1 > 1.0D - 0.025D / d0 ? observer.canEntityBeSeen(this) : false;
    }
	public boolean shouldDefendOwner(EntityLivingBase tar) 
	{
		return attackDefense;
	}
	
	
	@Override
	public boolean allowLeashing()
    {
		if(!isTamed())
		{
			return canTempt();
		}
		return super.allowLeashing();
    }
	
	@Override
	public float getBlockPathWeight(int x, int y, int z)
	{
		if(worldObj.getBlockMaterial(x, y, z) == Material.fire || worldObj.getBlockMaterial(x, y, z) == Material.lava)
		{
			return 0F;
		}
		if(worldObj.getBlockMaterial(x, y, z) == Material.water && isBurning())
		{
			return 20F;
		}
		
		TileEntity block = worldObj.getBlockTileEntity(x,  y,  z);
		
		if(block != null && block instanceof IHeatSource)
		{
			IHeatSource src = (IHeatSource)block;
			if(src.getHeat() > 0)
			{
				return 0F;
			}
		}
		
		return super.getBlockPathWeight(x, y, z);
	}
	@Override
	public void setItem(ItemStack item, int slot) 
	{
		armour[slot] = item;
	}

    @Override
    public Entity getOwner() {
        MinecraftServer server = MinecraftServer.getServer();
        Entity entity = server.getConfigurationManager().getPlayerForUsername(this.getOwnerName());
        return entity;
    }
}
