package minefantasy.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockPlanksMF extends BlockMedieval{

	private Icon[] type = new Icon[2];

	public BlockPlanksMF(int i) {
		super(i, Material.wood);
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		return type[meta];
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	@Override
    public float getBlockHardness(World world, int x, int y, int z)
    {
    	float f = super.getBlockHardness(world, x, y, z);
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 1)f *= 2;
    	
    	return f;
    	
    }
	
	public void registerIcons(IconRegister reg)
    {
		type[0] = reg.registerIcon("MineFantasy:Tree/planksIronbark");
		type[1] = reg.registerIcon("MineFantasy:Tree/planksEbony");
    }
	@Override
    public Block setUnlocalizedName(String name)
    {
    	this.setTextureName("minefantasy:Tree/" + name);
    	return super.setUnlocalizedName(name);
    }
}
