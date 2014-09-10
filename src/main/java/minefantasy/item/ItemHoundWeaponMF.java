package minefantasy.item;

import java.util.List;

import minefantasy.api.hound.ItemHoundWeapon;
import minefantasy.system.data_minefantasy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemHoundWeaponMF extends ItemHoundWeapon {

	public String texture;
	private int strength;
	public ItemHoundWeaponMF(int id, EnumToolMaterial material, String tex, int str, int dam) {
		super(id, material);
		texture = tex;
		strength = str;
        this.setCreativeTab(ItemListMF.tabPets);
        weaponDamage = dam;
	}
	
	@Override
    public EnumRarity getRarity(ItemStack itemStack) {
        if(toolMaterial == ToolMaterialMedieval.IGNOTUMITE)
        {
            if(itemStack.isItemEnchanted())
                return EnumRarity.epic;
            else
                return EnumRarity.rare;
        }
        if(toolMaterial == ToolMaterialMedieval.DRAGONFORGE)
        {
            if(itemStack.isItemEnchanted())
                return EnumRarity.rare;
            else
                return EnumRarity.uncommon;
        }
        return super.getRarity(itemStack);
    }
	
	@Override
	public String getTexture()
	{
		return data_minefantasy.image("/mob/hound_armour/" + texture + "_teeth.png");
	}
	
	@Override
	public int getRequiredStr() {
		return 0;//strength;
	}
	@Override
	public boolean hitEntity(ItemStack weapon, EntityLiving target, EntityLiving user)
    {
		if(this.toolMaterial == ToolMaterialMedieval.DRAGONFORGE)
			target.setFire(20);
		
		if(this.toolMaterial == ToolMaterialMedieval.IGNOTUMITE)
		{
			user.heal(2);
		}
		if(this.toolMaterial == ToolMaterialMedieval.ORNATE)
		{
			if (((EntityLiving) target).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD || target.getClass().getName().endsWith("MoCEntityWarewolf"))
			{
				target.setFire(20);
				target.worldObj.playSoundAtEntity(target, "random.fizz", 1, 1);
			}
		}
		
        return super.hitEntity(weapon, target, user);
    }
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Pets/"+name);
		return super.setUnlocalizedName(name);
    }
	
	@Override
	public float getDamage(Entity tar)
    {
		float dam = weaponDamage;
		
		if(tar != null && tar instanceof EntityLiving && toolMaterial == ToolMaterialMedieval.ORNATE)
		{
			if (((EntityLiving) tar).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD || tar.getClass().getName().endsWith("MoCEntityWarewolf"))
			{
				if(tar.getClass().getName().endsWith("MoCEntityWarewolf"))
				{
					dam *= 10;
				}
				else
				{
					dam *= 2;
				}
			}
		}
		
        return dam;
    }
	
	@Override
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		
    }
}
