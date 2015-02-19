package minefantasy;

import java.awt.Color;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

import minefantasy.api.MineFantasyAPI;
import minefantasy.api.arrow.Arrows;
import minefantasy.api.forge.HeatableItem;
import minefantasy.api.weapon.CrossbowAmmo;
import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.*;
import minefantasy.client.gui.GuiAnvil;
import minefantasy.entity.*;
import minefantasy.item.ItemListMF;
import minefantasy.system.*;
import minefantasy.system.network.PacketManagerMF;
import mods.battlegear2.api.quiver.QuiverArrowRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.src.*;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@Mod(modid = "MineFantasy", name = "Mine Fantasy", version = "1.3.7")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = { "MineFantasy" }, packetHandler = PacketManagerMF.class)
public class MineFantasyBase{
	public static String version = "1.2.1";
	//data_minefantasy, cfg, MFProxy__common, MFProxy_client, EventManagerMF
	public boolean client = FMLCommonHandler.instance().getSide().isClient();
	
	@SidedProxy(clientSide = "minefantasy.client.MFProxy_client", serverSide = "minefantasy.system.MFProxy_common")
	public static MFProxy_common proxy;
	@Instance
	public static MineFantasyBase instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{ 
		addModFlags();
		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		new cfg().setConfig(config);
		
		if(isDebug())
		{
			System.out.println("MineFantasy: Debug mode ACTIVE");
			HeatableItem.requiresHeating = false;
		}
		removeRecipes();
		GameRegistry.registerFuelHandler(new MineFantasyFuels());
		TickRegistry.registerTickHandler(new ArmourTickHandlerMF(), FMLCommonHandler.instance().getSide());
		TickRegistry.registerTickHandler(new ArmourTickHandlerMF(), Side.SERVER);
		TickRegistry.registerTickHandler(new HotItemTickHandler(), FMLCommonHandler.instance().getSide());
		TickRegistry.registerTickHandler(new HotItemTickHandler(), Side.SERVER);
		
		proxy.registerTickHandlers();
		proxy.registerRenderInformation();
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		if (client) {
			MinecraftForge.EVENT_BUS.register(new SoundManagerMF());
			StatListMF.init();
		}
		CrossbowAmmo.addCrossbowHandler(new CrossbowAmmoMF());
		
		rebuildRecipes();
		
		QuiverArrowRegistry.addArrowFireHandler(new QuiverArrowsMF());
	}

	private void addModFlags() 
	{
		isBGLoaded = setIsBGLoaded();
		isHOLoaded = setIsHOLoaded();
	}

	private void rebuildRecipes() {
		System.out.println("[MineFantasy] Hardcore crafting ENABLED: Tool / Weapon recipes removed. Rebuilding...");
		List recipes = new ArrayList();
		
		int prevSize = CraftingManager.getInstance().getRecipeList().size();
		
		for(int a = 0; a < CraftingManager.getInstance().getRecipeList().size(); a ++)
		{
			if(CraftingManager.getInstance().getRecipeList().get(a) instanceof IRecipe)
			{
				IRecipe recipe = (IRecipe)CraftingManager.getInstance().getRecipeList().get(a);
				if(recipe != null && !willRemove(recipe.getRecipeOutput()))
				{
					recipes.add(recipe);
				}
			}
		}
		int less = prevSize - recipes.size();
		
		System.out.println("[MineFantasy] Recipes Rebuilt " + less + " recipes removed. Applying new List....");
		CraftingManager.getInstance().getRecipeList().clear();
		
		for(int a = 0; a < recipes.size(); a ++)
		{
			CraftingManager.getInstance().getRecipeList().add(recipes.get(a));
		}
		
		System.out.println("[MineFantasy] New Recipe List Applied.");
	}

	private void removeRecipes() {
		System.out.println("MineFantasy: Removing replaced recipes...");
		for(int a = 0; a < CraftingManager.getInstance().getRecipeList().size(); a ++)
		{
			IRecipe rec = (IRecipe) CraftingManager.getInstance().getRecipeList().get(a);
			if(rec.getRecipeOutput() != null && rec.getRecipeOutput().isItemEqual(new ItemStack(Item.stick)))
			{
				CraftingManager.getInstance().getRecipeList().remove(a);
				
				if(rec instanceof ShapedRecipes)
				{
					ShapedRecipes shape = (ShapedRecipes)rec;
					
					if(shape.recipeHeight == 2 && shape.recipeWidth == 1)
					{
						GameRegistry.addRecipe(new ItemStack(ItemListMF.plank, 8), new Object[]{
							"A",
							"B",
							'A', shape.recipeItems[0],
							'A', shape.recipeItems[1],
						});
					}
				}
			}
			
			if(rec.getRecipeOutput() != null && rec.getRecipeOutput().isItemEqual(new ItemStack(Block.anvil)))
			{
				CraftingManager.getInstance().getRecipeList().remove(a);
			}
		}
	}

	private void tryRemove(String string, int id, ItemStack recipe) {
		if(OreDictionary.getOres(string) == null)
		{
			return;
		}
		if(OreDictionary.getOres(string).isEmpty())
		{
			return;
		}
		
		for(ItemStack ore : OreDictionary.getOres(string))
		{
			if(ore != null)
			{
				if(ore.isItemEqual(recipe))
				{
					FurnaceRecipes.smelting().getSmeltingList().remove(id);
				}
			}
		}
	}

	private void removeIron()
	{
		System.out.println("MineFantasy: Removing Ingot Smelting");
		
		for(int a2 = 0; a2 < FurnaceRecipes.smelting().getSmeltingList().size(); a2 ++)
		{
			Object recipe = FurnaceRecipes.smelting().getSmeltingList().get(a2);
			if(recipe != null && recipe instanceof ItemStack)
			{
				if(((ItemStack)recipe).itemID == Item.ingotIron.itemID)
				{
					FurnaceRecipes.smelting().getSmeltingList().remove(a2);
					if(cfg.easyIron)
					{
						MineFantasyAPI.addSpecialSmelt(ItemListMF.component(ItemListMF.ingotWroughtIron), -1, a2);
					}
					else
					{
						MineFantasyAPI.addBlastRecipe(a2, new ItemStack(Item.ingotIron));
					}
				}
			}
		}
	}

	private boolean willRemove(ItemStack item)
	{
		if(item == null)
			return false;
		
		int id = item.itemID;
		
		if(id == Item.flintAndSteel.itemID
		|| id == Item.helmetLeather.itemID
		|| id == Item.plateLeather.itemID
		|| id == Item.legsLeather.itemID
		|| id == Item.bootsLeather.itemID
		|| isBGItem(id, "quiver")
		)return true;
		
		if(cfg.hardcoreFurnace)
		{
			if(id == Block.furnaceIdle.blockID)return true;
		}
		
		
		if(cfg.hardcoreCraft)
		return id == Item.pickaxeDiamond.itemID
			|| id == Item.pickaxeGold.itemID
			|| id == Item.pickaxeWood.itemID
			|| id == Item.pickaxeStone.itemID
			|| id == Item.pickaxeIron.itemID
			
			|| id == Item.axeDiamond.itemID
			|| id == Item.axeGold.itemID
			|| id == Item.axeWood.itemID
			|| id == Item.axeStone.itemID
			|| id == Item.axeIron.itemID
			
			|| id == Item.shovelDiamond.itemID
			|| id == Item.shovelGold.itemID
			|| id == Item.shovelWood.itemID
			|| id == Item.shovelStone.itemID
			|| id == Item.shovelIron.itemID
			
			|| id == Item.hoeDiamond.itemID
			|| id == Item.hoeGold.itemID
			|| id == Item.hoeWood.itemID
			|| id == Item.hoeStone.itemID
			|| id == Item.hoeIron.itemID
			
			|| id == Item.swordDiamond.itemID
			|| id == Item.swordGold.itemID
			|| id == Item.swordWood.itemID
			|| id == Item.swordStone.itemID
			|| id == Item.swordIron.itemID
			
			|| id == Item.helmetIron.itemID
			|| id == Item.plateIron.itemID
			|| id == Item.legsIron.itemID
			|| id == Item.bootsIron.itemID
			
			|| id == Item.helmetGold.itemID
			|| id == Item.plateGold.itemID
			|| id == Item.legsGold.itemID
			|| id == Item.bootsGold.itemID
			
			|| id == Item.helmetDiamond.itemID
			|| id == Item.plateDiamond.itemID
			|| id == Item.legsDiamond.itemID
			|| id == Item.bootsDiamond.itemID
			
			|| id == Item.bow.itemID
			|| id == Block.bed.blockID
	        || id == Item.shears.itemID;
		
		return false;
	}

	private boolean isBGItem(int id, String string)
	{
		return isBGItem(id, string, OreDictionary.WILDCARD_VALUE);
	}
	private boolean isBGItem(int id, String string, int meta)
	{
		ItemStack BGItem = getBGItem(string, meta);
		
		if(BGItem != null)
		{
			if(id == BGItem.itemID)
			{
				return BGItem.getItemDamage() == meta || meta == OreDictionary.WILDCARD_VALUE;
			}
		}
		return false;
	}
	
	public static ItemStack getBGItem(String itemString, int meta) 
	{
		if(meta == OreDictionary.WILDCARD_VALUE)meta = 0;
		ItemStack item = null;

		try {
			String itemClass = "mods.battlegear2.utils.BattlegearConfig";
			Object obj = Class.forName(itemClass).getField(itemString).get(null);
			if (obj instanceof Item) {
				item = new ItemStack((Item) obj,1,meta);
			} else if (obj instanceof Block) {
				item = new ItemStack((Block) obj,1,meta);
			} else if (obj instanceof ItemStack) {
				item = (ItemStack) obj;
			}
		} catch (Exception ex) {
		}

		return item;
	}
	
	@EventHandler
	public void load(FMLInitializationEvent evt) 
	{
		Arrows.addHandler(new ArrowFireFlint());
		Arrows.addHandler(new ArrowFirerMF());
		
		DungeonHooks.addDungeonMob("MineFantasy.Minotaur", 50);
		DungeonHooks.addDungeonMob("MineFantasy.Basilisk", 10);
		GameRegistry.registerCraftingHandler(new CraftingHandlerMF());
		
		proxy.registerTileEntities();
		ItemListMF.init();
		BlockListMF.init();
		
		IWorldGenerator gen = new WorldGenMF();
		
		GameRegistry.registerWorldGenerator(gen);
		MinecraftForge.EVENT_BUS.register(new EventManagerMF());
		MinecraftForge.EVENT_BUS.register(new ArrowHandlerMF());
		MinecraftForge.ORE_GEN_BUS.register(gen);
		
		
		StatListMF.register();
		
		OreDictionary.registerOre("logWood",   new ItemStack(BlockListMF.log, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("treeLeaves",   new ItemStack(BlockListMF.leaves, 1, OreDictionary.WILDCARD_VALUE));
		
		OreDictionary.registerOre("treeSapling",   new ItemStack(BlockListMF.sapling, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("ingotSteel", new ItemStack(ItemListMF.ingotSteel));
		
		OreDictionary.registerOre("blockLimestone", new ItemStack(BlockListMF.limestone));
		OreDictionary.registerOre("blockSteel", new ItemStack(BlockListMF.storage, 1, 0));
		OreDictionary.registerOre("blockCopper", new ItemStack(BlockListMF.storage, 1, 1));
		OreDictionary.registerOre("blockTin", new ItemStack(BlockListMF.storage, 1, 2));
		OreDictionary.registerOre("blockBronze", new ItemStack(BlockListMF.storage, 1, 3));
		OreDictionary.registerOre("blockMithril", new ItemStack(BlockListMF.storage, 1, 4));
		OreDictionary.registerOre("blockDeepIron", new ItemStack(BlockListMF.storage, 1, 8));
		OreDictionary.registerOre("blockSilver", new ItemStack(BlockListMF.storage, 1, 5));
		OreDictionary.registerOre("blockIron", new ItemStack(BlockListMF.storage, 1, 7));
		
		OreDictionary.registerOre("ingotTin", new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotTin));
	    OreDictionary.registerOre("ingotCopper", new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotCopper));
		OreDictionary.registerOre("ingotBronze", new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotBronze));
        OreDictionary.registerOre("ingotSilver", new ItemStack(ItemListMF.ingotSilver));
        OreDictionary.registerOre("chain", new ItemStack(ItemListMF.misc, 1, ItemListMF.chainIron));
        
        OreDictionary.registerOre("oreCopper", new ItemStack(BlockListMF.oreCopper));
        OreDictionary.registerOre("oreTin", new ItemStack(BlockListMF.oreTin));
        OreDictionary.registerOre("oreSilver", new ItemStack(BlockListMF.oreUtil, 1, 0));
        
        OreDictionary.registerOre("oreNitre", new ItemStack(BlockListMF.oreUtil, 1, 1));
        OreDictionary.registerOre("oreNiter", new ItemStack(BlockListMF.oreUtil, 1, 2));
        OreDictionary.registerOre("oreSulfur", new ItemStack(BlockListMF.oreUtil, 1, 2));
        
        OreDictionary.registerOre("ingotIron", new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotWroughtIron));
        OreDictionary.registerOre("ingotIron", new ItemStack(Item.ingotIron));
        OreDictionary.registerOre("salt", ItemListMF.component(ItemListMF.salt));
        
        OreDictionary.registerOre("ingotMithril", new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotMithril));
        OreDictionary.registerOre("ingotDeepIron", new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotDeepIron));
        
	}
	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent evt)
	{
		MineFantasyAPI.initAllPlugins();
		RecipesMF.addFinalRecipes();
		removeIron();
		RecipesMF.initiate();
		AnvilRecipesMF.initiate();
	}
	
	

	public static int getColourForRGB(int red, int green, int blue) {
		return (red << 16) + (green << 8) + blue;
	}

	public static String getVersion()
	{
		return instance.getVersion();
	}

	public static boolean isDebug() {
		return cfg.debug.equalsIgnoreCase("XA42-DDY3-22Y4-32HG-997U");
	}
	static
	{
		MineFantasyAPI.modLoaded = true;
	}
	public static boolean isBGLoaded() 
	{
		return isBGLoaded;
	}
	public static boolean setIsBGLoaded() 
	{
		try 
		{
			Class.forName("mods.battlegear2.Battlegear");
		} 
		catch (ClassNotFoundException e) 
		{
			return false;
		}
		return true;
	}
	public static boolean isHOLoaded() 
	{
		return isHOLoaded;
	}
	public static boolean setIsHOLoaded() 
	{
		try 
		{
			Class.forName("iguanaman.hungeroverhaul.HungerOverhaul");
		} 
		catch (ClassNotFoundException e) 
		{
			return false;
		}
		return true;
	}
	public static float scaleExhaustion(float exhaust)
	{
		if(isHOLoaded())
		{
			exhaust *= 0.25F;
		}
		return exhaust;
	}
	public static boolean isBGLoaded;
	public static boolean isHOLoaded;
	
	

	/*
	 * A Message to decompilers:
	 * 
	 * 
	 * It is clear by now; you have access to MineFantasy Source files. Though what
	 * your intentions are can not be predicted; if you are here to either look over
	 * the coding for your own reasons or updating the mod.
	 * 
	 * If you are to continue this mod development(for whatever reason I may not be)
	 * it would be respectful for you to continue down the path this mod has headed.
	 * Although continuing exactly is near-impossible (as each user has their own view)
	 * please look over some notes found within and base your plans off that.
	 * 
	 * Keep in mind that though sometimes I may not communicate on public chats, there
	 * development could still be going on without you knowing. If you were to continue;
	 * make sure you have the skills required(I personally had no prior coding experience
	 * so most shouldn't have too much difficulty.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Overall plan
	 * 
	 * Anyway the mod as you know has been going down a number of plans, and as of Beta.
	 * will commit to it's own design. The concept is rather simple, a 2-stage adventure
	 * to expand on the MC 'Story', starting out following a similar path as histrory had
	 * before you. digging on the bottom rung than working your way up, expanding on different
	 * ways to survive on the way, and then when you see fit to, venture out for more
	 * discoveries (leading to the ancient forge, and techniques) with that you follow a tier
	 * tree of fantasy metals and different creations from there. some taking you to the nether
	 * and even the end(you can choose whether to or not).
	 * 
	 * When it comes to the concept of the mod. it will be a "Process Mod" aiming to get
	 * you involved in the game, (the whole wepaons and tools are to give you something to
	 * do). So id say there's enough weapons now, if not too many. There's room to improve on
	 * tools however.
	 * 
	 * 
	 * 
	 * 
	 * Magic Expansion
	 * 
	 * This is probably best left as an expansion.. but you can leave it in the mod.
	 * The Magic system utilises elements of the world(Wind, Water, Fire, Air, Ender, Holy)
	 * and whatever others you may want.
	 * 
	 * To use these elements, requires building up(something like a tower or such). where
	 * you build large contraptions to bend the elements to your will. the more you put
	 * into the facilities, the more powerful they become. (you can focus on 1 element or
	 * break your resources into multiple)
	 * 
	 * To enchant items, Sigils will be made(likely made of gemstones(like sapphire for water
	 * like sigils, rubies for fire like.. you can think of it)rule is that each sigil is made
	 * from a certain type of gem. Runes can be placed on surfaces(or re-implement totems) that
	 * apply effects (good or bad) to passers by.
	 * 
	 * Processes used in the magic system include custom enchanting(both sigil, and a custom enchant
	 * table). maybe a special alchemy, gemstone cutting for sigils, Crystals to hold essense, "Pure Gold"
	 * To fuel this all and a few other MF-connected things
	 * 
	 * Resources like pure gold will be designed to give gold value, pure gold: made from multiple gold ingots
	 * turns Minecraft's common gold into a rarer substance(while still being abundant for it's default uses)
	 * 
	 * 
	 * Other misc concepts were lockable storage(safes and such) for keeping valuables safe, the renaissance expansion
	 * (for expanding progression), maybe re-add alchemy, and transport(make sure it's well done)
	 */
}