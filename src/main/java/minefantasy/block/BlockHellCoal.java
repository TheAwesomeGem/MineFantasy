package minefantasy.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.item.ItemListMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BlockHellCoal extends BlockMedieval{

	public Icon[] types =  new Icon[2];
	public BlockHellCoal(int i)
	{
		super(i, Material.rock);
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return types[meta];
	}

	public void registerIcons(IconRegister reg)
    {
		types[0] = reg.registerIcon("MineFantasy:Basic/oreInferno");
		types[1] = reg.registerIcon("MineFantasy:Basic/oreHellfire");
    }
	
	
	@Override
    public float getBlockHardness(World world, int x, int y, int z)
	{
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 1)return 5.0F;//HELLFIRE
    			
    	return 2.0F;//INFERNO
    }
	
	@Override
    public float getExplosionResistance(Entity explosion, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 1)return 5000F;//HELLFIRE
		
    	return 2.0F;//INFERNO
    }
	
	
	@Override
	public int idDropped(int meta, Random rand, int fortune)
	{
		return ItemListMF.misc.itemID;
	}
	
	@Override
	public int damageDropped(int m)
	{
		if(m == 1)
		{
			return ItemListMF.HellCoal;
		}
		return ItemListMF.infernoCoal;
	}
	
	@Override
    public int getExpDrop(World world, int meta, int fortune)
    {
        if (this.idDropped(meta, world.rand, fortune) != this.blockID)
        {
            int j1 = 0;

            if (meta == ItemListMF.infernoCoal)
            {
                j1 = MathHelper.getRandomIntegerInRange(world.rand, 0, 5);
            }
            if (meta == ItemListMF.HellCoal)
            {
                j1 = MathHelper.getRandomIntegerInRange(world.rand, 0, 6);
            }
            return j1;
        }

        return 0;
    }
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (fortune > 0 && this.blockID != this.idDropped(0, random, fortune))
        {
            int j = random.nextInt(fortune + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.quantityDropped(random) * (j + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
    }
	
	@SideOnly(Side.CLIENT)
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
	 @Override
   	 public int idPicked(World world, int x, int y, int z)
	 {
		 return this.blockID;
	 }

    /**
     * Get the block's damage value (for use with pick block).
     */
	 @Override
	 public int getDamageValue(World world, int x, int y, int z)
	 {
        return world.getBlockMetadata(x, y, z);
	 }
}
