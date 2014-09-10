package minefantasy.block;

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
public class ItemBlockSlate extends ItemBlock{

    public ItemBlockSlate(int id)
    {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        for (int n = 0; n < BlockSlate.amount; ++n)
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
            case 0:
                return StatCollector.translateToLocal("tile.slate");
            case 1:
                return StatCollector.translateToLocal("tile.slate.tile");
            case 2:
                return StatCollector.translateToLocal("tile.slate.tile.d");
            case 3:
                return StatCollector.translateToLocal("tile.slate.brick");
        }
        return StatCollector.translateToLocal("tile.slate");
    }
}
