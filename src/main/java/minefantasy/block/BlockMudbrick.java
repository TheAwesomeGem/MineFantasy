package minefantasy.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.common.MinecraftForge;

public class BlockMudbrick extends BlockMedieval{

	public Icon[] types =  new Icon[2];
	public BlockMudbrick(int i)
	{
		super(i, Material.ground);
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
		types[0] = reg.registerIcon("MineFantasy:Basic/mudBrick");
		types[1] = reg.registerIcon("MineFantasy:Basic/mudBrick_rough");
    }
}
