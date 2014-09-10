package minefantasy.entity;

import java.util.List;

import minefantasy.block.BlockListMF;
import minefantasy.system.cfg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class EntityFirebreath extends Entity {

    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private boolean inGround = false;
    public EntityLiving shootingEntity;
    private int ticksAlive;
    private int ticksInAir = 0;
    public double accelerationX;
    public double accelerationY;
    private int lifespan = 40;
    public double accelerationZ;

    public EntityFirebreath(World world) {
        super(world);
        this.setSize(2.0F, 2.0F);
    }

    protected void entityInit() {
    	dataWatcher.addObject(4, Integer.valueOf(0));
    }
    public EntityFirebreath ice()
    {
    	dataWatcher.updateObject(4, 1);
    	return this;
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    public boolean isInRangeToRenderDist(double dist) {
        double var3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
        var3 *= 64.0D;
        return dist < var3 * var3;
    }

    public EntityFirebreath(World world, double x, double y, double z, double xtar, double ytar, double ztar) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setPosition(x, y, z);
        double var14 = (double) MathHelper.sqrt_double(xtar * xtar + ytar * ytar + ztar * ztar);
        this.accelerationX = xtar / var14 * 0.1D;
        this.accelerationY = ytar / var14 * 0.1D;
        this.accelerationZ = ztar / var14 * 0.1D;
    }

    public EntityFirebreath(World world, EntityLiving attacker, double xtar, double ytar, double ztar) {
        super(world);
        this.shootingEntity = attacker;
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(attacker.posX, attacker.posY, attacker.posZ, attacker.rotationYaw, attacker.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = this.motionY = this.motionZ = 0.0D;
        xtar += this.rand.nextGaussian() * 0.4D;
        ytar += this.rand.nextGaussian() * 0.4D;
        ztar += this.rand.nextGaussian() * 0.4D;
        double var9 = (double) MathHelper.sqrt_double(xtar * xtar + ytar * ytar + ztar * ztar);
        this.accelerationX = xtar / var9 * 0.1D;
        this.accelerationY = ytar / var9 * 0.1D;
        this.accelerationZ = ztar / var9 * 0.1D;
    }
    
    public int getType()
    {
    	return dataWatcher.getWatchableObjectInt(4);
    }
    public EntityFirebreath(World world, EntityLiving attacker, double xtar, double ytar, double ztar, int life) {
        this(world, attacker, xtar, ytar, ztar);
        lifespan = life;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
    	if(this.isWet() && rand.nextInt(40) == 0)
    		setDead();
        int var15_1 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

        if (var15_1 > 0)
        {
            Block.blocksList[var15_1].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB var2 = Block.blocksList[var15_1].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

            if (var2 != null && var2.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))
            {
                setDead();
            }
        }
        if (!this.worldObj.isRemote && (!this.worldObj.blockExists((int) this.posX, (int) this.posY, (int) this.posZ))) {
            this.setDead();
        } else {
            super.onUpdate();
            if(getType() == 0)
            this.setFire(1);
            ++this.ticksAlive;

            if (this.ticksAlive >= lifespan) {
                this.setDead();
            }

            if (this.inGround) {
                int var1 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

                if (var1 == this.inTile) {

                    this.setDead();

                    return;
                }

                this.inGround = false;
                this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
                this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
                this.ticksAlive = 0;
                this.ticksInAir = 0;
            } else {
                ++this.ticksInAir;
            }

            Vec3 var15 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var3 = this.worldObj.clip(var15, var2);
            var15 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (var3 != null) {
                var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
            }

            Entity var4 = null;
            List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double var6 = 0.0D;

            for (int var8 = 0; var8 < var5.size(); ++var8) {
                Entity var9 = (Entity) var5.get(var8);

                if (var9.canBeCollidedWith() && (!var9.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
                    float var10 = 0.3F;
                    AxisAlignedBB var11 = var9.boundingBox.expand((double) var10, (double) var10, (double) var10);
                    MovingObjectPosition var12 = var11.calculateIntercept(var15, var2);

                    if (var12 != null) {
                        double var13 = var15.distanceTo(var12.hitVec);

                        if (var13 < var6 || var6 == 0.0D) {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }

            boolean isRider;
            if (canShoot(var4)) {
                var3 = new MovingObjectPosition(var4);
                var4.setFire(1);
                
                if (rand.nextInt(3) == 0 && (!var4.isImmuneToFire() || getType() != 0)) 
                {
                	DamageSource source = getDamageSource();
                	var4.attackEntityFrom(source, getDamage());
                    applyEffects(var4);
                }
            }

            if (var3 != null) {
                this.hitEntity(var3);
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            float var16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float) (Math.atan2(this.motionY, (double) var16) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float var17 = 0.95F;

            if (this.isInWater()) {
                for (int var19 = 0; var19 < 4; ++var19) {
                    float var18 = 0.25F;
                    this.worldObj.spawnParticle("smoke", this.posX - this.motionX * (double) var18, this.posY - this.motionY * (double) var18, this.posZ - this.motionZ * (double) var18, this.motionX, this.motionY, this.motionZ);
                }

                var17 = 0.8F;
            }

            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            this.motionX *= (double) var17;
            this.motionY *= (double) var17;
            this.motionZ *= (double) var17;
            this.worldObj.spawnParticle("snoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
            this.setPosition(this.posX, this.posY, this.posZ);
        }
        AxisAlignedBB bb = this.boundingBox.expand(1D, 1D, 1D);
        if(!worldObj.isRemote)
        this.destroyBlocksInAABB(bb);
    }

    private DamageSource getDamageSource() 
    {
    	if(shootingEntity != null)
    	{
    		return new DamageSourceFirebreath(this, shootingEntity);
    	}
		return new DamageSourceFirebreath(this, this);
	}

	private boolean canShoot(Entity en) {
    	if(en == null)
    		return false;
    	
    	if(shootingEntity != null)
    	if(shootingEntity.riddenByEntity != null)
    	{
    		if(shootingEntity.riddenByEntity == en)
    			return false;
    	}
    	return true;
	}

	private void applyEffects(Entity tar) {
		if(tar instanceof EntityLiving)
		{
			EntityLiving live = (EntityLiving)tar;
			if(getType() == 1)
			{
				//FROST EFFECT
				int id = Potion.moveSlowdown.id;
				int d = 20;
				int l = 0;
				if(live.getActivePotionEffect(Potion.moveSlowdown) != null)
				{
						
					id = live.getActivePotionEffect(Potion.moveSlowdown).getPotionID();
					d = live.getActivePotionEffect(Potion.moveSlowdown).getDuration();
					l = live.getActivePotionEffect(Potion.moveSlowdown).getAmplifier();
					
					if(d < 1200)
					d += 30;
					if(rand.nextInt(10) == 0 && l < 5)
					{
						l ++;
					}
				}
				live.addPotionEffect(new PotionEffect(id, d, l));
			}
		}
	}

	protected void hitEntity(MovingObjectPosition pos) {
        if (!this.worldObj.isRemote) {
        	AxisAlignedBB var3 = this.boundingBox.expand(4.0D, 2.0D, 4.0D);
            List var4 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, var3);

            if (var4 != null && !var4.isEmpty()) {
   
            }
            if(rand.nextInt(60) == 0 && cfg.dragonGrief)
            {
            	int id = 0;
            	if(getType() == 0) id = Block.fire.blockID;
            	if(getType() == 1) id = Block.snow.blockID;
            	
            placeFire((int)posX, (int)posY, (int)posZ, id);
            placeFire((int)posX, (int)posY-1, (int)posZ, id);
            
            if(getType() == 3)
            {
            	freezeBlock((int)posX, (int)posY, (int)posZ);
            }
            }
            if (pos.entityHit != null && pos.entityHit.attackEntityFrom(getDamageSource(), getDamage())) 
            {
                applyEffects(pos.entityHit);
            }
            if (pos.entityHit != null && (pos.entityHit instanceof EntityDragonSmall || pos.entityHit instanceof EntityFirebreath))
            {
                ;
            }
            else
            setDead();
        }
    }

    private void placeFire(int x, int y, int z, int id) {
    	
    	if(worldObj.isAirBlock(x, y, z) || worldObj.getBlockMaterial(x, y, z) == Material.snow)
    	{
    		placeBlock(x, y, z, id);
    	}
	}

	private int getDamage() {
		switch(getType())
		{
		case 0:
			return 4;
		case 1:
			return 3;
		}
		return 4;
	}

	private void freezeBlock(int x, int y, int z) {
		int id;
		int repl = worldObj.getBlockId(x, y, z);
		
		if(repl == Block.waterStill.blockID)id = Block.ice.blockID;
		if(repl == Block.lavaStill.blockID)id = Block.obsidian.blockID;
		if(repl == Block.lavaMoving.blockID)id = Block.obsidian.blockID;
		if(worldObj.getBlockMaterial(x, y, z) == Material.leaves)id = BlockListMF.ice.blockID;
		if(worldObj.getBlockMaterial(x, y, z) == Material.plants)id = BlockListMF.ice.blockID;
	}

	private void placeBlock(int x, int y, int z, int id) {
		if(!worldObj.isAirBlock(x, y-1, z))
			if(worldObj.isBlockNormalCube(x, y-1, z) || Block.blocksList[id].isOpaqueCube())
				worldObj.setBlock(x, y, z, id, 0, 3);
	}

	/**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tag) {
        tag.setShort("xTile", (short) this.xTile);
        tag.setShort("yTile", (short) this.yTile);
        tag.setShort("zTile", (short) this.zTile);
        tag.setByte("inTile", (byte) this.inTile);
        tag.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        tag.setInteger("Type", getType());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tag) {
        this.xTile = tag.getShort("xTile");
        this.yTile = tag.getShort("yTile");
        this.zTile = tag.getShort("zTile");
        this.inTile = tag.getByte("inTile") & 255;
        this.inGround = tag.getByte("inGround") == 1;
        dataWatcher.updateObject(4, tag.getInteger("Type"));
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return true;
    }

    public float getCollisionBorderSize() {
        return 1.0F;
    }

    public float getShadowSize() {
        return 0.0F;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float amount) {
        return 1.0F;
    }

    public int getBrightnessForRender(float amount) {
        return 15728880;
    }
    
    private boolean destroyBlocksInAABB(AxisAlignedBB box)
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
                        if (var13 == Material.glass)
                        {
                            var9 = true;
                            this.worldObj.setBlockToAir(var10, var11, var12);
                        }
                        else if (var13 == Material.tnt)
                        {
                            var9 = true;
                            this.worldObj.setBlockToAir(var10, var11, var12);
                            this.worldObj.createExplosion(this, var10, var11, var12, 4.0F, true);
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
            for(int a = 0 ; a < 1+rand.nextInt(4); a ++)
                this.worldObj.playSoundAtEntity(this, "random.glass", 1F, 1F);
        }

        return var8;
    }
    
    public class DamageSourceFirebreath extends EntityDamageSourceIndirect
    {

    	public DamageSourceFirebreath(EntityFirebreath breath, Entity shooter) 
    	{
    		super("indirectMagic", breath, shooter);
    		this.setFireDamage();
    	}
    	
    }
}

