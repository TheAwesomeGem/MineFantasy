package minefantasy.client.gui.hound;

import java.awt.Color;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.client.TextureHelperMF;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.potion.Potion;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class GuiButtonHoundAttribute extends GuiButton
{
	private String name = "";
	private IBaseHoundScreen gui;
	private Minecraft mc = Minecraft.getMinecraft();
	
	public GuiButtonHoundAttribute(String name, IBaseHoundScreen base, int id, int x, int y)
    {
		this(id, x, y);
		gui = base;
		this.name = name;
    }
    public GuiButtonHoundAttribute(int id, int x, int y)
    {
        super(id, x, y, 72, 22, "");
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int x, int y)
    {
        if (this.drawButton)
        {
        	field_82253_i = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            bindTexture(data_minefantasy.image("/gui/Hound/Stats.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int yPos = 166;
            int xPos = 0;
            
            if (field_82253_i)
            {
                xPos += this.width;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, this.width, this.height);
            
        }
    }
    
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
	@Override
    public void func_82251_b(int par1, int par2)
    {
    	if(gui != null && name != null)
    	{
    		this.gui.drawText(name, par1, par2);
    	}
    }
}
