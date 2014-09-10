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
public class TanningRecipes
{
    private static final TanningRecipes tanningBase = new TanningRecipes();

    /** The list of smelting results. */
    private Map tanningList = new HashMap();
    private Map experienceList = new HashMap();
    private Map metaTanningList = new HashMap();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final TanningRecipes instance()
    {
        return tanningBase;
    }

    private TanningRecipes()
    {
    }

    /**
     * Adds a smelting recipe.
     */
    public void addTanning(int id, ItemStack result, float exp)
    {
        this.tanningList.put(Integer.valueOf(id), result);
        this.experienceList.put(Integer.valueOf(result.itemID), Float.valueOf(exp));
    }

    public Map getTanningList()
    {
        return this.tanningList;
    }

    /**
     * Add a metadata-sensitive furnace recipe
     * @param itemID The Item ID
     * @param metadata The Item Metadata
     * @param itemstack The ItemStack for the result
     */
    public void addTanning(int itemID, int metadata, ItemStack itemstack)
    {
        metaTanningList.put(Arrays.asList(itemID, metadata), itemstack);
    }
    
    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    public ItemStack getTanningResult(ItemStack item) 
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
