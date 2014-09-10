package minefantasy.client.gui.hound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.client.TextureHelperMF;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiButtonHoundTab extends GuiButton
{
	private int icon;
	private String name;
	private Minecraft mc= Minecraft.getMinecraft();
	private IBaseHoundScreen gui;
	
	public GuiButtonHoundTab(String title, IBaseHoundScreen base, int id, int x, int y, int index)
    {
		this(true, title, base, id, x, y, index);
    }
    public GuiButtonHoundTab(boolean active, String title, IBaseHoundScreen base, int id, int x, int y, int index)
    {
        super(id, x, y, 20, 20, "");
        this.drawButton = active;
        gui = base;
        icon = index;
        name = title;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int x, int y)
    {
        if (this.drawButton)
        {
            bindTexture(data_minefantasy.image("/gui/Hound/Main.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            field_82253_i = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            int yPos = 216;

            if (field_82253_i)
            {
                yPos += this.height;
            }
            int xPos = 20*icon;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, this.width, this.height);
        }
    }
    
    @Override
    public void func_82251_b(int par1, int par2)
    {
    	if(gui != null && name != null)
        this.gui.drawText(name, par1, par2);//GuiHound
    }
    private void bindTexture(String image) 
	{
		this.mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
}
