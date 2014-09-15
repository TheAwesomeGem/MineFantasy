package minefantasy.system;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import minefantasy.MineFantasyBase;
import minefantasy.api.armour.EnumArmourClass;
import minefantasy.item.ArmourDesign;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class cfg {
	//public static List<Integer> lightArmours = new ArrayList();
	//public static List<Integer> mediumArmours = new ArrayList();
	//public static List<Integer> heavyArmours = new ArrayList();
	//public static List<Integer> plateArmours = new ArrayList();
	
	//public static HashMap<Integer, Integer>minefantasy.api.armour.ArmourClass.configArmours = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Boolean>hangables = new HashMap<Integer, Boolean>();
	
    public static int itemId;
    public TreeMap<String, Property> spawnProperties = new TreeMap<String, Property>();
    public TreeMap<String, Property> achievementProperties = new TreeMap<String, Property>();
    public TreeMap<String, Property> oreGenProps = new TreeMap<String, Property>();
    public TreeMap<String, Property> statsProp = new TreeMap<String, Property>();
    public static final String CATEGORY_MOBS = "MineFantasy Mob Spawns";
    public static final String CATEGORY_ACHIEVEMENTS = "MineFantasy Achievements";
    public static final String CATEGORY_OREGENERATIONS = "MineFantasy Ore Generation";
    public static final String CATEGORY_STATS = "MineFantasy Statistics";
    public static final String CATEGORY_MISC = "MineFantasy Specifics";
    public static final String CATEGORY_HARDCORE = "MineFantasy Hardcore Options";
    public static final String CATEGORY_COMBAT = "MineFantasy Combat Options";
    
    public static final String CATEGORY_HUD = "MineFantasy HUD Positions";
    
    public static String debug;
    //BLOCKS
    public static int lanternId;
    public static int forgeId;
    public static int anvilId;
    public static int mudBrickId;
    public static int cobbBrickId;
    public static int rePlanksId;
    public static int oreUtilId;
    public static int slateId;
    public static int clayWallId;
    public static int graniteId;
    public static int graniteBrickId;
    public static int leavesId;
    public static int ironbarkId;
    public static int planksId;
    public static int saplingId;
    public static int tannerId;
    public static int roadId;
    public static int totemId;
    public static int oreIgnotumiteId;
    public static int stairStoneId;
    public static int stairCStoneId;
    public static int stairCStoneRoughId;
    public static int dogbowlId;
    public static int weaponRackId;   
    public static int hayRoofId;  
    public static int oreCopperId;
    public static int oreTinId;   
    public static int oreMithicId; 
    public static int bloomId; 
    public static int bellowsId; 
    public static int limestoneId; 
    public static int BlastId; 
    public static int ProspectorOreId;
    public static int infernoId;
    public static int ovenId;
    public static int ironbarkDoorId;
    public static int hardDoorId;
    public static int steelDoorId;
    //Free id 218
    public static int storeId;
    public static int iceId;
    public static int chimId;
    public static int firepitId;
    public static int renderId;
    public static int dirtSlabDId;
    public static int dirtSlabSId;
    public static int dirtSlabDIdMF;
    public static int dirtSlabSIdMF;
    
    public static int WslabId;
    public static int WdSlabId;
    public static int SslabId;
    public static int SdSlabId;
    
    public static int tripHammerId;
    public static int slagId;
    public static int roastId;
    public static int SlslabId;
    public static int SldSlabId;
    
    public static int stairSlateId;
    public static int stairSlateTileId;
    public static int stairDSlateTileId;
    public static int stairSlateBrickId;
    
    public static int furnaceOffId;
    public static int furnaceOnId;
    public static int foodPrepId;
    public static int tailorId;
    public static int spinnerId;
    

    public static boolean advancedAnvil;
    public static boolean lightForge;
    public static boolean weakIron;
    //ACHIEVEMENT
    public static int achCopper;
    public static int achAnvil;
    public static int achBomb;
    public static int achTin;
    public static int achAlloy;
    public static int achBronze;
    public static int achSuperore;
    public static int achDragon;
    public static int achDragoningot;
    public static int achTriplekill;
    public static int achTanner;
    public static int achPlate;
    public static int achArrow;
    public static int achIron;
    public static int achGranite;
    public static int achForge;
    public static int achSteel;
    public static int achMithril;
    public static int achEncrust;
    
    //MOBS
    public static int entityIDBase;
    
    public static int minotaurSpawnrate;
    public static int knightSpawnrate;
    public static int houndSpawnrate;
    public static int drakeSpawnrate;
    public static int basilSpawnrate;
    public static int basilSpawnrateNether;
    
    public static int dragonDiff;
    public static int minoDiff;
    public static int knightDiff;
    public static int knightLvl;
    public static boolean mobTime;
    
    public static int minotaurWeaponDist;
    public static int titanDist;
    public static int dragonDistance;
    public static int drakeDistance;
    public static int basiliskDistance;
    
    public static int dragonSpawnrateNether;
    public static boolean dragonGrief;
    public static boolean houndKillMan;
    public static boolean houndNoFire;
    public static boolean hungryHound;
    public static boolean starveHound;
    public static boolean dropMutton;
    
    public static int dragonChance;
    //ORES
    public static boolean spawnSilver;
    public static boolean spawnNitre;
    public static boolean spawnSulfur;
    public static boolean spawnCopper;
    public static boolean spawnTin;
    public static boolean spawnMithril;
    public static boolean spawnDeepIron;
    public static boolean spawnIgnot;
    public static boolean spawnIBark;
    public static boolean spawnEbony;
    public static boolean spawnHerb;
    public static boolean limeCavern;
    public static boolean generateSlate;
    public static double mithrilDistance;
    
    //STATS
    public static int statDragons;
    
    //INJURY
    public static boolean hitSound = true;
    public static boolean burnPlayer = true;
    
    //MISC
    public static boolean heavyBonus;
    public static boolean useBalance;
    public static boolean renderHot;
    public static boolean hardcoreCraft;
    public static boolean easyBlooms;
    public static boolean easyIron;
    public static boolean hardcoreLeather;
    public static boolean hardcoreFurnace;
    public static boolean regen;
    public static boolean redstoneHammer;
    public static int regenLayer;
    public static int maxHeat;
    
    public static double dryRocksChance;
	public static boolean disableWeight;
	public static boolean easyTameHound;
	public static int HoundBreed;
	public static boolean durableArrows;
	public static boolean disableFirebomb;
	
	
	public static int craftMetreY;
	public static int craftMetreX;
	public static boolean disableHUD;
	
	public static int basiliskMax;
	
    private Configuration config;
    
    public void setConfig(Configuration configuration)
    {
    	configuration.load();
    	config = configuration;
    	debug = configuration.get(CATEGORY_MISC, "Debug Mode: (Requires Serial): ", "").getString();
    	//blockId = Integer.parseInt(configuration.getBlock("Block Base Id", 180).getString());
        itemId = Integer.parseInt(this.get("Item Base Id", Configuration.CATEGORY_ITEM, 1250).getString());
        lanternId = Integer.parseInt(this.get("Lantern", Configuration.CATEGORY_BLOCK, 180).getString());
        forgeId = Integer.parseInt(this.get("Forge", Configuration.CATEGORY_BLOCK, 181).getString());
        anvilId = Integer.parseInt(this.get("Anvil", Configuration.CATEGORY_BLOCK, 182).getString());
        mudBrickId = Integer.parseInt(this.get("Mud Bricks", Configuration.CATEGORY_BLOCK, 183).getString());
        cobbBrickId = Integer.parseInt(this.get("Cobblestone Bricks", Configuration.CATEGORY_BLOCK, 184).getString());
        rePlanksId = Integer.parseInt(this.get("Reinforced Planks", Configuration.CATEGORY_BLOCK, 185).getString());
        oreUtilId = Integer.parseInt(this.get("Silver Ore", Configuration.CATEGORY_BLOCK, 186).getString());
        slateId = Integer.parseInt(this.get("Slate Block", Configuration.CATEGORY_BLOCK, 187).getString());
        clayWallId = Integer.parseInt(this.get("Clay Wall", Configuration.CATEGORY_BLOCK, 188).getString());
        graniteId = Integer.parseInt(this.get("Granite", Configuration.CATEGORY_BLOCK, 189).getString());
        graniteBrickId = Integer.parseInt(this.get("Granite Bricks", Configuration.CATEGORY_BLOCK, 190).getString());
        leavesId = Integer.parseInt(this.get("Leaves", Configuration.CATEGORY_BLOCK, 191).getString());
        ironbarkId = Integer.parseInt(this.get("Ironbark", Configuration.CATEGORY_BLOCK, 192).getString());
        planksId = Integer.parseInt(this.get("Planks", Configuration.CATEGORY_BLOCK, 193).getString());
        saplingId = Integer.parseInt(this.get("Sapling", Configuration.CATEGORY_BLOCK, 194).getString());
        tannerId = Integer.parseInt(this.get("Tanning Rack", Configuration.CATEGORY_BLOCK, 195).getString());
        roadId = Integer.parseInt(this.get("Road", Configuration.CATEGORY_BLOCK, 196).getString());
        dogbowlId = Integer.parseInt(this.get("Dog Bowl", Configuration.CATEGORY_BLOCK, 197).getString());
        totemId = Integer.parseInt(this.get("Totem", Configuration.CATEGORY_BLOCK, 198).getString());
        oreIgnotumiteId = Integer.parseInt(this.get("Ignotumite ore", Configuration.CATEGORY_BLOCK, 199).getString());
        stairStoneId = Integer.parseInt(this.get("Stone Stairs", Configuration.CATEGORY_BLOCK, 200).getString());
        stairCStoneId = Integer.parseInt(this.get("Cobblestone Brick Stairs", Configuration.CATEGORY_BLOCK, 201).getString());
        weaponRackId = Integer.parseInt(this.get("Weapon Rack", Configuration.CATEGORY_BLOCK, 202).getString());
        hayRoofId = Integer.parseInt(this.get("Hay Roof", Configuration.CATEGORY_BLOCK, 203).getString());
        oreCopperId = Integer.parseInt(this.get("Copper Ore", Configuration.CATEGORY_BLOCK, 205).getString());
        oreTinId = Integer.parseInt(this.get("Tin Ore", Configuration.CATEGORY_BLOCK, 206).getString());
        oreMithicId = Integer.parseInt(this.get("Mithril Ore", Configuration.CATEGORY_BLOCK, 207).getString());
        bloomId = Integer.parseInt(this.get("Bloom", Configuration.CATEGORY_BLOCK, 208).getString());
        bellowsId = Integer.parseInt(this.get("Bellows", Configuration.CATEGORY_BLOCK, 209).getString());
        limestoneId = Integer.parseInt(this.get("Limestone", Configuration.CATEGORY_BLOCK, 210).getString());
        BlastId = Integer.parseInt(this.get("Blast Furnace Blocks", Configuration.CATEGORY_BLOCK, 211).getString());
        ProspectorOreId = Integer.parseInt(this.get("Hidden Ore Block", Configuration.CATEGORY_BLOCK, 212).getString());
        infernoId = Integer.parseInt(this.get("Inferno Coal Ore", Configuration.CATEGORY_BLOCK, 213).getString());
        ovenId = Integer.parseInt(this.get("Oven", Configuration.CATEGORY_BLOCK, 214).getString());
        ironbarkDoorId = Integer.parseInt(this.get("Ironbark Door", Configuration.CATEGORY_BLOCK, 215).getString());
        hardDoorId = Integer.parseInt(this.get("Reinforced Door", Configuration.CATEGORY_BLOCK, 216).getString());
        steelDoorId = Integer.parseInt(this.get("Steel Door", Configuration.CATEGORY_BLOCK, 217).getString());
        storeId = Integer.parseInt(this.get("Storage Block", Configuration.CATEGORY_BLOCK, 219).getString());
        iceId = Integer.parseInt(this.get("Ice Block", Configuration.CATEGORY_BLOCK, 220).getString());
        chimId = Integer.parseInt(this.get("Chimney Block", Configuration.CATEGORY_BLOCK, 221).getString());
        dirtSlabDId = Integer.parseInt(this.get("Dirt Slab", Configuration.CATEGORY_BLOCK, 222).getString());
        dirtSlabSId = Integer.parseInt(this.get("Dirt Single Slab", Configuration.CATEGORY_BLOCK, 223).getString());
        dirtSlabDIdMF = Integer.parseInt(this.get("MF Dirt Slab", Configuration.CATEGORY_BLOCK, 224).getString());
        dirtSlabSIdMF = Integer.parseInt(this.get("MF Dirt Single Slab", Configuration.CATEGORY_BLOCK, 225).getString());
        firepitId = Integer.parseInt(this.get("Firepit", Configuration.CATEGORY_BLOCK, 226).getString());
        
        WslabId = Integer.parseInt(this.get("Single Wood Slab", Configuration.CATEGORY_BLOCK, 227).getString());
        WdSlabId = Integer.parseInt(this.get("Double Wood Slab", Configuration.CATEGORY_BLOCK, 228).getString());
        SslabId = Integer.parseInt(this.get("Single Stone Slab", Configuration.CATEGORY_BLOCK, 229).getString());
        SdSlabId = Integer.parseInt(this.get("Double Stone Slab", Configuration.CATEGORY_BLOCK, 230).getString());
        tripHammerId = Integer.parseInt(this.get("Trip Hammer", Configuration.CATEGORY_BLOCK, 231).getString());
        slagId = Integer.parseInt(this.get("Slag", Configuration.CATEGORY_BLOCK, 232).getString());
        roastId = Integer.parseInt(this.get("Spit Roast", Configuration.CATEGORY_BLOCK, 233).getString());
        SlslabId = Integer.parseInt(this.get("Single Slate Slab", Configuration.CATEGORY_BLOCK, 234).getString());
        SldSlabId = Integer.parseInt(this.get("Double Slate Slab", Configuration.CATEGORY_BLOCK, 235).getString());
        
        stairSlateId = Integer.parseInt(this.get("Slate Stairs", Configuration.CATEGORY_BLOCK, 236).getString());
        stairSlateTileId = Integer.parseInt(this.get("Slate Tile Stairs", Configuration.CATEGORY_BLOCK, 237).getString());
        stairDSlateTileId = Integer.parseInt(this.get("Diagonal Slate Tile Stairs", Configuration.CATEGORY_BLOCK, 238).getString());
        stairSlateBrickId = Integer.parseInt(this.get("Slate Brick Stairs", Configuration.CATEGORY_BLOCK, 239).getString());
        
        furnaceOffId = Integer.parseInt(this.get("Furnace Block Inactive", Configuration.CATEGORY_BLOCK, 240).getString());
        furnaceOnId = Integer.parseInt(this.get("Furnace Block Active", Configuration.CATEGORY_BLOCK, 241).getString());
        foodPrepId = Integer.parseInt(this.get("Benchtop", Configuration.CATEGORY_BLOCK, 242).getString());
        tailorId = Integer.parseInt(this.get("Tailor Bench", Configuration.CATEGORY_BLOCK, 243).getString());
        spinnerId = Integer.parseInt(this.get("Spinning Wheel", Configuration.CATEGORY_BLOCK, 244).getString());
        
        stairCStoneRoughId = Integer.parseInt(this.get("Rough Cobblestone Brick Stairs", Configuration.CATEGORY_BLOCK, 245).getString());
        
        renderId = Integer.parseInt(this.get("Render ID", Configuration.CATEGORY_BLOCK, 200).getString());
        advancedAnvil = Boolean.parseBoolean(this.get("Advanced Forging", Configuration.CATEGORY_BLOCK, true).getString());
        lightForge = Boolean.parseBoolean(this.get("Forges need to be lit", Configuration.CATEGORY_BLOCK, true).getString());
        //ACHIEVEMENT
        achCopper = Integer.parseInt(this.get("Copper Age Achievement Id", this.CATEGORY_ACHIEVEMENTS, 57).getString());
        achAnvil = Integer.parseInt(this.get("Anvil Achievement Id", this.CATEGORY_ACHIEVEMENTS, 58).getString());
        achBomb = Integer.parseInt(this.get("Bomb Achievement Id", this.CATEGORY_ACHIEVEMENTS, 59).getString());
        achTin = Integer.parseInt(this.get("Tin Achievement Id", this.CATEGORY_ACHIEVEMENTS, 60).getString());
        achAlloy = Integer.parseInt(this.get("Alloy Achievement Id", this.CATEGORY_ACHIEVEMENTS, 61).getString());
        achBronze = Integer.parseInt(this.get("Bronze Age Achievement Id", this.CATEGORY_ACHIEVEMENTS, 62).getString());
        achSuperore = Integer.parseInt(this.get("Ignotumite Achievement Id", this.CATEGORY_ACHIEVEMENTS, 63).getString());
        achDragon = Integer.parseInt(this.get("Dragonslayer Achievement Id", this.CATEGORY_ACHIEVEMENTS, 64).getString());
        achDragoningot = Integer.parseInt(this.get("Dragonforger Achievement Id", this.CATEGORY_ACHIEVEMENTS, 65).getString());
        achTriplekill = Integer.parseInt(this.get("Triple Kill Achievement Id", this.CATEGORY_ACHIEVEMENTS, 66).getString());
        achTanner = Integer.parseInt(this.get("Tanner Achievement Id", this.CATEGORY_ACHIEVEMENTS, 67).getString());
        achPlate = Integer.parseInt(this.get("Plate Achievement Id", this.CATEGORY_ACHIEVEMENTS, 68).getString());
        achArrow = Integer.parseInt(this.get("Arrow Achievement Id", this.CATEGORY_ACHIEVEMENTS, 69).getString());
        achIron = Integer.parseInt(this.get("Iron Age Achievement Id", this.CATEGORY_ACHIEVEMENTS, 70).getString());
        achGranite = Integer.parseInt(this.get("Granite Achievement Id", this.CATEGORY_ACHIEVEMENTS, 71).getString());
        achForge = Integer.parseInt(this.get("Blacksmith Achievement Id", this.CATEGORY_ACHIEVEMENTS, 72).getString());
        achSteel = Integer.parseInt(this.get("Steel Age Achievement Id", this.CATEGORY_ACHIEVEMENTS, 73).getString());
        achMithril = Integer.parseInt(this.get("Mithril Achievement Id", this.CATEGORY_ACHIEVEMENTS, 74).getString());
        achEncrust = Integer.parseInt(this.get("Encrusted Achievement Id", this.CATEGORY_ACHIEVEMENTS, 75).getString());

        //MOBS
        entityIDBase = Integer.parseInt(this.get("Base Entity ID", this.CATEGORY_MOBS, 400).getString());
        
        houndSpawnrate = Integer.parseInt(this.get("Hound Spawn Rate", this.CATEGORY_MOBS, 4).getString());
        minotaurSpawnrate = Integer.parseInt(this.get("Minotaur Spawn Rate", this.CATEGORY_MOBS, 5).getString());
        knightSpawnrate = Integer.parseInt(this.get("Skeletal Knight Spawn Rate", this.CATEGORY_MOBS, 3).getString());
        drakeSpawnrate = Integer.parseInt(this.get("Drake Spawn Rate", this.CATEGORY_MOBS, 2).getString());
        dragonSpawnrateNether = Integer.parseInt(this.get("Small Dragon Nether Spawn Rate", this.CATEGORY_MOBS, 1).getString());
        dragonGrief = Boolean.parseBoolean(this.get("Dragons Spread fire/Break blocks", this.CATEGORY_MOBS, true).getString());
        houndKillMan = Boolean.parseBoolean(this.get("Hounds Attack Villagers", this.CATEGORY_MOBS, true).getString());
        houndNoFire = Boolean.parseBoolean(this.get("Prevent Hound Fire Death", this.CATEGORY_MOBS, true).getString());
        hungryHound = Boolean.parseBoolean(this.get("Hounds Get Hungry", this.CATEGORY_MOBS, true).getString());
        starveHound = Boolean.parseBoolean(this.get("Hounds starve to death", this.CATEGORY_MOBS, true).getString());
        dropMutton = Boolean.parseBoolean(this.get("Sheep drop Mutton", this.CATEGORY_MOBS, true).getString());
        basilSpawnrate = Integer.parseInt(this.get("Basillisk Spawn Rate", this.CATEGORY_MOBS, 2).getString());
        basilSpawnrateNether = Integer.parseInt(this.get("Nether Basillisk Spawn Rate", this.CATEGORY_MOBS, 6).getString());
        
        minotaurWeaponDist = Integer.parseInt(this.get("Minotaur weapon spawn distance", this.CATEGORY_MOBS, 1000).getString());
        titanDist = Integer.parseInt(this.get("Minotaur Titan Spawn distance", this.CATEGORY_MOBS, 2000).getString());
        dragonDistance = Integer.parseInt(this.get("Dragon spawn min distance", this.CATEGORY_MOBS, 2000).getString());
        drakeDistance = Integer.parseInt(this.get("Drake spawn min distance", this.CATEGORY_MOBS, 1800).getString());
        basiliskDistance = Integer.parseInt(this.get("Basilisk spawn min distance", this.CATEGORY_MOBS, 2500).getString());
        
        minoDiff = Integer.parseInt(this.get("Minotaur Spawn Difficulty", this.CATEGORY_MOBS, 2).getString());
        knightDiff = Integer.parseInt(this.get("Skeletal Knight Spawn Difficulty", this.CATEGORY_MOBS, 2).getString());
        knightLvl = Integer.parseInt(this.get("Minimal Xp level for skeletal knights", this.CATEGORY_MOBS, 35).getString());
        dragonDiff = Integer.parseInt(this.get("Small Dragon Spawn Difficulty", this.CATEGORY_MOBS, 2).getString());
       
        dragonChance = Integer.parseInt(this.get("(1 in X)Small Dragon Spawn Chance", this.CATEGORY_MOBS, 4000).getString());
        
        easyTameHound = Boolean.parseBoolean(this.get("Easy Tame Hounds", Configuration.CATEGORY_GENERAL, false).getString());
        HoundBreed = Integer.parseInt(this.get("Hound Breed Difficulty(must be +) Higher = harder", Configuration.CATEGORY_GENERAL, 10).getString());
        durableArrows = Boolean.parseBoolean(this.get("Arrows don't always break", Configuration.CATEGORY_GENERAL, true).getString());
        disableFirebomb = Boolean.parseBoolean(this.get("Disable Fire Bomb Grief", Configuration.CATEGORY_GENERAL, false).getString());
        
        //ORES
        spawnSilver = Boolean.parseBoolean(this.get("Generate Silver Ore", this.CATEGORY_OREGENERATIONS, true).getString());
        spawnNitre = Boolean.parseBoolean(this.get("Generate Nitre Ore", this.CATEGORY_OREGENERATIONS, true).getString());
        spawnSulfur = Boolean.parseBoolean(this.get("Generate Sulfur Ore", this.CATEGORY_OREGENERATIONS, true).getString());
        
        spawnCopper = Boolean.parseBoolean(this.get("Generate Copper Ore", this.CATEGORY_OREGENERATIONS, true).getString());
        spawnTin = Boolean.parseBoolean(this.get("Generate Tin Ore", this.CATEGORY_OREGENERATIONS, true).getString());
        spawnMithril = Boolean.parseBoolean(this.get("Generate Mithril Ore", this.CATEGORY_OREGENERATIONS, true).getString());
        spawnDeepIron = Boolean.parseBoolean(this.get("Generate Deep Iron Ore", this.CATEGORY_OREGENERATIONS, true).getString());
        spawnIgnot = Boolean.parseBoolean(this.get("Generate Ignotumite Ore", this.CATEGORY_OREGENERATIONS, true).getString());
        
        spawnIBark = Boolean.parseBoolean(this.get("Generate Ironbark", this.CATEGORY_OREGENERATIONS, true).getString());
        spawnEbony = Boolean.parseBoolean(this.get("Generate Ebony", this.CATEGORY_OREGENERATIONS, true).getString());
        
        limeCavern = Boolean.parseBoolean(this.get("Generate Limestone Cavern", this.CATEGORY_OREGENERATIONS, true).getString());
        generateSlate = Boolean.parseBoolean(this.get("Generate Slate", this.CATEGORY_OREGENERATIONS, true).getString());
        mithrilDistance = Double.parseDouble(this.get("Mithril Min Distance", this.CATEGORY_OREGENERATIONS, 1000D).getString());
        //STATS
        statDragons = Integer.parseInt(this.get("Dragons Slain Stat Id", Configuration.CATEGORY_GENERAL, 5000).getString());
        
        //MISC
        useBalance = Boolean.parseBoolean(this.get("Heavy Weapon balance offset", CATEGORY_COMBAT, true).getString());
        heavyBonus = Boolean.parseBoolean(this.get("Heavy Weapon combat bonus", CATEGORY_COMBAT, false).getString());
        renderHot = Boolean.parseBoolean(this.get("Dynamic Hot Ingot Rendering", Configuration.CATEGORY_GENERAL, true).getString());
        weakIron = Boolean.parseBoolean(this.get("Mine Iron with stone (Not recommended, ruins bronze tiering)", Configuration.CATEGORY_GENERAL, false).getString());
        //regen = Boolean.parseBoolean(this.get("Force Regen: Loads MineFantasy Gen on worlds", Configuration.CATEGORY_GENERAL, false).getString());
        //regenLayer = Integer.parseInt(this.get("Regen Layer: Only change if you already generated on a world", Configuration.CATEGORY_GENERAL, 0).getString());
        maxHeat = Integer.parseInt(this.get("Maximum Metal Heat(Celcius): The point where ingot rendering hits yellow", Configuration.CATEGORY_GENERAL, 1400).getString());
        dryRocksChance = Double.parseDouble(this.get("Dry Rocks Success chance(As a decimal: 1 = 100% 0.5 = 50%)", CATEGORY_MISC, 0.1D).getString());
        redstoneHammer = Boolean.parseBoolean(this.get("Automatic Trip hammer with Redstone", Configuration.CATEGORY_GENERAL, true).getString());
        hitSound = Boolean.parseBoolean(this.get("Weapon Hit Sound", CATEGORY_COMBAT, true).getString());
        burnPlayer = Boolean.parseBoolean(this.get("Hot Items Burn Player", Configuration.CATEGORY_GENERAL, true).getString());
        
        disableWeight = Boolean.parseBoolean(this.get("Disable Armour weight slowing (This will break the armour tiering, and make nearly all armours obsolete!)", Configuration.CATEGORY_GENERAL, false).getString());
        
        loadArmoursFromList(0, configuration.get(CATEGORY_COMBAT, "Light Armour list (put the ID and : after each eg. id1:id2:id3:id4: )", "").getString(), "Light Armour list");
        loadArmoursFromList(1, configuration.get(CATEGORY_COMBAT, "Medium Armour list (put the ID and : after each eg. id1:id2:id3:id4: )", "").getString(), "Medium Armour list");
        loadArmoursFromList(2, configuration.get(CATEGORY_COMBAT, "Heavy Armour list (put the ID and : after each eg. id1:id2:id3:id4: )", "").getString(), "Heavy Armour list");
        loadArmoursFromList(3, configuration.get(CATEGORY_COMBAT, "Plate Armour list (put the ID and : after each eg. id1:id2:id3:id4: )", "").getString(), "Plate Armour list");
        
        loadHangablesFromList(configuration.get(CATEGORY_MISC, "Hangable Item list (put the ID and : after each eg. id1:id2:id3:id4: )", "").getString());
        
        craftMetreX = Integer.parseInt(this.get("Craft Metre X Pos (from crosshair)", CATEGORY_HUD, 0).getString());
        craftMetreY = Integer.parseInt(this.get("Craft Metre Y Pos (from bottom)", CATEGORY_HUD, 69).getString());
        disableHUD = Boolean.parseBoolean(this.get("Disable Craft HUD", CATEGORY_HUD, false).getString());
        
        basiliskMax = Integer.parseInt(this.get("Max basilisk meat buff (each 1.0 = half heart): default 60(30hearts)", CATEGORY_MISC, 60).getString());
        
        //hardcore
        hardcoreCraft = Boolean.parseBoolean(this.get("Hardcore Crafting", CATEGORY_HARDCORE, false).getString());
        hardcoreLeather = Boolean.parseBoolean(this.get("Hardcore Leather Tanning", CATEGORY_HARDCORE, false).getString());
        hardcoreFurnace = Boolean.parseBoolean(this.get("Remove Basic Furnace Recipe", CATEGORY_HARDCORE, true).getString());
        easyBlooms = Boolean.parseBoolean(this.get("Easy Bloomery Smelts", CATEGORY_HARDCORE, false).getString());
        easyIron = Boolean.parseBoolean(this.get("Simple Iron Smelting", CATEGORY_HARDCORE, false).getString());
        
        configuration.save();
    }

	public Property get(String string, String category, int i) {
		return config.get(category, string, i);
	}
	public Property get(String string, String category, double i) {
		return config.get(category, string, i);
	}
	public Property get(String string, String category, boolean i) {
		return config.get(category, string, i);
	}
	public Property get(String string, String category, String i) {
		return config.get(category, string, i);
	}
	
	private static void loadArmoursFromList(int tier, String str, String type)
	{
		if(MineFantasyBase.isDebug())
		{
			System.out.println("MineFantasy: Loading Config List for " + type);
		}
		try
		{
			String temp = "";
			for(int a = 0; a < str.length(); a ++)
			{
				if(a == str.length()-1)
				{
					temp = temp + str.charAt(a);
				}
				if(str.charAt(a) == ":".charAt(0) || a == str.length()-1)
				{
					int i = (Integer.valueOf(temp));
					minefantasy.api.armour.ArmourWeightClass.configArmours.put(i, tier);
					if(MineFantasyBase.isDebug())
					{
						System.out.println("MineFantasy: Added Id " + i + " to " + type);
					}
					temp = "";
				}
				else
				{
					if(str.charAt(a) != " ".charAt(0))
					{
						temp = temp + str.charAt(a);
					}
				}
			}
		}catch(Exception e)
		{
			System.out.println("MineFantasy: Failed to load config list: Make sure each number is followed by a : ");
		}
	}
	
	
	
	private static void loadHangablesFromList(String str)
	{
		String type = "Hangable Item Renders";
		
		if(MineFantasyBase.isDebug())
		{
			System.out.println("MineFantasy: Loading Config List for " + type);
		}
		try
		{
			String temp = "";
			for(int a = 0; a < str.length(); a ++)
			{
				if(a == str.length()-1)
				{
					temp = temp + str.charAt(a);
				}
				if(str.charAt(a) == ":".charAt(0) || a == str.length()-1)
				{
					int i = (Integer.valueOf(temp));
					hangables.put(i, true);
					if(MineFantasyBase.isDebug())
					{
						System.out.println("MineFantasy: Added Id " + i + " to " + type);
					}
					temp = "";
				}
				else
				{
					if(str.charAt(a) != " ".charAt(0))
					{
						temp = temp + str.charAt(a);
					}
				}
			}
		}catch(Exception e)
		{
			System.out.println("MineFantasy: Failed to load config list: Make sure each number is followed by a : ");
		}
	}
	
	/*public static EnumArmourClass getClassFor(ItemStack item, EnumArmourClass Default)
	{
		int id = item.itemID;
		
		if(!minefantasy.api.armour.ArmourClass.configArmours.isEmpty() && minefantasy.api.armour.ArmourClass.configArmours.containsKey(id))
		{
			if(hasId(0, id))return EnumArmourClass.LIGHT;
			if(hasId(1, id))return EnumArmourClass.MEDIUM;
			if(hasId(2, id))return EnumArmourClass.HEAVY;
			if(hasId(3, id))return EnumArmourClass.PLATE;
		}
		
		return Default;
	}

	private static boolean hasId(int tier, int id)
	{
		return minefantasy.api.armour.ArmourClass.configArmours.get(id) != null && minefantasy.api.armour.ArmourClass.configArmours.get(id) == tier;
	}*/
	
	public static boolean canRenderHung(int id)
	{
		if(!hangables.isEmpty() && hangables.containsKey(id))
		{
			return hangables.get(id);
		}
		return false;
	}
}
