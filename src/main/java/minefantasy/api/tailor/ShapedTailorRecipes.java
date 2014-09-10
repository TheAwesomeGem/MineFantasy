package minefantasy.api.tailor;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * 
 * @author AnonymousProductions
 *
 */
public class ShapedTailorRecipes implements ITailorRecipe
{
    /** How many horizontal slots this recipe is wide. */
    private int recipeWidth;
    
    /** How many vertical slots this recipe uses. */
    private int recipeHeight;
    
    /** Is a array of ItemStack that composes the recipe. */
    private ItemStack[] recipeItems;

    /** Is the ItemStack that you get when craft the recipe. */
    private ItemStack recipeOutput;
    
    private final int stitchCount;
    private final int needleTier;
    private final float stitchTime;
    private final int stringNeeded;

    /** Is the itemID of the output item that you get when craft the recipe. */
    public final int recipeOutputItemID;

    public ShapedTailorRecipes(int wdth, int heit, ItemStack[] inputs, ItemStack output, int tier, int stitches, float time, int string)
    {
        this.recipeOutputItemID = output.itemID;
        this.recipeWidth = wdth;
        this.recipeHeight = heit;
        this.recipeItems = inputs;
        this.recipeOutput = output;
        this.stitchCount = stitches;
        this.stitchTime = time;
        this.needleTier = tier;
        this.stringNeeded = string;
    }

    public ItemStack getRecipeOutput()
    {
        return this.recipeOutput;
    }
    public int getStitchCount()
    {
    	return stitchCount;
    }
    public float getStitchTime()
    {
    	return stitchTime;
    }
    public int getTier()
    {
    	return needleTier;
    }
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting matrix)
    {
        for (int var2 = 0; var2 <= 4 - this.recipeWidth; ++var2)
        {
            for (int var3 = 0; var3 <= 4 - this.recipeHeight; ++var3)
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
        for (int var5 = 0; var5 < 4; ++var5)
        {
            for (int var6 = 0; var6 < 4; ++var6)
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
    public int getString()
    {
    	return this.stringNeeded;
    }
}
