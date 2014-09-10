package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.api.leatherwork.ITanningItem;
import minefantasy.api.tanner.LeathercuttingRecipes;
import minefantasy.api.tanner.TanningRecipes;
import minefantasy.block.tileentity.TileEntityPrepBlock;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
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
public class BlockFoodPrep extends BlockContainer {

    private Random rand = new Random();

    public BlockFoodPrep(int i, Material m) {
        super(i, m);
        setBlockBounds(0F, 0, 0F, 1F, 0.0625F, 1F);
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
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
    	TileEntityPrepBlock tile = (TileEntityPrepBlock) world.getBlockTileEntity(x, y, z);
        tile.interact(player, true);
    }
    
    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
    	TileEntityPrepBlock tile = (TileEntityPrepBlock) world.getBlockTileEntity(x, y, z);
    	
    	ItemStack tex = tile.getStackInSlot(1);
    	if(tex != null)
    	{
    		if(tex.getItem() != null && tex.getItem() instanceof ItemBlock)
    		{
    			Block block = Block.blocksList[tex.itemID];
    			if(block != null)
    			{
    				return block.getIcon(side, tex.getItemDamage());
    			}
    		}
    	}
    	return null;
    }


	@Override
    public TileEntity createNewTileEntity(World w) {
        return new TileEntityPrepBlock();
    }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {
        int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        TileEntityPrepBlock tile = (TileEntityPrepBlock) world.getBlockTileEntity(x, y, z);
        tile.direction = dir;
    }

   
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2){
        TileEntityPrepBlock tile = (TileEntityPrepBlock) world.getBlockTileEntity(x, y, z);
        return tile.interact(player, false);
    }
	
    public void breakBlock(World world, int x, int y, int z, int i1, int i2) {
    	TileEntityPrepBlock tile = (TileEntityPrepBlock) world.getBlockTileEntity(x, y, z);
            dropItem(tile.getStackInSlot(0), world, x, y, z, true);

        super.breakBlock(world, x, y, z, i1, i2);
    }
    
    
    private void dropItem(ItemStack drop, World world, int x, int y, int z, boolean move) {

        if (drop != null) {
            float var8 = this.rand.nextFloat() * 0.8F + 0.1F;
            float var9 = this.rand.nextFloat() * 0.8F + 0.1F;
            float var10 = this.rand.nextFloat() * 0.8F + 0.1F;

            while (drop.stackSize > 0) {
                int var11 = this.rand.nextInt(21) + 10;

                if (var11 > drop.stackSize) {
                    var11 = drop.stackSize;
                }

                drop.stackSize -= var11;
                EntityItem var12 = new EntityItem(world, (double) ((float) x + var8), (double) ((float) y + var9), (double) ((float) z + var10), new ItemStack(drop.itemID, var11, drop.getItemDamage()));

                if (drop.hasTagCompound()) {
                    var12.getEntityItem().setTagCompound((NBTTagCompound) drop.getTagCompound().copy());
                }

                if(move)
                {
	                float var13 = 0.05F;
	                var12.motionX = (double) ((float) this.rand.nextGaussian() * var13);
	                var12.motionY = (double) ((float) this.rand.nextGaussian() * var13 + 0.2F);
	                var12.motionZ = (double) ((float) this.rand.nextGaussian() * var13);
                }
                world.spawnEntityInWorld(var12);
            }
        }
    }
    
    
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.doesBlockHaveSolidTopSurface(x, y - 1, z) || BlockFence.isIdAFence(world.getBlockId(x, y - 1, z));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighbor)
    {
        boolean flag = false;

        if (!world.doesBlockHaveSolidTopSurface(x, y - 1, z) && !BlockFence.isIdAFence(world.getBlockId(x, y - 1, z)))
        {
            flag = true;
        }

        if (flag)
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }
    
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg){}
    
}
