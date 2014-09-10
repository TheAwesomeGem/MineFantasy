package minefantasy.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.MineFantasyBase;
import minefantasy.api.hound.IHoundEquipment;
import minefantasy.entity.EntityHound;
import minefantasy.item.ItemHoundArmourMF;
import minefantasy.item.ItemHoundPackMF;
import net.minecraft.block.Block;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class SlotHoundArmour extends Slot
{
    /**
     * The armor type that can be placed on that slot, it uses the same values of armorType field on ItemArmor.
     */
    final int armourType;

    EntityHound hound;
    /**
     * The parent class of this clot, ContainerPlayer, SlotArmor is a Anon inner class.
     */
    final ContainerHoundArmour parent;

    SlotHoundArmour(ContainerHoundArmour container, EntityHound inventory, int id, int x, int y, int piece)
    {
        super(inventory, id, x, y);
        hound = inventory;
        this.parent = container;
        this.armourType = piece;
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return 1;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack item)
    {
    	if(hound.isChild())return false;

       if(item == null)
    	   return false;
       
       if(item.getItem() instanceof IHoundEquipment)
       {
    	   IHoundEquipment equipment = (IHoundEquipment)item.getItem();
    	   
    	   if(!MineFantasyBase.isDebug())
    	   {
    		   if(!hound.canEquip(equipment))
    		   {
    			   return false;
    		   }
    	   }
    	   
    	   return equipment.getPiece() == armourType;
       }
       
       return false;
    }
}
