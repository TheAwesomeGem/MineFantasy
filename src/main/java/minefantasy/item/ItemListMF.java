package minefantasy.item;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import minefantasy.MineFantasyBase;
import minefantasy.item.armour.EnumArmourMF;
import minefantasy.item.armour.ItemArmourMF;
import minefantasy.item.mabShield.*;
import minefantasy.item.tool.*;
import minefantasy.item.weapon.*;
import minefantasy.api.Components;
import minefantasy.api.MineFantasyAPI;
import minefantasy.api.anvil.ItemRepairHammer;
import minefantasy.api.weapon.CrossbowAmmo;
import minefantasy.api.weapon.EnumAmmo;
import minefantasy.block.BlockListMF;
import minefantasy.block.EnumMFDoor;
import minefantasy.entity.EntityArrowMF;
import minefantasy.entity.dispense.*;
import minefantasy.system.ArrowsMF;
import minefantasy.system.cfg;
import mods.battlegear2.api.quiver.QuiverArrowRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.gen.structure.ComponentVillageHouse2;
import net.minecraft.world.gen.structure.StructureVillagePieceWeight;
import net.minecraftforge.common.ChestGenHooks;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

//BlockListMF
public class ItemListMF 
{
	public static CreativeTabs tabPets = new CreativeTabPet(CreativeTabs.getNextID(), "pets");
	public static CreativeTabs tabWeapon = new CreativeTabMF(CreativeTabs.getNextID(), "forgedweapon", 0);
	public static CreativeTabs tabArmour = new CreativeTabMF(CreativeTabs.getNextID(), "forgedarmour", 1);
	public static CreativeTabs tabTool = new CreativeTabMF(CreativeTabs.getNextID(), "forgedtool", 2);
	public static CreativeTabs tabTailor = new CreativeTabMF(CreativeTabs.getNextID(), "tailor", 3);
	public static CreativeTabs tabArcher = new CreativeTabMF(CreativeTabs.getNextID(), "archery", 4);
	
	public static int itemId = cfg.itemId;
	
	public static final Item hammerIron = new ItemHammer(itemId, 1.0F, ToolMaterialMedieval.IRON).setUnlocalizedName("hammerIron");
    public static final Item ingotSteel = new ItemMedieval(itemId + 1, false, 64).setUnlocalizedName("ingotSteel");
    public static final Item tongsBronze = new ItemTongs(itemId + 2, ToolMaterialMedieval.BRONZE).setUnlocalizedName("tongsBronze");
    public static final Item apronSmithy = new ItemArmourMF(itemId + 3, ArmourDesign.LEATHER, EnumArmourMF.APRON, 1, 1, "smithApron").setUnlocalizedName("apronSmithy").setCreativeTab(tabArmour);
    public static final Item plank = new ItemMedieval(itemId + 4, false, 64).setUnlocalizedName("plank");
    public static final Item pickIronForged = new ItemMedievalPick(itemId + 5, ToolMaterialMedieval.IRON).setUnlocalizedName("pickIronForged");
    public static final Item pickSteelForged = new ItemMedievalPick(itemId + 6, ToolMaterialMedieval.STEEL).setUnlocalizedName("pickSteelForged");
    public static final Item pickIgnotumiteForged = new ItemMedievalPick(itemId + 55, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("pickIgnotumiteForged");
    public static final Item knifeStone = new ItemKnifeMF(itemId + 66, 3, ToolMaterialMedieval.PRIMITIVE_STONE).setUnlocalizedName("knifeStone");
    public static final Item mattockMithril = new ItemMattock(itemId + 7, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("mattockMithril");
    public static final Item swordIronForged = new ItemSwordMF(itemId + 8, ToolMaterialMedieval.IRON).setUnlocalizedName("swordIron");
    public static final Item swordSteelForged = new ItemSwordMF(itemId + 9, ToolMaterialMedieval.STEEL).setUnlocalizedName("swordSteel");
    public static final Item swordIgnotumite = new ItemSwordMF(itemId + 56, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("swordIgnotumite");
    public static final Item swordDragon = new ItemSwordMF(itemId + 67, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("swordDragonforge");
    public static final Item knifeMithril = new ItemKnifeMF(itemId + 10, 6, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("knifeMithril");
    public static final Item swordOrnate = new ItemSwordMF(itemId + 11, ToolMaterialMedieval.ORNATE).setUnlocalizedName("swordOrnate");
    public static final Item axeIronForged = new ItemMedievalAxe(itemId + 12, ToolMaterialMedieval.IRON).setUnlocalizedName("axeIronForged");
    public static final Item axeSteelForged = new ItemMedievalAxe(itemId + 13, ToolMaterialMedieval.STEEL).setUnlocalizedName("axeSteelForged");
    public static final Item axeIgnotumiteForged = new ItemMedievalAxe(itemId + 57, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("axeIgnotumiteForged");
    public static final Item swordTin = new ItemSwordMF(itemId + 68, ToolMaterialMedieval.TIN).setUnlocalizedName("swordTin");
    public static final Item sawMithril = new ItemSaw(itemId + 14, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("sawMithril");
    public static final Item spadeIronForged = new ItemMedievalSpade(itemId + 15, ToolMaterialMedieval.IRON).setUnlocalizedName("spadeIronForged");
    public static final Item spadeSteelForged = new ItemMedievalSpade(itemId + 16, ToolMaterialMedieval.STEEL).setUnlocalizedName("spadeSteel");
    public static final Item spadeIgnotumiteForged = new ItemMedievalSpade(itemId + 58, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("spadeIgnotumite");
    public static final Item knifeSteel = new ItemKnifeMF(itemId + 69, 4, ToolMaterialMedieval.STEEL).setUnlocalizedName("knifeSteel");
    public static final Item shearsMithril = new ItemShearsMF(itemId + 17, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("shearsMithril");
    public static final Item hoeIronForged = new ItemMedievalHoe(itemId + 18, ToolMaterialMedieval.IRON).setUnlocalizedName("hoeIronForged");
    public static final Item hoeSteelForged = new ItemMedievalHoe(itemId + 19, ToolMaterialMedieval.STEEL).setUnlocalizedName("hoeSteelForged");
    public static final Item rakeBronze = new ItemRakeMF(itemId + 59, ToolMaterialMedieval.BRONZE).setUnlocalizedName("rakeBronze");
    public static final Item rakeIron = new ItemRakeMF(itemId + 70, ToolMaterialMedieval.IRON).setUnlocalizedName("rakeIron");
    public static final Item rakeMithril = new ItemRakeMF(itemId + 20, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("rakeMithril");
    
    public static final Item helmetBronzeScale = (new ItemArmourMF(itemId + 21, ArmourDesign.SCALE, EnumArmourMF.BRONZE, 1, 0, "bronzeScale_1")).setUnlocalizedName("bronzeScaleHelmet");
    public static final Item plateBronzeScale = (new ItemArmourMF(itemId + 22, ArmourDesign.SCALE, EnumArmourMF.BRONZE, 1, 1, "bronzeScale_1")).setUnlocalizedName("bronzeScaleChest");
    public static final Item legsBronzeScale = (new ItemArmourMF(itemId + 23, ArmourDesign.SCALE, EnumArmourMF.BRONZE, 2, 2, "bronzeScale_2")).setUnlocalizedName("bronzeScaleLegs");
    public static final Item bootsBronzeScale = (new ItemArmourMF(itemId + 24, ArmourDesign.SCALE, EnumArmourMF.BRONZE, 2, 3, "bronzeScale_1")).setUnlocalizedName("bronzeScaleBoots");
    
    public static final Item helmetSteelPlate = (new ItemArmourMF(itemId + 25, ArmourDesign.PLATE, EnumArmourMF.STEEL, 0, 0, "steelPlate_1")).setUnlocalizedName("steelPlateHelmet");
    public static final Item plateSteelPlate = (new ItemArmourMF(itemId + 26, ArmourDesign.PLATE, EnumArmourMF.STEEL, 0, 1, "steelPlate_1")).setUnlocalizedName("steelPlateChest");
    public static final Item legsSteelPlate = (new ItemArmourMF(itemId + 27, ArmourDesign.PLATE, EnumArmourMF.STEEL, 0, 2, "steelPlate_2")).setUnlocalizedName("steelPlateLegs");
    public static final Item bootsSteelPlate = (new ItemArmourMF(itemId + 28, ArmourDesign.PLATE, EnumArmourMF.STEEL, 0, 3, "steelPlate_1")).setUnlocalizedName("steelPlateBoots");
    
    public static final Item knifeCopper = new ItemKnifeMF(itemId + 33, 3, ToolMaterialMedieval.COPPER).setUnlocalizedName("knifeCopper");
    public static final Item hammerCopper = new ItemHammer(itemId + 34, 0.4F, ToolMaterialMedieval.COPPER).setUnlocalizedName("hammerCopper");
    public static final Item ingotSilver = new ItemMedieval(itemId + 35, false, 64).setUnlocalizedName("ingotSilver");
    public static final Item tinderbox = new ItemLighter(itemId + 36, 64, 0.25F).setUnlocalizedName("tinderbox");
    //public static final Item knife = new ItemThrowable(itemId + 37, false).setUnlocalizedName("knife");
    //public static final Item iroShard = new ItemMedieval(itemId + 38, false, 64).setUnlocalizedName("iroShard");
    public static final Item bombMF = new ItemBombMF(itemId + 39).setUnlocalizedName("bombIron");
    public static final Item malletWood = new ItemMallet(itemId + 40, EnumToolMaterial.WOOD).setUnlocalizedName("malletWood");
    public static final Item malletIronbark = new ItemMallet(itemId + 64, ToolMaterialMedieval.IRONBARK).setUnlocalizedName("malletIronbark");
    public static final Item explosive = new ItemMedieval(itemId + 41, false, 64).setUnlocalizedName("explosive");
    public static final Item misc = new ItemMedievalComponent(itemId + 42).setUnlocalizedName("dust");
    public static final Item hotItem = new ItemHotItem(itemId + 43).setUnlocalizedName("hotItem");
    public static final Item broadIron = new ItemBroadsword(itemId + 44, ToolMaterialMedieval.IRON).setUnlocalizedName("broadswordIron");
    public static final Item morningstarIron = new ItemGreatmace(itemId + 45, ToolMaterialMedieval.IRON).setUnlocalizedName("greatmaceIron");
    public static final Item warpickIron = new ItemWarpick(itemId + 46, ToolMaterialMedieval.IRON).setUnlocalizedName("warpickIron");
    public static final Item broadSteel = new ItemBroadsword(itemId + 47, ToolMaterialMedieval.STEEL).setUnlocalizedName("broadswordSteel");
    public static final Item morningstarSteel = new ItemGreatmace(itemId + 48, ToolMaterialMedieval.STEEL).setUnlocalizedName("greatmaceSteel");
    public static final Item warpickSteel = new ItemWarpick(itemId + 49, ToolMaterialMedieval.STEEL).setUnlocalizedName("warpickSteel");
    public static final Item broadOrnate = new ItemBroadsword(itemId + 60, ToolMaterialMedieval.ORNATE).setUnlocalizedName("broadswordOrnate");
    public static final Item broadDragon = new ItemBroadsword(itemId + 71, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("broadswordDragonforge");
    public static final Item rakeSteel = new ItemRakeMF(itemId + 61, ToolMaterialMedieval.STEEL).setUnlocalizedName("rakeSteel");
    public static final Item morningstarDragon = new ItemGreatmace(itemId + 72, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("greatmaceDragon");
    public static final Item waraxeSteel = new ItemWaraxe(itemId + 62, ToolMaterialMedieval.STEEL).setUnlocalizedName("waraxeSteel");
    public static final Item warpickDragon = new ItemWarpick(itemId + 73, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("warpickDragon");
    public static final Item waraxeEncrusted = new ItemWaraxe(itemId + 50, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("waraxeEncrusted");
    public static final Item greatmaceOrnate = new ItemGreatmace(itemId + 51, ToolMaterialMedieval.ORNATE).setUnlocalizedName("greatmaceOrnate");
    public static final Item waraxeDragon = new ItemWaraxe(itemId + 52, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("waraxeDragon");
    //public static final Item blackjack = new ItemMaceMF(itemId + 53, EnumToolMaterial.WOOD).setUnlocalizedName("blackjack");
    public static final Item maceEncrusted = new ItemMaceMF(itemId + 54, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("maceEncrusted");
    public static final Item knifeBronze = new ItemKnifeMF(itemId + 63, 4, ToolMaterialMedieval.BRONZE).setUnlocalizedName("knifeBronze").setCreativeTab(tabTool);
    public static final Item knifeIron = new ItemKnifeMF(itemId + 77, 4, ToolMaterialMedieval.IRON).setUnlocalizedName("knifeIron");
    public static final Item hammerOrnate = new ItemHammer(itemId + 86, 2F, ToolMaterialMedieval.ORNATE).setUnlocalizedName("hammerPower");
    public static final Item axePrim = new ItemMedievalAxe(itemId + 87, ToolMaterialMedieval.PRIMITIVE_STONE).setUnlocalizedName("axePrim");
    
    public static final Item helmetDragonPlate = (new ItemArmourMF(itemId + 78, ArmourDesign.PLATE, EnumArmourMF.DRAGONFORGE, 1, 0, "dragonforgePlate_1")).setUnlocalizedName("dragonforgePlateHelmet");
    public static final Item plateDragonPlate = (new ItemArmourMF(itemId + 79, ArmourDesign.PLATE, EnumArmourMF.DRAGONFORGE, 1, 1, "dragonforgePlate_1")).setUnlocalizedName("dragonforgePlateChest");
    public static final Item legsDragonPlate = (new ItemArmourMF(itemId + 80, ArmourDesign.PLATE, EnumArmourMF.DRAGONFORGE, 2, 2, "dragonforgePlate_2")).setUnlocalizedName("dragonforgePlateLegs");
    public static final Item bootsDragonPlate = (new ItemArmourMF(itemId + 81, ArmourDesign.PLATE, EnumArmourMF.DRAGONFORGE, 2, 3, "dragonforgePlate_1")).setUnlocalizedName("dragonforgePlateBoots");
    
    public static final Item greatmaceEncrusted = new ItemGreatmace(itemId + 88, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("greatmaceEncrusted");
    public static final Item tongsIron = new ItemTongs(itemId + 138, ToolMaterialMedieval.IRON).setUnlocalizedName("tongsIron");
    public static final Item arrowMF = new ItemArrowMF(itemId + 90).setUnlocalizedName("arrowMF");
    public static final Item tongsSteel = new ItemTongs(itemId + 91, ToolMaterialMedieval.STEEL).setUnlocalizedName("tongsSteel");
    public static final Item tongsMithril = new ItemTongs(itemId + 89, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("tongsMithril");
    
    public static final Item pickTin = new ItemMedievalPick(itemId + 92, ToolMaterialMedieval.TIN).setUnlocalizedName("pickTin");
    public static final Item spadeTin = new ItemMedievalSpade(itemId + 93, ToolMaterialMedieval.TIN).setUnlocalizedName("spadeTin");
    public static final Item hoeTin = new ItemMedievalHoe(itemId + 94, ToolMaterialMedieval.TIN).setUnlocalizedName("hoeTin");
    public static final Item knifeTin = new ItemKnifeMF(itemId + 95, 3, ToolMaterialMedieval.TIN).setUnlocalizedName("knifeTin");
    public static final Item axeTin = new ItemMedievalAxe(itemId + 96, ToolMaterialMedieval.TIN).setUnlocalizedName("axeTin");

	public static final Item hammerBronze = new ItemHammer(itemId + 97, 0.6F, ToolMaterialMedieval.BRONZE).setUnlocalizedName("hammerBronze");
	public static final Item pickBronze = new ItemMedievalPick(itemId + 98, ToolMaterialMedieval.BRONZE).setUnlocalizedName("pickBronze");
    public static final Item spadeBronze = new ItemMedievalSpade(itemId + 99, ToolMaterialMedieval.BRONZE).setUnlocalizedName("spadeBronze");
    public static final Item hoeBronze = new ItemMedievalHoe(itemId + 100, ToolMaterialMedieval.BRONZE).setUnlocalizedName("hoeBronze");
    public static final Item swordBronze = new ItemSwordMF(itemId + 101, ToolMaterialMedieval.BRONZE).setUnlocalizedName("swordBronze");
    public static final Item axeBronze = new ItemMedievalAxe(itemId + 102, ToolMaterialMedieval.BRONZE).setUnlocalizedName("axeBronze");
    public static final Item broadBronze = new ItemBroadsword(itemId + 103, ToolMaterialMedieval.BRONZE).setUnlocalizedName("broadswordBronze");
    public static final Item morningstarBronze = new ItemGreatmace(itemId + 104, ToolMaterialMedieval.BRONZE).setUnlocalizedName("greatmaceBronze");
    public static final Item warpickBronze = new ItemWarpick(itemId + 105, ToolMaterialMedieval.BRONZE).setUnlocalizedName("warpickBronze");
    
    public static final Item hammerSteel = new ItemHammer(itemId + 106, 1.8F, ToolMaterialMedieval.STEEL).setUnlocalizedName("hammerSteel");

    public static final Item pickMithril = new ItemMedievalPick(itemId + 107, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("pickMithril");
    public static final Item spadeMithril = new ItemMedievalSpade(itemId + 108, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("spadeMithril");
    public static final Item hoeMithril = new ItemMedievalHoe(itemId + 109, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("hoeMithril");
    public static final Item swordMithril = new ItemSwordMF(itemId + 110, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("swordMithril");
    public static final Item axeMithril = new ItemMedievalAxe(itemId + 111, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("axeMithril");
    public static final Item broadMithril = new ItemBroadsword(itemId + 112, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("broadswordMithril");
    public static final Item morningstarMithril = new ItemGreatmace(itemId + 113, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("greatmaceMithril");
    public static final Item warpickMithril = new ItemWarpick(itemId + 114, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("warpickMithril");
    
    public static final Item bowComposite = new ItemBowMF(itemId + 115, ToolMaterialMedieval.STRONGWOOD, EnumBowType.COMPOSITE).setUnlocalizedName("bowComposite");
    public static final Item bowIronbark = new ItemBowMF(itemId + 116, ToolMaterialMedieval.IRONBARK, EnumBowType.COMPOSITE).setUnlocalizedName("bowIronbark");
    public static final Item longbow = new ItemBowMF(itemId + 117, ToolMaterialMedieval.STRONGWOOD, EnumBowType.LONGBOW).setUnlocalizedName("longbow");
    public static final Item warhammerEncrusted = new ItemWarhammer(itemId + 118, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("warhammerEncrusted");
    
    public static final Item pickEncrusted = new ItemMedievalPick(itemId + 119, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("pickEncrusted");
    public static final Item spadeEncrusted = new ItemMedievalSpade(itemId + 120, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("spadeEncrusted");
    public static final Item mattockBronze = new ItemMattock(itemId + 121, ToolMaterialMedieval.BRONZE).setUnlocalizedName("mattockBronze");
    public static final Item swordEncrusted = new ItemSwordMF(itemId + 122, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("swordEncrusted");
    public static final Item axeEncrusted = new ItemMedievalAxe(itemId + 123, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("axeEncrusted");
    public static final Item broadEncrusted = new ItemBroadsword(itemId + 124, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("broadswordEncrusted");
    public static final Item mattockIron = new ItemMattock(itemId + 125, ToolMaterialMedieval.IRON).setUnlocalizedName("mattockIron");
    public static final Item warpickEncrusted = new ItemWarpick(itemId + 126, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("warpickEncrusted");
    
    public static final Item helmetBronzeChain = (new ItemArmourMF(itemId + 127, ArmourDesign.CHAIN, EnumArmourMF.BRONZE, 1, 0, "bronzeChain_1")).setUnlocalizedName("bronzeChainHelmet");
    public static final Item plateBronzeChain = (new ItemArmourMF(itemId + 128, ArmourDesign.CHAIN, EnumArmourMF.BRONZE, 1, 1, "bronzeChain_1")).setUnlocalizedName("bronzeChainChest");
    public static final Item legsBronzeChain = (new ItemArmourMF(itemId + 129, ArmourDesign.CHAIN, EnumArmourMF.BRONZE, 2, 2, "bronzeChain_2")).setUnlocalizedName("bronzeChainLegs");
    public static final Item bootsBronzeChain = (new ItemArmourMF(itemId + 130, ArmourDesign.CHAIN, EnumArmourMF.BRONZE, 2, 3, "bronzeChain_1")).setUnlocalizedName("bronzeChainBoots");
    
    public static final Item helmetBronzePlate = (new ItemArmourMF(itemId + 131, ArmourDesign.PLATE, EnumArmourMF.BRONZE, 1, 0, "bronzePlate_1")).setUnlocalizedName("bronzePlateHelmet");
    public static final Item plateBronzePlate = (new ItemArmourMF(itemId + 132, ArmourDesign.PLATE, EnumArmourMF.BRONZE, 1, 1, "bronzePlate_1")).setUnlocalizedName("bronzePlateChest");
    public static final Item legsBronzePlate = (new ItemArmourMF(itemId + 133, ArmourDesign.PLATE, EnumArmourMF.BRONZE, 2, 2, "bronzePlate_2")).setUnlocalizedName("bronzePlateLegs");
    public static final Item bootsBronzePlate = (new ItemArmourMF(itemId + 134, ArmourDesign.PLATE, EnumArmourMF.BRONZE, 2, 3, "bronzePlate_1")).setUnlocalizedName("bronzePlateBoots");
   
    public static final Item doorIronbark = (new ItemDoorMF(itemId + 135, EnumMFDoor.IRONBARK)).setUnlocalizedName("doorIronbarkItem");
    public static final Item doorHard = (new ItemDoorMF(itemId + 136, EnumMFDoor.REINFORCED)).setUnlocalizedName("doorHardItem");
    public static final Item doorSteel = (new ItemDoorMF(itemId + 137, EnumMFDoor.STEEL)).setUnlocalizedName("doorSteelItem");
    public static final Item axeDragon = new ItemMedievalAxe(itemId + 139, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("axeDragon");
    
    public static final Item hammerDragon = new ItemHammer(itemId + 140, 2.5F, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("hammerDragon");
    public static final Item hoeDragon = new ItemMedievalHoe(itemId + 141, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("hoeDragon");
    public static final Item knifeDragon = new ItemKnifeMF(itemId + 142, 5, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("knifeDragon");
    public static final Item mattockDragon = new ItemMattock(itemId + 143, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("mattockDragon");
    public static final Item rakeDragon = new ItemRakeMF(itemId + 144, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("rakeDragon");
    public static final Item pickDragon = new ItemMedievalPick(itemId + 145, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("pickDragon");
    
    public static final Item helmetEncrustedPlate = (new ItemArmourMF(itemId + 146, ArmourDesign.PLATE, EnumArmourMF.ENCRUSTED, 1, 0, "encrustedPlate_1")).setUnlocalizedName("encrustedPlateHelmet");
    public static final Item plateEncrustedPlate = (new ItemArmourMF(itemId + 147, ArmourDesign.PLATE, EnumArmourMF.ENCRUSTED, 1, 1, "encrustedPlate_1")).setUnlocalizedName("encrustedPlateChest");
    public static final Item legsEncrustedPlate = (new ItemArmourMF(itemId + 148, ArmourDesign.PLATE, EnumArmourMF.ENCRUSTED, 2, 2, "encrustedPlate_2")).setUnlocalizedName("encrustedPlateLegs");
    public static final Item bootsEncrustedPlate = (new ItemArmourMF(itemId + 149, ArmourDesign.PLATE, EnumArmourMF.ENCRUSTED, 2, 3, "encrustedPlate_1")).setUnlocalizedName("encrustedPlateBoots");

    public static final Item helmetSteelSplint = (new ItemArmourMF(itemId + 150, ArmourDesign.SPLINT, EnumArmourMF.STEEL, 1, 0, "steelSplint_1")).setUnlocalizedName("steelSplintHelmet");
    public static final Item plateSteelSplint = (new ItemArmourMF(itemId + 151, ArmourDesign.SPLINT, EnumArmourMF.STEEL, 1, 1, "steelSplint_1")).setUnlocalizedName("steelSplintChest");
    public static final Item legsSteelSplint = (new ItemArmourMF(itemId + 152, ArmourDesign.SPLINT, EnumArmourMF.STEEL, 2, 2, "steelSplint_2")).setUnlocalizedName("steelSplintLegs");
    public static final Item bootsSteelSplint = (new ItemArmourMF(itemId + 153, ArmourDesign.SPLINT, EnumArmourMF.STEEL, 2, 3, "steelSplint_1")).setUnlocalizedName("steelSplintBoots");

    public static final Item helmetSteelScale = (new ItemArmourMF(itemId + 154, ArmourDesign.SCALE, EnumArmourMF.STEEL, 1, 0, "steelScale_1")).setUnlocalizedName("steelScaleHelmet");
    public static final Item plateSteelScale = (new ItemArmourMF(itemId + 155, ArmourDesign.SCALE, EnumArmourMF.STEEL, 1, 1, "steelScale_1")).setUnlocalizedName("steelScaleChest");
    public static final Item legsSteelScale = (new ItemArmourMF(itemId + 156, ArmourDesign.SCALE, EnumArmourMF.STEEL, 2, 2, "steelScale_2")).setUnlocalizedName("steelScaleLegs");
    public static final Item bootsSteelScale = (new ItemArmourMF(itemId + 157, ArmourDesign.SCALE, EnumArmourMF.STEEL, 2, 3, "steelScale_1")).setUnlocalizedName("steelScaleBoots");

    public static final Item helmetMithrilSplint = (new ItemArmourMF(itemId + 158, ArmourDesign.SPLINT, EnumArmourMF.MITHRIL, 1, 0, "mithrilSplint_1")).setUnlocalizedName("mithrilSplintHelmet");
    public static final Item plateMithrilSplint = (new ItemArmourMF(itemId + 159, ArmourDesign.SPLINT, EnumArmourMF.MITHRIL, 1, 1, "mithrilSplint_1")).setUnlocalizedName("mithrilSplintChest");
    public static final Item legsMithrilSplint = (new ItemArmourMF(itemId + 160, ArmourDesign.SPLINT, EnumArmourMF.MITHRIL, 2, 2, "mithrilSplint_2")).setUnlocalizedName("mithrilSplintLegs");
    public static final Item bootsMithrilSplint = (new ItemArmourMF(itemId + 161, ArmourDesign.SPLINT, EnumArmourMF.MITHRIL, 2, 3, "mithrilSplint_1")).setUnlocalizedName("mithrilSplintBoots");
    
    //public static final Item helmetStud = (new ItemArmourMF(itemId + 162, ArmourDesign.STUDDED, MedievalArmourMaterial.LEATHER, 1, 0, "studded_1")).setUnlocalizedName("helmetStud");
    //public static final Item plateStud = (new ItemArmourMF(itemId + 163, ArmourDesign.STUDDED, MedievalArmourMaterial.LEATHER, 1, 1, "studded_1")).setUnlocalizedName("plateStud");
    //public static final Item legsStud = (new ItemArmourMF(itemId + 164, ArmourDesign.STUDDED, MedievalArmourMaterial.LEATHER, 2, 2, "studded_2")).setUnlocalizedName("legsStud");
    //public static final Item bootsStud = (new ItemArmourMF(itemId + 165, ArmourDesign.STUDDED, MedievalArmourMaterial.LEATHER, 2, 3, "studded_1")).setUnlocalizedName("bootsStud");
    
    public static final Item helmetIronHvyChain = (new ItemArmourMF(itemId + 166, ArmourDesign.HVYCHAIN, EnumArmourMF.IRON, 1, 0, "ironHvyChain_1")).setUnlocalizedName("ironHvyHelmet");
    public static final Item plateIronHvyChain = (new ItemArmourMF(itemId + 167, ArmourDesign.HVYCHAIN, EnumArmourMF.IRON, 1, 1, "ironHvyChain_1")).setUnlocalizedName("ironHvyChest");
    public static final Item legsIronHvyChain = (new ItemArmourMF(itemId + 168, ArmourDesign.HVYCHAIN, EnumArmourMF.IRON, 2, 2, "ironHvyChain_2")).setUnlocalizedName("ironHvyLegs");
    public static final Item bootsIronHvyChain = (new ItemArmourMF(itemId + 169, ArmourDesign.HVYCHAIN, EnumArmourMF.IRON, 2, 3, "ironHvyChain_1")).setUnlocalizedName("ironHvyBoots");

    public static final Item drakeRaw = (new ItemFoodMF(itemId + 170, 5, 0.5F, true)).setUnlocalizedName("drakeRaw");
    public static final Item drakeCooked = (new ItemFoodMF(itemId + 171, 12, 1.2F, true, new PotionEffect(Potion.field_76443_y.id, 20*60*5, 0, true))).setUnlocalizedName("drakeCooked");
   
    public static final Item maceCopper = new ItemMaceMF(itemId + 172, ToolMaterialMedieval.COPPER).setUnlocalizedName("maceCopper");
    public static final Item maceBronze = new ItemMaceMF(itemId + 173, ToolMaterialMedieval.BRONZE).setUnlocalizedName("maceBronze");
    public static final Item maceIron = new ItemMaceMF(itemId + 174, ToolMaterialMedieval.IRON).setUnlocalizedName("maceIron");
    public static final Item maceSteel = new ItemMaceMF(itemId + 175, ToolMaterialMedieval.STEEL).setUnlocalizedName("maceSteel");
    public static final Item maceMithril = new ItemMaceMF(itemId + 176, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("maceMithril");
    public static final Item maceDragon = new ItemMaceMF(itemId + 177, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("maceDragon");
    public static final Item maceOrnate = new ItemMaceMF(itemId + 178, ToolMaterialMedieval.ORNATE).setUnlocalizedName("maceOrnate");
    public static final Item waraxeBronze = new ItemWaraxe(itemId + 179, ToolMaterialMedieval.BRONZE).setUnlocalizedName("waraxeBronze");
    
    public static final Item hammerMithril = new ItemHammer(itemId + 180, 4F, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("hammerMithril");
    public static final Item scytheMithril = new ItemScytheMF(itemId + 181, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("scytheMithril");
    public static final Item hound_Iplate = new ItemHoundArmourMF(itemId + 182, EnumArmourMF.IRON, true, "iron", 1, 40).setUnlocalizedName("hound_Iplate");
    public static final Item hound_IplateH = new ItemHoundArmourMF(itemId + 183, EnumArmourMF.IRON, true, "iron", 0, 40).setUnlocalizedName("hound_IplateH");
    public static final Item sawDragon = new ItemSaw(itemId + 184, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("sawDragon");
    
    public static final Item hound_feed = new ItemHoundFeedbagMF(itemId + 185, 100, "bigPack", 0).setUnlocalizedName("hound_feed");
    public static final Item hound_sPack = new ItemHoundPackMF(itemId + 186, 1, 3, "pack", 10, 0, 10).setUnlocalizedName("hound_sPack");
    public static final Item hound_bPack = new ItemHoundPackMF(itemId + 187, 1, 6, "bigPack", 50, 0, 30).setUnlocalizedName("hound_bPack");
   
    public static final Item hammerTin = new ItemHammer(itemId + 188, 0.35F, ToolMaterialMedieval.TIN).setUnlocalizedName("hammerTin");
    public static final Item shearsTin = new ItemShearsMF(itemId + 189, ToolMaterialMedieval.TIN).setUnlocalizedName("shearsTin");
    
    public static final Item hound_BMail = new ItemHoundArmourMF(itemId + 190, EnumArmourMF.BRONZE, false, "bronze", 1, 5).setUnlocalizedName("hound_BMail");
    public static final Item hound_BMailH = new ItemHoundArmourMF(itemId + 191, EnumArmourMF.BRONZE, false, "bronze", 0, 5).setUnlocalizedName("hound_BMailH");
    public static final Item hound_IMail = new ItemHoundArmourMF(itemId + 192, EnumArmourMF.IRON, false, "iron", 1, 10).setUnlocalizedName("hound_IMail");
    public static final Item hound_IMailH = new ItemHoundArmourMF(itemId + 193, EnumArmourMF.IRON, false, "iron", 0, 10).setUnlocalizedName("hound_IMailH");
    public static final Item hound_SMail = new ItemHoundArmourMF(itemId + 194, EnumArmourMF.STEEL, false, "steel", 1, 20).setUnlocalizedName("hound_SMail");
    public static final Item hound_SMailH = new ItemHoundArmourMF(itemId + 195, EnumArmourMF.STEEL, false, "steel", 0, 20).setUnlocalizedName("hound_SMailH");
    public static final Item hound_MMail = new ItemHoundArmourMF(itemId + 196, EnumArmourMF.MITHRIL, false, "mithril", 1, 35).setUnlocalizedName("hound_MMail");
    public static final Item hound_MMailH = new ItemHoundArmourMF(itemId + 197, EnumArmourMF.MITHRIL, false, "mithril", 0, 35).setUnlocalizedName("hound_MMailH");
    
    public static final Item hound_Bplate = new ItemHoundArmourMF(itemId + 198, EnumArmourMF.BRONZE, true, "bronze", 1, 30).setUnlocalizedName("hound_Bplate");
    public static final Item hound_BplateH = new ItemHoundArmourMF(itemId + 199, EnumArmourMF.BRONZE, true, "bronze", 0, 30).setUnlocalizedName("hound_BplateH");
    public static final Item hound_Splate = new ItemHoundArmourMF(itemId + 200, EnumArmourMF.STEEL, true, "steel", 1, 50).setUnlocalizedName("hound_Splate");
    public static final Item hound_SplateH = new ItemHoundArmourMF(itemId + 201, EnumArmourMF.STEEL, true, "steel", 0, 50).setUnlocalizedName("hound_SplateH");
    public static final Item hound_Dplate = new ItemHoundArmourMF(itemId + 202, EnumArmourMF.DRAGONFORGE, true, "dragon", 1, 55).setUnlocalizedName("hound_Dplate");
    public static final Item hound_DplateH = new ItemHoundArmourMF(itemId + 203, EnumArmourMF.DRAGONFORGE, true, "dragon", 0, 55).setUnlocalizedName("hound_DplateH");
    public static final Item hound_Eplate = new ItemHoundArmourMF(itemId + 204, EnumArmourMF.ENCRUSTED, true, "encrusted", 1, 60).setUnlocalizedName("hound_Eplate");
    public static final Item hound_EplateH = new ItemHoundArmourMF(itemId + 205, EnumArmourMF.ENCRUSTED, true, "encrusted", 0, 60).setUnlocalizedName("hound_EplateH");
    
    public static final Item hound_Bteeth = new ItemHoundWeaponMF(itemId + 206, ToolMaterialMedieval.BRONZE, "bronze", 10, 1).setUnlocalizedName("hound_Bteeth");
    public static final Item hound_Iteeth = new ItemHoundWeaponMF(itemId + 207, ToolMaterialMedieval.IRON, "iron", 15, 2).setUnlocalizedName("hound_Iteeth");
    public static final Item hound_Steeth = new ItemHoundWeaponMF(itemId + 208, ToolMaterialMedieval.STEEL, "steel", 25, 3).setUnlocalizedName("hound_Steeth");
    public static final Item hound_Eteeth = new ItemHoundWeaponMF(itemId + 209, ToolMaterialMedieval.ENCRUSTED, "encrusted", 35, 4).setUnlocalizedName("hound_Eteeth");
    public static final Item hound_Mteeth = new ItemHoundWeaponMF(itemId + 210, ToolMaterialMedieval.MITHRIL, "mithril", 45, 5).setUnlocalizedName("hound_Mteeth");
    public static final Item hound_Dteeth = new ItemHoundWeaponMF(itemId + 211, ToolMaterialMedieval.DRAGONFORGE, "dragon", 30, 4).setUnlocalizedName("hound_Dteeth");
    
    public static final Item spearBronze = new ItemSpearMF(itemId + 212, ToolMaterialMedieval.BRONZE).setUnlocalizedName("spearBronze");
    public static final Item spearIron = new ItemSpearMF(itemId + 213, ToolMaterialMedieval.IRON).setUnlocalizedName("spearIron");
    public static final Item spearSteel = new ItemSpearMF(itemId + 214, ToolMaterialMedieval.STEEL).setUnlocalizedName("spearSteel");
    public static final Item spearEncrusted = new ItemSpearMF(itemId + 215, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("spearEncrusted");
    public static final Item spearMithril = new ItemSpearMF(itemId + 216, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("spearMithril");
    public static final Item waraxeMithril = new ItemWaraxe(itemId + 217, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("waraxeMithril");
    public static final Item spearDragon = new ItemSpearMF(itemId + 218, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("spearDragon");
    public static final Item waraxeOrnate = new ItemWaraxe(itemId + 219, ToolMaterialMedieval.ORNATE).setUnlocalizedName("waraxeOrnate");
    public static final Item spearOrnate = new ItemSpearMF(itemId + 220, ToolMaterialMedieval.ORNATE).setUnlocalizedName("spearOrnate");
    
    public static final Item battleaxeBronze = new ItemBattleaxe(itemId + 221, ToolMaterialMedieval.BRONZE).setUnlocalizedName("battleaxeBronze");
    public static final Item battleaxeIron = new ItemBattleaxe(itemId + 222, ToolMaterialMedieval.IRON).setUnlocalizedName("battleaxeIron");
    public static final Item battleaxeSteel = new ItemBattleaxe(itemId + 223, ToolMaterialMedieval.STEEL).setUnlocalizedName("battleaxeSteel");
    public static final Item battleaxeEncrusted = new ItemBattleaxe(itemId + 224, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("battleaxeEncrusted");
    public static final Item battleaxeMithril = new ItemBattleaxe(itemId + 225, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("battleaxeMithril");
    public static final Item battleaxeOrnate = new ItemBattleaxe(itemId + 226, ToolMaterialMedieval.ORNATE).setUnlocalizedName("battleaxeOrnate");
    public static final Item mattockSteel = new ItemMattock(itemId + 227, ToolMaterialMedieval.STEEL).setUnlocalizedName("mattockSteel");
    public static final Item battleaxeDragon = new ItemBattleaxe(itemId + 228, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("battleaxeDragon");
    public static final Item sawBronze = new ItemSaw(itemId + 229, ToolMaterialMedieval.BRONZE).setUnlocalizedName("sawBronze");
    
    public static final Item scytheBronze = new ItemScytheMF(itemId + 230, ToolMaterialMedieval.BRONZE).setUnlocalizedName("scytheBronze");
    public static final Item scytheIron = new ItemScytheMF(itemId + 231, ToolMaterialMedieval.IRON).setUnlocalizedName("scytheIron");
    public static final Item scytheSteel = new ItemScytheMF(itemId + 232, ToolMaterialMedieval.STEEL).setUnlocalizedName("scytheSteel");
    public static final Item hammerStone = new ItemHammer(itemId + 233, 0.3F, EnumToolMaterial.STONE).setUnlocalizedName("hammerStone");
    
    public static final Item greatswordBronze = new ItemGreatsword(itemId + 234, ToolMaterialMedieval.BRONZE).setUnlocalizedName("greatswordBronze");
    public static final Item greatswordIron = new ItemGreatsword(itemId + 235, ToolMaterialMedieval.IRON).setUnlocalizedName("greatswordIron");
    public static final Item greatswordSteel = new ItemGreatsword(itemId + 236, ToolMaterialMedieval.STEEL).setUnlocalizedName("greatswordSteel");
    public static final Item greatswordEncrusted = new ItemGreatsword(itemId + 237, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("greatswordEncrusted");
    public static final Item greatswordMithril = new ItemGreatsword(itemId + 238, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("greatswordMithril");
    public static final Item greatswordDragon = new ItemGreatsword(itemId + 239, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("greatswordDragon");
    public static final Item sawIron = new ItemSaw(itemId + 240, ToolMaterialMedieval.IRON).setUnlocalizedName("sawIron");
    public static final Item greatswordOrnate = new ItemGreatsword(itemId + 241, ToolMaterialMedieval.ORNATE).setUnlocalizedName("greatswordOrnate");
    public static final Item sawSteel = new ItemSaw(itemId + 242, ToolMaterialMedieval.STEEL).setUnlocalizedName("sawSteel");
    
    public static final Item hammerRepair = new ItemRepairHammer(itemId + 243, 32, 0.5F, 0.5F, false, 0).setUnlocalizedName("hammerRepair");
    public static final Item hammerRepairOrnate = new ItemRepairHammer(itemId + 244, 64, 0.5F, 0.5F, true, 1).setUnlocalizedName("hammerRepairOrnate");
    
    public static final Item hammerRepair2 = new ItemRepairHammer(itemId + 245, 48, 0.5F, 0.8F, false, 1).setUnlocalizedName("hammerRepair2");
    public static final Item hammerRepairOrnate2 = new ItemRepairHammer(itemId + 246, 96, 0.5F, 0.8F, true, 2).setUnlocalizedName("hammerRepairOrnate2");
    
    public static final Item hammerRepairArtisan = new ItemRepairHammer(itemId + 247, 256, 0.8F, 1.0F, false, 2).setUnlocalizedName("hammerRepairArtisan");
    public static final Item hammerRepairOrnateArtisan = new ItemRepairHammer(itemId + 248, 384, 0.8F, 1.0F, true, 3).setUnlocalizedName("hammerRepairOrnateArtisan");
    
    public static final Item daggerBronze = new ItemDagger(itemId + 285, ToolMaterialMedieval.BRONZE).setUnlocalizedName("daggerBronze");
    public static final Item daggerIron = new ItemDagger(itemId + 286, ToolMaterialMedieval.IRON).setUnlocalizedName("daggerIron");
    public static final Item daggerSteel = new ItemDagger(itemId + 287, ToolMaterialMedieval.STEEL).setUnlocalizedName("daggerSteel");
    public static final Item daggerEncrusted = new ItemDagger(itemId + 288, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("daggerEncrusted");
    public static final Item daggerMithril = new ItemDagger(itemId + 289, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("daggerMithril");
    public static final Item daggerDragon = new ItemDagger(itemId + 290, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("daggerDragon");
    public static final Item pickCopperForged = new ItemMedievalPick(itemId + 291, ToolMaterialMedieval.COPPER).setUnlocalizedName("pickCopperForged");
    public static final Item daggerOrnate = new ItemDagger(itemId + 292, ToolMaterialMedieval.ORNATE).setUnlocalizedName("daggerOrnate");
    public static final Item axeCopperForged = new ItemMedievalAxe(itemId + 293, ToolMaterialMedieval.COPPER).setUnlocalizedName("axeCopperForged");
    
    public static final Item scytheDragon = new ItemScytheMF(itemId + 294, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("scytheDragon");
    public static final Item shearsDragon = new ItemShearsMF(itemId + 295, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("shearsDragon");
    public static final Item spadeDragon = new ItemMedievalSpade(itemId + 296, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("spadeDragon");
    public static final Item tongsDragon = new ItemTongs(itemId + 297, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("tongsDragon");
    public static final Item bowDragon = new ItemBowMF(itemId + 298, ToolMaterialMedieval.DRAGONFORGE, EnumBowType.RECURVE).setUnlocalizedName("bowDragonforge");
    public static final Item bowOrnate = new ItemBowMF(itemId + 299, ToolMaterialMedieval.ORNATE, EnumBowType.RECURVE).setUnlocalizedName("bowOrnate");
    public static final Item spadeCopperForged = new ItemMedievalSpade(itemId + 300, ToolMaterialMedieval.COPPER).setUnlocalizedName("spadeCopper");
    public static final Item malletEbony = new ItemMallet(itemId + 301, ToolMaterialMedieval.IRONBARK).setUnlocalizedName("malletEbony");
    public static final Item hoeCopperForged = new ItemMedievalHoe(itemId + 302, ToolMaterialMedieval.COPPER).setUnlocalizedName("hoeCopperForged");
    
    public static final Item helmetStealth = (new ItemStealthArmour(itemId + 303, MedievalArmourMaterial.STEALTH, 1, 0, "stealth_1")).setUnlocalizedName("stealthHelmet");
    public static final Item plateStealth = (new ItemStealthArmour(itemId + 304, MedievalArmourMaterial.STEALTH, 1, 1, "stealth_1")).setUnlocalizedName("stealthChest");
    public static final Item legsStealth = (new ItemStealthArmour(itemId + 305, MedievalArmourMaterial.STEALTH, 2, 2, "stealth_2")).setUnlocalizedName("stealthLegs");
    public static final Item bootsStealth = (new ItemStealthArmour(itemId + 306, MedievalArmourMaterial.STEALTH, 2, 3, "stealth_1")).setUnlocalizedName("stealthBoots");
    
    public static final Item crossbowHand = (new ItemCrossbow(itemId + 307, EnumAmmo.BOLT, EnumCrossbowType.HAND_CROSSBOW)).setUnlocalizedName("crossbowHand");
    public static final Item crossbowLight = (new ItemCrossbow(itemId + 308, EnumAmmo.BOLT, EnumCrossbowType.LIGHT_CROSSBOW)).setUnlocalizedName("crossbowLight");
    public static final Item crossbowRepeat = (new ItemCrossbow(itemId + 309, EnumAmmo.BOLT, EnumCrossbowType.REPEATER_CROSSBOW)).setUnlocalizedName("crossbowRepeat");
    public static final Item crossbowHeavy = (new ItemCrossbow(itemId + 310, EnumAmmo.ARROW, EnumCrossbowType.HEAVY_CROSSBOW)).setUnlocalizedName("crossbowHeavy");
    
    public static final Item tongsStone = new ItemTongs(itemId + 311, EnumToolMaterial.STONE).setUnlocalizedName("tongsStone");
    public static final Item boltMF = new ItemBoltMF(itemId + 312).setUnlocalizedName("boltMF");
    
    public static final Item spearStone = new ItemSpearMF(itemId + 313, ToolMaterialMedieval.PRIMITIVE_STONE).setUnlocalizedName("spearStonePrim").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item spearCopper = new ItemSpearMF(itemId + 314, ToolMaterialMedieval.PRIMITIVE_COPPER).setUnlocalizedName("spearCopperPrim").setCreativeTab(CreativeTabs.tabCombat);
    
    public static final Item pickStonePrim = new ItemPrimitivePick(itemId + 315, ToolMaterialMedieval.PRIMITIVE_STONE).setUnlocalizedName("pickStonePrim");
    public static final Item pickCopperPrim = new ItemPrimitivePick(itemId + 316, ToolMaterialMedieval.PRIMITIVE_COPPER).setUnlocalizedName("pickCopperPrim");
    public static final Item rocks = new ItemLighter(itemId + 317, 8, cfg.dryRocksChance).setUnlocalizedName("flintAndRock");
    public static final Item javelin = new ItemJavelin(itemId + 318, 4).setUnlocalizedName("javelin").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item armourRawhide = (new ItemArmourMFOld(itemId + 319, ArmourDesign.LEATHER, MedievalArmourMaterial.RAWHIDE, 1, 1, "rawhide_1")).setUnlocalizedName("rawhideChest");
    public static final Item legsRawhide = (new ItemArmourMFOld(itemId + 320, ArmourDesign.LEATHER, MedievalArmourMaterial.RAWHIDE, 2, 2, "rawhide_2")).setUnlocalizedName("rawhideLegs");
    public static final Item clubWood = new ItemSwordMF(itemId + 321, EnumToolMaterial.WOOD, 3, 64).setUnlocalizedName("clubPrim").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item clubStone = new ItemSwordMF(itemId + 322, ToolMaterialMedieval.PRIMITIVE_STONE, 4, 128).setUnlocalizedName("clubStonePrim").setCreativeTab(CreativeTabs.tabCombat);
   
    public static final Item bowEbony = new ItemBowMF(itemId + 323, ToolMaterialMedieval.EBONY, EnumBowType.COMPOSITE).setUnlocalizedName("bowEbony");
    
    public static final Item waraxeIron = new ItemWaraxe(itemId + 324, ToolMaterialMedieval.IRON).setUnlocalizedName("waraxeIron");
    
    public static final Item shearsCopper = new ItemShearsMF(itemId + 325, ToolMaterialMedieval.COPPER).setUnlocalizedName("shearsCopper");
    public static final Item shearsBronze = new ItemShearsMF(itemId + 326, ToolMaterialMedieval.BRONZE).setUnlocalizedName("shearsBronze");
    public static final Item shearsIron = new ItemShearsMF(itemId + 327, ToolMaterialMedieval.IRON).setUnlocalizedName("shearsIron");
    public static final Item shearsSteel = new ItemShearsMF(itemId + 328, ToolMaterialMedieval.STEEL).setUnlocalizedName("shearsSteel");
   
    public static final Item warhammerBronze = new ItemWarhammer(itemId + 329, ToolMaterialMedieval.BRONZE).setUnlocalizedName("warhammerBronze");
    public static final Item warhammerIron = new ItemWarhammer(itemId + 330, ToolMaterialMedieval.IRON).setUnlocalizedName("warhammerIron");
    public static final Item warhammerSteel = new ItemWarhammer(itemId + 331, ToolMaterialMedieval.STEEL).setUnlocalizedName("warhammerSteel");
    public static final Item warhammerOrnate = new ItemWarhammer(itemId + 332, ToolMaterialMedieval.ORNATE).setUnlocalizedName("warhammerOrnate");
    public static final Item warhammerMithril = new ItemWarhammer(itemId + 333, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("warhammerMithril");
    public static final Item warhammerDragon = new ItemWarhammer(itemId + 334, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("warhammerDragon");
    
    public static final Item helmetIronScale = (new ItemArmourMF(itemId + 335, ArmourDesign.SCALE, EnumArmourMF.IRON, 1, 0, "ironScale_1")).setUnlocalizedName("ironScaleHelmet");
    public static final Item plateIronScale = (new ItemArmourMF(itemId + 336, ArmourDesign.SCALE, EnumArmourMF.IRON, 1, 1, "ironScale_1")).setUnlocalizedName("ironScaleChest");
    public static final Item legsIronScale = (new ItemArmourMF(itemId + 337, ArmourDesign.SCALE, EnumArmourMF.IRON, 2, 2, "ironScale_2")).setUnlocalizedName("ironScaleLegs");
    public static final Item bootsIronScale = (new ItemArmourMF(itemId + 338, ArmourDesign.SCALE, EnumArmourMF.IRON, 2, 3, "ironScale_1")).setUnlocalizedName("ironScaleBoots");
    public static final Item transferHound = new ItemPetChange(itemId + 339).setUnlocalizedName("petChange");
    public static final Item hound_Igteeth = new ItemHoundWeaponMF(itemId + 340, ToolMaterialMedieval.IGNOTUMITE, "ignotumite", 80, 5).setUnlocalizedName("hound_Igteeth");
    public static final Item hound_Oteeth = new ItemHoundWeaponMF(itemId + 341, ToolMaterialMedieval.ORNATE, "ornate", 20, 1).setUnlocalizedName("hound_Oteeth");
    
    public static final Item halbeardBronze = new ItemHalbeard(itemId + 342, ToolMaterialMedieval.BRONZE).setUnlocalizedName("halbeardBronze");
    public static final Item halbeardIron = new ItemHalbeard(itemId + 343, ToolMaterialMedieval.IRON).setUnlocalizedName("halbeardIron");
    public static final Item halbeardSteel = new ItemHalbeard(itemId + 344, ToolMaterialMedieval.STEEL).setUnlocalizedName("halbeardSteel");
    public static final Item halbeardOrnate = new ItemHalbeard(itemId + 345, ToolMaterialMedieval.ORNATE).setUnlocalizedName("halbeardOrnate");
    public static final Item halbeardEncrusted = new ItemHalbeard(itemId + 346, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("halbeardEncrusted");
    public static final Item halbeardMithril = new ItemHalbeard(itemId + 347, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("halbeardMithril");
    public static final Item halbeardDragon = new ItemHalbeard(itemId + 348, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("halbeardDragon");
    
    public static final Item bloom = new ItemBloom(itemId + 349).setUnlocalizedName("bloom");
    public static final Item sling = new ItemSlingMF(itemId + 350, 256).setUnlocalizedName("sling");
    
    public static final Item basiliskRaw = (new ItemFoodMF(itemId + 351, 4, 0.4F, true, 4)).setPotionEffect(Potion.field_76444_x.id, 300, 3, 1.0F).setUnlocalizedName("basiliskRaw");
    public static final Item basiliskCooked = (new ItemFoodMF(itemId + 352, 10, 3F, true, 8)).setPotionEffect(Potion.field_76444_x.id, 600, 3, 1.0F).setUnlocalizedName("basiliskCooked");
    
    //public static final Item miscFood = (new ItemFoodIngreedient(itemId + 353)).setUnlocalizedName("foodIngreedient");
   
    public static final Item helmetMithrilScale = (new ItemArmourMF(itemId + 354, ArmourDesign.SCALE, EnumArmourMF.MITHRIL, 1, 0, "mithrilScale_1")).setUnlocalizedName("mithrilScaleHelmet");
    public static final Item plateMithrilScale = (new ItemArmourMF(itemId + 355, ArmourDesign.SCALE, EnumArmourMF.MITHRIL, 1, 1, "mithrilScale_1")).setUnlocalizedName("mithrilScaleChest");
    public static final Item legsMithrilScale = (new ItemArmourMF(itemId + 356, ArmourDesign.SCALE, EnumArmourMF.MITHRIL, 2, 2, "mithrilScale_2")).setUnlocalizedName("mithrilScaleLegs");
    public static final Item bootsMithrilScale = (new ItemArmourMF(itemId + 357, ArmourDesign.SCALE, EnumArmourMF.MITHRIL, 2, 3, "mithrilScale_1")).setUnlocalizedName("mithrilScaleBoots");
    public static final Item helmetDragonScale = (new ItemArmourMF(itemId + 358, ArmourDesign.SCALE, EnumArmourMF.DRAGONFORGE, 1, 0, "dragonforgeScale_1")).setUnlocalizedName("dragonforgeScaleHelmet");
    public static final Item plateDragonScale = (new ItemArmourMF(itemId + 359, ArmourDesign.SCALE, EnumArmourMF.DRAGONFORGE, 1, 1, "dragonforgeScale_1")).setUnlocalizedName("dragonforgeScaleChest");
    public static final Item legsDragonScale = (new ItemArmourMF(itemId + 360, ArmourDesign.SCALE, EnumArmourMF.DRAGONFORGE, 2, 2, "dragonforgeScale_2")).setUnlocalizedName("dragonforgeScaleLegs");
    public static final Item bootsDragonScale = (new ItemArmourMF(itemId + 361, ArmourDesign.SCALE, EnumArmourMF.DRAGONFORGE, 2, 3, "dragonforgeScale_1")).setUnlocalizedName("dragonforgeScaleBoots");
    
    public static final Item helmetSteelChain = (new ItemArmourMF(itemId + 362, ArmourDesign.CHAIN, EnumArmourMF.STEEL, 1, 0, "steelChain_1")).setUnlocalizedName("steelChainHelmet");
    public static final Item plateSteelChain = (new ItemArmourMF(itemId + 363, ArmourDesign.CHAIN, EnumArmourMF.STEEL, 1, 1, "steelChain_1")).setUnlocalizedName("steelChainChest");
    public static final Item legsSteelChain = (new ItemArmourMF(itemId + 364, ArmourDesign.CHAIN, EnumArmourMF.STEEL, 2, 2, "steelChain_2")).setUnlocalizedName("steelChainLegs");
    public static final Item bootsSteelChain = (new ItemArmourMF(itemId + 365, ArmourDesign.CHAIN, EnumArmourMF.STEEL, 2, 3, "steelChain_1")).setUnlocalizedName("steelChainBoots");
    public static final Item helmetMithrilChain = (new ItemArmourMF(itemId + 366, ArmourDesign.CHAIN, EnumArmourMF.MITHRIL, 1, 0, "mithrilChain_1")).setUnlocalizedName("mithrilChainHelmet");
    public static final Item plateMithrilChain = (new ItemArmourMF(itemId + 367, ArmourDesign.CHAIN, EnumArmourMF.MITHRIL, 1, 1, "mithrilChain_1")).setUnlocalizedName("mithrilChainChest");
    public static final Item legsMithrilChain = (new ItemArmourMF(itemId + 368, ArmourDesign.CHAIN, EnumArmourMF.MITHRIL, 2, 2, "mithrilChain_2")).setUnlocalizedName("mithrilChainLegs");
    public static final Item bootsMithrilChain = (new ItemArmourMF(itemId + 369, ArmourDesign.CHAIN, EnumArmourMF.MITHRIL, 2, 3, "mithrilChain_1")).setUnlocalizedName("mithrilChainBoots");
    public static final Item helmetDragonChain = (new ItemArmourMF(itemId + 370, ArmourDesign.CHAIN, EnumArmourMF.DRAGONFORGE, 1, 0, "dragonforgeChain_1")).setUnlocalizedName("dragonforgeChainHelmet");
    public static final Item plateDragonChain = (new ItemArmourMF(itemId + 371, ArmourDesign.CHAIN, EnumArmourMF.DRAGONFORGE, 1, 1, "dragonforgeChain_1")).setUnlocalizedName("dragonforgeChainChest");
    public static final Item legsDragonChain = (new ItemArmourMF(itemId + 372, ArmourDesign.CHAIN, EnumArmourMF.DRAGONFORGE, 2, 2, "dragonforgeChain_2")).setUnlocalizedName("dragonforgeChainLegs");
    public static final Item bootsDragonChain = (new ItemArmourMF(itemId + 373, ArmourDesign.CHAIN, EnumArmourMF.DRAGONFORGE, 2, 3, "dragonforgeChain_1")).setUnlocalizedName("dragonforgeChainBoots");
    
    public static final Item helmetBronzeSplint = (new ItemArmourMF(itemId + 374, ArmourDesign.SPLINT, EnumArmourMF.BRONZE, 1, 0, "bronzeSplint_1")).setUnlocalizedName("bronzeSplintHelmet");
    public static final Item plateBronzeSplint = (new ItemArmourMF(itemId + 375, ArmourDesign.SPLINT, EnumArmourMF.BRONZE, 1, 1, "bronzeSplint_1")).setUnlocalizedName("bronzeSplintChest");
    public static final Item legsBronzeSplint = (new ItemArmourMF(itemId + 376, ArmourDesign.SPLINT, EnumArmourMF.BRONZE, 2, 2, "bronzeSplint_2")).setUnlocalizedName("bronzeSplintLegs");
    public static final Item bootsBronzeSplint = (new ItemArmourMF(itemId + 377, ArmourDesign.SPLINT, EnumArmourMF.BRONZE, 2, 3, "bronzeSplint_1")).setUnlocalizedName("bronzeSplintBoots");
    public static final Item helmetIronSplint = (new ItemArmourMF(itemId + 378, ArmourDesign.SPLINT, EnumArmourMF.IRON, 1, 0, "ironSplint_1")).setUnlocalizedName("ironSplintHelmet");
    public static final Item plateIronSplint = (new ItemArmourMF(itemId + 379, ArmourDesign.SPLINT, EnumArmourMF.IRON, 1, 1, "ironSplint_1")).setUnlocalizedName("ironSplintChest");
    public static final Item legsIronSplint = (new ItemArmourMF(itemId + 380, ArmourDesign.SPLINT, EnumArmourMF.IRON, 2, 2, "ironSplint_2")).setUnlocalizedName("ironSplintLegs");
    public static final Item bootsIronSplint = (new ItemArmourMF(itemId + 381, ArmourDesign.SPLINT, EnumArmourMF.IRON, 2, 3, "ironSplint_1")).setUnlocalizedName("ironSplintBoots");
    public static final Item helmetDragonSplint = (new ItemArmourMF(itemId + 382, ArmourDesign.SPLINT, EnumArmourMF.DRAGONFORGE, 1, 0, "dragonforgeSplint_1")).setUnlocalizedName("dragonforgeSplintHelmet");
    public static final Item plateDragonSplint = (new ItemArmourMF(itemId + 383, ArmourDesign.SPLINT, EnumArmourMF.DRAGONFORGE, 1, 1, "dragonforgeSplint_1")).setUnlocalizedName("dragonforgeSplintChest");
    public static final Item legsDragonSplint = (new ItemArmourMF(itemId + 384, ArmourDesign.SPLINT, EnumArmourMF.DRAGONFORGE, 2, 2, "dragonforgeSplint_2")).setUnlocalizedName("dragonforgeSplintLegs");
    public static final Item bootsDragonSplint = (new ItemArmourMF(itemId + 385, ArmourDesign.SPLINT, EnumArmourMF.DRAGONFORGE, 2, 3, "dragonforgeSplint_1")).setUnlocalizedName("dragonforgeSplintBoots");
    
    public static final Item helmetBronzeHvyChain = (new ItemArmourMF(itemId + 386, ArmourDesign.HVYCHAIN, EnumArmourMF.BRONZE, 1, 0, "bronzeHvyChain_1")).setUnlocalizedName("bronzeHvyHelmet");
    public static final Item plateBronzeHvyChain = (new ItemArmourMF(itemId + 387, ArmourDesign.HVYCHAIN, EnumArmourMF.BRONZE, 1, 1, "bronzeHvyChain_1")).setUnlocalizedName("bronzeHvyChest");
    public static final Item legsBronzeHvyChain = (new ItemArmourMF(itemId + 388, ArmourDesign.HVYCHAIN, EnumArmourMF.BRONZE, 2, 2, "bronzeHvyChain_2")).setUnlocalizedName("bronzeHvyLegs");
    public static final Item bootsBronzeHvyChain = (new ItemArmourMF(itemId + 389, ArmourDesign.HVYCHAIN, EnumArmourMF.BRONZE, 2, 3, "bronzeHvyChain_1")).setUnlocalizedName("bronzeHvyBoots");
    public static final Item helmetSteelHvyChain = (new ItemArmourMF(itemId + 390, ArmourDesign.HVYCHAIN, EnumArmourMF.STEEL, 1, 0, "steelHvyChain_1")).setUnlocalizedName("steelHvyHelmet");
    public static final Item plateSteelHvyChain = (new ItemArmourMF(itemId + 391, ArmourDesign.HVYCHAIN, EnumArmourMF.STEEL, 1, 1, "steelHvyChain_1")).setUnlocalizedName("steelHvyChest");
    public static final Item legsSteelHvyChain = (new ItemArmourMF(itemId + 392, ArmourDesign.HVYCHAIN, EnumArmourMF.STEEL, 2, 2, "steelHvyChain_2")).setUnlocalizedName("steelHvyLegs");
    public static final Item bootsSteelHvyChain = (new ItemArmourMF(itemId + 393, ArmourDesign.HVYCHAIN, EnumArmourMF.STEEL, 2, 3, "steelHvyChain_1")).setUnlocalizedName("steelHvyBoots");
    public static final Item helmetMithrilHvyChain = (new ItemArmourMF(itemId + 394, ArmourDesign.HVYCHAIN, EnumArmourMF.MITHRIL, 1, 0, "mithrilHvyChain_1")).setUnlocalizedName("mithrilHvyHelmet");
    public static final Item plateMithrilHvyChain = (new ItemArmourMF(itemId + 395, ArmourDesign.HVYCHAIN, EnumArmourMF.MITHRIL, 1, 1, "mithrilHvyChain_1")).setUnlocalizedName("mithrilHvyChest");
    public static final Item legsMithrilHvyChain = (new ItemArmourMF(itemId + 396, ArmourDesign.HVYCHAIN, EnumArmourMF.MITHRIL, 2, 2, "mithrilHvyChain_2")).setUnlocalizedName("mithrilHvyLegs");
    public static final Item bootsMithrilHvyChain = (new ItemArmourMF(itemId + 397, ArmourDesign.HVYCHAIN, EnumArmourMF.MITHRIL, 2, 3, "mithrilHvyChain_1")).setUnlocalizedName("mithrilHvyBoots");
    public static final Item helmetDragonHvyChain = (new ItemArmourMF(itemId + 398, ArmourDesign.HVYCHAIN, EnumArmourMF.DRAGONFORGE, 1, 0, "dragonforgeHvyChain_1")).setUnlocalizedName("dragonforgeHvyHelmet");
    public static final Item plateDragonHvyChain = (new ItemArmourMF(itemId + 399, ArmourDesign.HVYCHAIN, EnumArmourMF.DRAGONFORGE, 1, 1, "dragonforgeHvyChain_1")).setUnlocalizedName("dragonforgeHvyChest");
    public static final Item legsDragonHvyChain = (new ItemArmourMF(itemId + 400, ArmourDesign.HVYCHAIN, EnumArmourMF.DRAGONFORGE, 2, 2, "dragonforgeHvyChain_2")).setUnlocalizedName("dragonforgeHvyLegs");
    public static final Item bootsDragonHvyChain = (new ItemArmourMF(itemId + 401, ArmourDesign.HVYCHAIN, EnumArmourMF.DRAGONFORGE, 2, 3, "dragonforgeHvyChain_1")).setUnlocalizedName("dragonforgeHvyBoots");
    
    public static final Item helmetIronPlate = (new ItemArmourMF(itemId + 402, ArmourDesign.PLATE, EnumArmourMF.IRON, 0, 0, "ironPlate_1")).setUnlocalizedName("ironPlateHelmet");
    public static final Item plateIronPlate = (new ItemArmourMF(itemId + 403, ArmourDesign.PLATE, EnumArmourMF.IRON, 0, 1, "ironPlate_1")).setUnlocalizedName("ironPlateChest");
    public static final Item legsIronPlate = (new ItemArmourMF(itemId + 404, ArmourDesign.PLATE, EnumArmourMF.IRON, 0, 2, "ironPlate_2")).setUnlocalizedName("ironPlateLegs");
    public static final Item bootsIronPlate = (new ItemArmourMF(itemId + 405, ArmourDesign.PLATE, EnumArmourMF.IRON, 0, 3, "ironPlate_1")).setUnlocalizedName("ironPlateBoots");
    public static final Item helmetMithrilPlate = (new ItemArmourMF(itemId + 406, ArmourDesign.PLATE, EnumArmourMF.MITHRIL, 0, 0, "mithrilPlate_1")).setUnlocalizedName("mithrilPlateHelmet");
    public static final Item plateMithrilPlate = (new ItemArmourMF(itemId + 407, ArmourDesign.PLATE, EnumArmourMF.MITHRIL, 0, 1, "mithrilPlate_1")).setUnlocalizedName("mithrilPlateChest");
    public static final Item legsMithrilPlate = (new ItemArmourMF(itemId + 408, ArmourDesign.PLATE, EnumArmourMF.MITHRIL, 0, 2, "mithrilPlate_2")).setUnlocalizedName("mithrilPlateLegs");
    public static final Item bootsMithrilPlate = (new ItemArmourMF(itemId + 409, ArmourDesign.PLATE, EnumArmourMF.MITHRIL, 0, 3, "mithrilPlate_1")).setUnlocalizedName("mithrilPlateBoots");
    
    public static final Item hound_Mplate = new ItemHoundArmourMF(itemId + 410, EnumArmourMF.MITHRIL, true, "mithril", 1, 70).setUnlocalizedName("hound_Mplate");
    public static final Item hound_MplateH = new ItemHoundArmourMF(itemId + 411, EnumArmourMF.MITHRIL, true, "mithril", 0, 70).setUnlocalizedName("hound_MplateH");
    public static final Item hound_DMail = new ItemHoundArmourMF(itemId + 412, EnumArmourMF.DRAGONFORGE, false, "dragonforge", 1, 30).setUnlocalizedName("hound_DMail");
    public static final Item hound_DMailH = new ItemHoundArmourMF(itemId + 413, EnumArmourMF.DRAGONFORGE, false, "dragonforge", 0, 30).setUnlocalizedName("hound_DMailH");
    
    public static final Item needleBone = new ItemNeedle(itemId + 414, EnumToolMaterial.STONE).setUnlocalizedName("needleBone");
    public static final Item needleBronze = new ItemNeedle(itemId + 415, ToolMaterialMedieval.BRONZE).setUnlocalizedName("needleBronze");
    public static final Item needleIron = new ItemNeedle(itemId + 416, ToolMaterialMedieval.IRON).setUnlocalizedName("needleIron");
    public static final Item needleSteel = new ItemNeedle(itemId + 417, ToolMaterialMedieval.STEEL).setUnlocalizedName("needleSteel");
    public static final Item needleMithril = new ItemNeedle(itemId + 418, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("needleMithril");
    
    public static final Item shortbow = new ItemBowMF(itemId + 419, ToolMaterialMedieval.STRONGWOOD, EnumBowType.COMPOSITE).setUnlocalizedName("shortbow");
    public static final Item bowBronze = new ItemBowMF(itemId + 420, ToolMaterialMedieval.BRONZE, EnumBowType.RECURVE).setUnlocalizedName("bowBronze");
    public static final Item bowIron = new ItemBowMF(itemId + 421, ToolMaterialMedieval.IRON, EnumBowType.RECURVE).setUnlocalizedName("bowIron");
    public static final Item bowSteel = new ItemBowMF(itemId + 422, ToolMaterialMedieval.STEEL, EnumBowType.RECURVE).setUnlocalizedName("bowSteel");
    public static final Item bowMithril = new ItemBowMF(itemId + 423, ToolMaterialMedieval.MITHRIL, EnumBowType.RECURVE).setUnlocalizedName("bowMithril");
    
    public static final Item tongsTin = new ItemTongs(itemId + 424, ToolMaterialMedieval.TIN).setUnlocalizedName("tongsTin");
    public static final Item tongsCopper = new ItemTongs(itemId + 425, ToolMaterialMedieval.COPPER).setUnlocalizedName("tongsCopper");
    
    public static final Item lanceBronze = new ItemLance(itemId + 426, ToolMaterialMedieval.BRONZE).setUnlocalizedName("lanceBronze");
    public static final Item lanceIron = new ItemLance(itemId + 427, ToolMaterialMedieval.IRON).setUnlocalizedName("lanceIron");
    public static final Item lanceSteel = new ItemLance(itemId + 428, ToolMaterialMedieval.STEEL).setUnlocalizedName("lanceSteel");
    public static final Item lanceEncrusted = new ItemLance(itemId + 429, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("lanceEncrusted");
    public static final Item lanceMithril = new ItemLance(itemId + 450, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("lanceMithril");
    public static final Item lanceDragon = new ItemLance(itemId + 451, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("lanceDragonforge");
    public static final Item lanceOrnate = new ItemLance(itemId + 452, ToolMaterialMedieval.ORNATE).setUnlocalizedName("lanceOrnate");
    
    public static final Item helmetGuildedPlate = (new ItemArmourMF(itemId + 453, ArmourDesign.PLATE, EnumArmourMF.GUILDED, 1, 0, "guildedPlate_1")).setUnlocalizedName("guildedPlateHelmet");
    public static final Item plateGuildedPlate = (new ItemArmourMF(itemId + 454, ArmourDesign.PLATE, EnumArmourMF.GUILDED, 1, 1, "guildedPlate_1")).setUnlocalizedName("guildedPlateChest");
    public static final Item legsGuildedPlate = (new ItemArmourMF(itemId + 455, ArmourDesign.PLATE, EnumArmourMF.GUILDED, 2, 2, "guildedPlate_2")).setUnlocalizedName("guildedPlateLegs");
    public static final Item bootsGuildedPlate = (new ItemArmourMF(itemId + 456, ArmourDesign.PLATE, EnumArmourMF.GUILDED, 2, 3, "guildedPlate_1")).setUnlocalizedName("guildedPlateBoots");
    
    public static final Item helmetGuildedChain = (new ItemArmourMF(itemId + 457, ArmourDesign.CHAIN, EnumArmourMF.GUILDED, 1, 0, "guildedChain_1")).setUnlocalizedName("guildedChainHelmet");
    public static final Item plateGuildedChain = (new ItemArmourMF(itemId + 458, ArmourDesign.CHAIN, EnumArmourMF.GUILDED, 1, 1, "guildedChain_1")).setUnlocalizedName("guildedChainChest");
    public static final Item legsGuildedChain = (new ItemArmourMF(itemId + 459, ArmourDesign.CHAIN, EnumArmourMF.GUILDED, 2, 2, "guildedChain_2")).setUnlocalizedName("guildedChainLegs");
    public static final Item bootsGuildedChain = (new ItemArmourMF(itemId + 460, ArmourDesign.CHAIN, EnumArmourMF.GUILDED, 2, 3, "guildedChain_1")).setUnlocalizedName("guildedChainBoots");
    
    public static final Item helmetGuildedSplint = (new ItemArmourMF(itemId + 461, ArmourDesign.SPLINT, EnumArmourMF.GUILDED, 1, 0, "guildedSplint_1")).setUnlocalizedName("guildedSplintHelmet");
    public static final Item plateGuildedSplint = (new ItemArmourMF(itemId + 462, ArmourDesign.SPLINT, EnumArmourMF.GUILDED, 1, 1, "guildedSplint_1")).setUnlocalizedName("guildedSplintChest");
    public static final Item legsGuildedSplint = (new ItemArmourMF(itemId + 463, ArmourDesign.SPLINT, EnumArmourMF.GUILDED, 2, 2, "guildedSplint_2")).setUnlocalizedName("guildedSplintLegs");
    public static final Item bootsGuildedSplint = (new ItemArmourMF(itemId + 464, ArmourDesign.SPLINT, EnumArmourMF.GUILDED, 2, 3, "guildedSplint_1")).setUnlocalizedName("guildedSplintBoots");
    
    public static final Item helmetGuildedHvyChain = (new ItemArmourMF(itemId + 465, ArmourDesign.HVYCHAIN, EnumArmourMF.GUILDED, 1, 0, "guildedHvyChain_1")).setUnlocalizedName("guildedHvyHelmet");
    public static final Item plateGuildedHvyChain = (new ItemArmourMF(itemId + 466, ArmourDesign.HVYCHAIN, EnumArmourMF.GUILDED, 1, 1, "guildedHvyChain_1")).setUnlocalizedName("guildedHvyChest");
    public static final Item legsGuildedHvyChain = (new ItemArmourMF(itemId + 467, ArmourDesign.HVYCHAIN, EnumArmourMF.GUILDED, 2, 2, "guildedHvyChain_2")).setUnlocalizedName("guildedHvyLegs");
    public static final Item bootsGuildedHvyChain = (new ItemArmourMF(itemId + 468, ArmourDesign.HVYCHAIN, EnumArmourMF.GUILDED, 2, 3, "guildedHvyChain_1")).setUnlocalizedName("guildedHvyBoots");
    
    public static final Item helmetGuildedScale = (new ItemArmourMF(itemId + 469, ArmourDesign.SCALE, EnumArmourMF.GUILDED, 1, 0, "guildedScale_1")).setUnlocalizedName("guildedScaleHelmet");
    public static final Item plateGuildedScale = (new ItemArmourMF(itemId + 470, ArmourDesign.SCALE, EnumArmourMF.GUILDED, 1, 1, "guildedScale_1")).setUnlocalizedName("guildedScaleChest");
    public static final Item legsGuildedScale = (new ItemArmourMF(itemId + 471, ArmourDesign.SCALE, EnumArmourMF.GUILDED, 2, 2, "guildedScale_2")).setUnlocalizedName("guildedScaleLegs");
    public static final Item bootsGuildedScale = (new ItemArmourMF(itemId + 472, ArmourDesign.SCALE, EnumArmourMF.GUILDED, 2, 3, "guildedScale_1")).setUnlocalizedName("guildedScaleBoots");
    
    public static final Item handpickBronze = new ItemHandpick(itemId + 473, ToolMaterialMedieval.BRONZE).setUnlocalizedName("handpickBronze");
    public static final Item handpickIron = new ItemHandpick(itemId + 474, ToolMaterialMedieval.IRON).setUnlocalizedName("handpickIron");
    public static final Item handpickSteel = new ItemHandpick(itemId + 475, ToolMaterialMedieval.STEEL).setUnlocalizedName("handpickSteel");
    public static final Item handpickEncrusted = new ItemHandpick(itemId + 476, ToolMaterialMedieval.ENCRUSTED).setUnlocalizedName("handpickEncrusted");
    public static final Item handpickMithril = new ItemHandpick(itemId + 477, ToolMaterialMedieval.MITHRIL).setUnlocalizedName("handpickMithril");
    public static final Item handpickDragonforge = new ItemHandpick(itemId + 478, ToolMaterialMedieval.DRAGONFORGE).setUnlocalizedName("handpickDragonforge");
    public static final Item handpickIgnotumite = new ItemHandpick(itemId + 479, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("handpickIgnotumite");
    
    public static final Item helmetLeatherRough = (new ItemArmourMF(itemId + 480, ArmourDesign.LEATHER, EnumArmourMF.LEATHER, 1, 0, "roughLeather_1")).setUnlocalizedName("roughLeatherHelmet");
    public static final Item plateLeatherRough = (new ItemArmourMF(itemId + 481, ArmourDesign.LEATHER, EnumArmourMF.LEATHER, 1, 1, "roughLeather_1")).setUnlocalizedName("roughLeatherChest");
    public static final Item legsLeatherRough = (new ItemArmourMF(itemId + 482, ArmourDesign.LEATHER, EnumArmourMF.LEATHER, 2, 2, "roughLeather_2")).setUnlocalizedName("roughLeatherLegs");
    public static final Item bootsLeatherRough = (new ItemArmourMF(itemId + 483, ArmourDesign.LEATHER, EnumArmourMF.LEATHER, 2, 3, "roughLeather_1")).setUnlocalizedName("roughLeatherBoots");
    
    public static final Item waraxeCopper = new ItemWaraxe(itemId + 484, ToolMaterialMedieval.COPPER).setUnlocalizedName("waraxeCopper");
    public static final Item waraxeTin = new ItemWaraxe(itemId + 485, ToolMaterialMedieval.TIN).setUnlocalizedName("waraxeTin");
    public static final Item swordCopper = new ItemSwordMF(itemId + 486, ToolMaterialMedieval.COPPER).setUnlocalizedName("swordCopper");
    public static final Item maceTin = new ItemMaceMF(itemId + 487, ToolMaterialMedieval.TIN).setUnlocalizedName("maceTin");
    
    public static final Item daggerIgnotumite = new ItemDagger(itemId + 488, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("daggerIgnotumite");
    public static final Item waraxeIgnotumite = new ItemWaraxe(itemId + 489, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("waraxeIgnotumite");
    public static final Item maceIgnotumite = new ItemMaceMF(itemId + 490, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("maceIgnotumite");
    public static final Item broadIgnotumite = new ItemBroadsword(itemId + 491, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("broadswordIgnotumite");
    public static final Item warpickIgnotumite = new ItemWarpick(itemId + 492, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("warpickIgnotumite");
    public static final Item greatswordIgnotumite = new ItemGreatsword(itemId + 493, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("greatswordIgnotumite");
    public static final Item morningstarIgnotumite = new ItemGreatmace(itemId + 494, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("greatmaceIgnotumite");
    public static final Item battleaxeIgnotumite = new ItemBattleaxe(itemId + 495, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("battleaxeIgnotumite");
    public static final Item warhammerIgnotumite = new ItemWarhammer(itemId + 496, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("warhammerIgnotumite");
    public static final Item spearIgnotumite = new ItemSpearMF(itemId + 497, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("spearIgnotumite");
    public static final Item halbeardIgnotumite = new ItemHalbeard(itemId + 498, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("halbeardIgnotumite");
    public static final Item lanceIgnotumite = new ItemWarpick(itemId + 499, ToolMaterialMedieval.IGNOTUMITE).setUnlocalizedName("lanceIgnotumite");
    public static final Item warpickOrnate = new ItemWarpick(itemId + 500, ToolMaterialMedieval.ORNATE).setUnlocalizedName("warpickOrnate");
    
    public static final Item hound_GMail = new ItemHoundArmourMF(itemId + 501, EnumArmourMF.GUILDED, false, "guilded", 1, 20).setUnlocalizedName("hound_GMail");
    public static final Item hound_GMailH = new ItemHoundArmourMF(itemId + 502, EnumArmourMF.GUILDED, false, "guilded", 0, 20).setUnlocalizedName("hound_GMailH");
    
    public static final Item hound_Gplate = new ItemHoundArmourMF(itemId + 503, EnumArmourMF.GUILDED, true, "guilded", 1, 50).setUnlocalizedName("hound_Gplate");
    public static final Item hound_GplateH = new ItemHoundArmourMF(itemId + 504, EnumArmourMF.GUILDED, true, "guilded", 0, 50).setUnlocalizedName("hound_GplateH");
    
    public static final Item muttonRaw = (new ItemFoodMF(itemId + 505, 3, 0.3F, true)).setUnlocalizedName("muttonRaw");
    public static final Item muttonCooked = (new ItemFoodMF(itemId + 506, 8, 0.8F, true)).setUnlocalizedName("muttonCooked");
    
    public static final Item kiteBronze = (new ItemShield(itemId + 507, ToolMaterialMedieval.BRONZE, EnumShieldDesign.KITE));
    public static final Item kiteIron = (new ItemShield(itemId + 508, ToolMaterialMedieval.IRON, EnumShieldDesign.KITE));
    public static final Item kiteSteel = (new ItemShield(itemId + 509, ToolMaterialMedieval.STEEL, EnumShieldDesign.KITE));
    public static final Item kiteMithril = (new ItemShield(itemId + 510, ToolMaterialMedieval.MITHRIL, EnumShieldDesign.KITE));
    public static final Item kiteGuilded = (new ItemShield(itemId + 511, ToolMaterialMedieval.ORNATE, EnumShieldDesign.KITE, "guilded"));
    public static final Item kiteEncrusted = (new ItemShield(itemId + 512, ToolMaterialMedieval.ENCRUSTED, EnumShieldDesign.KITE));
    public static final Item kiteDragonforge = (new ItemShield(itemId + 513, ToolMaterialMedieval.DRAGONFORGE, EnumShieldDesign.KITE));
    
    public static final Item towerBronze = (new ItemShield(itemId + 514, ToolMaterialMedieval.BRONZE, EnumShieldDesign.TOWER));
    public static final Item towerIron = (new ItemShield(itemId + 515, ToolMaterialMedieval.IRON, EnumShieldDesign.TOWER));
    public static final Item towerSteel = (new ItemShield(itemId + 516, ToolMaterialMedieval.STEEL, EnumShieldDesign.TOWER));
    public static final Item towerMithril = (new ItemShield(itemId + 517, ToolMaterialMedieval.MITHRIL, EnumShieldDesign.TOWER));
    public static final Item towerGuilded = (new ItemShield(itemId + 518, ToolMaterialMedieval.ORNATE, EnumShieldDesign.TOWER, "guilded"));
    public static final Item towerEncrusted = (new ItemShield(itemId + 519, ToolMaterialMedieval.ENCRUSTED, EnumShieldDesign.TOWER));
    public static final Item towerDragonforge = (new ItemShield(itemId + 520, ToolMaterialMedieval.DRAGONFORGE, EnumShieldDesign.TOWER));
    
    public static final Item bucklerBronze = (new ItemShield(itemId + 521, ToolMaterialMedieval.BRONZE, EnumShieldDesign.BUCKLER));
    public static final Item bucklerIron = (new ItemShield(itemId + 522, ToolMaterialMedieval.IRON, EnumShieldDesign.BUCKLER));
    public static final Item bucklerSteel = (new ItemShield(itemId + 523, ToolMaterialMedieval.STEEL, EnumShieldDesign.BUCKLER));
    public static final Item bucklerMithril = (new ItemShield(itemId + 524, ToolMaterialMedieval.MITHRIL, EnumShieldDesign.BUCKLER));
    public static final Item bucklerGuilded = (new ItemShield(itemId + 525, ToolMaterialMedieval.ORNATE, EnumShieldDesign.BUCKLER, "guilded"));
    public static final Item bucklerEncrusted = (new ItemShield(itemId + 526, ToolMaterialMedieval.ENCRUSTED, EnumShieldDesign.BUCKLER));
    public static final Item bucklerDragonforge = (new ItemShield(itemId + 527, ToolMaterialMedieval.DRAGONFORGE, EnumShieldDesign.BUCKLER));
    
    public static final Item shieldWood = (new ItemShield(itemId + 528, ToolMaterialMedieval.STRONGWOOD, EnumShieldDesign.ROUND));
    public static final Item shieldIronbark = (new ItemShield(itemId + 529, ToolMaterialMedieval.IRONBARK, EnumShieldDesign.ROUND));
    public static final Item shieldEbony = (new ItemShield(itemId + 530, ToolMaterialMedieval.EBONY, EnumShieldDesign.ROUND));
    
    public static final Item bandage = new ItemBandage(itemId + 531);
    
    public static final Item bowDeepIron = new ItemBowMF(itemId + 532, ToolMaterialMedieval.DEEP_IRON, EnumBowType.RECURVE).setUnlocalizedName("bowDeepIron");
    
    public static final Item hammerDeepIron = new ItemHammer(itemId + 533, 3F, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("hammerDeepIron");
    public static final Item tongsDeepIron = new ItemTongs(itemId + 534, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("tongsDeepIron");
    public static final Item knifeDeepIron = new ItemKnifeMF(itemId + 535, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("knifeDeepIron");
    public static final Item shearsDeepIron = new ItemShearsMF(itemId + 536, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("shearsDeepIron");
    public static final Item needleDeepIron = new ItemNeedle(itemId + 537, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("needleDeepIron");
    public static final Item pickDeepIron = new ItemMedievalPick(itemId + 53, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("pickDeepIron");
    public static final Item handpickDeepIron = new ItemHandpick(itemId + 539, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("handpickDeepIron");
    public static final Item axeDeepIron = new ItemMedievalAxe(itemId + 560, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("axeDeepIron");
    public static final Item sawDeepIron = new ItemSaw(itemId + 561, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("sawDeepIron");
    public static final Item spadeDeepIron = new ItemMedievalSpade(itemId + 562, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("spadeDeepIron");
    public static final Item mattockDeepIron = new ItemMattock(itemId + 563, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("mattockDeepIron");
    public static final Item hoeDeepIron = new ItemMedievalHoe(itemId + 564, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("hoeDeepIron");
    public static final Item scytheDeepIron = new ItemScytheMF(itemId + 565, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("scytheDeepIron");
    public static final Item rakeDeepIron = new ItemRakeMF(itemId + 566, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("rakeDeepIron");
    
    public static final Item daggerDeepIron = new ItemDagger(itemId + 567, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("daggerDeepIron");
    public static final Item swordDeepIron = new ItemSwordMF(itemId + 568, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("swordDeepIron");
    public static final Item broadswordDeepIron = new ItemBroadsword(itemId + 569, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("broadswordDeepIron");
    public static final Item greatswordDeepIron = new ItemGreatsword(itemId + 590, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("greatswordDeepIron");
    public static final Item maceDeepIron = new ItemMaceMF(itemId + 591, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("maceDeepIron");
    public static final Item warpickDeepIron = new ItemWarpick(itemId + 592, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("warpickDeepIron");
    public static final Item greatmaceDeepIron = new ItemGreatmace(itemId + 593, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("greatmaceDeepIron");
    public static final Item warhammerDeepIron = new ItemWarhammer(itemId + 594, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("warhammerDeepIron");
    public static final Item waraxeDeepIron = new ItemWaraxe(itemId + 595, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("waraxeDeepIron");
    public static final Item battleaxeDeepIron = new ItemBattleaxe(itemId + 596, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("battleaxeDeepIron");
    public static final Item spearDeepIron = new ItemSpearMF(itemId + 597, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("spearDeepIron");
    public static final Item halbeardDeepIron = new ItemHalbeard(itemId + 598, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("halbeardDeepIron");
    public static final Item lanceDeepIron = new ItemLance(itemId + 599, ToolMaterialMedieval.DEEP_IRON).setUnlocalizedName("lanceDeepIron");
    
    public static final Item bucklerDeepIron = (new ItemShield(itemId + 600, ToolMaterialMedieval.DEEP_IRON, EnumShieldDesign.BUCKLER));
    public static final Item kiteDeepIron = (new ItemShield(itemId + 601, ToolMaterialMedieval.DEEP_IRON, EnumShieldDesign.KITE));
    public static final Item towerDeepIron = (new ItemShield(itemId + 602, ToolMaterialMedieval.DEEP_IRON, EnumShieldDesign.TOWER));
    
    public static final Item hound_DImail = new ItemHoundArmourMF(itemId + 603, EnumArmourMF.DEEP_IRON, false, "deepIron", 1, 60).setUnlocalizedName("hound_DImail");
    public static final Item hound_DImailH = new ItemHoundArmourMF(itemId + 604, EnumArmourMF.DEEP_IRON, false, "deepIron", 0, 60).setUnlocalizedName("hound_DImailH");
    public static final Item hound_DIplate = new ItemHoundArmourMF(itemId + 605, EnumArmourMF.DEEP_IRON, true, "deepIron", 1, 60).setUnlocalizedName("hound_DIplate");
    public static final Item hound_DIplateH = new ItemHoundArmourMF(itemId + 606, EnumArmourMF.DEEP_IRON, true, "deepIron", 0, 60).setUnlocalizedName("hound_DIplateH");
    public static final Item hound_DIteeth = new ItemHoundWeaponMF(itemId + 607, ToolMaterialMedieval.DEEP_IRON, "deepIron", 10, 1).setUnlocalizedName("hound_DIteeth");
    
    public static final Item helmetDeepIronPlate = (new ItemArmourMF(itemId + 608, ArmourDesign.PLATE, EnumArmourMF.DEEP_IRON, 1, 0, "deepIronPlate_1")).setUnlocalizedName("deepIronPlateHelmet");
    public static final Item plateDeepIronPlate = (new ItemArmourMF(itemId + 609, ArmourDesign.PLATE, EnumArmourMF.DEEP_IRON, 1, 1, "deepIronPlate_1")).setUnlocalizedName("deepIronPlateChest");
    public static final Item legsDeepIronPlate = (new ItemArmourMF(itemId + 610, ArmourDesign.PLATE, EnumArmourMF.DEEP_IRON, 2, 2, "deepIronPlate_2")).setUnlocalizedName("deepIronPlateLegs");
    public static final Item bootsDeepIronPlate = (new ItemArmourMF(itemId + 611, ArmourDesign.PLATE, EnumArmourMF.DEEP_IRON, 2, 3, "deepIronPlate_1")).setUnlocalizedName("deepIronPlateBoots");
    
    public static final Item helmetDeepIronChain = (new ItemArmourMF(itemId + 612, ArmourDesign.CHAIN, EnumArmourMF.DEEP_IRON, 1, 0, "deepIronChain_1")).setUnlocalizedName("deepIronChainHelmet");
    public static final Item plateDeepIronChain = (new ItemArmourMF(itemId + 613, ArmourDesign.CHAIN, EnumArmourMF.DEEP_IRON, 1, 1, "deepIronChain_1")).setUnlocalizedName("deepIronChainChest");
    public static final Item legsDeepIronChain = (new ItemArmourMF(itemId + 614, ArmourDesign.CHAIN, EnumArmourMF.DEEP_IRON, 2, 2, "deepIronChain_2")).setUnlocalizedName("deepIronChainLegs");
    public static final Item bootsDeepIronChain = (new ItemArmourMF(itemId + 615, ArmourDesign.CHAIN, EnumArmourMF.DEEP_IRON, 2, 3, "deepIronChain_1")).setUnlocalizedName("deepIronChainBoots");
    
    public static final Item helmetDeepIronSplint = (new ItemArmourMF(itemId + 616, ArmourDesign.SPLINT, EnumArmourMF.DEEP_IRON, 1, 0, "deepIronSplint_1")).setUnlocalizedName("deepIronSplintHelmet");
    public static final Item plateDeepIronSplint = (new ItemArmourMF(itemId + 617, ArmourDesign.SPLINT, EnumArmourMF.DEEP_IRON, 1, 1, "deepIronSplint_1")).setUnlocalizedName("deepIronSplintChest");
    public static final Item legsDeepIronSplint = (new ItemArmourMF(itemId + 618, ArmourDesign.SPLINT, EnumArmourMF.DEEP_IRON, 2, 2, "deepIronSplint_2")).setUnlocalizedName("deepIronSplintLegs");
    public static final Item bootsDeepIronSplint = (new ItemArmourMF(itemId + 619, ArmourDesign.SPLINT, EnumArmourMF.DEEP_IRON, 2, 3, "deepIronSplint_1")).setUnlocalizedName("deepIronSplintBoots");
    
    public static final Item helmetDeepIronHvyChain = (new ItemArmourMF(itemId + 620, ArmourDesign.HVYCHAIN, EnumArmourMF.DEEP_IRON, 1, 0, "deepIronHvyChain_1")).setUnlocalizedName("deepIronHvyHelmet");
    public static final Item plateDeepIronHvyChain = (new ItemArmourMF(itemId + 621, ArmourDesign.HVYCHAIN, EnumArmourMF.DEEP_IRON, 1, 1, "deepIronHvyChain_1")).setUnlocalizedName("deepIronHvyChest");
    public static final Item legsDeepIronHvyChain = (new ItemArmourMF(itemId + 622, ArmourDesign.HVYCHAIN, EnumArmourMF.DEEP_IRON, 2, 2, "deepIronHvyChain_2")).setUnlocalizedName("deepIronHvyLegs");
    public static final Item bootsDeepIronHvyChain = (new ItemArmourMF(itemId + 623, ArmourDesign.HVYCHAIN, EnumArmourMF.DEEP_IRON, 2, 3, "deepIronHvyChain_1")).setUnlocalizedName("deepIronHvyBoots");
    
    public static final Item helmetDeepIronScale = (new ItemArmourMF(itemId + 624, ArmourDesign.SCALE, EnumArmourMF.DEEP_IRON, 1, 0, "deepIronScale_1")).setUnlocalizedName("deepIronScaleHelmet");
    public static final Item plateDeepIronScale = (new ItemArmourMF(itemId + 625, ArmourDesign.SCALE, EnumArmourMF.DEEP_IRON, 1, 1, "deepIronScale_1")).setUnlocalizedName("deepIronScaleChest");
    public static final Item legsDeepIronScale = (new ItemArmourMF(itemId + 626, ArmourDesign.SCALE, EnumArmourMF.DEEP_IRON, 2, 2, "deepIronScale_2")).setUnlocalizedName("deepIronScaleLegs");
    public static final Item bootsDeepIronScale = (new ItemArmourMF(itemId + 627, ArmourDesign.SCALE, EnumArmourMF.DEEP_IRON, 2, 3, "deepIronScale_1")).setUnlocalizedName("deepIronScaleBoots");
    //Misc
    public static final int flux = 0;
    public static final int splintBronze = 1;
    public static final int lumpIron = 2;
    public static final int hidePig = 3;
    public static final int leatherRaw = 4;
    public static final int leatherSalt = 5;
    public static final int hideSheep = 6;
    public static final int leatherRough = 7;
    public static final int leatherGore = 8;
    public static final int padding = 9;
    public static final int salt = 10;
    public static final int slag = 11;
    public static final int slagSmall = 12;
    public static final int linkIron = 13;
    public static final int coke = 14;
    public static final int coalPowder = 15;
    public static final int saltPaper = 16;
    public static final int rawHide = 17;
    public static final int linkBronze = 18;
    public static final int fireGland = 19;
    public static final int fireExplosive = 20;
    public static final int ingotDragonImpure = 21;
    public static final int ingotDragon = 22;
    public static final int featherArrow = 23;
    public static final int sulfur = 24;
    public static final int plankIronbark = 25;
    public static final int leatherStrip = 26;
    public static final int leatherBelt = 27;
    public static final int smlPlateSteel = 28;
    public static final int curvedPlateSteel = 29;
    public static final int splintSteel = 30;
    public static final int scaleSteel = 31;
    //public static final int bigPlateSteel = 32;
    
    public static final int hideHound = 33;
    //public static final int hideHoundSalt = 34;
    //public static final int hideHoundCure = 35;
    
    public static final int scaleBronze = 36;
    public static final int scaleIron = 37;
    public static final int scaleMithril = 38;
    public static final int scaleDragonforge = 39;
    public static final int linkSteel = 40;
    public static final int chainSteel = 41;
    public static final int linkMithril = 42;
    public static final int chainMithril = 43;
    public static final int linkDragonforge = 44;
    public static final int chainDragon = 45;
    public static final int splintIron = 46;
    public static final int splintDragon = 47;
    
    public static final int coinGold = 48;
    public static final int coinSilver = 49;
    public static final int ignotDust = 50;
   // public static final int IgnotOre = 51;
    public static final int IgnotImpure = 52;
    public static final int mithOre = 53;
    public static final int mithRefined = 54;
    public static final int ingotMithril = 55;
    
    public static final int ingotCopper = 56;
    public static final int ingotTin = 57;
    public static final int ingotBronze = 58;
    public static final int haft = 59;
    public static final int ingotWroughtIron = 60;
    public static final int platingIron = 61;
    public static final int splintMithril = 62;
    public static final int smlPlateIron = 63;
    public static final int coinCopper = 64;
    public static final int haftStrong = 65;
    public static final int limestoneHunk = 66;
    public static final int curvedPlateIron = 67;
    
    public static final int platingEncrusted = 68;
    public static final int smlPlateEncrusted = 69;
    public static final int curvedPlateEncrusted = 70;
    //public static final int bigPlateEncrusted = 71;
    public static final int platingBronze = 72;
    public static final int smlPlateBronze = 73;
    public static final int curvedPlateBronze = 74;
    //public static final int bigPlateBronze = 75;
    public static final int chainBronze = 76;
    
    public static final int haftIronbark = 77;
    //public static final int oreIron = 78;
    //public static final int oreGold = 79;
    //public static final int oreSilver = 80;
    
    public static final int haftOrnate = 81;
    //public static final int oreCopper = 82;
    public static final int haftEbony = 83;
   //public static final int oreTin = 84;
    
    //public static final int studdedLeather = 85;
    //public static final int bowlWater = 86;
    public static final int stickIronbark = 87;
    public static final int glueWeak = 88;
    public static final int glueStrong = 89;
    
    /**
     * FORGE: Burns 20% hotter
     * FURNACE: Burns 4x longer
     */
    public static final int infernoCoal = 90;
    /**
     * FORGE: Burns 40% hotter, 50% longer
     * FURNACE: Burns 8x longer
     */
    public static final int HellCoal = 91;
    /**
     * FORGE: Burns 2x longer
     * FURNACE: Burns 2x longer
     */
    public static final int longCoal = 92;
    
   // public static final int linen = 93;
    //public static final int bigPlateIron = 94;
    public static final int platingMithril = 95;
    public static final int smlPlateMithril = 96;
    public static final int curvedPlateMithril = 97;
    
    public static final int hideMinotaur = 98;
    //public static final int saltMinotaur = 99;
    //public static final int curedMinotaur = 100;
    
    public static final int hideDrake = 101;
    //public static final int saltDrake = 102;
    //public static final int curedDrake = 103;
    
    public static final int blackLeather = 104;
    
    public static final int crossbowMech = 105;
    public static final int crossbowMechRepeat = 106;
    public static final int boltBox = 107;
    
    public static final int rock = 108;
    public static final int tendon = 109;
    public static final int vine = 110;
    public static final int shale = 111;
    //public static final int tendonSalt = 112;
    public static final int shardCopper = 113;
    public static final int plankEbony = 114;
    //public static final int bigPlateMithril = 115;
    public static final int chainIron = 116;
    public static final int platingSteel = 117;
    public static final int platingDragon = 118;
    public static final int smlPlateDragon = 119;
    public static final int curvedPlateDragon = 120;
    //public static final int bigPlateDragon = 121;
    public static final int lumpBronze = 122;
    public static final int lumpSteel = 123;
    public static final int lumpMithril = 124;
    public static final int buckle = 125;
    
    public static final int stickEbony = 126;
    
    public static final int arrowheadBronze = 127;
    public static final int arrowheadIron = 128;
    public static final int arrowheadSteel = 129;
    public static final int arrowheadMithril = 130;
    public static final int arrowheadSilver = 131;
    public static final int arrowheadEncrusted = 132;
    public static final int arrowheadDragonforge = 133;
    public static final int arrowheadIgnotumite = 134;
    
    public static final int broadheadBronze = 135;
    public static final int broadheadIron = 136;
    public static final int broadheadSteel = 137;
    public static final int broadheadMithril = 138;
    public static final int broadheadSilver = 139;
    public static final int broadheadEncrusted = 140;
    public static final int broadheadDragonforge = 141;
    public static final int broadheadIgnotumite = 142;
    
    public static final int bodkinheadBronze = 143;
    public static final int bodkinheadIron = 144;
    public static final int bodkinheadSteel = 145;
    public static final int bodkinheadMithril = 146;
    public static final int bodkinheadSilver = 147;
    public static final int bodkinheadEncrusted = 148;
    public static final int bodkinheadDragonforge = 149;
    public static final int bodkinheadIgnotumite = 150;
    public static final int twine = 151;
    public static final int hunkIgnotumite = 152;
    public static final int ingotIgnotumite = 153;
    public static final int hideHorse = 154;
    //public static final int hideHorseSalt = 155;
    //public static final int hideHorseCure = 156;
    public static final int hideBasiliskBlue = 157;
    //public static final int hideBasiliskBlueSalt = 158;
    //public static final int hideBasiliskBlueCure = 159;
    public static final int hideBasiliskBrown = 160;
    //public static final int hideBasiliskBrownSalt = 161;
    //public static final int hideBasiliskBrownCure = 162;
    public static final int hideBasiliskBlack = 163;
    //public static final int hideBasiliskBlackSalt = 164;
    //public static final int hideBasiliskBlackCure = 165;
    
    public static final int nuggetSteel = 166;
    public static final int ingotGoldPure = 167;
    
    public static final int scaleGuilded = 168;
    public static final int linkGuilded = 169;
    public static final int chainGuilded = 170;
    public static final int splintGuilded = 171;
    public static final int smlPlateSilver = 172;
    public static final int platingSilver = 173;
    public static final int curvedPlateSilver = 174;
    //public static final int plateSilverBig = 175;
    public static final int shrapnel = 176;
    public static final int nitre = 177;
    
    public static final int scaleDeepIron = 178;
    public static final int linkDeepIron = 179;
    public static final int chainDeepIron = 180;
    public static final int splintDeepIron = 181;
    public static final int platingDeepIron = 182;
    public static final int curvedPlateDeepIron = 183;
    public static final int smlPlateDeepIron = 184;
    public static final int ingotDeepIron = 185;
    public static final int lumpDeepIron = 186;
    public static final int bodkinheadDeepIron = 187;
    public static final int broadheadDeepIron = 188;
    public static final int arrowheadDeepIron = 189;
    
	public static void init() 
	{
		addChestGen();
		CrossbowAmmo.addArrow(Item.arrow.itemID);
		
		addDispenserBehavior(arrowMF, new DispenseArrowMF());
		addDispenserBehavior(boltMF, new DispenseBoltMF());
		addDispenserBehavior(bombMF, new DispenseBombMF());
		//flux
		MineFantasyAPI.addFlux(component(flux));
		
		//TC flux
		
		//carbon
		MineFantasyAPI.addCarbon(Item.coal.itemID);
		MineFantasyAPI.addCarbon(component(coke));
		
		//blast fuel
		MineFantasyAPI.addBlastFuel(component(coalPowder), 5);
		MineFantasyAPI.addBlastFuel(component(coke), 6);
		MineFantasyAPI.addBlastFuel(Item.coal.itemID, 0, 8);
		MineFantasyAPI.addBlastFuel(Block.coalBlock.blockID, 0, 80);
		MineFantasyAPI.addBlastFuel(Item.coal.itemID, 1, 8);
		MineFantasyAPI.addBlastFuel(misc.itemID, longCoal, 16);
		MineFantasyAPI.addBlastFuel(misc.itemID, infernoCoal, 32);
		MineFantasyAPI.addBlastFuel(misc.itemID, HellCoal, 64);
		MineFantasyAPI.addBlastFuel(Item.bucketLava.itemID, 256);
		
		//forge fuel
		MineFantasyAPI.addForgeFuel(misc.itemID, coalPowder, 600, 300);//30s
		MineFantasyAPI.addForgeFuel(misc.itemID, coke, 800, 400);//40s
		MineFantasyAPI.addForgeFuel(misc.itemID, longCoal, 6000, 400);//5mins
		MineFantasyAPI.addForgeFuel(Item.coal.itemID, 1200, 400);//1 min
		MineFantasyAPI.addForgeFuel(misc.itemID, infernoCoal, 1600, 1300);//1:30
		MineFantasyAPI.addForgeFuel(Item.blazePowder.itemID, 1200, 800, true);//1 min
		MineFantasyAPI.addForgeFuel(misc.itemID, HellCoal, 2400, 1500, true);//2 mins
		MineFantasyAPI.addForgeFuel(Item.bucketLava.itemID, 12000, 1000, true);//10 mins
		
		//random oredrops
		MineFantasyAPI.addRandomOre(component(nitre), 0.03F);// 3% chance
		MineFantasyAPI.addRandomOre(component(flux), 0.02F);// 2% chance
		MineFantasyAPI.addRandomOre(new ItemStack(Item.flint), 0.05F);// 5% chance
		MineFantasyAPI.addRandomOre(component(flux), 0.1F, 0, BlockListMF.limestone, false);// 10% chance
		MineFantasyAPI.addRandomOre(component(sulfur), 0.05F, 0, Block.netherrack, false);// 5% chance
		MineFantasyAPI.addRandomOre(new ItemStack(Item.blazePowder), 0.07F, 0, Block.netherrack, false);// 7% chance
		MineFantasyAPI.addRandomOre(component(sulfur), 0.02F, 3, Block.stone, 0, 32, false);// 2% chance
		MineFantasyAPI.addRandomOre(new ItemStack(Item.coal), 0.06F);// 6% chance
		
		MineFantasyAPI.addRandomOre(new ItemStack(Item.coal), 0.75F, 0, Block.oreCoal, true);// 50% chance
		MineFantasyAPI.addRandomOre(new ItemStack(Item.diamond), 0.2F, 0, Block.oreDiamond, true);// 20% chance
		MineFantasyAPI.addRandomOre(new ItemStack(Item.redstone), 1.0F, 0, Block.oreRedstone, true);// 100% chance
		MineFantasyAPI.addRandomOre(new ItemStack(Item.redstone), 1.0F, 0, Block.oreRedstoneGlowing, true);// 100% chance
		MineFantasyAPI.addRandomOre(new ItemStack(Item.dyePowder, 4), 1.0F, 0, Block.oreLapis, true);// 100% chance
		MineFantasyAPI.addRandomOre(new ItemStack(Item.emerald), 0.15F, 0, Block.oreEmerald, true);// 15% chance
		
        assignItemOverrides();
	}
	private static void assignItemOverrides() 
	{
		System.out.println("MineFantasy: Assigning Item Overrides ignore slot conflict messages");
        Item.paper = (new ItemPaper(83)).setUnlocalizedName("paper").setTextureName("paper");
       
        Item.helmetChain = (ItemArmor) (new ItemArmourMF(46, ArmourDesign.CHAIN, EnumArmourMF.IRON, 1, 0, "chain_1")).setUnlocalizedName("ironChainHelmet");
        Item.plateChain = (ItemArmor) (new ItemArmourMF(47, ArmourDesign.CHAIN, EnumArmourMF.IRON, 1, 1, "chain_1")).setUnlocalizedName("ironChainChest");
        Item.legsChain = (ItemArmor) (new ItemArmourMF(48, ArmourDesign.CHAIN, EnumArmourMF.IRON, 1, 2, "chain_2")).setUnlocalizedName("ironChainLegs");
        Item.bootsChain = (ItemArmor) (new ItemArmourMF(49, ArmourDesign.CHAIN, EnumArmourMF.IRON, 1, 3, "chain_1")).setUnlocalizedName("ironChainBoots");
        
        Item.bow = (ItemBow) new ItemVanillaBow(5).setUnlocalizedName("bow").setTextureName("bow");
        System.out.println("MineFantasy: Finished Item Overrides");
    }
	public static ItemStack component(int id)
	{
		return new ItemStack(misc, 1, id);
	}
	public static ItemStack component(int id, int val)
	{
		return new ItemStack(misc, val, id);
	}
	private static void addChestGen()
	{
		//WeightedRandomChestContent
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(pickBronze), 1, 1, 10));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(sawBronze), 1, 1, 20));
		
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(apronSmithy), 1, 1, 30));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(component(ingotWroughtIron), 1, 4, 30));
		
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(pickIronForged), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(component(ingotCopper), 1, 4, 5));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(component(ingotTin), 1, 4, 3));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(BlockListMF.oreCopper), 1, 4, 6));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(BlockListMF.oreTin), 1, 4, 3));
		
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(swordOrnate), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(daggerOrnate), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(broadOrnate), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(greatswordOrnate), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(waraxeOrnate), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(battleaxeOrnate), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(maceOrnate), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(greatmaceOrnate), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(spearOrnate), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(lanceOrnate), 1, 1, 1));
		
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(component(ingotGoldPure), 1, 4, 2));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(component(ingotGoldPure), 1, 4, 1));
		
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(component(ingotGoldPure), 1, 4, 2));
	}
	public static void addDispenserBehavior(Item item, IBehaviorDispenseItem behavior)
    {
    	BlockDispenser.dispenseBehaviorRegistry.putObject(item, behavior);
    }
}