package minefantasy.system;

import java.util.EnumSet;
import java.util.Random;


import minefantasy.MineFantasyBase;
import minefantasy.item.ItemBloom;
import minefantasy.item.ItemHotItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ArmourTickHandlerMF implements ITickHandler {

	Random rand = new Random();
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if (cfg.disableWeight || !type.contains(TickType.PLAYER)) return;
        
        EntityLivingBase user = (EntityLivingBase) tickData[0];
        
        if (user != null)
        {
        	if(user.onGround)
        	{
        		float speedMod = CombatManager.getMovementSpeedModifier(user);
        		if(!(cfg.disableWeight && speedMod < 1.0F))
        		{
        			float speed = speedMod;
        			user.motionX *= speed;
        			user.motionZ *= speed;
        		}
        	}
        }
        
        int sprint = CombatManager.getAntisprintArmours(user);
        if(!cfg.disableWeight && sprint > 0 && user.isSprinting())
        {
        	if(user instanceof EntityPlayer)
        	{
        		float exhaust = MineFantasyBase.scaleExhaustion(0.01F*sprint);
        		((EntityPlayer)user).addExhaustion(exhaust);
        	}
        	user.moveStrafing += (user.getRNG().nextFloat()*10F) -5F;
        }
        double speed = Math.hypot(user.motionX, user.motionZ);
        if(speed > 0.01D && CombatManager.getAntisprintArmours(user) > 0)
        {
        	float volume = 0.05F * CombatManager.getAntisprintArmours(user);
        	if(rand.nextInt(20) == 0)
        	{
        		user.playSound("mob.irongolem.throw", volume, 1.0F);
        	}
        }
        
        
        if(user.isInWater() && !user.worldObj.isBlockSolidOnSide((int)Math.round(user.posX), (int)user.posY-2, (int)Math.round(user.posZ), ForgeDirection.UP))
		{
			float spd = CombatManager.getMovementSpeedModifier(user);
			if(spd < 1.0F)
			{
				float sink = 0.01F * (1F/spd);
				user.motionX *= spd;
				user.motionZ *= spd;
			}
		}
	}

	@Override
    public EnumSet<TickType> ticks()
    {
            return EnumSet.of(TickType.PLAYER);
    }
    
    @Override
    public String getLabel()
    {
            return "Armour Tick Handler";
    }
    
  
}
