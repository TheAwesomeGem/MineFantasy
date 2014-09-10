package minefantasy.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.block.BlockListMF;
import minefantasy.block.EnumMFDoor;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemDoorMF extends Item
{
    private Material doorMaterial;
    private EnumMFDoor type;

    public ItemDoorMF(int id, EnumMFDoor door)
    {
        super(id);
        type = door;
        this.doorMaterial = door.getMaterial();
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else
        {
            ++par5;
            Block var11 = getDoorBlock();

            if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack))
            {
                if (!var11.canPlaceBlockAt(par3World, par4, par5, par6))
                {
                    return false;
                }
                else
                {
                    int var12 = MathHelper.floor_double((double)((par2EntityPlayer.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    placeDoorBlock(par3World, par4, par5, par6, var12, var11);
                    --par1ItemStack.stackSize;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    private Block getDoorBlock() {
		if(type == EnumMFDoor.IRONBARK)
			return BlockListMF.doorIronbark;
		if(type == EnumMFDoor.REINFORCED)
			return BlockListMF.doorHard;
		if(type == EnumMFDoor.STEEL)
			return BlockListMF.doorSteel;
		return null;
	}

	public static void placeDoorBlock(World world, int x, int y, int z, int side, Block block)
    {
        byte var6 = 0;
        byte var7 = 0;

        if (side == 0)
        {
            var7 = 1;
        }

        if (side == 1)
        {
            var6 = -1;
        }

        if (side == 2)
        {
            var7 = -1;
        }

        if (side == 3)
        {
            var6 = 1;
        }

        int var8 = (world.isBlockNormalCube(x - var6, y, z - var7) ? 1 : 0) + (world.isBlockNormalCube(x - var6, y + 1, z - var7) ? 1 : 0);
        int var9 = (world.isBlockNormalCube(x + var6, y, z + var7) ? 1 : 0) + (world.isBlockNormalCube(x + var6, y + 1, z + var7) ? 1 : 0);
        boolean var10 = world.getBlockId(x - var6, y, z - var7) == block.blockID || world.getBlockId(x - var6, y + 1, z - var7) == block.blockID;
        boolean var11 = world.getBlockId(x + var6, y, z + var7) == block.blockID || world.getBlockId(x + var6, y + 1, z + var7) == block.blockID;
        boolean var12 = false;

        if (var10 && !var11)
        {
            var12 = true;
        }
        else if (var9 > var8)
        {
            var12 = true;
        }

        world.setBlock(x, y, z, block.blockID, side, 2);
        world.setBlock(x, y + 1, z, block.blockID, 8 | (var12 ? 1 : 0), 2);
        world.notifyBlocksOfNeighborChange(x, y, z, block.blockID);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, block.blockID);
    }
	
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Misc/"+name);
		return super.setUnlocalizedName(name);
    }
}
