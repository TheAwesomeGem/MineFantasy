package minefantasy.entity.dispense;

import minefantasy.entity.EntityBoltMF;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DispenseBoltMF extends BehaviorProjectileDispense
{

	public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
    {
		float y = 1.1F;
        World var3 = source.getWorld();
        IPosition var4 = BlockDispenser.getIPositionFromBlockSource(source);
        EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
        
        IProjectile iprojectile = this.getProjectileEntity(var3, var4, stack.getItemDamage());
        if(iprojectile != null)
        {
        	iprojectile.setThrowableHeading((double)enumfacing.getFrontOffsetX(), (double)((float)enumfacing.getFrontOffsetY() + 0.1F), (double)enumfacing.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());
        	var3.spawnEntityInWorld((Entity)iprojectile);
        	stack.splitStack(1);
        }
        return stack;
    }
	
    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected IProjectile getProjectileEntity(World world, IPosition pos, int meta)
    {
    	EntityBoltMF bolt = new EntityBoltMF(world, pos.getX(), pos.getY(), pos.getZ(), meta);
    	bolt.canBePickedUp = 1;
    	return bolt;
    }

	@Override
	protected IProjectile getProjectileEntity(World var1, IPosition var2)
	{
		return getProjectileEntity(var1, var2, 0);
	}
}
