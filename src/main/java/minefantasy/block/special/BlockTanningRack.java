package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.api.leatherwork.EnumToolType;
import minefantasy.api.leatherwork.ITanningItem;
import minefantasy.api.tanner.LeathercuttingRecipes;
import minefantasy.api.tanner.TanningRecipes;
import minefantasy.block.tileentity.TileEntityTanningRack;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
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
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
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
public class BlockTanningRack extends BlockContainer {

    private Random rand = new Random();

    public BlockTanningRack(int i, int n, Material m)
    {
        super(i, m);
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
    public void addCreativeItems(ArrayList itemList) 
    {
        itemList.add(new ItemStack(this));
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) 
    {
    	ItemStack itemstack = player.inventory.getCurrentItem();
    	TileEntityTanningRack tile = (TileEntityTanningRack) world.getBlockTileEntity(x, y, z);
        
        use(tile, player);
    }


	@Override
    public TileEntity createNewTileEntity(World w)
	{
        return new TileEntityTanningRack();
    }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) 
	{
        int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        TileEntityTanningRack tile = (TileEntityTanningRack) world.getBlockTileEntity(x, y, z);
        tile.direction = dir;
    }

   

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2)
    {
    	TileEntityTanningRack tile = (TileEntityTanningRack) world.getBlockTileEntity(x, y, z);
    	
        ItemStack itemstack = player.inventory.getCurrentItem();
        ItemStack hang = tile.getHung();
        
        if(use(tile, player))
        {
        	return true;
        }
        if(!world.isRemote && hang == null && itemstack != null)
        {
        	if(tile.canHang(itemstack))
        	{
	        	ItemStack placed = itemstack.copy();
	        	placed.stackSize = 1;
	        	tile.hang(placed);
	        	
	        	itemstack.stackSize --;
	        	if(itemstack.stackSize <= 0)
	        	{
	        		player.setCurrentItemOrArmor(0, null);
	        	}
        	}
        }
        if(hang != null)
        {
        	tile.retrieveItem(player);
        }
        
        return true;
    }
    
    public void breakBlock(World world, int x, int y, int z, int i1, int i2) 
    {
    	TileEntityTanningRack tile = (TileEntityTanningRack) world.getBlockTileEntity(x, y, z);

        super.breakBlock(world, x, y, z, i1, i2);
    }
    
    private boolean use(TileEntityTanningRack tile, EntityPlayer player)
    {
    	ItemStack held = player.getHeldItem();
    	ItemStack hung = tile.getHung();
    	
    	EnumToolType tool = null;
    	float efficiency = 1.0F;
    	if(held != null && hung != null)
    	{
    		if(held.getItem() instanceof ItemShears)
    		{
    			tool = EnumToolType.CUTTER;
    			efficiency = EnumToolMaterial.IRON.getEfficiencyOnProperMaterial();
    		}
    		if(held.getItem() instanceof ITanningItem)
    		{
    			tool = ((ITanningItem)held.getItem()).getType();
    			efficiency = ((ITanningItem)held.getItem()).getQuality();
    		}
    		
    		if(tool != null)
    		{
    			tile.use(player, tool, efficiency);
    			return true;
    		}
    	}
    	return false;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg){}
    
}
