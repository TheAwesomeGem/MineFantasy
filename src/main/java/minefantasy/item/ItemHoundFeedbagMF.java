package minefantasy.item;

import java.util.Iterator;
import java.util.List;

import minefantasy.api.hound.IHoundEquipment;
import minefantasy.api.hound.ItemHoundFeedbag;
import minefantasy.system.data_minefantasy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHoundFeedbagMF extends ItemHoundFeedbag{

	private String texture;
	public int stamin;
	
	public ItemHoundFeedbagMF(int id, int max, String tex, int sta)
	{
		super(id, max);
		texture = tex;
		stamin = sta;
		this.setCreativeTab(ItemListMF.tabPets);
	}
	
	@Override
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        if(this == ItemListMF.hound_feed)
        {
        	Iterator var4 = EntityList.entityEggs.values().iterator();

            while (var4.hasNext())
            {
                EntityEggInfo var5 = (EntityEggInfo)var4.next();
                
                String var3 = EntityList.getStringFromID(var5.spawnedID);

                if (var3 != null && var3 == "HoundMF")
                {
                	list.add(new ItemStack(Item.monsterPlacer.itemID, 1, var5.spawnedID));
                }
                
            }
            list.add(new ItemStack(Item.bone));
            list.add(new ItemStack(id, 1, getMaxDamage()-1));
            addTabItems(id, tabs, list);
        }
        else
        super.getSubItems(id, tabs, list);
    }
	
	public String getTexture()
	{
		return data_minefantasy.image("/mob/" + texture + ".png");
	}
	@Override
	public int getRequiredSta() {
		return stamin;
	}
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Pets/"+name);
		return super.setUnlocalizedName(name);
    }
	
	private void addTabItems(int id, CreativeTabs tabs, List list)
    {
		add(list, ItemListMF.transferHound);
		add(list, ItemListMF.hound_sPack);
		add(list, ItemListMF.hound_bPack);
		
		add(list, ItemListMF.hound_BMail);
		add(list, ItemListMF.hound_BMailH);
		add(list, ItemListMF.hound_IMail);
		add(list, ItemListMF.hound_IMailH);
		add(list, ItemListMF.hound_GMail);
		add(list, ItemListMF.hound_GMailH);
		add(list, ItemListMF.hound_SMail);
		add(list, ItemListMF.hound_SMailH);
		add(list, ItemListMF.hound_DMail);
		add(list, ItemListMF.hound_DMailH);
		add(list, ItemListMF.hound_DImail);
		add(list, ItemListMF.hound_DImailH);
		add(list, ItemListMF.hound_MMail);
		add(list, ItemListMF.hound_MMailH);
		
		add(list, ItemListMF.hound_Bplate);
		add(list, ItemListMF.hound_BplateH);
		add(list, ItemListMF.hound_Iplate);
		add(list, ItemListMF.hound_IplateH);
		add(list, ItemListMF.hound_Gplate);
		add(list, ItemListMF.hound_GplateH);
		add(list, ItemListMF.hound_Splate);
		add(list, ItemListMF.hound_SplateH);
		add(list, ItemListMF.hound_Dplate);
		add(list, ItemListMF.hound_DplateH);
		add(list, ItemListMF.hound_Eplate);
		add(list, ItemListMF.hound_EplateH);
		add(list, ItemListMF.hound_DIplate);
		add(list, ItemListMF.hound_DIplateH);
		add(list, ItemListMF.hound_Mplate);
		add(list, ItemListMF.hound_MplateH);
		
		add(list, ItemListMF.hound_Bteeth);
		add(list, ItemListMF.hound_Iteeth);
		add(list, ItemListMF.hound_Oteeth);
		add(list, ItemListMF.hound_Steeth);
		add(list, ItemListMF.hound_Dteeth);
		add(list, ItemListMF.hound_Eteeth);
		add(list, ItemListMF.hound_DIteeth);
		add(list, ItemListMF.hound_Mteeth);
		add(list, ItemListMF.hound_Igteeth);
		
    }
	private void add(List list, Item item) {
		list.add(new ItemStack(item));
	}
}
