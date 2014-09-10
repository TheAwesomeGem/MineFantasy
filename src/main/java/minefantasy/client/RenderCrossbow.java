package minefantasy.client;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
import minefantasy.api.weapon.EnumAmmo;
import minefantasy.item.weapon.ItemCrossbow;
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
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderCrossbow implements IItemRenderer {

	private float scale;
    RenderItem renderItem = new RenderItem();

    public RenderCrossbow(float sc)
    {
    	scale = sc;
    }
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type.equals(ItemRenderType.EQUIPPED) || type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON);
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
        	for(int ic = 0; ic < 2; ic ++)
        	{
	             renderWeapon(item, player, ic);
        	}
        }
        if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON)) 
        {
        	EntityLivingBase player = null;
        	for(int a = 0; a < data.length; a ++)
        	{
        		if(data[a] instanceof EntityLivingBase)
        		{
        			player = (EntityLivingBase)data[a];
        		}
        	}
        	for(int ic = 0; ic < 2; ic ++)
        	{
	            renderWeapon(item, player, ic);
        	}
        }
    }

    private void renderFirstPerson(ItemStack item, EntityPlayer player, int ic) {
    	GL11.glPushMatrix();
    	//if(item.getItem() == MFP_RenItem.bayonet)
        	//GL11.glTranslatef(0.25F, 0, 0);
    	GL11.glTranslatef(-0.4F, 0.2F, 0F);
    	if(player.isUsingItem() && item.getItemUseAction() == EnumAction.bow)
    		GL11.glTranslatef(0.1F, -0.1F, 0F);
    	if(player.isUsingItem() && item.getItemUseAction() == EnumAction.block)
    	{
    		GL11.glRotatef(90, 0, 0, 1);
    		GL11.glTranslatef(-1.0F, -1.0F, 0F);
    	}
        renderWeapon(item, player, ic);
        GL11.glPopMatrix();
    }

    private void renderWeapon(ItemStack item, EntityLivingBase player, int ic) {

        GL11.glPushMatrix();

        GL11.glTranslatef(0.75F, -0.25F, 0F);
        GL11.glTranslatef(0f, 0f, 0);

        if(player == null)
        {
        	GL11.glRotatef(-90, 1, 1, 0);
        	GL11.glTranslatef((12F/16F), -(2F/16F), (8F/16F));
        }
        renderWeaponAsItem(item, ic);
        
        GL11.glPopMatrix();

    }
    
    private void renderWeaponAsItem(ItemStack item, int ic) 
    {
    	GL11.glPushMatrix();
    	float x = -0.35F;
    	float y = 0.5F;
    	
    	ItemCrossbow bow = (ItemCrossbow)item.getItem();
    	if(scale == 2.0F)
    	{
    		x -= 0.625F;
    		y -= 0.425F;
    	}
    	if(ic == 1)
        {
	    	y += bow.headYoffset();
	    	x += bow.headXoffset();
        }
        GL11.glTranslatef(x, y, 0);
        if(ic == 1)
        {
        	GL11.glTranslatef(0, -0.1F, 0);
        	GL11.glScalef(scale, scale, scale);
        }
        else
        {
        	GL11.glScalef(scale, scale, 1.0F);
        	GL11.glTranslatef(0F, 0F, 0.0625F/2F);
        }
        Minecraft mc = FMLClientHandler.instance().getClient();
        if(item != null)
        mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        
        if(ic == 1)
        {
        	GL11.glRotatef(90, 1, 1, 0);
        }


        Tessellator tessellator = Tessellator.instance;
        ItemCrossbow crossbow = (ItemCrossbow)item.getItem();
        Icon icon = ic == 0 ? crossbow.getStockItem(item) : crossbow.getHeadItem(item);
        renderIcon(mc, item, tessellator, icon);
        
        if(ic == 1)
        {
	        for(int slot = 0; slot <= crossbow.cap; slot ++)
	        {
	        	GL11.glPushMatrix();
	        	float m = 0.01F;
	        	if(slot % 2 == 0)
	        	{
	        		m = 0;
	        	}
	        	
	        	if(crossbow.ammoItem == EnumAmmo.ARROW)
	        	{
	        		GL11.glTranslatef(0.2F-m, 0.7F-m, -(slot+1)/64F-(1F/32F));
	        	}
	        	else
	        	{
	        		GL11.glTranslatef(0.1F-m+(4F/16F), 0.35F-m+(4F/16F), -(slot+1)/64F-(1F/32F));
	        	}
	        	
	        	float ammoSize = crossbow.ammoItem == EnumAmmo.ARROW ? 1.0F : 0.5F;
	        	
	        	
	        	GL11.glScalef(0.5F, 0.5F, 0.5F);
	        	GL11.glRotatef(90F, 0F, 0F, -1F);
	        	ItemStack loaded = crossbow.getNextShot(item, slot);
	        	
	        	if(loaded != null)
	        	{
	        		Icon bolt = loaded.getIconIndex();
	                renderIcon(mc, item, tessellator, bolt, ammoSize);
	        	}
	        	GL11.glPopMatrix();
	        }
        }
        GL11.glPopMatrix();

    }

    private void renderIcon(Minecraft mc, ItemStack item, Tessellator tessellator, Icon icon) 
    {
    	renderIcon(mc, item, tessellator, icon, 1.0F);
    }
    private void renderIcon(Minecraft mc, ItemStack item, Tessellator tessellator, Icon icon, float scale) 
    {
    	float x1 = icon.getMinU();
        float x2 = icon.getMaxU();
        float y1 = icon.getMinV();
        float y2 = icon.getMaxV();
        float var10 = 0.0F;
        float var11 = 0.3F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        /*
        GL11.glTranslatef(-var10, -var11, 0.0F);
        float var12 = 1.5F;
        GL11.glScalef(var12, var12, var12);
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
         */


        GL11.glScalef(scale, scale, scale);
        ItemRenderer.renderItemIn2D(tessellator, x2, y1, x1, y2, (int)(icon.getIconWidth()), (int)(icon.getIconHeight()), 0.0625F);

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
            ItemRenderer.renderItemIn2D(tessellator, x2, y1, x1, y2, (int)(icon.getIconWidth()*scale), (int)(icon.getIconHeight()*scale), 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(var14, var14, var14);
            var15 = (float) (System.currentTimeMillis() % 4873L) / 4873.0F * 8.0F;
            GL11.glTranslatef(-var15, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, x2, y1, x1, y2, (int)(icon.getIconWidth()*scale), (int)(icon.getIconHeight()*scale), 0.0625F);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);

	}
	public static void draw2dItem(Tessellator tessellator, float f, float f1, float f2, float f3, float thickness, float res, float resSq) {
        float f4 = 1.0F;
        float f5 = 0.0625F * thickness;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, f, f3);
        tessellator.addVertexWithUV(f4, 0.0D, 0.0D, f2, f3);
        tessellator.addVertexWithUV(f4, 1.0D, 0.0D, f2, f1);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, f, f1);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - f5, f, f1);
        tessellator.addVertexWithUV(f4, 1.0D, 0.0F - f5, f2, f1);
        tessellator.addVertexWithUV(f4, 0.0D, 0.0F - f5, f2, f3);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - f5, f, f3);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        for (int i = 0; i < res; i++) {
            float f6 = (float) i / res;
            float f10 = (f + (f2 - f) * f6) - (1 / (2 * resSq));
            float f14 = f4 * f6;
            tessellator.addVertexWithUV(f14, 0.0D, 0.0F - f5, f10, f3);
            tessellator.addVertexWithUV(f14, 0.0D, 0.0D, f10, f3);
            tessellator.addVertexWithUV(f14, 1.0D, 0.0D, f10, f1);
            tessellator.addVertexWithUV(f14, 1.0D, 0.0F - f5, f10, f1);
        }

        tessellator.draw();


        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        for (int j = 0; j < res; j++) {
            float f7 = (float) j / res;
            float f11 = (f + (f2 - f) * f7) - (1 / (2 * resSq));
            float f15 = f4 * f7 + 1 / (res - .01F);
            tessellator.addVertexWithUV(f15, 1.0D, 0.0F - f5, f11, f1);
            tessellator.addVertexWithUV(f15, 1.0D, 0.0D, f11, f1);
            tessellator.addVertexWithUV(f15, 0.0D, 0.0D, f11, f3);
            tessellator.addVertexWithUV(f15, 0.0D, 0.0F - f5, f11, f3);
        }

        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        for (int k = 0; k < res; k++) {
            float f8 = (float) k / res;
            float f12 = (f3 + (f1 - f3) * f8) - (1 / (2 * resSq));
            float f16 = f4 * f8 + 1 / (res - .01F);
            tessellator.addVertexWithUV(0.0D, f16, 0.0D, f, f12);
            tessellator.addVertexWithUV(f4, f16, 0.0D, f2, f12);
            tessellator.addVertexWithUV(f4, f16, 0.0F - f5, f2, f12);
            tessellator.addVertexWithUV(0.0D, f16, 0.0F - f5, f, f12);
        }

        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        for (int l = 0; l < res; l++) {
            float f9 = (float) l / res;
            float f13 = (f3 + (f1 - f3) * f9) - (1 / (2 * resSq));
            float f17 = f4 * f9;
            tessellator.addVertexWithUV(f4, f17, 0.0D, f2, f13);
            tessellator.addVertexWithUV(0.0D, f17, 0.0D, f, f13);
            tessellator.addVertexWithUV(0.0D, f17, 0.0F - f5, f, f13);
            tessellator.addVertexWithUV(f4, f17, 0.0F - f5, f2, f13);
        }

        tessellator.draw();
    }
}