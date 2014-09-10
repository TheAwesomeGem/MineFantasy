package minefantasy.item.armour;

import java.text.DecimalFormat;
import java.util.List;

import org.lwjgl.opengl.GL11;


import minefantasy.MineFantasyBase;
import minefantasy.api.armour.EnumArmourClass;
import minefantasy.api.armour.IArmourClass;
import minefantasy.api.armour.IArmourCustomSpeed;
import minefantasy.api.armour.IElementalResistance;
import minefantasy.api.weapon.DamageSourceAP;
import minefantasy.client.entityrender.ModelApron;
import minefantasy.item.*;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * @author Anonymous Productions
 * 
 *         Sources are provided for educational reasons. though small bits of
 *         code, or methods can be used in your own creations.
 */
public class ItemArmourMF extends ItemArmor implements
		IArmourClass, IElementalResistance, ISpecialArmor, IArmourCustomSpeed {

	public static final DecimalFormat decimal_format = new DecimalFormat("#.##");
	@SideOnly(Side.CLIENT)
	private Icon[] ironChain;
	
	private static final int maxDamageArray[] = { 11, 16, 15, 13 };

	/**
	 * Stores the armor type: 0 is helmet, 1 is plate, 2 is legs and 3 is boots
	 */
	public final int armourType;

	/** Holds the amount of damage that the armor reduces at full durability. */
	public final int damageReduceAmount;
	private String file;

	/**
	 * Used on RenderPlayer to select the correspondent armor to be rendered on
	 * the player: 0 is cloth, 1 is chain, 2 is iron, 3 is diamond and 4 is
	 * gold.
	 */
	public final int renderIndex;

	/** The EnumArmorMaterial used for this ItemArmor */
	private final EnumArmourMF material;
	private static EnumArmorMaterial base = MedievalArmourMaterial.BASIC;
	
	private final ArmourDesign AD;
	public ItemArmourMF(int i, ArmourDesign design, EnumArmourMF mat, int render, int type, String s) 
	{
		super(i, base, render, type);
		
		AD = design;
		setCreativeTab(ItemListMF.tabArmour);
		
		material = mat;
		file = s;
		armourType = type;
		renderIndex = render;
		damageReduceAmount = (int) Math.min(20, (base.getDamageReductionAmount(type)*mat.armourRating*design.protection));
		setMaxDamage((int) (mat.durability * base.getDamageReductionAmount(type) * AD.dura));
		maxStackSize = 1;
	}

	private boolean isBasicMaterial(ArmourDesign d, EnumArmorMaterial mat) 
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)

    /**
     * allows items to add custom lines of information to the mouseover description
     * @param special wheather advanced tooltips are active
     */
    public void addInformation(ItemStack item, EntityPlayer user, List info, boolean special) 
	{
		super.addInformation(item, user, info, special);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int dam)
	{
		if(isVChain())
		{
			return ironChain[armourType];
		}
		return super.getIconFromDamage(dam);
	}
	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	public int getItemEnchantability() {
		return material.enchantment;
	}

	/**
	 * Returns the 'max damage' factor array for the armor, each piece of armor
	 * have a durability factor (that gets multiplied by armor material factor)
	 */
	static int[] getMaxDamageArray() 
	{
		return maxDamageArray;
	}

	public EnumArmourMF getMaterial() {
		return material;
	}

	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		if (getMaterial() == EnumArmourMF.DRAGONFORGE)
		{
			return rarity(itemStack, 1);
		}
		return rarity(itemStack, 0);
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
		if(AD == ArmourDesign.PLATE)
		{
			lvl ++;
		}
		if(lvl >= rarity.length)
		{
			lvl = rarity.length-1;
		}
		return rarity[lvl];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
		if(stack.itemID == ItemListMF.apronSmithy.itemID)
		{
			return "textures/entity/villager/smith.png";
		}
		return data_minefantasy.armour + file +".png";
	}
	

	private boolean isVChain() {
		return getMaterial() == EnumArmourMF.IRON && AD == ArmourDesign.CHAIN;
	}

	@SideOnly(Side.CLIENT)
	private void joinParts(ModelRenderer part, ModelRenderer anchor) {
		part.rotateAngleX = anchor.rotateAngleX;
		part.rotateAngleY = anchor.rotateAngleY;
		part.rotateAngleZ = anchor.rotateAngleZ;
	}
	@Override
	public EnumArmourClass getArmourClass() {
		if (getMaterial() == EnumArmourMF.APRON || getMaterial() == EnumArmourMF.RAWHIDE || getMaterial() == EnumArmourMF.STEALTH)return EnumArmourClass.LIGHT;
		
		return ArmourDesign.getAC(AD.AC);
	}

	@Override
	public float fireResistance() {
		if (getMaterial() == EnumArmourMF.DRAGONFORGE) 
		{
			float resistance = 1.0F / AD.PLATE.protection * AD.protection;
			
			if(resistance > 1.0F)resistance = 1.0F;
			
			return 1.0F - resistance;
		}
		if (getMaterial() == EnumArmourMF.APRON) {
			return 0.8F;
		}
		return 1.0F;
	}

	@Override
	public float shockResistance() {
		return 1.0F;
	}

	@Override
	public float corrosiveResistance() {
		return 1.0F;
	}

	@Override
	public float frostResistance() {
		if (getArmourClass() == EnumArmourClass.PLATE) {
			return 0.9F;
		}
		return 1.0F;
	}

	@Override
	public float arcaneResistance() {
		if (getMaterial() == EnumArmourMF.ENCRUSTED) {
			return 0.8F;
		}
		return 1.0F;
	}

	@Override
	public int damageReduction() {
		return 0;
	}
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack item, int armourslot)
	{
		if (getMaterial() == EnumArmourMF.APRON)
		{
			ModelApron apron = new ModelApron(0.0F);
			return apron;
		}
		return super.getArmorModel(entity, item, armourslot);
	}
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		if(getMaterial() == EnumArmourMF.GUILDED)
		{
			if(source != null && source.getEntity() != null)
			{
				if(source.getEntity() instanceof EntityLivingBase)
				{
					EntityLivingBase mob = (EntityLivingBase)source.getEntity();
					if(shouldSilverHurt(mob))
					{
						float deflect = (float)damage * (source.isProjectile() ? 0.5F : 1.0F);
						mob.attackEntityFrom(DamageSource.magic, deflect);
					}
				}
			}
			if(source.isMagicDamage() || source == DamageSource.wither)
			{
				if(damage <= 1.0F && player.getRNG().nextFloat()*2 < AD.protection)
				{
					damage = 0;
				}
				damage /= (2 * AD.protection);
			}
		}
		
		int value = base.getDamageReductionAmount(armorType);
		double AC = (double)value * material.armourRating;
		
		if(source.isProjectile())
		{
			AC *= AD.projResist;
		}
		else if(source.isFireDamage())
		{
			AC *= AD.fireResist;
		}
		else if(source.isExplosion())
		{
			AC *= AD.expResist;
		}
		else if(source == DamageSourceAP.blunt)
		{
			AC *= AD.bluntResist;
		}
		else
		{
			AC *= AD.protection;
		}
		
		if(source != DamageSourceAP.blunt && source.isUnblockable())
		{
			AC = 0;
		}
		
		if(source == DamageSource.fall)
		{
			if(getMaterial() == EnumArmourMF.STEALTH)
			{
				AC += 4;
			}
			else
			{
				AC *= AD.fallResist;
			}
		}
		
		if(!player.worldObj.isRemote && MineFantasyBase.isDebug())
		{
			System.out.println("AC: " + AC);
		}
		
		float m = (float) ((getMaxDamage() + 1 - armor.getItemDamage()) * AC);
		double maxRatio = 0.99D / 10 * this.base.getDamageReductionAmount(armorType);
		double ratio = Math.min(AC / 25D, maxRatio);
		
		return new ArmorProperties(0, AC / 25D, (int)m);
	}
	
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) 
	{
		float AC = (float)(((ItemArmourMF)armor.getItem()).damageReduceAmount);
		AC *= AD.protection*material.armourRating;
		
		return (int)AC;
	}
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) 
	{
		if(getMaterial() == EnumArmourMF.ADAMANTIUM || getMaterial() == EnumArmourMF.IGNOTUMITE)
		{
			return;
		}
		if(getMaterial() == EnumArmourMF.GUILDED && source != null && source.getEntity() != null)
		{
			if(source.getEntity() instanceof EntityLivingBase)
			{
				if(shouldSilverHurt((EntityLivingBase)source.getEntity()))
				{
					return;
				}
			}
		}
		if(getMaterial() == EnumArmourMF.DRAGONFORGE && source != null && source.isFireDamage())
		{
			return;
		}
		stack.damageItem(1, entity);
		
	}

	public static boolean shouldSilverHurt(EntityLivingBase target) 
	{
		Class enClass = target.getClass();
		String name = "";
		if(enClass != null && EntityList.classToStringMapping.get(enClass) != null)
		{
			name = (String) EntityList.classToStringMapping.get(enClass);
		}
		
		if (target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) 
		{
			return true;
		}
		if (name.endsWith("Werewolf")) 
		{
			return true;
		}
		
		return false;
	}

	@Override
	public float getMoveSpeed(ItemStack item)
	{
		float decr = 1.0F - AD.moveSpeed;
		return 1.0F - (decr*material.armourWeight);
	}
	@Override
	public Item setUnlocalizedName(String name)
    {
		if(isVChain())
		{
			setTextureName("minecraft:");
			return super.setUnlocalizedName(name);
		}
		this.setTextureName("minefantasy:Apparel/"+name);
		return super.setUnlocalizedName(name);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg)
	{
		if(isVChain())
		{
			ironChain = new Icon[4];
			
			ironChain[0] = reg.registerIcon("MineFantasy:Apparel/ironChainHelmet");
			ironChain[1] = reg.registerIcon("MineFantasy:Apparel/ironChainChest");
			ironChain[2] = reg.registerIcon("MineFantasy:Apparel/ironChainLegs");
			ironChain[3] = reg.registerIcon("MineFantasy:Apparel/ironChainBoots");
		}
		else
			super.registerIcons(reg);
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		if(tabs != ItemListMF.tabArmour)
		{
			super.getSubItems(id, tabs, list);
			return;
		}
		if(id != ItemListMF.apronSmithy.itemID)
			return;
		
		add(list, ItemListMF.apronSmithy);
		
		add(list, ItemListMF.helmetLeatherRough);
		add(list, ItemListMF.plateLeatherRough);
		add(list, ItemListMF.legsLeatherRough);
		add(list, ItemListMF.bootsLeatherRough);
		
		add(list, ItemListMF.helmetBronzeScale);
		add(list, ItemListMF.plateBronzeScale);
		add(list, ItemListMF.legsBronzeScale);
		add(list, ItemListMF.bootsBronzeScale);
		add(list, ItemListMF.helmetIronScale);
		add(list, ItemListMF.plateIronScale);
		add(list, ItemListMF.legsIronScale);
		add(list, ItemListMF.bootsIronScale);
		add(list, ItemListMF.helmetGuildedScale);
		add(list, ItemListMF.plateGuildedScale);
		add(list, ItemListMF.legsGuildedScale);
		add(list, ItemListMF.bootsGuildedScale);
		add(list, ItemListMF.helmetSteelScale);
		add(list, ItemListMF.plateSteelScale);
		add(list, ItemListMF.legsSteelScale);
		add(list, ItemListMF.bootsSteelScale);
		add(list, ItemListMF.helmetDeepIronScale);
		add(list, ItemListMF.plateDeepIronScale);
		add(list, ItemListMF.legsDeepIronScale);
		add(list, ItemListMF.bootsDeepIronScale);
		add(list, ItemListMF.helmetMithrilScale);
		add(list, ItemListMF.plateMithrilScale);
		add(list, ItemListMF.legsMithrilScale);
		add(list, ItemListMF.bootsMithrilScale);
		add(list, ItemListMF.helmetDragonScale);
		add(list, ItemListMF.plateDragonScale);
		add(list, ItemListMF.legsDragonScale);
		add(list, ItemListMF.bootsDragonScale);
		
		add(list, ItemListMF.helmetBronzeChain);
		add(list, ItemListMF.plateBronzeChain);
		add(list, ItemListMF.legsBronzeChain);
		add(list, ItemListMF.bootsBronzeChain);
		add(list, Item.helmetChain);
		add(list, Item.plateChain);
		add(list, Item.legsChain);
		add(list, Item.bootsChain);
		add(list, ItemListMF.helmetGuildedChain);
	    add(list, ItemListMF.plateGuildedChain);
		add(list, ItemListMF.legsGuildedChain);
		add(list, ItemListMF.bootsGuildedChain);
		add(list, ItemListMF.helmetSteelChain);
		add(list, ItemListMF.plateSteelChain);
		add(list, ItemListMF.legsSteelChain);
		add(list, ItemListMF.bootsSteelChain);
		add(list, ItemListMF.helmetDeepIronChain);
		add(list, ItemListMF.plateDeepIronChain);
		add(list, ItemListMF.legsDeepIronChain);
		add(list, ItemListMF.bootsDeepIronChain);
		add(list, ItemListMF.helmetMithrilChain);
		add(list, ItemListMF.plateMithrilChain);
		add(list, ItemListMF.legsMithrilChain);
		add(list, ItemListMF.bootsMithrilChain);
		add(list, ItemListMF.helmetDragonChain);
		add(list, ItemListMF.plateDragonChain);
		add(list, ItemListMF.legsDragonChain);
		add(list, ItemListMF.bootsDragonChain);
		
		add(list, ItemListMF.helmetBronzeSplint);
		add(list, ItemListMF.plateBronzeSplint);
		add(list, ItemListMF.legsBronzeSplint);
		add(list, ItemListMF.bootsBronzeSplint);
		add(list, ItemListMF.helmetIronSplint);
		add(list, ItemListMF.plateIronSplint);
		add(list, ItemListMF.legsIronSplint);
		add(list, ItemListMF.bootsIronSplint);
		add(list, ItemListMF.helmetGuildedSplint);
		add(list, ItemListMF.plateGuildedSplint);
		add(list, ItemListMF.legsGuildedSplint);
		add(list, ItemListMF.bootsGuildedSplint);
		add(list, ItemListMF.helmetSteelSplint);
		add(list, ItemListMF.plateSteelSplint);
		add(list, ItemListMF.legsSteelSplint);
		add(list, ItemListMF.bootsSteelSplint);
		add(list, ItemListMF.helmetDeepIronSplint);
		add(list, ItemListMF.plateDeepIronSplint);
		add(list, ItemListMF.legsDeepIronSplint);
		add(list, ItemListMF.bootsDeepIronSplint);
		add(list, ItemListMF.helmetMithrilSplint);
		add(list, ItemListMF.plateMithrilSplint);
		add(list, ItemListMF.legsMithrilSplint);
		add(list, ItemListMF.bootsMithrilSplint);
		add(list, ItemListMF.helmetDragonSplint);
		add(list, ItemListMF.plateDragonSplint);
		add(list, ItemListMF.legsDragonSplint);
		add(list, ItemListMF.bootsDragonSplint);
		
		
		add(list, ItemListMF.helmetBronzeHvyChain);
		add(list, ItemListMF.plateBronzeHvyChain);
		add(list, ItemListMF.legsBronzeHvyChain);
		add(list, ItemListMF.bootsBronzeHvyChain);
		add(list, ItemListMF.helmetIronHvyChain);
		add(list, ItemListMF.plateIronHvyChain);
		add(list, ItemListMF.legsIronHvyChain);
		add(list, ItemListMF.bootsIronHvyChain);
		add(list, ItemListMF.helmetGuildedHvyChain);
		add(list, ItemListMF.plateGuildedHvyChain);
		add(list, ItemListMF.legsGuildedHvyChain);
		add(list, ItemListMF.bootsGuildedHvyChain);
		add(list, ItemListMF.helmetSteelHvyChain);
	    add(list, ItemListMF.plateSteelHvyChain);
		add(list, ItemListMF.legsSteelHvyChain);
		add(list, ItemListMF.bootsSteelHvyChain);
		add(list, ItemListMF.helmetDeepIronHvyChain);
		add(list, ItemListMF.plateDeepIronHvyChain);
		add(list, ItemListMF.legsDeepIronHvyChain);
		add(list, ItemListMF.bootsDeepIronHvyChain);
		add(list, ItemListMF.helmetMithrilHvyChain);
		add(list, ItemListMF.plateMithrilHvyChain);
		add(list, ItemListMF.legsMithrilHvyChain);
		add(list, ItemListMF.bootsMithrilHvyChain);
		add(list, ItemListMF.helmetDragonHvyChain);
		add(list, ItemListMF.plateDragonHvyChain);
		add(list, ItemListMF.legsDragonHvyChain);
		add(list, ItemListMF.bootsDragonHvyChain);
		
		add(list, ItemListMF.helmetBronzePlate);
		add(list, ItemListMF.plateBronzePlate);
		add(list, ItemListMF.legsBronzePlate);
		add(list, ItemListMF.bootsBronzePlate);
		add(list, ItemListMF.helmetIronPlate);
		add(list, ItemListMF.plateIronPlate);
		add(list, ItemListMF.legsIronPlate);
		add(list, ItemListMF.bootsIronPlate);
		add(list, ItemListMF.helmetGuildedPlate);
		add(list, ItemListMF.plateGuildedPlate);
		add(list, ItemListMF.legsGuildedPlate);
		add(list, ItemListMF.bootsGuildedPlate);
		add(list, ItemListMF.helmetSteelPlate);
		add(list, ItemListMF.plateSteelPlate);
		add(list, ItemListMF.legsSteelPlate);
		add(list, ItemListMF.bootsSteelPlate);
		add(list, ItemListMF.helmetEncrustedPlate);
		add(list, ItemListMF.plateEncrustedPlate);
		add(list, ItemListMF.legsEncrustedPlate);
		add(list, ItemListMF.bootsEncrustedPlate);
		add(list, ItemListMF.helmetDragonPlate);
		add(list, ItemListMF.plateDragonPlate);
		add(list, ItemListMF.legsDragonPlate);
		add(list, ItemListMF.bootsDragonPlate);
		add(list, ItemListMF.helmetDeepIronPlate);
		add(list, ItemListMF.plateDeepIronPlate);
		add(list, ItemListMF.legsDeepIronPlate);
		add(list, ItemListMF.bootsDeepIronPlate);
		add(list, ItemListMF.helmetMithrilPlate);
		add(list, ItemListMF.plateMithrilPlate);
		add(list, ItemListMF.legsMithrilPlate);
		add(list, ItemListMF.bootsMithrilPlate);
		add(list, ItemListMF.helmetStealth);
		add(list, ItemListMF.plateStealth);
		add(list, ItemListMF.legsStealth);
		add(list, ItemListMF.bootsStealth);
    }
	private void add(List list, Item item) {
		list.add(new ItemStack(item));
	}
	
	public static float[]armourSize = new float[]{5F/24F, 8F/24F, 7F/24F, 4F/24F};
	
}
