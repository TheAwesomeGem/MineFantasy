package minefantasy.client.gui.hound;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.*;
import minefantasy.MineFantasyBase;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerHoundArmour;
import minefantasy.container.ContainerHoundStats;
import minefantasy.entity.EntityHound;
import minefantasy.entity.INameableEntity;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

//GuiHound
@SideOnly(Side.CLIENT)
public class GuiHoundStats extends GuiContainer implements IBaseHoundScreen
{
    private EntityHound hound;
    private EntityPlayer viewer;
    public Minecraft mc = Minecraft.getMinecraft();
    public static final DecimalFormat decimal_format = new DecimalFormat("#.#");
    
    public GuiHoundStats(EntityHound dog, EntityPlayer player)
    {
    	super(new ContainerHoundStats(dog));
    	xSize = 190;
        ySize = 166;
        this.hound = dog;
        viewer = player;
    }
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    	RenderHelper.disableStandardItemLighting();
    	drawInfo(x, y);
    	RenderHelper.enableStandardItemLighting();
    	Iterator iterator = this.buttonList.iterator();

        while (iterator.hasNext())
        {
            GuiButton guibutton = (GuiButton)iterator.next();

            if (guibutton.func_82252_a())
            {
                guibutton.func_82251_b(x - this.guiLeft, y - this.guiTop);
                break;
            }
        }
        
        RenderHelper.enableGUIStandardItemLighting();
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float scale, int x, int y)
    {
    	
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/Hound/Stats.png"));
        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xOffset, yOffset, 0, 0, this.xSize, this.ySize);
        drawButton(xOffset+20, yOffset-25, 3);
    }
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
    
    public void drawInfo(int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/Hound/Stats.png"));
        int xOffset = 0;//(this.width - this.xSize) / 2;
        int yOffset = 0;//(this.height - this.ySize) / 2;
        
        
        int xp = getExpScaled(93);
        if(xp > 93)xp = 93;
        drawTexturedModalRect(xOffset+85, yOffset+23, 0, 188, xp, 7);
        
        drawCentreString("Level " + hound.level, 132, 13, Color.BLACK.getRGB(), xOffset, yOffset, 6);
        drawCentreString(hound.xp + " / " + hound.xpMax, 132, 23, Color.WHITE.getRGB(), xOffset, yOffset, 6);
        
        drawString("Points", 11, 18, Color.BLACK.getRGB(), xOffset, yOffset);
        drawString("Att", 22, 42, Color.BLACK.getRGB(), xOffset, yOffset);
        drawString("Sta", 22, 84, Color.BLACK.getRGB(), xOffset, yOffset);
        drawString("End", 22, 126, Color.BLACK.getRGB(), xOffset, yOffset);
        
        drawString(decimal_format.format(hound.AtPoints), 58, 18, Color.WHITE.getRGB(), xOffset, yOffset);
        drawString(decimal_format.format(hound.strength), 58, 42, Color.WHITE.getRGB(), xOffset, yOffset);
        drawString(decimal_format.format(hound.stamina), 58, 84, Color.WHITE.getRGB(), xOffset, yOffset);
        drawString(decimal_format.format(hound.endurance), 58, 126, Color.WHITE.getRGB(), xOffset, yOffset);
        
        //STR BOX
        drawString("Base Damage:", 84, 39, Color.WHITE.getRGB(), xOffset, yOffset);
        drawString(decimal_format.format(hound.getBaseDamage(hound.strength)), 132, 48, Color.WHITE.getRGB(), xOffset, yOffset);
        
        drawString("Next Level:", 84, 57, Color.WHITE.getRGB(), xOffset, yOffset);
        	if(hound.strength < 100)
        drawString(decimal_format.format(hound.getBaseDamage(hound.strength+1)), 132, 66, Color.WHITE.getRGB(), xOffset, yOffset);
        	else
        drawString("N/A", 132, 66, Color.WHITE.getRGB(), xOffset, yOffset);
        	
        //STA BOX
        drawString("Hunger Decay:", 84, 81, Color.WHITE.getRGB(), xOffset, yOffset);
        drawString(decimal_format.format(hound.getHungerDecay(hound.stamina)/20) +"s", 132, 90, Color.WHITE.getRGB(), xOffset, yOffset);
        
        drawString("Next Level:", 84, 99, Color.WHITE.getRGB(), xOffset, yOffset);
        	if(hound.stamina < 100)
        drawString(decimal_format.format(hound.getHungerDecay(hound.stamina+1)/20) +"s", 132, 108, Color.WHITE.getRGB(), xOffset, yOffset);
        	else
        drawString("N/A", 132, 108, Color.WHITE.getRGB(), xOffset, yOffset);
        
        	
        //END BOX
        drawString("Health:", 84, 123, Color.WHITE.getRGB(), xOffset, yOffset);
        drawString(decimal_format.format(hound.getMaxHealth(hound.endurance)), 132, 132, Color.WHITE.getRGB(), xOffset, yOffset);
        
        drawString("Next Level:", 84, 141, Color.WHITE.getRGB(), xOffset, yOffset);
        	if(hound.endurance < 100)
        drawString(decimal_format.format(hound.getMaxHealth(hound.endurance+1)), 132, 150, Color.WHITE.getRGB(), xOffset, yOffset);
        	else
        drawString("N/A", 132, 150, Color.WHITE.getRGB(), xOffset, yOffset);
    }
    
    
    
    
	private void checkActive(int i, boolean flag) 
    {
    	if(!flag)return;
    	
    	int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        
        int barX = xOffset+(xSize/2) - 124 + 6;
        int barY = yOffset+ySize + 6;
        
        this.drawTexturedModalRect(barX + (24*i), barY, 0, 74, 22, 22);
	}
	@Override
    public void initGui() {
        
    	super.initGui();
    	buttonList.clear();
        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        //main
        this.buttonList.add(new GuiButtonHoundTab("Main", this, 0, xOffset, yOffset-20, 1));
        this.buttonList.add(new GuiButtonHoundAttribute("Click to level Attack", this, 1, xOffset+8, yOffset+35));
        this.buttonList.add(new GuiButtonHoundAttribute("Click to level Stamina", this, 2, xOffset+8, yOffset+77));
        this.buttonList.add(new GuiButtonHoundAttribute("Click to level Endurance", this, 3, xOffset+8, yOffset+119));
    }
    @Override
    protected void actionPerformed(GuiButton button) 
    {
    	if(hound == null)return;
    	
    	switch (button.id) 
    	{
        case 0:
            	viewer.openGui(MineFantasyBase.instance, 2, viewer.worldObj, hound.entityId,
						0, 0);
            break;
        case 1:
            if (hound.getOwnerName().equals(viewer.username)) {
            	hound.levelFromClient(0);
            }
            break;
        case 2:
            if (hound.getOwnerName().equals(viewer.username)) {
            	hound.levelFromClient(1);
            }
            break;
        case 3:
            if (hound.getOwnerName().equals(viewer.username)) {
            	hound.levelFromClient(2);
            }
            break;
    	}
        hound.onUpdate();
    }
    
    @Override
    public boolean doesGuiPauseGame()
    {
    	return false;
    }
    
    
    
    
    public void drawString(String message, int x, int y, int colour, int xOffset, int yOffset) {
        fontRenderer.drawString(message, xOffset + x, yOffset + y, colour);
    }

    private void addButton(int id, int x, int y, int xs, int ys, String name, int xOffset, int yOffset) {
        this.buttonList.add(new GuiButton(id, x+xOffset, y+yOffset, xs, ys, name));
    }
    
    public int getExpScaled(int i) {
		return (hound.xp * i) / hound.xpMax;
	}
    
    public void drawCentreString(String string, int x, int y, int colour, int xOffset, int yOffset, int size)
    {
    	drawString(string, x - ((string.length()/2)*size), y, colour, xOffset, yOffset);
    }
    
    @Override
	public void drawText(String[] name, int x, int y)
	{
		this.func_102021_a(Arrays.asList(name), x, y);
	}
    @Override
	public void drawText(String name, int x, int y)
	{
		this.drawCreativeTabHoveringText(name, x, y);
	}
    
    
    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int x, int y, float f)
    {
        super.drawScreen(x, y, f); //Buttons
    }
    
    private void drawButton(int x, int y, int icon)
    {
    	bindTexture(data_minefantasy.image("/gui/Hound/Main.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int yPos = 216;
        int xPos = 20*icon;

        this.drawTexturedModalRect(x, y, xPos, yPos, 20, 20);
    }
}
