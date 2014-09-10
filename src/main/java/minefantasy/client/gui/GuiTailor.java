package minefantasy.client.gui;

import minefantasy.MineFantasyBase;
import minefantasy.block.tileentity.TileEntityTailor;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerTailor;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;



public class GuiTailor extends GuiContainer
{
    private TileEntityTailor tailor;

    public GuiTailor(InventoryPlayer invPlayer, TileEntityTailor tile)
    {
        super(new ContainerTailor(invPlayer, tile));
        this.ySize = 179;
        this.tailor = tile;
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mX, int mY)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("tile.tailor.name"), 28, 6, 4210752);
       if(!MineFantasyBase.isDebug())
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 124, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float Scale, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/tailor.png"));
        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xOffset, yOffset, 0, 0, this.xSize, this.ySize);
        int prog;

	    prog = this.tailor.getProgressScale(24);
	    this.drawTexturedModalRect(xOffset + 115, yOffset + 48, 176, 0, prog + 1, 16);
    }
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}
