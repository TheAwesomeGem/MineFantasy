package minefantasy.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;

import minefantasy.MineFantasyBase;
import minefantasy.api.tactic.ISpecialSenses;
import minefantasy.entity.ai.EntityAIBreakDoorAnimate;
import minefantasy.entity.ai.EntityAIUseDoorMF;
import minefantasy.item.ItemListMF;
import minefantasy.system.CombatManager;
import minefantasy.system.MF_Calculate;
import minefantasy.system.TacticalManager;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

/**
 * 
 * @author Anonymous Productions
 * 
 *         Sources are provided for educational reasons. though small bits of
 *         code, or methods can be used in your own creations.
 */
public class EntitySkeletalKnight extends EntityMob implements IMob, ISpecialSenses, IRangedAttackMob, PacketUserMF {

	private static final float[] enchantmentProbability = new float[] {0.0F, 0.0F, 0.1F, 0.2F};
	private int rallyCooldown;
	private int sprintCooldown;
	private static final int dataId = 12;
	private float attackRange = 2.5F;

	public EntitySkeletalKnight(World world) 
	{
		super(world);
		setWeapon((byte)0);
		
		this.tasks.addTask(3, new EntityAIArrowAttack(this, 0.25F, 20, 60, 15.0F));
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoorAnimate(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityLiving.class, 1.0D, true));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        
        this.equipmentDropChances[0] = 2.0F;
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(64.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.23F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setAttribute(1.0D);
    }
	
	@Override
	public void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(dataId, (int)0);
		this.dataWatcher.addObject(dataId+1, (byte)0);
		this.dataWatcher.addObject(dataId+2, (byte)0);
	}
	 @Override
    public EntityLivingData onSpawnWithEgg(EntityLivingData data)
    {
        this.setCanPickUpLoot(false);
        enchant(defaultHeldItem);
    	enchant(bluntItem);
    	enchant(rangedItem);
    	enchant(stealthItem);

        return super.onSpawnWithEgg(data);
    }
	
	@Override
	public int getTotalArmorValue()
	{
		return 20;
	}

	private boolean isProperDistance() {
    	if(this.dimension != 0)
    		return true;
    	
    	ChunkCoordinates spawn = worldObj.getSpawnPoint();
		return this.getDistance(spawn.posX, spawn.posY, spawn.posZ) > getDistanceToSpawn();
	}
	
	public double getDistanceToSpawn() {
		return 0;
	}
	@Override
	public boolean getCanSpawnHere() 
	{
		if(rand.nextInt(10) != 0)
		{
			return false;
		}
		EntityPlayer xpPlayer = getHighestXPLevel();
		int lvl = 0;
		if(xpPlayer != null)
		{
			lvl = xpPlayer.experienceLevel;
		}
		if(lvl < cfg.knightLvl)
		{
			return false;
		}
		if(worldObj.difficultySetting < cfg.knightDiff)
		{
			return false;
		}
		
		if(!super.getCanSpawnHere())
		{
			return false;
		}
		if(MineFantasyBase.isDebug())
		{
			System.out.println("Try Knight Spawn: " + lvl + " for " + xpPlayer.getEntityName());
		}
		return true;
	}

	private EntityPlayer getHighestXPLevel() {
		EntityPlayer play = worldObj.getClosestPlayerToEntity(this, -1);
		if(play != null)
		{
			return play;
		}
		return null;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(!worldObj.isRemote)
		{
			if(rallyCooldown > 0)rallyCooldown --;
		}
		if(rallyCooldown <= 0)
		{
			rally(getAttackTarget());
		}
		
		if(getAttackTarget() != null)
		{
			if(getAttackTarget().isDead)
			{
				setAttackTarget(null);
			}
		}
		
		if(!worldObj.isRemote)
		{
			int block = this.getBlockTime();
			if(block > 0)block --;
			
			dataWatcher.updateObject(dataId, block);
			
			if(ticksExisted % 100 == 0)
			{
				heal(1);
			}
			if(ticksExisted % 10 == 0)
			{
				updateWeapons();
			}
			
			if(getAttackTarget() != null)
			{
				if(getAttackTarget().isSwingInProgress)
				{
					if(this.getHeldItem() == this.defaultHeldItem)
					{
						if(isInfront(getAttackTarget()))
						{
							dataWatcher.updateObject(dataId, 15);
						}
					}
				}
				if(this.getDistanceToEntity(getAttackTarget()) < 2 && rand.nextInt(5) == 0 && !isSneaking())
				{
					double pounceFactor = getDistanceToEntity(getAttackTarget())/8;
					addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)pounceFactor * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)pounceFactor * 0.5F));
					jump();
				}
				if(this.getDistanceToEntity(getAttackTarget()) < 2 && fallDistance > 0.0 && !isOnLadder())
				{
					this.attackEntityAsMob(getAttackTarget());
				}
			}
			else
			{
				setWeapon((byte)0);
			}
			sprintCooldown --;
			setSneaking(getSneakStage());
			if(startSprinting())
			{
				setSprinting(true);
			}
			if(stopSprinting())
			{
				setSprinting(false);
			}
		}
		if(!worldObj.isRemote)
		{
			sendPacketToClients();
			
			if(getAttackTarget() != null && !getAttackTarget().isDead && rand.nextInt(400) == 0)
			{
				if(getDistanceToEntity(getAttackTarget()) > 4 && getDistanceToEntity(getAttackTarget()) < 12)
				{
					throwBombAt(getAttackTarget());
					return;
				}
			}
		}
	}

	private void updateWeapons() 
	{
		if(getAttackTarget() != null)
		{
			int AC = getAttackTarget().getTotalArmorValue();
			int bluntAC = 15;
			
			if(isSneaking())
			{
				this.setWeaponUsed((byte)1);
			}
		    else if(AC >= bluntAC)
			{
				this.setWeaponUsed((byte)2);
			}
			else
			{
				this.setWeaponUsed((byte)0);
			}
		}
		else
		{
			this.setWeaponUsed((byte)0);
		}
	}

	private void sendPacketToClients()
	{
		try
		{
			Packet packet = PacketManagerMF.getEntityPacketInteger(this, rallyCooldown);
			FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendToAllNear(posX, posY, posZ, 16D, dimension, packet);
		}
		catch(Exception e){}
	}

	@Override
	protected void jump()
    {
		if(onGround)
		super.jump();
    }
	
	
	public boolean useRanged() {
		return dataWatcher.getWatchableObjectByte(dataId+1)== 1;
	}
	/**
	 * @param type
	 * 0 = melee
	 * 1 = ranged
	 */
	private void setWeapon(byte type)
	{
		this.dataWatcher.updateObject(dataId+1, type);
		
		this.setCurrentItemOrArmor(0, getNextWeapon());
	}
	
	/**
	 * 0 = Knight
	 * 1 = Guardian
	 */
	private int getType()
	{
		return 1;
	}
	
	private boolean startSprinting() {
		if(getNavigator().noPath())
			return false;
		if(isSneaking())
		{
			return false;
		}
		if(getType() == 0)
		{
			return false;
		}
		if(sprintCooldown > 0)
		{
			return true;
		}
		if(this.getAttackTarget() != null)
		{
			if(this.getDistanceToEntity(getAttackTarget()) > 4)
			{
				sprintCooldown = 20;
				return true;
			}
		}
		return false;
	}
	private boolean stopSprinting() {
		if(getNavigator().noPath())
			return true;
		if(getType() == 0)
		{
			return true;
		}
		if(sprintCooldown > 0)
		{
			return false;
		}
		if(this.getAttackTarget() != null)
		{
			if(this.getDistanceToEntity(getAttackTarget()) < 1.5D)
			{
				sprintCooldown = 0;
				return true;
			}
		}
		return true;
	}
	
	private boolean getSneakStage() {
		if(getNavigator().noPath())
			return false;
		if(isSprinting())
		{
			return false;
		}
		if(getType() == 0)
		{
			return false;
		}
		if(this.getAttackTarget() != null)
		{
			if(!TacticalManager.isDetected(this, getAttackTarget()))
			{
				return true;
			}
			if(fallDistance > 3.0)
			{
				return true;
			}
		}
		return false;
	}

	public boolean isBlocking()
	{
		return getBlockTime() > 0;
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public void playStepSound(int x, int y, int z, int block) {
		super.playStepSound(x, y, z, block);
		float volume = 0.8F;
		playSound("mob.irongolem.walk", volume, 1.0F);
		if(rand.nextInt(5) == 0)
			playSound("mob.irongolem.throw", volume, 1.0F);
		this.playSound("mob.skeleton.step", 0.15F, 1.0F);
	}

	@Override
	public void setAttackTarget(EntityLivingBase entity) {
		if(entity instanceof EntityMob)
		{
			((EntityMob)entity).setAttackTarget(null);
			return;
		}
		super.setAttackTarget(entity);
	}

	public void rally(EntityLivingBase entity)
	{
		boolean target = entity != null && !entity.isDead;
		boolean success = false;
		
		rallyCooldown = getRallyTime();
		
		List<EntityLiving> mobs = worldObj.getEntitiesWithinAABB(EntityLiving.class,AxisAlignedBB.getAABBPool().getAABB(posX, posY, posZ, posX + 1.0D,posY + 1.0D, posZ + 1.0D).expand(64D, 4D, 64D));
		for(EntityLiving minion : mobs)
		{
			if(minion != null && minion instanceof IMob)
			{
				success = true;
				spawnAngryParticle(minion);
				minion.heal(2);
				if(target)
				{
					minion.setAttackTarget(entity);
					minion.getNavigator().tryMoveToEntityLiving(entity, 1.0F);
				}
			}
		}
		if(success)
		{
			spawnAngryParticle(this);
		}
	}
	private void spawnAngryParticle(Entity entity)
	{
		for (int var2 = 0; var2 < 5; ++var2)
        {
            double var3 = this.rand.nextGaussian() * 0.02D;
            double var5 = this.rand.nextGaussian() * 0.02D;
            double var7 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle("angryVillager", entity.posX + (double)(this.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width, entity.posY + 1.0D + (double)(this.rand.nextFloat() * entity.height), entity.posZ + (double)(this.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width, var3, var5, var7);
        }
	}

	private int getRallyTime() {
		return 400;
	}

	@Override
	protected void damageEntity(DamageSource source, float dam)
    {
		if(worldObj.isRemote)return;
		
		Entity en = source.getEntity();
		
        if (isInfront(en) && !source.isUnblockable() && this.isBlocking())
        {
            dam /= 2;
        }
        if(!worldObj.isRemote)
		{
			System.out.println("Full Dam: " + dam);
		}
        super.damageEntity(source, dam);
    }
	
	
	
	private boolean isInfront(Entity en) {
		if(en == null)
		return false;
		
		if(TacticalManager.isFlankedBy(en, this, 180))
			return false;
		
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage)
	{
		if(source instanceof EntityDamageSourceIndirect)
		{
			EntityDamageSourceIndirect src = (EntityDamageSourceIndirect)source;
			
			if(src.getEntity() != null && src.getEntity() == this)
			{
				return false;
			}
		}
		if(this.hurtTime == 0)
		{
			playSound("mob.skeleton.hurt", 1.0F, 1.0F);
		}
		if(!worldObj.isRemote)
		{
			System.out.println("Dam: " + damage);
		}
		return super.attackEntityFrom(source, damage);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if(!this.canEntityBeSeen(entity))return false;
		setWeapon((byte)0);
		swingItem();
		
		int knockback = 0;

        if (entity instanceof EntityLivingBase)
        {
            knockback += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)entity);
        }

        if (this.isSprinting())
        {
            knockback+=2;
        }
        if (knockback > 0)
        {
            entity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)knockback * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)knockback * 0.5F));
            this.motionX *= 0.6D;
            this.motionZ *= 0.6D;
        }
		return super.attackEntityAsMob(entity);
	}
	
	
	
	
	public void attackEntityWithRangedAttack(EntityLivingBase target, float damage)
    {
		if(worldObj.isRemote)return;
		
		if(target == null)return;
		
		if(getDistanceToEntity(target) < 2 || isSneaking() || isSprinting() || getAttackTarget() == null && getAttackTarget().isDead)
		{
			setWeapon((byte)0);
			return;
		}
		setWeapon((byte)1);
        EntityArrow entityarrow = new EntityArrowMF(this.worldObj, this, target, 1.6F, (float)(1 - this.worldObj.difficultySetting * 0.3D), 5);
        entityarrow.setDamage(2.0D + (2.0D * worldObj.difficultySetting));
        if(rand.nextInt(4) == 0)
        {
        	entityarrow.setIsCritical(true);
        }
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        entityarrow.setDamage((double)(damage * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.difficultySetting * 0.11F));

        if (i > 0)
        {
            entityarrow.setDamage(entityarrow.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            entityarrow.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0)
        {
            entityarrow.setFire(100);
        }

        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(entityarrow);
    }

	private void throwBombAt(EntityLivingBase target)
	{
		if(target == null || !this.canEntityBeSeen(target))
		{
			return;
		}
		swingItem();
		EntityBombThrown bomb = new EntityBombThrown(this.worldObj, this, target, 1.0F, (float)(1 - this.worldObj.difficultySetting * 0.3D)).setID(0);
        this.worldObj.spawnEntityInWorld(bomb);
	}

	public boolean attackEntity(Entity entity) {
		return super.attackEntityAsMob(entity);
	}

	@Override
	protected String getLivingSound() {
		return "mob.skeleton";
	}
	public float getAttackStrength(Entity entity)
    {
		float dam = 2.0F;
		if(getHeldItem() != null)
		{
			dam = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		}
		
		
		if (this.isPotionActive(Potion.damageBoost))
        {
			dam += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if (this.isPotionActive(Potion.weakness))
        {
        	dam -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }
        
        
        boolean flag = this.fallDistance > 0.0F && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Potion.blindness) && this.ridingEntity == null && entity instanceof EntityLivingBase;

        if (flag && dam > 0)
        {
            dam += this.rand.nextFloat()*(dam / 2 + 2);
        }

        
        return dam;
    }

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.skeletonhurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.skeletonhurt";
	}

	public ItemStack getNextWeapon() 
	{
		if(useRanged())
		{
			return rangedItem;
		}
		byte wp = this.getLastWeaponUsed();
		
		if(wp == 1)
		{
			return stealthItem;
		}
		if(wp == 2)
		{
			return bluntItem;
		}
		return defaultHeldItem;
	}

	private static final ItemStack defaultHeldItem = new ItemStack(
			ItemListMF.broadSteel, 1);
	private static final ItemStack bluntItem = new ItemStack(
			ItemListMF.warhammerSteel, 1);
	private static final ItemStack stealthItem = new ItemStack(
			ItemListMF.daggerSteel, 1);
	private static final ItemStack rangedItem = new ItemStack(
			ItemListMF.bowComposite, 1);

	private int getSwingSpeedModifier() {
		return 6;
	}

	@Override
	protected void dropFewItems(boolean pKill, int loot) 
	{
		if(worldObj.isRemote)return;
		int dropSize = this.rand.nextInt(5 + loot) + 4;
		int count;

		dropSize = this.rand.nextInt(3 + loot);

		for (count = 0; count < dropSize; ++count) {
			this.dropItem(Item.bone.itemID, 1);
		}
		ItemStack[] other = this.getDropItems(loot);
		if(other.length > 0)
		{
			for(ItemStack drop: other)
			{
			//	this.entityDropItem(drop, 0.0F);
			}
		}
		
		for(int a = 0; a < rand.nextInt(4 + loot); a ++)
		{
			this.entityDropItem(new ItemStack(ItemListMF.bombMF, 1, 0) , 0.0F);
		}
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound tag) 
	{
		super.writeEntityToNBT(tag);
		tag.setByte("Weapon", dataWatcher.getWatchableObjectByte(dataId+1));
		tag.setByte("Melee", dataWatcher.getWatchableObjectByte(dataId+2));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag) 
	{
		super.readEntityFromNBT(tag);
		
		if(tag.hasKey("Weapon"))
		setWeapon(tag.getByte("Weapon"));
		
		if(tag.hasKey("Melee"))
			setWeaponUsed(tag.getByte("Melee"));
	}
	
	private ItemStack[] getDropItems(int looting) 
	{
		return new ItemStack[]
		{
			getDamagedItem(defaultHeldItem),
			getDamagedItem(bluntItem),
			getDamagedItem(stealthItem),
			getDamagedItem(rangedItem),
		};
	}
	
	private ItemStack getDamagedItem(ItemStack itemstack)
	{
		if (itemstack.isItemStackDamageable())
        {
            int maxDamage = Math.max(itemstack.getMaxDamage() - 25, 1);
            int damageDone = itemstack.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(maxDamage) + 1);

            if (damageDone > maxDamage)
            {
                damageDone = maxDamage;
            }

            if (damageDone < 1)
            {
                damageDone = 1;
            }

            itemstack.setItemDamage(damageDone);
        }
		
		return itemstack;
	}

	private int getBlockTime()
	{
		return dataWatcher.getWatchableObjectInt(dataId);
	}
	@Override
	public int getViewingArc() {
		return 70;
	}

	@Override
	public int getHearing() {
		return 12;
	}

	@Override
	public int getSight() {
		return -10;
	}
	
	
	public EntityItem dropItem(int id, int stack, int damage)
    {
		int d = damage/2 + rand.nextInt(damage/2);
        return this.dropItemWithOffset(id, stack, d, 0.0F);
    }
	
	public EntityItem dropItemWithOffset(int id, int stack, int dam, float offset)
    {
        return this.entityDropItem(new ItemStack(id, stack, dam), offset);
    }
	
	protected void enchant(ItemStack item)
    {
		if(item != null && item.isItemEnchanted())
		{
			return;
		}
        if (item != null && this.rand.nextFloat() < enchantmentProbability[this.worldObj.difficultySetting])
        {
            EnchantmentHelper.addRandomEnchantment(this.rand, item, 5 + this.worldObj.difficultySetting * this.rand.nextInt(6));
        }
    }

	@Override
	public void recievePacket(ByteArrayDataInput data)
	{
		try
		{
			rallyCooldown = data.readInt();
		}catch(Exception e){};
	}
	
	/**
	 * 0 = sword
	 * 1 = dagger
	 * 2 = blunt
	 */
	private byte getLastWeaponUsed()
	{
		return dataWatcher.getWatchableObjectByte(dataId+2);
	}
	
	/**
	 * 0 = sword
	 * 1 = dagger
	 * 2 = blunt
	 */
	private void setWeaponUsed(byte id)
	{
		dataWatcher.updateObject(dataId+2, id);
	}
	
	@Override
	public void knockBack(Entity entity, float x, double y, double z)
    {
    }
}
