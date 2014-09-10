package minefantasy.block;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemBlockStorageMF extends ItemBlock{

    public ItemBlockStorageMF(int id)
    {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    
    
    @Override
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        for (int n = 0; n < 9; ++n)
        {
        	if(n != 6)
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
        int t = itemstack.getItemDamage();
        String tier = "";
        
        if(t == 0)
        {
            tier = StatCollector.translateToLocal("tier.steel");
        }
        if(t == 1)
        {
            tier = StatCollector.translateToLocal("tier.copper");
        }
        if(t == 2)
        {
            tier = StatCollector.translateToLocal("tier.tin");
        }
        if(t == 3)
        {
            tier = StatCollector.translateToLocal("tier.bronze");
        }
        if(t == 4)
        {
            tier = StatCollector.translateToLocal("tier.mithril");
        }
        if(t == 5)
        {
            tier = StatCollector.translateToLocal("tier.silver");
        }
        if(t == 7)
        {
            tier = StatCollector.translateToLocal("tier.iron.wrought");
        }
        if(t == 8)
        {
            tier = StatCollector.translateToLocal("tier.iron.deep");
        }
        
        return StatCollector.translateToLocal("block.blockstore") + " " + tier;
    }
}
