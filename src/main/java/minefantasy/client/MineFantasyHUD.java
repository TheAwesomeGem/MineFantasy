package minefantasy.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.api.IMFCrafter;
import cpw.mods.fml.relauncher.Side;
import minefantasy.block.tileentity.TileEntityAnvil;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class MineFantasyHUD extends Gui
{
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public void renderGameOverlay(float partialTicks, int mouseX, int mouseY) 
	{
		if(cfg.disableHUD) return;
		
		GL11.glColor3f(255, 255, 255);
		int[] coords = getClickedBlock(partialTicks, mouseX, mouseY);
		if(coords == null)return;
		
		int x = coords[0];
		int y = coords[1];
		int z = coords[2];
		EntityPlayer player = mc.thePlayer;
		World world = player.worldObj;
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile != null && tile instanceof IMFCrafter)
		{
			this.renderCraftMetre(world, player, (IMFCrafter)tile);
		}
		if(cfg.advancedAnvil && tile != null && tile instanceof TileEntityAnvil)
		{
			this.renderAnvil(world, player, (TileEntityAnvil)tile);
		}
	}
	
	/*
	 * DEFAULT GRID W:427 H:240
	 */
	private void renderCraftMetre(World world, EntityPlayer player, IMFCrafter tile) 
	{
		if(!tile.shouldRenderCraftMetre())
		{
			return;
		}
		GL11.glPushMatrix();
		ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();
        
        bindTexture(data_minefantasy.image("/gui/craftMetre.png"));
        int xPos = width/2 + cfg.craftMetreX-48;
        int yPos = height - cfg.craftMetreY;
        
        this.drawTexturedModalRect(xPos, yPos, 0, 0, 96, 16);
        this.drawTexturedModalRect(xPos+4, yPos+4, 0, 17, tile.getProgressBar(88), 9);
        
        String s = tile.getResultName();
        mc.fontRenderer.drawString(s, xPos + 48 - (mc.fontRenderer.getStringWidth(s) / 2), yPos+5, 0);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        
        GL11.glPopMatrix();
	}
	
	
	private void renderAnvil(World world, EntityPlayer player, TileEntityAnvil anvil) 
	{
		if(!anvil.shouldRenderCraftMetre())
		{
			return;
		}
		
		GL11.glPushMatrix();
		ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        bindTexture(data_minefantasy.image("/gui/craftMetre.png"));
        int xPos = width/2 + cfg.craftMetreX - 48;
        int yPos = height - cfg.craftMetreY + 16;

        if(anvil.canCraft())
        {
	        this.drawTexturedModalRect(xPos, yPos, 0, 34, 96, 10);
	        
	        int quality = anvil.getQualityPosScaled(anvil.quality, 88);
	    	int max = anvil.getQualityPosScaled(anvil.getQualityPeak(), 88);
	    	
	    	this.drawTexturedModalRect(xPos + 4 + quality, yPos, 5, 45, 5, 11);
	    	this.drawTexturedModalRect(xPos +4 + max, yPos, 10, 45, 5, 5);
	    	this.drawTexturedModalRect(xPos +1, yPos-7, 0, 45, 5, 12);
        }	
        GL11.glPopMatrix();
	}


	public int[] getClickedBlock(float ticks, int mouseX, int mouseY)
	{
		if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
        {
            int j = mc.objectMouseOver.blockX;
            int k = mc.objectMouseOver.blockY;
            int l = mc.objectMouseOver.blockZ;
            
            return new int[]{j, k, l};
        }
		return null;
	}
	public void bindTexture(String image) 
	{
		mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}
