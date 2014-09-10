package minefantasy.item;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.api.forge.IHotItem;
import minefantasy.api.weapon.ICustomDamager;
import minefantasy.block.BlockListMF;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemMedievalComponent extends ItemMedieval implements ICustomDamager {

    private int length = 190;
    private Icon[] icons;

	public ItemMedievalComponent(int i) 
	{
        super(i, false, 64);
        setCreativeTab(CreativeTabs.tabMaterials);
        setHasSubtypes(true);
        setMaxDamage(0);
    }
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        for (int n = 0; n < length ; ++n)
        {
        	ItemStack item = new ItemStack(id, 1, n);
        	if(!getUnlocalisedName(item).endsWith("unused"))
            list.add(item);
        }
    }

    @Override
    public Icon getIconFromDamage(int id)
    {
        return icons[id];

    }
    @Override
    public String getItemDisplayName(ItemStack item)
    {
    	return StatCollector.translateToLocal("item.mfmisc." + getUnlocalisedName(item));
    }
	public String getUnlocalisedName(ItemStack item)
    {
        int type = item.getItemDamage();
        if (type == 0) {
            return "flux";
        }
        if (type == 1) {
            return "splintBronze";
        }
        if (type == 2) {
            return "lumpIron";
        }
        if (type == 3) {
        	return "hidePig";
        }
        if (type == 4) {
        	return "leather.raw";
        }
        if (type == 5) {
        	return "leather.salt";
        }
        if (type == 6) {
        	return "hideSheep";
        }
        if (type == 7) {
        	return "leather.rough";
        }
        if (type == 8) {
        	return "leather.gore";
        }
        if (type == 9) {
        	return "padding";
        }
        if (type == 10) {
        	return "salt";
        }
        if (type == 11) {
        	return "slag";
        }
        if (type == 12) {
        	return "slagSmall";
        }
        if (type == 13) {
        	return "linkIron";
        }
        if (type == 14) {
            return "coke";
        }
        if (type == 15) {
            return "coalPowder";
        }
        if (type == 16) {
            return "paperSalt";
        }
        if (type == 17) {
            return "hideCow";
        }
        if (type == 18) {
            return "linkBronze";
        }
        if (type == 19) {
            return "fireGland";
        }
        if (type == 20) {
            return "fireDust";
        }
        if (type == 21) {
            return "ingotDragonImp";
        }
        if (type == 22) {
            return "ingotDragon";
        }
        if (type == 23) {
            return "featherArrow";
        }
        if (type == 24) {
            return "sulfur";
        }
        if (type == 25) {
            return "plankIronbark";
        }
        if (type == 26) {
            return "stripLeather";
        }
        if (type == 27) {
            return "beltLeather";
        }
        if (type == 28) {
            return "plateSteelSmall";
        }
        if (type == 29) {
            return "plateSteelCurve";
        }
        if (type == 30) {
            return "splintSteel";
        }
        if (type == 31) {
            return "scaleSteel";
        }
        if (type == 32) {
            return "unused";
        }
        
        if (type == 33) {
            return "hideHound";
        }
        if (type == 34) {
            return "unused";
        }
        if (type == 35) {
            return "unused";
        }
        
        if (type == 36) {
            return "scaleBronze";
        }
        if (type == 37) {
            return "scaleIron";
        }
        if (type == 38) {
            return "scaleMithril";
        }
        if (type == 39) {
            return "scaleDragonforge";
        }
        if (type == 40) {
            return "linkSteel";
        }
        if (type == 41) {
            return "chainSteel";
        }
        
        if (type == 42) {
            return "linkMithril";
        }
        if (type == 43) {
            return "chainMithril";
        }
        if (type == 44) {
            return "linkDragonforge";
        }
        
        if (type == 45) {
            return "chainDragonforge";
        }
        if (type == 46) {
            return "splintIron";
        }
        if (type == 47) {
            return "splintDragonforge";
        }
        if (type == 48) {
            return "coinGold";
        }
        if (type == 49) {
            return "coinSilver";
        }
        if (type == 50) {
            return "dustIgnotumite";
        }
        if (type == 51) {
            return "unused";
        }
        if (type == 52) {
            return "ingotIgnotumiteImpure";
        }
        if (type == 53) {
            return "oreMithril";
        }
        if (type == 54) {
            return "mithrilRefined";
        }
        if (type == 55) {
            return "ingotMithril";
        }
        if (type == 56) {
            return "ingotCopper";
        }
        if (type == 57) {
            return "ingotTin";
        }
        if (type == 58) {
            return "ingotBronze";
        }
        if (type == 59) {
            return "haft";
        }
        if (type == 60) {
            return "ingotWroughtIron";
        }
        if (type == 61) {
            return "plateIron";
        }
        if (type == 62) {
            return "splintMithril";
        }
        if (type == 63) {
            return "plateIronSmall";
        }
        if (type == 64) {
            return "coinCopper";
        }
        if (type == 65) {
            return "haftStrong";
        }
        if (type == 66) {
            return "limestone";
        }
        if (type == 67) {
            return "plateIronCurve";
        }
        
        if (type == 68) {
            return "plateEncrusted";
        }
        if (type == 69) {
            return "plateEncrustedSmall";
        }
        if (type == 70) {
            return "plateEncrustedCurve";
        }
        if (type == 71) {
            return "unused";
        }
        if (type == 72) {
            return "plateBronze";
        }
        if (type == 73) {
            return "plateBronzeSmall";
        }
        if (type == 74) {
            return "plateBronzeCurve";
        }
        if (type == 75) {
            return "unused";
        }
        if (type == 76) {
            return "chainBronze";
        }
        
        if (type == 77) {
            return "haftIronbark";
        }
        if (type == 78) {
            return "oreIron";
        }
        if (type == 79) {
            return "oreGold";
        }
        if (type == 80) {
            return "oreSilver";
        }
        if (type == 81) {
            return "haftOrnate";
        }
        if (type == 82) {
            return "oreCopper";
        }
        if (type == 83) {
            return "haftEbony";
        }
        if (type == 84) {
            return "oreTin";
        }
        if (type == 85) {
            return "leatherStud";
        }
        if (type == 86) {
            return "unused";
        }
        if (type == 87) {
            return "stickIronbark";
        }
        if (type == 88) {
            return "glueWeak";
        }
        if (type == 89) {
            return "glueStrong";
        }
        if (type == 90) {
            return "coalInferno";
        }
        if (type == 91) {
            return "coalHell";
        }
        if (type == 92) {
            return "coalSlow";
        }
        if (type == 93) {
            return "unused";
        }
        if (type == 94) {
            return "unused";
        }
        if (type == 95) {
            return "plateMithril";
        }
        if (type == 96) {
            return "plateMithrilSmall";
        }
        if (type == 97) {
            return "plateMithrilCurve";
        }
        
        if (type == 98) {
            return "hideMinotaur";
        }
        if (type == 99) {
            return "unused";
        }
        if (type == 100) {
            return "unused";
        }
        
        if (type == 101) {
            return "hideDrake";
        }
        if (type == 102) {
            return "unused";
        }
        if (type == 103) {
            return "unused";
        }
        if (type == 104) {
            return "leatherBlack";
        }
        if (type == 105) {
            return "crossbowMech";
        }
        if (type == 106) {
            return "crossbowMech.repeat";
        }
        if (type == 107) {
            return "crossbowBolts";
        }
        if (type == 108) {
            return "rockSharp";
        }
        if (type == 109) {
            return "tendon";
        }
        if (type == 110) {
            return "vine";
        }
        if (type == 111) {
            return "shale";
        }
        if (type == 112) {
            return "unused";
        }
        if (type == 113) {
            return "shardCopper";
        }
        if (type == 114) {
            return "plankEbony";
        }
        if (type == 115) {
            return "unused";
        }
        if (type == 116) {
            return "chainIron";
        }
        if (type == 117) {
            return "plateSteel";
        }
        if (type == 118) {
            return "plateDragonforge";
        }
        if (type == 119) {
            return "plateDragonforgeSmall";
        }
        if (type == 120) {
            return "plateDragonforgeCurve";
        }
        if (type == 121) {
            return "unused";
        }
        if (type == 122) {
            return "lumpBronze";
        }
        if (type == 123) {
            return "lumpSteel";
        }
        if (type == 124) {
            return "lumpMithril";
        }
        if (type == 125) {
            return "buckle";
        }
        if (type == 126) {
            return "stickEbony";
        }
        
        if (type == 127) {
            return "arrowheadBronze";
        }
        if (type == 128) {
            return "arrowheadIron";
        }
        if (type == 129) {
            return "arrowheadSteel";
        }
        if (type == 130) {
            return "arrowheadMithril";
        }
        if (type == 131) {
            return "arrowheadSilver";
        }
        if (type == 132) {
            return "arrowheadEncrusted";
        }
        if (type == 133) {
            return "arrowheadDragonforge";
        }
        if (type == 134) {
            return "arrowheadIgnotumite";
        }
        
        
        
        if (type == 135) {
            return "broadheadBronze";
        }
        if (type == 136) {
            return "broadheadIron";
        }
        if (type == 137) {
            return "broadheadSteel";
        }
        if (type == 138) {
            return "broadheadMithril";
        }
        if (type == 139) {
            return "broadheadSilver";
        }
        if (type == 140) {
            return "broadheadEncrusted";
        }
        if (type == 141) {
            return "broadheadDragonforge";
        }
        if (type == 142) {
            return "broadheadIgnotumite";
        }
        
        
        
        if (type == 143) {
            return "bodkinheadBronze";
        }
        if (type == 144) {
            return "bodkinheadIron";
        }
        if (type == 145) {
            return "bodkinheadSteel";
        }
        if (type == 146) {
            return "bodkinheadMithril";
        }
        if (type == 147) {
            return "bodkinheadSilver";
        }
        if (type == 148) {
            return "bodkinheadEncrusted";
        }
        if (type == 149) {
            return "bodkinheadDragonforge";
        }
        if (type == 150) {
            return "bodkinheadIgnotumite";
        }
        if (type == 151) {
            return "twine";
        }
        if (type == 152) {
            return "hunkIgnotumite";
        }
        if (type == 153) {
            return "ingotIgnotumite";
        }
        if (type == 154) {
            return "hideHorse";
        }
        if (type == 155) {
            return "unused";
        }
        if (type == 156) {
            return "unused";
        }
        if (type == 157) {
            return "hideBasilisk.blue";
        }
        if (type == 158) {
            return "unused";
        }
        if (type == 159) {
            return "unused";
        }
        if (type == 160) {
            return "hideBasilisk.brown";
        }
        if (type == 161) {
            return "unused";
        }
        if (type == 162) {
            return "unused";
        }
        if (type == 163) {
            return "hideBasilisk.black";
        }
        if (type == 164) {
            return "unused";
        }
        if (type == 165) {
            return "unused";
        }
        if (type == 166) {
            return "nuggetSteel";
        }
        if (type == 167) {
            return "ingotGoldPure";
        }
        if (type == 168) {
            return "scaleGuilded";
        }
        if (type == 169) {
            return "linkGuilded";
        }
        if (type == 170) {
            return "chainGuilded";
        }
        if (type == 171) {
            return "splintGuilded";
        }
        if (type == 172) {
            return "plateGuildedSmall";
        }
        if (type == 173) {
            return "plateGuilded";
        }
        if (type == 174) {
            return "plateGuildedCurve";
        }
        if (type == 175) {
            return "unused";
        }
        if (type == 176) {
            return "shrapnel";
        }
        if (type == 177) {
            return "nitre";
        }
        
        if (type == 178) {
            return "scaleDeepIron";
        }
        if (type == 179) {
            return "linkDeepIron";
        }
        if (type == 180) {
            return "chainDeepIron";
        }
        if (type == 181) {
            return "splintDeepIron";
        }
        if (type == 182) {
            return "plateDeepIron";
        }
        if (type == 183) {
            return "plateDeepIronCurve";
        }
        if (type == 184) {
            return "plateDeepIronSmall";
        }
        if (type == 185) {
            return "ingotDeepIron";
        }
        
        if (type == 186) {
            return "lumpDeepIron";
        }
        if (type == 187) {
            return "bodkinheadDeepIron";
        }
        if (type == 188) {
            return "broadheadDeepIron";
        }
        if (type == 189) {
            return "arrowheadDeepIron";
        }
        
        return "unused";
    }

    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null)
        {
            return item;
        }
        else
        {
            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
            {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k))
                {
                    return item;
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item))
                {
                    return item;
                }

                if (isWaterSource(world, i, j, k))
                {
                	if(cfg.hardcoreLeather)
                	{
                		tryClean(item, world, player, ItemListMF.leatherSalt, 2, new ItemStack(ItemListMF.misc, 1, ItemListMF.leatherRough));
                	}
                	else
                	{
                		tryClean(item, world, player, ItemListMF.leatherSalt, 2, new ItemStack(Item.leather));
                	}
                	//tryClean(item, world, player, ItemListMF.MoCFurSalt, 5, new ItemStack(ItemListMF.misc, 1, ItemListMF.MoCFurCured));
                	//tryClean(item, world, player, ItemListMF.MoCHideSalt, 5, new ItemStack(ItemListMF.misc, 1, ItemListMF.MoCHideCured));
                	//tryClean(item, world, player, ItemListMF.MoCReptileSalt, 5, new ItemStack(ItemListMF.misc, 1, ItemListMF.MoCReptileCured));
                }
                
                
                if(!world.isRemote && item.isItemEqual(ItemListMF.component(ItemListMF.slag)))
            	{
                	boolean full = false;
                	boolean placed = false;
                	if(world.getBlockId(i, j, k) == BlockListMF.slag.blockID)
                	{
	            		int meta = world.getBlockMetadata(i, j, k);
	            		if(meta < 15)
	            		{
	            			world.setBlockMetadataWithNotify(i, j, k, meta+1, 2);
	            			placed = true;
	            			if(!player.capabilities.isCreativeMode)
	                		{
	                			player.getHeldItem().stackSize --;
	                		}
                		}
	            		else
	            		{
	            			full = true;
	            		}
                	}
                	if(!placed && (full || !world.isAirBlock(i, j, k)))
                	{
                		if(world.isAirBlock(i, j+1, k))
                		{
                			world.setBlock(i, j+1, k, BlockListMF.slag.blockID);
                			
                			if(!player.capabilities.isCreativeMode)
	                		{
	                			player.getHeldItem().stackSize --;
	                		}
                		}
                	}
            	}
            }

            return item;
        }
    }
    
    private void tryClean(ItemStack item, World world, EntityPlayer player, int input, int chance, ItemStack result)
    {
    	if (item != null && item.getItemDamage() == input) {
            Random rand = new Random();
            player.swingItem();
            if (!world.isRemote) {
                world.playSoundAtEntity(player, "random.splash", 0.125F + rand.nextFloat()/4F, 0.5F + rand.nextFloat());
                if (rand.nextInt(chance) == 0) {
                    item.stackSize--;
                    EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ, result);
                    world.spawnEntityInWorld(resultItem);
                }
            }
        }
	}

    @Override
    public EnumRarity getRarity(ItemStack itemstack) 
    {
        switch (itemstack.getItemDamage()) 
        {
            case ItemListMF.fireGland:
                return EnumRarity.uncommon;
            case ItemListMF.ingotGoldPure:
                return EnumRarity.uncommon;
            case ItemListMF.fireExplosive:
                return EnumRarity.uncommon;
            case ItemListMF.ingotDragonImpure:
                return EnumRarity.uncommon;
            case ItemListMF.ingotDragon:
                return EnumRarity.rare;
            case ItemListMF.ignotDust:
                return EnumRarity.rare;
            case ItemListMF.ingotIgnotumite:
                return EnumRarity.rare;
            case ItemListMF.IgnotImpure:
                return EnumRarity.rare;
            case ItemListMF.hunkIgnotumite:
                return EnumRarity.rare;
            case ItemListMF.plankEbony:
                return EnumRarity.uncommon;
            case ItemListMF.stickEbony:
                return EnumRarity.uncommon;
        }
        return super.getRarity(itemstack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg)
    {
        this.icons = new Icon[length];

        for (int i = 0; i < length; ++i)
        {
            this.icons[i] = reg.registerIcon("minefantasy:Misc/MineFantasy_Component_" + i);
        }
    }
    
    public boolean onBlockDestroyed(ItemStack item, World world, int x, int y, int z, int meta, EntityLiving living)
    {
        if (x == Block.vine.blockID && isSharpRock(item))
        {
            return true;
        }
        else
        {
        	return super.onBlockDestroyed(item, world, x, y, z, meta, living);
        }
    }
	private boolean isSharpRock(ItemStack item) {
		return item.getItemDamage() == 108 || item.getItemDamage() == 113;
	}
	
	@Override
	public boolean canHarvestBlock(Block block, ItemStack item)
    {
		if(!isSharpRock(item))return false;
		
        return block.blockID == Block.vine.blockID;
    }
	
	@Override
    public boolean onBlockStartBreak(ItemStack item, int x, int y, int z, EntityPlayer living)
    {
    	if(!isSharpRock(item))
    	{
    		return super.onBlockStartBreak(item, x, y, z, living);
    	}
    	World world = living.worldObj;
    	ItemStack newdrop = null;
    	int id = world.getBlockId(x, y, z);
    	if(id == Block.vine.blockID)
    	{
    		newdrop = ItemListMF.component(ItemListMF.vine);
    	}
    	if(newdrop != null)
    	{
    		if(!world.isRemote)
    		{
    			for(int a = 1; a <= living.getRNG().nextInt(9); a ++)
    			dropBlockAsItem_do(world, x, y, z, newdrop);
    		}
    	}
        return super.onBlockStartBreak(newdrop, x, y, z, living);
    }
    
    protected void dropBlockAsItem_do(World world, int x, int y, int z, ItemStack item)
    {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            float f = 0.7F;
            double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, item);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }
    
    private boolean isWaterSource(World world, int i, int j, int k) {
		if(world.getBlockMaterial(i, j, k) == Material.water)
		{
			return true;
		}
		if(isCauldron(world, i, j, k))
		{
			return true;
		}
		return false;
	}
    
    public boolean isCauldron(World world, int x, int y, int z)
    {
    	return world.getBlockId(x, y, z) == Block.cauldron.blockID && world.getBlockMetadata(x, y, z)>0;
    }
	@Override
	public float getHitDamage(ItemStack item)
	{
		int type = item.getItemDamage();
    	if(type == 113)return 3.0F;
    	if(type == 108)return 2.0F;
    	
		return 1.0F;
	}
}
