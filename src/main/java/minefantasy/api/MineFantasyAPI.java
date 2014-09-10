package minefantasy.api;


import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;

import minefantasy.api.anvil.CraftingManagerAnvil;
import minefantasy.api.cooking.FoodPrepRecipe;
import minefantasy.api.cooking.OvenRecipes;
import minefantasy.api.forge.*;
import minefantasy.api.mining.RandomOre;
import minefantasy.api.refine.Alloy;
import minefantasy.api.refine.AlloyRecipes;
import minefantasy.api.refine.BlastFurnaceFuel;
import minefantasy.api.refine.BlastRecipes;
import minefantasy.api.refine.BloomRecipe;
import minefantasy.api.refine.CrushRecipes;
import minefantasy.api.refine.ICustomCrushRecipe;
import minefantasy.api.refine.SpecialFurnaceRecipes;
import minefantasy.api.tailor.CraftingManagerTailor;
import minefantasy.api.tailor.StringRecipes;
import minefantasy.api.tanner.LeathercuttingRecipes;
import minefantasy.api.tanner.TanningRecipes;
import minefantasy.api.targeting.ITargetAllyMF;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
/**
 * 
 * @author AnonymousProductions
 * Scripts for init are included here(works like ModLoader)
 */
public class MineFantasyAPI {
	
	public static boolean modLoaded = false;
	private static List<IMineFantasyPlugin> plugins = new ArrayList();
	
	/**
	 * Registers your plugin so it will initiate with MineFantasy
	 * @param plugin the class to initiate(IMineFantasyPlugin)
	 */
	public static void addPlugin(IMineFantasyPlugin plugin)
	{
		plugins.add(plugin);
	}
	/**
	 * This script is used by MineFantasyBase(the basemod)
	 */
	public static void initAllPlugins()
	{
			System.out.println("MineFantasy: Loading plugins...");
			for(IMineFantasyPlugin plugin: plugins)
			{
				plugin.initWithMineFantasy();
				System.out.println("MineFantasy: plugin " + plugin.pluginName() +" loaded");
			}
			System.out.println("MineFantasy: Finished loading plugins: " + plugins.size() + " plugins loaded");
	}
	/**
	 * 
	 * @return True if MineFantasy is installed
	 * use this to implement alternative recipes
	 * for when MineFantasy is not installed
	 */
	public static boolean isModLoaded()
	{
		return modLoaded;
	}
	
	
	/**
	 * Adds a shaped recipe for anvils with all variables
	 * 
	 * @param result The output item
	 * @param hot True if the result is hot(Does not apply to blocks)
	 * @param experiance the experiance gained from crafting
	 * @param hammerType the hammer required for creation:
	 * 		0 = regular.
	 * 		1 = ornate.
	 * @param anvil the anvil required bronze 0, iron 1, steel 2
	 * @param forgeTime The time taken to forge(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 */
	public static void addAnvilRecipe(ItemStack result, boolean hot, int hammerType, int anvil, int forgeTime, Object... input)
	{
		CraftingManagerAnvil.getInstance().addRecipe(result, hot, 0F, hammerType, anvil, forgeTime, input);
	}
	
	
	/**
	 * Adds a shaped recipe for anvils with additional variables
	 * 
	 * @param result The output item
	 * @param experiance the experiance gained from crafting
	 * @param hammerType the hammer required for creation:
	 * 		0 = regular.
	 * 		1 = ornate.
	 * @param anvil the anvil required bronze 0, iron 1, steel 2
	 * @param forgeTime The time taken to forge(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 */
	public static void addAnvilRecipe(ItemStack result, int hammerType, int anvil, int forgeTime, Object... input)
	{
		CraftingManagerAnvil.getInstance().addRecipe(result, false, 0F, hammerType, anvil, forgeTime, input);
	}
	/**
	 * Adds a shaped recipe for anvils with additional variables
	 * 
	 * @param result The output item
	 * @param experiance the experiance gained from crafting
	 * @param hammerType the hammer required for creation:
	 * 		0 = regular.
	 * 		1 = ornate.
	 * @param forgeTime The time taken to forge(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 */
	public static void addAnvilRecipe(ItemStack result, int hammerType, int forgeTime, Object... input)
	{
		CraftingManagerAnvil.getInstance().addRecipe(result, false, 0F, hammerType, 1, forgeTime, input);
	}
	
	/**
	 * Adds a shaped recipe for anvils
	 * 
	 * @param result The output item
	 * @param forgeTime The time taken to forge(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 * this automatically sets the required hammer to 0 (any) and set 0 experiance
	 */
	public static void addAnvilRecipe(ItemStack result, int forgeTime, Object... input)
	{
		CraftingManagerAnvil.getInstance().addRecipe(result, false, 0F, 0, 1, forgeTime, input);
	}
	

	/**
	 * Adds a shapeless recipe for anvils with all variables
	 * 
	 * @param result The output item
	 * @param hot True if the result is hot(only works for items)
	 * @param anvil The required Anvil
	 * @param hammerType the hammer required for creation:
	 * 		0 = regular.
	 * 		1 = ornate.
	 * @param forgeTime The time taken to forge(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 */
	public static void addShapelessAnvilRecipe(ItemStack result, boolean hot, int anvil, int hammerType, int forgeTime, Object... input)
	{
		CraftingManagerAnvil.getInstance().addShapelessRecipe(result, hot, 0F, hammerType, anvil, forgeTime, input);
	}
	
	/**
	 * Adds a shapeless recipe for anvils with additional variables
	 * 
	 * @param result The output item
	 * @param anvil The required Anvil
	 * @param hammerType the hammer required for creation:
	 * 		0 = regular.
	 * 		1 = ornate.
	 * @param forgeTime The time taken to forge(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 */
	public static void addShapelessAnvilRecipe(ItemStack result, int anvil, int hammerType, int forgeTime, Object... input)
	{
		CraftingManagerAnvil.getInstance().addShapelessRecipe(result, false, 0F, hammerType, anvil, forgeTime, input);
	}
	
	/**
	 * Adds a shapeless recipe for anvils with additional variables
	 * 
	 * @param result The output item
	 * * @param hot: True if the result is hot(only works for items)
	 * @param hammerType the hammer required for creation:
	 * 		0 = regular.
	 * 		1 = ornate.
	 * @param forgeTime The time taken to forge(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 */
	public static void addShapelessAnvilRecipe(ItemStack result, int hammerType, int forgeTime, Object... input)
	{
		CraftingManagerAnvil.getInstance().addShapelessRecipe(result, false, 0F, hammerType, 1, forgeTime, input);
	}

	
	/**
	 * Adds a shapeless recipe for anvils
	 * 
	 * @param result The output item
	 * @param forgeTime The time taken to forge(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 * this automatically sets the required hammer to 0 (any) and set 0 experiance
	 */
	public static void addShapelessAnvilRecipe(ItemStack result, int forgeTime, Object... input)
	{
		CraftingManagerAnvil.getInstance().addShapelessRecipe(result, false, 0F, 0, 1, forgeTime, input);
	}
	
	/**
	 * 
	 * @param entity The class to check the alliance
	 * @return if the target is an ally
	 */
	public static boolean isEntityAlly(Object entity)
	{
		if(entity instanceof ITargetAllyMF)
		{
			ITargetAllyMF tar = (ITargetAllyMF)entity;
			return tar.isAlly();
		}
		return false;
	}
	
	/**
	 * 
	 * @param entity The class to check the alliance
	 * @return if the target is neutral
	 */
	public static boolean isEntityNeutral(Object entity)
	{
		return !isEntityEnemy(entity) && !isEntityAlly(entity);
	}
	
	/**
	 * 
	 * @param entity The class to check the alliance
	 * @return if the target is an enemy
	 */
	public static boolean isEntityEnemy(Object entity)
	{
		if(entity instanceof ITargetAllyMF)
		{
			ITargetAllyMF tar = (ITargetAllyMF)entity;
			return tar.isEnemy();
		}
		return false;
	}
	/**
	 * Adds a recipe when a knife is used on a tanning rack
	 * 
	 * @param input The itemID to input(like furnaces)
	 * @param output The resulted itemstack
	 */
	public static void addTanningRecipe(int input, ItemStack output)
	{
		TanningRecipes.instance().addTanning(input, output, 0);
	}
	/**
	 * Adds a recipe when a knife is used on a tanning rack
	 * 
	 * @param input The itemID to input(like furnaces)
	 * @param metadata The inputs metadata
	 * @param output The resulted itemstack
	 */
	public static void addTanningRecipe(int input, int metadata, ItemStack output)
	{
		TanningRecipes.instance().addTanning(input, metadata, output);
	}
	
	
	
	/**
	 * Adds a recipe when sheers are used on a tanning rack
	 * 
	 * @param input The itemID to input(like furnaces)
	 * @param output The resulted itemstack
	 */
	public static void addLeathercuttingRecipe(int input, ItemStack output)
	{
		LeathercuttingRecipes.instance().addCutting(input, output, 0);
	}
	/**
	 * Adds a recipe when sheers are used on a tanning rack
	 * 
	 * @param input The itemID to input(like furnaces)
	 * @param metadata The inputs metadata
	 * @param output The resulted itemstack
	 */
	public static void addLeathercuttingRecipe(int input, int metadata, ItemStack output)
	{
		LeathercuttingRecipes.instance().addCutting(input, metadata, output);
	}
	public static void addBloomRecipe(ItemStack input, ItemStack output)
	{
		BloomRecipe.add(new BloomRecipe(input, output));
	}
	public static void addBloomRecipe(ItemStack input, ItemStack output, int time)
	{
		BloomRecipe.add(new BloomRecipe(input, output, time));
	}
	
	public static void addBloomRecipe(Item input, ItemStack output)
	{
		BloomRecipe.add(new BloomRecipe(new ItemStack(input), output));
	}
	public static void addBloomRecipe(int input, ItemStack output) {
		BloomRecipe.add(new BloomRecipe(new ItemStack(input, 1, 0), output));
	}
	
	public static void addBlastRecipe(ItemStack input, ItemStack output) {
		BlastRecipes.add(new BlastRecipes(input, output));
	}
	public static void addBlastRecipe(int in, ItemStack output) {
		addBlastRecipe(new ItemStack(in, 1, 0), output);
	}
	
	
	
	
	/**
	 * Allows an item to be crafted into flux
	 * @param item the item to craft
	 * @param result the amount of flux made(MAX 64)
	 */
	public static void addCraftableFlux(ItemStack item, int result)
	{
		ItemHandler.addFluxRecipe(item, result);
	}
	
	/**
	 * Adds a flux item for purifying (like limestone)
	 * @param item the item to be used
	 */
	public static void addFlux(ItemStack item)
	{
		if(item != null)
		ItemHandler.flux.add(item);
	}
	/**
	 * Adds a flux item for purifying (like limestone): doesnt could metadata
	 * @param id the item id to be used
	 */
	public static void addFlux(int id)
	{
		ItemHandler.flux.add(new ItemStack(id, 1, 0));
	}
	
	
	
	
	/**
	 * Adds a carbon item for alloying (like coal)
	 * @param item the item to be used
	 */
	public static void addCarbon(ItemStack item)
	{
		ItemHandler.carbon.add(item);
	}
	/**
	 * Adds a carbon item for alloying (like coal): doesnt count metadata
	 * @param id the item id to be used
	 */
	public static void addCarbon(int id)
	{
		ItemHandler.carbon.add(new ItemStack(id, 1, OreDictionary.WILDCARD_VALUE));
	}
	
	/**
	 * Adds a fuel for blast furnaces
	 * @param item the item to be used
	 * @param uses the amount of smelts said item gives
	 */
	public static void addBlastFuel(ItemStack item, float uses)
	{
		ItemHandler.BlastFuel.add(new BlastFurnaceFuel(item, uses));
	}
	
	/**
	 * Adds a fuel for blast furnaces: doesnt count metadata
	 * @param id the item ID to be used
	 * @param uses the amount of smelts said item gives
	 */
	public static void addBlastFuel(int id, float uses)
	{
		ItemHandler.BlastFuel.add(new BlastFurnaceFuel(new ItemStack(id, 2, 0), uses));
	}
	
	/**
	 * Adds a fuel for blast furnaces: does count metadata
	 * @param id the item ID to be used
	 * @param meta the metadata
	 * @param uses the amount of smelts said item gives
	 */
	public static void addBlastFuel(int id, int meta, float uses)
	{
		ItemHandler.BlastFuel.add(new BlastFurnaceFuel(new ItemStack(id, 1, meta), uses));
	}
	
	
	
	
	
	
	/**
	 * Adds a fuel for forges
	 * @param item the item ID to be used
	 * @param meta the item Meta to be used
	 * @param dura the amount of ticks said item gives
	 * @param temperature the base temperature given
	 * @param willLight weather the fuel lights forges(like lava)
	 */
	public static void addForgeFuel(int item, int meta, float dura, int temperature, boolean willLight)
	{
		addForgeFuel(new ItemStack(item, 1, meta), dura, temperature, willLight);
	}
	/**
	 * Adds a fuel for forges
	 * @param item the item ID to be used
	 * @param meta the item Meta to be used
	 * @param dura the amount of ticks said item gives
	 * @param temperature the base temperature given
	 */
	public static void addForgeFuel(int item, int meta, float dura, int temperature)
	{
		addForgeFuel(item, meta, dura, temperature, false);
	}
	
	
	
	/**
	 * Adds a fuel for forges
	 * @param item the item to be used
	 * @param dura the amount of ticks said item gives
	 * @param temperature the base temperature given
	 * @param willLight weather the fuel lights forges(like lava)
	 */
	public static void addForgeFuel(ItemStack item, float dura, int temperature, boolean willLight)
	{
		ItemHandler.forgeFuel.add(new ForgeFuel(item, dura, temperature, willLight));
		if((int)(temperature*1.25) > ItemHandler.forgeMaxTemp)
		{
			ItemHandler.forgeMaxTemp = (int)(temperature*1.25);
		}
	}
	
	/**
	 * Adds a fuel for forges
	 * @param item the item to be used
	 * @param dura the amount of ticks said item gives
	 * @param temperature the base temperature given
	 * @param willLight weather the fuel lights forges(like lava)
	 */
	public static void addForgeFuel(int id, float dura, int temperature, boolean willLight)
	{
		addForgeFuel(new ItemStack(id, 2, OreDictionary.WILDCARD_VALUE), dura, temperature, willLight);
	}
	
	
	/**
	 * Adds a fuel for forges
	 * @param item the item to be used
	 * @param dura the amount of ticks said item gives
	 * @param temperature the base temperature given
	 */
	public static void addForgeFuel(ItemStack item, float dura, int temperature)
	{
		addForgeFuel(item, dura, temperature, false);
	}
	
	/**
	 * Adds a fuel for forges
	 * @param item the item to be used
	 * @param dura the amount of ticks said item gives
	 * @param temperature the base temperature given
	 */
	public static void addForgeFuel(int id, float dura, int temperature)
	{
		addForgeFuel(id, dura, temperature, false);
	}
	
	/**
	 * Allows an item to be heated
	 * @param item the item to heat
	 * @param min the minimum heat to forge with(celcius)
	 * @param max the maximum heat until the item is ruined(celcius)
	 */
	public static void addHeatableItem(ItemStack item, int min, int unstable, int max)
	{
		HeatableItem.addItem(item, min, unstable, max);
	}
	
	/**
	 * Allows an item to be heated ignoring subId
	 * @param id the item to heat
	 * @param min the minimum heat to forge with(celcius)
	 * @param max the maximum heat until the item is ruined(celcius)
	 */
	public static void addHeatableItem(int id, int min, int unstable, int max)
	{
		HeatableItem.addItem(new ItemStack(id, 1, OreDictionary.WILDCARD_VALUE), min, unstable, max);
	}
	
	/**
	 * Adds an alloy for any Crucible
	 * @param out The result
	 * @param in The list of required items
	 */
	public static void addAlloy(ItemStack out, Object... in)
	{
		AlloyRecipes.addAlloy(out, convertList(in));
	}
	
	/**
	 * Adds an alloy with a minimal furnace level
	 * @param out The result
	 * @param level The minimal furnace level
	 * @param in The list of required items
	 */
	public static void addAlloy(ItemStack out, int level, Object... in)
	{
		AlloyRecipes.addAlloy(out, level, convertList(in));
	}
	
	
	/**
	 * Adds an alloy with a minimal furnace level
	 * @param dupe the amount of times the ratio can be added
	 * @param out The result
	 * @param level The minimal furnace level
	 * @param in The list of required items
	 */
	public static void addRatioAlloy(int dupe, ItemStack out, int level, Object... in)
	{
		AlloyRecipes.addRatioRecipe(out, level, convertList(in), dupe);
	}
	
	/**
	 * Adds an alloy with any smelter
	 * @param dupe the amount of times the ratio can be added
	 * @param out The result
	 * @param level The minimal furnace level
	 * @param in The list of required items
	 */
	public static void addRatioAlloy(int dupe, ItemStack out, Object... in)
	{
		AlloyRecipes.addRatioRecipe(out, 0, convertList(in), dupe);
	}
	
	/**
	 * Adds a custom alloy
	 * @param alloy the Alloy to add
	 * Use this if you want your alloy to have special properties
	 * @see Alloy
	 */
	public static void addAlloy(Alloy alloy)
	{
		AlloyRecipes.addAlloy(alloy);
	}
	
	/**
	 * Adds a Crush Recipe for Anvil or Trip Hammer
	 * @param in The input
	 * @param out The output
	 */
	public static void addCrushRecipe(ItemStack in, ItemStack out)
	{
		CrushRecipes.addRecipe(in, out);
	}
	
	public static void addCrushRecipe(ICustomCrushRecipe recipe)
	{
		CrushRecipes.addRecipe(recipe);
	}
	
	/**
	 * Adds a Crush Recipe for Anvil or Trip Hammer, not considering metadata
	 * @param in The input
	 * @param out The output
	 */
	public static void addCrushRecipe(int in, ItemStack out)
	{
		CrushRecipes.addRecipe(new ItemStack(in, 1, OreDictionary.WILDCARD_VALUE), out);
	}
	
	/**
	 * Adds a food prep for chopping boards
	 * @param input the food that goes in the board
	 * @param output the result of the food
	 * @param time the time taken to craft (this is directly counted from efficiency)
	 * @param utensil the utensil type used(Ie."knife")
	 * @param sound the sound made when crafting
	 */
	public static void addFoodPrep(ItemStack input, ItemStack output, float time, String utensil, String sound)
	{
		FoodPrepRecipe.addFoodPrepRecipe(input, output, time, utensil, sound);
	}
	
	
	/**
	 * Adds a food prep for chopping boards not considering sub Ids
	 * @param input the food ID that goes in the board
	 * @param output the result of the food
	 * @param time the time taken to craft (this is directly counted from efficiency)
	 * @param utensil the utensil type used(Ie."knife")
	 * @param sound the sound made when crafting
	 */
	public static void addFoodPrep(int input, ItemStack output, float time, String utensil, String sound)
	{
		FoodPrepRecipe.addFoodPrepRecipe(input, output, time, utensil, sound);
	}
	
	private static List convertList(Object[] in) {
		ArrayList arraylist = new ArrayList();
        Object[] aobject = in;
        int i = in.length;

        for (int j = 0; j < i; ++j)
        {
            Object object1 = aobject[j];

            if (object1 instanceof ItemStack)
            {
                arraylist.add(((ItemStack)object1).copy());
            }
            else if (object1 instanceof Item)
            {
                arraylist.add(new ItemStack((Item)object1));
            }
            else
            {
                if (!(object1 instanceof Block))
                {
                    throw new RuntimeException("MineFantasy: Invalid alloy!");
                }

                arraylist.add(new ItemStack((Block)object1));
            }
        }
        
        return arraylist;
	}
	
	/**
	 * Adds a tailor recipe
	 * @param result the result
	 * @param stitches the amount of clicks taken and how much string is used
	 * @param time the difficulty of each stitch
	 * @param string the string tier used
	 * @param input the items in the grid
	 */
	public static void addTailorRecipe(ItemStack result, int stitches, float time, int string, Object... input)
	{
		addTailorRecipe(result, 0, stitches, time, string, input);
	}
	
	/**
	 * Adds a tailor recipe
	 * @param result the result
	 * @param tier the required needle level
	 * @param stitches the amount of clicks taken and how much string is used
	 * @param time the difficulty of each stitch
	 * @param string the string tier used
	 * @param input the items in the grid
	 */
	public static void addTailorRecipe(ItemStack result, int tier, int stitches, float time, int string, Object... input)
	{
		CraftingManagerTailor.getInstance().addRecipe(result, tier, string, stitches, time, input);
	}
	public static boolean isHotToPickup(ItemStack item) 
	{
		if(item != null && item.getItem() != null && item.getItem() instanceof IHotItem)
		{
			return ((IHotItem)item.getItem()).isHot(item);
		}
		return false;
	}
	
	/**
	 * Adds a recipe for the spinning wheel
	 * @param input the item put in
	 * @param output the resulting string
	 * @param time the time taken
	 */
	public static void addStringRecipe(ItemStack input, ItemStack output, int time)
	{
		StringRecipes.addRecipe(input, output, time);
	}
	
	/**
	 * Adds a recipe for the spinning wheel
	 * @param input the item put in
	 * @param output the resulting string
	 * @param time the time taken
	 */
	public static void addStringRecipe(Item input, ItemStack output, int time)
	{
		addStringRecipe(input.itemID, output, time);
	}
	/**
	 * Adds a recipe for the spinning wheel
	 * @param input the item put in
	 * @param output the resulting string
	 * @param time the time taken
	 */
	public static void addStringRecipe(Block input, ItemStack output, int time)
	{
		addStringRecipe(input.blockID, output, time);
	}
	
	/**
	 * Adds a recipe for the spinning wheel, ignoring subID
	 * @param input the item ID put in
	 * @param output the resulting string
	 * @param time the time taken
	 */
	public static void addStringRecipe(int input, ItemStack output, int time)
	{
		StringRecipes.addRecipe(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), output, time);
	}
	
	
	/**
	 * Adds a special recipe for any MF furnace
	 * @param out The result
	 * @param in The list of required items
	 */
	public static void addSpecialSmelt(ItemStack out, Object... in)
	{
		SpecialFurnaceRecipes.addAlloy(out, convertList(in));
	}
	public static void addSpecialSmelt(ItemStack out, int level, Object in)
	{
		if(in instanceof ItemStack)
		{
			SpecialFurnaceRecipes.addSmelting(((ItemStack)in).itemID, ((ItemStack)in).getItemDamage(), out, level);
		}
		if(in instanceof Item)
		{
			SpecialFurnaceRecipes.addSmelting(((Item)in).itemID, out, level);
		}
		if(in instanceof Block)
		{
			SpecialFurnaceRecipes.addSmelting(((Block)in).blockID, out, level);
		}
	}
	public static void addSpecialSmelt(ItemStack out, Object in)
	{
		addSpecialSmelt(out, in);
	}
	
	/**
	 * Adds a special recipe with a minimal furnace level
	 * @param out The result
	 * @param level The minimal furnace level
	 * @param in The list of required items
	 */
	public static void addSpecialSmelt(ItemStack out, int level, Object... in)
	{
		SpecialFurnaceRecipes.addAlloy(out, level, convertList(in));
	}
	
	/**
	 * Adds a randomly dropped ore with handpicks any harvest level on stone
	 * @param drop the ore that drops
	 * @param chance the chance to happen (1.0 = 100%)
	 */
	public static void addRandomOre(ItemStack drop, float chance)
	{
		addRandomOre(drop, chance, 0, Block.stone, false);
	}
	
	/**
	 * Adds a randomly dropped ore with handpicks when mining stone
	 * @param drop the ore that drops
	 * @param chance the chance to happen (1.0 = 100%)
	 * @param harvestLvl the minimal harvest level to succeed
	 */
	public static void addRandomOre(ItemStack drop, float chance, int harvestLvl)
	{
		addRandomOre(drop, chance, harvestLvl, Block.stone, false);
	}
	
	/**
	 * Adds a randomly dropped ore with handpicks
	 * @param drop the ore that drops
	 * @param chance the chance to happen (1.0 = 100%)
	 * @param harvestLvl the minimal harvest level to succeed
	 * @param block the block (id, block or itemstack) that is mined
	 */
	public static void addRandomOre(ItemStack drop, float chance, int harvestLvl, Object block, boolean silkDisable)
	{
		addRandomOre(drop, chance, harvestLvl, block, -1, -1, silkDisable);
	}
	
	/**
	 * Adds a randomly dropped ore with handpicks at certain height
	 * @param drop the ore that drops
	 * @param chance the chance to happen (1.0 = 100%)
	 * @param harvestLvl the minimal harvest level to succeed
	 * @param block the block (id, block or itemstack) that is mined
	 * @param min the lowest point found
	 * @param max highest point found
	 */
	public static void addRandomOre(ItemStack drop, float chance, int harvestLvl, Object block, int min, int max, boolean silkDisable)
	{
		RandomOre.addOre(drop, chance, block, harvestLvl, min, max, silkDisable);
	}
	
	/**
	 * Adds a recipe for ovens only
	 * @param input what goes in
	 * @param output what goes out
	 */
	public static void addOvenRecipe(ItemStack input, ItemStack output)
	{
		OvenRecipes.addSmelting(input.itemID, input.getItemDamage(), output, 0.0F);
	}
	/**
	 * Adds a recipe for ovens only
	 * @param input what goes in
	 * @param output what goes out
	 * @param exp the exp given (this isn't very important)
	 */
	public static void addOvenRecipe(ItemStack input, ItemStack output, float exp)
	{
		OvenRecipes.addSmelting(input.itemID, input.getItemDamage(), output, exp);
	}
	
	
	/**
	 * Adds a recipe for ovens only
	 * @param input what goes in
	 * @param output what goes out
	 */
	public static void addOvenRecipe(int input, ItemStack output)
	{
		OvenRecipes.addSmelting(input, output, 0.0F);
	}
	/**
	 * Adds a recipe for ovens only
	 * @param input what goes in
	 * @param output what goes out
	 * @param exp the exp given (this isn't very important)
	 */
	public static void addOvenRecipe(int input, ItemStack output, float exp)
	{
		OvenRecipes.addSmelting(input, output, exp);
	}
	
}
