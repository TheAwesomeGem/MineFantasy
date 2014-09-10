package minefantasy.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.item.ItemListMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BlockUtilOre extends BlockMedieval{

	public Icon[] types =  new Icon[3];
	public BlockUtilOre(int i)
	{
		super(i, Material.rock);
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		if(meta > types.length-1)
		{
			meta = types.length-1;
		}
		return types[meta];
	}
	
	public void registerIcons(IconRegister reg)
    {
		types[0] = reg.registerIcon("MineFantasy:Basic/oreSilver");
		types[1] = reg.registerIcon("MineFantasy:Basic/oreNitre");
		types[2] = reg.registerIcon("MineFantasy:Basic/oreSulfur");
    }
	
	
	@Override
    public float getBlockHardness(World world, int x, int y, int z)
	{
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 1)return 2F;//Nitre
    	if(meta == 2)return 3F;//Sulfur
    			
    	return 2F;//SILVER
    }
	
	@Override
	public int idDropped(int meta, Random rand, int fortune)
	{
		if(meta == 0)
		{
			return blockID;
		}
		return ItemListMF.misc.itemID;
	}
	
	@Override
	public int damageDropped(int m)
	{
		if(m == 1)
		{
			return ItemListMF.nitre;
		}
		if(m == 2)
		{
			return ItemListMF.sulfur;
		}
		return super.damageDropped(m);
	}
	
	@Override
    public int getExpDrop(World world, int meta, int fortune)
    {
        if (this.idDropped(meta, world.rand, fortune) != this.blockID)
        {
            int j1 = 0;

            if (meta == ItemListMF.nitre)
            {
                j1 = MathHelper.getRandomIntegerInRange(world.rand, 0, 5);
            }
            if (meta == ItemListMF.sulfur)
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
	
	@Override
    public float getExplosionResistance(Entity explosion, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 1)return 1F;//Nitre
    	if(meta == 2)return 0.5F;//Sulfur
    			
    	return 5F;//SILVER
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
