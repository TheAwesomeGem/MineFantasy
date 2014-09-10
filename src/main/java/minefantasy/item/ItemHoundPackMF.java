package minefantasy.item;

import java.util.Iterator;
import java.util.List;

import minefantasy.api.hound.IHoundEquipment;
import minefantasy.api.hound.ItemHoundPack;
import minefantasy.system.data_minefantasy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHoundPackMF extends ItemHoundPack{

	private String texture;
	public int stren;
	public int stamin;
	
	public ItemHoundPackMF(int id, int t, int rows, String tex, int loy, int str, int sta)
	{
		super(id, t, rows);
		texture = tex;
		stren = str;
		stamin = sta;
		this.setCreativeTab(ItemListMF.tabPets);
	}
	
	public String getTexture()
	{
		return data_minefantasy.image("/mob/" + texture + ".png");
	}

	@Override
	public int getRequiredStr() {
		return 0;//stren;
	}

	@Override
	public int getRequiredSta() {
		return 0;//stamin;
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
}
