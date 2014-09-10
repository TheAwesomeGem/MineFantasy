package minefantasy.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BlockMythicOre extends BlockMedieval{

	public Icon[] types =  new Icon[3];
	public BlockMythicOre(int i)
	{
		super(i, Material.rock);
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return types[meta];
	}

	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	public void registerIcons(IconRegister reg)
    {
		types[0] = reg.registerIcon("MineFantasy:Basic/oreMithril");
		types[1] = reg.registerIcon("MineFantasy:Basic/oreDeepIron");
		types[2] = reg.registerIcon("MineFantasy:Basic/oreDeepIronNether");
    }
	
	
	@Override
    public float getBlockHardness(World world, int x, int y, int z)
	{
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 0)return 8F;//MITHRIL
    			
    	return 3.5F;//DEEP IRON
    }
	
	@Override
    public float getExplosionResistance(Entity explosion, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 0)return 15F;//MITHRIL
    			
    	return 8F;//DEEP IRON
    }
}
