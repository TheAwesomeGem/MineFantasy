package minefantasy.api.anvil;

import minefantasy.api.Components;
import minefantasy.api.forge.HeatableItem;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * 
 * @author AnonymousProductions
 *
 */
public class ShapedAnvilRecipes implements IAnvilRecipe
{
    /** How many horizontal slots this recipe is wide. */
    private int recipeWidth;
    
    private final int recipeHammer;

    private final boolean outputHot;
    /** How many vertical slots this recipe uses. */
    private int recipeHeight;
    
    /** The Anvil needed to craft */
    private final int anvil;

    /** Is a array of ItemStack that composes the recipe. */
    private ItemStack[] recipeItems;

    /** Is the ItemStack that you get when craft the recipe. */
    private ItemStack recipeOutput;
    
    private final int recipeTime;
    private final float recipeExperiance;

    /** Is the itemID of the output item that you get when craft the recipe. */
    public final int recipeOutputItemID;

    public ShapedAnvilRecipes(int wdth, int heit, ItemStack[] inputs, ItemStack output, int time, int hammer, int anvi, float exp, boolean hot)
    {
    	this.outputHot = hot;
        this.recipeOutputItemID = output.itemID;
        this.recipeWidth = wdth;
        this.anvil = anvi;
        this.recipeHeight = heit;
        this.recipeItems = inputs;
        this.recipeOutput = output;
        this.recipeTime = time;
        this.recipeHammer = hammer;
        this.recipeExperiance = exp;
    }

    public ItemStack getRecipeOutput()
    {
        return this.recipeOutput;
    }
    public int getCraftTime()
    {
    	return recipeTime;
    }
    public float getExperiance()
    {
    	return this.recipeExperiance;
    }
    public int getRecipeHammer()
    {
    	return recipeHammer;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting matrix)
    {
        for (int var2 = 0; var2 <= AnvilProps.globalWidth - this.recipeWidth; ++var2)
        {
            for (int var3 = 0; var3 <= AnvilProps.globalHeight - this.recipeHeight; ++var3)
            {
                if (this.checkMatch(matrix, var2, var3, true))
                {
                    return true;
                }

                if (this.checkMatch(matrix, var2, var3, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean b)
    {
        for (int var5 = 0; var5 < AnvilProps.globalWidth; ++var5)
        {
            for (int var6 = 0; var6 < AnvilProps.globalHeight; ++var6)
            {
                int var7 = var5 - x;
                int var8 = var6 - y;
                ItemStack recipeItem = null;

                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight)
                {
                    if (b)
                    {
                        recipeItem = this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth];
                    }
                    else
                    {
                        recipeItem = this.recipeItems[var7 + var8 * this.recipeWidth];
                    }
                }

                ItemStack inputItem = matrix.getStackInRowAndColumn(var5, var6);

                if (inputItem != null || recipeItem != null)
                {
                    if (inputItem == null && recipeItem != null || inputItem != null && recipeItem == null)
                    {
                        return false;
                    }
                    if(HeatableItem.requiresHeating && HeatableItem.canHeatItem(inputItem))
                    {
                    	return false;
                    }
                    inputItem = getHotItem(inputItem);
                    
                    if(inputItem == null)
                    {
                    	return false;
                    }

                    if (recipeItem.itemID != inputItem.itemID)
                    {
                        return false;
                    }

                    if (recipeItem.getItemDamage() != OreDictionary.WILDCARD_VALUE && recipeItem.getItemDamage() != inputItem.getItemDamage())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private ItemStack getHotItem(ItemStack inputItem) {
    	ItemStack hotItem = Components.getItem("hotItem", 0);
    	
    	//If no hot item exists
    	if(hotItem == null)
    	{
    		return inputItem;
    	}
    	//If the item is heated
    	if(inputItem.itemID == hotItem.itemID)
    	{
    		int temp = getTemp(inputItem);
    		
    		ItemStack ingot = getItem(inputItem);
    		if(ingot != null)
    		{
    			inputItem = ingot;
    		}
    		if(!HeatableItem.canWorkItem(inputItem, temp))
    		{
    			return null;
    		}
    	}
		return inputItem;
	}

    public static int getTemp(ItemStack item)
	{
		NBTTagCompound tag = getNBT(item);
		
		if(tag.hasKey("MFtemp"))return tag.getInteger("MFtemp");
		
		return 0;
	}
	
	public static ItemStack getItem(ItemStack item)
	{
		NBTTagCompound tag = getNBT(item);
		
		if(tag.hasKey("ingotID") && tag.hasKey("ingotMeta"))
		{
			return new ItemStack(tag.getInteger("ingotID"), 1, tag.getInteger("ingotMeta"));
		}
		
		return null;
	}
	private static NBTTagCompound getNBT(ItemStack item)
	{
		if(!item.hasTagCompound())item.setTagCompound(new NBTTagCompound());
		return item.getTagCompound();
	}
	/**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting matrix)
    {
        return new ItemStack(this.recipeOutput.itemID, this.recipeOutput.stackSize, this.recipeOutput.getItemDamage());
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return this.recipeWidth * this.recipeHeight;
    }

	@Override
	public int getAnvil() {
		return anvil;
	}

	@Override
	public boolean outputHot() {
		return this.outputHot;
	}
}
