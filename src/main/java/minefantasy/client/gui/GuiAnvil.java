package minefantasy.client.gui;

import minefantasy.MineFantasyBase;
import minefantasy.block.tileentity.TileEntityAnvil;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerAnvil;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;



public class GuiAnvil extends GuiContainer
{
    private TileEntityAnvil anvil;

    public GuiAnvil(InventoryPlayer invPlayer, TileEntityAnvil tile)
    {
        super(new ContainerAnvil(invPlayer, tile));
        this.anvil = tile;
        if(tile.isBig())
    	{
    		this.ySize = 204;
    	}
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mX, int mY)
    {
    	if(!MineFantasyBase.isDebug())
    	{
	    	if(!anvil.isBig())
	    	{
	    		String s = this.anvil.isInvNameLocalized() ? this.anvil.getInvName() : I18n.getString(this.anvil.getInvName());
	            this.fontRenderer.drawString(s, this.xSize / 2 + 50 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	    	}
	        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 104, this.ySize - 96 + 2, 4210752);
    	}
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float Scale, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/" + anvil.getGui() + ".png"));
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int prog;

        if(anvil.isBig())
        {
        	prog = this.anvil.getForgeProgressScaled(24);
	        this.drawTexturedModalRect(var5 + 55, var6 + 102, 176, 0, prog + 1, 16);
        }
        else
        {
	        prog = this.anvil.getForgeProgressScaled(24);
	        this.drawTexturedModalRect(var5 + 109, var6 + 34, 176, 0, prog + 1, 16);
        }
        
        if(cfg.advancedAnvil && anvil.canCraft())
        {
        	bindTexture(data_minefantasy.image("/gui/anvil.png"));
        	this.drawTexturedModalRect(var5, var6-8, 0, 166, this.xSize, 13);
        	
        	int quality = anvil.getQualityPosScaled(anvil.quality, xSize-8);
        	int max = anvil.getQualityPosScaled(anvil.getQualityPeak(), xSize-8);
        	
        	this.drawTexturedModalRect(var5+4+quality, var6-7, 5, 179, 5, 11);
        	this.drawTexturedModalRect(var5+4+max, var6-7, 10, 179, 5, 5);
        	this.drawTexturedModalRect(var5+1, var6-14, 0, 179, 5, 12);
        }
    }

	private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}
