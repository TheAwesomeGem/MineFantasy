package minefantasy.client.gui.hound;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import minefantasy.MineFantasyBase;
import minefantasy.entity.EntityHound;
import minefantasy.system.network.PacketManagerMF;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiHoundRename extends GuiScreen{

	private EntityPlayer user;
	private World worldObj;
	private EntityHound pet;
	private GuiTextField field;
	
	public GuiHoundRename(EntityPlayer player, World world, EntityHound hound)
	{
		user = player;
		worldObj = world;
		pet = hound;

	}
	@Override
    public boolean doesGuiPauseGame()
    {
    	return false;
    }
	
	public void initGui()
    {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        
        int yPos = -20;
        int gap = 20;
        int size = 98;
        
        this.field = new GuiTextField(this.fontRenderer, this.width / 2 - 104, this.height/2 + yPos, 208, 24);
        this.field.setTextColor(-1);
        this.field.setSelectionPos(-1);
        this.field.setEnableBackgroundDrawing(true);
        this.field.setMaxStringLength(28);
		if(pet != null && pet.getName() != null)
		field.setText(pet.getName());
        this.buttonList.add(new GuiButton(0, this.width / 2 - size - (gap/2), this.height/2 + yPos+40, 98, 20, StatCollector.translateToLocal("gui.done")));
        this.buttonList.add(new GuiButton(1, this.width / 2 + (gap/2), this.height/2 + yPos+40, 98, 20, StatCollector.translateToLocal("gui.cancel")));
        
    }
	
	protected void keyTyped(char key, int num)
    {
        if (this.field.textboxKeyTyped(key, num))
        {
            this.mc.thePlayer.sendQueue.addToSendQueue(new Packet250CustomPayload("MC|ItemName", this.field.getText().getBytes()));
        }
        else
        {
            super.keyTyped(key, num);
        }
    }
	
	protected void mouseClicked(int x, int y, int z)
    {
        super.mouseClicked(x, y, z);
        this.field.mouseClicked(x, y, z);
    }
	
    public void drawScreen(int x, int y, float scale)
    {
    	super.drawScreen(x, y, scale);
	    GL11.glDisable(GL11.GL_LIGHTING);
        this.field.drawTextBox();
    }
	protected void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
            if (button.id == 0)
            {
            	setHoundNamePacket();
            	user.openGui(MineFantasyBase.instance, 2, pet.worldObj, pet.entityId,
						0, 0);
            }
            
            if (button.id == 1)
            {
            	user.openGui(MineFantasyBase.instance, 2, pet.worldObj, pet.entityId,
						0, 0);
            }
        }
    }
	/**
	 * Sends a packet to update the name
	 */
	private void setHoundNamePacket()
	{
		try {
			
			Packet packet = PacketManagerMF.getEntityRenamePacket(pet, field.getText());
			PacketDispatcher.sendPacketToServer(packet);
			FMLCommonHandler.instance().getMinecraftServerInstance()
					.getConfigurationManager()
					.sendPacketToAllPlayers(packet);
		} catch (NullPointerException e) {
			;
		}
	}
}
