package minefantasy.item.mabShield;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.MineFantasyBase;
import minefantasy.api.weapon.DamageSourceAP;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.item.weapon.ItemWeaponMF;
import mods.battlegear2.api.shield.IArrowCatcher;
import mods.battlegear2.api.IDyable;
import mods.battlegear2.api.IEnchantable;
import mods.battlegear2.api.ISheathed;
import mods.battlegear2.api.shield.IShield;
import mods.battlegear2.api.shield.ShieldType;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class ItemShield extends Item implements IShield, IDyable, IEnchantable, ISheathed, IArrowCatcher{

    public EnumShieldDesign design;
    public EnumToolMaterial material;

    private Icon backIcon;
    private Icon trimIcon;
    private Icon paintIcon;
    private String customname;
    
    public static final float[] arrowX = new float[64];
    public static final float[] arrowY = new float[64];
    public static final float[] arrowDepth = new float[64];
    public static final float[] pitch = new float[64];
    public static final float[] yaw = new float[64];
    public static final DecimalFormat threshold = new DecimalFormat("#.#");
    
    public ItemShield(int id, EnumToolMaterial mat, EnumShieldDesign shield) 
    {
        super(id);
        this.setCreativeTab(ItemListMF.tabArmour);
        material = mat;
        design = shield;

        this.setUnlocalizedName("shield."+design.getTitle()+"."+getMatName());
        this.setTextureName("minefantasy:Shield/"+design.getTitle()+"/"+design.getTitle()+"."+getMatName());

        this.getShareTag();

        this.setMaxDamage((int)(material.getMaxUses() * shield.getThreshold()) / 2);
        this.setMaxStackSize(1);
        this.setHasSubtypes(false);
        GameRegistry.registerItem(this, this.getUnlocalizedName());
    }
    public ItemShield(int id, EnumToolMaterial mat, EnumShieldDesign shield, String name) 
    {
    	this(id, mat, shield);
    	customname = name;
    	this.setUnlocalizedName("shield."+design.getTitle()+"."+name);
        this.setTextureName("minefantasy:Shield/"+design.getTitle()+"/"+design.getTitle()+"."+name);
    }

    @Override
    public void registerIcons(IconRegister reg) 
    {
        super.registerIcons(reg);

        backIcon = reg.registerIcon("minefantasy:Shield/"+design.getTitle()+"/"+design.getTitle()+"."+getMatName()+".back");
        trimIcon = reg.registerIcon("minefantasy:Shield/"+design.getTitle()+"/"+design.getTitle()+"."+getMatName()+".trim");
        paintIcon = reg.registerIcon("minefantasy:Shield/"+design.getTitle()+"/"+design.getTitle()+".paint");

    }
    
    @Override
    public int getArrowCount(ItemStack stack)
    {
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("arrows"))
        {
            return stack.getTagCompound().getShort("arrows");
        }else
            return 0;
    }
    
    @Override
    public void setArrowCount(ItemStack stack, int count)
    {
        if(!stack.hasTagCompound()){
            stack.setTagCompound(new NBTTagCompound());
        }

        //Should never happen, you would need A LOT of arrows for this to happen
        if(count > Short.MAX_VALUE)
        {
            count = Short.MAX_VALUE;
        }

        stack.getTagCompound().setShort("arrows", (short)count);
    }

    public Icon getBackIcon() 
    {
        return backIcon;
    }
    
    public Icon getPaintIcon() 
    {
        return paintIcon;
    }

    public Icon getTrimIcon()
    {
        return trimIcon;
    }

    private String getMatName()
    {
    	if(customname != null && customname.length() > 0)
    	{
    		return customname;
    	}
    	return material.name().toLowerCase();
    }
    
    @Override
    public float getDecayRate(ItemStack shield) 
    {
    	int use = 0;
        return (getItemDecayRate()*(1-use*0.1F) * getMaterialWeightModifier());
    }
    
    private float getItemDecayRate()
    {
    	return 1F/design.getCarryTime()/20F;
	}

	@Override
    public float getRecoveryRate(ItemStack shield)
    {
    	int recover = 0;//EnchantmentHelper.getEnchantmentLevel(BaseEnchantment.shieldRecover.effectId, shield);
    	return 0.01F*(1+recover*0.2F);//should take 5 seconds to fully recover without enchantment
    }

    @Override
    public boolean canBlock(ItemStack shield, DamageSource source) 
    {
    	if(source instanceof DamageSourceShieldfail)
    	{
    		return false;
    	}
    	if(material == ToolMaterialMedieval.DRAGONFORGE && source.isFireDamage())
    	{
    		return true;
    	}
    	
    	if(source == DamageSourceAP.blunt)
    	{
    		return design == EnumShieldDesign.TOWER;
    	}
        return !source.isUnblockable();
    }

    @Override
    public float getDamageDecayRate(ItemStack shield, float amount) 
    {
        return (getDamageDecay() * amount * getMaterialWeightModifier());
    }

    private float getDamageDecay()
    {
    	if(design == EnumShieldDesign.TOWER)
		{
			return 0F;
		}
    	return 1F/(20F * design.getThreshold());
	}

	@Override
    public float getBlockAngle(ItemStack shield)
    {
        return design.getArc();
    }

    @Override
    public int getBashTimer(ItemStack shield)
    {
        return design.getBashTime();
    }
    
    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean extra)
    {
        super.addInformation(item, player, list, extra);

        if(!MineFantasyBase.isBGLoaded())
        {
        	list.add(EnumChatFormatting.DARK_RED+"Requires Battlegear2");
        }
        if(MineFantasyBase.isBGLoaded() || MineFantasyBase.isDebug())
        {
        	list.add("");
	        list.add(EnumChatFormatting.DARK_GREEN+
	                ItemWeaponMF.decimal_format.format((float) 1F / (getItemDecayRate()) / 20F)+
	                StatCollector.translateToLocal("attribute.shield.block.time"));
	
	        int arrowCount = getArrowCount(item);
	        if(arrowCount > 0){
	            list.add(String.format("%s%s %s", EnumChatFormatting.GOLD, arrowCount, StatCollector.translateToLocal("attribute.shield.arrow.count")));
	        }
	        list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("attribute.shield.threshold")
		        	+ " " + threshold.format(getThreshold(item)));
    	}

    }
    /**
     * Return whether the specified armor ItemStack has a color.
     */
    @Override
    public boolean hasColor(ItemStack item)
    {
        return item.hasTagCompound() && item.getTagCompound().hasKey("display") && item.getTagCompound().getCompoundTag("display").hasKey("color");
    }

    private float getMaterialWeightModifier()
    {
    	if(material == ToolMaterialMedieval.MITHRIL)
    	{
    		return 0.75F;
    	}
    	return 1.0F;
    }
    /**
     * Return the color for the specified armor ItemStack.
     */
    @Override
    public int getColor(ItemStack item)
    {
        {
            NBTTagCompound nbttagcompound = item.getTagCompound();

            if (nbttagcompound == null)
            {
                return getDefaultColor(item);
            }
            else
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
                return nbttagcompound1 == null ? getDefaultColor(item): (nbttagcompound1.hasKey("color") ? nbttagcompound1.getInteger("color") : getDefaultColor(item));
            }
        }
    }

    /**
     * Remove the color from the specified armor ItemStack.
     */
    @Override
    public void removeColor(ItemStack item)
    {
        NBTTagCompound nbttagcompound = item.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1.hasKey("color"))
            {
                nbttagcompound1.removeTag("color");
            }
        }
    }

    @Override
    public int getDefaultColor(ItemStack stack)
    {
        return Color.WHITE.getRGB();
    }

    public float getScale(ItemStack item)
    {
    	return design.getScale();
    }
    
    @Override
    public void setColor(ItemStack stack, int colour)
    {

        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

        if (!nbttagcompound.hasKey("display"))
        {
            nbttagcompound.setCompoundTag("display", nbttagcompound1);
        }

        nbttagcompound1.setInteger("color", colour);
    }

	@Override
	public boolean isEnchantable(Enchantment baseEnchantment, ItemStack stack) 
	{
		return true;
	}
	
	@Override
	public int getItemEnchantability()
	{
        return material.getEnchantability();
    }

	@Override
	public boolean sheatheOnBack(ItemStack item)
	{
		return true;
	}
	
	public float getThreshold(ItemStack item)
	{
		return getThreshold(item, null);
	}
	public float getThreshold(ItemStack item, DamageSource src)
	{
		float mp = 1.0F;
		if(src != null)
		{
			if(design == EnumShieldDesign.TOWER && src == DamageSourceAP.blunt)
			{
				mp = 0.25F;
			}
		}
		return ((4F + material.getDamageVsEntity())*2F * design.getThreshold()) * mp;
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		if(!MineFantasyBase.isBGLoaded() && !MineFantasyBase.isDebug())
		{
			return;
		}
		
		if(id != ItemListMF.bucklerBronze.itemID)
			return;
		
		add(list, ItemListMF.shieldWood);
		add(list, ItemListMF.shieldIronbark);
		add(list, ItemListMF.shieldEbony);
		
		add(list, ItemListMF.bucklerBronze);
		add(list, ItemListMF.bucklerIron);
		add(list, ItemListMF.bucklerGuilded);
		add(list, ItemListMF.bucklerSteel);
		add(list, ItemListMF.bucklerEncrusted);
		add(list, ItemListMF.bucklerDragonforge);
		add(list, ItemListMF.bucklerDeepIron);
		add(list, ItemListMF.bucklerMithril);
		
		add(list, ItemListMF.kiteBronze);
		add(list, ItemListMF.kiteIron);
		add(list, ItemListMF.kiteGuilded);
		add(list, ItemListMF.kiteSteel);
		add(list, ItemListMF.kiteEncrusted);
		add(list, ItemListMF.kiteDragonforge);
		add(list, ItemListMF.kiteDeepIron);
		add(list, ItemListMF.kiteMithril);
		
		add(list, ItemListMF.towerBronze);
		add(list, ItemListMF.towerIron);
		add(list, ItemListMF.towerGuilded);
		add(list, ItemListMF.towerSteel);
		add(list, ItemListMF.towerEncrusted);
		add(list, ItemListMF.towerDragonforge);
		add(list, ItemListMF.towerDeepIron);
		add(list, ItemListMF.towerMithril);
    }

	private void add(List list, Item item)
	{
		list.add(new ItemStack(item));
	}

	static
    {
        for(int i = 0; i < 64; i++)
        {
            double r = Math.random()*5;
            double theta = Math.random()*Math.PI*2;

            arrowX[i] = (float)(r * Math.cos(theta));
            arrowY[i] = (float)(r * Math.sin(theta));
            arrowDepth[i] = (float)(Math.random()* 0.5 + 0.5F);

            pitch[i] = (float)(Math.random()*50 - 25);
            yaw[i] = (float)(Math.random()*50 - 25);
        }
    }

	
	public boolean onHit(ItemStack shield, EntityLivingBase defender, DamageSource source, EntityLivingBase attacker, float dam)
	{
		boolean damage = true;
		if(source instanceof DamageSourceShieldfail)
		{
			return false;
		}
		
		if(material == ToolMaterialMedieval.DRAGONFORGE && source.getSourceOfDamage() instanceof EntityLivingBase)
		{
			attacker.setFire(20);
			defender.extinguish();
			damage = false;
			
		}
		if(material == ToolMaterialMedieval.ORNATE)
		{
			if(attacker.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
			{
				float deflect = dam;
				if(source.isProjectile())
				{
					deflect /= 2;
				}
				attacker.attackEntityFrom(DamageSource.magic, deflect);
				damage = false;
			}
		}
		float pass = dam - this.getThreshold(shield, source);
		
		if(pass > 0 && !defender.worldObj.isRemote && !defender.isDead)
		{
			defender.hurtResistantTime = 0;
 			defender.attackEntityFrom(new DamageSourceShieldfail(source), pass);
		}
		return damage;
	}
}
