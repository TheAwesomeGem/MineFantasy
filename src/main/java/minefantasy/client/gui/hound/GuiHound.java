package minefantasy.client.gui.hound;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.*;
import minefantasy.MineFantasyBase;
import minefantasy.client.TextureHelperMF;
import minefantasy.container.ContainerHoundArmour;
import minefantasy.entity.EntityHound;
import minefantasy.entity.INameableEntity;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

//GuiHoundStats
@SideOnly(Side.CLIENT)
public class GuiHound extends GuiContainer implements IBaseHoundScreen
{
    private EntityHound hound;
    public Minecraft mc = Minecraft.getMinecraft();
    private EntityPlayer viewer;
    public static final DecimalFormat decimal_format = new DecimalFormat("#.#");
    public static final DecimalFormat percent = new DecimalFormat("#");
	
    public GuiHound(EntityHound dog, EntityPlayer player)
    {
        super(new ContainerHoundArmour(player, dog));
        this.hound = dog;
        viewer = player;
    }
    
    @Override
    public void drawScreen(int x, int y, float f)
    {
        super.drawScreen(x, y, f); //Buttons
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
    protected void drawGuiContainerBackgroundLayer(float scale, int x, int y)
    {
    	
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/Hound/Main.png"));
        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xOffset, yOffset, 0, 0, this.xSize, this.ySize);
        
        if(!hound.getOwnerName().equals(viewer.username))
        {
        	this.drawTexturedModalRect(xOffset+116, yOffset+36, 176, 7, 47, 21);
        }
        drawButton(xOffset, yOffset-25, 1);
        
        bindTexture(data_minefantasy.image("/gui/Hound/Command.png"));
        if(hound.showCommands)
        {
        	this.drawTexturedModalRect(xOffset+175, yOffset-1, 0, 74, 22, 22);
        	this.drawTexturedModalRect(xOffset+(xSize/2) - 124, yOffset+ySize, 0, 0, 248, 33);
        	
        	checkActive(0, hound.attackMob);
            checkActive(1, hound.attackAnimal);
            checkActive(2, hound.attackPlayer);
            checkActive(3, hound.attackDefense);
            checkActive2(-10, 11, hound.fightPvp);
            
            checkActive(5, hound.leapAttack);
            checkActive(6, hound.boostStep);
            checkActive(7, hound.pickupItems);
        }
        
    }
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
    
    public void drawInfo(int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(data_minefantasy.image("/gui/Hound/Main.png"));

        int xOffset = 0;
        int yOffset = 0;
		//Bars
        
        //HEALTH//
        GL11.glPushMatrix();
        int hp = getHealScaled(64);
        if(hp > 64)hp = 64;
        GL11.glColor3f(192, 0, 0);
        drawTexturedModalRect(xOffset+21, yOffset+51, 176, 0, hp, 7);
        GL11.glPopMatrix();
        
        //Hunger//
        GL11.glPushMatrix();
        int hu = getHungerScale(64);
        if(hu > 64)hu = 64;
        GL11.glColor3f(0, 192, 0);
        drawTexturedModalRect(xOffset+21, yOffset+64, 176, 0, hu, 7);
        GL11.glPopMatrix();
        
        boolean isOwner = false;
        
        if(hound.getOwnerName() != null && viewer != null)
        {
        	isOwner = hound.getOwnerName().equals(viewer.username);
        }
        
        /**
         * Buttons
         * 0: Rename
         * 1: Idle
         * 2: Stay
         * 3: Follow
         * 4: Open Commands
         * 5: Inventory
         * 6: Attack Mob
         * 7: Attack Animal
         * 8: Attack Player
         * 9: Defend
         * 10: DefendPvp
         *11: Stats Tab
         */
        
        ((GuiButton)buttonList.get(0)).drawButton = isOwner;//Rename
        ((GuiButton)buttonList.get(4)).drawButton = isOwner;//Command
        ((GuiButton)buttonList.get(5)).drawButton = 
        	(isOwner && hound.getAvailableRows() > 0);//Inv
        for(int a = 1; a <= 3; a ++)
        {
        	if(buttonList.get(a) != null)
        	((GuiButton)buttonList.get(a)).drawButton = hound.showCommands && isOwner;
        }
        for(int a = 6; a <= 10; a ++)
        {
        	if(buttonList.get(a) != null)
        	((GuiButton)buttonList.get(a)).drawButton = hound.showCommands && isOwner;
        }
        for(int a = 12; a <= 16; a ++)
        {
        	if(buttonList.get(a) != null)
        	((GuiButton)buttonList.get(a)).drawButton = hound.showCommands && isOwner;
        }
        ((GuiButton)buttonList.get(11)).drawButton = true;
        
        if(!hound.hasUnlockedLeap())
        {
        	((GuiButton)buttonList.get(12)).drawButton = false;
        }
        if(!hound.hasUnlockedPickup())
        {
        	((GuiButton)buttonList.get(13)).drawButton = false;
        }
        if(!hound.hasUnlockedBoost())
        {
        	((GuiButton)buttonList.get(14)).drawButton = false;
        }
        if(!hound.hasUnlockedTeleport())
        {
        	((GuiButton)buttonList.get(15)).drawButton = false;
        	((GuiButton)buttonList.get(16)).drawButton = false;
        }
        
        if(buttonList.get(17) != null)
        	((GuiButton)buttonList.get(17)).drawButton = hound.showCommands && isOwner;
        
        int c = Color.WHITE.getRGB();
        if (isOwner) {
            c = 0x009900;
        }
        
        drawString("Owner:", 6, 20, Color.WHITE.getRGB(), xOffset, yOffset);
        drawString(hound.getOwnerName(), 42, 20, c, xOffset, yOffset);
        
        drawString(hound.getEntityName(), 6, 6, Color.WHITE.getRGB(), xOffset, yOffset);
        drawString(hound.getCommand(), 21, 37, Color.WHITE.getRGB(), xOffset, yOffset);
        
        String AC = percent.format(this.hound.getACDisplayPercent()*100F) + StatCollector.translateToLocal("%");
                
        drawString(AC, 137, 61, Color.BLACK.getRGB(), xOffset, yOffset);
        drawString(decimal_format.format(hound.getBiteDamage(null)), 137, 73, Color.BLACK.getRGB(), xOffset, yOffset);
        
        //HEALTH//
        drawCentreString((int)(hound.getDisplayHealth()) + " / " + (int)(hound.getMaxHealth()), 52, 51, Color.WHITE.getRGB(), xOffset, yOffset, 6);
      //HUNGER//
        drawCentreString((int)(hound.getHunger()) + " / " + (int)(hound.getMaxHunger()), 52, 64, Color.WHITE.getRGB(), xOffset, yOffset, 6);
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
    private void checkActive2(int x, int y, boolean flag) 
    {
    	if(!flag)return;
    	
    	int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        
        int barX = xOffset+(xSize/2) - 125;
        int barY = yOffset+ySize + 6;
        
        this.drawTexturedModalRect(barX + x, barY + y, 22, 84, 12, 12);
	}
	@Override
    public void initGui() {
        
    	super.initGui();
    	buttonList.clear();
        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        
        //Rename
        this.buttonList.add(new GuiButtonHoundTab(false, "Rename", this, 0, xOffset-20, 3+yOffset, 4));
        
        int bX = 32;
        int bY = 17;
        
        //Commands
        addButton(1, 175, 22, bX, bY, "Idle", xOffset, yOffset, false);
        addButton(2, 175, 22 + bY, bX, bY, "Stay", xOffset, yOffset, false);
        addButton(3, 175, 22 + (bY*2), bX, bY, "Follow", xOffset, yOffset, false);
        
        //Commands
        this.buttonList.add(new GuiButtonHoundTab(false, "Open Commands", this, 4, xOffset+176, yOffset-0, 2));
        
        //Chest
        this.buttonList.add(new GuiButtonHoundTab(false, "Inventory", this, 5, xOffset+95, yOffset+37, 5));
        
        int barX = xOffset+(xSize/2) - 124;
        int barY = yOffset+ySize;
        
        int gap = 24;
        //Abilities
        this.buttonList.add(new GuiButtonHoundPower("Attack Monsters", this, 6, barX + 7, barY + 7, 0));
        this.buttonList.add(new GuiButtonHoundPower("Attack Animals", this, 7, barX + 31, barY + 7, 1));
        this.buttonList.add(new GuiButtonHoundPower("Attack Players", this, 8, barX + 55, barY + 7, 2));
        this.buttonList.add(new GuiButtonHoundPower("Protect Owner", this, 9, barX + 79, barY + 7, 3));
        this.buttonList.add(new GuiButtonHoundSmall("Enable PvP", this, 10, barX - 10, barY + 18, 0));
        
        //Stats
        this.buttonList.add(new GuiButtonHoundTab(true, "Stats", this, 11, xOffset+20, yOffset-20, 3));
        
        this.buttonList.add(new GuiButtonHoundPower(new String[]{"Leap Attack", 
        		"The hound will",
        		"sometimes leap",
        		"at the target"}
        , this, 12, barX + 7+(gap*5), barY + 7, 4));
        
        this.buttonList.add(new GuiButtonHoundPower(new String[]{"Collect Items",
        		"If a pack is  ",
        		"worn, the hound",
        		"will collect items"}
        		,this, 13, barX + 7+(gap*7), barY + 7, 5));
        
        this.buttonList.add(new GuiButtonHoundPower(new String[]{"Step Boost",
        		"The hound can step",
        		"1 block high but",
        		"gets hungry 10%",
        		"faster"}
        		,this, 14, barX + 7+(gap*6), barY + 7, 6));
        
        this.buttonList.add(new GuiButtonHoundPower(new String[]{"Teleport Home",
        		"The hound will",
        		"return home",
        		"(default: spawn)"}
        		,this, 15, barX + 7+(gap*8), barY + 7, 7));
        
        this.buttonList.add(new GuiButtonHoundPower(new String[]{"Set Home",
        		"Sets where teleport",
        		"will take the hound"}
        		,this, 16, barX + 7+(gap*9), barY + 7, 8));
        
        this.buttonList.add(new GuiButtonHoundSmall("Disengage", this, 17, barX - 10, barY + 6, 1));
    }
    @Override
    protected void actionPerformed(GuiButton button) {
    	if(hound == null)return;
    	
        switch (button.id) {
	        case 0:
	            if (hound.getOwnerName().equals(viewer.username)) {
	            	viewer.openGui(MineFantasyBase.instance, 2, viewer.worldObj, hound.entityId,
							0, 1);
	            }
	            break;
            case 1:
                if (hound.getOwnerName().equals(viewer.username)) {
                    hound.commandFromClient(0);
                }
                break;
            case 2:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.commandFromClient(1);
                }
                break;
            case 3:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.commandFromClient(2);
                }
                break;
            case 4:
                if (hound.getOwnerName().equals(viewer.username)) {
                    hound.showCommands = !hound.showCommands;
                }
                break;
            case 5:
	            if (hound.getAvailableRows() > 0) {
	            	PacketDispatcher.sendPacketToServer(PacketManagerMF.getHoundInv(hound, viewer, 2));
	            }
	            break;
            case 6:
                if (hound.getOwnerName().equals(viewer.username)) {
                    hound.usePower(0);
                }
                break;
            case 7:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.usePower(1);
                }
                break;
            case 8:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.usePower(2);
                }
                break;
            case 9:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.usePower(3);
                }
                break;
            case 10:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.usePower(4);
                }
                break;
            case 11:
	            	viewer.openGui(MineFantasyBase.instance, 2, viewer.worldObj, hound.entityId,
							0, 3);
	            break;
	            
	            
	            //POWERS
	            
            case 12:
                if (hound.getOwnerName().equals(viewer.username)) {
                    hound.usePower(5);
                }
                break;
            case 13:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.usePower(6);
                }
                break;
            case 14:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.usePower(7);
                }
                break;
            case 15:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.usePower(8);
                }
                break;
            case 16:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.usePower(9);
                }
                break;
            case 17:
                if (hound.getOwnerName().equals(viewer.username)) {
                	hound.usePower(10);
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

    private void addButton(int id, int x, int y, int xs, int ys, String name, int xOffset, int yOffset, boolean active) {
        GuiButton button = new GuiButton(id, x+xOffset, y+yOffset, xs, ys, name);
        button.drawButton = active;
    	this.buttonList.add(button);
    }
    
    public int getHealScaled(int i) 
    {
		return (int) ((hound.getDisplayHealth() * i) / hound.getMaxHealth());
	}
    public int getHungerScale(int i) {
		return (int)( (hound.getHunger() * i) / hound.getMaxHunger() );
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
    
    
    private void drawButton(int x, int y, int icon)
    {
    	bindTexture(data_minefantasy.image("/gui/Hound/Main.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int yPos = 216;
        int xPos = 20*icon;

        this.drawTexturedModalRect(x, y, xPos, yPos, 20, 20);
    }
}
