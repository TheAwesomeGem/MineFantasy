package minefantasy.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.ForgeSubscribe;

@SideOnly(Side.CLIENT)
public class HudHandlerMF 
{
	private MineFantasyHUD inGameGUI = new MineFantasyHUD();
	
	@ForgeSubscribe
	public void postRenderOverlay(RenderGameOverlayEvent.Post event) 
	{
		if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR)
		{
			inGameGUI.renderGameOverlay(event.partialTicks, event.mouseX, event.mouseY);
		}
	}
}
