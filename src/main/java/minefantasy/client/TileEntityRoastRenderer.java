package minefantasy.client;


import minefantasy.block.tileentity.TileEntityRoast;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 * 
 * Custom renderers based off render tutorial by MC_DucksAreBest
 */
public class TileEntityRoastRenderer extends TileEntitySpecialRenderer {

    public TileEntityRoastRenderer() {
        model = new ModelSpitRoast();
    }
    
    public TileEntityRoastRenderer(TileEntityRenderer render) {
        model = new ModelSpitRoast();
        this.setTileEntityRenderer(render);
    }

    public void renderAModelAt(TileEntityRoast tile, double d, double d1, double d2, float f) {
        if (tile != null);
        int i = 1;
        if (tile.worldObj != null) {
            i = tile.direction; //this is for rotation
        }

        int j = 90 * i;

        if (i == 0) {
            j = 0;
        }

        if (i == 1) {
            j = 270;
        }

        if (i == 2) {
            j = 180;
        }

        if (i == 3) {
            j = 90;
        }
        if (i == 4) {
            j = 90;
        }
        
        bindTextureByName(data_minefantasy.image("/item/SpitRoast.png"));
        
        GL11.glPushMatrix();//Start all
        GL11.glTranslatef((float) d + 0.5F, (float) d1+1.5F, (float) d2 + 0.5F);
        GL11.glRotatef(j+180, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        
        float itemsGap = 0.17175F;
        float itemsStart = -0.5F + itemsGap;
        model.renderModel(tile.renderLeft(), tile.renderRight(), 0.0625F, tile.willShowBase());
        for(int a = 0; a < 5; a ++)
        {
        	GL11.glPushMatrix();//Start Individual Items
        	ItemStack itemstack = tile.getStackInSlot(a);
    		if(itemstack != null && itemstack.getItem() != null)
    		{
    			float x = itemsStart + (a*itemsGap);
    			float y = 1.0F;
    			float z = 0.0F;
    			float r = getRotationForItem(itemstack.getItem());
    			float scale = 1.0F;
    			
    			GL11.glTranslatef(x, y, z);
    			
    			GL11.glPushMatrix();
    			GL11.glRotatef(r+180, 1, 0, 0);
    			GL11.glScalef(scale, scale, 1);
    			Icon icon = itemstack.getIconIndex();
    			if(icon != null)
    			{
    				GL11.glPushMatrix();
    				GL11.glRotatef(90, 0, 1, 0);
    				renderItem(tile, itemstack, icon, a);
    				GL11.glPopMatrix();
    			}
    			GL11.glPopMatrix();
    		}
    		
    		GL11.glPopMatrix();//END individual item placement
        }
        GL11.glPopMatrix(); //end all
        

    }

    private void bindTextureByName(String image)
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}
    
	private float getRotationForItem(Item item) {
		String classname = item.getClass().getName();
		if(classname.endsWith("ItemSpade") || classname.endsWith("ItemCrossbow") || classname.endsWith("ItemBlunderbuss") || classname.endsWith("ItemBlowgun") || classname.endsWith("ItemMusket"))
		{
			return 45F;
		}
		return -45F;
	}

	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityRoast) tileentity, d, d1, d2, f); //where to render
    }
	
	private void renderItem(TileEntityRoast tile, ItemStack itemstack, Icon icon, int slot) {
		Minecraft mc = Minecraft.getMinecraft();
	    	mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
	
	    	GL11.glColor3f(255, 255, 255);
	        Tessellator image = Tessellator.instance;
	        float x1 = icon.getMinU();
	        float x2 = icon.getMaxU();
	        float y1 = icon.getMinV();
	        float y2 = icon.getMaxV();
	        float xPos = 0.3F;
	        float yPos = 0.3F;
	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        GL11.glTranslatef(-xPos, -yPos, 0.0F);
	        float scale = 0.6F;
	        GL11.glScalef(scale, scale, scale);
	        ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		
	        
	        GL11.glPushMatrix();
	        if (itemstack != null && tile.isEnchanted(slot))
            {
                GL11.glDepthFunc(GL11.GL_EQUAL);
                GL11.glDisable(GL11.GL_LIGHTING);
                mc.renderEngine.bindTexture(TextureHelperMF.ITEM_GLINT);
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
	        GL11.glPopMatrix();
	}
	
    private ModelSpitRoast model;
}