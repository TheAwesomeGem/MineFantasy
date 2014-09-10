package minefantasy.client.gui;


import minefantasy.block.tileentity.TileEntitySmelter;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerBloomery;
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
public class GuiBloomery extends GuiContainer
{
    private TileEntitySmelter furnaceInventory;

    public GuiBloomery(InventoryPlayer inventoryplayer, TileEntitySmelter tile)
    {
    	super(new ContainerBloomery(inventoryplayer, tile));
        furnaceInventory = tile;
    }

    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    	this.fontRenderer.drawString(StatCollector.translateToLocal("container.bloomery"), 60, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/Bloomery.png"));
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        if (furnaceInventory.isBurning())
        {
            int j1 = furnaceInventory.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(l + 56, (i1 + 29 + 12) - j1, 185, 12 - j1, 14, j1 + 2);
        }
        int k1 = furnaceInventory.getCookProgressScaled(26);
        drawTexturedModalRect(l + 96, i1 + 15, 176, 0, 8, k1+1);
    }
    
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}