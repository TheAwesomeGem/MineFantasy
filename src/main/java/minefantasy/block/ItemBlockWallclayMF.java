package minefantasy.block;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockWallclayMF extends ItemBlock{
	public ItemBlockWallclayMF(int id)
    {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        for (int n = 0; n < 4; ++n)
        {
            list.add(new ItemStack(id, 1, n));
        }
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        int i = itemstack.getItemDamage();
        switch(i)
        {
            case 1:
                return StatCollector.translateToLocal("tile.clayWall.cross");
            case 2:
                return StatCollector.translateToLocal("tile.clayWall.diagonal1");
                
            case 3:
                return StatCollector.translateToLocal("tile.clayWall.diagonal2");
                
        }
        return StatCollector.translateToLocal("tile.clayWall");
    }
}
