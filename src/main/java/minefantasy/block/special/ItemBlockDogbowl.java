package minefantasy.block.special;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
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
public class ItemBlockDogbowl extends ItemBlock{

    public ItemBlockDogbowl(int id)
    {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        for (int n = 0; n < 3; ++n)
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
        String tier = "";
        if(i == 0)
        {
            tier = StatCollector.translateToLocal("tier.wood");
        }
        if(i == 1)
        {
            tier = StatCollector.translateToLocal("tier.iron");
        }
        if(i == 2)
        {
            tier = StatCollector.translateToLocal("tier.steel");
        }
        return tier + " " + StatCollector.translateToLocal("block.dogbowl");
    }
}
