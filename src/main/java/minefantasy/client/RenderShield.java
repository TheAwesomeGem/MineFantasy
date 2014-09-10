package minefantasy.client;

import cpw.mods.fml.client.FMLClientHandler;
import minefantasy.item.mabShield.ItemShield;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderShield implements IItemRenderer
{
    private Minecraft mc;
    private RenderItem itemRenderer;

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) 
    {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) 
    {
        return type == ItemRenderType.ENTITY &&
                (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        if(item.getItem() instanceof ItemShield)
        {
            if (mc == null) 
            {
                mc = FMLClientHandler.instance().getClient();
                itemRenderer = new RenderItem();
            }

            ItemShield shield = (ItemShield)item.getItem();

            GL11.glPushMatrix();

            float sc = shield.getScale(item);
            
            Tessellator tessellator = Tessellator.instance;

            int col = shield.getColor(item);
            float red = (float)(col >> 16 & 255) / 255.0F;
            float green = (float)(col >> 8 & 255) / 255.0F;
            float blue = (float)(col & 255) / 255.0F;

            Icon icon = shield.getIconIndex(item);

            switch (type)
            {
                case ENTITY:
                    GL11.glTranslatef(-0.5F, -0.25F, 0);

                    GL11.glColor3f(1,1,1);
                    ItemRenderer.renderItemIn2D(tessellator,
                            icon.getMaxU(),
                            icon.getMinV(),
                            icon.getMinU(),
                            icon.getMaxV(),
                            icon.getIconWidth(),
                            icon.getIconHeight(), 16F / 256F);
                    
                    if(shield.hasColor(item))
                    {
	                    icon = shield.getPaintIcon();
	                    GL11.glColor3f(red, green, blue);
	                    ItemRenderer.renderItemIn2D(tessellator,
	                    		icon.getMaxU(),
	                            icon.getMinV(),
	                            icon.getMinU(),
	                            icon.getMaxV(),
	                            icon.getIconWidth(),
	                            icon.getIconHeight(), 16F/256F);
                    }

                    GL11.glTranslatef(0, 0, -16F/256F);
                    icon = shield.getBackIcon();
                    GL11.glColor3f(1,1,1);
                    ItemRenderer.renderItemIn2D(tessellator,
                    		icon.getMaxU(),
                            icon.getMinV(),
                            icon.getMinU(),
                            icon.getMaxV(),
                            icon.getIconWidth(),
                            icon.getIconHeight(), 1F/256F);

                    GL11.glTranslatef(0, 0, 24F/256F);
                    icon = shield.getTrimIcon();
                    GL11.glColor3f(1,1,1);
                    ItemRenderer.renderItemIn2D(tessellator,
                    		icon.getMaxU(),
                            icon.getMinV(),
                            icon.getMinU(),
                            icon.getMaxV(),
                            icon.getIconWidth(),
                            icon.getIconHeight(), (8F+16F)/256F);

                    if(item.isItemEnchanted())
                    	TextureHelperMF.renderEnchantmentEffects(tessellator);
                    break;
                case EQUIPPED:
                case EQUIPPED_FIRST_PERSON:
                	
                	EntityLivingBase user = null;
                	
                	if(data.length >= 2 && data[1] instanceof EntityLivingBase)
            		{
            			user = (EntityLivingBase)data[1];
            		}
                	if(user == null)
                	{
                		GL11.glRotatef(-135, 0, 0, 1);
                		GL11.glTranslatef(-1.0F - 0.203125F, -0.25F, 0F);
                	}

                	GL11.glScalef(sc, sc, sc);
                	GL11.glTranslatef(-(sc-1.0F)*0.25F, -(sc-1.0F)*0.5F, 0F);
                	
                    GL11.glColor3f(1, 1, 1);
                    ItemRenderer.renderItemIn2D(tessellator,
                    		icon.getMaxU(),
                            icon.getMinV(),
                            icon.getMinU(),
                            icon.getMaxV(),
                            icon.getIconWidth(),
                            icon.getIconHeight(), 16F/256F);
                    
                    if(shield.hasColor(item))
                    {
	                    icon = shield.getPaintIcon();
	                    GL11.glColor3f(red, green, blue);
	                    ItemRenderer.renderItemIn2D(tessellator,
	                    		icon.getMaxU(),
	                            icon.getMinV(),
	                            icon.getMinU(),
	                            icon.getMaxV(),
	                            icon.getIconWidth(),
	                            icon.getIconHeight(), 16F/256F);
                    }
                    
                    GL11.glColor3f(1, 1, 1);
                    GL11.glTranslatef(0, 0, 1F/256F);
                    icon = shield.getBackIcon();
                    ItemRenderer.renderItemIn2D(tessellator,
                    		icon.getMaxU(),
                            icon.getMinV(),
                            icon.getMinU(),
                            icon.getMaxV(),
                            icon.getIconWidth(),
                            icon.getIconHeight(), 1F/256F);

                    GL11.glColor3f(1,1,1);

                    GL11.glTranslatef(0, 0, -1F/256F);
                    icon = shield.getTrimIcon();
                    ItemRenderer.renderItemIn2D(tessellator,
                    		icon.getMaxU(),
                            icon.getMinV(),
                            icon.getMinU(),
                            icon.getMaxV(),
                            icon.getIconWidth(),
                            icon.getIconHeight(), (8F+16F)/256F);

                    if(item.isItemEnchanted())
                    	TextureHelperMF.renderEnchantmentEffects(tessellator);

                    break;
                case INVENTORY:

                    GL11.glPushMatrix();
                    GL11.glColor3f(1, 1, 1);
                    itemRenderer.renderIcon(0, 0, icon, 16, 16);
                    
                    if(shield.hasColor(item))
                    {
	                    GL11.glColor3f(red, green, blue);
	                    icon = shield.getPaintIcon();
	                    itemRenderer.renderIcon(0, 0, icon, 16, 16);
                    }
                    
                    GL11.glColor3f(1, 1, 1);
                    icon = shield.getTrimIcon();
                    itemRenderer.renderIcon(0, 0, icon, 16, 16);

                    GL11.glPopMatrix();

                    break;
            }

            int arrowCount = shield.getArrowCount(item);
	    //Bounds checking (rendering this many is quite silly, any more would look VERY silly)
            if(arrowCount > 64)
                arrowCount = 64;
           
            for(int i = 0; i < arrowCount; i++){
                renderArrow(type == ItemRenderType.ENTITY,
                        ItemShield.arrowX[i],ItemShield.arrowY[i], ItemShield.arrowDepth[i],
                        ItemShield.pitch[i]+90F, ItemShield.yaw[i]+45F);
            }
            GL11.glPopMatrix();

        }
    }

    public static void renderArrow(boolean isEntity, float x, float y, float depth, float pitch, float yaw){
        GL11.glPushMatrix();

        //depth = 1;

        Minecraft.getMinecraft().renderEngine.bindTexture(arrowTex);
        Tessellator tessellator = Tessellator.instance;

        byte b0 = 0;
        float f2 = 12F/32F * depth;
        float f3 = 0F;
        float f4 = (float)(0 + b0 * 10) / 32.0F;
        float f5 = (float)(5 + b0 * 10) / 32.0F;
        float f10 = 0.05F;

        GL11.glScalef(f10, f10, f10);
        if(isEntity){
            GL11.glScalef(1, 1, -1);
        }

        GL11.glTranslatef(x + 8 + 2.5F, y + 8 + 1.5F, 0);

        GL11.glRotatef( pitch, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef( yaw, 1.0F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);

        for (int i = 0; i < 2; ++i)
        {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0.0D * depth, -2.0D, 0.0D, (double)f2, (double)f4);
            tessellator.addVertexWithUV(16.0D * depth, -2.0D, 0.0D, (double)f3, (double)f4);
            tessellator.addVertexWithUV(16.0D * depth, 2.0D, 0.0D, (double)f3, (double)f5);
            tessellator.addVertexWithUV(0.0D * depth, 2.0D, 0.0D, (double)f2, (double)f5);
            tessellator.draw();

            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0.0D * depth, 2.0D, 0.0D, (double)f2, (double)f5);
            tessellator.addVertexWithUV(16.0D * depth, 2.0D, 0.0D, (double)f3, (double)f5);
            tessellator.addVertexWithUV(16.0D * depth, -2.0D, 0.0D, (double)f3, (double)f4);
            tessellator.addVertexWithUV(0.0D * depth, -2.0D, 0.0D, (double)f2, (double)f4);
            tessellator.draw();
        }
        GL11.glPopMatrix();
    }
    private static final ResourceLocation arrowTex = new ResourceLocation("textures/entity/arrow.png");
}
