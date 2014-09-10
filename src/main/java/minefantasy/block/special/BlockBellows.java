package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityBellows;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class BlockBellows extends BlockContainer {

    private Random rand = new Random();

    public BlockBellows(int i) {
        super(i, Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean isOpaqueCube() {
        return false;
    }
    @Override
    public Icon getIcon(int side, int meta)
    {
    	return Block.planks.getIcon(side, 0);
    }
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return cfg.renderId;
    }

    @Override
    public void addCreativeItems(ArrayList itemList) {
        itemList.add(new ItemStack(this));
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock() {
        return false;
    }
   
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {
        int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        TileEntityBellows bellows = (TileEntityBellows)world.getBlockTileEntity(x, y, z);
    	bellows.direction = (byte)dir;
    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2)
    {
    	TileEntityBellows bellows = (TileEntityBellows)world.getBlockTileEntity(x, y, z);
    	if(bellows != null)
    	{
    		bellows.interact(player, 2F);
    	}
    	return true;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityBellows();
	}
	

	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg){}
}
