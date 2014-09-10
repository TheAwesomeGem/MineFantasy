package minefantasy.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemPetChange extends Item
{

	public ItemPetChange(int id)
	{
		super(id);
		setMaxStackSize(1);
	}
	
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Pets/"+name);
		return super.setUnlocalizedName(name);
    }
	private void addTabItems(int id, CreativeTabs tabs, List list)
    {
    }
}
