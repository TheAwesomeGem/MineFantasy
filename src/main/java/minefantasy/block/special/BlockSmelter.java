package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.MineFantasyBase;
import minefantasy.block.BlockChimney;
import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntitySmelter;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSmelter extends BlockContainer
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

    public BlockSmelter(int id)
    {
        super(id, Material.rock);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        float scale = 1.2F;
        float offset = (scale-1)/2;
    }
    
    @Override
    public Icon getIcon(int side, int meta)
    {
    	if(meta >= 2)//Crucible
    	{
    		return Block.stone.getIcon(side, meta);
    	}
    	
    	return BlockListMF.cobbBrick.getIcon(side, 0);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    @Override
    public void addCreativeItems(ArrayList itemList) {
        itemList.add(new ItemStack(this));
    }

    public boolean renderAsNormalBlock() {
        return false;
    }
   

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
        if (meta == 1)
        {
        	return 5;
        }
        
        if (meta == 3)
        {
        	return 5;
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
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return cfg.renderId;
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        TileEntitySmelter tile = (TileEntitySmelter)world.getBlockTileEntity(x, y, z);
        if (tile.isBurning())
        {
            int var6 = tile.direction;
            float var7 = (float)x + 0.5F;
            float var8 = (float)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
            float var9 = (float)z + 0.5F;
            float var10 = 0.52F;
            float var11 = rand.nextFloat() * 0.6F - 0.3F;
        }
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
            TileEntitySmelter tile = (TileEntitySmelter)world.getBlockTileEntity(x, y, z);

            if (tile != null)
            {
            	
            	if(tile.tryTakeItem(player))
            	{
            		return true;
            	}
            	
                player.openGui(MineFantasyBase.instance, 1, world, x, y, z);
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
        return new TileEntitySmelter();
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {
        int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        TileEntitySmelter tile = (TileEntitySmelter) world.getBlockTileEntity(x, y, z);
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
            TileEntitySmelter tile = (TileEntitySmelter)world.getBlockTileEntity(x, y, z);

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
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getAABBPool().getAABB((double)x, (double)y, (double)z, (double)(x+1), (double)(y + 0.9D), (double)(z + 1));
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
    	if(world.isRemote)
    	{
    		return;
    	}
    	
    	TileEntitySmelter tile = (TileEntitySmelter)world.getBlockTileEntity(x, y, z);
    	if(entity == null || tile == null)
    	{
    		return;
    	}
    	boolean bloomery = tile.getTier() == 0;
    	
    	if(!tile.isBurning())
    	{
    		return;
    	}
    	
    	if(entity instanceof EntityItem)
    	{
    		if(!bloomery)
    		{
	    		entity.motionY = 0.20000000298023224D;
	    		entity.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
    		}
    	}
    	else
    	{
    		entity.attackEntityFrom(DamageSource.inFire, 1.0F);
    	}
        
    	if(!bloomery)
    	entity.setFire(30);
    }
}
