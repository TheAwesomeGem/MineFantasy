package minefantasy.system;

import java.util.ArrayList;
import java.util.List;

import minefantasy.api.Components;
import minefantasy.block.BlockListMF;
import minefantasy.item.ItemListMF;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatBasic;
import net.minecraftforge.common.AchievementPage;

public class StatListMF {
	
	public static final Achievement copper = (new Achievement(cfg.achCopper, "minefantasy.copper", 0, 1, new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotCopper), (Achievement) null)).registerAchievement();
	public static final Achievement tin = (new Achievement(cfg.achTin, "minefantasy.tin", 0, -1, new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotTin), copper)).registerAchievement();
	public static final Achievement bronze = (new Achievement(cfg.achBronze, "minefantasy.bronze", 2, 0, new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotBronze), copper)).registerAchievement();
	public static final Achievement iron = (new Achievement(cfg.achIron, "minefantasy.iron", 4, 0, new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotWroughtIron), bronze)).registerAchievement();
	public static final Achievement granite = (new Achievement(cfg.achGranite, "minefantasy.granite", 5, -1, BlockListMF.granite, iron)).registerAchievement();
	public static final Achievement steel = (new Achievement(cfg.achSteel, "minefantasy.steel", 6, 0, ItemListMF.ingotSteel, granite)).registerAchievement();
	public static final Achievement encrust = (new Achievement(cfg.achEncrust, "minefantasy.encrust", 7, 1, ItemListMF.pickEncrusted, steel)).registerAchievement();
	public static final Achievement mithril = (new Achievement(cfg.achMithril, "minefantasy.mithril", 8, -1, BlockListMF.oreMythic, encrust)).registerAchievement();
    
    public static final Achievement tanner = (new Achievement(cfg.achTanner, "minefantasy.tanner", 0, 3, BlockListMF.tanner, null)).registerAchievement();
	public static final Achievement alloy = (new Achievement(cfg.achAlloy, "minefantasy.alloy", 2, 3, new ItemStack(BlockListMF.smelter, 1, 1), null)).registerAchievement();
	public static final Achievement anvil = (new Achievement(cfg.achAnvil, "minefantasy.anvil", 4, 3, new ItemStack(BlockListMF.anvil), null)).registerAchievement();
	
	public static final Achievement forged = (new Achievement(cfg.achForge, "minefantasy.forged", 3, 5, ItemListMF.hammerIron, anvil)).registerAchievement();
    public static final Achievement bomb = (new Achievement(cfg.achBomb, "minefantasy.bomb", 3, 6, ItemListMF.bombMF, anvil)).registerAchievement();
    public static final Achievement plate = (new Achievement(cfg.achPlate, "minefantasy.plate", 3, 7, ItemListMF.helmetSteelPlate, anvil)).registerAchievement();
    public static final Achievement arrow = (new Achievement(cfg.achArrow, "minefantasy.arrow", 3, 8, new ItemStack(ItemListMF.arrowMF, 1, 9), anvil)).registerAchievement();
    public static final Achievement tripleKill = (new Achievement(cfg.achTriplekill, "minefantasy.triplekill", 1, 8, new ItemStack(ItemListMF.arrowMF, 1, 9), arrow)).registerAchievement().setSpecial();
    
    public static final Achievement ignotumite = (new Achievement(cfg.achSuperore, "minefantasy.ignotumite", 8, -3, Components.component(ItemListMF.hunkIgnotumite), mithril)).registerAchievement();
    public static final Achievement dragonslayer = (new Achievement(cfg.achDragon, "minefantasy.dragonslayer", -2, 0, new ItemStack(ItemListMF.misc, 1, ItemListMF.fireGland), (Achievement) null)).registerAchievement();
    public static final Achievement dragonforger = (new Achievement(cfg.achDragoningot, "minefantasy.dragonforger", -2, -2, new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotDragon), dragonslayer)).registerAchievement();
    
    public static void init()
    {
    }
    
    public static AchievementPage page1 = new AchievementPage("MineFantasy",
    		copper, tin, alloy, bronze, tanner, anvil, forged, plate, mithril, encrust, bomb, arrow, iron, steel, granite, ignotumite, dragonslayer, dragonforger, tripleKill
    		);
    public static void register()
    {
    	AchievementPage.registerAchievementPage(page1);
    }
}
