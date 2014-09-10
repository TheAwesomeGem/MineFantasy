package minefantasy.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.block.BlockListMF;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public final class CreativeTabMF extends CreativeTabs
{
	private int type;
    CreativeTabMF(int id, String item, int t)
    {
        super(id, item);
        type = t;
    }

    @SideOnly(Side.CLIENT)

    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex()
    {
    	switch(type)
    	{
    	case 0:
    		return ItemListMF.swordSteelForged.itemID;
    	case 1:
    		return ItemListMF.helmetSteelPlate.itemID;
    	case 2:
    		return ItemListMF.sawSteel.itemID;
    	case 3:
    		return BlockListMF.tailor.blockID;
    	case 4:
    		return ItemListMF.shortbow.itemID;
    	}
    	return ItemListMF.swordSteelForged.itemID;
    }
}
