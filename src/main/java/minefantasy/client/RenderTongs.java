package minefantasy.client;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
import minefantasy.MineFantasyBase;
import minefantasy.api.forge.TongsHelper;
import minefantasy.item.I2HWeapon;
import minefantasy.item.ItemHotItem;
import minefantasy.item.ItemListMF;
import minefantasy.item.tool.ItemTongs;
import minefantasy.system.cfg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.Color;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderTongs implements IItemRenderer {

    RenderItem renderItem = new RenderItem();
    
    public RenderTongs()
    {
    }
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type.equals(ItemRenderType.EQUIPPED) || type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack tongs, Object... data) 
    {
    	Minecraft mc = FMLClientHandler.instance().getClient();
    	
    	for(int a = 0; a < tongs.getItem().getRenderPasses(0); a ++)
    	{
    		Icon ic = tongs.getItem().getIcon(tongs, a);
    		int colour = tongs.getItem().getColorFromItemStack(tongs, a);
    		if(ic != null && (a == 1 || TongsHelper.getHeldItem(tongs) != null))
    		{
    			renderItemIn3D(tongs, ic, colour, a == 0);
    		}
    	}
    }
    private void renderItemIn3D(ItemStack stack, Icon icon, int colour, boolean held) {
        GL11.glPushMatrix();
        
        float red = (float)(colour >> 16 & 255) / 255.0F;
        float green = (float)(colour >> 8 & 255) / 255.0F;
        float blue = (float)(colour & 255) / 255.0F;
        
        GL11.glColor4f(red, green, blue, 1.0F);
        
        ItemStack item = stack;
    	if(ItemHotItem.getItem(stack) != null)
    	{
    		item = ItemHotItem.getItem(stack);
    	}
    	
    	float scale = 1.0F;
        Minecraft mc = FMLClientHandler.instance().getClient();
        mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        Tessellator tessellator = Tessellator.instance;
        float x1 = icon.getMinU();
        float x2 = icon.getMaxU();
        float y1 = icon.getMinV();
        float y2 = icon.getMaxV();
        
        float xOffset = 0.15F+(0.5F * (scale - 1));
        float yOffset = 0.15F-(0.5F * (scale - 1));
        
        if(held)
        {
        	xOffset += 0.5F;
        }
        float xPos = 0.0F+xOffset-yOffset;
        float yPos = 0.3F-xOffset-yOffset;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-xPos, -yPos, 0.0F);
        GL11.glScalef(scale, scale, 1);
        ItemRenderer.renderItemIn2D(tessellator, x2, y1, x1, y2, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);

        if (item != null && item.isItemEnchanted()) {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            mc.renderEngine.bindTexture(TextureHelperMF.ITEM_GLINT);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            float var13 = 0.76F;
            GL11.glColor4f(0.5F * var13, 0.25F * var13, 0.8F * var13, 1.0F);
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            float var14 = 0.125F;
            GL11.glScalef(var14, var14, var14);
            float var15 = (float) (System.currentTimeMillis() % 3000L) / 3000.0F * 8.0F;
            GL11.glTranslatef(var15, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, x2, y1, x1, y2, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(var14, var14, var14);
            var15 = (float) (System.currentTimeMillis() % 4873L) / 4873.0F * 8.0F;
            GL11.glTranslatef(-var15, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, x2, y1, x1, y2, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);

        GL11.glPopMatrix();

    }
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}
    
}