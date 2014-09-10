package minefantasy.api.anvil;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRepairHammer extends Item{

	public boolean canRepairEnchant;
	public float effectivness;
	public float maxRepair;
	private int level;
	
	/**
	 * Used to make custom repair hammers
	 * @param id The item ID
	 * @param dam The max uses (basic hammer is 32)
	 * @param pwr The effectiveness(basic hammer is 0.25F)
	 * @param max The maximum repair level % As a decimal - default 0.8F (80%)
	 * @param enchant True if the hammer can repair enchanted items
	 */
	public ItemRepairHammer(int id, int dam, float pwr, float max, boolean enchant, int lvl) {
		super(id);
		setMaxDamage(dam);
		level = lvl;
		maxRepair = max;
		setMaxStackSize(1);
		effectivness = pwr;
		this.setFull3D();
		canRepairEnchant = enchant;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack item)
	{
		switch(level)
		{
		case 0:
			return EnumRarity.common;
		case 1:
			return EnumRarity.uncommon;
		case 2:
			return EnumRarity.rare;
		case 3:
			return EnumRarity.epic;
		}
		return EnumRarity.common;
	}
	@Override
    public void addInformation(ItemStack item, EntityPlayer player, List desc, boolean flag) {
        super.addInformation(item, player, desc, flag);
        desc.add("Efficiency: " + effectivness);
        desc.add("Max: " + maxRepair*100 + "%");
	}
	
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
}
