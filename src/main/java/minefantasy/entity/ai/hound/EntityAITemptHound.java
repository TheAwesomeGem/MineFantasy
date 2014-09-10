package minefantasy.entity.ai.hound;

import minefantasy.entity.EntityHound;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntityAITemptHound extends EntityAIBase
{
    /** The entity using this AI that is tempted by the player. */
    private EntityHound theDog;
    private float moveSpeed;
    private double prevPosX;
    private double prevPosY;
    private double prevPosZ;
    private double prevRotPitch;
    private double prevPosYaw;

    /** The player that is tempting the entity that is using this AI. */
    private EntityPlayer temptingPlayer;

    /**
     * A counter that is decremented each time the shouldExecute method is called. The shouldExecute method will always
     * return false if delayTemptCounter is greater than 0.
     */
    private int delayTemptCounter = 0;
    private boolean hasStartedTask;

    /**
     * Whether the entity using this AI will be scared by the tempter's sudden movement.
     */
    private boolean scaredByPlayerMovement;
    private boolean avoidWater;

    public EntityAITemptHound(EntityHound hound, float speed, boolean scare)
    {
        this.theDog = hound;
        this.moveSpeed = speed;
        this.scaredByPlayerMovement = scare;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if(!theDog.canTempt())
    	{
    		return false;
    	}
        if (this.delayTemptCounter > 0)
        {
            --this.delayTemptCounter;
            return false;
        }
        else
        {
            this.temptingPlayer = this.theDog.worldObj.getClosestPlayerToEntity(this.theDog, 10.0D);

            if (this.temptingPlayer == null)
            {
                return false;
            }
            else
            {
                ItemStack itemstack = this.temptingPlayer.getCurrentEquippedItem();
                return itemstack == null ? false : theDog.isTempting(itemstack);
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (this.scaredByPlayerMovement)
        {
            if (this.theDog.getDistanceSqToEntity(this.temptingPlayer) < 36.0D)
            {
                if (this.temptingPlayer.getDistanceSq(this.prevPosX, this.prevPosY, this.prevPosZ) > 0.010000000000000002D)
                {
                    return false;
                }

                if (Math.abs((double)this.temptingPlayer.rotationPitch - this.prevRotPitch) > 5.0D || Math.abs((double)this.temptingPlayer.rotationYaw - this.prevPosYaw) > 5.0D)
                {
                    return false;
                }
            }
            else
            {
                this.prevPosX = this.temptingPlayer.posX;
                this.prevPosY = this.temptingPlayer.posY;
                this.prevPosZ = this.temptingPlayer.posZ;
            }

            this.prevRotPitch = (double)this.temptingPlayer.rotationPitch;
            this.prevPosYaw = (double)this.temptingPlayer.rotationYaw;
        }

        return this.shouldExecute();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.prevPosX = this.temptingPlayer.posX;
        this.prevPosY = this.temptingPlayer.posY;
        this.prevPosZ = this.temptingPlayer.posZ;
        this.hasStartedTask = true;
        this.avoidWater = this.theDog.getNavigator().getAvoidsWater();
        this.theDog.getNavigator().setAvoidsWater(false);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.temptingPlayer = null;
        this.theDog.getNavigator().clearPathEntity();
        this.delayTemptCounter = 100;
        this.hasStartedTask = false;
        this.theDog.getNavigator().setAvoidsWater(this.avoidWater);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.theDog.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, 30.0F, (float)this.theDog.getVerticalFaceSpeed());

        if (this.theDog.getDistanceSqToEntity(this.temptingPlayer) < 6.25D)
        {
            this.theDog.getNavigator().clearPathEntity();
        }
        else
        {
            this.theDog.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.moveSpeed);
        }
    }

    public boolean func_75277_f()
    {
        return this.hasStartedTask;
    }
}
