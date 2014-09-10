package minefantasy.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.api.aesthetic.IChimney;
import minefantasy.block.tileentity.TileEntityBFurnace;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockChimney extends Block implements IChimney{

	private Icon[] icons = new Icon[14];
	private String[] names = new String[]{"ChimneyIron_Side", "ChimneyIron_Top", "ChimneySteel_Side", "ChimneySteel_Top", "ChimneyCobblestone_Side", "ChimneyCobblestone_Top", "ChimneyBrick_Side", "ChimneyBrick_Top", "ChimneyBronze_Side", "ChimneyBronze_Top", "ChimneyDeepIron_Side", "ChimneyDeepIron_Top"};

	public BlockChimney(int id) {
		super(id, Material.rock);
		setCreativeTab(CreativeTabs.tabDecorations);
		float f = 3F/16F;
		setBlockBounds(f, 0F, f, 1-f, 1F, 1-f);
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public Icon getIcon(int side, int metadata)
    {
		int ic = metadata*2;
    	if(side <= 1)ic ++;
    	
    	return icons[ic];
    }
	
	@Override
	public boolean puffSmoke(World world, int x, int y, int z, float num, float speedX, float speedY)
	{
		IChimney chimney = (IChimney)Block.blocksList[world.getBlockId(x, y, z)];
		if(chimney == null)
		{
			return false;
		}
		
		Random rand = new Random();
		
		if(Block.blocksList[world.getBlockId(x, y+1, z)] instanceof IChimney)
		{
			IChimney chimney2 = (IChimney)Block.blocksList[world.getBlockId(x, y+1, z)];
			return chimney2.puffSmoke(world, x, y+1, z, num, speedX, speedY);
		}
		
		for(int a = 0; a < 30*num ; a ++)
        {
        	if(!world.isBlockSolidOnSide(x, y+1, z, ForgeDirection.DOWN))
        	world.spawnParticle("largesmoke", x+0.5F, y+1, z+0.5F, (rand.nextFloat()-0.5F)/6*speedX, 0.065F*speedY, (rand.nextFloat()-0.5F)/6*speedX);
        }
		
		return true;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	@SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister reg)
    {
		icons = new Icon[names.length];
        for(int a = 0; a < names.length; a++)
        {
        	icons[a] = reg.registerIcon("minefantasy:Furn/" + names [a]);
        }
    }

}
