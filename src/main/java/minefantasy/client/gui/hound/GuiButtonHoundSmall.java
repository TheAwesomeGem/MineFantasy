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
public class GuiButtonHoundSmall extends GuiButton
{
	private int icon;
	private String[] text;
	private IBaseHoundScreen gui;
	private Minecraft mc = Minecraft.getMinecraft();
	
	public GuiButtonHoundSmall(String name, IBaseHoundScreen base, int id, int x, int y, int index)
    {
		this(new String[]{name}, base, id, x, y, index);
    }
	public GuiButtonHoundSmall(String[] name, IBaseHoundScreen base, int id, int x, int y, int index)
    {
		this(id, x, y, index);
		gui = base;
		this.text = name;
    }
    public GuiButtonHoundSmall(int id, int x, int y, int index)
    {
        super(id, x, y, 10, 10, "");
        this.drawButton = false;
        icon = index;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int x, int y)
    {
        if (this.drawButton)
        {
        	field_82253_i = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            bindTexture(data_minefantasy.image("/gui/Hound/Command.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int yPos = 96;

            if (field_82253_i)
            {
                yPos += this.height;
            }
            int xPos = 10*icon;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, this.width, this.height);
            
        }
    }
    
    private void bindTexture(String image) 
	{
		this.mc .renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
    @Override
    public void func_82251_b(int x, int y)
    {
    	if(gui != null && text != null)
    	{
    		y -= ((text.length -1) * 4);
    		this.gui.drawText(text, x, y);
    	}
    }
}
