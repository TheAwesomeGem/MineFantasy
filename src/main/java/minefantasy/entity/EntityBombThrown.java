package minefantasy.entity;

import java.util.Iterator;
import java.util.List;


import minefantasy.api.weapon.EntityDamageSourceBomb;
import minefantasy.item.ItemListMF;
import minefantasy.item.ItemBombMF;
import minefantasy.system.CombatManager;
import minefantasy.system.cfg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class EntityBombThrown extends EntityThrowableBounce implements IBomb 
{
	private int ticksRemaining = 60;
	private float bounce = 0.8F;
    private EntityLivingBase thrower;
    
    public EntityBombThrown(World world)
    {
        super(world);
    }

    public EntityBombThrown(World world, EntityLivingBase entity)
    {
        super(world, entity);
        thrower = entity;
    }
    public EntityBombThrown(World world, EntityLivingBase entity, int fuse, int id)
    {
    	this(world, entity);
    	setID(id);
    	this.ticksRemaining = fuse;
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0.15F;
    }
    public EntityBombThrown(World world, double x, double y, double z, int fuse)
    {
        super(world, x, y, z);
        this.ticksRemaining = fuse;
    }
    
    public EntityBombThrown(World world, EntityLivingBase shooter, EntityLivingBase target, float power, float precision)
    {
        super(world, shooter);
        this.renderDistanceWeight = 10.0D;
        this.thrower = shooter;

        this.posY = shooter.posY + (double)shooter.getEyeHeight() - 0.10000000149011612D;
        double var6 = target.posX - shooter.posX;
        double var8 = target.posY + (double)target.getEyeHeight() - (0.699999988079071D / 0.2F * getArc()) - this.posY;
        double var10 = target.posZ - shooter.posZ;
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
            this.setThrowableHeading(var6, var8 + (double)var20, var10, power, precision);
        }
    }
    
    private float getArc() {
		return this.getGravityVelocity()*4;
	}

    /**
     * sets the bomb to be shrapnel
     */
    public EntityBombThrown shrapnel()
    {
    	setID(0);
    	return this;
    }
    
    @Override
    public void entityInit()
    {
    	super.entityInit();
    	dataWatcher.addObject(2, (byte)0);
    	dataWatcher.addObject(3, 1);
    	dataWatcher.addObject(4, 0);
    }
    
    public boolean getIsShrapnel()
    {
    	return dataWatcher.getWatchableObjectByte(2) == 0 ? false : true;
    }

    @Override
    public void onUpdate()
    {
    	this.worldObj.spawnParticle("smoke", posX, posY, posZ, -motionX, -motionY, -motionZ);
    	super.onUpdate();
    	this.ticksRemaining --;
    	if(ticksRemaining <= 0)
    	{
    		this.explode(new MovingObjectPosition(this));
    	}
    }
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        nbt.setFloat("Bounce", bounce);
        nbt.setInteger("Ticks", ticksRemaining);
        nbt.setInteger("ID", getID());
    }

    public void readEntityFromNBT(NBTTagCompound nbt)
    {
       setID(nbt.getInteger("ID"));
       bounce = nbt.getFloat("Bounce");
       ticksRemaining = nbt.getInteger("Ticks");
    }

    @Override
	public void explode(MovingObjectPosition pos) 
    {
		worldObj.playSoundAtEntity(this, "random.explode", 0.3F, 10F - 5F);
    	worldObj.createExplosion(this, posX, posY, posZ, 0, false);
        if (!this.worldObj.isRemote)
        {
        	double area = 4.0D;
            AxisAlignedBB var3 = this.boundingBox.expand(area, area/2, area);
            List var4 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);

            if (var4 != null && !var4.isEmpty())
            {
                Iterator var5 = var4.iterator();

                while (var5.hasNext())
                {
                    Entity var6 = (Entity)var5.next();
                    double var7 = this.getDistanceSqToEntity(var6);

                    double radius = getRadius();
                    if (var7 < radius)
                    {
                    	applyEffects(var6);
                    	
                    	int dam = getDamage();
                    	
                    	if(var7 < radius/2)
                    	{
                    		double sc = (radius/2)-var7;
                    		if(sc < 0)sc = 0;
                    		if(sc > (radius/2))sc = (radius);
                    		dam *= (1 + (0.5D / (radius/2) * sc));
                    	}
                    	if(!(var6 instanceof EntityItem))
                    	{
                    		DamageSource source = causeBombDamage(this, this);
                    		if(thrower != null)
                    		{
                    			source = causeBombDamage(this, thrower);
                    		}
                    		source.setExplosion();
                    		if(this.canEntityBeSeen(var6))
                    		var6.attackEntityFrom(source, dam);
                    	}
                    }
                }
            }
            
            if(getID() == 0)
            {
            	for(int shards = 0; shards < 32 ; shards ++)
            	{
            		EntityShrapnel shrapnel = new EntityShrapnel(thrower, worldObj, posX, posY+0.5F, posZ);
            		shrapnel.canBePickedUp = 0;
            		shrapnel.ticksExisted = 20;
            		
            		float range = 0.6F;
            		shrapnel.setVelocity((rand.nextDouble()-0.5)*range, (rand.nextDouble())*(range/2), (rand.nextDouble()-0.5)*range);
            		shrapnel.setDamage(5);
            		worldObj.spawnEntityInWorld(shrapnel);
            	}
            }
            
            if(getID() == 1)//FIRE
            {
            	this.startFire(pos);
            }
            this.setDead();
        }
	}
    private void applyEffects(Entity hit) 
    {
    	EntityLivingBase live = null;
    	if(hit instanceof EntityLivingBase)
    	{
    		live = (EntityLivingBase)hit;
    	}
    	
    	if(getID() == 1)//FIRE
    	{
    		hit.setFire(20);
    	}
    	if(getID() == 2 && live != null)//POISON
    	{
    		live.addPotionEffect(new PotionEffect(Potion.poison.id, 100, 0));
    	}
    	if(getID() == 3 && live != null)//CONCUSSION
    	{
    		live.addPotionEffect(new PotionEffect(Potion.confusion.id, 400, 1));
    		live.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 400, 1));
    	}
	}

	private double getRadius()
	{
		return 16.0D;
	}

	private int getDamage() 
	{
		switch(getID())
		{
		case 0://SHRAPNEL
			return 18;
		case 1://FIRE
			return 4;
		case 2://POISON
			return 4;
		case 3://CONCUSSION
			return 4;
		}
		return 4;
	}

	/**
	 * 0 = SHRAPNEL
	 * 1 = FIRE
	 * 2 = POISON
	 * 3 = CONCUSSION
	 */
	private int getID() 
	{
		return dataWatcher.getWatchableObjectInt(4);
	}
	/**
	 * 0 = SHRAPNEL
	 * 1 = FIRE
	 * 2 = POISON
	 * 3 = CONCUSSION
	 */
	public EntityBombThrown setID(int id)
	{
		dataWatcher.updateObject(4, id);
		return this;
	}

	@Override
    public float getResistance()
    {
    	return bounce;
    }

	@Override
	public int getFuseTime() 
	{
		return 30;
	}
	
	@Override
	public void bounce(MovingObjectPosition pos)
	{
		super.bounce(pos);
		if(bounce > 0)
		this.bounce -= 0.1F;
	}

	@Override
	public Icon getIcon() 
	{
		if(getID() >= ItemBombMF.icons.length)
		{
			return ItemBombMF.icons[ItemBombMF.icons.length-1];
		}
		return ItemBombMF.icons[getID()];
	}
	public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }
	
	@Override
	public boolean shouldBreakBlock(Block block) 
	{
		return false;
	}
	
	
	public static DamageSource causeBombDamage(Entity bomb, Entity user)
    {
        return (new EntityDamageSourceBomb(bomb, user)).setProjectile();
    }
	
	public boolean canEntityBeSeen(Entity entity)
    {
        return this.worldObj.clip(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), this.worldObj.getWorldVec3Pool().getVecFromPool(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ)) == null;
    }
	
	private void startFire(MovingObjectPosition pos) 
	{
		if(cfg.disableFirebomb)return;
		
        if (!this.worldObj.isRemote) 
        {
        	if (worldObj.isAirBlock((int) posX, (int) posY, (int) posZ)) 
        	{
                worldObj.setBlock((int) posX, (int) posY, (int) posZ, Block.fire.blockID, 0, 3);
            }
        	
            int r = 3;
            for (int x = -r; x < r; x++) 
            {
                for (int y = -r; y < r; y++)
                {
                    for (int z = -r; z < r; z++) 
                    {
                        if (rand.nextInt(5) == 0 && worldObj.isAirBlock((int) posX + x, (int) posY + y, (int) posZ + z)) 
                        {
                            worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Block.fire.blockID, 0, 3);
                        }
                    }
                }
            }
        }
    }
}
