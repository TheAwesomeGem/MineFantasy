
package minefantasy.system;

import java.util.ArrayList;

import minefantasy.MineFantasyBase;
import minefantasy.api.MineFantasyAPI;
import minefantasy.api.forge.HeatableItem;
import minefantasy.api.forge.ItemHandler;
import minefantasy.api.tailor.StringList;
import minefantasy.api.tanner.LeathercuttingRecipes;
import minefantasy.api.tanner.TanningRecipes;
import minefantasy.block.BlockListMF;
import minefantasy.block.BlockSlate;
import minefantasy.item.ItemListMF;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.RecipesMapCloning;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 *
 * @author AnonymousProductions
 * 
 * AnvilRecipesMF
 */
public class RecipesMF {

	//AnvilRecipesMF
    public static void initiate() 
    {
    	addBombs();
    	addLeatherRecipes();
    	addTailoring();
    	addPrimitive();
    	addHeatables();
    	addHaftTiers();
    	addIngotAlternatives();
    	addFletching();
    	
    	addBlock(new ItemStack(BlockListMF.storage, 1, 0), new ItemStack(ItemListMF.ingotSteel));
    	addBlock(com(ItemListMF.shale), com(ItemListMF.rock));
    	addBlock(new ItemStack(Block.vine), com(ItemListMF.vine));
    	addBlock(new ItemStack(BlockListMF.storage, 1, 1), com(ItemListMF.ingotCopper));
    	addBlock(new ItemStack(BlockListMF.storage, 1, 2), com(ItemListMF.ingotTin));
    	addBlock(new ItemStack(BlockListMF.storage, 1, 3), com(ItemListMF.ingotBronze));
    	addBlock(new ItemStack(BlockListMF.storage, 1, 4), com(ItemListMF.ingotMithril));
    	addBlock(new ItemStack(BlockListMF.storage, 1, 5), new ItemStack(ItemListMF.ingotSilver));
    	addBlock(new ItemStack(BlockListMF.storage, 1, 7), com(ItemListMF.ingotWroughtIron));
    	addBlock(new ItemStack(BlockListMF.storage, 1, 8), com(ItemListMF.ingotDeepIron));
    	ArrayList<ItemStack> steel = OreDictionary.getOres("ingotSteel");
    	ArrayList<ItemStack> copper = OreDictionary.getOres("ingotCopper");
    	ArrayList<ItemStack> tin = OreDictionary.getOres("ingotTin");
    	ArrayList<ItemStack> copperB = OreDictionary.getOres("blockCopper");
    	ArrayList<ItemStack> Silver = OreDictionary.getOres("ingotSilver");
    	
    	MineFantasyAPI.addOvenRecipe(com(ItemListMF.saltPaper), com(ItemListMF.salt, 2));
    	FurnaceRecipes.smelting().addSmelting(ItemListMF.misc.itemID, ItemListMF.saltPaper, com(ItemListMF.salt, 2), 0F);
    	for(int a = 0; a < steel.size(); a ++)
    	{
    		addSteel(steel.get(a));
    	}
    	addSalt("salt");
    	addSalt("oreSalt");
    	addSalt("foodSalt");
    	GameRegistry.addShapelessRecipe(com(ItemListMF.salt), new Object[]{com(ItemListMF.saltPaper)});
    	for(int a = 0; a < copper.size(); a ++)
    	{
    		addCopper(copper.get(a));
    		for(int b = 0; b < tin.size(); b ++)
        	{
    			MineFantasyAPI.addRatioAlloy(5, com(ItemListMF.ingotBronze, 3), new Object[]
    					{
    				copper.get(a),
    				copper.get(a),
    				tin.get(b),
    					});
        	}
    	}
    	
    	GameRegistry.addRecipe(new ItemStack(ItemListMF.malletWood), new Object[]{
            "W",
            "G",
            "S",
            'G', com(ItemListMF.glueWeak),
            'W', ItemListMF.plank,
            'S', Item.stick,
		});
    	GameRegistry.addRecipe(new ItemStack(ItemListMF.malletIronbark), new Object[]{
            "W",
            "G",
            "S",
            'G', com(ItemListMF.glueStrong),
            'W', com(ItemListMF.plankIronbark),
            'S', com(ItemListMF.stickIronbark),
		});
    	GameRegistry.addRecipe(new ItemStack(ItemListMF.malletEbony), new Object[]{
            "W",
            "G",
            "S",
            'G', com(ItemListMF.glueStrong),
            'W', com(ItemListMF.plankEbony),
            'S', com(ItemListMF.stickEbony),
		});
    	
    	for(int a = 0; a < ItemHandler.carbon.size(); a ++)
    	{
	    	MineFantasyAPI.addRatioAlloy(5, com(ItemListMF.nuggetSteel), 1, new Object[]
					{
				ItemHandler.carbon.get(a),
				ItemHandler.carbon.get(a),
				com(ItemListMF.ingotWroughtIron),
					});
	    	
	    	MineFantasyAPI.addSpecialSmelt(new ItemStack(ItemListMF.ingotSteel), 2, new Object[]{
	    		ItemHandler.carbon.get(a),
				ItemHandler.carbon.get(a),
				ItemHandler.carbon.get(a),
				com(ItemListMF.ingotWroughtIron),
	    	});
    	}
    	MineFantasyAPI.addSpecialSmelt(com(ItemListMF.coke, 2), 1, com(ItemListMF.coalPowder));
    	MineFantasyAPI.addCrushRecipe(Item.coal.itemID, com(ItemListMF.coalPowder));
    	
    	for(int a = 0; a < ItemHandler.flux.size(); a ++)
    	{
    		MineFantasyAPI.addRatioAlloy(5, com(ItemListMF.ingotGoldPure), 0, new Object[]
					{
				Item.ingotGold,
				Item.ingotGold,
				Item.ingotGold,
				Item.ingotGold,
				ItemHandler.flux.get(a),
					});
    		
	    	MineFantasyAPI.addRatioAlloy(5, com(ItemListMF.ingotIgnotumite), 1, new Object[]
					{
				com(ItemListMF.IgnotImpure),
				ItemHandler.flux.get(a),
				ItemHandler.flux.get(a),
				ItemHandler.flux.get(a),
				ItemHandler.flux.get(a),
					});
	    	
	    	//1:4:1 Mithril:Silver:Flux
	    	MineFantasyAPI.addRatioAlloy(5, com(ItemListMF.ingotMithril, 4), 1, new Object[]
					{
				com(ItemListMF.mithOre),
				ItemListMF.ingotSilver,
				ItemListMF.ingotSilver,
				ItemListMF.ingotSilver,
				ItemListMF.ingotSilver,
				ItemHandler.flux.get(a),
					});
	    	
	    	GameRegistry.addRecipe(new ItemStack(ItemListMF.boltMF, 8, 0), new Object[]{
	            "H",
	            "F",
	            
	            'H', Item.flint,
	            'F', Item.feather,
	            });
	    	
	    	GameRegistry.addRecipe(com(ItemListMF.slag), new Object[]{
	            "SS",
	            "SS",
	            
	            'S', com(ItemListMF.slagSmall),
	            });
	    	
	    	for(int b = 0; b < ItemHandler.carbon.size(); b ++)
	    	{
		    	MineFantasyAPI.addRatioAlloy(5, com(ItemListMF.ingotDragon), 1, new Object[]
						{
					com(ItemListMF.ingotDragonImpure),
					ItemHandler.flux.get(a),
					ItemHandler.flux.get(a),
					ItemHandler.flux.get(a),
					ItemHandler.carbon.get(b),
						});
	    	}
    	}
    	
    	for(ItemStack ore : tin)
    	{
    		GameRegistry.addRecipe(new ItemStack(Item.bucketEmpty), new Object[]{
    		    "I I",
    		    " I ",
    		    'I', ore,
    		    });
    		
    	}
    	for(ItemStack bronze : OreDictionary.getOres("ingotBronze"))
    	{
    		GameRegistry.addRecipe(new ItemStack(BlockListMF.anvil, 1, 1), new Object[]{
	            " II",
	            "III",
	            " L ",
	            'L', Block.cobblestone,
	            'I', bronze,});
    		
    		GameRegistry.addRecipe(new ItemStack(BlockListMF.chimney, 8, 4), new Object[]{
                "I I",
                "I I",
                "I I",
                'I', bronze,});
    	}
    	for(ItemStack deepIron: OreDictionary.getOres("ingotDeepIron"))
    	{
    		GameRegistry.addRecipe(new ItemStack(BlockListMF.chimney, 12, 5), new Object[]{
                "I I",
                "I I",
                "I I",
                'I', deepIron,});
    		GameRegistry.addRecipe(new ItemStack(BlockListMF.anvil, 1, 7), new Object[]{
	            " II",
	            "III",
	            " L ",
	            'L', Block.cobblestone,
	            'I', deepIron,});
    	}
    	for(ItemStack mithril : OreDictionary.getOres("ingotMithril"))
    	{
    		
    		GameRegistry.addRecipe(new ItemStack(BlockListMF.chimney, 8, 4), new Object[]{
                "I I",
                "I I",
                "I I",
                'I', mithril,});
    	}
    	
    	//IRON ORE
    	if(cfg.easyIron || MineFantasyBase.isDebug())
    	{
    		addEasyIron();
    	}
    	else
    	{
    		addIron();
    	}
    	
    	//SMELT
    	MineFantasyAPI.addBlastRecipe(new ItemStack(BlockListMF.oreMythic.blockID, 1, 1), com(ItemListMF.ingotDeepIron));
    	MineFantasyAPI.addBlastRecipe(new ItemStack(BlockListMF.oreMythic.blockID, 1, 2), com(ItemListMF.ingotDeepIron));
    	
    	MineFantasyAPI.addBlastRecipe(new ItemStack(BlockListMF.oreMythic.blockID, 1, 0), com(ItemListMF.mithOre));
    	MineFantasyAPI.addBlastRecipe(new ItemStack(ItemListMF.misc, 1, ItemListMF.ignotDust), new ItemStack(ItemListMF.misc, 1, ItemListMF.IgnotImpure));
    	
    	FurnaceRecipes.smelting().addSmelting(ItemListMF.misc.itemID, ItemListMF.shardCopper, new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotCopper), 0.5F);
    	
    	GameRegistry.addSmelting(ItemListMF.muttonRaw.itemID, new ItemStack(ItemListMF.muttonCooked), 0.1F);
    	GameRegistry.addSmelting(ItemListMF.drakeRaw.itemID, new ItemStack(ItemListMF.drakeCooked), 0.3F);
    	GameRegistry.addSmelting(ItemListMF.basiliskRaw.itemID, new ItemStack(ItemListMF.basiliskCooked), 0.2F);
    	GameRegistry.addSmelting(BlockListMF.oreUtil.blockID, new ItemStack(ItemListMF.ingotSilver), 1.0F);
    	GameRegistry.addSmelting(BlockListMF.oreTin.blockID, com(ItemListMF.ingotTin), 0.6F);
    	GameRegistry.addSmelting(BlockListMF.oreCopper.blockID, com(ItemListMF.ingotCopper), 0.5F);
        GameRegistry.addSmelting(BlockListMF.oreIgnotumite.blockID, com(ItemListMF.hunkIgnotumite), 20F);
        
        //SLABS
        GameRegistry.addRecipe(new ItemStack(BlockListMF.woodSingleSlab, 6, 0), new Object[]{
            "HHH",
            'H', new ItemStack(BlockListMF.planks, 0, 0),
        });
        GameRegistry.addRecipe(new ItemStack(BlockListMF.woodSingleSlab, 6, 1), new Object[]{
            "HHH",
            'H', new ItemStack(BlockListMF.planks, 0, 1),
        });
        GameRegistry.addRecipe(new ItemStack(BlockListMF.woodSingleSlab, 6, 2), new Object[]{
            "HHH",
            'H', BlockListMF.rePlanks,
        });
        GameRegistry.addRecipe(new ItemStack(BlockListMF.woodSingleSlab, 6, 3), new Object[]{
            "HHH",
            "HHH",
            'H', Item.wheat,
        });
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.stoneSingleSlab, 6, 0), new Object[]{
            "HHH",
            'H', new ItemStack(BlockListMF.cobbBrick, 1, 0),
        });
        GameRegistry.addRecipe(new ItemStack(BlockListMF.stoneSingleSlab, 6, 1), new Object[]{
            "HHH",
            'H', BlockListMF.granite,
        });
        GameRegistry.addRecipe(new ItemStack(BlockListMF.stoneSingleSlab, 6, 2), new Object[]{
            "HHH",
            'H', new ItemStack(BlockListMF.graniteBrick, 0, 0),
        });
        GameRegistry.addRecipe(new ItemStack(BlockListMF.stoneSingleSlab, 2, 3), new Object[]{
            "H",
        	"H",
            'H', new ItemStack(Block.stoneSingleSlab, 0, 0),
        });
        GameRegistry.addRecipe(new ItemStack(BlockListMF.stoneSingleSlab, 6, 4), new Object[]{
            "HHH",
            'H', new ItemStack(BlockListMF.mudBrick, 1, 0),
        });
        GameRegistry.addRecipe(new ItemStack(BlockListMF.stoneSingleSlab, 6, 5), new Object[]{
            "HHH",
            'H', new ItemStack(BlockListMF.cobbBrick, 1, 3),
        });
        GameRegistry.addRecipe(new ItemStack(BlockListMF.stoneSingleSlab, 6, 6), new Object[]{
            "HHH",
            'H', new ItemStack(BlockListMF.mudBrick, 1, 1),
        });
        
        GameRegistry.addRecipe(new ItemStack(Item.minecartPowered), new Object[]{
            "F",
            "M",
            'M', Item.minecartEmpty,
            'F', new ItemStack(BlockListMF.furnace, 1, 0),
        });
        
        for(int a = 0; a < BlockSlate.amount; a ++)
        {
        	GameRegistry.addRecipe(new ItemStack(BlockListMF.slateSingleSlab, 6, a), new Object[]{
                "HHH",
                'H', new ItemStack(BlockListMF.slate, 1, a),
            });
        }
        GameRegistry.addRecipe(new ItemStack(BlockListMF.slateStairs, 4), new Object[]{
            "S  ",
            "SS ",
            "SSS",
            'S', new ItemStack(BlockListMF.slate, 1, 0),});
        GameRegistry.addRecipe(new ItemStack(BlockListMF.slateStairsTile, 4), new Object[]{
            "S  ",
            "SS ",
            "SSS",
            'S', new ItemStack(BlockListMF.slate, 1, 1),});
        GameRegistry.addRecipe(new ItemStack(BlockListMF.slateDStairsTile, 4), new Object[]{
            "S  ",
            "SS ",
            "SSS",
            'S', new ItemStack(BlockListMF.slate, 1, 2),});
        GameRegistry.addRecipe(new ItemStack(BlockListMF.slateStairsBrick, 4), new Object[]{
            "S  ",
            "SS ",
            "SSS",
            'S', new ItemStack(BlockListMF.slate, 1, 3),});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.dogbowl), new Object[]{
                    "PBP",
                    " P ",
                    'P', ItemListMF.plank,
                    'B', Item.bowlEmpty,
        });
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.smelter), new Object[]{
            " S ",
            "S S",
            "SSS",
            'S', Block.cobblestone,
        });
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.smelter, 1, 1), new Object[]{
            "S S",
            "S S",
            " S ",
            'S', Block.cobblestone,
        });
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.smelter, 1, 2), new Object[]{
            "S S",
            "S S",
            " S ",
            'S', BlockListMF.granite,
        });
        
        
        GameRegistry.addRecipe(com(ItemListMF.shardCopper), new Object[]{
            "W",
            'W', BlockListMF.oreCopper,
        });
        
        GameRegistry.addRecipe(new ItemStack(Block.planks), new Object[]{
            "WW",
            "WW",
            'W', ItemListMF.plank,
        });
        GameRegistry.addRecipe(new ItemStack(Item.stick, 4), new Object[]{
            "W",
            "W",
            'W', ItemListMF.plank,
        });
        
        
        GameRegistry.addShapelessRecipe(com(ItemListMF.infernoCoal), new Object[]{
            Item.coal, Item.blazePowder
       });
        
       GameRegistry.addShapelessRecipe(new ItemStack(ItemListMF.transferHound), new Object[]
       {
            Item.paper, Item.feather, new ItemStack(Item.dyePowder, 1, 0), Item.bone
       });
        
        GameRegistry.addRecipe(com(ItemListMF.HellCoal, 8), new Object[]{
            "CCC",
            "CGC",
            "CCC",
            'C', new ItemStack(Item.coal, 1, 0),
            'G', com(ItemListMF.fireGland),});
        
        GameRegistry.addRecipe(com(ItemListMF.HellCoal, 6), new Object[]{
            "CCC",
            "CGC",
            "CCC",
            'C', new ItemStack(Item.coal, 1, 1),
            'G', com(ItemListMF.fireGland),});
        
        GameRegistry.addShapelessRecipe(com(ItemListMF.longCoal), new Object[]{
            Item.coal, Block.netherrack
       });
        
        
        
        
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.chimney, 8, 0), new Object[]{
            "I I",
            "I I",
            "I I",
            'I', com(ItemListMF.ingotWroughtIron),});
        
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.chimney, 8, 2), new Object[]{
            "I I",
            "I I",
            "I I",
            'I', BlockListMF.cobbBrick,});
        
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.chimney, 8, 3), new Object[]{
            "I I",
            "I I",
            "I I",
            'I', Block.brick,});
        
        GameRegistry.addRecipe(new RecipeBookClone());
        //GameRegistry.addRecipe(new RecipeSchematicClone());
        GameRegistry.addRecipe(new ItemStack(ItemListMF.doorIronbark), new Object[]{
            "HH",
            "HH",
            "HH",
            'H', new ItemStack(BlockListMF.planks, 1, 0),
        });
        
        GameRegistry.addRecipe(new ItemStack(ItemListMF.doorHard), new Object[]{
            "HH",
            "HH",
            "HH",
            'H', BlockListMF.rePlanks,
        });
        GameRegistry.addRecipe(new ItemStack(ItemListMF.doorSteel), new Object[]{
            "HH",
            "HH",
            "HH",
            'H', ItemListMF.ingotSteel,
        });
        
        for(ItemStack lime : OreDictionary.getOres("blockLimestone"))
        {
		    GameRegistry.addRecipe(com(ItemListMF.limestoneHunk, 4), new Object[]{
		        "L",
		        'L', lime
		    });
        }
        GameRegistry.addRecipe(new ItemStack(BlockListMF.limestone), new Object[]{
            "LL",
            "LL",
            'L', com(ItemListMF.limestoneHunk),
        });
        
        GameRegistry.addRecipe(new ItemStack(ItemListMF.misc, 8, ItemListMF.featherArrow), new Object[]{
                    " S ",
                    " P ",
                    "P P",
                    'P', Item.paper,
                    'S', Item.stick,
        });
        
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.bellows), new Object[]{
            "WWP",
            "LL ",
            "WW ",
            'W', Block.planks,
            'P', ItemListMF.plank,
            'L', Item.leather,
        });

        GameRegistry.addRecipe(new ItemStack(BlockListMF.anvil, 1, 0), new Object[]{
            " CC",
            "CCC",
            " C ",
            'C', Block.cobblestone,});
        
        	GameRegistry.addRecipe(new ItemStack(BlockListMF.anvil, 1, 3), new Object[]{
	            " II",
	            "III",
	            " L ",
	            'L', Block.cobblestone,
	            'I', com(ItemListMF.ingotWroughtIron),});
        	
        	GameRegistry.addRecipe(new ItemStack(Block.anvil, 1, 0), new Object[]{
	            "III",
	            " I ",
	            "III",
	            'I', com(ItemListMF.ingotWroughtIron),});
	        
        for(ItemStack steelI : steel)
        {
        	GameRegistry.addRecipe(new ItemStack(BlockListMF.anvil, 1, 5), new Object[]{
	            " II",
	            "III",
	            " L ",
	            'L', Block.cobblestone,
	            'I', steelI,});
        }
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.lantern, 1), new Object[]{
                    "SPS",
                    "GTG",
                    "SPS",
                    'S', Item.stick,
                    'G', Block.thinGlass,
                    'T', Block.torchWood,
                    'P', Block.planks,});

        GameRegistry.addRecipe(new ItemStack(BlockListMF.forge, 1), new Object[]{
                    "S S",
                    "SCS",
                    "CCC",
                    'C', Item.coal,
                    'S', Block.stoneBrick,});
        GameRegistry.addRecipe(new ItemStack(BlockListMF.forge, 1, 1), new Object[]{
            "S S",
            "SCS",
            "CCC",
            'C', Item.coal,
            'S', BlockListMF.cobbBrick,});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.forge, 1), new Object[]{
            "S S",
            "SCS",
            "CCC",
            'C', new ItemStack(Item.coal, 1, 2),
            'S', Block.stoneBrick,});
		GameRegistry.addRecipe(new ItemStack(BlockListMF.forge, 1, 1), new Object[]{
		    "S S",
		    "SCS",
		    "CCC",
		    'C',  new ItemStack(Item.coal, 1, 1),
		    'S', BlockListMF.cobbBrick,});
		
		
        GameRegistry.addRecipe(new ItemStack(ItemListMF.plank), new Object[]{
                    "P",
                    "P",
                    'P', Item.stick,});
        
        GameRegistry.addRecipe(com(ItemListMF.plankIronbark), new Object[]{
            "P",
            "P",
            'P', com(ItemListMF.stickIronbark),});
        GameRegistry.addRecipe(com(ItemListMF.plankEbony), new Object[]{
            "P",
            "P",
            'P', com(ItemListMF.stickEbony),});
        
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.graniteBrick, 4), new Object[]{
                    "PP",
                    "PP",
                    'P', BlockListMF.granite,});
        
        addCobbleConversion(BlockListMF.cobbBrick);
        addCobbleConversion(BlockListMF.cobbBrick, Block.cobblestone);
        
        addMudbrickConversion(BlockListMF.mudBrick);
        addMudbrickConversion(BlockListMF.mudBrick, Block.dirt);
        
        addSlateConversion(BlockListMF.slate);
        addSlateConversion(BlockListMF.slateDoubleSlab);
        addSlateConversion(BlockListMF.slateSingleSlab);
        
        Block[] stairConvert = new Block[]{BlockListMF.slateStairs, BlockListMF.slateStairsTile, BlockListMF.slateDStairsTile, BlockListMF.slateStairsBrick};
        for(Block block: stairConvert)
        {
        	GameRegistry.addRecipe(new ItemStack(BlockListMF.slateStairsBrick, 4), new Object[]{
                "PP",
                "PP",
                'P', block,});
        	GameRegistry.addRecipe(new ItemStack(BlockListMF.slateDStairsTile, 4), new Object[]{
                " P ",
                "P P",
                " P ",
                'P', block,});
        	GameRegistry.addRecipe(new ItemStack(BlockListMF.slateStairsTile, 4), new Object[]{
                "P P",
                "   ",
                "P P",
                'P', block,});
        	GameRegistry.addRecipe(new ItemStack(BlockListMF.slateStairs), new Object[]{
                "P",
                'P', block,});
        }
		
        GameRegistry.addRecipe(new ItemStack(BlockListMF.rePlanks, 6), new Object[]{
                    "SIS",
                    "SIS",
                    "SIS",
                    'I', Item.ingotIron,
                    'S', Block.planks,});

        GameRegistry.addRecipe(new ItemStack(BlockListMF.clayWall, 4), new Object[]{
                    " P ",
                    "PCP",
                    " P ",
                    'C', Block.blockClay,
                    'P', ItemListMF.plank,});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.clayWall, 1, 1), new Object[]{
            "P P",
            " C ",
            "P P",
            'C', BlockListMF.clayWall,
            'P', ItemListMF.plank,});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.clayWall, 1, 2), new Object[]{
            "  P",
            " C ",
            "PP ",
            'C', BlockListMF.clayWall,
            'P', ItemListMF.plank,});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.clayWall, 1, 3), new Object[]{
            "PP ",
            " C ",
            "  P",
            'C', BlockListMF.clayWall,
            'P', ItemListMF.plank,});

        GameRegistry.addShapelessRecipe(new ItemStack(ItemListMF.misc, 8, ItemListMF.fireExplosive), new Object[]{
                    new ItemStack(ItemListMF.misc, 1, ItemListMF.fireGland), Item.gunpowder
                });
        GameRegistry.addShapelessRecipe(new ItemStack(ItemListMF.misc, 1, ItemListMF.fireExplosive), new Object[]{
            Item.blazePowder, Item.gunpowder
        });
        
        GameRegistry.addShapelessRecipe(new ItemStack(ItemListMF.explosive, 4), new Object[]{
            com(ItemListMF.coalPowder), com(ItemListMF.sulfur), com(ItemListMF.nitre)
       });

        GameRegistry.addRecipe(new ItemStack(Item.gunpowder), new Object[]
        {
            "BB",
            "BB",
            'B', ItemListMF.explosive,
         });

        GameRegistry.addRecipe(new ItemStack(BlockListMF.planks, 4, 0), new Object[]{
                    "I",
                    'I', new ItemStack(BlockListMF.log, 1, 0),});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.planks, 4, 1), new Object[]{
            "I",
            'I', new ItemStack(BlockListMF.log, 1, 1),});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.planks, 1, 0), new Object[]{
            "PP",
            "PP",
            'P', com(ItemListMF.plankIronbark),});
        GameRegistry.addRecipe(new ItemStack(BlockListMF.planks, 1, 1), new Object[]{
            "PP",
            "PP",
            'P', com(ItemListMF.plankEbony),});
        
        GameRegistry.addRecipe(new ItemStack(Block.workbench), new Object[]{
                    "II",
                    "II",
                    'I', BlockListMF.planks,});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.foodPrep), new Object[]{
            "III",
            'I', ItemListMF.plank,});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.hayRoof, 4), new Object[]{
            "  I",
            " II",
            "III",
            'I', Item.wheat,});
        GameRegistry.addRecipe(new ItemStack(BlockListMF.hayRoof, 4), new Object[]{
            "I  ",
            "II ",
            "III",
            'I', Item.wheat,});

        GameRegistry.addRecipe(com(ItemListMF.stickIronbark, 4), new Object[]{
            "I",
            "I",
            'I', com(ItemListMF.plankIronbark),});
        GameRegistry.addRecipe(com(ItemListMF.stickEbony, 4), new Object[]{
            "I",
            "I",
            'I', com(ItemListMF.plankEbony),});
        
        GameRegistry.addRecipe(com(ItemListMF.plankIronbark, 8), new Object[]
		{
            "I",
            "I",
            'I', new ItemStack(BlockListMF.planks, 1, 0)});
        
        
		GameRegistry.addRecipe(com(ItemListMF.plankEbony, 8), new Object[]
				{
		    "I",
		    "I",
		    'I', new ItemStack(BlockListMF.planks, 1, 1)});

        GameRegistry.addRecipe(new ItemStack(Item.bowlEmpty, 4), new Object[]{
                    "I I",
                    " I ",
                    'I', BlockListMF.planks,});
        GameRegistry.addRecipe(new ItemStack(Block.pressurePlatePlanks), new Object[]{
                    "II",
                    'I', BlockListMF.planks,});
        GameRegistry.addRecipe(new ItemStack(Item.doorWood), new Object[]{
                    "II",
                    "II",
                    "II",
                    'I', BlockListMF.planks,});
        GameRegistry.addRecipe(new ItemStack(Block.trapdoor, 2), new Object[]{
                    "III",
                    "III",
                    'I', BlockListMF.planks,});
        GameRegistry.addRecipe(new ItemStack(Block.chest, 1), new Object[]{
                    "III",
                    "I I",
                    "III",
                    'I', BlockListMF.planks,});
        GameRegistry.addRecipe(new ItemStack(Item.boat, 1), new Object[]{
                    "I I",
                    "III",
                    'I', BlockListMF.planks,});
        GameRegistry.addRecipe(new ItemStack(Item.sign, 1), new Object[]{
                    "III",
                    "III",
                    " V ",
                    'I', BlockListMF.planks,
                    'V', Item.stick,});


        
       /**
        ItemStack fur = getItem("drzhark.mocreatures.MoCreatures", "fur", 0);
        ItemStack hide = getItem("drzhark.mocreatures.MoCTools", "hide", 0);
        ItemStack reptile = getItem("drzhark.mocreatures.MoCTools", "reptilehide", 0);
        
        if(fur != null)
        GameRegistry.addShapelessRecipe(new ItemStack(ItemListMF.misc, 1, ItemListMF.MoCFurSalt), new Object[]{
            fur, new ItemStack(ItemListMF.misc, 1, ItemListMF.salt)
        });
        if(hide != null)
        GameRegistry.addShapelessRecipe(new ItemStack(ItemListMF.misc, 1, ItemListMF.MoCHideSalt), new Object[]{
            hide, new ItemStack(ItemListMF.misc, 1, ItemListMF.salt)
        });
        if(reptile != null)
        GameRegistry.addShapelessRecipe(new ItemStack(ItemListMF.misc, 1, ItemListMF.MoCReptileSalt), new Object[]{
            reptile, new ItemStack(ItemListMF.misc, 1, ItemListMF.salt)
        });
        */
        
        
        GameRegistry.addShapelessRecipe(new ItemStack(ItemListMF.misc, 1, ItemListMF.leatherSalt), new Object[]{
            new ItemStack(ItemListMF.misc, 1, ItemListMF.leatherRaw), new ItemStack(ItemListMF.misc, 1, ItemListMF.flux)
        });
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.tanner), new Object[]{
                    "PPP",
                    "P P",
                    "WWW",
                    'W', Block.planks,
                    'P', ItemListMF.plank,});
        GameRegistry.addRecipe(new ItemStack(BlockListMF.roast), new Object[]{
            "SSS",
            "P P",
            'S', Item.stick,
            'P', ItemListMF.plank,});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.tanner), new Object[]{
            "PPP",
            "P P",
            "WWW",
            'W', BlockListMF.planks,
            'P', ItemListMF.plank,});
        
        GameRegistry.addRecipe(new ItemStack(BlockListMF.weaponRack), new Object[]{
            "P P",
            "PPP",
            "P P",
            'P', ItemListMF.plank,});
        GameRegistry.addRecipe(new ItemStack(BlockListMF.stairSmoothstone, 4), new Object[]{
            "S  ",
            "SS ",
            "SSS",
            'S', Block.stone,});
        GameRegistry.addRecipe(new ItemStack(BlockListMF.stairCobbBrick, 4), new Object[]{
            "S  ",
            "SS ",
            "SSS",
            'S', new ItemStack(BlockListMF.cobbBrick, 1, 0),});
        GameRegistry.addRecipe(new ItemStack(BlockListMF.stairCobbBrickRough, 4), new Object[]{
            "S  ",
            "SS ",
            "SSS",
            'S', new ItemStack(BlockListMF.cobbBrick, 1, 3),});
        
    }
    
	private static void addSalt(String type)
	{
		ArrayList<ItemStack> salts = OreDictionary.getOres(type);
		if(salts != null && !salts.isEmpty())
		{
			for(ItemStack salt : salts)
	    	{
	    		GameRegistry.addShapelessRecipe(new ItemStack(ItemListMF.misc, 1, ItemListMF.leatherSalt), new Object[]{
	                new ItemStack(ItemListMF.misc, 1, ItemListMF.leatherRaw), salt
	            });
	    		
	    		GameRegistry.addShapelessRecipe(com(ItemListMF.flux), new Object[]{salt, salt});
	    	}
		}
	}

	private static void addIron() 
	{
		MineFantasyAPI.addCrushRecipe(new ItemStack(Item.ingotIron), com(ItemListMF.ingotWroughtIron));
		MineFantasyAPI.addBlastRecipe(Block.oreIron.blockID, new ItemStack(Item.ingotIron));
		MineFantasyAPI.addBloomRecipe(new ItemStack(Block.oreIron), com(ItemListMF.ingotWroughtIron), 800);
	}
	private static void addEasyIron() 
	{
		MineFantasyAPI.addBloomRecipe(new ItemStack(Block.oreIron), com(ItemListMF.ingotWroughtIron), 500);
		MineFantasyAPI.addSpecialSmelt(com(ItemListMF.ingotWroughtIron), -1, new ItemStack(Block.oreIron));
		MineFantasyAPI.addRatioAlloy(8, new ItemStack(Item.ingotIron), 0, new Object[]
		{
    		com(ItemListMF.ingotWroughtIron),
    		com(ItemListMF.flux)
		});
	}
	private static void addSlateConversion(Block block)
	{
		GameRegistry.addRecipe(new ItemStack(block, 4, 3), new Object[]{
            "PP",
            "PP",
            'P', block,});
        GameRegistry.addRecipe(new ItemStack(block, 4, 2), new Object[]{
            " P ",
            "P P",
            " P ",
            'P', block,});
        GameRegistry.addRecipe(new ItemStack(block, 4, 1), new Object[]{
            "P P",
            "   ",
            "P P",
            'P', block,});
        GameRegistry.addRecipe(new ItemStack(block, 1), new Object[]{
            "P",
            'P', block,});
	}
	
	private static void addCobbleConversion(Block block) 
	{
		addCobbleConversion(block, block);
	}
	private static void addCobbleConversion(Block block, Block block2) 
	{
		GameRegistry.addRecipe(new ItemStack(block, 4, 3), new Object[]{
            "PP",
            "PP",
            'P', block2,});
        GameRegistry.addRecipe(new ItemStack(block, 4, 0), new Object[]{
            "P P",
            "   ",
            "P P",
            'P', block2,});
        
        if(block2 != Block.cobblestone)
        {
	        GameRegistry.addRecipe(new ItemStack(Block.cobblestone), new Object[]{
	            "P",
	            'P', block2,});
        }
	}
	private static void addMudbrickConversion(Block block) 
	{
		addMudbrickConversion(block, block);
	}
	private static void addMudbrickConversion(Block block, Block block2) 
	{
		GameRegistry.addRecipe(new ItemStack(block, 4, 1), new Object[]{
            "PP",
            "PP",
            'P', block2,});
        GameRegistry.addRecipe(new ItemStack(block, 4, 0), new Object[]{
            "P P",
            "   ",
            "P P",
            'P', block2,});
        
        if(block2 != Block.dirt)
        {
	        GameRegistry.addRecipe(new ItemStack(Block.dirt), new Object[]{
	            "P",
	            'P', block2,});
		}
	}
	
	private static void addIngotAlternatives() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(Item.book), new Object[]{
			Item.paper, Item.paper, Item.paper, com(ItemListMF.leatherRough)
		});
		GameRegistry.addRecipe(new ItemStack(Block.dispenser), new Object[]{
            "CCC",
            "CBC",
            "CRC",
            'C', Block.cobblestone,
            'B', ItemListMF.shortbow,
            'R', Item.redstone,
            });
		
		GameRegistry.addRecipe(new ItemStack(Item.cauldron), new Object[]{
            "I I",
            "I I",
            "III",
            'I', com(ItemListMF.ingotWroughtIron),
            });
		GameRegistry.addRecipe(new ItemStack(Item.minecartEmpty), new Object[]{
            "I I",
            "III",
            'I', com(ItemListMF.ingotWroughtIron),
            });
		
		GameRegistry.addRecipe(new ItemStack(Block.rail, 32), new Object[]{
            "I I",
            "ISI",
            "I I",
            'S', Item.stick,
            'I', ItemListMF.ingotSteel,
            });
		
		GameRegistry.addShapelessRecipe(new ItemStack(Item.nameTag), new Object[]
			{
			Item.silk, Item.paper
            });
		
		
		GameRegistry.addRecipe(new ItemStack(Block.railPowered, 64), new Object[]{
            "I I",
            "ISI",
            "IRI",
            'R', Item.redstone,
            'S', Item.stick,
            'I', com(ItemListMF.ingotGoldPure),
            });
		GameRegistry.addRecipe(new ItemStack(Item.appleGold, 1, 1), new Object[]{
            "III",
            "IAI",
            "III",
            'A', Item.appleRed,
            'I', com(ItemListMF.ingotGoldPure),
            });
	}
	private static void addHaftTiers() 
	{
		Object[] glueMats = new Object[]{Item.rottenFlesh, Item.fishRaw, Item.leather, com(ItemListMF.leatherRough), com(ItemListMF.tendon)};
		for(int a = 0; a < ItemHandler.flux.size(); a ++)
		{
			for(Object mat: glueMats)
			{
				MineFantasyAPI.addRatioAlloy(8, com(ItemListMF.glueWeak), 0, new Object[]
				{
					ItemHandler.flux.get(a),
					mat,
				});
			}
		}
		MineFantasyAPI.addAlloy(com(ItemListMF.glueStrong, 4), 1, new Object[]
		{
			com(ItemListMF.glueWeak),com(ItemListMF.glueWeak),com(ItemListMF.glueWeak),com(ItemListMF.glueWeak),
			Item.slimeBall,
		});
		
		GameRegistry.addShapelessRecipe(com(ItemListMF.glueStrong, 4), new Object[]{
            com(ItemListMF.glueWeak),com(ItemListMF.glueWeak),com(ItemListMF.glueWeak),com(ItemListMF.glueWeak), Item.slimeBall
		});
		
		MineFantasyAPI.addTailorRecipe(com(ItemListMF.haft, 1), 2, 2F, 1, new Object[]{
            "L",
            "P",
            'L', com(ItemListMF.leatherStrip),
            'P', ItemListMF.plank,
		});
		
		MineFantasyAPI.addTailorRecipe(com(ItemListMF.haftStrong, 1), 2, 4F, 1, new Object[]{
            "L",
            "G",
            "P",
            'W', Block.cloth,
            'L', com(ItemListMF.leatherStrip),
            'G', com(ItemListMF.glueWeak),
            'P', ItemListMF.plank,
		});
		
		
		MineFantasyAPI.addTailorRecipe(com(ItemListMF.haftIronbark, 1), 4, 5F, 1, new Object[]{
			" L ", 
			"GWG", 
			" P ",
			'W', Block.cloth,
            'L', com(ItemListMF.leatherStrip),
            'G', com(ItemListMF.glueStrong),
            'P', com(ItemListMF.plankIronbark),});
		
		
			MineFantasyAPI.addTailorRecipe(com(ItemListMF.haftEbony, 1), 4, 10F, 1, new Object[]{
			" L ", 
			"GWG", 
			" P ",
			'W', Block.cloth,
            'L', com(ItemListMF.leatherStrip),
            'G', com(ItemListMF.glueStrong),
            'P', com(ItemListMF.plankEbony),});
		
			MineFantasyAPI.addTailorRecipe(com(ItemListMF.haftOrnate, 1), 4, 10F, 1, new Object[]{
				" L ", 
				"GWG", 
				"IPI",
				'W', Block.cloth,
	            'L', com(ItemListMF.leatherStrip),
	            'G', com(ItemListMF.glueWeak),
	            'I', Item.ingotGold, 
	            'P', com(ItemListMF.plankEbony),});
		
	}
	private static void addCopper(ItemStack copper) 
	{
		addBlock(new ItemStack(BlockListMF.storage, 1, 1), copper);
	}
	private static ItemStack block(int i) {
		return new ItemStack(BlockListMF.storage, 1, i);
	}
	private static void addBlock(ItemStack block, ItemStack item) {
		GameRegistry.addRecipe(block, new Object[]{
            "III",
            "III",
            "III",
            'I', item,
		});
		
		
		GameRegistry.addRecipe(new ItemStack(item.itemID, 9, item.getItemDamage()), new Object[]{
	            "I",
	            'I', block,
			});
		
	}
	public static void addSteel(ItemStack ore) {
		
		//DRAGON STEEL Gland:Gold:Steel 1:1:1
		MineFantasyAPI.addRatioAlloy(1, com(ItemListMF.ingotDragonImpure, 1), 1, new Object[]
				{
			ore,
			com(ItemListMF.fireGland),
			Item.ingotGold,
				});
		
		GameRegistry.addRecipe(new ItemStack(BlockListMF.chimney, 8, 1), new Object[]{
            "I I",
            "I I",
            "I I",
            'I', ore,});
		
		GameRegistry.addRecipe(new ItemStack(Item.flintAndSteel), new Object[]{
            "A ",
            " B",
            'A', ore,
            'B', Item.flint});
    }
	
	public static void addFinalRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(Item.bed), new Object[]{
            "C  ",
            "CCC",
            "WWW",
            'C', com(ItemListMF.padding),
            'W', ItemListMF.plank,
            });
		
		for(ItemStack plank : OreDictionary.getOres("plankWood"))
		{
			GameRegistry.addRecipe(new ItemStack(ItemListMF.plank, 8), new Object[]{
	            "W",
	            "W",
	            'W', plank,
			});
		}
	}
	private static void addLeatherRecipes()
	{
    	TanningRecipes.instance().addTanning(ItemListMF.misc.itemID, ItemListMF.rawHide, com(ItemListMF.leatherRaw, 4));
    	TanningRecipes.instance().addTanning(ItemListMF.misc.itemID, ItemListMF.hideHound, com(ItemListMF.leatherRaw, 2));
    	TanningRecipes.instance().addTanning(ItemListMF.misc.itemID, ItemListMF.hideMinotaur, com(ItemListMF.leatherRaw, 8));
    	TanningRecipes.instance().addTanning(ItemListMF.misc.itemID, ItemListMF.hideDrake, com(ItemListMF.leatherRaw, 8));
    	
    	TanningRecipes.instance().addTanning(ItemListMF.misc.itemID, ItemListMF.hidePig, com(ItemListMF.leatherRaw, 1));
    	TanningRecipes.instance().addTanning(ItemListMF.misc.itemID, ItemListMF.hideSheep, com(ItemListMF.leatherRaw, 3));
    	TanningRecipes.instance().addTanning(ItemListMF.misc.itemID, ItemListMF.hideHorse, com(ItemListMF.leatherRaw, 5));
    	
    	TanningRecipes.instance().addTanning(ItemListMF.misc.itemID, ItemListMF.hideBasiliskBlue, com(ItemListMF.leatherRaw, 12));
    	TanningRecipes.instance().addTanning(ItemListMF.misc.itemID, ItemListMF.hideBasiliskBrown, com(ItemListMF.leatherRaw, 16));
    	TanningRecipes.instance().addTanning(ItemListMF.misc.itemID, ItemListMF.hideBasiliskBlack, com(ItemListMF.leatherRaw, 24));
    	
    	GameRegistry.addShapelessRecipe(com(ItemListMF.leatherGore), new Object[]{com(ItemListMF.leatherRough), Item.rottenFlesh});
    	MineFantasyAPI.addFoodPrep(com(ItemListMF.leatherGore), new ItemStack(Item.leather), 2.5F, "mallet", data_minefantasy.sound("mallet_use"));
    	
        LeathercuttingRecipes.instance().addCutting(Item.leather.itemID, new ItemStack(ItemListMF.misc, 8, ItemListMF.leatherStrip));
        LeathercuttingRecipes.instance().addCutting(com(ItemListMF.leatherRough), new ItemStack(ItemListMF.misc, 4, ItemListMF.leatherStrip));
	}
	
	private static void addPrimitive()
	{
		GameRegistry.addRecipe(com(ItemListMF.rock), new Object[]{
            "D",
            'D', Block.dirt
		});
		
		GameRegistry.addRecipe(new ItemStack(ItemListMF.rocks), new Object[]{
            "AR",
            "RA",
            'R', com(ItemListMF.rock),
		});
		
		GameRegistry.addRecipe(new ItemStack(BlockListMF.firepit), new Object[]{
			"P",
			'P', ItemListMF.plank,
		});
		
		ItemStack[] rocks = new ItemStack[]{new ItemStack(Item.flint), com(ItemListMF.rock)};
		ItemStack[] binds = new ItemStack[]{new ItemStack(Item.silk), com(ItemListMF.vine), com(ItemListMF.tendon)};
		ItemStack[] hides = new ItemStack[]{com(ItemListMF.leatherRaw), com(ItemListMF.rawHide), com(ItemListMF.hidePig), com(ItemListMF.hideSheep), com(ItemListMF.hideDrake), com(ItemListMF.hideMinotaur), com(ItemListMF.hideHound)};
		
		for(ItemStack rock: rocks)
		{
			for(ItemStack bind: binds)
			{
				GameRegistry.addRecipe(new ItemStack(ItemListMF.spearStone), new Object[]{
		            "ATR",
		            "AST",
		            "SAA",
		            'S', Item.stick,
		            'T', bind,
		            'R', rock
				});
				
				if(cfg.hardcoreCraft)
				{
					GameRegistry.addRecipe(new ItemStack(ItemListMF.hammerStone), new Object[]{
			            "TRT",
			            " S ",
			            'S', ItemListMF.plank,
			            'T', bind,
			            'R', Block.cobblestone
					});
					
					GameRegistry.addRecipe(new ItemStack(ItemListMF.knifeStone), new Object[]{
			            "TR",
			            "ST",
			            'S', Item.stick,
			            'T', bind,
			            'R', rock
					});
					
					GameRegistry.addRecipe(new ItemStack(ItemListMF.tongsStone), new Object[]{
			            "RT",
			            "SR",
			            'S', Item.stick,
			            'T', bind,
			            'R', rock
					});
				}
				else
				{
					GameRegistry.addRecipe(new ItemStack(ItemListMF.hammerStone), new Object[]{
			            "R",
			            "S",
			            'S', ItemListMF.plank,
			            'R', Block.cobblestone
					});
					
					GameRegistry.addRecipe(new ItemStack(ItemListMF.tongsStone), new Object[]{
			            "R ",
			            "SR",
			            'S', Item.stick,
			            'R', rock
					});
					
					GameRegistry.addRecipe(new ItemStack(ItemListMF.knifeStone), new Object[]{
			            " R",
			            "S ",
			            'S', Item.stick,
			            'R', rock
					});
				}
				
				GameRegistry.addRecipe(new ItemStack(ItemListMF.axePrim), new Object[]{
		            "AAT",
		            "ATR",
		            "ASA",
		            'S', Item.stick,
		            'T', bind,
		            'R', rock
				});
				
				GameRegistry.addRecipe(new ItemStack(ItemListMF.pickStonePrim), new Object[]{
		            "RTT",
		            "ASA",
		            "ASA",
		            'S', Item.stick,
		            'T', bind,
		            'R', rock
				});
				
				GameRegistry.addRecipe(new ItemStack(ItemListMF.javelin), new Object[]{
		            "R",
		            "T",
		            "S",
		            'S', Item.stick,
		            'T', bind,
		            'R', rock
				});
			}
		}
		
		
		
			for(ItemStack bind: binds)
			{
				for(ItemStack hide : hides)
				{
					GameRegistry.addRecipe(new ItemStack(ItemListMF.sling), new Object[]{
			            "H  ",
			            " B ",
			            "  B",
			            'H', hide,
			            'B', bind,
					});
					
					GameRegistry.addRecipe(new ItemStack(ItemListMF.bandage, 4), new Object[]{
			            "B",
			            "H",
			            "B",
			            'H', hide,
			            'B', bind,
					});
					
					GameRegistry.addRecipe(new ItemStack(ItemListMF.armourRawhide), new Object[]{
			            "H H",
			            "BHB",
			            'H', hide,
			            'B', bind,
					});
					GameRegistry.addRecipe(new ItemStack(ItemListMF.legsRawhide), new Object[]{
			            "BB",
						"HH",
			            'H', hide,
			            'B', bind,
					});
					
					GameRegistry.addRecipe(new ItemStack(ItemListMF.spearCopper), new Object[]{
			            "ATR",
			            "AST",
			            "SAA",
			            'S', Item.stick,
			            'T', bind,
			            'R', com(ItemListMF.shardCopper)
					});
					
					GameRegistry.addRecipe(new ItemStack(ItemListMF.pickCopperPrim), new Object[]{
			            "RTT",
			            "ASA",
			            "ASA",
			            'S', Item.stick,
			            'T', bind,
			            'R', com(ItemListMF.shardCopper)
					});
					
					for(ItemStack planks: OreDictionary.getOres("plankWood"))
					{
						GameRegistry.addRecipe(new ItemStack(ItemListMF.clubWood), new Object[]{
				            "TRT",
				            "ATA",
				            "ASA",
				            'S', Item.stick,
				            'R', planks,
				            'T', bind,
						});
					}
					GameRegistry.addRecipe(new ItemStack(ItemListMF.clubStone), new Object[]{
			            "TRT",
			            "ATA",
			            "ASA",
			            'S', Item.stick,
			            'R', Block.cobblestone,
			            'T', bind,
					});
				}
			}
	}
	
	
	
	private static ItemStack com(int id)
	{
		return ItemListMF.component(id);
	}
	private static ItemStack com(int id, int val)
	{
		return ItemListMF.component(id, val);
	}
	
	
	private static void addHeatables()
	{
		MineFantasyAPI.addHeatableItem(com(ItemListMF.ingotIgnotumite), 950, 3000, 5000);
		MineFantasyAPI.addHeatableItem(com(ItemListMF.ingotDragon), 900, 3000, 5000);
		MineFantasyAPI.addHeatableItem(com(ItemListMF.ingotWroughtIron), 400, 1200, 1500);
		MineFantasyAPI.addHeatableItem(com(ItemListMF.lumpIron), 200, 1200, 1500);
		MineFantasyAPI.addHeatableItem(com(ItemListMF.lumpSteel), 250, 1500, 1800);
		MineFantasyAPI.addHeatableItem(com(ItemListMF.lumpBronze), 150, 600, 800);
		MineFantasyAPI.addHeatableItem(com(ItemListMF.lumpMithril), 1250, 3200, 3500);
		MineFantasyAPI.addHeatableItem(Item.ingotGold.itemID, 250, 900, 1100);
		
		if(!cfg.easyIron)
		{
			MineFantasyAPI.addHeatableItem(Item.ingotIron.itemID, 350, 1000, 1200);
		}
		
		addHeatable("ingotSteel", 400, 900, 1200);
		addHeatable("ingotDamascusSteel", 450, 1000, 1250);
		addHeatable("ingotCopper", 300, 600, 700);
		addHeatable("ingotRefinedIron", 350, 800, 1100);
		addHeatable("ingotNickel", 500, 1000, 1300);
		addHeatable("ingotPlatinum", 500, 1100, 1400);
		addHeatable("ingotSilver", 200, 750, 900);
		addHeatable("ingotTin", 100, 350, 450);
		addHeatable("ingotTitanium", 700, 1200, 1600);
		addHeatable("ingotZinc", 200, 300, 450);
		addHeatable("ingotBronze", 300, 600, 800);
		
		addHeatable("ingotDeepIron", 1200, 2400, 3000);
		addHeatable("ingotMithril", 1300, 2800, 3200);
	}
	private static void addHeatable(String oreStr, int min, int unstable, int max)
	{
		for(ItemStack ore: OreDictionary.getOres(oreStr))
			MineFantasyAPI.addHeatableItem(ore, min, unstable, max);
	}
	
	
	
	public static ItemStack getItem(String itemClass, String itemString, int meta) {
		ItemStack item = null;

		try {
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
	
	private static void addTailoring()
	{
		GameRegistry.addRecipe(new ItemStack(ItemListMF.needleBone), new Object[]{
            "B",
            "B",
            'B', Item.bone
		});
		
		StringList.addString(Item.silk, 2);
		StringList.addString(com(ItemListMF.twine), 1);
		StringList.addString(com(ItemListMF.tendon), 0);
		MineFantasyAPI.addStringRecipe(Block.cloth, com(ItemListMF.twine, 4), 10);
		
		GameRegistry.addRecipe(new ItemStack(BlockListMF.spinningWheel), new Object[]{
		    "W P",
		    "WWW",
		    "PPP",
		    
		    'W', Block.planks,
		    'P', ItemListMF.plank,
		    });
		
		GameRegistry.addRecipe(new ItemStack(Item.silk), new Object[]{
		    "SGS",
		    " S ",
		    "SGS",
		    
		    'S', com(ItemListMF.twine),
		    'G', com(ItemListMF.glueWeak),
		    });
		GameRegistry.addRecipe(new ItemStack(Item.silk), new Object[]{
		    "SGS",
		    " S ",
		    "S S",
		    
		    'S', com(ItemListMF.twine),
		    'G', com(ItemListMF.glueStrong),
		    });
		
		GameRegistry.addRecipe(new ItemStack(BlockListMF.tailor), new Object[]{
		    "SSS",
		    "WLW",
		    "P P",
		    'S', Item.stick,
		    'W', Block.planks,
		    'L', Item.leather,
		    'P', ItemListMF.plank,
		    });
		GameRegistry.addRecipe(new ItemStack(BlockListMF.tailor), new Object[]{
		    "SSS",
		    "WLW",
		    "P P",
		    'S', Item.stick,
		    'W', Block.planks,
		    'L', com(ItemListMF.leatherRough),
		    'P', ItemListMF.plank,
		    });
		
		MineFantasyAPI.addTailorRecipe(com(ItemListMF.padding, 4), 4, 4F, 1, new Object[]{
            "LFL",
            " C ",
            "LFL",
            'C', Block.cloth,
            'L', Item.leather,
            'F', Item.feather,
        });
		
		MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.bandage, 4, 1), 4, 2F, 1, new Object[]{
            "W",
            "W",
            'W', Block.cloth,
        });
		MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.bandage, 1, 2), 2, 2F, 2, new Object[]{
            "L",
            "B",
            "L",
            'L', com(ItemListMF.leatherStrip),
            'B', new ItemStack(ItemListMF.bandage, 4, 1),
        });
		
		MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.apronSmithy), 12, 10F, 1, new Object[]{
            "LAL",
            "LWL",
            "ALA",
            'W', Block.cloth,
            'L', Item.leather,
        });
		MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.apronSmithy), 12, 10F, 1, new Object[]{
            "LAL",
            "LWL",
            "ALA",
            'W', Block.cloth,
            'L', com(ItemListMF.leatherRough),
        });
		
		MineFantasyAPI.addTailorRecipe(com(ItemListMF.leatherBelt), 2, 8F, 1, new Object[]{
        	"B",
        	"L",
        	"L",
        	'L', com(ItemListMF.leatherStrip),
        	'B', com(ItemListMF.buckle),
        });
		
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.hound_feed, 1, ItemListMF.hound_feed.getMaxDamage()-1), 8, 14F, 1, new Object[]{
        	"B B",
        	"L L",
        	" L ",
        	'L', Item.leather,
        	'B', com(ItemListMF.leatherBelt),
        });
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.hound_sPack), 12, 16.0F, 1, new Object[]{
        	"LCL",
            "LLL",
            "B B",
            'C', Block.chest,
            'L', Item.leather,
            'B', com(ItemListMF.leatherBelt),
        });
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.hound_bPack), 24, 24.0F, 2, new Object[]{
        	"CLC",
        	"LPL",
        	"BLB",
            "B B",
            'L', Item.leather,
            'C', Block.chest,
            'P', ItemListMF.hound_sPack,
            'B', com(ItemListMF.leatherBelt),
        });
        
        
        //ARMOURS
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.helmetLeatherRough), 5, 10F, 1, new Object[]{
        	"LLL",
        	"L L",
        	'L', com(ItemListMF.leatherRough),
        });
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.plateLeatherRough), 8, 10F, 1, new Object[]{
        	"L L",
        	"LLL",
        	"LLL",
        	'L', com(ItemListMF.leatherRough),
        });
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.legsLeatherRough), 7, 10F, 1, new Object[]{
        	"LLL",
        	"L L",
        	"L L",
        	'L', com(ItemListMF.leatherRough),
        });
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.bootsLeatherRough), 4, 10F, 1, new Object[]{
        	"L L",
        	"L L",
        	'L', com(ItemListMF.leatherRough),
        });
        
        
        
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(Item.helmetLeather), 6, 10F, 1, new Object[]{
        	"LLL",
        	"L L",
        	'L', Item.leather,
        });
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(Item.plateLeather), 16, 10F, 1, new Object[]{
        	"L L",
        	"LLL",
        	"LLL",
        	'L', Item.leather,
        });
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(Item.legsLeather), 12, 10F, 1, new Object[]{
        	"LLL",
        	"L L",
        	"L L",
        	'L', Item.leather,
        });
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(Item.bootsLeather), 4, 10F, 1, new Object[]{
        	"L L",
        	"L L",
        	'L', Item.leather,
        });
        
        
        
        MineFantasyAPI.addTailorRecipe(com(ItemListMF.blackLeather), 2, 4, 8.0F, 2, new Object[]{
            " I ",
            "LBL",
            'L', Item.leather,
            'B', com(ItemListMF.glueStrong),
            'I', new ItemStack(Item.dyePowder, 1, 0),});
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.helmetStealth), 2, 8, 28.0F, 2, new Object[]{
            "LLL",
            "L L",
            'L', com(ItemListMF.blackLeather)});
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.plateStealth), 2, 16, 28.0F, 2, new Object[]{
            "L L",
            "LLL",
            "LLL",
            'L', com(ItemListMF.blackLeather)});
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.legsStealth), 2, 12, 28.0F, 2, new Object[]{
            "LLL",
            "L L",
            "L L",
            'L', com(ItemListMF.blackLeather)});
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(ItemListMF.bootsStealth), 2, 4, 28.0F, 2, new Object[]{
            "L L",
            "L L",
            'L', com(ItemListMF.blackLeather)});
        
        
        MineFantasyAPI.addTailorRecipe(new ItemStack(Item.saddle), 32, 10F, 1, new Object[]{
            "L  ",
            "LLL",
            "B B",
            'B', com(ItemListMF.leatherBelt),
            'L', Item.leather,
        });
        
        ItemStack quiver = MineFantasyBase.getBGItem("quiver", 0);
        if(quiver != null)
        MineFantasyAPI.addTailorRecipe(quiver, 12, 10F, 1, new Object[]{
        	"L L",
        	"L L",
        	"LLL",
        	'L', Item.leather,
        });
	}
	
	private static void addFletching()
	{
		GameRegistry.addRecipe(new ItemStack(ItemListMF.arrowMF, 4, 0), new Object[]{
			"R",
			"R",
			"F",
			
			'R', Item.reed,
			'F', Item.feather,
		    });
		
		addArrow(1, ItemListMF.arrowheadBronze, Item.stick, ItemListMF.featherArrow);
		addArrow(4, ItemListMF.arrowheadIron, Item.stick, ItemListMF.featherArrow);
		addArrow(7, ItemListMF.arrowheadSteel, Item.stick, ItemListMF.featherArrow);
		addArrow(10, ItemListMF.arrowheadMithril, ItemListMF.stickIronbark, ItemListMF.featherArrow);
		addArrow(13, ItemListMF.arrowheadSilver, Item.stick, ItemListMF.featherArrow);
		addArrow(16, ItemListMF.arrowheadEncrusted, Item.stick, ItemListMF.featherArrow);
		addArrow(19, ItemListMF.arrowheadDragonforge, Item.stick, ItemListMF.featherArrow);
		addArrow(22, ItemListMF.arrowheadIgnotumite, ItemListMF.stickEbony, ItemListMF.featherArrow);
		addArrow(25, ItemListMF.arrowheadDeepIron, ItemListMF.stickIronbark, ItemListMF.featherArrow);
		
		
		addArrow(2, ItemListMF.bodkinheadBronze, Item.stick, ItemListMF.featherArrow);
		addArrow(5, ItemListMF.bodkinheadIron, Item.stick, ItemListMF.featherArrow);
		addArrow(8, ItemListMF.bodkinheadSteel, Item.stick, ItemListMF.featherArrow);
		addArrow(11, ItemListMF.bodkinheadMithril, ItemListMF.stickIronbark, ItemListMF.featherArrow);
		addArrow(14, ItemListMF.bodkinheadSilver, Item.stick, ItemListMF.featherArrow);
		addArrow(17, ItemListMF.bodkinheadEncrusted, Item.stick, ItemListMF.featherArrow);
		addArrow(20, ItemListMF.bodkinheadDragonforge, Item.stick, ItemListMF.featherArrow);
		addArrow(23, ItemListMF.bodkinheadIgnotumite, ItemListMF.stickEbony, ItemListMF.featherArrow);
		addArrow(26, ItemListMF.bodkinheadDeepIron, ItemListMF.stickIronbark, ItemListMF.featherArrow);
		
		
		addArrow(3, ItemListMF.broadheadBronze, ItemListMF.plank, ItemListMF.featherArrow);
		addArrow(6, ItemListMF.broadheadIron, ItemListMF.plank, ItemListMF.featherArrow);
		addArrow(9, ItemListMF.broadheadSteel, ItemListMF.plank, ItemListMF.featherArrow);
		addArrow(12, ItemListMF.broadheadMithril, ItemListMF.plankIronbark, ItemListMF.featherArrow);
		addArrow(15, ItemListMF.broadheadSilver, ItemListMF.plank, ItemListMF.featherArrow);
		addArrow(18, ItemListMF.broadheadEncrusted, ItemListMF.plank, ItemListMF.featherArrow);
		addArrow(21, ItemListMF.broadheadDragonforge, ItemListMF.plank, ItemListMF.featherArrow);
		addArrow(24, ItemListMF.broadheadIgnotumite, ItemListMF.plankEbony, ItemListMF.featherArrow);
		addArrow(27, ItemListMF.broadheadDeepIron, ItemListMF.plankIronbark, ItemListMF.featherArrow);
	}
	
	private static void addArrow(int type, int head, Object body, Object feather)
	{
		if(body instanceof Integer)
		{
			int b = (Integer)body;
			body = com(b);
		}
		if(feather instanceof Integer)
		{
			int b = (Integer)feather;
			feather = com(b);
		}
		
		GameRegistry.addRecipe(new ItemStack(ItemListMF.arrowMF, 1, type), new Object[]{
			"H",
			"S",
			"F",
			
			'H', com(head),
			'S', body,
			'F', feather,
		    });
	}
	
	private static void addBombs()
	{
		GameRegistry.addRecipe(new ItemStack(ItemListMF.bombMF, 1, 0), new Object[]{
			" S ",
			"CFC",
			" P ",
			
			'S', Item.silk,
			'C', Item.clay,
			'P', ItemListMF.explosive,
			'F', com(ItemListMF.shrapnel),
		    });
		
		GameRegistry.addRecipe(new ItemStack(ItemListMF.bombMF, 1, 1), new Object[]{
			" S ",
			"CFC",
			" P ",
			
			'S', Item.silk,
			'C', Item.clay,
			'P', ItemListMF.explosive,
			'F', com(ItemListMF.fireExplosive),
		    });
		
		GameRegistry.addRecipe(new ItemStack(ItemListMF.bombMF, 1, 2), new Object[]{
			" S ",
			"CFC",
			" P ",
			
			'S', Item.silk,
			'C', Item.clay,
			'P', ItemListMF.explosive,
			'F', Item.fermentedSpiderEye,
		    });
		
		GameRegistry.addRecipe(new ItemStack(ItemListMF.bombMF, 1, 3), new Object[]{
			" S ",
			"CFC",
			" P ",
			
			'S', Item.silk,
			'C', Item.clay,
			'P', ItemListMF.explosive,
			'F', Item.redstone,
		    });
	}
}
