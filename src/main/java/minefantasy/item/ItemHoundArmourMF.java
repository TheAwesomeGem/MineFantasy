package minefantasy.item;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.api.hound.IHoundApparel;
import minefantasy.api.hound.IHoundArmour;
import minefantasy.api.hound.IHoundEquipment;
import minefantasy.api.weapon.DamageSourceAP;
import minefantasy.item.armour.EnumArmourMF;
import minefantasy.system.data_minefantasy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

//EntityHound
public class ItemHoundArmourMF extends Item implements IHoundArmour, IHoundEquipment, IHoundApparel
{
	private int plateEnd = 30;
    public final int endurance;
    public String texture;
    public boolean isPlate;
    public int slot;
    public EnumArmourMF material;
    
    public ItemHoundArmourMF(int id, EnumArmourMF material, boolean plate, String tex, int part, int end)
    {
        super(id);
        texture = tex;
        this.isPlate = plate;
        this.material = material;
        endurance = end;
        slot = part;
        setMaxDamage((int)(material.durability * (slot == 0 ? 15F : 20F) * (plate ? ArmourDesign.PLATE.protection : ArmourDesign.CHAIN.protection)));
        
        this.maxStackSize = 1;
        this.setCreativeTab(ItemListMF.tabPets);
    }
    
    private float getSlotMod(int part) 
    {
		return part == 0 ? 5 : 10;
	}
    
    @Override
    public String getTexture()
    {
    	if(isPlate)
    	{
    		return null;
    	}
    	return data_minefantasy.image("/mob/hound_armour/" + texture + "_mail.png");
    }

    @Override
	public String getOverlay() 
	{
		if(isPlate)
    	{
			return data_minefantasy.image("/mob/hound_armour/" + texture + "_plate.png");
    	}
		return null;
	}

	@Override
	public int getPiece()
	{
		return slot;
	}

	@Override
	public int getRequiredEnd() {
		return 0;//endurance;
	}
	
	
	@Override
	public float getMobilityModifier() 
	{
		float mod = 0.0F;
		if(isPlate)
		{
			float slow = 0.25F * material.armourWeight / 15F * this.getSlotMod(slot);
			mod -= slow;
		}
		return mod;
	}
	
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Pets/"+name);
		return super.setUnlocalizedName(name);
    }
	
	@Override
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		
    }

	@Override
	public int getRequiredStr() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRequiredSta() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean shouldDamage(DamageSource source) 
	{
		if(material == EnumArmourMF.GUILDED)
		{
			if(source.getSourceOfDamage() != null && source.getSourceOfDamage() instanceof EntityLivingBase)
			{
				if(((EntityLivingBase)source.getSourceOfDamage()).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
				{
					return false;
				}
			}
		}
		if(material == EnumArmourMF.DRAGONFORGE)
		{
			if(source.isFireDamage())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public float getResistance(DamageSource source)
	{
		float rating = material.armourRating;
		if(source instanceof DamageSourceAP)
		{
			rating = isPlate ? rating/2F : 0F;
		}
		if(source.isFireDamage())
		{
			if(material == EnumArmourMF.DRAGONFORGE)
			{
				float dfBonus = 100F / 15F * getSlotMod(slot) * getDesignAC();
				if(dfBonus > 0.9F)
				{
					dfBonus = 1.0F;
				}
				return dfBonus;
			}
		}
		if(source.isUnblockable() && !(source instanceof DamageSourceAP))
		{
			return 0;
		}
		return (rating * this.getSlotMod(slot) * getDesignAC()) / 25F;
	}

	private float getDesignAC()
	{
		return isPlate ? 1.0F : 0.8F;
	}

	@Override
	public float getACDisplayPercent() 
	{
		return (material.armourRating * this.getSlotMod(slot) * getDesignAC()) / 25F;
	}
	
}
