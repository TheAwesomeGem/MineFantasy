package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityDogBowl;
import minefantasy.item.ItemListMF;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class BlockDogBowl extends BlockContainer {

    private Random rand = new Random();
    
    public BlockDogBowl(int i) {
        super(i, Material.wood);
        setBlockBounds(0.2F, 0, 0.2F, 0.8F, 0.5F, 0.8F);
        this.setCreativeTab(ItemListMF.tabPets);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void addCreativeItems(ArrayList itemList) 
    {
    	for(int m = 0; m < 3; m ++)
        itemList.add(new ItemStack(this, 1, m));
    }

    @Override
    public Icon getIcon(int side, int meta) 
    {
    	if(meta == 1)
    	{
    		return BlockListMF.storage.getIcon(0, 7);
    	}
    	if(meta == 2)
    	{
    		return BlockListMF.storage.getIcon(0, 0);
    	}
        return Block.planks.getIcon(0, 0);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return cfg.renderId;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World w) {
        return new TileEntityDogBowl();
    }

    @Override
    public int damageDropped(int meta)
    {
    	return meta;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i1, float f1, float f2, float f3) {
        ItemStack itemstack = player.inventory.getCurrentItem();
        TileEntityDogBowl tile = (TileEntityDogBowl) world.getBlockTileEntity(x, y, z);
        
        if (!world.isRemote && tile != null && itemstack != null && itemstack.getItem() instanceof ItemFood)
        {
            ItemFood food = (ItemFood) itemstack.getItem();
            if (food.isWolfsFavoriteMeat() && tile.canPutFood()) 
            {
                tile.addFood(food.getHealAmount());
                if(!player.capabilities.isCreativeMode)
                itemstack.stackSize--;
                player.swingItem();
                return true;
            }
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg){}
}
