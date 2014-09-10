package minefantasy.item.tool;
import java.util.List;
import java.util.Random;

import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.block.BlockListMF;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.IPublicMaterialItem;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
public class ItemMedievalPick extends ItemPickaxe implements IPublicMaterialItem, IWeaponSpecial
{
    private int damageVsEntity;

	/** an array of the blocks this pickaxe is effective against */

    public ItemMedievalPick(int id, EnumToolMaterial material)
    {
        super(id, material);
        MinecraftForge.setToolClass(this, "pickaxe", material.getHarvestLevel());
        setCreativeTab(ItemListMF.tabTool);
    }
    @Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
    }
    
    public float getStrVsBlock(ItemStack item, Block block)
    {
    	if(item != null)
    	{
    		if(item.getItem() instanceof ItemMedievalPick)
    		{
    			if(block == Block.obsidian)
    			{
	    			if(((ItemMedievalPick)item.getItem()).getMaterial() == ToolMaterialMedieval.MITHRIL)
	    			{
	    				return this.efficiencyOnProperMaterial*1.5F;
	    			}
	    			if(((ItemMedievalPick)item.getItem()).getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
	    			{
	    				return this.efficiencyOnProperMaterial*2;
	    			}
    			}
    		}
    	}
    	return super.getStrVsBlock(item, block);
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
}
