package minefantasy.api.anvil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

 
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * 
 * @author AnonymousProductions
 *
 */
public class CraftingManagerAnvil
{
    /** The static instance of this class */
    private static final CraftingManagerAnvil instance = new CraftingManagerAnvil();

    /** A list of all the recipes added */
    public List recipes = new ArrayList();

    /**
     * Returns the static instance of this class
     */
    public static CraftingManagerAnvil getInstance()
    {
        return instance;
    }

    private CraftingManagerAnvil()
    {
        Collections.sort(this.recipes, new RecipeSorterAnvil(this));
        System.out.println("MineFantasy: Anvil recipes initiating");
    }

    /**
     * Adds a recipe. See spreadsheet on first page for details.
     */
    public void addRecipe(ItemStack result, boolean hot, float exp, int hammer, int anvil, int time, Object ... input)
    {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var9;

        if (input[var4] instanceof String[])
        {
            String[] var7 = (String[])((String[])input[var4++]);
            String[] var8 = var7;
            var9 = var7.length;

            for (int var10 = 0; var10 < var9; ++var10)
            {
                String var11 = var8[var10];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        }
        else
        {
            while (input[var4] instanceof String)
            {
                String var13 = (String)input[var4++];
                ++var6;
                var5 = var13.length();
                var3 = var3 + var13;
            }
        }

        HashMap var14;

        for (var14 = new HashMap(); var4 < input.length; var4 += 2)
        {
            Character var16 = (Character)input[var4];
            ItemStack var17 = null;

            if (input[var4 + 1] instanceof Item)
            {
                var17 = new ItemStack((Item)input[var4 + 1], 1, 32767);
            }
            else if (input[var4 + 1] instanceof Block)
            {
                var17 = new ItemStack((Block)input[var4 + 1], 1, 32767);
            }
            else if (input[var4 + 1] instanceof ItemStack)
            {
                var17 = (ItemStack)input[var4 + 1];
            }

            var14.put(var16, var17);
        }

        ItemStack[] var15 = new ItemStack[var5 * var6];

        for (var9 = 0; var9 < var5 * var6; ++var9)
        {
            char var18 = var3.charAt(var9);

            if (var14.containsKey(Character.valueOf(var18)))
            {
                var15[var9] = ((ItemStack)var14.get(Character.valueOf(var18))).copy();
            }
            else
            {
                var15[var9] = null;
            }
        }

        this.recipes.add(new ShapedAnvilRecipes(var5, var6, var15, result, time, hammer, anvil, exp, hot));
    }

    public void addShapelessRecipe(ItemStack output, boolean hot, float experiance, int hammer, int anvil, int time, Object ... input)
    {
        ArrayList var3 = new ArrayList();
        Object[] var4 = input;
        int var5 = input.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            Object var7 = var4[var6];

            if (var7 instanceof ItemStack)
            {
                var3.add(((ItemStack)var7).copy());
            }
            else if (var7 instanceof Item)
            {
                var3.add(new ItemStack((Item)var7));
            }
            else
            {
                if (!(var7 instanceof Block))
                {
                    throw new RuntimeException("MineFantasy: Invalid shapeless anvil recipe!");
                }

                var3.add(new ItemStack((Block)var7));
            }
        }

        this.recipes.add(new ShapelessAnvilRecipes(output, experiance, hammer, anvil, time, var3, hot));
    }
    public ItemStack findMatchingRecipe(InventoryCrafting matrix)
    {
        int var2 = 0;
        ItemStack var3 = null;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < matrix.getSizeInventory(); ++var5)
        {
            ItemStack var6 = matrix.getStackInSlot(var5);

            if (var6 != null)
            {
                if (var2 == 0)
                {
                    var3 = var6;
                }

                if (var2 == 1)
                {
                    var4 = var6;
                }

                ++var2;
            }
        }

        if (var2 == 2 && var3.itemID == var4.itemID && var3.stackSize == 1 && var4.stackSize == 1 && Item.itemsList[var3.itemID].isRepairable())
        {
            Item var10 = Item.itemsList[var3.itemID];
            int var12 = var10.getMaxDamage() - var3.getItemDamageForDisplay();
            int var7 = var10.getMaxDamage() - var4.getItemDamageForDisplay();
            int var8 = var12 + var7 + var10.getMaxDamage() * 10 / 100;
            int var9 = var10.getMaxDamage() - (var8)*2;

            if (var9 < 0)
            {
                var9 = 0;
            }

            return new ItemStack(var3.itemID, 1, var9);
        }
        else
        {
            Iterator var11 = this.recipes.iterator();
            IAnvilRecipe var13;

            do
            {
                if (!var11.hasNext())
                {
                    return null;
                }

                var13 = (IAnvilRecipe)var11.next();
            }
            while (!var13.matches(matrix));

            return var13.getCraftingResult(matrix);
        }
    }
    
    
    
    public ItemStack findMatchingRecipe(IAnvil anvil, InventoryCrafting matrix)
    {
    	int time = 200;
    	int anvi = 1;
    	boolean hot = false;
    	int hammer = 0;
        int var2 = 0;
        ItemStack var3 = null;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < matrix.getSizeInventory(); ++var5)
        {
            ItemStack var6 = matrix.getStackInSlot(var5);

            if (var6 != null)
            {
                if (var2 == 0)
                {
                    var3 = var6;
                }

                if (var2 == 1)
                {
                    var4 = var6;
                }

                ++var2;
            }
        }

        if (var2 == 2 && var3.itemID == var4.itemID && var3.stackSize == 1 && var4.stackSize == 1 && Item.itemsList[var3.itemID].isRepairable())
        {
            Item var10 = Item.itemsList[var3.itemID];
            int var12 = var10.getMaxDamage() - var3.getItemDamageForDisplay();
            int var7 = var10.getMaxDamage() - var4.getItemDamageForDisplay();
            int var8 = var12 + var7 + var10.getMaxDamage() * 10 / 100;
            int var9 = var10.getMaxDamage() - var8;

            if (var9 < 0)
            {
                var9 = 0;
            }

            return new ItemStack(var3.itemID, 1, var9);
        }
        else
        {
            Iterator var11 = this.recipes.iterator();
            IAnvilRecipe var13 = null;

            while(var11.hasNext())
            {
            	IAnvilRecipe rec = (IAnvilRecipe)var11.next();
            	
            	if(rec.matches(matrix))
            	{
            		var13 = rec;
            	}
            }

            if(var13 != null)
            {
	            time = var13.getCraftTime();
	            hammer = var13.getRecipeHammer();
	            anvi = var13.getAnvil();
	            hot = var13.outputHot();
	            
	            anvil.setForgeTime(time);
	            anvil.setHammerUsed(hammer);
	            anvil.setRequiredAnvil(anvi);
	            anvil.setHotOutput(hot);
	            
	            return var13.getCraftingResult(matrix);
            }
            return null;
        }
    }

    /**
     * returns the List<> of all recipes
     */
    public List getRecipeList()
    {
        return this.recipes;
    }
}
