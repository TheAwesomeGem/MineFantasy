package minefantasy.api.tailor;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

/**
 * 
 * @author AnonymousProductions
 *
 */
public interface ITailorRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(InventoryCrafting var1);

    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getCraftingResult(InventoryCrafting var1);
    int getStitchCount();
    float getStitchTime();
    /**
     * Returns the size of the recipe area
     */
    int getRecipeSize();
    int getTier();
    int getString();
    ItemStack getRecipeOutput();
}
