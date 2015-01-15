package minefantasy.block;

import minefantasy.block.special.*;
import minefantasy.item.ItemListMF;
import minefantasy.system.cfg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockStep;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTextureTile;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;

//ItemListMF
public class BlockListMF 
{
	public static StepSound stepSoundSlag = new StepSound("gravel", 1.0F, 0.5F);
	
	public static final Block lantern = new BlockLantern(cfg.lanternId, 0, Material.wood).setUnlocalizedName("lantern").setHardness(2F).setResistance(7F).setLightValue(1.0F);
    public static final Block forge = new BlockForge(cfg.forgeId).setUnlocalizedName("forge").setHardness(10F).setResistance(15F);
    /**
     * Tiers:
     * Stone-0__
     * SmlBronze-1__
     * Bronze-2__
     * SmlIron-3__
     * Iron-4__
     * SmlSteel-5__
     * Steel-6__
     */
    public static final Block anvil = new BlockAnvilMF(cfg.anvilId, 1, Material.iron).setUnlocalizedName("anvil").setHardness(5F).setResistance(10F);
    public static final Block mudBrick = new BlockMudbrick(cfg.mudBrickId).setHardness(0.7F).setResistance(1F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("mudBrick");
    public static final Block cobbBrick = new BlockCobbleBrick(cfg.cobbBrickId).setHardness(3F).setResistance(12F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("cobbBrick");
    public static final Block rePlanks = new BlockMedieval(cfg.rePlanksId, Material.wood, cfg.rePlanksId).setHardness(3F).setResistance(12F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("rePlanks");
    public static final Block oreUtil = new BlockUtilOre(cfg.oreUtilId).setHardness(2F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreSilver");
    public static final Block slate = (new BlockSlate(cfg.slateId)).setHardness(2.2F).setResistance(6F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("slate");
    public static final Block clayWall = new BlockClayWall(cfg.clayWallId).setHardness(1F).setResistance(1.2F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("clayWall");
    public static final Block granite = new BlockMedieval(cfg.graniteId, Material.rock, cfg.graniteId).setHardness(5F).setResistance(18F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("granite");
    public static final Block graniteBrick = new BlockGraniteBrick(cfg.graniteBrickId).setHardness(8F).setResistance(25F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("graniteBrick");
    public static final Block planks = new BlockPlanksMF(cfg.planksId).setHardness(3F).setResistance(5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("planksMF");
    public static final Block log = new BlockLogMedieval(cfg.ironbarkId).setHardness(5.0F).setResistance(8F).setUnlocalizedName("logMF").setStepSound(Block.soundWoodFootstep);
    public static final Block leaves = new BlockLeavesMF(cfg.leavesId).setHardness(0.2F).setLightOpacity(1).setUnlocalizedName("leavesIronbark").setStepSound(Block.soundGrassFootstep);
    public static final Block sapling = new BlockSaplingMF(cfg.saplingId).setHardness(0.2F).setLightOpacity(1).setUnlocalizedName("saplingIronbark");
    public static final Block tanner = new BlockTanningRack(cfg.tannerId, 1, Material.wood).setUnlocalizedName("tanner").setHardness(5F).setResistance(10F);
    public static final Block road = new BlockRoad(cfg.roadId, 0.9375F).setUnlocalizedName("road").setHardness(0.5F).setStepSound(Block.soundGravelFootstep);
    public static final Block oreIgnotumite = new BlockIgnotumite(cfg.oreIgnotumiteId, 169, Material.rock).setLightValue(0.7F).setUnlocalizedName("oreIgnotumite").setHardness(100F).setResistance(2000F);
    public static final Block stairSmoothstone = new BlockStairsMod(cfg.stairStoneId, Block.stone, 0).setLightOpacity(1).setUnlocalizedName("stairSmoothstone").setHardness(1F).setResistance(1.5F);
    public static final Block stairCobbBrick = new BlockMedievalStairs(cfg.stairCStoneId, cobbBrick).setLightOpacity(1).setUnlocalizedName("stairCobbBrick").setHardness(1F).setResistance(1.5F);
    public static final Block dogbowl = new BlockDogBowl(cfg.dogbowlId).setUnlocalizedName("dogbowl").setHardness(0.2F).setResistance(0F);
    public static final Block weaponRack = new BlockWeaponRack(cfg.weaponRackId).setUnlocalizedName("weaponRack").setHardness(0.4F).setResistance(1F);
    public static final Block hayRoof = new BlockMedievalStairs(cfg.hayRoofId, leaves, Material.leaves, 113).setLightOpacity(1).setUnlocalizedName("hayRoof").setHardness(1F).setResistance(0.2F);
    public static final Block oreCopper = new BlockMedieval(cfg.oreCopperId, Material.rock, cfg.oreCopperId).setHardness(2F).setResistance(3F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreCopper");
    public static final Block oreTin = new BlockMedieval(cfg.oreTinId, Material.rock, cfg.oreTinId).setHardness(2F).setResistance(3F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreTin");
    public static final Block oreMythic = new BlockMythicOre(cfg.oreMithicId).setHardness(5F).setResistance(15F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreMithril");
    public static final Block smelter = new BlockSmelter(cfg.bloomId).setUnlocalizedName("bloom").setHardness(5F).setResistance(10F);
    public static final Block bellows = new BlockBellows(cfg.bellowsId).setUnlocalizedName("bellows").setHardness(1F).setResistance(1F);
    public static final Block limestone = new BlockMedieval(cfg.limestoneId, Material.rock, cfg.limestoneId).setUnlocalizedName("limestone").setHardness(3F).setResistance(5F);
    /**
     * 0 = shaft
     * 1 = in
     * 2 = fuel
     * 3 = out
     */
    public static final Block Blast = new BlockBFurnace(cfg.BlastId, 0, Material.iron).setUnlocalizedName("Blast").setHardness(8F).setResistance(10F);
    public static final Block Lowroad = new BlockRoad(cfg.dirtSlabSId, 0.5F).setUnlocalizedName("road").setHardness(0.5F).setStepSound(Block.soundGravelFootstep);

    public static final Block doorIronbark = (new BlockDoorMF(cfg.ironbarkDoorId, EnumMFDoor.IRONBARK)).setHardness(4.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("doorIronbark");
    public static final Block doorHard = (new BlockDoorMF(cfg.hardDoorId, EnumMFDoor.REINFORCED)).setHardness(6.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("doorHard");
    public static final Block doorSteel = (new BlockDoorMF(cfg.steelDoorId, EnumMFDoor.STEEL)).setHardness(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("doorSteel");
    /**
     * 0 = steel_______________
     * 1 = copper_______________
     * 2 = tin_______________
     * 3 = bronze_______________
     * 4 = mithril_______________
     * 5 = silver_______________
     * 6 = Unused_______________
     * 7 = wrought_______________
     * 8 = deep iron_____________
     */
    public static final Block storage = (new BlockStorageMF(cfg.storeId)).setHardness(5F).setResistance(8F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("storage").setCreativeTab(CreativeTabs.tabBlock);
    public static final Block ice = (new BlockIceMF(cfg.iceId, 67)).setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("ice");
    public static final Block chimney = (new BlockChimney(cfg.chimId)).setHardness(1.5F).setResistance(0.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("chimney");
    
    public static final Block firepit = (new BlockFirepit(cfg.firepitId)).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("firepit");
    
    
    public static final BlockHalfSlab woodDoubleSlab = (BlockHalfSlab)(new BlockWoodSlabMF(cfg.WslabId, true)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("woodSlab");
    public static final BlockHalfSlab woodSingleSlab = (BlockHalfSlab)(new BlockWoodSlabMF(cfg.WdSlabId, false)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("woodSlab");
    public static final BlockHalfSlab stoneDoubleSlab = (BlockHalfSlab)(new BlockStoneSlabMF(cfg.SslabId, true)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("stoneSlab");
    public static final BlockHalfSlab stoneSingleSlab = (BlockHalfSlab)(new BlockStoneSlabMF(cfg.SdSlabId, false)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("stoneSlab");
    
    public static final Block tripHammer = new BlockTripHammer(cfg.tripHammerId).setUnlocalizedName("tripHammer").setHardness(10F).setResistance(10F);
    public static final Block slag = new BlockSlag(cfg.slagId).setUnlocalizedName("slag").setHardness(1F).setResistance(0.5F).setStepSound(stepSoundSlag);
    public static final Block roast = new BlockRoast(cfg.roastId).setUnlocalizedName("roast").setHardness(0.4F).setResistance(1F);
    
    public static final BlockHalfSlab slateDoubleSlab = (BlockHalfSlab)(new BlockSlateSlab(cfg.SlslabId, true)).setHardness(2.4F).setResistance(12.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("slateSlab");
    public static final BlockHalfSlab slateSingleSlab = (BlockHalfSlab)(new BlockSlateSlab(cfg.SldSlabId, false)).setHardness(1.2F).setResistance(12.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("slateSlab");
    public static final Block slateStairs = new BlockMedievalStairs(cfg.stairSlateId, slate, 0).setLightOpacity(1).setUnlocalizedName("stairSlate").setHardness(2F).setResistance(3F);
    public static final Block slateStairsTile = new BlockMedievalStairs(cfg.stairSlateTileId, slate, 1).setLightOpacity(1).setUnlocalizedName("stairSlateTile").setHardness(2F).setResistance(3F);
    public static final Block slateDStairsTile = new BlockMedievalStairs(cfg.stairDSlateTileId, slate, 2).setLightOpacity(1).setUnlocalizedName("stairDSlateTile").setHardness(2F).setResistance(3F);
    public static final Block slateStairsBrick = new BlockMedievalStairs(cfg.stairSlateBrickId, slate, 3).setLightOpacity(1).setUnlocalizedName("stairSlateBrick").setHardness(2F).setResistance(3F);
    
    public static final Block furnace = new BlockFurnaceMF(cfg.furnaceOnId).setUnlocalizedName("furnace").setHardness(5F).setResistance(10F);
    public static final Block foodPrep = new BlockFoodPrep(cfg.foodPrepId, Material.wood).setUnlocalizedName("foodPrep").setHardness(1F).setResistance(0F);
    public static final Block tailor = new BlockTailor(cfg.tailorId, Material.wood).setUnlocalizedName("tailor").setHardness(4F).setResistance(2F);
    public static final Block spinningWheel = new BlockSpinningWheel(cfg.spinnerId).setUnlocalizedName("spinningWheel").setHardness(2F).setResistance(0.5F);
    
    public static final Block oreInferno = new BlockHellCoal(cfg.infernoId).setUnlocalizedName("oreInferno").setHardness(2F).setResistance(2F).setLightValue(0.25F);
    public static final Block oven = new BlockOven(cfg.ovenId).setUnlocalizedName("oven").setHardness(3.5F).setResistance(8F);
    //public static final Block axle = new BlockAxle(cfg.axleId).setUnlocalizedName("axle").setHardness(0.5F).setResistance(0.1F);
    //public static final Block gearbox = new BlockGearbox(cfg.gearboxId).setUnlocalizedName("gearbox").setHardness(1F).setResistance(0.3F);
    public static final Block stairCobbBrickRough = new BlockMedievalStairs(cfg.stairCStoneRoughId, cobbBrick, 3).setLightOpacity(1).setUnlocalizedName("stairCobbBrick").setHardness(1F).setResistance(1.5F);
    
    public static void init()
    {
    	MinecraftForge.setBlockHarvestLevel(slate, "pickaxe", 1);
    	MinecraftForge.setBlockHarvestLevel(slateStairs, "pickaxe", 1);
    	MinecraftForge.setBlockHarvestLevel(slateStairsTile, "pickaxe", 1);
    	MinecraftForge.setBlockHarvestLevel(slateDStairsTile, "pickaxe", 1);
    	MinecraftForge.setBlockHarvestLevel(slateStairsBrick, "pickaxe", 1);
    	MinecraftForge.setBlockHarvestLevel(slateDoubleSlab, "pickaxe", 1);
    	MinecraftForge.setBlockHarvestLevel(slateSingleSlab, "pickaxe", 1);
    	MinecraftForge.setBlockHarvestLevel(storage, "pickaxe", 1);
    	MinecraftForge.setBlockHarvestLevel(Blast, "pickaxe", 2);
    	MinecraftForge.setBlockHarvestLevel(oreCopper, "pickaxe", 1);
    	MinecraftForge.setBlockHarvestLevel(oreInferno, 0, "pickaxe", 2);
    	MinecraftForge.setBlockHarvestLevel(oreInferno, 1, "pickaxe", 4);
        MinecraftForge.setBlockHarvestLevel(oreUtil, "pickaxe", 2);
        
        MinecraftForge.setBlockHarvestLevel(oreMythic, 0, "pickaxe", 4);
        MinecraftForge.setBlockHarvestLevel(oreMythic, 1, "pickaxe", 3);
        MinecraftForge.setBlockHarvestLevel(oreMythic, 2, "pickaxe", 3);
        MinecraftForge.setBlockHarvestLevel(forge, 1, "pickaxe", 3);
        
        if(!cfg.weakIron)
        {
        	MinecraftForge.setBlockHarvestLevel(Block.oreIron, "pickaxe", 2);
        }
        MinecraftForge.setBlockHarvestLevel(oreIgnotumite, "pickaxe", 4);
        MinecraftForge.setBlockHarvestLevel(granite, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(limestone, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(graniteBrick, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(log, "axe", 1);
        MinecraftForge.setBlockHarvestLevel(planks, "axe", 1);
        MinecraftForge.setBlockHarvestLevel(road, "shovel", 0);
        GameRegistry.registerBlock(spinningWheel, "SpinningWheel");
        GameRegistry.registerBlock(oreInferno, ItemBlockHellCoal.class, "oreInferno");
        GameRegistry.registerBlock(tailor, "Tailor");
        GameRegistry.registerBlock(slateStairs, "slateStair");
        GameRegistry.registerBlock(slateStairsTile, "slateStairTile");
        GameRegistry.registerBlock(slateDStairsTile, "slateDTileStair");
        GameRegistry.registerBlock(slateStairsBrick, "slateStairBrick");
        
        GameRegistry.registerBlock(oven, ItemBlockOven.class, "ovenMF");
        GameRegistry.registerBlock(foodPrep, "benchMF");
        GameRegistry.registerBlock(furnace, ItemBlockFurnaceMF.class, "furnaceMF");
        GameRegistry.registerBlock(tripHammer, ItemBlockTripHammer.class, "tripHammer");
        GameRegistry.registerBlock(slate, ItemBlockSlate.class, "slateMF");
        GameRegistry.registerBlock(firepit, "fireMF");
        GameRegistry.registerBlock(slag, "slag");
        GameRegistry.registerBlock(chimney, ItemBlockChimney.class, "chimneyMF");
        GameRegistry.registerBlock(storage, ItemBlockStorageMF.class, "metalMF");
        GameRegistry.registerBlock(planks,  ItemBlockTreeMF.class, "planksMF");
        GameRegistry.registerBlock(lantern, "lanternMF");
        GameRegistry.registerBlock(doorIronbark, "doorIBark");
        GameRegistry.registerBlock(doorHard, "doorRe");
        GameRegistry.registerBlock(doorSteel, "doorSteel");
        GameRegistry.registerBlock(Blast, ItemBlockBFurnace.class, "blastFurn");
        GameRegistry.registerBlock(bellows, "bellowsMF");
        GameRegistry.registerBlock(limestone, "limestoneMF");
        GameRegistry.registerBlock(smelter, ItemBlockSmelter.class, "smelterMF");
        GameRegistry.registerBlock(oreTin, "oreTin");
        GameRegistry.registerBlock(oreCopper, "oreCopper");
        GameRegistry.registerBlock(oreMythic, ItemBlockMythicOre.class, "oreMythic");
        GameRegistry.registerBlock(forge, ItemBlockForge.class, "forgeMF");
        GameRegistry.registerBlock(anvil, ItemBlockAnvil.class, "anvilMF");
        GameRegistry.registerBlock(mudBrick, ItemBlockMudbrick.class, "mudBrick");
        GameRegistry.registerBlock(stairSmoothstone, "smoothStair");
        GameRegistry.registerBlock(stairCobbBrick, "cobbBrickStair");
        GameRegistry.registerBlock(stairCobbBrickRough, "cobbBrickStairRough");
        GameRegistry.registerBlock(cobbBrick, ItemBlockCobbbrick.class, "cobbBrick");
        GameRegistry.registerBlock(rePlanks, "rePlanks");
        GameRegistry.registerBlock(oreUtil, ItemBlockUtilOre.class, "oreUtil");
        GameRegistry.registerBlock(oreIgnotumite, "oreIgnotumite");
        GameRegistry.registerBlock(clayWall, ItemBlockWallclayMF.class, "clayWall");
        GameRegistry.registerBlock(road, "roadMF");
        GameRegistry.registerBlock(Lowroad, "lowRoadMF");
        GameRegistry.registerBlock(granite, "graniteMF");
        GameRegistry.registerBlock(graniteBrick, ItemBlockGranbrick.class, "granBrickMF");
        GameRegistry.registerBlock(log, ItemBlockTreeMF.class, "logMF");
        GameRegistry.registerBlock(leaves,  ItemBlockTreeMF.class, "leavesMF");
        GameRegistry.registerBlock(tanner, "tannerMF");
        GameRegistry.registerBlock(sapling,  ItemBlockSaplingMF.class, "saplingMF");
        GameRegistry.registerBlock(dogbowl, ItemBlockDogbowl.class, "dogBowl");
        GameRegistry.registerBlock(weaponRack, "weaponRack");
        GameRegistry.registerBlock(roast, "spitRoast");
        GameRegistry.registerBlock(hayRoof, "hayRoof");
        
    }
    
    
    
    static
    {
    	Item.itemsList[woodSingleSlab.blockID] = (new ItemWoodSlabMF(woodSingleSlab.blockID - 256, woodSingleSlab, woodDoubleSlab, false)).setUnlocalizedName("woodSlab");
        Item.itemsList[woodDoubleSlab.blockID] = (new ItemWoodSlabMF(woodDoubleSlab.blockID - 256, woodSingleSlab, woodDoubleSlab, true)).setUnlocalizedName("woodSlab");
        Item.itemsList[stoneSingleSlab.blockID] = (new ItemStoneSlabMF(stoneSingleSlab.blockID - 256, stoneSingleSlab, stoneDoubleSlab, false)).setUnlocalizedName("stoneSlab");
        Item.itemsList[stoneDoubleSlab.blockID] = (new ItemStoneSlabMF(stoneDoubleSlab.blockID - 256, stoneSingleSlab, stoneDoubleSlab, true)).setUnlocalizedName("stoneSlab");
        Item.itemsList[slateSingleSlab.blockID] = (new ItemBlockSlateSlab(slateSingleSlab.blockID - 256, slateSingleSlab, slateDoubleSlab, false)).setUnlocalizedName("slateSlab");
        Item.itemsList[slateDoubleSlab.blockID] = (new ItemBlockSlateSlab(slateDoubleSlab.blockID - 256, slateSingleSlab, slateDoubleSlab, true)).setUnlocalizedName("slateSlab");
    }
}