package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.MineFantasyBase;
import minefantasy.block.BlockChimney;
import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityFurnaceMF;
import minefantasy.block.tileentity.TileEntitySmelter;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFurnaceMF extends BlockContainer
{
    /**
     * Is the random generator used by furnace to drop the inventory contents in random directions.
     */
    private Random rand = new Random();

    /**
     * This flag is used to prevent the furnace inventory to be dropped upon block removal, is used internally when the
     * furnace block changes from idle to active and vice-versa.
     */
    private static boolean keepFurnaceInventory = false;

    public BlockFurnaceMF(int id)
    {
        super(id, Material.iron);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public Icon getIcon(int side, int meta)
    {
		int type = (int)Math.floor(meta/2);
    	if(type == 1)
    	{
    		return BlockListMF.storage.getIcon(side, 3);
    	}
    	if(type == 2)
    	{
    		return BlockListMF.storage.getIcon(side, 7);
    	}
    	if(type == 3)
    	{
    		return BlockListMF.storage.getIcon(side, 0);
    	}
    	if(type == 4)
    	{
    		return BlockListMF.storage.getIcon(side, 8);
    	}
    	return Block.furnaceIdle.getIcon(side, 0);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }
   

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
        if(meta % 2 == 1)
        {
        	return 10;
        }
    	return super.getLightValue(world, x, y, z);
    }
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return cfg.renderId;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityFurnaceMF tile = (TileEntityFurnaceMF)world.getBlockTileEntity(x, y, z);

            ItemStack item = player.getHeldItem();
            
            if (tile != null)
            {
                player.openGui(MineFantasyBase.instance, 12, world, x, y, z);
            }

            return true;
        }
    }

    /**

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity createNewTileEntity(World w)
    {
        return new TileEntityFurnaceMF();
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {
        int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        TileEntityFurnaceMF tile = (TileEntityFurnaceMF) world.getBlockTileEntity(x, y, z);
        tile.direction = dir;
    }

    /**
     * Called whenever the block is removed.
     */
    @Override
    public void breakBlock(World world, int x, int y, int z, int i1, int i2)
    {
        if (!keepFurnaceInventory)
        {
            TileEntityFurnaceMF tile = (TileEntityFurnaceMF)world.getBlockTileEntity(x, y, z);

            if (tile != null)
            {
                for (int var6 = 0; var6 < tile.getSizeInventory(); ++var6)
                {
                    ItemStack var7 = tile.getStackInSlot(var6);

                    if (var7 != null)
                    {
                        float var8 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float var9 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float var10 = this.rand.nextFloat() * 0.8F + 0.1F;

                        while (var7.stackSize > 0)
                        {
                            int var11 = this.rand.nextInt(21) + 10;

                            if (var11 > var7.stackSize)
                            {
                                var11 = var7.stackSize;
                            }

                            var7.stackSize -= var11;
                            EntityItem var12 = new EntityItem(world, (double)((float)x + var8), (double)((float)y + var9), (double)((float)z + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));

                            if (var7.hasTagCompound())
                            {
                            	 var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
                            }

                            float var13 = 0.05F;
                            var12.motionX = (double)((float)this.rand.nextGaussian() * var13);
                            var12.motionY = (double)((float)this.rand.nextGaussian() * var13 + 0.2F);
                            var12.motionZ = (double)((float)this.rand.nextGaussian() * var13);
                            world.spawnEntityInWorld(var12);
                        }
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, i1, i2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg){}
    
    @Override
    public int damageDropped(int meta)
    {
    	return (int)Math.floor(meta/2);
    }
}
