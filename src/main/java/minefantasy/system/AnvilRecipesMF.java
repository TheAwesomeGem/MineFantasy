package minefantasy.system;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;

import minefantasy.MineFantasyBase;
import minefantasy.api.Components;
import minefantasy.api.MineFantasyAPI;
import minefantasy.api.forge.ItemHandler;
import minefantasy.api.refine.FluxItem;
import minefantasy.block.BlockListMF;
import minefantasy.item.ItemListMF;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AnvilRecipesMF {
	/**
	 * TOOL SHAPES
	 * 1H Blade: 1 haft for hilt
	 * 1H other: 2 haft for handle
	 * 2H weapon: 4 haft for handle
	 * Spear: 5 haft for handle
	 * 
	 * Tool: 2 haft for handle
	 * 
	 * RecipesMF
	 */
	public static void initiate() {
		addArmour();
		addRepair();
		addMisc();
    	MineFantasyAPI.addCrushRecipe(new RecipeBloom());
		addTools();
		addFurnaces();
		addHound();
		addBows();
		addCrossbows();
		
		//TIN
		for(ItemStack ore : OreDictionary.getOres("ingotTin"))
    	{
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.tinderbox), 0 , -1 , 200, new Object[] {
				"WFW",
				" I ",
				'I', ore,
				'W', Item.stick,
				'F', Item.flint,
				});
    	}
				
		//BRONZE
		for(ItemStack ore : OreDictionary.getOres("ingotBronze"))
    	{
    		addBronze(ore);
    	}
		
		//IRON
    	{
    		ItemStack ore = com(ItemListMF.ingotWroughtIron);
    		addIron(com(ItemListMF.ingotWroughtIron));
    	}
    	//STEEL
    	ArrayList<ItemStack> Steel = OreDictionary.getOres("ingotSteel");
    	for(int a = 0; a < Steel.size(); a ++)
    	{
    		addSteel(Steel.get(a));
    	}
    	
    	
    	//SILVER
    	for(ItemStack silver : OreDictionary.getOres("ingotSilver"))
    	{
    		addSilver(silver);
    	}
    	
    	//DEEP
    	for(ItemStack deep : OreDictionary.getOres("ingotDeepIron"))
    	{
    		addDeepIron(deep);
    	}
    	//MITHRIL
    	for(ItemStack mith : OreDictionary.getOres("ingotMithril"))
    	{
    		addMithril(mith);
    	}
		
    	addDragonforge(com(ItemListMF.ingotDragon));
    	MineFantasyAPI.addCraftableFlux(com(ItemListMF.limestoneHunk), 4);
    	MineFantasyAPI.addCraftableFlux(new ItemStack(Item.netherQuartz), 8);
    	for(int a = 0; a < ItemHandler.fluxes.size() ; a ++)
    	{
    		FluxItem flux = ItemHandler.fluxes.get(a);
    		MineFantasyAPI.addCrushRecipe(flux.fluxItem, com(ItemListMF.flux, flux.fluxOut));
    	}
    	MineFantasyAPI.addCrushRecipe(com(ItemListMF.hunkIgnotumite), com(ItemListMF.ignotDust));
    	MineFantasyAPI.addCrushRecipe(new ItemStack(Item.flint), com(ItemListMF.shrapnel));
		
	}



	private static ItemStack com(int id) {
		return com(id, 1);
	}
	private static ItemStack com(int id, int num)
	{
		return new ItemStack(ItemListMF.misc, num, id);
	}







	private static void addArmour() 
	{
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.chainIron),0,1 , ironTime, new Object[] {
			"CC",
			"CC",
			'C', com(ItemListMF.linkIron),
			});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.chainGuilded),0,1 , steelTime, new Object[] {
			"CC",
			"CC",
			'C', com(ItemListMF.linkGuilded),
			});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.chainSteel),0,2 , steelTime, new Object[] {
			"CC",
			"CC",
			'C', com(ItemListMF.linkSteel),
			});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.chainDeepIron),0,3 , deepIronTime, new Object[] {
			"CC",
			"CC",
			'C', com(ItemListMF.linkDeepIron),
			});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.chainMithril),0,3 , mithrilTime, new Object[] {
			"CC",
			"CC",
			'C', com(ItemListMF.linkMithril),
			});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.chainDragon),1,2 , dragonforgeTime, new Object[] {
			"CC",
			"CC",
			'C', com(ItemListMF.linkDragonforge),
			});
		
	////BRONZE CHAIN////
	MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetBronzeChain), 0, 0, bronzeTime*2,  new Object[]{
        "CCC",
        "C C",
        'C', com(ItemListMF.chainBronze),});
	MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateBronzeChain), 0, 0, bronzeTime*4,  new Object[]{
        "C C",
        "CCC",
        "CCC",
        'C', com(ItemListMF.chainBronze),});
	MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsBronzeChain), 0, 0, bronzeTime*3,  new Object[]{
        "CCC",
        "C C",
        "C C",
        'C', com(ItemListMF.chainBronze),});
	MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsBronzeChain), 0, 0, bronzeTime*1,  new Object[]{
        "C C",
        "C C",
        'C', com(ItemListMF.chainBronze),});
			
			
		////IRON CHAIN////
		MineFantasyAPI.addAnvilRecipe(new ItemStack(Item.helmetChain), 0, 1, ironTime*2,  new Object[]{
            "CCC",
            "C C",
            'C', com(ItemListMF.chainIron),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(Item.plateChain), 0, 1, ironTime*4,  new Object[]{
            "C C",
            "CCC",
            "CCC",
            'C', com(ItemListMF.chainIron),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(Item.legsChain), 0, 1, ironTime*3,  new Object[]{
            "CCC",
            "C C",
            "C C",
            'C', com(ItemListMF.chainIron),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(Item.bootsChain), 0, 1, ironTime*1,  new Object[]{
            "C C",
            "C C",
            'C', com(ItemListMF.chainIron),});
		
			////GUILDED CHAIN////
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetGuildedChain), 0, 1, ironTime*2,  new Object[]{
	            "CCC",
	            "C C",
	            'C', com(ItemListMF.chainGuilded),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateGuildedChain), 0, 1, ironTime*4,  new Object[]{
	            "C C",
	            "CCC",
	            "CCC",
	            'C', com(ItemListMF.chainGuilded),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsGuildedChain), 0, 1, ironTime*3,  new Object[]{
	            "CCC",
	            "C C",
	            "C C",
	            'C', com(ItemListMF.chainGuilded),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsGuildedChain), 0, 1, ironTime*1,  new Object[]{
	            "C C",
	            "C C",
	            'C', com(ItemListMF.chainGuilded),});
				
		
			////STEEL CHAIN////
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetSteelChain), 0, 2, steelTime*2,  new Object[]{
	            "CCC",
	            "C C",
	            'C', com(ItemListMF.chainSteel),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateSteelChain), 0, 2, steelTime*4,  new Object[]{
	            "C C",
	            "CCC",
	            "CCC",
	            'C', com(ItemListMF.chainSteel),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsSteelChain), 0, 2, steelTime*3,  new Object[]{
	            "CCC",
	            "C C",
	            "C C",
	            'C', com(ItemListMF.chainSteel),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsSteelChain), 0, 2, steelTime*1,  new Object[]{
	            "C C",
	            "C C",
	            'C', com(ItemListMF.chainSteel),});
			
			
		////DEEP IRON CHAIN////
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetDeepIronChain), 0, 3, deepIronTime*2,  new Object[]{
            "CCC",
            "C C",
            'C', com(ItemListMF.chainDeepIron),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateDeepIronChain), 0, 3, deepIronTime*4,  new Object[]{
            "C C",
            "CCC",
            "CCC",
            'C', com(ItemListMF.chainDeepIron),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsDeepIronChain), 0, 3, deepIronTime*3,  new Object[]{
            "CCC",
            "C C",
            "C C",
            'C', com(ItemListMF.chainDeepIron),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsDeepIronChain), 0, 3, deepIronTime*1,  new Object[]{
            "C C",
            "C C",
            'C', com(ItemListMF.chainDeepIron),});
					
			
			////MITHRIL CHAIN////
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetMithrilChain), 0, 3, mithrilTime*2,  new Object[]{
	            "CCC",
	            "C C",
	            'C', com(ItemListMF.chainMithril),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateMithrilChain), 0, 3, mithrilTime*4,  new Object[]{
	            "C C",
	            "CCC",
	            "CCC",
	            'C', com(ItemListMF.chainMithril),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsMithrilChain), 0, 3, mithrilTime*3,  new Object[]{
	            "CCC",
	            "C C",
	            "C C",
	            'C', com(ItemListMF.chainMithril),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsMithrilChain), 0, 3, mithrilTime*1,  new Object[]{
	            "C C",
	            "C C",
	            'C', com(ItemListMF.chainMithril),});
					
			
			
			
			////DRAGONFORGE CHAIN////
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetDragonChain), 1, 2, dragonforgeTime*2,  new Object[]{
	            "CCC",
	            "C C",
	            'C', com(ItemListMF.chainDragon),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateDragonChain), 1, 2, dragonforgeTime*4,  new Object[]{
	            "C C",
	            "CCC",
	            "CCC",
	            'C', com(ItemListMF.chainDragon),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsDragonChain), 1, 2, dragonforgeTime*3,  new Object[]{
	            "CCC",
	            "C C",
	            "C C",
	            'C', com(ItemListMF.chainDragon),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsDragonChain), 1, 2, dragonforgeTime*1,  new Object[]{
	            "C C",
	            "C C",
	            'C', com(ItemListMF.chainDragon),});
					
					
					
					
		
		
		
		////BRONZE SCALE////
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateBronzeScale), 0, 0, (int)(bronzeTime*0.75*2),  new Object[]{
            "AL LA",
            "ALLLA",
            "ALLLA",
            "AAAAA",
            'L', Item.leather,
            'A', com(ItemListMF.scaleBronze),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsBronzeScale), 0, 0, (int)(bronzeTime*0.75*4), new Object[]{
            " AAA ",
			"AIIIA",
            "AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleBronze),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsBronzeScale), 0, 0, (int)(bronzeTime*0.75*3), new Object[]{
            "A   A",
        	"AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleBronze),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetBronzeScale), 0, 0, (int)(bronzeTime*0.75*1), new Object[]{
            "A   A",
        	"AIIIA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleBronze),});
        
        
        ////IRON SCALE////
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateIronScale), 0, 1, (int)(ironTime*0.75*2),  new Object[]{
            "AL LA",
            "ALLLA",
            "ALLLA",
            "AAAAA",
            'L', Item.leather,
            'A', com(ItemListMF.scaleIron),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsIronScale), 0, 1, (int)(ironTime*0.75*4), new Object[]{
            " AAA ",
			"AIIIA",
            "AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleIron),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsIronScale), 0, 1, (int)(ironTime*0.75*3), new Object[]{
            "A   A",
        	"AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleIron),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetIronScale), 0, 1, (int)(ironTime*0.75*1), new Object[]{
            "A   A",
        	"AIIIA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleIron),});
        
       
        ////GUILDED SCALE////
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateGuildedScale), 0, 1, (int)(ironTime*0.75*2),  new Object[]{
            "AL LA",
            "ALLLA",
            "ALLLA",
            "AAAAA",
            'L', Item.leather,
            'A', com(ItemListMF.scaleGuilded),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsGuildedScale), 0, 1, (int)(ironTime*0.75*4), new Object[]{
            " AAA ",
			"AIIIA",
            "AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleGuilded),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsGuildedScale), 0, 1, (int)(ironTime*0.75*3), new Object[]{
            "A   A",
        	"AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleGuilded),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetGuildedScale), 0, 1, (int)(ironTime*0.75*1), new Object[]{
            "A   A",
        	"AIIIA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleGuilded),});
        
        
        ////STEEL SCALE////
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateSteelScale), 0, 2, (int)(steelTime*0.75*2),  new Object[]{
            "AL LA",
            "ALLLA",
            "ALLLA",
            "AAAAA",
            'L', Item.leather,
            'A', com(ItemListMF.scaleSteel),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsSteelScale), 0, 2, (int)(steelTime*0.75*4), new Object[]{
            " AAA ",
			"AIIIA",
            "AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleSteel),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsSteelScale), 0, 2, (int)(steelTime*0.75*3), new Object[]{
            "A   A",
        	"AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleSteel),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetSteelScale), 0, 2, (int)(steelTime*0.75*1), new Object[]{
            "A   A",
        	"AIIIA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleSteel),});
        
    ////DEEP IRON SCALE////
    MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateDeepIronScale), 0, 3, (int)(deepIronTime*0.75*2),  new Object[]{
        "AL LA",
        "ALLLA",
        "ALLLA",
        "AAAAA",
        'L', Item.leather,
        'A', com(ItemListMF.scaleDeepIron),});
	MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsDeepIronScale), 0, 3, (int)(deepIronTime*0.75*4), new Object[]{
        " AAA ",
		"AIIIA",
        "AI IA",
        "AI IA",
        'I', Item.leather,
        'A', com(ItemListMF.scaleDeepIron),});
    MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsDeepIronScale), 0, 3, (int)(deepIronTime*0.75*3), new Object[]{
        "A   A",
    	"AI IA",
        "AI IA",
        'I', Item.leather,
        'A', com(ItemListMF.scaleDeepIron),});
    MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetDeepIronScale), 0, 3, (int)(deepIronTime*0.75*1), new Object[]{
        "A   A",
    	"AIIIA",
        "AI IA",
        'I', Item.leather,
        'A', com(ItemListMF.scaleDeepIron),});
        
        
        ////MITHRIL SCALE////
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateMithrilScale), 0, 3, (int)(mithrilTime*0.75*2),  new Object[]{
            "AL LA",
            "ALLLA",
            "ALLLA",
            "AAAAA",
            'L', Item.leather,
            'A', com(ItemListMF.scaleMithril),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsMithrilScale), 0, 3, (int)(mithrilTime*0.75*4), new Object[]{
            " AAA ",
			"AIIIA",
            "AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleMithril),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsMithrilScale), 0, 3, (int)(mithrilTime*0.75*3), new Object[]{
            "A   A",
        	"AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleMithril),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetMithrilScale), 0, 3, (int)(mithrilTime*0.75*1), new Object[]{
            "A   A",
        	"AIIIA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleMithril),});
        
        
        ////DRAGONFORGE SCALE////
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateDragonScale), 1, 2, (int)(dragonforgeTime*0.75*2),  new Object[]{
            "AL LA",
            "ALLLA",
            "ALLLA",
            "AAAAA",
            'L', Item.leather,
            'A', com(ItemListMF.scaleDragonforge),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsDragonScale), 1, 2, (int)(dragonforgeTime*0.75*4), new Object[]{
            " AAA ",
			"AIIIA",
            "AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleDragonforge),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsDragonScale), 1, 2, (int)(dragonforgeTime*0.75*3), new Object[]{
            "A   A",
        	"AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleDragonforge),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetDragonScale), 1, 2, (int)(dragonforgeTime*0.75*1), new Object[]{
            "A   A",
        	"AIIIA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.scaleDragonforge),});
        
        
        
        
        
        
        
        ////BRONZE SPLINT////
    		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateBronzeSplint), 0, 0, (int)(bronzeTime*1.2F*2),  new Object[]{
                "AL LA",
                "ALLLA",
                "ALLLA",
                "AAAAA",
                'L', Item.leather,
                'A', com(ItemListMF.splintBronze),});
    		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsBronzeSplint), 0, 0, (int)(bronzeTime*1.2F*4), new Object[]{
                " AAA ",
    			"AIIIA",
                "AI IA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintBronze),});
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsBronzeSplint), 0, 0, (int)(bronzeTime*1.2F*3), new Object[]{
                "A   A",
            	"AI IA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintBronze),});
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetBronzeSplint), 0, 0, (int)(bronzeTime*1.2F*1), new Object[]{
                "A   A",
            	"AIIIA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintBronze),});
            
            
            ////IRON SPLINT////
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateIronSplint), 0, 1, (int)(ironTime*1.2F*2),  new Object[]{
                "AL LA",
                "ALLLA",
                "ALLLA",
                "AAAAA",
                'L', Item.leather,
                'A', com(ItemListMF.splintIron),});
    		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsIronSplint), 0, 1, (int)(ironTime*1.2F*4), new Object[]{
                " AAA ",
    			"AIIIA",
                "AI IA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintIron),});
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsIronSplint), 0, 1, (int)(ironTime*1.2F*3), new Object[]{
                "A   A",
            	"AI IA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintIron),});
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetIronSplint), 0, 1, (int)(ironTime*1.2F*1), new Object[]{
                "A   A",
            	"AIIIA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintIron),});
            
            
            
	        ////GUILDED SPLINT////
	        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateGuildedSplint), 0, 1, (int)(ironTime*1.2F*2),  new Object[]{
	            "AL LA",
	            "ALLLA",
	            "ALLLA",
	            "AAAAA",
	            'L', Item.leather,
	            'A', com(ItemListMF.splintGuilded),});
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsGuildedSplint), 0, 1, (int)(ironTime*1.2F*4), new Object[]{
	            " AAA ",
				"AIIIA",
	            "AI IA",
	            "AI IA",
	            'I', Item.leather,
	            'A', com(ItemListMF.splintGuilded),});
	        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsGuildedSplint), 0, 1, (int)(ironTime*1.2F*3), new Object[]{
	            "A   A",
	        	"AI IA",
	            "AI IA",
	            'I', Item.leather,
	            'A', com(ItemListMF.splintGuilded),});
	        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetGuildedSplint), 0, 1, (int)(ironTime*1.2F*1), new Object[]{
	            "A   A",
	        	"AIIIA",
	            "AI IA",
	            'I', Item.leather,
	            'A', com(ItemListMF.splintGuilded),});
            
            
            
            ////STEEL SPLINT////
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateSteelSplint), 0, 2, (int)(steelTime*1.2F*2),  new Object[]{
                "AL LA",
                "ALLLA",
                "ALLLA",
                "AAAAA",
                'L', Item.leather,
                'A', com(ItemListMF.splintSteel),});
    		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsSteelSplint), 0, 2, (int)(steelTime*1.2F*4), new Object[]{
                " AAA ",
    			"AIIIA",
                "AI IA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintSteel),});
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsSteelSplint), 0, 2, (int)(steelTime*1.2F*3), new Object[]{
                "A   A",
            	"AI IA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintSteel),});
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetSteelSplint), 0, 2, (int)(steelTime*1.2F*1), new Object[]{
                "A   A",
            	"AIIIA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintSteel),});
            
            
            
        ////DEEP IRON SPLINT////
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateDeepIronSplint), 0, 3, (int)(deepIronTime*1.2F*2),  new Object[]{
            "AL LA",
            "ALLLA",
            "ALLLA",
            "AAAAA",
            'L', Item.leather,
            'A', com(ItemListMF.splintDeepIron),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsDeepIronSplint), 0, 3, (int)(deepIronTime*1.2F*4), new Object[]{
            " AAA ",
			"AIIIA",
            "AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.splintDeepIron),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsDeepIronSplint), 0, 3, (int)(deepIronTime*1.2F*3), new Object[]{
            "A   A",
        	"AI IA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.splintDeepIron),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetDeepIronSplint), 0, 3, (int)(deepIronTime*1.2F*1), new Object[]{
            "A   A",
        	"AIIIA",
            "AI IA",
            'I', Item.leather,
            'A', com(ItemListMF.splintDeepIron),});
            
            
            ////MITHRIL SPLINT////
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateMithrilSplint), 0, 3, (int)(mithrilTime*1.2F*2),  new Object[]{
                "AL LA",
                "ALLLA",
                "ALLLA",
                "AAAAA",
                'L', Item.leather,
                'A', com(ItemListMF.splintMithril),});
    		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsMithrilSplint), 0, 3, (int)(mithrilTime*1.2F*4), new Object[]{
                " AAA ",
    			"AIIIA",
                "AI IA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintMithril),});
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsMithrilSplint), 0, 3, (int)(mithrilTime*1.2F*3), new Object[]{
                "A   A",
            	"AI IA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintMithril),});
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetMithrilSplint), 0, 3, (int)(mithrilTime*1.2F*1), new Object[]{
                "A   A",
            	"AIIIA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintMithril),});
            
            
            ////DRAGONFORGE SPLINT////
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateDragonSplint), 1, 2, (int)(dragonforgeTime*1.2F*2),  new Object[]{
                "AL LA",
                "ALLLA",
                "ALLLA",
                "AAAAA",
                'L', Item.leather,
                'A', com(ItemListMF.splintDragon),});
    		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsDragonSplint), 1, 2, (int)(dragonforgeTime*1.2F*4), new Object[]{
                " AAA ",
    			"AIIIA",
                "AI IA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintDragon),});
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsDragonSplint), 1, 2, (int)(dragonforgeTime*1.2F*3), new Object[]{
                "A   A",
            	"AI IA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintDragon),});
            MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetDragonSplint), 1, 2, (int)(dragonforgeTime*1.2F*1), new Object[]{
                "A   A",
            	"AIIIA",
                "AI IA",
                'I', Item.leather,
                'A', com(ItemListMF.splintDragon),});
            
            
        
      //BRONZE HVY CHAIN
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateBronzeHvyChain), 0, 0, (int)(bronzeTime*1.5F*2),  new Object[]{
            "AL LA",
            "ALLLA",
            " LLL ",
            'L', com(ItemListMF.chainBronze),
            'A', com(ItemListMF.platingBronze),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsBronzeHvyChain), 0, 0, (int)(bronzeTime*1.5F*4), new Object[]{
			"AIIIA",
            "AI IA",
            " I I ",
            'I', com(ItemListMF.chainBronze),
            'A', com(ItemListMF.platingBronze),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsBronzeHvyChain), 0, 0, (int)(bronzeTime*1.5F*3), new Object[]{
        	"AI IA",
            "AI IA",
            'I', com(ItemListMF.chainBronze),
            'A', com(ItemListMF.platingBronze),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetBronzeHvyChain), 0, 0, (int)(bronzeTime*1.5F*1), new Object[]{
        	"AIIIA",
            "AI IA",
            'I', com(ItemListMF.chainBronze),
            'A', com(ItemListMF.platingBronze),});
		
		
		
		//IRON HVY CHAIN
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateIronHvyChain), 0, 1, (int)(ironTime*1.5F*2),  new Object[]{
            "AL LA",
            "ALLLA",
            " LLL ",
            'L', com(ItemListMF.chainIron),
            'A', com(ItemListMF.platingIron),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsIronHvyChain), 0, 1, (int)(ironTime*1.5F*4), new Object[]{
			"AIIIA",
            "AI IA",
            " I I ",
            'I', com(ItemListMF.chainIron),
            'A', com(ItemListMF.platingIron),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsIronHvyChain), 0, 1, (int)(ironTime*1.5F*3), new Object[]{
        	"AI IA",
            "AI IA",
            'I', com(ItemListMF.chainIron),
            'A', com(ItemListMF.platingIron),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetIronHvyChain), 0, 1, (int)(ironTime*1.5F*1), new Object[]{
        	"AIIIA",
            "AI IA",
            'I', com(ItemListMF.chainIron),
            'A', com(ItemListMF.platingIron),});
		
		
		
		//STEEL HVY CHAIN
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateSteelHvyChain), 0, 2, (int)(steelTime*1.5F*2),  new Object[]{
            "AL LA",
            "ALLLA",
            " LLL ",
            'L', com(ItemListMF.chainSteel),
            'A', com(ItemListMF.platingSteel),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsSteelHvyChain), 0, 2, (int)(steelTime*1.5F*4), new Object[]{
			"AIIIA",
            "AI IA",
            " I I ",
            'I', com(ItemListMF.chainSteel),
            'A', com(ItemListMF.platingSteel),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsSteelHvyChain), 0, 2, (int)(steelTime*1.5F*3), new Object[]{
        	"AI IA",
            "AI IA",
            'I', com(ItemListMF.chainSteel),
            'A', com(ItemListMF.platingSteel),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetSteelHvyChain), 0, 2, (int)(steelTime*1.5F*1), new Object[]{
        	"AIIIA",
            "AI IA",
            'I', com(ItemListMF.chainSteel),
            'A', com(ItemListMF.platingSteel),});
		
		
		
		//GUILDED HVY CHAIN
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateGuildedHvyChain), 0, 1, (int)(ironTime*1.5F*2),  new Object[]{
            "AL LA",
            "ALLLA",
            " LLL ",
            'L', com(ItemListMF.chainGuilded),
            'A', com(ItemListMF.platingSilver),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsGuildedHvyChain), 0, 1, (int)(ironTime*1.5F*4), new Object[]{
			"AIIIA",
            "AI IA",
            " I I ",
            'I', com(ItemListMF.chainGuilded),
            'A', com(ItemListMF.platingSilver),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsGuildedHvyChain), 0, 1, (int)(ironTime*1.5F*3), new Object[]{
        	"AI IA",
            "AI IA",
            'I', com(ItemListMF.chainGuilded),
            'A', com(ItemListMF.platingSilver),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetGuildedHvyChain), 0, 1, (int)(ironTime*1.5F*1), new Object[]{
        	"AIIIA",
            "AI IA",
            'I', com(ItemListMF.chainGuilded),
            'A', com(ItemListMF.platingSilver),});
		
		
		
      //DEEP IRON HVY CHAIN
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateDeepIronHvyChain), 0, 3, (int)(deepIronTime*1.5F*2),  new Object[]{
            "AL LA",
            "ALLLA",
            " LLL ",
            'L', com(ItemListMF.chainDeepIron),
            'A', com(ItemListMF.platingDeepIron),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsDeepIronHvyChain), 0, 3, (int)(deepIronTime*1.5F*4), new Object[]{
			"AIIIA",
            "AI IA",
            " I I ",
            'I', com(ItemListMF.chainDeepIron),
            'A', com(ItemListMF.platingDeepIron),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsDeepIronHvyChain), 0, 3, (int)(deepIronTime*1.5F*3), new Object[]{
        	"AI IA",
            "AI IA",
            'I', com(ItemListMF.chainDeepIron),
            'A', com(ItemListMF.platingDeepIron),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetDeepIronHvyChain), 0, 3, (int)(deepIronTime*1.5F*1), new Object[]{
        	"AIIIA",
            "AI IA",
            'I', com(ItemListMF.chainDeepIron),
            'A', com(ItemListMF.platingDeepIron),
        });
        
        
		//MITHRIL HVY CHAIN
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateMithrilHvyChain), 0, 3, (int)(mithrilTime*1.5F*2),  new Object[]{
            "AL LA",
            "ALLLA",
            " LLL ",
            'L', com(ItemListMF.chainMithril),
            'A', com(ItemListMF.platingMithril),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsMithrilHvyChain), 0, 3, (int)(mithrilTime*1.5F*4), new Object[]{
			"AIIIA",
            "AI IA",
            " I I ",
            'I', com(ItemListMF.chainMithril),
            'A', com(ItemListMF.platingMithril),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsMithrilHvyChain), 0, 3, (int)(mithrilTime*1.5F*3), new Object[]{
        	"AI IA",
            "AI IA",
            'I', com(ItemListMF.chainMithril),
            'A', com(ItemListMF.platingMithril),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetMithrilHvyChain), 0, 3, (int)(mithrilTime*1.5F*1), new Object[]{
        	"AIIIA",
            "AI IA",
            'I', com(ItemListMF.chainMithril),
            'A', com(ItemListMF.platingMithril),
        });
		
		
		
		//DRAGON HVY CHAIN
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.plateDragonHvyChain), 1, 2, (int)(dragonforgeTime*1.5F*2),  new Object[]{
            "AL LA",
            "ALLLA",
            " LLL ",
            'L', com(ItemListMF.chainDragon),
            'A', com(ItemListMF.platingDragon),});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.legsDragonHvyChain), 1, 2, (int)(dragonforgeTime*1.5F*4), new Object[]{
			"AIIIA",
            "AI IA",
            " I I ",
            'I', com(ItemListMF.chainDragon),
            'A', com(ItemListMF.platingDragon),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bootsDragonHvyChain), 1, 2, (int)(dragonforgeTime*1.5F*3), new Object[]{
        	"AI IA",
            "AI IA",
            'I', com(ItemListMF.chainDragon),
            'A', com(ItemListMF.platingDragon),});
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.helmetDragonHvyChain), 1, 2, (int)(dragonforgeTime*1.5F*1), new Object[]{
        	"AIIIA",
            "AI IA",
            'I', com(ItemListMF.chainDragon),
            'A', com(ItemListMF.platingDragon),});
	}


	
	private static void addSilver(ItemStack silver)
	{
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 16, ItemListMF.scaleGuilded), 0, 1 , 1100, new Object[] {
			"MGM",
			" M ",
			'G', com(ItemListMF.ingotGoldPure),
			'M', silver,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 8, ItemListMF.splintGuilded), 0, 1 , 1200, new Object[] {
			"G",
			"M",
			"M",
			"M",
			'G', com(ItemListMF.ingotGoldPure),
			'M', silver,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 24, ItemListMF.linkGuilded),0,1 , 1100, new Object[] {
			" M ",
			"MGM",
			" M ",
			'G', com(ItemListMF.ingotGoldPure),
			'M', silver,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerOrnate), true, 0, 1,
				1200, new Object[] { 
			"L", 
			"S", 
			"I",
			'I', com(ItemListMF.haftOrnate),
			'S', silver,
			'L', new ItemStack(Item.dyePowder, 1, 4) });
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.maceOrnate), true , 0, 1, 4500, new Object[] {
			"AAMM",
			"PPDM",
			"AAAA",
			'P', haft(5),
			'M', silver,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.maceOrnate), true , 0, 1, 4500, new Object[] {
			"AAAA",
			"PPDM",
			"AAMM",
			'P', haft(5),
			'M', silver,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.warpickOrnate), true , 0, 1, 4500, new Object[] {
			"AAMM",
			"PPDM",
			"AAAM",
			'P', haft(5),
			'M', silver,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.warpickOrnate), true , 0, 1, 4500, new Object[] {
			"AAAM",
			"PPDM",
			"AAMM",
			'P', haft(5),
			'M', silver,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.spearOrnate), true, 0 , 1, 4500, new Object[] {
				"AAAAMA",
				"PPPPPD",
				"AAAAMA",
				'P', haft(5),
				'M', silver,
				'G', Item.ingotGold,
				'D', new ItemStack(Item.dyePowder, 1, 4),
				});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.halbeardOrnate), true, 0 , 1, 5000, new Object[] {
			"AAAAMA",
			"PPPPPD",
			"AAAAMM",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.halbeardOrnate), true, 0 , 1, 5000, new Object[] {
			"AAAAMM",
			"PPPPPD",
			"AAAAMA",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.waraxeOrnate), true, 0 , 1, 4000, new Object[] {
			"AMM",
			"PPD",
			"AAG",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.waraxeOrnate), true, 0 , 1, 4000, new Object[] {
			"AAG",
			"PPD",
			"AMM",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.broadOrnate), true, 0 , 1, 3000, new Object[] {
			"AGMM",
			"PDMM",
			"AGAA",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.broadOrnate), true, 0 , 1, 4000, new Object[] {
			"AGAA",
			"PDMM",
			"AGMM",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.swordOrnate), true, 0 , 1, 4000, new Object[] {
			"AGAA",
			"PDMM",
			"AGAA",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.daggerOrnate), true, 0 , 1, 1500, new Object[] {
			"PDMA",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.lanceOrnate), true, 0 , 1, 12000, new Object[] {
			"  G     ",
			"GGPDMMMM",
			"  G     ",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.greatswordOrnate), true, 0 , 1, 6000, new Object[] {
			" G    ",
			"PDMMMM",
			" G    ",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.greatmaceOrnate), true, 0, 1, 1500, new Object[] {
			"AAAAAAA",
			"GPPPPDM",
			"AAAAAMM",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.greatmaceOrnate), true, 0, 1, 1500, new Object[] {
			"AAAAAMM",
			"GPPPPDM",
			"AAAAAAA",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.warhammerOrnate), true, 0, 1, 2000, new Object[] {
			"AAAAAAM",
			"GPPPPDM",
			"AAAAAMM",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.warhammerOrnate), true, 0, 1, 2000, new Object[] {
			"AAAAAMM",
			"GPPPPDM",
			"AAAAAAM",
			'P', haft(5),
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.battleaxeOrnate), true , 0, 1, 2500, new Object[] {
				"AAAAMM",
				"GPPPPD",
				"AAAAMM",
				'P', haft(5),
				'M', silver,
				'G', Item.ingotGold,
				'D', new ItemStack(Item.dyePowder, 1, 4),
				});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_Oteeth), true , 0, 1, 1500, new Object[] {
			"SB ",
			" GD",
			"SB ",
			'B', com(ItemListMF.leatherBelt),
			'S', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bowOrnate), true , 0, 1, 1250, new Object[] 
		{
			"GSSG",
			"M  M",
			"DLLD",
			'L', Item.leather,
			'S', Item.silk,
			'M', silver,
			'G', Item.ingotGold,
			'D', new ItemStack(Item.dyePowder, 1, 4),
			});
		
		
		if(bg())
		{
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bucklerGuilded), true , 0, 2, ironTime, new Object[] {
				" G ",
				"SLS",
				" S ",
				'L', Item.leather,
				'S', silver,
				'G', com(ItemListMF.ingotGoldPure),
				});
			
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.kiteGuilded), true , 0, 2, ironTime*3, new Object[] {
				"G G",
				"SWS",
				" S ",
				'W', new ItemStack(BlockListMF.planks, 1, 1),
				'S', silver,
				'G', com(ItemListMF.ingotGoldPure),
				});
			
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.towerGuilded), true , 0, 2, ironTime*5, new Object[] {
				"SSS",
				"GWG",
				"GWG",
				"SSS",
				'W', new ItemStack(BlockListMF.planks, 1, 1),
				'S', silver,
				'G', com(ItemListMF.ingotGoldPure),
				});
		}
	}
	
	
	private static void addSteel(ItemStack ore)
	{
		addEncrusted(ore);
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.lumpSteel, 4), false, 0, 2, 400, new Object[] {
			"I",
			'I', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.dogbowl, 1, 2), false, 0, 2, 1000, new Object[] {
			"IBI",
			" I ",
			
			'I', ore,
			'B', Item.bowlEmpty,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepair2), true , 0, 1, 500, new Object[] {
			"PLMM",
			"AAMM",
			'L', Item.leather,
			'P', ItemListMF.plank,
			'M', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepair2), true , 0, 1, 500, new Object[] {
			"AAMM",
			"PLMM",
			'L', Item.leather,
			'P', ItemListMF.plank,
			'M', ore,
			});
		
		for(ItemStack block : OreDictionary.getOres("blockSteel"))
		{
			MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.anvil, 1, 6), true, 0, 1, 1800, new Object[] {
				" BB",
				"III",
				" I ",
				
				'I', ore,
				'B', block,
				});
		}
		
		
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 16, ItemListMF.scaleSteel), 0, 2 , 1600, new Object[] {
			"MMM",
			" M ",
			'M', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 8, ItemListMF.splintSteel), 0, 2 , 1600, new Object[] {
			"M",
			"M",
			"M",
			"M",
			'M', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 24, ItemListMF.linkSteel),0,2 , 1200, new Object[] {
			" M ",
			"M M",
			" M ",
			'M', ore,
			});
	}
	
	private static void addEncrusted(ItemStack steel) 
	{
		//AXE
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.axeEncrusted), true , 0, 2, 2700, new Object[] {
				"ADDA",
				"AMMD",
				"PPMA",
				"AAAA",
				'P', haft(2),
				'M', steel,
				'D', Item.diamond,
				});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.waraxeEncrusted), true ,0, 2, 3600, new Object[] {
			"ADD",
			"PPM",
			"AAM",
			'P', haft(2),
			'D', Item.diamond,
			'M', steel,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.waraxeEncrusted), true ,0, 2, 3600, new Object[] {
			"AAM",
			"PPM",
			"ADD",
			'P', haft(2),
			'D', Item.diamond,
			'M', steel,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.spearEncrusted), true , 0, 2, 2700, new Object[] {
				"AAAAMD",
				"PPPPPM",
				"AAAAMD",
				'P', haft(2),
				'M', steel,
				'D', Item.diamond,
				});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.halbeardEncrusted), true , 0, 2, 3100, new Object[] {
			"AAAAMD",
			"PPPPPM",
			"AAAADD",
			'P', haft(2),
			'M', steel,
			'D', Item.diamond,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.halbeardEncrusted), true , 0, 2, 3100, new Object[] {
			"AAAADD",
			"PPPPPM",
			"AAAAMD",
			'P', haft(2),
			'M', steel,
			'D', Item.diamond,
			});

		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.axeEncrusted), true ,0, 2, 2700, new Object[] {
				"AAAA",
				"PPMA",
				"AMMD",
				"ADDA",
				'P', haft(2),
				'M', steel,
				'D', Item.diamond,
				});
		
		
		//SPADE
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.spadeEncrusted), true ,0, 2, 900, new Object[] {
		"AAAA",
		"PPMD",
		"AAAA",
		'P', haft(2),
		'M', steel,
		'D', Item.diamond,
		});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_Eteeth), true , 0, 2, (int)(encrustedTime*1.5F), new Object[] {
			"DMB",
			"  M",
			"DMB",
			'D', Item.diamond,
			'B', com(ItemListMF.leatherBelt),
			'M', steel,
			});
		
		//PICK
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.pickEncrusted), true ,0, 2, 2700, new Object[] {
				"AAMD",
				"PPMD",
				"AAMD",
				'P', haft(2),
				'M', steel,
				'D', Item.diamond,
				});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.handpickEncrusted), true ,0, 2, 900, new Object[] {
			"AMD",
			"PMD",
			'P', haft(2),
			'M', steel,
			'D', Item.diamond,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.handpickEncrusted), true ,0, 2, 900, new Object[] {
			"PMD",
			"AMD",
			'P', haft(2),
			'M', steel,
			'D', Item.diamond,
			});
		
		//SWORD
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.swordEncrusted), true ,0, 2, 1800, new Object[] {
				"AMAA",
				"PMDD",
				"AMAA",
				'P', haft(2),
				'M', steel,
				'D', Item.diamond,
				});
		
		//BROAD
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.broadEncrusted), true ,0, 2, 2000, new Object[] {
				"AMAA",
				"PMDD",
				"AMDD",
				'P', haft(2),
				'M', steel,
				'D', Item.diamond,
				});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.broadEncrusted), true ,0, 2, 2000, new Object[] {
			"AMDD",
			"PMDD",
			"AMAA",
			'P', haft(2),
			'M', steel,
			'D', Item.diamond,
			});
		//DAGGER
				MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.daggerEncrusted), true ,0, 2, 900, new Object[] {
						"AAAA",
						"PMDA",
						"AAAA",
						'P', haft(2),
						'M', steel,
						'D', Item.diamond,
						});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.greatswordEncrusted), true ,0, 2, 3600, new Object[] {
			" M    ",
			"PMMMDD",
			" M    ",
			'P', haft(2),
			'M', steel,
			'D', Item.diamond,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.lanceEncrusted), true ,0, 2, 7200, new Object[] {
			"  M     ",
			"MMPMMDDD",
			"  M     ",
			'P', haft(2),
			'M', steel,
			'D', Item.diamond,
			});
		
		//WARPICK
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.warpickEncrusted), true ,0, 2, 1350, new Object[] {
				"AAAD",
				"PPMD",
				"AAMM",
				'P', haft(2),
				'M', steel,
				'D', Item.diamond,
				});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.warpickEncrusted), true ,0, 2, 1350, new Object[] {
			"AAMM",
			"PPMD",
			"AAAD",
			'P', haft(2),
			'M', steel,
			'D', Item.diamond,
			});
		
		
		//BATTLEAXE
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.battleaxeEncrusted), true , 0, 2, 5400, new Object[] {
			"AAAADD",
			"MPPPPM",
			"AAAADD",
			'P', haft(2),
			'M', steel,
			'D', Item.diamond,
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.boltMF, 8, 6), false , 0, 2, 2000, new Object[] {
			"D",
			"M",
			"F",
			'D', Item.diamond,
			'F', com(ItemListMF.featherArrow),
			'M', steel,
			});
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.arrowheadEncrusted, 16), false , 0, 2, 800, new Object[] {
			"M  ",
			" MD",
			"M  ",
			'D', Item.diamond,
			'M', steel,
			});
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.bodkinheadEncrusted, 16), false , 0, 2, 800, new Object[] {
			"M   ",
			" MMD",
			"M   ",
			'D', Item.diamond,
			'M', steel,
			});
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.broadheadEncrusted, 16), false , 0, 2, 800, new Object[] {
			"  MD ",
			"MMMMD",
			"  MD ",
			'D', Item.diamond,
			'M', steel,
			});
		
		//MACE
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.maceEncrusted), true , 0, 2, 1200, new Object[] {
			"AADD",
			"PPMM",
			"AAAA",
			'P', com(ItemListMF.haftStrong),
			'M', steel,
			'D', Item.diamond,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.maceEncrusted), true , 0, 2, 1200, new Object[] {
			"AAAA",
			"PPMM",
			"AADD",
			'P', com(ItemListMF.haftStrong),
			'M', steel,
			'D', Item.diamond,
			});
		
		//MORNINGSTAR
		//MACE
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.greatmaceEncrusted), true , 0, 2, 1440, new Object[] {
			"AAAAADD",
			"MPPPPMM",
			"AAAAAAA",
			'P', com(ItemListMF.haftStrong),
			'M', steel,
			'D', Item.diamond,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.greatmaceEncrusted), true , 0, 2, 1440, new Object[] {
			"AAAAAAA",
			"MPPPPMM",
			"AAAAADD",
			'P', com(ItemListMF.haftStrong),
			'M', steel,
			'D', Item.diamond,
			});
				
		//WARHAMMER
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.warhammerEncrusted), true , 0, 2, 1600, new Object[] {
			"AAAAAAM",
			"MPPPPMM",
			"AAAAADD",
			'P', com(ItemListMF.haftStrong),
			'M', steel,
			'D', Item.diamond,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.warhammerEncrusted), true , 0, 2, 1600, new Object[] {
			"AAAAADD",
			"MPPPPMM",
			"AAAAAAM",
			'P', com(ItemListMF.haftStrong),
			'M', steel,
			'D', Item.diamond,
			});
		
		if(bg())
		{
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bucklerEncrusted), true , 0, 2, encrustedTime, new Object[] {
				" D ",
				"SLS",
				" S ",
				'L', Item.leather,
				'S', steel,
				'D', Item.diamond,
				});
			
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.kiteEncrusted), true , 0, 2, encrustedTime*3, new Object[] {
				"D D",
				"SWS",
				" S ",
				'W', Block.planks,
				'S', steel,
				'D', Item.diamond,
				});
			
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.towerEncrusted), true , 0, 2, encrustedTime*5, new Object[] {
				"SSS",
				"DWD",
				"DWD",
				"SSS",
				'W', Block.planks,
				'S', steel,
				'D', Item.diamond,
				});
		}
		
	}
	
	
	
	
	private static void addHound()
	{
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_BMail), 0, 0 , bronzeTime*2, new Object[] {
			"   CC",
			"CCCC ",
			" C C ",
			'C', com(ItemListMF.chainBronze),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_BMailH), 0, 0 , bronzeTime, new Object[] {
			" CC",
			"CC ",
			'C', com(ItemListMF.chainBronze),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_IMail), 0, 1 , ironTime*2, new Object[] {
			"   CC",
			"CCCC ",
			" C C ",
			'C', com(ItemListMF.chainIron),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_IMailH), 0, 1 , ironTime, new Object[] {
			" CC",
			"CC ",
			'C', com(ItemListMF.chainIron),
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_GMail), 0, 1 , steelTime*2, new Object[] {
			"   CC",
			"CCCC ",
			" C C ",
			'C', com(ItemListMF.chainGuilded),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_GMailH), 0, 1 , steelTime, new Object[] {
			" CC",
			"CC ",
			'C', com(ItemListMF.chainGuilded),
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_SMail), 0, 2 , steelTime*2, new Object[] {
			"   CC",
			"CCCC ",
			" C C ",
			'C', com(ItemListMF.chainSteel),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_SMailH), 0, 2 , steelTime, new Object[] {
			" CC",
			"CC ",
			'C', com(ItemListMF.chainSteel),
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_DMail), 1, 2 , dragonforgeTime*2, new Object[] {
			"   CC",
			"CCCC ",
			" C C ",
			'C', com(ItemListMF.chainDragon),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_DMailH), 1, 2 , dragonforgeTime, new Object[] {
			" CC",
			"CC ",
			'C', com(ItemListMF.chainDragon),
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_DImail), 0, 3 , deepIronTime*2, new Object[] {
			"   CC",
			"CCCC ",
			" C C ",
			'C', com(ItemListMF.chainDeepIron),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_DImailH), 0, 3 , deepIronTime, new Object[] {
			" CC",
			"CC ",
			'C', com(ItemListMF.chainDeepIron),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_MMail), 0, 3 , mithrilTime*2, new Object[] {
			"   CC",
			"CCCC ",
			" C C ",
			'C', com(ItemListMF.chainMithril),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hound_MMailH), 0, 3 , mithrilTime, new Object[] {
			" CC",
			"CC ",
			'C', com(ItemListMF.chainMithril),
			});
		
		//PLATE
		
		//BRONZE
		addHoundPlate(ItemListMF.platingBronze, ItemListMF.smlPlateBronze, new Item[]
		{
			ItemListMF.hound_Bplate,
			ItemListMF.hound_BplateH,
		},
		0, 0, bronzeTime);
		
		//IRON
		addHoundPlate(ItemListMF.platingIron, ItemListMF.smlPlateIron, new Item[]
		{
				ItemListMF.hound_Iplate,
				ItemListMF.hound_IplateH,
		},
		0, 1, ironTime);
		
		//GUILDED
		addHoundPlate(ItemListMF.platingSilver, ItemListMF.smlPlateSilver, new Item[]
		{
				ItemListMF.hound_Gplate,
				ItemListMF.hound_GplateH,
		},
		0, 1, ironTime);
		
		//STEEL
		addHoundPlate(ItemListMF.platingSteel, ItemListMF.smlPlateSteel, new Item[]
		{
				ItemListMF.hound_Splate,
				ItemListMF.hound_SplateH,
		},
		0, 2, steelTime);
		
		//ENCRUSTED
		addHoundPlate(ItemListMF.platingEncrusted, ItemListMF.smlPlateEncrusted, new Item[]
		{
				ItemListMF.hound_Eplate,
				ItemListMF.hound_EplateH,
		},
		0, 2, encrustedTime);
		
		//DRAGONFORGE
		addHoundPlate(ItemListMF.platingDragon, ItemListMF.smlPlateDragon, new Item[]
		{
				ItemListMF.hound_Dplate,
				ItemListMF.hound_DplateH,
		},
		1, 2, dragonforgeTime);
		
		//DEEPIRON
		addHoundPlate(ItemListMF.platingDeepIron, ItemListMF.smlPlateDeepIron, new Item[]
		{
				ItemListMF.hound_DIplate,
				ItemListMF.hound_DIplateH,
		},
		0, 3, mithrilTime);
				
		//MITHRIL
		addHoundPlate(ItemListMF.platingMithril, ItemListMF.smlPlateMithril, new Item[]
		{
				ItemListMF.hound_Mplate,
				ItemListMF.hound_MplateH,
		},
		0, 3, mithrilTime);
	}
	
	
	private static void addCrossbows()
	{
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.crossbowMech) , 0, 1, 800, new Object[] {
			"ASAAA",
			"PPIPP",
			"AAASS",
			'I', com(ItemListMF.ingotWroughtIron),
			'S', Item.stick,
			'P', ItemListMF.plank,
			});
		
		for(ItemStack steel: OreDictionary.getOres("ingotSteel"))
		{
			MineFantasyAPI.addAnvilRecipe(com(ItemListMF.crossbowMechRepeat) , 0, 2, 1200, new Object[] {
				"AIAIA",
				"IPMPP",
				"AAAPA",
				'I', steel,
				'M', com(ItemListMF.crossbowMech),
				'P', ItemListMF.plank,
				});
			
			MineFantasyAPI.addAnvilRecipe(com(ItemListMF.boltBox) , 0, 2, 600, new Object[] {
				"APAPA",
				"APAPA",
				"APAPA",
				"AIIPP",
				'I', steel,
				'P', ItemListMF.plank,
				});
			
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.crossbowHeavy) , 0, 2, 5000, new Object[] {
				"AAAABA",
				"PPPMSB",
				"PPPISB",
				"AAAABA",
				'S', Item.silk,
				'M', com(ItemListMF.crossbowMech),
				'B', com(ItemListMF.plankIronbark),
				'P', ItemListMF.plank,
				'I', steel,
				});
		}
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.crossbowHand) , 0, 0, 1500, new Object[] {
			"AAAPA",
			"PPMSP",
			"AAAPA",
			'S', Item.silk,
			'M', com(ItemListMF.crossbowMech),
			'P', ItemListMF.plank,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.crossbowLight) , 0, 0, 2000, new Object[] {
			"AAAPA",
			"AAMSP",
			"PPPSP",
			"AAAPA",
			'S', Item.silk,
			'M', com(ItemListMF.crossbowMech),
			'P', ItemListMF.plank,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.crossbowRepeat) , 0, 2, 2000, new Object[] {
			"AABPA",
			"AAMSP",
			"PPPSP",
			"AAAPA",
			'S', Item.silk,
			'M', com(ItemListMF.crossbowMechRepeat),
			'B', com(ItemListMF.boltBox),
			'P', ItemListMF.plank,
			});
		
	}
	
	private static void addBronze(ItemStack ore)
	{
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.lumpBronze, 4), false, 0, 0, 200, new Object[] {
			"I",
			'I', ore,
			});
		/*
		 * TRIP HAMMER
		 * Head:
		 *   Bronze x3
		 *   Iron   x12
		 *   Stone  x5
		 *   
		 * Crank:
		 *   Bronze x14
		 *   Iron   x1
		 *   Stone  x2
		 *   
		 *   
		 * Total:
		 *   Bronze x17
		 *   Iron   x13
		 *   Stone  x7
		 */
		MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.tripHammer, 1, 0), 0, 1 , 1200, new Object[] {
			"HIIIB",
			"W  BB",
			"S  SS",
			'H', ItemListMF.hammerBronze,
			'I', com(ItemListMF.ingotWroughtIron),
			'B', ore,
			'W', block(7),
			'S', Block.stone,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.tripHammer, 1, 1), 0, 1 , 1200, new Object[] {
			"  B ",
			" BIB",
			"BWB ",
			"S S ",
			'I', com(ItemListMF.ingotWroughtIron),
			'B', ore,
			'W', block(3),
			'S', Block.stone,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 16, ItemListMF.scaleBronze), 0, 0 , 800, new Object[] {
			"MMM",
			" M ",
			'M', ore,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 8, ItemListMF.splintBronze), 0, 0 , 800, new Object[] {
			"M",
			"M",
			"M",
			"M",
			'M', ore,
			});
		
		
        MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 24, ItemListMF.linkBronze),0,0 , 600, new Object[] {
			" M ",
			"M M",
			" M ",
			'M', ore,
			});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.chainBronze),0,0 , bronzeTime, new Object[] {
			"CC",
			"CC",
			'C', com(ItemListMF.linkBronze),
			});
		
		for(ItemStack block : OreDictionary.getOres("blockBronze"))
		{
			MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.anvil, 1, 2), true, 0, -1, 900, new Object[] {
				" BB",
				"III",
				" I ",
				
				'I', ore,
				'B', block,
				});
		}
	}
	private static void addIron(ItemStack ore)
	{
	
		MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.dogbowl, 1, 1), false, 0, 1, 500, new Object[] {
			"IBI",
			" I ",
			
			'I', ore,
			'B', Item.bowlEmpty,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.anvil, 1, 4), true, 0, 0, 1000, new Object[] {
			" BB",
			"III",
			" I ",
			
			'I', ore,
			'B', new ItemStack(BlockListMF.storage, 1, 7),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.Blast, 1, 0), 1000, new Object[] {
			"G G",
			"G G",
			"GIG",
			'I', ore,
			'G', Block.stoneBrick,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.Blast, 1, 1), 1000, new Object[] {
			"G G",
			"GFG",
			"GIG",
			'I', ore,
			'F', new ItemStack(BlockListMF.furnace, 1, 0),
			'G', Block.stoneBrick,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.Blast, 1, 2), 1000, new Object[] {
			"III",
			"IFI",
			"III",
			'I', ore,
			'F', new ItemStack(BlockListMF.furnace, 1, 0),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.Blast, 1, 3), 1000, new Object[] {
			"I I",
			"G G",
			"GGG",
			'I', ore,
			'G', Block.stoneBrick,
			});
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.lumpIron, 4), false, 0, 1, 300, new Object[] {
			"I",
			'I', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 24, ItemListMF.linkIron),0,1 , 800, new Object[] {
			" M ",
			"M M",
			" M ",
			'M', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 16, ItemListMF.scaleIron), 0, 1 , 1200, new Object[] {
			"MMM",
			" M ",
			'M', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 8, ItemListMF.splintIron), 0, 1 , 1200, new Object[] {
			"M",
			"M",
			"M",
			"M",
			'M', ore,
			});
	}
	
	private static void addDeepIron(ItemStack ore)
	{
		for(ItemStack block : OreDictionary.getOres("blockDeepIron"))
		{
			MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.anvil, 1, 8), true, 0, 2, 1800, new Object[] {
				" BB",
				"III",
				" I ",
				
				'I', ore,
				'B', block,
				});
		}
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.lumpDeepIron, 4), false, 0, 3, 600, new Object[] {
			"I",
			'I', ore,
		});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 16, ItemListMF.scaleDeepIron), 0, 3 , 2000, new Object[] {
			"MMM",
			" M ",
			'M', ore,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 24, ItemListMF.linkDeepIron),0, 3 , 1500, new Object[] {
			" M ",
			"M M",
			" M ",
			'M', ore,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 8, ItemListMF.splintDeepIron), 0, 3 , 2000, new Object[] {
			"M",
			"M",
			"M",
			"M",
			'M', ore,
			});
	}
	
	private static void addMithril(ItemStack ore)
	{
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.lumpMithril, 4), false, 0, 3, 800, new Object[] {
			"I",
			'I', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 8, ItemListMF.splintMithril), 0, 3 , 2400, new Object[] {
			"M","M","M","M",
			'M', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepairArtisan), true , 0, 3, 1000, new Object[] {
			"AAAMA",
			"PLPMM",
			"AAAMM",
			'L', Item.leather,
			'P', ItemListMF.plank,
			'M', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepairArtisan), true , 0, 3, 1000, new Object[] {
			"AAAMM",
			"PLPMM",
			"AAAMA",
			'L', Item.leather,
			'P', ItemListMF.plank,
			'M', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepairOrnateArtisan), true , 0, 3, 1500, new Object[] {
			"AAAMA",
			"GLPMM",
			"AAAMM",
			'G', Item.ingotGold,
			'L', Item.leather,
			'P', com(ItemListMF.ingotWroughtIron),
			'M', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepairOrnateArtisan), true , 0, 3, 1500, new Object[] {
			"AAAMM",
			"GLPMM",
			"AAAMA",
			'G', Item.ingotGold,
			'L', Item.leather,
			'P', com(ItemListMF.ingotWroughtIron),
			'M', ore,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 16, ItemListMF.scaleMithril), 0, 3 , 2400, new Object[] {
			"MMM",
			" M ",
			'M', ore,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 24, ItemListMF.linkMithril),0, 3 , 1800, new Object[] {
			" M ",
			"M M",
			" M ",
			'M', ore,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 8, ItemListMF.splintMithril), 0, 3 , 2400, new Object[] {
			"M",
			"M",
			"M",
			"M",
			'M', ore,
			});
		
	}
	
	private static void addDragonforge(ItemStack ore) 
	{
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 16, ItemListMF.scaleDragonforge), 1, 2 , 3200, new Object[] {
			"MMM",
			" M ",
			'M', ore,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 24, ItemListMF.linkDragonforge), 1, 2 , 2400, new Object[] {
			" M ",
			"M M",
			" M ",
			'M', ore,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.misc, 8, ItemListMF.splintDragon), 1, 2 , 3200, new Object[] {
			"M",
			"M",
			"M",
			"M",
			'M', ore,
			});
	}
	
	
	private static ItemStack block(int i) {
		return new ItemStack(BlockListMF.storage, 1, i);
	}
	
	private static int bronzeTime = 400;
	private static int ironTime = 500;
	private static int steelTime = 750;
	private static int encrustedTime = 1000;
	
	private static int deepIronTime = 1100;
	private static int mithrilTime = 1500;
	private static int dragonforgeTime = 1500;
	private static int ignotumiteTime = 1800;
	
	private static void addTools()
	{
		addKnife(0, "ingotTin", new ItemStack(ItemListMF.knifeTin), 0, -1, bronzeTime);
		addKnife(0, "ingotCopper", new ItemStack(ItemListMF.knifeCopper), 0, -1, bronzeTime);
		addKnife(1, "ingotBronze", new ItemStack(ItemListMF.knifeBronze), 0, 0, bronzeTime);
		addKnife(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.knifeIron), 0, 1, ironTime);
		addKnife(2, "ingotSteel", new ItemStack(ItemListMF.knifeSteel), 0, 2, steelTime);
		addKnife(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.knifeDragon), 1, 2, dragonforgeTime);
		addKnife(3, "ingotDeepIron", new ItemStack(ItemListMF.knifeDeepIron), 0, 3, deepIronTime);
		addKnife(3, "ingotMithril", new ItemStack(ItemListMF.knifeMithril), 0, 3, mithrilTime);
		
		addShears(0, "ingotTin", new ItemStack(ItemListMF.shearsTin), 0, -1, bronzeTime);
		addShears(0, "ingotCopper", new ItemStack(ItemListMF.shearsCopper), 0, -1, bronzeTime);
		addShears(1, "ingotBronze", new ItemStack(ItemListMF.shearsBronze), 0, 0, bronzeTime);
		addShears(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.shearsIron), 0, 1, ironTime);
		addShears(2, "ingotSteel", new ItemStack(ItemListMF.shearsSteel), 0, 2, steelTime);
		addShears(3, "ingotMithril", new ItemStack(ItemListMF.shearsMithril), 0, 3, mithrilTime);
		addShears(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.shearsDragon), 1, 2, dragonforgeTime);
		addShears(3, "ingotDeepIron", new ItemStack(ItemListMF.shearsDeepIron), 0, 3, deepIronTime);
		
		addPick(0, "ingotTin", new ItemStack(ItemListMF.pickTin), 0, -1, bronzeTime);
		addPick(0, "ingotCopper", new ItemStack(ItemListMF.pickCopperForged), 0, -1, bronzeTime);
		addPick(1, "ingotBronze", new ItemStack(ItemListMF.pickBronze), 0, 0, bronzeTime);
		addPick(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.pickIronForged), 0, 1, ironTime);
		addPick(2, "ingotSteel", new ItemStack(ItemListMF.pickSteelForged), 0, 2, steelTime);
		addPick(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.pickDragon), 1, 2, dragonforgeTime);
		addPick(3, "ingotDeepIron", new ItemStack(ItemListMF.pickDeepIron), 0, 3, deepIronTime);
		addPick(3, "ingotMithril", new ItemStack(ItemListMF.pickMithril), 0, 3, mithrilTime);
		addPick(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.pickIgnotumiteForged), 1, 3, ignotumiteTime);
		
		addHandpick(1, "ingotBronze", new ItemStack(ItemListMF.handpickBronze), 0, 0, bronzeTime);
		addHandpick(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.handpickIron), 0, 1, ironTime);
		addHandpick(2, "ingotSteel", new ItemStack(ItemListMF.handpickSteel), 0, 2, steelTime);
		addHandpick(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.handpickDragonforge), 1, 2, dragonforgeTime);
		addHandpick(3, "ingotMithril", new ItemStack(ItemListMF.handpickMithril), 0, 3, mithrilTime);
		addHandpick(3, "ingotDeepIron", new ItemStack(ItemListMF.handpickDeepIron), 0, 3, deepIronTime);
		addHandpick(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.handpickIgnotumite), 1, 3, ignotumiteTime);
		
		addSpade(0, "ingotTin", new ItemStack(ItemListMF.spadeTin), 0, -1, bronzeTime);
		addSpade(0, "ingotCopper", new ItemStack(ItemListMF.spadeCopperForged), 0, -1, bronzeTime);
		addSpade(1, "ingotBronze", new ItemStack(ItemListMF.spadeBronze), 0, 0, bronzeTime);
		addSpade(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.spadeIronForged), 0, 1, ironTime);
		addSpade(2, "ingotSteel", new ItemStack(ItemListMF.spadeSteelForged), 0, 2, steelTime);
		addSpade(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.spadeDragon), 1, 2, dragonforgeTime);
		addSpade(3, "ingotMithril", new ItemStack(ItemListMF.spadeMithril), 0, 3, mithrilTime);
		addSpade(3, "ingotDeepIron", new ItemStack(ItemListMF.spadeDeepIron), 0, 3, deepIronTime);
		addSpade(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.spadeIgnotumiteForged), 1, 3, ignotumiteTime);
		
		addAxe(0, "ingotTin", new ItemStack(ItemListMF.axeTin), 0, -1, bronzeTime);
		addAxe(0, "ingotCopper", new ItemStack(ItemListMF.axeCopperForged), 0, -1, bronzeTime);
		addAxe(1, "ingotBronze", new ItemStack(ItemListMF.axeBronze), 0, 0, bronzeTime);
		addAxe(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.axeIronForged), 0, 1, ironTime);
		addAxe(2, "ingotSteel", new ItemStack(ItemListMF.axeSteelForged), 0, 2, steelTime);
		addAxe(3, "ingotMithril", new ItemStack(ItemListMF.axeMithril), 0, 3, mithrilTime);
		addAxe(3, "ingotDeepIron", new ItemStack(ItemListMF.axeDeepIron), 0, 3, deepIronTime);
		addAxe(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.axeIgnotumiteForged), 1, 3, ignotumiteTime);
		addAxe(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.axeDragon), 1, 2, dragonforgeTime);
		
		addHoe(0, "ingotTin", new ItemStack(ItemListMF.hoeTin), 0, -1, bronzeTime);
		addHoe(0, "ingotCopper", new ItemStack(ItemListMF.hoeCopperForged), 0, -1, bronzeTime);
		addHoe(1, "ingotBronze", new ItemStack(ItemListMF.hoeBronze), 0, 0, bronzeTime);
		addHoe(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.hoeIronForged), 0, 1, ironTime);
		addHoe(2, "ingotSteel", new ItemStack(ItemListMF.hoeSteelForged), 0, 2, steelTime);
		addHoe(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.hoeDragon), 1, 2, dragonforgeTime);
		addHoe(3, "ingotMithril", new ItemStack(ItemListMF.hoeMithril), 0, 3, mithrilTime);
		addHoe(3, "ingotDeepIron", new ItemStack(ItemListMF.hoeDeepIron), 0, 3, deepIronTime);
		
		
		addDagger(1, "ingotBronze", new ItemStack(ItemListMF.daggerBronze), 0, 0, bronzeTime);
		addDagger(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.daggerIron), 0, 1, ironTime);
		addDagger(2, "ingotSteel", new ItemStack(ItemListMF.daggerSteel), 0, 2, steelTime);
		addDagger(3, "ingotMithril", new ItemStack(ItemListMF.daggerMithril), 0, 3, mithrilTime);
		addDagger(3, "ingotDeepIron", new ItemStack(ItemListMF.daggerDeepIron), 0, 3, deepIronTime);
		addDagger(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.daggerDragon), 1, 2, dragonforgeTime);
		addDagger(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.daggerIgnotumite), 1, 3, ignotumiteTime);
		
		addSword(0, "ingotCopper", new ItemStack(ItemListMF.swordCopper), 0, -1, bronzeTime);
		addSword(0, "ingotTin", new ItemStack(ItemListMF.swordTin), 0, -1, bronzeTime);
		addSword(1, "ingotBronze", new ItemStack(ItemListMF.swordBronze), 0, 0, bronzeTime);
		addSword(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.swordIronForged), 0, 1, ironTime);
		addSword(2, "ingotSteel", new ItemStack(ItemListMF.swordSteelForged), 0, 2, steelTime);
		addSword(3, "ingotMithril", new ItemStack(ItemListMF.swordMithril), 0, 3, mithrilTime);
		addSword(3, "ingotDeepIron", new ItemStack(ItemListMF.swordDeepIron), 0, 3, deepIronTime);
		addSword(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.swordDragon), 1, 2, dragonforgeTime);
		addSword(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.swordIgnotumite), 1, 3, ignotumiteTime);
		
		addBroadsword(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.broadIron), 0, 1, ironTime);
		addBroadsword(2, "ingotSteel", new ItemStack(ItemListMF.broadSteel), 0, 2, steelTime);
		addBroadsword(1, "ingotBronze", new ItemStack(ItemListMF.broadBronze), 0, 0, bronzeTime);
		addBroadsword(3, "ingotMithril", new ItemStack(ItemListMF.broadMithril), 0, 3, mithrilTime);
		addBroadsword(3, "ingotDeepIron", new ItemStack(ItemListMF.broadswordDeepIron), 0, 3, deepIronTime);
		addBroadsword(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.broadDragon), 1, 2, dragonforgeTime);
		addBroadsword(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.broadIgnotumite), 1, 3, ignotumiteTime);
		
		addGreatmace(1, "ingotBronze", new ItemStack(ItemListMF.morningstarBronze), 0, 0, bronzeTime);
		addGreatmace(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.morningstarIron), 0, 1, ironTime);
		addGreatmace(2, "ingotSteel", new ItemStack(ItemListMF.morningstarSteel), 0, 2, steelTime);
		addGreatmace(3, "ingotMithril", new ItemStack(ItemListMF.morningstarMithril), 0, 3, mithrilTime);
		addGreatmace(3, "ingotDeepIron", new ItemStack(ItemListMF.greatmaceDeepIron), 0, 3, deepIronTime);
		addGreatmace(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.morningstarDragon), 1, 2, dragonforgeTime);
		addGreatmace(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.morningstarIgnotumite), 1, 3, ignotumiteTime);
		
		addWarhammer(1, "ingotBronze", new ItemStack(ItemListMF.warhammerBronze), 0, 0, bronzeTime);
		addWarhammer(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.warhammerIron), 0, 1, ironTime);
		addWarhammer(2, "ingotSteel", new ItemStack(ItemListMF.warhammerSteel), 0, 2, steelTime);
		addWarhammer(3, "ingotMithril", new ItemStack(ItemListMF.warhammerMithril), 0, 3, mithrilTime);
		addWarhammer(3, "ingotDeepIron", new ItemStack(ItemListMF.warhammerDeepIron), 0, 3, deepIronTime);
		addWarhammer(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.warhammerDragon), 1, 2, dragonforgeTime);
		addWarhammer(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.warhammerIgnotumite), 1, 3, ignotumiteTime);
		
		addGreatsword(1, "ingotBronze", new ItemStack(ItemListMF.greatswordBronze), 0, 0, bronzeTime);
		addGreatsword(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.greatswordIron), 0, 1, ironTime);
		addGreatsword(2, "ingotSteel", new ItemStack(ItemListMF.greatswordSteel), 0, 2, steelTime);
		addGreatsword(3, "ingotMithril", new ItemStack(ItemListMF.greatswordMithril), 0, 3, mithrilTime);
		addGreatsword(3, "ingotDeepIron", new ItemStack(ItemListMF.greatswordDeepIron), 0, 3, deepIronTime);
		addGreatsword(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.greatswordDragon), 1, 2, dragonforgeTime);
		addGreatsword(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.greatswordIgnotumite), 1, 3, ignotumiteTime);
		
		addSpear(1, "ingotBronze", new ItemStack(ItemListMF.spearBronze), 0, 0, bronzeTime);
		addSpear(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.spearIron), 0, 1, ironTime);
		addSpear(2, "ingotSteel", new ItemStack(ItemListMF.spearSteel), 0, 2, steelTime);
		addSpear(3, "ingotMithril", new ItemStack(ItemListMF.spearMithril), 0, 3, mithrilTime);
		addSpear(3, "ingotDeepIron", new ItemStack(ItemListMF.spearDeepIron), 0, 3, deepIronTime);
		addSpear(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.spearDragon), 1, 2, dragonforgeTime);
		addSpear(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.spearIgnotumite), 1, 3, ignotumiteTime);
		
		addHalbeard(1, "ingotBronze", new ItemStack(ItemListMF.halbeardBronze), 0, 0, bronzeTime);
		addHalbeard(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.halbeardIron), 0, 1, ironTime);
		addHalbeard(2, "ingotSteel", new ItemStack(ItemListMF.halbeardSteel), 0, 2, steelTime);
		addHalbeard(3, "ingotMithril", new ItemStack(ItemListMF.halbeardMithril), 0, 3, mithrilTime);
		addHalbeard(3, "ingotDeepIron", new ItemStack(ItemListMF.halbeardDeepIron), 0, 3, deepIronTime);
		addHalbeard(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.halbeardDragon), 1, 2, dragonforgeTime);
		addHalbeard(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.halbeardIgnotumite), 1, 3, ignotumiteTime);
		
		addMace(0, "ingotCopper", new ItemStack(ItemListMF.maceCopper), 0, -1, bronzeTime);
		addMace(0, "ingotTin", new ItemStack(ItemListMF.maceTin), 0, -1, bronzeTime);
		addMace(1, "ingotBronze", new ItemStack(ItemListMF.maceBronze), 0, 0, bronzeTime);
		addMace(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.maceIron), 0, 1, ironTime);
		addMace(2, "ingotSteel", new ItemStack(ItemListMF.maceSteel), 0, 2, steelTime);
		addMace(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.maceDragon), 0, 2, dragonforgeTime);
		addMace(3, "ingotMithril", new ItemStack(ItemListMF.maceMithril), 0, 3, mithrilTime);
		addMace(3, "ingotDeepIron", new ItemStack(ItemListMF.maceDeepIron), 0, 3, deepIronTime);
		addMace(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.maceIgnotumite), 1, 3, ignotumiteTime);
		
		addWaraxe(0, "ingotCopper", new ItemStack(ItemListMF.waraxeCopper), 0, -1, bronzeTime);
		addWaraxe(0, "ingotTin", new ItemStack(ItemListMF.waraxeTin), 0, -1, bronzeTime);
		addWaraxe(1, "ingotBronze", new ItemStack(ItemListMF.waraxeBronze), 0, 0, bronzeTime);
		addWaraxe(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.waraxeIron), 0, 1, ironTime);
		addWaraxe(2, "ingotSteel", new ItemStack(ItemListMF.waraxeSteel), 0, 2, steelTime);
		addWaraxe(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.waraxeDragon), 1, 2, dragonforgeTime);
		addWaraxe(3, "ingotDeepIron", new ItemStack(ItemListMF.waraxeDeepIron), 0, 3, deepIronTime);
		addWaraxe(3, "ingotMithril", new ItemStack(ItemListMF.waraxeMithril), 0, 3, mithrilTime);
		addWaraxe(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.waraxeIgnotumite), 1, 3, ignotumiteTime);
		
		addBattleaxe(1, "ingotBronze", new ItemStack(ItemListMF.battleaxeBronze), 0, 0, bronzeTime);
		addBattleaxe(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.battleaxeIron), 0, 1, ironTime);
		addBattleaxe(2, "ingotSteel", new ItemStack(ItemListMF.battleaxeSteel), 0, 2, steelTime);
		addBattleaxe(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.battleaxeDragon), 1, 2, dragonforgeTime);
		addBattleaxe(3, "ingotMithril", new ItemStack(ItemListMF.battleaxeMithril), 0, 3, mithrilTime);
		addBattleaxe(3, "ingotDeepIron", new ItemStack(ItemListMF.battleaxeDeepIron), 0, 3, deepIronTime);
		addBattleaxe(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.battleaxeIgnotumite), 1, 3, ignotumiteTime);
		
		addWarpick(1, "ingotBronze", new ItemStack(ItemListMF.warpickBronze), 0, 0, bronzeTime);
		addWarpick(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.warpickIron), 0, 1, ironTime);
		addWarpick(2, "ingotSteel", new ItemStack(ItemListMF.warpickSteel), 0, 2, steelTime);
		addWarpick(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.warpickDragon), 1, 2, dragonforgeTime);
		addWarpick(3, "ingotMithril", new ItemStack(ItemListMF.warpickMithril), 0, 3, mithrilTime);
		addWarpick(3, "ingotDeepIron", new ItemStack(ItemListMF.warpickDeepIron), 0, 3, deepIronTime);
		addWarpick(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.warpickIgnotumite), 1, 3, ignotumiteTime);
		
		addScythe(1, "ingotBronze", new ItemStack(ItemListMF.scytheBronze), 0, 0, bronzeTime);
		addScythe(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.scytheIron), 0, 1, ironTime);
		addScythe(2, "ingotSteel", new ItemStack(ItemListMF.scytheSteel), 0, 2, steelTime);
		addScythe(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.scytheDragon), 1, 2, dragonforgeTime);
		addScythe(3, "ingotMithril", new ItemStack(ItemListMF.scytheMithril), 0, 3, mithrilTime);
		addScythe(3, "ingotDeepIron", new ItemStack(ItemListMF.scytheDeepIron), 0, 3, deepIronTime);
		
		addRake(1, "ingotBronze", new ItemStack(ItemListMF.rakeBronze), 0, 0, bronzeTime);
		addRake(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.rakeIron), 0, 1, ironTime);
		addRake(2, "ingotSteel", new ItemStack(ItemListMF.rakeSteel), 0, 2, steelTime);
		addRake(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.rakeDragon), 1, 2, dragonforgeTime);
		addRake(3, "ingotMithril", new ItemStack(ItemListMF.rakeMithril), 0, 3, mithrilTime);
		addRake(3, "ingotDeepIron", new ItemStack(ItemListMF.rakeDeepIron), 0, 3, deepIronTime);
		
		addMattock(1, "ingotBronze", new ItemStack(ItemListMF.mattockBronze), 0, 0, bronzeTime);
		addMattock(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.mattockIron), 0, 1, ironTime);
		addMattock(2, "ingotSteel", new ItemStack(ItemListMF.mattockSteel), 0, 2, steelTime);
		addMattock(3, "ingotMithril", new ItemStack(ItemListMF.mattockMithril), 0, 3, mithrilTime);
		addMattock(3, "ingotDeepIron", new ItemStack(ItemListMF.mattockDeepIron), 0, 3, deepIronTime);
		addMattock(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.mattockDragon), 1, 2, dragonforgeTime);
		
		addSaw(1, "ingotBronze", new ItemStack(ItemListMF.sawBronze), 0, 0, bronzeTime);
		addSaw(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.sawIron), 0, 1, ironTime);
		addSaw(2, "ingotSteel", new ItemStack(ItemListMF.sawSteel), 0, 2, steelTime);
		addSaw(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.sawDragon), 1, 2, dragonforgeTime);
		addSaw(3, "ingotDeepIron", new ItemStack(ItemListMF.sawDeepIron), 0, 3, deepIronTime);
		addSaw(3, "ingotMithril", new ItemStack(ItemListMF.sawMithril), 0, 3, mithrilTime);
		
		addTeeth("ingotBronze", new ItemStack(ItemListMF.hound_Bteeth), 0, 0, bronzeTime);
		addTeeth(com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.hound_Iteeth), 0, 1, ironTime);
		addTeeth("ingotSteel", new ItemStack(ItemListMF.hound_Steeth), 0, 2, steelTime);
		addTeeth(com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.hound_Dteeth), 0, 2, steelTime);
		addTeeth("ingotDeepIron", new ItemStack(ItemListMF.hound_DIteeth), 0, 3, deepIronTime);
		addTeeth("ingotMithril", new ItemStack(ItemListMF.hound_Mteeth), 0, 3, mithrilTime);
		addTeeth(com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.hound_Igteeth), 1, 3, ignotumiteTime);
		
		addRecurve("ingotBronze", new ItemStack(ItemListMF.bowBronze), 0, 0, bronzeTime);
		addRecurve(com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.bowIron), 0, 1, ironTime);
		addRecurve("ingotSteel", new ItemStack(ItemListMF.bowSteel), 0, 2, steelTime);
		addRecurve(com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.bowDragon), 1, 2, dragonforgeTime);
		addRecurve("ingotDeepIron", new ItemStack(ItemListMF.bowDeepIron), 0, 3, deepIronTime);
		addRecurve("ingotMithril", new ItemStack(ItemListMF.bowMithril), 0, 3, mithrilTime);
		
		//ItemBoltMF
		addBolt("ingotBronze", 1, 0, 0, bronzeTime);
		addBolt(com(ItemListMF.ingotWroughtIron), 2, 0, 1, ironTime);
		addBolt("ingotSteel", 3, 0, 2, steelTime);
		addBolt("ingotMithril", 4, 0, 3, mithrilTime);
		addBolt("ingotSilver", 5, 0, 1, ironTime);
		addBolt(com(ItemListMF.ingotDragon), 7, 1, 2, dragonforgeTime);
		addBolt(com(ItemListMF.ingotIgnotumite), 8, 1, 3, ignotumiteTime);
		addBolt("ingotDeepIron", 9, 0, 3, deepIronTime);
		
		//ItemArrowMF
		addArrowhead("ingotBronze", ItemListMF.arrowheadBronze, 0, 0, bronzeTime);
		addArrowhead(com(ItemListMF.ingotWroughtIron), ItemListMF.arrowheadIron, 0, 1, ironTime);
		addArrowhead("ingotSteel", ItemListMF.arrowheadSteel, 0, 2, steelTime);
		addArrowhead("ingotMithril", ItemListMF.arrowheadMithril, 0, 3, mithrilTime);
		addArrowhead("ingotSilver", ItemListMF.arrowheadSilver, 0, 1, ironTime);
		addArrowhead(com(ItemListMF.ingotDragon), ItemListMF.arrowheadDragonforge, 1, 2, dragonforgeTime);
		addArrowhead(com(ItemListMF.ingotIgnotumite), ItemListMF.arrowheadIgnotumite, 1, 3, ignotumiteTime);
		addArrowhead("ingotDeepIron", ItemListMF.arrowheadDeepIron, 0, 3, deepIronTime);
		
		addBodkinhead("ingotBronze", ItemListMF.bodkinheadBronze, 0, 0, bronzeTime);
		addBodkinhead(com(ItemListMF.ingotWroughtIron), ItemListMF.bodkinheadIron, 0, 1, ironTime);
		addBodkinhead("ingotSteel", ItemListMF.bodkinheadSteel, 0, 2, steelTime);
		addBodkinhead("ingotMithril", ItemListMF.bodkinheadMithril, 0, 3, mithrilTime);
		addBodkinhead("ingotSilver", ItemListMF.bodkinheadSilver, 0, 1, ironTime);
		addBodkinhead(com(ItemListMF.ingotDragon), ItemListMF.bodkinheadDragonforge, 1, 2, dragonforgeTime);
		addBodkinhead(com(ItemListMF.ingotIgnotumite), ItemListMF.bodkinheadIgnotumite, 1, 3, ignotumiteTime);
		addBodkinhead("ingotDeepIron", ItemListMF.bodkinheadDeepIron, 0, 3, deepIronTime);
		
		addBroadhead("ingotBronze", ItemListMF.broadheadBronze, 0, 0, bronzeTime);
		addBroadhead(com(ItemListMF.ingotWroughtIron), ItemListMF.broadheadIron, 0, 1, ironTime);
		addBroadhead("ingotSteel", ItemListMF.broadheadSteel, 0, 2, steelTime);
		addBroadhead("ingotMithril", ItemListMF.broadheadMithril, 0, 3, mithrilTime);
		addBroadhead("ingotSilver", ItemListMF.broadheadSilver, 0, 1, ironTime);
		addBroadhead(com(ItemListMF.ingotDragon), ItemListMF.broadheadDragonforge, 1, 2, dragonforgeTime);
		addBroadhead(com(ItemListMF.ingotIgnotumite), ItemListMF.broadheadIgnotumite, 1, 3, ignotumiteTime);
		addBroadhead("ingotDeepIron", ItemListMF.broadheadDeepIron, 0, 3, deepIronTime);
		
		addTongs("ingotTin", new ItemStack(ItemListMF.tongsTin), 0, -1, bronzeTime);
		addTongs("ingotCopper", new ItemStack(ItemListMF.tongsCopper), 0, -1, bronzeTime);
		addTongs("ingotBronze", new ItemStack(ItemListMF.tongsBronze), 0, 0, bronzeTime);
		addTongs(com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.tongsIron), 0, 1, ironTime);
		addTongs("ingotSteel", new ItemStack(ItemListMF.tongsSteel), 0, 2, steelTime);
		addTongs(com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.tongsDragon), 1, 2, dragonforgeTime);
		addTongs("ingotMithril", new ItemStack(ItemListMF.tongsMithril), 0, 3, mithrilTime);
		addTongs("ingotDeepIron", new ItemStack(ItemListMF.tongsDeepIron), 0, 3, deepIronTime);
		
		addHammer(0, "ingotTin", new ItemStack(ItemListMF.hammerTin), 0, -1, bronzeTime);
		addHammer(0, "ingotCopper", new ItemStack(ItemListMF.hammerCopper), 0, -1, bronzeTime);
		addHammer(1, "ingotBronze", new ItemStack(ItemListMF.hammerBronze), 0, 0, bronzeTime);
		addHammer(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.hammerIron), 0, 1, ironTime);
		addHammer(2, "ingotSteel", new ItemStack(ItemListMF.hammerSteel), 0, 2, steelTime);
		addHammer(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.hammerDragon), 1, 2, dragonforgeTime);
		addHammer(3, "ingotMithril", new ItemStack(ItemListMF.hammerMithril), 0, 3, mithrilTime);
		addHammer(3, "ingotDeepIron", new ItemStack(ItemListMF.hammerDeepIron), 0, 3, deepIronTime);
	
		addLance(1, "ingotBronze", new ItemStack(ItemListMF.lanceBronze), 0, 0, bronzeTime);
		addLance(1, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.lanceIron), 0, 1, ironTime);
		addLance(2, "ingotSteel", new ItemStack(ItemListMF.lanceSteel), 0, 2, steelTime);
		addLance(3, "ingotMithril", new ItemStack(ItemListMF.lanceMithril), 0, 3, mithrilTime);
		addLance(2, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.lanceDragon), 1, 2, dragonforgeTime);
		addLance(4, com(ItemListMF.ingotIgnotumite), new ItemStack(ItemListMF.lanceIgnotumite), 1, 3, ignotumiteTime);
		
		addOven("ingotBronze", new ItemStack(BlockListMF.oven, 1, 0), 0, 0, bronzeTime);
		addOven(com(ItemListMF.ingotWroughtIron), new ItemStack(BlockListMF.oven, 1, 2), 0, 1, ironTime);
		addOven("ingotSteel", new ItemStack(BlockListMF.oven, 1, 4), 0, 2, steelTime);
		addOven("ingotDeepIron", new ItemStack(BlockListMF.oven, 1, 6), 0, 3, deepIronTime);
		
		addPlating("ingotBronze", com(ItemListMF.platingBronze, 4), 0, 0, bronzeTime);
		addPlating(com(ItemListMF.ingotWroughtIron), com(ItemListMF.platingIron, 4), 0, 1, ironTime);
		addPlating("ingotSteel", com(ItemListMF.platingSteel, 4), 0, 2, steelTime);
		addPlating2("ingotSteel", Item.diamond, com(ItemListMF.platingEncrusted, 4), 0, 2, encrustedTime);
		addPlating("ingotMithril", com(ItemListMF.platingMithril, 4), 0, 3, mithrilTime);
		addPlating("ingotDeepIron", com(ItemListMF.platingDeepIron, 4), 0, 3, deepIronTime);
		addPlating2("ingotSilver", com(ItemListMF.ingotGoldPure), com(ItemListMF.platingSilver, 4), 0, 1, ironTime);
		addPlating(com(ItemListMF.ingotDragon), com(ItemListMF.platingDragon, 4), 1, 2, dragonforgeTime);
		
		addPlatingPadded(com(ItemListMF.platingBronze), com(ItemListMF.smlPlateBronze), 0, 0, bronzeTime);
		addPlatingPadded(com(ItemListMF.platingIron), com(ItemListMF.smlPlateIron), 0, 1, ironTime);
		addPlatingPadded(com(ItemListMF.platingSteel), com(ItemListMF.smlPlateSteel), 0, 2, steelTime);
		addPlatingPadded(com(ItemListMF.platingMithril), com(ItemListMF.smlPlateMithril), 0, 3, mithrilTime);
		addPlatingPadded(com(ItemListMF.platingDeepIron), com(ItemListMF.smlPlateDeepIron), 0, 3, deepIronTime);
		addPlatingPadded(com(ItemListMF.platingSilver), com(ItemListMF.smlPlateSilver), 0, 1, ironTime);
		addPlatingPadded(com(ItemListMF.platingDragon), com(ItemListMF.smlPlateDragon), 1, 2, dragonforgeTime);
		addPlatingPadded(com(ItemListMF.platingEncrusted), com(ItemListMF.smlPlateEncrusted), 0, 2, encrustedTime);
		
		addPlatingCurved(com(ItemListMF.platingBronze), com(ItemListMF.curvedPlateBronze), 0, 0, bronzeTime);
		addPlatingCurved(com(ItemListMF.platingIron), com(ItemListMF.curvedPlateIron), 0, 1, ironTime);
		addPlatingCurved(com(ItemListMF.platingSteel), com(ItemListMF.curvedPlateSteel), 0, 2, steelTime);
		addPlatingCurved(com(ItemListMF.platingMithril), com(ItemListMF.curvedPlateMithril), 0, 3, mithrilTime);
		addPlatingCurved(com(ItemListMF.platingDeepIron), com(ItemListMF.curvedPlateDeepIron), 0, 3, deepIronTime);
		addPlatingCurved(com(ItemListMF.platingSilver), com(ItemListMF.curvedPlateSilver), 0, 1, ironTime);
		addPlatingCurved(com(ItemListMF.platingDragon), com(ItemListMF.curvedPlateDragon), 1, 2, dragonforgeTime);
		addPlatingCurved(com(ItemListMF.platingEncrusted), com(ItemListMF.curvedPlateEncrusted), 0, 2, encrustedTime);
		
		if(bg())
		{
			addBuckler("ingotBronze", new ItemStack(ItemListMF.bucklerBronze), 0, 0, bronzeTime);
			addBuckler(com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.bucklerIron), 0, 1, ironTime);
			addBuckler("ingotSteel", new ItemStack(ItemListMF.bucklerSteel), 0, 2, steelTime);
			addBuckler("ingotDeepIron", new ItemStack(ItemListMF.bucklerDeepIron), 0, 3, deepIronTime);
			addBuckler("ingotMithril", new ItemStack(ItemListMF.bucklerMithril), 0, 3, mithrilTime);
			addBuckler(com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.bucklerDragonforge), 1, 2, dragonforgeTime);
			
			addKite(0, "ingotBronze", new ItemStack(ItemListMF.kiteBronze), 0, 0, bronzeTime);
			addKite(0, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.kiteIron), 0, 1, ironTime);
			addKite(0, "ingotSteel", new ItemStack(ItemListMF.kiteSteel), 0, 2, steelTime);
			addKite(1, "ingotMithril", new ItemStack(ItemListMF.kiteMithril), 0, 3, mithrilTime);
			addKite(1, "ingotDeepIron", new ItemStack(ItemListMF.kiteDeepIron), 0, 3, deepIronTime);
			addKite(0, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.kiteDragonforge), 1, 2, dragonforgeTime);
			
			addTower(0, "ingotBronze", new ItemStack(ItemListMF.towerBronze), 0, 0, bronzeTime);
			addTower(0, com(ItemListMF.ingotWroughtIron), new ItemStack(ItemListMF.towerIron), 0, 1, ironTime);
			addTower(0, "ingotSteel", new ItemStack(ItemListMF.towerSteel), 0, 2, steelTime);
			addTower(1, "ingotMithril", new ItemStack(ItemListMF.towerMithril), 0, 3, mithrilTime);
			addTower(1, "ingotDeepIron", new ItemStack(ItemListMF.towerDeepIron), 0, 3, deepIronTime);
			addTower(0, com(ItemListMF.ingotDragon), new ItemStack(ItemListMF.towerDragonforge), 1, 2, dragonforgeTime);
			
			addRound(0, new ItemStack(ItemListMF.plank), new ItemStack(ItemListMF.shieldWood), 0, 0, bronzeTime);
			addRound(1, com(ItemListMF.plankIronbark), new ItemStack(ItemListMF.shieldIronbark), 0, 1, bronzeTime*2);
			addRound(1, com(ItemListMF.plankEbony), new ItemStack(ItemListMF.shieldEbony), 0, 1, bronzeTime*3);
		}
		
		//BRONZE
		addPlateAssembly(ItemListMF.platingBronze, ItemListMF.curvedPlateBronze, ItemListMF.smlPlateBronze, new Item[]
		{
			ItemListMF.helmetBronzePlate,
			ItemListMF.plateBronzePlate,
			ItemListMF.legsBronzePlate,
			ItemListMF.bootsBronzePlate,
		},
		0, 0, bronzeTime);
		
		//IRON
		addPlateAssembly(ItemListMF.platingIron, ItemListMF.curvedPlateIron, ItemListMF.smlPlateIron, new Item[]
		{
			ItemListMF.helmetIronPlate,
			ItemListMF.plateIronPlate,
			ItemListMF.legsIronPlate,
			ItemListMF.bootsIronPlate,
		},
		0, 1, ironTime);
		
		//GUILDED
		addPlateAssembly(ItemListMF.platingSilver, ItemListMF.curvedPlateSilver, ItemListMF.smlPlateSilver, new Item[]
		{
			ItemListMF.helmetGuildedPlate,
			ItemListMF.plateGuildedPlate,
			ItemListMF.legsGuildedPlate,
			ItemListMF.bootsGuildedPlate,
		},
		0, 1, ironTime);
		
		//STEEL
		addPlateAssembly(ItemListMF.platingSteel, ItemListMF.curvedPlateSteel, ItemListMF.smlPlateSteel, new Item[]
		{
			ItemListMF.helmetSteelPlate,
			ItemListMF.plateSteelPlate,
			ItemListMF.legsSteelPlate,
			ItemListMF.bootsSteelPlate,
		},
		0, 2, steelTime);
		
		//ENCRUSTED
		addPlateAssembly(ItemListMF.platingEncrusted, ItemListMF.curvedPlateEncrusted, ItemListMF.smlPlateEncrusted, new Item[]
		{
			ItemListMF.helmetEncrustedPlate,
			ItemListMF.plateEncrustedPlate,
			ItemListMF.legsEncrustedPlate,
			ItemListMF.bootsEncrustedPlate,
		},
		0, 2, encrustedTime);
		
		//DRAGONFORGE
		addPlateAssembly(ItemListMF.platingDragon, ItemListMF.curvedPlateDragon, ItemListMF.smlPlateDragon, new Item[]
		{
			ItemListMF.helmetDragonPlate,
			ItemListMF.plateDragonPlate,
			ItemListMF.legsDragonPlate,
			ItemListMF.bootsDragonPlate,
		},
		1, 2, dragonforgeTime);
		
		//DEEP IRON
		addPlateAssembly(ItemListMF.platingDeepIron, ItemListMF.curvedPlateDeepIron, ItemListMF.smlPlateDeepIron, new Item[]
		{
			ItemListMF.helmetDeepIronPlate,
			ItemListMF.plateDeepIronPlate,
			ItemListMF.legsDeepIronPlate,
			ItemListMF.bootsDeepIronPlate,
		},
		0, 3, deepIronTime);
				
		//MITHRIL
		addPlateAssembly(ItemListMF.platingMithril, ItemListMF.curvedPlateMithril, ItemListMF.smlPlateMithril, new Item[]
		{
			ItemListMF.helmetMithrilPlate,
			ItemListMF.plateMithrilPlate,
			ItemListMF.legsMithrilPlate,
			ItemListMF.bootsMithrilPlate,
		},
		0, 3, mithrilTime);
	}
	
	private static boolean bg() 
	{
		return MineFantasyBase.isBGLoaded() || MineFantasyBase.isDebug();
	}



	private static void addPlating2(String ore, Object material2, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addPlating2(material, material2, output, hammer, anvil, matTime);
	}
	private static void addPlating2(Object material, Object material2, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, false , hammer, anvil, (int)(matTime*0.5F), new Object[] {
			"A ",
			"MM",
			'A', material2,
			'M', material,
			});
	}
	
	//PLATE 0.5xMaterial
	private static void addPlating(String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addPlating(material, output, hammer, anvil, matTime);
	}
	private static void addPlating(Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, false , hammer, anvil, (int)(matTime*0.5F), new Object[] {
			"MM",
			'M', material,
			});
	}
	private static void addPlatingPadded(Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, false , hammer, anvil, (int)(matTime*0.5F), new Object[] {
			"M",
			"P",
			'P', com(ItemListMF.padding),
			'M', material,
			});
	}
	private static void addPlatingCurved(Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, false , hammer, anvil, (int)(matTime*0.25F), new Object[] {
			"M",
			'M', material,
			});
	}
	
	
	
	
	//SPADES 1xMaterial
	private static void addSpade(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		addSpade(lvl, material, output, hammer, anvil, matTime);
	}
	private static void addSpade(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, matTime, new Object[] {
			"AAA",
			"PPM",
			"AAA",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	//AXES 3.2xMaterial
	private static void addAxe(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		{
			addAxe(lvl, material, output, hammer, anvil, matTime);
		}
	}
	private static void addAxe(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.2F), new Object[] {
			"AMM",
			"PPM",
			"AAA",
			'P', haft(lvl),
			'M', material,
			});
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.2F), new Object[] {
			"AAA",
			"PPM",
			"AMM",
			'P', haft(lvl),
			'M', material,
			});
	}
		
		//Hoes 2xMaterial
		private static void addHoe(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
			{
				addHoe(lvl, material, output, hammer, anvil, matTime);
			}
		}

		private static void addHoe(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.2F), new Object[] {
				"AAM",
				"PPM",
				"AAA",
				'P', haft(lvl),
				'M', material,
				});
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.2F), new Object[] {
				"AAA",
				"PPM",
				"AAM",
				'P', haft(lvl),
				'M', material,
				});
		}
		
		
	//PICKS 3xMaterial
	private static void addPick(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addPick(lvl, material, output, hammer, anvil, matTime);
	}

	private static void addPick(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, matTime*3, new Object[] {
			"AAM",
			"PPM",
			"AAM",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	//HAND PICKS 1xMaterial
	private static void addHandpick(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addHandpick(lvl, material, output, hammer, anvil, matTime);
	}

	private static void addHandpick(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, matTime, new Object[] {
			"AM",
			"PM",
			'P', haft(lvl),
			'M', material,
			});
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, matTime, new Object[] {
			"PM",
			"AM",
			'P', haft(lvl),
			'M', material,
			});
	}

	
	//KNIFE 0.5xMaterial
	private static void addKnife(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addKnife(lvl, material, output, hammer, anvil, matTime);
	}
	private static void addKnife(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*0.5F), new Object[] {
			"M",
			"M",
			"P",
			'P', haft(lvl),
			'M', material,
			});
	}
		
	//SHEARS 1xMaterial
	private static void addShears(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addShears(lvl, material, output, hammer, anvil, matTime);
	}
	private static void addShears(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, matTime, new Object[] {
			"PM",
			"MA",
			'P', haft(lvl),
			'M', material,
			});
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, matTime, new Object[] {
			"MA",
			"PM",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	
	
	//DAGGER 0.6xMaterial
	private static void addDagger(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		addDagger(lvl, material, output, hammer, anvil, matTime);
	}
	private static void addDagger(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*0.6F), new Object[] {
			"PMM",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	
	//MACE 1.5xMaterial
		private static void addMace(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
			{
				addMace(lvl, material, output, hammer, anvil, matTime);
			}
		}
		

		private static void addMace(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*1.5F), new Object[] {
				"AAMM",
				"PPMM",
				"AAAA",
				'P', haft(lvl),
				'M', material,
				});
			
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*1.5F), new Object[] {
				"AAAA",
				"PPMM",
				"AAMM",
				'P', haft(lvl),
				'M', material,
				});
		}
		
		
		
		//WARAXE 2.0xMaterial
		private static void addWaraxe(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
			{
				addWaraxe(lvl, material, output, hammer, anvil, matTime);
			}
		}
		private static void addWaraxe(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*1.5F), new Object[] {
				"AMM",
				"PPM",
				"AAM",
				'P', haft(lvl),
				'M', material,
				});
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*1.5F), new Object[] {
				"AAM",
				"PPM",
				"AMM",
				'P', haft(lvl),
				'M', material,
				});
		}
	
	
	
	//LONGSWORD 2xMaterial
	private static void addSword(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		addSword(lvl, material, output, hammer, anvil, matTime);
	}
	private static void addSword(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*2F), new Object[] {
			"AMAA",
			"PMMM",
			"AMAA",
			'P', haft(lvl),
			'M', material,
			});
	}
	//GREATSWORD 3.5xMaterial
	private static void addGreatsword(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		addGreatsword(lvl, material, output, hammer, anvil, matTime);
	}
	private static void addGreatsword(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.5F), new Object[] {
			"AMAAAA",
			"PMMMMM",
			"AMAAAA",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	//LANCE 7xMaterial
	private static void addLance(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addLance(lvl, material, output, hammer, anvil, matTime);
	}
	private static void addLance(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*7F), new Object[] {
			"  M     ",
			"MMPMMMMM",
			"  M     ",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	//BROADSWORD 2.5xMaterial
	private static void addBroadsword(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		{
			addBroadsword(lvl, material, output, hammer, anvil, matTime);
		}
	}
	private static void addBroadsword(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*2.5F), new Object[] {
			"AMMM",
			"PMMM",
			"AMAA",
			'P', haft(lvl),
			'M', material,
			});
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*2.5F), new Object[] {
			"AMAA",
			"PMMM",
			"AMMM",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	
	
	
	//MORNINGSTAR 1.8xMaterial
		private static void addGreatmace(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
			{
				addGreatmace(lvl, material, output, hammer, anvil, matTime);
			}		
		}
		private static void addGreatmace(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*1.8F), new Object[] {
				"AAAAAAA",
				"MPPPPMM",
				"AAAAAMM",
				'P', haft(lvl),
				'M', material,
				});
			
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*1.2F), new Object[] {
				"AAAAAMM",
				"MPPPPMM",
				"AAAAAAA",
				'P', haft(lvl),
				'M', material,
				});
		}
	
		//WARHAMMER 2.0xMaterial
		private static void addWarhammer(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
			{
				addWarhammer(lvl, material, output, hammer, anvil, matTime);
			}		
		}
		private static void addWarhammer(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*2.0F), new Object[] {
				"AAAAAAM",
				"MPPPPMM",
				"AAAAAMM",
				'P', haft(lvl),
				'M', material,
				});
			
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*1.5F), new Object[] {
				"AAAAAMM",
				"MPPPPMM",
				"AAAAAAM",
				'P', haft(lvl),
				'M', material,
				});
		}
	
	
	//SPEAR 2.5xMaterial
	private static void addSpear(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		{
			addSpear(lvl, material, output, hammer, anvil, matTime);
		}		
	}
	private static void addSpear(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*2.5F), new Object[] {
			"AAAAMA",
			"PPPPPM",
			"AAAAMA",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	//HALBEARD 3xMaterial
		private static void addHalbeard(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
			{
				addHalbeard(lvl, material, output, hammer, anvil, matTime);
			}		
		}
		private static void addHalbeard(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.0F), new Object[] {
				"AAAAMM",
				"PPPPPM",
				"AAAAMA",
				'P', haft(lvl),
				'M', material,
				});
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.0F), new Object[] {
				"AAAAMA",
				"PPPPPM",
				"AAAAMM",
				'P', haft(lvl),
				'M', material,
				});
		}
	
	
	//BATTLEAXE 4xMaterial
	private static void addBattleaxe(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		{
			addBattleaxe(lvl, material, output, hammer, anvil, matTime);
		}		
	}
	private static void addBattleaxe(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*4F), new Object[] {
			"AAAAMM",
			"MPPPPM",
			"AAAAMM",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	
	
	
	//SCYTHE 3.3xMaterial
	private static void addScythe(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		{
			addScythe(lvl, material, output, hammer, anvil, matTime);
		}		
	}
	private static void addScythe(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.3F), new Object[] {
			"AAAMA",
			"AAAAM",
			"AAPAM",
			"PPPPM",
			'P', haft(lvl),
			'M', material,
			});
		
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.3F), new Object[] {
			"PPPPM",
			"AAPAM",
			"AAAAM",
			"AAAMA",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	
	
	//RAKE 1.5xMaterial
		private static void addRake(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
			{
				addRake(lvl, material, output, hammer, anvil, matTime);
			}		
		}
		private static void addRake(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*1.5F), new Object[] {
				"AAAM",
				"PPMA",
				"AAAM",
				'P', haft(lvl),
				'M', material,
				});
		}
	
		//MATTOCK 2xMaterial
		private static void addMattock(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
			{
				addMattock(lvl, material, output, hammer, anvil, matTime);
			}		
		}
		private static void addMattock(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*2.0F), new Object[] {
				"AMA",
				"PPM",
				"AAM",
				'P', haft(lvl),
				'M', material,
				});
			
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*2.0F), new Object[] {
				"AAM",
				"PPM",
				"AMA",
				'P', haft(lvl),
				'M', material,
				});
		}
		
		//SAW 2.2xMaterial
		private static void addSaw(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
			{
				addSaw(lvl, material, output, hammer, anvil, matTime);
			}		
		}
		private static void addSaw(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*2.2F), new Object[] {
				"PMMMM",
				"PMMMA",
				'P', haft(lvl),
				'M', material,
				});
		}

	
	//WARPICK 2.8xMaterial
	private static void addWarpick(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		{
			addWarpick(lvl, material, output, hammer, anvil, matTime);
		}		
	}
	private static void addWarpick(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*2.8F), new Object[] {
			"AAMM",
			"PPMM",
			"AAAM",
			'P', haft(lvl),
			'M', material,
			});
		
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*2.8F), new Object[] {
			"AAAM",
			"PPMM",
			"AAMM",
			'P', haft(lvl),
			'M', material,
			});
	}
	
	
		//HOUND TEETH 1.5xMaterial
		private static void addTeeth(String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
				addTeeth(material, output, hammer, anvil, matTime);
		}
		private static void addTeeth(Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*1.5F), new Object[] {
				"MB",
				" M",
				"MB",
				'B', com(ItemListMF.leatherBelt),
				'M', material,
				});
		}
		
	//RECURVE BOW 2xMaterial
	private static void addRecurve(String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addRecurve(material, output, hammer, anvil, matTime);
	}
	private static void addRecurve(Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)((float)matTime*2F), new Object[] {
			"MSSM",
			"M  M",
			" LL ",
			'L', Item.leather,
			'S', Item.silk,
			'M', material,
			});
	}
	
	
	
	//BOLT 2.5xMaterial
	private static void addBolt(String ore, int output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addBolt(material, output, hammer, anvil, matTime);
	}
	private static void addBolt(Object material, int output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.boltMF, 8, output), false , hammer, anvil, (int)((float)matTime*2.5F), new Object[] {
			"M",
			"F",
			'F', com(ItemListMF.featherArrow),
			'M', material,
			});
	}
	
	
	//ARROWHEAD 1.0xMaterial
	private static void addArrowhead(String ore, int output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addArrowhead(material, output, hammer, anvil, matTime);
	}
	private static void addArrowhead(Object material, int output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(com(output, 16), false , hammer, anvil, (int)((float)matTime*1.0F), new Object[] {
			"M ",
			" M",
			"M ",
			
			'M', material,
			});
	}
	
	//BROADHEAD 2.0xMaterial
	private static void addBroadhead(String ore, int output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addBroadhead(material, output, hammer, anvil, matTime);
	}
	private static void addBroadhead(Object material, int output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(com(output, 16), false , hammer, anvil, (int)((float)matTime*2.0F), new Object[] {
			"  M ",
			"MMMM",
			"  M ",
			
			'M', material,
			});
	}
	
	
	//BODKINHEAD 1.5xMaterial
	private static void addBodkinhead(String ore, int output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addBodkinhead(material, output, hammer, anvil, matTime);
	}
	private static void addBodkinhead(Object material, int output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(com(output, 16), false , hammer, anvil, (int)((float)matTime*1.5F), new Object[] {
			"M  ",
			" MM",
			"M  ",
			
			'M', material,
			});
	}
	
	//TONGS 0.5xMaterial
	private static void addTongs(String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addTongs(material, output, hammer, anvil, matTime);
	}
	private static void addTongs(Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*0.5F), new Object[] {
			" M",
			"M ",
			'M', material,
			});
	}
	//HAMMER 1xMaterial
		private static void addHammer(int lvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
		{
			for(ItemStack material:OreDictionary.getOres(ore))
				addHammer(lvl, material, output, hammer, anvil, matTime);
		}
		private static void addHammer(int lvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*0.5F), new Object[] {
				"M",
				"P",
				
				'P', haft(lvl),
				'M', material,
				});
		}
		
	private static ItemStack haft(int level)
	{
		int type = 0;
		switch(level)
		{
		case 0:
			return new ItemStack(ItemListMF.plank);
		case 1:
			type = ItemListMF.haft;
			break;
		case 2:
			type = ItemListMF.haftStrong;
			break;
		case 3:
			type = ItemListMF.haftIronbark;
			break;
		case 4:
			type = ItemListMF.haftEbony;
			break;
		case 5:
			type = ItemListMF.haftOrnate;
			break;
		}
		return com(type);
	}
	
	
	private static void addFurnaces()
	{
		GameRegistry.addRecipe(new ItemStack(BlockListMF.furnace, 1, 0), new Object[] { 
			"SSS", 
			"S S", 
			"SFS",
			'S', Block.cobblestone,
			'F', BlockListMF.forge });
		
		addFurnace(0, "ingotBronze", new ItemStack(BlockListMF.furnace, 1, 1), 0, 0, bronzeTime);
		addFurnace(1, com(ItemListMF.ingotWroughtIron), new ItemStack(BlockListMF.furnace, 1, 2), 0, 1, ironTime);
		addFurnace(2, "ingotSteel", new ItemStack(BlockListMF.furnace, 1, 3), 0, 2, steelTime);
		addFurnace(3, "ingotDeepIron", new ItemStack(BlockListMF.furnace, 1, 4), 0, 3, deepIronTime);
		if(minefantasy.system.cfg.hardcoreObsidianForge){
			MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.forge, 1, 2), 0, 1, 1500, new Object[] 
			{
				" DDD ",
				"O C O",
				"OCCCO",
				"IOOOI",
				
				'D', Item.diamond,
				'O', Block.obsidian,
				'C', com(ItemListMF.HellCoal),
			});
			}
		else
		{
			MineFantasyAPI.addAnvilRecipe(new ItemStack(BlockListMF.forge, 1, 2), 0, 1, 1500, new Object[] 
			{
				" DDD ",
				"O C O",
				"OCCCO",
				"IOOOI",
						
				'D', Item.diamond,
				'O', Block.obsidian,
				'C', com(ItemListMF.infernoCoal),
			});
		}
	}
	
	
	
	private static void addMisc()
	{
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.ingotBronze), 0, 0, bronzeTime, new Object[] 
		{
			"II",
			"II",
			'I', com(ItemListMF.lumpBronze),
		});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.ingotWroughtIron), 0, 1, ironTime, new Object[] 
		{
			"II",
			"II",
			'I', com(ItemListMF.lumpIron),
		});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.ingotSteel), 0, 2, steelTime, new Object[] 
		{
			"II",
			"II",
			'I', com(ItemListMF.lumpSteel),
		});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.ingotDeepIron), 0, 3, deepIronTime, new Object[] 
		{
			"II",
			"II",
			'I', com(ItemListMF.lumpDeepIron),
		});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.ingotMithril), 0, 3, mithrilTime, new Object[] 
		{
			"II",
			"II",
			'I', com(ItemListMF.lumpMithril),
		});
		
		MineFantasyAPI.addShapelessAnvilRecipe(new ItemStack(ItemListMF.ingotSteel), true, 0, 0, 400, new Object[] {
			com(ItemListMF.nuggetSteel),com(ItemListMF.nuggetSteel),com(ItemListMF.nuggetSteel),
			});
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.buckle, 4), 0, 0, 400, new Object[] {
			" I ",
			"I I",
			" I ",
			'I', com(ItemListMF.lumpBronze),
			});
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.buckle, 6), 0, 1, 400, new Object[] {
			" I ",
			"I I",
			" I ",
			'I', com(ItemListMF.lumpIron),
			});
		
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.buckle, 8), 0, 2, 400, new Object[] {
			" I ",
			"I I",
			" I ",
			'I', com(ItemListMF.lumpSteel),
			});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.buckle, 10), 0, 3, 400, new Object[] {
			" I ",
			"I I",
			" I ",
			'I', com(ItemListMF.lumpDeepIron),
			});
		MineFantasyAPI.addAnvilRecipe(com(ItemListMF.buckle, 12), 0, 3, 400, new Object[] {
			" I ",
			"I I",
			" I ",
			'I', com(ItemListMF.lumpMithril),
			});
		
		
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.needleBronze), true, 0, 0, bronzeTime, new Object[] {
			"I",
			"I",
			"I",
			"I",
			'I', com(ItemListMF.lumpBronze),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.needleIron), true, 0, 1, ironTime, new Object[] {
			"I",
			"I",
			"I",
			"I",
			'I', com(ItemListMF.lumpIron),
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.needleSteel), true, 0, 2, steelTime, new Object[] {
			"I",
			"I",
			"I",
			"I",
			'I', com(ItemListMF.lumpSteel),
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.needleDeepIron), true, 0, 3, deepIronTime, new Object[] {
			"I",
			"I",
			"I",
			"I",
			'I', com(ItemListMF.lumpDeepIron),
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.needleMithril), true, 0, 3, mithrilTime, new Object[] {
			"I",
			"I",
			"I",
			"I",
			'I', com(ItemListMF.lumpMithril),
			});
	}
	
	private static void addBows()
	{
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.shortbow), 0, 0, 500, new Object[] {
			"PSSP",
			" PP ",
			'S', Item.silk,
			'P', ItemListMF.plank,
			});
		
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bowComposite), 0, 1, 1000, new Object[] {
			"BSSB",
			"P  P",
			" LL ",
			'B', Item.bone,
			'S', Item.silk,
			'P', ItemListMF.plank,
			'L', Item.leather,
			});
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bowIronbark), 0, 1, 1500, new Object[] {
			"PSSP",
			"P  P",
			" LL ",
			'S', Item.silk,
			'P', com(ItemListMF.plankIronbark),
			'L', Item.leather,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.bowEbony), 0, 1, 2000, new Object[] {
			"PSSP",
			"P  P",
			" LL ",
			'S', Item.silk,
			'P', com(ItemListMF.plankEbony),
			'L', Item.leather,
			});
		
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.longbow), 0, 0, 800, new Object[] {
			"PSSSP",
			"P   P",
			" PLP ",
			'S', Item.silk,
			'P', ItemListMF.plank,
			'L', Item.leather,
			});
	}
	
	private static void addOven(String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addOven(material, output, hammer, anvil, matTime);
	}

	private static void addOven(Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, false , hammer, anvil, matTime*5, new Object[] 
		{
			"MMM",
			"M M",
			"MMM",
			'M', material,
		});
	}
	
	private static void addFurnace(int stonelvl, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
			addFurnace(stonelvl, material, output, hammer, anvil, matTime);
	}

	private static void addFurnace(int stonelvl, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		Block[] stone = new Block[]{Block.cobblestone, BlockListMF.slate, BlockListMF.granite, Block.obsidian};
		MineFantasyAPI.addAnvilRecipe(output, false , hammer, anvil, matTime*8, new Object[] 
		{
			"MMM",
			"M M",
			"MMM",
			"SFS",
			'F', BlockListMF.forge,
			'S', stone[stonelvl],
			'M', material,
		});
	}
	
	private static void addRepair()
	{
		MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepair), true , 0, 1, ironTime*2, new Object[] 
		{
			"SM",
			"H ",
			'M', com(ItemListMF.ingotWroughtIron),
			'S', Item.slimeBall,
			'H', haft(1),
		});
		
		for(ItemStack metal: OreDictionary.getOres("ingotSteel"))
		{
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepair2), true , 0, 2, steelTime*2, new Object[] 
			{
				"SM",
				"H ",
				'M', metal,
				'S', Item.slimeBall,
				'H', haft(2),
			});
		}
		for(ItemStack metal: OreDictionary.getOres("ingotMithril"))
		{
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepairArtisan), true , 0, 3, mithrilTime*2, new Object[] 
			{
				"SM",
				"H ",
				'M', metal,
				'S', Item.slimeBall,
				'H', haft(3),
			});
		}
		for(ItemStack silver : OreDictionary.getOres("ingotSilver"))
		{
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepairOrnate), true , 1, 1, 700, new Object[] 
			{
				" L ",
				"SHS",
				" G ",
				'S', silver,
				'G', Item.ingotGold,
				'L', new ItemStack(Item.dyePowder, 1, 4),
				'H', ItemListMF.hammerRepair
			});
			
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepairOrnate2), true , 1, 2, 1400, new Object[] 
			{
				" L ",
				"SHS",
				" G ",
				'S', silver,
				'G', Item.ingotGold,
				'L', new ItemStack(Item.dyePowder, 1, 4),
				'H', ItemListMF.hammerRepair2
			});
			
			MineFantasyAPI.addAnvilRecipe(new ItemStack(ItemListMF.hammerRepairOrnateArtisan), true , 1, 3, 2100, new Object[] 
			{
				" L ",
				"SHS",
				" G ",
				'S', silver,
				'G', Item.ingotGold,
				'L', new ItemStack(Item.dyePowder, 1, 4),
				'H', ItemListMF.hammerRepairArtisan
			});
		}
	}
	
	/*
	 * PLATE ARMOUR
	 * Head  x2.5
	 * Body  x4
	 * Legs  x3.5
	 * Boots x3
	 */
	public static void addPlateAssembly(int plate, int curve, int padding, Item[] output, int hammer, int anvil, int time)
	{
		addPlateAssembly(com(plate),com(curve),com(padding), output, hammer, anvil, time);
	}
	public static void addPlateAssembly(ItemStack plate, ItemStack curve, ItemStack padding, Item[] output, int hammer, int anvil, int time)
	{
		//HELM
		MineFantasyAPI.addAnvilRecipe(new ItemStack(output[0]), hammer, anvil, (int)(time*2.5F), new Object[]
		{
			" pPp ",
        	"PCCCP",
            " C C ",
            
            'P', plate,
            'p', curve,
            'C', padding,
         });
		//CHEST
		MineFantasyAPI.addAnvilRecipe(new ItemStack(output[1]), hammer, anvil, (int)(time*4F), new Object[]
		{
			" p p ",
			"PC CP",
        	"PCCCP",
            "PCCCP",
            'P', plate,
            'p', curve,
            'C', padding,
         });
		//LEGS
		MineFantasyAPI.addAnvilRecipe(new ItemStack(output[2]), hammer, anvil, (int)(time*3.5F), new Object[]
		{
			" pPp ",
        	"PCCCP",
            "PC CP",
            " C C ",
            'P', plate,
            'p', curve,
            'C', padding,
         });
		//BOOTS
		MineFantasyAPI.addAnvilRecipe(new ItemStack(output[3]), hammer, anvil, (int)(time*2F), new Object[]
		{
        	" C C ",
            " C C ",
            "Pp pP",
            'P', plate,
            'p', curve,
            'C', padding,
         });
	}
	
	
	/*
	 * HOUND PLATE ARMOUR
	 * Head  x3
	 * Body  x5
	 */
	public static void addHoundPlate(int plate, int padding, Item[] output, int hammer, int anvil, int time)
	{
		addHoundPlate(com(plate),com(padding), output, hammer, anvil, time);
	}
	public static void addHoundPlate(ItemStack plate, ItemStack padding, Item[] output, int hammer, int anvil, int time)
	{
		//HELM
		MineFantasyAPI.addAnvilRecipe(new ItemStack(output[1]), hammer, anvil, (int)(time*3F), new Object[]
		{
			"  PP",
			" PCC",
        	"PCC ",
            
            'P', plate,
            'C', padding,
         });
		
		//BODY
		MineFantasyAPI.addAnvilRecipe(new ItemStack(output[0]), hammer, anvil, (int)(time*5F), new Object[]
		{
			"   PP",
			" PPCC",
			"CCCC ",
        	" C C ",
            
            'P', plate,
            'C', padding,
         });
	}
	
	
	//BUCKER 1xmaterial
	private static void addBuckler(String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		{
			addBuckler(material, output, hammer, anvil, matTime);
		}		
	}
	private static void addBuckler(Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*1.0F), new Object[] {
			" M ",
			"MLM",
			" M ",
			'L', Item.leather,
			'M', material,
			});
	}
	
	//KITE SHIELD 3xmaterial
	private static void addKite(int tier, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		{
			addKite(tier, material, output, hammer, anvil, matTime);
		}		
	}
	private static void addKite(int tier, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		if(tier == 0)
		{
			for(int a = 0; a < 16; a ++)
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.0F), new Object[] {
				"M M",
				"MWM",
				" M ",
				'W', new ItemStack(Block.planks, 1, a),
				'M', material,
				});
		}
		else
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*3.0F), new Object[] {
				"M M",
				"MWM",
				" M ",
				'W', new ItemStack(BlockListMF.planks, 1, tier-1),
				'M', material,
				});
		}
	}
	
	//TOWER SHIELD 5xmaterial
	private static void addTower(int tier, String ore, ItemStack output, int hammer, int anvil, int matTime)
	{
		for(ItemStack material:OreDictionary.getOres(ore))
		{
			addTower(tier, material, output, hammer, anvil, matTime);
		}		
	}
	private static void addTower(int tier, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		if(tier == 0)
		{
			for(int a = 0; a < 16; a ++)
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*5.0F), new Object[] {
				"MMM",
				"MWM",
				"MWM",
				"MMM",
				'W', new ItemStack(Block.planks, 1, a),
				'M', material,
				});
		}
		else
		{
			MineFantasyAPI.addAnvilRecipe(output, true , hammer, anvil, (int)(matTime*5.0F), new Object[] {
				"MMM",
				"MWM",
				"MWM",
				"MMM",
				'W', new ItemStack(BlockListMF.planks, 1, tier-1),
				'M', material,
				});
		}
	}
	
	private static void addRound(int tier, Object material, ItemStack output, int hammer, int anvil, int matTime)
	{
		Object[] glue = new Object[]
		{
				com(ItemListMF.glueWeak), com(ItemListMF.glueStrong)
		};
		
		MineFantasyAPI.addAnvilRecipe(output, false , hammer, anvil, (int)(matTime*5.0F), new Object[] {
			"PPP",
			"PGP",
			"PPP",
			'P', material,
			'G', glue[tier],
			});
	}
}
