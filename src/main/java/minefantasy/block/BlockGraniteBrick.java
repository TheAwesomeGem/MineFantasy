package minefantasy.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockGraniteBrick extends BlockMedieval{

	public Icon[] types =  new Icon[3];
	public BlockGraniteBrick(int i) {
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
		types[0] = reg.registerIcon("MineFantasy:Basic/Granite_brick");
		types[1] = reg.registerIcon("MineFantasy:Basic/Granite_brick_mossy");
		types[2] = reg.registerIcon("MineFantasy:Basic/Granite_brick_cracked");
    }
	@Override
    public Block setUnlocalizedName(String name)
    {
    	this.setTextureName("minefantasy:Basic/" + name);
    	return super.setUnlocalizedName(name);
    }
}
