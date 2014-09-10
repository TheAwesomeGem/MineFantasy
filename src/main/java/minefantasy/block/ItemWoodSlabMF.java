package minefantasy.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemWoodSlabMF extends ItemSlab{


    public ItemWoodSlabMF(int id, BlockHalfSlab half,
			BlockHalfSlab full, boolean isFull) {
		super(id, half, full, isFull);
	}

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        int i = itemstack.getItemDamage();
        switch(i)
        {
            case 0:
                return StatCollector.translateToLocal("tile.ironbark.half");
            case 1:
                return StatCollector.translateToLocal("tile.ebony.half");
            case 2:
                return StatCollector.translateToLocal("tile.rePlanks.half");
            case 3:
                return StatCollector.translateToLocal("tile.hayRoof.half");
        }
        return "Wood Slab";
    }
	
	@Override
	public EnumRarity getRarity(ItemStack item)
	{
		if(item.getItemDamage() == 1)
		{
			return EnumRarity.uncommon;
		}
		return EnumRarity.common;
	}
}
