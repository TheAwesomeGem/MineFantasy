package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.MineFantasyBase;
import minefantasy.api.anvil.ITongs;
import minefantasy.api.forge.TongsHelper;
import minefantasy.block.tileentity.TileEntitySmelter;
import minefantasy.block.tileentity.TileEntityTripHammer;
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
public class BlockTripHammer extends BlockContainer {

    private Random rand = new Random();

    public BlockTripHammer(int i) {
        super(i, Material.iron);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean isOpaqueCube() {
        return false;
    }
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return cfg.renderId;
    }

    @Override
    public Icon getIcon(int side, int meta)
    {
    	return Block.pistonBase.getIcon(2, 0);
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

        TileEntityTripHammer hammer = (TileEntityTripHammer)world.getBlockTileEntity(x, y, z);
    	hammer.direction = (byte)dir;
    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2)
    {
    	TileEntityTripHammer hammer = (TileEntityTripHammer)world.getBlockTileEntity(x, y, z);
    	if(hammer != null)
    	{
    		if(hammer.getType() == 0)
    		{
    			if(!interact(hammer, world, player) && !world.isRemote)
    			{
    				player.openGui(MineFantasyBase.instance, 9, world, x, y, z);
    			}
    		}
    		else
    		{
    			hammer.force = 10;
    		}
    	}
    	return true;
    }
    
	private boolean interact(TileEntityTripHammer tile, World world, EntityPlayer player) 
	{
		ItemStack tongs = player.getHeldItem();
		
		if(tongs != null && tongs.getItem() instanceof ITongs)
    	{
			if(world.isRemote)
			{
				return true;
			}
			
    		ItemStack holding = TongsHelper.getHeldItem(tongs);
    		if(holding == null)
    		{
    			ItemStack inHammer = tile.getStackInSlot(0);
    			if(inHammer != null)
    			{
					if(TongsHelper.trySetHeldItem(tongs, inHammer))
					{
						tile.decrStackSize(0, 1);
						tile.onInventoryChanged();
						return true;
					}
    			}
    		}
    		else
    		{
    			ItemStack out = tile.getStackInSlot(0);
    			if(out == null)
    			{
					tile.setInventorySlotContents(0, holding);
					TongsHelper.clearHeldItem(tongs, player);
					return true;
    			}
    		}
    		return true;
    	}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityTripHammer();
	}

	@Override
    public void breakBlock(World world, int x, int y, int z, int i1, int i2)
    {
        TileEntityTripHammer tile = (TileEntityTripHammer)world.getBlockTileEntity(x, y, z);

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

        super.breakBlock(world, x, y, z, i1, i2);
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg){}
    
    @Override
    public int damageDropped(int meta)
    {
    	return (int)Math.floor(meta);
    }
}
