package minefantasy.block.special;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.MineFantasyBase;
import minefantasy.block.tileentity.TileEntityWeaponRack;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
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
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class BlockWeaponRack extends BlockContainer {

    private Random rand = new Random();

    private static final float thickness = 5F/16F;
    public BlockWeaponRack(int id)
    {
        super(id, Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.updateLadderBounds(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }

    /**
     * Update the ladder block bounds based on the given metadata value.
     */
    public void updateLadderBounds(int par1)
    {
        float f = thickness;

        if (par1 == 2)
        {
            this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }

        if (par1 == 3)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }

        if (par1 == 4)
        {
            this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

        if (par1 == 5)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return cfg.renderId;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST ) ||
               par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST ) ||
               par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH) ||
               par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH);
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World world, int x, int y, int z, int side, float xo, float yo, float zr, int meta)
    {
        int j1 = meta;

        if ((j1 == 0 || side == 2) && world.isBlockSolidOnSide(x, y, z + 1, NORTH))
        {
            j1 = 2;
        }

        if ((j1 == 0 || side == 3) && world.isBlockSolidOnSide(x, y, z - 1, SOUTH))
        {
            j1 = 3;
        }

        if ((j1 == 0 || side == 4) && world.isBlockSolidOnSide(x + 1, y, z, WEST))
        {
            j1 = 4;
        }

        if ((j1 == 0 || side == 5) && world.isBlockSolidOnSide(x - 1, y, z, EAST))
        {
            j1 = 5;
        }

        return j1;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int i1 = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = false;

        if (i1 == 2 && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH))
        {
            flag = true;
        }

        if (i1 == 3 && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH))
        {
            flag = true;
        }

        if (i1 == 4 && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST))
        {
            flag = true;
        }

        if (i1 == 5 && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST))
        {
            flag = true;
        }

        if (!flag)
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, i1, 0);
            par1World.setBlockToAir(par2, par3, par4);
        }

        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
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
    
    @Override
    public TileEntity createNewTileEntity(World w) {
        return new TileEntityWeaponRack();
    }

    /**
     * Called whenever the block is removed.
     */
    public void breakBlock(World world, int x, int y, int z, int i1, int i2){
    	TileEntityWeaponRack tile = (TileEntityWeaponRack) world.getBlockTileEntity(x, y, z);

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
    	TileEntityWeaponRack tile = (TileEntityWeaponRack)world.getBlockTileEntity(x, y, z);
		
		if(world.isRemote)
 		{
			int slot = tile.getSlotFor(f, f2);
			if(slot >= 0 && slot < 4)
			{
		    	tryPlaceItem(slot, world, tile, player);
			}
			
 			Packet packet = PacketManagerMF.getPacketIntegerArray(tile, new int[]{1, player.entityId, i, slot});
 			try
 			{
 				PacketDispatcher.sendPacketToServer(packet);
 			} catch(NullPointerException e)
 			{
 				System.out.println("MineFantasy: Client connection lost");
 			}
 		}
		
        return true;
    }

	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg){}
    
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int dir =0;
    	int m = world.getBlockMetadata(x, y, z);
        TileEntityWeaponRack tile = (TileEntityWeaponRack) world.getBlockTileEntity(x, y, z);
        
        if(m == 4)dir = 3;
        if(m == 2)dir = 0;
        if(m == 5)dir = 1;
        if(m == 3)dir = 2;
        
        if(!world.isRemote)
        {
        	System.out.println("meta: " + m + "dir: " + dir);
        }
        
        tile.direction = dir;
    }
    
    
    
    
    public static boolean tryPlaceItem(int slot, World world, TileEntityWeaponRack tile, EntityPlayer player)
    {
    	if(player.isSneaking())
    	{
    		player.openGui(MineFantasyBase.instance, 4, world, tile.xCoord, tile.yCoord, tile.zCoord);
    		return false;
    	}
    	
    	ItemStack held = player.getHeldItem();
    	if(held == null)
    	{
			ItemStack hung = tile.getStackInSlot(slot);
			if(hung != null)
			{
				if(!world.isRemote)
				{
					player.setCurrentItemOrArmor(0, hung);
					tile.setInventorySlotContents(slot, null);
					tile.syncItems();
				}
				player.swingItem();
				return true;
			}
    	}
    	else
    	{
    		ItemStack hung = tile.getStackInSlot(slot);
    		
			if(hung == null && tile.canHang(player.getHeldItem()))
			{
				if(!world.isRemote)
				{
					tile.setInventorySlotContents(slot, player.getHeldItem().copy());
					player.setCurrentItemOrArmor(0, null);
					tile.syncItems();
				}
				player.swingItem();
				return true;
			}
			else if(held != null && hung != null)
			{
				if(hung.isItemEqual(held))
				{
					int space = hung.getMaxStackSize() - hung.stackSize;
					
					if(held.stackSize > space)
					{
						if(!world.isRemote)
						{
							held.stackSize -= space;
							hung.stackSize += space;
						}
						player.swingItem();
						return true;
					}
					else
					{
						if(!world.isRemote)
						{
							hung.stackSize += held.stackSize;
							player.setCurrentItemOrArmor(0, null);
						}
						player.swingItem();
						return true;
					}
				}
			}
    	}
    	player.openGui(MineFantasyBase.instance, 4, world, tile.xCoord, tile.yCoord, tile.zCoord);
		return false;
	}
}
