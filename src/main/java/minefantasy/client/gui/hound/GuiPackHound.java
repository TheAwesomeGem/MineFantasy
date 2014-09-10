package minefantasy.client.gui.hound;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.MineFantasyBase;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerPackHound;
import minefantasy.entity.EntityHound;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiPackHound extends GuiContainer
{
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    private EntityPlayer user;
    private EntityHound hound;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;

    public GuiPackHound(EntityPlayer use, EntityHound dog, int rows)
    {
        super(new ContainerPackHound(use.inventory, dog.pack, rows));
        this.upperChestInventory = use.inventory;
        this.lowerChestInventory = dog.pack;
        this.allowUserInput = false;
        short var3 = 222;
        int var4 = var3 - 108;
        this.inventoryRows = rows;
        this.ySize = var4 + this.inventoryRows * 18;
        user = use;
        hound = dog;
    }

    @Override
    public void initGui() {
        
    	super.initGui();
    	
    	buttonList.clear();
        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        
	        this.buttonList.add(new GuiButtonHoundTab("", null, 0, xOffset+xSize, yOffset + ySize - 21, 1));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
	        case 0:
	        		PacketDispatcher.sendPacketToServer(PacketManagerMF.getHoundInv(hound, user, 0));
	            break;
        }
	}
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal(this.lowerChestInventory.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float sc, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/pack.png"));
        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        
        
        this.drawTexturedModalRect(xOffset, yOffset+1, 0, 0, this.xSize, 16);
        for(int a = 0; a < inventoryRows; a++)
        {
        	this.drawTexturedModalRect(xOffset, yOffset+17 + (a*18), 0, 18, this.xSize, 18);
        }
        this.drawTexturedModalRect(xOffset, yOffset + 16 + (inventoryRows*18), 0, 37, this.xSize, 96);
    }
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}
