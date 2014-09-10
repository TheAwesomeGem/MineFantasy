package minefantasy.entity.dispense;

import minefantasy.entity.EntityArrowMF;
import minefantasy.entity.EntityBombThrown;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DispenseBombMF extends BehaviorProjectileDispense
{

	public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
    {
		float y = 1.1F;
        World var3 = source.getWorld();
        IPosition var4 = BlockDispenser.getIPositionFromBlockSource(source);
        EnumFacing var5 = BlockDispenser.getFacing(source.getBlockMetadata());
        
        IProjectile var6 = this.getProjectileEntity(var3, var4, stack.getItemDamage());
        if(var6 != null)
        {
        	var6.setThrowableHeading((double)var5.getFrontOffsetX(), 0.10000000149011612D*y, (double)var5.getFrontOffsetY(), this.func_82500_b(), this.func_82498_a());
        	var3.spawnEntityInWorld((Entity)var6);
        	stack.splitStack(1);
        }
        return stack;
    }
	
    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected IProjectile getProjectileEntity(World world, IPosition pos, int type)
    {
    	EntityBombThrown bomb = new EntityBombThrown(world, pos.getX(), pos.getY(), pos.getZ(), 20).setID(type);
    	return bomb;
    }

	@Override
	protected IProjectile getProjectileEntity(World var1, IPosition var2) {
		return getProjectileEntity(var1, var2, 0);
	}
}
