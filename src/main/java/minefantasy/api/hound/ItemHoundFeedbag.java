package minefantasy.api.hound;

import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHoundFeedbag extends Item implements IHoundEquipment, IHoundPackItem{

	public int size;
	
	/**
	 * Feedbag for hounds (works like dogbowl)
	 * @param id The item id
	 * @See ItemFood for heal amount details
	 * The damage tracks the amount of food. (empty means damage == maxDamage)
	 */
	public ItemHoundFeedbag(int id, int max)
	{
		super(id);
		this.setMaxDamage(max);
		this.setMaxStackSize(1);
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
	/**
	 * @return The texture for the Pack Model
	 */
	public String getTexture()
	{
		return null;
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
	public float getMobilityModifier() {
		return 0;
	}
}
