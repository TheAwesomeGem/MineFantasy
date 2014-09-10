package minefantasy.block.special;

import java.util.List;

import net.minecraft.block.Block;
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
public class ItemBlockFurnaceMF extends ItemBlock{

    public ItemBlockFurnaceMF(int id)
    {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        for (int n = 0; n < 5; ++n)
        {
            list.add(new ItemStack(id, 1, n));
        }
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage*2;
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        int t = itemstack.getItemDamage();
        String tier = "";
        
        if(t == 0)
        {
        	return StatCollector.translateToLocal("tile.furnace.name") + " " + StatCollector.translateToLocal("block.furnace.heater");
        }
        
        if(t == 1)
        {
            tier = StatCollector.translateToLocal("tier.bronze");
        }
        if(t == 2)
        {
            tier = StatCollector.translateToLocal("tier.iron");
        }
        if(t == 3)
        {
            tier = StatCollector.translateToLocal("tier.steel");
        }
        if(t == 4)
        {
            tier = StatCollector.translateToLocal("tier.iron.deep");
        }
        
        return tier + " " + StatCollector.translateToLocal("tile.furnace.name");
    }
}
