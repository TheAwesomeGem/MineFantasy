package minefantasy.client.gui;


import minefantasy.block.tileentity.TileEntityFurnaceMF;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerFurnaceMF;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;



/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class GuiFurnaceMF extends GuiContainer
{
    private TileEntityFurnaceMF smelter;

    public GuiFurnaceMF(EntityPlayer play, InventoryPlayer inventoryplayer, TileEntityFurnaceMF tile)
    {
    	super(new ContainerFurnaceMF(play, inventoryplayer, tile));
        smelter = tile;
    }

    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    	this.fontRenderer.drawString(smelter.getInvName(), 60, 6, 4210752);
    	this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(smelter.isHeater())
        {
        	bindTexture(data_minefantasy.image("/gui/Heater.png"));
        }
        else
        {
        	bindTexture(data_minefantasy.image("/gui/bigFurnace.png"));
        }
        int posX = (width - xSize) / 2;
        int posY = (height - ySize) / 2;
        
        drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
        
        
        
        
        if (this.smelter.isBurning())
        {
        	if(smelter.isHeater())
        	{
	        	//FUEL
	            int fuelSc = this.smelter.getBurnTimeRemainingScaled(12);
	            if(smelter.fuel > 0)
	            this.drawTexturedModalRect(posX + 59, posY + 27 + 12 - fuelSc, 176, 12 - fuelSc, 14, fuelSc + 2);
	            
	            //HEATMAX
	            int heatScM = this.smelter.getItemHeatScaled(68);
	            this.drawTexturedModalRect(posX + 104, posY + 76 - heatScM, 176, 14, 16, 3);
	            
	            //HEAT
	            int heatSc = this.smelter.getHeatScaled(68);
	            this.drawTexturedModalRect(posX + 107, posY + 76 - heatSc, 176, 17, 10, 5);
        	}
        	else
        	{
        		if(smelter.progress > 0)
        		{
        			int prog = smelter.getCookProgressScaled(24);
        	        drawTexturedModalRect(posX + 76, posY + 34, 176, 0, prog + 1, 16);
        		}
        	}
        }
    }
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}