package minefantasy.item.tool;

import java.util.List;

import minefantasy.api.tailor.INeedle;
import minefantasy.block.BlockListMF;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemNeedle extends Item implements INeedle
{
	private float efficiency;
	private int tier;
	
	public ItemNeedle(int id, EnumToolMaterial material, int customTier)
	{
		this(id, material);
		tier = customTier;
	}
	
	public ItemNeedle(int id, EnumToolMaterial material)
	{
		super(id);
		setMaxStackSize(1);
		setCreativeTab(ItemListMF.tabTailor);
		setMaxDamage(material.getMaxUses()*4);
		
		efficiency = material.getEfficiencyOnProperMaterial();
		tier = material.getHarvestLevel();
	}
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
	@Override
	public float getEfficiency()
	{
		return efficiency;
	}
	@Override
	public int getTier() 
	{
		return tier;
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		if(id != ItemListMF.needleBone.itemID)
			return;
		
		add(list, ItemListMF.needleBone);
		add(list, ItemListMF.needleBronze);
		add(list, ItemListMF.needleIron);
		add(list, ItemListMF.needleSteel);
		add(list, ItemListMF.needleDeepIron);
		add(list, ItemListMF.needleMithril);
		
		add(list, ItemListMF.twine);
		add(list, Item.silk);
    }

	private void add(List list, int item) 
	{
		list.add(new ItemStack(ItemListMF.misc, 1, item));
	}
	private void add(List list, Item item) 
	{
		list.add(new ItemStack(item));
	}
	private void add(List list, Block block) 
	{
		list.add(new ItemStack(block));
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
}
