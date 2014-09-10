package minefantasy.container;

import java.util.Iterator;

import minefantasy.entity.EntityHound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerHoundStats extends Container
{
    private EntityHound dog;
    private int lastStr = 0;
    private int lastSta = 0;
    private int lastEnd = 0;
    private int lastPt = 0;
    private int lastLvl = 0;

    public ContainerHoundStats(EntityHound hound)
    {
    }

    public void updateStats(ICrafting craft)
    {
        super.addCraftingToCrafters(craft);
        craft.sendProgressBarUpdate(this, 0, dog.strength);
        craft.sendProgressBarUpdate(this, 1, dog.stamina);
        craft.sendProgressBarUpdate(this, 2, dog.endurance);
        craft.sendProgressBarUpdate(this, 3, dog.AtPoints);
        craft.sendProgressBarUpdate(this, 4, dog.level);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        super.detectAndSendChanges();
        Iterator var1 = this.crafters.iterator();

        while (var1.hasNext())
        {
            ICrafting var2 = (ICrafting)var1.next();

            if (this.lastStr !=dog.strength)
            {
                var2.sendProgressBarUpdate(this, 0, dog.strength);
            }
            if (this.lastSta !=dog.stamina)
            {
                var2.sendProgressBarUpdate(this, 1, dog.stamina);
            }
            if (this.lastEnd !=dog.endurance)
            {
                var2.sendProgressBarUpdate(this, 2, dog.endurance);
            }
            if (this.lastPt !=dog.AtPoints)
            {
                var2.sendProgressBarUpdate(this, 3, dog.AtPoints);
            }
            if (this.lastLvl !=dog.level)
            {
                var2.sendProgressBarUpdate(this, 4, dog.level);
            }
        }

        this.lastStr = dog.strength;
        this.lastSta = dog.stamina;
        this.lastEnd = dog.endurance;
        this.lastPt = dog.AtPoints;
        this.lastLvl = dog.level;
    }

    @SideOnly(Side.CLIENT)
    public void updateStats(int id, int amount)
    {
        if (id == 0)
        {
            dog.strength = amount;
        }
        if (id == 1)
        {
            dog.stamina = amount;
        }
        if (id == 2)
        {
            dog.endurance = amount;
        }
        if (id == 3)
        {
            dog.AtPoints = amount;
        }
        if (id == 4)
        {
            dog.level = amount;
        }
    }
    
	public boolean canInteractWith(EntityPlayer user)
    {
		return dog.isUseableByPlayer(user);
    }
}
