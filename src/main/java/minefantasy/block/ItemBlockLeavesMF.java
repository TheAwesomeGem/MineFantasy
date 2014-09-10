package minefantasy.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.ColorizerFoliage;

public class ItemBlockLeavesMF extends ItemBlock
{
    public ItemBlockLeavesMF(int id)
    {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int meta)
    {
        return meta | 4;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int meta)
    {
        return BlockListMF.leaves.getIcon(0, meta);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int id)
    {
        int j = item.getItemDamage();
        return (j & 1) == 1 ? ColorizerFoliage.getFoliageColorPine() : ((j & 2) == 2 ? ColorizerFoliage.getFoliageColorBirch() : ColorizerFoliage.getFoliageColorBasic());
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack item)
    {
        int i = item.getItemDamage();

        if (i < 0 || i >= BlockLeavesMF.LEAF_TYPES.length)
        {
            i = 0;
        }

        return StatCollector.translateToLocal("tile.leaves." + BlockLeavesMF.LEAF_TYPES[i]);
    }
}
