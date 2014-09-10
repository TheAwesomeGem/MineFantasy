package minefantasy.api.hound;

import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHoundPack extends Item implements IHoundEquipment, IHoundPackItem{

	public int size;
	public int type;
	
	/**
	 * Basic Storage Item for packs
	 * @param id The item id
	 * @param piece The slot to equip in (0 = head, 1 = body)
	 * @param rows The rows avalible in it's inventory
	 */
	public ItemHoundPack(int id, int piece, int rows)
	{
		super(id);
		this.setMaxStackSize(1);
		size = rows;
		type = piece;
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
	public int getAvalibleRows()
	{
		return size;
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
		return type;
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
