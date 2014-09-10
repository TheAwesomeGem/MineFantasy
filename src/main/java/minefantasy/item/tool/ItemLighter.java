package minefantasy.item.tool;

import minefantasy.api.forge.ILighter;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLighter extends Item implements ILighter
{
	private double efficiency;
	
    public ItemLighter(int id, int uses, double successRate)
    {
        super(id);
        this.maxStackSize = 1;
        this.setMaxDamage(uses);
        efficiency = successRate;
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float f, float f1, float f2)
    {
        if (side == 0)
        {
            --y;
        }

        if (side == 1)
        {
            ++y;
        }

        if (side == 2)
        {
            --z;
        }

        if (side == 3)
        {
            ++z;
        }

        if (side == 4)
        {
            --x;
        }

        if (side == 5)
        {
            ++x;
        }

        if (!player.canPlayerEdit(x, y, z, side, item))
        {
            return false;
        }
        else
        {
            int i1 = world.getBlockId(x, y, z);

            if (i1 == 0)
            {
            	world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
            	world.spawnParticle("flame", (double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, 0F, 0F, 0F);
            	if(player.getRNG().nextDouble() < efficiency)
                {
                	if(!world.isRemote)
                	{
                		world.setBlock(x, y, z, Block.fire.blockID);
                		item.damageItem(1, player);
                	}
                }
            }

            return true;
        }
    }
    @Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }

	@Override
	public double getChance() 
	{
		return efficiency;
	}

	@Override
	public boolean canLight() 
	{
		return true;
	}
}
