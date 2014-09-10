package minefantasy.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.common.MinecraftForge;

public class BlockCobbleBrick extends BlockMedieval{

	public Icon[] types =  new Icon[6];
	public BlockCobbleBrick(int i)
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
		types[0] = reg.registerIcon("MineFantasy:Basic/Cobblestone_brick");
		types[1] = reg.registerIcon("MineFantasy:Basic/Cobblestone_brick_mossy");
		types[2] = reg.registerIcon("MineFantasy:Basic/Cobblestone_brick_cracked");
		
		types[3] = reg.registerIcon("MineFantasy:Basic/Cobblestone_brick_rough");
		types[4] = reg.registerIcon("MineFantasy:Basic/Cobblestone_brick_rough_mossy");
		types[5] = reg.registerIcon("MineFantasy:Basic/Cobblestone_brick_rough_cracked");
    }
}
