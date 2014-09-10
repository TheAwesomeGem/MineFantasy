package minefantasy.api.hound;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHoundWeapon extends Item implements IHoundEquipment, IHoundApparel, IHoundWeapon
{
	protected float weaponDamage;
    protected final EnumToolMaterial toolMaterial;

    /**
     * A forged Jaw Item. Fixed to head
     * @param id The item ID
     * @param material The material
     */
    public ItemHoundWeapon(int id, EnumToolMaterial material)
    {
        super(id);
        this.toolMaterial = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses()*2);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.weaponDamage = 4 + material.getDamageVsEntity();
    }
    
    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List desc, boolean flag) {
        super.addInformation(item, player, desc, flag);
        if(getRequiredStr() > 0 || getRequiredSta() > 0 || getRequiredEnd() > 0)
        {
	        desc.add("Requirments:");
	        if(getRequiredStr() > 0)	desc.add("Attack: " + getRequiredStr());
	        if(getRequiredEnd() > 0)	desc.add("Defense: " + getRequiredEnd());
	        if(getRequiredSta() > 0)	desc.add("Stamina: " + getRequiredSta());
        }	
	}

    
    @Override
    public float getDamage(Entity tar)
    {
        return weaponDamage;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack weapon, EntityLiving target, EntityLiving user)
    {
        weapon.damageItem(1, user);
        return true;
    }
    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }

    public String getMaterialName()
    {
        return this.toolMaterial.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack item1, ItemStack item2)
    {
        return this.toolMaterial.getToolCraftingMaterial() == item2.itemID ? true : super.getIsRepairable(item1, item2);
    }

	@Override
	public int getPiece() {
		return 0;
	}

	@Override
	public int getRequiredStr() {
		return 0;
	}

	@Override
	public int getRequiredEnd() {
		return 0;
	}

	@Override
	public int getRequiredSta() {
		return 0;
	}

	@Override
	public String getTexture() {
		return null;
	}

	@Override
	public String getOverlay() {
		return null;
	}

	@Override
	public float getMobilityModifier() {
		// TODO Auto-generated method stub
		return 0;
	}
}
