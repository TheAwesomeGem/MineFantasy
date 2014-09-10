package minefantasy.api.tanner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;




/**
 * 
 * @author AnonymousProductions
 *
 */
public class LeathercuttingRecipes
{
    private static final LeathercuttingRecipes tanningBase = new LeathercuttingRecipes();

    /** The list of smelting results. */
    private Map tanningList = new HashMap();
    private Map experienceList = new HashMap();
    private Map metaTanningList = new HashMap();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final LeathercuttingRecipes instance()
    {
        return tanningBase;
    }

    private LeathercuttingRecipes()
    {
    }

    /**
     * Adds a smelting recipe.
     */
    public void addCutting(int id, ItemStack result, float exp)
    {
        this.tanningList.put(Integer.valueOf(id), result);
        this.experienceList.put(Integer.valueOf(result.itemID), Float.valueOf(exp));
    }

    public Map getCuttingList()
    {
        return this.tanningList;
    }

    /**
     * Add a metadata-sensitive furnace recipe
     * @param itemID The Item ID
     * @param metadata The Item Metadata
     * @param itemstack The ItemStack for the result
     */
    public void addCutting(int itemID, int metadata, ItemStack itemstack)
    {
        metaTanningList.put(Arrays.asList(itemID, metadata), itemstack);
    }
    /**
     * Add a metadata-sensitive furnace recipe
     * @param itemID The Item ID
     * @param itemstack The ItemStack for the result
     */
    public void addCutting(int itemID, ItemStack itemstack)
    {
        metaTanningList.put(Arrays.asList(itemID, 0), itemstack);
    }
    /**
     * Add a metadata-sensitive furnace recipe
     * @param itemID The Item ID
     * @param itemstack The ItemStack for the result
     */
    public void addCutting(ItemStack item, ItemStack itemstack)
    {
        metaTanningList.put(Arrays.asList(item.itemID, item.getItemDamage()), itemstack);
    }
    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    public ItemStack getCuttingResult(ItemStack item) 
    {
        if (item == null)
        {
            return null;
        }
        ItemStack ret = (ItemStack)metaTanningList.get(Arrays.asList(item.itemID, item.getItemDamage()));
        if (ret != null) 
        {
            return ret;
        }
        return (ItemStack)tanningList.get(Integer.valueOf(item.itemID));
    }
}
