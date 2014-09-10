package minefantasy.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;

import minefantasy.item.ItemListMF;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoorMF extends Block
{
	public EnumMFDoor type;
	private int blockIndexInTexture;
	public Icon[] icon = new Icon[6];
	
	private static final String[] doornames = new String[] {"doorIronbark_lower", "doorIronbark_upper", "doorSteel_lower", "doorSteel_upper", "doorReinforced_lower", "doorReinforced_upper"};
    private final int baseTex;
    protected BlockDoorMF(int id, EnumMFDoor door)
    {
        super(id, door.getMaterial());
        type = door;
        this.disableStats();
        this.blockIndexInTexture = door.getTexture() + 1;
        baseTex = door.getTexture();

        float var3 = 0.5F;
        float var4 = 1.0F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
    }
    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public Icon getIcon(int side, int metadata)
    {
        return this.icon[this.baseTex];
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int meta)
    {
        if (meta != 1 && meta != 0)
        {
            int i1 = this.getFullMetadata(world, x, y, z);
            int j1 = i1 & 3;
            boolean flag = (i1 & 4) != 0;
            boolean flag1 = false;
            boolean flag2 = (i1 & 8) != 0;

            if (flag)
            {
                if (j1 == 0 && meta == 2)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && meta == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && meta == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && meta == 4)
                {
                    flag1 = !flag1;
                }
            }
            else
            {
                if (j1 == 0 && meta == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && meta == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && meta == 4)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && meta == 2)
                {
                    flag1 = !flag1;
                }

                if ((i1 & 16) != 0)
                {
                    flag1 = !flag1;
                }
            }

            return this.icon[this.baseTex + (flag1 ? doornames.length : 0) + (flag2 ? 1 : 0)];
        }
        else
        {
            return this.icon[this.baseTex];
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

    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z)
    {
        int var5 = this.getFullMetadata(world, x, y, z);
        return (var5 & 4) != 0;
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
        return 7;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        this.setDoorRotation(this.getFullMetadata(world, x, y, z));
    }

    /**
     * Returns 0, 1, 2 or 3 depending on where the hinge is.
     */
    public int getDoorOrientation(IBlockAccess world, int x, int y, int z)
    {
        return this.getFullMetadata(world, x, y, z) & 3;
    }

    public boolean isDoorOpen(IBlockAccess world, int x, int y, int z)
    {
        return (this.getFullMetadata(world, x, y, z) & 4) != 0;
    }

    private void setDoorRotation(int par1)
    {
        float var2 = 0.1875F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int var3 = par1 & 3;
        boolean var4 = (par1 & 4) != 0;
        boolean var5 = (par1 & 16) != 0;

        if (var3 == 0)
        {
            if (var4)
            {
                if (!var5)
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
        }
        else if (var3 == 1)
        {
            if (var4)
            {
                if (!var5)
                {
                    this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }
        }
        else if (var3 == 2)
        {
            if (var4)
            {
                if (!var5)
                {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
                }
            }
            else
            {
                this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        else if (var3 == 3)
        {
            if (var4)
            {
                if (!var5)
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float yaw, float pitch, float pan)
    {
        if (type != null && !type.canBeHandOpened())
        {
            return false; //Allow items to interact with the door
        }
        else
        {
            int var10 = this.getFullMetadata(world, x, y, z);
            int var11 = var10 & 7;
            var11 ^= 4;

            if ((var10 & 8) == 0)
            {
                world.setBlockMetadataWithNotify(x, y, z, var11, 2);
                world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            }
            else
            {
                world.setBlockMetadataWithNotify(x, y - 1, z, var11, 2);
                world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
            }

            world.playAuxSFXAtEntity(user, 1003, x, y, z, 0);
            return true;
        }
    }

    /**
     * A function to open a door.
     */
    public void onPoweredBlockChange(World world, int x, int y, int z, boolean charge)
    {
        int var6 = this.getFullMetadata(world, x, y, z);
        boolean var7 = (var6 & 4) != 0;

        if (var7 != charge)
        {
            int var8 = var6 & 7;
            var8 ^= 4;

            if ((var6 & 8) == 0)
            {
                world.setBlockMetadataWithNotify(x, y, z, var8, 2);
                world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            }
            else
            {
                world.setBlockMetadataWithNotify(x, y - 1, z, var8, 2);
                world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
            }

            world.playAuxSFXAtEntity((EntityPlayer)null, 1003, x, y, z, 0);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, int meta)
    {
        int var6 = world.getBlockMetadata(x, y, z);

        if ((var6 & 8) == 0)
        {
            boolean var7 = false;

            if (world.getBlockId(x, y + 1, z) != this.blockID)
            {
                world.setBlockToAir(x, y, z);
                var7 = true;
            }

            if (!world.doesBlockHaveSolidTopSurface(x, y - 1, z))
            {
                world.setBlockToAir(x, y, z);
                var7 = true;

                if (world.getBlockId(x, y + 1, z) == this.blockID)
                {
                    world.setBlockToAir(x, y + 1, z);
                }
            }

            if (var7)
            {
                if (!world.isRemote)
                {
                    this.dropBlockAsItem(world, x, y, z, var6, 0);
                }
            }
            else
            {
                boolean var8 = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);

                if ((var8 || meta > 0 && Block.blocksList[meta].canProvidePower()) && meta != this.blockID)
                {
                    this.onPoweredBlockChange(world, x, y, z, var8);
                }
            }
        }
        else
        {
            if (world.getBlockId(x, y - 1, z) != this.blockID)
            {
                world.setBlockToAir(x, y, z);
            }

            if (meta > 0 && meta != this.blockID)
            {
                this.onNeighborBlockChange(world, x, y - 1, z, meta);
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int id, Random rand, int meta)
    {
        return (id & 8) != 0 ? 0 : (getDoorItem().itemID);
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 vec, Vec3 vec2)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, vec, vec2);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par3 >= 255 ? false : par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && super.canPlaceBlockAt(par1World, par2, par3, par4) && super.canPlaceBlockAt(par1World, par2, par3 + 1, par4);
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 1;
    }

    /**
     * Returns the full metadata value created by combining the metadata of both blocks the door takes up.
     */
    public int getFullMetadata(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        boolean var6 = (var5 & 8) != 0;
        int var7;
        int var8;

        if (var6)
        {
            var7 = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
            var8 = var5;
        }
        else
        {
            var7 = var5;
            var8 = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
        }

        boolean var9 = (var8 & 1) != 0;
        return var7 & 7 | (var6 ? 8 : 0) | (var9 ? 16 : 0);
    }

    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return getDoorItem().itemID;
    }
    
    private Item getDoorItem() {
		if(type == EnumMFDoor.IRONBARK)
			return ItemListMF.doorIronbark;
		if(type == EnumMFDoor.REINFORCED)
			return ItemListMF.doorHard;
		if(type == EnumMFDoor.STEEL)
			return ItemListMF.doorSteel;
		return null;
	}

    /**
     * Called when the block is attempted to be harvested
     */
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player)
    {
        if (player.capabilities.isCreativeMode && (meta & 8) != 0 && world.getBlockId(x, y - 1, z) == this.blockID)
        {
            world.setBlockToAir(x, y-1, z);
        }
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.icon = new Icon[doornames.length * 2];

        for (int i = 0; i < doornames.length; ++i)
        {
            this.icon[i] = par1IconRegister.registerIcon("MineFantasy:Furn/" + doornames[i]);
            this.icon[i + doornames.length] = new IconFlipped(this.icon[i], true, false);
        }
    }
}
