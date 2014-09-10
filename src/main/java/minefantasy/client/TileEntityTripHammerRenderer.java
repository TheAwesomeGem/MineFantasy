package minefantasy.client;

import minefantasy.block.tileentity.TileEntityTanningRack;
import minefantasy.block.tileentity.TileEntityTripHammer;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
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
public class TileEntityTripHammerRenderer extends TileEntitySpecialRenderer {

    public TileEntityTripHammerRenderer() {
        model = new ModelTripHammer();
        model2 = new ModelTripHammerBack();
    }
    public TileEntityTripHammerRenderer(TileEntityRenderer render) {
        model = new ModelTripHammer();
        model2 = new ModelTripHammerBack();
        this.setTileEntityRenderer(render);
    }

    public void renderAModelAt(TileEntityTripHammer tile, double d, double d1, double d2, float f) {
        if (tile != null);
        int i = 0;
        if (tile.worldObj != null) {
            i = tile.direction; //this is for rotation
        }

        int j = 90 * i;

        if (i == 1) {
            j = 0;
        }

        if (i == 2) {
            j = 270;
        }

        if (i == 3) {
            j = 180;
        }

        if (i == 0) {
            j = 90;
        }
        if (i == 0) {
            j = 90;
        }
        float p = tile.getArmValue();
        if(tile.getType() == 0)
        {
        	model.rotate(p);
        }
        
        String type = "TripHammer";
        if(tile.getType() == 1)
        	type = "TripHammerBack";
        
        bindTextureByName(data_minefantasy.image("/item/"+type+".png")); //texture
        GL11.glPushMatrix(); //start
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.525F, (float) d2 + 0.5F); //size
        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F); //rotate based on metadata
        GL11.glScalef(1.0F, -1F, -1F); //if you read this comment out this line and you can see what happens
        if(tile.getType() == 1)
        {
        	model2.renderModel(p, 0.0625F);
        }
        else
        {
        	model.renderModel(p, 0.0625F);
        }
        
        GL11.glPushMatrix();
	        GL11.glTranslatef(-pixel(1), pixel(11), 0.35F);
	        GL11.glPushMatrix();
	        GL11.glScalef(0.75F, 0.75F, 0.75F);
	        GL11.glRotatef(p*360, 0, 0, 1F);
	        renderWheel(tile, 86, 24, 20, 20, 128, 64);
	        GL11.glPopMatrix();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(-pixel(1), pixel(11), -0.35F);
        GL11.glPushMatrix();
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        GL11.glRotatef(p*360, 0, 0, 1F);
        renderWheel(tile, 86, 24, 20, 20, 128, 64);
        GL11.glPopMatrix();
    GL11.glPopMatrix();
        
        GL11.glPopMatrix(); //end

    }
    
    private void bindTextureByName(String image)
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}

    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityTripHammer) tileentity, d, d1, d2, f); //where to render
    }
    
    
    private void renderWheel(TileEntityTripHammer tile, int x, int y, int w, int h, int tw, int th) {
		Minecraft mc = Minecraft.getMinecraft();
		
		float f = 0.01F / (float)tw;
        float f1 = 0.01F / (float)th;
        
		float x1 = (float)x / (float)tw + f;
		float x2 = (float)(x + w) / tw - f;
		float y1 = (float)y / th + f1;
		float y2 = (float)(y + h) / th - f1;
        
        Tessellator image = Tessellator.instance;
        float xPos = 0.5F;
        float yPos = 0.5F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-xPos, -yPos, pixel(0.5F));
        float var13 = 1F;
        GL11.glScalef(var13, var13, var13);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-1F, -1F, 0.0F);
        ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, tw, th, 0.0625F);
	
	}
    public float pixel(float count)
    {
    	return count * 0.0625F;
    }
    private ModelTripHammerBack model2;
    private ModelTripHammer model;
}