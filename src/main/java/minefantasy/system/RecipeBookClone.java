package minefantasy.system;

import minefantasy.item.ItemListMF;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeBookClone implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting craftMatrix, World world)
    {
        int var3 = 0;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < craftMatrix.getSizeInventory(); ++var5)
        {
            ItemStack var6 = craftMatrix.getStackInSlot(var5);

            if (var6 != null)
            {
                if (var6.itemID == Item.writtenBook.itemID)
                {
                    if (var4 != null)
                    {
                        return false;
                    }

                    var4 = var6;
                }
                else
                {
                    if (var6.itemID != Item.book.itemID)
                    {
                        return false;
                    }

                    ++var3;
                }
            }
        }

        return var4 != null && var3 > 0;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting craftMatrix)
    {
        int var2 = 0;
        ItemStack var3 = null;

        for (int var4 = 0; var4 < craftMatrix.getSizeInventory(); ++var4)
        {
            ItemStack var5 = craftMatrix.getStackInSlot(var4);

            if (var5 != null)
            {
                if (var5.itemID == Item.writtenBook.itemID)
                {
                    if (var3 != null)
                    {
                        return null;
                    }

                    var3 = var5;
                }
                else
                {
                    if (var5.itemID != Item.book.itemID)
                    {
                        return null;
                    }

                    ++var2;
                }
            }
        }

        if (var3 != null && var2 >= 1)
        {
            ItemStack var6 = new ItemStack(Item.writtenBook, var2 + 1, var3.getItemDamage());

            if (var3.hasDisplayName())
            {
                var6.setItemName(var3.getDisplayName());
            }
    			var6.setTagCompound(var3.getTagCompound());

            return var6;
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 9;
    }

    public ItemStack getRecipeOutput()
    {
        return null;
    }
}
