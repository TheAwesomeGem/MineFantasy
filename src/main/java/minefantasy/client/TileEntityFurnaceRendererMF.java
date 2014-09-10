package minefantasy.client;

import minefantasy.block.tileentity.TileEntityFurnaceMF;
import minefantasy.block.tileentity.TileEntityTripHammer;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class TileEntityFurnaceRendererMF extends TileEntitySpecialRenderer {

public TileEntityFurnaceRendererMF() {
model = new ModelFurnaceMF();
}
public TileEntityFurnaceRendererMF(TileEntityRenderer render) {
model = new ModelFurnaceMF();
this.setTileEntityRenderer(render);
}


public void renderAModelAt(TileEntityFurnaceMF tile, double d, double d1, double d2, float f) {
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

    String type = tile.getTexture();
    
    
    bindTextureByName(data_minefantasy.image("/item/"+type+".png")); //texture
                	
    boolean display = tile.isBurning();
    
    GL11.glPushMatrix(); //start
    float scale = 1.0F;
    
    float offset = (1.0F - 0.0625F);
    GL11.glTranslatef((float)d + 0.5F, (float)d1 + (0.0635F * 2F), (float)d2 + 0.5F); //size
    GL11.glRotatef(j-90, 0.0F, 1.0F, 0.0F);
    GL11.glScalef(scale, -scale, -scale);
    model.renderModel(display, 0.0625F);
    
    float sc = 0.75F;
    float angle = 90F / 20F * (float)tile.doorAngle;
    GL11.glPushMatrix();
	  GL11.glTranslatef(-pixel(8), -pixel(12), 0);
	  if(tile.isHeater())
	  {
		  GL11.glTranslatef(0, pixel(12), 0);
	  }
	  GL11.glPushMatrix();
	    GL11.glScalef(sc, sc, sc);
	    GL11.glRotatef(90, 0, 1, 0);
	    if(tile.isHeater())
	    {
	    	GL11.glRotatef(angle, 1F, 0, 0);
	    	renderDoorHeater(tile, 0, 54, 12, 8, 128, 64);
	    }
	    else
	    {
	    	GL11.glRotatef(-angle, 1F, 0, 0);
	    	renderDoor(tile, 0, 54, 12, 8, 128, 64);
	    }
	  GL11.glPopMatrix();
	GL11.glPopMatrix();


    GL11.glPopMatrix(); //end

}

private void bindTextureByName(String image)
{
	bindTexture(TextureHelperMF.getResource(image));
}

public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
renderAModelAt((TileEntityFurnaceMF) tileentity, d, d1, d2, f); //where to render
}


private void renderDoor(TileEntityFurnaceMF tile, int x, int y, int w, int h, int tw, int th) {
	Minecraft mc = Minecraft.getMinecraft();
	
	float f = 0.01F / (float)tw;
    float f1 = 0.01F / (float)th;
    
	float x1 = (float)x / (float)tw + f;
	float x2 = (float)(x + w) / tw - f;
	float y1 = (float)y / th + f1;
	float y2 = (float)(y + h) / th - f1;
    
    Tessellator image = Tessellator.instance;
    float xPos = 0.5F;
    float yPos = 0.0F;
    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    GL11.glTranslatef(-xPos, -yPos, pixel(0.5F));
    float var13 = 1F;
    GL11.glScalef(var13, var13, var13);
    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
    GL11.glTranslatef(-1F, -1F, 0.0F);
    ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, tw, th, 0.0625F);
    

}

private void renderDoorHeater(TileEntityFurnaceMF tile, int x, int y, int w, int h, int tw, int th) {
	Minecraft mc = Minecraft.getMinecraft();
	
	float f = 0.01F / (float)tw;
    float f1 = 0.01F / (float)th;
    
	float x1 = (float)x / (float)tw + f;
	float x2 = (float)(x + w) / tw - f;
	float y1 = (float)y / th + f1;
	float y2 = (float)(y + h) / th - f1;
    
    Tessellator image = Tessellator.instance;
    float xPos = 0.5F;
    float yPos = 1.0F;
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


private ModelFurnaceMF model;
}