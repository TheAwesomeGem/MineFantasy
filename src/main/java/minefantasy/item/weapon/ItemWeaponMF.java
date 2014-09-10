package minefantasy.item.weapon;

import minefantasy.api.aesthetic.IWeaponrackHangable;
import minefantasy.api.armour.EnumArmourClass;
import minefantasy.api.weapon.*;
import minefantasy.item.*;
import minefantasy.system.cfg;
import mods.battlegear2.api.PlayerEventChild.OffhandAttackEvent;
import mods.battlegear2.api.weapons.IBattlegearWeapon;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * Weapon Types:
 * Blades are versitile, some can block, and have average damage
 * Blunt weapons have bonus armour penetration, same dam as blade, can not block
 * Axes do the most damage, but nothing else
 * Polearms are slow and long ranged
 */
public abstract class ItemWeaponMF extends ItemSword implements IWeaponrackHangable, IHitReaction, IBattlegearWeapon, IWeaponSpecial, IWeightedWeapon
{
	protected Random rand = new Random();
	protected float baseDamage;
	protected EnumToolMaterial material;
	public static final DecimalFormat decimal_format = new DecimalFormat("#.###");

	public ItemWeaponMF(int id, EnumToolMaterial material) 
	{
		super(id, material);
		setCreativeTab(ItemListMF.tabWeapon);
		this.material = material;
		setMaxDamage((int)(material.getMaxUses() * getDurability()));
		
		this.baseDamage = (4 + material.getDamageVsEntity())* this.getDamageModifier();
	}
	
	public EnumToolMaterial getMaterial() 
	{
		return this.material;
	}
	
	
	@Override
	public Multimap getItemAttributeModifiers()
    {
		Multimap map = HashMultimap.create();
		map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.baseDamage, 0));

        return map;
    }

    @Override
    public void addInformation(ItemStack weapon, EntityPlayer player, List list, boolean fullInfo)
    {
        super.addInformation(weapon, player, list, fullInfo);


        if(this.canBlock() || this instanceof IExtendedReachItem || this instanceof IWeaponSpecialBlock || this instanceof IWeaponPenetrateArmour || this instanceof IWeaponCustomSpeed){
            list.add("");
            
            if(this instanceof IWeaponCustomSpeed)
            {
                int hitMod = ((IWeaponCustomSpeed)this).getHitTime(weapon, null);
                if(hitMod > 0)
                {
                    list.add(EnumChatFormatting.RED+
                            StatCollector.translateToLocalFormatted("attribute.modifier.plus."+ 1,
                                    new Object[] {decimal_format.format((float)hitMod / 10F * 100),
                                            StatCollector.translateToLocal("attribute.weapon.attackSpeed")}));
                }else{
                    list.add(EnumChatFormatting.DARK_GREEN+
                            StatCollector.translateToLocalFormatted("attribute.modifier.take."+ 1,
                                    new Object[] {decimal_format.format(-(float)hitMod / 10F * 100),
                                            StatCollector.translateToLocal("attribute.weapon.attackSpeed")}));
                }
            }
            
            

            if(this instanceof IWeaponPenetrateArmour)
            {
                list.add(EnumChatFormatting.DARK_GREEN+
                        StatCollector.translateToLocalFormatted("attribute.modifier.plus."+ 1,
                        new Object[] {decimal_format.format(this.getAPPercent()*100F),
                        StatCollector.translateToLocal("attribute.weapon.penetrateArmor")}));
                
                if(((IWeaponPenetrateArmour)this).buffDamage())
                {
                    float boost = ((IWeaponPenetrateArmour)this).getArmourDamageBonus() - 1.0F;

                    if(boost > 0)
                    {
                        list.add(EnumChatFormatting.DARK_GREEN+
                		StatCollector.translateToLocalFormatted("attribute.modifier.plus."+ 1,
                        new Object[] {decimal_format.format(boost*100F),
                        StatCollector.translateToLocal("attribute.weapon.boostarmourdamage")}));
                    }
                }
            }
            if(this instanceof IWeaponWeakArmour)
            {
                float AE = ((IWeaponWeakArmour)this).getArmourPower(weapon);
                if(AE > 0)
                {
                    list.add(EnumChatFormatting.RED+
                            StatCollector.translateToLocalFormatted("attribute.modifier.take."+ 1,
                                    new Object[] {decimal_format.format(AE*100F),
                                            StatCollector.translateToLocal("attribute.weapon.penetrateArmor")}));
                }
            }
            
            

            if(this instanceof IExtendedReachItem)
            {
                float reach = ((IExtendedReachItem)this).getReachModifierInBlocks(weapon);

                if(reach > 0)
                {
                    list.add(EnumChatFormatting.DARK_GREEN+
            		StatCollector.translateToLocalFormatted("attribute.modifier.plus."+ 0,
                    new Object[] {decimal_format.format(reach),
                    StatCollector.translateToLocal("attribute.weapon.extendedReach")}));
                }else
                {
                    list.add(EnumChatFormatting.RED+
                    StatCollector.translateToLocalFormatted("attribute.modifier.take."+ 0,
                    new Object[] {decimal_format.format(-1 * reach),
                    StatCollector.translateToLocal("attribute.weapon.extendedReach")}));
                }
            }

            
            
            if(this instanceof IStealthWeapon)
            {
                list.add(EnumChatFormatting.GOLD+
                StatCollector.translateToLocal("attribute.weapon.stealthWeapon"));

            }
            if(canJoust())
            {
            	list.add(EnumChatFormatting.GOLD+
            	StatCollector.translateToLocal("attribute.weapon.joust"));
            }
            if(this instanceof IWeaponSpecialBlock)
            {
            	list.add(EnumChatFormatting.GOLD+
            	StatCollector.translateToLocal("attribute.weapon.block.special"));
            }
            else if(canBlock())
            {
            	list.add(EnumChatFormatting.GOLD+
            	StatCollector.translateToLocal("attribute.weapon.block"));
            }
        }

    }
	
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		if(tabs != ItemListMF.tabWeapon)
		{
			super.getSubItems(id, tabs, list);
			return;
		}
		if(id != ItemListMF.swordSteelForged.itemID)
			return;
		
		add(list, ItemListMF.daggerBronze);
		add(list, ItemListMF.daggerIron);
		add(list, ItemListMF.daggerSteel);
		add(list, ItemListMF.daggerEncrusted);
		add(list, ItemListMF.daggerDeepIron);
		add(list, ItemListMF.daggerMithril);
		add(list, ItemListMF.daggerDragon);
		add(list, ItemListMF.daggerOrnate);
		add(list, ItemListMF.daggerIgnotumite);
		
		add(list, ItemListMF.swordCopper);
		add(list, ItemListMF.swordTin);
		add(list, ItemListMF.swordBronze);
		add(list, ItemListMF.swordIronForged);
		add(list, ItemListMF.swordSteelForged);
		add(list, ItemListMF.swordEncrusted);
		add(list, ItemListMF.swordDeepIron);
		add(list, ItemListMF.swordMithril);
		add(list, ItemListMF.swordDragon);
		add(list, ItemListMF.swordOrnate);
		add(list, ItemListMF.swordIgnotumite);
		
		add(list, ItemListMF.broadBronze);
		add(list, ItemListMF.broadIron);
		add(list, ItemListMF.broadSteel);
		add(list, ItemListMF.broadEncrusted);
		add(list, ItemListMF.broadswordDeepIron);
		add(list, ItemListMF.broadMithril);
		add(list, ItemListMF.broadDragon);
		add(list, ItemListMF.broadOrnate);
		add(list, ItemListMF.broadIgnotumite);
		
		add(list, ItemListMF.greatswordBronze);
		add(list, ItemListMF.greatswordIron);
		add(list, ItemListMF.greatswordSteel);
		add(list, ItemListMF.greatswordEncrusted);
		add(list, ItemListMF.greatswordDeepIron);
		add(list, ItemListMF.greatswordMithril);
		add(list, ItemListMF.greatswordDragon);
		add(list, ItemListMF.greatswordOrnate);
		add(list, ItemListMF.greatswordIgnotumite);
		
		add(list, ItemListMF.maceCopper);
		add(list, ItemListMF.maceTin);
		add(list, ItemListMF.maceBronze);
		add(list, ItemListMF.maceIron);
		add(list, ItemListMF.maceSteel);
		add(list, ItemListMF.maceEncrusted);
		add(list, ItemListMF.maceDeepIron);
		add(list, ItemListMF.maceMithril);
		add(list, ItemListMF.maceDragon);
		add(list, ItemListMF.maceOrnate);
		add(list, ItemListMF.maceIgnotumite);
		
		add(list, ItemListMF.warpickBronze);
		add(list, ItemListMF.warpickIron);
		add(list, ItemListMF.warpickSteel);
		add(list, ItemListMF.warpickEncrusted);
		add(list, ItemListMF.warpickDeepIron);
		add(list, ItemListMF.warpickMithril);
		add(list, ItemListMF.warpickDragon);
		add(list, ItemListMF.warpickOrnate);
		add(list, ItemListMF.warpickIgnotumite);
		
		add(list, ItemListMF.morningstarBronze);
		add(list, ItemListMF.morningstarIron);
		add(list, ItemListMF.morningstarSteel);
		add(list, ItemListMF.greatmaceEncrusted);
		add(list, ItemListMF.greatmaceDeepIron);
		add(list, ItemListMF.morningstarMithril);
		add(list, ItemListMF.morningstarDragon);
		add(list, ItemListMF.greatmaceOrnate);
		add(list, ItemListMF.morningstarIgnotumite);
		
		add(list, ItemListMF.warhammerBronze);
		add(list, ItemListMF.warhammerIron);
		add(list, ItemListMF.warhammerSteel);
		add(list, ItemListMF.warhammerEncrusted);
		add(list, ItemListMF.warhammerDeepIron);
		add(list, ItemListMF.warhammerMithril);
		add(list, ItemListMF.warhammerDragon);
		add(list, ItemListMF.warhammerOrnate);
		add(list, ItemListMF.warhammerIgnotumite);
		
		add(list, ItemListMF.waraxeCopper);
		add(list, ItemListMF.waraxeTin);
		add(list, ItemListMF.waraxeBronze);
		add(list, ItemListMF.waraxeIron);
		add(list, ItemListMF.waraxeSteel);
		add(list, ItemListMF.waraxeEncrusted);
		add(list, ItemListMF.waraxeDeepIron);
		add(list, ItemListMF.waraxeMithril);
		add(list, ItemListMF.waraxeDragon);
		add(list, ItemListMF.waraxeOrnate);
		add(list, ItemListMF.waraxeIgnotumite);
		
		
		add(list, ItemListMF.battleaxeBronze);
		add(list, ItemListMF.battleaxeIron);
		add(list, ItemListMF.battleaxeSteel);
		add(list, ItemListMF.battleaxeEncrusted);
		add(list, ItemListMF.battleaxeDeepIron);
		add(list, ItemListMF.battleaxeMithril);
		add(list, ItemListMF.battleaxeDragon);
		add(list, ItemListMF.battleaxeOrnate);
		add(list, ItemListMF.battleaxeIgnotumite);
		
		add(list, ItemListMF.spearBronze);
		add(list, ItemListMF.spearIron);
		add(list, ItemListMF.spearSteel);
		add(list, ItemListMF.spearEncrusted);
		add(list, ItemListMF.spearDeepIron);
		add(list, ItemListMF.spearMithril);
		add(list, ItemListMF.spearDragon);
		add(list, ItemListMF.spearOrnate);
		add(list, ItemListMF.spearIgnotumite);
		
		add(list, ItemListMF.halbeardBronze);
		add(list, ItemListMF.halbeardIron);
		add(list, ItemListMF.halbeardSteel);
		add(list, ItemListMF.halbeardEncrusted);
		add(list, ItemListMF.halbeardDeepIron);
		add(list, ItemListMF.halbeardMithril);
		add(list, ItemListMF.halbeardDragon);
		add(list, ItemListMF.halbeardOrnate);
		add(list, ItemListMF.halbeardIgnotumite);
		
		add(list, ItemListMF.lanceBronze);
		add(list, ItemListMF.lanceIron);
		add(list, ItemListMF.lanceSteel);
		add(list, ItemListMF.lanceEncrusted);
		add(list, ItemListMF.lanceDeepIron);
		add(list, ItemListMF.lanceMithril);
		add(list, ItemListMF.lanceDragon);
		add(list, ItemListMF.lanceOrnate);
		add(list, ItemListMF.lanceIgnotumite);
    }

	private void add(List list, Item item)
	{
		list.add(new ItemStack(item));
	}
	
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Weapon/"+name);
		return super.setUnlocalizedName(name);
    }
	
	public abstract float getDamageModifier();

	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
		if(canBlock())
		{
			return super.onItemRightClick(item, world, player);
		}
		else
		{
			return item;
		}
    }
	public boolean canBlock()
	{
		return false;
	}
	
	@Override
	public void onHit(float damage, ItemStack weapon, EntityLivingBase target, EntityLivingBase attacker)
	{
		Class enClass = target.getClass();
		String name = "";
		if(enClass != null && EntityList.classToStringMapping.get(enClass) != null)
		{
			name = (String) EntityList.classToStringMapping.get(enClass);
		}
		if (getMaterial() == ToolMaterialMedieval.DRAGONFORGE) 
		{
			target.setFire(20);
		}
		
		if (getMaterial() == ToolMaterialMedieval.ORNATE) 
		{
			if (target instanceof EntityLiving) 
			{
				if (((EntityLiving) target).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) 
				{
					target.setFire(20);
					int hurt = target.hurtResistantTime;
					target.hurtResistantTime = 0;
					target.attackEntityFrom(DamageSource.generic, damage);
					target.hurtResistantTime = hurt;
					target.worldObj.playSoundAtEntity(target, "random.fizz", 1, 1);
				}
				if (name.endsWith("Werewolf")) 
				{
					target.setFire(20);
					int hurt = target.hurtResistantTime;
					target.hurtResistantTime = 0;
					target.attackEntityFrom(DamageSource.generic, damage*10);
					target.hurtResistantTime = hurt;
					target.worldObj.playSoundAtEntity(target, "random.fizz", 1, 1);
				}
			}
		}
		if(canJoust())
		{
			joust(damage, weapon, target, attacker);
		}
		
		if(rand.nextFloat() < getStunChance())
		{
			if (target instanceof EntityLiving) 
			{
				PotionEffect poison = new PotionEffect(Potion.moveSlowdown.id, 100, 0);
				((EntityLiving)target).addPotionEffect(poison);
			}
		}
		if(getKnockback() > 0 && !attacker.isRiding())
		{
			knockbackEntity(target, attacker, getKnockback()/2F);
		}
		if(cfg.heavyBonus)
		{
			applyHeavyAttackBonus(attacker, target);
		}
		applyAttackBonus(attacker, target, damage);
	}


	private void applyAttackBonus(EntityLivingBase attacker, EntityLivingBase target, float damage) 
	{
		if(getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
		{
			attacker.heal(damage/4F);
		}
	}
	
	private void applyDefenseBonus(DamageSource source, EntityLivingBase target) 
	{
		if(getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
		{
			if(target.getHealth() < (target.getHealth()*0.25F))
			{
				target.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
			}
		}
	}
	
	protected void applyHeavyAttackBonus(EntityLivingBase attacker, EntityLivingBase target)
	{
	}
	@Override
	public void onUserHit(DamageSource source, EntityLivingBase target)
	{
		if(cfg.heavyBonus)
		{
			applyHeavyDefenseBonus(source, target);
		}
		applyDefenseBonus(source, target);
	}

	public void applyHeavyDefenseBonus(DamageSource source, EntityLivingBase target)
	{
		
	}

	/**
	 * Determines bonus knockback
	 * Greatswords have +3, Battleaxes have +4, Warhammers have +5
	 * Spears have +5.5, halbeards have +6
	 */
	public float getKnockback() 
	{
		return 0.0F;
	}

	public void joust(float damage, ItemStack weapon, EntityLivingBase target, EntityLivingBase attacker)
	{
    	if(attacker.isRiding())
    	{
    		Entity mount = attacker.ridingEntity;
    		float speed = (float)Math.hypot(mount.motionX, mount.motionZ) * 20F;
    		
    		if(speed > 5F)speed = 5F;
    		
    		float bonus = 1F / 5F * speed;
    		target.attackEntityFrom(new DamageSourceWeaponBonus("battlegearSpecial", attacker, false), damage + (damage * bonus * getJoustModifierDamage()));
            if(attacker instanceof EntityPlayer)
            {
            	((EntityPlayer) attacker).onCriticalHit(target);
            }
            
    		if(target.isRiding() && speed > 2F)
    		{
    			target.dismountEntity(target.ridingEntity);
    			target.mountEntity(null);
    		}
    		
    		if(speed > 0.0F)
    		{
                knockbackEntity(target, attacker, 10F * bonus);
    		}
    	}
	}
	
	public float getJoustModifierDamage() 
	{
		return 0.5F;
	}

	/**
	 * Gets the chance to slow enemies
	 * Mace: 20%_____
	 * Morningstar: 30%______
	 * Hammer: 50%______
	 */
	public float getStunChance()
	{
		return 0.0F;
	}
	
	/**
	 * Gets the Amount of uses an item has
	 * Swords have Basic
	 * Axes have +25% Bonus
	 * Blunt have 50% Bonus
	 * 2Handed +50%
	 */
	public abstract float getDurability();
	
	public boolean canJoust() 
	{
		return false;
	}
	
	private void knockbackEntity(Entity target, EntityLivingBase attacker, float knockbackMod)
	{
        float height = knockbackMod/5F;
        target.addVelocity((double) (-MathHelper.sin(attacker.rotationYaw * (float) Math.PI / 180.0F) * (float) knockbackMod * 0.5F), height, (double) (MathHelper.cos(attacker.rotationYaw * (float) Math.PI / 180.0F) * (float) knockbackMod * 0.5F));
    }
	
	@Override
	public boolean offhandClickBlock(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem) 
	{
		return false;
	}

	@Override
	public void performPassiveEffects(Side effectiveSide, ItemStack mainhandItem, ItemStack offhandItem) 
	{
		
	}

	@Override
	public boolean allowOffhand(ItemStack mainhand, ItemStack offhand) 
	{
		return getHandsUsed() == 1;
	}

	@Override
	public boolean isOffhandHandDual(ItemStack off) 
	{
		return getHandsUsed() == 1;
	}

	@Override
	public boolean sheatheOnBack(ItemStack item) 
	{
		return getHandsUsed() == 2;
	}
	
	@Override
	public boolean offhandClickAir(PlayerInteractEvent event,
			ItemStack mainhandItem, ItemStack offhandItem) {
		return true;
	}

	@Override
	public int getItemEnchantability() 
	{
        return this.getMaterial().getEnchantability();
    }
	@Override
	public boolean offhandAttackEntity(OffhandAttackEvent event, ItemStack mainhandItem, ItemStack offhandItem) 
	{
		return true;
	}
	public abstract int getHandsUsed();
	
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
	
	public boolean isPrimitive()
	{
		return material == ToolMaterialMedieval.PRIMITIVE_STONE || material == ToolMaterialMedieval.PRIMITIVE_COPPER;
	}
	
	@Override
	public float getBalance()
	{
		return 0.0F;
	}
	
	@Override
	public float getBlockFailureChance()
	{
		return 0.0F;
	}
	
	protected float getAPPercent()
	{
		return 0.0F;
	}
	@Override
	public boolean canUseRenderer(ItemStack item)
	{
		return true;
	}
}
