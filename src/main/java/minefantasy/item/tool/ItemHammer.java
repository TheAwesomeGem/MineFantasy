package minefantasy.item.tool;

import java.util.List;

import minefantasy.api.anvil.IHammer;
import minefantasy.api.cooking.IUtensil;
import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.block.BlockListMF;
import minefantasy.block.special.BlockAnvilMF;
import minefantasy.block.tileentity.TileEntityAnvil;
import minefantasy.item.EnumHammerType;
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

public class ItemHammer extends ItemTool implements IHammer, IWeaponSpecial{

	private EnumToolMaterial hammerMaterial;
	private float efficiency;
	private int level;
	public ItemHammer(int i, float power, EnumToolMaterial mat)
	{
		super(i, mat.getEfficiencyOnProperMaterial(), mat, new Block[]{});
		this.hammerMaterial = mat;
		this.setMaxStackSize(1);
		efficiency = power;
        this.setMaxDamage(mat.getMaxUses());
        this.setCreativeTab(ItemListMF.tabTool);
        maxStackSize = 1;
        level = mat == ToolMaterialMedieval.ORNATE ? 1 : 0;
	}
    @Override
    public boolean isFull3D() {
        return true;
    }
    
	public float getForgeStrength()
	{
		return efficiency;
	}
	
	public int getItemEnchantability()
    {
        return this.hammerMaterial.getEnchantability();
    }
	
	public int getForgeLevel()
	{
		return level;
	}
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
    {
		if(block == BlockListMF.anvil)
		{
			return 0.0F;
		}
		return super.getStrVsBlock(stack, block);
    }
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		
    }
	@Override
    public void addInformation(ItemStack weapon, EntityPlayer player, List list, boolean fullInfo)
    {
        super.addInformation(weapon, player, list, fullInfo);
        
        list.add("Efficiency: "+getForgeStrength());
    }

	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		if (hammerMaterial == ToolMaterialMedieval.DRAGONFORGE || hammerMaterial == ToolMaterialMedieval.ORNATE)
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
	public void onHit(float damage, ItemStack weapon, EntityLivingBase target, EntityLivingBase attacker) 
	{
		if (hammerMaterial == ToolMaterialMedieval.DRAGONFORGE) 
		{
			target.setFire(20);
		}
		if (hammerMaterial == ToolMaterialMedieval.IGNOTUMITE) 
		{
			attacker.heal(1F);
		}
	}
	
	public EnumToolMaterial getMaterial()
	{
		return hammerMaterial;
	}
}
