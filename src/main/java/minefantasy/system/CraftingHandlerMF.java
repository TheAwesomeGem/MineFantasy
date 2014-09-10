package minefantasy.system;

import minefantasy.api.armour.EnumArmourClass;
import net.minecraft.item.*;
import minefantasy.block.BlockListMF;
import minefantasy.block.special.ItemBlockAnvil;
import minefantasy.item.*;
import minefantasy.item.tool.ItemPrimitivePick;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandlerMF implements ICraftingHandler{

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,
			IInventory craftMatrix) {
		if(item == null || item.getItem() == null)
		{
			return;
		}
		
		for(ItemStack copper : OreDictionary.getOres("ingotCopper"))
		{
			if(item.isItemEqual(copper))
			{
				player.addStat(StatListMF.copper, 1);
			}
		}
		if(item.getItem() instanceof ItemHoe)
		{
			player.addStat(AchievementList.buildHoe, 1);
		}
		if(item.getItem() instanceof ItemPickaxe)
		{
			player.addStat(AchievementList.buildPickaxe, 1);
		}
		if(item.getItem() instanceof ItemPrimitivePick)
		{
			player.addStat(AchievementList.buildPickaxe, 1);
		}
		if(item.getItem() instanceof ItemSword)
		{
			player.addStat(AchievementList.buildSword, 1);
		}
		
		
		if(item.isItemEqual(new ItemStack(BlockListMF.smelter, 1, 1)))
		{
			player.addStat(StatListMF.alloy, 1);
		}
		if(item.isItemEqual(new ItemStack(BlockListMF.tanner)))
		{
			player.addStat(StatListMF.tanner, 1);
		}
		if(item.getItem() instanceof ItemBlockAnvil)
		{
			player.addStat(StatListMF.anvil, 1);
		}
		if(item.getItem() instanceof ItemBombMF)
		{
			player.addStat(StatListMF.bomb, 1);
		}
		if(item.isItemEqual(new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotDragon)))
		{
			player.addStat(StatListMF.dragonforger, 1);
		}
		if(item.getItem() instanceof ItemArmourMFOld)
		{
			if(((ItemArmourMFOld)item.getItem()).getArmourClass() == EnumArmourClass.PLATE)
			player.addStat(StatListMF.plate, 1);
		}
		if(item.getItem() instanceof IPublicMaterialItem)
		{
			if(((IPublicMaterialItem)item.getItem()).getMaterial() == ToolMaterialMedieval.ENCRUSTED)
			player.addStat(StatListMF.encrust, 1);
		}
		if(item.getItem() instanceof ItemArrowMF)
		{
			System.out.println("Arrow Craft");
			if(((ItemArrowMF)item.getItem()).isBroad(item))
			{
				System.out.println("Broad Arrow Craft");
				player.addStat(StatListMF.arrow, 1);
			}
		}
		
	}

	private ItemStack getCopyBook(IInventory craftMatrix) {
		return null;
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		if(item.isItemEqual(new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotCopper)))
		{
			player.addStat(StatListMF.copper, 1);
		}
		if(item.isItemEqual(new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotTin)))
		{
			player.addStat(StatListMF.tin, 1);
		}
		if(item.isItemEqual(new ItemStack(ItemListMF.misc, 1, ItemListMF.ingotBronze)))
		{
			player.addStat(StatListMF.bronze, 1);
		}
		if(item.itemID == Item.ingotIron.itemID)
		{
			player.addStat(StatListMF.iron, 1);
		}
		if(item.itemID == ItemListMF.ingotSteel.itemID)
		{
			player.addStat(StatListMF.steel, 1);
		}
	}

}
