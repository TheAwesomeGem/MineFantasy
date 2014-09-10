package minefantasy.item.tool;

import java.util.List;

import minefantasy.api.cooking.IUtensil;
import minefantasy.api.leatherwork.EnumToolType;
import minefantasy.api.leatherwork.ITanningItem;
import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemKnifeMF extends ItemTool implements ITanningItem, IUtensil, IWeaponSpecial{

	private float quality;
	public ItemKnifeMF(int id, EnumToolMaterial material) 
	{
		this(id, material.getDamageVsEntity()+1F, material);
	}
	public ItemKnifeMF(int id, float dam, EnumToolMaterial material) 
	{
		super(id, material.getEfficiencyOnProperMaterial(), material, new Block[]{});
		quality = material.getEfficiencyOnProperMaterial();
		damageVsEntity = dam;
        setCreativeTab(ItemListMF.tabTool);
	}
	
	@Override
    public boolean isFull3D() {
        return true;
    }
	
    @Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
    
	@Override
	public float getQuality() {
		return quality;
	}
	@Override
	public EnumToolType getType() {
		return EnumToolType.KNIFE;
	}
	
	@Override
	public String getType(ItemStack tool) {
		return "knife";
	}
	@Override
	public float getEfficiency(ItemStack tool) 
	{
		int enchant = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, tool);
	
		return quality * (1.0F + (0.5F*enchant));
	}

	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
	{
		if(id != ItemListMF.knifeBronze.itemID)
			return;
		
		add(list, ItemListMF.spadeCopperForged);
		add(list, ItemListMF.spadeTin);
		add(list, ItemListMF.spadeBronze);
		add(list, ItemListMF.spadeIronForged);
		add(list, ItemListMF.spadeSteelForged);
		add(list, ItemListMF.spadeEncrusted);
		add(list, ItemListMF.spadeDragon);
		add(list, ItemListMF.spadeDeepIron);
		add(list, ItemListMF.spadeMithril);
		add(list, ItemListMF.spadeIgnotumiteForged);
		
		add(list, ItemListMF.axeCopperForged);
		add(list, ItemListMF.axeTin);
		add(list, ItemListMF.axeBronze);
		add(list, ItemListMF.axeIronForged);
		add(list, ItemListMF.axeSteelForged);
		add(list, ItemListMF.axeEncrusted);
		add(list, ItemListMF.axeDragon);
		add(list, ItemListMF.axeDeepIron);
		add(list, ItemListMF.axeMithril);
		add(list, ItemListMF.axeIgnotumiteForged);
		
		add(list, ItemListMF.handpickBronze);
		add(list, ItemListMF.handpickIron);
		add(list, ItemListMF.handpickSteel);
		add(list, ItemListMF.handpickEncrusted);
		add(list, ItemListMF.handpickDragonforge);
		add(list, ItemListMF.handpickDeepIron);
		add(list, ItemListMF.handpickMithril);
		add(list, ItemListMF.handpickIgnotumite);
		
		add(list, ItemListMF.pickCopperForged);
		add(list, ItemListMF.pickTin);
		add(list, ItemListMF.pickBronze);
		add(list, ItemListMF.pickIronForged);
		add(list, ItemListMF.pickSteelForged);
		add(list, ItemListMF.pickEncrusted);
		add(list, ItemListMF.pickDragon);
		add(list, ItemListMF.pickDeepIron);
		add(list, ItemListMF.pickMithril);
		add(list, ItemListMF.pickIgnotumiteForged);
		
		add(list, ItemListMF.hoeCopperForged);
		add(list, ItemListMF.hoeTin);
		add(list, ItemListMF.hoeBronze);
		add(list, ItemListMF.hoeIronForged);
		add(list, ItemListMF.hoeSteelForged);
		add(list, ItemListMF.hoeDragon);
		add(list, ItemListMF.hoeDeepIron);
		add(list, ItemListMF.hoeMithril);
		
		add(list, ItemListMF.knifeStone);
		add(list, ItemListMF.knifeCopper);
		add(list, ItemListMF.knifeTin);
		add(list, ItemListMF.knifeBronze);
		add(list, ItemListMF.knifeIron);
		add(list, ItemListMF.knifeSteel);
		add(list, ItemListMF.knifeDragon);
		add(list, ItemListMF.knifeDeepIron);
		add(list, ItemListMF.knifeMithril);
		
		add(list, ItemListMF.malletWood);
		add(list, ItemListMF.malletIronbark);
		add(list, ItemListMF.malletEbony);
		
		add(list, ItemListMF.hammerStone);
		add(list, ItemListMF.hammerCopper);
		add(list, ItemListMF.hammerTin);
		add(list, ItemListMF.hammerBronze);
		add(list, ItemListMF.hammerIron);
		add(list, ItemListMF.hammerSteel);
		add(list, ItemListMF.hammerDragon);
		add(list, ItemListMF.hammerDeepIron);
		add(list, ItemListMF.hammerMithril);
		add(list, ItemListMF.hammerOrnate);
		
		add(list, ItemListMF.tongsStone);
		add(list, ItemListMF.tongsCopper);
		add(list, ItemListMF.tongsTin);
		add(list, ItemListMF.tongsBronze);
		add(list, ItemListMF.tongsIron);
		add(list, ItemListMF.tongsSteel);
		add(list, ItemListMF.tongsDragon);
		add(list, ItemListMF.tongsDeepIron);
		add(list, ItemListMF.tongsMithril);
		
		add(list, ItemListMF.hammerRepair);
		add(list, ItemListMF.hammerRepair2);
		add(list, ItemListMF.hammerRepairArtisan);
		add(list, ItemListMF.hammerRepairOrnate);
		add(list, ItemListMF.hammerRepairOrnate2);
		add(list, ItemListMF.hammerRepairOrnateArtisan);
		
		add(list, ItemListMF.shearsCopper);
		add(list, ItemListMF.shearsTin);
		add(list, ItemListMF.shearsBronze);
		add(list, ItemListMF.shearsIron);
		add(list, ItemListMF.shearsSteel);
		add(list, ItemListMF.shearsDragon);
		add(list, ItemListMF.shearsDeepIron);
		add(list, ItemListMF.shearsMithril);
		
		add(list, ItemListMF.rakeBronze);
		add(list, ItemListMF.rakeIron);
		add(list, ItemListMF.rakeSteel);
		add(list, ItemListMF.rakeDragon);
		add(list, ItemListMF.rakeDeepIron);
		add(list, ItemListMF.rakeMithril);
		
		add(list, ItemListMF.mattockBronze);
		add(list, ItemListMF.mattockIron);
		add(list, ItemListMF.mattockSteel);
		add(list, ItemListMF.mattockDragon);
		add(list, ItemListMF.mattockDeepIron);
		add(list, ItemListMF.mattockMithril);
		
		add(list, ItemListMF.sawBronze);
		add(list, ItemListMF.sawIron);
		add(list, ItemListMF.sawSteel);
		add(list, ItemListMF.sawDragon);
		add(list, ItemListMF.sawDeepIron);
		add(list, ItemListMF.sawMithril);
		
		add(list, ItemListMF.scytheBronze);
		add(list, ItemListMF.scytheIron);
		add(list, ItemListMF.scytheSteel);
		add(list, ItemListMF.scytheDragon);
		add(list, ItemListMF.scytheDeepIron);
		add(list, ItemListMF.scytheMithril);
	}
	private void add(List list, Item item) {
		list.add(new ItemStack(item));
	}
	public EnumToolMaterial getMaterial()
	{
		return this.toolMaterial;
	}
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
}
