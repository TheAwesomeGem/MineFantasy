package minefantasy.client.gui;

import minefantasy.block.tileentity.TileEntityForge;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerForge;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiForge extends GuiContainer
{
    private TileEntityForge theForge;

    public GuiForge(InventoryPlayer invPlayer, TileEntityForge forge)
    {
        super(new ContainerForge(invPlayer, forge));
        this.theForge = forge;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.mfForge"), 60, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 32, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float scale, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/forge.png"));
        int posX = (this.width - this.xSize) / 2;
        int posY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(posX, posY, 0, 0, this.xSize, this.ySize);
        int remScale;

        if(this.theForge.fuel > 0)
        {
        	remScale = this.theForge.getBurnTimeRemainingScaled(12);
            if(theForge.fuel > 0)
            this.drawTexturedModalRect(posX + 26, posY + 27 + 12 - remScale, theForge.isBurning() ? 176 : 190, 12 - remScale, 14, remScale + 2);
            
        }
        if (this.theForge.isBurning())
        {
            //HEATMAX
            int heatScM = this.theForge.getItemHeatScaled(68);
            this.drawTexturedModalRect(posX + 6, posY + 76 - heatScM, 176, 14, 16, 3);
            
            //HEAT
            int heatSc = this.theForge.getHeatScaled(68);
            this.drawTexturedModalRect(posX + 9, posY + 76 - heatSc, 176, 17, 10, 5);
        }
    }
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}
