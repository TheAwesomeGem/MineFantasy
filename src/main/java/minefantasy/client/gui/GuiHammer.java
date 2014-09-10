package minefantasy.client.gui;


import minefantasy.block.tileentity.TileEntityTripHammer;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerHammer;
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
public class GuiHammer extends GuiContainer
{
    private TileEntityTripHammer inv;

    public GuiHammer(InventoryPlayer inventoryplayer, TileEntityTripHammer tile)
    {
        super(new ContainerHammer(inventoryplayer, tile));
        inv = tile;
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(StatCollector.translateToLocal("block.triphammer"), 60, 6, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/hammer.png"));
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
    }
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}