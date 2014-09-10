package minefantasy.client.gui;


import minefantasy.block.tileentity.TileEntityRoast;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerRoast;
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
public class GuiRoast extends GuiContainer
{
    private TileEntityRoast roast;

    public GuiRoast(InventoryPlayer inventoryplayer, TileEntityRoast tile)
    {
        super(new ContainerRoast(inventoryplayer, tile));
        roast = tile;
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(StatCollector.translateToLocal("tile.roast.name"), 60, 6, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/roast.png"));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        for(int a = 0; a < 5; a ++)
        {
        	int prog = this.roast.getCookProgressScaled(28, a);
        	if(prog > 0)
	        this.drawTexturedModalRect(x + 46 + (18*a), y + 4, 176, 0, 9, prog+1);
        }
    }
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}