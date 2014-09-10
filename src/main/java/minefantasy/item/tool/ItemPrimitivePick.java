package minefantasy.item.tool;

import minefantasy.block.BlockListMF;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemPrimitivePick extends ItemTool{

	public ItemPrimitivePick(int id, EnumToolMaterial material) {
		super(id, 2, material, ItemPickaxe.blocksEffectiveAgainst);
	}

	public boolean canHarvestBlock(Block block)
    {
		if(this.toolMaterial == ToolMaterialMedieval.PRIMITIVE_COPPER)
		{
			return Item.pickaxeWood.canHarvestBlock(block);
		}
		return false;
    }

    public float getStrVsBlock(ItemStack item, Block block)
    {
        return Item.pickaxeWood.getStrVsBlock(item, block);
    }
    
    @Override
    public boolean onBlockStartBreak(ItemStack item, int x, int y, int z, EntityPlayer living)
    {
    	boolean copper = toolMaterial == ToolMaterialMedieval.PRIMITIVE_COPPER;
    	
    	World world = living.worldObj;
    	ItemStack newdrop = null;
    	int id = world.getBlockId(x, y, z);
    	int meta = world.getBlockMetadata(x, y, z);
    	if(isCopper(id, meta))
    	{
    		if(!copper)
    		newdrop = ItemListMF.component(ItemListMF.shardCopper);
    	}
    	if(id == Block.stone.blockID || id == Block.cobblestone.blockID)
    	{
    		if(!copper)
    		newdrop = ItemListMF.component(ItemListMF.shale);
    	}
    	if(newdrop != null)
    	{
    		if(!world.isRemote)
    			dropBlockAsItem_do(world, x, y, z, newdrop);
    	}
        return super.onBlockStartBreak(newdrop, x, y, z, living);
    }
    
    private boolean isCopper(int id, int meta) {
    	for(ItemStack copper : OreDictionary.getOres("oreCopper"))
    	{
    		if(copper == null)return false;
    		
    		if(copper.isItemEqual(new ItemStack(id, 1, meta)))
    		{
    			return true;
    		}
    	}
    	return false;
	}

	protected void dropBlockAsItem_do(World world, int x, int y, int z, ItemStack item)
    {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            float f = 0.7F;
            double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, item);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
}
