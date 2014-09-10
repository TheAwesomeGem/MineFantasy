package minefantasy.block.special;

import java.util.List;

import minefantasy.block.tileentity.TileEntityAnvil;
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
public class ItemBlockAnvil extends ItemBlock{

    public ItemBlockAnvil(int id)
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
        String size = "";
        
        int t = TileEntityAnvil.anvils[i][1];
        int s = TileEntityAnvil.anvils[i][0];
        if(t == -1)
        {
            tier = StatCollector.translateToLocal("tier.stone");
        }
        if(t == 0)
        {
            tier = StatCollector.translateToLocal("tier.bronze");
        }
        if(t == 1)
        {
            tier = StatCollector.translateToLocal("tier.iron");
        }
        if(t == 2)
        {
            tier = StatCollector.translateToLocal("tier.steel");
        }
        if(t == 3)
        {
            tier = StatCollector.translateToLocal("tier.iron.deep");
        }
        
        if(s == 0 && t >= 0)
        {
            size = StatCollector.translateToLocal("size.small")+ " ";
        }
        
        return size + tier + " " + StatCollector.translateToLocal("tile.anvil.name");
    }
    
    @Override
    public EnumRarity getRarity(ItemStack item)
    {
    	if(item.getItemDamage() >= 5)
    	{
    		return EnumRarity.uncommon;
    	}
    	return EnumRarity.common;
    }
}
