
package minefantasy.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.google.common.io.ByteArrayDataInput;

import minefantasy.MineFantasyBase;
import minefantasy.api.armour.EnumArmourClass;
import minefantasy.api.tactic.ISpecialSenses;
import minefantasy.item.ItemListMF;
import minefantasy.system.MF_Calculate;
import minefantasy.system.TacticalManager;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ForgeDummyContainer;
import net.minecraftforge.common.ForgeHooks;

/**
 * Titan:
 * Can not charge
 * Does more damage
 * Can inflict explosive damage
 * 50% larger
 * Jumps higher and moves faster
 * Resistant to projectile damage: only does 25%
 * High health
 * Massive armour rating
 * Will heal and gain more health when making a kill
 */
public class EntityMinotaur extends EntityMob implements ISpecialSenses
{
    public int swing;
    private int swingTar;
    private int hitSize;
    protected int swingY;
    private int swingTarY;
    private int hitSizeY;
	private float attackMod;
	public boolean isTitan = false;
	/**
	 * Origin, Charge
	 */
	private float[] moveSpeeds = new float[]{0.3F, 0.6F};

    public EntityMinotaur(World world) 
    {
        super(world);
        
        attackMod = 1;
        chargeDuration = 0;
        chargeCool = 0;
        isCharging = false;
        this.setSize(1.5F, 3F);
        this.experienceValue = 50;
        
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.6F));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityLiving.class, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        
        for (int i = 0; i < this.equipmentDropChances.length; ++i)
        {
            this.equipmentDropChances[i] = 2.0F;
        }
    }
    
    @Override
    public boolean interact(EntityPlayer player)
    {
    	ItemStack held = player.getHeldItem();
    	if(MineFantasyBase.isDebug() && held != null)
    	{
    		if(held.itemID == Item.appleGold.itemID && held.getItemDamage() == 1 && canSetTitan())
    		{
    			setTitan();
    			addRandomItems();
    			return true;
    		}
    		if(held.itemID == Item.coal.itemID)
    		{
    			setHealth(0.1F);
    			return true;
    		}
    	}
    	return super.interact(player);
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(60D);
    }

    @Override
    protected boolean isAIEnabled() 
    {
        return true;
    }

    public void onLivingUpdate() 
    {
        super.onLivingUpdate();
        
        if (chargeDuration > 0) {
            chargeDuration--;
        }
        if (swingY > swingTarY) {
            swingY--;
        }
        if (swingY < swingTarY) {
            swingY++;
        }
        if (swingY >= hitSizeY) {
            swingTarY = 0;
        }
        if (swing > swingTar) {
            swing--;
        }
        if (swing < swingTar) {
            swing++;
        }
        if (swing >= hitSize) {
            swingTar = 0;
        }
        if (chargeCool > 0) {
            chargeCool--;
        }
        if (chargeDuration <= 0) 
        {
        	attackMod = 1;
        	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.moveSpeeds[0]);
            isCharging = false;
        }
        if (rand.nextInt(50) == 0 && this.getAttackTarget() != null) {
            float f = this.getDistanceToEntity(getAttackTarget());
            tryCharge(getAttackTarget(), f);
        }
    }

    private boolean canSetTitan() 
    {
    	if(!worldObj.canBlockSeeTheSky((int)posX, (int)posY+1, (int)posZ) && dimension != -1)
    	{
    		return false;
    	}
    	for(int x = -1; x <= 1; x++)
    	{
    		for(int z = -1; z <= 1; z++)
        	{
    			for(int y = 0; y <= 3; y++)
    	    	{
    	    		if(worldObj.getBlockMaterial((int)posX+x, (int)posY+y, (int)posZ+z).isSolid())
    	    		{
    	    			return false;
    	    		}
    	    	}	
        	}	
    	}
		return true;
	}

	private void setTitan()
    {
    	if(worldObj.isRemote)
    	{
    		return;
    	}
    	experienceValue = 250;
    	this.stepHeight = 1.0F;
    	this.jumpMovementFactor = 0.02F * 1.5F;
    	this.setSize(1.5F, 4.5F);
    	isTitan = true;
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.5F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(7.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(120D);
        this.setHealth(getMaxHealth());
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, IMob.class, 0, false));
	}
	
	@Override
	public void onKillEntity(EntityLivingBase killed)
	{
		if(isTitan)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(getMaxHealth() + 1);
			this.heal(killed.getMaxHealth()/4F);
		}
		super.onKillEntity(killed);
	}

	@Override
	public void jump()
	{
		float jumpHeight = isTitan ? 1.5F : 1.0F;
		this.motionY = 0.41999998688697815D * jumpHeight;

        if (this.isPotionActive(Potion.jump))
        {
            this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }

        if (this.isSprinting())
        {
            float f = this.rotationYaw * 0.017453292F;
            this.motionX -= (double)(MathHelper.sin(f) * 0.2F);
            this.motionZ += (double)(MathHelper.cos(f) * 0.2F);
        }

        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);
	}
    @Override
    public int getTotalArmorValue()
    {
    	return isTitan ? 20 : 4;
    }
    
	@Override
    public boolean attackEntityAsMob(Entity tar)
    {
    	if(!this.canEntityBeSeen(tar))return false;
    	
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * attackMod;
        int i = 0;

        if (tar instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)tar);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)tar);
        }

        boolean flag = tar.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if(isTitan)
        {
	        if(!worldObj.isRemote && rand.nextInt(getStrongChance()) == 0)
	        {
	        	worldObj.createExplosion(this, tar.posX, tar.posY, tar.posZ, rand.nextFloat()*0.5F + 0.5F, true);
	        }
        }
        
        if (flag)
        {
            if (i > 0)
            {
                tar.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                tar.setFire(j * 4);
            }

            if (tar instanceof EntityLivingBase)
            {
                EnchantmentThorns.func_92096_a(this, (EntityLivingBase)tar, this.rand);
            }
        }

        return flag;
    }
	
	private int getStrongChance() 
	{
		if(getHealth() < (getMaxHealth()/2))
		{
			return 5;
		}
		if(getHealth() < (getMaxHealth()/4))
		{
			return 2;
		}
		if(getHealth() < (getMaxHealth()/8))
		{
			return 1;
		}
		return 10;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float dam)
	{
		if(isTitan)
		{
			if(source.isProjectile())
			{
				dam *= 0.25F;
			}
			if(!worldObj.isRemote && source == DamageSource.fall)
			{
				worldObj.createExplosion(this, posX, posY, posZ, dam/3, false);
				worldObj.createExplosion(this, posX, posY, posZ, 0.5F+rand.nextFloat(), true);
				dam = 0;
			}
		}
		return super.attackEntityFrom(source, dam);
	}

	public void Charge() 
	{
		if(isTitan)
		{
			return;
		}
        chargeDuration = 80;
        chargeCool = 320;
        attackMod = 1.2F;
        isCharging = true;
        
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.moveSpeeds[1]);
    }
	private static final int idBase = 12;
    @Override
    public void entityInit()
    {
    	super.entityInit();
    	dataWatcher.addObject(idBase, new Integer(swing));
    	dataWatcher.addObject(idBase+1, new Integer(swingY));
    	dataWatcher.addObject(idBase+2, new Integer(0));
    	dataWatcher.addObject(idBase+3, new Integer(0));
    }
    
    @Override
    public EntityLivingData onSpawnWithEgg(EntityLivingData data)
    {
        this.setCanPickUpLoot(false);
        int chance = dimension == -1 ? 150 : 600;
        if(rand.nextInt(chance) < worldObj.difficultySetting && canSetTitan() && getDistanceAway() < cfg.titanDist)
    	{
    		setTitan();
    	}
        else
        {
        	if((posY < 32 && !worldObj.canBlockSeeTheSky((int)posX, (int)posY+1, (int)posZ+1)) || dimension == -1)
        	{
        		setBreed(1);
        	}
        	else if(isInSnow())
        	{
        		setBreed(2);
        	}
        }
        applyStats();
        this.addRandomItems();

        return super.onSpawnWithEgg(data);
    }

    private boolean isInSnow() 
    {
    	return worldObj.getBiomeGenForCoords((int)posX, (int)posZ).getEnableSnow();
	}

	public float getSpeedMod() {
        float f = 1.0F;
        if (isCharging) {
            f *= 2F;
        }
        if (isPotionActive(Potion.moveSpeed)) {
            f *= 1.0F + 0.2F * (float) (getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        if (isPotionActive(Potion.moveSlowdown)) {
            f *= 1.0F - 0.15F * (float) (getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
        }
        return f;
    }

    @Override
    public boolean getCanSpawnHere() 
    {
    	int c = 8 - worldObj.difficultySetting;
    	if(rand.nextInt(c) != 0 && dimension == 0)
    		return false;
    	
    	return super.getCanSpawnHere() &&  worldObj.difficultySetting >= cfg.minoDiff;

    }

    protected boolean isValidLightLevel()
    {
    	if(!isInSnow())
    	{
    		return super.isValidLightLevel();
    	}
    	
    	
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);

        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int l = this.worldObj.getBlockLightValue(i, j, k);

            if (this.worldObj.isThundering())
            {
                int i1 = this.worldObj.skylightSubtracted;
                this.worldObj.skylightSubtracted = 10;
                l = this.worldObj.getBlockLightValue(i, j, k);
                this.worldObj.skylightSubtracted = i1;
            }

            if(rand.nextInt(10) == 0)
            {
            	return l <= 10;
            }
            return l <= this.rand.nextInt(10);
        }
    }
    
    protected String getLivingSound() {
        return data_minefantasy.sound("mob.minotaurLive");
    }

    protected String getHurtSound() {
        return data_minefantasy.sound("mob.minotaurHurt");
    }

    protected String getDeathSound() {
        return data_minefantasy.sound("mob.minotaurDie");
    }

    protected float getSoundVolume() {
        return 1.2F;
    }

    protected int getDropItemId() 
    {
        if (isBurning()) {
            return Item.beefCooked.itemID;
        }
        return Item.beefRaw.itemID;
    }
    @Override
    protected void dropFewItems(boolean player, int looting)
    {
    	super.dropFewItems(player, looting);
    	int hide = 1 + rand.nextInt(1+looting);
    	if(isTitan)
    	{
    		hide *= 3;
    		for(int a = 0; a < 2 + rand.nextInt(5 * (looting+1)); a ++)
        	{
    			if(isBurning())
    			{
    				entityDropItem(new ItemStack(Item.beefCooked), 0F);
    			}
    			else
    			{
    				entityDropItem(new ItemStack(Item.beefRaw), 0F);
    			}
        	}
    	}
    	for(int a = 0; a < hide; a ++)
    	{
    		entityDropItem(new ItemStack(ItemListMF.misc, 1, ItemListMF.hideMinotaur), 0F);
    	}
    }
    
    protected void dropEquipment2(boolean dropAll, int chance)
    {
    	if(worldObj.isRemote)return;
    	
        ItemStack itemstack = this.getHeldItem();
        if (itemstack != null)
        {
            if (itemstack.isItemStackDamageable())
            {
                int k = Math.max(itemstack.getMaxDamage() - 25, 1);
                int l = itemstack.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(k) + 1);

                if (l > k)
                {
                    l = k;
                }

                if (l < 1)
                {
                    l = 1;
                }

                itemstack.setItemDamage(l);
            }

            this.entityDropItem(itemstack, 0.0F);
        }
    }
    public int chargeDuration;
    public int chargeCool;
    public boolean isCharging;

    private void knockbackEntity(Entity entity) {
        float knockbackMod = 2;
        float height = 1F;
        if(entity != null && entity instanceof EntityPlayer)
        {
        	EntityPlayer player = (EntityPlayer)entity;
        	knockbackMod = 0.5F + (getArmourKnockbackModifier(player)*1.5F);
        	height = 0.25F + (getArmourKnockbackModifier(player)*0.75F);
        }
        if (chargeDuration > 0) {
            knockbackMod *= 2;
        }
        
        entity.addVelocity((double) (-MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * (float) knockbackMod * 0.5F), height, (double) (MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * (float) knockbackMod * 0.5F));
    }

    public double getHeadChargeAngle() {
        return chargeDuration > 0 ? 30 : 0;
    }

    private void tryCharge(Entity entity, float f) {
        if (chargeCool <= 0 && f < 32.0F && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY) {
            Charge();
        }
    }
    @Override
    public void onUpdate()
    {
    	super.onUpdate();
    	if(!worldObj.isRemote)
    	{
    		dataWatcher.updateObject(idBase, Integer.valueOf(swing));
    		dataWatcher.updateObject(idBase+1, Integer.valueOf(swingY));
    		dataWatcher.updateObject(idBase+2, Integer.valueOf(isTitan ? 1 : 0));
    	} else
    	{
    		swing = dataWatcher.getWatchableObjectInt(idBase);
    		swingY = dataWatcher.getWatchableObjectInt(idBase+1);
    		isTitan = dataWatcher.getWatchableObjectInt(idBase+2) == 1;
    	}
    }
    
    
    public float getArmourKnockbackModifier(EntityPlayer player)
	{
		float r = 1F;

			EnumArmourClass AC0 = TacticalManager.getClassInSlot(player, 0);
			r -= (float)getArmourFractionOfTotal(AC0.getKnockback(), 4);
			
			EnumArmourClass AC1 = TacticalManager.getClassInSlot(player, 1);
			r -= (float)getArmourFractionOfTotal(AC1.getKnockback(), 7);
			
			EnumArmourClass AC2 = TacticalManager.getClassInSlot(player, 2);
			r -= (float)getArmourFractionOfTotal(AC2.getKnockback(), 8);
			
			EnumArmourClass AC3 = TacticalManager.getClassInSlot(player, 3);
			r -= (float)getArmourFractionOfTotal(AC3.getKnockback(), 5);

		return r > 0 ? r : 0;
	}
    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Titan", isTitan);
        nbt.setInteger("Breed", getBreed());
    }
    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        
        if(nbt.getBoolean("Titan"))
        {
        	setTitan();
        }
        if(nbt.hasKey("Breed"))
        {
        	setBreed(nbt.getInteger("Breed"));
        }
    }
    
    @Override
    public String getEntityName()
    {
    	if(hasCustomNameTag())
    	{
    		return this.getCustomNameTag();
    	}
    	if(isTitan)
    	{
    		return StatCollector.translateToLocal("entity.MineFantasy.MinotaurTitan.name");
    	}
    	return StatCollector.translateToLocal("entity.MineFantasy.Minotaur.name");
    }
    
    public double getArmourFractionOfTotal(double total, int armour)
	{
		return (total/24)*armour;
	}

	@Override
	public int getViewingArc() {
		return 180;
	}

	@Override
	public int getHearing() {
		return 0;
	}

	@Override
	public int getSight() {
		return -10;
	}
	/**
	 * Adds a held weapon
	 */
	protected void addRandomItems()
    {
    	ChunkCoordinates spawn = worldObj.getSpawnPoint();
    	double dist = getDistance(spawn.posX, spawn.posY, spawn.posZ);
    	
    	Item[]bronzeWeps = new Item[]{ItemListMF.battleaxeBronze, ItemListMF.greatswordBronze, ItemListMF.morningstarBronze, ItemListMF.warhammerBronze};
    	Item[]ironWeps = new Item[]{ItemListMF.battleaxeIron, ItemListMF.greatswordIron, ItemListMF.morningstarIron, ItemListMF.warhammerIron};
    	Item[]steelWeps = new Item[]{ItemListMF.battleaxeSteel, ItemListMF.greatswordSteel, ItemListMF.morningstarSteel, ItemListMF.warhammerSteel};
    	Item[]mithrilWeps = new Item[]{ItemListMF.battleaxeMithril, ItemListMF.greatswordMithril, ItemListMF.morningstarMithril, ItemListMF.warhammerMithril};
    	
    	int spawnDist = cfg.minotaurWeaponDist;
    	
    	if(dist > spawnDist*4 || (dimension == -1 && rand.nextInt(40) == 0))
    	{
    		int a = mithrilWeps.length;
    		this.setCurrentItemOrArmor(0, new ItemStack(mithrilWeps[rand.nextInt(a)]));
    		return;
    	}
    	else if(dist > spawnDist*3 || (dimension == -1 && rand.nextInt(30) == 0))
    	{
    		int a = steelWeps.length;
    		this.setCurrentItemOrArmor(0, new ItemStack(steelWeps[rand.nextInt(a)]));
    		return;
    	}
    	else if(dist > spawnDist*2 || (dimension == -1 && rand.nextInt(20) == 0))
    	{
    		int a = ironWeps.length;
    		this.setCurrentItemOrArmor(0, new ItemStack(ironWeps[rand.nextInt(a)]));
    		return;
    	}
    	else if(dist > spawnDist || (dimension == -1 && rand.nextInt(5) == 0))
    	{
    		int a = bronzeWeps.length;
    		this.setCurrentItemOrArmor(0, new ItemStack(bronzeWeps[rand.nextInt(a)]));
    		return;
    	}
    }
	
	public String getTexture() 
	{
		if(isTitan)
		{
			return "minotaurTitan";
		}
		switch(getBreed())
		{
		case 0:return "minotaur";
		case 1:return "minotaurBlack";
		case 2:return "minotaurAlbino";
		}
		return "minotaur";
	}
	
	/**
	 * 0 = Brown
	 * 1 = Black
	 * 2 = Albino
	 * 
	 * Does not include Titan
	 */
	public int getBreed()
	{
		return dataWatcher.getWatchableObjectInt(idBase+3);
	}
	
	/**
	 * 0 = Brown
	 * 1 = Black
	 * 2 = Albino
	 * 
	 * Does not include Titan
	 */
	private void setBreed(int i)
	{
		dataWatcher.updateObject(idBase+3, i);
		isImmuneToFire = i == 1;
	}
	
	private void applyStats()
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(getBreedHealth());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(getBreedDamage());
		setHealth(getMaxHealth());
	}

	private double getBreedDamage()
	{
		switch(getBreed())
		{
		case 0:return 5.0D;//BROWN
		case 1:return 6.0D;//BLACK
		case 2:return 4.0D;//ALBINO
		}
		return 5.0D;
	}

	private double getBreedHealth() 
	{
		switch(getBreed())
		{
		case 0:return 60F;//BROWN
		case 1:return 80F;//BLACK
		case 2:return 100F;//ALBINO
		}
		return 60F;
	}
	
	@Override
	public void onDeath(DamageSource source)
	{
		super.onDeath(source);
		if(worldObj.isRemote)return;
		
		boolean placeChest = false;
		
		if(!isTitan)return;
		
		
		TileEntityChest chest = getLootChest();
		
		int x = (int)posX;
		int z = (int)posZ;
		for(int y = (int)posY+1; y > 0; y --)
		{
			if(worldObj.getBlockMaterial(x, y, z).isSolid())
			{
				break;
			}
			
			if(tryPlaceChest(x, y, z, chest))
			{
				placeChest = true;
				break;
			}
		}
		
		if(!placeChest)
		{
			for(int a = 0; a < chest.getSizeInventory(); a ++)
			{
				ItemStack drop = chest.getStackInSlot(a);
				if(drop != null)
				this.entityDropItem(drop, 0);
			}
		}
	}

	private boolean tryPlaceChest(int x, int y, int z, TileEntityChest tile) 
	{
		boolean replace = false;
		int id = worldObj.getBlockId(x, y, z);
		Block bl = Block.blocksList[id];
		
		if(worldObj.getBlockMaterial(x, y-1, z).isSolid())
		{
			if(bl != null)
			{
				replace = bl.isBlockReplaceable(worldObj, x, y, z);
			}
			if(worldObj.isAirBlock(x, y, z) || replace)
			{
				worldObj.setBlock(x, y, z, Block.chest.blockID);
				worldObj.setBlockTileEntity(x, y, z, tile);
				return true;
			}
		}
		return false;
	}
	
	private TileEntityChest getLootChest()
	{
		TileEntityChest tile = new TileEntityChest();
		
		tile.setChestGuiName("Minotaur Titan Loot");
		
		fillChest(tile, ChestGenHooks.DUNGEON_CHEST);
		fillChest(tile, ChestGenHooks.PYRAMID_DESERT_CHEST);
		fillChest(tile, ChestGenHooks.PYRAMID_JUNGLE_CHEST);
		fillChest(tile, ChestGenHooks.STRONGHOLD_CROSSING);
		fillChest(tile, ChestGenHooks.STRONGHOLD_CORRIDOR);
		
		return tile;
	}
	
	private void fillChest(TileEntityChest tile, String contents)
	{
		ChestGenHooks info = ChestGenHooks.getInfo(contents);
		WeightedRandomChestContent.generateChestContents(rand, info.getItems(rand), (IInventory)tile, info.getCount(rand));
	}
	
	private double getDistanceAway()
	{
		ChunkCoordinates spawn = worldObj.getSpawnPoint();
		return this.getDistance(spawn.posX, spawn.posY, spawn.posZ);
	}
}
