package minefantasy.client.gui;


import minefantasy.MineFantasyBase;
import minefantasy.block.tileentity.TileEntitySmelter;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerCrucible;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.gui.inventory.GuiContainer;
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
public class GuiCrucible extends GuiContainer
{
    private TileEntitySmelter smelter;

    public GuiCrucible(InventoryPlayer inventoryplayer, TileEntitySmelter tile)
    {
    	super(new ContainerCrucible(inventoryplayer, tile));
        smelter = tile;
    }

    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    	if(!MineFantasyBase.isDebug())
    	{
	    	if(smelter.getTier() == 1)
	    	{
	    		this.fontRenderer.drawString(StatCollector.translateToLocal("container.crucible"), 60, 6, 4210752);
	    		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	    	}
	    	
	    	if(smelter.getTier() == 2)
	        	this.fontRenderer.drawString(StatCollector.translateToLocal("container.crucible"), 102, 6, 4210752);
	    }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(smelter.getTier() == 1)
        	bindTexture(data_minefantasy.image("/gui/alloySml.png"));
        else
        	bindTexture(data_minefantasy.image("/gui/alloy.png"));
        int posX = (width - xSize) / 2;
        int posY = (height - ySize) / 2;
        int offset = 0;
        if(smelter.getTier() == 2)
        {
        	offset = 9;
        }
        drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
        if (smelter.maxFuel > 0 && smelter.fuel > 0)
        {
            int j1 = smelter.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(posX + 8, (posY + 36 + 12) - j1, 176, 12 - j1, 14, j1 + 2);
        }
        int k1 = smelter.getCookProgressScaled(24);
        drawTexturedModalRect(posX + 100+offset, posY + 34, 176, 14, k1 + 1, 16);
        
        if(smelter.getTier() > 0)
        {
        	//HEAT
            int heatSc = this.smelter.getHeatScaled(68);
            if(heatSc > 0)
            this.drawTexturedModalRect(posX + 9, posY + 76 - heatSc, 190, 9, 10, 5);
        }
    }
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}