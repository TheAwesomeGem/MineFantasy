package minefantasy.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockStorageMF extends BlockMedieval
{
	private Icon[] type = new Icon[9];

	public BlockStorageMF(int i) {
		super(i, Material.iron);
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return type[meta];
	}
	
	@Override
    public float getBlockHardness(World world, int x, int y, int z)
    {
    	float f = Block.blockIron.blockHardness;
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 0)f *= 1.5F;//STEEL
    	if(meta == 1)f *= 0.5F;//COPPER
    	if(meta == 2)f *= 0.6F;//TIN
    	if(meta == 3)f *= 0.8F;//BRONZE
    	if(meta == 4)f *= 2.0F;//MITHRIL
    	if(meta == 5)f *= 0.5F;//SILVER
    	if(meta == 6)f *= 0.5F;
    	if(meta == 7)f *= 1.0F;//IRON
    	
    	return f;
    	
    }
	
	@Override
    public float getExplosionResistance(Entity explosion, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
    	float f = Block.blockIron.blockResistance;
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 0)f *= 1.5F;//STEEL
    	if(meta == 1)f *= 0.5F;//COPPER
    	if(meta == 2)f *= 0.6F;//TIN
    	if(meta == 3)f *= 0.8F;//BRONZE
    	if(meta == 4)f *= 2.0F;//MITHRIL
    	if(meta == 5)f *= 0.5F;//SILVER
    	if(meta == 6)f *= 0.5F;
    	if(meta == 7)f *= 1.0F;//IRON
    	
    	return f;
    	
    }
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	public void registerIcons(IconRegister reg)
    {
		type[0] = reg.registerIcon("MineFantasy:Basic/MF_Storage_Steel");
		type[1] = reg.registerIcon("MineFantasy:Basic/MF_Storage_Copper");
		type[2] = reg.registerIcon("MineFantasy:Basic/MF_Storage_Tin");
		type[3] = reg.registerIcon("MineFantasy:Basic/MF_Storage_Bronze");
		type[4] = reg.registerIcon("MineFantasy:Basic/MF_Storage_Mithril");
		type[5] = reg.registerIcon("MineFantasy:Basic/MF_Storage_Silver");
		type[6] = reg.registerIcon("MineFantasy:Basic/MF_Storage_Silver");//UNUSED
		type[7] = reg.registerIcon("MineFantasy:Basic/MF_Storage_Wrought");
		type[8] = reg.registerIcon("MineFantasy:Basic/MF_Storage_DeepIron");
    }
	
	@Override
	public boolean isBeaconBase(World worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
    {
        return true;
    }

}
