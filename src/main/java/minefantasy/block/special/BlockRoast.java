package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.MineFantasyBase;
import minefantasy.block.tileentity.TileEntityForge;
import minefantasy.block.tileentity.TileEntityRoast;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class BlockRoast extends BlockContainer {

    private Random rand = new Random();

    public BlockRoast(int i) {
        super(i, Material.wood);
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
    	return Block.planks.getIcon(side, 0);
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
    public TileEntity createNewTileEntity(World w) {
        return new TileEntityRoast();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {
        int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        TileEntityRoast tile = (TileEntityRoast) world.getBlockTileEntity(x, y, z);
        tile.direction = dir;
        tile.sendPacketToServer();
    }

    /**
     * Called whenever the block is removed.
     */
    public void breakBlock(World world, int x, int y, int z, int i1, int i2){
    	TileEntityRoast tile = (TileEntityRoast) world.getBlockTileEntity(x, y, z);

        if (tile != null) {
            for (int var6 = 0; var6 < tile.getSizeInventory(); ++var6) {
                ItemStack var7 = tile.getStackInSlot(var6);

                if (var7 != null) {
                    float var8 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float var9 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float var10 = this.rand.nextFloat() * 0.8F + 0.1F;

                    while (var7.stackSize > 0) {
                        int var11 = this.rand.nextInt(21) + 10;

                        if (var11 > var7.stackSize) {
                            var11 = var7.stackSize;
                        }

                        var7.stackSize -= var11;
                        EntityItem var12 = new EntityItem(world, (double) ((float) x + var8), (double) ((float) y + var9), (double) ((float) z + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));

                        if (var7.hasTagCompound()) {
                            var12.getEntityItem().setTagCompound((NBTTagCompound) var7.getTagCompound().copy());
                        }

                        float var13 = 0.05F;
                        var12.motionX = (double) ((float) this.rand.nextGaussian() * var13);
                        var12.motionY = (double) ((float) this.rand.nextGaussian() * var13 + 0.2F);
                        var12.motionZ = (double) ((float) this.rand.nextGaussian() * var13);
                        world.spawnEntityInWorld(var12);
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, i1, i2);
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
        	TileEntityRoast tile = (TileEntityRoast)world.getBlockTileEntity(x, y, z);

            if (tile != null)
            {
            	if(canPlaceFood(tile, player, i))
            	{
	            	ItemStack item = player.getHeldItem();
	            	
	            	//FOOD OUT
	            	if(tile.tryTakeItem(player))
	            	{
	            		return true;
	            	}
	            	//FOOD IN
	            	if(tile.tryAddItem(item))
	            	{
	            		if(!player.capabilities.isCreativeMode)
	            		{
	            			item.stackSize --;
	            		}
	            		
	            		return true;
	            	}
	            	
            	}	
                player.openGui(MineFantasyBase.instance, 11, world, x, y, z);
            }

            return true;
        }
    }
    
    
   private boolean canPlaceFood(TileEntityRoast tile, EntityPlayer player, int i)
   {
	   ForgeDirection tileFace = BlockClickHelper.FD[tile.direction];
	   ForgeDirection face = ForgeDirection.getOrientation(i);
	   
	   if(player.isSneaking())return false;
	   
	   if(tileFace == ForgeDirection.NORTH || tileFace == ForgeDirection.SOUTH)
	   {
		   return face == ForgeDirection.NORTH || face == ForgeDirection.SOUTH;
	   }
	   if(tileFace == ForgeDirection.EAST || tileFace == ForgeDirection.WEST)
	   {
		   return face == ForgeDirection.EAST || face == ForgeDirection.WEST;
	   }
		return false;
	}

	/* @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2) {
    	if(world.isRemote)return false;
    	
    	boolean hasTaken = false;
    	TileEntityRoast roast = (TileEntityRoast)world.getBlockTileEntity(x, y, z);
    	
    	for(int a = 0; a < 5; a ++)
    	{
    		ItemStack hung = roast.getStackInSlot(a);
    		if(hung != null && roast.getResultFor(a) == null)
    		{
    			player.inventory.addItemStackToInventory(hung);
    			roast.decrStackSize(a, 1);
    			hasTaken = true;
    			break;
    		}
    	}
    	if(!hasTaken)
    	{
    		
    	}
    	
    	if(!hasTaken)
    		player.openGui(MineFantasyBase.instance, 11, world, x, y, z);
        return true;
    }*/
   @SideOnly(Side.CLIENT)
   @Override
   public void registerIcons(IconRegister reg){}
}
