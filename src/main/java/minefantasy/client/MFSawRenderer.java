package minefantasy.client;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
import minefantasy.item.I2HWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;

public class MFSawRenderer implements IItemRenderer {

    RenderItem renderItem = new RenderItem();
    private float scale;
    
    public MFSawRenderer()
    {
    	this(1.0F);
    }
    public MFSawRenderer(float Sc)
    {
    	scale = Sc;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type.equals(ItemRenderType.EQUIPPED);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
            ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
    	Minecraft mc = FMLClientHandler.instance().getClient();
        if (type.equals(ItemRenderType.EQUIPPED)) 
        {
        	EntityLivingBase player = null;
        	for(int a = 0; a < data.length; a ++)
        	{
        		if(data[a] instanceof EntityLivingBase)
        		{
        			player = (EntityLivingBase)data[a];
        		}
        	}
        	
        	renderWeaponAsItem(item, player);
        } 

    }

    private void renderFirstPerson(ItemStack item, EntityLivingBase player) {
    	GL11.glPushMatrix();
    	GL11.glTranslatef(-1.2F, 1.3F, 0.0F);
    	boolean flying = false;
    	if(player instanceof EntityPlayer)
    	{
    		flying = ((EntityPlayer)player).capabilities.isFlying;
    	}
    	if(player.fallDistance > 3 && !flying)
    	{
    		GL11.glRotatef(90, 1, 0, 0);
    		GL11.glTranslatef(0.25F, 0, 0.8F);
    	}
    	
        renderWeaponAsItem(item, player);

        GL11.glPopMatrix();
    }

    private void renderWeapon(ItemStack item, EntityLivingBase player) {


        GL11.glTranslatef(-0.87F, -0.2F, 0.0F);
        
        GL11.glRotatef(45, 0, 0, 1);
        	
        renderWeaponAsItem(item, player);


    }
    
    private boolean isFirstPerson(EntityLivingBase player) {
    	Minecraft mc = FMLClientHandler.instance().getClient();
		return mc.gameSettings.thirdPersonView == 0 && player == mc.thePlayer && !(mc.currentScreen instanceof GuiInventory);
	}
    
    private void renderWeaponAsItem(ItemStack item, EntityLivingBase player)
    {
        GL11.glPushMatrix();
        
        Icon icon = item.getIconIndex();
        Minecraft mc = FMLClientHandler.instance().getClient();
        mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        Tessellator tessellator = Tessellator.instance;
        float x1 = icon.getMinU();
        float x2 = icon.getMaxU();
        float y1 = icon.getMinV();
        float y2 = icon.getMaxV();
        
        float xOffset = 0.05F+(0.5F * (scale - 1));
        float yOffset = 0.35F-(0.5F * (scale - 1));
        float xPos = 0.0F+xOffset-yOffset;
        float yPos = 0.3F-xOffset-yOffset;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-xPos, -yPos, 0.0F);
        GL11.glScalef(scale, scale, 1);
        
        if(player == null)
        {
        	GL11.glTranslatef(0.6F, -0.20F, 0);
        	GL11.glRotatef(90, 0, 0, 1);
        }
        
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
}