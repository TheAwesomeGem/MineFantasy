package minefantasy.item.tool;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import minefantasy.api.mining.RandomOre;
import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.block.BlockListMF;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.IPublicMaterialItem;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemHandpick extends ItemPickaxe implements IPublicMaterialItem, IWeaponSpecial
{
    private int damageVsEntity;

	/** an array of the blocks this pickaxe is effective against */

    public ItemHandpick(int id, EnumToolMaterial material)
    {
        super(id, material);
        MinecraftForge.setToolClass(this, "pickaxe", material.getHarvestLevel());
        setCreativeTab(ItemListMF.tabTool);
        this.setMaxDamage((int)  (material.getMaxUses() * 0.5F) );
        this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial() * 0.5F;
    }
    
    @Override
    public boolean canHarvestBlock(Block block)
    {
    	if(block == Block.oreDiamond)
    	{
    		return this.getMaterial().getHarvestLevel() >= 2 && this.getMaterial() != ToolMaterialMedieval.BRONZE;
    	}
    	if(block == Block.oreIron)
    	{
    		return this.getMaterial().getHarvestLevel() >= 2;
    	}
    	if(block == Block.obsidian)
    	{
    		return this.getMaterial().getHarvestLevel() >= 3;
    	}
    	
    	int harvest = MinecraftForge.getBlockHarvestLevel(block, 0, "pickaxe");
    	if(this.getMaterial().getHarvestLevel() >= harvest)
    	{
    		return true;
    	}
    	return super.canHarvestBlock(block);
    }
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    @Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		if (getMaterial() == ToolMaterialMedieval.DRAGONFORGE)
		{
			return rarity(itemStack, 1);
		}
		if (getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
		{
			return rarity(itemStack, 2);
		}
		return super.getRarity(itemStack);
	}
	
	private EnumRarity rarity(ItemStack item, int lvl)
	{
		EnumRarity[] rarity = new EnumRarity[]{EnumRarity.common, EnumRarity.uncommon, EnumRarity.rare, EnumRarity.epic};
		if(item.isItemEnchanted())
		{
			if(lvl == 0)
			{
				lvl++;
			}
			lvl ++;
		}
		if(lvl >= rarity.length)
		{
			lvl = rarity.length-1;
		}
		return rarity[lvl];
	}
	@Override
	public EnumToolMaterial getMaterial() {
		return toolMaterial;
	}
	
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
	@Override
	public void onHit(float damage, ItemStack weapon, EntityLivingBase target, EntityLivingBase attacker) 
	{
		if (getMaterial() == ToolMaterialMedieval.DRAGONFORGE) 
		{
			target.setFire(20);
		}
		
		if (getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
		{
			attacker.heal(1F);
		}
	}
	

    @Override
    public boolean onBlockStartBreak(ItemStack item, int x, int y, int z, EntityPlayer living)
    {
    	World world = living.worldObj;
    	
    	if(!living.capabilities.isCreativeMode && living.capabilities.allowEdit && !world.isRemote)
		{
    		int id = world.getBlockId(x, y, z);
        	int meta = world.getBlockMetadata(x, y, z);
        	int harvestlvl = this.getMaterial().getHarvestLevel();
        	int fortune = EnchantmentHelper.getFortuneModifier(living);
        	boolean silk = EnchantmentHelper.getSilkTouchModifier(living);
        	
        	ArrayList<ItemStack>drops = RandomOre.getDroppedItems(id, meta, harvestlvl, fortune, silk, y);
	    	
	    	if(drops != null && !drops.isEmpty())
	    	{
	    		Iterator list = drops.iterator();
	    		
	    		while(list.hasNext())
	    		{
	    			ItemStack newdrop = (ItemStack)list.next();
	    			
			    	if(newdrop != null)
			    	{
			    		if(newdrop.stackSize < 1)newdrop.stackSize = 1;
			    		
			    		dropBlockAsItem_do(world, x, y, z, newdrop);
			    	}
	    		}
	    	}
		}
        return super.onBlockStartBreak(item, x, y, z, living);
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
	public boolean onBlockDestroyed(ItemStack item, World world, int id, int x, int y, int z, EntityLivingBase user)
    {
        Random rand = new Random();
        if(getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
        {
        	if(user instanceof EntityPlayer)
        	{
        		((EntityPlayer)user).getFoodStats().addStats(1, 0.2F);
        	}
        }
        
        return super.onBlockDestroyed(item, world, id, x, y, z, user);
    }
    @Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
    }
}
