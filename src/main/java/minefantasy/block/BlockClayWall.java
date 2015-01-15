package minefantasy.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.common.MinecraftForge;

public class BlockClayWall extends BlockMedieval{

	public Icon[] types =  new Icon[4];
	public BlockClayWall(int i)
	{
		super(i, Material.clay);
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
		types[0] = reg.registerIcon("MineFantasy:Basic/clayWall");
		types[1] = reg.registerIcon("MineFantasy:Basic/clayWall_cross");
		types[2] = reg.registerIcon("MineFantasy:Basic/clayWall_diagonal1");
		types[3] = reg.registerIcon("MineFantasy:Basic/clayWall_diagonal2");
    }
}
