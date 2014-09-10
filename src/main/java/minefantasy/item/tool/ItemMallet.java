package minefantasy.item.tool;

import java.util.List;

import minefantasy.api.anvil.IHammer;
import minefantasy.api.cooking.IUtensil;
import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.block.BlockListMF;
import minefantasy.block.special.BlockAnvilMF;
import minefantasy.block.tileentity.TileEntityAnvil;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeHooks;

public class ItemMallet extends ItemTool implements IUtensil{

	private EnumToolMaterial material;

	public ItemMallet(int i, EnumToolMaterial mat) {
		super(i, mat.getEfficiencyOnProperMaterial(), mat, new Block[]{});
		this.material = mat;
		this.setMaxStackSize(1);
        this.setMaxDamage(mat.getMaxUses()*4);
        this.setCreativeTab(ItemListMF.tabTool);
        maxStackSize = 1;
	}
    @Override
    public boolean isFull3D() {
        return true;
    }
    
	public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }
	
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		
    }
	
	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		if (material == ToolMaterialMedieval.EBONY)
		{
			return rarity(itemStack, 1);
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
	public String getType(ItemStack tool)
	{
		return "mallet";
	}
	@Override
	
	public float getEfficiency(ItemStack tool)
	{
		return material.getEfficiencyOnProperMaterial();
	}
}
