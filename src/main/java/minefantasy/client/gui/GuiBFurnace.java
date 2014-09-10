package minefantasy.client.gui;


import minefantasy.MineFantasyBase;
import minefantasy.block.tileentity.TileEntityBFurnace;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerBFurnace;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
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
public class GuiBFurnace extends GuiContainer
{
    private TileEntityBFurnace furn;
    private int type;

    public GuiBFurnace(int meta, EntityPlayer entityplayer, TileEntityBFurnace tile)
    {
        super(new ContainerBFurnace(meta, entityplayer, tile));
        type = meta;
        furn = tile;
    }

    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    	if(!MineFantasyBase.isDebug())
    	{
    		String s = this.furn.isInvNameLocalized() ? this.furn.getInvName() : I18n.getString(this.furn.getInvName());
            this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		    this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    	}
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/blast/" + furn.getTexture() + ".png"));
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        
        if (type == 2) // FUEL
        {
            int j1 = furn.getBurnTimeRemainingScaled(12);
            if(furn.isBurning() && furn.fuel > 0)
            drawTexturedModalRect(l + 80, (i1 + 36 + 12) - j1, 176, 12 - j1, 14, j1 + 2);
            
            int progBar = furn.getHeatProgressScaled(160);
            
            if(furn.heat > 0 && furn.heat < furn.requiredHeat)
            {
            	String message = "Heating...";
            	fontRenderer.drawString(message, l+76-(message.length()), i1 + 14, 4210752);
            	
            	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                bindTexture(data_minefantasy.image("/gui/blast/" + furn.getTexture() + ".png"));
                drawTexturedModalRect(l + 8, i1 + 22, 0, 166, 160, 8);
                if(progBar >0)
                drawTexturedModalRect(l + 8, i1 + 22, 0, 174, progBar, 8);
            }
            if(furn.heat <= 0)
            {
            	String message = "Not Heated";
            	fontRenderer.drawString(message, l+76-(message.length()), i1 + 14, 4210752);
            }
        }
        if(type == 1)//INPUT
        {
        	int k1 = furn.getCookProgressScaled(24);
        	drawTexturedModalRect(l + 103, i1 + 34, 176, 0, k1 + 1, 16);
        }
    }
    
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}