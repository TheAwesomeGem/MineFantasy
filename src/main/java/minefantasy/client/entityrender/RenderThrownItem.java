package minefantasy.client.entityrender;



import minefantasy.client.TextureHelperMF;
import minefantasy.entity.EntityArrowMF;
import minefantasy.entity.EntityThrowableBounce;
import minefantasy.entity.IThrownItem;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;



public class RenderThrownItem extends Render
{

    public RenderThrownItem()
    {
        this.shadowSize = 0.5F;
    }
    
    public void renderArrow(Entity arrow, double x, double y, double z, float xr, float yr)
    {
    	boolean isEnchanted = false;
    	Minecraft mc = Minecraft.getMinecraft();
    	mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(arrow.prevRotationYaw + (arrow.rotationYaw - arrow.prevRotationYaw) * yr - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(arrow.prevRotationPitch + (arrow.rotationPitch - arrow.prevRotationPitch) * yr, 0.0F, 0.0F, 1.0F);
        Tessellator image = Tessellator.instance;
        Icon icon = null;
        ItemStack itemstack = null;
        float scale = 1.0F;
        float spin = 0.0F;
        float rotate = 0.0F;
        if(arrow instanceof IThrownItem)
        {
        	IThrownItem t = (IThrownItem)arrow;
        	ItemStack is = ((IThrownItem)arrow).getRenderItem();
        	if(is != null && is.getItem() != null)
        	{
        		icon = is.getIconIndex();
        		itemstack = is;
        	}
        	isEnchanted = t.isEnchanted();
        	scale = t.getScale();
    		spin = t.getSpin();
    		rotate = t.getRotate();
        }
        
        GL11.glPushMatrix();
        GL11.glRotatef(spin-135, 0, 0, 1);
        if(icon != null)
        {
        	float x1 = icon.getMinU();
	        float x2 = icon.getMaxU();
	        float y1 = icon.getMinV();
	        float y2 = icon.getMaxV();
	        float xPos = 0.5F*scale;
	        float yPos = 0.5F*scale;
	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        GL11.glTranslatef(-xPos, -yPos, 0.0F);
	        GL11.glScalef(scale, scale, 1);
	        
	        ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
	        
	        if (itemstack != null && isEnchanted)
            {
                GL11.glDepthFunc(GL11.GL_EQUAL);
                GL11.glDisable(GL11.GL_LIGHTING);
                this.renderManager.renderEngine.bindTexture(TextureHelperMF.ITEM_GLINT);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                float f13 = 0.76F;
                GL11.glColor4f(0.5F * f13, 0.25F * f13, 0.8F * f13, 1.0F);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glPushMatrix();
                float f14 = 0.125F;
                GL11.glScalef(f14, f14, f14);
                float f15 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                GL11.glTranslatef(f15, 0.0F, 0.0F);
                GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                ItemRenderer.renderItemIn2D(image, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, 0.0625F);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(f14, f14, f14);
                f15 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                GL11.glTranslatef(-f15, 0.0F, 0.0F);
                GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                ItemRenderer.renderItemIn2D(image, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, 0.0625F);
                GL11.glPopMatrix();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
            }
        }
 
        GL11.glPopMatrix();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double x, double y, double z, float xr, float yr)
    {
        this.renderArrow(entity, x, y, z, xr, yr);
    }
    
    @Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
	private void loadTexture(String image) 
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}
}
