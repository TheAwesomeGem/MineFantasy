package minefantasy.api.tailor;

import java.util.Comparator;

import net.minecraft.src.*;

/**
 * 
 * @author AnonymousProductions
 *
 */
class RecipeSorterTailor implements Comparator
{
    final CraftingManagerTailor craftingManager;

    RecipeSorterTailor(CraftingManagerTailor manager)
    {
        this.craftingManager = manager;
    }

    public int compareRecipes(ITailorRecipe recipe1, ITailorRecipe recipe2)
    {
        return recipe2 instanceof ShapedTailorRecipes ? 1 : (recipe1 instanceof ShapedTailorRecipes ? -1 : (recipe2.getRecipeSize() < recipe1.getRecipeSize() ? -1 : (recipe2.getRecipeSize() > recipe1.getRecipeSize() ? 1 : 0)));
    }

    public int compare(Object input1, Object input2)
    {
        return this.compareRecipes((ITailorRecipe)input1, (ITailorRecipe)input2);
    }
}
